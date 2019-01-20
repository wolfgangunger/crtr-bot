/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import org.springframework.stereotype.Component;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.IsFallingRule;
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
     * @param strenght
     * @return
     */
    public boolean isClosedPriceRising(TimeSeries s, int durationTimeframe, double strenght) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        Rule rule1 = new IsRisingRule(closePrice, durationTimeframe, strenght);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("Price is rising :" + satisfied);
        return satisfied;
    }

    /**
     *
     * @param s
     * @param durationTimeframe
     * @return
     */
    public boolean isClosedPriceFallingStrict(TimeSeries s, int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        Rule rule1 = new IsFallingRule(closePrice, durationTimeframe);
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

    public double determineMAStrength(TimeSeries s, int sma, int durationTimeframe) {
        double result = 0d;
        System.out.println("determineMAStrength for SMA " + sma);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        result = determineSlopeStrength(smaInd, durationTimeframe, s.getEndIndex());
        System.out.println("strenght : " + result);
        System.out.println("end determineMAStrength ");
        return result;
    }

    public double determineClosedPriceStrength(TimeSeries s, int durationTimeframe) {
        double result = 0d;
        System.out.println("determineClosedPriceStrength ");
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        Rule rule1 = new IsRisingRule(closePrice, durationTimeframe);
        boolean rising = rule1.isSatisfied(s.getEndIndex());
        System.out.println("Price is rising :" + rising);
        Rule rule2 = new IsFallingRule(closePrice, durationTimeframe);
        boolean falling = rule2.isSatisfied(s.getEndIndex());
        System.out.println("Price is falling :" + falling);

        System.out.println("determine  strenght");
        result = determineSlopeStrength(closePrice, durationTimeframe, s.getEndIndex());
        System.out.println("strenght : " + result);
        System.out.println("end determineClosedPriceStrength ");
        return result;
    }

    private double determineSlopeStrength(Indicator<Num> ind, int durationTimeframe, int endIndex) {
        Rule rule = new IsRisingRule(ind, durationTimeframe, 0.1d);
        boolean rising = rule.isSatisfied(endIndex);
        System.out.println("determine slope  strenght, rising = " + rising);
        Rule rule1;
        for (double d = 0; d <= 1; d += 0.1d) {
            if (rising) {
                rule1 = new IsRisingRule(ind, durationTimeframe, d);
            } else {
                rule1 = new IsFallingRule(ind, durationTimeframe, d);
            }
            boolean satisfied = rule1.isSatisfied(endIndex);
            //System.out.println("Strenth : " + d + " rising/falling : " + satisfied);
            if (!satisfied) {
                return rising ? d - 0.1d : -(d - 0.1d);
            }
        }
        return rising ? 1.0d : -1.0d;
    }

}
