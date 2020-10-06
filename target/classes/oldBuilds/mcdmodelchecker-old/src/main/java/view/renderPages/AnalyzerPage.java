package view.renderPages;

import _options.Options;
import controller.dataTypes.graphHelpers.DisplayGraphsEnum;
import controller.dataTypes.graphHelpers.GraphModelEnum;
import controller.dataTypes.graphHelpers.VertexList;
import controller.dataTypes.graphItems.GraphModelObj;
import controller.dataTypes.pageHelpers.AnalyzerState;
import controller.helpers.ListHelper;
import controller.helpers.McdException;
import model.Model;
import view.View;
import view.drawGraphs.directedGraph.DirectedGraphOptions;
import view.drawGraphs.directedGraph.DrawDirectedGraph;
import view.drawGraphs.treeGraph.DrawTreeGraph;
import view.drawGraphs.treeGraph.TreeGraphOptions;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;

import static controller.dataTypes.graphHelpers.DisplayGraphsEnum.*;
import static controller.dataTypes.graphHelpers.GraphModelEnum.GRAPH_MODEL;
import static controller.dataTypes.graphHelpers.GraphModelEnum.GRAPH_MODEL_PREV_STEP;
import static controller.dataTypes.pageHelpers.AnalyzerState.DISPLAY_RESULTS;
import static controller.dataTypes.pageHelpers.AnalyzerState.SELECT_MODEL;
import static controller.helpers.DisplayGraphEnumHelper.displayGraphEnumToString;
import static controller.helpers.LineNumber.getLineNumber;


public class AnalyzerPage {

    public void setModelStateContent(Model model, View view, Options options) throws McdException {
        initContentBothModelAndResultsStates(model, view, options);
        setAnalyzerSelections(SELECT_MODEL, model, view);
        scrollAnalyzerListItemsIntoView(SELECT_MODEL, model, view, options);
    }

    public void setResultsStateContent(Model model, View view, Options options, GraphModelObj graphModel) throws McdException {
        initContentBothModelAndResultsStates(model, view, options);
        setAnalyzerSelections(DISPLAY_RESULTS, model, view);
        scrollAnalyzerListItemsIntoView(DISPLAY_RESULTS, model, view, options);
        String resultsString = graphModel.getModelCheckResult().getResultStr();
        view.getMainPage().resultTextpane.setText(resultsString);
    }

    public void initContentBothModelAndResultsStates(Model model, View view, Options options) throws McdException {
        ListHelper listHelper = new ListHelper();
        String stateListArr[] = listHelper.listToStringArray(model.getStateDisplayList());
        String[] labelListStrings = listHelper.listToStringArray(model.getLabelDisplayList());
        String[] displayGraphArr = model.getDisplayGraphList();
        String selectedDisplayGraph = displayGraphEnumToString(model.getSelectedDisplayGraph(), options.getAnalyzerDisplayGraphListItems());

        // TODO: move get stepsList arr here

        int selectedDisplayGraphIndex = listHelper.getSelectedIndexFromString(selectedDisplayGraph, view.getMainPage().displayGraphList);
        view.getMainPage().stateList.setListData(stateListArr);
        view.getMainPage().labelList.setListData(labelListStrings);
        view.getMainPage().displayGraphList.setListData(displayGraphArr);
        view.getMainPage().displayGraphList.setSelectedIndex(selectedDisplayGraphIndex);
        if (model.getSelectedStep() != null) {
            Integer selectedStep = model.getSelectedStep();
            view.getMainPage().stepsList.setSelectedValue(selectedStep, true);
        }

        // if (model.getSelectedStep() != null) {
            // Integer selectedStep = model.getSelectedStep();
            // view.getMainPage().stepsList.setSelectedValue(selectedStep, true);
            // String selectedStepStr = selectedStep.toString();
            // Integer stepListSelectedIndex = listHelper.getSelectedIndexFromNumberString(selectedStepStr, view.getMainPage().stepsList);
            // view.getMainPage().stepsList.setSelectedIndex(stepListSelectedIndex);
        // }
        DirectedGraphOptions xmlGraphOptions = getXmlGraphOptions(options);
        DirectedGraphOptions translationGraphOptions = getTranslationGraphOptions(options);
        DirectedGraphOptions interleavingsGraphOptions = getInterleavingsGraphOptions(options);
        DirectedGraphOptions halfWideGraphOptions = getHalfWideGraphOptions(options);

        // draw the graphs
        if (    model.getGraphModelFromEnum(GRAPH_MODEL).getXmlVertexList() != null
                && model.getSelectedDisplayGraph() == ALL_GRAPHS
                || model.getSelectedDisplayGraph() == XML_ONLY              ) {
            drawDirectedGraph(view.getMainPage().xmlGraphPanel, xmlGraphOptions, model.getGraphModelFromEnum(GRAPH_MODEL).getXmlVertexList());
        }
        if (    model.getGraphModelFromEnum(GRAPH_MODEL).getTranslationVertexList() != null
                && model.getSelectedDisplayGraph() == ALL_GRAPHS
                || model.getSelectedDisplayGraph() == TRANSLATION_ONLY) {
            drawDirectedGraph(view.getMainPage().translationGraphPanel, translationGraphOptions, model.getGraphModelFromEnum(GRAPH_MODEL).getTranslationVertexList());
        }
        if (model.getSelectedDisplayGraph() == TRANSLATION_COMPARISON) {
            VertexList vertexList = model.getGraphModelFromEnum(GRAPH_MODEL).getTranslationVertexList();
            VertexList vertexListPrev = model.getGraphModelFromEnum(GRAPH_MODEL_PREV_STEP).getTranslationVertexList();
            drawDirectedGraph(view.getMainPage().translationGraphPanel, halfWideGraphOptions, vertexListPrev);
            drawDirectedGraph(view.getMainPage().interleavingsGraphPanel, halfWideGraphOptions, vertexList);
        }
        if (model.getSelectedDisplayGraph() == INTERLEAVINGS_COMPARISON) {
            VertexList vertexList = model.getGraphModelFromEnum(GRAPH_MODEL).getInterleavingsVertexList();
            VertexList vertexListPrev = model.getGraphModelFromEnum(GRAPH_MODEL_PREV_STEP).getInterleavingsVertexList();
            drawDirectedGraph(view.getMainPage().translationGraphPanel, halfWideGraphOptions, vertexListPrev);
            drawDirectedGraph(view.getMainPage().interleavingsGraphPanel, halfWideGraphOptions, vertexList);
        }
        if (    model.getSelectedDisplayGraph() == ALL_GRAPHS
                || model.getSelectedDisplayGraph() == INTERLEAVINGS_ONLY) {
            drawDirectedGraph(view.getMainPage().interleavingsGraphPanel, interleavingsGraphOptions, model.getGraphModelFromEnum(GRAPH_MODEL).getInterleavingsVertexList());
        }

    }

