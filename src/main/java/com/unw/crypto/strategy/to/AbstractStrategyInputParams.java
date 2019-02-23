/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;

import com.unw.crypto.model.BarDuration;
import lombok.Data;

/**
 *
 * @author UNGERW
 */
@Data
public abstract class AbstractStrategyInputParams {

    protected BarDuration barDuration;
    protected boolean barMultiplikator;
    protected boolean extraMultiplikator;
    protected float extraMultiplikatorValue;

}
