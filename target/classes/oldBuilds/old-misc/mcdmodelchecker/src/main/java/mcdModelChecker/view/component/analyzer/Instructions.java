package mcdModelChecker.view.component.analyzer;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Instructions {

    public JTextArea instructionsTextarea;
    public GridBagConstraints instructionsTextareaStyle;

    public Instructions() {
        instructionsTextarea = new JTextArea();
        instructionsTextareaStyle = new GridBagConstraints();
        instructionsTextareaStyle.gridx = 1;
        instructionsTextareaStyle.gridy = 1;
        instructionsTextareaStyle.fill = GridBagConstraints.HORIZONTAL;
        // instructionsTextarea.setFont(app.view.getMainPage().labelFont);
        Border instructionsBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
        instructionsBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
        instructionsTextarea.setBorder(BorderFactory.createCompoundBorder(instructionsBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        instructionsTextarea.setText("Select your file(s), and app.model below.");
        // mainContainers.sidebarInner.add(instructionsTextarea, instructionsTextareaStyle);
    }

}
