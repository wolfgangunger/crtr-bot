/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.chart.BarUtil;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author UNGERW
 */
@Builder
//@Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StrategyInputParams {

    // to be discussed: should timeframe values be set in days ( and multiplicated inside this cointainer ) or 
    // set with the timeframe value ( days * bar multiplicator) from outside 
    /// todo : timeframe values must be calculated with factor of barsize
    //  smaLong = smaLon * BarDurationUtil.getMAMultiplicator
    // bar / candle size
    private BarDuration barDuration;

    private boolean barMultiplikator;
    private boolean extraMultiplikator;
    private float extraMultiplikatorValue;

    //entry and exitRuleChain
    private EntryRuleChain entryRuleChain;
    private ExitRuleChain exitRuleChain;

    /// indicators
    // simple MA indays
    private int smaShort;
    private int smaLong;
    private int sma8 = 8;
    private int sma14 = 14;
    private int sma200 = 200;
    private int sma314 = 314;
    // expotential MA in days
    private int emaShort;
    private int emaLong;

    // RSI
    private int rsiTimeframe;
    //StochasticRSIIndicator
    private int stoRsiTimeframe;
    //StochasticOscillatorKIndicator
    private int stoOscKTimeFrame;
    //SMAIndicator
    private int smaIndicatorTimeframe;
    //EMAIndicator
    private int emaIndicatorTimeframe;
    // for closed price e.g. isFalling
    private int priceTimeFrame;

    /// rules
    // rsi 
    private int rsiThresholdLow;
    private int rsiThresholdHigh;
    // sto
    private double stoThresholdLow;
    private double stoThresholdHigh;
    //stochasticOscillK
    private int stoOscKThresholdLow;
    private int stoOscKThresholdHigh;
    // the strength for the is falling and is rising rules
    private double fallingStrenght;
    private double risingStrenght;
    // stopp loss
    private double stopLoss;
    // stop gain
    private double stopGain;
    // wait rule
    private int waitBars;

    private float getMAMultiplicator() {
        float tmp = 1;
        if (barMultiplikator) {
            tmp = (float) BarUtil.getMAMultiplicator(barDuration);
        }
        if (extraMultiplikator) {
            tmp = tmp * extraMultiplikatorValue;
        }
        return tmp;
    }

    public BarDuration getBarDuration() {
        return barDuration;
    }

    public EntryRuleChain getEntryRuleChain() {
        return entryRuleChain;
    }

    public ExitRuleChain getExitRuleChain() {
        return exitRuleChain;
    }

    public int getSmaShort() {
        return (int)(smaShort * getMAMultiplicator());
    }

    public int getSmaLong() {
        return (int)(smaLong * getMAMultiplicator());
    }

    public int getSma8() {
        return (int)(sma8 * getMAMultiplicator());
    }

    public int getSma14() {
        return (int)(sma14 * getMAMultiplicator());
    }

    public int getSma200() {
        return (int)(sma200 * getMAMultiplicator());
    }

    public int getSma314() {
        return (int)(sma314 * getMAMultiplicator());
    }

    public int getEmaShort() {
        return (int)(emaShort * getMAMultiplicator());
    }

    public int getEmaLong() {
        return (int)(emaLong * getMAMultiplicator());
    }

    public int getRsiTimeframe() {
        return (int)(rsiTimeframe * getMAMultiplicator());
    }

    public int getStoRsiTimeframe() {
        return (int)(stoRsiTimeframe * getMAMultiplicator());
    }

    public int getStoOscKTimeFrame() {
        return (int)(stoOscKTimeFrame * getMAMultiplicator());
    }

    public int getEmaIndicatorTimeframe() {
        return (int)(emaIndicatorTimeframe * getMAMultiplicator());
    }

    public int getSmaIndicatorTimeframe() {
        return (int)(smaIndicatorTimeframe * getMAMultiplicator());
    }

    public int getPriceTimeFrame() {
        return priceTimeFrame;
    }

    public int getRsiThresholdLow() {
        return rsiThresholdLow;
    }

    public int getRsiThresholdHigh() {
        return rsiThresholdHigh;
    }

    public double getStoThresholdLow() {
        return stoThresholdLow;
    }

    public double getStoThresholdHigh() {
        return stoThresholdHigh;
    }

    public int getStoOscKThresholdLow() {
        return stoOscKThresholdLow;
    }

    public int getStoOscKThresholdHigh() {
        return stoOscKThresholdHigh;
    }

    public double getStopLoss() {
        return stopLoss;
    }

    public double getStopGain() {
        return stopGain;
    }

    public int getWaitBars() {
        return waitBars;
    }

    public double getFallingStrenght() {
        return fallingStrenght;
    }

    public double getRisingStrenght() {
        return risingStrenght;
    }
    
    

}
