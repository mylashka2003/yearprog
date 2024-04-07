package ru.yearprog.yearprog.windows;

import ru.yearprog.yearprog.MainProperties;
import ru.yearprog.yearprog.data.Quadrilateral;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuadrilateralInfo extends JFrame {
    private static DefaultTableModel model;
    private static JLabel area;
    private static JLabel type;

    public QuadrilateralInfo() {
        super("Info");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        model = new DefaultTableModel(new String[]{"Point", "x", "y"}, 4);
        JTable table = new JTable(model);
        table.setFont(new Font("Liberation Mono", Font.BOLD, 14));
        area = new JLabel("");
        area.setFont(new Font("Liberation Mono", Font.BOLD, 25));
        type = new JLabel("");
        type.setFont(new Font("Liberation Mono", Font.BOLD, 25));
        JPanel panel = new JPanel(true);
        JPanel labels = new JPanel(true);
        labels.setLayout(new GridLayout(2, 1));
        panel.setLayout(new GridLayout(2, 1));
        panel.add(new JScrollPane(table));
        panel.add(labels);
        labels.add(area);
        labels.add(type);
        this.add(panel);
        this.setBounds(MainProperties.getFieldSize() + 20, 0, 300, 225);
        this.setResizable(false);
        this.setVisible(true);
    }

    public static void updateTable(Quadrilateral quadrilateral) {
        model.setRowCount(0);
        model.addRow(new Object[]{1, quadrilateral.getDemoved()[0].x, quadrilateral.getDemoved()[0].y});
        model.addRow(new Object[]{2, quadrilateral.getDemoved()[1].x, quadrilateral.getDemoved()[1].y});
        model.addRow(new Object[]{3, quadrilateral.getDemoved()[2].x, quadrilateral.getDemoved()[2].y});
        model.addRow(new Object[]{4, quadrilateral.getDemoved()[3].x, quadrilateral.getDemoved()[3].y});
        area.setText("area: " + quadrilateral.getArea());
        type.setText("type: " + quadrilateral.getType());
    }
}
