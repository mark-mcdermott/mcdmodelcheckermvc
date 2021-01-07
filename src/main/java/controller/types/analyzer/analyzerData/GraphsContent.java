package controller.types.analyzer.analyzerData;

import controller.types.ctl.Kripke;
import controller.types.graph.LabelHash;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;

public class GraphsContent {

    private VertexList xmlVertList;
    private VertexList transVertList;
    private VertexList interVertList;
    private Kripke xmlKripke;
    private Kripke transKripke;
    private Kripke interKripke;
    private LabelHash labelHash;
    private Boolean isStepGraph;
    private Integer numSteps;
    private Integer[] steps;
    private Vertex[] states;
    private Double translationTime;
    private VertexList[] stepGraphs;    // only not null if isStepGraph is true.
                                        // stepGraphs is either null or has two vertexList elements in array -
                                        // first element is the vertexList for targetStep - 1
                                        // second element is the vertexList for targetstep
    private Integer numProps;

    public GraphsContent(VertexList xmlVertList, VertexList transVertList, VertexList interVertList, Kripke xmlKripke, Kripke transKripke, Kripke interKripke, LabelHash labelHash, Double translationTime) {
        this.xmlVertList = xmlVertList;
        this.transVertList = transVertList;
        this.interVertList = interVertList;
        this.xmlKripke = xmlKripke;
        this.transKripke = transKripke;
        this.interKripke = interKripke;
        this.labelHash = labelHash;
        this.isStepGraph = false;
        this.numSteps = null;
        this.stepGraphs = null;
        this.translationTime = translationTime;
    }

    public GraphsContent(VertexList xmlVertList, VertexList transVertList, Kripke xmlKripke, Kripke transKripke, LabelHash labelHash) {
        this.xmlVertList = xmlVertList;
        this.transVertList = transVertList;
        this.interVertList = interVertList;
        this.xmlKripke = xmlKripke;
        this.transKripke = transKripke;
        this.interKripke = interKripke;
        this.labelHash = labelHash;
        this.isStepGraph = false;
        this.numSteps = null;
        this.stepGraphs = null;
    }

    public GraphsContent(VertexList xmlVertList, LabelHash labelHash, VertexList[] stepGraphs) {
        this.xmlVertList = xmlVertList;
        this.transVertList = transVertList;
        this.interVertList = interVertList;
        this.xmlKripke = xmlKripke;
        this.transKripke = transKripke;
        this.interKripke = interKripke;
        this.labelHash = labelHash;
        this.isStepGraph = true;
        this.numSteps = null;
        this.stepGraphs = stepGraphs;
    }

    public GraphsContent(VertexList xmlVertList, LabelHash labelHash, Integer numSteps, VertexList[] stepGraphs) {
        this.xmlVertList = xmlVertList;
        this.transVertList = transVertList;
        this.interVertList = interVertList;
        this.xmlKripke = xmlKripke;
        this.transKripke = transKripke;
        this.interKripke = interKripke;
        this.labelHash = labelHash;
        this.isStepGraph = true;
        this.numSteps = numSteps;
        this.stepGraphs = stepGraphs;
    }

    public LabelHash getLabelHash() {
        return labelHash;
    }

    public Kripke getInterKripke() {
        return interKripke;
    }

    public Kripke getTransKripke() {
        return transKripke;
    }

    public Kripke getXmlKripke() {
        return xmlKripke;
    }

    public VertexList getInterVertList() {
        return interVertList;
    }

    public VertexList getTransVertList() {
        return transVertList;
    }

    public VertexList getXmlVertList() {
        return xmlVertList;
    }

    public void setLabelHash(LabelHash labelHash) {
        this.labelHash = labelHash;
    }

    public void setInterKripke(Kripke interKripke) {
        this.interKripke = interKripke;
    }

    public void setInterVertList(VertexList interVertList) {
        this.interVertList = interVertList;
    }

    public void setTransKripke(Kripke transKripke) {
        this.transKripke = transKripke;
    }

    public void setTransVertList(VertexList transVertList) {
        this.transVertList = transVertList;
    }

    public void setXmlKripke(Kripke xmlKripke) {
        this.xmlKripke = xmlKripke;
    }

    public void setXmlVertList(VertexList xmlVertList) {
        this.xmlVertList = xmlVertList;
    }

    public Boolean getIsStepGraph() {
        return isStepGraph;
    }

    public VertexList[] getStepGraphs() {
        return stepGraphs;
    }

    public VertexList getStepGraphSelectedStepMinusOne() {
        return stepGraphs[0];
    }

    public VertexList getStepGraphSelectedStep() {
        return stepGraphs[1];
    }

    public Integer getNumSteps() {
        return numSteps;
    }

    public Boolean getStepGraph() {
        return isStepGraph;
    }

    public Double getTranslationTime() {
        return translationTime;
    }

    public void setNumProps(Integer numProps) {
        this.numProps = numProps;
    }

    public Integer getNumProps() {
        return numProps;
    }
}
