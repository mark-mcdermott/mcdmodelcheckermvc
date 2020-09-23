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
import view.View;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static controller.types.data.AppState.ANALY_DEFAULT;
import static controller.types.data.DisplayType.ALL_GRAPHS;

public class Controller {

    Options options;

    public Controller(Model model, View view) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {

        options = new Options();
        initXmlFilesCache(model, options.getPathToXmlDir());
        Data initialData = getInitalData(model.getFilesCache());
        model.setData(initialData);

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

    private GraphsContent initialGraphsContent(String[] selectedFiles, int numLoops, File[] xmlFileCache, Integer selectedStep) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        if (selectedFiles == null) {
            new ExceptionMessage("Null in selected files, Controller.java");
            return null;
        } else {
            return new GetGraphs().getGraphsFromXmlFilenames(selectedFiles, false, numLoops, xmlFileCache, selectedStep);
        }
    }

    private Data getInitalData(File[] xmlFileCache) throws IOException, ExceptionMessage, ParserConfigurationException, SAXException {
        AppState appState = ANALY_DEFAULT;
        Selections selections = initialSelections();
        ListsContent listsContent = initalListsContent();
        Integer selectedStep = selections.getStep() == null ? null : Integer.parseInt(selections.getStep());
        GraphsContent graphsContent = initialGraphsContent(selections.getFiles(), selections.getLoop(), xmlFileCache, selectedStep);
        CheckedModel checkedModel = null;
        return new Data(appState, selections, listsContent, graphsContent, checkedModel);
    }


}
