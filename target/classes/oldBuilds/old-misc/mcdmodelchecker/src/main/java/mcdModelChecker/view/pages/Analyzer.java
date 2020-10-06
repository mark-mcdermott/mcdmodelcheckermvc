package mcdModelChecker.view.pages;

import mcdModelChecker.McdModelCheckerApp;
import mcdModelChecker.controller.Controller;
import mcdModelChecker.model.Model;
import mcdModelChecker.view.View;
import mcdModelChecker.view.component.analyzer.Instructions;
import mcdModelChecker.view.component.analyzer.Lists;
import mcdModelChecker.view.component.sharedComponents.ButtonsPanel;
import mcdModelChecker.view.component.sharedComponents.MainContainers;
import mcdModelChecker.view.types.AnalyzerState;

import javax.swing.*;
import java.awt.*;

public class Analyzer extends View {

    public MainContainers mainContainers;
    public ButtonsPanel buttonsPanel;
    public Instructions instructions;
    public Lists lists;

    public Analyzer() { }

    public void renderAnalyzer(Model model, View view, Controller controller) {

        shell();
        // content();
        // render();

    }

    void shell() {

        // init components
        mainContainers = new MainContainers();
        buttonsPanel = new ButtonsPanel();
        instructions = new Instructions();
        lists = new Lists();

        // rename major components to make the add components section below more readable
        JPanel mainPanel = mainContainers.mainPanel;
        String mainPanelStyle = BorderLayout.NORTH;
        JPanel sidebarInner = mainContainers.sidebarInner;
        JPanel buttons = buttonsPanel.panel;
        GridBagConstraints buttonsStyle = buttonsPanel.buttonsPanelStyle;
        JTextArea instuctions = instructions.instructionsTextarea;
        GridBagConstraints instructionsStyle = instructions.instructionsTextareaStyle;

        // add components
        view.frame.add(mainPanel, mainPanelStyle);
        sidebarInner.add(buttons, buttonsStyle);
        sidebarInner.add(instuctions, instructionsStyle);

    }









    void content(AnalyzerState state) {

    }

    void listeners(AnalyzerState state) {

    }

}
