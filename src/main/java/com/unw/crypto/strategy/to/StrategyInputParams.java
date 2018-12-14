/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.strategy.BarDurationUtil;
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
    
    private RuleChain ruleChain;

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
    //EMAIndicator
    private int emaIndicatorTimeframe;
    
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
    // stopp loss
    private double stopLoss;
    // stop gain
    private double stopGain;
    // wait rule
    private int waitBars;

    private int getMAMultiplicator(){
        return BarDurationUtil.getMAMultiplicator(barDuration);
    }
    public BarDuration getBarDuration() {
        return barDuration;
    }

    public RuleChain getRuleChain() {
        return ruleChain;
    }

    public int getSmaShort() {
        return smaShort * getMAMultiplicator();
    }

    public int getSmaLong() {
        return smaLong * getMAMultiplicator();
    }

    public int getSma8() {
        return sma8 * getMAMultiplicator();
    }

    public int getSma14() {
        return sma14  * getMAMultiplicator();
    }

    public int getSma200() {
        return sma200 * getMAMultiplicator();
    }

    public int getSma314() {
        return sma314 * getMAMultiplicator();
    }

    public int getEmaShort() {
        return emaShort * getMAMultiplicator();
    }

    public int getEmaLong() {
        return emaLong * getMAMultiplicator();
    }

    public int getRsiTimeframe() {
        return rsiTimeframe * getMAMultiplicator();
    }

    public int getStoRsiTimeframe() {
        return stoRsiTimeframe * getMAMultiplicator();
    }

    public int getStoOscKTimeFrame() {
        return stoOscKTimeFrame * getMAMultiplicator();
    }

    public int getEmaIndicatorTimeframe() {
        return emaIndicatorTimeframe * getMAMultiplicator();
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
    
    
    
}