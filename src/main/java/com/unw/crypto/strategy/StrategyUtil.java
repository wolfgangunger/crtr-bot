/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy;

import com.unw.crypto.model.ExtOrder;
import com.unw.crypto.model.Tick;
import java.util.List;
import org.ta4j.core.Bar;
import org.ta4j.core.BaseTimeSeries;
import org.ta4j.core.BaseTradingRecord;
import org.ta4j.core.Order;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.TradingRecord;
import org.ta4j.core.num.DoubleNum;

/**
 *
 * @author UNGERW
 */
public final class StrategyUtil {

    private StrategyUtil() {
    }

    public static BaseTimeSeries copySeries(TimeSeries source) {
        BaseTimeSeries result = new BaseTimeSeries("series" , DoubleNum::valueOf);
        List<Bar> bars = source.getBarData();
        bars.forEach((b) -> {
            result.addBar(b);
        });
        return result;
    }

    public static int getTimeInHoursAndMinutes(Tick tick) {
        return tick.getTradeTime().getHours() + tick.getTradeTime().getMinutes();
    }

    public static TradingRecord buildTradingRecord(List<ExtOrder> orders) {
        Order[] orderArray = new Order[orders.size()];
        orderArray = orders.toArray(orderArray);
        return orders.size() > 0 ? new BaseTradingRecord(orderArray) : new BaseTradingRecord();
    }

}