    public void scrollAnalyzerListItemsIntoView(AnalyzerState analyzerState, Model model, View view, Options options) {
        int yPosMultiplier = options.getAnalyzerListVertMultiplier();
        if (model.getSelectedFiles() != null) {
            int firstSelectedIndex = view.getMainPage().fileList.getSelectedIndices()[0];
            int yPos = firstSelectedIndex * yPosMultiplier;
            view.getMainPage().fileListScrollPane.getViewport().setViewPosition(new Point(0, yPos));
        }
        if (analyzerState == DISPLAY_RESULTS) {
            if (model.getSelectedModel() != null) {
                int selectedModelIndex = view.getMainPage().modelList.getSelectedIndex();
                int yPos = selectedModelIndex * yPosMultiplier;
                view.getMainPage().modelListScrollPane.getViewport().setViewPosition(new Point(0, yPos));
            }
            if (model.getGraphModelFromEnum(GRAPH_MODEL).getSelectedState() != null) {
                int selectedStateIndex = view.getMainPage().stateList.getSelectedIndex();
                int yPos = selectedStateIndex * yPosMultiplier;
                view.getMainPage().stateListScrollPane.getViewport().setViewPosition(new Point(0, yPos));
            }
        }
    }

    public void initAnalyzerShell(Model model, View view, Options options, GraphModelEnum graphModelEnum) throws BadLocationException {
        initAnalyzerShellComponents(view);
        setAnalyzerShellComponentsLayoutModes(view);
        initAnalyzerShellStyles(view);
        setAnalyzerShellStyles(model, view, options); // graph title text & pane width logic here, among lots of other style stuff (there is also some graph styles set in Options.java around 220, i think these options are loaded when the component is added, in this file around 450).
        addAnalyzerShellComponents(view);
        addAnalyzerShellContent(model, view, options, graphModelEnum); // logic here for when/if the steps list in sidebar is populated, among lots of other stuff
    }

    public void setAnalyzerDisplayGraphAndStepSelections(Model model, View view, Options options) {
        ListHelper listHelper = new ListHelper();
        String selectedDisplayGraph = displayGraphEnumToString(model.getSelectedDisplayGraph(), options.getAnalyzerDisplayGraphListItems());
        if (selectedDisplayGraph != null) {
            int selectedDisplayGraphIndex = listHelper.getSelectedIndexFromString(selectedDisplayGraph, view.getMainPage().displayGraphList);
            view.getMainPage().displayGraphList.setSelectedIndex(selectedDisplayGraphIndex);
        }
        // if (model.getSelectedStep() != null) {
            // int stepsIndex = listHelper.getSelectedIndexFromNumberString(model.getSelectedStep().toString(), view.getMainPage().stepsList);
            // int selectedStep = model.getSelectedStep();
           // view.getMainPage().stepsList.setSelectedValue(selectedStep, true);
        // }
    }

    public void setAnalyzerSelections(AnalyzerState analyzerState, Model model, View view) {
        ListHelper listHelper = new ListHelper();
        String[] selectedFileStrings = listHelper.listToStringArray(model.getSelectedFiles());
        String selectedModel = model.getSelectedModel();
        Integer selectedLoops = model.getAnalyzerLoopsNumber();
        String selectedState = model.getGraphModelFromEnum(GRAPH_MODEL).getSelectedState().toString();
        if (selectedFileStrings != null) {
            int[] selectedFilesIndices = listHelper.getSelectedIndicesFromStrings(selectedFileStrings, view.getMainPage().fileList);
            view.getMainPage().fileList.setSelectedIndices(selectedFilesIndices);
        }
        if (analyzerState == DISPLAY_RESULTS) {
            if (selectedModel != null) {
                view.getMainPage().modelList.setSelectedValue(selectedModel, true);
            }
            if (selectedLoops != null) {
                view.getMainPage().loopTextArea.setText(selectedLoops.toString());
            }
            if (selectedState != null) {
                view.getMainPage().stateList.setSelectedValue(selectedState, true);
            }
        }
    }

