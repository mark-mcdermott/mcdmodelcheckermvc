package _options;

import controller.dataTypes.graphHelpers.DisplayGraphsEnum;
import controller.dataTypes.pageHelpers.PageEnum;

import javax.swing.*;
import java.awt.*;

import static controller.dataTypes.graphHelpers.DisplayGraphsEnum.*;
import static controller.dataTypes.pageHelpers.PageEnum.ANALYZER_PAGE;

public class Options {

    // feel free to change any _options in this file

    Boolean debug;
    int targetNumNodesExpanded;

    private PageEnum defaultPageToRender;
    private DisplayGraphsEnum defaultGraphsToRender;
    private String pathToXmlDir;
    private String pathToKrpDir;
    private String modelsPath1Var;
    private String modelsPath2Vars;
    private String pathToTests;
    private String buttonTextAnalyzer;
    private String buttonTextTester;
    private String analyzerWindowTitle;
    private String analyzerInstructionsText;
    private String analyzerFilesTitle;
    private String analyzerModelsTitle;
    private String analyzerLoopsTitle;
    private Integer analyzerLoopsNumber;
    private String analyzerStatesTitle;
    private String analyzerLabelsTitle;
    private String analyzerDisplayGraphsTitle;
    private String analyzerStepsTitle;
    private String analyzerDisplayGraphListItems;
    private String analyzerResultsTitle;
    private String xmlGraphTitle;
    private String translationGraphTitle;
    private String interleavingsGraphTitle;
    private String defaultFileSelection;
    private String defaultModelSelection;
    private String defaultStateSelection;

    private String testerFileLabel;
    private String testerIndividualTestLabel;
    private String testerAggregateTestLabel;
    private String testerXmlTitle;
    private String testerXmlExpectedTitle;
    private String testerXmlActualTitle;
    private String testerTranslationTitle;
    private String testerTranslationExpectedTitle;
    private String testerTranslationActualTitle;
    private String testerInterleavingsTitle;
    private String testerInterleavingsExpectedTitle;
    private String testerInterleavingsActualTitle;
    private String testerModelCheckingTitle;
    private String testerModelCheckingExpectedTitle;
    private String testerModelCheckingActualTitle;
    private int testerKripkeLinesToSkip;
    private int testerModelCheckingLinesToSkip;
    private int testerLineLength;

    private String font;
    private String testerPageTitle;
    private Color mainPanelBackgroundColor;
    private Color selectedListItemColor;

    private int windowCloseOperation;
    private int largeFontSize;
    private int sectionTitleFontSize;
    private int listFontSize;
    private int graphPanelsWidth;
    private int graphPanelsHeight;
    private int graphLayoutsWidth;
    private int graphHalfLayoutWidth;
    private int graphFullLayoutWidth;
    private int graphLayoutsHeight;
    private int windowWidth;
    private int windowHeight;
    private Integer analyzerDefaultModelIndex;
    private Integer analyzerDefaultStateIndex;
    private Integer analyzerDefaultLabelIndex;

    private int xmlGraphEdgeXLength;
    private int xmlGraphEdgeYLength;
    private int xmlGraphNodeDiameter;
    private int xmlGraphPanelWidth;
    private int xmlGraphPanelHeight;
    private int xmlGraphLayoutWidth;
    private int xmlGraphLayoutHeight;
    private int xmlGraphVertexSiblingOffset;
    private double xmlGraphVertexAttractionMultiplier;

    private int translationGraphEdgeXLength;
    private int translationGraphEdgeYLength;
    private int translationGraphNodeDiameter;
    private int translationGraphPanelWidth;
    private int translationGraphPanelHeight;
    private int translationGraphLayoutWidth;
    private int translationGraphLayoutHeight;
    private int analyzerListVertMultiplier;
    private int analyzerResultsLineLength;

    private Integer translationGraphLevel;
    private Integer translationGraphNumEdges;
    private Integer translationGraphCanvasWidth;
    private Integer translationGraphCanvasHeight;
    private Integer translationGraphVertexSiblingOffset;
    private Integer translationGraphVertexVertMultiplier;
    private double translationGraphVertexAttractionMultiplier;

    private int interleavingsGraphEdgeXLength;
    private int interleavingsGraphEdgeYLength;
    private int interleavingsGraphNodeDiameter;
    private int interleavingsGraphPanelWidth;
    private int interleavingsGraphPanelHeight;
    private int interleavingsGraphLayoutWidth;
    private int interleavingsGraphLayoutHeight;
    private double interleavingsGraphVertexAttractionMultiplier;

