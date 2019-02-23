/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;

import lombok.Builder;
import lombok.Data;

/**
 *
 * @author UNGERW
 */
@Data
@Builder
public class StrategyInputParamsQuadCCI extends AbstractStrategyInputParams {

    private int cci14;
    private int cci50;
    private int cci100;
    private int cci200;

    private int cci14Threshold;
    private int cci50Threshold;
    private int cci100Threshold;
    private int cci200Threshold;

}
