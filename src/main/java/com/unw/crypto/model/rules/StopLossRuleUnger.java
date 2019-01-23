package com.unw.crypto.model.rules;

import org.ta4j.core.TimeSeries;
import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.AbstractRule;

/**
 * A stop-loss rule.
 * </p>
 * Satisfied when the close price reaches the loss threshold.
 */
public class StopLossRuleUnger extends AbstractRule {

    /**
     * The close price indicator
     */
    private ClosePriceIndicator closePrice;

    /**
     * The loss ratio threshold (e.g. 0.97 for 3%)
     */
    private Num lossRatioThreshold;

    private Num lossPercentage;

    /**
     * Constructor.
     *
     * @param closePrice the close price indicator
     * @param lossPercentage the loss percentage
     */
    public StopLossRuleUnger(ClosePriceIndicator closePrice, Number lossPercentage) {
        this(closePrice, closePrice.numOf(lossPercentage));
    }

    /**
     * Constructor.
     *
     * @param closePrice the close price indicator
     * @param lossPercentage the loss percentage
     */
    public StopLossRuleUnger(ClosePriceIndicator closePrice, Num lossPercentage) {
        this.closePrice = closePrice;
        this.lossPercentage = lossPercentage;
        if (closePrice != null) {
            TimeSeries series = closePrice.getTimeSeries();
            this.lossRatioThreshold = series.numOf(100).minus(lossPercentage).dividedBy(series.numOf(100));
        }
    }

    public void rebuildRule(ClosePriceIndicator closePrice, Num lossPercentage) {
        this.closePrice = closePrice;
        this.lossPercentage = lossPercentage;
    }

    @Override
    public boolean isSatisfied(int index, TradingRecord tradingRecord) {
        boolean satisfied = false;
        // No trading history or no trade opened, no loss
        if (tradingRecord != null) {
            Trade currentTrade = tradingRecord.getCurrentTrade();
            if (currentTrade.isOpened()) {
                Num entryPrice = currentTrade.getEntry().getPrice();
                Num currentPrice = closePrice.getValue(index);
                Num threshold = entryPrice.multipliedBy(lossRatioThreshold);
                if (currentTrade.getEntry().isBuy()) {
                    satisfied = currentPrice.isLessThanOrEqual(threshold);
                } else {
                    satisfied = currentPrice.isGreaterThanOrEqual(threshold);
                }
            }
        }
        traceIsSatisfied(index, satisfied);
        return satisfied;
    }
}
