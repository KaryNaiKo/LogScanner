package splat.test.task.view;

import splat.test.task.controller.Controller;
import splat.test.task.exeptions.ExceptionHandler;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TextPanel extends JPanel {
    private JTextPane textPane;
    private JTabbedPane tabbedPane;
    private Controller controller;

    public TextPanel(Controller controller) {
        this.controller = controller;
        textPane = new JTextPane();
        tabbedPane = new JTabbedPane();
        init();
    }

    private void init() {
        this.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        this.setLayout(new BorderLayout());

        JPanel head = new JPanel();
        head.setLayout(new FlowLayout(FlowLayout.LEFT));
        JButton previous = new JButton("Previous");
        previous.addActionListener(new PreviousActionListener());
        previous.setMaximumSize(new Dimension(20, 20));
        JButton next = new JButton("Next");
        next.addActionListener(new NextActionListener());
        next.setMaximumSize(new Dimension(20, 20));
        head.add(previous);
        head.add(next);

        JScrollPane scrollPane = new JScrollPane(textPane);
        //tabbedPane.addTab("test", scrollPane);
        this.add(tabbedPane, BorderLayout.CENTER);
        this.add(head, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
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

    private class NextActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.fireLoadNext();
        }
    }

    private class PreviousActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            controller.fireLoadPrevious();
        }
    }
}
