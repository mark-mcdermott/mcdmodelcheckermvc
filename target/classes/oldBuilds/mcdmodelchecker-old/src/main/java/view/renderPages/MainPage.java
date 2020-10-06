package view.renderPages;

import _options.Options;
import controller.ModelChecker;
import controller.dataTypes.ctlHelpers.ModelCheckResult;
import controller.dataTypes.graphHelpers.DisplayGraphsEnum;
import controller.dataTypes.graphHelpers.GraphModelEnum;
import controller.dataTypes.graphItems.GraphModelObj;
import view.View;
import controller.dataTypes.graphItems.Kripke;
import controller.dataTypes.graphHelpers.LabelHash;
import controller.dataTypes.graphItems.Vertex;
import controller.dataTypes.pageHelpers.AnalyzerState;
import controller.dataTypes.pageHelpers.PageEnum;
import controller.helpers.ListHelper;
import controller.helpers.McdException;
import controller.dataTypes.graphHelpers.FileSelectResult;
import controller.helpers.VertexHelper;
import model.Model;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Vector;

import static controller.Controller.runModelCheckAndStoreResults;
import static controller.dataTypes.graphHelpers.DisplayGraphsEnum.*;
import static controller.dataTypes.graphHelpers.GraphModelEnum.GRAPH_MODEL;
import static controller.dataTypes.graphHelpers.GraphModelEnum.GRAPH_MODEL_PREV_STEP;
import static controller.dataTypes.pageHelpers.AnalyzerState.*;
import static controller.dataTypes.pageHelpers.PageEnum.ANALYZER_PAGE;
import static controller.dataTypes.pageHelpers.PageEnum.TESTER_PAGE;
import static controller.helpers.DisplayGraphEnumHelper.displayGraphStringToEnum;

public class MainPage {

    // pages
    MainPage mainPage;
    AnalyzerPage analyzerPage;
    TesterPage testerPage;

    // analyzer vars
    private JFrame frame;
    public JButton analyzerButton;
    JPanel mainPanel;
    JPanel sidebarWrapper;
    JPanel sidebar;
    JPanel sidebarInner;
    JPanel sidebarPaddingLeft;
    JPanel buttonsPanel;
    JPanel sidebarPaddingRight;
    JPanel graphsCheckboxPanel;
    JPanel graphPanel;
    JPanel graphInnerPanel;
    JPanel xmlGraphPanel;
    JPanel translationGraphPanel;
    JPanel interleavingsGraphPanel;
    JPanel kripkeGraphTitleWrapper;
    JButton testerButton;
    Border instructionsBorder;
    JLabel fileLabel;
    JLabel displayGraphLabel;
    JLabel stepsLabel;
    JLabel modelLabel;
    JLabel loopLabel;
    JLabel labelLabel;
    JLabel resultLabel;
    JLabel stateLabel;
    JLabel xmlGraphTitle;
    JLabel translationTitle;
    JLabel interleavingsTitle;
    String fileListArr[];
    String modelArr[];
    String labelArr[];
    String stateArr[];
    String displayGraphArr[];
    String stepsArr[];
    String stepsListArr[];
    String translationStepListArr;
    String interleavingsStepListArr;
    JList fileList;
    JList displayGraphList;
    JList stepsList;
    JList modelList;
    JList labelList;
    JList stateList;
    JList statesArr;
    DefaultListModel stepsListModel;
    JScrollPane fileListScrollPane;
    JScrollPane graphOptionsScrollPane;
    JScrollPane stepsScrollPane;
    JScrollPane modelListScrollPane;
    JScrollPane stateListScrollPane;
    JScrollPane labelListScrollPane;
    JScrollPane testsXmlExpectedScrollPane;
    JScrollPane testsXmlActualScrollPane;
    JScrollPane testsTranslationExpectedScrollPane;
    JScrollPane testsTranslationActualScrollPane;
    JScrollPane testsInterleavingsExpectedScrollPane;
    JScrollPane testsInterleavingsActualScrollPane;
    JScrollPane testsModelCheckingExpectedScrollPane;
    JScrollPane testsModelCheckingActualScrollPane;
    JTextArea instructionsTextarea;
    JTextArea loopTextArea;
    JTextArea testsXmlExpectedTextArea;
    JTextArea testsXmlActualTextArea;
    JTextArea testsTranslationExpectedTextArea;
    JTextArea testsTranslationActualTextArea;
    JTextArea testsInterleavingsExpectedTextArea;
    JTextArea testsInterleavingsActualTextArea;
    JTextArea testsModelCheckingExpectedTextArea;
    JTextArea testsModelCheckingActualTextArea;
    JTextPane resultTextpane;
    JScrollPane resultScrollpane;
    GridBagConstraints sidebarWrapperStyle;
    GridBagConstraints sidebarStyle;
    GridBagConstraints sidebarInnerStyle;
    GridBagConstraints sidebarPaddingLeftStyle;
    GridBagConstraints buttonsPanelStyle;
    GridBagConstraints analyzerButtonStyle;
    GridBagConstraints testerButtonStyle;
    GridBagConstraints instructionsTextareaStyle;
    GridBagConstraints fileLabelStyle;
    GridBagConstraints fileListStyle;
    GridBagConstraints fileListScrollPaneStyle;
    GridBagConstraints modelListScrollPaneStyle;
    GridBagConstraints modelLabelStyle;
    GridBagConstraints modelListStyle;
    GridBagConstraints loopLabelStyle;
    GridBagConstraints loopTextAreaStyle;
    GridBagConstraints labelLabelStyle;
    GridBagConstraints graphLabelStyle;
    GridBagConstraints graphOptionsListStyle;
    GridBagConstraints graphOptionsScrollpaneStyle;
    GridBagConstraints stepsLabelStyle;
    GridBagConstraints stepsListStyle;
    GridBagConstraints stepsScrollpaneStyle;
    GridBagConstraints labelListStyle;
    GridBagConstraints resultLabelStyle;
    GridBagConstraints resultTextpaneStyle;
    GridBagConstraints resultScrollpaneStyle;
    GridBagConstraints sidebarPaddingRightStyle;
    GridBagConstraints stateLabelStyle;
    GridBagConstraints stateListStyle;
    GridBagConstraints stateScrollPaneStyle;

