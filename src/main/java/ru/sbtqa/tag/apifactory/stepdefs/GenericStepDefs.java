package ru.sbtqa.tag.apifactory.stepdefs;

import cucumber.api.java.en.And;
import ru.sbtqa.tag.apifactory.ApiFactory;
import ru.sbtqa.tag.apifactory.exception.ApiException;

public class GenericStepDefs {

    @And("userSendRequestNoParams")
    public void userSendRequestNoParams(String action) throws ApiException {
        ApiFactory.getApiFactory().getApiEntry(action);
        ApiFactory.getApiFactory().getCurrentApiEntry().fireRequest();
    }
    
    @And("userValidate")
    public void userValidate(String rule) throws ApiException {
        ApiFactory.getApiFactory().getCurrentApiEntry().fireValidationRule(rule);
    }

//    /**
//     * Execute action with one parameter User|he keywords are optional
//     *
//     * @param action title of the action to execute
//     * @param param parameter
//     * @throws PageInitializationException if current page is not initialized
//     * @throws NoSuchMethodException if corresponding method doesn't exist
//     */
//    @And("userActionOneParam")
//    public void userActionOneParam(String action, String param) throws PageInitializationException, NoSuchMethodException {
//        PageFactory.getInstance().getCurrentPage().executeMethodByTitle(action, param);
//    }
//
//    /**
//     * Execute action with two parameters User|he keywords are optional
//     *
//     * @param action title of the action to execute
//     * @param param1 first parameter
//     * @param param2 second parameter
//     * @throws PageInitializationException if current page is not initialized
//     * @throws NoSuchMethodException if corresponding method doesn't exist
//     */
//    @And("userActionTwoParams")
//    public void userActionTwoParams(String action, String param1, String param2) throws PageInitializationException, NoSuchMethodException {
//        PageFactory.getInstance().getCurrentPage().executeMethodByTitle(action, param1, param2);
//    }
//
//    /**
//     * Execute action with three parameters User|he keywords are optional
//     *
//     * @param action title of the action to execute
//     * @param param1 first parameter
//     * @param param2 second patrameter
//     * @param param3 third parameter
//     * @throws PageInitializationException if current page is not initialized
//     * @throws NoSuchMethodException if corresponding method doesn't exist
//     */
//    @And("userActionThreeParams")
//    public void userActionThreeParams(String action, String param1, String param2, String param3) throws PageInitializationException, NoSuchMethodException {
//        PageFactory.getInstance().getCurrentPage().executeMethodByTitle(action, param1, param2, param3);
//    }
//
//    /**
//     * Execute action with parameters from given {@link cucumber.api.DataTable}
//     * User|he keywords are optional
//     *
//     * @param action title of the action to execute
//     * @param dataTable table of parameters
//     * @throws PageInitializationException if current page is not initialized
//     * @throws NoSuchMethodException if corresponding method doesn't exist
//     */
//    @And("userActionTableParam")
//    public void userActionTableParam(String action, DataTable dataTable) throws PageInitializationException, NoSuchMethodException {
//        PageFactory.getInstance().getCurrentPage().executeMethodByTitle(action, dataTable);
//    }
}
