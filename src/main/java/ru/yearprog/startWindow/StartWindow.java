package ru.yearprog.startWindow;

import ru.yearprog.Main;
import ru.yearprog.selectionWindows.FileSelectionWindow;
import ru.yearprog.selectionWindows.KeyboardInputWindow;
import ru.yearprog.selectionWindows.MouseInputWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class StartWindow extends JFrame {
    private final JButton fileButton;
    private final JButton mouseButton;
    private final JButton keyboardButton;
    private final JPanel panel;
    public StartWindow() {
        super("Выбор способа ввода");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setBounds(10, 10, 800, 600);

        panel = new JPanel(true);
        this.add(panel);
        panel.setLayout(null);

        fileButton = new JButton("Ввод из файла");
        mouseButton = new JButton("Ввод мышью");
        keyboardButton = new JButton("Ввод с клавиатуры");

        panel.add(fileButton);
        panel.add(mouseButton);
        panel.add(keyboardButton);

        fileButton.addActionListener(e -> {
            FileSelectionWindow fileSelectionWindow = new FileSelectionWindow();
            this.dispose();
        });

        mouseButton.addActionListener(e -> {
            MouseInputWindow mouseInputWindow = new MouseInputWindow();
            this.dispose();
        });

        keyboardButton.addActionListener(e -> {
            KeyboardInputWindow keyboardInputWindow = new KeyboardInputWindow();
            this.dispose();
        });

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resized();
            }
        });

        setVisible(true);
    }

    private void resized() {
        Main.setRelativeSize(0.2, 0.1, 0.6, 0.2, fileButton, panel);
        Main.setRelativeSize(0.2, 0.4, 0.6, 0.2, mouseButton, panel);
        Main.setRelativeSize(0.2, 0.7, 0.6, 0.2, keyboardButton, panel);
    }
}