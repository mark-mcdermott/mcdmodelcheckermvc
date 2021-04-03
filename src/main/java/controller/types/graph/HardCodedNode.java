package controller.types.graph;

import java.awt.geom.Point2D;

public class HardCodedNode {
    private String name;
    private Point2D.Double xy;

    public HardCodedNode(String name, Integer x, Integer y) {
        this.name = name;
        this.xy = new Point2D.Double(x,y);
    }

    public String getName() {
        return name;
    }

    public Point2D.Double getXy() {
        return xy;
    }

}
