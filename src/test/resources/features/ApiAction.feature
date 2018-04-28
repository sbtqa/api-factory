#language:en
@apiaction
Feature: Api action test

  @get
  Scenario: get
    * user sends request for (get test)
    * system returns "default client"
