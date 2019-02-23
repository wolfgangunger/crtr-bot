/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author UNGERW
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddOrderInfo {

    private int rsi;
    private double sto;
    private double closedPriceStrenth;
    private double rsiSrength;
    private double stoStrength;
    private double rsiSrength1;
    private double stoStrength1;
    private double sma3;
    private double sma8;
    private double sma50;
    private double sma200;
    private double sma314;
    private double ema14;
    private double ema50;
    private boolean priceAboveSma200;
    private boolean priceAboveSma3141;
    private boolean isSMALongTimeBullish;

}
