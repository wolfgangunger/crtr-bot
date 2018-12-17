package com.unw.crypto.rest;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.stereotype.Component;


/**
 * @author UNGERW
 */
@Component
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(HealthService.class);
    }


}
