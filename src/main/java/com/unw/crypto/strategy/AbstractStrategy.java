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
 * super class for strategies
 * @author UNGERW
 */
public abstract class AbstractStrategy {

    protected int iMAShort = 12;
    protected int iMALong = 26;

    public abstract Strategy buildStrategy(TimeSeries series, BarDuration barDuration);

    public abstract TradingRecord execute(TimeSeries series, BarDuration barDuration);

    public int getiMAShort() {
        return iMAShort;
    }

    public void setiMAShort(int iMAShort) {
        this.iMAShort = iMAShort;
    }

    public int getiMALong() {
        return iMALong;
    }

    public void setiMALong(int iMALong) {
        this.iMALong = iMALong;
    }

}
