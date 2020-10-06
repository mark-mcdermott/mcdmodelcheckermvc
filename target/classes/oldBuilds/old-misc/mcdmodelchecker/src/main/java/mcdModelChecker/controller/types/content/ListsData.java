package mcdModelChecker.controller.types.content;

import java.util.List;

/**
 * All the content for the lists (and loops textfield) in the left column. It's 1/3 of the Content data structure.
 */
public class ListsData {
    String[] files;
    String[] graphs;
    String[] steps;
    String[] models;
    String loops;
    String[] states;
    String[] labels;

    /**
     * Sole constructor - takes in all ListsData local vars as params and set them equal to the params.
     *
     * @param files     String array of all the xml filenames for the Files list in left sidebar
     * @param graphs    String array of all the graph types in the Graphs list in the left sidebar
     * @param steps     Optional String array of the steps in the Steps list in the left sidebar. Only populated when graphs is equal to trans/inter/trans comp/inter comp.
     * @param models    String array of all the models for the Models list in left sidebar. Will be populated from the 1 variable models list or the 2 var models list, depending on the selected file.
     * @param loops     Number of loops in the Loops text field in the left sidebar. Defaults to zero.
     * @param states    Optional number of states in the States list in the left sidebar. Only populated after the model is chosed and checked - user can then specify a specific state to recheck the model on.
     * @param labels    A list of the labels in the xml file and their corresponding properties. Example: p (Doctor performs heart transplant).
     */
    public ListsData(String[] files, String[] graphs, String[] steps, String[] models, String loops, String[] states, String[] labels) {
        this.files = files;
        this.graphs = graphs;
        this.steps = steps;
        this.models = models;
        this.loops = loops;
        this.states = states;
        this.labels = labels;
    }

}
