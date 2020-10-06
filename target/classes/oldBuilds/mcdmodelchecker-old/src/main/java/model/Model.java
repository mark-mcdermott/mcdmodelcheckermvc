package model;

import _options.Options;
import controller.dataTypes.graphHelpers.*;
import controller.dataTypes.graphItems.GraphModelObj;
import controller.dataTypes.graphItems.Label;
import controller.dataTypes.pageHelpers.PageEnum;
import controller.dataTypes.testHelpers.FileTest;
import controller.dataTypes.testHelpers.FileTestSet;
import controller.helpers.ListHelper;
import controller.helpers.McdException;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

import static controller.dataTypes.graphHelpers.GraphModelEnum.GRAPH_MODEL;
import static controller.dataTypes.graphHelpers.GraphModelEnum.GRAPH_MODEL_PREV_STEP;

public class Model {

    public GraphModelObj graphModel;                   // main graph info
    public GraphModelObj graphModelPrevStep;           // only used for comparison runs
    public DisplayGraphsEnum selectedGraph;        // which graph(s) selected to display
    public Integer selectedLoopsNum;                   // number of times graph can traverse each loop
    public Integer selectedStep;                       // step of translation selected to display
    public String selectedModel;                       // selected model to use for the model checker

    private PageEnum pageToDisplay;                 
    private String[] fileList;                      
    private String[] translationStepsList;          
    private String[] interleavingsStepList;         
    private int[] selectedFileIndices;              
    private ArrayList<String> selectedFiles;        
    private DisplayGraphsEnum selectedDisplayGraph; 
    private Integer selectedModelIndex;             
    private LabelHash labelHash;
    private Label selectedLabel;                    
    private int numProperties;                      
    private String buttonTextAnalyzer;              
    private String buttonTextTester;
    private String analyzerWindowTitle;
    private String analyzerInstructionsText;
    private String analyzerFilesTitle;
    private String analyzerGraphsTitle;
    private String analyzerStepsTitle;
    private String analyzerModelsTitle;
    private String analyzerLoopsTitle;
    private Integer analyzerLoopsNumber;
    private String analyzerStatesTitle;
    private String analyzerLabelsTitle;
    private String analyzerResultsTitle;
    private String[] displayGraphList;                
    private String[] stepsList;
    private String[] analyzerModelsList;
    private String[] analyzerStatesList;
    private String[] analyzerLabelsList;
    private ArrayList<String> stateDisplayList;
    private ArrayList<String> labelDisplayList;
    private FileTestSet fileTestsSet;
    private FileTest selectedFileTest;
    private String xmlGraphTitle;                     
    private String translationGraphTitle;
    private String translationGraphTitleWithStep;
    private String interleavingsGraphTitle;
    private String interleavingsGraphTitleWithStep;
    private Integer numTranslationSteps;
    private Integer numInterleavingsSteps;


    public Model() {
        graphModel = new GraphModelObj();
        graphModelPrevStep = new GraphModelObj();
        selectedGraph = null;
        selectedLoopsNum = null;
        selectedStep = null;
        selectedModel = null;

    }

    public void setSelectedModelAndModelIndex(String selectedModel, Options options) {
        ListHelper listHelper = new ListHelper();
        int selectedModelIndex = listHelper.findIndexInArr(getAnalyzerModelsList(), options.getDefaultModelSelection());
        setSelectedModel(selectedModel);
        setSelectedModelIndex(selectedModelIndex);
    }

    void setAnalyzerFileModelGraphOptionsListsToDefaultsFromDisk(Options options) throws ParseException, FileNotFoundException {
        ListHelper listHelper = new ListHelper();
        String xmlPath = options.getPathToXmlDir();
        String krpPath = options.getPathToKrpDir();
        String[] modelFileList = listHelper.fetchFileListFromTwoDirs(xmlPath, krpPath);
        String[] graphOptionsFileList = options.getAnalyzerDisplayGraphListItems().split(",");
        setFileList(modelFileList);
        setAnalyzerModelsList(initModelList(numProperties, options));
        setDisplayGraphList(graphOptionsFileList);
        // setAnalyzerModelsList(listHelper.textFileLinesToArr(options.getModelsPath1Var()));
    }



