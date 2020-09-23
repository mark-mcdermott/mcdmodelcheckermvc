package controller.types.ctl;

import controller.types.graph.Vertex;

public class Relation {

    Vertex fromVertex;
    Vertex toVertex;

    public Relation(Vertex from, Vertex to) {
        fromVertex = from;
        toVertex = to;
    }

    public void printRelation() {
        System.out.print("(s" + fromVertex.getNumber() + ",s" + toVertex.getNumber() + ")");
    }

    public String toString() {
        return "(s" + fromVertex + ",s" + toVertex + ")";
    }

    public Vertex getFromVertex() {
        return fromVertex;
    }

    public Vertex getToVertex() {
        return toVertex;
    }

    public void setFromVertex(Vertex fromVertex) { this.fromVertex = fromVertex; }

    public void setToVertex(Vertex toVertex) { this.toVertex = toVertex; }
}
