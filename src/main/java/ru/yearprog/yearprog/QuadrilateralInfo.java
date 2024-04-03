package ru.yearprog.yearprog;

import ru.yearprog.yearprog.CountCycle;
import ru.yearprog.yearprog.Quadrilateral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuadrilateralInfo extends JFrame {
    public static DefaultTableModel model;
    public static JLabel label;

    public QuadrilateralInfo() {
        super("Info");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        model = new DefaultTableModel(new String[]{"Point", "x", "y"}, 4);

        model.addRow(new Object[]{1, CountCycle.quadrilaterals[0].getPoints()[0].x, CountCycle.quadrilaterals[0].getPoints()[0].y});
        model.addRow(new Object[]{2, CountCycle.quadrilaterals[0].getPoints()[1].x, CountCycle.quadrilaterals[0].getPoints()[1].y});
        model.addRow(new Object[]{3, CountCycle.quadrilaterals[0].getPoints()[2].x, CountCycle.quadrilaterals[0].getPoints()[2].y});
        model.addRow(new Object[]{4, CountCycle.quadrilaterals[0].getPoints()[3].x, CountCycle.quadrilaterals[0].getPoints()[3].y});
        JTable table = new JTable(model);
        table.setFont(new Font("Liberation Mono", Font.BOLD, 14));
        String text = "area: " + CountCycle.quadrilaterals[0].getArea();
        label = new JLabel(text);
        label.setFont(new Font("Liberation Mono", Font.BOLD, 25));
        JPanel panel = new JPanel(true);
        panel.setLayout(new GridLayout(2, 1));
        panel.add(new JScrollPane(table));
        panel.add(label);
        this.add(panel);
        this.setBounds(1000, 0, 300, 225);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void updateTable(Quadrilateral quadrilateral) {
        model.setRowCount(0);
        model.addRow(new Object[]{1, quadrilateral.getPoints()[0].x, quadrilateral.getPoints()[0].y});
        model.addRow(new Object[]{2, quadrilateral.getPoints()[1].x, quadrilateral.getPoints()[1].y});
        model.addRow(new Object[]{3, quadrilateral.getPoints()[2].x, quadrilateral.getPoints()[2].y});
        model.addRow(new Object[]{4, quadrilateral.getPoints()[3].x, quadrilateral.getPoints()[3].y});
        String text = "area: " + quadrilateral.getArea();
        label.setText(text);
    }
}
