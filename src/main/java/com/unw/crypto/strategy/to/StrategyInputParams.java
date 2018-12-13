/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;

import com.unw.crypto.model.BarDuration;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author UNGERW
 */
@Data
@NoArgsConstructor
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
    
    
    // todo : booleans for all rules - to be able to disable and enable the rules concatenation from outside ( brutforce ...)
}
