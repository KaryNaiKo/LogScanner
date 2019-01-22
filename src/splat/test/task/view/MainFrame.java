package splat.test.task.view;

import splat.test.task.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Controller controller;
    private TextPanel textPanel;
    private TreePanel treePanel;

    public MainFrame(Controller controller) {
        this.controller = controller;
        textPanel = new TextPanel(controller);
        treePanel = new TreePanel(controller);
        init();
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(600, 600));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        this.setTitle("LogScanner");

        initGUI();
        pack();
    }

    private void initGUI() {
        JScrollPane scrollTreePane = new JScrollPane(treePanel);

        this.add(new HeadPanel(controller), BorderLayout.NORTH);
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout());
        pane.add(scrollTreePane);
        pane.add(textPanel);
        this.add(pane, BorderLayout.CENTER);
    }

    public TextPanel getTextPanel() {
        return textPanel;
    }

    public TreePanel getTreePanel() {
        return treePanel;
    }
}
