package com.unw.crypto.strategy;

import org.springframework.stereotype.Component;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.EMAIndicator;
import org.ta4j.core.indicators.MACDIndicator;
import org.ta4j.core.indicators.StochasticOscillatorKIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.trading.rules.CrossedDownIndicatorRule;
import org.ta4j.core.trading.rules.CrossedUpIndicatorRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

/**
 * Moving momentum strategy.
 * <p>
 * </p> @see
 * <a href="http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:moving_momentum">
 * http://stockcharts.com/school/doku.php?id=chart_school:trading_strategies:moving_momentum</a>
 */
@Component
public class MovingMomentumStrategy extends AbstractStrategy {

    private int iMAShort = 9;
    private int iMALong = 26;
    /**
     * @param series a time series
     * @return a moving momentum strategy
     */
    public Strategy buildStrategy(TimeSeries series) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

        // The bias is bullish when the shorter-moving average moves above the longer moving average.
        // The bias is bearish when the shorter-moving average moves below the longer moving average.
        EMAIndicator shortEma = new EMAIndicator(closePrice, iMAShort);
        EMAIndicator longEma = new EMAIndicator(closePrice, iMALong);

        StochasticOscillatorKIndicator stochasticOscillK = new StochasticOscillatorKIndicator(series, 14);

        MACDIndicator macd = new MACDIndicator(closePrice, iMAShort, iMALong);
        EMAIndicator emaMacd = new EMAIndicator(macd, 18);

        // Entry rule
        Rule entryRule = new OverIndicatorRule(shortEma, longEma) // Trend
                .and(new CrossedDownIndicatorRule(stochasticOscillK, Decimal.valueOf(20))) // Signal 1
                .and(new OverIndicatorRule(macd, emaMacd)); // Signal 2

        // Exit rule
        Rule exitRule = new UnderIndicatorRule(shortEma, longEma) // Trend
                .and(new CrossedUpIndicatorRule(stochasticOscillK, Decimal.valueOf(80))) // Signal 1
                .and(new UnderIndicatorRule(macd, emaMacd)); // Signal 2

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
