package ru.yearprog.yearprog;

import ru.yearprog.yearprog.windows.MainFrame;

import javax.swing.*;

public class Main {
    private static int fieldSize;
    private static MainFrame f;
    private static IntegerInput input;

    public static int getFieldSize() {
        return fieldSize;
    }

    public static MainFrame getF() {
        return f;
    }

    public static void main(String[] args) {
        input = new IntegerInput(600, 1000, value -> {
            if (value % 100 == 0) {
                fieldSize = value;
                f = new MainFrame();
                input.dispose();
            } else {
                JOptionPane.showMessageDialog(input, "Windows size: 600 - 1000, step 100", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }, "Size", "Field size", false, true);
        input.setLocationRelativeTo(null);
    }
}
