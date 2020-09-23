package view;

import _options.DirectedGraphOptions;
import _options.Options;
import controller.types.data.ListsContent;
import controller.types.data.Selections;
import model.Model;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

import static controller.utils.Utils.getIndexFromListElem;
import static controller.utils.Utils.getIndicesFromListElems;

public class View extends JFrame implements Observer {

    Model model;
    Components components;
    Options options;

    public View(Model model) {
        this.model = model;
        this.components = new Components();
        this.options = new Options();
    }

    public void update(Observable o, Object arg) {
        if (arg == "ANALY_DEFAULT") {
            renderAnalyzerDefaultState();
        }
    }

    private void renderAnalyzerDefaultState() {
        analyzerShell(this);
        populateLists();
        setSelections();
        // TODO: grayOutInactiveSections(appState)
        drawGraphs();
        repaint();
    }

    private void drawGraphs() {
        DirectedGraphOptions graphOptions = options.getXmlGraphOptions();
        DrawGraph drawGraph = new DrawGraph(graphOptions);
        JPanel graphPanel1 = components.graphPanel1;
        drawGraph.drawGraph(graphPanel1, model.getXmlVertexList());
    }

    private void analyzerShell(JFrame frame) {
        components.sharedComponents(frame);
        components.analyzerComponents();
    }

    private void populateLists() {
        ListsContent listsContent = model.getListsContent();
        components.fileList.setListData(listsContent.getFiles());
        components.displayList.setListData(listsContent.getDisplays());
        components.modelList.setListData(listsContent.getModels());
        components.loopTextarea.setText(listsContent.getLoops().toString());
    }

    private void setSelections() {
        Selections selections = model.getSelections();
        components.fileList.setSelectedIndices(getIndicesFromListElems(selections.getFiles(), components.fileList));
        components.displayList.setSelectedIndex(getIndexFromListElem(selections.getDisplay().toString(), components.displayList));
        if (selections.getStep() != null) { components.stepList.setSelectedIndex(getIndexFromListElem(selections.getStep(), components.stepList)); }
        if (selections.getModel() != null) { components.modelList.setSelectedIndex(getIndexFromListElem(selections.getModel(), components.modelList)); }
        if (selections.getState() != null) { components.stateList.setSelectedIndex(getIndexFromListElem(selections.getState(), components.stateList)); }
    }

}