    Font largeFont;
    Font labelFont;
    Font listFont;

    // tester vars
    GridBagConstraints columnStyle;
    JPanel sidebarPanel;
    JPanel col1;
    JPanel col1Inner;
    JPanel col1LeftPadding;
    Font titleFont;
    JTextArea allResultsTextarea;
    Border allResultsBorder;
    JLabel individualResultLabel;
    String actualLabelArr[];
    JList actualLabelList;
    String xmlActualArr[];
    JList testsXmlActualList;
    JPanel col1RightPadding;
    String expectedLabelArr[];
    JList expectedLabelList;
    String xmlExpectedArr[];
    JList testsXmlExpectedList;
    JPanel testsPanel;
    JPanel testerSpacerRight;
    JPanel testerSpacerBottom0;
    JPanel testerSpacerBottom1;
    JPanel testerSpacerBottom2;
    JPanel testerSpacerBottom3;
    Border individualResultBorder;
    JTextArea individualResultTextarea;
    JLabel allResultsLabel;
    String testsXmlExpectedTextAreaText;
    String testsXmlActualTextAreaText;
    String testsTranslationExpectedTextAreaText;
    String testsTranslationActualTextAreaText;
    String testsInterleavingsExpectedTextAreaText;
    String testsInterleavingsActualTextAreaText;
    String testsModelCheckingExpectedTextAreaText;
    String testsModelCheckingActualTextAreaText;

    JPanel testsXmlPanel;
    JLabel testsXmlTitle;
    GridBagConstraints xmlPanelGridPosition;
    JLabel testsXmlExpectedLabel;
    JLabel testsXmlActualLabel;
    JSplitPane testsXmlExpectedSplitPane;
    JSplitPane testsXmlActualSplitPane;

    GridBagConstraints translationPanelGridPosition;
    JPanel testsTranslationPanel;
    JLabel testsTranslationTitle;
    JLabel testsTranslationExpectedLabel;
    JLabel testsTranslationActualLabel;
    JSplitPane testsTranslationExpectedSplitPane;
    JSplitPane testsTranslationActualSplitPane;
    JList testsTranslationExpectedList;
    JList testsTranslationActualList;
    String[] testsTranslationExpectedArr;
    String[] testsTranslationActualArr;

    GridBagConstraints interleavingsPanelGridPosition;
    JPanel testsInterleavingsPanel;
    JLabel testsInterleavingsTitle;
    JLabel testsInterleavingsExpectedLabel;
    JLabel testsInterleavingsActualLabel;
    JSplitPane testsInterleavingsExpectedSplitPane;
    JSplitPane testsInterleavingsActualSplitPane;
    JList testsInterleavingsExpectedList;
    JList testsInterleavingsActualList;
    String[] testsInterleavingsExpectedArr;
    String[] testsInterleavingsActualArr;

    GridBagConstraints modelCheckingPanelGridPosition;
    JPanel testsModelCheckingPanel;
    JLabel testsModelCheckingTitle;
    JLabel testsModelCheckingExpectedLabel;
    JLabel testsModelCheckingActualLabel;
    JSplitPane testsModelCheckingExpectedSplitPane;
    JSplitPane testsModelCheckingActualSplitPane;
    JList testsModelCheckingExpectedList;
    JList testsModelCheckingActualList;
    String[] testsModelCheckingExpectedArr;
    String[] testsModelCheckingActualArr;

    // GridBagConstraints buttonsPanelStyle;
    GridBagConstraints col1InnerStyle;
    // GridBagConstraints fileLabelStyle;
    // GridBagConstraints fileListStyle;
    GridBagConstraints individualResultLabelStyle;
    GridBagConstraints individualResultStyle;
    GridBagConstraints aggregateResultLabel;
    GridBagConstraints aggregateResultStyle;
    GridBagConstraints layoutSettings;
    GridBagConstraints testerSpacerRightStyle;
    GridBagConstraints testerSpacerBottom0Style;
    GridBagConstraints testerSpacerBottom1Style;
    GridBagConstraints testerSpacerBottom2Style;
    GridBagConstraints testerSpacerBottom3Style;

    // listener vars
    private ActionListener analyzerButtonListener;
    private ActionListener testerButtonListener;
    private ListSelectionListener fileListListener;
    private ListSelectionListener displayGraphListListener;
    private ListSelectionListener modelListListener;
    private ListSelectionListener stateListListener;
    private ListSelectionListener labelListListener;
    private ListSelectionListener stepsListListener;
    private Model model;
    // private Options options;
    // private AnalyzerLayout analyzerLayout;
    // private TesterLayout testerLayout;
    private int[] selectedFileIndices;
    private String[] selectedFiles;
    private Integer selectedStep;
    private Integer prevStep;
    private int selectedDisplayGraphIndex;
    private String selectedDisplayGraphStr;
    private DisplayGraphsEnum selectedGraph;
    private String[] modelListItems;
    private Integer selectedModelIndex;
    private String selectedModel;
    private Vertex selectedState;
    private String selectedLabelStr;
    private Label selectedLabel;
    private String[] stateListStrings;
    private JList liveModelList;
    private JList liveStateList;
    private JList liveLabelList;
    private int selectedStateIndex;
    private String selectedStateStr;
    private LabelHash labelHash;
    private String[] labelListStrings;
    private Kripke kripke;
    private ArrayList<Vertex> ctlResult;
    // private GraphsAndLabelHash graphsAndLabelHash;
    // private Graphs graphs;
    // private FileDependentLists fileDependentLists;

