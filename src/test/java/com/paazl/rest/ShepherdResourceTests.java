package com.paazl.rest;

import com.paazl.SpringWebservicesTestCaseApplication;
import com.paazl.configuration.ApplicationConfiguration;
import com.paazl.service.SheepStatusesDto;
import com.paazl.service.ShepherdService;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static junit.framework.TestCase.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApplicationConfiguration.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class ShepherdResourceTests {

    private static Client client;
    private static WebTarget target;
    @MockBean
    private ShepherdService service;

    @BeforeClass
    public static void before(){
        client = ClientBuilder.newClient();
        target = client.target(getBaseURI());
    }

    @Test
    public void lol(){
        SheepStatusesDto sheepStatuses = service.getSheepStatusses();
        String expected =  String.format("Balance: %d, number of sheep healthy and dead: [%d, %d]",
                service.getBalance(), sheepStatuses.getNumberOfHealthySheep(), sheepStatuses.getNumberOfDeadSheep());
        Response response = target.path("status").request().get();
        assertEquals(MediaType.TEXT_PLAIN, response.getMediaType().toString());
        assertEquals(200 , response.getStatus());
    }

    private static URI getBaseURI() {
        return UriBuilder.fromUri("http://localhost:8080/rest/shepherdmanager").build();
    }
}
