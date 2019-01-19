package com.unw.crypto.strategy;

import com.unw.crypto.model.BarDuration;
import org.springframework.stereotype.Component;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.SMAIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.IsRisingRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.TrailingStopLossRule;
import org.ta4j.core.trading.rules.WaitForRule;

/**
 * Moving momentum strategy.
 * <p>
 * </p> @see
 * <a href="http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:moving_momentum">
 * http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:moving_momentum</a>
 */
@Component
public class TestStrategy extends AbstractStrategy {


    /**
     * @param series a time series
     * @return a moving momentum strategy
     */
    public Strategy buildStrategy(TimeSeries series, BarDuration barDuration) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }
        // indicators -----------------------------------------------------
        // simple base indicator for closed price
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

//        int shortMa = iMAShort * 12;
//        int longMa = iMALong * 12;
        // The bias is bullish when the shorter-moving average moves above the longer moving average.
        // The bias is bearish when the shorter-moving average moves below the longer moving average.
        // exp. moving average
        EMAIndicator shortEma = new EMAIndicator(closePrice, iMAShort);
        EMAIndicator longEma = new EMAIndicator(closePrice, iMALong);
        // simple moving average 
        SMAIndicator smaLong = new SMAIndicator(closePrice, iMALong);
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
        // MAs
        // simple rule when shortEma crosses up longEma (v)
        //Rule entryRule = new CrossedUpIndicatorRule(shortEma, longEma) ;// Trend
        // simple rule when RSI moves below 20 (v)
        //Rule entryRule = new CrossedDownIndicatorRule(rsiIndicator, Decimal.valueOf(10));
        // sma is pointing up (v) - second parameter can be varied - brute force ?
        //Rule entryRule = new IsRisingRule(smaLong, iMAShort);
        // // Price is (near or) below the 8-MA
        //Rule entryRule = new UnderIndicatorRule(closePrice, smaLong);
        // RSI
        // simple rule when RSI moves over 20 (v)
        //Rule entryRule = new CrossedUpIndicatorRule(rsiIndicator, Decimal.valueOf(10));
        // not working with these params - invest  
        //Rule entryRule = new CrossedUpIndicatorRule(stochasticRSIIndicator, Decimal.valueOf(20));
        // simple rule when Stoch moves up
        // Rule entryRule = new CrossedUpIndicatorRule(stochasticRSIIndicator, Decimal.valueOf(0.1d));
       Rule entryRule = new OverIndicatorRule(shortEma, longEma) // Trend
                .and(new CrossedDownIndicatorRule(stochasticOscillK, DoubleNum.valueOf(20))) // Signal 1
                .and(new OverIndicatorRule(macd, emaMacd)); // Signal 2
        //Rule entryRule = new OverIndicatorRule(shortEma, longEma) ;// Trend    
        
        Rule entryRule1 = new CrossedDownIndicatorRule(rsiIndicator, DoubleNum.valueOf(20));
        // 2  STO is crossing low threshold 
        Rule entryRule2 = new CrossedDownIndicatorRule(stochasticRSIIndicator, DoubleNum.valueOf(0.18d));
        
                // rule 11 rsi pointing up
        Rule entryRule3 = new IsRisingRule(rsiIndicator, 1 , 0.1d);
        
        //Rule entryRule = entryRule1.and(entryRule3);
        // Exit rules --------------
        //Rule exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
        // .and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal.valueOf(80))) // Signal 1
        // .and(new UnderIndicatorRule(macd, emaMacd))
        // .and(new StopLossRule(closePrice, Decimal.valueOf(3)));
        // .and(new StopGainRule(closePrice, Decimal.valueOf(-1)));
        //.and(new IsFallingRule(closePrice, 3));
        //Rule exitRule = new StopGainRule(closePrice, Decimal.valueOf(6));
        //Rule exitRule = new StopLossRule(closePrice, Decimal.valueOf(0.2d))
        //       .or(new StopGainRule(closePrice, Decimal.valueOf(0.2d)));
//Rule exitRule = new IsFallingRule(closePrice, 10, 0.2d);
//        Rule exitRule = new StopGainRule(closePrice, Decimal.valueOf(2))
//                .and(new StopLossRule(closePrice, Decimal.valueOf(1)));
        //Rule exitRule = new FixedRule(indexes);
        
        //Rule exitRule = new WaitForRule(Order.OrderType.BUY, 15);
         Rule exitRule = new WaitForRule(Order.OrderType.BUY, 25).or(new TrailingStopLossRule(closePrice, DoubleNum.valueOf(0.5d)));
//                Rule exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
//                .and(new CrossedUpIndicatorRule(stochasticOscillK, DoubleNum.valueOf(80))) // Signal 1
//                .and(new UnderIndicatorRule(macd, emaMacd)).or( new TrailingStopLossRule(closePrice, DoubleNum.valueOf(1.5d)));
        
        
        // checks only the timeframe not the volume
        //Rule exitRule = new IsFallingRule(closePrice, 3);
        //Rule exitRule = new FixedRule(1,2,4,5);
        return new BaseStrategy(entryRule, exitRule);
    }

    public TradingRecord execute(TimeSeries series,BarDuration barDuration) {

        // Building the trading strategy
        Strategy strategy = buildStrategy(series, barDuration);

        // Running the strategy
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        System.out.println("Number of trades for the strategy: " + tradingRecord.getTradeCount());

        // Analysis
        System.out.println("Total profit for the strategy: " + new TotalProfitCriterion().calculate(series, tradingRecord));
        return tradingRecord;
    }

}
