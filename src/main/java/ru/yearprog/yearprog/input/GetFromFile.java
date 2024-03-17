package ru.yearprog.yearprog.input;

import ru.yearprog.yearprog.Main;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GetFromFile {
    public static boolean readFile(File file, InputSelection parentComponent) {
        Scanner fin;

        try {
            fin = new Scanner(file);
            Point[] pointsCur = new Point[100];
            int index = 0;

            while (fin.hasNext()) {
                if (index >= 1000) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                String s = fin.nextLine();
                String[] sd = s.split(" ");

                if (sd.length != 2) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }

                try {
                    int x = Integer.parseInt(sd[0]);
                    int y = Integer.parseInt(sd[1]);
                    Point p = new Point(x ,y);
                    if (Math.abs(p.x) > 500 || Math.abs(p.y) > 500) {
                        JOptionPane.showMessageDialog(parentComponent, "Некорректная точка в файле!", "Error!", JOptionPane.ERROR_MESSAGE);
                        return false;
                    }
                    pointsCur[index] = new Point(x, y);
                    index++;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!", "Error!", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }

            if (index < 4) {
                JOptionPane.showMessageDialog(parentComponent, "Введено слишком мало точек!", "Error!", JOptionPane.ERROR_MESSAGE);
            }

            // Запись в основной массив
            Main.points = pointsCur;
            Main.count = index - 1;

            fin.close();
            parentComponent.dispose();
            return true;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(parentComponent, "Некорректно выбран файл!", "Error!", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
