package ru.yearprog.yearprog;

import ru.yearprog.yearprog.input.IntegerInput;
import ru.yearprog.yearprog.windows.MainFrame;

import javax.swing.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {
    public static void main(String[] args) {
        Localization.setMessagesRU(ResourceBundle.getBundle("LanguageRes", new Locale("ru", "RU")));
        Localization.setMessagesEN(ResourceBundle.getBundle("LanguageRes", new Locale("en", "US")));
        IntegerInput input = new IntegerInput(600, 1000, value -> {
            if (value % 100 == 0) {
                MainProperties.setFieldSize(value);
                MainProperties.setF(new MainFrame());
                MainProperties.getInput().dispose();
            } else {
                JOptionPane.showMessageDialog(MainProperties.getInput(), "Windows size: 600 - 1000, step 100", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }, "Size", "Field size", 1);
        input.setLocationRelativeTo(null);
        MainProperties.setInput(input);
    }
}
