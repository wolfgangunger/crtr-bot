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

    private int cci14ThresholdBuy;
    private int cci50ThresholdBuy;
    private int cci100ThresholdBuy;
    private int cci200ThresholdBuy;

    private int cci14ThresholdSell;
    private int cci50ThresholdSell;

    @Builder.Default
    private int cci14FallingTimeframe = 1;
    @Builder.Default
    private int cci50FallingTimeframe = 1;
    @Builder.Default    
    private double fallingStrenght = 0.5d;

    private double stopLoss;
    private double trStopLoss;

    private boolean stopLossActive;
    private boolean trStopLossActive;

}
