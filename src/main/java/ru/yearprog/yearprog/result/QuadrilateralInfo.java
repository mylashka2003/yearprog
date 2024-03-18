package ru.yearprog.yearprog.result;

import ru.yearprog.yearprog.geometry.QuadrilateralResult;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuadrilateralInfo extends JFrame {
    public static DefaultTableModel model;
    //public static JPanel panel;

    public QuadrilateralInfo() {
        super("Info");
        SwingUtilities.invokeLater(() -> {
            this.setDefaultCloseOperation(EXIT_ON_CLOSE);
            //this.setBounds(1200, 500, 0, 0);


            //panel = new JPanel(true);
            //panel.setPreferredSize(new Dimension(500, 500));
            //this.add(panel);
            //panel.setLayout(null);

            Font labelFont = new Font("Liberation Mono", Font.BOLD, 25);

            String[] columnNames = {"Point", "x", "y"};

            model = new DefaultTableModel(columnNames, 0);

            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            this.getContentPane().add(scrollPane);
            this.pack();

            model.setRowCount(0);
            model.addRow(new Object[]{1, DrawingCycle.quadrilateralResults[0].points[0].x, DrawingCycle.quadrilateralResults[0].points[0].y});
            model.addRow(new Object[]{2, DrawingCycle.quadrilateralResults[0].points[1].x, DrawingCycle.quadrilateralResults[0].points[1].y});
            model.addRow(new Object[]{3, DrawingCycle.quadrilateralResults[0].points[2].x, DrawingCycle.quadrilateralResults[0].points[2].y});
            model.addRow(new Object[]{4, DrawingCycle.quadrilateralResults[0].points[3].x, DrawingCycle.quadrilateralResults[0].points[3].y});

            //panel.add(table);
            this.setResizable(false);
            //this.pack();
            this.setVisible(true);
        });
    }

    public static void updateTable(QuadrilateralResult quadrilateralResult) {
        model.setRowCount(0);
        model.addRow(new Object[]{1, quadrilateralResult.points[0].x, quadrilateralResult.points[0].y});
        model.addRow(new Object[]{2, quadrilateralResult.points[1].x, quadrilateralResult.points[1].y});
        model.addRow(new Object[]{3, quadrilateralResult.points[2].x, quadrilateralResult.points[2].y});
        model.addRow(new Object[]{4, quadrilateralResult.points[3].x, quadrilateralResult.points[3].y});
        //panel.repaint();
    }
}
