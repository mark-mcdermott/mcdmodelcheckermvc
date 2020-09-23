import controller.Controller;
import controller.utils.ExceptionMessage;
import model.Model;
import org.xml.sax.SAXException;
import view.View;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main {

    // mvc code/approach adapted from https://github.com/nap/SimpleMVC & https://stackoverflow.com/a/3072979
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Model model = new Model();
                View view = new View(model);
                model.addObserver(view);
                try {
                    Controller controller = new Controller(model,view);
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (ExceptionMessage exceptionMessage) {
                    exceptionMessage.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                }

                view.setVisible(true);
            }
        });
    }
}
