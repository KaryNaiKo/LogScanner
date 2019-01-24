package splat.test.task.view;

import splat.test.task.controller.Controller;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;
import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.BlockingQueue;

public class TreePanel extends JPanel {
    private JTree jTree;
    private DefaultMutableTreeNode rootNode;
    private DefaultTreeModel treeModel;
    private BlockingQueue<Path> queue;
    private Controller controller;

    public TreePanel(Controller controller) {
        this.controller = controller;
        this.queue = controller.getContentForTree();
        init();
    }

    private void init() {
        this.setBorder((BorderFactory.createEmptyBorder(0, 0, 0, 5)));
        this.setLayout(new GridLayout());


        rootNode = new DefaultMutableTreeNode("found files");
        treeModel = new DefaultTreeModel(rootNode);
        jTree = new JTree(treeModel);
        jTree.setRootVisible(false);
        this.add(jTree);

        jTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        jTree.addTreeSelectionListener(new TreeSelectionListener() {
            @Override
            public void valueChanged(TreeSelectionEvent e) {
                System.out.println("You are selected " + jTree.getLastSelectedPathComponent());
                if (jTree.getLastSelectedPathComponent() != null) {
                    Object[] pathPieces = jTree.getSelectionPath().getPath();
                    StringBuilder sb = new StringBuilder();
                    for (int i = 1; i < pathPieces.length; i++) {
                        sb.append(pathPieces[i]).append('\\');
                    }
                    String strPath = sb.substring(0, sb.length() - 1);
                    Path path = Paths.get(strPath);
                    if (Files.isRegularFile(path)) {
                        controller.fireLoadTextToSelectedTab(path);
                    }
                }
            }
        });

        Thread treePopulator = new Thread(() -> {
            while (true) {
                if (!queue.isEmpty()) {
                    Path path = queue.poll();
                    addToTreePanel(path);
                    treeModel.reload();
                    System.out.println("poll from queue " + path.toString());
                }
            }
        });
        treePopulator.start();
    }

    private void addToTreePanel(Path path) {
        DefaultTreeModel model = (DefaultTreeModel) jTree.getModel();
        DefaultMutableTreeNode root = (DefaultMutableTreeNode) model.getRoot();
        if (root == null) {
            root = new DefaultMutableTreeNode(".");
        }
        String[] splittedFileName = path.toFile().getAbsolutePath().split("[\\\\]");
        DefaultMutableTreeNode cur = root.getNextNode();
        int i = 0;
        if (cur != null) {
            DefaultMutableTreeNode node = null;
            String curFile = null;
            boolean hasSame;
            for (; cur != null && i < splittedFileName.length; cur = cur.getNextNode(), i++) {
                node = cur;
                curFile = splittedFileName[i];
                hasSame = false;
                do {
                    if (node.toString().equals(curFile)) {
                        cur = node;
                        hasSame = true;
                        break;
                    }
                } while (((node = node.getNextSibling()) != null));
                if (!hasSame) {
                    cur = cur.getPreviousNode();
                    break;
                }
            }

        } else
            cur = root;
        while (i < splittedFileName.length) {
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(splittedFileName[i++]);
            cur.add(node);
            cur = node;
        }
    }

    public void clear() {
        rootNode.removeAllChildren();
        treeModel.reload();
    }
}
