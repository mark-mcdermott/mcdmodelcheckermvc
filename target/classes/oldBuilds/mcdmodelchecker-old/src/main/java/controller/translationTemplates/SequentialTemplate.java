package controller.translationTemplates;

import controller.TranslateOld;
import controller.dataTypes.graphHelpers.TemplateSwapDetails;
import controller.dataTypes.graphHelpers.VertexKind;
import controller.dataTypes.graphHelpers.VertexList;
import controller.dataTypes.graphItems.Label;
import controller.dataTypes.graphItems.Relation;
import controller.dataTypes.graphItems.Vertex;

import java.util.ArrayList;

import static controller.dataTypes.graphHelpers.VertexStatus.*;
import static controller.dataTypes.graphHelpers.VertexStatus.TERMINATED;

public class SequentialTemplate {

    TemplateSwapDetails templateSwapDetails;

    SequentialTemplate() { }

    public SequentialTemplate(Vertex vertexToReplace, VertexList vertexList) {

        // init vars
        Integer number = new TranslateOld().getHighestVertexNum(vertexList);
        VertexKind kind = (vertexToReplace.getKind() == null) ? null : vertexToReplace.getKind();
        String blurb = (vertexToReplace.getBlurb() == null) ? null : vertexToReplace.getBlurb();
        ArrayList<String> properties = (vertexToReplace.getProperties() == null) ? null : vertexToReplace.getProperties();
        ArrayList<Label> labels = (vertexToReplace.getLabels() == null) ? null : vertexToReplace.getLabels();
        ArrayList<Vertex> parents = (vertexToReplace.getParents() == null) ? null : vertexToReplace.getParents();
        ArrayList<Vertex> children = (vertexToReplace.getChildren() == null) ? null : vertexToReplace.getChildren();
        Integer distanceFromRoot = (vertexToReplace.getDistanceFromRoot() == null) ? null : vertexToReplace.getDistanceFromRoot();
        Integer siblingNum = (vertexToReplace.getSiblingNum() == null) ? null : vertexToReplace.getSiblingNum();
        Integer parentSiblingNum = (vertexToReplace.getParentSiblingNum() == null) ? null : vertexToReplace.getParentSiblingNum();
        Integer origNumber = number;
        ArrayList<Vertex> origParents = (vertexToReplace.getOrigParents() == null) ? null : vertexToReplace.getOrigParents();
        ArrayList<Vertex> origChildren = (vertexToReplace.getOrigChildren() == null) ? null : vertexToReplace.getOrigChildren();
        Integer origDistanceFromRoot = distanceFromRoot;
        Integer origSiblingNum = siblingNum;
        Boolean isRoot = false;
        Boolean isOriginal = false;
        Integer numChildren = children.size();
        Vertex firstChild = children.get(0);
        if (vertexToReplace.getParents() != null && vertexToReplace.getParents().size() > 0) {
            parents = vertexToReplace.getParents();
            parentSiblingNum = parents.get(0).getSiblingNum();
        } else {
            parentSiblingNum = 0;
            siblingNum = 0;
        }

        // create template vertices
        Vertex seqPosted = new Vertex(number, "s" + number.toString(), kind, POSTED, blurb, properties, labels, null, null,
                distanceFromRoot, siblingNum, parentSiblingNum, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex seqStarted = new Vertex(++number, "s" + number, kind, STARTED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex seqCompleted = new Vertex(++number, "s" + number, kind, COMPLETED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex seqTerminated = new Vertex(++number, "s" + number, kind, TERMINATED, blurb, null, null, null, null,
                distanceFromRoot, 1, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        // hook up first two template nodes to each other
        seqPosted.addChild(seqStarted);
        seqStarted.addParent(seqPosted);
        seqPosted.addRelation(new Relation(seqPosted, seqStarted));

        // get relations for hooking up children/parents to template vertices
        ArrayList<Relation> relationsToAdd = new ArrayList<>();
        ArrayList<Relation> relationsToRemove = new ArrayList<>();

        // TODO: there is a judgement call here:
        // if the child of a sequential is a sequential, then the order that the parent
        // sequential's completed and terminal steps attach to the child sequential matters
        // in whether of the parent's completed or terminal nodes have the child's
        // completed step as their child (only the later one will)
        // this is demonstrated in the 4th translation step of FiveSteps.ljs
        // TODO: ask Dr. Podorozhny about which order to do them (then add that logic here)
        if (children != null && numChildren != null) {
            for (Integer i = 0; i < numChildren; i++) {
                Vertex thisChild = children.get(i);
                Vertex prevChild = (i == 0) ? null : children.get(i - 1);
                thisChild.setParentSiblingNum(0);
                thisChild.setSiblingNum(0);
                if (i == 0) {
                    relationsToAdd.add(new Relation(seqStarted, firstChild));
                    relationsToRemove.add(new Relation(vertexToReplace, thisChild));
                } else {
                    relationsToAdd.add(new Relation(prevChild, thisChild));
                    relationsToRemove.add(new Relation(vertexToReplace, thisChild));
                }
                if (i == numChildren - 1) {
                    relationsToAdd.add(new Relation(thisChild, seqCompleted));
                }
                // adding terminated node here messes up the order - does not match official sequential diagram
                // relationsToAdd.add(new Relation(thisChild, seqTerminated));
            }
            // adding terminated node here should fix the order - children will precede the terminated node, like the official sequential diagram
            for (Integer i = 0; i < numChildren; i++) {
                Vertex thisChild = children.get(i);
                relationsToAdd.add(new Relation(thisChild, seqTerminated));
            }
        }

        // get relations for hooking up parents to template vertices
        if (parents != null) {
            for (Vertex parent : parents) {
                relationsToAdd.add(new Relation(parent, seqPosted));
                relationsToRemove.add(new Relation(parent, vertexToReplace));
            }
        }

        // create template vertex list
        VertexList template = new VertexList(seqPosted, seqStarted, seqCompleted, seqTerminated);

        this.templateSwapDetails = new TemplateSwapDetails(template, vertexToReplace, relationsToAdd, relationsToRemove);

    }

    public TemplateSwapDetails getTemplateSwapDetails() {
        return templateSwapDetails;
    }
}
