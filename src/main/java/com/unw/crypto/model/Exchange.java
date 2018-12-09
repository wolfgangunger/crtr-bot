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
public enum Exchange {

    COINBASE("Coinbase"),
    BINANCE("Binance"),
    BITFINAX("Bitfinax"),
    KRAKEN ("Kraken"),
    BITTREX("Bittrex"),
    GDAX("GDax"),
    HITBTC("Hitbtc"),
    UNKNOWN("Unknown");

    private final String stringValue;

    private Exchange(String stringValue) {
        this.stringValue = stringValue;
    }

    public String getStringValue() {
        return stringValue;
    }

}
