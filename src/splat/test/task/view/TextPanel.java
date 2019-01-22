package splat.test.task.view;

import splat.test.task.controller.Controller;
import splat.test.task.exeptions.ExceptionHandler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
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

    public void addTextToPane(String str) {
        try {
            int offset = textPane.getDocument().getLength();
            textPane.getDocument().insertString(offset, str, null);
        } catch (BadLocationException e) {
            ExceptionHandler.logPaneExeption(this, "Unable to insert text to textPane");
        }
    }

    public void clearTextPane() {
        textPane.setText("");
    }
}
