package controller;

import _options.Options;
import controller.content.GetGraphs;
import controller.content.staticContent.DisplayTypes;
import controller.content.staticContent.Models;
import controller.content.staticContent.XmlFileOrder;
import controller.filesCache.FilesCache;
import controller.types.data.*;
import controller.utils.ExceptionMessage;
import model.Model;
import org.xml.sax.SAXException;
import view.Components;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static controller.types.data.AppState.ANALY_COMP;
import static controller.types.data.AppState.ANALY_DEFAULT;
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
    public void handleFileListClick(Components components, Model model) {
        String[] selectedFiles = getListSelections(components.fileList);
        try {
            GraphsContent graphsContent = getGraphsContent(selectedFiles, model);
            Data data = model.getData();
            data.setFileSelections(selectedFiles);
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

    public void handleDisplayListClick(Components components, Model model) {
        DisplayType selectedDisplay = DisplayType.valueOf(getListSelection(components.fileList));
        try {
            AppState appState = null;
            if (selectedDisplay == ALL_GRAPHS || selectedDisplay == XML_ONLY || selectedDisplay == TRANS_ONLY || selectedDisplay == INTER_ONLY) {
                appState = ANALY_DEFAULT;
            } else if (selectedDisplay == TRANS_COMP || selectedDisplay == INTER_COMP) {
                appState = ANALY_COMP;
            }
            GraphsContent graphsContent = getGraphsContent(model);
            Data data = model.getData();
            data.setDisplaySelections(selectedDisplay);
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
        DisplayType displayType = ALL_GRAPHS;
        String step = null;
        String model = null;
        Integer loop = 0;
        String state = null;
        return new Selections(files, displayType, step, model, loop, state);
    }

    private ListsContent initalListsContent() {
        String[] files = new XmlFileOrder().getFileOrder();
        String[] displays = new DisplayTypes().getDisplayTypes();
        String[] steps = null;
        String[] models = new Models().getModels1Var();
        Integer loops = 0;
        String[] states = null;
        String[] labels = null;
        return new ListsContent(files, displays, steps, models, loops, states, labels);
    }

    public GraphsContent getGraphsContent(String[] selectedFiles, int numLoops, File[] xmlFileCache, Integer selectedStep) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        return new GetGraphs().getGraphsFromXmlFilenames(selectedFiles, false, numLoops, xmlFileCache, selectedStep);
    }

    public GraphsContent getGraphsContent(String[] selectedFiles, Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        Boolean isStepSelected = model.getSelectedStep() == null ? false : true;
        Integer selectedLoops = model.getLoops();
        File[] xmlFileCache = model.getFilesCache();
        Integer selectedStep = model.getSelectedStep() == null ? null : Integer.parseInt(model.getSelectedStep());
        return new GetGraphs().getGraphsFromXmlFilenames(selectedFiles, isStepSelected, selectedLoops, xmlFileCache, selectedStep);
    }

    public GraphsContent getGraphsContent(Model model) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        String[] selectedFiles = model.getSelectedFiles();
        Boolean isStepSelected = model.getSelectedStep() == null ? false : true;
        Integer selectedLoops = model.getLoops();
        File[] xmlFileCache = model.getFilesCache();
        Integer selectedStep = model.getSelectedStep() == null ? null : Integer.parseInt(model.getSelectedStep());
        return new GetGraphs().getGraphsFromXmlFilenames(selectedFiles, isStepSelected, selectedLoops, xmlFileCache, selectedStep);
    }

    private Data getInitalData(File[] xmlFileCache) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        // AppState appState = INITIAL_RUN;
        AppState appState = ANALY_DEFAULT;
        Selections selections = initialSelections();
        ListsContent listsContent = initalListsContent();
        Integer selectedStep = selections.getStep() == null ? null : Integer.parseInt(selections.getStep());
        GraphsContent graphsContent = getGraphsContent(selections.getFiles(), selections.getLoop(), xmlFileCache, selectedStep);
        CheckedModel checkedModel = null;
        return new Data(appState, selections, listsContent, graphsContent, checkedModel);
    }


}
