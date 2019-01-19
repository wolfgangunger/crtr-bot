/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.strategy.to.StrategyInputParams;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TradingRecord;

/**
 *
 * @author UNGERW
 */
public interface IFinalTradingStrategy {
    
         Strategy buildStrategyWithParams(TimeSeries series, StrategyInputParams params) ;
         
         TradingRecord executeWithParams(TimeSeries series, StrategyInputParams params, Strategy strategy) ;
    
}
