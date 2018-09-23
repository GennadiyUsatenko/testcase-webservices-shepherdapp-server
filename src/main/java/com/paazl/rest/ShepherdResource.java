package com.paazl.rest;

import com.paazl.service.SheepStatusesDto;
import com.paazl.service.ShepherdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.math.BigInteger;

@Path("/shepherdmanager")
@Service
public class ShepherdResource {
    ShepherdService service;

    @Autowired
    public ShepherdResource(ShepherdService service) {
		this.service = service;
	}

	@GET
    @Path("/balance")
    public BigInteger getBalance() {
        return service.getBalance();
    }

    @GET
    @Path("/status")
    @Produces(MediaType.TEXT_PLAIN)
    public String getStatus() {
        SheepStatusesDto sheepStatuses = service.getSheepStatusses();
        return String.format("Balance: %d, number of sheep healthy and dead: [%d, %d]",
                getBalance(),
                sheepStatuses.getNumberOfHealthySheep(),
                sheepStatuses.getNumberOfDeadSheep());
    }

    @POST
    @Path("/order/{nofSheepDesired}")
    @Produces(MediaType.TEXT_PLAIN)
    public String orderNewSheep(@PathParam("nofSheepDesired") int nofSheepDesired) {
        return service.orderNewSheep(nofSheepDesired);
    }
}