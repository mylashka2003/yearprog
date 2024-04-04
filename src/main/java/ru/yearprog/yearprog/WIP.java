package ru.yearprog.yearprog;

import javax.swing.*;

public class WIP extends JFrame {
    private final BoundedRangeModel model;
    private final JPanel panel;
    private final JProgressBar bar;
    public WIP() {
         super("Progress");
         this.setDefaultCloseOperation(EXIT_ON_CLOSE);
         panel = new JPanel(true);
         model = new DefaultBoundedRangeModel(0, 0, 0, CountCycle.binomialCoefficient(Main.getCountOfPoints()));
         bar = new JProgressBar(model);
         bar.setStringPainted(true);
         panel.add(bar);
         this.add(panel);
         this.setBounds(600, 600, 300, 225);
         this.setResizable(false);
         this.setVisible(true);
     }

     public void barPlus() {
         model.setValue(model.getValue() + 1);
         panel.repaint();
     }

    public void barSet(int progress) {
        bar.setValue(progress);
        //System.out.println("setted");
    }
}
