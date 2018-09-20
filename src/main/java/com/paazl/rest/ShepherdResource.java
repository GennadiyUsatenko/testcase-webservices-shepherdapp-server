package com.paazl.rest;

import com.paazl.service.ShepherdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
}