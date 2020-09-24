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
    public JLabel fileTitle;
    public GridBagConstraints fileTitleStyle;
    public JLabel displayTitle;
    public GridBagConstraints displayTitleStyle;
    public JLabel stepTitle;
    public GridBagConstraints stepTitleStyle;
    public JLabel modelTitle;
    public GridBagConstraints modelTitleStyle;
    public JLabel loopTitle;
    public GridBagConstraints loopTitleStyle;
    public JLabel stateTitle;
    public GridBagConstraints stateTitleStyle;
    public JLabel labelTitle;
    public GridBagConstraints labelTitleStyle;
    public JLabel resultTitle;
    public GridBagConstraints resultTitleStyle;
    public JLabel resultDoesHoldTitle;
    public GridBagConstraints resultDoesHoldTitleStyle;
    public JTextArea resultDoesHoldField;
    public GridBagConstraints resultDoesHoldFieldStyle;
    public JLabel resultStatesThatHoldTitle;
    public GridBagConstraints resultStatesThatHoldTitleStyle;
    public JTextArea resultStatesField;
    public GridBagConstraints resultStatesFieldStyle;
    public JScrollPane resultStatesScrollpane;
    public GridBagConstraints resultStatesScrollpaneStyle;
    public JLabel resultCounterExampleTitle;
    public GridBagConstraints resultCounterExampleTitleStyle;
    public JTextArea resultCounterExampleField;
    public GridBagConstraints resultCounterExampleFieldStyle;
    public JScrollPane resultCounterExampleScrollpane;
    public GridBagConstraints resultCounterExampleScrollpaneStyle;
    public JLabel resultTimeTitle;
    public GridBagConstraints resultTimeTitleStyle;
    public JTextArea resultTimeField;
    public GridBagConstraints resultTimeFieldStyle;
    public JList fileList;
    public GridBagConstraints fileListStyle;
    public JList displayList;
    public GridBagConstraints displayListStyle;
    public JList stepList;
    public GridBagConstraints stepListStyle;
    public JList modelList;
    public GridBagConstraints modelListStyle;
    public JTextArea loopTextarea;
    public GridBagConstraints loopTextAreaStyle;
    public JList stateList;
    public GridBagConstraints stateListStyle;
    public JList labelList;
    public GridBagConstraints labelListStyle;
    public JScrollPane fileScrollpane;
    public JScrollPane displayScrollpane;
    public JScrollPane stepScrollpane;
    public JScrollPane modelScrollpane;
    public JScrollPane stateScrollpane;
    public JScrollPane labelScrollpane;
    private Font extraLargeFont;
    private Font titleFont;
    private Font listFont;
    private Color selectedListItemColor;
    public JPanel mainGraphPanel;
    public GridBagConstraints mainGraphPanelStyle;
    public JLabel graphPanel1Title;
    public GridBagConstraints graphPanel1TitleStyle;
    public JPanel graphPanel1;
    public GridBagConstraints graphPanel1Style;
    public JLabel graphPanel2Title;
    public GridBagConstraints graphPanel2TitleStyle;
    public JPanel graphPanel2;
    public GridBagConstraints graphPanel2Style;
    public JLabel graphPanel3Title;
    public GridBagConstraints graphPanel3TitleStyle;
    public JPanel graphPanel3;
    public GridBagConstraints graphPanel3Style;

    public void sharedComponents(JFrame frame) {
        initFrame(frame);
        initStyles();
        mainPanel(frame);
        analyzerSidebarContainer();
        buttonsPanel();
    }

    public void analyzerComponents() {
        analyzerSidebarComponents();
        analyzerGraphComponents();
    }

    // init frame
    public void initFrame(JFrame frame) {
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.setSize(new Dimension(1650, 950)); // might be able to change to setPreferredSize
    }

    // shared components
    public void initStyles() {
        extraLargeFont = new Font("Verdana", Font.PLAIN, 13);
        titleFont = new Font("Verdana", Font.PLAIN, 11);
        listFont = new Font("Verdana", Font.PLAIN, 10);
        selectedListItemColor = Color.decode("#5e7f85"); // gray/blue
    }

    public void mainPanel(JFrame frame) {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(Color.white);
        frame.add(mainPanel);
    }

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

    public void buttonsPanel() {
        buttonsPanel = new JPanel();
        buttonsPanelStyle = new GridBagConstraints();
        buttonsPanelStyle.gridx = 1;
        buttonsPanelStyle.gridy = 0;
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(7, 10, 6, 10));
        sidebarInner.add(buttonsPanel, buttonsPanelStyle);

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
    public void analyzerSidebarComponents() {
        instructionsTextarea = new JTextArea("Select your file(s), model and\nnumber of loops below");
        instructionsTextareaStyle = new GridBagConstraints();
        instructionsTextareaStyle.gridx = 1;
        instructionsTextareaStyle.gridy = 1;
        instructionsTextareaStyle.fill = GridBagConstraints.HORIZONTAL;
        instructionsTextarea.setFont(titleFont);
        instructionsTextarea.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.BLACK, 3), BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        sidebarInner.add(instructionsTextarea, instructionsTextareaStyle);

        fileTitle = new JLabel("Files");
        fileTitle.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        fileTitle.setFont(titleFont);
        fileTitleStyle = new GridBagConstraints();
        fileTitleStyle.gridx = 1;
        fileTitleStyle.gridy = 2;
        fileTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(fileTitle, fileTitleStyle);

        fileList = new JList();
        fileList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        fileList.setSelectionBackground(selectedListItemColor);
        fileList.setFont(listFont);
        fileListStyle = new GridBagConstraints();
        fileListStyle.gridx = 1;
        fileListStyle.gridy = 3;
        fileListStyle.fill = GridBagConstraints.HORIZONTAL;
        fileScrollpane = new JScrollPane(fileList);
        fileScrollpane.setPreferredSize(new Dimension(150, 100));
        sidebarInner.add(fileScrollpane, fileListStyle);

        // display graphs label
        displayTitle = new JLabel("Display");
        displayTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        displayTitle.setFont(titleFont);
        displayTitleStyle = new GridBagConstraints();
        displayTitleStyle.gridx = 1;
        displayTitleStyle.gridy = 4;
        displayTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(displayTitle, displayTitleStyle);
        // display graphs list
        displayList = new JList();
        displayList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        displayList.setSelectionBackground(selectedListItemColor);
        displayList.setFont(listFont);
        displayListStyle = new GridBagConstraints();
        displayListStyle.gridx = 1;
        displayListStyle.gridy = 5;
        displayListStyle.fill = GridBagConstraints.HORIZONTAL;
        displayScrollpane = new JScrollPane(displayList);
        displayScrollpane.setPreferredSize(new Dimension(150, 67));
        sidebarInner.add(displayScrollpane, displayListStyle);
        // steps label
        stepTitle = new JLabel("Steps");
        stepTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        stepTitle.setFont(titleFont);
        stepTitleStyle = new GridBagConstraints();
        stepTitleStyle.gridx = 1;
        stepTitleStyle.gridy = 6;
        stepTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(stepTitle, stepTitleStyle);
        // steps list
        stepList = new JList();
        stepList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        stepList.setSelectionBackground(selectedListItemColor);
        stepList.setFont(listFont);
        stepListStyle = new GridBagConstraints();
        stepListStyle.gridx = 1;
        stepListStyle.gridy = 7;
        stepListStyle.fill = GridBagConstraints.HORIZONTAL;
        stepScrollpane = new JScrollPane(stepList);
        stepScrollpane.setPreferredSize(new Dimension(150, 30));
        // stepScrollpane.setPreferredSize(new Dimension(150, 67));
        sidebarInner.add(stepScrollpane, stepListStyle);
        // models label
        modelTitle = new JLabel("Models");
        modelTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        modelTitle.setFont(titleFont);
        modelTitleStyle = new GridBagConstraints();
        modelTitleStyle.gridx = 1;
        modelTitleStyle.gridy = 8;
        modelTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(modelTitle, modelTitleStyle);
        // models list
        modelList = new JList();
        modelList.setSelectionBackground(selectedListItemColor);
        modelList.setFont(listFont);
        modelListStyle = new GridBagConstraints();
        modelListStyle.gridx = 1;
        modelListStyle.gridy = 9;
        modelListStyle.fill = GridBagConstraints.HORIZONTAL;
        modelScrollpane = new JScrollPane(modelList);
        modelScrollpane.setPreferredSize(new Dimension(150, 100));
        sidebarInner.add(modelScrollpane, modelListStyle);
        // number of loops label
        loopTitle = new JLabel("Loops");
        loopTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        loopTitle.setFont(titleFont);
        loopTitleStyle = new GridBagConstraints();
        loopTitleStyle.gridx = 1;
        loopTitleStyle.gridy = 10;
        loopTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(loopTitle, loopTitleStyle);
        // loops list
        loopTextarea = new JTextArea();
        loopTextarea.setFont(listFont);
        loopTextAreaStyle = new GridBagConstraints();
        loopTextAreaStyle.gridx = 1;
        loopTextAreaStyle.gridy = 11;
        loopTextAreaStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(loopTextarea, loopTextAreaStyle);
        // state label
        stateTitle = new JLabel("States");
        stateTitle.setFont(titleFont);
        stateTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        stateTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        stateTitleStyle = new GridBagConstraints();
        stateTitleStyle.gridx = 1;
        stateTitleStyle.gridy = 12;
        stateTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(stateTitle, stateTitleStyle);
        // states list
        stateList = new JList();
        stateList.setSelectionBackground(selectedListItemColor);
        stateList.setFont(listFont);
        stateList.setAlignmentX(Component.TOP_ALIGNMENT);
        stateScrollpane = new JScrollPane(stateList);
        stateListStyle = new GridBagConstraints();
        stateListStyle.gridx = 1;
        stateListStyle.gridy = 13;
        stateListStyle.fill = GridBagConstraints.HORIZONTAL;
        stateScrollpane.setPreferredSize(new Dimension(150, 30));
        // stateScrollpane.setPreferredSize(new Dimension(150, 67));
        sidebarInner.add(stateScrollpane, stateListStyle);
        // labels label
        labelTitle = new JLabel("Labels");
        labelTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        labelTitle.setFont(titleFont);
        labelTitleStyle = new GridBagConstraints();
        labelTitleStyle.gridx = 1;
        labelTitleStyle.gridy = 14;
        labelTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(labelTitle, labelTitleStyle);
        // labels list
        labelList = new JList();
        labelList.setFont(listFont);
        labelListStyle = new GridBagConstraints();
        labelListStyle.gridx = 1;
        labelListStyle.gridy = 15;
        labelListStyle.fill = GridBagConstraints.HORIZONTAL;
        labelScrollpane = new JScrollPane(labelList);
        labelScrollpane.setPreferredSize(new Dimension(150, 37));
        sidebarInner.add(labelScrollpane, labelListStyle);
        // result label
        /* resultTitle = new JLabel("Result");
        resultTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        resultTitle.setFont(titleFont);
        resultTitleStyle = new GridBagConstraints();
        resultTitleStyle.gridx = 1;
        resultTitleStyle.gridy = 16;
        resultTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(resultTitle, resultTitleStyle); */
        // result does hold
        resultDoesHoldTitle = new JLabel("Does hold:");
        resultDoesHoldTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        resultDoesHoldTitle.setFont(titleFont);
        resultDoesHoldTitleStyle = new GridBagConstraints();
        resultDoesHoldTitleStyle.gridx = 1;
        resultDoesHoldTitleStyle.gridy = 16;
        resultDoesHoldTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(resultDoesHoldTitle, resultDoesHoldTitleStyle);
        resultDoesHoldField = new JTextArea();
        resultDoesHoldField.setFont(listFont);
        resultDoesHoldFieldStyle = new GridBagConstraints();
        resultDoesHoldFieldStyle.gridx = 1;
        resultDoesHoldFieldStyle.gridy = 17;
        resultDoesHoldFieldStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(resultDoesHoldField, resultDoesHoldFieldStyle);
        // result states that hold label
        resultStatesThatHoldTitle = new JLabel("States that hold:");
        resultStatesThatHoldTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        resultStatesThatHoldTitle.setFont(titleFont);
        resultStatesThatHoldTitleStyle = new GridBagConstraints();
        resultStatesThatHoldTitleStyle.gridx = 1;
        resultStatesThatHoldTitleStyle.gridy = 18;
        resultStatesThatHoldTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(resultStatesThatHoldTitle, resultStatesThatHoldTitleStyle);
        resultStatesField = new JTextArea();
        resultStatesField.setFont(listFont);
        resultStatesFieldStyle = new GridBagConstraints();
        resultStatesFieldStyle.gridx = 1;
        resultStatesFieldStyle.gridy = 19;
        resultStatesFieldStyle.fill = GridBagConstraints.HORIZONTAL;
        resultStatesScrollpane = new JScrollPane(resultStatesField);
        sidebarInner.add(resultStatesScrollpane, resultStatesFieldStyle);
        // counter examples label
        resultCounterExampleTitle = new JLabel("Counterexample:");
        resultCounterExampleTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        resultCounterExampleTitle.setFont(titleFont);
        resultCounterExampleTitleStyle = new GridBagConstraints();
        resultCounterExampleTitleStyle.gridx = 1;
        resultCounterExampleTitleStyle.gridy = 20;
        resultCounterExampleTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(resultCounterExampleTitle, resultCounterExampleTitleStyle);
        resultCounterExampleField = new JTextArea();
        resultCounterExampleField.setFont(listFont);
        resultCounterExampleFieldStyle = new GridBagConstraints();
        resultCounterExampleFieldStyle.gridx = 1;
        resultCounterExampleFieldStyle.gridy = 21;
        resultCounterExampleFieldStyle.fill = GridBagConstraints.HORIZONTAL;
        resultCounterExampleScrollpane = new JScrollPane(resultCounterExampleField);
        sidebarInner.add(resultCounterExampleScrollpane, resultCounterExampleFieldStyle);
        // time label
        resultTimeTitle = new JLabel("Time:");
        resultTimeTitle.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        resultTimeTitle.setFont(titleFont);
        resultTimeTitleStyle = new GridBagConstraints();
        resultTimeTitleStyle.gridx = 1;
        resultTimeTitleStyle.gridy = 22;
        resultTimeTitleStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(resultTimeTitle, resultTimeTitleStyle);
        resultTimeField = new JTextArea();
        resultTimeField.setFont(listFont);
        resultTimeFieldStyle = new GridBagConstraints();
        resultTimeFieldStyle.gridx = 1;
        resultTimeFieldStyle.gridy = 23;
        resultTimeFieldStyle.fill = GridBagConstraints.HORIZONTAL;
        sidebarInner.add(resultTimeField, resultTimeFieldStyle);
    }

    public void analyzerGraphComponents() {
        mainGraphPanel = new JPanel();
        mainGraphPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        mainGraphPanel.setBackground(Color.white);
        mainPanel.add(mainGraphPanel);

        graphPanel1Title = new JLabel();
        graphPanel1Title.setHorizontalAlignment(SwingConstants.CENTER);
        graphPanel1Title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        mainGraphPanel.add(graphPanel1Title);
        graphPanel1 = new JPanel();
        graphPanel1.setLayout(new BorderLayout());
        graphPanel1.setBackground(Color.white);
        mainGraphPanel.add(graphPanel1);

        graphPanel2Title = new JLabel();
        graphPanel2Title.setHorizontalAlignment(SwingConstants.CENTER);
        graphPanel2Title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        graphPanel2 = new JPanel();
        graphPanel2.setLayout(new BorderLayout());
        graphPanel2.setBackground(Color.white);

        graphPanel3Title = new JLabel();
        graphPanel3Title.setHorizontalAlignment(SwingConstants.CENTER);
        graphPanel3Title.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        graphPanel3 = new JPanel();
        graphPanel3.setLayout(new BorderLayout());
        graphPanel3.setBackground(Color.white);
    }

    // tester components



}
