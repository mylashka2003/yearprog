package ru.yearprog;

import javax.swing.*;
import java.awt.*;

public class HandInputWindow extends JFrame {
    private final JPanel panel;

    public HandInputWindow() {
        super("Hand Input");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(10, 10, 1100, 600);
        this.setResizable(false);

        panel = new JPanel(true);
        this.add(panel);
        panel.setLayout(null);
    }
}
