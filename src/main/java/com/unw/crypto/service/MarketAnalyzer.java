/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import org.springframework.stereotype.Component;
import org.ta4j.core.Rule;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.IsRisingRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;

/**
 *
 * @author UNGERW
 */
@Component
public class MarketAnalyzer {

    /**
     *
     * @param s
     * @param durationTimeframe
     * @return
     */
    public boolean isClosedPriceRising(TimeSeries s, int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        Rule rule1 = new IsRisingRule(closePrice, durationTimeframe);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("Price is rising :" + satisfied);
        return satisfied;
    }

    /**
     *
     * @param s
     * @param durationTimeframe
     * @param sma (sma8, sma14, sma200 , pass the value you want )
     * @return
     */
    public boolean isSmaRising(TimeSeries s, int sma, int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        Rule rule1 = new IsRisingRule(smaInd, durationTimeframe);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("SMA " + sma + "  is rising :" + satisfied);
        return satisfied;
    }

    /**
     *
     * @param s
     * @param ema
     * @param durationTimeframe
     * @return
     */
    public boolean isEmaRising(TimeSeries s, int ema, int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        EMAIndicator emaInd = new EMAIndicator(closePrice, ema);
        Rule rule1 = new IsRisingRule(emaInd, durationTimeframe);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("EMA " + ema + "  is rising :" + satisfied);
        return satisfied;
    }

    /**
     * 
     * @param s TimeSeries
     * @param smaShort ( use 5 for example)
     * @param smaLong ( use 200 for example)
     * @param durationTimeframe
     * @return 
     */
    public boolean isSMALongTimeBullish(TimeSeries s, int smaShort, int smaLong, int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        SMAIndicator shortSma = new SMAIndicator(closePrice, smaShort);
        SMAIndicator longSma = new SMAIndicator(closePrice, smaLong);
        Rule rule1 = new OverIndicatorRule(shortSma, longSma);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("isSMALongTimeBullish " + satisfied);
        return satisfied;
    }

}