    public void setFileSelectResultOperation(FileSelectResult fileSelectResult, GraphModelObj graphModel) {

        // get specific graph model instance stuff
        graphModel.setFileSelectResult(fileSelectResult);
        graphModel.setXmlVertexList(fileSelectResult.getXmlVertexList());
        graphModel.setTranslationVertexList(fileSelectResult.getTranslationVertexList());
        graphModel.setInterleavingsVertexList(fileSelectResult.getInterleavingsVertexList());
        graphModel.setInterleavingsKripke(fileSelectResult.getInterleavingsKripke());

        // set the global stuff stored here in Model.java
        this.selectedFiles = fileSelectResult.getSelectedFiles();
        this.selectedDisplayGraph = fileSelectResult.getSelectedDisplayGraph();
        this.selectedStep = fileSelectResult.getSelectedStep();
        this.stateDisplayList = fileSelectResult.getStateDisplayList();
        this.labelDisplayList = fileSelectResult.getLabelDisplayList();
        this.labelHash = fileSelectResult.getLabelHash();
        this.numProperties = fileSelectResult.getNumProperties();
        this.analyzerModelsList = fileSelectResult.getModelList();
        if (fileSelectResult.getTranslationVertexList().getNumTotalSteps() != null) {
            this.numTranslationSteps = fileSelectResult.getTranslationVertexList().getNumTotalSteps();
        }
        if (fileSelectResult.getInterleavingsVertexList().getNumTotalSteps() != null) {
            this.numInterleavingsSteps = fileSelectResult.getInterleavingsVertexList().getNumTotalSteps();
        }

    }

    // listHelper.textFileLinesToArr(options.getModelsPath1Var())
    public String[] initModelList(int numProperties, Options options) throws FileNotFoundException {
        ListHelper listHelper = new ListHelper();
        String[] modelList = new String[0];
        if (numProperties == 1) {
            modelList = listHelper.textFileLinesToArr(options.getModelsPath1Var());
        } else if (numProperties == 2) {
            modelList = listHelper.textFileLinesToArr(options.getModelsPath2Vars());
        }
        return modelList;
    }

    void setAnalyzerFileListsToDefaultsFromDisk(Options options) throws ParseException {
        ListHelper listHelper = new ListHelper();
        // String[] modelFileList = listHelper.fetchFileList(options.getPathToXmlDir());
        String xmlPath = options.getPathToXmlDir();
        String krpPath = options.getPathToKrpDir();
        String[] modelFileList = listHelper.fetchFileListFromTwoDirs(xmlPath, krpPath);
        setFileList(modelFileList);
    }

    public void setFileSelectResultWithDefaults(FileSelectResult fileSelectResult, GraphModelObj graphModel) {
        setFileSelectResultOperation(fileSelectResult, graphModel);
        setAnalyzerDefaultSelections(graphModel);
    }

    public void setAnalyzerDefaultSelections(GraphModelObj graphModel) {
        graphModel.setSelectedState(graphModel.getInterleavingsVertexList().getRoot());
        setSelectedLoopsNum(0);
    }

    public void setFileSelectResult(FileSelectResult fileSelectResult, GraphModelObj graphModel) {
        setFileSelectResultOperation(fileSelectResult, graphModel);
    }

    public GraphModelObj getGraphModelFromEnum(GraphModelEnum graphModelEnum) {
        if (graphModelEnum.equals(GRAPH_MODEL)) {
            return graphModel;
        } else if (graphModelEnum.equals(GRAPH_MODEL_PREV_STEP)) {
            return graphModelPrevStep;
        } else {
            new McdException("Incorrect graphModelEnum argument in getGraphModelFromEnum");
            return null;
        }
    }

    void setAnalyzerFileListSelectionsToNull() {
        setSelectedFiles(null);
        setSelectedFileIndices(null);
    }