    private float xmlGraphScaleFactor;
    private float translationGraphScaleFactor;
    private float interleavingsGraphScaleFactor;

    private int testerExpectedActualPanelWidth;
    private int testerExpectedActualPanelHeight;
    private int testerExpectedActualTextAreaWidth;
    private int testerExpectedActualTextAreaHeight;

    // TODO: remove these temp placeholders
    String[] analyzerModelList;
    String[] analyzerStatesList;
    String[] analyzerLabelsList;
    String analyzerDefaultResults;


    public Options() {

        // debug options
        debug = true; // when true, skips to step specified in targetNumNodesExpanded
        targetNumNodesExpanded = 133; // covid-hospitalization breaks on 106th

        // default page to render
        defaultPageToRender = ANALYZER_PAGE; // ANALYZER_PAGE or TESTER_PAGE

        // default graphs to render
        defaultGraphsToRender = ALL_GRAPHS; // ALL_GRAPHS, XML_ONLY, TRANSLATION_ONLY or INTERLEAVINGS_ONLY

        // default list selections (for testing only, set to null otherwise)
        defaultFileSelection = null; // "OneStep.ljx"; // TODO: change this back to null when done fixing negative states bug!!
        defaultModelSelection = null; // "EX(p)"; TODO: change this back to null when done fixing negative states bug!!
        defaultStateSelection = null; // "s0";

        // paths
        pathToXmlDir = "src/main/resources/xml/";
        pathToKrpDir = "src/main/resources/krp/";
        pathToTests = "src/main/resources/tests/";
        modelsPath1Var = "src/main/resources/models/models-1-var.txt";
        modelsPath2Vars = "src/main/resources/models/models-2-vars.txt";

        // fonts
        font = "Verdana";
        largeFontSize = 13;
        sectionTitleFontSize = 11;
        listFontSize = 10;

        // window size
        windowWidth = 1650;
        windowHeight = 950;
        windowCloseOperation = JFrame.EXIT_ON_CLOSE;

        // colors
        mainPanelBackgroundColor = Color.white;
        selectedListItemColor = Color.decode("#5e7f85"); // grey/blue

        // buttons text
        buttonTextAnalyzer = "Analyzer";
        buttonTextTester = "Tests";

        // analyzer page content text
        analyzerWindowTitle = "Model Checking - Analyzer";
        analyzerInstructionsText = "Select your file(s), model and\nnumber of loops below";
        analyzerFilesTitle = "Select Input File(s)";
        analyzerModelsTitle = "Select Model";
        analyzerLoopsTitle = "Enter Number Of Loops";
        analyzerLoopsNumber = 0;
        analyzerStatesTitle = "Select State";
        analyzerLabelsTitle = "Labels In Selected Files";
        analyzerDisplayGraphsTitle = "Display Graphs";
        analyzerDisplayGraphListItems = "all 3 graphs,xml only,trans. only,inter. only,trans. comp.,inter. comp.";
        analyzerStepsTitle = "Display Step";
        analyzerResultsTitle = "Results";
        analyzerResultsLineLength = 25;

        // factor to multiply selected list index by for setting the vertical scrollbar position so selected index shows
        analyzerListVertMultiplier = 12;
        analyzerDefaultModelIndex = 0; // when a file is clicked, it also selects the first items in the model, state & label lists
        analyzerDefaultStateIndex = 0;
        analyzerDefaultLabelIndex = 0;

        // graphs titles
        xmlGraphTitle = "Xml";
        translationGraphTitle = "Translation";
        interleavingsGraphTitle = "Interleavings";

        // graph width/heights
        graphPanelsWidth = 400;
        graphPanelsHeight = 600;
        graphLayoutsWidth = 350;
        graphHalfLayoutWidth = 675;
        graphFullLayoutWidth = 1400;
        graphLayoutsHeight = 550;

        // xml graphs settings
        xmlGraphEdgeXLength = 50; // 50 is default i think
        xmlGraphEdgeYLength = 35;
        xmlGraphScaleFactor = 0.6f; // shrinks graphs to 75% size to fix in containing box
        xmlGraphNodeDiameter = 18;
        xmlGraphPanelWidth = 300;
        xmlGraphPanelHeight = 875;
        xmlGraphLayoutWidth = 275;
        xmlGraphLayoutHeight = 825;
        xmlGraphVertexAttractionMultiplier = .3;
        xmlGraphVertexSiblingOffset = 150;

        // translation graphs settings
        translationGraphEdgeXLength = 50; // 50 is default i think
        translationGraphEdgeYLength = 35;
        translationGraphScaleFactor = 0.75f; // shrinks graphs to 75% size to fix in containing box
        translationGraphNodeDiameter = 18;
        translationGraphPanelWidth = 450;
        translationGraphPanelHeight = 800;
        translationGraphLayoutWidth = 550;
        translationGraphLayoutHeight = 875;
        translationGraphLevel = 1;
        translationGraphNumEdges = 0;
        translationGraphCanvasWidth = 475;
        translationGraphCanvasHeight = 350;
        translationGraphVertexSiblingOffset = 150;
        translationGraphVertexAttractionMultiplier = .99;
        translationGraphVertexVertMultiplier = 60;

        // interleavings graphs settings
        interleavingsGraphEdgeXLength = 50; // 50 is default i think
        interleavingsGraphEdgeYLength = 35;
        interleavingsGraphScaleFactor = 0.7f; // shrinks graphs to 75% size to fix in containing box
        interleavingsGraphEdgeXLength = 50; // 50 is default i think
        interleavingsGraphEdgeYLength = 35;
        interleavingsGraphEdgeXLength = 50; // 50 is default i think
        interleavingsGraphNodeDiameter = 18;
        interleavingsGraphPanelWidth = 550;
        interleavingsGraphPanelHeight = 900;
        interleavingsGraphLayoutWidth = 525;;
        interleavingsGraphLayoutHeight = 875;
        interleavingsGraphScaleFactor = 0.75f; // shrinks graphs to 75% size to fix in containing box
        interleavingsGraphVertexAttractionMultiplier = .99;

        // tester page titles
        testerPageTitle = "Model Checking Tests";
        testerFileLabel = "Test Files";
        testerIndividualTestLabel = "Individual Test Result";
        testerAggregateTestLabel = "Aggregate Test Results";

        // tester test titles
        testerXmlTitle = "Xml";
        testerXmlExpectedTitle = "Expected";
        testerXmlActualTitle = "Actual";
        testerTranslationTitle = "Translation";
        testerTranslationExpectedTitle = "Expected";
        testerTranslationActualTitle = "Actual";
        testerInterleavingsTitle = "Interleavings";
        testerInterleavingsExpectedTitle = "Expected";
        testerInterleavingsActualTitle = "Actual";
        testerModelCheckingTitle = "Model Checking";
        testerModelCheckingExpectedTitle = "Expected";
        testerModelCheckingActualTitle = "Actual";

        // tester misc
        testerKripkeLinesToSkip = 2; // based on the stucture of the tests. if the structure of the tests were changed, this would have to change.
        testerModelCheckingLinesToSkip = 2;
        testerLineLength = 18; // line length for expected & actual results for xml, translations & interleavings
        testerExpectedActualPanelWidth = 350;
        testerExpectedActualPanelHeight = 700;
        testerExpectedActualTextAreaWidth = 163;

    }


