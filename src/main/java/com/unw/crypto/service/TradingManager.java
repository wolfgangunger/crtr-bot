/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import com.unw.crypto.chart.BarUtil;
import com.unw.crypto.loader.TimeSeriesDBLoader;
import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.Order;
import org.ta4j.core.TimeSeries;

/**
 * main service class coordinating the trades
 *
 * @author UNGERW
 */
@Component
public class TradingManager implements TradeListener {

    // just needed for simulation of live trading
    private int counter = 0;

    @Autowired
    private MarketAnalyzer analyzer;
    @Autowired
    private TimeSeriesDBLoader timeSeriesDBLoader;

//    @Autowired
//    private List<SingleCoinTrader> traders;
    // the trading service - for each currency to trade on service
    @Autowired
    private SingleCoinTradingService[] traders;
    // saving the indexes for the services by currency in this map
    private Map<Currency, Integer> currencyIndex = new HashMap<>();

    private TimeSeries series;
    // is there a trade going on - just one trade at one time
    private boolean entered = false;

    @PostConstruct
    public void init() {
        initTraders();
        if (series == null) {
            LocalDate from = LocalDate.now().minusMonths(20);
            LocalDate until = LocalDate.now().minusMonths(18);
            //series = timeSeriesDBLoader.loadSeriesWithParams(from, until, Currency.BTC, Exchange.COINBASE, 60);
            series = timeSeriesDBLoader.loadSeriesWithParams(from, until, Currency.BTC, Exchange.BITSTAMP, 60);
        }
    }

    public void minuteExecution() {
        watchBTCMarket();
        //watchBTCMarket2();
        //watchAllMarkets();
        //tradeAllMarkets();
    }

    private void tradeAllMarkets() {
        for (int i = 0; i < traders.length; i++) {
            SingleCoinTradingService sct = traders[i];
            sct.trade();
        }
    }

    private void watchBTCMarket2() {
        if (series.getBeginIndex() == -1) {
            return;
        }
        int beginIndex = series.getBeginIndex();
        int endIndex = series.getEndIndex();
        int medium = endIndex / 2;
        int first = beginIndex + counter;
        int last = medium + counter;
        List<Bar> bars = BarUtil.createBarList(first, last, series);
        TimeSeries rollingSeries = new BaseTimeSeries("bitstamp_trades", bars);
        log(rollingSeries);
        // check the MAs

        counter++;

    }

