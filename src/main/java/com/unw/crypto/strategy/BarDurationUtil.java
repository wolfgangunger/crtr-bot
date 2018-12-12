/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.model.BarDuration;

/**
 *
 * @author UNGERW
 */
public final class BarDurationUtil {

    private BarDurationUtil() {
    }

    public static int getMAMultiplicator(BarDuration barDuration) {
        switch (barDuration) {
            case SIXTY_MIN:
                return 1;
            case THIRTY_MIN:
                return 2;
            case TWENTY_MIN:
                return 3;
            case TEN_MIN:
                return 6;
            case FIVE_MIN:
                return 12;
            case TWO_MIN:
                return 30;
            case ONE_MIN:
                return 60;
            default:
                return 12;
        }
    }

}
