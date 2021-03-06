package com.unw.crypto.test;

import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import com.unw.crypto.model.ExtOrder;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.ta4j.core.AnalysisCriterion;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.Order;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;

@Service
public class ForwardtestService {

    ForwardtestResultRepository forwardtestResultRepository;

    private static final String LB = "\n";
    private static final String TAB = "\t";

    @Autowired
    public ForwardtestService(ForwardtestResultRepository forwardtestResultRepository) {
        this.forwardtestResultRepository = forwardtestResultRepository;
    }

    public void persistForwardtestResult(TradingRecord tradingRecord, TimeSeries series, int configNumber, String config, Currency currency, Exchange exchange, int testRun, String strategy) {
        ForwardtestResult result = new ForwardtestResult();
        result.setConfigNumber(configNumber);
        result.setStrategy(strategy);
        result.setTestRun(testRun);
        result.setCurrency(currency.getStringValue());
        result.setExchange(exchange.getStringValue());
        result.setPeriodStart(series.getFirstBar().getEndTime().toLocalDateTime());
        result.setPeriodEnd(series.getLastBar().getEndTime().toLocalDateTime());
        result.setNumberOfTrades(tradingRecord.getTradeCount());
        // Analysis
        result.setTotalProfit(new TotalProfitCriterion().calculate(series, tradingRecord).doubleValue());
        //
        List<org.ta4j.core.Trade> trades = tradingRecord.getTrades();
        List<Trade> resultTrades = new ArrayList<>();
        Num sum = DoubleNum.valueOf(0d);
        Num averagePercent = DoubleNum.valueOf(0d);
        for (org.ta4j.core.Trade trade : trades) {
            Trade resultTrade = new Trade();
            resultTrade.setPriceEntry(trade.getEntry().getPrice().doubleValue());
            resultTrade.setPriceExit(trade.getExit().getPrice().doubleValue());
            Num diff = trade.getExit().getPrice().minus(trade.getEntry().getPrice());
            Num diffPercent = trade.getExit().getPrice().dividedBy(trade.getEntry().getPrice());
            diffPercent = diffPercent.minus(DoubleNum.valueOf(1));
            diffPercent = diffPercent.multipliedBy(DoubleNum.valueOf(100));
            resultTrade.setAmount(diff.floatValue());
            resultTrade.setPercent(diffPercent.doubleValue());
            resultTrade.setIndexDiff(trade.getExit().getIndex() - trade.getEntry().getIndex());
//            sb.append(LB);
            sum = sum.plus(diff);
            averagePercent = averagePercent.plus(diffPercent);
            resultTrades.add(resultTrade);
        }
        result.setTotal(sum.doubleValue());
        result.setTotalPercent(averagePercent.doubleValue());
    
        if(trades.size()> 0){
            averagePercent = averagePercent.dividedBy(DoubleNum.valueOf(trades.size()));
        }
        result.setAveragePercent(averagePercent.doubleValue());

        result.setTrades(resultTrades);
        resultTrades.stream().forEach(t -> t.setForwardtestResult(result));
        forwardtestResultRepository.save(result);
    }

    public void persistForwardtestResult2(List<ExtOrder> orders, TimeSeries timeSeries, String config) {
        List<Trade> trades = new ArrayList<>();

        int numberOfTrades = 0;
//        for (Order order : orders) {
//            Trade trade = new Trade();
//            if (order.isBuy()) {
//                trade.setOrderType(OrderType.BUY);
//            } else {
//                trade.setOrderType(OrderType.SELL);
//                numberOfTrades++;
//
//            }
//            trade.setPrice(order.getPrice().doubleValue());
//            trades.add(trade);
//        }

        ForwardtestResult result = new ForwardtestResult();
        result.setTrades(trades);
        trades.stream().forEach(t -> t.setForwardtestResult(result));

        Order[] orderArray = new Order[orders.size()];
        orderArray = orders.toArray(orderArray);
        TradingRecord tradingRecord = new BaseTradingRecord(orderArray);
        AnalysisCriterion criterion = new TotalProfitCriterion();
        Num profit = criterion.calculate(timeSeries, tradingRecord);

        result.setAvgProfitPerTrade(((profit.doubleValue() * 100) - 100) / numberOfTrades);
        result.setProfit((profit.doubleValue() * 100) - 100);
        result.setPeriodStart(timeSeries.getFirstBar().getEndTime().toLocalDateTime());
        result.setPeriodEnd(timeSeries.getLastBar().getEndTime().toLocalDateTime());
        result.setConfiguration(config);

        forwardtestResultRepository.save(result);
    }

    public void persistForwardtestResult_old(List<Order> orders, TimeSeries timeSeries, String config) {
        List<Trade> trades = new ArrayList<>();

        int numberOfTrades = 0;
//        for (Order order : orders) {
//            Trade trade = new Trade();
//            if (order.isBuy()) {
//                trade.setOrderType(OrderType.BUY);
//            } else {
//                trade.setOrderType(OrderType.SELL);
//                numberOfTrades++;
//
//            }
//            trade.setDate(timeSeries.getBar(order.getIndex()).getEndTime().toLocalDateTime());
//            trade.setPrice(order.getPrice().doubleValue());
//            trades.add(trade);
//        }

        ForwardtestResult result = new ForwardtestResult();
        result.setTrades(trades);
        trades.stream().forEach(t -> t.setForwardtestResult(result));

        Order[] orderArray = new Order[orders.size()];
        orderArray = orders.toArray(orderArray);
        TradingRecord tradingRecord = new BaseTradingRecord(orderArray);
        AnalysisCriterion criterion = new TotalProfitCriterion();
        Num profit = criterion.calculate(timeSeries, tradingRecord);

        result.setAvgProfitPerTrade(((profit.doubleValue() * 100) - 100) / numberOfTrades);
        result.setProfit((profit.doubleValue() * 100) - 100);
        result.setPeriodStart(timeSeries.getFirstBar().getEndTime().toLocalDateTime());
        result.setPeriodEnd(timeSeries.getLastBar().getEndTime().toLocalDateTime());
        result.setConfiguration(config);

        forwardtestResultRepository.save(result);
    }

}
