package project;

import java.io.File;
import java.io.PrintWriter;
import java.util.concurrent.Callable;

public class FileWriterTask implements Callable<Long> {

    private final String filePath;

    public FileWriterTask(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public Long call() throws Exception {
        String input = "Hello world, This is the best java course on YouTube.";
        try (PrintWriter writer = new PrintWriter(filePath)) {
            for (int i = 0; i < 10000; i++) {
                writer.println(input);
            }
        }
        File file = new File(filePath);
        return file.length();
    }
}
