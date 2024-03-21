package ru.yearprog.yearprog.result;

import ru.yearprog.yearprog.Main;
import ru.yearprog.yearprog.Geometry;
import ru.yearprog.yearprog.QuadrilateralResult;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

public class DrawingCycle extends JFrame {
    public static QuadrilateralResult[] quadrilateralResults;
    public static int index;
    public static QuadrilateralResult quadrilateralResult;
    public DrawingCycle() throws InterruptedException {
        super("Result");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        DrawCyclePanel panel = new DrawCyclePanel();
        panel.setLayout(null);
        this.getContentPane().add(panel);
        this.pack();
        this.setVisible(true);
        this.setResizable(false);

        generateRs(Main.points, Main.countOfPoints);
        new QuadrilateralInfo();
        quadrilateralResults = sortR(quadrilateralResults);

        int length = Math.min(10, quadrilateralResults.length);
        QuadrilateralResult[] top10 = new QuadrilateralResult[length];
        System.arraycopy(quadrilateralResults, 0, top10, 0, length);
        java.util.List<QuadrilateralResult> tops = Arrays.asList(top10);
        Collections.reverse(tops);
        top10 = tops.toArray(new QuadrilateralResult[0]);

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

    public static QuadrilateralResult[] sortR(QuadrilateralResult[] rs) {
        java.util.List<QuadrilateralResult> rs1 = Arrays.asList(rs);

        rs1.sort(Comparator.comparingDouble(a -> a.area));
        Collections.reverse(rs1);
        return rs1.toArray(new QuadrilateralResult[0]);
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
