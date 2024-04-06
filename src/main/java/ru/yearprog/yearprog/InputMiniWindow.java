package ru.yearprog.yearprog;

import ru.yearprog.yearprog.data.Data;
import ru.yearprog.yearprog.windows.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class InputMiniWindow extends JFrame {
    private JButton next;
    private JButton finish;
    private JTextField xSpinner;
    private JTextField ySpinner;
    private JLabel xLabel;
    private JLabel yLabel;
    private boolean shiftPressed = false;
    private boolean aPressed = false;

    public InputMiniWindow() {
        super("Input");
        JPanel panel = new JPanel(true);
        panel.setPreferredSize(new Dimension(300, 200));
        panel.setLayout(new GridLayout(3, 2));
        this.add(panel);
        this.setResizable(false);
        this.setFocusable(true);
        KeyAdapter keyAdapter = createAdapter();
        this.addKeyListener(keyAdapter);
        this.pack();
        createStuff();
        applyFont(new Font("Comic Sans MS", Font.BOLD, 25), new JComponent[]{xLabel, yLabel, xSpinner, ySpinner, next, finish});
        addToPanel(panel, new JComponent[]{xLabel, xSpinner, yLabel, ySpinner, next, finish});
        xSpinner.addKeyListener(keyAdapter);
        ySpinner.addKeyListener(keyAdapter);
        next.addKeyListener(keyAdapter);
        finish.addKeyListener(keyAdapter);
        next.addActionListener(e -> readValue());
        finish.addActionListener(e -> finishAction());
        this.setVisible(true);
    }

    private KeyAdapter createAdapter() {
        return new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) readValue();
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shiftPressed = true;
                if (e.getKeyCode() == KeyEvent.VK_A) aPressed = true;
                if (shiftPressed && aPressed) finishAction();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SHIFT) shiftPressed = false;
                if (e.getKeyCode() == KeyEvent.VK_A) aPressed = false;
            }
        };
    }

    private void readValue() {
        String xs = xSpinner.getText();
        String ys = ySpinner.getText();
        try {
            int x = Integer.parseInt(xs);
            int y = Integer.parseInt(ys);
            if (x >= -Main.getFieldSize() / 2 && x <= Main.getFieldSize() / 2 && y >= -Main.getFieldSize() / 2 && y <= Main.getFieldSize() / 2) {
                addPoint(new Point(x, y));
            } else
                JOptionPane.showMessageDialog(this, "Number from " + -Main.getFieldSize() / 2 + " to " + Main.getFieldSize() / 2, "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(this, "Incorrect format!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPoint(Point point) {
        xSpinner.setText("0");
        ySpinner.setText("0");
        movePoint(point);
        if (Arrays.asList(Data.getPoints()).contains(point)) JOptionPane.showMessageDialog(this, "Repeated point!", "Error!", JOptionPane.ERROR_MESSAGE);
        else if (Data.getMaxPoints() == Data.getCountOfPoints()) {
            JOptionPane.showMessageDialog(this, "Too many points!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            Data.addPoint(point);
            MainFrame.setLastHandInputed(false);
        }
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

        xSpinner = new JTextField("0");
        xSpinner.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        ySpinner = new JTextField("0");
        ySpinner.setFont(new Font("Comic Sans MS", Font.BOLD, 20));

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
