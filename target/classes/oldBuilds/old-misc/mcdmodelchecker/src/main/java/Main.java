import mcdModelChecker.McdModelCheckerApp;
import mcdModelChecker._options.Options;
import mcdModelChecker.controller.Controller;
import mcdModelChecker.model.Model;
import mcdModelChecker.utils.ListHelper;
import mcdModelChecker.view.View;

import javax.swing.*;

/**
 * @author      Mark McDermott {@literal mark@markmcdermott.io}
 * @version     0.2
 */
public class Main {

    /**
     * Java Swing base MVC code from https://www.tutorialspoint.com/explain-the-architecture-of-java-swing-in-java, accessed 9/17/20
     *
     * @param args generic blank string args for command line execution - nothing expected here for this app.
     */

    public static void main(String[] args) {



        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    new McdModelCheckerApp().runApp();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
