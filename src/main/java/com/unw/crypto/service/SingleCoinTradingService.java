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
    private TestComp testComp;
    
    private boolean active = false;

    public SingleCoinTradingService() {
    }

    public SingleCoinTradingService(Currency currency, Exchange exchange) {
        this.currency = currency;
        this.exchange = exchange;
    }

    public void trade(){
        if(active){
             System.out.println(" Active - will try to trade");
        }else{
             System.out.println("Inactive - doing nothing");            
        }
    }
    public void foo() {
        System.out.println(" trader " + currency.getStringValue()+ " " + exchange.getStringValue()+ " " + testComp.getStr());
    }

}
