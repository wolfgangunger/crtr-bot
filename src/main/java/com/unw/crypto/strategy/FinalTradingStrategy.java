/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import org.springframework.stereotype.Component;
import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Decimal;
import org.ta4j.core.Rule;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TimeSeriesManager;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.RSIIndicator;
import org.ta4j.core.indicators.StochasticRSIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.StopGainRule;
import org.ta4j.core.trading.rules.StopLossRule;

/**
 *
 * @author UNGERW
 */
@Component
public class FinalTradingStrategy extends AbstractStrategy {

    private int iMAShort = 9;
    private int iMALong = 26;

    @Override
    public Strategy buildStrategy(TimeSeries series) {

//1- RSI is low and pointing up (v)
//2- Stochastic is low and pointing up (v)
//3- Price is above SMA200&314
//4- 8-MA is pointing up
//5- Price is near or below the 8-MA (the further away from the 8-MA price is, the higher probability price will turn back towards it)
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
        EMAIndicator shortEma = new EMAIndicator(closePrice, iMAShort);
        EMAIndicator longEma = new EMAIndicator(closePrice, iMALong);
        // RSI
        RSIIndicator rsiIndicator = new RSIIndicator(closePrice, 4);
        // stochastik
        StochasticRSIIndicator stochasticRSIIndicator = new StochasticRSIIndicator(closePrice, 18);

        // rules 
        // 1
        Rule entryRule1 = new CrossedUpIndicatorRule(rsiIndicator, Decimal.valueOf(10));
        //
        Rule entryRule2 = new CrossedUpIndicatorRule(stochasticRSIIndicator, Decimal.valueOf(0.1d));

        // exit rule - todo
        Rule exitRule = new StopLossRule(closePrice, Decimal.valueOf(0.4d))
                .or(new StopGainRule(closePrice, Decimal.valueOf(0.4d)));

        return new BaseStrategy(entryRule1, exitRule);

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