    // getters/setters

    public PageEnum getDefaultPageToRender() {
        return defaultPageToRender;
    }

    public void setDefaultPageToRender(PageEnum defaultPageToRender) {
        this.defaultPageToRender = defaultPageToRender;
    }

    public String getPathToXmlDir() {
        return pathToXmlDir;
    }

    public String getButtonTextAnalyzer() {
        return buttonTextAnalyzer;
    }

    public String getButtonTextTester() {
        return buttonTextTester;
    }

    public String getAnalyzerWindowTitle() {
        return analyzerWindowTitle;
    }

    public void setAnalyzerWindowTitle(String analyzerWindowTitle) {
        this.analyzerWindowTitle = analyzerWindowTitle;
    }

    public String getAnalyzerInstructionsText() {
        return analyzerInstructionsText;
    }

    public void setAnalyzerInstructionsText(String analyzerInstructionsText) {
        this.analyzerInstructionsText = analyzerInstructionsText;
    }

    public String getAnalyzerFilesTitle() {
        return analyzerFilesTitle;
    }

    public void setAnalyzerFilesTitle(String analyzerFilesTitle) {
        this.analyzerFilesTitle = analyzerFilesTitle;
    }

    public String getAnalyzerModelsTitle() {
        return analyzerModelsTitle;
    }

