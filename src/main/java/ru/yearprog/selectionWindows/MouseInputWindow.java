package ru.yearprog.selectionWindows;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class MouseInputWindow extends JFrame {
    private final JPanel panel;
    private final JButton finishButton;
    //private final int bigBorder = 0;
    //private final int smallBorder = 0;
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
        finishButton.setBounds((int) (panel.getWidth() * 0.89), (int) (panel.getHeight() * 0.93), (int) (panel.getWidth() * 0.1), (int) (panel.getHeight() * 0.05));
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
