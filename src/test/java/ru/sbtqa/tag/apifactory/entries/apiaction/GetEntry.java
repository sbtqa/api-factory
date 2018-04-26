package ru.sbtqa.tag.apifactory.entries.apiaction;

import org.junit.Assert;
import ru.sbtqa.tag.apifactory.ApiEntry;
import ru.sbtqa.tag.apifactory.ApiFactory;
import ru.sbtqa.tag.apifactory.annotation.ApiAction;
import ru.sbtqa.tag.apifactory.annotation.ApiValidationRule;
import ru.sbtqa.tag.apifactory.rest.HTTP;
import ru.sbtqa.tag.apifactory.utils.TestDataUtils;
import ru.sbtqa.tag.parsers.JsonParser;
import ru.sbtqa.tag.parsers.core.exceptions.ParserException;

@ApiAction(method = HTTP.GET, path = "apiaction/get", title = "get test")
public class GetEntry extends ApiEntry {

    @ApiValidationRule(title = "default client")
    public void validate() throws ParserException {
        JsonParser parser = new JsonParser();

        String response = (String) ApiFactory.getApiFactory().getResponseRepository().getBody(this.getClass());
        int id = parser.read(response, "$.id");
        String name = parser.read(response, "$.name");
        String email = parser.read(response, "$.email");

        Assert.assertEquals(TestDataUtils.getIdDefault(), id);
        Assert.assertEquals(TestDataUtils.getNameDefault(), name);
        Assert.assertEquals(TestDataUtils.getEmailDefault(), email);
    }
}
