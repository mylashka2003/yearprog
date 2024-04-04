package ru.yearprog.yearprog;

import java.awt.*;

public class Quadrilateral {
    private final double area;
    private final Point[] points;
    private final String type;

    public double getArea() {
        return area;
    }

    public Point[] getPoints() {
        return points;
    }

    Quadrilateral(double area, Point[] points, String type) {
        this.area = area;
        this.points = points;
        this.type = type;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);

        g.drawLine(points[0].x, points[0].y, points[1].x, points[1].y);
        g.drawLine(points[1].x, points[1].y, points[2].x, points[2].y);
        g.drawLine(points[2].x, points[2].y, points[3].x, points[3].y);
        g.drawLine(points[3].x, points[3].y, points[0].x, points[0].y);

        MainFrame.drawPoint(Color.RED, points[0], g);
        MainFrame.drawPoint(Color.RED, points[1], g);
        MainFrame.drawPoint(Color.RED, points[2], g);
        MainFrame.drawPoint(Color.RED, points[3], g);
    }

    @Override
    public String toString() {
        return "Type: " + type + " | Points: " + "(" + points[0].x + "," + points[0].y + "); " +
                                                "(" + points[1].x + "," + points[1].y + "); " +
                                                "(" + points[2].x + "," + points[2].y + "); " +
                                                "(" + points[3].x + "," + points[0].y + "); " + "| Area: " +
                area;
    }

    public String getType() {
        return type;
    }
}
