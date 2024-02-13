package ru.yearprog.selectionWindows;

import ru.yearprog.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.font.LineMetrics;


public class KeyboardInputWindow extends JFrame {
    private final JPanel panel;
    private final JButton finishButton;
    private final JButton nextButton;
    private final JSpinner spinnerX;
    private final JSpinner spinnerY;
    private final JLabel xLabel;
    private final JLabel yLabel;
    private final Point[] pointsCur = new Point[100];
    private int index = 0;

    public KeyboardInputWindow() {
        // Window and Panel settings
        super("Ввод данных с клавиатуры");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setMinimumSize(new Dimension(900, 600));
        this.setBounds(10, 10, 900, 600);

        panel = new JPanel(true);
        this.add(panel);
        panel.setLayout(null);

        // Spinners for X and Y
        SpinnerModel modelX = new SpinnerNumberModel(10, -1000, 1000, 1);
        SpinnerModel modelY = new SpinnerNumberModel(10, -1000, 1000, 1);

        spinnerX = new JSpinner(modelX);
        spinnerX.setFont(Main.BigFont);
        spinnerY = new JSpinner(modelY);
        spinnerY.setFont(Main.BigFont);

        panel.add(spinnerX);
        panel.add(spinnerY);

        // "Finish" and "next" buttons
        finishButton = new JButton("Finish");
        finishButton.setFont(Main.BigFont);

        nextButton = new JButton("Next");
        nextButton.setFont(Main.BigFont);

        panel.add(finishButton);
        panel.add(nextButton);

        // Labels
        xLabel = new JLabel("<- X coord");
        xLabel.setFont(Main.BigFont);
        yLabel = new JLabel("Y coord ->");
        yLabel.setFont(Main.BigFont);
        panel.add(xLabel);
        panel.add(yLabel);

        // Button Actions
        finishButton.addActionListener(e -> {
            if (index < 4) {
                JOptionPane.showMessageDialog(this, "Введено слишком мало точек!", "Error!", JOptionPane.ERROR_MESSAGE);
            } else {
                Main.points = pointsCur;
                Main.indexOf = index - 1;
            }
        });

        nextButton.addActionListener(e -> {
            pointsCur[index] = new Point((int) spinnerX.getValue(), (int) spinnerY.getValue());
            index = index + 1;
            System.out.println(this.getInsets().top);
        });

        this.addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                resized();
            }
        });

        this.setVisible(true);
        resized();
    }

    private void resized() {
        int diagonal = (int) Math.sqrt(panel.getHeight() * panel.getHeight() + panel.getWidth() * panel.getWidth());
        Font font = new Font("Liberation Mono", Font.BOLD, (int) (diagonal * 0.025));

        nextButton.setFont(font);
        finishButton.setFont(font);
        spinnerX.setFont(font);
        spinnerY.setFont(font);
        xLabel.setFont(font);
        yLabel.setFont(font);

        Main.setRelativeSize(0.2, 0.45, 0.1, 0.1, spinnerX, panel);
        Main.setRelativeSize(0.7, 0.45, 0.1, 0.1, spinnerY, panel);

        Main.setRelativeSize(0.3, 0.45, 4, 0.05, xLabel, panel);
        Main.setRelativeSize(0.3, 0.5, 4, 0.05, yLabel, panel);

        xLabel.setHorizontalAlignment(SwingConstants.CENTER);
        xLabel.setVerticalAlignment(SwingConstants.CENTER);

        Main.setRelativeSize(0.5, 0.85, 0.2, 0.1, nextButton, panel);
        Main.setRelativeSize(0.75, 0.85, 0.2, 0.1, finishButton, panel);
    }
}