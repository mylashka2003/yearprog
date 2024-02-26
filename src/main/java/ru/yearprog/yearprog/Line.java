package ru.yearprog.yearprog;

import java.awt.*;

public class Line {
    public double a;
    public double b;
    public double c;

    public Line(int x1, int y1, int x2, int y2) {
        a = y1 - y2;
        b = x2 - x1;
        c = x1 * y2 - x2 * y1;

        if (b < 0) {
            a *= -1;
            b *= -1;
            c *= -1;
        }
    }

    public Line(Point point1, Point point2) {
        a = point1.y - point2.y;
        b = point2.x - point1.x;
        c = point1.x * point2.y - point2.x * point1.y;

        if (b < 0) {
            a *= -1;
            b *= -1;
            c *= -1;
        }
    }

    @Override
    public String toString() {
        String bS; String cS;

        if (b >= 0) bS = String.format(" + %.2fy", b);
        else bS = String.format(" - %.2fy", -b);

        if (c >= 0) cS = String.format(" + %.2f", c);
        else cS = String.format(" - %.2f", -c);

        return String.format("%.2fx", a) + bS + cS + " = 0";
    }

    public boolean isParallel(Line line) {
        double aR = Math.round(a * 1000) / 1000.0;
        double bR = Math.round(b * 1000) / 1000.0;

        double aR2 = Math.round(line.a * 1000) / 1000.0;
        double bR2 = Math.round(line.b * 1000) / 1000.0;

        return Math.atan(bR / aR) == Math.atan(bR2 / aR2);
    }

    public Point intersection(Line line) {
        if (this.isParallel(line)) return null;
        else {
            double y = (-line.c + (line.a * c) / a) / (line.b - (line.a) * b / a);
            double x = (-c - b * y) / a;
            return new Point((int) x, (int) y);
        }
    }

    public static double areaTriangle(Point point1, Point point2, Point point3) {
        return 0.5 * Math.abs(point1.x * (point2.y - point3.y) + point2.x * (point3.y - point1.y) + point3.x * (point1.y - point2.y));
    }

    public static boolean selfIntersection(Point a, Point b, Point c, Point d) {
        Line ab = new Line(a, b);
        Line bc = new Line(b, c);
        Line ad = new Line(a, d);
        Line cd = new Line(c, d);

        Point int1 = ab.intersection(cd);
        Point int2 = ad.intersection(bc);
        if (int1 != null) {
            if (isBetween(a.x, b.x, int1.x) && isBetween(a.y, b.y, int1.y) && isBetween(c.x, d.x, int1.x) && isBetween(c.y, d.y, int1.y)) return true;
        }
        if (int2 != null) {
            return isBetween(a.x, d.x, int2.x) && isBetween(a.y, d.y, int2.y) && isBetween(b.x, c.x, int2.x) && isBetween(b.y, c.y, int2.y);
        }
        return false;
    }

    public static boolean isBetween(int a, int b, int numToCheck) {
        return (numToCheck > Math.min(a, b) && numToCheck < Math.max(a, b));
    }

    public boolean pointOnLine(Point point) {
        return this.a * point.x + this.b * point.y + this.c == 0;
    }
}
