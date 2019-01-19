package splat.test.task.view;

import splat.test.task.exeptions.*;
import splat.test.task.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

public class HeadPanel extends JPanel {
    private JButton startScan = new JButton("Start scan");
    private JButton cancelScan = new JButton("Cancel");
    private JButton dirChoose = new JButton("...");
    private JTextField keyWord = new JTextField("", 25);
    private JTextField directory = new JTextField("", 25);
    private JTextField fileExtension = new JTextField(".log", 5);
    private JLabel enterExt = new JLabel("Enter file extension: ");
    private JLabel enterDir = new JLabel("Enter directory: ");
    private JLabel enterKey = new JLabel("Enter key word: ");

    private Controller controller;


    public HeadPanel(Controller controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        JPanel top = new JPanel();
        top.setLayout(new BorderLayout());
        JPanel topLeft = new JPanel(new FlowLayout());
        topLeft.add(enterDir);
        topLeft.add(directory);
        topLeft.add(dirChoose);
        top.add(topLeft, BorderLayout.WEST);
        top.add(startScan, BorderLayout.EAST);

        JPanel mid = new JPanel();
        JPanel midLeft = new JPanel();
        midLeft.setLayout(new FlowLayout());
        midLeft.add(enterKey);
        midLeft.add(keyWord);
        mid.setLayout(new BorderLayout());
        mid.add(midLeft, BorderLayout.WEST);
        mid.add(cancelScan, BorderLayout.EAST);
        mid.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        JPanel bottomLeft = new JPanel();
        bottomLeft.setLayout(new FlowLayout());
        bottomLeft.add(enterExt);
        bottomLeft.add(fileExtension);
        bottom.add(bottomLeft, BorderLayout.WEST);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(top);
        this.add(mid);
        this.add(bottom);

        addActions();
    }

    private void addActions() {
        dirChoose.addActionListener(new DirChooseListener());
        startScan.addActionListener(new StartScanListener());
        cancelScan.addActionListener(new CancelScanListener());
    }


    private class DirChooseListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);
            int ret = chooser.showDialog(null, "Choose directory");
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = chooser.getSelectedFile();
                directory.setText(file.toString());
            }
        }
    }

    private class StartScanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String dir = directory.getText();
            try {
                Path path = Paths.get(dir);
                controller.startScan(path, fileExtension.getText(), keyWord.getText());
            } catch (Exception e1) {
                ExceptionHandler.logPaneExeption(HeadPanel.this, "Incorrect directory");
            }
        }
    }

    private class CancelScanListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {

        }
    }
}
