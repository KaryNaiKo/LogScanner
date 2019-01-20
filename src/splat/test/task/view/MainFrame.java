package splat.test.task.view;

import splat.test.task.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private Controller controller;

    public MainFrame(Controller controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(600, 600));
        this.getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        initGUI();
        pack();
    }

    private void initGUI() {
        JScrollPane scrollTextPane = new JScrollPane(new TextPanel());
        JScrollPane scrollTreePane = new JScrollPane(new TreePanel());

        this.add(new HeadPanel(controller), BorderLayout.NORTH);
        JPanel pane = new JPanel();
        pane.setLayout(new GridLayout());
        pane.add(scrollTreePane);
        pane.add(scrollTextPane);
        this.add(pane, BorderLayout.CENTER);
    }

}
