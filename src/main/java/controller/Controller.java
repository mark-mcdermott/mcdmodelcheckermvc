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

import static controller.types.data.AppState.ANALY_DEFAULT;
import static controller.types.data.AppState.INITIAL_RUN;
import static controller.types.data.DisplayType.ALL_GRAPHS;

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

    /*
    public void fileListener(Components components, Model model) {
        components.fileList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String[] selectedFiles = getListSelections(components.fileList);
                    Integer selectedLoops = model.getLoops();
                    File[] xmlFileCache = model.getFilesCache();
                    Integer selectedStep = model.getSelectedStep() == null ? null : Integer.parseInt(model.getSelectedStep());
                    try {
                        GraphsContent graphsContent = getGraphsContent(selectedFiles, selectedLoops, xmlFileCache, selectedStep);
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
            }
        });
    }
    */

    private String[] getListSelections(JList list) {
        Object[] selectedFilesObj = list.getSelectedValues();
        String[] selectedFiles = Arrays.copyOf(selectedFilesObj, selectedFilesObj.length, String[].class);
        return selectedFiles;
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

    private Data getInitalData(File[] xmlFileCache) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        AppState appState = INITIAL_RUN;
        Selections selections = initialSelections();
        ListsContent listsContent = initalListsContent();
        Integer selectedStep = selections.getStep() == null ? null : Integer.parseInt(selections.getStep());
        GraphsContent graphsContent = getGraphsContent(selections.getFiles(), selections.getLoop(), xmlFileCache, selectedStep);
        CheckedModel checkedModel = null;
        return new Data(appState, selections, listsContent, graphsContent, checkedModel);
    }


}
