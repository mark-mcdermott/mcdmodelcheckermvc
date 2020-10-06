package mcdModelChecker.controller.types.input;

import mcdModelChecker.controller.types.graph.GraphType;
import mcdModelChecker.view.types.AnalyzerState;

public class Selections {

    String file;
    GraphType graphs;
    Integer step;
    String model;
    Integer loops;
    AnalyzerState state;

    /** Sole constructor - takes in all of Selections' local vars as params and sets them equal to the params.
     * "Selections" in this case means user selections of all the user input lists in the left sidebar.
     *
     * @param file      File selected from the list of XML files in the left sidebar
     * @param graphs    Type of graph selected from the left sidebar list of possible graph types to display
     * @param step      Optional selected step from left sidebar steps. The step list only shows when the graph type selected is trans, inter, trans comp, or inter comp
     * @param model     Model selected from left sidebar list of models to use in the model checking
     * @param loops     Number of loops set to run, specified in loops text field in left sidebar. Defaults to zero.
     * @param state     State selected from list of states in left sidebar. State is used in the model checking and checks if that state holds for specified property. Defaults to inital state.
     */
    public Selections(String file, GraphType graphs, Integer step, String model, Integer loops, AnalyzerState state) {
        this.file = file;
        this.graphs = graphs;
        this.step = step;
        this.model = model;
        this.loops = loops;
        this.state = state;
    }

    public String getFile() {
        return file;
    }

    public GraphType getGraphs() {
        return graphs;
    }

    public Integer getStep() {
        return step;
    }

    public String getModel() {
        return model;
    }

    public Integer getLoops() {
        return loops;
    }

    public AnalyzerState getState() {
        return state;
    }

}
