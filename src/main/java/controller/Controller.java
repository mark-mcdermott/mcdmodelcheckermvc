package controller;

import controller.content.DisplayTypes;
import controller.content.Models;
import controller.content.XmlFileOrder;
import controller.types.Data;
import controller.types.ListsContent;
import model.Model;
import view.View;

import static controller.types.AppState.ANALY_DEFAULT;

public class Controller {

    public Controller(Model model, View view) {

        // scratch work
        ListsContent listsContent = initalListsContent();

        Data data = new Data(ANALY_DEFAULT, content);

        model.setData(data);

    }

    public ListsContent initalListsContent() {
        String[] files = new XmlFileOrder().getFileOrder();
        String[] displays = new DisplayTypes().getDisplayTypes();
        String[] steps = null;
        String[] models = new Models().getModels1Var();
        Integer loops = 0;
        String[] states = null;
        String[] labels = null;
        return new ListsContent(files, displays, steps, models, loops, states, labels);
    }

}
