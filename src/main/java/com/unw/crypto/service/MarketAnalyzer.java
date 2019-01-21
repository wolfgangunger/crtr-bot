/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import com.unw.crypto.model.AddOrderInfo;
import org.springframework.stereotype.Component;
import org.ta4j.core.Indicator;
import org.ta4j.core.Rule;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.IsFallingRule;
import org.ta4j.core.trading.rules.IsRisingRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;

/**
 * helper class for raw indicator
 * @author UNGERW
 */
@Component
public class MarketAnalyzer {

    /**
     * determine if the price is rising
     * @param  s TimeSeries
     * @param durationTimeframe
     * @param strenght rise or fall strengh(0.5 and 1.0 - no other float values working yet)
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
     * determine if the price is falling with strength 1
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
     * determine if the Simple MA is rising
     * @param s TimeSeries
     * @param sma (sma8, sma14, sma200 , pass the value you want )
     * @param durationTimeframe
     * @param strenght  rise or fall strengh(0.5 and 1.0 - no other float values working yet)
     * @return
     */
    public boolean isSmaRising(TimeSeries s, int sma, int durationTimeframe, double strenght) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        Rule rule1 = new IsRisingRule(smaInd, durationTimeframe, strenght);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("SMA " + sma + "  is rising :" + satisfied);
        return satisfied;
    }

    /**
     * determine if the Expotential MA is rising
     * @param s TimeSeries
     * @param ema (ema8, ema14, ema200 , pass the value you want )
     * @param durationTimeframe
     * @param strength rise or fall strengh(0.5 and 1.0 - no other float values working yet)
     * @return
     */
    public boolean isEmaRising(TimeSeries s, int ema, int durationTimeframe, double strength) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        EMAIndicator emaInd = new EMAIndicator(closePrice, ema);
        Rule rule1 = new IsRisingRule(emaInd, durationTimeframe, strength);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        System.out.println("EMA " + ema + "  is rising :" + satisfied);
        return satisfied;
    }

    /**
     * determine if the Short MA is over the long MA (bullish signal)
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
        return satisfied;
    }

    /**
     * determine if the closed price is above SMA200 and SMA314
     * @param series
     * @return
     */
    public boolean isPriceAboveSMA200and314(TimeSeries series) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator sma200 = new SMAIndicator(closePrice, 200);
        SMAIndicator sma314 = new SMAIndicator(closePrice, 314);
        Rule rule = new OverIndicatorRule(closePrice, sma200).and(new OverIndicatorRule(closePrice, sma314));
        return rule.isSatisfied(series.getEndIndex());
    }

    /**
     * determine if the closed price is above SMA
     * @param series
     * @param sma (the MA, for example 200 or 314)
     * @return
     */
    public boolean isPriceAboveSMA(TimeSeries series, int sma) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        Rule rule = new OverIndicatorRule(closePrice, smaInd);
        return rule.isSatisfied(series.getEndIndex());
    }

    /**
     * determine the rise or fall strenth (- is falling, + is rising)
     * @param s TimeSeries
     * @param sma
     * @param durationTimeframe
     * @return double strenght ( from -1 to +1)
     */
    public double determineSMAStrength(TimeSeries s, int sma, int durationTimeframe) {
        double result = 0d;
        //System.out.println("determineMAStrength for SMA " + sma);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        result = determineSlopeStrength(smaInd, durationTimeframe, s.getEndIndex());
        //System.out.println("strenght : " + result);
        //System.out.println("end determineMAStrength ");
        return result;
    }

    /**
     * determine the rise or fall strenth (- is falling, + is rising)
     * @param s TimeSeries
     * @param ema
     * @param durationTimeframe
     * @return double strenght ( from -1 to +1)
     */
    public double determineEMAStrength(TimeSeries s, int ema, int durationTimeframe) {
        double result = 0d;
        //System.out.println("determineMAStrength for SMA " + ema);
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        EMAIndicator emaInd = new EMAIndicator(closePrice, ema);
        result = determineSlopeStrength(emaInd, durationTimeframe, s.getEndIndex());
        //System.out.println("strenght : " + result);
        //System.out.println("end determineMAStrength ");
        return result;
    }

    /**
     * determine the rise or fall strenth (- is falling, + is rising)
     * @param s
     * @param durationTimeframe
     * @return double strenght ( from -1 to +1)
     */
    public double determineClosedPriceStrength(TimeSeries s, int durationTimeframe) {
        double result = 0d;
        //System.out.println("determineClosedPriceStrength ");
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        //System.out.println("determine  strenght");
        result = determineSlopeStrength(closePrice, durationTimeframe, s.getEndIndex());
        //System.out.println("strenght : " + result);
        //System.out.println("end determineClosedPriceStrength ");
        return result;
    }

    private double determineSlopeStrength(Indicator<Num> ind, int durationTimeframe, int endIndex) {
        Rule rule = new IsRisingRule(ind, durationTimeframe, 0.1d);
        boolean rising = rule.isSatisfied(endIndex);
        //System.out.println("determine slope  strenght, rising = " + rising);
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

    /**
     * calculate RSI (0-100)
     * @param s TimeSeries
     * @param timeFrame
     * @return 
     */
    public int calculateRSI(TimeSeries s, int timeFrame) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        RSIIndicator rsiIndicator = new RSIIndicator(closePrice, timeFrame);
        Num result = rsiIndicator.getValue(s.getEndIndex());
        return result.intValue();
    }

    /**
     * calculate STO (0.0 -1-0)
     * @param s
     * @param timeFrame
     * @return 
     */
    public double calculateSTO(TimeSeries s, int timeFrame) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        StochasticRSIIndicator stochasticRSIIndicator = new StochasticRSIIndicator(closePrice, timeFrame);
        Num result = stochasticRSIIndicator.getValue(s.getEndIndex());
        return result.doubleValue();
    }

    /**
     * create TO AddOrderInfo with current buy/sell params 
     * @param s TimeSeries
     * @param rsiTimeframe
     * @param stoTimeframe
     * @return AddOrderInfo
     */
    public AddOrderInfo analyzeOrderParams(TimeSeries s, int rsiTimeframe, int stoTimeframe) {
        int rsi = calculateRSI(s, rsiTimeframe);
        double sto = calculateSTO(s, stoTimeframe);
        double closedPriceStrenth = determineClosedPriceStrength(s, 2);
        double sma3 = determineSMAStrength(s, 3, 2);
        double sma8 = determineSMAStrength(s, 8, 2);
        double sma50 = determineSMAStrength(s, 50, 2);
        double sma200 = determineSMAStrength(s, 200, 2);
        double sma314 = determineSMAStrength(s, 314, 2);
        double ema14 = determineEMAStrength(s, 14, 2);
        double ema50 = determineEMAStrength(s, 50, 2);
        boolean priceAboveSma200 = isPriceAboveSMA(s, 200);
        boolean priceAboveSma3141 = isPriceAboveSMA(s, 314);        
        boolean isSMALongTimeBullish = isSMALongTimeBullish(s, 3, 80, 2);
        
        return AddOrderInfo.builder().rsi(rsi).sto(sto).closedPriceStrenth(closedPriceStrenth).sma3(sma3).sma8(sma8).sma50(sma50).
                sma200(sma200).sma314(sma314).ema14(ema14).ema50(ema50).priceAboveSma200(priceAboveSma200).
                priceAboveSma3141(priceAboveSma3141).isSMALongTimeBullish(isSMALongTimeBullish).build();

    }
}