    public void initAnalyzerShellComponents(View view) throws BadLocationException {
        view.getMainPage().mainPanel = new JPanel();
        view.getMainPage().sidebarWrapper = new JPanel();
        view.getMainPage().sidebar = new JPanel();
        view.getMainPage().sidebarInner = new JPanel();
        view.getMainPage().sidebarPaddingLeft = new JPanel();
        view.getMainPage().sidebarPaddingRight = new JPanel();
        view.getMainPage().graphsCheckboxPanel = new JPanel();
        view.getMainPage().buttonsPanel = new JPanel();
        view.getMainPage().analyzerButton = new JButton();
        view.getMainPage().testerButton = new JButton();
        view.getMainPage().instructionsTextarea = new JTextArea();
        view.getMainPage().fileLabel = new JLabel();
        view.getMainPage().fileList = new JList();
        view.getMainPage().displayGraphLabel = new JLabel();
        view.getMainPage().displayGraphList = new JList();
        view.getMainPage().graphOptionsScrollPane = new JScrollPane();
        view.getMainPage().stepsLabel = new JLabel();
        view.getMainPage().stepsList = new JList();
        view.getMainPage().stepsListModel = new DefaultListModel();
        view.getMainPage().stepsList.setModel(view.getMainPage().stepsListModel);
        view.getMainPage().stepsScrollPane = new JScrollPane();
        view.getMainPage().modelLabel = new JLabel();
        view.getMainPage().modelList = new JList();
        view.getMainPage().loopLabel = new JLabel();
        view.getMainPage().loopTextArea = new JTextArea();
        view.getMainPage().stateLabel = new JLabel();
        view.getMainPage().stateList = new JList();
        view.getMainPage().labelLabel = new JLabel();
        view.getMainPage().labelList = new JList();
        view.getMainPage().resultLabel = new JLabel();
        view.getMainPage().resultTextpane = new JTextPane();
        view.getMainPage().resultScrollpane = new JScrollPane();
        view.getMainPage().graphPanel = new JPanel();
        view.getMainPage().graphInnerPanel = new JPanel();
        view.getMainPage().xmlGraphTitle = new JLabel();
        view.getMainPage().translationTitle = new JLabel();
        view.getMainPage().interleavingsTitle = new JLabel();
        view.getMainPage().xmlGraphPanel = new JPanel();
        view.getMainPage().kripkeGraphTitleWrapper = new JPanel();
        view.getMainPage().translationGraphPanel = new JPanel();
        view.getMainPage().interleavingsGraphPanel = new JPanel();
    }

    public void setAnalyzerShellComponentsLayoutModes(View view) {
        view.getMainPage().mainPanel.setLayout(new BorderLayout());
        view.getMainPage().sidebarWrapper.setLayout(new GridBagLayout());
        view.getMainPage().sidebar.setLayout(new BorderLayout());
        view.getMainPage().sidebarInner.setLayout(new GridBagLayout());
        view.getMainPage().graphPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        view.getMainPage().xmlGraphPanel.setLayout(new BorderLayout());
        view.getMainPage().translationGraphPanel.setLayout(new BorderLayout());
        view.getMainPage().interleavingsGraphPanel.setLayout(new BorderLayout());
    }

    public void initAnalyzerShellStyles(View view) {
        view.getMainPage().sidebarWrapperStyle = new GridBagConstraints();
        view.getMainPage().sidebarWrapperStyle = new GridBagConstraints();
        view.getMainPage().sidebarStyle = new GridBagConstraints();
        view.getMainPage().sidebarInnerStyle = new GridBagConstraints();
        view.getMainPage().sidebarPaddingLeftStyle = new GridBagConstraints();
        view.getMainPage().buttonsPanelStyle = new GridBagConstraints();
        view.getMainPage().analyzerButtonStyle = new GridBagConstraints();
        view.getMainPage().testerButtonStyle = new GridBagConstraints();
        view.getMainPage().instructionsTextareaStyle = new GridBagConstraints();
        view.getMainPage().fileLabelStyle = new GridBagConstraints();
        view.getMainPage().fileListStyle = new GridBagConstraints();
        view.getMainPage().fileListScrollPaneStyle = new GridBagConstraints();
        view.getMainPage().modelLabelStyle = new GridBagConstraints();
        view.getMainPage().modelListStyle = new GridBagConstraints();
        view.getMainPage().modelListScrollPaneStyle = new GridBagConstraints();
        view.getMainPage().loopLabelStyle = new GridBagConstraints();
        view.getMainPage().loopTextAreaStyle = new GridBagConstraints();
        view.getMainPage().labelLabelStyle = new GridBagConstraints();
        view.getMainPage().labelListStyle = new GridBagConstraints();
        view.getMainPage().stateLabelStyle = new GridBagConstraints();
        view.getMainPage().stateListStyle = new GridBagConstraints();
        view.getMainPage().stateScrollPaneStyle = new GridBagConstraints();
        view.getMainPage().graphLabelStyle = new GridBagConstraints();
        view.getMainPage().graphOptionsListStyle = new GridBagConstraints();
        view.getMainPage().graphOptionsScrollpaneStyle = new GridBagConstraints();
        view.getMainPage().stepsLabelStyle = new GridBagConstraints();
        view.getMainPage().stepsListStyle = new GridBagConstraints();
        view.getMainPage().stepsScrollpaneStyle = new GridBagConstraints();
        view.getMainPage().resultLabelStyle = new GridBagConstraints();
        view.getMainPage().resultScrollpaneStyle = new GridBagConstraints();
        view.getMainPage().resultTextpaneStyle = new GridBagConstraints();
        view.getMainPage().sidebarPaddingRightStyle = new GridBagConstraints();
    }

