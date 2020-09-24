package view;

import _options.DirectedGraphOptions;
import _options.Options;
import controller.Controller;
import controller.types.data.*;
import controller.types.graph.VertexList;
import controller.utils.ExceptionMessage;
import model.Model;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.io.IOException;
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
        if (arg == "ANALY_DEFAULT") {
            renderAnalyzerDefaultState();
        } else if (arg == "ANALY_COMP") {
            renderAnalyzerComparisonState();
        } else if (arg == "ANALY_RESULTS") {
            renderAnalyzerResultsState();
        }
    }

    private void renderAnalyzerDefaultState() {
        initAnalyzer();
        drawGraphs(ANALY_DEFAULT);
        repaint();
    }

    private void renderAnalyzerComparisonState() {
        initAnalyzer();
        drawGraphs(ANALY_COMP);
        repaint();
    }

    private void renderAnalyzerResultsState() {
        initAnalyzer();
        drawGraphs(ANALY_RESULTS);
        repaint();
    }

    private void initAnalyzer() {
        this.getContentPane().removeAll();
        analyzerShell(this);
        populateLists();
        setSelections();
        addAnalyzerListeners();
        // TODO: grayOutInactiveSections(appState)
    }

    private void addAnalyzerListeners() {
        addAnalyzerListListener(components.fileList, components, model);        // file list listener
        addAnalyzerListListener(components.displayList, components, model);     // display list listener
        addAnalyzerListListener(components.stepList, components, model);        // step list listener
        addAnalyzerListListener(components.modelList, components, model);        // model list listener
    }

    // adds a specific listener (handles all of them, just chooses one at a time - depending on params)
    private void addAnalyzerListListener(JList list, Components components, Model model) {
        list.addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {

                    // run through cases, call correct handler method in conroller (cumbersome code, but keeps addAnalyzerListeners() nice and clean)
                    if (list == components.fileList) {
                        controller.handleFileListClick(components, model);
                    } else if (list == components.displayList) {
                        controller.handleDisplayListClick(components, model);
                    } else if (list == components.stepList) {
                        try {
                            controller.handleStepListClick(components, model);
                        } catch (SAXException saxException) {
                            saxException.printStackTrace();
                        } catch (ParserConfigurationException parserConfigurationException) {
                            parserConfigurationException.printStackTrace();
                        } catch (ExceptionMessage exceptionMessage) {
                            exceptionMessage.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    } else if (list == components.modelList) {
                        try {
                            controller.handleModelListClick(components, model);
                        } catch (SAXException saxException) {
                            saxException.printStackTrace();
                        } catch (ParserConfigurationException parserConfigurationException) {
                            parserConfigurationException.printStackTrace();
                        } catch (ExceptionMessage exceptionMessage) {
                            exceptionMessage.printStackTrace();
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
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




    // TODO maybe break into drawDefaultGraphs() and drawComparisonGraphs();
    private void drawGraphs(AppState appState) {
        // set up graph settings
        DirectedGraphOptions graphOptions3AcrossSpot1 = options.graphOptions3AcrossSpot1();
        Boolean isStepGraph = model.getSelectedStep() == null ? false : true;
        Integer selectedStep = null;
        if (isStepGraph) { selectedStep = model.getSelectedStep(); }

        // get necessary vars
        // AppState appState = model.getAppState();
        DisplayType type = model.getSelectedDisplay();
        DrawGraph drawGraph = new DrawGraph(graphOptions3AcrossSpot1); // TODO maybe change the param name
        JPanel mainGraphPanel = components.mainGraphPanel;

        if (appState == ANALY_DEFAULT || appState == ANALY_RESULTS) {
            drawDefaultGraphs(drawGraph, type);
        } else if (appState == ANALY_COMP) {
            drawComparisonGraphs(drawGraph, type);
        }


    }

    private void drawDefaultGraphs(DrawGraph drawGraph, DisplayType displayType) {
        if (displayType == XML_ONLY || displayType == TRANS_ONLY || displayType == INTER_ONLY) {
            drawOneAcrossGraph(drawGraph, displayType);
        } else if (displayType == ALL_GRAPHS) {
            drawThreeAcrossGraphs(drawGraph);
        }
    }

    private void drawComparisonGraphs(DrawGraph drawGraph, DisplayType displayType) {
        drawTwoAcrossGraphs(drawGraph, displayType);
    }



    private void drawOneAcrossGraph(DrawGraph drawGraph, DisplayType displayType) {
        // setup one-across graph styles
        components.mainGraphPanel.remove(components.graphPanel1);
        components.mainGraphPanel.add(components.graphPanel1, new BorderLayout());
        components.graphPanel1.add(components.graphPanel1Title, PAGE_START);
        components.graphPanel1.setPreferredSize(new Dimension(options.getGraphLayout1AcrossWidth(), options.getGraphLayoutsHeight()));
        // add one-across graph and its title
        if (displayType == XML_ONLY) {
            drawGraph.drawGraph(components.graphPanel1, model.getXmlVertexList());
            components.graphPanel1Title.setText("XML");
        } else if (displayType == TRANS_ONLY) {
            drawGraph.drawGraph(components.graphPanel1, model.getTranslationVertexList());
            components.graphPanel1Title.setText("Translation");
        } else if (displayType == INTER_ONLY) {
            drawGraph.drawGraph(components.graphPanel1, model.getInterleavingsVertexList());
            components.graphPanel1Title.setText("Interleavings");
        }
    }

    private void drawTwoAcrossGraphs(DrawGraph drawGraph, DisplayType type) {
        // setup two-across graph styles
        components.mainGraphPanel.remove(components.graphPanel1);
        components.mainGraphPanel.remove(components.graphPanel2);
        components.mainGraphPanel.add(components.graphPanel1, new BorderLayout());
        components.mainGraphPanel.add(components.graphPanel2, new BorderLayout());
        components.graphPanel1.add(components.graphPanel1Title, PAGE_START);
        components.graphPanel2.add(components.graphPanel2Title, PAGE_START);
        components.graphPanel1.setPreferredSize(new Dimension(options.getGraphLayout2AcrossWidth(), options.getGraphLayoutsHeight()));
        components.graphPanel2.setPreferredSize(new Dimension(options.getGraphLayout2AcrossWidth(), options.getGraphLayoutsHeight()));

        VertexList stepGraphSelectedStepMinusOne = model.getStepGraphSelectedStepMinusOne();
        VertexList stepGraphSelectedStep = model.getStepGraphSelectedStep();

        Integer stepX = stepGraphSelectedStep.getNumTotalSteps();
        Integer stepXMinus1 = stepX - 1;

        drawGraph.drawGraph(components.graphPanel1, stepGraphSelectedStepMinusOne);
        drawGraph.drawGraph(components.graphPanel2, stepGraphSelectedStep);

        if (type == TRANS_COMP) {
            components.graphPanel1Title.setText("Translation Step " + stepXMinus1.toString());
            components.graphPanel2Title.setText("Translation Step " + stepX.toString());
        } else if (type == INTER_COMP) {
            components.graphPanel1Title.setText("Interleavings Step " + stepXMinus1.toString());
            components.graphPanel2Title.setText("Interleavings Step " + stepX.toString());
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
        DisplayType displayType = model.getSelectedDisplay();
        ListsContent listsContent = model.getListsContent();
        components.fileList.setListData(listsContent.getFiles());
        components.displayList.setListData(listsContent.getDisplays());
        if (displayType == TRANS_COMP || displayType == INTER_COMP) {
            components.stepList.setListData(listsContent.getSteps());
        }
        components.modelList.setListData(listsContent.getModels());
        components.loopTextarea.setText(listsContent.getLoops().toString());
        components.labelList.setListData(model.getLabelDisplay());
        // populate results here
        // if (model.getCheckedModel() != null) {
    }

    private void setSelections() {
        Selections selections = model.getSelections();
        components.fileList.setSelectedIndices(getIndicesFromListElems(selections.getFiles(), components.fileList));
        components.displayList.setSelectedIndex(getIndexFromListElem(selections.getDisplay().toString(), components.displayList));
        if (selections.getStep() != null) { components.stepList.setSelectedIndex(getIndexFromListElem(selections.getStep().toString(), components.stepList)); }
        if (selections.getModel() != null) { components.modelList.setSelectedIndex(getIndexFromListElem(selections.getModel(), components.modelList)); }
        if (selections.getState() != null) { components.stateList.setSelectedIndex(getIndexFromListElem(selections.getState().getName(), components.stateList)); }
    }

}
