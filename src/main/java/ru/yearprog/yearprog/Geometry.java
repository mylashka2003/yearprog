package ru.yearprog.yearprog;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Geometry {
    // Проверка нахождения трёх точек на одной прямой
    private static boolean areCollinear(Point p1, Point p2, Point p3) {
        return triangleArea(p1, p2, p3) == 0;
    }

    // Вычисление площади треугольника
    private static double triangleArea(Point p1, Point p2, Point p3) {
        return Math.abs(p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y)) / 2.0;
    }

    // Вычисление площади выпуклого четырехугольника
    private static double convexQuadrilateralArea(Point p1, Point p2, Point p3, Point p4) {
        return triangleArea(p1, p2, p3) + triangleArea(p1, p3, p4);
    }

    // Проверка нахождения точки внутри треугольника
    private static boolean isPointInsideTriangle(Point p, Point p1, Point p2, Point p3) {
        double totalArea = triangleArea(p1, p2, p3);
        double area1 = triangleArea(p, p2, p3);
        double area2 = triangleArea(p1, p, p3);
        double area3 = triangleArea(p1, p2, p);

        return (area1 + area2 + area3) == totalArea;
    }

    // Вычисление максимальной площади и порядка точек невыпуклого четырехугольника
    private static QuadrilateralResult nonConvexQuadrilateralArea(Point p, Point p1, Point p2, Point p3) {
        double area1 = triangleArea(p1, p2, p) + triangleArea(p2, p3, p);
        double area2 = triangleArea(p1, p3, p) + triangleArea(p2, p3, p);
        double area3 = triangleArea(p1, p2, p) + triangleArea(p1, p3, p);

        if (area1 >= area2 && area1 >= area3) {
            return new QuadrilateralResult(area1, new Point[]{p, p1, p2, p3}, "nonConvex");
        } else if (area2 >= area1 && area2 >= area3) {
            return new QuadrilateralResult(area2, new Point[]{p, p1, p3, p2}, "nonConvex");
        } else {
            return new QuadrilateralResult(area3, new Point[]{p, p2, p1, p3}, "nonConvex");
        }
    }

    // Поиск крайних точек на прямой
    private static Point[] findExtremePoints(Point[] points) {
        Point minPoint = points[0];
        Point maxPoint = points[1];

        double directionX = maxPoint.x - minPoint.x;
        double directionY = maxPoint.y - minPoint.y;

        for (Point point : points) {
            double paramForMin = (minPoint.x * directionX + minPoint.y * directionY);
            double paramForMax = (maxPoint.x * directionX + maxPoint.y * directionY);
            double paramForCurrent = (point.x * directionX + point.y * directionY);

            if (paramForCurrent < paramForMin) {
                minPoint = point;
            }
            if (paramForCurrent > paramForMax) {
                maxPoint = point;
            }
        }
        return new Point[]{minPoint, maxPoint};
    }

    // Поиск средней точки
    private static Point findCenter(Point[] points) {
        int sumX = 0;
        int sumY = 0;
        for (Point p : points) {
            sumX += p.x;
            sumY += p.y;
        }
        return new Point(sumX / points.length, sumY / points.length);
    }

    // Сортировка точек по углу
    private static Point[] sortPoints(Point[] points) {
        final Point center = findCenter(points);

        List<Point> points1 = Arrays.asList(points);
        points1.sort((a, b) -> {
            double angleA = Math.atan2(a.y - center.y, a.x - center.x);
            double angleB = Math.atan2(b.y - center.y, b.x - center.x);

            return Double.compare(angleA, angleB);
        });

        return points1.toArray(new Point[0]);
    }

    // Основная проверка и подсчет площади
    public static QuadrilateralResult calculateQuadrilateralArea(Point p1, Point p2, Point p3, Point p4) {
        if (areCollinear(p1, p2, p3) || areCollinear(p1, p2, p4) ||
                areCollinear(p1, p3, p4) || areCollinear(p2, p3, p4)) {

            // Все точки на одной прямой -> Линия
            if (areCollinear(p1, p2, p3) && areCollinear(p1, p2, p4) &&
                    areCollinear(p1, p3, p4) && areCollinear(p2, p3, p4)) {
                return new QuadrilateralResult(0, findExtremePoints(new Point[]{p1, p2, p3, p4}), "line");
            }

            // Три точки на одной прямой -> Треугольник
            if (areCollinear(p1, p2, p3)) {
                Point[] p = findExtremePoints(new Point[]{p1, p2, p3});
                return new QuadrilateralResult(triangleArea(p[0], p[1], p4), new Point[]{p[0], p[1], p4}, "triangle");
            }
            if (areCollinear(p1, p2, p4)) {
                Point[] p = findExtremePoints(new Point[]{p1, p2, p4});
                return new QuadrilateralResult(triangleArea(p[0], p[1], p3), new Point[]{p[0], p[1], p3}, "triangle");
            }
            if (areCollinear(p1, p3, p4)) {
                Point[] p = findExtremePoints(new Point[]{p1, p3, p4});
                return new QuadrilateralResult(triangleArea(p[0], p[1], p2), new Point[]{p[0], p[1], p2}, "triangle");
            }
            if (areCollinear(p2, p3, p4)) {
                Point[] p = findExtremePoints(new Point[]{p2, p3, p4});
                return new QuadrilateralResult(triangleArea(p[0], p[1], p1), new Point[]{p[0], p[1], p1}, "triangle");
            }
        }

        // Точка внутри треугольника из других -> Невыпуклый
        if (isPointInsideTriangle(p1, p2, p3, p4)) return nonConvexQuadrilateralArea(p1, p2, p3, p4);
        if (isPointInsideTriangle(p2, p1, p3, p4)) return nonConvexQuadrilateralArea(p2, p1, p3, p4);
        if (isPointInsideTriangle(p3, p1, p2, p4)) return nonConvexQuadrilateralArea(p3, p1, p2, p4);
        if (isPointInsideTriangle(p4, p1, p2, p3)) return nonConvexQuadrilateralArea(p4, p1, p2, p3);

        // Выпуклый сортированный четырёхугольник
        Point[] sortedPoints = sortPoints(new Point[]{p1, p2, p3, p4});
        return new QuadrilateralResult(convexQuadrilateralArea(sortedPoints[0], sortedPoints[1], sortedPoints[2], sortedPoints[3]), sortedPoints, "convex");
    }
}