/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.strategy.to;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 *
 * @author UNGERW
 */
@Data
@AllArgsConstructor
@Builder
public class ExitRuleChain {

    private boolean rule1_rsiHigh;
    private boolean rule2_stoHigh;
    private boolean rule3_8maDown;

    private boolean rule11_rsiPointingDown;
    private boolean rule12_StoPointingDown;

}
