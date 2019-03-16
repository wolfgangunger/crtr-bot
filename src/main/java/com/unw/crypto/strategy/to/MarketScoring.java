/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;


import javafx.util.Pair;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author UNGERW
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarketScoring {

    // the calculated score
    private int score;
    
    private Pair pair;
    // analyze for bull or bear market?
    private boolean bullish;

    // params
    private boolean maBullish;
    private boolean cciBullish;
    private boolean maBearish;
    private boolean cciBearish;
    private double ma8;
    private double ma14;
    private double ma50;
    private double ma100;
    private double ma200;

    private double cci14;
    private double cci50;
    private double cci100;
    private double cci200;

    private double rsi;
    private double rsiStrenght;
    private double sto;
    private double stoStrength;

}
