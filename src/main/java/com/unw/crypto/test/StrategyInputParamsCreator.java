/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.test;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.strategy.to.AbstractStrategyInputParams;
import com.unw.crypto.strategy.to.EntryRuleChain;
import com.unw.crypto.strategy.to.ExitRuleChain;
import com.unw.crypto.strategy.to.StrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParamsBuilder;
import com.unw.crypto.strategy.to.StrategyInputParamsQuadCCI;

/**
 *
 * @author UNGERW
 */
public final class StrategyInputParamsCreator {

    private StrategyInputParamsCreator() {
    }

    public static AbstractStrategyInputParams createStrategyInputParams(int variant, BarDuration barDuration) {
        switch (variant) {
            case 1:
                //return createStrategyInputParams1(barDuration);
                return createStrategyInputParams1(barDuration);
//                return createStrategyInputParamsQuadCCI1(barDuration);
            //return createStrategyInputParams23_1(barDuration);
            case 2:
                //return createStrategyInputParams2(barDuration);
                return createStrategyInputParams1Alt1(barDuration);
            //return createStrategyInputParams9Alt1(barDuration);
//                return createStrategyInputParamsQuadCCI1Alt1(barDuration);
            //return createStrategyInputParams23_2(barDuration);
            case 3:
                //return createStrategyInputParams3(barDuration);
                return createStrategyInputParams1Alt2(barDuration);
            // return createStrategyInputParams9Alt2(barDuration);
//                return createStrategyInputParamsQuadCCI1Alt2(barDuration);
            // return createStrategyInputParams23_3(barDuration);
            case 4:
                //return createStrategyInputParams4(barDuration);
                return createStrategyInputParams5Alt1(barDuration);
            // return createStrategyInputParams9Alt3(barDuration);
//                return createStrategyInputParamsQuadCCI1Alt3(barDuration);
//                return createStrategyInputParams23_4(barDuration);
            case 5:
//                return createStrategyInputParams5Alt2(barDuration);
                // return createStrategyInputParams9Alt4(barDuration);
//                return createStrategyInputParamsQuadCCI1Alt4(barDuration);
                return createStrategyInputParams5Alt2(barDuration);
            case 6:
//                return createStrategyInputParams5Alt3(barDuration);
                //return createStrategyInputParams9Alt5(barDuration);
//                return createStrategyInputParamsQuadCCI1Alt5(barDuration);
                return createStrategyInputParams5Alt3(barDuration);
            // short
            case -1:
                return createStrategyInputParams1Short(barDuration);
            default:
                return null;
        }
    }

