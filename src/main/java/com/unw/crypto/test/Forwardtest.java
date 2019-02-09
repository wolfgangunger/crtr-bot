package com.unw.crypto.test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ta4j.core.Order;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.num.DoubleNum;

import com.unw.crypto.chart.BarUtil;
import com.unw.crypto.chart.TickUtil;
import com.unw.crypto.db.TickRepository;
import com.unw.crypto.loader.TimeSeriesDBLoader;
import com.unw.crypto.model.AddOrderInfo;
import com.unw.crypto.model.BarDuration;
import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import com.unw.crypto.model.ExtOrder;
import com.unw.crypto.model.Tick;
import com.unw.crypto.service.MarketAnalyzer;
import com.unw.crypto.strategy.FinalTradingStrategy;
import com.unw.crypto.strategy.FinalTradingStrategyShort;
import com.unw.crypto.strategy.IFinalTradingStrategy;
import com.unw.crypto.strategy.LogUtil;
import com.unw.crypto.strategy.StrategyUtil;
import com.unw.crypto.strategy.to.StrategyInputParams;
import java.time.LocalDate;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.Strategy;
import org.ta4j.core.TradingRecord;

@Component
public class Forwardtest {

    ForwardtestService forwardtestService;

    StategyConfiguarationService strategyConfiguarationService;

    @Autowired
    private TimeSeriesDBLoader timeSeriesDBLoader;

    @Autowired
    private MarketAnalyzer marketAnalyzer;

    @Autowired
    private TickRepository tickRepository;

    private List<ExtOrder> forwardTestOrders = new ArrayList<>();

    private TimeSeries completeSeries;

    private TimeSeries preSeries;

    private TimeSeries series;

    private List<Tick> ticks;

    private FinalTradingStrategy finalTradingStrategyLong = new FinalTradingStrategy();
    private FinalTradingStrategyShort finalTradingStrategyShort = new FinalTradingStrategyShort();
    private IFinalTradingStrategy finalTradingStrategy;
    //private AbstractStrategy currentStrategy;
    private Strategy tradingStrategy;

    // month to load  before the period, should be 2 to be able to build MA200 for 10 Min Bar, for faster testing it is reduced to one 
    private static final int PRE_MONTH = 1;

    @Autowired
    public Forwardtest(TickRepository tickRepository, ForwardtestService forwardtestService,
            StategyConfiguarationService strategyConfiguarationService) {
        this.tickRepository = tickRepository;
        this.forwardtestService = forwardtestService;
        this.strategyConfiguarationService = strategyConfiguarationService;
    }

    public void forwardtest() {

        // increase this number
        int testRun = 10;
        // set this to false for short strategy
        boolean tradeLong = true;
        Currency currency = Currency.BTC;
        Exchange exchange = Exchange.BITSTAMP;
        // iterate over 12 month
        for (int i = 1; i <= 12; i++) {
            // for (int i = 1; i <= 12; i++) {
            LocalDate from = LocalDate.of(2018, i, 1);
            LocalDate until = from.plusMonths(1);
            //LocalDate until = from.plusWeeks(1);
            //execute test
            System.out.println("Run test for " + exchange.getStringValue() + " for month " + i);
            loadSeries(from, until, currency, exchange, BarDuration.TWO_HOURS, testRun, tradeLong);
        }
    }

