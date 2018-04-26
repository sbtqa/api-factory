package ru.sbtqa.tag.apifactory.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

public class JettySetupStepDefs {

    private static final int PORT = 9998;
    private static final String HOST = "http://localhost/";

    Server server;

    @Before(order = 1)
    public void before() {
        URI uri = UriBuilder.fromUri(HOST).port(PORT).build();
        ResourceConfig config = new ResourceConfig();
        config.register(Rest.class);

        server = JettyHttpContainerFactory.createServer(uri, config);
    }

    @After
    public void after() throws Exception {
        server.stop();
    }
}
