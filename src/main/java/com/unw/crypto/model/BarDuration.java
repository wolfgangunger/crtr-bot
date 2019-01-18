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
public enum BarDuration {

    ONE_MIN(1),
    TWO_MIN(2),
    FIVE_MIN(5),
    TEN_MIN(10),
    TWENTY_MIN(20),
    THIRTY_MIN(30),
    SIXTY_MIN(60),
    TWO_HOURS(120);

    private final int intValue;

    private BarDuration(int intValue) {
        this.intValue = intValue;
    }

    public int getIntValue() {
        return intValue;
    }

}