    public void setAnalyzerShellStyles(Model model, View view, Options options) {
        DisplayGraphsEnum graphsToRender = model.getSelectedDisplayGraph();
        // fonts
        view.getMainPage().largeFont = new Font(options.getFont(), Font.PLAIN, options.getLargeFontSize());
        view.getMainPage().labelFont = new Font(options.getFont(), Font.PLAIN, options.getSectionTitleFontSize());
        view.getMainPage().listFont = new Font(options.getFont(), Font.PLAIN, options.getListFontSize());
        // window
        view.getMainPage().getFrame().setPreferredSize(new Dimension(options.getWindowWidth(), options.getWindowHeight()));
        // sidebar
        // sidebar left padding (invisible spacer column for padding)
        view.getMainPage().sidebarPaddingLeftStyle.gridx = 0;
        view.getMainPage().sidebarPaddingLeftStyle.gridy = 0;
        // button row
        view.getMainPage().buttonsPanel.add(view.getMainPage().analyzerButton);
        view.getMainPage().buttonsPanel.add(view.getMainPage().testerButton);
        view.getMainPage().buttonsPanelStyle.gridx = 1;
        view.getMainPage().buttonsPanelStyle.gridy = 0;
        // instructions box
        view.getMainPage().instructionsTextareaStyle.gridx = 1;
        view.getMainPage().instructionsTextareaStyle.gridy = 1;
        view.getMainPage().instructionsTextareaStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().instructionsTextarea.setFont(view.getMainPage().labelFont);
        view.getMainPage().instructionsBorder = BorderFactory.createLineBorder(Color.BLACK, 3);
        view.getMainPage().instructionsTextarea.setBorder(BorderFactory.createCompoundBorder(view.getMainPage().instructionsBorder, BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        // files label
        view.getMainPage().fileLabelStyle.gridx = 1;
        view.getMainPage().fileLabelStyle.gridy = 2;
        view.getMainPage().fileLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().fileLabel.setBorder(BorderFactory.createEmptyBorder(15, 5, 5, 5));
        view.getMainPage().fileLabel.setFont(view.getMainPage().labelFont);
        // files list
        view.getMainPage().fileList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        view.getMainPage().fileList.setSelectionBackground(options.getSelectedListItemColor());
        view.getMainPage().fileList.setFont(view.getMainPage().listFont);
        view.getMainPage().fileListStyle.gridx = 1;
        view.getMainPage().fileListStyle.gridy = 3;
        view.getMainPage().fileListStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().fileListScrollPane = new JScrollPane(view.getMainPage().fileList);
        view.getMainPage().fileListScrollPane.setPreferredSize(new Dimension(150, 100));
        // display graphs label
        view.getMainPage().graphLabelStyle.gridx = 1;
        view.getMainPage().graphLabelStyle.gridy = 4;
        view.getMainPage().graphLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().displayGraphLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().displayGraphLabel.setFont(view.getMainPage().labelFont);
        // display graphs list
        view.getMainPage().displayGraphList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        view.getMainPage().displayGraphList.setSelectionBackground(options.getSelectedListItemColor());
        view.getMainPage().displayGraphList.setFont(view.getMainPage().listFont);
        view.getMainPage().graphOptionsListStyle.gridx = 1;
        view.getMainPage().graphOptionsListStyle.gridy = 5;
        view.getMainPage().graphOptionsListStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().graphOptionsScrollPane = new JScrollPane(view.getMainPage().displayGraphList);
        view.getMainPage().graphOptionsScrollPane.setPreferredSize(new Dimension(150, 67));
        // steps label
        view.getMainPage().stepsLabelStyle.gridx = 1;
        view.getMainPage().stepsLabelStyle.gridy = 6;
        view.getMainPage().stepsLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().stepsLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().stepsLabel.setFont(view.getMainPage().labelFont);
        // steps list
        view.getMainPage().stepsList.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        view.getMainPage().stepsList.setSelectionBackground(options.getSelectedListItemColor());
        view.getMainPage().stepsList.setFont(view.getMainPage().listFont);
        view.getMainPage().stepsListStyle.gridx = 1;
        view.getMainPage().stepsListStyle.gridy = 7;
        view.getMainPage().stepsListStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().stepsScrollPane = new JScrollPane(view.getMainPage().stepsList);
        view.getMainPage().stepsScrollPane.setPreferredSize(new Dimension(150, 67));
        // models label
        view.getMainPage().modelLabelStyle.gridx = 1;
        view.getMainPage().modelLabelStyle.gridy = 8;
        view.getMainPage().modelLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().modelLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().modelLabel.setFont(view.getMainPage().labelFont);
        // models list
        view.getMainPage().modelList.setSelectionBackground(options.getSelectedListItemColor());
        view.getMainPage().modelList.setFont(view.getMainPage().listFont);
        view.getMainPage().modelListStyle.gridx = 1;
        view.getMainPage().modelListStyle.gridy = 9;
        view.getMainPage().modelListStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().modelListScrollPane = new JScrollPane(view.getMainPage().modelList);
        view.getMainPage().modelListScrollPane.setPreferredSize(new Dimension(150, 100));
        // number of loops label
        view.getMainPage().loopLabelStyle.gridx = 1;
        view.getMainPage().loopLabelStyle.gridy = 10;
        view.getMainPage().loopLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().loopLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().loopLabel.setFont(view.getMainPage().labelFont);
        // loops list
        view.getMainPage().loopTextArea.setFont(view.getMainPage().listFont);
        view.getMainPage().loopTextAreaStyle.gridx = 1;
        view.getMainPage().loopTextAreaStyle.gridy = 11;
        view.getMainPage().loopTextAreaStyle.fill = GridBagConstraints.HORIZONTAL;
        // state label
        view.getMainPage().stateLabel.setFont(view.getMainPage().labelFont);
        view.getMainPage().stateLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        view.getMainPage().stateLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().stateLabelStyle.gridx = 1;
        view.getMainPage().stateLabelStyle.gridy = 12;
        view.getMainPage().stateLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        // states list
        view.getMainPage().stateList.setSelectionBackground(options.getSelectedListItemColor());
        view.getMainPage().stateList.setFont(view.getMainPage().listFont);
        view.getMainPage().stateList.setAlignmentX(Component.TOP_ALIGNMENT);
        view.getMainPage().stateListScrollPane = new JScrollPane(view.getMainPage().stateList);
        view.getMainPage().stateScrollPaneStyle.gridx = 1;
        view.getMainPage().stateScrollPaneStyle.gridy = 13;
        view.getMainPage().stateScrollPaneStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().stateListScrollPane.setPreferredSize(new Dimension(150, 67));
        // labels label
        view.getMainPage().labelLabelStyle.gridx = 1;
        view.getMainPage().labelLabelStyle.gridy = 14;
        view.getMainPage().labelLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().labelLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().labelLabel.setFont(view.getMainPage().labelFont);
        // labels list
        view.getMainPage().labelList.setFont(view.getMainPage().listFont);
        view.getMainPage().labelListStyle.gridx = 1;
        view.getMainPage().labelListStyle.gridy = 15;
        view.getMainPage().labelListStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().labelListScrollPane = new JScrollPane(view.getMainPage().labelList);
        view.getMainPage().labelListScrollPane.setPreferredSize(new Dimension(150, 37));
        // results label
        view.getMainPage().resultLabelStyle.gridx = 1;
        view.getMainPage().resultLabelStyle.gridy = 16;
        view.getMainPage().resultLabelStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 5, 5, 5));
        view.getMainPage().resultLabel.setFont(view.getMainPage().largeFont);
        // results text box
        view.getMainPage().resultScrollpaneStyle.gridx = 1;
        view.getMainPage().resultScrollpaneStyle.gridy = 17;
        view.getMainPage().resultScrollpane.setPreferredSize(new Dimension(150, 300));
        view.getMainPage().resultScrollpane.setViewportView(view.getMainPage().resultTextpane);
        view.getMainPage().resultScrollpaneStyle.fill = GridBagConstraints.HORIZONTAL;
        view.getMainPage().resultScrollpane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        // sidebar padding right
        view.getMainPage().sidebarPaddingRightStyle.gridx = 2;
        view.getMainPage().sidebarPaddingRightStyle.gridy = 0;
        // main panel layout
        view.getMainPage().sidebarWrapperStyle.anchor = GridBagConstraints.NORTH;
        view.getMainPage().sidebarWrapperStyle.weighty = 1;
        // graphs panel color
        view.getMainPage().graphPanel.setBackground(options.getMainPanelBackgroundColor());

        // xml graphs
        view.getMainPage().xmlGraphPanel.setBackground(Color.WHITE);
        if (graphsToRender == ALL_GRAPHS) {
            view.getMainPage().xmlGraphPanel.setPreferredSize(new Dimension(options.getXmlGraphPanelWidth(), options.getXmlGraphPanelHeight()));
        } else if (graphsToRender == XML_ONLY) {
            view.getMainPage().xmlGraphPanel.setPreferredSize(new Dimension(options.getGraphFullLayoutWidth(), options.getXmlGraphPanelHeight()));
            view.getMainPage().translationGraphPanel.remove(view.getMainPage().translationTitle);
            view.getMainPage().interleavingsGraphPanel.remove(view.getMainPage().interleavingsTitle);
        }
        // translation graphs
        view.getMainPage().translationGraphPanel.setBackground(Color.WHITE);
        if (graphsToRender == ALL_GRAPHS || graphsToRender == TRANSLATION_COMPARISON) {
            view.getMainPage().translationGraphPanel.setPreferredSize(new Dimension(options.getTranslationGraphLayoutWidth(), options.getTranslationGraphLayoutHeight()));
        } else if (graphsToRender == TRANSLATION_ONLY) {
            view.getMainPage().translationGraphPanel.setPreferredSize(new Dimension(options.getGraphFullLayoutWidth(), options.getTranslationGraphLayoutHeight()));
            view.getMainPage().xmlGraphPanel.remove(view.getMainPage().xmlGraphTitle);
            view.getMainPage().interleavingsGraphPanel.remove(view.getMainPage().interleavingsTitle);
        }
        // TODO: Translation Comparison Styles Here
        // interleavings graphs
        view.getMainPage().interleavingsGraphPanel.setBackground(Color.WHITE);
        if (graphsToRender == ALL_GRAPHS) {
            view.getMainPage().interleavingsGraphPanel.setPreferredSize(new Dimension(options.getInterleavingsGraphLayoutWidth(), options.getInterleavingsGraphLayoutHeight()));
            view.getMainPage().stepsLabel.setForeground(Color.gray);
            view.getMainPage().stepsList.setForeground(Color.gray);
        } else if (graphsToRender == INTERLEAVINGS_ONLY) {
            view.getMainPage().interleavingsGraphPanel.setPreferredSize(new Dimension(options.getGraphFullLayoutWidth(), options.getInterleavingsGraphLayoutHeight()));
            view.getMainPage().xmlGraphPanel.remove(view.getMainPage().xmlGraphTitle);
            view.getMainPage().translationGraphPanel.remove(view.getMainPage().translationTitle);
        }
        // TODO: Interleavings Comparison Styles Here
        // button panel padding
        view.getMainPage().buttonsPanel.setBorder(BorderFactory.createEmptyBorder(7, 10, 6, 10));
    }

