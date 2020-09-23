package view;

import _options.DirectedGraphOptions;
import _options.Options;
import controller.Controller;
import controller.types.data.*;
import controller.utils.ExceptionMessage;
import model.Model;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Observable;
import java.util.Observer;

import static controller.types.data.AppState.*;
import static controller.types.data.DisplayType.*;
import static controller.utils.Utils.getIndexFromListElem;
import static controller.utils.Utils.getIndicesFromListElems;
import static java.awt.BorderLayout.PAGE_START;

public class View extends JFrame implements Observer {

    Model model;
    Controller controller;
    Components components;
    Options options;

    public View(Model model, Controller controller) {
        this.model = model;
        this.controller = controller;
        this.components = new Components();
        this.options = new Options();
    }

    public void update(Observable o, Object arg) {
        if (arg == "INITIAL_RUN") {
            renderAnalyzerInitalRender();
        } else if (arg == "ANALY_DEFAULT") {
            renderAnalyzerDefaultState();
        }
    }

    private void renderAnalyzerInitalRender() {
        analyzerShell(this);
        populateLists();
        setSelections();
        // TODO: grayOutInactiveSections(appState)
        drawGraphs();
        addAnalyzerListeners();
        repaint();
    }

    private void renderAnalyzerDefaultState() {
        this.getContentPane().removeAll();
        analyzerShell(this);
        populateLists();
        setSelections();
        // TODO: grayOutInactiveSections(appState)
        drawGraphs();
        addAnalyzerListeners();
        repaint();
    }

    private void addAnalyzerListeners() {

        // file list
        //controller.fileListener(components, model);

        components.fileList.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    String[] selectedFiles = getListSelections(components.fileList);
                    Integer selectedLoops = model.getLoops();
                    File[] xmlFileCache = model.getFilesCache();
                    Integer selectedStep = model.getSelectedStep() == null ? null : Integer.parseInt(model.getSelectedStep());
                    try {
                        GraphsContent graphsContent = controller.getGraphsContent(selectedFiles, selectedLoops, xmlFileCache, selectedStep);
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

    private String[] getListSelections(JList list) {
        // Object[] selectedFilesObj = list.getSelectedValues();
        // String[] selectedFiles = Arrays.copyOf(selectedFilesObj, selectedFilesObj.length, String[].class);
        ListModel listModel = list.getModel();
        int[] selectedIndices = list.getSelectedIndices();
        int numElems = selectedIndices.length;
        String[] listElems = new String[numElems];
        for (int i=0; i<numElems; i++) {
            listElems[i] = listModel.getElementAt(selectedIndices[i]).toString();
        }
        return listElems;
    }

    private String getListElemFromIndex(int index, JList list) {
        return list.getModel().getElementAt(index).toString();
    }




    private void drawGraphs() {
        DirectedGraphOptions graphOptions3AcrossSpot1 = options.graphOptions3AcrossSpot1();

        // get step graph settings
        Boolean isStepGraph = model.getSelectedStep() == null ? false : true;
        Integer selectedStep = null;
        if (isStepGraph) { selectedStep = Integer.parseInt(model.getSelectedStep()); }

        // layouts
        AppState appState = model.getAppState();
        DisplayType type = model.getSelectedDisplay();
        DrawGraph drawGraph = new DrawGraph(graphOptions3AcrossSpot1); // TODO maybe change the param name

        JPanel mainGraphPanel = components.mainGraphPanel;
        if (appState == INITIAL_RUN || appState == ANALY_DEFAULT || appState == ANALY_RESULTS) {
            // draw one-across graph
            if (type == XML_ONLY) { drawGraph.drawGraph(components.graphPanel1, model.getXmlVertexList()); }
            else if (type == TRANS_ONLY) { drawGraph.drawGraph(components.graphPanel1, model.getTranslationVertexList()); }
            else if (type == INTER_ONLY) { drawGraph.drawGraph(components.graphPanel1, model.getInterleavingsVertexList()); }
            else if (type == ALL_GRAPHS) {
                drawThreeAcrossGraphs(drawGraph);
            }
        } else if (appState == ANALY_COMP) {
            // TODO draw 2 two-across graphs
        }

    }


    private void drawThreeAcrossGraphs(DrawGraph drawGraph) {
        // draw three-across graphs
        drawGraph.drawGraph(components.graphPanel1, model.getXmlVertexList());
        drawGraph.drawGraph(components.graphPanel2, model.getTranslationVertexList());
        drawGraph.drawGraph(components.graphPanel3, model.getInterleavingsVertexList());
        // add three-across graphs
        components.mainGraphPanel.remove(components.graphPanel1);
        components.mainGraphPanel.add(components.graphPanel1, new BorderLayout());
        components.mainGraphPanel.add(components.graphPanel2, new BorderLayout());
        components.mainGraphPanel.add(components.graphPanel3, new BorderLayout());
        // add three-across titles
        components.graphPanel1.add(components.graphPanel1Title, PAGE_START);
        components.graphPanel2.add(components.graphPanel2Title, PAGE_START);
        components.graphPanel3.add(components.graphPanel3Title, PAGE_START);
        components.graphPanel1Title.setText("XML");
        components.graphPanel2Title.setText("Translation");
        components.graphPanel3Title.setText("Interleavings");
        // set three-across styles
        components.graphPanel1.setPreferredSize(new Dimension(options.getGraphLayouts3AcrossWidth(), options.getGraphLayoutsHeight()));
        components.graphPanel2.setPreferredSize(new Dimension(options.getGraphLayouts3AcrossWidth(), options.getGraphLayoutsHeight()));
        components.graphPanel3.setPreferredSize(new Dimension(options.getGraphLayouts3AcrossWidth(), options.getGraphLayoutsHeight()));
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
