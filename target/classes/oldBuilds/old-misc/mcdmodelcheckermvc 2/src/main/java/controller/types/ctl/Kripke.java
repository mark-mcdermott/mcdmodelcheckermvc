package controller.types.ctl;

import controller.types.graph.Vertex;
import controller.types.graph.VertexList;

import java.util.ArrayList;
import java.util.Comparator;

public class Kripke {

    public ArrayList<Vertex> S = new ArrayList<Vertex>();			// set of Vertices
    public ArrayList<Relation> R = new ArrayList<Relation>();	    // set of relations
    public ArrayList<Vertex> I = new ArrayList<Vertex>();			// initial Vertex(s)
    public ArrayList<Vertex> L = new ArrayList<Vertex>();		    // set of Vertices with labels
    public Vertex root;                                             // if only one initial Vertices, root = initial

    public Kripke() { }

    public Kripke(VertexList vertexList) {
        vertexList.getList().sort(Comparator.comparing(Vertex::getNumber));  // from https://stackoverflow.com/a/2784576
        for (Vertex vertex : vertexList.getList()) {
            S.add(vertex);
            if (vertex.getRelations() != null) {
                for (Integer i = 0; i < vertex.getRelations().size(); i++) {
                    R.add(vertex.getRelations().get(i));
                }
            }
            if (vertex.getIsRoot() != null && vertex.getIsRoot()) {
                I.add(vertex);
            }
            if (vertex.getLabels() != null && vertex.getLabels().size() != 0) {
                L.add(vertex);
            }
        }
    }

    public String toString() {
        String verticesString = "";
        String relationsString = "";
        String labelsString = "";
        String resultString = "";

        // Vertices
        verticesString = verticesString + "W={";
        for (int i=0; i<S.size(); i++) {
            verticesString = verticesString + S.get(i).getName();
            if (i != S.size() - 1) {
                verticesString = verticesString + ",";
            }
        }
        verticesString = verticesString + "};\n";

        // relations
        relationsString = relationsString + "R={";
        for (int i=0; i<R.size(); i++) {
            relationsString = relationsString + "(" + R.get(i).getFromVertex().getName() + "," + R.get(i).getToVertex().getName() + ")";
            if (i != R.size() - 1) {
                relationsString = relationsString + ",";
            }
        }
        relationsString = relationsString + "};\n";

        // labels
        for (int i=0; i<L.size(); i++) {
            labelsString = labelsString + "L(" + L.get(i).getName() + ")={";
            for (int j=0; j<L.get(i).getLabels().size(); j++) {
                labelsString = labelsString + L.get(i).getLabels().get(j).getLabel();
                if (j != L.get(i).getLabels().size() - 1) {
                    labelsString = labelsString + ",";
                }
            }
            labelsString = labelsString + "}";
            if (i != L.size() - 1) {
                labelsString = labelsString + ",";
            }
        }
        labelsString = labelsString + ";";

        resultString = verticesString + relationsString + labelsString;
        return resultString;
    }

    public String[] toMultiLineString() {
        return this.toString().split("\n");
    }

    public String toDescriptiveKripke() {
        String kripkeString = new String();
        Vertex fromVertex = R.get(0).getFromVertex();
        String fromVertexName = fromVertex.toShortString();
        for (int i=0; i<R.size(); i++) {
            Vertex currentFromVertex = R.get(i).getFromVertex();
            Vertex toVertex = R.get(i).getToVertex();
            String toVertexName = toVertex.toShortString();
            // if (currentFromVertex.equals(fromVertex)) {
            if (currentFromVertex.hasSamePropsAs(fromVertex)) {
                if (i != 0) {
                    kripkeString = kripkeString + ",";
                }
                kripkeString = kripkeString + fromVertexName + "->" + toVertexName;
            } else {
                fromVertex = R.get(i).getFromVertex();
                fromVertexName = fromVertex.toShortString();
                kripkeString = kripkeString + "\n";
                kripkeString = kripkeString + fromVertexName + "->" + toVertexName;
            }
        }
        return kripkeString;
    }

    public String toDescriptiveKripkeWithNodeNumbers() {
        String kripkeString = new String();
        Vertex fromVertex = R.get(0).getFromVertex();
        String fromVertexName = fromVertex.toShortStringWithNodeNumber();
        for (int i=0; i<R.size(); i++) {
            Vertex currentFromVertex = R.get(i).getFromVertex();
            Vertex toVertex = R.get(i).getToVertex();
            String toVertexName = toVertex.toShortStringWithNodeNumber();
            // if (currentFromVertex.equals(fromVertex)) {
            if (currentFromVertex.hasSamePropsAs(fromVertex)) {
                if (i != 0) {
                    kripkeString = kripkeString + ",";
                }
                kripkeString = kripkeString + fromVertexName + "->" + toVertexName;
            } else {
                fromVertex = R.get(i).getFromVertex();
                fromVertexName = fromVertex.toShortStringWithNodeNumber();
                kripkeString = kripkeString + "\n";
                kripkeString = kripkeString + fromVertexName + "->" + toVertexName;
            }
        }
        return kripkeString;
    }

}
