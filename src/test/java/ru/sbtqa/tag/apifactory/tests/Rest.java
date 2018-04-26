package ru.sbtqa.tag.apifactory.tests;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/test")
public class Rest {

    @GET
    @Path("hello")
    @Produces(MediaType.APPLICATION_JSON)
    public Track hello() {
        Track track = new Track();
        track.setTitle("Enter Sandman");
        track.setSinger("Metallica");

        return track;
    }
}