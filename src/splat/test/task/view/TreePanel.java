package splat.test.task.view;

import javax.swing.*;
import java.awt.*;

public class TreePanel extends JPanel {
    private JTree jTree = new JTree();

    public TreePanel() {
        init();
    }

    private void init() {
        this.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 5)));
        this.setLayout(new GridLayout());
        this.add(jTree);
    }
}
