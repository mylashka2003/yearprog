package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;

public class Main {
    public static Point[] points = new Point[1000];
    public static int countOfPoints = 0;
    public static final int fieldSize = 700;
    public static ClassicFrame f;

    public static void main(String[] args) {
        f = new ClassicFrame();
    }

    public static void addPoint(Point p) {
        points[countOfPoints] = p;
        countOfPoints++;
    }

    public static void drawPoint(Color color, Point point, Graphics g) {
        g.setColor(color);
        g.fillOval(point.x - 3, point.y - 3, 6, 6);
        g.setColor(Color.BLACK);
        g.drawOval(point.x - 3, point.y - 3, 6, 6);
    }

    public static void drawCoordinateLines(Graphics g, int size) {
        g.drawLine(0, size / 2, size, size / 2);
        g.drawLine(size / 2, 0, size / 2, size);

        g.drawLine(size, size / 2, size - 5, size / 2 - 5);
        g.drawLine(size, size / 2, size - 5, size / 2 + 5);
        g.drawLine(size / 2, 0, size / 2 - 5, 5);
        g.drawLine(size / 2, 0, size / 2 + 5, 5);

        for (int i = 50; i < size; i += 50) {
            g.drawLine(i, size / 2 - 3, i, size / 2 + 3);
            g.drawString(String.valueOf(i - size / 2), i - 10, size / 2 + 15);

            if (i != size / 2) {
                g.drawLine(size / 2 - 3, i, size / 2 + 3, i);
                g.drawString(String.valueOf(i - size / 2), size / 2 - 30, size - i + 5);
            }
        }
    }

    public static void drawCoordinatePlane(Graphics g, int size) {
        for (int i = 50; i < size; i += 50) {
            drawDashedLine(g, i, 0, i, size);
            drawDashedLine(g, 0, i, size, i);
        }
    }

    public static void drawDashedLine(Graphics g, int x1, int y1, int x2, int y2) {
        Graphics2D g2d = (Graphics2D) g.create();

        float[] dashingPattern1 = {2, 2};
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL,
                1, dashingPattern1, 0);
        g2d.setStroke(dashed);

        g2d.drawLine(x1, y1, x2, y2);
        g2d.dispose();
    }
}
