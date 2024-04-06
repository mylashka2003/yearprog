package ru.yearprog.yearprog;

import ru.yearprog.yearprog.data.Data;
import ru.yearprog.yearprog.windows.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class InputMiniWindow extends JFrame {
    private JButton next;
    private JButton finish;
    private SpinnerModel modelX;
    private SpinnerModel modelY;
    private JSpinner xSpinner;
    private JSpinner ySpinner;
    private JLabel xLabel;
    private JLabel yLabel;
    public InputMiniWindow() {
        super("Input");
        JPanel panel = new JPanel(true);
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setLayout(new GridLayout(3, 2));
        this.add(panel);
        this.setResizable(false);
        this.pack();
        createStuff();
        applyFont(new Font("Comic Sans MS", Font.BOLD, 25), new JComponent[]{xLabel, yLabel, xSpinner, ySpinner, next, finish});
        addToPanel(panel, new JComponent[]{xLabel, xSpinner, yLabel, ySpinner, next, finish});
        next.addActionListener(e -> nextAction());
        finish.addActionListener(e -> finishAction());
        this.setVisible(true);
    }

    private void nextAction() {
        Point p = new Point((int) modelX.getValue(), (int) modelY.getValue());
        modelX.setValue(0);
        modelY.setValue(0);
        movePoint(p);
        if (Arrays.asList(Data.getPoints()).contains(p)) JOptionPane.showMessageDialog(this, "Repeated point!", "Error!", JOptionPane.ERROR_MESSAGE);
        else if (Data.getMaxPoints() == Data.getCountOfPoints()) {
            JOptionPane.showMessageDialog(this, "Too many points!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else Data.addPoint(p);
        MainFrame.getPanel().repaint();
    }

    private void finishAction() {
        if (Data.getCountOfPoints() < 4) JOptionPane.showMessageDialog(this, "Not enough points!", "Error!", JOptionPane.ERROR_MESSAGE);
        else {
            this.dispose();
            Main.getF().dispose();
            new CountCycle();
        }
    }

    private void applyFont(Font font, JComponent[] components) {
        for (JComponent component : components) {
            component.setFont(font);
        }
    }

    private void addToPanel(JPanel panel, JComponent[] components) {
        for (JComponent component : components) {
            panel.add(component);
        }
    }

    private void createStuff() {
        next = new JButton("Next");
        finish = new JButton("Finish!");

        modelX = new SpinnerNumberModel(0, -500, 500, 1);
        modelY = new SpinnerNumberModel(0, -500, 500, 1);
        xSpinner = new JSpinner(modelX);
        ySpinner = new JSpinner(modelY);

        xLabel = new JLabel("x coord: ");
        yLabel = new JLabel("y coord: ");
    }

    public static void movePoint(Point point) {
        point.x = Main.getFieldSize() / 2 + point.x;
        point.y = Main.getFieldSize() / 2 - point.y;
    }

    public static void demovePoint(Point point) {
        point.setLocation(point.x - Main.getFieldSize() / 2, point.y * -1 + Main.getFieldSize() / 2);
    }
}
