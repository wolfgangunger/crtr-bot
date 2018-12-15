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

/**
 *
 * @author UNGERW
 */
@Component
public class Analyzer {

    /**
     * 
     * @param s
     * @param durationTimeframe 
     */
    public void analyseClosedPrice(TimeSeries s,  int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        Rule rule1 = new IsRisingRule(closePrice, durationTimeframe);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("Price is rising :" + satisfied);
    }

    /**
     *
     * @param s
     * @param durationTimeframe 
     * @param sma (sma8, sma14, sma200 , pass the value you want )
     */
    public void analyseSMA(TimeSeries s, int sma, int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        Rule rule1 = new IsRisingRule(smaInd, durationTimeframe);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("SMA " + sma + "  is rising :" + satisfied);
    }
    
    /**
     * 
     * @param s
     * @param ema
     * @param durationTimeframe 
     */
    public void analyseEMA(TimeSeries s, int ema, int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        EMAIndicator emaInd = new EMAIndicator(closePrice, ema);
        Rule rule1 = new IsRisingRule(emaInd, durationTimeframe);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("EMA " + ema + "  is rising :" + satisfied);
    }

}
