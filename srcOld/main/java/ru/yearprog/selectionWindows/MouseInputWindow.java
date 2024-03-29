package ru.yearprog.yearprog.selectionWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MouseInputWindow extends JFrame {
    private final JPanel panel;
    private final JButton finishButton;
    public MouseInputWindow() {
        super("Ввод данных мышью");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(800, 600));
        this.setBounds(10, 10, 1500, 800);
        this.setResizable(false);

        panel = new JPanel(true);
        this.add(panel);
        panel.setLayout(null);

        finishButton = new JButton("Закончить");

        panel.add(finishButton);

        finishButton.addActionListener(e -> System.out.println(555));

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resized();
            }
        });

        this.setVisible(true);
    }

    private void resized() {
        finishButton.setBounds(panel.getWidth() - 125, panel.getHeight() - 75, 100, 50);
    }

    public void drawCoordinateGrid(Graphics g) {
        //g.drawLine(50, 50, 700, 50);
        //g.drawLine(50, 50, 50, 700);
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //drawCoordinateGrid(g);
    }
}
