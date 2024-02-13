package ru.yearprog.File;

import ru.yearprog.Main;
import ru.yearprog.startWindow.StartWindow;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ContentFile  {
    public static boolean readFile(File file, StartWindow parentComponent) {
        Scanner fin;

        try {
            fin = new Scanner(file);
            Point[] pointsCur = new Point[100];
            int index = 0;

            while (fin.hasNext()) {
                if (index >= 1000) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!");
                    return false;
                }

                String s = fin.nextLine();
                String[] sd = s.split(" ");

                if (sd.length != 2) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!");
                    return false;
                }

                try {
                    int x = Integer.parseInt(sd[0]);
                    int y = Integer.parseInt(sd[1]);
                    pointsCur[index] = new Point(x, y);
                    index++;
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(parentComponent, "Некорректная структура в файла!");
                    return false;
                }
            }

            Main.points = pointsCur;
            Main.indexOf = index - 1;

            fin.close();
            parentComponent.dispose();
            return true;
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(parentComponent, "Некорректно выбран файл!");
            return false;
        }
    }
}
