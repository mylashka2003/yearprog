package ru.yearprog;

import ru.yearprog.startWindow.StartWindow;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        StartWindow window = new StartWindow();
    }

    public static void setRelativeSize(double startX, double startY, double sizeX, double sizeY,
                                       JComponent component, JComponent parentComponent) {
        component.setBounds((int) (parentComponent.getWidth() * startX), (int) (parentComponent.getHeight() * startY),
                (int) (parentComponent.getWidth() * sizeX), (int) (parentComponent.getHeight() * sizeY));
    }
}