    public void addAnalyzerShellComponents(View view) {
        view.getMainPage().sidebarInner.add(view.getMainPage().sidebarPaddingRight, view.getMainPage().sidebarPaddingRightStyle);
        view.getMainPage().sidebar.add(view.getMainPage().sidebarInner, BorderLayout.NORTH);
        view.getMainPage().sidebarWrapper.add(view.getMainPage().sidebar, view.getMainPage().sidebarWrapperStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().sidebarPaddingLeft, view.getMainPage().sidebarPaddingLeftStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().buttonsPanel, view.getMainPage().buttonsPanelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().instructionsTextarea, view.getMainPage().instructionsTextareaStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().fileLabel, view.getMainPage().fileLabelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().fileListScrollPane, view.getMainPage().fileListStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().displayGraphLabel, view.getMainPage().graphLabelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().graphOptionsScrollPane, view.getMainPage().graphOptionsListStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().stepsLabel, view.getMainPage().stepsLabelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().stepsScrollPane, view.getMainPage().stepsListStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().modelLabel, view.getMainPage().modelLabelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().modelListScrollPane, view.getMainPage().modelListStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().loopLabel, view.getMainPage().loopLabelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().loopTextArea, view.getMainPage().loopTextAreaStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().stateLabel, view.getMainPage().stateLabelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().stateListScrollPane, view.getMainPage().stateScrollPaneStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().labelLabel, view.getMainPage().labelLabelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().labelListScrollPane, view.getMainPage().labelListStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().resultLabel, view.getMainPage().resultLabelStyle);
        view.getMainPage().sidebarInner.add(view.getMainPage().resultScrollpane, view.getMainPage().resultScrollpaneStyle);
        view.getMainPage().xmlGraphPanel.add(view.getMainPage().xmlGraphTitle, BorderLayout.NORTH);
        view.getMainPage().translationGraphPanel.add(view.getMainPage().translationTitle, BorderLayout.NORTH);
        view.getMainPage().interleavingsGraphPanel.add(view.getMainPage().interleavingsTitle, BorderLayout.NORTH);
        view.getMainPage().graphPanel.add(view.getMainPage().xmlGraphPanel);
        view.getMainPage().graphPanel.add(view.getMainPage().translationGraphPanel);
        view.getMainPage().graphPanel.add(view.getMainPage().interleavingsGraphPanel);
        view.getMainPage().mainPanel.add(view.getMainPage().sidebarWrapper, BorderLayout.WEST);
        view.getMainPage().mainPanel.add(view.getMainPage().graphPanel);
        view.getMainPage().getFrame().add(view.getMainPage().mainPanel, BorderLayout.NORTH);
    }

