package view;

import javax.swing.*;
import java.awt.*;

public class Components {

    public JPanel mainPanel;
    public JPanel buttonsPanel;
    public GridBagConstraints buttonsPanelStyle;
    public JPanel sidebarWrapper;
    public JPanel sidebar;
    public JPanel sidebarInner;
    public JPanel sidebarPaddingLeft;
    public JPanel sidebarPaddingRight;
    public JTextArea instructionsTextarea;
    public GridBagConstraints instructionsTextareaStyle;
    

    // shared components
    public void mainPanel() {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
    }

    public void buttonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanelStyle = new GridBagConstraints();
        buttonsPanelStyle.gridx = 1;
        buttonsPanelStyle.gridy = 0;
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(7, 10, 6, 10));

        JButton analyzerButton = new JButton();
        GridBagConstraints analyzerButtonStyle = new GridBagConstraints();
        analyzerButton.setText("Analyzer");
        buttonsPanel.add(analyzerButton);

        JButton testerButton = new JButton();
        GridBagConstraints testerButtonStyle = new GridBagConstraints();
        testerButton.setText("Tests");
        buttonsPanel.add(testerButton);
    }

    // analyzer components
    public void analyzerSidebarContainer() {
        JPanel sidebarWrapper = new JPanel();
        sidebarWrapper.setLayout(new GridBagLayout());
        GridBagConstraints sidebarWrapperStyle = new GridBagConstraints();
        sidebarWrapperStyle.anchor = GridBagConstraints.NORTH;
        sidebarWrapperStyle.weighty = 1;
        mainPanel.add(sidebarWrapper, BorderLayout.WEST);

        JPanel sidebar = new JPanel();
        sidebar.setLayout(new BorderLayout());
        sidebarWrapper.add(sidebar, sidebarWrapperStyle);

        sidebarInner = new JPanel();
        sidebarInner.setLayout(new GridBagLayout());
        sidebar.add(sidebarInner, BorderLayout.NORTH);

        JPanel sidebarPaddingLeft = new JPanel();
        GridBagConstraints sidebarPaddingLeftStyle = new GridBagConstraints();
        sidebarPaddingLeftStyle.gridx = 0;
        sidebarPaddingLeftStyle.gridy = 0;
        sidebarInner.add(sidebarPaddingLeft, sidebarPaddingLeftStyle);

        JPanel sidebarPaddingRight = new JPanel();
        GridBagConstraints sidebarPaddingRightStyle = new GridBagConstraints();
        sidebarPaddingRightStyle.gridx = 2;
        sidebarPaddingRightStyle.gridy = 0;
        sidebarInner.add(sidebarPaddingRight, sidebarPaddingRightStyle);
    }



    // tester components



}
