package splat.test.task.model;

import splat.test.task.controller.Controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Model {
    private Controller controller;
    private BlockingQueue<Path> queue;
    private ScanFileVisitor visitor;

    public Model() {
        queue = new ArrayBlockingQueue<>(1000);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void scan(Path path, String fileExtension, String keyWord) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                visitor = new ScanFileVisitor(keyWord, fileExtension, queue);
                try {
                    Files.walkFileTree(path,  visitor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

    public BlockingQueue<Path> getContentForTree() {
        return queue;
    }

    public void stopScan() {
        visitor.stop();
    }

    public String loadTextFromFile(Path path) {
        try {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(path.toFile())))) {
                StringBuilder sb = new StringBuilder();
                for (String line; (line = reader.readLine()) != null; ) {
                    sb.append(line).append('\n');
                }
                return sb.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }
}
