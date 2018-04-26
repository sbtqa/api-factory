package ru.sbtqa.tag.apifactory.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import java.net.URI;
import javax.ws.rs.core.UriBuilder;
import org.eclipse.jetty.server.Server;
import org.glassfish.jersey.jetty.JettyHttpContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import ru.sbtqa.tag.apifactory.endpoints.ApiAction;

public class JettySetupStepDefs {

    private static final int PORT = 9998;
    private static final String HOST = "http://localhost/";

    Server server;

    @Before(order = 1)
    public void before() throws InterruptedException {
        URI uri = UriBuilder.fromUri(HOST).port(PORT).build();
        ResourceConfig config = new ResourceConfig();
        config.register(ApiAction.class);

        server = JettyHttpContainerFactory.createServer(uri, config);
//        Thread.sleep(1000000);
    }

    @After
    public void after() throws Exception {
        server.stop();
    }
}
