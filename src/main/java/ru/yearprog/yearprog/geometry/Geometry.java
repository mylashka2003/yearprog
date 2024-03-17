package ru.yearprog.yearprog.geometry;

import java.awt.Point;
import java.util.Arrays;
import java.util.List;

public class Geometry {
    public static boolean areCollinear(Point p1, Point p2, Point p3) {
        return triangleArea(p1, p2, p3) == 0;
    }

    public static double triangleArea(Point p1, Point p2, Point p3) {
        return Math.abs(p1.x * (p2.y - p3.y) + p2.x * (p3.y - p1.y) + p3.x * (p1.y - p2.y)) / 2.0;
    }

    public static boolean isPointInsideTriangle(Point p, Point p1, Point p2, Point p3) {
        double totalArea = triangleArea(p1, p2, p3);
        double area1 = triangleArea(p, p2, p3);
        double area2 = triangleArea(p1, p, p3);
        double area3 = triangleArea(p1, p2, p);
        //System.out.println(p + " other " + p1 + " " + p2 + " " + p3);
        //System.out.println(totalArea + " " + area1 + " " + area2 + " " + area3);
        //System.out.println("--------------");

        return (area1 + area2 + area3) == totalArea;
    }

    public static double convexQuadrilateralArea(Point p1, Point p2, Point p3, Point p4) {
        return triangleArea(p1, p2, p3) + triangleArea(p1, p3, p4);
    }

    public static QuadrilateralResult nonconvexQuadrilateralArea(Point p, Point p1, Point p2, Point p3) {
        double area1 = triangleArea(p1, p2, p) + triangleArea(p2, p3, p);
        double area2 = triangleArea(p1, p3, p) + triangleArea(p2, p3, p);
        double area3 = triangleArea(p1, p2, p) + triangleArea(p1, p3, p);

        if (area1 >= area2 && area1 >= area3) {
            return new QuadrilateralResult(area1, new Point[]{p, p1, p2, p3});
        } else if (area2 >= area1 && area2 >= area3) {
            return new QuadrilateralResult(area2, new Point[]{p, p1, p3, p2});
        } else {
            return new QuadrilateralResult(area3, new Point[]{p, p2, p1, p3});
        }
    }

    public static Point[] findExtremePoints(Point[] points) {
        // Проверяем, что у нас есть хотя бы две точки для сравнения
        if (points.length < 2) return points;

        // Выбираем две первые точки для начального определения крайних точек
        Point minPoint = points[0];
        Point maxPoint = points[1];

        // Расчитываем вектор, коллинеарный прямой
        double directionX = maxPoint.x - minPoint.x;
        double directionY = maxPoint.y - minPoint.y;

        for (Point point : points) {
            // Расчитываем параметры для точек на прямой
            double paramForMin = (minPoint.x * directionX + minPoint.y * directionY);
            double paramForMax = (maxPoint.x * directionX + maxPoint.y * directionY);
            double paramForCurrent = (point.x * directionX + point.y * directionY);

            // Сравниваем параметры и обновляем крайние точки при необходимости
            if (paramForCurrent < paramForMin) {
                minPoint = point;
            }
            if (paramForCurrent > paramForMax) {
                maxPoint = point;
            }
        }

        // Возвращаем массив с крайними точками
        return new Point[]{minPoint, maxPoint};
    }

    public static QuadrilateralResult calculateQuadrilateralArea(Point p1, Point p2, Point p3, Point p4) {
        // Проверяем коллинеарность всех возможных троек точек
        // Если находим коллинеарные тройки - возвращаем специальные значения площадей
        if (areCollinear(p1, p2, p3) || areCollinear(p1, p2, p4) ||
                areCollinear(p1, p3, p4) || areCollinear(p2, p3, p4)) {
            // Все точки коллинеарны, возвращаем 0
            if (areCollinear(p1, p2, p3) && areCollinear(p1, p2, p4) &&
                    areCollinear(p1, p3, p4) && areCollinear(p2, p3, p4)) {
                return new QuadrilateralResult(0, findExtremePoints(new Point[]{p1, p2, p3, p4}));
            }

            // Ищем неколлинеарную четвертую точку и возвращаем площадь треугольника
            if (areCollinear(p1, p2, p3)) {
                Point[] p = findExtremePoints(new Point[]{p1, p2, p3});
                return new QuadrilateralResult(triangleArea(p[0], p[1], p4), new Point[]{p[0], p[1], p4});
            }
            if (areCollinear(p1, p2, p4)) {
                Point[] p = findExtremePoints(new Point[]{p1, p2, p4});
                return new QuadrilateralResult(triangleArea(p[0], p[1], p3), new Point[]{p[0], p[1], p3});
            }
            if (areCollinear(p1, p3, p4)) {
                Point[] p = findExtremePoints(new Point[]{p1, p3, p4});
                return new QuadrilateralResult(triangleArea(p[0], p[1], p2), new Point[]{p[0], p[1], p2});
            }
            if (areCollinear(p2, p3, p4)) {
                Point[] p = findExtremePoints(new Point[]{p2, p3, p4});
                return new QuadrilateralResult(triangleArea(p[0], p[1], p1), new Point[]{p[0], p[1], p1});
            }
        }

        // Проверяем, лежит ли одна из точек внутри треугольника, образованного тремя другими

        if (isPointInsideTriangle(p1, p2, p3, p4)) return nonconvexQuadrilateralArea(p1, p2, p3, p4);
        if (isPointInsideTriangle(p2, p1, p3, p4)) return nonconvexQuadrilateralArea(p2, p1, p3, p4);
        if (isPointInsideTriangle(p3, p1, p2, p4)) return nonconvexQuadrilateralArea(p3, p1, p2, p4);
        if (isPointInsideTriangle(p4, p1, p2, p3)) return nonconvexQuadrilateralArea(p4, p1, p2, p3);

        // Если четырехугольник выпуклый, возвращаем его площадь
        // Вам потребуется функция для выявления правильного порядка точек
        // Предположим, что функция sortPoints возвращает точки в правильном порядке
        Point[] sortedPoints = sortPoints(new Point[]{p1, p2, p3, p4});
        return new QuadrilateralResult(convexQuadrilateralArea(sortedPoints[0], sortedPoints[1], sortedPoints[2], sortedPoints[3]), sortedPoints);
    }

    public static Point findCenter(Point[] points) {
        int sumX = 0;
        int sumY = 0;
        for (Point p : points) {
            sumX += p.x;
            sumY += p.y;
        }
        return new Point(sumX / points.length, sumY / points.length);
    }

    public static Point[] sortPoints(Point[] points) {
        final Point center = findCenter(points);

        List<Point> points1 = Arrays.asList(points);
        points1.sort((a, b) -> {
            double angleA = Math.atan2(a.y - center.y, a.x - center.x);
            double angleB = Math.atan2(b.y - center.y, b.x - center.x);

            return Double.compare(angleA, angleB);
        });

        return points1.toArray(new Point[0]);
    }

    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(3, 0);
        Point p3 = new Point(1, 3);
        Point p4 = new Point(1, 1);

        QuadrilateralResult res = calculateQuadrilateralArea(p1, p2, p3, p4);
        for (Point point : res.points) {
            System.out.print(point + " ");
        }
        System.out.println();
        System.out.println(res.area);
    }
}