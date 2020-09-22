package controller.types.data.content;

import com.google.inject.internal.util.Lists;

public class Content {

    ListsContent listsContent;

    public Content(ListsContent listsContent) {
        this.listsContent = listsContent;
    }

    public ListsContent getListsContent() {
        return listsContent;
    }
}
