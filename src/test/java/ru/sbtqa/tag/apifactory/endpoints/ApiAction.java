package ru.sbtqa.tag.apifactory.endpoints;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import ru.sbtqa.tag.apifactory.dto.Client;
import ru.sbtqa.tag.apifactory.utils.TestDataUtils;

@Path("/apiaction")
public class ApiAction {

    @GET
    @Path("get")
    @Produces(MediaType.APPLICATION_JSON)
    public Client get() {
        return TestDataUtils.createDefaultClient();
    }
}