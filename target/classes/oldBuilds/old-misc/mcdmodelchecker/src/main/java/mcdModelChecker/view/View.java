package mcdModelChecker.view;

import mcdModelChecker.McdModelCheckerApp;
import mcdModelChecker._options.Options;
import mcdModelChecker.controller.Controller;
import mcdModelChecker.model.Model;
import mcdModelChecker.view.pages.Analyzer;

import javax.swing.*;

import java.awt.*;

public class View extends McdModelCheckerApp {

    // the only global variable components are those adding
    // components from another method and components used by listeners
    public JFrame frame;
    public Options options;

    public View() {
        // this.options = new Options();
    }

    public void initialRender(Model model, Controller controller) {
        new Analyzer().renderAnalyzer(model, this, controller);
    }

    void initFrame() {
        frame = new JFrame();
        // frame.setPreferredSize(new Dimension(options.getWindowWidth(), options.getWindowHeight()));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
    }

    public void render() {
        frame.validate();
        frame.pack();
        frame.setVisible(true);
    }


}
