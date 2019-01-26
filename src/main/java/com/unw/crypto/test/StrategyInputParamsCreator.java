/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.test;

import com.unw.crypto.model.BarDuration;
import com.unw.crypto.strategy.to.EntryRuleChain;
import com.unw.crypto.strategy.to.ExitRuleChain;
import com.unw.crypto.strategy.to.StrategyInputParams;
import com.unw.crypto.strategy.to.StrategyInputParamsBuilder;

/**
 *
 * @author UNGERW
 */
public final class StrategyInputParamsCreator {

    private StrategyInputParamsCreator() {
    }

    public static StrategyInputParams createStrategyInputParams(int variant, BarDuration barDuration) {
        switch (variant) {
            case 1:
                return createStrategyInputParams1(barDuration);
            case 2:
                return createStrategyInputParams2(barDuration);
            case 3:
                return createStrategyInputParams3(barDuration);
            case 4:
                return createStrategyInputParams4(barDuration);
            case 5:
                return createStrategyInputParams5(barDuration);
            case 6:
                return createStrategyInputParams6(barDuration);
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
        int rsiTimeframe = 1;
        int rsiStoTimeframe = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrame = 1;
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
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8, ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframe,
                rsiStoTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrame, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
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
        int rsiTimeframe = 1;
        int rsiStoTimeframe = 2;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrame = 1;
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
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8, ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframe,
                rsiStoTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrame, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
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
        int rsiTimeframe = 2;
        int rsiStoTimeframe = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrame = 1;
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
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8, ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframe,
                rsiStoTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrame, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
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
        int rsiTimeframe = 2;
        int rsiStoTimeframe = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrame = 1;
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
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8, ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframe,
                rsiStoTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrame, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
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
        int rsiTimeframe = 2;
        int rsiStoTimeframe = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrame = 1;
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
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8, ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframe,
                rsiStoTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrame, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
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
        int rsiTimeframe = 2;
        int rsiStoTimeframe = 4;
        int stoOscKTimeFrame = 4;
        int emaIndicatorTimeframe = 4;
        int smaIndicatorTimeframe = 4;
        int priceTimeFrame = 1;
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
        result = StrategyInputParamsBuilder.createStrategyInputParams(barDuration, barMultiplikator, extraMultiplikator, extraMultiplikatorValue, ma8, ma14, ma200, ma314, smaShort, smaLong, emaShort, emaLong, rsiTimeframe,
                rsiStoTimeframe, stoOscKTimeFrame, emaIndicatorTimeframe, smaIndicatorTimeframe, priceTimeFrame, rsiThresholdLow, rsiThresholdHigh, stoThresholdLow, stoThresholdHigh,
                stoOscKThresholdLow, stoOscKThresholdHigh, risingStrenght, fallingStrenght, stopLoss, trailingStopLoss, stopGain, waitBars, entryruleChain, exitRuleChain);
        return result;
    }

}
