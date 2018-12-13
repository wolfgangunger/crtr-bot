/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author UNGERW
 */
@Data
@AllArgsConstructor
@Builder
public class RuleChain {

    //1- RSI is low and pointing up (v)
//2- Stochastic is low and pointing up (v)
//3- Price is above SMA200&314 ???? really ?
//4- 8-MA is pointing up (v)
//5- Price is near or below the 8-MA (v) (the further away from the 8-MA price is, the higher probability price will turn back towards it)
//6- Price is _above_ a known area of resistance (use Fib levels to determine those zones)
//7- Moving EMA bands are angled up
//8- Price is not approaching prior resistance
//9- Price is near the bottom of an identified cycle
//10- Still room to grow in larger time frames
    private boolean rule1_rsiLow;
    private boolean rule2_stoLow;
    private boolean rule3_priceAboveSMA200;
    private boolean rule4_ma8PointingUp;
    private boolean rule5_priceBelow8MA;
    private boolean rule7_emaBandsPointingUp;
}
