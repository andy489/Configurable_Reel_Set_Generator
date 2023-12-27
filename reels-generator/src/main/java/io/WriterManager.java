package io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class WriterManager {
    public static void writeResults(String resultPath, StringBuilder sb) {
        Path resultAsPath = Paths.get(resultPath);

        if (!Files.exists(resultAsPath)) {
            File f = new File(resultPath);
            boolean genParDirs = f.getParentFile().mkdirs();
            // System.out.println("Created file: " + f.getName());
        }

        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(resultPath, true)))) {
            writer.write(String.valueOf(sb));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
