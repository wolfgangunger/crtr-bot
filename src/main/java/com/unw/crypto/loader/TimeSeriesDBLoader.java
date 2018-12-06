/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.loader;

import com.unw.crypto.Config;
import com.unw.crypto.db.TickRepository;
import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import com.unw.crypto.model.Tick;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

/**
 *
 * @author UNGERW
 */
@Component
public class TimeSeriesDBLoader {

    @Autowired
    private TickRepository tickRepository;

    /**
     * @deprecated @return
     */
    public TimeSeries loadData() {
        LocalDate df = LocalDate.parse(Config.fromDate);
        LocalDate du = LocalDate.parse(Config.untilDate);
        // Instant instantFrom = df.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
        //Instant instantUntil = du.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
        TimeSeries series = loadBitstampSeries(df, du, Currency.BTC, Exchange.COINBASE);
        return series;
    }

    public TimeSeries loadDataWithParams(LocalDate from, LocalDate until, Currency currency, Exchange exchange) {
        //Instant instantFrom = from.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
        //Instant instantUntil = until.atStartOfDay(ZoneId.of("Europe/Berlin")).toInstant();
        TimeSeries series = loadBitstampSeries(from, until, currency, exchange);
        return series;
    }

    public TimeSeries loadBitstampSeries(LocalDate from, LocalDate until, Currency currency, Exchange exchange) {
        Sort sort = new Sort(Sort.Direction.ASC, "tradeTime");
        //List<Tick> ticks = tickRepository.findAll(sort);
        //List<Tick> ticks = tickRepository.findAll();
        Date df = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date du = Date.from(until.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Tick> ticks = tickRepository.findByExchangeAndCurrencyAndDates(exchange.getStringValue(), currency.getStringValue(), df, du);
        ZonedDateTime beginTime = null;
        ZonedDateTime endTime = null;

        List<Bar> bars = new ArrayList<>();
        if ((ticks != null) && !ticks.isEmpty()) {

            // Getting the first and last trades timestamps
            beginTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(0).getTradeTime().getTime()), ZoneId.systemDefault());
            endTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(ticks.size() - 1).getTradeTime().getTime()), ZoneId.systemDefault());
            if (beginTime.isAfter(endTime)) {
                // Since the CSV file has the most recent trades at the top of the file, we'll reverse the list to feed the List<Bar> correctly.
                Instant beginInstant = beginTime.toInstant();
                Instant endInstant = endTime.toInstant();
                beginTime = ZonedDateTime.ofInstant(endInstant, ZoneId.systemDefault());
                endTime = ZonedDateTime.ofInstant(beginInstant, ZoneId.systemDefault());
                Collections.reverse(ticks);
            }
            // build the list of populated bars
            //duration in seconds ( 300 = 5 min)
            bars = buildBars(beginTime, endTime, 300, ticks);
        }
        return new BaseTimeSeries("bitstamp_trades", bars);
    }

    /**
     *
     * @param beginTime
     * @param endTime
     * @param duration in seconds ( 300 = 5 min)
     * @param ticks
     * @return
     */
    private List<Bar> buildBars(ZonedDateTime beginTime, ZonedDateTime endTime, int duration, List<Tick> ticks) {
        List<Bar> bars = new ArrayList<>();
//        for (Tick t : ticks) {
//            System.out.println("###");
//            System.out.println(t.getTradeTime());
//        }
//        if (true) {
//            return null;
//        }
        Duration barDuration = Duration.ofSeconds(duration);
        ZonedDateTime barEndTime = beginTime;
        int i = 0;
        // line number of trade data
        do {
            // build a bar
            barEndTime = barEndTime.plus(barDuration);
            Bar bar = new BaseBar(barDuration, barEndTime);
            do {
                // get a trade
                Tick t = ticks.get(i);
                // ZonedDateTime tradeTimeStamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(t.getTradeTime().getTime() * 1000), ZoneId.systemDefault());
                ZonedDateTime tradeTimeStamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(t.getTradeTime().getTime()), ZoneId.systemDefault());
                // if the trade happened during the bar
                if (bar.inPeriod(tradeTimeStamp)) {
                    // add the trade to the bar
                    double tradePrice = t.getPrice();
                    double tradeAmount = t.getAmount();
                    bar.addTrade(tradeAmount, tradePrice);
//                    System.out.println("### in period");
//                    System.out.println(bar.getBeginTime());
//                    System.out.println(bar.getEndTime());
//                    System.out.println(tradeTimeStamp);
                } else {
                    // should not happen - order problem ?
                    System.out.println("### out of period");
                    System.out.println(bar.getBeginTime());
                    System.out.println(bar.getEndTime());
                    System.out.println(tradeTimeStamp);
                    // the trade happened after the end of the bar
                    // go to the next bar but stay with the same trade (don't increment i)
                    // this break will drop us after the inner "while", skipping the increment
                    break;
                }
                i++;
            } while (i < ticks.size());
            if (bar.getTrades() > 0) {
                bars.add(bar);
            }
        } while (barEndTime.isBefore(endTime));
        return bars;
    }

}
