package mcdModelChecker.controller;

import mcdModelChecker.McdModelCheckerApp;
import mcdModelChecker.controller.graphs.ReadXml;
import mcdModelChecker.controller.graphs.Translate;
import mcdModelChecker.controller.types.content.Content;
import mcdModelChecker.controller.types.content.ListsData;
import mcdModelChecker.controller.types.graph.GraphType;
import mcdModelChecker.controller.types.graph.Graphs;
import mcdModelChecker.controller.types.kripke.Kripke;
import mcdModelChecker.controller.types.graph.LabelHash;
import mcdModelChecker.controller.types.input.Selections;
import mcdModelChecker.controller.types.model.CheckedModel;
import mcdModelChecker.controller.types.model.ResultsData;
import mcdModelChecker.controller.types.vertex.VertexList;
import mcdModelChecker.model.Model;
import mcdModelChecker.view.View;
import mcdModelChecker.controller.exceptions.McdException;
import mcdModelChecker.view.types.AnalyzerState;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.Arrays;

import static mcdModelChecker.view.types.AnalyzerState.DEFAULT_STATE;

public class Controller extends McdModelCheckerApp {

    public Controller() { }

    public void startApp() throws SAXException, McdException, ParserConfigurationException, IOException {

        // get default data
        Selections defaultSelections = getDefaultSelections();
        Content defaultContent = getDefaultContent();
        CheckedModel checkedModel = new CheckedModel();

        // set default data in model
        model.setModel(defaultSelections, defaultContent, checkedModel);

        view.initialRender(model, this);

    }

    /**
     * Gets default selections for lists and text fields in left sidebar.
     * Is only run at very beginning of app execution.
     * They are all the first line of each list or just zero for the numeric text field, I think it's ok.
     * You can move these to Options.java if you want more granular control or to change them back from hard
     * coded magic numbers - I'm trying to keep a compromise of granular control vs code pages of unmanagable sizes.
     *
     * @return a Selections object populated with app defaults (first element of each list and zero for the numeric text field).
     */
    Selections getDefaultSelections() {
        String file = options.getXmlFileOrder()[0];
        GraphType graphs = GraphType.values()[0];
        Integer step = null;
        String modelStr = options.getModels1Var()[0]; // change this to getModels2Var if you change selectedFile to a file with 2 properties
        Integer loops = 0;
        AnalyzerState state = DEFAULT_STATE;
        return new Selections(file, graphs, step, modelStr, loops, state);
    }

    ListsData getDefaultListsData(Graphs graphs) {
        String[] files = options.getXmlFileOrder();
        String[] graphTypes = graphTypes();
        String[] steps = null;
        String[] models = options.getModels1Var();
        String loops = "0";
        String[] states = null;
        String[] labels = graphs.getLabelHash().getLabelDisplayListArr();
        return new ListsData(files, graphTypes, steps, models, loops, states, labels);
    }

    Content getDefaultContent() throws SAXException, McdException, ParserConfigurationException, IOException {
        Graphs defaultGraphs = getDefaultGraphs();
        ListsData defaultListsData = getDefaultListsData(defaultGraphs);
        ResultsData resultsData = new ResultsData();
        return new Content(defaultListsData, defaultGraphs, resultsData);
    }

    Graphs getDefaultGraphs() throws SAXException, McdException, ParserConfigurationException, IOException {
        Translate translate = new Translate();
        String[] defaultFile = { options.getXmlFileOrder()[0] };
        LabelHash labelHash = new LabelHash();
        VertexList xmlVertList = new ReadXml().convertXmlToVertexList(defaultFile, labelHash);
        VertexList transVertList = translate.getTranslatedVertexListNoInterleavings(xmlVertList, 0, false, false);
        VertexList interVertList = translate.getTranslatedVertexListWithInterleavings(xmlVertList, 0, false, false);
        Kripke xmlKripke = new Kripke(xmlVertList);
        Kripke transKripke = new Kripke(transVertList);
        Kripke interKripke = new Kripke(interVertList);
        return new Graphs(xmlVertList, transVertList, interVertList, xmlKripke, transKripke, interKripke, null, labelHash);
    }


    // controller utils

    /**
     * Small one liner that gives a string array of all enum members
     * Code found at <a href="https://stackoverflow.com/q/45507197">https://stackoverflow.com/q/45507197</a>,
     * accessed 9/17/20
     *
     * @return a string array all the constants in the enum {@link GraphType}.
     */
    public String[] graphTypes() {
        // enum to string array code from https://stackoverflow.com/q/45507197
        return Arrays.stream(GraphType.values()).map(Enum::toString).toArray(String[]::new);
    }

    /**
     * One liner that gets the current line number for an exception message
     * For use in {@link McdException}
     * Code found at <a href="https://stackoverflow.com/a/115027">https://stackoverflow.com/a/115027</a>,
     * accessed around 7/1/20
     *
     * @return an int of line number
     */
    public static int getLineNumber() {
        return Thread.currentThread().getStackTrace()[2].getLineNumber();
    }

}
