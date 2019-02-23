package com.unw.crypto.strategy;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.strategy.to.AbstractStrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParamsQuadCCI;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.IsFallingRule;
import org.ta4j.core.trading.rules.IsRisingRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.StopLossRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

/**
 *
 * @author UNGERW
 */
@Component
public class QuadCCIStrategy extends AbstractStrategy implements IFinalTradingStrategy {

    /**
     * @param series a time series
     * @return a CCI correction strategy
     */
    public Strategy buildStrategy(TimeSeries series, BarDuration barDuration) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }
        StrategyInputParamsQuadCCI params = StrategyInputParamsQuadCCI.builder().cci100(100).cci200(200).cci50(50).cci14(14)
                .cci100Threshold(0).cci200Threshold(0).cci50(-100).cci14Threshold(-100).build();

        return buildStrategyWithParams(series, params);
    }

    public Strategy buildStrategyWithParams(TimeSeries series, AbstractStrategyInputParams p) {
        StrategyInputParamsQuadCCI params = (StrategyInputParamsQuadCCI) p;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

        CCIIndicator cci14 = new CCIIndicator(series, params.getCci14());
        CCIIndicator cci50 = new CCIIndicator(series, params.getCci50());
        CCIIndicator cci100 = new CCIIndicator(series, params.getCci100());
        CCIIndicator cci200 = new CCIIndicator(series, params.getCci200());
        Num zero = DoubleNum.valueOf(BigDecimal.valueOf(0));
        Num plus100 = DoubleNum.valueOf(BigDecimal.valueOf(100));
        Num minus100 = DoubleNum.valueOf(BigDecimal.valueOf(- 100));

        Rule entryRule1 = new OverIndicatorRule(cci200, params.getCci200Threshold()); // Bull trend
        Rule entryRule2 = new OverIndicatorRule(cci100, params.getCci100Threshold()); // Bull trend
        Rule entryRule3 = new OverIndicatorRule(cci50, params.getCci50Threshold()); // Bull trend

        Rule entryRule4 = new UnderIndicatorRule(cci14, params.getCci14Threshold()); // Signal
        Rule entryRule4b = new IsRisingRule(cci14, 1); // Signal

        Rule entryRule = entryRule1.and(entryRule2).and(entryRule3).and(entryRule4).and(entryRule4b);

        /// exit
        Rule exitRule1 = new OverIndicatorRule(cci14, plus100); // Signal
        Rule exitRule1b = new IsFallingRule(cci14, 1);
        // stop loss
        Rule exitRule10 = new StopLossRule(closePrice, DoubleNum.valueOf(5d));

        Rule exitRule = exitRule1.and(exitRule1b).and(exitRule10);

        Strategy strategy = new BaseStrategy(entryRule, exitRule);
        strategy.setUnstablePeriod(5);
        return strategy;

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
    public TradingRecord executeWithParams(TimeSeries series, AbstractStrategyInputParams params, Strategy strategy) {
        Strategy s = buildStrategyWithParams(series, (StrategyInputParamsQuadCCI) params);
        // Running the strategy
        TimeSeriesManager seriesManager = new TimeSeriesManager(series);
        TradingRecord tradingRecord = seriesManager.run(s);
        System.out.println("Number of trades for the strategy: " + tradingRecord.getTradeCount());
        // Analysis
        System.out.println("Total profit for the strategy: " + new TotalProfitCriterion().calculate(series, tradingRecord));
        return tradingRecord;
    }
}
