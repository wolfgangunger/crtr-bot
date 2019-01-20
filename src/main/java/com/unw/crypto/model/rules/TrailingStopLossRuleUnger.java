/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.model.rules;

import org.ta4j.core.Trade;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.indicators.helpers.ClosePriceIndicator;
import org.ta4j.core.num.Num;
import org.ta4j.core.trading.rules.AbstractRule;

/**
 * A trailing stop-loss rule
 * <p>
 * </p>
 * Satisfied when the close price reaches the trailing loss threshold.
 */
public class TrailingStopLossRuleUnger extends AbstractRule {

    /**
     * The close price indicator
     */
    private ClosePriceIndicator closePrice;
    /**
     * the loss-distance as percentage
     */
    private  Num lossPercentage;
    /**
     * the current price extremum
     */
    private Num currentExtremum = null;
    /**
     * the current threshold
     */
    private Num threshold = null;
    /**
     * the current trade
     */
    private Trade supervisedTrade;

    /**
     * Constructor.
     *
     * @param closePrice the close price indicator
     * @param lossPercentage the loss percentage
     */
    public TrailingStopLossRuleUnger(ClosePriceIndicator closePrice, Num lossPercentage) {
        this.closePrice = closePrice;
        this.lossPercentage = lossPercentage;
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
                if (!currentTrade.equals(supervisedTrade)) {
                    supervisedTrade = currentTrade;
                    currentExtremum = null;
                    threshold = null;
                }
                Num currentPrice = closePrice.getValue(index);
                if (currentTrade.getEntry().isBuy()) {
                    satisfied = isBuySatisfied(currentPrice);
                } else {
                    satisfied = isSellSatisfied(currentPrice);
                }
            }
        }
        traceIsSatisfied(index, satisfied);
        return satisfied;
    }

    private boolean isBuySatisfied(Num currentPrice) {
        boolean satisfied = false;
        if (currentExtremum == null) {
            currentExtremum = currentPrice.numOf(Float.MIN_VALUE);
        }
        if (currentPrice.isGreaterThan(currentExtremum)) {
            currentExtremum = currentPrice;
            Num lossRatioThreshold = currentPrice.numOf(100).minus(lossPercentage).dividedBy(currentPrice.numOf(100));
            threshold = currentExtremum.multipliedBy(lossRatioThreshold);
        }
        if (threshold != null) {
            satisfied = currentPrice.isLessThanOrEqual(threshold);
        }
        return satisfied;
    }

    private boolean isSellSatisfied(Num currentPrice) {
        boolean satisfied = false;
        if (currentExtremum == null) {
            currentExtremum = currentPrice.numOf(Float.MAX_VALUE);
        }
        if (currentPrice.isLessThan(currentExtremum)) {
            currentExtremum = currentPrice;
            Num lossRatioThreshold = currentPrice.numOf(100).plus(lossPercentage).dividedBy(currentPrice.numOf(100));
            threshold = currentExtremum.multipliedBy(lossRatioThreshold);
        }
        if (threshold != null) {
            satisfied = currentPrice.isGreaterThanOrEqual(threshold);
        }
        return satisfied;
    }

    public ClosePriceIndicator getClosePrice() {
        return closePrice;
    }

    public void setClosePrice(ClosePriceIndicator closePrice) {
        this.closePrice = closePrice;
    }

}
