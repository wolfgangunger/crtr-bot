/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import com.unw.crypto.SpringConfig;
import com.unw.crypto.chart.BarUtil;
import com.unw.crypto.loader.TimeSeriesDBLoader;
import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

/**
 * main service class coordinating the trades
 *
 * @author UNGERW
 */
//@Component
public class TradingManager {

    private int counter = 0;

    @Autowired
    private Analyzer analyzer;
    @Autowired
    private TimeSeriesDBLoader timeSeriesDBLoader;

//    @Autowired
//    private List<SingleCoinTrader> traders;
    @Autowired
    private SingleCoinTradingService[] traders;

    private Map<Currency, Integer> currencyIndex = new HashMap<>();

    @Autowired
    private SpringConfig springConfig;

    private TimeSeries series;

    @PostConstruct
    public void init() {
        initTraders();
        if (series == null) {
            LocalDate from = LocalDate.now().minusMonths(8);
            LocalDate until = LocalDate.now().minusMonths(6);
            series = timeSeriesDBLoader.loadSeriesWithParams(from, until, Currency.BTC, Exchange.COINBASE, 60);
        }
    }

    public void minuteExecution() {
        watchBTCMarket();
        watchAllMarkets();
        tradeAllMarkets();
    }

    private void tradeAllMarkets() {
        for (int i = 0; i < traders.length; i++) {
            SingleCoinTradingService sct = traders[i];
            sct.trade();
        }
    }

    private void watchBTCMarket() {
        int beginIndex = series.getBeginIndex();
        int endIndex = series.getEndIndex();
        int medium = endIndex / 2;
        int first = beginIndex + counter;
        int last = medium + counter;
        List<Bar> bars = BarUtil.createBarList(first, last, series);
        TimeSeries rollingSeries = new BaseTimeSeries("bitstamp_trades", bars);
        log(rollingSeries);
        analyzer.isClosedPriceRising(rollingSeries, 2);
        // sma 8
        analyzer.isSmaRising(rollingSeries, 8, 2);
        // sma 50
        analyzer.isSmaRising(rollingSeries, 50, 2);
        // sma 200
        analyzer.isSmaRising(rollingSeries, 200, 2);
        // ema 14
        analyzer.isEmaRising(rollingSeries, 14, 2);
        // ema 50
        analyzer.isEmaRising(rollingSeries, 50, 3);
        // long time trend
        analyzer.isSMALongTimeBullish(rollingSeries, 3, 80, 2);

        // notificate the traders
        if (analyzer.isSmaRising(rollingSeries, 8, 2)) {
            traders[currencyIndex.get(Currency.BTC)].setActive(true);
        } else {
            traders[currencyIndex.get(Currency.BTC)].setActive(false);
        }
        counter++;
    }

    private void watchAllMarkets() {

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
            // saving the indexes for each currency in this map
            currencyIndex.put(singleCoinTrader.getCurrency(), i);
        }
        traders = ArrayUtils.remove(traders, index2Remove);

        for (int i = 0; i < traders.length; i++) {
            SingleCoinTradingService singleCoinTrader = traders[i];
            singleCoinTrader.foo();
        }
    }

    private void log(TimeSeries s) {
        Bar b = s.getBar(0);
        System.out.println(b.getBeginTime() + " " + b.getClosePrice());
    }

}
