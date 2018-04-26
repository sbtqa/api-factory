package ru.sbtqa.tag.apifactory.utils;

import ru.sbtqa.tag.apifactory.dto.Client;

public class TestDataUtils {

    private static final int ID_DEFAULT = 11223344;
    private static final String NAME_DEFAULT = "Default person";
    private static final String EMAIL_DEFAULT = "default_person@google.com";

    private TestDataUtils() {}

    public static Client createDefaultClient() {
        Client client = new Client();
        client.setId(ID_DEFAULT);
        client.setName(NAME_DEFAULT);
        client.setEmail(EMAIL_DEFAULT);

        return client;
    }

    public static int getIdDefault() {
        return ID_DEFAULT;
    }

    public static String getNameDefault() {
        return NAME_DEFAULT;
    }

    public static String getEmailDefault() {
        return EMAIL_DEFAULT;
    }
}
