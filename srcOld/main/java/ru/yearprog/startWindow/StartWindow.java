package ru.yearprog.startWindow;

import ru.yearprog.File.ContentFile;
import ru.yearprog.Main;
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
        this.setMinimumSize(new Dimension(1100, 600));
        this.setBounds(10, 10, 1100, 600);

        panel = new JPanel(true);
        this.add(panel);
        panel.setLayout(null);

        fileButton = new JButton("File Input");
        fileButton.setFont(Main.BigFont);
        mouseButton = new JButton("Mouse Input");
        mouseButton.setFont(Main.BigFont);
        keyboardButton = new JButton("Keyboard Input");
        keyboardButton.setFont(Main.BigFont);

        panel.add(fileButton);
        panel.add(mouseButton);
        panel.add(keyboardButton);

        fileButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();

            fileChooser.setDialogTitle("Выберите файл для открытия");
            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                boolean res = ContentFile.readFile(fileChooser.getSelectedFile(), this);

                if (res) {
                    // open final window
                }
            }
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

        int diagonal = (int) Math.sqrt(panel.getHeight() * panel.getHeight() + panel.getWidth() * panel.getWidth());
        Font font = new Font("Liberation Mono", Font.BOLD, (int) (diagonal * 0.05));

        fileButton.setFont(font);
        mouseButton.setFont(font);
        keyboardButton.setFont(font);
    }
}