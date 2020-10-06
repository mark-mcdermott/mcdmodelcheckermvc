package mcdModelChecker.controller.types.content;

import mcdModelChecker.controller.types.graph.Graphs;
import mcdModelChecker.controller.types.model.ResultsData;

public class Content {

    Graphs graphs;
    ListsData listsData;
    ResultsData resultsData;

    /**
     * Sole contructor, takes all local vars as params and sets them equal to the params
     *
     * @param listsData     Content for all the lists on the left sidebar (files, graphs, steps, models, loops, states, labels)
     * @param graphs        The 3 vertexLists (xml, translation and interleavings), their 3 Kripke structures, optionally one or two stepGraphs (vertexLists that stopped translating at a certain step specified by the user, for debugging/comparison) and the labelHash with the properties and labels of the xml graph
     * @param resultsData   Output data TODO: fill in this stub
     */
    public Content(ListsData listsData, Graphs graphs, ResultsData resultsData) {
        this.listsData = listsData;
        this.graphs = graphs;
        this.resultsData = resultsData;
    }

}
