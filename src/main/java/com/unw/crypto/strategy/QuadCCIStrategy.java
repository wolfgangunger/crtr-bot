package com.unw.crypto.strategy;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.model.rules.StopLossRuleUnger;
import com.unw.crypto.model.rules.TrailingStopLossRuleUnger;
import com.unw.crypto.strategy.to.AbstractStrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParamsQuadCCI;
import org.springframework.stereotype.Component;
import org.ta4j.core.*;
import org.ta4j.core.analysis.criteria.TotalProfitCriterion;
import org.ta4j.core.indicators.CCIIndicator;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.DoubleNum;
import org.ta4j.core.trading.rules.IsFallingRule;
import org.ta4j.core.trading.rules.IsRisingRule;
import org.ta4j.core.trading.rules.OverIndicatorRule;
import org.ta4j.core.trading.rules.UnderIndicatorRule;

/**
 *
 * @author UNGERW
 */
@Component
public class QuadCCIStrategy extends AbstractStrategy implements IFinalTradingStrategy {

    private Rule exitRuleStopLoss = new StopLossRuleUnger(null, DoubleNum.valueOf("2"));
    private Rule exitRuleTrStopLoss = new TrailingStopLossRuleUnger(null, DoubleNum.valueOf("5"));

    /**
     * @param series a time series
     * @return a CCI correction strategy
     */
    public Strategy buildStrategy(TimeSeries series, BarDuration barDuration) {
        if (series == null) {
            throw new IllegalArgumentException("Series cannot be null");
        }
        StrategyInputParamsQuadCCI params = StrategyInputParamsQuadCCI.builder().cci100(100).cci200(200).cci50(50).cci14(14)
                .cci100ThresholdBuy(0).cci200ThresholdBuy(0).cci50ThresholdBuy(-100).cci14ThresholdBuy(-100).build();

        return buildStrategyWithParams(series, params);
    }

    public Strategy buildStrategyWithParams(TimeSeries series, AbstractStrategyInputParams p) {
        StrategyInputParamsQuadCCI params = (StrategyInputParamsQuadCCI) p;

        ClosePriceIndicator closePrice = new ClosePriceIndicator(series);

        CCIIndicator cci14 = new CCIIndicator(series, params.getCci14());
        CCIIndicator cci50 = new CCIIndicator(series, params.getCci50());
        CCIIndicator cci100 = new CCIIndicator(series, params.getCci100());
        CCIIndicator cci200 = new CCIIndicator(series, params.getCci200());
//        Num zero = DoubleNum.valueOf(BigDecimal.valueOf(0));
//        Num plus100 = DoubleNum.valueOf(BigDecimal.valueOf(100));
//        Num minus100 = DoubleNum.valueOf(BigDecimal.valueOf(- 100));

        //entry
        Rule entryRule1 = new OverIndicatorRule(cci200, params.getCci200ThresholdBuy()); // Bull trend
        Rule entryRule2 = new OverIndicatorRule(cci100, params.getCci100ThresholdBuy()); // Bull trend
        Rule entryRule3 = new OverIndicatorRule(cci50, params.getCci50ThresholdBuy()); // Bull trend

        Rule entryRule4 = new UnderIndicatorRule(cci14, params.getCci14ThresholdBuy()); // Signal
        Rule entryRule4b = new IsRisingRule(cci14, 1); // Signal

        Rule entryRule = entryRule1.and(entryRule2).and(entryRule3).and(entryRule4).and(entryRule4b);

        /// exit
        Rule exitRule1 = new OverIndicatorRule(cci14, params.getCci14ThresholdSell()); // Signal
        Rule exitRule1b = new IsFallingRule(cci14, 1, params.getFallingStrenght());
        Rule exitRule2 = new OverIndicatorRule(cci50, params.getCci50ThresholdSell()); // confim Signal
        Rule exitRule2b = new IsFallingRule(cci50, 1, params.getFallingStrenght());
        // stop loss
        ((StopLossRuleUnger) exitRuleStopLoss).rebuildRule(closePrice, DoubleNum.valueOf(params.getStopLoss()));
        ((TrailingStopLossRuleUnger) exitRuleTrStopLoss).rebuildRule(closePrice, DoubleNum.valueOf(params.getTrStopLoss()));

        //Rule exitRule = exitRule1.and(exitRule1b);
        Rule exitRule = exitRule1.and(exitRule2).and(exitRule2b);
        exitRule = buildCompleteExitRule(exitRule, params.isStopLossActive(), params.isTrStopLossActive(), exitRuleStopLoss, exitRuleTrStopLoss);
        
        Strategy strategy = new BaseStrategy(entryRule, exitRule);
        //strategy.setUnstablePeriod(5);
        return strategy;

    }

    private Rule buildCompleteExitRule(Rule result, boolean stopLoss, boolean trStopLoss, Rule sl, Rule trsl) {
        if (stopLoss) {
            result = result.or(sl);
        }
        if (trStopLoss) {
            result = result.or(trsl);
        }
        return result;
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
