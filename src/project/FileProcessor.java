package project;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class FileProcessor {

    private static final ExecutorService service = Executors.newFixedThreadPool(8);

    // process the files
    public static void process() {
        List<Future<Long>> futures = new ArrayList<>();
        for (int i = 1; i <= 1000; i++) {
            String filePath = String.format("files/%d.txt", i);
            FileWriterTask task = new FileWriterTask(filePath);
            Future<Long> future = service.submit(task);
            futures.add(future);
        }
        try {
            long total = 0;
            for (Future<Long> f : futures) {
                long size = f.get();
                total = total + size;
            }
            System.out.println("Total bytes written: " + total);
        } catch (Exception ex) {
            System.out.println("Exception: " + ex.getMessage());
        } finally {
            service.shutdown();
            try {
                boolean flag = service.awaitTermination(10, TimeUnit.SECONDS);
                if (!flag) {
                    service.shutdownNow();
                }
            } catch (Exception ex) {
                System.out.println("Exception while shutting down: " + ex.getMessage());
            }
        }
    }
}