    public void setAnalyzerModelsTitle(String analyzerModelsTitle) {
        this.analyzerModelsTitle = analyzerModelsTitle;
    }

    public String getAnalyzerLoopsTitle() {
        return analyzerLoopsTitle;
    }

    public void setAnalyzerLoopsTitle(String analyzerLoopsTitle) {
        this.analyzerLoopsTitle = analyzerLoopsTitle;
    }

    public Integer getAnalyzerLoopsNumber() {
        return analyzerLoopsNumber;
    }

    public void setAnalyzerLoopsNumber(Integer analyzerLoopsNumber) {
        this.analyzerLoopsNumber = analyzerLoopsNumber;
    }

    public String getAnalyzerStatesTitle() {
        return analyzerStatesTitle;
    }

    public void setAnalyzerStatesTitle(String analyzerStatesTitle) {
        this.analyzerStatesTitle = analyzerStatesTitle;
    }

    public String getAnalyzerLabelsTitle() {
        return analyzerLabelsTitle;
    }

    public String getAnalyzerDisplayGraphsTitle() {
        return analyzerDisplayGraphsTitle;
    }

    public String getAnalyzerStepsTitle() {
        return analyzerStepsTitle;
    }

    public String getAnalyzerDisplayGraphListItems() {
        return analyzerDisplayGraphListItems;
    }

    public void setAnalyzerDisplayGraphListItems(String analyzerDisplayGraphListItems) {
        this.analyzerDisplayGraphListItems = analyzerDisplayGraphListItems;
    }

    public void setAnalyzerDisplayGraphsTitle(String analyzerDisplayGraphsTitle) {
        this.analyzerDisplayGraphsTitle = analyzerDisplayGraphsTitle;
    }

    public void setAnalyzerLabelsTitle(String analyzerLabelsTitle) {
        this.analyzerLabelsTitle = analyzerLabelsTitle;
    }

    public String getAnalyzerResultsTitle() {
        return analyzerResultsTitle;
    }

    public void setAnalyzerResultsTitle(String analyzerResultsTitle) {
        this.analyzerResultsTitle = analyzerResultsTitle;
    }

    public String[] getAnalyzerModelList() {
        return analyzerModelList;
    }

    public void setAnalyzerModelList(String[] analyzerModelList) {
        this.analyzerModelList = analyzerModelList;
    }

    public String[] getAnalyzerStatesList() {
        return analyzerStatesList;
    }

    public void setAnalyzerStatesList(String[] analyzerStatesList) {
        this.analyzerStatesList = analyzerStatesList;
    }

    public String[] getAnalyzerLabelsList() {
        return analyzerLabelsList;
    }

    public void setAnalyzerLabelsList(String[] analyzerLabelsList) {
        this.analyzerLabelsList = analyzerLabelsList;
    }

    public String getAnalyzerDefaultResults() {
        return analyzerDefaultResults;
    }

    public void setAnalyzerDefaultResults(String analyzerDefaultResults) {
        this.analyzerDefaultResults = analyzerDefaultResults;
    }

    public String getXmlGraphTitle() {
        return xmlGraphTitle;
    }

    public void setXmlGraphTitle(String xmlGraphTitle) {
        this.xmlGraphTitle = xmlGraphTitle;
    }

    public String getTranslationGraphTitle() {
        return translationGraphTitle;
    }

    public void setTranslationGraphTitle(String translationGraphTitle) {
        this.translationGraphTitle = translationGraphTitle;
    }

    public int getXmlGraphEdgeXLength() {
        return xmlGraphEdgeXLength;
    }

    public void setXmlGraphEdgeXLength(int xmlGraphEdgeXLength) {
        this.xmlGraphEdgeXLength = xmlGraphEdgeXLength;
    }

    public int getXmlGraphEdgeYLength() {
        return xmlGraphEdgeYLength;
    }

    public void setXmlGraphEdgeYLength(int xmlGraphEdgeYLength) {
        this.xmlGraphEdgeYLength = xmlGraphEdgeYLength;
    }

    public int getXmlGraphNodeDiameter() {
        return xmlGraphNodeDiameter;
    }

    public void setXmlGraphNodeDiameter(int xmlGraphNodeDiameter) {
        this.xmlGraphNodeDiameter = xmlGraphNodeDiameter;
    }

    public int getLargeFontSize() {
        return largeFontSize;
    }

    public int getListFontSize() {
        return listFontSize;
    }

    public int getSectionTitleFontSize() {
        return sectionTitleFontSize;
    }

