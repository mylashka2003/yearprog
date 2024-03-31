package ru.yearprog.yearprog.result;

import ru.yearprog.yearprog.Main;
import ru.yearprog.yearprog.Geometry;
import ru.yearprog.yearprog.QuadrilateralResult;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

public class CountCycle extends JFrame {
    public static QuadrilateralResult[] quadrilateralResults;
    public static int index;
    public static QuadrilateralResult quadrilateralResult;
    public CountCycle() throws InterruptedException {
        super("Result");
        long t1 = System.currentTimeMillis();
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DrawCyclePanel panel = new DrawCyclePanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);

        // 1
        long startTime = System.currentTimeMillis();
        generateRs(Main.points, Main.countOfPoints);
        long endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        new QuadrilateralInfo();

        // 3
        startTime = System.currentTimeMillis();
        quickSort(quadrilateralResults, 0, quadrilateralResults.length - 1);
        endTime = System.currentTimeMillis();
        System.out.println(endTime - startTime);

        int length = Math.min(10, quadrilateralResults.length);
        QuadrilateralResult[] top10 = new QuadrilateralResult[length];
        System.arraycopy(quadrilateralResults, 0, top10, 0, length);
        java.util.List<QuadrilateralResult> tops = Arrays.asList(top10);
        Collections.reverse(tops);
        top10 = tops.toArray(new QuadrilateralResult[0]);

        long t2 = System.currentTimeMillis();
        System.out.println("------");
        System.out.println(t2 - t1);

        draw(panel, top10);
    }

    public static int binomialCoefficient(int n, int k) {
        if (k < 0 || k > n) return 0;
        if (k > n - k) k = n - k;

        int coefficient = 1;
        for (int i = 1; i <= k; i++) {
            coefficient *= n - (k - i);
            coefficient /= i;
        }
        return coefficient;
    }

    public void generateRs(Point[] points, int count) {
        int index = 0;
        quadrilateralResults = new QuadrilateralResult[binomialCoefficient(count, 4)];
        for (int a = 0; a < count - 3; a++) {
            for (int b = a + 1; b < count - 2; b++) {
                for (int c = b + 1; c < count - 1; c++) {
                    for (int d = c + 1; d < count; d++) {
                        QuadrilateralResult r = Geometry.calculateQuadrilateralArea(points[a], points[b], points[c], points[d]);
                        quadrilateralResults[index] = r;
                        index++;
                    }
                }
            }
        }
    }

    public static void draw(JPanel panel, QuadrilateralResult[] top) {
        index = 0;
        Timer timer = new Timer(1000, e -> {
            if (index < top.length) {
                quadrilateralResult = top[index];
                panel.repaint();
                QuadrilateralInfo.updateTable(quadrilateralResult);
                index++;
            } else {
                ((Timer)e.getSource()).stop();
            }
        });
        timer.setInitialDelay(0);
        timer.start();
    }

    public static void quickSort(QuadrilateralResult[] shapes, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(shapes, begin, end);

            quickSort(shapes, begin, partitionIndex - 1);
            quickSort(shapes, partitionIndex + 1, end);
        }
    }

    private static int partition(QuadrilateralResult[] shapes, int begin, int end) {
        double pivot = shapes[end].area;
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (shapes[j].area >= pivot) {
                i++;

                // Меняем местами shapes[i] и shapes[j]
                QuadrilateralResult swapTemp = shapes[i];
                shapes[i] = shapes[j];
                shapes[j] = swapTemp;
            }
        }

        // Меняем местами shapes[i + 1] и shapes[end] (или pivot)
        QuadrilateralResult swapTemp = shapes[i + 1];
        shapes[i + 1] = shapes[end];
        shapes[end] = swapTemp;

        return i + 1;
    }

    static class DrawCyclePanel extends JPanel {
        public DrawCyclePanel() {
            super(true);
            this.setPreferredSize(new Dimension(Main.fieldSize, Main.fieldSize));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            Main.drawCoordinateLines(g, Main.fieldSize);
            Main.drawCoordinatePlane(g, Main.fieldSize);
            drawAllPoints(g);
            if (quadrilateralResult != null) quadrilateralResult.draw(g);
        }

        public void drawAllPoints(Graphics g) {
            for (int i = 0; i < Main.countOfPoints; i++) {
                Main.drawPoint(Color.BLACK, Main.points[i], g);
            }
        }
    }
}
