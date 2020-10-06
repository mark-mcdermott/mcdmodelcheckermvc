package controller.translationTemplates;

import controller.TranslateOld;
import controller.dataTypes.graphHelpers.VertexKind;
import controller.dataTypes.graphHelpers.VertexList;
import controller.dataTypes.graphItems.Label;
import controller.dataTypes.graphItems.Relation;
import controller.dataTypes.graphItems.Vertex;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.dataTypes.graphHelpers.VertexStatus.*;

public class LeafTemplateOld {

    // public Vertex leafTemplate(Vertex vertex, VertexList vertexList, Integer startingVertexNum) {
    public ArrayList<Vertex> leafTemplate(Vertex vertex, VertexList vertexList) {

        ArrayList<Vertex> leafTemplate;
        Integer number;
        VertexKind kind;
        String blurb;
        ArrayList<String> properties;
        ArrayList<Label> labels;
        ArrayList<Vertex> parents;
        ArrayList<Vertex> children;
        Integer distanceFromRoot;
        Integer siblingNum;
        Integer parentSiblingNum;
        Integer origNumber;
        Integer origDistanceFromRoot;
        Integer origSiblingNum;
        ArrayList<Vertex> origParents;
        ArrayList<Vertex> origChildren;
        Point2D xmlGraphPos;
        Point2D kripkeGraphPos;
        Boolean isRoot;
        Boolean isOriginal;
        Vertex leafPosted;
        Vertex leafStarted;
        Vertex leafCompleted;
        Vertex leafTerminated;

        number = new TranslateOld().getHighestVertexNum(vertexList) + 1;
        kind = (vertex.getKind() == null) ? null : vertex.getKind();
        blurb = (vertex.getBlurb() == null) ? null : vertex.getBlurb();
        properties = (vertex.getProperties() == null) ? null : vertex.getProperties();
        labels = (vertex.getLabels() == null) ? null : vertex.getLabels();
        parents = (vertex.getParents() == null) ? null : vertex.getParents();
        children = (vertex.getChildren() == null) ? null : vertex.getChildren();
        distanceFromRoot = (vertex.getDistanceFromRoot() == null) ? null : vertex.getDistanceFromRoot();
        siblingNum = (vertex.getSiblingNum() == null) ? null : vertex.getSiblingNum();
        parentSiblingNum = (vertex.getParentSiblingNum() == null) ? null : vertex.getParentSiblingNum();
        // origNumber = number;
        origNumber = vertex.getNumber();
        origParents = (vertex.getOrigParents() == null) ? null : vertex.getOrigParents();
        origChildren = (vertex.getOrigChildren() == null) ? null : vertex.getOrigChildren();
        origDistanceFromRoot = distanceFromRoot;
        origSiblingNum = siblingNum;
        isRoot = false;
        isOriginal = false;

        // create the substeps
        leafPosted = new Vertex(number, "s" + number.toString(), kind, POSTED, blurb, properties, labels, null, null,
                distanceFromRoot, siblingNum, parentSiblingNum, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        leafStarted = new Vertex(++number, "s" + number, kind, STARTED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        leafCompleted = new Vertex(++number, "s" + number, kind, COMPLETED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        leafTerminated = new Vertex(++number, "s" + number, kind, TERMINATED, blurb, null, null, null, null,
                distanceFromRoot, 1, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        // remove the orig vertex from its children and add the template vertices & relations
        if (children != null) {
            for (Vertex child : children) {
                child.addParent(leafCompleted);
                child.addParent(leafTerminated);
                child.removeParent(vertex);
                leafCompleted.addChild(child);
                leafCompleted.addRelation(new Relation(leafCompleted, child));
                leafTerminated.addChild(child);
                leafTerminated.addRelation(new Relation(leafTerminated, child));

            }
        }

        // mark which substeps are root/terminal
        leafPosted.setIsTemplateRoot(true);
        leafCompleted.setIsTemplateTerminal(true);
        leafTerminated.setIsTemplateTerminal(true);

        // hook up substeps to each other
        leafPosted.addChild(leafStarted);
        leafPosted.addRelation(new Relation(leafPosted, leafStarted));
        leafStarted.addChild(leafCompleted);
        leafStarted.addRelation(new Relation(leafStarted, leafCompleted));
        leafStarted.addChild(leafTerminated);
        leafStarted.addRelation(new Relation(leafStarted, leafTerminated));

        // set substeps' sibling numbers (for graph coordinate calculations)
        leafPosted.setSiblingNum(0);
        leafStarted.setSiblingNum(0);
        leafCompleted.setSiblingNum(0);
        leafTerminated.setSiblingNum(1);

        // set substeps' parent sibling nubmers
        leafPosted.setParentSiblingNum(vertex.getParentSiblingNum());
        leafStarted.setParentSiblingNum(0);
        leafCompleted.setParentSiblingNum(0);
        leafTerminated.setParentSiblingNum(0);

        // hook up substeps/relations to orig vertex's parents & add parents to template nodes
        leafPosted.setParents(parents);
        leafStarted.addParent(leafPosted);
        leafCompleted.addParent(leafStarted);
        leafTerminated.addParent(leafStarted);
        if (parents != null) {
            for (Vertex parent : parents) {
                parent.removeChild(vertex);
                parent.removeRelationByVertex(vertex);
                parent.addChild(leafPosted);
                parent.addRelation(new Relation(parent, leafPosted));
            }
        }

        if (children != null) {
            for (Vertex child : children) {
                leafCompleted.addChild(child);
                leafCompleted.addRelation(new Relation(leafCompleted, child));
                leafTerminated.addChild(child);
                leafTerminated.addRelation(new Relation(leafTerminated, child));
            }
        }


        // mark graph root in substeps, if exists
        if (vertexList.getRoot() == vertex) {
            vertexList.setRoot(leafPosted);
        }

        if (vertex.getLabels() != null) {
            labels = vertex.getLabels();
            leafPosted.setLabels(labels);
            leafStarted.setLabels(labels);
            leafCompleted.setLabels(labels);
        }

        // add substeps to a template array
        leafTemplate = new ArrayList<>();
        leafTemplate.add(leafPosted);
        leafTemplate.add(leafStarted);
        leafTemplate.add(leafCompleted);
        leafTemplate.add(leafTerminated);

        vertexList.addVertex(leafPosted);
        vertexList.addVertex(leafStarted);
        vertexList.addVertex(leafCompleted);
        vertexList.addVertex(leafTerminated);

        vertexList.removeVertexFromList(vertex);

        return leafTemplate;
    }


}