    public String getFont() {
        return font;
    }

    public int getWindowWidth() {
        return windowWidth;
    }

    public int getWindowHeight() {
        return windowHeight;
    }

    public Color getMainPanelBackgroundColor() {
        return mainPanelBackgroundColor;
    }

    public int getXmlGraphPanelWidth() {
        return xmlGraphPanelWidth;
    }

    public int getXmlGraphPanelHeight() {
        return xmlGraphPanelHeight;
    }

    public int getXmlGraphLayoutWidth() {
        return xmlGraphLayoutWidth;
    }

    public int getXmlGraphLayoutHeight() {
        return xmlGraphLayoutHeight;
    }

    public float getXmlGraphScaleFactor() {
        return xmlGraphScaleFactor;
    }

    public float getTranslationGraphScaleFactor() {
        return translationGraphScaleFactor;
    }

    public int getTranslationGraphEdgeXLength() {
        return translationGraphEdgeXLength;
    }

    public int getTranslationGraphEdgeYLength() {
        return translationGraphEdgeYLength;
    }

    public int getTranslationGraphLayoutHeight() {
        return translationGraphLayoutHeight;
    }

    public int getTranslationGraphLayoutWidth() {
        return translationGraphLayoutWidth;
    }

    public int getTranslationGraphNodeDiameter() {
        return translationGraphNodeDiameter;
    }

    public int getTranslationGraphPanelHeight() {
        return translationGraphPanelHeight;
    }

    public int getTranslationGraphPanelWidth() {
        return translationGraphPanelWidth;
    }

    public int getInterleavingsGraphEdgeXLength() {
        return interleavingsGraphEdgeXLength;
    }

    public float getInterleavingsGraphScaleFactor() {
        return interleavingsGraphScaleFactor;
    }

    public int getInterleavingsGraphEdgeYLength() {
        return interleavingsGraphEdgeYLength;
    }

    public int getInterleavingsGraphLayoutHeight() {
        return interleavingsGraphLayoutHeight;
    }

    public int getInterleavingsGraphLayoutWidth() {
        return interleavingsGraphLayoutWidth;
    }

    public int getInterleavingsGraphNodeDiameter() {
        return interleavingsGraphNodeDiameter;
    }

    public int getInterleavingsGraphPanelHeight() {
        return interleavingsGraphPanelHeight;
    }

    public int getInterleavingsGraphPanelWidth() {
        return interleavingsGraphPanelWidth;
    }

    public String getInterleavingsGraphTitle() {
        return interleavingsGraphTitle;
    }

    public Color getSelectedListItemColor() {
        return selectedListItemColor;
    }

    public double getTranslationGraphVertexAttractionMultiplier() {
        return translationGraphVertexAttractionMultiplier;
    }

    public Integer getTranslationGraphCanvasHeight() {
        return translationGraphCanvasHeight;
    }

    public Integer getTranslationGraphCanvasWidth() {
        return translationGraphCanvasWidth;
    }

    public Integer getTranslationGraphLevel() {
        return translationGraphLevel;
    }

    public Integer getTranslationGraphNumEdges() {
        return translationGraphNumEdges;
    }

    public Integer getTranslationGraphVertexSiblingOffset() {
        return translationGraphVertexSiblingOffset;
    }

    public Integer getTranslationGraphVertexVertMultiplier() {
        return translationGraphVertexVertMultiplier;
    }

    public String getModelsPath1Var() {
        return modelsPath1Var;
    }

    public String getModelsPath2Vars() {
        return modelsPath2Vars;
    }

    public int getAnalyzerListVertMultiplier() {
        return analyzerListVertMultiplier;
    }

    public double getXmlGraphVertexAttractionMultiplier() {
        return xmlGraphVertexAttractionMultiplier;
    }

    public int getXmlGraphVertexSiblingOffset() { return xmlGraphVertexSiblingOffset; }

    public Integer getAnalyzerDefaultModelIndex() {
        return analyzerDefaultModelIndex;
    }

    public void setAnalyzerDefaultModelIndex(Integer analyzerDefaultModelIndex) {
        this.analyzerDefaultModelIndex = analyzerDefaultModelIndex;
    }

    public Integer getAnalyzerDefaultStateIndex() {
        return analyzerDefaultStateIndex;
    }

    public void setAnalyzerDefaultStateIndex(Integer analyzerDefaultStateIndex) {
        this.analyzerDefaultStateIndex = analyzerDefaultStateIndex;
    }

