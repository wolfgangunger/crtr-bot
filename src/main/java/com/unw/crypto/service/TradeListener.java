/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import org.ta4j.core.Order.OrderType;

/**
 *
 * @author UNGERW
 */
public interface TradeListener {
    
    public void tradeExecuted(OrderType orderType);
    
}