    private void watchBTCMarket() {
        if (series.getBeginIndex() == -1) {
            return;
        }
        int beginIndex = series.getBeginIndex();
        int endIndex = series.getEndIndex();
        int medium = endIndex / 2;
        int first = beginIndex + counter;
        int last = medium + counter;
        List<Bar> bars = BarUtil.createBarList(first, last, series);
        TimeSeries rollingSeries = new BaseTimeSeries("bitstamp_trades", bars);
        //log(rollingSeries);
        analyzer.isClosedPriceRising(rollingSeries, 2, 0.1d);
        double cp = analyzer.determineClosedPriceStrength(rollingSeries, 2);
        //System.out.println("ClosedPirce : " + cp);
        // test for rsi

        boolean bull = analyzer.isBullish(rollingSeries);
        System.out.println("bullish " + bull);
        boolean bear = analyzer.isBearish(rollingSeries);
        System.out.println("bearish " + bear);

        double maS7 = analyzer.determineSMAStrength(rollingSeries, 14, 7);
        System.out.println("strength MA 14 - 7 :  " + maS7);
        double maS28 = analyzer.determineSMAStrength(rollingSeries, 28, 14);
        System.out.println("strength MA 18 - 14 :  " + maS28);

        counter++;
        if (true) {
            return;
        }

        if (analyzer.rsiRisingUp(rollingSeries, 2, 30)) {
            System.out.println("rsi:buy 1 " + counter);
        }
        if (analyzer.rsiPointingDown(rollingSeries, 2, 70)) {
            System.out.println("rsi:sell 1 " + counter);
        }
        if (analyzer.stoRisingUp(rollingSeries, 4, 0.35d)) {
            System.out.println("sto:buy 1 " + counter);
        }
        if (analyzer.stoPointingDown(rollingSeries, 4, 0.65d)) {
            System.out.println("sto:sell 1 " + counter);
        }

        if (analyzer.rsiRisingUp(rollingSeries, 2, 30) && analyzer.stoRisingUp(rollingSeries, 4, 0.35d)) {
            System.out.println("#####rsi+sto:buy 1 " + counter);
        }
        if (analyzer.rsiPointingDown(rollingSeries, 2, 70) && analyzer.stoPointingDown(rollingSeries, 4, 0.65d)) {
            System.out.println("#####rsi+sto:sell 1 " + counter);
        }

        // sma 3
        analyzer.isSmaRising(rollingSeries, 3, 2, 0.1d);
        double sma3 = analyzer.determineSMAStrength(rollingSeries, 3, 2);
        System.out.println("SMA 3 : " + sma3);
        // sma 8
        analyzer.isSmaRising(rollingSeries, 8, 2, 0.1d);
        double sma8 = analyzer.determineSMAStrength(rollingSeries, 8, 2);
        System.out.println("SMA 8 : " + sma8);
        // sma 50
        analyzer.isSmaRising(rollingSeries, 50, 2, 0.1d);
        double sma50 = analyzer.determineSMAStrength(rollingSeries, 50, 2);
        System.out.println("SMA 50 : " + sma50);
        // sma 200
        analyzer.isSmaRising(rollingSeries, 200, 2, 0.1d);
        double sma200 = analyzer.determineSMAStrength(rollingSeries, 200, 2);
        System.out.println("SMA 200 : " + sma200);
        // sma 314
        analyzer.isSmaRising(rollingSeries, 314, 2, 0.1d);
        double sma314 = analyzer.determineSMAStrength(rollingSeries, 314, 2);
        System.out.println("SMA 314: " + sma314);
        // ema 14
        analyzer.isEmaRising(rollingSeries, 14, 2, 0.1d);
        double ema14 = analyzer.determineEMAStrength(rollingSeries, 14, 2);
        System.out.println("EMA 14: " + ema14);
        // ema 50
        analyzer.isEmaRising(rollingSeries, 50, 3, 0.1d);
        double ema50 = analyzer.determineEMAStrength(rollingSeries, 50, 2);
        System.out.println("EMA 50: " + ema50);
        // long time trend
        boolean b = analyzer.isSMALongTimeBullish(rollingSeries, 3, 80, 2);
        System.out.println("Long time bullish " + b);
        // 
        b = analyzer.isPriceAboveSMA200and314(rollingSeries);
        System.out.println("Price over SMA200 & 314 " + b);
        //
        int rsi = analyzer.calculateRSI(rollingSeries, 2);
        System.out.println("RSI : " + rsi);
        double sto = analyzer.calculateSTO(rollingSeries, 4);
        System.out.println("STO : " + sto);
//        double sma14 = analyzer.determineMAStrength(rollingSeries, 14, 2);
//        System.out.println("SMA : " + sma14);
        // notificate the traders
        if (analyzer.isSmaRising(rollingSeries, 8, 2, 0.1d)) {
            traders[currencyIndex.get(Currency.BTC)].setActive(true);
        } else {
            traders[currencyIndex.get(Currency.BTC)].setActive(false);
        }
        // btc price falling strict, deactivate all traders
        if (analyzer.isClosedPriceFallingStrict(series, 2)) {
            for (int i = 0; i < traders.length; i++) {
                traders[i].setActive(false);
            }
        }
        counter++;
    }

    private void watchAllMarkets() {

    }

    public int getNumberOfTraders() {
        return traders.length;
    }

    public int getNumberOfActiveTraders() {
        int result = 0;
        for (int i = 0; i < traders.length; i++) {
            if (traders[i].isActive()) {
                result++;
            };
        }
        return result;
    }

    public List<String> getActiveTraders() {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < traders.length; i++) {
            if (traders[i].isActive()) {
                result.add(traders[i].getInfo());
            };
        }
        return result;
    }

    /**
     * set up the array of trading service
     */
    private void initTraders() {
        System.out.println(traders.length);
        int index2Remove = 0;
        for (int i = 0; i < traders.length; i++) {
            SingleCoinTradingService singleCoinTrader = traders[i];
            if (singleCoinTrader.getCurrency() == null) {
                index2Remove = i;
            }

        }
        traders = ArrayUtils.remove(traders, index2Remove);

        // correct list of traders, register listener and init map
        for (int i = 0; i < traders.length; i++) {
            SingleCoinTradingService singleCoinTrader = traders[i];
            System.out.println(singleCoinTrader.getInfo());
            traders[i].registerListener(this);
            // saving the indexes for each currency in this map
            currencyIndex.put(singleCoinTrader.getCurrency(), i);
        }
    }

    private void notifyTraders() {
        for (int i = 0; i < traders.length; i++) {
            SingleCoinTradingService singleCoinTrader = traders[i];
            if (!singleCoinTrader.isEntered()) {
                singleCoinTrader.setActive(singleCoinTrader.isActive() && !entered);
            }
        }
    }

    @Override
    public void tradeExecuted(Order.OrderType orderType) {
        if (orderType.equals(orderType.BUY)) {
            System.out.println("Notified : Trade ongoing ..");
            entered = true;
        } else {
            System.out.println("Notified : Trade completed ..");
            entered = false;
        }
    }

    private void log(TimeSeries s) {
        Bar b = s.getBar(0);
        System.out.println(b.getBeginTime() + " " + b.getClosePrice());
    }

    public boolean isEntered() {
        return entered;
    }

}
