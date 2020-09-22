package controller;

import controller.content.Models;
import controller.types.data.Data;
import controller.types.data.content.Content;
import controller.types.data.content.ListsContent;
import model.Model;
import view.View;

import static controller.types.data.AppState.ANALY_DEFAULT;

public class Controller {

    public Controller(Model model, View view) {

        // scratch work
        String[][] models = new Models().getModels();
        ListsContent listsContent = new ListsContent(models[0]);
        Content content = new Content (listsContent);

        Data data = new Data(ANALY_DEFAULT, content);

        model.setData(data);

    }
}
