/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.model;

/**
 *
 * @author UNGERW
 */
public enum Currency {
    
    BTC("Btc"),
    ETH("Eth");
    
   private final String stringValue;

    private Currency(String stringValue) {
        this.stringValue = stringValue;
    }
   
   public String getStringValue(){
       return stringValue;
   }
    
}
