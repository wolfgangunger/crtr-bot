package com.unw.crypto.rest;

import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/**
 * @author UNGERW
 */
@Component
@Path("/crtr/")
public class HealthService {

    @GET
    @Path("/health")
    public Response test() {
        return Response.status(200).entity("Service running").build();
    }

}