    // remove parameters from Page() definition?
    public MainPage() {
        mainPage = this;
        frame = new JFrame();
        analyzerPage = new AnalyzerPage();
        testerPage = new TesterPage();
    }

    public void renderAnalyzerPage(AnalyzerState analyzerState, Model model, View view, Options options, Boolean isInitialRender, GraphModelEnum graphModelEnum) throws McdException, BadLocationException {
        // init window
        if (analyzerState == SELECT_FILE || isInitialRender) { initWindow(isInitialRender); }
        else { initWindow(false); }

        // init shell
        analyzerPage.initAnalyzerShell(model, view, options, graphModelEnum);

        // gray out inactive states (file & model states only)
        if (analyzerState == SELECT_FILE) { analyzerPage.grayOutInactiveSectionsFileState(view); }
        else if (analyzerState == SELECT_MODEL) { analyzerPage.grayOutInactiveSectionsModelState(view); }

        // set selection on display graph list
        analyzerPage.setAnalyzerDisplayGraphAndStepSelections(model, view, options);

        // init model/results state
        if (analyzerState == SELECT_MODEL) {
            analyzerPage.setModelStateContent(model, view, options);
        } else if (analyzerState == DISPLAY_RESULTS) {
            analyzerPage.setResultsStateContent(model, view, options, model.getGraphModelFromEnum(graphModelEnum));
        }

        // set page to visible & set listeners
        setVisible();
        setListeners(ANALYZER_PAGE, analyzerState, model, view, options, graphModelEnum);
    }

    public void renderTesterPage(Model model, View view, Options options, Boolean isInitialRender, GraphModelEnum graphModelEnum) throws IOException, ParseException, McdException, ParserConfigurationException, SAXException {
        model.setModelToInitialDefaults(options);
        initWindow(isInitialRender);
        testerPage.initTesterPage(model, view, options, graphModelEnum);
        setListeners(TESTER_PAGE, null, model, view, options, graphModelEnum);
    }

    public void rerenderTesterPage(Model model, View view, Options options, Boolean isInitialRender, GraphModelEnum graphModelEnum) throws IOException, ParseException, McdException, ParserConfigurationException, SAXException {
        initWindow(isInitialRender);
        testerPage.reinitTesterPage(model, view, options, graphModelEnum);
        setListeners(TESTER_PAGE, null, model, view, options, graphModelEnum);
    }

    public void initWindow(Boolean isInitialRender) {
        if (isInitialRender) {
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLayout(new BorderLayout());
        } else {
            frame.getContentPane().removeAll();
        }
    }

    public void setVisible() {
        frame.validate();
        frame.pack();
        frame.setVisible(true);
    }


    // listeners

    public void setListeners(PageEnum whichPage, AnalyzerState analyzerState, Model model, View view, Options options, GraphModelEnum graphModelEnum) {
        setButtonListeners(model, view, options, graphModelEnum);
        if (whichPage == ANALYZER_PAGE) { setAnalyzerListeners(analyzerState, model, view, options, graphModelEnum); }
        else if (whichPage == TESTER_PAGE) { setTesterListeners(model, view, options, graphModelEnum); }
    }

    public void setButtonListeners(Model model, View view, Options options, GraphModelEnum graphModelEnum) {
        initButtonListeners(model, view, options, graphModelEnum);
        addButtonListeners();
    }

    public void setAnalyzerListeners(AnalyzerState analyzerState, Model model, View view, Options options, GraphModelEnum graphModelEnum) {
        initAnalyzerListeners(analyzerState, model, view, options, graphModelEnum);
        addAnalyzerListeners(analyzerState);
    }

    public void setTesterListeners(Model model, View view, Options options, GraphModelEnum graphModelEnum) {
        initTesterListeners(model, view, options, graphModelEnum);
        addTesterListeners();
    }

