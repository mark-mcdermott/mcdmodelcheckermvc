package view;

import model.Model;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

public class View extends JFrame implements Observer {

    Components components;

    public View(Model model) {
        components = new Components();
        analyzerShell(this);
    }

    public void update(Observable o, Object arg) {

    }

    public void analyzerShell(JFrame frame) {
        components.sharedComponents(frame);
        components.analyzerComponents();
    }
}