    public void addAnalyzerShellContent(Model model, View view, Options options, GraphModelEnum graphModelEnum) {
        DisplayGraphsEnum graphsToRender = model.getSelectedDisplayGraph();
        // window title
        view.getMainPage().getFrame().setTitle(model.getAnalyzerWindowTitle());
        // button text
        view.getMainPage().analyzerButton.setText(model.getButtonTextAnalyzer());
        view.getMainPage().testerButton.setText(model.getButtonTextTester());
        // instructions box text
        view.getMainPage().instructionsTextarea.setText(model.getAnalyzerInstructionsText());
        // input files
        view.getMainPage().fileLabel.setText(model.getAnalyzerFilesTitle());
        view.getMainPage().fileListArr = model.getFileList();
        view.getMainPage().fileList.setListData(view.getMainPage().fileListArr);
        // graphs
        view.getMainPage().displayGraphLabel.setText(model.getAnalyzerGraphsTitle());
        view.getMainPage().displayGraphArr = model.getDisplayGraphList();
        view.getMainPage().displayGraphList.setListData(view.getMainPage().displayGraphArr);
        // steps
        view.getMainPage().stepsLabel.setText(model.getAnalyzerStepsTitle());
        DisplayGraphsEnum displayGraph = model.getSelectedDisplayGraph();
        if (displayGraph != ALL_GRAPHS || displayGraph != XML_ONLY) {
            // Integer numSteps = getNumSteps(displayGraph, model.getGraphModelFromEnum(graphModelEnum));
            Integer numSteps;
            String[] stepListArr;
            // if (numSteps != null) {
            //     stepListArr = populateStepsArr(numSteps);
            //     view.getMainPage().stepsList.setListData(stepListArr);
            // } else {
            if (
                    model.getNumTranslationSteps() != null
                            && displayGraph == TRANSLATION_ONLY
                            || displayGraph == TRANSLATION_COMPARISON
            ) {
                String[] translationSteps = populateStepsArr(model.getNumTranslationSteps());
                view.getMainPage().stepsListModel.clear();
                for (int i=0; i<translationSteps.length; i++) {
                    String step = translationSteps[i];
                    view.getMainPage().stepsListModel.addElement(step);
                }
                // view.getMainPage().stepsList.setListData(translationSteps);
            } else if (displayGraph == INTERLEAVINGS_ONLY || displayGraph == INTERLEAVINGS_COMPARISON) {
                String[] interleavingsSteps = populateStepsArr(model.getNumInterleavingsSteps());
                view.getMainPage().stepsListModel.clear();
                for (int i=0; i<interleavingsSteps.length; i++) {
                    String step = interleavingsSteps[i];
                    view.getMainPage().stepsListModel.addElement(step);
                }
                // view.getMainPage().stepsList.setListData(interleavingsSteps);
            }
            // view.getMainPage().stepsList.setSelectedIndex(model.getSelectedStep() - 1);
            // if (model.getSelectedStep() != null) {
            //      view.getMainPage().stepsList.setSelectedValue(model.getSelectedStep(), true); // i think this is one-based?
          //  }
        }
        // populateStepsList(graphsToRender, model, view);
        // models
        view.getMainPage().modelLabel.setText(model.getAnalyzerModelsTitle());
        view.getMainPage().modelArr = model.getAnalyzerModelsList();
        view.getMainPage().modelList.setListData(view.getMainPage().modelArr);
        // loops
        view.getMainPage().loopLabel.setText(model.getAnalyzerLoopsTitle());
        view.getMainPage().loopTextArea.setText(model.getAnalyzerLoopsNumber().toString());
        // state
        view.getMainPage().stateLabel.setText(model.getAnalyzerStatesTitle());
        // label
        view.getMainPage().labelLabel.setText(model.getAnalyzerLabelsTitle());
        // results
        view.getMainPage().resultLabel.setText(model.getAnalyzerResultsTitle());
        // xml graphs
        if (graphsToRender == ALL_GRAPHS || graphsToRender == XML_ONLY) {
            view.getMainPage().xmlGraphTitle.setText(model.getXmlGraphTitle());
            view.getMainPage().xmlGraphTitle.setHorizontalAlignment(SwingConstants.CENTER);
            view.getMainPage().xmlGraphTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        }
        // kripke graphs
        if (graphsToRender == ALL_GRAPHS || graphsToRender == TRANSLATION_ONLY) {
            String translationTitle = addTotalStepsToTitle(model.getTranslationGraphTitle(), model.getNumTranslationSteps());
            view.getMainPage().translationTitle.setText(translationTitle);
            view.getMainPage().translationTitle.setHorizontalAlignment(SwingConstants.CENTER);
            view.getMainPage().translationTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        }
        // interleavings graphs
        if (graphsToRender == ALL_GRAPHS || graphsToRender == INTERLEAVINGS_ONLY) {
            String interleavingsTitle = addTotalStepsToTitle(model.getInterleavingsGraphTitle(), model.getNumInterleavingsSteps());
            view.getMainPage().interleavingsTitle.setText(interleavingsTitle);
            view.getMainPage().interleavingsTitle.setHorizontalAlignment(SwingConstants.CENTER);
            view.getMainPage().interleavingsTitle.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        }
    }

