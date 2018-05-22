package ru.sbtqa.tag.apifactory;

import cucumber.api.CucumberOptions;
import org.eclipse.jetty.server.Server;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import ru.sbtqa.tag.apifactory.utils.JettyServiceUtils;
import ru.sbtqa.tag.cucumber.TagCucumber;
import ru.sbtqa.tag.parsers.JsonParser;

@RunWith(TagCucumber.class)
@CucumberOptions(monochrome = true, format = {"pretty"},
        glue = {"ru.sbtqa.tag.stepdefs", "setting"},
        features = {"src/test/resources/features"}
        ,tags = {"~@disabled"}
)
public class CucumberTest {

    private static Server server;

    @BeforeClass
    public static void setup() {
        server = JettyServiceUtils.startJetty();
        ApiFactory.getApiFactory().setParser(JsonParser.class);
    }

    @AfterClass
    public static void teardown() throws Exception {
        server.stop();
    }
}
