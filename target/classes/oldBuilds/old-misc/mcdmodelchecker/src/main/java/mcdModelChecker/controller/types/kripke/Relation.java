package mcdModelChecker.controller.types.kripke;

import mcdModelChecker.controller.types.vertex.Vertex;

/**
 * A relation is set of vertices, a from vertex and a to vertex - a relation is essentially an edge in a edge/node graph
 * A set of relations a part of every <a href="https://en.wikipedia.org/wiki/Kripke_structure_(model_checking)">Kripke structure</a>
 * The relations are the <code>R</code> in the Kripke's <code>M = (S, I, R, L)</code> structure.
 */
public class Relation {

    Vertex fromVertex;
    Vertex toVertex;

    /**
     * Sole constructor, every relation is just a from vertex and a to vertex
     *
     * @param from  fromVertex in (fromVertex, toVertex) pair. Represents the vertex an graph edge transition begins at.
     * @param to    toVertex in (fromVertex, toVertex) pair. Represents the vertex an graph edge transition ends at.
     */
    public Relation(Vertex from, Vertex to) {
        fromVertex = from;
        toVertex = to;
    }

    /**
     * Prints a set of vertices in a relation, for example like <code>(s0,s1)</code>
     */
    public void printRelation() {
        System.out.print("(s" + fromVertex.getNumber() + ",s" + toVertex.getNumber() + ")");
    }

    /**
     *
     * @return returns a String of a set of vertices in a relation, for example like <code>(s0,s1)</code>
     */
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
