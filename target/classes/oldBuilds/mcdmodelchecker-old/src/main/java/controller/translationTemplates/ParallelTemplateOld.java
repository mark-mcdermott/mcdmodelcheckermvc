package controller.translationTemplates;

import controller.Interleavings;
import controller.TranslateOld;
import controller.dataTypes.graphHelpers.VertexKind;
import controller.dataTypes.graphHelpers.VertexList;
import controller.dataTypes.graphItems.Label;
import controller.dataTypes.graphItems.Relation;
import controller.dataTypes.graphItems.Vertex;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.dataTypes.graphHelpers.VertexStatus.*;

public class ParallelTemplateOld {

    public ArrayList<Vertex> getParallelTemplate(Vertex vertex, VertexList vertexList, Boolean getInterleavings) {

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

        // parents = null;
        parentSiblingNum = null;
        siblingNum = null;
        if (vertex.getParents() != null) {
            parents = vertex.getParents();
            if (parents != null && parents.size() > 0) {
                parentSiblingNum = parents.get(0).getSiblingNum();
            } else {
                parentSiblingNum = 0;
            }
        } else {
            parentSiblingNum = 0;
            siblingNum = 0;
        }

        // origParents = (vertex.getParents() == null) ? null : vertex.getParents();
        // origChildren = (vertex.getChildren() == null) ? null : vertex.getChildren();
        Integer numOrigChildren = (origChildren == null) ? null : origChildren.size();
        Integer numChildren = (children == null) ? null : children.size();
        String name = vertex.getName();

        Vertex parPosted = new Vertex(number, "s" + number.toString(), kind, POSTED, blurb, properties, labels, null, null,
                distanceFromRoot, siblingNum, parentSiblingNum, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        Vertex parStarted = new Vertex(++number, "s" + number, kind, STARTED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        Vertex parCompleted = new Vertex(++number, "s" + number, kind, COMPLETED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        Vertex parTerminated = new Vertex(++number, "s" + number, kind, TERMINATED, blurb, null, null, null, null,
                distanceFromRoot, 1, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        parPosted.setParentSiblingNum(parentSiblingNum);
        parStarted.setParentSiblingNum(0);
        parCompleted.setParentSiblingNum(0);
        parTerminated.setParentSiblingNum(0);

        parPosted.setSiblingNum(vertex.getSiblingNum());
        parStarted.setSiblingNum(0);
        parCompleted.setSiblingNum(0);
        parTerminated.setSiblingNum(1);

        ArrayList<Vertex> templateArray = new ArrayList<>();
        templateArray.add(parPosted);
        templateArray.add(parStarted);
        templateArray.add(parCompleted);
        templateArray.add(parTerminated);

        parPosted.setIsTemplateRoot(true);
        parCompleted.setIsTemplateTerminal(true);
        parTerminated.setIsTemplateTerminal(true);

        if (vertex.getLabels() != null) {
            labels = vertex.getLabels();
            parPosted.setLabels(labels);
            parStarted.setLabels(labels);
            parCompleted.setLabels(labels);
        }

        parPosted.addChild(parStarted);
        parStarted.addParent(parPosted);

        // add relations
        parPosted.addRelation(new Relation(parPosted, parStarted));
        if (parents != null) {
            for (Vertex parent : parents) {
                parent.addRelation(new Relation(parent, parPosted));
            }
        }

        // if (children != null) {
        //     for (Vertex child : children) {
        //         parCompleted.addRelation(new Relation(parCompleted, child));
        //         parTerminated.addRelation(new Relation(parTerminated, child));
        //     }
        // }

        // normal flow without interleavings -
        // hook up children normally
        if (!getInterleavings) {
            if (children != null && numChildren != null) {
                for (Integer i = 0; i < numChildren; i++) {

                    Vertex thisChild = children.get(i);
                    thisChild.removeParent(vertex);

                    parStarted.addChild(thisChild);
                    parStarted.addRelation(new Relation(parStarted, thisChild));

                    thisChild.addParent(parStarted);
                    for (Integer j = 0; j < numChildren; j++) {
                        if (i != j) {
                            Vertex otherChild = children.get(j);
                            thisChild.addChild(otherChild);
                            thisChild.addRelation(new Relation(thisChild, otherChild));
                            otherChild.addParent(thisChild);
                            otherChild.addRelation(new Relation(otherChild, thisChild));
                        }
                    }
                    thisChild.setParentSiblingNum(0);
                    thisChild.setSiblingNum(i);
                    thisChild.addChild(parCompleted);
                    thisChild.getChildren().add(parTerminated);

                    parCompleted.addParent(thisChild);
                    thisChild.addRelation(new Relation(thisChild, parCompleted));
                    parTerminated.addParent(thisChild);
                    thisChild.addRelation(new Relation(thisChild, parTerminated));
                }

            } else {
                parStarted.addChild(parCompleted);
                parStarted.addChild(parTerminated);
                parStarted.addRelation(new Relation(parStarted, parCompleted));
                parStarted.addRelation(new Relation(parStarted, parTerminated));
                parCompleted.addParent(parStarted);
                parTerminated.addParent(parStarted);
            }

            // if interleavings, instead of hooking up children normally,
            // get all permutations and hook those up instead
        } else if (getInterleavings) {
            ArrayList<ArrayList<Vertex>> permutations = new Interleavings().getOrigChildrenInterleavings(origChildren);

            // remove original children
            for (Vertex child : origChildren) {
                vertex.removeChild(child);
                vertex.removeRelationByVertex(child);
                vertexList.removeVertexFromList(child);
            }

            // attach interleavings back to the parallel template nodes
            for (ArrayList<Vertex> thisPermutation : permutations) {

                // attach the first of each permutation as child of parStarted
                Vertex firstInThisPermutation = thisPermutation.get(0);
                parStarted.addChild(firstInThisPermutation);
                parStarted.addRelation(new Relation(parStarted, firstInThisPermutation));
                firstInThisPermutation.addParent(parStarted);

                // add all permutation vertices to vertexList
                int numInPermutation = thisPermutation.size();
                for (int i=0; i<numInPermutation; i++) {
                    Vertex thisVertex = thisPermutation.get(i);
                    thisVertex.addChild(parTerminated);
                    thisVertex.addRelation(new Relation(thisVertex, parTerminated));
                    if (i == numInPermutation - 1) {
                        thisVertex.addChild(parCompleted);
                        thisVertex.addRelation(new Relation(thisVertex, parCompleted));
                    }
                    vertexList.addVertex(thisVertex);
                }

            }

        }

        vertexList.addVertex(parPosted);
        vertexList.addVertex(parStarted);
        vertexList.addVertex(parCompleted);
        vertexList.addVertex(parTerminated);

        if (vertexList.getRoot() == vertex) vertexList.setRoot(parPosted);

        if (parents != null) {
            for (Vertex parent : parents) {
                parent.addChild(parPosted);
                parent.addRelation(new Relation(parent, parPosted));
                parPosted.addParent(parent);
                parent.getChildren().remove(vertex);
            }
        } else parPosted.setIsRoot(true);

        return templateArray;

    }

}
