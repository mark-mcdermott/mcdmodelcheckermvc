import _options.Options;
import controller.Controller;
import controller.helpers.McdException;
import model.Model;
import org.xml.sax.SAXException;
import view.View;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Options options = new Options();

                // java swing base mvc code from https://www.tutorialspoint.com/explain-the-architecture-of-java-swing-in-java
                Model model = new Model();
                View view = new View();
                try {
                    new Controller(model, view, options);
                } catch (FileNotFoundException | McdException | ParseException e) {
                    e.printStackTrace();
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }

        });

    }

}
