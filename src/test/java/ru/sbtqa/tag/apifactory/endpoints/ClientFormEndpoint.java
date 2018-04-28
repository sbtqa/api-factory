package ru.sbtqa.tag.apifactory.endpoints;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.PATCH;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import ru.sbtqa.tag.apifactory.dto.Client;
import ru.sbtqa.tag.apifactory.dto.SimpleResult;
import ru.sbtqa.tag.apifactory.utils.Default;
import ru.sbtqa.tag.apifactory.utils.TestDataUtils;

@Path("/client/form")
public class ClientFormEndpoint {

    @GET
    @Path("get")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response get() {
        Client client = TestDataUtils.createDefaultClient();
        return Response.ok(client)
                .header("first-header", "header-value-1")
                .build();
    }


    @POST
//    @Path("post")
    @Consumes("application/x-www-form-urlencoded")
    @Produces(MediaType.TEXT_PLAIN)
    public String post(MultivaluedMap<String, String> formParams) {

        System.out.println("!!!!!!!!");
        return "a";

//        SimpleResult result = new SimpleResult();
//        result.setResult(client.getId() + client.getName() + client.getEmail());
//        return Response.ok(result)
//                .build();
    }

    @PUT
    @Path("put")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response put(Client client) {
        SimpleResult result = new SimpleResult();
        result.setResult(client.getId() + client.getName() + client.getEmail());
        return Response.ok(result)
                .build();
    }

    @PATCH
    @Path("patch")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response patch(Client client) {
        SimpleResult result = new SimpleResult();
        result.setResult(client.getId() + client.getName() + client.getEmail());
        return Response.ok(result)
                .build();
    }

    @DELETE
    @Path("delete")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_FORM_URLENCODED)
    public Response getWithParams(
            @QueryParam(Default.PARAMETER_NAME1) String param) {

        SimpleResult result = new SimpleResult();
        result.setResult(param);

        return Response.ok(result)
                .build();
    }
}