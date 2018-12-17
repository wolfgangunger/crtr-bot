/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

/**
 *
 * @author UNGERW
 */
@Component
public class TimerService {

    private TimeSeries series;
    private int counter = 0;

    @Autowired
    private Analyzer analyzer;

    // delay in ms
    @Scheduled(fixedDelay = 100)
    public void scheduleFixedDelayTask() {
        if (series == null) {
            return;
        }
        int beginIndex = series.getBeginIndex();
        int endIndex = series.getEndIndex();
        int medium = endIndex / 2;
        int first = beginIndex + counter;
        int last = medium + counter;
        List<Bar> bars = createBarList(first, last);
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

        counter++;
    }

    private void log(TimeSeries s) {
        Bar b = s.getBar(0);
        System.out.println(b.getBeginTime() + " " + b.getClosePrice());
    }

    private List<Bar> createBarList(int start, int end) {
        List<Bar> result = new ArrayList();
        for (int i = start; i < end; i++) {
            result.add(series.getBar(i));
        }
        return result;
    }

//    @Scheduled(fixedRate = 1000)
//    public void scheduleFixedRateTask() {
//        System.out.println(
//                "Fixed rate task - " + System.currentTimeMillis() / 1000);
//    }
    public TimeSeries getSeries() {
        return series;
    }

    public void setSeries(TimeSeries series) {
        this.series = series;
    }

}
