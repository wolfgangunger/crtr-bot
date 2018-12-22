/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.service;

import org.springframework.stereotype.Component;

/**
 *
 * @author UNGERW
 */
@Component
public class ExchangeService {
    
    
    public void foo(){
        System.out.println("foo");
    }
    
    public String getStr(){
        return "from ExchangeService";
    }
}
