package ru.yearprog.yearprog;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWorker {
    public static void writeObjectToFile(File file, Object[] objects) {
        try {
            FileWriter fooWriter = new FileWriter(file, false);
            for (Object object : objects) {
                if (object != null) {
                    if (object.getClass() == Point.class) {
                        Point point = (Point) object;
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

    public static File openFile(JFrame parentComponent) {
        JFileChooser fileChooser = new JFileChooser();

        fileChooser.setDialogTitle("Choose file");
        if (fileChooser.showOpenDialog(parentComponent) == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }
}
