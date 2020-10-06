package controller.dataTypes.graphHelpers;

import controller.dataTypes.graphItems.Relation;
import controller.dataTypes.graphItems.Vertex;

import java.util.ArrayList;

public class TemplateSwapDetails {

    VertexList template;
    Vertex vertexToReplace;
    ArrayList<Relation> relationsToAdd;
    ArrayList<Relation> relationsToRemove;
    ArrayList<Vertex> origVerticesPermuted; // (these get removed and references to them are moved to their permuted equivalents)

    public TemplateSwapDetails() { }

    public TemplateSwapDetails(VertexList template, Vertex vertexToReplace, ArrayList<Relation> relationsToAdd, ArrayList<Relation> relationsToRemove) {
        this.template = template;
        this.vertexToReplace = vertexToReplace;
        this.relationsToAdd = relationsToAdd;
        this.relationsToRemove = relationsToRemove;
    }

    public TemplateSwapDetails(VertexList template, Vertex vertexToReplace, ArrayList<Relation> relationsToAdd, ArrayList<Relation> relationsToRemove, ArrayList<Vertex> origVerticesPermuted) {
        this.template = template;
        this.vertexToReplace = vertexToReplace;
        this.relationsToAdd = relationsToAdd;
        this.relationsToRemove = relationsToRemove;
        this.origVerticesPermuted = origVerticesPermuted;
    }

    public ArrayList<Relation> getRelationsToAdd() {
        return relationsToAdd;
    }

    public ArrayList<Relation> getRelationsToRemove() {
        return relationsToRemove;
    }

    public Vertex getVertexToReplace() {
        return vertexToReplace;
    }

    public VertexList getTemplate() {
        return template;
    }

    public ArrayList<Vertex> getOrigVerticesPermuted() {
        return origVerticesPermuted;
    }

    public void setOrigVerticesPermuted(ArrayList<Vertex> origVerticesPermuted) {
        this.origVerticesPermuted = origVerticesPermuted;
    }

}