    public Integer getAnalyzerDefaultLabelIndex() {
        return analyzerDefaultLabelIndex;
    }

    public void setAnalyzerDefaultLabelIndex(Integer analyzerDefaultLabelIndex) {
        this.analyzerDefaultLabelIndex = analyzerDefaultLabelIndex;
    }

    public int getAnalyzerResultsLineLength() {
        return analyzerResultsLineLength;
    }

    public String getDefaultFileSelection() {
        return defaultFileSelection;
    }

    public String getDefaultModelSelection() {
        return defaultModelSelection;
    }

    public String getDefaultStateSelection() {
        return defaultStateSelection;
    }

    public String getTesterPageTitle() {
        return testerPageTitle;
    }

    public int getWindowCloseOperation() {
        return windowCloseOperation;
    }

    public String getTesterFileLabel() {
        return testerFileLabel;
    }

    public String getTesterXmlActualTitle() {
        return testerXmlActualTitle;
    }

    public String getTesterAggregateTestLabel() {
        return testerAggregateTestLabel;
    }

    public String getTesterXmlExpectedTitle() {
        return testerXmlExpectedTitle;
    }

    public String getTesterIndividualTestLabel() {
        return testerIndividualTestLabel;
    }

    public String getTesterXmlTitle() {
        return testerXmlTitle;
    }

    public String getTesterTranslationTitle() {
        return testerTranslationTitle;
    }

    public String getTesterInterleavingsTitle() {
        return testerInterleavingsTitle;
    }

    public String getTesterModelCheckingTitle() {
        return testerModelCheckingTitle;
    }

    public String getTesterTranslationActualTitle() {
        return testerTranslationActualTitle;
    }

    public String getTesterTranslationExpectedTitle() {
        return testerTranslationExpectedTitle;
    }

    public String getTesterInterleavingsActualTitle() {
        return testerInterleavingsActualTitle;
    }

    public String getTesterInterleavingsExpectedTitle() {
        return testerInterleavingsExpectedTitle;
    }

    public String getTesterModelCheckingActualTitle() {
        return testerModelCheckingActualTitle;
    }

    public String getTesterModelCheckingExpectedTitle() {
        return testerModelCheckingExpectedTitle;
    }

    public String getPathToTests() {
        return pathToTests;
    }

    public int getTesterKripkeLinesToSkip() {
        return testerKripkeLinesToSkip;
    }

    public int getTesterLineLength() {
        return testerLineLength;
    }

    public double getInterleavingsGraphVertexAttractionMultiplier() {
        return interleavingsGraphVertexAttractionMultiplier;
    }

    public int getGraphLayoutsHeight() {
        return graphLayoutsHeight;
    }

    public int getGraphLayoutsWidth() {
        return graphLayoutsWidth;
    }

    public int getGraphPanelsHeight() {
        return graphPanelsHeight;
    }

    public int getGraphPanelsWidth() {
        return graphPanelsWidth;
    }

    public int getTesterExpectedActualPanelHeight() {
        return testerExpectedActualPanelHeight;
    }

    public int getTesterExpectedActualPanelWidth() {
        return testerExpectedActualPanelWidth;
    }

    public int getTesterExpectedActualTextAreaHeight() {
        return testerExpectedActualTextAreaHeight;
    }

    public void setButtonTextTester(String buttonTextTester) {
        this.buttonTextTester = buttonTextTester;
    }

    public int getTesterExpectedActualTextAreaWidth() {
        return testerExpectedActualTextAreaWidth;
    }

    public void setTesterPageTitle(String testerPageTitle) {
        this.testerPageTitle = testerPageTitle;
    }

    public int getTesterModelCheckingLinesToSkip() {
        return testerModelCheckingLinesToSkip;
    }

    public void setPathToKrpDir(String pathToKrpDir) {
        this.pathToKrpDir = pathToKrpDir;
    }

    public String getPathToKrpDir() {
        return pathToKrpDir;
    }

    public Boolean getDebug() { return debug; }

    public int getTargetNumNodesExpanded() { return targetNumNodesExpanded; }

    public DisplayGraphsEnum getDefaultGraphsToRender() {
        return defaultGraphsToRender;
    }

    public int getGraphFullLayoutWidth() {
        return graphFullLayoutWidth;
    }

    public int getGraphHalfLayoutWidth() {
        return graphHalfLayoutWidth;
    }

}
