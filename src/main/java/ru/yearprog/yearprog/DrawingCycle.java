package ru.yearprog.yearprog;

import ru.yearprog.yearprog.geometry.Geometry;
import ru.yearprog.yearprog.geometry.QuadrilateralResult;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DrawingCycle extends JFrame {
    public static QuadrilateralResult[] quadrilateralResults;
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

        generateRs(Main.points, Main.count);
        draw(panel);
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

    public static void draw(JPanel panel) {
        Timer timer = new Timer(500, new ActionListener() {
            private int index = 0;
            @Override
            public void actionPerformed(ActionEvent e) {
                if (index < quadrilateralResults.length) {
                    quadrilateralResult = quadrilateralResults[index];
                    panel.repaint();
                    index++;
                } else {
                    ((Timer)e.getSource()).stop();
                }
            }
        });
        timer.setInitialDelay(0);
        timer.start();
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
            for (int i = 0; i < Main.count; i++) {
                Main.drawPoint(Color.BLACK, Main.points[i], g);
            }
        }
    }
}
