package controller.analyzer.translationTemplates;

import controller.types.analyzer.TemplateSwapDetails;
import controller.types.ctl.Relation;
import controller.types.ctl.Label;
import controller.types.graph.Vertex;
import controller.types.graph.VertexKind;
import controller.types.graph.VertexList;
import controller.types.graph.VertexStatus;
import controller.utils.ListHelper;

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
        Integer kindNum = vertexToReplace.getKindNum();
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

        // TODO: test this more
        ArrayList<Vertex> syntheticChildren = (vertexToReplace.getSyntheticChildren() == null) ? null : vertexToReplace.getSyntheticChildren();

        ArrayList<Vertex> origChildrenCopy = new ListHelper().copyVertexArrList(origChildren);
        Integer origDistanceFromRoot = distanceFromRoot;
        Integer origSiblingNum = siblingNum;
        Boolean isRoot = false;
        Boolean isOriginal = false;
        Integer numChildren = (children == null) ? 0 : children.size();
        Vertex firstChild = (children == null || children.size() == 0) ? null : children.get(0);
        if (vertexToReplace.getParents() != null && vertexToReplace.getParents().size() > 0) {
            parents = vertexToReplace.getParents();
            parentSiblingNum = parents.get(0).getSiblingNum();
        } else {
            parentSiblingNum = 0;
            siblingNum = 0;
        }


        // create template vertices
        Vertex seqPosted = new Vertex(number, "s" + number.toString(), kind, kindNum, POSTED, blurb, properties, labels, null, null,
                distanceFromRoot, siblingNum, parentSiblingNum, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex seqStarted = new Vertex(++number, "s" + number, kind, kindNum, STARTED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex seqCompleted = new Vertex(++number, "s" + number, kind, kindNum, COMPLETED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex seqTerminated = new Vertex(++number, "s" + number, kind, kindNum, TERMINATED, blurb, null, null, null, null,
                distanceFromRoot, 1, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        // hook up first two template nodes to each other
        seqPosted.addChild(seqStarted);
        seqStarted.addParent(seqPosted);
        seqPosted.addRelation(new Relation(seqPosted, seqStarted));

        // get relations for hooking up children/parents to template vertices
        ArrayList<Relation> relationsToAdd = new ArrayList<>();
        ArrayList<Relation> relationsToRemove = new ArrayList<>();

        // hook up original children as the substeps (as in dr. p's sequential diagram seqStep.pdf)
        Integer numOrigChildren = null;
        if (origChildren != null) {
            numOrigChildren = origChildren.size();

            for (int i=0; i<numOrigChildren; i++) {
                // Vertex thisSubstep = origChildren.get(i);
                Vertex thisSubstep = origChildrenCopy.get(i);
                Vertex prevSubstep = (i == 0) ? null : origChildrenCopy.get(i - 1);
                thisSubstep.setParentSiblingNum(0);
                thisSubstep.setSiblingNum(0);

                // remove substep's original children (they will be dealt with recursively in future steps)
                if (thisSubstep.getChildren() != null && thisSubstep.getChildren().size() > 0) {
                    for (Vertex thisSubstepChild : thisSubstep.getChildren()) {
                        relationsToRemove.add(new Relation(thisSubstep,thisSubstepChild));
                    }
                }

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
        }

        // See if node to replace was originally hooked up to a terminated in the translatedVertexList. If so, hook seqTerminated up to it
        Vertex childTerminated = getChildTerminated(children);
        if (childTerminated != null) {
            relationsToAdd.add(new Relation(seqTerminated, childTerminated));
        }

        // hook up all children that aren't original (and aren't terminated) as children of seqCompleted (hook terminated up as child of seqTerminated)
        if (origChildrenCopy != null && children != null) {
            // ArrayList<Vertex> childrenNotOrig = getChildrenNotOrig(origChildrenCopy, children);
            // for (Vertex childNotOrig : childrenNotOrig) {
            for (Vertex syntheticChild : syntheticChildren) {
                // VertexStatus status = childNotOrig.getStatus();
                VertexStatus status = syntheticChild.getStatus();
                if (status != TERMINATED) {
                    // relationsToAdd.add(new Relation(seqCompleted, childNotOrig));
                    relationsToAdd.add(new Relation(seqCompleted, syntheticChild));
                } else {
                    // relationsToAdd.add(new Relation(seqTerminated, childNotOrig));
                    relationsToAdd.add(new Relation(seqTerminated, syntheticChild));
                }
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
        VertexList template = new VertexList(seqPosted, seqStarted, seqCompleted, seqTerminated, new VertexList(origChildrenCopy));

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
