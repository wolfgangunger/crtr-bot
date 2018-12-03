/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.loader;

import com.unw.crypto.db.TickRepository;
import com.unw.crypto.model.Tick;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.TimeSeries;

/**
 *
 * @author UNGERW
 */
public class DBLoader {

    @Autowired
    private static TickRepository tickRepository;

    public static TimeSeries loadBitstampSeries(String filename, Instant from, Instant until) {
        List<Tick> ticks = tickRepository.findAll();
        ZonedDateTime beginTime = null;
        ZonedDateTime endTime = null;

        List<Bar> bars = null;
        if ((ticks != null) && !ticks.isEmpty()) {

            // Getting the first and last trades timestamps
            beginTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(0).getTradeTime().getTime() * 1000), ZoneId.systemDefault());
            endTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(ticks.size() - 1).getTradeTime().getTime() * 1000), ZoneId.systemDefault());
            if (beginTime.isAfter(endTime)) {
                Instant beginInstant = beginTime.toInstant();
                Instant endInstant = endTime.toInstant();
                beginTime = ZonedDateTime.ofInstant(endInstant, ZoneId.systemDefault());
                endTime = ZonedDateTime.ofInstant(beginInstant, ZoneId.systemDefault());
                // Since the CSV file has the most recent trades at the top of the file, we'll reverse the list to feed the List<Bar> correctly.
                // Collections.reverse(ticks);
            }
            // build the list of populated bars

            bars = buildBars(beginTime, endTime, 300, ticks);
        }

        return new BaseTimeSeries("bitstamp_trades", bars);
    }

    private static List<Bar> buildBars(ZonedDateTime beginTime, ZonedDateTime endTime, int duration, List<Tick> ticks) {

        List<Bar> bars = new ArrayList<>();

        Duration barDuration = Duration.ofSeconds(duration);
        ZonedDateTime barEndTime = beginTime;
        // line number of trade data
        do {
            // build a bar
            barEndTime = barEndTime.plus(barDuration);
            Bar bar = new BaseBar(barDuration, barEndTime);
            for (Tick t : ticks) {
              // get a trade
                ZonedDateTime tradeTimeStamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(t.getTradeTime().getTime() * 1000), ZoneId.systemDefault());
                // if the trade happened during the bar
                if (bar.inPeriod(tradeTimeStamp)) {
                    // add the trade to the bar
                    double tradePrice = t.getPrice();
                    double tradeAmount = t.getAmount();
                    bar.addTrade(tradeAmount, tradePrice);
                } else {
                    // the trade happened after the end of the bar
                    // go to the next bar but stay with the same trade (don't increment i)
                    // this break will drop us after the inner "while", skipping the increment
                    break;
                }
            }
            if (bar.getTrades() > 0) {
                bars.add(bar);
            }
        } while (barEndTime.isBefore(endTime));
        return bars;
    }

}