    /*
    public String[] getStepsListArr(DisplayGraphsEnum graphsToRender, Model model, View view, GraphModelObj graphModel) {
        Integer numSteps = getNumSteps(graphsToRender, graphModel);
        if (numSteps != null) {
            String[] stepsStrArr = populateStepsArr(numSteps);
            view.getMainPage().stepsArr = stepsStrArr;
            view.getMainPage().stepsList.setListData(view.getMainPage().stepsArr);
            if (model.getSelectedStep() != null) {
            //     view.getMainPage().stepsList.setSelectedIndex(model.getSelectedStep() - 1);
            }
        }
    }
    */

    public Integer getNumSteps(DisplayGraphsEnum graphsToRender, GraphModelObj graphModel) {
        Integer numSteps = null;
        if (graphsToRender == TRANSLATION_ONLY) {
            numSteps = graphModel.getTranslationVertexList().getNumTotalSteps();
        } else if (graphsToRender == INTERLEAVINGS_ONLY) {
            numSteps = graphModel.getInterleavingsVertexList().getNumTotalSteps();
        }
        return numSteps;
    }

    public String[] populateStepsArr(Integer numSteps) {
        String steps[] = new String[numSteps];
        if (numSteps != null) {
            for (int i=0; i<numSteps; i++) {
                String stepNumStr = Integer.toString(i+1); // number of steps is one-based
                steps[i] = stepNumStr;
            }
        } else {
            new McdException("numSteps is null at AnalyzerPage.java:497");
        }
        return steps;
    }

    public String addTotalStepsToTitle(String title, Integer steps) {
        if (steps != null) {
            title = title + "\n(" + steps + " steps)";
        }
        return title;
    }

    public void grayOutInactiveSectionsFileState(View view) {
        view.getMainPage().modelLabel.setForeground(Color.gray);
        view.getMainPage().modelList.setForeground(Color.gray);
        view.getMainPage().modelListScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#fafafa")));
        grayOutInactiveSectionsBothFileAndModelStates(view);
    }

    public void grayOutInactiveSectionsModelState(View view) {
        grayOutInactiveSectionsBothFileAndModelStates(view);
    }

