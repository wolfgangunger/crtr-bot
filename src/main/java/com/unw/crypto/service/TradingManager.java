/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import com.unw.crypto.loader.TimeSeriesDBLoader;
import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.ta4j.core.TimeSeries;

/**
 *  main service class coordinating the trades
 *
 * @author UNGERW
 */
@Component
public class TradingManager {

    @Autowired
    private TimerService timerService;
    @Autowired
    private TimeSeriesDBLoader timeSeriesDBLoader;

    private TimeSeries series;



    public void execute() {
        System.out.println("execute...");
        LocalDate from = LocalDate.now().minusMonths(8);
        LocalDate until = LocalDate.now().minusMonths(6);
        series = timeSeriesDBLoader.loadDataWithParams(from, until, Currency.BTC, Exchange.COINBASE, 60);
        timerService.setSeries(series);
    }

}
