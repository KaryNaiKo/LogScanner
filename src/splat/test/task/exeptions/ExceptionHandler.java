package splat.test.task.exeptions;

import javax.swing.*;
import java.awt.*;

public class ExceptionHandler {
    public static void logExeption(String str) {
        System.out.println(str);
    }

    public static void logPaneExeption(Component com, String str) {
        JOptionPane.showMessageDialog(com, str);
    }
}
