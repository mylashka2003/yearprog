package ru.yearprog.yearprog;

import java.util.Objects;
import java.util.ResourceBundle;

public class Localization {
    private static ResourceBundle messagesRU;
    private static ResourceBundle messagesEN;
    private static String currentLocale = "en";

    public static void setMessagesRU(ResourceBundle messagesRU) {
        Localization.messagesRU = messagesRU;
    }

    public static void setMessagesEN(ResourceBundle messagesEN) {
        Localization.messagesEN = messagesEN;
    }

    public static void setCurrentLocale(String currentLocale) {
        Localization.currentLocale = currentLocale;
    }

    public static ResourceBundle getMessages() {
        if (Objects.equals(currentLocale, "ru")) return messagesRU;
        else return messagesEN;
    }
}
