package splat.test.task.view;

import splat.test.task.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private JTextPane textPane = new JTextPane();
    private Controller controller;

    public TextPanel(Controller controller) {
        this.controller = controller;
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.setLayout(new GridLayout());
        this.add(textPane);
    }
}
