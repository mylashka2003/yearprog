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
            Point[] pointsCur = new Point[1000];
            int index = 0;

            while (fin.hasNext()) {
                if (index + Main.countOfPoints > 150) {
                    JOptionPane.showMessageDialog(parentComponent, "Слишком много точек!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }
                String[] sd = fin.nextLine().split(" ");
                if (sd.length != 2) {
                    JOptionPane.showMessageDialog(parentComponent, "Неверный формат строки!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }

                try {
                    Point p = new Point(Integer.parseInt(sd[0]), Integer.parseInt(sd[1]));
                    if (Math.abs(p.x) > Main.fieldSize / 2 || Math.abs(p.y) > Main.fieldSize / 2) {
                        JOptionPane.showMessageDialog(parentComponent, "Некорректная точка в файле!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                    }
                    pointsCur[index] = p;
                    index++;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная точка в файле!", "Error!", JOptionPane.ERROR_MESSAGE); return;
                }
            }

            if (index < 4) {
                JOptionPane.showMessageDialog(parentComponent, "Введено слишком мало точек!", "Error!", JOptionPane.ERROR_MESSAGE); return;
            }
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
                panel.setLayout(new GridLayout(1,2));

                Font labelFont = new Font("Comic Sans MS", Font.BOLD, 20);
                JButton input = new JButton("Input");
                input.setFont(labelFont);

                SpinnerModel model = new SpinnerNumberModel(1, 1, 100, 1);
                JSpinner spinner = new JSpinner(model);
                spinner.setFont(labelFont);

                panel.add(spinner);
                panel.add(input);

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
                    pointsSet.add(point);
                }

                Main.countOfPoints = size;
                Point[] pointsArray = new Point[pointsSet.size()];
                Point[] arr = pointsSet.toArray(pointsArray);
                Main.points = new Point[1000];
                System.arraycopy(arr, 0, Main.points, 0, Main.countOfPoints);
            }
        }

        new CountInput();
    }
}