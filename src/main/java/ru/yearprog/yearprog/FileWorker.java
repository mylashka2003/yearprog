package ru.yearprog.yearprog;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileWorker {
    public static void writeObjectToFile(File file, Object[] objects) {
        try {
            FileWriter fooWriter = new FileWriter(file, false);
            for (Object object : objects) {
                fooWriter.write(object.toString());
            }
            fooWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
