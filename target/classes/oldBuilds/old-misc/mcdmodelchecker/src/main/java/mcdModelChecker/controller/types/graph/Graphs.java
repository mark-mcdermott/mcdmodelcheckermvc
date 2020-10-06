package mcdModelChecker.controller.types.graph;

import mcdModelChecker.controller.types.kripke.Kripke;
import mcdModelChecker.controller.types.vertex.VertexList;

/**
 * The Graph object holds the data necessary for the graphs and related data.
 * Specifically, it holds the 3 vertexLists (xml, translation and interleavings), their 3 Kripke structures,
 * optionally one or two stepGraphs (vertexLists that stopped translating at a certain step specified by the user, for
 * debugging/comparison) and the labelHash with the properties and labels of the xml graph
 */
public class Graphs {

    VertexList xmlVertList;     // xml vertex list
    VertexList transVertList;   // translation vertex list
    VertexList interVertList;   // interleavings vertex list
    Kripke xmlKripke;           // xml kripke
    Kripke transKripke;         // translation kripke
    Kripke interKripke;         // interleavings kripke
    Graphs[] stepGraphs;        // holds 0 or 2 graphs that stop at a certain step for comparing a step and the prev step
    LabelHash labelHash;        // hashmap with the labels and properties in the xml list

    /**
     * Sole constructor - takes in all of Graph's local vars as params and sets them equal to the params
     *
     * @param xmlVertList       List of vertices for the xml file
     * @param transVertList     List of vertices for the translated (according to Little-JIL specs) xml file
     * @param interVertList     List of vertices for the translated xml file with interleavings
     * @param xmlKripke         Kripke structure for the xml file
     * @param transKripke       Kripke structure for the translated (according to Little-JIL specs) xml file
     * @param interKripke       Kripke structure for the translated xml file with interleavings
     * @param stepGraphs        Optional list of vertices for one or two stepGraphs, trans/inter graphs which stopped translating at a step specified by the user (for debugging/comparison purposes)
     * @param labelHash         HashMap with the labels and properties from the xml vertexList
     */
    public Graphs(VertexList xmlVertList, VertexList transVertList, VertexList interVertList, Kripke xmlKripke, Kripke transKripke, Kripke interKripke, Graphs[] stepGraphs, LabelHash labelHash) {
        this.xmlVertList = xmlVertList;
        this.transVertList = transVertList;
        this.interVertList = interVertList;
        this.xmlKripke = xmlKripke;
        this.transKripke = transKripke;
        this.interKripke = interKripke;
        this.stepGraphs = stepGraphs;
        this.labelHash = labelHash;
    }

    public VertexList getXmlVertList() {
        return xmlVertList;
    }

    public VertexList getTransVertList() {
        return transVertList;
    }

    public VertexList getInterVertList() {
        return interVertList;
    }

    public Kripke getXmlKripke() {
        return xmlKripke;
    }

    public Kripke getTransKripke() {
        return transKripke;
    }

    public Kripke getInterKripke() {
        return interKripke;
    }

    public Graphs[] getStepGraphs() {
        return stepGraphs;
    }

    public LabelHash getLabelHash() {
        return labelHash;
    }

}
