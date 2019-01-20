package splat.test.task;

import splat.test.task.controller.Controller;
import splat.test.task.model.Model;
import splat.test.task.view.MainFrame;

public class Main {
    public static void main(String[] args) {
        Model model = new Model();
        Controller controller = new Controller(model);
        MainFrame view = new MainFrame(controller);
        controller.setMainFrame(view);
        model.setController(controller);
    }
}
