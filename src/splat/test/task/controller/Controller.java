package splat.test.task.controller;

import splat.test.task.model.Model;
import splat.test.task.view.MainFrame;

import java.nio.file.Path;
import java.util.concurrent.BlockingQueue;

public class Controller {
    private MainFrame mainFrame;
    private Model model;

    public Controller(Model model) {
        this.model = model;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public void startScan(Path path, String fileExtension, String keyWord) {
        model.scan(path, fileExtension, keyWord);
    }

    public BlockingQueue<Path> getContentForTree() {
        return model.getContentForTree();
    }

    public void stopScan() {
        model.stopScan();
    }

    public void clearJTree() {
        mainFrame.getTreePanel().clear();
    }

    public void uploadTextToTextPanel(Path path) {
        mainFrame.getTextPanel().clearTextPane();
        String text = model.loadTextFromFile(path);
        mainFrame.getTextPanel().addTextToPane(text);
    }
}