    public void loadSeries(LocalDate from, LocalDate until, Currency currency, Exchange exchange, BarDuration barDuration, int testRun, boolean tradeLong) {

        int barDurationInMinutes = barDuration.getIntValue();
        // series for live testing, 2 month before series 
        System.out.println("Loading ticks");
        ticks = timeSeriesDBLoader.loadTicksWithParams(from, until, currency, exchange, barDurationInMinutes);
        //series = timeSeriesDBLoader.loadSeriesWithParams(from.getValue(), until.getValue(), cmbCurrency.getValue(), cmbExchange.getValue(), barDurationInMinutes);
        if (ticks.isEmpty()) {
            System.out.println("no data found" + currency + " " + exchange + " ; please reload with different params");
            // create a dummy tick to avoid NPE
            ticks.add(TickUtil.createDummyTick(currency.getStringValue(), exchange.getStringValue()));
        }
        System.out.println("Loading series");
        series = timeSeriesDBLoader.loadSeriesByTicks(ticks, barDurationInMinutes);
        // loading the pre-series ( 2 month before from)
        LocalDate preFrom = from.minusMonths(PRE_MONTH);
        System.out.println("Loading preseriesF");
        preSeries = timeSeriesDBLoader.loadSeriesWithParams(preFrom, from, currency, exchange, barDurationInMinutes);
        if (tradeLong) {
            //currentStrategy = finalTradingStrategyLong;
            finalTradingStrategy = finalTradingStrategyLong;
        } else {
            //currentStrategy = finalTradingStrategyShort;
            finalTradingStrategy = finalTradingStrategyShort;
        }

        StrategyInputParams params;
        for (int i = 1; i <= 6; i++) {
            params = StrategyInputParamsCreator.createStrategyInputParams(i, barDuration);
            System.out.println("-Run test for configuration " + i);
            executeForwardTest(barDurationInMinutes, params, i, currency, exchange, testRun);
        }
        // short
//            params = StrategyInputParamsCreator.createStrategyInputParams(-1, barDuration);
//            System.out.println("-Run test for configuration -1" );
//            executeForwardTest(barDurationInMinutes, params, -1, currency, exchange, testRun);
    }

    private void executeForwardTest(int candleMinutes, StrategyInputParams params, int configNumber, Currency currency, Exchange exchange, int testRun) {

        forwardTestOrders = new ArrayList<>();
        completeSeries = StrategyUtil.copySeries(preSeries);
        //completeSeries = preSeries;
        boolean entered = false;
        //double ticksSize = ticks.size();
        double progressBarCounter = 0;
        ZonedDateTime beginTimeCurrentBar = completeSeries.getLastBar().getEndTime();
        // for trailing stop loss
        TradingRecord tr = new BaseTradingRecord();
        for (Tick tick : ticks) {
            if (beginTimeCurrentBar.isAfter(ZonedDateTime.of(LocalDateTime.ofInstant(tick.getTradeTime().toInstant(), ZoneId.systemDefault()), ZoneId.systemDefault()))) {
                System.out.println("overlap " + tick.getTradeTime());
                progressBarCounter++;
                continue;
            }
            if (beginTimeCurrentBar.plusMinutes(candleMinutes)
                    .isBefore(ZonedDateTime.of(LocalDateTime.ofInstant(tick.getTradeTime().toInstant(), ZoneId.systemDefault()), ZoneId.systemDefault()))) {
                completeSeries.addBar(BarUtil.createNewBar(tick));
                beginTimeCurrentBar = completeSeries.getLastBar().getEndTime();
                //System.out.println((progressBarCounter / ticksSize) * 100 + " %");
            } else {
                completeSeries.getLastBar().addTrade(DoubleNum.valueOf(tick.getAmount()),
                        DoubleNum.valueOf(tick.getPrice()));
            }
            // now lets check if we execute the strategy ( once in a minute)
            int time = StrategyUtil.getTimeInHoursAndMinutes(tick);
            int prevTime = progressBarCounter > 1 ? StrategyUtil.getTimeInHoursAndMinutes(ticks.get((int) progressBarCounter - 1)) : StrategyUtil.getTimeInHoursAndMinutes(tick);
            boolean lastTick = tick.getId().equals(ticks.get(ticks.size() - 1).getId());
            if (time == prevTime) {
                // we execute the strategy only once in a minute, if there are more ticks, we do nothing
            } else {
                // execute the strategy at least every minute
                tradingStrategy = finalTradingStrategy.buildStrategyWithParams(completeSeries, params);
                // needed for stop loss

                if (tradingStrategy.shouldEnter(completeSeries.getEndIndex(), tr) && !entered) {
                    ExtOrder order = new ExtOrder(Order.buyAt(completeSeries.getEndIndex(), completeSeries));
                    order.setTradeTime(tick.getTradeTime());
                    tr.enter(order.getIndex(), order.getPrice(), order.getAmount());
                    AddOrderInfo info = marketAnalyzer.analyzeOrderParams(completeSeries, params.getRsiTimeframeBuy(), params.getStoRsiTimeframeBuy());
                    order.setAddOrderInfo(info);
                    forwardTestOrders.add(order);
                    entered = true;
                } else if (tradingStrategy.shouldExit(completeSeries.getEndIndex(), tr) && entered) {
                    ExtOrder order = new ExtOrder(Order.sellAt(completeSeries.getEndIndex(), completeSeries));
                    order.setTradeTime(tick.getTradeTime());
                    AddOrderInfo info = marketAnalyzer.analyzeOrderParams(completeSeries, params.getRsiTimeframeBuy(), params.getStoRsiTimeframeBuy());
                    order.setAddOrderInfo(info);
                    forwardTestOrders.add(order);
                    tr.exit(order.getIndex());
                    entered = false;
                }
            }
            if (lastTick && entered) {
                System.out.println("Trade open on last tick- sell anyway");
                // if there is still a trade open in last tick, sell anyway
                ExtOrder order = new ExtOrder(Order.sellAt(completeSeries.getEndIndex(), completeSeries));
                order.setTradeTime(tick.getTradeTime());
                AddOrderInfo info = marketAnalyzer.analyzeOrderParams(completeSeries, params.getRsiTimeframeBuy(), params.getStoRsiTimeframeBuy());
                order.setAddOrderInfo(info);
                forwardTestOrders.add(order);
                tr.exit(order.getIndex());
                entered = false;
            }
            progressBarCounter++;
        }
        TradingRecord record = StrategyUtil.buildTradingRecord(forwardTestOrders);
        updateLog(record);
        forwardtestService.persistForwardtestResult(record, completeSeries, configNumber, "", currency, exchange, testRun, finalTradingStrategy.getClass().getSimpleName());
        // LogUtil.printAddOrderInfo(forwardTestOrders);
    }

