package controller;

import _options.Options;
import controller.content.staticContent.DisplayTypes;
import controller.content.staticContent.Models;
import controller.content.staticContent.XmlFileOrder;
import controller.filesCache.FilesCache;
import controller.types.data.*;
import model.Model;
import view.View;

import java.io.File;

import static controller.types.data.AppState.ANALY_DEFAULT;
import static controller.types.data.DisplayType.ALL_GRAPHS;

public class Controller {

    Options options;

    public Controller(Model model, View view) {

        options = new Options();
        initXmlFilesCache(model, options.getPathToXmlDir());
        Data initialData = getInitalData();
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

    private GraphsContent initialGraphsContent() {



        return new GraphsContent();
    }

    private Data getInitalData() {
        AppState appState = ANALY_DEFAULT;
        Selections selections = initialSelections();
        ListsContent listsContent = initalListsContent();
        GraphsContent graphsContent = initialGraphsContent();
        return new Data(ANALY_DEFAULT, selections, listsContent, graphsContent, null);
    }


}
