package ru.yearprog.yearprog.data;

import java.awt.*;

public class Data {
    private static final int maxPoints = 200;
    private static Point[] points = new Point[maxPoints];
    private static int countOfPoints = 0;

    public static void resetPoints() {
        Data.points = new Point[Data.maxPoints];
        Data.countOfPoints = 0;
    }

    public static void setCountOfPoints(int countOfPoints) {
        Data.countOfPoints = countOfPoints;
    }

    public static int getMaxPoints() {
        return maxPoints;
    }

    public static Point[] getPoints() {
        return points;
    }

    public static int getCountOfPoints() {
        return countOfPoints;
    }

    public static void addPoint(Point p) {
        points[countOfPoints] = p;
        countOfPoints++;
    }
}
