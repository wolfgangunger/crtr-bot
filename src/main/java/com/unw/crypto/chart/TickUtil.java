/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.chart;

import com.unw.crypto.model.Tick;
import java.util.Date;

/**
 *
 * @author UNGERW
 */
public final class TickUtil {

    private TickUtil() {
    }

    public static Tick createDummyTick(String currency, String exchange) {
        Tick dummy = new Tick();
        dummy.setAmount(0d);
        dummy.setCurrency(currency);
        dummy.setExchange(exchange);
        dummy.setPrice(0d);
        dummy.setTradeTime(new Date());
        return dummy;
    }

}
