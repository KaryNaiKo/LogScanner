package splat.test.task.controller;

import splat.test.task.view.MainFrame;

import java.nio.file.Path;

public class Controller {
    private MainFrame mainFrame;

    public void startScan(Path path, String fileExtension, String keyWord) {
        System.out.println(path);
        System.out.println(fileExtension);
        System.out.println(keyWord);
    }
}