    public void initButtonListeners(Model model, View view, Options options, GraphModelEnum graphModelEnum) {
        analyzerButtonListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {

                    model.setModelToInitialDefaults(options);
                    model.setPageToDisplay(ANALYZER_PAGE);
                    // there could be an error state here if Display Graphs is set to a comparison, but Display Set is set to step 1.
                    // make Display Step logic run regular trans only / inter only if 1 is selected (keep step 1 highlighted though)
                    if (
                            model.getSelectedGraph() != null
                            && model.getSelectedGraph() != null
                            && model.getSelectedGraph().equals(TRANSLATION_COMPARISON)
                            || model.getSelectedGraph().equals(INTERLEAVINGS_COMPARISON)
                    ) {
                        model.setModelToInitialDefaults(options);
                        model.setPageToDisplay(ANALYZER_PAGE);
                    }

                    renderAnalyzerPage(SELECT_FILE, model, view, options, false, graphModelEnum);
                    setListeners(ANALYZER_PAGE, SELECT_FILE, model, view, options, graphModelEnum);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                } catch (McdException e) {
                    e.printStackTrace();
                } catch (BadLocationException e) {
                    e.printStackTrace();
                }
            }
        };
        testerButtonListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    model.setModelToInitialDefaults(options);
                    model.setPageToDisplay(TESTER_PAGE);
                    renderTesterPage(model, view, options, false, graphModelEnum);
                    setListeners(TESTER_PAGE, null, model, view, options, graphModelEnum);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (ParseException | IOException | McdException | ParserConfigurationException | SAXException e) {
                    e.printStackTrace();
                }
            }
        };
    }

    public void addButtonListeners() {
        analyzerButton.addActionListener(analyzerButtonListener);
        testerButton.addActionListener(testerButtonListener);
    }

    public void initAnalyzerListeners(AnalyzerState analyzerState, Model model, View view, Options options, GraphModelEnum graphModelEnum) {

        ListHelper listHelper = new ListHelper();

        // file listener
        fileListListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                selectedFiles = listHelper.getListSelections(fileList);
                displayGraphList.setSelectedIndex(0);
                selectedDisplayGraphStr = (String) displayGraphList.getSelectedValue();
                selectedGraph = displayGraphStringToEnum(selectedDisplayGraphStr, options.getAnalyzerDisplayGraphListItems());
                selectedStep = null;
                stepsList.clearSelection();
                stepsList.setListData(new Vector());
                stepsLabel.setForeground(Color.gray);
                stepsList.setForeground(Color.gray);
                FileSelectResult fileSelectResult = null; // graphs, kripke, labelHash, a couple lists

                try {
                    fileSelectResult = new FileSelectResult(selectedFiles, selectedGraph, selectedStep, model, view, options, false, false);
                    model.setFileSelectResultWithDefaults(fileSelectResult, model.getGraphModelFromEnum(GRAPH_MODEL));
                    renderAnalyzerPage(SELECT_MODEL, model, view, options, false, graphModelEnum);
                    setListeners(ANALYZER_PAGE, analyzerState, model, view, options, graphModelEnum);

                    // view.getMainPage().displayGraphList.setSelectedIndex(0);
                    // System.out.println(view.getMainPage().displayGraphList.getSelectedIndex());
                    // System.out.println(view.getMainPage().displayGraphList.getSelectedValue());

                } catch (SAXException saxException) {
                    saxException.printStackTrace();
                } catch (McdException mcdException) {
                    mcdException.printStackTrace();
                } catch (ParserConfigurationException parserConfigurationException) {
                    parserConfigurationException.printStackTrace();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                } catch (BadLocationException badLocationException) {
                    badLocationException.printStackTrace();
                }

            }
        };

        // display graphs listener
        displayGraphListListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                FileSelectResult fileSelectResult;
                // get display graph option clicked and record it to the model
                selectedDisplayGraphStr = (String) displayGraphList.getSelectedValue();
                selectedGraph = displayGraphStringToEnum(selectedDisplayGraphStr, options.getAnalyzerDisplayGraphListItems());
                // stepsList.setSelectedIndex(0);
                // stepsList.clearSelection();

                model.setSelectedDisplayGraph(selectedGraph);
                // model.setSelectedGraph(selectedGraph);

                if (selectedDisplayGraphStr != null && selectedFiles != null) {

                    stepsListModel.clear();

                    try {

                        // get number of steps for the step list (depends on whether it's the translation or interleavings)
                        Integer numStepsForStepList = null;
                        if (selectedGraph.equals(TRANSLATION_COMPARISON) || selectedGraph.equals(TRANSLATION_ONLY)) {
                            numStepsForStepList = model.getNumTranslationSteps();
                        } else if (selectedGraph.equals(INTERLEAVINGS_COMPARISON) || selectedGraph.equals(INTERLEAVINGS_ONLY)) {
                            numStepsForStepList = model.getNumInterleavingsSteps();
                        }

                        if (selectedGraph != XML_ONLY && selectedGraph != ALL_GRAPHS) {
                            // populate steps list
                            // stepsListModel.clear();
                            for (Integer i = 0; i < numStepsForStepList; i++) {
                                stepsListModel.addElement(i+1);
                            }
                            stepsLabel.setForeground(Color.black);   // make steps list not grayed-out
                            stepsList.setForeground(Color.black);
                        }

                    } catch (Exception exception) {
                        exception.printStackTrace();
                    }
                }

                if (selectedGraph != TRANSLATION_COMPARISON && selectedGraph != INTERLEAVINGS_COMPARISON) {
                    try {
                        renderAnalyzerPage(SELECT_MODEL, model, view, options, false, graphModelEnum);
                    } catch (McdException mcdException) {
                        mcdException.printStackTrace();
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                    setListeners(ANALYZER_PAGE, analyzerState, model, view, options, graphModelEnum);
                }

            }
        };

        // steps listener
        stepsListListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                String selectedStepStr = null;
                Integer selectedIndex = stepsList.getSelectedIndex();
                if (stepsList.getSelectedValue() != null) {
                    selectedStepStr = stepsList.getSelectedValue().toString();
                }

                if (selectedStepStr != null) {

                    // get the type of graph to display (translation / interleavings)
                    // DisplayGraphsEnum selectedGraph = model.getSelectedGraph();
                    // selectedGraph = model.getSelectedGraph();

                    // get the number selected and set to the model
                    selectedStep = Integer.parseInt(selectedStepStr);
                    // model.setSelectedStep(selectedStep);
                    // stepsList.setSelectedIndex(selectedIndex);

                    // clear out any old fileSelectResults
                    FileSelectResult fileSelectResult = null;
                    FileSelectResult fileSelectResultPrev = null;

                    // get the main graph (up to the selected step)
                    // get fileSelectResult
                    try {
                        fileSelectResult = new FileSelectResult(selectedFiles, MainPage.this.selectedGraph, selectedStep, model, view, options, true, false);
                    } catch (SAXException saxException) {
                        saxException.printStackTrace();
                    } catch (McdException mcdException) {
                        mcdException.printStackTrace();
                    } catch (ParserConfigurationException parserConfigurationException) {
                        parserConfigurationException.printStackTrace();
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                    model.setFileSelectResultWithDefaults(fileSelectResult, model.getGraphModelFromEnum(GRAPH_MODEL));
                    model.setSelectedStep(selectedStep);

                    // if a comparison display & step selected isn't first step,
                    // set GRAPH_MODEL_PREV_STEP step to selectedStep number minus one
                    if (
                            selectedGraph.equals(TRANSLATION_COMPARISON)
                            || selectedGraph.equals(INTERLEAVINGS_COMPARISON)
                            && selectedStep != null
                            && selectedStep != 1
                    ) {

                        // get graph for step just before the step above
                        Integer prevStep = selectedStep - 1;
                        try {
                            fileSelectResultPrev = new FileSelectResult(selectedFiles, MainPage.this.selectedGraph, prevStep, model, view, options, true, true);
                        } catch (SAXException saxException) {
                            saxException.printStackTrace();
                        } catch (McdException mcdException) {
                            mcdException.printStackTrace();
                        } catch (ParserConfigurationException parserConfigurationException) {
                            parserConfigurationException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                        model.setFileSelectResultWithDefaults(fileSelectResultPrev, model.getGraphModelFromEnum(GRAPH_MODEL_PREV_STEP));
                    }

                    try {
                        renderAnalyzerPage(SELECT_MODEL, model, view, options, false, graphModelEnum);
                    } catch (McdException mcdException) {
                        mcdException.printStackTrace();
                    } catch (BadLocationException badLocationException) {
                        badLocationException.printStackTrace();
                    }
                    setListeners(ANALYZER_PAGE, analyzerState, model, view, options, graphModelEnum);
                }

            }
        };

        if (analyzerState == SELECT_MODEL || analyzerState == DISPLAY_RESULTS) {

            // model listener
            modelListListener = new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {
                    selectedModel = modelList.getSelectedValue().toString();

                    // this is where the whole model checking process is launched
                    runModelCheckAndStoreResults(selectedModel, model, labelHash, GRAPH_MODEL);
                    if (model.getSelectedGraph() == TRANSLATION_COMPARISON
                            || model.getSelectedGraph() == INTERLEAVINGS_COMPARISON) {
                        runModelCheckAndStoreResults(selectedModel, model, labelHash, GRAPH_MODEL_PREV_STEP);
                    }
                    try {
                        renderAnalyzerPage(DISPLAY_RESULTS, model, view, options, false, graphModelEnum);
                    } catch (McdException | BadLocationException mcdException) {
                        mcdException.printStackTrace();
                    }
                    // setListeners(ANALYZER_PAGE, analyzerState, model, view, options);
                    setListeners(ANALYZER_PAGE, DISPLAY_RESULTS, model, view, options, graphModelEnum);

                }
            };

        }

        if (analyzerState == DISPLAY_RESULTS) {

            // loops listener
            JTextArea loopTextField = loopTextArea;
            loopTextField.addKeyListener(new KeyAdapter() {
                public void keyPressed(KeyEvent e) {
                    // allow digits or backspace only (textarea digits only strategy/code from https://www.tutorialspoint.com/how-can-we-make-jtextfield-accept-only-numbers-in-java, accessed 4/14/20)
                    if ((e.getKeyChar() >= '0' && e.getKeyChar() <= '9') || e.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                        loopTextArea.setEditable(true);
                    } else {
                        loopTextArea.setEditable(false);
                    }

                    // rerender page if enter is pressed
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {

                        Integer loops = Integer.parseInt(loopTextField.getText());
                        model.setSelectedLoopsNum(loops);

                        // selectedModel = modelList.getSelectedValue().toString();
                        selectedModel = model.getSelectedModel();
                        selectedGraph = model.getSelectedGraph();
                        selectedStep = model.getSelectedStep();

                        try {
                            FileSelectResult fileSelectResult = null;
                            fileSelectResult = new FileSelectResult(selectedFiles, selectedGraph, selectedStep, model, view, options, false, false);
                            model.getGraphModelFromEnum(GRAPH_MODEL).setFileSelectResult(fileSelectResult);
                            // get other items needed for the model checking from the model
                            Kripke kripke = model.getGraphModelFromEnum(GRAPH_MODEL).getInterleavingsKripke();
                        } catch (SAXException saxException) {
                            saxException.printStackTrace();
                        } catch (McdException mcdException) {
                            mcdException.printStackTrace();
                        } catch (ParserConfigurationException parserConfigurationException) {
                            parserConfigurationException.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }


                        if (selectedGraph == TRANSLATION_COMPARISON || selectedGraph == INTERLEAVINGS_COMPARISON) {
                            try {
                                FileSelectResult fileSelectResultPrev = null;
                                prevStep = selectedStep - 1;
                                fileSelectResultPrev = new FileSelectResult(selectedFiles, selectedGraph, prevStep, model, view, options, false, true);
                                model.getGraphModelFromEnum(GRAPH_MODEL_PREV_STEP).setFileSelectResult(fileSelectResultPrev);
                                // get other items needed for the model checking from the model
                                Kripke kripke = model.getGraphModelFromEnum(GRAPH_MODEL_PREV_STEP).getInterleavingsKripke();
                            } catch (SAXException saxException) {
                                saxException.printStackTrace();
                            } catch (McdException mcdException) {
                                mcdException.printStackTrace();
                            } catch (ParserConfigurationException parserConfigurationException) {
                                parserConfigurationException.printStackTrace();
                            } catch (IOException ioException) {
                                ioException.printStackTrace();
                            }
                        }


                        // int loops = model.getAnalyzerLoopsNumber();
                        Vertex stateToCheck = model.getGraphModelFromEnum(GRAPH_MODEL).getSelectedState();
                        // run the model checker
                        ModelChecker modelChecker = new ModelChecker(selectedModel, kripke, stateToCheck, loops, labelHash);
                        ModelCheckResult modelCheckResult = modelChecker.getModelCheckResult();
                        // set the results in the model
                        model.setSelectedModel(selectedModel);
                        model.getGraphModelFromEnum(GRAPH_MODEL).setModelCheckResult(modelCheckResult);

                        // if a graph comparison run, run the model again, on the previous step
                        DisplayGraphsEnum graphToDisplay = model.getSelectedGraph();
                        GraphModelObj graphModelPrev = model.getGraphModelFromEnum(GRAPH_MODEL_PREV_STEP);
                        if (graphToDisplay.equals(TRANSLATION_ONLY) || graphToDisplay.equals(INTERLEAVINGS_ONLY)) {
                            // int loops = model.getAnalyzerLoopsNumber();
                            stateToCheck = model.getGraphModelFromEnum(GRAPH_MODEL_PREV_STEP).getSelectedState();
                            // run the model checker
                            modelChecker = new ModelChecker(selectedModel, kripke, stateToCheck, loops, labelHash);
                            modelCheckResult = modelChecker.getModelCheckResult();
                            // set the results in the model
                            model.setSelectedModel(selectedModel);
                            model.getGraphModelFromEnum(GRAPH_MODEL_PREV_STEP).setModelCheckResult(modelCheckResult);
                        }

                        try {
                            renderAnalyzerPage(DISPLAY_RESULTS, model, view, options, false, graphModelEnum);
                        } catch (McdException | BadLocationException mcdException) {
                            mcdException.printStackTrace();
                        }

                        // renderAnalyzerPage(DISPLAY_RESULTS, model, view, options, false);

                        setListeners(ANALYZER_PAGE, analyzerState, model, view, options, graphModelEnum);
                    }

                }
            });

            // state listener
            stateListListener = new ListSelectionListener() {
                public void valueChanged(ListSelectionEvent e) {

                    // get selected state
                    selectedState = new VertexHelper().selectedValueToVertex(stateList.getSelectedValue(), model, model.getGraphModelFromEnum(graphModelEnum));
                    model.getGraphModelFromEnum(graphModelEnum).setSelectedState(selectedState);

                    // get other items needed for the model checking from the model
                    Kripke kripke = model.getGraphModelFromEnum(graphModelEnum).getInterleavingsKripke();
                    int loops = model.getAnalyzerLoopsNumber();
                    Vertex stateToCheck = model.getGraphModelFromEnum(graphModelEnum).getSelectedState();

                    // run the model checker
                    ModelChecker modelChecker = new ModelChecker(selectedModel, kripke, stateToCheck, loops, labelHash);
                    ModelCheckResult modelCheckResult = modelChecker.getModelCheckResult();

                    // set the results in the model
                    model.getGraphModelFromEnum(graphModelEnum).setModelCheckResult(modelCheckResult);
                    try {
                        renderAnalyzerPage(DISPLAY_RESULTS, model, view, options, false, graphModelEnum);
                    } catch (McdException | BadLocationException mcdException) {
                        mcdException.printStackTrace();
                    }
                    setListeners(ANALYZER_PAGE, analyzerState, model, view, options, graphModelEnum);

                }
            };
        }
    }

    public void addAnalyzerListeners(AnalyzerState analyzerState) {
        fileList.addListSelectionListener(fileListListener);
        displayGraphList.addListSelectionListener(displayGraphListListener);
        stepsList.addListSelectionListener(stepsListListener);
        if (analyzerState == SELECT_MODEL || analyzerState == DISPLAY_RESULTS) {
            modelList.addListSelectionListener(modelListListener);
        }
        if (analyzerState == DISPLAY_RESULTS) {
            // add loops listener
            stateList.addListSelectionListener(stateListListener);
        }
    }

    public void renderAnalyzerPageModelStatePreparations() {

    }

    public void initTesterListeners(Model model, View view, Options options, GraphModelEnum graphModelEnum) {

        ListHelper listHelper = new ListHelper();

        // file listener
        fileListListener = new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {

                selectedFiles = listHelper.getListSelections(fileList);
                selectedDisplayGraphStr = (String) displayGraphList.getSelectedValue();
                selectedGraph = displayGraphStringToEnum(selectedDisplayGraphStr, options.getAnalyzerDisplayGraphListItems());
                selectedStep = model.getSelectedStep();

                if (selectedFiles != null && selectedFiles.length > 0) {
                    FileSelectResult fileSelectResult = null; // graphs, kripke, labelHash, a couple lists
                    try {
                        fileSelectResult = new FileSelectResult(selectedFiles, selectedGraph, selectedStep, model, view, options, false, false);
                        model.setFileSelectResultWithDefaults(fileSelectResult, model.getGraphModelFromEnum(graphModelEnum));
                        model.setPageToDisplay(TESTER_PAGE);
                        rerenderTesterPage(model, view, options, false, graphModelEnum);
                        setListeners(TESTER_PAGE, null, model, view, options, graphModelEnum);
                    } catch (SAXException saxException) {
                        saxException.printStackTrace();
                    } catch (McdException mcdException) {
                        mcdException.printStackTrace();
                    } catch (ParserConfigurationException parserConfigurationException) {
                        parserConfigurationException.printStackTrace();
                    } catch (IOException | ParseException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        };

    }

    public void addTesterListeners() {
        fileList.addListSelectionListener(fileListListener);
    }

    // helpers
    // might have to add more to this if everything doesn't full clear between pageloads
    public void clearModel(Options options) throws FileNotFoundException, ParseException {
        model.setModelToInitialDefaults(options);
    }


    // getters & setters


    public JTextPane getResultTextpane() {
        return resultTextpane;
    }

    public JScrollPane getResultScrollpane() {
        return resultScrollpane;
    }

    public JPanel getButtonsPanel() {
        return buttonsPanel;
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }

    public JButton getAnalyzerButton() {
        return analyzerButton;
    }

    public JButton getTesterButton() {
        return testerButton;
    }

    public JFrame getFrame() {
        return frame;
    }

    public AnalyzerPage getAnalyzerPage() {
        return analyzerPage;
    }

    public Border getInstructionsBorder() {
        return instructionsBorder;
    }

    public JPanel getGraphInnerPanel() {
        return graphInnerPanel;
    }

    public JPanel getGraphPanel() {
        return graphPanel;
    }

    public JPanel getSidebar() {
        return sidebar;
    }

    public JLabel getFileLabel() {
        return fileLabel;
    }

    public JPanel getInterleavingsGraphPanel() {
        return interleavingsGraphPanel;
    }

    public JLabel getModelLabel() {
        return modelLabel;
    }

    public JPanel getKripkeGraphTitleWrapper() {
        return kripkeGraphTitleWrapper;
    }

    public JPanel getSidebarInner() {
        return sidebarInner;
    }

    public JPanel getSidebarPaddingLeft() {
        return sidebarPaddingLeft;
    }

    public JPanel getSidebarPaddingRight() {
        return sidebarPaddingRight;
    }

    public JPanel getSidebarWrapper() {
        return sidebarWrapper;
    }

    public JPanel getTranslationGraphPanel() {
        return translationGraphPanel;
    }

    public JPanel getXmlGraphPanel() {
        return xmlGraphPanel;
    }

    public MainPage getMainPage() {
        return mainPage;
    }

    public TesterPage getTesterPage() {
        return testerPage;
    }

    public GridBagConstraints getAnalyzerButtonStyle() {
        return analyzerButtonStyle;
    }

    public GridBagConstraints getButtonsPanelStyle() {
        return buttonsPanelStyle;
    }

    public GridBagConstraints getFileLabelStyle() {
        return fileLabelStyle;
    }

    public JLabel getLabelLabel() {
        return labelLabel;
    }

    public GridBagConstraints getFileListScrollPaneStyle() {
        return fileListScrollPaneStyle;
    }

    public GridBagConstraints getFileListStyle() {
        return fileListStyle;
    }

    public GridBagConstraints getInstructionsTextareaStyle() {
        return instructionsTextareaStyle;
    }

    public GridBagConstraints getLabelLabelStyle() {
        return labelLabelStyle;
    }

    public GridBagConstraints getLabelListStyle() {
        return labelListStyle;
    }

    public GridBagConstraints getLoopLabelStyle() {
        return loopLabelStyle;
    }

    public JLabel getLoopLabel() {
        return loopLabel;
    }

    public JLabel getInterleavingsTitle() {
        return interleavingsTitle;
    }

    public Font getLargeFont() {
        return largeFont;
    }

    public GridBagConstraints getLoopTextAreaStyle() {
        return loopTextAreaStyle;
    }

    public JLabel getResultLabel() {
        return resultLabel;
    }

    public Font getLabelFont() {
        return labelFont;
    }

    public Font getListFont() {
        return listFont;
    }

    public GridBagConstraints getModelLabelStyle() {
        return modelLabelStyle;
    }

    public GridBagConstraints getColumnStyle() {
        return columnStyle;
    }

    public GridBagConstraints getModelListScrollPaneStyle() {
        return modelListScrollPaneStyle;
    }

    public JLabel getStateLabel() {
        return stateLabel;
    }

    public GridBagConstraints getModelListStyle() {
        return modelListStyle;
    }

    public GridBagConstraints getResultLabelStyle() {
        return resultLabelStyle;
    }

    public GridBagConstraints getResultTextpaneStyle() {
        return resultTextpaneStyle;
    }

    public GridBagConstraints getResultScrollpaneStyle() {
        return resultScrollpaneStyle;
    }

    public GridBagConstraints getSidebarInnerStyle() {
        return sidebarInnerStyle;
    }

    public GridBagConstraints getSidebarPaddingLeftStyle() {
        return sidebarPaddingLeftStyle;
    }

    public GridBagConstraints getSidebarPaddingRightStyle() {
        return sidebarPaddingRightStyle;
    }

    public GridBagConstraints getSidebarStyle() {
        return sidebarStyle;
    }

    public Font getTitleFont() {
        return titleFont;
    }

    public GridBagConstraints getSidebarWrapperStyle() {
        return sidebarWrapperStyle;
    }

    public GridBagConstraints getStateLabelStyle() {
        return stateLabelStyle;
    }

    public GridBagConstraints getStateListStyle() {
        return stateListStyle;
    }

    public Border getAllResultsBorder() {
        return allResultsBorder;
    }

    public GridBagConstraints getStateScrollPaneStyle() {
        return stateScrollPaneStyle;
    }

    public GridBagConstraints getTesterButtonStyle() {
        return testerButtonStyle;
    }

    public JLabel getIndividualResultLabel() {
        return individualResultLabel;
    }

    public JLabel getTranslationTitle() {
        return translationTitle;
    }

    public JLabel getXmlGraphTitle() {
        return xmlGraphTitle;
    }

    public JList getActualLabelList() {
        return actualLabelList;
    }

    public JList getFileList() {
        return fileList;
    }

    public JList getLabelList() {
        return labelList;
    }

    public JList getModelList() {
        return modelList;
    }

    public JList getTestsXmlActualList() {
        return testsXmlActualList;
    }

    public JList getStateList() {
        return stateList;
    }

    public JPanel getCol1() {
        return col1;
    }

    public String[] getFileListArr() {
        return fileListArr;
    }

    public JPanel getCol1Inner() {
        return col1Inner;
    }

    public JPanel getCol1LeftPadding() {
        return col1LeftPadding;
    }

    public JList getExpectedLabelList() {
        return expectedLabelList;
    }

    public JPanel getCol1RightPadding() {
        return col1RightPadding;
    }

    public JPanel getSidebarPanel() {
        return sidebarPanel;
    }

    public JScrollPane getFileListScrollPane() {
        return fileListScrollPane;
    }

    public JList getTestsXmlExpectedList() {
        return testsXmlExpectedList;
    }

    public JScrollPane getLabelListScrollPane() {
        return labelListScrollPane;
    }

    public JPanel getTestsPanel() {
        return testsPanel;
    }

    public JScrollPane getModelListScrollPane() {
        return modelListScrollPane;
    }

    public Border getIndividualResultBorder() {
        return individualResultBorder;
    }

    public JScrollPane getStateListScrollPane() {
        return stateListScrollPane;
    }

    public JTextArea getAllResultsTextarea() {
        return allResultsTextarea;
    }

    public JTextArea getIndividualResultTextarea() {
        return individualResultTextarea;
    }

    public JLabel getAllResultsLabel() {
        return allResultsLabel;
    }

    public JTextArea getInstructionsTextarea() {
        return instructionsTextarea;
    }

    public JLabel getTestsXmlExpectedLabel() {
        return testsXmlExpectedLabel;
    }

    public JTextArea getLoopTextArea() {
        return loopTextArea;
    }

    public JSplitPane getTestsXmlExpectedSplitPane() {
        return testsXmlExpectedSplitPane;
    }

    public String[] getXmlActualArr() {
        return xmlActualArr;
    }

    public JLabel getTestsXmlActualLabel() {
        return testsXmlActualLabel;
    }

    public String[] getActualLabelArr() {
        return actualLabelArr;
    }

    public JSplitPane getTestsXmlActualSplitPane() {
        return testsXmlActualSplitPane;
    }

    public String[] getXmlExpectedArr() {
        return xmlExpectedArr;
    }

    public GridBagConstraints getCol1InnerStyle() {
        return col1InnerStyle;
    }

    public String[] getExpectedLabelArr() {
        return expectedLabelArr;
    }

    public GridBagConstraints getIndividualResultLabelStyle() {
        return individualResultLabelStyle;
    }

    public String[] getLabelArr() {
        return labelArr;
    }

    public GridBagConstraints getIndividualResultStyle() {
        return individualResultStyle;
    }

    public String[] getModelArr() {
        return modelArr;
    }



    public GridBagConstraints getAggregateResultLabel() {
        return aggregateResultLabel;
    }

    public String[] getStateArr() {
        return stateArr;
    }

    public GridBagConstraints getAggregateResultStyle() {
        return aggregateResultStyle;
    }

    public String[] getLabelListStrings() {
        return labelListStrings;
    }

    public JLabel getTestsXmlTitle() {
        return testsXmlTitle;
    }

    public JPanel getTestsXmlPanel() {
        return testsXmlPanel;
    }

    public JTextArea getTestsXmlExpectedTextArea() {
        return testsXmlExpectedTextArea;
    }

    public GridBagConstraints getXmlPanelGridPosition() {
        return xmlPanelGridPosition;
    }

    public JTextArea getTestsInterleavingsActualTextArea() {
        return testsInterleavingsActualTextArea;
    }

    public JTextArea getTestsInterleavingsExpectedTextArea() {
        return testsInterleavingsExpectedTextArea;
    }

    public JTextArea getTestsModelCheckingActualTextArea() {
        return testsModelCheckingActualTextArea;
    }

    public JTextArea getTestsModelCheckingExpectedTextArea() {
        return testsModelCheckingExpectedTextArea;
    }

    public JPanel getTesterSpacerRight() {
        return testerSpacerRight;
    }

    public JTextArea getTestsTranslationActualTextArea() {
        return testsTranslationActualTextArea;
    }

    public JPanel getTesterSpacerBottom0() {
        return testerSpacerBottom0;
    }

    public JTextArea getTestsTranslationExpectedTextArea() {
        return testsTranslationExpectedTextArea;
    }

    public JPanel getTesterSpacerBottom1() {
        return testerSpacerBottom1;
    }

    public JTextArea getTestsXmlActualTextArea() {
        return testsXmlActualTextArea;
    }

    public JPanel getTesterSpacerBottom2() {
        return testerSpacerBottom2;
    }

    public LabelHash getLabelHash() {
        return labelHash;
    }

    public String[] getDisplayGraphArr() {
        return displayGraphArr;
    }

    public JPanel getGraphsCheckboxPanel() {
        return graphsCheckboxPanel;
    }


}



