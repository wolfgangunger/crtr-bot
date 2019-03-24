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
import org.ta4j.core.indicators.CCIIndicator;
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
 *
 * @author UNGERW
 */
@Component
public class MarketAnalyzer {



    /**
     * determine if the price is rising
     *
     * @param s TimeSeries
     * @param durationTimeframe
     * @param strenght rise or fall strengh(0.5 and 1.0 - no other float values
     * working yet)
     * @return
     */
    public boolean isClosedPriceRising(TimeSeries s, int durationTimeframe, double strenght) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        Rule rule1 = new IsRisingRule(closePrice, durationTimeframe, strenght);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        return satisfied;
    }

    /**
     * determine if the price is falling with strength 1
     *
     * @param s
     * @param durationTimeframe
     * @return
     */
    public boolean isClosedPriceFallingStrict(TimeSeries s, int durationTimeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        Rule rule1 = new IsFallingRule(closePrice, durationTimeframe);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        return satisfied;
    }

    /**
     * check if the price is falling strict <br>
     * this means: falling more from the last to the pre-last bar as the given
     * percent value
     *
     * @param s timeSeries
     * @param percent
     * @return true if falling strict
     */
    public boolean isFallingStrict(TimeSeries s, double percent) {
        double lastPrice = s.getLastBar().getClosePrice().doubleValue();
        double preLastPrice = s.getBar(s.getEndIndex() - 1).getClosePrice().doubleValue();
        if (lastPrice > preLastPrice) {
            return false;
        }
        double d = (1d - lastPrice / preLastPrice) * 100;
        return d >= percent;
    }

    /**
     * determine if the Simple MA is rising - simple method with no additional
     * logic
     *
     * @param s TimeSeries
     * @param sma (sma8, sma14, sma200 , pass the value you want )
     * @param durationTimeframe
     * @param strenght rise or fall strengh(0.5 and 1.0 - no other float values
     * working yet)
     * @return
     */
    public boolean isSmaRising(TimeSeries s, int sma, int durationTimeframe, double strenght) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        Rule rule1 = new IsRisingRule(smaInd, durationTimeframe, strenght);
        boolean satisfied = rule1.isSatisfied(s.getEndIndex());
        return satisfied;
    }

    /**
     * determine if the Expotential MA is rising - simple method with no
     * additional logic
     *
     * @param s TimeSeries
     * @param ema (ema8, ema14, ema200 , pass the value you want )
     * @param durationTimeframe
     * @param strength rise or fall strengh(0.5 and 1.0 - no other float values
     * working yet)
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
        return satisfied;
    }

    /**
     * determine if the closed price is above SMA200 and SMA314
     *
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
     *
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
     * calculate a Strenght value (-1 to 1)for the longterm CCI (50,100,200)
     *
     * @param series
     * @return
     */
    public double getMarketCCIStrenght(TimeSeries series) {
        double cci50 = calculateCCIStrenght(determineCCI(series, 50));
        double cci100 = calculateCCIStrenght(determineCCI(series, 100));
        double cci200 = calculateCCIStrenght(determineCCI(series, 200));
        return cci50 + cci100 + cci200;
    }

    private double calculateCCIStrenght(int cci) {
        // TODO this logic must be enhanced
        if (cci > 0) {
            return 0.3;
        } else {
            return -0.3;
        }
    }

  
    /**
     * determine the SMA strenght for the given MA. returns a value between -1
     * and 1 logic to calculate the MA strength is to be enhanced
     *
     * @param series
     * @param sma the lenght of the MA for example 14 or 50
     * @return double between -1 and 1
     */
    public double getMarketSMAStrength(TimeSeries series, int sma) {
        double result = 0d;
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        // check the last trend + final result must be positiv or negativ dependig on this value
//        boolean rising = isLastBarRising(smaInd);
        // only last bar is no good, check last  bars, for longer MAs max 10
        int lastBars = sma / 5;
//        if (lastBars > 10) {
//            lastBars = 10;
//        }
        boolean rising = isLastTrendRising(smaInd, lastBars);
        int barCount = series.getBarCount();
        // smaIndicator has always one bar less than closePirce
        barCount = barCount - 1;
        // cut down to a even number of bars
        if (barCount % 2 != 0) {
            barCount = barCount - 1;
        }
        double totalStrength = determineSlopeStrength(smaInd, barCount, barCount - 1);
//        System.out.println("total strengh t" + totalStrength);
        if ((rising && totalStrength < 0) || (!rising && totalStrength > 0)) {
            // last direction diametral to allover direction, return a small strenght
            // TODO value must be advanced - not just 0.1
            return rising ? 0.1 : -0.1;

        }
        double mediumStrength = 0d;
        int count = 0;
        for (int i = barCount; i > 1; i = i / 2) {
            double strength = determineSlopeStrength(smaInd, i, barCount);
            mediumStrength += strength;
            count++;
        }
        mediumStrength = mediumStrength / count;
//        System.out.println("medium strenght " + mediumStrength);
//        double percentDiffTotal = calculateLastToFirstDiff(series, rising);
//        System.out.println("percent all " + percentDiffTotal);
// weighted method will weight second half percentage double
        double percentDiff = calculateWeightedLastToFirstDiff(series, rising);
        double percentDiff2 = calculateLastToFirstDiff(smaInd, sma);
//        System.out.println("percent weighted " + percentDiff);
        System.out.println("percent " + percentDiff2);
        double percentDiffAbs = Math.abs(percentDiff);
        double mediumStrengthAbs = Math.abs(mediumStrength);
        result = calculateMediumFromStrenghtAndPercent(mediumStrengthAbs, percentDiffAbs);
        return rising ? result : -result;
    }

    private void printSMA(Indicator<Num> ind) {
        // just helper method for debugging
        TimeSeries s = ind.getTimeSeries();
        for (int i = 0; i <= s.getEndIndex(); i++) {
            System.out.println(s.getBar(i).getClosePrice().doubleValue());
            System.out.println(ind.getValue(i));
        }
    }

    private double calculateMediumFromStrenghtAndPercent(double mediumStrenght, double percent) {
        // if percent is higher than 100% return 1.0 ( maximum strenght)
        double medium = 0d;
        if (percent > 100) {
            return 1.0d;
        }
        // convert to a value between 0 and 1
        percent = percent / 100;
        // medium value from percent and strenght; percent is double weighted
        medium = (mediumStrenght + (2 * percent)) / 3;
        return medium;
    }

    private double calculateLastToFirstDiff(Indicator<Num> indicator, int barCount) {
        TimeSeries s = indicator.getTimeSeries();
        System.out.println(indicator.getValue(barCount -1 ));
        System.out.println(indicator.getValue(0));

        System.out.println(s.getBar(s.getEndIndex()).getDateName() + " " + s.getBar(s.getEndIndex()).getClosePrice());        
        System.out.println(s.getBar(s.getEndIndex() - barCount).getDateName() + " " + s.getBar(s.getEndIndex() - barCount).getClosePrice());
        return 0d;
    }

    /**
     * calculate the percentual diff between the last and the first bar
     *
     * @param series
     * @param rising
     * @return
     */
    private double calculateLastToFirstDiff(TimeSeries series, boolean rising) {
        double lastPrice = series.getLastBar().getClosePrice().doubleValue();
        double firstPrice = series.getFirstBar().getClosePrice().doubleValue();
//        System.out.println("first price " + firstPrice + " date " + series.getFirstBar().getDateName());
//        System.out.println("last price " + lastPrice + " date " + series.getLastBar().getDateName());
        double diff;
        double percent;
        if (rising) {
            diff = lastPrice - firstPrice;
            percent = diff / firstPrice * 100;
        } else {
            diff = firstPrice - lastPrice;
            percent = diff / lastPrice * 100;
        }
        return rising ? percent : -percent;
    }

    /**
     * see method above, but this method will weight the second half of the
     * series higher than the allover percent
     *
     * @param series
     * @param rising
     * @return
     */
    private double calculateWeightedLastToFirstDiff(TimeSeries series, boolean rising) {
        double lastPrice = series.getLastBar().getClosePrice().doubleValue();
        double firstPrice = series.getFirstBar().getClosePrice().doubleValue();
        int midIndex = series.getEndIndex() / 2;
        double midPrice = series.getBar(midIndex).getClosePrice().doubleValue();
//        System.out.println("first price " + firstPrice + " date " + series.getFirstBar().getDateName());
//        System.out.println("last price " + lastPrice + " date " + series.getLastBar().getDateName());
//        System.out.println("mid price " + midPrice + " date " + series.getBar(midIndex).getDateName());

        double diffComplete;
        double diffMid;
        double percentComplete;
        double percentMid;
        double weightedPercentage;
        if (rising) {
            diffComplete = lastPrice - firstPrice;
            diffMid = lastPrice - midPrice;
            percentComplete = diffComplete / firstPrice * 100;
            percentMid = diffMid / midPrice * 100;
        } else {
            diffComplete = firstPrice - lastPrice;
            diffMid = midPrice - lastPrice;
            percentComplete = diffComplete / lastPrice * 100;
            percentMid = diffMid / midPrice * 100;
        }
        weightedPercentage = calculateMediumPercent(percentComplete, percentMid);
        return rising ? weightedPercentage : -weightedPercentage;
    }

    private double calculateMediumPercent(double percentComplete, double percentMid) {
        // mid percent is half of period, therefore must be doubled 
        percentMid = percentMid * 2;
        // now weight the percentage of the second half of the period with double weight
        return (percentComplete + percentMid) / 3;
    }

    /**
     * determine the rise or fall strenth (- is falling, + is rising)
     *
     * @deprecated use determineSMAStrength(TimeSeries s, int sma)
     * @param s TimeSeries
     * @param sma
     * @param durationTimeframe
     * @return double strenght ( from -1 to +1)
     */
    public double determineSMAStrength(TimeSeries s, int sma, int durationTimeframe) {
        double result = 0d;
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        SMAIndicator smaInd = new SMAIndicator(closePrice, sma);
        result = determineSlopeStrength(smaInd, durationTimeframe, s.getEndIndex());
        return result;
    }

    /**
     * determine the rise or fall strenth (- is falling, + is rising)
     *
     * @param s TimeSeries
     * @param ema
     * @param durationTimeframe
     * @return double strenght ( from -1 to +1)
     */
    public double determineEMAStrength(TimeSeries s, int ema, int durationTimeframe) {
        double result = 0d;
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        EMAIndicator emaInd = new EMAIndicator(closePrice, ema);
        result = determineSlopeStrength(emaInd, durationTimeframe, s.getEndIndex());
        return result;
    }

    /**
     * determine the rise or fall strenth (- is falling, + is rising)
     *
     * @param s
     * @param durationTimeframe
     * @return double strenght ( from -1 to +1)
     */
    public double determineClosedPriceStrength(TimeSeries s, int durationTimeframe) {
        double result = 0d;
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        result = determineSlopeStrength(closePrice, durationTimeframe, s.getEndIndex());
        return result;
    }

    /**
     * return true if the last bar is rising ( short trend)
     *
     * @param ind
     * @return
     */
    private boolean isLastBarRising(Indicator<Num> ind) {
        Rule rule = new IsRisingRule(ind, 1, 0.1d);
        return rule.isSatisfied(ind.getTimeSeries().getEndIndex());
    }

    /**
     * returns true if the last bars are rising - number of bars is parameter 2
     *
     * @param ind
     * @param barcount
     * @return
     */
    private boolean isLastTrendRising(Indicator<Num> ind, int barcount) {
        Rule rule = new IsRisingRule(ind, barcount, 0.1d);
        return rule.isSatisfied(ind.getTimeSeries().getEndIndex());
    }

    public double determineSlopeStrength(Indicator<Num> ind, int durationTimeframe, int endIndex) {
        Rule rule = new IsRisingRule(ind, durationTimeframe, 0.1d);
        boolean rising = rule.isSatisfied(endIndex);
        Rule rule1;
        for (double d = 0; d <= 1; d += 0.1d) {
            if (rising) {
                rule1 = new IsRisingRule(ind, durationTimeframe, d);
            } else {
                rule1 = new IsFallingRule(ind, durationTimeframe, d);
            }
            boolean satisfied = rule1.isSatisfied(endIndex);
            if (!satisfied) {
                return rising ? d - 0.1d : -(d - 0.1d);
            }
        }
        return rising ? 1.0d : -1.0d;
    }

    /**
     * determine the rise or fall strenth of RSI (- is falling, + is rising)
     *
     * @param s
     * @param timeframe
     * @return
     */
    public double determineRSIStrength(TimeSeries s, int timeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        RSIIndicator rsiIndicator = new RSIIndicator(closePrice, timeframe);
        double result = determineSlopeStrength(rsiIndicator, timeframe, s.getEndIndex());
        return result;
    }

    public double determineRSIStrengthForTimefame1(TimeSeries s, int timeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        RSIIndicator rsiIndicator = new RSIIndicator(closePrice, timeframe);
        double result = determineSlopeStrength(rsiIndicator, 1, s.getEndIndex());
        return result;
    }

    /**
     * determine the rise or fall strenth of STO(- is falling, + is rising)
     *
     * @param s
     * @param timeframe
     * @return
     */
    public double determineSTOStrength(TimeSeries s, int timeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        StochasticRSIIndicator stochasticRSIIndicator = new StochasticRSIIndicator(closePrice, timeframe);
        return determineSlopeStrength(stochasticRSIIndicator, timeframe, s.getEndIndex());
    }

    public double determineSTOStrengthForTimeframe1(TimeSeries s, int timeframe) {
        ClosePriceIndicator closePrice = new ClosePriceIndicator(s);
        StochasticRSIIndicator stochasticRSIIndicator = new StochasticRSIIndicator(closePrice, timeframe);
        return determineSlopeStrength(stochasticRSIIndicator, 1, s.getEndIndex());
    }

    /**
     * calculate RSI (0-100)
     *
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

    public boolean rsiRisingUp(TimeSeries s, int timeFrame, int rsiThreshold) {
        int rsi = calculateRSI(s, timeFrame);
        double rsiS1 = determineRSIStrengthForTimefame1(s, timeFrame);
        return rsi < rsiThreshold && rsiS1 > 0d;
    }

    public boolean rsiPointingDown(TimeSeries s, int timeFrame, int rsiThreshold) {
        int rsi = calculateRSI(s, timeFrame);
        double rsiS1 = determineRSIStrengthForTimefame1(s, timeFrame);
        return rsi > rsiThreshold && rsiS1 < 0d;
    }

    public boolean stoRisingUp(TimeSeries s, int timeFrame, double stoThreshold) {
        double sto = calculateSTO(s, timeFrame);
        double stoS1 = determineSTOStrengthForTimeframe1(s, timeFrame);
        return sto < stoThreshold && stoS1 > 0d;
    }

    public boolean stoPointingDown(TimeSeries s, int timeFrame, double stoThreshold) {
        double sto = calculateSTO(s, timeFrame);
        double stoS1 = determineSTOStrengthForTimeframe1(s, timeFrame);
        return sto > stoThreshold && stoS1 < 0d;
    }

    /**
     * calculate STO (0.0 -1-0)
     *
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
     * analyzes if the market is long term bullish
     *
     * @param s
     * @return
     */
    public boolean isBullish(TimeSeries s) {
        return isBullishOrBearish(s, true);
    }

    /**
     * analyzes if the market is long term bearish
     *
     * @param s
     * @return
     */
    public boolean isBearish(TimeSeries s) {
        return isBullishOrBearish(s, false);
    }

    /**
     * analyzes if the market is long term bullish or bearish
     *
     * @param s
     * @param bullish
     * @return
     */
    public boolean isBullishOrBearish(TimeSeries s, boolean bullish) {
        boolean result = true;
        // default values 
        boolean ma = true;
        boolean cci = true;
        int cciTimeframe = 100;
        int cciThreshold = 0;
        int maTimeframe = 40;
        double maStrenght = 0.5d;


        if (ma) {
            result &= isMABullish(s, maTimeframe, maStrenght, bullish);
        }
        if (cci) {
            result &= isCCIBullish(s, cciTimeframe, cciThreshold, bullish);
        }
        if (!ma && !cci) {
            result = false;
        }
        return result;
    }

    public double getMarketStrenght(TimeSeries s) {
        double result = 0d;
        boolean ma = true;
        boolean cci = true;
        int maTimeframe = 40;

        double strMA = getMarketSMAStrength(s, maTimeframe);
        double strCCI = getMarketCCIStrenght(s);
        if (ma && !cci) {
            result = strMA;
        } else if (cci && !ma) {
            result = strCCI;
        } else if (ma && cci) {
            // medium of ma & cci - to be enhanced
            result = (strMA + strCCI) / 2;
        } else {
            return 0d;
        }
        return result;
    }

    /**
     * analyzes if the market is long term bullish or bearish based on the MA
     * data
     *
     * @param s
     * @param maTimeframe
     * @param strenght rising or falling strength of MA
     * @param bullish
     * @return
     */
    public boolean isMABullish(TimeSeries s, int maTimeframe, double strenght, boolean bullish) {
        double strMA = getMarketSMAStrength(s, maTimeframe);
        if (bullish) {
            return strMA >= strenght;
        } else {
            return strMA <= -strenght;
        }
    }

    /**
     * analyzes if the market is long term bullish or bearish based on the CCI
     * data
     *
     * @param s
     * @param timeframe the CCI timeframe
     * @param threshold the CCI threshold
     * @param bullish
     * @return
     */
    public boolean isCCIBullish(TimeSeries s, int timeframe, int threshold, boolean bullish) {
        int cci = determineCCI(s, timeframe);
        return bullish ? cci > threshold : cci < threshold;
    }

    /**
     * determine the value of the CCI
     *
     * @param s
     * @param timeframe
     * @return
     */
    public int determineCCI(TimeSeries s, int timeframe) {
        CCIIndicator cci = new CCIIndicator(s, timeframe);
        Num result = cci.getValue(s.getEndIndex());
        return result.intValue();
    }




    /**
     * create TO AddOrderInfo with current buy/sell params
     *
     * @param s TimeSeries
     * @param rsiTimeframe
     * @param stoTimeframe
     * @return AddOrderInfo
     */
    public AddOrderInfo analyzeOrderParams(TimeSeries s, int rsiTimeframe, int stoTimeframe) {
        int timeframe = 2;
        int rsi = calculateRSI(s, rsiTimeframe);
        double sto = calculateSTO(s, stoTimeframe);
        double closedPriceStrenth = determineClosedPriceStrength(s, 2);
        double rsiStrenght = determineRSIStrength(s, rsiTimeframe);
        double stoStrenght = determineSTOStrength(s, stoTimeframe);
        double rsiStrenght1 = determineRSIStrengthForTimefame1(s, rsiTimeframe);
        double stoStrenght1 = determineSTOStrengthForTimeframe1(s, stoTimeframe);
        double sma3 = determineSMAStrength(s, 3, timeframe);
        double sma8 = determineSMAStrength(s, 8, timeframe);
        double sma14 = determineSMAStrength(s, 14, timeframe);
        double sma50 = determineSMAStrength(s, 50, timeframe);
        double sma200 = determineSMAStrength(s, 200, timeframe);
        double sma314 = determineSMAStrength(s, 314, timeframe);
        double ema14 = determineEMAStrength(s, 14, timeframe);
        double ema50 = determineEMAStrength(s, 50, timeframe);
        double cci14 = determineCCI(s, 14);
        double cci50 = determineCCI(s, 50);
        boolean priceAboveSma200 = isPriceAboveSMA(s, 200);
        boolean priceAboveSma3141 = isPriceAboveSMA(s, 314);
        boolean isSMALongTimeBullish = isSMALongTimeBullish(s, 3, 80, timeframe);

        return AddOrderInfo.builder().rsi(rsi).sto(sto).closedPriceStrenth(closedPriceStrenth).sma3(sma3).sma14(sma14).sma8(sma8).sma50(sma50).
                sma200(sma200).sma314(sma314).ema14(ema14).ema50(ema50).priceAboveSma200(priceAboveSma200).
                priceAboveSma3141(priceAboveSma3141).isSMALongTimeBullish(isSMALongTimeBullish).
                rsiStrength(rsiStrenght).stoStrength(stoStrenght).
                rsiStrength1(rsiStrenght1).stoStrength1(stoStrenght1).cci14(cci14).cci50(cci50).build();

    }
}
