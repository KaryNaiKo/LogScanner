package splat.test.task;

import splat.test.task.controller.Controller;
import splat.test.task.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        new MainFrame(new Controller());
    }
}
