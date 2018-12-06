/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import org.ta4j.core.Strategy;
import org.ta4j.core.TimeSeries;

/**
 *
 * @author UNGERW
 */
public abstract class AbstractStrategy {

    public abstract Strategy buildStrategy(TimeSeries series);

}
