/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.strategy.to.EntryRuleChain;
import com.unw.crypto.strategy.to.ExitRuleChain;
import com.unw.crypto.strategy.to.StrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParamsBuilder;
import org.springframework.stereotype.Component;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Order;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TimeSeriesManager;
import org.ta4j.core.TradingRecord;
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
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.IsFallingRule;
import org.ta4j.core.trading.rules.IsRisingRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.StopGainRule;
import org.ta4j.core.trading.rules.TrailingStopLossRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;
import org.ta4j.core.trading.rules.WaitForRule;

/**
 *
 * @author UNGERW
 */
@Component
public class FinalTradingStrategy extends AbstractStrategy implements IFinalTradingStrategy {

    private int iMAShort = 9;
    private int iMALong = 26;

    /**
     * use this method to execute strategy from outside with params
     *
     * @param series
     * @param params
     * @return
     */
    public TradingRecord executeWithParams(TimeSeries series, StrategyInputParams params) {
        Strategy strategy = buildStrategyWithParams(series, params);
        // Running the strategy
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        System.out.println("Number of trades for the strategy: " + tradingRecord.getTradeCount());
        // Analysis
        System.out.println("Total profit for the strategy: " + new TotalProfitCriterion().calculate(series, tradingRecord));
        return tradingRecord;
    }

    /**
     * use this method to execute strategy from outside with params
     *
     * @param series
     * @param params
     * @param strategy
     * @return
     */
    public TradingRecord executeWithParams(TimeSeries series, StrategyInputParams params, Strategy strategy) {
        // Running the strategy
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        TradingRecord tradingRecord = seriesManager.run(strategy);
        System.out.println("Number of trades for the strategy: " + tradingRecord.getTradeCount());
        // Analysis
        System.out.println("Total profit for the strategy: " + new TotalProfitCriterion().calculate(series, tradingRecord));
        return tradingRecord;
    }

    public TradingRecord execute(TimeSeries series, BarDuration barDuration) {
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

    @Override
    public Strategy buildStrategy(TimeSeries series, BarDuration barDuration) {

        boolean barMultiplikator = true;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int rsiTimeframe = 4;
        int stoRsiTimeframe = 18;
        int stoOscKTimeFrame = 14;
        int emaIndicatorTimeframe = 18;
        int smaIndicatorTimeframe = 12;
        int priceTimeframe = 2;
        int rsiThresholdLow = 15;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.15d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.7d;
        double fallingStrenght = 0.7d;
        double stopLoss = 1;
        double stopGain = -1d;
        int waitBars = 50;
        EntryRuleChain entryRuleChain = EntryRuleChain.builder().rule1_rsiLow(true).rule2_stoLow(true).rule3_priceAboveSMA200(false).
                rule4_ma8PointingUp(true).rule5_priceBelow8MA(true).rule7_emaBandsPointingUp(true).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(true).rule2_stoHigh(true)
                .rule3_8maDown(true).rule11_rsiPointingDown(false).rule12_StoPointingDown(false).build();
        StrategyInputParams params = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8, ma14, ma200, ma314, iMAShort, iMALong, iMAShort, iMALong, rsiTimeframe,
                stoRsiTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeframe, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, stopGain, waitBars, entryRuleChain, exitRuleChain);

        return buildStrategyWithParams(series, params);
    }

