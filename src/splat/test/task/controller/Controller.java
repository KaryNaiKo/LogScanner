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

    public void fireStopScan() {
        model.stopScan();
    }

    public void fireClearJTree() {
        mainFrame.getTreePanel().clear();
    }

    public void uploadTextToTextPanel(Path path) {
        mainFrame.getTextPanel().clearTextPane();
        String text = model.loadTextFirstTime(path, 0);
        mainFrame.getTextPanel().addTextToPane(text);
    }

    public void fireLoadNext() {
        mainFrame.getTextPanel().clearTextPane();
        String text = model.loadNext(0);
        mainFrame.getTextPanel().addTextToPane(text);
    }
    
    public void fireLoadPrevious() {
        mainFrame.getTextPanel().clearTextPane();
        String text = model.loadPrevious(0);
        mainFrame.getTextPanel().addTextToPane(text);
    }
}
