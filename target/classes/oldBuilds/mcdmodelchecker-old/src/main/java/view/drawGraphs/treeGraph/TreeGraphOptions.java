package view.drawGraphs.treeGraph;

public class TreeGraphOptions {

    private int edgeXLength;
    private int edgeYLength;
    private int nodeDiameter;
    private int layoutWidth;
    private int layoutHeight;
    private float scaleFactor;

    public TreeGraphOptions(
            int edgeXLength,
            int edgeYLength,
            int nodeDiameter,
            int layoutWidth,
            int layoutHeight,
            float scaleFactor
    ) {
        this.edgeXLength = edgeXLength;
        this.edgeYLength = edgeYLength;
        this.nodeDiameter = nodeDiameter;
        this.layoutWidth = layoutWidth;
        this.layoutHeight = layoutHeight;
        this.scaleFactor = scaleFactor;
    }



    public int getEdgeXLength() {
        return edgeXLength;
    }

    public void setEdgeXLength(int edgeXLength) {
        this.edgeXLength = edgeXLength;
    }

    public int getEdgeYLength() {
        return edgeYLength;
    }

    public void setEdgeYLength(int edgeYLength) {
        this.edgeYLength = edgeYLength;
    }

    public int getNodeDiameter() {
        return nodeDiameter;
    }

    public void setNodeDiameter(int nodeDiameter) {
        this.nodeDiameter = nodeDiameter;
    }

    public int getLayoutWidth() {
        return layoutWidth;
    }

    public int getLayoutHeight() {
        return layoutHeight;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }


}
