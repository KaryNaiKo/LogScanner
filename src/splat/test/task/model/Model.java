package splat.test.task.model;

import splat.test.task.controller.Controller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Model {
    private Controller controller;
    private BlockingQueue<Path> queue;
    private ScanFileVisitor visitor;
    private String keyWord;
    private Map<Integer, TabHandler> tabHandlerMap;

    public Model() {
        queue = new ArrayBlockingQueue<>(1000);
        tabHandlerMap = new HashMap<>();
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public void scan(Path path, String fileExtension, String keyWord) {
        this.keyWord = keyWord;
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

    public String loadTextFirstTime(Path path, int indexOfTab) {
        try {
            TabHandler tab = new TabHandler(keyWord, path);
            tabHandlerMap.put(indexOfTab, tab);
            return tab.loadFirst();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public String loadNext(int indexOfTab) {
        TabHandler tab = tabHandlerMap.get(indexOfTab);
        if (tab != null) {
            return tab.loadNext();
        }
        return "";
    }

    public String loadPrevious(int indexOfTab) {
        TabHandler tab = tabHandlerMap.get(indexOfTab);
        if (tab != null) {
            return tab.loadPrevious();
        }
        return "";
    }
}
