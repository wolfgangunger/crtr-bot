/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.knowm.xchange.dto.Order.OrderType;

/**
 *
 * @author UNGERW
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "t_add_order_info")
public class AddOrderInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false, nullable = false)
    private Long id;

    private OrderType orderType;
    @Column(nullable = true)
    private int rsi;
    @Column(nullable = true)
    private double sto;
    @Column(nullable = true)
    private double closedPriceStrenth;
    @Column(nullable = true)
    private double rsiStrength;
    @Column(nullable = true)
    private double stoStrength;
    @Column(nullable = true)
    private double rsiStrength1;
    @Column(nullable = true)
    private double stoStrength1;
    @Column(nullable = true)
    private double sma3;
    @Column(nullable = true)
    private double sma8;
    @Column(nullable = true)
    private double sma14;
    @Column(nullable = true)
    private double sma50;
    @Column(nullable = true)
    private double sma200;
    @Column(nullable = true)
    private double sma314;
    @Column(nullable = true)
    private double ema14;
    @Column(nullable = true)
    private double ema50;
    @Column(nullable = true)
    private double cci14;
    @Column(nullable = true)
    private double cci50;
    @Column(nullable = true)
    private boolean priceAboveSma200;
    @Column(nullable = true)
    private boolean priceAboveSma3141;
    @Column(nullable = true)
    private boolean isSMALongTimeBullish;

}
