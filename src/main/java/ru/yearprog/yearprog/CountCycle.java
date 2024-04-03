package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class CountCycle extends JFrame {
    public static Quadrilateral[] quadrilaterals;
    public static int index;
    public static Quadrilateral quadrilateral;
    public CountCycle() {
        super("Result");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DrawCyclePanel panel = new DrawCyclePanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);

        // 1
        generateRs(Main.points, Main.countOfPoints);

        // 3
        quickSort(quadrilaterals, 0, quadrilaterals.length - 1);

        int length = Math.min(10, quadrilaterals.length);
        Quadrilateral[] top10 = new Quadrilateral[length];
        System.arraycopy(quadrilaterals, 0, top10, 0, length);
        java.util.List<Quadrilateral> tops = Arrays.asList(top10);
        Collections.reverse(tops);
        top10 = tops.toArray(new Quadrilateral[0]);

        new QuadrilateralInfo();
        draw(panel, top10);
    }

    private static int binomialCoefficient(int n) {
        int coefficient = 1;
        for (int i = 1; i <= 4; i++) {
            coefficient *= n - (4 - i);
            coefficient /= i;
        }
        return coefficient;
    }

    private void generateRs(Point[] points, int count) {
        int index = 0;
        quadrilaterals = new Quadrilateral[binomialCoefficient(count)];
        for (int a = 0; a < count - 3; a++) {
            for (int b = a + 1; b < count - 2; b++) {
                for (int c = b + 1; c < count - 1; c++) {
                    for (int d = c + 1; d < count; d++) {
                        Quadrilateral r = Geometry.calculateQuadrilateralArea(points[a], points[b], points[c], points[d]);
                        quadrilaterals[index] = r;
                        index++;
                    }
                }
            }
        }
    }

    private static void draw(JPanel panel, Quadrilateral[] top) {
        index = 0;
        Timer timer = new Timer(1000, e -> {
            if (index < top.length) {
                quadrilateral = top[index];
                panel.repaint();
                QuadrilateralInfo.updateTable(quadrilateral);
                index++;
            } else {
                ((Timer)e.getSource()).stop();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    private static void quickSort(Quadrilateral[] shapes, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(shapes, begin, end);

            quickSort(shapes, begin, partitionIndex - 1);
            quickSort(shapes, partitionIndex + 1, end);
        }
    }

    private static int partition(Quadrilateral[] shapes, int begin, int end) {
        double pivot = shapes[end].getArea();
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (shapes[j].getArea() >= pivot) {
                i++;
                Quadrilateral swapTemp = shapes[i];
                shapes[i] = shapes[j];
                shapes[j] = swapTemp;
            }
        }

        Quadrilateral swapTemp = shapes[i + 1];
        shapes[i + 1] = shapes[end];
        shapes[end] = swapTemp;

        return i + 1;
    }

    static class DrawCyclePanel extends JPanel {
        public DrawCyclePanel() {
            super(true);
            this.setPreferredSize(new Dimension(Main.getFieldSize(), Main.getFieldSize()));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            MainFrame.drawCoordinateLines(g, Main.getFieldSize());
            MainFrame.drawCoordinatePlane(g, Main.getFieldSize(), 50);
            drawAllPoints(g);
            if (quadrilateral != null) quadrilateral.draw(g);
        }

        private void drawAllPoints(Graphics g) {
            for (int i = 0; i < Main.countOfPoints; i++) {
                MainFrame.drawPoint(Color.BLACK, Main.points[i], g);
            }
        }
    }
}
