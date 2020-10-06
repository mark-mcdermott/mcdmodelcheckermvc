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

public class SequentialTemplateOld {

    public ArrayList<Vertex> swapInSequentialTemplate(Vertex vertex, VertexList vertexList) {

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
        Integer numChildren;
        ArrayList<Vertex> origParents;
        ArrayList<Vertex> origChildren;
        Point2D xmlGraphPos;
        Point2D kripkeGraphPos;
        Boolean isRoot;
        Boolean isOriginal;
        Vertex seqPosted;
        Vertex seqStarted;
        Vertex seqCompleted;
        Vertex seqTerminated;

        number = new TranslateOld().getHighestVertexNum(vertexList);
        kind = (vertex.getKind() == null) ? null : vertex.getKind();
        blurb = (vertex.getBlurb() == null) ? null : vertex.getBlurb();
        properties = (vertex.getProperties() == null) ? null : vertex.getProperties();
        labels = (vertex.getLabels() == null) ? null : vertex.getLabels();
        parents = (vertex.getParents() == null) ? null : vertex.getParents();
        children = (vertex.getChildren() == null) ? null : vertex.getChildren();
        distanceFromRoot = (vertex.getDistanceFromRoot() == null) ? null : vertex.getDistanceFromRoot();
        siblingNum = (vertex.getSiblingNum() == null) ? null : vertex.getSiblingNum();
        parentSiblingNum = (vertex.getParentSiblingNum() == null) ? null : vertex.getParentSiblingNum();
        origNumber = number;
        origParents = (vertex.getOrigParents() == null) ? null : vertex.getOrigParents();
        origChildren = (vertex.getOrigChildren() == null) ? null : vertex.getOrigChildren();
        origDistanceFromRoot = distanceFromRoot;
        origSiblingNum = siblingNum;
        isRoot = false;
        isOriginal = false;

        seqPosted = new Vertex(number, "s" + number.toString(), kind, POSTED, blurb, properties, labels, null, null,
                distanceFromRoot, siblingNum, parentSiblingNum, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        seqStarted = new Vertex(++number, "s" + number, kind, STARTED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        seqCompleted = new Vertex(++number, "s" + number, kind, COMPLETED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        seqTerminated = new Vertex(++number, "s" + number, kind, TERMINATED, blurb, null, null, null, null,
                distanceFromRoot, 1, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);


        if (vertex.getParents() != null && vertex.getParents().size() > 0) {
            parents = vertex.getParents();
            parentSiblingNum = parents.get(0).getSiblingNum();
        } else {
            parentSiblingNum = 0;
            siblingNum = 0;
        }

        ArrayList<Vertex> templateArray = new ArrayList<>();
        templateArray.add(seqPosted);
        templateArray.add(seqStarted);
        templateArray.add(seqCompleted);
        templateArray.add(seqTerminated);

        seqPosted.setParentSiblingNum(parentSiblingNum);
        seqStarted.setParentSiblingNum(0);
        seqCompleted.setParentSiblingNum(0);
        seqTerminated.setParentSiblingNum(0);

        seqPosted.setSiblingNum(vertex.getSiblingNum());
        seqStarted.setSiblingNum(0);
        seqCompleted.setSiblingNum(0);
        seqTerminated.setSiblingNum(1);

        // label is carried to all template nodes except terminated
        if (vertex.getLabels() != null) {
            labels = vertex.getLabels();
            seqPosted.setLabels(labels);
            seqStarted.setLabels(labels);
            seqCompleted.setLabels(labels);
        }

        // hook up first two template nodes to each other
        seqPosted.addChild(seqStarted);
        seqStarted.addParent(seqPosted);
        seqPosted.addRelation(new Relation(seqPosted, seqStarted));

        // hook up any children to seqStarted & hook up seqComplted and seqTerminated
        numChildren = children.size();
        if (children != null && numChildren != null) {
            for (Integer i = 0; i < numChildren; i++) {
                if (i == 0) {
                    seqStarted.addChild(children.get(0));
                    children.get(i).addParent(seqStarted);
                    children.get(i).removeParent(vertex);
                    seqStarted.addRelation(new Relation(seqStarted, children.get(i)));
                } else {
                    children.get(i - 1).addChild(children.get(i));
                    children.get(i).addParent(children.get(i - 1));
                    children.get(i - 1).addRelation(new Relation(children.get(i - 1), children.get(i)));
                    children.get(i).removeParent(vertex);
                }
                if (i == numChildren - 1) {
                    children.get(i).addChild(seqCompleted);
                    seqCompleted.addParent(children.get(i));
                    children.get(i).addRelation(new Relation(children.get(i), seqCompleted));
                }
                children.get(i).setParentSiblingNum(0);
                children.get(i).setSiblingNum(0);
                if (children.get(i).getChildren() != null) {
                    children.get(i).getChildren().add(seqTerminated);
                }
                seqTerminated.addParent(children.get(i));
                children.get(i).addRelation(new Relation(children.get(i), seqTerminated));

            }
        }

        // set new root, if needed
        if (vertexList.getRoot() == vertex) vertexList.setRoot(seqPosted);

        // add template nodes to vertex list
        vertexList.addVertex(seqPosted);
        vertexList.addVertex(seqStarted);
        vertexList.addVertex(seqCompleted);
        vertexList.addVertex(seqTerminated);

        // don't remove original vertex - that's done in template.java (probably to avoid concurrent modification errors)
        vertexList.getList().remove(vertex);

        // hook up any parents to seqPosted
        if (parents != null) {
            for (Vertex parent : parents) {
                parent.addChild(seqPosted);
                parent.addRelation(new Relation(parent, seqPosted));
                parent.getChildren().remove(vertex);
            }
            seqPosted.setParents(parents);
        } else seqPosted.setIsRoot(true);

        vertex = seqPosted;

        return templateArray;
    }

}
