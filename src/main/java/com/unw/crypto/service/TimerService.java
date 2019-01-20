/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * main timer service
 * @author UNGERW
 */
@Component
public class TimerService {

    private int counter = 0;
    
    // delays:
    // 1000 sec
    // 60000 min
    // 3600000 hour
    // 86400000 day

    @Autowired
    private TradingManager tradingManager;

    // delay in ms
    @Scheduled(fixedDelay = 1000)
    public void scheduledSecond() {
        System.out.println("second timer ..");
        tradingManager.minuteExecution();
    }


        // delay in ms
    @Scheduled(fixedDelay = 60000)
    public void scheduledMinute() {
        System.out.println("minute timer ..");
        tradingManager.minuteExecution();
    }
    
        // delay in ms
    @Scheduled(fixedDelay = 86400000)
    public void scheduledDay() {
        System.out.println("day timer ..");
        // cleanup db ; entries older now - 2 month
    }

  

}
