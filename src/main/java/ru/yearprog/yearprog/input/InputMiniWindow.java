package ru.yearprog.yearprog.input;

import ru.yearprog.yearprog.result.DrawingCycle;
import ru.yearprog.yearprog.Main;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

class InputMiniWindow extends JFrame {
    public InputMiniWindow() {
        super("Input");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(1200, 500, 0, 0);

        JPanel panel = new JPanel(true);
        panel.setPreferredSize(new Dimension(300, 200));
        this.add(panel);
        this.setResizable(false);
        this.pack();
        panel.setLayout(null);

        JButton next = new JButton("Next");
        JButton finish = new JButton("Finish!");

        Main.setRelativeSize(0, 0.5, 0.5, 0.25, next, panel);
        Main.setRelativeSize(0.5, 0.5, 0.5, 0.25, finish, panel);


        SpinnerModel modelX = new SpinnerNumberModel(0, -500, 500, 1);
        SpinnerModel modelY = new SpinnerNumberModel(0, -500, 500, 1);
        JSpinner xSpinner = new JSpinner(modelX);
        JSpinner ySpinner = new JSpinner(modelY);

        Main.setRelativeSize(0.5, 0, 0.5, 0.25, xSpinner, panel);
        Main.setRelativeSize(0.5, 0.25, 0.5, 0.25, ySpinner, panel);

        Font labelFont = new Font("Liberation Mono", Font.BOLD, 25);
        JLabel xLabel = new JLabel("x coord: ");
        JLabel yLabel = new JLabel("y coord: ");
        xLabel.setFont(labelFont);
        yLabel.setFont(labelFont);
        next.setFont(labelFont);
        finish.setFont(labelFont);
        xSpinner.setFont(labelFont);
        ySpinner.setFont(labelFont);

        Main.setRelativeSize(0.05, 0, 0.45, 0.25, xLabel, panel);
        Main.setRelativeSize(0.05, 0.25, 0.45, 0.25, yLabel, panel);

        JCheckBox coordinatePlane = new JCheckBox("Toggle coordinate plane");
        Main.setRelativeSize(0.23, 0.77, 0.75, 0.2, coordinatePlane, panel);

        panel.add(next);
        panel.add(finish);
        panel.add(xSpinner);
        panel.add(ySpinner);
        panel.add(xLabel);
        panel.add(yLabel);
        panel.add(coordinatePlane);

        coordinatePlane.addActionListener(e -> {
            HandInputWindow.drawCoordinatePlane = coordinatePlane.isSelected();
            HandInputWindow.panel.repaint();
        });

        next.addActionListener(e -> {
            int x = (int) modelX.getValue();
            int y = (int) modelY.getValue();
            modelX.setValue(0);
            modelY.setValue(0);

            Point p = new Point(x, y);
            movePoint(p);
            if (Arrays.asList(Main.points).contains(p)) {
                JOptionPane.showMessageDialog(this, "Repeated point!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                Main.points[Main.count] = p;
                Main.count++;
            }
            HandInputWindow.panel.repaint();
        });

        finish.addActionListener(e -> {
            if (Main.count < 4) {
                JOptionPane.showMessageDialog(this, "Not enough points!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                this.dispose();
                InputSelection.handInputWindow.dispose();
                try {
                    new DrawingCycle();
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });

        this.setVisible(true);
    }

    public static void movePoint(Point point) {
        point.x = Main.fieldSize / 2 + point.x;
        point.y = Main.fieldSize / 2 - point.y;
    }
}
