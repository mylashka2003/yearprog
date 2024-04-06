package ru.yearprog.yearprog.windows;

import ru.yearprog.yearprog.CountCycle;
import ru.yearprog.yearprog.data.Data;

import javax.swing.*;

public class WIP extends JFrame {
    private final JProgressBar bar;

    public WIP() {
        super("Progress");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        JPanel panel = new JPanel(true);
        BoundedRangeModel model = new DefaultBoundedRangeModel(0, 0, 0, CountCycle.binomialCoefficient(Data.getCountOfPoints()));
        bar = new JProgressBar(model);
        JLabel label = new JLabel("Processing");
        bar.setStringPainted(true);
        panel.add(label);
        panel.add(bar);
        this.add(panel);
        this.setBounds(600, 600, 300, 70);
        this.setResizable(false);
        this.setVisible(true);
    }

    public void barSet(int progress) {
        bar.setValue(progress);
    }
}
