/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.ta4j;

import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;

/**
 *
 * @author UNGERW
 */
public class UnwBaseStrategy extends BaseStrategy {

    public UnwBaseStrategy(Rule entryRule, Rule exitRule) {
        super(entryRule, exitRule);
    }

    @Override
    public boolean shouldExit(int index, TradingRecord tradingRecord) {
        boolean result = super.shouldExit(index, tradingRecord);
        if (result) {
            System.out.println("UnwBaseStrategy should Exit");
            Rule rule = getExitRule();
            StringBuilder sb = new StringBuilder();
            sb = StrategyLogUtil.handleRule(rule, index, tradingRecord, sb);
            System.out.println(sb.toString());
        }
        return result;
    }

    @Override
    public boolean shouldEnter(int index, TradingRecord tradingRecord) {
        boolean result = super.shouldEnter(index, tradingRecord);
        if (result) {
            System.out.println("UnwBaseStrategy should Enter");
            Rule rule = getEntryRule();
            StringBuilder sb = new StringBuilder();
            sb = StrategyLogUtil.handleRule(rule, index, tradingRecord, sb);
            System.out.println(sb.toString());
        }
        return result;
    }

}