    private void updateLog(TradingRecord tradingRecord) {
        String log = LogUtil.createLogOutput(tradingRecord, series);
        System.out.println(log);
        // textArea.setText(log);
    }

    //// -         -----------
//    public void forwardtest1() throws ParseException {
//        List<String> configurations = strategyConfiguarationService.getStrategyConfiguration(StrategyType.RSI);
//
//        List<Tick> ticks = getTicksForPeriod("2018-06-01 00:00", "2018-06-15 23:59");
//        configurations.parallelStream().forEach(config -> {
//            try {
//                executeBruteForceTest(ticks, strategyType, config);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//        });
//    }
//    private void executeBruteForceTest(List<Tick_old> ticks, StrategyType strategyType, String config)
//            throws ParseException {
//
//        TimeSeries timeSeries = new BaseTimeSeries.SeriesBuilder().withName("testSeries" + config.hashCode())
//                .withNumTypeOf(DoubleNum.class).build();
//
//        boolean entered = false;
//        List<Order> orders = new ArrayList<>();
//        ZonedDateTime beginTimeCurrentBar = null;
//
//        for (Tick_old tick : ticks) {
//            if (timeSeries.isEmpty()) {
//                timeSeries.addBar(createNewBar(tick));
//                beginTimeCurrentBar = timeSeries.getLastBar().getEndTime();
//            } else if (beginTimeCurrentBar.plusMinutes(CANDLE_SIZE_MIN)
//                    .isBefore(ZonedDateTime.of(tick.getTradeTime(), ZoneId.of("UTC")))) {
//                timeSeries.addBar(createNewBar(tick));
//                beginTimeCurrentBar = timeSeries.getLastBar().getEndTime();
//            } else {
//                timeSeries.getLastBar().addTrade(DoubleNum.valueOf(tick.getAmount()),
//                        DoubleNum.valueOf(tick.getPrice()));
//            }
//
//            Strategy strategy = strategyService.getStrategy(strategyType, timeSeries, config);
//            if (strategy.shouldEnter(timeSeries.getEndIndex()) && !entered) {
//                orders.add(Order.buyAt(timeSeries.getEndIndex(), timeSeries));
//                entered = true;
//            } else if (strategy.shouldExit(timeSeries.getEndIndex()) && entered) {
//                orders.add(Order.sellAt(timeSeries.getEndIndex(), timeSeries));
//                entered = false;
//            }
//        }
//
//        if (orders.size() > 1) {
//            forwardtestService.persistForwardtestResult(orders, timeSeries, strategyType, config);
//        }
//    }
}
