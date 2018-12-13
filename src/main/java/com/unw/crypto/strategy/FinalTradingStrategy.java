/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.strategy.to.RuleChain;
import com.unw.crypto.strategy.to.StrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParamsBuilder;
import org.springframework.stereotype.Component;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Decimal;
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
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.IsRisingRule;
import org.ta4j.core.trading.rules.StopGainRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;
import org.ta4j.core.trading.rules.WaitForRule;

/**
 *
 * @author UNGERW
 */
@Component
public class FinalTradingStrategy extends AbstractStrategy {

    private int iMAShort = 9;
    private int iMALong = 26;


    @Override
    public Strategy buildStrategy(TimeSeries series, BarDuration barDuration) {

        int stoRsiTimeframe = 18;
        int stoOscKTimeFrame = 14;
        int emaIndicatorTimeframe = 18;
        int rsiThresholdLow = 15;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.15d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double stopLoss  =1;
       double stopGain = -1d;
       int waitBars = 50;
        StrategyInputParams params = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, iMAShort, iMALong, iMAShort, iMALong, 4,
                stoRsiTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, stopLoss, stopGain, waitBars );

        return buildStrategyWithParams(series, params);
    }

    /**
     * this method can be used from outside app to test strategy , for example
     * junit or later for scheduled services
     *
     * @param series TimeSeries
     * @param params the parameter object StrategyInputParams ( contains all params for indicators and strategies )
     * @return Strategy (BaseStrategy)
     */
    public Strategy buildStrategyWithParams(TimeSeries series, StrategyInputParams params) {

        // these rules are designed for hour candles - in case of shorter candles and bars the timeframe params (MA ...) must be adapted
//1- RSI is low and pointing up (v)
//2- Stochastic is low and pointing up (v)
//3- Price is above SMA200&314 ???? really ?
//4- 8-MA is pointing up (v)
//5- Price is near or below the 8-MA (v) (the further away from the 8-MA price is, the higher probability price will turn back towards it)
//6- Price is _above_ a known area of resistance (use Fib levels to determine those zones)
//7- Moving EMA bands are angled up
//8- Price is not approaching prior resistance
//9- Price is near the bottom of an identified cycle
//10- Still room to grow in larger time frames
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
        Rule entryRule1 = new CrossedDownIndicatorRule(rsiIndicator, Decimal.valueOf(params.getRsiThresholdLow()));
        // 2  STO is crossing low threshold 
        Rule entryRule2 = new CrossedDownIndicatorRule(stochasticRSIIndicator, Decimal.valueOf(params.getStoThresholdLow()));
        // 3 - to be done - does it make sense ?

        // 4 8-MA is pointing up - second param to check
        Rule entryRule4 = new IsRisingRule(smaLong, params.getSma8());

        //5- Price is near or below the 8-MA 
        Rule entryRule5 = new UnderIndicatorRule(closePrice, smaLong);

        // the complete final rule 
        Rule entryRule = entryRule1.and(entryRule2).and(entryRule4).and(entryRule5);

        // exit rule - todo
//        Rule exitRule = new StopLossRule(closePrice, Decimal.valueOf(0.4d))
//                .or(new StopGainRule(closePrice, Decimal.valueOf(0.4d)));
        Rule exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
                .and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal.valueOf(params.getStoOscKThresholdHigh()))) // Signal 1
                .and(new UnderIndicatorRule(macd, emaMacd))
                .and(new StopGainRule(closePrice, Decimal.valueOf(params.getStopGain()))); // works
//                .and(new StopGainRule(closePrice, Decimal.valueOf(-1))); // works
        //             .or(new StopLossRule(closePrice, Decimal.valueOf(0.3d)));

        Rule exitRule2 = new WaitForRule(Order.OrderType.BUY, params.getWaitBars());

        return new BaseStrategy(entryRule, exitRule2);
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
    private Rule buildCompleteEntryRule(RuleChain ruleChain){
        
        return null;
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
