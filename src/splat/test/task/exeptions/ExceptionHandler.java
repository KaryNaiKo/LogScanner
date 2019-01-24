package splat.test.task.exeptions;

        import javax.swing.*;
        import java.awt.*;

public class ExceptionHandler {
    public static void logExeption(Exception e) {
        e.printStackTrace();
    }

    public static void logPaneExeption(Component com, String str) {
        JOptionPane.showMessageDialog(com, str);
    }
}
