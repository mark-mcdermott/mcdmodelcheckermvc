package mcdModelChecker.model;

import mcdModelChecker.controller.types.content.Content;
import mcdModelChecker.controller.types.model.CheckedModel;
import mcdModelChecker.controller.types.graph.Graphs;
import mcdModelChecker.controller.types.input.Selections;

/**
 * The main data model (and the M in MVC) - the model is used to store all the current data of the app (the app state).
 * In general, the controller's logic gets the data, the controller then passes it to the model and then the view
 * gets the data from the model to populate the view. Then when the view's event listeners are clicked, the controller's
 * logic crunches the new data depending on the new input and then again passes the new data to the model and the
 * view then again gets the new data from the model to repopulate the view.
 */
public class Model {

    Selections selections;          /** The user selections of the lists and input text fields in the left sidebar */
    Content content;                /** The lists data for the left sidebar lists, the graphs for main display and results data for the left column. */
    CheckedModel checkedModel;      /** The model checker's result. TODO: fill this in more when I figure this structure out more */

    /** Sole constructor, empty - only called once, at start of app.
     * Model data is set with setModel(Selections selections, Graphs graphs, CheckedModel checkedModel)
     *
     */
    public Model() { }

    /**
     * Takes in all of Model's local vars as params and sets them equal to the params.
     * @param selections    The user selections of the lists and input text fields in the left sidebar
     * @param content       The lists data for the left sidebar lists, the graphs for main display and results data for the left column.
     * @param checkedModel  The model checker's result. TODO: fill this in more when I figure this structure out more
     */
    public void setModel(Selections selections, Content content, CheckedModel checkedModel) {
        this.selections = selections;
        this.content = content;
        this.checkedModel = checkedModel;
    }

    public Selections getSelections() {
        return selections;
    }

    public Content getContent() {
        return content;
    }

    public CheckedModel getCheckedModel() {
        return checkedModel;
    }

}
