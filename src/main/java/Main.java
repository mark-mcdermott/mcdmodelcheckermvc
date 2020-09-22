import controller.Controller;
import model.Model;
import view.View;

import javax.swing.*;

public class Main {

    // mvc code/approach adapted from https://github.com/nap/SimpleMVC & https://stackoverflow.com/a/3072979
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Model model = new Model();
                View view = new View(model);
                Controller controller = new Controller(model,view);

                model.addObserver(view);
                view.setVisible(true);
            }
        });
    }
}