    void setAnalyzerTextToDefaultsFromOptions(Options options) {
        setAnalyzerButtonText(options.getButtonTextAnalyzer());
        setTesterButtonText(options.getButtonTextTester());
        setAnalyzerInstructionsText(options.getAnalyzerInstructionsText());
        setAnalyzerFilesTitle(options.getAnalyzerFilesTitle());
        setAnalyzerGraphsTitle(options.getAnalyzerDisplayGraphsTitle());
        setAnalyzerStepsTitle(options.getAnalyzerStepsTitle());
        setAnalyzerModelsTitle(options.getAnalyzerModelsTitle());
        setAnalyzerLoopsTitle(options.getAnalyzerLoopsTitle());
        setAnalyzerLoopsNumber(options.getAnalyzerLoopsNumber());
        setAnalyzerStatesTitle(options.getAnalyzerStatesTitle());
        setAnalyzerLabelsTitle(options.getAnalyzerLabelsTitle());
        setAnalyzerResultsTitle(options.getAnalyzerResultsTitle());
        setXmlGraphTitle(options.getXmlGraphTitle());
        setTranslationGraphTitle(options.getTranslationGraphTitle());
        setInterleavingsGraphTitle(options.getInterleavingsGraphTitle());
    }

    public void setModelToInitialDefaults(Options options) throws FileNotFoundException, ParseException {
        // setAnalyzerFileListSelectionsToNull();
        setAnalyzerTextToDefaultsFromOptions(options);
        // setAnalyzerFileListsToDefaultsFromDisk(options);
        setAnalyzerFileModelGraphOptionsListsToDefaultsFromDisk(options);
    }

    public GraphModelObj getGraphModelPrevStep() {
        return graphModelPrevStep;
    }

    public DisplayGraphsEnum getSelectedGraph() {
        return selectedGraph;
    }

    public void setSelectedGraph(DisplayGraphsEnum selectedGraph) {
        this.selectedGraph = selectedGraph;
    }

    public GraphModelObj getGraphModelFromEnum() {
        return graphModel;
    }

    public Integer getSelectedStep() {
        return selectedStep;
    }

    public Integer getSelectedLoopsNum() {
        return selectedLoopsNum;
    }

    public String getSelectedModel() {
        return selectedModel;
    }

    public void setSelectedStep(Integer selectedStep) {
        this.selectedStep = selectedStep;
    }

    public void setGraphModel(GraphModelObj graphModel) {
        this.graphModel = graphModel;
    }

    public void setGraphModelPrevStep(GraphModelObj graphModelPrevStep) {
        this.graphModelPrevStep = graphModelPrevStep;
    }

    public void setSelectedLoopsNum(Integer selectedLoopsNum) {
        this.selectedLoopsNum = selectedLoopsNum;
    }

    public void setSelectedModel(String selectedModel) {
        this.selectedModel = selectedModel;
    }

    public void setFileList(String[] fileList) {
        this.fileList = fileList;
    }

    public void setDisplayGraphList(String[] displayGraphList) {
        this.displayGraphList = displayGraphList;
    }

    public PageEnum getPageToDisplay() {
        return pageToDisplay;
    }

    public void setPageToDisplay(PageEnum pageToDisplay) {
        this.pageToDisplay = pageToDisplay;
    }

    public String[] getFileList() {
        return fileList;
    }

    public String getButtonTextAnalyzer() {
        return buttonTextAnalyzer;
    }

    public void setAnalyzerButtonText(String buttonTextAnalyzer) {
        this.buttonTextAnalyzer = buttonTextAnalyzer;
    }

    public String getButtonTextTester() {
        return buttonTextTester;
    }

