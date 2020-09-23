package controller.types.data;

import controller.types.ctl.Kripke;
import controller.types.graph.LabelHash;
import controller.types.graph.VertexList;

public class GraphsContent {

    private VertexList xmlVertList;
    private VertexList transVertList;
    private VertexList interVertList;
    private Kripke xmlKripke;
    private Kripke transKripke;
    private Kripke interKripke;
    private LabelHash labelHash;

    public GraphsContent(VertexList xmlVertList, VertexList transVertList, VertexList interVertList, Kripke xmlKripke, Kripke transKripke, Kripke interKripke, LabelHash labelHash) {
        this.xmlVertList = xmlVertList;
        this.transVertList = transVertList;
        this.interVertList = interVertList;
        this.xmlKripke = xmlKripke;
        this.transKripke = transKripke;
        this.interKripke = interKripke;
        this.labelHash = labelHash;
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

}
