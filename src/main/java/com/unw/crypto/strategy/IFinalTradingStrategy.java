/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.strategy.to.AbstractStrategyInputParams;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TradingRecord;

/**
 *
 * @author UNGERW
 */
public interface IFinalTradingStrategy {
    
         Strategy buildStrategyWithParams(TimeSeries series, AbstractStrategyInputParams params) ;
         
         TradingRecord executeWithParams(TimeSeries series, AbstractStrategyInputParams params, Strategy strategy) ;
    
}
