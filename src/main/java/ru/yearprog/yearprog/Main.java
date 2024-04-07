package ru.yearprog.yearprog;

import ru.yearprog.yearprog.input.IntegerInput;
import ru.yearprog.yearprog.windows.MainFrame;

import javax.swing.*;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class Main {
    private static int fieldSize;
    private static MainFrame f;
    private static IntegerInput input;
    private static ResourceBundle messagesRU;
    private static ResourceBundle messagesEN;

    public static IntegerInput getInput() {
        return input;
    }

    public static int getFieldSize() {
        return fieldSize;
    }

    public static MainFrame getF() {
        return f;
    }

    private static String currentLocale = "en";

    public static void setCurrentLocale(String currentLocale) {
        Main.currentLocale = currentLocale;
    }

    public static String getCurrentLocale() {
        return currentLocale;
    }

    public static ResourceBundle getMessages() {
        if (Objects.equals(currentLocale, "ru")) return messagesRU;
        else return messagesEN;
    }

    public static void main(String[] args) {
        Locale localeEN = new Locale("en", "US");
        Locale localeRU = new Locale("ru", "RU");
        messagesRU = ResourceBundle.getBundle("MessagesBundle", localeRU);
        messagesEN = ResourceBundle.getBundle("MessagesBundle", localeEN);
        input = new IntegerInput(600, 1000, value -> {
            if (value % 100 == 0) {
                fieldSize = value;
                f = new MainFrame();
                input.dispose();
            } else {
                JOptionPane.showMessageDialog(input, "Windows size: 600 - 1000, step 100", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }, "Size", "Field size", false, true, 1);
        input.setLocationRelativeTo(null);
    }
}
