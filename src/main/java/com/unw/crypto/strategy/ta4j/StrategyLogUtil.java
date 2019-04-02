/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.ta4j;

import java.lang.reflect.Field;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.trading.rules.AndRule;
import org.ta4j.core.trading.rules.OrRule;

/**
 *
 * @author UNGERW
 */
public final class StrategyLogUtil {

    private StrategyLogUtil() {
    }

    public static StringBuilder handleRule(Rule rule, int index, TradingRecord tradingRecord, StringBuilder sb) {
        if (rule instanceof AndRule) {
            handleAndRule(rule, index, tradingRecord, sb);
        } else if (rule instanceof OrRule) {
            handleOrRule(rule, index, tradingRecord, sb);
        } else {
            boolean s = rule.isSatisfied(index, tradingRecord);
            if (s) {
//                System.out.println("Rule satifsfied " + rule.toString());
                sb.append("Rule satifsfied " + rule.toString() + "\n");
            }
        }
        return sb;
    }

    private static void handleOrRule(Rule rule, int index, TradingRecord tradingRecord,StringBuilder sb) {
        try {
            OrRule orRule = (OrRule) rule;
            Field[] fields = OrRule.class.getDeclaredFields();
            Field fRule1 = fields[0];
            Field fRule2 = fields[1];
            fRule1.setAccessible(true);
            fRule2.setAccessible(true);
            Rule rule1 = (Rule) fRule1.get(orRule);
            Rule rule2 = (Rule) fRule2.get(orRule);
            handleRule(rule1, index, tradingRecord,sb);
            handleRule(rule2, index, tradingRecord, sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleAndRule(Rule rule, int index, TradingRecord tradingRecord , StringBuilder sb) {
        try {
            AndRule andRule = (AndRule) rule;
            Field[] fields = AndRule.class.getDeclaredFields();
            Field fRule1 = fields[0];
            Field fRule2 = fields[1];
            fRule1.setAccessible(true);
            fRule2.setAccessible(true);
            Rule rule1 = (Rule) fRule1.get(andRule);
            Rule rule2 = (Rule) fRule2.get(andRule);
            handleRule(rule1, index, tradingRecord,sb);
            handleRule(rule2, index, tradingRecord,sb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
