package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;

public class DrawingCycle extends JFrame {
    public static DrawCyclePanel panel;
    public DrawingCycle() {
        super("Result");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        panel = new DrawCyclePanel();
        this.add(panel);
        this.pack();
        panel.setLayout(null);
svo
        this.setVisible(true);
        this.setResizable(false);
    }

    static class DrawCyclePanel extends JPanel {
        public DrawCyclePanel() {
            super(true);
            this.setPreferredSize(new Dimension(1000, 1000));
        }

        @Override
        public void paint(Graphics g) {
            super.paint(g);
            drawCoordinateLines(g);
            drawCoordinatePlane(g);
        }

        public void drawPoint(Color color, Point point, Graphics g) {
            g.setColor(color);
            g.fillOval(point.x - 3, point.y - 3, 6, 6);
            g.setColor(Color.BLACK);
            g.drawOval(point.x - 3, point.y - 3, 6, 6);
        }

        private void drawCoordinateLines(Graphics g) {
            g.drawLine(0, this.getHeight() / 2, this.getWidth(), this.getHeight() / 2);
            g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2, this.getHeight());

            g.drawLine(this.getWidth(), this.getHeight() / 2, this.getWidth() - 5, this.getHeight() / 2 - 5);
            g.drawLine(this.getWidth(), this.getHeight() / 2, this.getWidth() - 5, this.getHeight() / 2 + 5);
            g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2 - 5, 5);
            g.drawLine(this.getWidth() / 2, 0, this.getWidth() / 2 + 5, 5);

            for (int i = 50; i < 1000; i += 50) {
                g.drawLine(i, this.getHeight() / 2 - 3, i, this.getHeight() / 2 + 3);
                g.drawString(String.valueOf(i - this.getWidth() / 2), i - 10, this.getHeight() / 2 + 15);

                if (i != this.getHeight() / 2) {
                    g.drawLine(this.getWidth() / 2 - 3, i, this.getWidth() / 2 + 3, i);
                    g.drawString(String.valueOf(i - this.getHeight() / 2), this.getWidth() / 2 - 30, this.getHeight() - i + 5);
                }
            }
        }

        private void drawCoordinatePlane(Graphics g) {
            for (int i = 50; i < 1000; i += 50) {
                drawDashedLine(g, i, 0, i, this.getHeight());
                drawDashedLine(g, 0, i, this.getWidth(), i);
            }
        }

        public void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {
            Graphics2D g2d = (Graphics2D) g.create();

            float[] dashingPattern1 = {2, 2};
            Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                    1, dashingPattern1, 0);
            g2d.setStroke(dashed);

            g2d.drawLine(x1, y1, x2, y2);
            g2d.dispose();
        }
    }

    public static Point[] calculate(Point[] points, int count) {
        double maxArea = -1;
        Point[] ans = new Point[4];
        for (int a = 0; a < count - 3; a++) {
            for (int b = a + 1; b < count - 2; b++) {
                for (int c = b + 1; c < count - 1; c++) {
                    for (int d = c + 1; d < count; d++) {
                        if (getArea4(new Point[]{points[a], points[b], points[c], points[d]}) > maxArea) {
                            maxArea = getArea4(new Point[]{points[a], points[b], points[c], points[d]});
                            ans = new Point[]{points[a], points[b], points[c], points[d]};
                        }
                    }
                }
            }
        }
        return ans;
    }
    public static double getArea4(Point[] points) {
        if (Line.selfIntersection(points[0], points[1], points[2], points[3])) return -2;
        Line ab = new Line(points[0], points[1]);
        if (ab.pointOnLine(points[2]) && ab.pointOnLine(points[3])) return -2;

        if (ab.pointOnLine(points[2])) {
            if (Line.pointIsBetween(points[0], points[1], points[2])) return getArea3(points[0], points[1], points[3]);
            if (Line.pointIsBetween(points[0], points[2], points[1])) return getArea3(points[0], points[2], points[3]);
            return getArea3(points[1], points[2], points[3]);
        }

        if (ab.pointOnLine(points[2])) {
            if (Line.pointIsBetween(points[0], points[1], points[2])) return getArea3(points[0], points[1], points[3]);
            if (Line.pointIsBetween(points[0], points[2], points[1])) return getArea3(points[0], points[2], points[3]);
            return getArea3(points[1], points[2], points[3]);
        }
    }

    public static double getArea3(Point point1, Point point2, Point point3) {
        return 0.5 * Math.abs(point1.x * (point2.y - point3.y) + point2.x * (point3.y - point1.y) + point3.x * (point1.y - point2.y));
    }
}
