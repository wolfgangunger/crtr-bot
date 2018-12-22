/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.unw.crypto.rest;

import com.unw.crypto.service.TradingManager;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author UNGERW
 */
@Path("/crtr/admin/")
public class AdminResource {

    @Autowired
    private TradingManager tradingManager;

    @GET
    @Path("/status")
    public Response test() {
        return Response.status(200).entity("CRTR Service running").build();
    }

    @GET
    @Path("/traders/status")
    public Response tradersStatus() {
        int traders = tradingManager.getNumberOfTraders();
        return Response.status(200).entity("Number of traders: " + traders).build();
    }

    @GET
    @Path("/traders/active")
    public Response getActiveTraders() {
        int count = tradingManager.getNumberOfActiveTraders();
        List<String> traders = tradingManager.getActiveTraders();
        return Response.status(200).entity("Number of traders: " + count + " " + traders.toString()).build();
    }

    @GET
    @Path("/traders/entered")
    public Response isEntered() {
        boolean b = tradingManager.isEntered();
        return Response.status(200).entity("In Trade:  " + b ).build();
    }
}
