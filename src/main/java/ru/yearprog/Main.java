package ru.yearprog;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static Point[] points = new Point[1000];
    public static int indexOf = 0;
    public static Font BigFont = new Font("Liberation Mono", Font.BOLD, 30);

    public static void main(String[] args) {
        new InputSelection();
    }

    public static void setRelativeSize(double startX, double startY, double sizeX, double sizeY,
                                       JComponent component, JComponent parentComponent) {
        component.setBounds((int) (parentComponent.getWidth() * startX), (int) (parentComponent.getHeight() * startY),
                (int) (parentComponent.getWidth() * sizeX), (int) (parentComponent.getHeight() * sizeY));
    }
}
