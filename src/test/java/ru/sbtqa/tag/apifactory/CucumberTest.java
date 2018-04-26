package ru.sbtqa.tag.apifactory;

import cucumber.api.CucumberOptions;
import org.junit.runner.RunWith;
import ru.sbtqa.tag.cucumber.TagCucumber;

@RunWith(TagCucumber.class)
@CucumberOptions(monochrome = true, format = {"pretty"},
        glue = {"ru.sbtqa.tag.apifactory.stepdefs", "setting"},
        features = {"src/test/resources/features"})
public class CucumberTest {
}
