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
     * load the very first entry in the db for the given params (used in bottom bar as info)
     * @param currency
     * @param exchange
     * @return Tick
     */
    public Tick loadFirstEntry(Currency currency, Exchange exchange) {
        return tickRepository.findTopByCurrencyAndExchangeOrderByTradeTimeAsc(currency.getStringValue(), exchange.getStringValue());
    }

    /**
     * load the very last entry in the db for the given params (used in bottom bar as info)
     * @param currency
     * @param exchange
     * @return  Tick
     */
    public Tick loadLastEntry(Currency currency, Exchange exchange) {
        return tickRepository.findTopByCurrencyAndExchangeOrderByTradeTimeDesc(currency.getStringValue(), exchange.getStringValue());
    }

    /**
     * load ticks by defined params
     * @param from
     * @param until
     * @param currency
     * @param exchange
     * @param barDurationInMinutes
     * @return List 
     */
    public List<Tick> loadTicksWithParams(LocalDate from, LocalDate until, Currency currency, Exchange exchange, int barDurationInMinutes) {
        Date df = Date.from(from.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date du = Date.from(until.atStartOfDay(ZoneId.systemDefault()).toInstant());
        List<Tick> ticks = tickRepository.findByExchangeAndCurrencyAndDates(exchange.getStringValue(), currency.getStringValue(), df, du);
        ZonedDateTime beginTime;
        ZonedDateTime endTime;

        if ((ticks != null) && !ticks.isEmpty()) {
            // Getting the first and last trades timestamps
            beginTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(0).getTradeTime().getTime()), ZoneId.systemDefault());
            endTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(ticks.size() - 1).getTradeTime().getTime()), ZoneId.systemDefault());
            if (beginTime.isAfter(endTime)) {
                // Since the CSV file has the most recent trades at the top of the file, we'll reverse the list to feed the List<Bar> correctly.
                Collections.reverse(ticks);
            }
        }
        return ticks;
    }

    /**
     * load series by defined parameters
     * @param from
     * @param until
     * @param currency
     * @param exchange
     * @param barDurationInMinutes
     * @return 
     */
    public TimeSeries loadSeriesWithParams(LocalDate from, LocalDate until, Currency currency, Exchange exchange, int barDurationInMinutes) {
        List<Tick> ticks = loadTicksWithParams(from, until, currency, exchange, barDurationInMinutes);

        ZonedDateTime beginTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(0).getTradeTime().getTime()), ZoneId.systemDefault());
        ZonedDateTime endTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(ticks.size() - 1).getTradeTime().getTime()), ZoneId.systemDefault());

        List<Bar> bars = bars = buildBars(beginTime, endTime, ticks, barDurationInMinutes);
        return new BaseTimeSeries("bitstamp_trades", bars);
    }

    /**
     * build series by existing ticks list
     * @param ticks
     * @param barDurationInMinutes
     * @return 
     */
    public TimeSeries loadSeriesByTicks(List<Tick> ticks,  int barDurationInMinutes) {
        ZonedDateTime beginTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(0).getTradeTime().getTime()), ZoneId.systemDefault());
        ZonedDateTime endTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(ticks.get(ticks.size() - 1).getTradeTime().getTime()), ZoneId.systemDefault());
        List<Bar> bars = bars = buildBars(beginTime, endTime, ticks, barDurationInMinutes);
        return new BaseTimeSeries("bitstamp_trades", bars);
    }

    /**
     * 
     * @param from
     * @param until
     * @param currency
     * @param exchange
     * @param barDurationInMinutes
     * @deprecated use #loadSeriesWithParams
     * @return 
     */
    public TimeSeries loadSeriesWithParamsOld(LocalDate from, LocalDate until, Currency currency, Exchange exchange, int barDurationInMinutes) {
        //Sort sort = new Sort(Sort.Direction.ASC, "tradeTime");
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
            bars = buildBars(beginTime, endTime, ticks, barDurationInMinutes);
        }
        return new BaseTimeSeries("bitstamp_trades", bars);
    }

    /**
     *
     * @param beginTime
     * @param endTime
     * @param duration
     * @param ticks
     * @return
     */
    private List<Bar> buildBars(ZonedDateTime beginTime, ZonedDateTime endTime, List<Tick> ticks, int barDurationInMinutes) {
        List<Bar> bars = new ArrayList<>();
        Duration barDuration = Duration.ofSeconds(barDurationInMinutes * 60);
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
                ZonedDateTime tradeTimeStamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(t.getTradeTime().getTime()), ZoneId.systemDefault());
                // if the trade happened during the bar
                if (bar.inPeriod(tradeTimeStamp)) {
                    // add the trade to the bar
                    double tradePrice = t.getPrice();
                    double tradeAmount = t.getAmount();
                    bar.addTrade(tradeAmount, tradePrice);
                } else {
                    // should not happen - order problem ?
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