    /**
     * this method can be used from outside app to test strategy , for example
     * junit or later for scheduled services
     *
     * @param series TimeSeries
     * @param params the parameter object StrategyInputParams ( contains all
     * params for indicators and strategies )
     * @return Strategy (BaseStrategy)
     */
    public Strategy buildStrategyWithParams(TimeSeries series, StrategyInputParams params) {

        // these rules are designed for hour candles - in case of shorter candles and bars the timeframe params (MA ...) must be adapted
//1- RSI is low and pointing up (v) only crossed down implemented, see rule 11 for pointing up
//2- Stochastic is low and pointing up (v) only crossed down implemented, see rule 12 for pointing up
//3- Price is above SMA200&314 (v) 
//4- 8-MA is pointing up (v)
//5- Price is near or below the 8-MA (v) (the further away from the 8-MA price is, the higher probability price will turn back towards it)
//6- Price is _above_ a known area of resistance (use Fib levels to determine those zones) TODO
//7- Moving EMA bands are angled up (v)
//8- Price is not approaching prior resistance
//9- Price is near the bottom of an identified cycle
//10- Still room to grow in larger time frames
// 11 RSI is pointing up
// 12 STO is pointing up
// 13 Moving Momentum
//Be prepare to sell when the rsi, stoch and 8-MA turn down in agreement
        // simple base indicator for closed price
        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

        // moving averages
        // The bias is bullish when the shorter-moving average moves above the longer moving average.
        // The bias is bearish when the shorter-moving average moves below the longer moving average.
        EMAIndicator shortEma = new EMAIndicator(closePrice, params.getEmaShort());
        EMAIndicator longEma = new EMAIndicator(closePrice, params.getEmaLong());

        // simple moving average on long time frame
        SMAIndicator smaLong = new SMAIndicator(closePrice, params.getSmaLong());
        // simple MA8 
        SMAIndicator sma8 = new SMAIndicator(closePrice, params.getSma8());
        // simple MA200
        SMAIndicator sma200 = new SMAIndicator(closePrice, params.getSma200());
        SMAIndicator sma2314 = new SMAIndicator(closePrice, params.getSma314());
        // RSI
        RSIIndicator rsiIndicator = new RSIIndicator(closePrice, params.getRsiTimeframe());
        // stochastik
        StochasticRSIIndicator stochasticRSIIndicator = new StochasticRSIIndicator(closePrice, params.getStoRsiTimeframe());
        StochasticOscillatorKIndicator stochasticOscillK = new StochasticOscillatorKIndicator(series, params.getStoOscKTimeFrame());
        //MACD
        MACDIndicator macd = new MACDIndicator(closePrice, params.getSmaShort(), params.getSmaLong());
        EMAIndicator emaMacd = new EMAIndicator(macd, params.getEmaIndicatorTimeframe());

        // ----------
        // rules 
        // 1 - RSI is crossing low threshold 
        Rule entryRule1 = new CrossedDownIndicatorRule(rsiIndicator, DoubleNum.valueOf(params.getRsiThresholdLow()));
        // 2  STO is crossing low threshold 
        Rule entryRule2 = new CrossedDownIndicatorRule(stochasticRSIIndicator, DoubleNum.valueOf(params.getStoThresholdLow()));
        // 3 - prive over SMA 200 (and 314)
        Rule entryRule3 = new OverIndicatorRule(closePrice, sma200);
        // 4 8-MA is pointing up - second param to check
        Rule entryRule4 = new IsRisingRule(sma8, params.getSmaIndicatorTimeframe(), params.getRisingStrenght());
        //5- Price is near or below the 8-MA 
        Rule entryRule5 = new UnderIndicatorRule(closePrice, sma8);
        // Rule 6 TODO 

        // Rule 7
        Rule entryRule7 = new IsRisingRule(shortEma, params.getEmaIndicatorTimeframe())
                .and(new IsRisingRule(longEma, params.getEmaIndicatorTimeframe()));
        // .and(new OverIndicatorRule(shortEma, longEma)) // Trend

        // rule 11 rsi pointing up
        Rule entryRule11 = new IsRisingRule(rsiIndicator, params.getRsiTimeframe(), params.getRisingStrenght());

        //rule 12 sto pointing up
        Rule entryRule12 = new IsRisingRule(stochasticRSIIndicator, params.getStoRsiTimeframe(), params.getRisingStrenght());

        // rule 13 - moving momentung
        Rule entryRule13 = new OverIndicatorRule(shortEma, longEma) // Trend
                .and(new CrossedDownIndicatorRule(stochasticOscillK, DoubleNum.valueOf(params.getStoThresholdLow()))) // Signal 1
                .and(new OverIndicatorRule(macd, emaMacd)); // Signal 2

        // build the complete final rule 
        Rule entryRule = buildCompleteEntryRule(closePrice, params.getEntryRuleChain(), entryRule1, entryRule2, entryRule3, entryRule4, entryRule5,
                entryRule7, entryRule11, entryRule12, entryRule13);

        // exit rule - todo
//        Rule exitRule = new StopLossRule(closePrice, Decimal.valueOf(0.4d))
//                .or(new StopGainRule(closePrice, Decimal.valueOf(0.4d)));
        //Rule exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
        //        .and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal.valueOf(params.getStoOscKThresholdHigh()))); // Signal 1
        // .and(new UnderIndicatorRule(macd, emaMacd));
        //.and(new StopGainRule(closePrice, Decimal.valueOf(params.getStopGain()))); // works
//                .and(new StopGainRule(closePrice, Decimal.valueOf(-1))); // works
        //Rule exitRule2 = new WaitForRule(Order.OrderType.BUY, params.getWaitBars()).
        //        or(new StopLossRule(closePrice, Decimal.valueOf(params.getStopLoss())));
        Rule exitRuleb = new WaitForRule(Order.OrderType.BUY, params.getWaitBars());

        //////////// exit rules
        // rsi is falling - isFalling or CrossedUp ? don't work together
        Rule exitRule1 = new CrossedUpIndicatorRule(rsiIndicator, DoubleNum.valueOf(params.getRsiThresholdHigh()));
        Rule exitRule11 = new IsFallingRule(rsiIndicator, params.getRsiTimeframe(), params.getFallingStrenght());

        Rule exitRule2 = new CrossedUpIndicatorRule(stochasticRSIIndicator, DoubleNum.valueOf(params.getStoThresholdHigh()));
        Rule exitRule12 = new IsFallingRule(stochasticRSIIndicator, params.getStoRsiTimeframe(), params.getFallingStrenght());
        // ma 8 is falling
        Rule exitRule3 = new IsFallingRule(sma8, params.getSmaIndicatorTimeframe(), params.getFallingStrenght());

        // prive is falling
        Rule exitRule21 = new IsFallingRule(closePrice, params.getPriceTimeFrame(), params.getFallingStrenght());
        // strict falling ruing
        Rule exitRule21b = new IsFallingRule(closePrice, 1, 1d);

        //Rule exitRule22 = new StopLossRule(closePrice, DoubleNum.valueOf(params.getStopLoss()));
        Rule exitRule22 = new TrailingStopLossRule(closePrice, DoubleNum.valueOf(params.getStopLoss()));        
        //.and(new StopGainRule(closePrice, Decimal.valueOf(-1))); // works
        Rule exitRule23 = new StopGainRule(closePrice, DoubleNum.valueOf(params.getStopGain()));

        Rule exitRule = buildCompleteExitRule(closePrice, params.getExitRuleChain(), exitRule1, exitRule2, exitRule3, exitRule11,
                exitRule12, exitRule21, exitRule21b, exitRule22, exitRule23);

        return new BaseStrategy(entryRule, exitRule);
    }

