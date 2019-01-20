/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.model;

import java.util.Date;
import org.ta4j.core.Order;
import org.ta4j.core.TimeSeries;
import org.ta4j.core.num.Num;

/**
 *
 * @author UNGERW
 */
public class ExtOrder extends Order{
    
    private Date tradeTime;
    private AddOrderInfo addOrderInfo;

    public ExtOrder(Order order) {
          super(order.getIndex(), order.getType(), order.getPrice(), order.getAmount());
    }
    
    public ExtOrder(int index, TimeSeries series, OrderType type) {
        super(index, series, type);
    }

    public ExtOrder(int index, OrderType type, Num price, Num amount) {
        super(index, type, price, amount);
    }

    public Date getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(Date tradeTime) {
        this.tradeTime = tradeTime;
    }

    public AddOrderInfo getAddOrderInfo() {
        return addOrderInfo;
    }

    public void setAddOrderInfo(AddOrderInfo addOrderInfo) {
        this.addOrderInfo = addOrderInfo;
    }
    
    
  
    
}
