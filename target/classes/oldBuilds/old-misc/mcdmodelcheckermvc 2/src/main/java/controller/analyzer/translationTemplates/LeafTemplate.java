package controller.analyzer.translationTemplates;

import controller.types.analyzer.TemplateSwapDetails;
import controller.types.ctl.Relation;
import controller.types.ctl.Label;
import controller.types.graph.Vertex;
import controller.types.graph.VertexKind;
import controller.types.graph.VertexList;

import java.util.ArrayList;

import static controller.types.graph.VertexStatus.*;
import static controller.utils.Utils.getHighestVertexNum;

public class LeafTemplate {

    TemplateSwapDetails templateSwapDetails;

    LeafTemplate() { }

    public LeafTemplate(Vertex vertexToReplace, VertexList vertexList) {

        // init vars
        Integer number = getHighestVertexNum(vertexList) + 1;
        VertexKind kind = (vertexToReplace.getKind() == null) ? null : vertexToReplace.getKind();
        String blurb = (vertexToReplace.getBlurb() == null) ? null : vertexToReplace.getBlurb();
        ArrayList<String> properties = (vertexToReplace.getProperties() == null) ? null : vertexToReplace.getProperties();
        ArrayList<Label> labels = (vertexToReplace.getLabels() == null) ? null : vertexToReplace.getLabels();
        ArrayList<Vertex> parents = (vertexToReplace.getParents() == null) ? null : vertexToReplace.getParents();
        ArrayList<Vertex>children = (vertexToReplace.getChildren() == null) ? null : vertexToReplace.getChildren();
        Integer distanceFromRoot = (vertexToReplace.getDistanceFromRoot() == null) ? null : vertexToReplace.getDistanceFromRoot();
        Integer siblingNum = (vertexToReplace.getSiblingNum() == null) ? null : vertexToReplace.getSiblingNum();
        Integer parentSiblingNum = (vertexToReplace.getParentSiblingNum() == null) ? null : vertexToReplace.getParentSiblingNum();
        Integer origNumber = vertexToReplace.getNumber();
        ArrayList<Vertex> origParents = (vertexToReplace.getOrigParents() == null) ? null : vertexToReplace.getOrigParents();
        ArrayList<Vertex> origChildren = (vertexToReplace.getOrigChildren() == null) ? null : vertexToReplace.getOrigChildren();
        Integer origDistanceFromRoot = distanceFromRoot;
        Integer origSiblingNum = siblingNum;
        Boolean isRoot = false;
        Boolean isOriginal = false;

        // create template vertices
        Vertex leafPosted = new Vertex(number, "s" + number.toString(), kind, POSTED, blurb, properties, labels, null, null,
                distanceFromRoot, siblingNum, parentSiblingNum, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex leafStarted = new Vertex(++number, "s" + number, kind, STARTED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex leafCompleted = new Vertex(++number, "s" + number, kind, COMPLETED, blurb, properties, labels, null, null,
                ++distanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex leafTerminated = new Vertex(++number, "s" + number, kind, TERMINATED, blurb, null, null, null, null,
                distanceFromRoot, 1, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        // hook up template nodes to each other
        leafPosted.addChild(leafStarted);
        leafStarted.addParent(leafPosted);
        leafPosted.addRelation(new Relation(leafPosted, leafStarted));
        leafStarted.addChild(leafCompleted);
        leafCompleted.addParent(leafStarted);
        leafStarted.addRelation(new Relation(leafStarted, leafCompleted));
        leafStarted.addChild(leafTerminated);
        leafTerminated.addParent(leafStarted);
        leafStarted.addRelation(new Relation(leafStarted, leafTerminated));
        // get relations for hooking up children/parents to template vertices
        ArrayList<Relation> relationsToAdd = new ArrayList<>();
        ArrayList<Relation> relationsToRemove = new ArrayList<>();
        // relations for hooking up children
        if (children != null) {
            for (Vertex child : children) {
                relationsToAdd.add(new Relation(leafCompleted, child));
                relationsToAdd.add(new Relation(leafTerminated, child));
                relationsToRemove.add(new Relation(vertexToReplace, child));
            }
        }
        // get relations for hooking up parents
        if (parents != null) {
            for (Vertex parent : parents) {
                relationsToAdd.add(new Relation(parent, leafPosted));
                relationsToRemove.add(new Relation(parent, vertexToReplace));
            }
        }

        // create template vertex list
        VertexList template = new VertexList(leafPosted, leafStarted, leafCompleted, leafTerminated);

        this.templateSwapDetails = new TemplateSwapDetails(template, vertexToReplace, relationsToAdd, relationsToRemove);

    }

    public TemplateSwapDetails getTemplateSwapDetails() {
        return templateSwapDetails;
    }

}
