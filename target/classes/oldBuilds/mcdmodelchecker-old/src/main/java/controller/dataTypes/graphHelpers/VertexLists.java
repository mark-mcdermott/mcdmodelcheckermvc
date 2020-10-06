package controller.dataTypes.graphHelpers;

public class VertexLists {

    private VertexList xmlVertexList;
    private VertexList translationVertexList;
    private VertexList interleavingsVertexList;


    public VertexLists(VertexList xmlVertexList, VertexList translationVertexList, VertexList interleavingsVertexList) {
        this.xmlVertexList = xmlVertexList;
        this.translationVertexList = translationVertexList;
        this.interleavingsVertexList = interleavingsVertexList;
    }

    public VertexList getXmlVertexList() {
        return xmlVertexList;
    }

    public VertexList getInterleavingsVertexList() {
        return interleavingsVertexList;
    }

    public VertexList getTranslationVertexList() {
        return translationVertexList;
    }

}
