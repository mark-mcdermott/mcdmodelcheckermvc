package controller.analyzer.translationTemplates;

import controller.types.analyzer.TemplateSwapDetails;
import controller.types.ctl.Relation;
import controller.types.ctl.Label;
import controller.types.graph.Vertex;
import controller.types.graph.VertexKind;
import controller.types.graph.VertexList;
import controller.types.graph.VertexStatus;

import java.util.ArrayList;

import static controller.types.graph.VertexStatus.*;
import static controller.utils.Utils.getHighestVertexNum;

public class SequentialTemplate {

    TemplateSwapDetails templateSwapDetails;

    SequentialTemplate() { }

    public SequentialTemplate(Vertex vertexToReplace, VertexList vertexList) {

        // init vars
        Integer number = getHighestVertexNum(vertexList);
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
        /*
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
        } */



        // hook up original children as the substeps (as in the sequential diagram)
        int numOrigChildren = origChildren.size();

        for (int i=0; i<numOrigChildren; i++) {
            Vertex thisSubstep = origChildren.get(i);
            Vertex prevSubstep = (i == 0) ? null : origChildren.get(i - 1);
            thisSubstep.setParentSiblingNum(0);
            thisSubstep.setSiblingNum(0);

            if (i == 0) { // first substep hooked up as a child of seqStarted
                relationsToAdd.add(new Relation(seqStarted, thisSubstep));
                relationsToRemove.add(new Relation(vertexToReplace, thisSubstep));
            } else { // all substeps but first are hooked up as a child of the previous substep
                relationsToAdd.add(new Relation(prevSubstep, thisSubstep));
                relationsToRemove.add(new Relation(vertexToReplace, thisSubstep));
            }
            if (i == numOrigChildren - 1) { // last substep hooks up to seqCompleted
                relationsToAdd.add(new Relation(thisSubstep, seqCompleted));
            }
            relationsToAdd.add(new Relation(thisSubstep, seqTerminated)); // all substeps hook up to seqTerminated
        }

        // See if node to replace was originally hooked up to a terminated. If so, hook seqTerminated up to it
        Vertex childTerminated = getChildTerminated(children);
        if (childTerminated != null) {
            relationsToAdd.add(new Relation(seqTerminated, childTerminated));
        }

        // hook up all children that aren't original (and aren't terminated) as children of seqCompleted (hook terminated up as child of seqTerminated)
        ArrayList<Vertex> childrenNotOrig = getChildrenNotOrig(origChildren, children);
        for (Vertex childNotOrig : childrenNotOrig) {
            VertexStatus status = childNotOrig.getStatus();
            if (status != TERMINATED) {
                relationsToAdd.add(new Relation(seqCompleted, childNotOrig));
            } else {
                relationsToAdd.add(new Relation(seqTerminated, childNotOrig));
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


    // see if one of the children is a terminated
    private Vertex getChildTerminated(ArrayList<Vertex> children) {
        Vertex terminatedChild = null;
        int numChildren = children.size();
        for (int i = 0; i < numChildren; i++) {
            Vertex child = children.get(i);
            VertexStatus childStatus = child.getStatus();
            if (childStatus == TERMINATED) {
                terminatedChild = child;
            }
        }
        return terminatedChild;
    }

    public TemplateSwapDetails getTemplateSwapDetails() {
        return templateSwapDetails;
    }

    private ArrayList<Vertex> getChildrenNotOrig(ArrayList<Vertex> origChildren, ArrayList<Vertex> children) {
        if (children == null) { return null; }
        ArrayList<Vertex> childrenNotOrig = new ArrayList<>();
        for (Vertex child : children) {
            Boolean found = false;
            for (Vertex origChild : origChildren) {
                if (origChild == child) {
                    found = true;
                }
            }
            if (!found) {
                childrenNotOrig.add(child);
            }
        }
        return childrenNotOrig;
    }

}
