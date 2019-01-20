package splat.test.task.model;

import splat.test.task.controller.Controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Model {
    private Controller controller;
    private BlockingQueue<Path> queue;

    public Model() {
        queue = new ArrayBlockingQueue<>(100);
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void scan(Path path, String fileExtension, String keyWord) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ScanFIleVisitor visitor = new ScanFIleVisitor(keyWord, fileExtension, queue);
                try {
                    Files.walkFileTree(path,  visitor);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }
}
