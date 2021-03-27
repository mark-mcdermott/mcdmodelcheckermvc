package _options;

public class DirectedGraphOptions {

    private Integer nodeDiameter;
    private Integer level;
    private Integer numEdges;
    private Integer canvasWidth;
    private Integer canvasHeight;
    private Integer vertexSiblingOffset;
    private Integer vertexVertMultiplier;
    private Integer layoutWidth;
    private Integer layoutHeight;
    private Integer edgeXLength;
    private Integer edgeYLength;
    private Integer collisionXOffset;
    private Integer collisionYOffest;
    private float scaleFactor;
    private double vertexAttractionMultiplier;

    public DirectedGraphOptions(
            Integer nodeDiameter,
            Integer level,
            Integer numEdges,
            Integer canvasWidth,
            Integer canvasHeight,
            Integer vertexSiblingOffset,
            Integer vertexVertMultiplier,
            Integer layoutWidth,
            Integer layoutHeight,
            Integer edgeXLength,
            Integer edgeYLength,
            Integer collisionXOffset,
            Integer collisionYOffest,
            float scaleFactor,
            double vertexAttractionMultiplier
    ) {
        this.nodeDiameter = nodeDiameter;
        this.level = level;
        this.numEdges = numEdges;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.vertexSiblingOffset = vertexSiblingOffset;
        this.vertexVertMultiplier = vertexVertMultiplier;
        this.layoutWidth = layoutWidth;
        this.layoutHeight = layoutHeight;
        this.edgeXLength = edgeXLength;
        this.edgeYLength = edgeYLength;
        this.collisionXOffset = collisionXOffset;
        this.collisionYOffest = collisionYOffest;
        this.scaleFactor = scaleFactor;
        this.vertexAttractionMultiplier  = vertexAttractionMultiplier;
    }

    public Integer getLevel() {
        return level;
    }

    public Integer getNumEdges() {
        return numEdges;
    }

    public Integer getCanvasWidth() {
        return canvasWidth;
    }

    public Integer getCanvasHeight() {
        return canvasHeight;
    }

    public Integer getVertexSiblingOffset() {
        return vertexSiblingOffset;
    }

    public Integer getVertexVertMultiplier() {
        return vertexVertMultiplier;
    }

    public Integer getLayoutWidth() {
        return layoutWidth;
    }

    public Integer getLayoutHeight() {
        return layoutHeight;
    }

    public double getVertexAttractionMultiplier() {
        return vertexAttractionMultiplier;
    }

    public Integer getEdgeXLength() {
        return edgeXLength;
    }

    public Integer getEdgeYLength() {
        return edgeYLength;
    }

    public Integer getCollisionXOffset() {
        return collisionXOffset;
    }

    public Integer getCollisionYOffest() {
        return collisionYOffest;
    }

    public float getScaleFactor() {
        return scaleFactor;
    }

    public Integer getNodeDiameter() {
        return nodeDiameter;
    }

}
