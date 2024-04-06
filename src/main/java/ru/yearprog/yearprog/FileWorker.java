package ru.yearprog.yearprog;

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
            for (Object object : objects) {
                if (object != null) {
                    if (object.getClass() == Point.class) {
                        Point point = (Point) ((Point) object).clone();
                        InputMiniWindow.demovePoint(point);
                        fooWriter.write(point.x + " " + point.y + "\n");
                    }

                    if (object.getClass() == Quadrilateral.class) {
                        Quadrilateral quadrilateral = (Quadrilateral) object;
                        fooWriter.write(quadrilateral +"\n");
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
            return fileChooser.getSelectedFile();
        } else if (close) {
            System.exit(130);
        }
        return null;
    }
}
