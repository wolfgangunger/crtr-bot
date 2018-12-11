package com.unw.crypto.strategy;

import org.springframework.stereotype.Component;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.StopGainRule;
import org.ta4j.core.trading.rules.StopLossRule;

/**
 * Moving momentum strategy.
 * <p>
 * </p> @see
 * <a href="http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:moving_momentum">
 * http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:moving_momentum</a>
 */
@Component
public class TestStrategy extends AbstractStrategy {

    private int iMAShort = 12;
    private int iMALong = 26;

    /**
     * @param series a time series
     * @return a moving momentum strategy
     */
    public Strategy buildStrategy(TimeSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }
        System.out.println("short ma: " + iMAShort);
        System.out.println("long ma: " + iMALong);
        // indicators -----------------------------------------------------
        // simple base indicator for closed price
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

//        int shortMa = iMAShort * 12;
//        int longMa = iMALong * 12;
        // The bias is bullish when the shorter-moving average moves above the longer moving average.
        // The bias is bearish when the shorter-moving average moves below the longer moving average.
        EMAIndicator shortEma = new EMAIndicator(closePrice, iMAShort);
        EMAIndicator longEma = new EMAIndicator(closePrice, iMALong);
        // RSI
        RSIIndicator rsiIndicator = new RSIIndicator(closePrice, 4);
        // stochastik
        StochasticRSIIndicator stochasticRSIIndicator = new StochasticRSIIndicator(closePrice, 18);
        StochasticOscillatorKIndicator stochasticOscillK = new StochasticOscillatorKIndicator(series, 14);

        MACDIndicator macd = new MACDIndicator(closePrice, iMAShort, iMALong);
        EMAIndicator emaMacd = new EMAIndicator(macd, 18);

        //Rules  -----------------------------------------------------------------
        // FixedRule, InSlopeRule, InPipeRule, IsFallingRule, IsHighestRule, IsRisingRule, JustOnceRule, WaitForRule, BooleanIndicatorRule
        //        CrossedDownIndicatorRule, CrossedUpIndicatorRule,
        // Entry rules -------------
        // simple rule when shortEma crosses up longEma (v)
        //Rule entryRule = new CrossedUpIndicatorRule(shortEma, longEma) ;// Trend
        // simple rule when RSI moves below 20 (v)
        //Rule entryRule = new CrossedDownIndicatorRule(rsiIndicator, Decimal.valueOf(10));
        // simple rule when RSI moves over 20 (v)
        //Rule entryRule = new CrossedUpIndicatorRule(rsiIndicator, Decimal.valueOf(10));
        // not working with these params - invest  
        //Rule entryRule = new CrossedUpIndicatorRule(stochasticRSIIndicator, Decimal.valueOf(20));
        // simple rule when Stoch moves up
        Rule entryRule = new CrossedUpIndicatorRule(stochasticRSIIndicator, Decimal.valueOf(0.1d));
        //Rule entryRule = new CrossedDownIndicatorRule(stochasticRSIIndicator, Decimal.valueOf(80)) ;

//                .and(new CrossedDownIndicatorRule(stochasticOscillK, Decimal.valueOf(20))) // Signal 1
//                .and(new OverIndicatorRule(macd, emaMacd)); // Signal 2
        //Rule entryRule = new OverIndicatorRule(shortEma, longEma) ;// Trend    
        // Exit rules --------------
        // Rule exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
        // .and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal.valueOf(80))) // Signal 1
        // .and(new UnderIndicatorRule(macd, emaMacd))
        // .and(new StopLossRule(closePrice, Decimal.valueOf(3)));
        // .and(new StopGainRule(closePrice, Decimal.valueOf(-1)));
        //.and(new IsFallingRule(closePrice, 3));
        //Rule exitRule = new StopGainRule(closePrice, Decimal.valueOf(6));
        Rule exitRule = new StopLossRule(closePrice, Decimal.valueOf(0.4d))
                .or(new StopGainRule(closePrice, Decimal.valueOf(0.4d)));
        //Rule exitRule = new IsFallingRule(closePrice, 10, 0.2d);
//        Rule exitRule = new StopGainRule(closePrice, Decimal.valueOf(2))
//                .and(new StopLossRule(closePrice, Decimal.valueOf(1)));

        //Rule exitRule = new FixedRule(1,2,4,5);
        return new BaseStrategy(entryRule, exitRule);
    }

    public TradingRecord execute(TimeSeries series) {

        // Building the trading strategy
        Strategy strategy = buildStrategy(series);

        // Running the strategy
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        System.out.println("Number of trades for the strategy: " + tradingRecord.getTradeCount());

        // Analysis
        System.out.println("Total profit for the strategy: " + new TotalProfitCriterion().calculate(series, tradingRecord));
        return tradingRecord;
    }

    public int getiMAShort() {
        return iMAShort;
    }

    public void setiMAShort(int iMAShort) {
        this.iMAShort = iMAShort;
    }

    public int getiMALong() {
        return iMALong;
    }

    public void setiMALong(int iMALong) {
        this.iMALong = iMALong;
    }
}
