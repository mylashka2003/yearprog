package ru.yearprog.yearprog.workers;

import ru.yearprog.yearprog.input.InputMiniWindow;
import ru.yearprog.yearprog.data.Quadrilateral;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class FileWorker {
    public static void writeObjectToFile(File file, Object[] objects) {
        try {
            FileWriter fooWriter = new FileWriter(file, false);
            for (int i = 0; i < objects.length; i++) {
                if (objects[i] != null) {
                    if (objects[i] instanceof Point) {
                        Point point = (Point) ((Point) objects[i]).clone();
                        InputMiniWindow.demovePoint(point);
                        fooWriter.write(point.x + " " + point.y + "\n");
                    }

                    if (objects[i] instanceof Quadrilateral quadrilateral) {
                        if (i != objects.length - 1) fooWriter.write(quadrilateral +"\n");
                        else {
                            fooWriter.write("-----");
                            fooWriter.write(quadrilateral.toString());
                        }
                    }
                }
            }
            fooWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static File openFile(JFrame parentComponent, CountDownLatch countDownLatch, boolean close) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Choose file");
        if (fileChooser.showOpenDialog(parentComponent) == JFileChooser.APPROVE_OPTION) {
            if (countDownLatch != null) countDownLatch.countDown();
            String name = fileChooser.getSelectedFile().getName();
            if (!name.endsWith("txt")) {
                JOptionPane.showMessageDialog(parentComponent, "Not .txt file!", "Error!", JOptionPane.ERROR_MESSAGE);
                return null;
            }
            return fileChooser.getSelectedFile();
        } else if (close) {
            System.exit(130);
        }
        return null;
    }
}