    public void grayOutInactiveSectionsBothFileAndModelStates(View view) {
        view.getMainPage().loopLabel.setForeground(Color.gray);
        view.getMainPage().loopTextArea.setForeground(Color.gray);
        view.getMainPage().stateLabel.setForeground(Color.gray);
        view.getMainPage().stateList.setForeground(Color.gray);
        view.getMainPage().labelLabel.setForeground(Color.gray);
        view.getMainPage().stateListScrollPane.setBorder(BorderFactory.createLineBorder(Color.decode("#fafafa")));
        view.getMainPage().labelList.setForeground(Color.gray);
        view.getMainPage().resultLabel.setForeground(Color.gray);
        view.getMainPage().resultScrollpane.setBorder(BorderFactory.createLineBorder(Color.decode("#fafafa")));
        view.getMainPage().resultTextpane.setForeground(Color.gray);
    }

    // helpers
    public DirectedGraphOptions getXmlGraphOptions(Options options) {
        DirectedGraphOptions xmlGraphOptions = new DirectedGraphOptions(
                options.getXmlGraphNodeDiameter(),
                options.getTranslationGraphLevel(),
                options.getTranslationGraphNumEdges(),
                options.getTranslationGraphCanvasWidth(),
                options.getTranslationGraphCanvasHeight(),
                options.getTranslationGraphVertexSiblingOffset(),
                options.getTranslationGraphVertexVertMultiplier(),
                options.getXmlGraphLayoutWidth(),
                options.getXmlGraphLayoutHeight(),
                options.getXmlGraphEdgeXLength(),
                options.getXmlGraphEdgeYLength(),
                options.getXmlGraphScaleFactor(),
                options.getXmlGraphVertexAttractionMultiplier()
        );
        return xmlGraphOptions;
    }

    public DirectedGraphOptions getTranslationGraphOptions(Options options) {
        DirectedGraphOptions translationGraphOptions = new DirectedGraphOptions(
                options.getTranslationGraphNodeDiameter(),
                options.getTranslationGraphLevel(),
                options.getTranslationGraphNumEdges(),
                options.getTranslationGraphCanvasWidth(),
                options.getTranslationGraphCanvasHeight(),
                options.getTranslationGraphVertexSiblingOffset(),
                options.getTranslationGraphVertexVertMultiplier(),
                options.getTranslationGraphLayoutWidth(),
                options.getTranslationGraphLayoutHeight(),
                options.getTranslationGraphEdgeXLength(),
                options.getTranslationGraphEdgeYLength(),
                options.getTranslationGraphScaleFactor(),
                options.getTranslationGraphVertexAttractionMultiplier()
        );
        return translationGraphOptions;
    }

    public DirectedGraphOptions getInterleavingsGraphOptions(Options options) {
        DirectedGraphOptions interleavingsGraphOptions = new DirectedGraphOptions(
                options.getInterleavingsGraphNodeDiameter(),
                options.getTranslationGraphLevel(),
                options.getTranslationGraphNumEdges(),
                options.getTranslationGraphCanvasWidth(),
                options.getTranslationGraphCanvasHeight(),
                options.getTranslationGraphVertexSiblingOffset(),
                options.getTranslationGraphVertexVertMultiplier(),
                options.getTranslationGraphLayoutWidth(),
                options.getTranslationGraphLayoutHeight(),
                options.getTranslationGraphEdgeXLength(),
                options.getTranslationGraphEdgeYLength(),
                options.getTranslationGraphScaleFactor(),
                options.getTranslationGraphVertexAttractionMultiplier()
        );
        return interleavingsGraphOptions;
    }

    public DirectedGraphOptions getHalfWideGraphOptions(Options options) {
        DirectedGraphOptions interleavingsGraphOptions = new DirectedGraphOptions(
                options.getInterleavingsGraphNodeDiameter(),
                options.getTranslationGraphLevel(),
                options.getTranslationGraphNumEdges(),
                options.getTranslationGraphCanvasWidth(),
                options.getTranslationGraphCanvasHeight(),
                options.getTranslationGraphVertexSiblingOffset(),
                options.getTranslationGraphVertexVertMultiplier(),
                options.getTranslationGraphLayoutWidth(),
                options.getTranslationGraphLayoutHeight(),
                options.getTranslationGraphEdgeXLength(),
                options.getTranslationGraphEdgeYLength(),
                options.getTranslationGraphScaleFactor(),
                options.getTranslationGraphVertexAttractionMultiplier()
        );
        return interleavingsGraphOptions;
    }

    public void drawTreeGraph(JPanel thisGraphPanel, TreeGraphOptions treeGraphOptions, VertexList vertexList) throws McdException {
        if (vertexList != null && vertexList.getList().size() > 0) {
            DrawTreeGraph graph;
            graph = new DrawTreeGraph(treeGraphOptions);
            graph.drawGraph(thisGraphPanel, vertexList);
        } else {
            throw new McdException("view.layouts.AnalyzerLayout.java " + getLineNumber() + ": Vertex list is null or empty at drawTreeGraph()");
        }
    }

    public void drawDirectedGraph(JPanel thisGraphPanel, DirectedGraphOptions directedGraphOptions, VertexList vertexList) throws McdException {
        if (vertexList != null && vertexList.getList().size() > 0) {
            DrawDirectedGraph graph;
            graph = new DrawDirectedGraph(directedGraphOptions);
            graph.drawGraph(thisGraphPanel, vertexList);
        } else {
            throw new McdException("view.layouts.AnalyzerLayout.java " + getLineNumber() + ": Vertex list is null or empty at drawDirectedGraph()");
        }

    }

}
