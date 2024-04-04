package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Input {
    public static void readFile(File file, JFrame parentComponent) {
        try {
            Scanner fin = new Scanner(file);
            Point[] pointsCur = new Point[Main.getMaxPoints()];
            int index = 0;

            while (fin.hasNext()) {
                if (index + Main.getCountOfPoints() >= Main.getMaxPoints()) {
                    JOptionPane.showMessageDialog(parentComponent, "Слишком много точек!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }
                String[] sd = fin.nextLine().split(" ");
                if (sd.length != 2) {
                    JOptionPane.showMessageDialog(parentComponent, "Неверный формат строки!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }

                try {
                    Point p = new Point(Integer.parseInt(sd[0]), Integer.parseInt(sd[1]));
                    if (Math.abs(p.x) > Main.getFieldSize() / 2 || Math.abs(p.y) > Main.getFieldSize() / 2) {
                        JOptionPane.showMessageDialog(parentComponent, "Некорректная точка в файле!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                    }
                    pointsCur[index] = p;
                    index++;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная точка в файле!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }
            }

            for (Point p : pointsCur) {
                if (p != null) {
                    InputMiniWindow.movePoint(p);
                    if (!Arrays.asList(Main.getPoints()).contains(p)) Main.addPoint(p);
                }
            }
            MainFrame.getPanel().repaint();
            fin.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(parentComponent, "Некорректно выбран файл!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void getRandomPoints() {
        new IntegerInput(1, Main.getMaxPoints() - Main.getCountOfPoints(), Input::generateRandomPoints, "Size", "Points count", true);
    }

    private static void generateRandomPoints(int value) {
        Set<Point> pointsSet = Arrays.stream(Main.getPoints()).collect(Collectors.toSet());
        pointsSet.remove(null);
        Random random = new Random();
        int size = Main.getCountOfPoints() + value;

        while (pointsSet.size() < size) {
            int x = random.nextInt(Main.getFieldSize());
            int y = random.nextInt(Main.getFieldSize());
            Point point = new Point(x, y);
            pointsSet.add(point);
        }

        Main.setCountOfPoints(size);
        Point[] pointsArray = new Point[pointsSet.size()];
        Point[] arr = pointsSet.toArray(pointsArray);
        Main.points = new Point[Main.getMaxPoints()];
        System.arraycopy(arr, 0, Main.points, 0, Main.getCountOfPoints());
        MainFrame.getPanel().repaint();
    }
}
