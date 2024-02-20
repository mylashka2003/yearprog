package ru.yearprog.yearprog;

import ru.yearprog.yearprog.File.ContentFile;

import javax.swing.*;
import java.awt.*;
import java.nio.file.attribute.UserPrincipal;

public class HandInputWindow extends JFrame {
    private final DrawPanel panel;

    public HandInputWindow() {
        super("Hand Input");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        new KeyboardInput();

        panel = new DrawPanel();
        this.add(panel);
        this.pack();
        panel.setLayout(null);

        this.setVisible(true);
        this.setResizable(false);
    }

    class DrawPanel extends JPanel {
        public DrawPanel() {
            super(true);
            this.setPreferredSize(new Dimension(1000, 1000));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            drawCoordinatePlane(g);
        }

        private void drawCoordinatePlane(Graphics g) {
            g.drawLine(0, panel.getHeight() / 2, panel.getWidth(), panel.getHeight() / 2);
            g.drawLine(panel.getWidth() / 2, 0, panel.getWidth() / 2, panel.getHeight());

            g.drawLine(panel.getWidth(), panel.getHeight() / 2, panel.getWidth() - 5, panel.getHeight() / 2 - 5);
            g.drawLine(panel.getWidth(), panel.getHeight() / 2, panel.getWidth() - 5, panel.getHeight() / 2 + 5);

            g.drawLine(panel.getWidth() / 2, 0, panel.getWidth() / 2 - 5, 5);
            g.drawLine(panel.getWidth() / 2, 0, panel.getWidth() / 2 + 5, 5);

            for (int i = 50; i < 1000; i += 50) {
                g.drawLine(i, panel.getHeight() / 2 - 3, i, panel.getHeight() / 2 + 3);
                g.drawString(String.valueOf(i - panel.getWidth() / 2), i - 10, panel.getHeight() / 2 + 15);

                if (i != panel.getHeight() / 2) {
                    g.drawLine(panel.getWidth() / 2 - 3, i, panel.getWidth() / 2 + 3, i);
                    g.drawString(String.valueOf(i - panel.getHeight() / 2), panel.getWidth() / 2 - 30, panel.getHeight() - i + 5);
                }
            }
        }
    }

    class KeyboardInput extends JFrame {
        private final JButton next;
        private final JButton finish;
        private final JLabel xLabel;
        private final JLabel yLabel;
        private final JSpinner xSpinner;
        private final JSpinner ySpinner;
        private final JPanel panel;
        public KeyboardInput() {
            super("Keyboard Input");
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            this.setBounds(1000, 100, 300, 200);
            this.setResizable(false);

            panel = new JPanel(true);
            this.add(panel);
            panel.setLayout(null);

            next = new JButton("Next");
            next.setBounds(0, (int) (this.getHeight() * 0.66), (int) (this.getWidth() * 0.3), (int) (this.getHeight() * 0.15));
            finish = new JButton("Finish!");
            finish.setBounds((int) (this.getWidth() * 0.35), (int) (this.getHeight() * 0.66), (int) (this.getWidth() * 0.3), (int) (this.getHeight() * 0.15));

            SpinnerModel modelX = new SpinnerNumberModel(10, -500, 500, 1);
            SpinnerModel modelY = new SpinnerNumberModel(10, -500, 500, 1);
            xSpinner = new JSpinner(modelX);
            xSpinner.setBounds((int) (this.getWidth() * 0.45), 0, (int) (this.getWidth() * 0.45), (int) (this.getHeight() * 0.33));
            ySpinner = new JSpinner(modelY);
            ySpinner.setBounds((int) (this.getWidth() * 0.45), (int) (this.getHeight() * 0.33), (int) (this.getWidth() * 0.45), (int) (this.getHeight() * 0.3));

            Font labelFont = new Font("Liberation Mono", Font.BOLD, 15);
            xLabel = new JLabel("x coord: ");
            xLabel.setFont(labelFont);
            xLabel.setBounds((int) (this.getWidth() * 0.1), 0, (int) (this.getWidth() * 0.45), (int) (this.getHeight() * 0.33));
            yLabel = new JLabel("y coord: ");
            yLabel.setFont(labelFont);
            yLabel.setBounds((int) (this.getWidth() * 0.1), (int) (this.getHeight() * 0.33), (int) (this.getWidth() * 0.45), (int) (this.getHeight() * 0.3));

            panel.add(next);
            panel.add(finish);
            panel.add(xSpinner);
            panel.add(ySpinner);
            panel.add(xLabel);
            panel.add(yLabel);

            next.addActionListener(e -> {
            });

            this.setVisible(true);
        }
    }
}
