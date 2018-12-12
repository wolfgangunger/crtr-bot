/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.model.BarDuration;
import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TradingRecord;

/**
 *
 * @author UNGERW
 */
public abstract class AbstractStrategy {

    public abstract Strategy buildStrategy(TimeSeries series, BarDuration barDuration);

     public abstract TradingRecord execute(TimeSeries series, BarDuration barDuration) ;
}
