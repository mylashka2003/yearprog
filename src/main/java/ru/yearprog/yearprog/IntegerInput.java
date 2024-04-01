package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class IntegerInput extends JFrame {
    private final int min;
    private final int max;
    private final Interface method;
    private final JTextField spinner;
    public IntegerInput(int min, int max, Interface method, String title, String label) {
        super(title);
        this.min = min;
        this.max = max;
        this.method = method;
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel(true);
        panel.setPreferredSize(new Dimension(250, 70));
        panel.setLayout(new GridLayout(1,2));
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

    private void readValue() {
        String text = spinner.getText();
        try {
            int value = Integer.parseInt(text);
            if (value >= min && value <= max) {
                method.act(value);
                dispose();
            } else JOptionPane.showMessageDialog(IntegerInput.this, "Некорректный формат!", "Error!", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException err) {
            JOptionPane.showMessageDialog(IntegerInput.this, "Некорректный формат!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public interface Interface {
        void act(int value);
    }
}