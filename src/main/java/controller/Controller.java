package controller;

import controller.content.DisplayTypes;
import controller.content.Models;
import controller.content.XmlFileOrder;
import controller.types.data.*;
import model.Model;
import view.View;

import static controller.types.data.AppState.ANALY_DEFAULT;
import static controller.types.data.DisplayType.ALL_GRAPHS;

public class Controller {

    public Controller(Model model, View view) {

        Data initialData = getInitalData();
        model.setData(initialData);

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
        // TODO
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
