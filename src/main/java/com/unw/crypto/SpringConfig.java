/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto;

import com.unw.crypto.model.Currency;
import com.unw.crypto.model.Exchange;
import com.unw.crypto.service.SingleCoinTradingService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 *
 * @author UNGERW
 */
@Configuration
@EnableScheduling
public class SpringConfig {

    @Bean
    public SingleCoinTradingService btcTrader() {
        return new SingleCoinTradingService(Currency.BTC, Exchange.BINANCE);
    }

    @Bean
    public SingleCoinTradingService ltcTrader() {
        return new SingleCoinTradingService(Currency.LTC, Exchange.BINANCE);
    }

    @Bean
    public SingleCoinTradingService ethTrader() {
        return new SingleCoinTradingService(Currency.ETH, Exchange.BINANCE);
    }


}
