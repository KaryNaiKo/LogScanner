package splat.test.task.view;

import javax.swing.*;
import java.awt.*;

public class TextPanel extends JPanel {
    private JTextPane textPane = new JTextPane();

    public TextPanel() {
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.setLayout(new GridLayout());
        this.add(textPane);
    }
}
