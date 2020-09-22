package controller.types.data;

import controller.types.data.checkedModel.CheckedModel;
import controller.types.data.stateContent.Content;
import controller.types.data.selections.Selections;

public class Data {

    AppState state;
    Selections selections;
    Content content;
    CheckedModel checkedModel;

    public Data(AppState state, Content content) {
        this.state = state;
        this.content = content;
    }

    public AppState getState() {
        return state;
    }

    public String getStateStr() {
        return state.toString();
    }

}
