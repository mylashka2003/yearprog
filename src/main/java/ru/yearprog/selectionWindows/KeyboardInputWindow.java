package ru.yearprog.selectionWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class KeyboardInputWindow extends JFrame {
    private final JPanel panel;
    private final JButton finishButton;
    private final JButton nextButton;
    public KeyboardInputWindow() {
        super("Ввод данных с клавиатуры");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setBounds(10, 10, 1300, 800);

        panel = new JPanel(true);
        this.add(panel);
        panel.setLayout(null);

        finishButton = new JButton("Закончить");
        nextButton = new JButton("Ввести");

        panel.add(finishButton);
        panel.add(nextButton);

        finishButton.addActionListener(e -> System.out.println(555));

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resized();
            }
        });

        this.setVisible(true);
    }

    private void resized() {
        nextButton.setBounds((int) (panel.getWidth() * 0.76), (int) (panel.getHeight() * 0.92), (int) (panel.getWidth() * 0.1), (int) (panel.getHeight() * 0.05));
        finishButton.setBounds((int) (panel.getWidth() * 0.88), (int) (panel.getHeight() * 0.92), (int) (panel.getWidth() * 0.1), (int) (panel.getHeight() * 0.05));
    }
}