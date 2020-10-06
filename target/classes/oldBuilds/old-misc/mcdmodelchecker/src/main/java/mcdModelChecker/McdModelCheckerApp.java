package mcdModelChecker;

import mcdModelChecker._options.Options;
import mcdModelChecker.controller.Controller;
import mcdModelChecker.model.Model;
import mcdModelChecker.utils.ListHelper;
import mcdModelChecker.view.View;

import javax.swing.*;

/**
 * Base superclass of the app, used to instantiate {@link Options} and {@link ListHelper} just one time.
 */
public class McdModelCheckerApp {

    public Model model;
    public View view;
    public Controller controller;
    public Options options;
    public ListHelper listHelper;

    public McdModelCheckerApp() {
        this.model = model;
        this.view = view;
    }

    public void runApp() {
        
    }



}
