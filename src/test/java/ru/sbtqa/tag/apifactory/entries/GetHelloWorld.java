package ru.sbtqa.tag.apifactory.entries;

import ru.sbtqa.tag.apifactory.ApiEntry;
import ru.sbtqa.tag.apifactory.ApiFactory;
import ru.sbtqa.tag.apifactory.annotation.ApiAction;
import ru.sbtqa.tag.apifactory.annotation.ApiRequestHeader;
import ru.sbtqa.tag.apifactory.annotation.ApiRequestParam;
import ru.sbtqa.tag.apifactory.annotation.ApiValidationRule;
import ru.sbtqa.tag.apifactory.rest.HTTP;

@ApiAction(method = HTTP.GET, path = "test/hello", title = "account info")
public class GetHelloWorld extends ApiEntry {

    @ApiRequestParam(title = "token")
    @ApiRequestHeader(header = "X-Auth-Token")
//    @DependentResponseParam(path = "$.data.user.account.token", responseEntry = AuthorizationApiEntry.class)
    private String token;

    @ApiValidationRule(title = "account info")
    public void validate() throws Throwable {
        String response = (String) ApiFactory.getApiFactory().getResponseRepository().getBody(this.getClass());
        System.out.println(response);
    }
}
