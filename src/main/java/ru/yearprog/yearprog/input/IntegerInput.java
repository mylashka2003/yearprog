package ru.yearprog.yearprog.input;

import ru.yearprog.yearprog.Main;
import ru.yearprog.yearprog.windows.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class IntegerInput extends JFrame {
    private final int min;
    private final int max;
    private final Interface method;
    private final JTextField spinner;
    private final boolean disposeAfterUse;
    private final String label;

    public IntegerInput(int min, int max, Interface method, String title, String label, boolean disposeAfterUse, boolean programStop, int parent) {
        super(title);
        this.min = min;
        this.max = max;
        this.method = method;
        this.label = label;
        this.disposeAfterUse = disposeAfterUse;
        if (programStop) this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        else if (parent == 1) {
            this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    Main.getInput().setSpinners();
                    Main.getInput().setVisible(false);
                }
            });
        } else if (disposeAfterUse) {
            this.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    MainFrame.getInputRandomWindow().setSpinners();
                    MainFrame.getInputRandomWindow().setVisible(false);
                }
            });
        }
        JPanel panel = new JPanel(true);
        panel.setPreferredSize(new Dimension(250, 70));
        panel.setLayout(new GridLayout(1, 2));
        this.getContentPane().add(panel);
        this.setResizable(false);
        this.pack();
        JButton input = new JButton("Input");
        input.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        spinner = new JTextField(label);
        spinner.setFont(new Font("Comic Sans MS", Font.BOLD, 20));
        panel.add(spinner);
        panel.add(input);
        input.addActionListener(e -> readValue());
        spinner.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) readValue();
            }
        });
        this.setVisible(true);
    }

    private void setSpinners() {
        spinner.setText(label);
    }

    private void readValue() {
        String text = spinner.getText();
        try {
            int value = Integer.parseInt(text);
            if (value >= min && value <= max) {
                method.act(value);
                if (disposeAfterUse) {
                    this.setSpinners();
                    this.setVisible(false);
                }
            } else
                JOptionPane.showMessageDialog(IntegerInput.this, "Number from " + min + " to " + max, "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(IntegerInput.this, "Incorrect format!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public interface Interface {
        void act(int value);
    }
}