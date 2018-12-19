/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;

import com.unw.crypto.model.BarDuration;

/**
 *
 * @author UNGERW
 */
public final class StrategyInputParamsBuilder {

    private StrategyInputParamsBuilder() {
    }

    public static StrategyInputParams createStrategyInputParams(BarDuration barDuration, boolean barMultiplikator, boolean extraMultiplikator, float extraMultiplikatorValue, int ma8, int ma14, int ma200, int ma314, int smaShort, int smaLong, int emaShort, int emaLong,
            int rsiTimeFrame, int stoRsiTimeframe, int stoOscKTimeFrame, int emaIndicatorTimeframe, int smaIndicatorTimeframe, int rsiThresholdLow, int rsiThresholdHigh,
            double stoThresholdLow, double stoThresholdHigh, int stoOscKThresholdLow, int stoOscKThresholdHigh,
            double stopLoss, double stopGain, int waitBars, EntryRuleChain entryRuleChain, ExitRuleChain exitRuleChain) {
        StrategyInputParams result = new StrategyInputParams();
        result.setBarMultiplikator(barMultiplikator);
        result.setExtraMultiplikator(extraMultiplikator);
        result.setExtraMultiplikatorValue(extraMultiplikatorValue);
        result.setSma8(ma8);
        result.setSma14(ma14);
        result.setSma200(ma200);
        result.setSma314(ma314);
        result.setBarDuration(barDuration);
        result.setSmaShort(smaShort);
        result.setSmaLong(smaLong);
        result.setEmaShort(emaShort);
        result.setEmaLong(emaLong);
        result.setRsiTimeframe(rsiTimeFrame);
        result.setStoRsiTimeframe(stoRsiTimeframe);
        result.setStoOscKTimeFrame(stoOscKTimeFrame);
        result.setEmaIndicatorTimeframe(emaIndicatorTimeframe);
        result.setSmaIndicatorTimeframe(smaIndicatorTimeframe);
        result.setRsiThresholdLow(rsiThresholdLow);
        result.setRsiThresholdHigh(rsiThresholdHigh);
        result.setStoThresholdLow(stoThresholdLow);
        result.setStoThresholdHigh(stoThresholdHigh);
        result.setStoOscKThresholdLow(stoOscKThresholdLow);
        result.setStoOscKThresholdHigh(stoOscKThresholdHigh);
        result.setStopLoss(stopLoss);
        result.setStopGain(stopGain);
        result.setWaitBars(waitBars);
        result.setEntryRuleChain(entryRuleChain);
        result.setExitRuleChain(exitRuleChain);
        return result;
    }

}