    public void setTesterButtonText(String buttonTextTester) {
        this.buttonTextTester = buttonTextTester;
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



    public String getAnalyzerGraphsTitle() {
        return analyzerGraphsTitle;
    }

    public void setAnalyzerGraphsTitle(String analyzerGraphsTitle) {
        this.analyzerGraphsTitle = analyzerGraphsTitle;
    }

    public String getAnalyzerStepsTitle() {
        return analyzerStepsTitle;
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

    public String[] getAnalyzerModelsList() {
        return analyzerModelsList;
    }

    public void setAnalyzerModelsList(String[] analyzerModelsList) {
        this.analyzerModelsList = analyzerModelsList;
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

    public ArrayList<String> getSelectedFiles() {
        return selectedFiles;
    }

    public void setSelectedFiles(ArrayList<String> selectedFiles) {
        this.selectedFiles = selectedFiles;
    }

    public int[] getSelectedFileIndices() {
        return selectedFileIndices;
    }

    public void setSelectedFileIndices(int[] selectedFileIndices) {
        this.selectedFileIndices = selectedFileIndices;
    }

    public String getTranslationGraphTitle() {
        return translationGraphTitle;
    }

    public void setTranslationGraphTitle(String translationGraphTitle) {
        this.translationGraphTitle = translationGraphTitle;
    }

    public String getXmlGraphTitle() {
        return xmlGraphTitle;
    }

    public void setXmlGraphTitle(String xmlGraphTitle) {
        this.xmlGraphTitle = xmlGraphTitle;
    }


    public Integer getSelectedModelIndex() {
        return selectedModelIndex;
    }

    public void setSelectedModelIndex(Integer selectedModelIndex) {
        this.selectedModelIndex = selectedModelIndex;
    }

    public ArrayList<String> getStateDisplayList() {
        return stateDisplayList;
    }

    public void setStateDisplayList(ArrayList<String> stateDisplayList) {
        this.stateDisplayList = stateDisplayList;
    }

    public LabelHash getLabelHash() {
        return labelHash;
    }

    public void setLabelHash(LabelHash labelHash) {
        this.labelHash = labelHash;
    }

    public void setLabelDisplayList(ArrayList<String> labelDisplayList) {
        this.labelDisplayList = labelDisplayList;
        // this.labelArr = labelArr;
    }

    public ArrayList<String> getLabelDisplayList() {
        return labelDisplayList;
    }


    public Label getSelectedLabel() {
        return selectedLabel;
    }

    public void setSelectedLabel(Label selectedLabel) {
        this.selectedLabel = selectedLabel;
    }

    public String getInterleavingsGraphTitle() {
        return interleavingsGraphTitle;
    }

    public void setInterleavingsGraphTitle(String interleavingsGraphTitle) {
        this.interleavingsGraphTitle = interleavingsGraphTitle;
    }


    public int getNumProperties() {
        return numProperties;
    }

    public void setNumProperties(int numProperties) {
        this.numProperties = numProperties;
    }

    public FileTestSet getFileTestsSet() {
        return fileTestsSet;
    }

    public void setFileTestsSet(FileTestSet fileTestsSet) {
        this.fileTestsSet = fileTestsSet;
    }

    public FileTest getSelectedFileTest() {
        return selectedFileTest;
    }

    public void setSelectedFileTest(FileTest selectedFileTest) {
        this.selectedFileTest = selectedFileTest;
    }

    public String[] getDisplayGraphList() {
        return displayGraphList;
    }

    public String[] getStepsList() {
        return stepsList;
    }

    public void setAnalyzerStepsTitle(String analyzerStepsTitle) {
        this.analyzerStepsTitle = analyzerStepsTitle;
    }

    public DisplayGraphsEnum getSelectedDisplayGraph() {
        return selectedDisplayGraph;
    }

    public void setSelectedDisplayGraph(DisplayGraphsEnum selectedDisplayGraph) {
        this.selectedDisplayGraph = selectedDisplayGraph;
    }

    public String getTranslationGraphTitleWithStep() {
        return translationGraphTitleWithStep;
    }

    public void setTranslationGraphTitleWithStep(String translationGraphTitleWithStep) {
        this.translationGraphTitleWithStep = translationGraphTitleWithStep;
    }

    public String getInterleavingsGraphTitleWithStep() {
        return interleavingsGraphTitleWithStep;
    }

    public void setInterleavingsGraphTitleWithStep(String interleavingsGraphTitleWithStep) {
        this.interleavingsGraphTitleWithStep = interleavingsGraphTitleWithStep;
    }

    public Integer getNumInterleavingsSteps() {
        return numInterleavingsSteps;
    }

    public Integer getNumTranslationSteps() {
        return numTranslationSteps;
    }

    public String[] getInterleavingsStepList() {
        return interleavingsStepList;
    }

    public String[] getTranslationStepsList() {
        return translationStepsList;
    }

    public void setTranslationStepsList(String[] translationStepsList) {
        this.translationStepsList = translationStepsList;
    }

    public void setInterleavingsStepList(String[] interleavingsStepList) {
        this.interleavingsStepList = interleavingsStepList;
    }


}
