/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.model.Tick;
import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseBar;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.num.DoubleNum;

/**
 *
 * @author UNGERW
 */
public final class BarUtil {

    private BarUtil() {
    }

    /**
     *
     * @param beginTime
     * @param endTime
     * @param ticks
     * @param barDurationInMinutes
     * @return
     */
    public static List<Bar> buildBars(ZonedDateTime beginTime, ZonedDateTime endTime, List<Tick> ticks, int barDurationInMinutes) {
        List<Bar> bars = new ArrayList<>();
        Duration barDuration = Duration.ofSeconds(barDurationInMinutes * 60);
        ZonedDateTime barEndTime = beginTime;
        int i = 0;
        // line number of trade data
        do {
            // build a bar
            barEndTime = barEndTime.plus(barDuration);
            // TODO ; null ok ?
            Bar bar = new BaseBar(barDuration, barEndTime, DoubleNum::valueOf);
            do {
                // get a trade
                Tick t = ticks.get(i);
                ZonedDateTime tradeTimeStamp = ZonedDateTime.ofInstant(Instant.ofEpochMilli(t.getTradeTime().getTime()), ZoneId.systemDefault());
                // if the trade happened during the bar
                if (bar.inPeriod(tradeTimeStamp)) {
                    // add the trade to the bar
                    double tradePrice = t.getPrice();
                    double tradeAmount = t.getAmount();
                    bar.addTrade(DoubleNum.valueOf(tradeAmount), DoubleNum.valueOf(tradePrice));
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

    public static Bar createNewBar(Tick tick) {
        ZonedDateTime dateTime = ZonedDateTime.ofInstant(Instant.ofEpochMilli(tick.getTradeTime().getTime()), ZoneId.systemDefault());
        return new BaseBar(dateTime, tick.getPrice(), tick.getPrice(), tick.getPrice(), tick.getPrice(),
                tick.getAmount(), DoubleNum::valueOf);
    }

    public static List<Bar> createBarList(int start, int end, TimeSeries series) {
        List<Bar> result = new ArrayList();
        for (int i = start; i < end; i++) {
            result.add(series.getBar(i));
        }
        return result;
    }

    public static float getMAMultiplicator(BarDuration barDuration) {
        switch (barDuration) {
            case TWO_HOURS:
                return 0.5f;
            case SIXTY_MIN:
                return 1;
            case THIRTY_MIN:
                return 2;
            case TWENTY_MIN:
                return 3;
            case TEN_MIN:
                return 6;
            case FIVE_MIN:
                return 12;
            case TWO_MIN:
                return 30;
            case ONE_MIN:
                return 60;
            default:
                return 12;
        }
    }

}
