package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;

public class Main {
    private static final int maxPoints = 150;
    static Point[] points = new Point[maxPoints];
    static int countOfPoints = 0;
    private static int fieldSize;
    private static MainFrame f;
    private static IntegerInput input;

    public static void resetPoints() {
        Main.points = new Point[Main.maxPoints];
        Main.countOfPoints = 0;
    }

    public static int getMaxPoints() {
        return maxPoints;
    }

    public static Point[] getPoints() {
        return points;
    }

    public static int getCountOfPoints() {
        return countOfPoints;
    }

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
                JOptionPane.showMessageDialog(input, "Возможные размеры окна: 600 - 1000, шаг 100", "Error!", JOptionPane.ERROR_MESSAGE);
            }
        }, "Size", "Field size", false);
        input.setLocationRelativeTo(null);
    }

    public static void addPoint(Point p) {
        points[countOfPoints] = p;
        countOfPoints++;
    }
}