    /**
     * concatenate the rules depending on the boolean params
     *
     * @param closePrice
     * @param ruleChain
     * @param rule1
     * @param rule2
     * @param rule3
     * @param rule4
     * @param rule5
     * @param rule7
     * @return the complete Rule Chain
     */
    private Rule buildCompleteEntryRule(ClosePriceIndicator closePrice, EntryRuleChain ruleChain, Rule rule1, Rule rule2, Rule rule3, Rule rule4, Rule rule5,
            Rule rule7, Rule rule11, Rule rule12, Rule rule13) {
        // first create a rule, which will always be chained and is always true
        Rule result = new OverIndicatorRule(closePrice, DoubleNum.valueOf(0));

        if (ruleChain.isRule1_rsiLow()) {
            result = result.and(rule1);
        }
        if (ruleChain.isRule2_stoLow()) {
            result = result.and(rule2);
        }
        if (ruleChain.isRule3_priceAboveSMA200()) {
            result = result.and(rule3);
        }
        if (ruleChain.isRule4_ma8PointingUp()) {
            result = result.and(rule4);
        }
        if (ruleChain.isRule5_priceBelow8MA()) {
            result = result.and(rule5);
        }
        if (ruleChain.isRule7_emaBandsPointingUp()) {
            result = result.and(rule7);
        }
        //
        if (ruleChain.isRule11_isRsiPointingUp()) {
            result = result.and(rule11);
        }
        if (ruleChain.isRule12_isStoPointingUp()) {
            result = result.and(rule12);
        }
        if (ruleChain.isRule13_movingMomentum()) {
            result = result.and(rule13);
        }
        return result;
    }

    /**
     * concatenate the rules depending on the boolean params
     * @param closePrice
     * @param ruleChain
     * @param rule1
     * @param rule2
     * @param rule3
     * @param rule11
     * @param rule12
     * @param rule21
     * @param rule21b
     * @param rule22
     * @param rule23
     * @return the complete Rule Chain
     */
    private Rule buildCompleteExitRule(ClosePriceIndicator closePrice, ExitRuleChain ruleChain, Rule rule1, Rule rule2, Rule rule3,
            Rule rule11, Rule rule12, Rule rule21, Rule rule21b, Rule rule22, Rule rule23) {
        // first create a rule, which will always be chained and is always true
        Rule result = new OverIndicatorRule(closePrice, DoubleNum.valueOf(0));

        if (ruleChain.isRule1_rsiHigh()) {
            result = result.and(rule1);
        }
        if (ruleChain.isRule2_stoHigh()) {
            result = result.and(rule2);
        }
        if (ruleChain.isRule3_8maDown()) {
            result = result.and(rule3);
        }
        if (ruleChain.isRule11_rsiPointingDown()) {
            result = result.and(rule11);
        }
        if (ruleChain.isRule12_StoPointingDown()) {
            result = result.and(rule12);
        }
        
        if (ruleChain.isRule21_priceFalling()) {
            result = result.and(rule21);
        }
        /// or rules - single rule will cause sell
        if (ruleChain.isRule22_stopLoss()) {
            result = result.or(rule22);
        }
        if (ruleChain.isRule23_stopGain()) {
            // or / and ? to be checked
            //result = result.or(rule23);
            result = result.and(rule23);
        }
        // strict price falling rule
        // result = result.or(rule21b);

        return result;
    }

}
