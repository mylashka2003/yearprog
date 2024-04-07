package ru.yearprog.yearprog.input;

import ru.yearprog.yearprog.Main;
import ru.yearprog.yearprog.data.Data;
import ru.yearprog.yearprog.windows.MainFrame;

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
            Point[] pointsCur = new Point[Data.getMaxPoints()];
            int index = 0;

            while (fin.hasNext()) {
                if (index + Data.getCountOfPoints() >= Data.getMaxPoints()) {
                    JOptionPane.showMessageDialog(parentComponent, "Too many points!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }
                String[] sd = fin.nextLine().split(" ");
                if (sd.length != 2) {
                    JOptionPane.showMessageDialog(parentComponent, "Incorrect string format!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }

                try {
                    Point p = new Point(Integer.parseInt(sd[0]), Integer.parseInt(sd[1]));
                    if (Math.abs(p.x) > Main.getFieldSize() / 2 || Math.abs(p.y) > Main.getFieldSize() / 2) {
                        JOptionPane.showMessageDialog(parentComponent, "Out of bounce point!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                    }
                    pointsCur[index] = p;
                    index++;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parentComponent, "Incorrect number format point!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }
            }

            for (Point p : pointsCur) {
                if (p != null) {
                    InputMiniWindow.movePoint(p);
                    if (!Arrays.asList(Data.getPoints()).contains(p)) {
                        Data.addPoint(p);
                        MainFrame.setLastHandInputed(false);
                    }
                }
            }
            MainFrame.getPanel().repaint();
            fin.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(parentComponent, "Incorrect file!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void getRandomPoints(JFrame parentComponent) {
        if (1 > Data.getMaxPoints() - Data.getCountOfPoints()) {
            JOptionPane.showMessageDialog(parentComponent, "Too many points!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
        else {
            if (MainFrame.getInputRandomWindow() == null)
                MainFrame.setInputRandomWindow(new IntegerInput(1, Data.getMaxPoints() - Data.getCountOfPoints(), Input::generateRandomPoints, "Size", "Points count", true, false, 2));
            else
                MainFrame.getInputRandomWindow().setVisible(true);
        }
    }

    private static void generateRandomPoints(int value) {
        Set<Point> pointsSet = Arrays.stream(Data.getPoints()).collect(Collectors.toSet());
        pointsSet.remove(null);
        Random random = new Random();
        int size = Data.getCountOfPoints() + value;

        while (pointsSet.size() < size) {
            int x = random.nextInt(Main.getFieldSize());
            int y = random.nextInt(Main.getFieldSize());
            Point point = new Point(x, y);
            pointsSet.add(point);
        }

        Data.setCountOfPoints(size);
        Point[] pointsArray = new Point[pointsSet.size()];
        Point[] arr = pointsSet.toArray(pointsArray);
        Data.resetPoints();
        for (Point p : arr) {
            Data.addPoint(p);
            MainFrame.setLastHandInputed(false);
        }
        MainFrame.getPanel().repaint();
    }
}
