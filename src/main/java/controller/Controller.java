package controller;

import _options.Options;
import controller.analyzer.ModelChecker;
import controller.content.GetGraphs;
import controller.content.staticContent.DisplayTypes;
import controller.content.staticContent.Models;
import controller.content.staticContent.XmlFileOrder;
import controller.filesCache.FilesCache;
import controller.types.ctl.Kripke;
import controller.types.analyzer.analyzerData.*;
import controller.types.graph.LabelHash;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;
import controller.types.modelChecking.CheckedModel;
import controller.types.tester.FileTest;
import controller.types.tester.FileTestSet;
import controller.types.tester.testerData.TesterData;
import controller.types.tester.testerData.TesterMisc;
import controller.utils.ExceptionMessage;
import controller.utils.ListHelper;
import model.Model;
import org.xml.sax.SAXException;
import view.Components;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static controller.types.analyzer.analyzerData.AppState.*;
import static controller.types.analyzer.analyzerData.DisplayType.*;
// import static controller.types.data.AppState.INITIAL_RUN;


public class Controller {

    Model model;
    Options options;

    public Controller(Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        this.model = model;
        options = new Options();
        initXmlFilesCache(model, options.getPathToXmlDir());
    }

    public void setInitialData() throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        // AppState appState = TESTER; // ANALY_DEFAULT;
        AppState appState = ANALY_DEFAULT;
        AnalyzerData initialAnalyzerData = getInitialAnalyzerData(model.getFilesCache());
        TesterData initialTesterData = getInitialTesterData(initialAnalyzerData.getListsContent()); // TODO: uncomment this
        model.setData(appState, initialAnalyzerData, initialTesterData); // TODO: uncomment this
        // model.setData(appState, initialAnalyzerData); // TODO: comment this
    }

    // from https://stackoverflow.com/a/1846349, accessed 9/25/20
    public String[] getFileStringsListFromDir(final File folder) {
        File[] dirFiles = folder.listFiles();
        int numFiles = dirFiles.length;
        String[] fileList = new String[numFiles];
        for (int i=0; i<dirFiles.length; i++) {
            File file = dirFiles[i];
            fileList[i] = file.getName();
        }
        return fileList;
    }

    // handle listeners clicks

    public void handleAnalyzerButtonClick() throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        setInitialData();
    }

    public void handleTesterButtonClick() throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        // get inital tester page data & set it to model
        String[] files = new XmlFileOrder().getFileOrder();
        AppState appState = TESTER;
        String selectedFile = files[0];
        String testDirPath = options.getPathToTests();
        FileTest fileTestSelectedFile = getFileTest(selectedFile, testDirPath, model, options);
        FileTestSet fileTestSet = getFileTestsSet(model, options, files);
        TesterData testerData = new TesterData(selectedFile, model.getAllTestFiles(),fileTestSet, fileTestSelectedFile);
        model.setData(appState, testerData);
    }

    // get file test helper
    FileTest getFileTest(String xmlFile, String xmlPath, Model model, Options options) throws SAXException, ExceptionMessage, ParserConfigurationException, IOException {
        return new FileTest(xmlFile, xmlPath, model, options);
    }

    FileTestSet getFileTestsSet(Model model, Options options, String[] fileList) throws SAXException, ExceptionMessage, ParserConfigurationException, IOException {
        ListHelper listHelper = new ListHelper();
        ArrayList<FileTest> fileTestsArrList = new ArrayList<>();
        ArrayList<String> xmlFiles = listHelper.stringArrToArrList(fileList);
        String xmlPath = options.getPathToTests();
        for (String xmlFile : xmlFiles) {

            // check if file exists (https://stackoverflow.com/a/1816676, accessed 9/25/20)
            File f = new File(xmlPath + xmlFile);
            if(f.exists() && !f.isDirectory()) {
                FileTest fileTest = new FileTest(xmlFile, xmlPath, model, options);
                fileTestsArrList.add(fileTest);
            }
        }
        FileTestSet fileTestSet = new FileTestSet(fileTestsArrList);
        return fileTestSet;
    }

    // analyzer file list click
    public void handleAnalyzerFileListClick(Components components, Model model) {
        String[] selectedFiles = getListSelections(components.fileList);
        try {
            GraphsContent graphsContent = getGraphsContent(selectedFiles, ANALY_DEFAULT, ALL_GRAPHS, model, false);
            AnalyzerData analyzerData = model.getAnalyzerData();
            analyzerData.setFileSelections(selectedFiles);
            analyzerData.setDisplaySelections(ALL_GRAPHS);
            analyzerData.setStepSelections(null);
            analyzerData.setModelSelections("⊤");
            analyzerData.setGraphsContent(graphsContent);
            model.setAppState(ANALY_DEFAULT);        // when selected file(s) is changed, app goes to default state
            model.setAnalyzerData(analyzerData);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ExceptionMessage exceptionMessage) {
            exceptionMessage.printStackTrace();
        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (SAXException saxException) {
            saxException.printStackTrace();
        }
    }

    // analyzer display list click
    public void handleDisplayListClick(Components components, Model model) {
        DisplayType selectedDisplay = DisplayType.valueOf(getListSelection(components.displayList));
        Integer selectedStep = null;
        try {
            AppState appState = null;
            if (selectedDisplay == ALL_GRAPHS || selectedDisplay == XML_ONLY || selectedDisplay == TRANS_ONLY || selectedDisplay == INTER_ONLY) {
                appState = ANALY_DEFAULT;
            } else if (selectedDisplay == TRANS_COMP || selectedDisplay == INTER_COMP) {
                appState = ANALY_COMP;
                selectedStep = 2;  // when a COMP display is clicked, selectedStep defaults to step 2
            }

            String[] selectedFiles = model.getSelectedFiles();
            int numLoops = model.getLoops();
            File[] xmlFileCache = model.getFilesCache();

            GraphsContent graphsContent = getGraphsContent(selectedFiles, selectedDisplay, numLoops, xmlFileCache, selectedStep);

            AnalyzerData analyzerData = model.getAnalyzerData();
            analyzerData.setDisplaySelections(selectedDisplay);
            if (selectedStep != null) { analyzerData.setStepSelections(selectedStep); }
            if (graphsContent.getNumSteps() != null) {
                Integer[] steps = getStepsFromNumSteps(graphsContent.getNumSteps());
                analyzerData.setListsContentSteps(steps);
            }
            analyzerData.setGraphsContent(graphsContent);
            model.setAppState(appState);        // when selected file(s) is changed, app goes to default state
            model.setAnalyzerData(analyzerData);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        } catch (ExceptionMessage exceptionMessage) {
            exceptionMessage.printStackTrace();
        } catch (ParserConfigurationException parserConfigurationException) {
            parserConfigurationException.printStackTrace();
        } catch (SAXException saxException) {
            saxException.printStackTrace();
        }
    }

    // analyzer step list click
    public void handleStepListClick(Components components, Model model) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        Integer selectedStep = Integer.parseInt(getListSelection(components.stepList));
        GraphsContent graphsContent = getGraphsContent(selectedStep, model);
        AnalyzerData analyzerData = model.getAnalyzerData();
        analyzerData.setStepSelections(selectedStep);
        analyzerData.setGraphsContent(graphsContent);
        model.setAnalyzerData(analyzerData);
    }

    // analyzer model list click
    public void handleModelListClick(Components components, Model model) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        String selectedModel = getListSelection(components.modelList);

        // check model here
        String selectedFiles[] = model.getSelectedFiles();
        GraphsContent graphsContent = getGraphsContent(selectedFiles, ANALY_DEFAULT, ALL_GRAPHS, model, false);
        Kripke interKripke = graphsContent.getInterKripke();
        Vertex[] states = vertexArrListToArr(interKripke.S);
        Vertex selectedState = states[0];
        int loops = model.getLoops();
        CheckedModel checkedModel = runModelChecker(selectedModel, interKripke, loops, model.getLabelHash(), states, selectedState);
        AnalyzerData analyzerData = model.getAnalyzerData();
        model.setAppState(ANALY_RESULTS);
        analyzerData.setDisplaySelections(ALL_GRAPHS);
        analyzerData.setModelSelections(selectedModel);
        // data.setListsContentStates(vertexArrListToArr(model.getInterleavingsKripke().S));
        analyzerData.setListsContentStates(vertexArrListToArr(interKripke.S));
        analyzerData.setStateSelections(analyzerData.getListsContentStates()[0]);
        analyzerData.setGraphsContent(graphsContent);
        analyzerData.setCheckedModel(checkedModel);
        analyzerData.setDoesHold(checkedModel.getResultDoesHold());
        analyzerData.setStatesThatHold(checkedModel.getResultStatesThatHold());
        analyzerData.setCounterExample(checkedModel.getResultCounterExample());
        analyzerData.setTime(checkedModel.getResultTime());
        model.setAnalyzerData(analyzerData);
    }

    // analyzer state list click
    public void handleStateListClick(Components components, Model model) {
        String selectedStateStr = getListSelection(components.stateList);
        VertexList interVertList = model.getInterleavingsVertexList();
        Vertex selectedState = getVertexFromStateName(selectedStateStr, interVertList);

        String selectedModel = model.getSelectedModel();
        Kripke interKripke = model.getInterleavingsKripke();
        int loops = model.getLoops();
        Vertex[] states = model.getListsContent().getStates();
        CheckedModel checkedModel = runModelChecker(selectedModel, interKripke, loops, model.getLabelHash(), states, selectedState);

        if (selectedState == null) {
            new ExceptionMessage("selectedState is null in handleStateListClick(), Controller.java");
        } else {
            AnalyzerData analyzerData = model.getAnalyzerData();
            analyzerData.setStateSelections(selectedState);
            analyzerData.setCheckedModel(checkedModel);
            analyzerData.setDoesHold(checkedModel.getResultDoesHold());
            analyzerData.setStatesThatHold(checkedModel.getResultStatesThatHold());
            analyzerData.setCounterExample(checkedModel.getResultCounterExample());
            analyzerData.setTime((checkedModel.getResultTime()));
            model.setAnalyzerData(analyzerData);
        }
    }

    public void handleTesterFileListClick(Components components, Model model) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {

        // get newly selected file
        String selectedFile = getListSelection(components.testerFileList);

        // get other elements from model necessary to run the tester
        String[] files = model.getAllTestFiles();
        String testDirPath = options.getPathToTests();

        // rerun the tester on newly selected file
        FileTest fileTestSelectedFile = getFileTest(selectedFile, testDirPath, model, options);
        FileTestSet fileTestSet = getFileTestsSet(model, options, files);

        // set new test results to the model
        TesterData testerData = new TesterData(selectedFile, model.getAllTestFiles(),fileTestSet, fileTestSelectedFile);
        model.setData(TESTER, testerData);

    }

    String[] getStatesFromKripke(Kripke interKripke) {
        return getStateStrListFromNumStates(getNumStatesFromInterKripke(interKripke));
    }

    Integer getNumStatesFromInterKripke(Kripke interKripke) {
        return interKripke.S.size();
    }

    private Vertex[] vertexArrListToArr(ArrayList<Vertex> arrList) {
        // ArrayList<Vertex> statesArrList = model.getInterleavingsKripke().S;
        Vertex[] statesArr = new Vertex[arrList.size()];
        arrList.toArray(statesArr);
        return statesArr;
    }

    private String[] getStateStrListFromNumStates(Integer numStates) {
        String[] stateStrList = new String[numStates];
        for (Integer i=0; i<numStates; i++) {
            stateStrList[i] = Integer.toString(i);
        }
        return stateStrList;
    }

    public CheckedModel runModelChecker(String selectedModel, Kripke interKripke, int numLoops, LabelHash labelHash, Vertex[] states, Vertex stateToCheck) {

        // get items needed for the model checking from the model
        // Kripke kripke = model.getInterleavingsKripke();
        int loops = model.getLoops();
        // Vertex stateToCheck = model.getSelectedState();

        // run the model checker
        ModelChecker modelChecker = new ModelChecker(selectedModel, interKripke, stateToCheck, loops, labelHash);
        CheckedModel checkedModel = modelChecker.getCheckedModel();
        return checkedModel;

    }

    private Vertex getVertexFromStateName(String stateStr, VertexList list) {
        for (Vertex vertex : list.getList()) {
            if (vertex.getName().equals(stateStr)) {
                return vertex;
            }
        }
        new ExceptionMessage("vertex not found in vertexList in getVertexFromStateName(), Controller.java");
        return null;
    }

    private Integer[] getStepsFromNumSteps(Integer numSteps) {
        Integer[] steps = new Integer[numSteps];
        for (int i=1; i<=numSteps; i++) {
            steps[i-1] = i;
        }
        return steps;
    }

    private String[] getStateArrFromIntArr(Integer[] ints) {
        String[] strings = new String[ints.length];
        for (int i=0; i<ints.length; i++) {
            strings[i] = "s" + ints[i].toString();
        }
        return strings;
    }



    private String[] getListSelections(JList list) {
        Object[] selectedFilesObj = list.getSelectedValues();
        String[] selectedFiles = Arrays.copyOf(selectedFilesObj, selectedFilesObj.length, String[].class);
        return selectedFiles;
    }

    private String getListSelection(JList list) {
        String selection = list.getSelectedValue().toString();
        return selection;
    }

    private void initXmlFilesCache(Model model, String pathToXmlFiles) {
        File[] xmlFileCache = new FilesCache(pathToXmlFiles).getFiles();
        model.setXmlFileCache(xmlFileCache);
    }

    private Selections initialSelections() {
        String[] files = {"OneStep.ljx"};
        // String[] files = {"Bank-Parallel.ljx"};
        DisplayType displayType = ALL_GRAPHS;
        Integer step = null;
        // String model = "⊤";
        String model = null;
        Integer loop = 0;
        Vertex state = null;
        return new Selections(files, displayType, step, model, loop, state);
    }

    private ListsContent initalListsContent() {
        String[] files = new XmlFileOrder().getFileOrder();
        String[] displays = new DisplayTypes().getDisplayTypes();
        Integer[] steps = null;
        // String[] models = new Models().getModels1Var();
        String[] models = new Models().getModels2Var(); // TODO: change back to getModels1Var when you change default xml file to a 1 property fiole
        Integer loops = 0;
        Vertex[] states = null;
        String[] labels = null;
        return new ListsContent(files, displays, steps, models, loops, states, labels);
    }

    public GraphsContent getGraphsContent(String[] selectedFiles, DisplayType displayType, int numLoops, File[] xmlFileCache, Integer selectedStep) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        Boolean isStepSelected = selectedStep == null ? false : true;
        return new GetGraphs(model).getGraphsFromXmlFilenames(selectedFiles, displayType,isStepSelected, numLoops, xmlFileCache, selectedStep);
    }

    public GraphsContent getGraphsContent(String[] selectedFiles, Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        Boolean isStepSelected = model.getSelectedStep() == null ? false : true;
        DisplayType displayType = model.getSelectedDisplay();
        Integer selectedLoops = model.getLoops();
        File[] xmlFileCache = model.getFilesCache();
        Integer selectedStep = model.getSelectedStep() == null ? null : model.getSelectedStep();
        return new GetGraphs(model).getGraphsFromXmlFilenames(selectedFiles, displayType, isStepSelected, selectedLoops, xmlFileCache, selectedStep);
    }

    public GraphsContent getGraphsContent(String[] selectedFiles, AppState appState, DisplayType displayType, Model model, Boolean isStepSelected) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        Integer selectedLoops = model.getLoops();
        File[] xmlFileCache = model.getFilesCache();
        Integer selectedStep = model.getSelectedStep() == null ? null : model.getSelectedStep();
        return new GetGraphs(model).getGraphsFromXmlFilenames(selectedFiles, displayType, isStepSelected, selectedLoops, xmlFileCache, selectedStep);
    }

    public GraphsContent getGraphsContent(Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        String[] selectedFiles = model.getSelectedFiles();
        DisplayType displayType = model.getSelectedDisplay();
        Boolean isStepSelected = model.getSelectedStep() == null ? false : true;
        Integer selectedLoops = model.getLoops();
        File[] xmlFileCache = model.getFilesCache();
        Integer selectedStep = model.getSelectedStep() == null ? null : model.getSelectedStep();
        return new GetGraphs(model).getGraphsFromXmlFilenames(selectedFiles, displayType, isStepSelected, selectedLoops, xmlFileCache, selectedStep);
    }

    public GraphsContent getGraphsContent(Integer selectedStep, Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        String[] selectedFiles = model.getSelectedFiles();
        DisplayType displayType = model.getSelectedDisplay();
        Boolean isStepSelected = true;
        Integer selectedLoops = model.getLoops();
        File[] xmlFileCache = model.getFilesCache();
        return new GetGraphs(model).getGraphsFromXmlFilenames(selectedFiles, displayType, isStepSelected, selectedLoops, xmlFileCache, selectedStep);
    }

    public GraphsContent getGraphsContent(DisplayType displayType, Integer selectedStep, Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        String[] selectedFiles = model.getSelectedFiles();
        Boolean isStepSelected = model.getSelectedStep() == null ? false : true;
        Integer selectedLoops = model.getLoops();
        File[] xmlFileCache = model.getFilesCache();
        return new GetGraphs(model).getGraphsFromXmlFilenames(selectedFiles, displayType, isStepSelected, selectedLoops, xmlFileCache, selectedStep);
    }

    private AnalyzerData getInitialAnalyzerData(File[] xmlFileCache) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        Selections selections = initialSelections();
        ListsContent listsContent = initalListsContent();
        AnalyzerData initialAnalyzerData = new AnalyzerData(selections, listsContent, null, null);
        model.setInitialAnalyzerDataWithoutNotifyingObservers(initialAnalyzerData);
        Integer selectedStep = selections.getStep() == null ? null : selections.getStep();
        GraphsContent graphsContent = getGraphsContent(selections.getFiles(), selections.getDisplay(), selections.getLoop(), xmlFileCache, selectedStep);
        CheckedModel checkedModel = null;
        return new AnalyzerData(selections, listsContent, graphsContent, checkedModel);
    }

    private TesterData getInitialTesterData(ListsContent initialAnalyzerListContent) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        String pathToTestFiles = options.getPathToTests();
        // String[] allFiles = getFileStringsListFromDir(new File(pathToTestFiles));
        // String[] allFiles = { "OneStep.ljx" };
        String[] allFiles = new XmlFileOrder().getFileOrder();
        String selectedFile = allFiles[0];
        FileTest fileTestSelectedFile = new FileTest(selectedFile, pathToTestFiles, model, options);
        FileTestSet fileTestSet = fileTestSetFromTestsPath(pathToTestFiles,allFiles);
        TesterData testerData = new TesterData(selectedFile, allFiles, fileTestSet, fileTestSelectedFile);
        return testerData;
    }

    private void setNonCalculationInitialTesterDataToModel(Model model) {
        int numLoops = 0;
        // String[] models = content
        TesterMisc testerMisc = new TesterMisc(0);
        TesterData testerData = new TesterData(testerMisc);
        model.setTesterData(testerData);
    }

    private FileTestSet fileTestSetFromTestsPath(String pathToTestFiles, String[] allFiles) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        ArrayList<FileTest> fileTestSetArr = new ArrayList<>();
        for (String thisFile : allFiles) {
            FileTest fileTestThisFile = new FileTest(thisFile, pathToTestFiles, model, options);
            fileTestSetArr.add(fileTestThisFile);
        }
        FileTestSet fileTestSet = new FileTestSet(fileTestSetArr);
        return fileTestSet;
    }


}