    /**
     * configuration 1
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams1(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.6d;
        double fallingStrenght = 0.6d;
        double stopLoss = 2;
        double trailingStopLoss = 10;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 1
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams1Alt1(BarDuration barDuration) {
        // alteration 1 : rising + falling 0.5
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;

        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 10;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 1
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams1Alt2(BarDuration barDuration) {
        // alteration 2 
        // rsi + sto thresholds lower
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;

        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.6d;
        double fallingStrenght = 0.6d;
        double stopLoss = 2;
        double trailingStopLoss = 10;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams1Alt3(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell sto time frame 1
        // rsi -> down, sto -> down, 8ma down --- 

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 1;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;

        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.6d;
        double fallingStrenght = 0.6d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = false;
        boolean rule2_stoHigh = false;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = false;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 1
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams1Alt5(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.6d;
        double fallingStrenght = 0.6d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = true;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = false;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams1Alt6(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.6d;
        double fallingStrenght = 0.6d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = true;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams2(BarDuration barDuration) {
        // config 2: with trailing stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down, trailing stop loss

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;

        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.6d;
        double fallingStrenght = 0.6d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams3(BarDuration barDuration) {

        // config 3: no stop loss
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down
        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = false;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams4(BarDuration barDuration) {

        // config 4: with trailing loss
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down
        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams5(BarDuration barDuration) {

        // config 5: no stop loss
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up, rsi -> down, sto->down
        //sell
        // rsi high, sto high, 8ma down
        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdHigh = 70;
        int rsiThresholdLow = 30;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams5Alt1(BarDuration barDuration) {

        // config 5: no stop loss
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up, rsi -> down, sto->down + price below8MA
        //sell
        // rsi high, sto high, 8ma down
        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdHigh = 70;
        int rsiThresholdLow = 30;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 10;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = true;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams5Alt2(BarDuration barDuration) {

        // config 5: no stop loss
        // time frames rsi 2 ; sto 4
        // buy thresholds 20 + 80
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up, rsi -> down, sto->down 
        //sell
        // rsi high, sto high, 8ma down
        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdHigh = 80;
        int rsiThresholdLow = 20;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 10;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams5Alt3(BarDuration barDuration) {

        // config 5: with tr. stop loss
        // time frames rsi 2 ; sto 4
        // buy thresholds 20 + 80
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up, rsi -> down, sto->down 
        //sell
        // rsi high, sto high, 8ma down
        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdHigh = 80;
        int rsiThresholdLow = 20;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 10;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams6(BarDuration barDuration) {

        // config 6: with trailing stop loss
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up, rsi -> down, sto->down
        //sell
        // rsi high, sto high, 8ma down
        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 1
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams7(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 4 ; sto 8
        // buy
        // rsi low, sto low, rsi pointing up
        //sell
        // rsi high, sto high, rsi-> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeBuy = 8;
        int rsiStoTimeframeSell = 8;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = false;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = false;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = false;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = false;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams7Alt1(BarDuration barDuration) {
        // config Alt1: w tr. stop loss 6
        // time frames rsi 4 ; sto 8
        // buy
        // rsi low, sto low, rsi pointing up
        //sell
        // rsi high, sto high, rsi-> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeBuy = 8;
        int rsiStoTimeframeSell = 8;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = false;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = false;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = false;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams7Alt2(BarDuration barDuration) {
        // config 1: tr stop loss
        // time frames rsi 4 ; sto 8
        // buy
        // rsi low, sto low, rsi pointing up, above sma200
        //sell
        // rsi high, sto high, rsi-> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeBuy = 8;
        int rsiStoTimeframeSell = 8;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = false;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = false;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = false;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams7Alt3(BarDuration barDuration) {
        // config 1: tr stop loss 6
        // time frames rsi 4 ; sto 8
        // buy
        // rsi low, sto low, rsi pointing up, above sma200, 8MA pointing up
        //sell
        // rsi high, sto high, rsi-> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeBuy = 8;
        int rsiStoTimeframeSell = 8;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = false;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = false;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = false;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams7Alt4(BarDuration barDuration) {
        // config 1: tr stop loss 6
        // time frames rsi 4 ; sto 8
        // buy
        // rsi low, sto low, rsi pointing up, above sma200, 7EMA bands pointing up
        //sell
        // rsi high, sto high, rsi-> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeBuy = 8;
        int rsiStoTimeframeSell = 8;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = false;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = false;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = false;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams7Alt5(BarDuration barDuration) {
        // config 1: tr stop loss
        // time frames rsi 4 ; sto 8
        // buy
        // rsi low, sto low, rsi pointing up, above sma200, 8MA pointing up, 7 EMA bands up
        //sell
        // rsi high, sto high, rsi-> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeBuy = 8;
        int rsiStoTimeframeSell = 8;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = false;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams8(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up
        //sell
        // rsi high, sto high, rsi + sot -> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = false;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams8Alt1(BarDuration barDuration) {
        // config 8 :trai stop loss
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up, 8ma up, 7ema up
        //sell
        // rsi high, sto high, rsi + sot -> down, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams8Alt2(BarDuration barDuration) {
        // config 8 :trai stop loss
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up, 8ma up, 7ema up, above ma200
        //sell
        // rsi high, sto high, rsi + sot -> down, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams9(BarDuration barDuration) {
        // config 1: trailing stop loss 8
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up
        //sell
        // rsi high, sto high, rsi + sot -> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 0.24d;
        double stoThresholdHigh = 0.76d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 8;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams9Alt1(BarDuration barDuration) {
        // config 1: trailing stop loss 8
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up + above ma200
        //sell
        // rsi high, sto high, rsi + sot -> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 0.24d;
        double stoThresholdHigh = 0.76d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 8;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams9Alt2(BarDuration barDuration) {
        // config 1: trailing stop loss 8
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up
        //sell
        // rsi high, sto high, rsi + sot -> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 28;
        int rsiThresholdHigh = 72;
        double stoThresholdLow = 0.28d;
        double stoThresholdHigh = 0.72d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 8;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams9Alt3(BarDuration barDuration) {
        // config 1: trailing stop loss 8
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up + above ma200
        //sell
        // rsi high, sto high, rsi + sot -> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 28;
        int rsiThresholdHigh = 72;
        double stoThresholdLow = 0.28d;
        double stoThresholdHigh = 0.72d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 8;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams9Alt4(BarDuration barDuration) {
        // config 1: trailing stop loss 8
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up
        //sell
        // rsi high, sto high, rsi + sot -> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 8;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    /**
     * configuration 8
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams9Alt5(BarDuration barDuration) {
        // config 1: trailing stop loss 8
        // time frames rsi 2 ; sto 4
        // buy
        // rsi low, sto low, rsi+ sto pointing up + above ma200
        //sell
        // rsi high, sto high, rsi + sot -> down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 8;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams19_1(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams19_2(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams19_3(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeBuy = 6;
        int rsiStoTimeframeSell = 6;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 25;
        int rsiThresholdHigh = 75;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams19_4(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeBuy = 8;
        int rsiStoTimeframeSell = 8;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 25;
        int rsiThresholdHigh = 75;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams19_5(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 6;
        int rsiTimeframeSell = 6;
        int rsiStoTimeframeBuy = 10;
        int rsiStoTimeframeSell = 10;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 25;
        int rsiThresholdHigh = 75;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams19_6(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 6;
        int rsiTimeframeSell = 6;
        int rsiStoTimeframeBuy = 12;
        int rsiStoTimeframeSell = 12;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 25;
        int rsiThresholdHigh = 75;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    ////
    private static StrategyInputParams createStrategyInputParams23_1(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    ////
    private static StrategyInputParams createStrategyInputParams23_2(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.7d;
        double fallingStrenght = 0.7d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    ////
    private static StrategyInputParams createStrategyInputParams23_3(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 25;
        int rsiThresholdHigh = 75;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    ////
    private static StrategyInputParams createStrategyInputParams23_4(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 15;
        int rsiThresholdHigh = 85;
        double stoThresholdLow = 0.15d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    ////
    private static StrategyInputParams createStrategyInputParams23_5(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 10;
        int rsiThresholdHigh = 90;
        double stoThresholdLow = 0.1d;
        double stoThresholdHigh = 0.9d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }
    ////

    private static StrategyInputParams createStrategyInputParams23_6(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 40;
        int ma14 = 70;
        int ma200 = 1000;
        int ma314 = 1600;
        int smaShort = 15;
        int smaLong = 50;
        int emaShort = 25;
        int emaLong = 60;
        int rsiTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.8d;
        double fallingStrenght = 0.8d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = true;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    ////////////////// short strategies
    /**
     * configuration 1
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams1Short(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 1;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 1;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;

        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.6d;
        double fallingStrenght = 0.6d;
        double stopLoss = 2;
        double trailingStopLoss = 5;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = false;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams37_1(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 20d;
        double stoThresholdHigh = 80d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams37_2(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 24d;
        double stoThresholdHigh = 76d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams37_3(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 28;
        int rsiThresholdHigh = 72;
        double stoThresholdLow = 28d;
        double stoThresholdHigh = 72d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams37_4(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 32;
        int rsiThresholdHigh = 68;
        double stoThresholdLow = 32d;
        double stoThresholdHigh = 68d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams37_5(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 22;
        int rsiThresholdHigh = 78;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams37_6(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = true;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    ///
    private static StrategyInputParams createStrategyInputParams39_1(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 24d;
        double stoThresholdHigh = 76d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }
    ///

    private static StrategyInputParams createStrategyInputParams39_2(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 28;
        int rsiThresholdHigh = 72;
        double stoThresholdLow = 28d;
        double stoThresholdHigh = 72d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams39_3(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 26;
        int rsiThresholdHigh = 74;
        double stoThresholdLow = 26d;
        double stoThresholdHigh = 74d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams39_4(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams39_5(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 26;
        int rsiThresholdHigh = 74;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams39_6(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 28;
        int rsiThresholdHigh = 72;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams41_1(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams41_2(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 28d;
        double stoThresholdHigh = 72d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams41_3(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 26d;
        double stoThresholdHigh = 74d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams41_4(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 8;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams41_5(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = false;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams41_6(BarDuration barDuration) {

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 24;
        int rsiThresholdHigh = 76;
        double stoThresholdLow = 30d;
        double stoThresholdHigh = 70d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 7;
        double stopGain = 5d;
        int waitBars = 35;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = true;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = true;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    //// quad cci
    private static StrategyInputParamsQuadCCI createStrategyInputParamsQuadCCI1(BarDuration barDuration) {
        StrategyInputParamsQuadCCI result = StrategyInputParamsQuadCCI.builder().cci14(14).cci50(50).cci100(100).cci200(200)
                .cci200ThresholdBuy(0).cci100ThresholdBuy(-25).cci50ThresholdBuy(-50).cci14ThresholdBuy(-100).cci14ThresholdSell(100).cci50ThresholdSell(100)
                .stopLoss(6).trStopLoss(2).stopLossActive(false).trStopLossActive(true).cci14SellActive(false).cci50SellActive(false).build();
        return result;
    }

    private static StrategyInputParamsQuadCCI createStrategyInputParamsQuadCCI1Alt1(BarDuration barDuration) {
        StrategyInputParamsQuadCCI result = StrategyInputParamsQuadCCI.builder().cci14(14).cci50(50).cci100(100).cci200(200)
                .cci200ThresholdBuy(0).cci100ThresholdBuy(-25).cci50ThresholdBuy(-50).cci14ThresholdBuy(-100).cci14ThresholdSell(100).cci50ThresholdSell(100)
                .stopLoss(6).trStopLoss(4).stopLossActive(false).trStopLossActive(true).cci14SellActive(false).cci50SellActive(false).build();
        return result;
    }

    private static StrategyInputParamsQuadCCI createStrategyInputParamsQuadCCI1Alt2(BarDuration barDuration) {
        StrategyInputParamsQuadCCI result = StrategyInputParamsQuadCCI.builder().cci14(14).cci50(50).cci100(100).cci200(200)
                .cci200ThresholdBuy(0).cci100ThresholdBuy(-25).cci50ThresholdBuy(-50).cci14ThresholdBuy(-100).cci14ThresholdSell(100).cci50ThresholdSell(100)
                .stopLoss(6).trStopLoss(6).stopLossActive(false).trStopLossActive(true).cci14SellActive(false).cci50SellActive(false).build();
        return result;
    }

    private static StrategyInputParamsQuadCCI createStrategyInputParamsQuadCCI1Alt3(BarDuration barDuration) {
        StrategyInputParamsQuadCCI result = StrategyInputParamsQuadCCI.builder().cci14(14).cci50(50).cci100(100).cci200(200)
                .cci200ThresholdBuy(0).cci100ThresholdBuy(-25).cci50ThresholdBuy(-50).cci14ThresholdBuy(-100).cci14ThresholdSell(100).cci50ThresholdSell(100)
                .stopLoss(6).trStopLoss(8).stopLossActive(false).trStopLossActive(true).cci14SellActive(false).cci50SellActive(false).build();
        return result;
    }

    // double time frame
    private static StrategyInputParamsQuadCCI createStrategyInputParamsQuadCCI1Alt4(BarDuration barDuration) {
        StrategyInputParamsQuadCCI result = StrategyInputParamsQuadCCI.builder().cci14(28).cci50(100).cci100(200).cci200(400)
                .cci200ThresholdBuy(0).cci100ThresholdBuy(0).cci50ThresholdBuy(-20).cci14ThresholdBuy(-100).cci14ThresholdSell(100).cci50ThresholdSell(100)
                .stopLoss(5).trStopLoss(5).stopLossActive(false).trStopLossActive(true).build();
        return result;
    }

    private static StrategyInputParamsQuadCCI createStrategyInputParamsQuadCCI1Alt5(BarDuration barDuration) {
        StrategyInputParamsQuadCCI result = StrategyInputParamsQuadCCI.builder().cci14(28).cci50(100).cci100(200).cci200(400)
                .cci200ThresholdBuy(100).cci100ThresholdBuy(100).cci50ThresholdBuy(0).cci14ThresholdBuy(-100).cci14ThresholdSell(100).cci50ThresholdSell(100)
                .stopLoss(5).trStopLoss(5).stopLossActive(false).trStopLossActive(true).build();
        return result;

    }
    //

    /**
     * configuration 1
     *
     * @param barDuration
     * @return
     */
    private static StrategyInputParams createStrategyInputParams28_1(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams28_2(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams28_3(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 4;
        int rsiStoTimeframeBuy = 4;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeSell = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams28_4(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 4;
        int rsiStoTimeframeBuy = 6;
        int rsiTimeframeSell = 4;
        int rsiStoTimeframeSell = 6;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = -1d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = false;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams30_1(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 30;
        int rsiThresholdHigh = 70;
        double stoThresholdLow = 0.3d;
        double stoThresholdHigh = 0.7d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = true;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams30_2(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 35;
        int rsiThresholdHigh = 65;
        double stoThresholdLow = 0.35d;
        double stoThresholdHigh = 0.65d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = true;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams30_3(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 25;
        int rsiThresholdHigh = 75;
        double stoThresholdLow = 0.25d;
        double stoThresholdHigh = 0.75d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = true;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams30_4(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 20;
        int rsiThresholdHigh = 80;
        double stoThresholdLow = 0.2d;
        double stoThresholdHigh = 0.8d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = true;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

    private static StrategyInputParams createStrategyInputParams30_5(BarDuration barDuration) {
        // config 1: no stop loss
        // time frames rsi 1 ; sto 2
        // buy
        // rsi low, sto low, above sma200, 8ma pointin up, ema bands up
        //sell
        // rsi high, sto high, 8ma down

        StrategyInputParams result;
        boolean barMultiplikator = false;
        boolean extraMultiplikator = false;
        float extraMultiplikatorValue = 1f;
        int ma8 = 8;
        int ma14 = 14;
        int ma200 = 200;
        int ma314 = 314;
        int smaShort = 3;
        int smaLong = 10;
        int emaShort = 5;
        int emaLong = 12;
        int rsiTimeframeBuy = 2;
        int rsiStoTimeframeBuy = 2;
        int rsiTimeframeSell = 2;
        int rsiStoTimeframeSell = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrameBuy = 1;
        int priceTimeFrameSell = 1;
        int rsiThresholdLow = 15;
        int rsiThresholdHigh = 85;
        double stoThresholdLow = 0.15d;
        double stoThresholdHigh = 0.85d;
        int stoOscKThresholdLow = 20;
        int stoOscKThresholdHigh = 80;
        double risingStrenght = 0.5d;
        double fallingStrenght = 0.5d;
        double stopLoss = 2;
        double trailingStopLoss = 6;
        double stopGain = 5d;
        int waitBars = 50;

        //entry rules
        boolean rule1_rsiLow = true;
        boolean rule2_stoLow = true;
        boolean rule3_priceAboveSMA200 = true;
        boolean rule3b_priceAboveSMA314 = false;
        boolean rule4_ma8PointingUp = true;
        boolean rule5_priceBelow8MA = false;
        boolean rule7_emaBandsPointingUp = true;
        boolean rule11_isRsiPointingUp = false;
        boolean rule12_isStoPointingUp = false;
        boolean rule13_movingMomentum = false;

        //exit rules
        boolean rule1_rsiHigh = true;
        boolean rule2_stoHigh = true;
        boolean rule3_8maDown = true;
        boolean rule11_rsiPointingDown = false;
        boolean rule12_StoPointingDown = false;
        boolean rule21_priceFalling = false;
        boolean rule22_stopLoss = false;
        boolean rule22b_trailingStopLoss = true;
        boolean rule23_stopGain = true;
        boolean rule24_macdFalling = false;
        boolean rule25_shortEmaFalling = false;

        EntryRuleChain entryruleChain = EntryRuleChain.builder().rule1_rsiLow(rule1_rsiLow).rule2_stoLow(rule2_stoLow).
                rule3_priceAboveSMA200(rule3_priceAboveSMA200).rule3b_priceAboveSMA314(rule3b_priceAboveSMA314).
                rule4_ma8PointingUp(rule4_ma8PointingUp).rule5_priceBelow8MA(rule5_priceBelow8MA).rule7_emaBandsPointingUp(rule7_emaBandsPointingUp)
                .rule11_isRsiPointingUp(rule11_isRsiPointingUp).rule12_isStoPointingUp(rule12_isStoPointingUp).rule13_movingMomentum(rule13_movingMomentum).build();
        ExitRuleChain exitRuleChain = ExitRuleChain.builder().rule1_rsiHigh(rule1_rsiHigh).rule2_stoHigh(rule2_stoHigh)
                .rule3_8maDown(rule3_8maDown).rule11_rsiPointingDown(rule11_rsiPointingDown)
                .rule12_StoPointingDown(rule12_StoPointingDown).rule21_priceFalling(rule21_priceFalling)
                .rule23_stopGain(rule23_stopGain).rule22_stopLoss(rule22_stopLoss).rule22b_trailingStopLoss(rule22b_trailingStopLoss).build();
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8,
                ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframeBuy, rsiTimeframeSell,
                rsiStoTimeframeBuy, rsiStoTimeframeSell, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrameBuy,
                priceTimeFrameSell, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }
}
