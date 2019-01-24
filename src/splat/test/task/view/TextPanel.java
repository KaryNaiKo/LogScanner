package splat.test.task.view;

import splat.test.task.controller.Controller;
import splat.test.task.exeptions.ExceptionHandler;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class TextPanel extends JPanel {
    private ArrayList<JTextPane> textPanes;
    private JTabbedPane tabbedPane;
    private Controller controller;
    private Style match;

    public TextPanel(Controller controller) {
        this.controller = controller;
        textPanes = new ArrayList<>();
        tabbedPane = new JTabbedPane();
        init();
        createStyle();
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
        JButton delete = new JButton("Delete Tab");
        delete.addActionListener(new DeleteActionListener());
        next.setMaximumSize(new Dimension(20, 20));
        head.add(previous);
        head.add(next);
        head.add(delete);

        JTextPane textPane1 = new JTextPane();
        textPanes.add(textPane1);
        JScrollPane scrollPane1 = new JScrollPane(textPane1);
        tabbedPane.addTab("", scrollPane1);

        JTextPane textPane2 = new JTextPane();
        textPanes.add(textPane2);
        JScrollPane scrollPane2 = new JScrollPane(textPane2);
        tabbedPane.addTab("create new", scrollPane2);

        tabbedPane.addChangeListener(new TabbedChangeListener());
        this.add(tabbedPane, BorderLayout.CENTER);
        this.add(head, BorderLayout.NORTH);
    }

    private void createStyle() {
        StyleContext sc = new StyleContext();
        match = sc.addStyle("Match", null);
        match.addAttribute(StyleConstants.Foreground, Color.red);
        match.addAttribute(StyleConstants.FontSize, 16);
        match.addAttribute(StyleConstants.FontFamily, "serif");
        match.addAttribute(StyleConstants.Bold, Boolean.TRUE);
    }

    public void addTextToTab(String[] str, int indexOfTab) {
        try {
            if (str != null) {
                JTextPane textPane = textPanes.get(indexOfTab);
                textPane.getDocument().insertString(0, str[0], null);
                textPane.getDocument().insertString(str[0].length(), str[1], match);
                textPane.getDocument().insertString(str[0].length() + str[1].length(), str[2], null);
            }
        } catch (BadLocationException e) {
            ExceptionHandler.logPaneExeption(this, "Unable to insert text to textPane");
        }
    }

    public void clearTextTab(int indexOfTab) {
        textPanes.get(indexOfTab).setText("");
    }

    public int getSelectedTab() {
        return tabbedPane.getSelectedIndex();
    }

    public void setSelectedTabTitle(int indexOfTab, String title) {
        tabbedPane.setTitleAt(indexOfTab, title);
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

    private class DeleteActionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            int selectedTab = tabbedPane.getSelectedIndex();
            tabbedPane.remove(selectedTab);
            textPanes.remove(selectedTab);
        }
    }

    private class TabbedChangeListener implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (tabbedPane.getSelectedIndex() == tabbedPane.getTabCount() - 1) {
                tabbedPane.setTitleAt(tabbedPane.getTabCount() - 1, "");
                JTextPane textPane = new JTextPane();
                textPanes.add(textPane);
                JScrollPane scrollPane = new JScrollPane(textPane);
                tabbedPane.addTab("create new", scrollPane);
            }
        }
    }
}
