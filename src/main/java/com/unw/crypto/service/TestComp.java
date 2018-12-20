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
public class TestComp {
    
    
    public void foo(){
        System.out.println("test com");
    }
    
    public String getStr(){
        return "from TestComp";
    }
}
