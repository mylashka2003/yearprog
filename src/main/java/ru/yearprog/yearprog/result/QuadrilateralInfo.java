package ru.yearprog.yearprog.result;

import ru.yearprog.yearprog.QuadrilateralResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuadrilateralInfo extends JFrame {
    public static DefaultTableModel model;
    public static JLabel label;

    public QuadrilateralInfo() {
        super("Info");
        SwingUtilities.invokeLater(() -> {
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);

            Font labelFont = new Font("Liberation Mono", Font.BOLD, 25);
            Font tableFont = new Font("Liberation Mono", Font.BOLD, 14);

            String[] columnNames = {"Point", "x", "y"};

            model = new DefaultTableModel(columnNames, 0);

            model.setRowCount(4);
            model.addRow(new Object[]{1, CountCycle.quadrilateralResults[0].points[0].x, CountCycle.quadrilateralResults[0].points[0].y});
            model.addRow(new Object[]{2, CountCycle.quadrilateralResults[0].points[1].x, CountCycle.quadrilateralResults[0].points[1].y});
            model.addRow(new Object[]{3, CountCycle.quadrilateralResults[0].points[2].x, CountCycle.quadrilateralResults[0].points[2].y});
            model.addRow(new Object[]{4, CountCycle.quadrilateralResults[0].points[3].x, CountCycle.quadrilateralResults[0].points[3].y});

            JTable table = new JTable(model);
            table.setFont(tableFont);
            String text = "area: " + CountCycle.quadrilateralResults[0].area;
            label = new JLabel(text);
            label.setFont(labelFont);

            JPanel panel = new JPanel(true);
            panel.setLayout(new GridLayout(2, 1)); // Вертикальное расположение компонентов

            // Добавляем компоненты в панель
            panel.add(new JScrollPane(table));
            panel.add(label);

            // Добавляем панель в окно
            this.add(panel);

            this.setBounds(0,0, 200, 225);
            this.setResizable(false);
            this.setVisible(true);
        });
    }

    public static void updateTable(QuadrilateralResult quadrilateralResult) {
        model.setRowCount(0);
        model.addRow(new Object[]{1, quadrilateralResult.points[0].x, quadrilateralResult.points[0].y});
        model.addRow(new Object[]{2, quadrilateralResult.points[1].x, quadrilateralResult.points[1].y});
        model.addRow(new Object[]{3, quadrilateralResult.points[2].x, quadrilateralResult.points[2].y});
        model.addRow(new Object[]{4, quadrilateralResult.points[3].x, quadrilateralResult.points[3].y});
        String text = "area: " + quadrilateralResult.area;
        label.setText(text);
    }
}
