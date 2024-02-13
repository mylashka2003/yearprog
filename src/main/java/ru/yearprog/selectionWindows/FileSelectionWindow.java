package ru.yearprog.selectionWindows;

import javax.swing.*;
import java.awt.*;

public class FileSelectionWindow extends JFrame {
    public FileSelectionWindow() {
        super("Выбор файла");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(100, 100, 800, 600);
        this.setMinimumSize(new Dimension(800, 600));

        // Ваш код для ввода данных мышью
    }
}