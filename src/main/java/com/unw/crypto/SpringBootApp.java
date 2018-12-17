/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto;

import com.unw.crypto.service.TradingManager;
import java.io.IOException;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * the backround/service app
 * @author UNGERW
 */
//@SpringBootApplication
public class SpringBootApp {

    public static void main(String[] args) throws BeansException, IOException {
        ConfigurableApplicationContext context = SpringApplication.run(SpringBootApp.class);
        // dataLoader = context.getBean(DataLoaderDB.class);
        TradingManager manager = context.getBean(TradingManager.class);
        manager.execute();
    }

}
