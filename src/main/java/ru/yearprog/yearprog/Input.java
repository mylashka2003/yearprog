package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;

public class Input {
    public static void readFile(File file, JFrame parentComponent) {
        Scanner fin;

        try {
            fin = new Scanner(file);
            Point[] pointsCur = new Point[1000];
            int index = 0;

            while (fin.hasNext()) {
                if (index + Main.countOfPoints >= 1000) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String s = fin.nextLine();
                String[] sd = s.split(" ");

                if (sd.length != 2) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    int x = Integer.parseInt(sd[0]);
                    int y = Integer.parseInt(sd[1]);
                    Point p = new Point(x, y);
                    if (Math.abs(p.x) > Main.fieldSize / 2 || Math.abs(p.y) > Main.fieldSize / 2) {
                        JOptionPane.showMessageDialog(parentComponent, "Некорректная точка в файле!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    pointsCur[index] = new Point(x, y);
                    index++;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            if (index < 4) {
                JOptionPane.showMessageDialog(parentComponent, "Введено слишком мало точек!", "Error!", JOptionPane.ERROR_MESSAGE);
            }

            // Запись в основной массив
            for (Point p : pointsCur) {
                Main.addPoint(p);
            }

            fin.close();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(parentComponent, "Некорректно выбран файл!", "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void getRandomPoints() {
        class CountInput extends JFrame {
            public CountInput() {
                super("How many");

                JPanel panel = new JPanel(true);
                panel.setPreferredSize(new Dimension(200, 70));
                this.add(panel);
                this.setResizable(false);
                this.pack();
                panel.setLayout(null);

                Font labelFont = new Font("Liberation Mono", Font.BOLD, 20);
                JButton input = new JButton("Input");
                input.setBounds(100, 0, 100, 70);
                input.setFont(labelFont);

                SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
                JSpinner spinner = new JSpinner(model);
                spinner.setBounds(0, 0, 100, 70);
                spinner.setFont(labelFont);

                panel.add(input);
                panel.add(spinner);

                input.addActionListener(e -> {
                    generateRandomPoints((Integer) spinner.getValue());
                    Main.f.repaint();
                    this.dispose();
                });

                this.setVisible(true);
            }

            private void generateRandomPoints(int value) {
                Set<Point> pointsSet = Arrays.stream(Main.points).collect(Collectors.toSet());
                pointsSet.remove(null);
                Random random = new Random();
                int size = Main.countOfPoints + value;

                while (pointsSet.size() < size) {
                    int x = random.nextInt(Main.fieldSize + 1);
                    int y = random.nextInt(Main.fieldSize + 1);
                    Point point = new Point(x, y);
                    //InputMiniWindow.movePoint(point);
                    pointsSet.add(point);
                }

                Main.countOfPoints = size;
                Point[] pointsArray = new Point[pointsSet.size()];
                Point[] arr = pointsSet.toArray(pointsArray);
                Main.points = new Point[1000];
                if (Main.countOfPoints >= 0) System.arraycopy(arr, 0, Main.points, 0, Main.countOfPoints);
            }
        }

        new CountInput();
    }
}