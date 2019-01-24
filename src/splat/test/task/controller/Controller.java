package splat.test.task.controller;

import splat.test.task.exeptions.ExceptionHandler;
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

    public String getKeyWord() {
        return model.getKeyWord();
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

    public void fireLoadTextToSelectedTab(Path path) {
        int selectedTab = mainFrame.getTextPanel().getSelectedTab();
        mainFrame.getTextPanel().setSelectedTabTitle(selectedTab, path.getFileName().toString());
        mainFrame.getTextPanel().clearTextPane(selectedTab);
        String[] text = model.loadTextFirstTime(path, selectedTab);
        mainFrame.getTextPanel().addTextToPane(text, selectedTab);
    }

    public void fireLoadNext() {
        int selectedTab = mainFrame.getTextPanel().getSelectedTab();
        mainFrame.getTextPanel().clearTextPane(selectedTab);
        String text[] = model.loadNext(selectedTab);
        if (text == null) {
            ExceptionHandler.logPaneExeption(mainFrame, "Not found");
        } else {
            mainFrame.getTextPanel().addTextToPane(text, selectedTab);
        }
    }

    public void fireLoadPrevious() {
        int selectedTab = mainFrame.getTextPanel().getSelectedTab();
        mainFrame.getTextPanel().clearTextPane(selectedTab);
        String text[] = model.loadPrevious(selectedTab);
        if (text == null) {
            ExceptionHandler.logPaneExeption(mainFrame, "Not found");
        } else {
            mainFrame.getTextPanel().addTextToPane(text, selectedTab);
        }
    }
}
