/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import org.ta4j.core.BaseStrategy;
import org.ta4j.core.Rule;
import org.ta4j.core.TradingRecord;

/**
 *
 * @author UNGERW
 */
public class UnwBaseStrategy extends BaseStrategy{
    
    public UnwBaseStrategy(Rule entryRule, Rule exitRule) {
        super(entryRule, exitRule);
    }

    @Override
    public boolean shouldExit(int index, TradingRecord tradingRecord) {
        boolean exit = super.shouldExit(index, tradingRecord);
        if(exit){
           // todo log why exit - which rule
        }
        return exit;
    }

    @Override
    public boolean shouldEnter(int index, TradingRecord tradingRecord) {
        boolean enter =  super.shouldEnter(index, tradingRecord);
        if(enter){
            // todo log why enter
        }
        return enter;
    }
    
    
    
}
