package controller;

import _options.Options;
import controller.analyzer.ModelChecker;
import controller.content.GetGraphs;
import controller.content.staticContent.DisplayTypes;
import controller.content.staticContent.Models;
import controller.content.staticContent.XmlFileOrder;
import controller.filesCache.FilesCache;
import controller.types.ctl.Kripke;
import controller.types.data.*;
import controller.types.graph.LabelHash;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;
import controller.types.modelChecking.CheckedModel;
import controller.utils.ExceptionMessage;
import model.Model;
import org.xml.sax.SAXException;
import view.Components;

import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static controller.types.data.AppState.*;
import static controller.types.data.DisplayType.*;
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
        Data initialData = getInitalData(model.getFilesCache());
        model.setData(initialData);
    }

    // handle listeners clicks
    // file list click
    public void handleFileListClick(Components components, Model model) {
        String[] selectedFiles = getListSelections(components.fileList);
        try {
            GraphsContent graphsContent = getGraphsContent(selectedFiles, ANALY_DEFAULT, ALL_GRAPHS, model, false);
            Data data = model.getData();
            data.setFileSelections(selectedFiles);
            data.setDisplaySelections(ALL_GRAPHS);
            data.setStepSelections(null);
            data.setModelSelections("⊤");
            data.setGraphsContent(graphsContent);
            data.setAppState(ANALY_DEFAULT);        // when selected file(s) is changed, app goes to default state
            model.setData(data);
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

    // display list click
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

            Data data = model.getData();
            data.setDisplaySelections(selectedDisplay);
            if (selectedStep != null) { data.setStepSelections(selectedStep); }
            if (graphsContent.getNumSteps() != null) {
                Integer[] steps = getStepsFromNumSteps(graphsContent.getNumSteps());
                data.setListsContentSteps(steps);
            }
            data.setGraphsContent(graphsContent);
            data.setAppState(appState);        // when selected file(s) is changed, app goes to default state
            model.setData(data);
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

    public void handleStepListClick(Components components, Model model) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        Integer selectedStep = Integer.parseInt(getListSelection(components.stepList));
        GraphsContent graphsContent = getGraphsContent(selectedStep, model);
        Data data = model.getData();
        data.setStepSelections(selectedStep);
        data.setGraphsContent(graphsContent);
        model.setData(data);
    }

    public void handleModelListClick(Components components, Model model) throws SAXException, ParserConfigurationException, ExceptionMessage, IOException {
        String selectedModel = getListSelection(components.modelList);

        // check model here
        Vertex[] states = vertexArrListToArr(model.getInterleavingsKripke().S);
        Vertex selectedState = states[0];
        CheckedModel checkedModel = runModelChecker(selectedModel, model, model.getLabelHash(), states, selectedState);

        Data data = model.getData();
        data.setAppState(ANALY_RESULTS);
        data.setModelSelections(selectedModel);
        data.setListsContentStates(vertexArrListToArr(model.getInterleavingsKripke().S));
        data.setStateSelections(data.getListsContentStates()[0]);
        data.setCheckedModel(checkedModel);
        data.setDoesHold(checkedModel.getResultDoesHold());
        data.setStatesThatHold(checkedModel.getResultStatesThatHold());
        data.setCounterExample(checkedModel.getResultCounterExample());
        data.setCounterExample(checkedModel.getResultTime());
        model.setData(data);
    }

    String[] getStatesFromKripke(Kripke interKripke) {
        return getStateStrListFromNumStates(getNumStatesFromInterKripke(interKripke));
    }

    Integer getNumStatesFromInterKripke(Kripke interKripke) {
        return interKripke.S.size();
    }

    private Vertex[] vertexArrListToArr(ArrayList<Vertex> arrList) {
        ArrayList<Vertex> statesArrList = model.getInterleavingsKripke().S;
        Vertex[] statesArr = new Vertex[statesArrList.size()];
        statesArrList.toArray(statesArr);
        return statesArr;
    }

    private String[] getStateStrListFromNumStates(Integer numStates) {
        String[] stateStrList = new String[numStates];
        for (Integer i=0; i<numStates; i++) {
            stateStrList[i] = Integer.toString(i);
        }
        return stateStrList;
    }

    public CheckedModel runModelChecker(String selectedModel, Model model, LabelHash labelHash, Vertex[] states, Vertex stateToCheck) {

        // get items needed for the model checking from the model
        Kripke kripke = model.getInterleavingsKripke();
        int loops = model.getLoops();
        // Vertex stateToCheck = model.getSelectedState();

        // run the model checker
        ModelChecker modelChecker = new ModelChecker(selectedModel, kripke, stateToCheck, loops, labelHash);
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
        String[] files = {"TwoSteps.ljx"};
        DisplayType displayType = ALL_GRAPHS;
        Integer step = null;
        String model = "⊤";
        Integer loop = 0;
        Vertex state = null;
        return new Selections(files, displayType, step, model, loop, state);
    }

    private ListsContent initalListsContent() {
        String[] files = new XmlFileOrder().getFileOrder();
        String[] displays = new DisplayTypes().getDisplayTypes();
        Integer[] steps = null;
        String[] models = new Models().getModels1Var();
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

    private Data getInitalData(File[] xmlFileCache) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        // AppState appState = INITIAL_RUN;
        AppState appState = ANALY_DEFAULT;
        Selections selections = initialSelections();
        ListsContent listsContent = initalListsContent();
        Integer selectedStep = selections.getStep() == null ? null : selections.getStep();
        GraphsContent graphsContent = getGraphsContent(selections.getFiles(), selections.getDisplay(), selections.getLoop(), xmlFileCache, selectedStep);
        CheckedModel checkedModel = null;
        return new Data(appState, selections, listsContent, graphsContent, checkedModel);
    }


}
