package view;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class Components {

    // shared vars
    public JPanel mainPanel;
    public JPanel buttonsPanel;
    public GridBagConstraints buttonsPanelStyle;
    public JButton analyzerButton;
    public JButton testerButton;

    // analyzer vars
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

    // tester vars
    public GridBagConstraints columnStyle;
    public JPanel sidebarPanel;
    public JPanel col1;
    public JPanel col1Inner;
    public JPanel col1LeftPadding;
    public JLabel testerFileLabel;
    public JList testerFileList;
    public GridBagConstraints testerFileLabelStyle;
    public GridBagConstraints testerFileListStyle;
    public JTextArea aggregateResultTextarea;
    public GridBagConstraints aggregateResultTextareaStyle;
    public Border allResultsBorder;
    public JLabel individualResultLabel;
    public String actualLabelArr[];
    public JList actualLabelList;
    public String xmlActualArr[];
    public JList testsXmlActualList;
    public JPanel col1RightPadding;
    public GridBagConstraints col1RightPaddingStyle;
    public String expectedLabelArr[];
    public JList expectedLabelList;
    public String xmlExpectedArr[];
    public JList testsXmlExpectedList;
    public JPanel testsPanel;
    public JPanel testerSpacerRight;
    public JPanel testerSpacerBottom0;
    public JPanel testerSpacerBottom1;
    public JPanel testerSpacerBottom2;
    public JPanel testerSpacerBottom3;
    public Border individualResultBorder;
    public JTextArea individualResultTextarea;
    public GridBagConstraints individualResultTextareaStyle;
    public JLabel aggregateResultLabel;
    public String testsXmlExpectedTextAreaText;
    public String testsXmlActualTextAreaText;
    public String testsTranslationExpectedTextAreaText;
    public String testsTranslationActualTextAreaText;
    public String testsInterleavingsExpectedTextAreaText;
    public String testsInterleavingsActualTextAreaText;
    public String testsModelCheckingExpectedTextAreaText;
    public String testsModelCheckingActualTextAreaText;
    public JPanel testsXmlPanel;
    public JLabel testsXmlTitle;
    public GridBagConstraints xmlPanelGridPosition;
    public JLabel testsXmlExpectedLabel;
    public JScrollPane testsXmlExpectedScrollPane;
    public JScrollPane testsXmlActualScrollPane;
    public JTextArea testsXmlExpectedTextArea;
    public JTextArea  testsXmlActualTextArea;
    public JScrollPane testsTranslationExpectedScrollPane;
    public JScrollPane testsTranslationActualScrollPane;
    public JTextArea testsTranslationExpectedTextArea;
    public JTextArea testsTranslationActualTextArea;
    public JScrollPane testsInterleavingsExpectedScrollPane;
    public JScrollPane testsInterleavingsActualScrollPane;
    public JTextArea testsInterleavingsExpectedTextArea;
    public JTextArea testsInterleavingsActualTextArea;
    public JLabel testsXmlActualLabel;
    public JSplitPane testsXmlExpectedSplitPane;
    public JSplitPane testsXmlActualSplitPane;
    public GridBagConstraints translationPanelGridPosition;
    public JPanel testsTranslationPanel;
    public JLabel testsTranslationTitle;
    public JLabel testsTranslationExpectedLabel;
    public JLabel testsTranslationActualLabel;
    public JSplitPane testsTranslationExpectedSplitPane;
    public JSplitPane testsTranslationActualSplitPane;
    public JList testsTranslationExpectedList;
    public JList testsTranslationActualList;
    public String[] testsTranslationExpectedArr;
    public String[] testsTranslationActualArr;
    public GridBagConstraints interleavingsPanelGridPosition;
    public JPanel testsInterleavingsPanel;
    public JLabel testsInterleavingsTitle;
    public JLabel testsInterleavingsExpectedLabel;
    public JLabel testsInterleavingsActualLabel;
    public JSplitPane testsInterleavingsExpectedSplitPane;
    public JSplitPane testsInterleavingsActualSplitPane;
    public JList testsInterleavingsExpectedList;
    public JList testsInterleavingsActualList;
    public String[] testsInterleavingsExpectedArr;
    public String[] testsInterleavingsActualArr;
    public GridBagConstraints modelCheckingPanelGridPosition;
    public JPanel testsModelCheckingPanel;
    public JLabel testsModelCheckingTitle;
    public JLabel testsModelCheckingExpectedLabel;
    public JLabel testsModelCheckingActualLabel;
    public JSplitPane testsModelCheckingExpectedSplitPane;
    public JSplitPane testsModelCheckingActualSplitPane;
    public JList testsModelCheckingExpectedList;
    public JList testsModelCheckingActualList;
    public String[] testsModelCheckingExpectedArr;
    public String[] testsModelCheckingActualArr;
    public GridBagConstraints col1InnerStyle;
    public GridBagConstraints individualResultLabelStyle;
    public GridBagConstraints individualResultStyle;
    public GridBagConstraints aggregateResultLabelStyle;
    public GridBagConstraints layoutSettings;
    public GridBagConstraints testerSpacerRightStyle;
    public GridBagConstraints testerSpacerBottom0Style;
    public GridBagConstraints testerSpacerBottom1Style;
    public GridBagConstraints testerSpacerBottom2Style;
    public GridBagConstraints testerSpacerBottom3Style;
    public JScrollPane testsModelCheckingExpectedScrollPane;
    public JTextArea testsModelCheckingExpectedTextArea;
    public JScrollPane testsModelCheckingActualScrollPane;
    public JTextArea testsModelCheckingActualTextArea;

    public void sharedComponents(JFrame frame) {
        initFrame(frame);
        initStyles();
        mainPanel(frame);
    }

    public void analyzerComponents(String selectedState) {
        analyzerSidebarComponents(selectedState);
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

        analyzerButton = new JButton();
        GridBagConstraints analyzerButtonStyle = new GridBagConstraints();
        analyzerButton.setText("Analyzer");
        buttonsPanel.add(analyzerButton);

        testerButton = new JButton();
        GridBagConstraints testerButtonStyle = new GridBagConstraints();
        testerButton.setText("Tests");
        buttonsPanel.add(testerButton);
    }

    // analyzer components
    public void analyzerSidebarComponents(String selectedState) {
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
        // result does hold
        String doesHoldTitle = selectedState == null ? "Does selected state hold:" : "Does selected state " + selectedState + " hold:";
        resultDoesHoldTitle = new JLabel(doesHoldTitle);
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
        resultStatesThatHoldTitle = new JLabel("All states in graph that hold for property:");
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
        resultCounterExampleTitle = new JLabel("Counterexample path(s):");
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

    public void testerSidebar() {
        // sidebar
        sidebarPanel = new JPanel(new GridBagLayout());
        // column1
        col1 = new JPanel(new BorderLayout());
        columnStyle = new GridBagConstraints();
        columnStyle.fill = GridBagConstraints.HORIZONTAL;
        // col1Inner
        col1Inner = new JPanel(new GridBagLayout());
        col1InnerStyle = new GridBagConstraints();
        col1InnerStyle.gridx = 0;
        col1InnerStyle.gridy = 0;
        col1InnerStyle.anchor = GridBagConstraints.NORTH;
        col1InnerStyle.weighty = 1;
        col1.add(col1Inner, BorderLayout.NORTH);
        sidebarPanel.add(col1, col1InnerStyle);
        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        // col1LeftPadding (this is never assigned a width, but it does take up space. must have a default width or something)
         col1LeftPadding = new JPanel();
         col1Inner.add(col1LeftPadding,col1InnerStyle);
        // buttons panel
        buttonsPanel = new JPanel();
        buttonsPanelStyle = new GridBagConstraints();
        buttonsPanelStyle = new GridBagConstraints();
        analyzerButton = new JButton("Analyzer");
        testerButton = new JButton("Tester");
        buttonsPanel.add(analyzerButton);
        buttonsPanel.add(testerButton);
        col1Inner.add(buttonsPanel, buttonsPanelStyle);
        // file list
        testerFileLabel = new JLabel("Files");
        testerFileLabel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        testerFileLabel.setFont(titleFont);
        testerFileLabelStyle = new GridBagConstraints();
        testerFileLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        testerFileLabelStyle.gridx = 1;
        testerFileLabelStyle.gridy = 1;
        col1Inner.add(testerFileLabel, testerFileLabelStyle);
        testerFileList = new JList();
        testerFileList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        testerFileList.setFont(listFont);
        testerFileList.setSelectionBackground(selectedListItemColor);
        testerFileListStyle = new GridBagConstraints();
        testerFileListStyle.fill = GridBagConstraints.HORIZONTAL;
        testerFileListStyle = new GridBagConstraints();
        testerFileListStyle.gridx = 1;
        testerFileListStyle.gridy = 2;
        col1Inner.add(testerFileList, testerFileListStyle);
        // individual test area
        individualResultLabel = new JLabel("Individual Test");
        individualResultLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        individualResultLabel.setFont(titleFont);
        individualResultLabelStyle = new GridBagConstraints();
        individualResultLabelStyle.gridx = 1;
        individualResultLabelStyle.gridy = 3;
        individualResultLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        col1Inner.add(individualResultLabel, individualResultLabelStyle);
        individualResultTextarea = new JTextArea();
        individualResultTextarea.setFont(titleFont);
        individualResultTextarea.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        individualResultBorder = BorderFactory.createLineBorder(Color.BLACK, 2);
        individualResultTextarea.setBorder(BorderFactory.createCompoundBorder(individualResultBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        individualResultTextareaStyle = new GridBagConstraints();
        individualResultTextareaStyle.fill = GridBagConstraints.HORIZONTAL;
        individualResultTextareaStyle.gridx = 1;
        individualResultTextareaStyle.gridy = 4;
        col1Inner.add(individualResultTextarea, individualResultTextareaStyle);
        // all result area
        aggregateResultLabel = new JLabel("Aggregate Test");
        aggregateResultLabel.setBorder(BorderFactory.createEmptyBorder(20, 5, 5, 5));
        aggregateResultLabel.setFont(titleFont);
        aggregateResultLabelStyle = new GridBagConstraints();
        aggregateResultLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        aggregateResultLabelStyle.gridx = 1;
        aggregateResultLabelStyle.gridy = 5;
        col1Inner.add(aggregateResultLabel, aggregateResultLabelStyle);
        aggregateResultTextarea = new JTextArea();
        aggregateResultTextarea.setFont(titleFont);
        allResultsBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
        aggregateResultTextarea.setBorder(BorderFactory.createCompoundBorder(allResultsBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        aggregateResultTextareaStyle = new GridBagConstraints();
        aggregateResultTextareaStyle.fill = GridBagConstraints.HORIZONTAL;
        aggregateResultTextareaStyle.gridx = 1;
        aggregateResultTextareaStyle.gridy = 6;
        col1Inner.add(aggregateResultTextarea, aggregateResultTextareaStyle);
        // col1RightPadding (this is never assigned a width, but it does take up space. must have a default width or something)
        col1RightPadding = new JPanel();
        col1RightPaddingStyle = new GridBagConstraints();
        col1RightPaddingStyle.gridx = 2;
        col1RightPaddingStyle.gridy = 0;
        col1RightPaddingStyle.anchor = GridBagConstraints.NORTH;
        col1RightPaddingStyle.weighty = 1;
        col1Inner.add(col1RightPadding,col1RightPaddingStyle);
    }

    public void testerComponents() {
        // tests panel
        testsPanel = new JPanel();
        testsPanel.setBackground(Color.white);
        testsPanel.setPreferredSize(new Dimension(500, 800));
        mainPanel.add(testsPanel);
        // xml tests
        testsXmlPanel = new JPanel();
        testsXmlPanel.setLayout(new BorderLayout());
        testsXmlPanel.setLayout(new BorderLayout());
        testsXmlPanel.setPreferredSize(new Dimension(350, 700));
        testsXmlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        xmlPanelGridPosition = new GridBagConstraints();
        xmlPanelGridPosition.gridx = 0;
        xmlPanelGridPosition.gridy = 0;
        xmlPanelGridPosition.fill = GridBagConstraints.HORIZONTAL;
        testsPanel.add(testsXmlPanel, xmlPanelGridPosition);
        testsXmlTitle = new JLabel("Xml");
        testsXmlTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        testsXmlPanel.add(testsXmlTitle, BorderLayout.NORTH);
        testsXmlExpectedLabel = new JLabel("Expected");
        testsXmlExpectedList = new JList();
        testsXmlExpectedTextArea = new JTextArea();
        testsXmlExpectedTextArea.setFont(titleFont);
        testsXmlExpectedTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsXmlExpectedScrollPane = new JScrollPane();
        testsXmlExpectedScrollPane.setPreferredSize(new Dimension(163, 700));
        testsXmlExpectedScrollPane.setViewportView(testsXmlExpectedTextArea);
        testsXmlPanel.add(testsXmlExpectedScrollPane, BorderLayout.WEST);
        testsXmlActualLabel = new JLabel("Acutal");
        testsXmlActualList = new JList();
        testsXmlActualTextArea = new JTextArea();
        testsXmlActualTextArea.setFont(titleFont);
        testsXmlActualTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsXmlActualScrollPane = new JScrollPane();
        testsXmlActualScrollPane.setPreferredSize(new Dimension(163, 700));
        testsXmlActualScrollPane.setViewportView(testsXmlActualTextArea);
        testsXmlPanel.add(testsXmlActualScrollPane, BorderLayout.EAST);
        // translation tests
        testsTranslationPanel = new JPanel();
        testsTranslationPanel.setLayout(new BorderLayout());
        testsTranslationPanel.setPreferredSize(new Dimension(350, 700));
        testsTranslationPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        testsTranslationPanel.setLayout(new BorderLayout());
        translationPanelGridPosition = new GridBagConstraints();
        translationPanelGridPosition.gridx = 1;
        translationPanelGridPosition.gridy = 0;
        translationPanelGridPosition.fill = GridBagConstraints.HORIZONTAL;
        testsPanel.add(testsTranslationPanel, xmlPanelGridPosition);
        testsTranslationTitle = new JLabel("Translation");
        testsTranslationTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        testsTranslationPanel.add(testsTranslationTitle, BorderLayout.NORTH);
        testsTranslationExpectedLabel = new JLabel("Expected");
        testsTranslationExpectedList = new JList();
        testsTranslationExpectedTextArea = new JTextArea();
        testsTranslationExpectedTextArea.setFont(titleFont);
        testsTranslationExpectedTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsTranslationExpectedScrollPane = new JScrollPane();
        testsTranslationExpectedScrollPane.setPreferredSize(new Dimension(163, 700));
        testsTranslationExpectedScrollPane.setViewportView(testsTranslationExpectedTextArea);
        testsTranslationPanel.add(testsTranslationExpectedScrollPane, BorderLayout.WEST);
        testsTranslationActualLabel = new JLabel("Actual");
        testsTranslationActualList = new JList();
        testsTranslationActualTextArea = new JTextArea();
        testsTranslationActualTextArea.setFont(titleFont);
        testsTranslationActualTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsTranslationActualScrollPane = new JScrollPane();
        testsTranslationActualScrollPane.setPreferredSize(new Dimension(163, 700));
        testsTranslationActualScrollPane.setViewportView(testsTranslationActualTextArea);
        testsTranslationPanel.add(testsTranslationActualScrollPane, BorderLayout.EAST);
        // interleavings tests
        testsInterleavingsPanel = new JPanel();
        testsInterleavingsPanel.setLayout(new BorderLayout());
        testsInterleavingsPanel.setPreferredSize(new Dimension(350, 700));
        testsInterleavingsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        testsInterleavingsPanel.setLayout(new BorderLayout());
        interleavingsPanelGridPosition = new GridBagConstraints();
        interleavingsPanelGridPosition.gridx = 2;
        interleavingsPanelGridPosition.gridy = 0;
        interleavingsPanelGridPosition.fill = GridBagConstraints.HORIZONTAL;
        testsPanel.add(testsInterleavingsPanel, interleavingsPanelGridPosition);
        testsInterleavingsTitle = new JLabel("Interleavings");
        testsInterleavingsTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        testsInterleavingsPanel.add(testsInterleavingsTitle, BorderLayout.NORTH);
        testsInterleavingsExpectedLabel = new JLabel();
        testsInterleavingsExpectedList = new JList();
        testsInterleavingsExpectedTextArea = new JTextArea();
        testsInterleavingsExpectedTextArea.setFont(titleFont);
        testsInterleavingsExpectedTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsInterleavingsExpectedScrollPane = new JScrollPane();
        testsInterleavingsExpectedScrollPane.setPreferredSize(new Dimension(163, 700));
        testsInterleavingsExpectedScrollPane.setViewportView(testsXmlExpectedTextArea);
        testsInterleavingsPanel.add(testsInterleavingsExpectedScrollPane, BorderLayout.WEST);
        testsInterleavingsActualLabel = new JLabel();
        testsInterleavingsActualList = new JList();
        testsInterleavingsActualTextArea = new JTextArea();
        testsInterleavingsActualTextArea.setFont(titleFont);
        testsInterleavingsActualTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsInterleavingsActualScrollPane = new JScrollPane();
        testsInterleavingsActualScrollPane.setPreferredSize(new Dimension(163, 700));
        testsInterleavingsActualScrollPane.setViewportView(testsInterleavingsActualTextArea);
        testsInterleavingsPanel.add(testsInterleavingsActualScrollPane, BorderLayout.EAST);
        // model checking tests
        testsModelCheckingPanel = new JPanel();
        testsModelCheckingPanel.setLayout(new BorderLayout());
        testsModelCheckingPanel.setPreferredSize(new Dimension(350, 700));
        testsModelCheckingPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        testsModelCheckingPanel.setLayout(new BorderLayout());
        modelCheckingPanelGridPosition = new GridBagConstraints();
        modelCheckingPanelGridPosition.gridx = 3;
        modelCheckingPanelGridPosition.gridy = 0;
        modelCheckingPanelGridPosition.fill = GridBagConstraints.HORIZONTAL;
        testsModelCheckingTitle = new JLabel();
        testsModelCheckingTitle.setBorder(BorderFactory.createEmptyBorder(0, 100, 10, 0));
        testsModelCheckingExpectedLabel = new JLabel("Expected");
        testsModelCheckingExpectedLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        testsModelCheckingExpectedLabel.setFont(titleFont);
        testsModelCheckingExpectedList = new JList();
        testsModelCheckingExpectedList.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsModelCheckingExpectedList.setFont(titleFont);
        testsModelCheckingExpectedTextArea = new JTextArea();
        testsModelCheckingExpectedTextArea.setFont(titleFont);
        testsModelCheckingExpectedTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsModelCheckingExpectedScrollPane = new JScrollPane();
        testsModelCheckingExpectedScrollPane.setPreferredSize(new Dimension(163, 700));
        testsModelCheckingExpectedSplitPane = new JSplitPane();
        testsModelCheckingActualLabel = new JLabel("Actual");
        testsModelCheckingActualLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 15, 10));
        testsModelCheckingActualLabel.setFont(titleFont);
        testsModelCheckingActualList = new JList();
        testsModelCheckingActualList.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsModelCheckingActualList.setFont(titleFont);
        testsModelCheckingActualTextArea = new JTextArea();
        testsModelCheckingActualTextArea.setFont(titleFont);
        testsModelCheckingActualTextArea.setBorder(BorderFactory.createEmptyBorder(15, 15, 17, 15));
        testsModelCheckingActualScrollPane = new JScrollPane();
        testsModelCheckingActualScrollPane.setPreferredSize(new Dimension(163, 700));

        // spacers
        testerSpacerRight = new JPanel();
        testerSpacerRightStyle = new GridBagConstraints();
        testerSpacerRightStyle.gridx = 4;
        testerSpacerRightStyle.gridy = 0;
        testerSpacerRightStyle.weightx = 1;
        testerSpacerBottom0 = new JPanel();
        testerSpacerBottom0Style = new GridBagConstraints();
        testerSpacerBottom0Style.gridx = 0;
        testerSpacerBottom0Style.gridy = 1;
        testerSpacerBottom0Style.weighty = 1;
        testerSpacerBottom1 = new JPanel();
        testerSpacerBottom1Style = new GridBagConstraints();
        testerSpacerBottom1Style.gridx = 1;
        testerSpacerBottom1Style.gridy = 1;
        testerSpacerBottom1Style.weighty = 1;
        testerSpacerBottom2 = new JPanel();
        testerSpacerBottom2Style = new GridBagConstraints();
        testerSpacerBottom2Style.gridx = 2;
        testerSpacerBottom2Style.gridy = 1;
        testerSpacerBottom2Style.weighty = 1;
        testerSpacerBottom3 = new JPanel();
        testerSpacerBottom3Style = new GridBagConstraints();
        testerSpacerBottom3Style.gridx = 3;
        testerSpacerBottom3Style.gridy = 1;
        testerSpacerBottom3Style.weighty = 1;

    }





}
