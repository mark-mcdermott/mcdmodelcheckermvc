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

public class TryTemplate {

    TemplateSwapDetails templateSwapDetails;

    public TryTemplate() { }

    public TryTemplate(Vertex vertexToReplace, VertexList translatedVertexList, VertexList originalVertexList) {

        // init vars
        Integer number = getHighestVertexNum(translatedVertexList);
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
        Vertex tryPosted = new Vertex(number, "s" + number.toString(), kind, kindNum, POSTED, blurb, properties, labels, null, null,
                distanceFromRoot, siblingNum, parentSiblingNum, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex tryStarted = new Vertex(++number, "s" + number, kind, kindNum, STARTED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex tryCompleted = new Vertex(++number, "s" + number, kind, kindNum, COMPLETED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex tryTerminated = new Vertex(++number, "s" + number, kind, kindNum, TERMINATED, blurb, null, null, null, null,
                distanceFromRoot, 1, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        // hook up first two template nodes to each other
        tryPosted.addChild(tryStarted);
        tryStarted.addParent(tryPosted);
        tryPosted.addRelation(new Relation(tryPosted, tryStarted));

        // get relations for hooking up children/parents to template vertices
        ArrayList<Relation> relationsToAdd = new ArrayList<>();
        ArrayList<Relation> relationsToRemove = new ArrayList<>();

        // hook up original children as substeps
        if (origChildren != null && origChildren.size() > 0) {
            for (Integer i = 0; i < numChildren; i++) {
                Vertex thisChild = origChildren.get(i);
                Vertex prevChild = (i == 0) ? null : origChildren.get(i - 1);
                thisChild.setParentSiblingNum(0);
                thisChild.setSiblingNum(0);
                if (i == 0) {
                    relationsToAdd.add(new Relation(tryStarted, firstChild));
                    relationsToRemove.add(new Relation(vertexToReplace, thisChild));
                } else {
                    relationsToAdd.add(new Relation(prevChild, thisChild));
                    relationsToRemove.add(new Relation(vertexToReplace, thisChild));
                }
                if (i == numChildren - 1) {
                    relationsToAdd.add(new Relation(thisChild, tryTerminated));
                }
                relationsToAdd.add(new Relation(thisChild, tryCompleted));
            }
        }

        // get relations for hooking up parents to template vertices
        if (parents != null) {
            for (Vertex parent : parents) {
                relationsToAdd.add(new Relation(parent, tryPosted));
                relationsToRemove.add(new Relation(parent, vertexToReplace));
            }
        }

        // hook up children from translatedVertexList
        for (Vertex child : children) {
            VertexStatus status = child.getStatus();
            if (status == TERMINATED) {
                relationsToAdd.add(new Relation(tryTerminated,child));
            } else {
                relationsToAdd.add(new Relation(tryCompleted, child));
            }

        }

        // create template vertex list
        VertexList template = new VertexList(tryPosted, tryStarted, tryCompleted, tryTerminated);

        this.templateSwapDetails = new TemplateSwapDetails(template, vertexToReplace, relationsToAdd, relationsToRemove);

    }

    public TemplateSwapDetails getTemplateSwapDetails() {
        return templateSwapDetails;
    }

}
