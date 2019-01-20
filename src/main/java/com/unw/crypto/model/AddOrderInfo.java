/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.model;

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
public class AddOrderInfo {

    private int rsi;
    private double sto;
    private double closedPriceStrenth;
    double sma3;
    double sma8;
    double sma50;
    double sma200;
    double sma314;
    double ema14;
    double ema50;
    boolean priceAboveSma200;
    boolean priceAboveSma3141;
    boolean isSMALongTimeBullish;

}
