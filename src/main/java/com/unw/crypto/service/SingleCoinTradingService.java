/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.ta4j.core.Order;

/**
 *
 * @author UNGERW
 */
@Data
@Component
public class SingleCoinTradingService {
//
    //@Autowired
    //@Qualifier("currency")

    private Currency currency;

    private Exchange exchange;

    @Autowired
    private ExchangeService testComp;
    // should the service try to trade ?
    private boolean active = false;
    // is the trader currently in a trade ( sell order but not yet completed)
    private boolean entered = false;
    // the listener to notify on the trades
    private TradeListener listener;

    public SingleCoinTradingService() {
    }

    public SingleCoinTradingService(Currency currency, Exchange exchange) {
        this.currency = currency;
        this.exchange = exchange;
    }

    public void trade() {
        if (active) {
            System.out.println(getInfo() + " Active - will try to trade");
            // let's simulate we do a trade
            if (!entered) {
                listener.tradeExecuted(Order.OrderType.BUY);
                entered = true;
            } else {
                // get out
                listener.tradeExecuted(Order.OrderType.SELL);
                entered = false;
            }

        } else {
            System.out.println(getInfo() + " Inactive - doing nothing");
        }
    }

    public String getInfo() {
        return ("Trader " + currency.getStringValue() + " : " + exchange.getStringValue() + " : Active " + active + " : In Trade: " + entered);
    }

    public void registerListener(TradeListener listener) {
        this.listener = listener;

    }

}
