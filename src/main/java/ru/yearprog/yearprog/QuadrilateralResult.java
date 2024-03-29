package ru.yearprog.yearprog;


import ru.yearprog.yearprog.Main;

import java.awt.*;

public class QuadrilateralResult {
    public double area;
    public Point[] points;
    public String type;

    QuadrilateralResult(double area, Point[] points, String type) {
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

        Main.drawPoint(Color.RED, points[0], g);
        Main.drawPoint(Color.RED, points[1], g);
        Main.drawPoint(Color.RED, points[2], g);
        Main.drawPoint(Color.RED, points[3], g);
    }
}
