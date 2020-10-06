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

public class ChoiceTemplate {

    TemplateSwapDetails templateSwapDetails;

    public ChoiceTemplate() { }

    public ChoiceTemplate(Vertex vertexToReplace, VertexList vertexList) {

        // init vars
        ArrayList<Vertex> templateArrList = new ArrayList<>();
        // Integer number = new TranslateOld().getHighestVertexNum(vertexList) + 1;
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

        int numChildren = children.size();
        int startedDistanceFromRoot = distanceFromRoot + 1;
        int completedDistanceFromRoot = startedDistanceFromRoot + 3;
        int terminatedDistanceFromRoot = startedDistanceFromRoot + numChildren + 1;

        // create posted & started template vertices
        Vertex choicePosted = new Vertex(number, "s" + number.toString(), kind, POSTED, blurb, properties, labels, null, null,
                distanceFromRoot, siblingNum, parentSiblingNum, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex choiceStarted = new Vertex(++number, "s" + number, kind, STARTED, blurb, properties, labels, null, null,
                startedDistanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex choiceCompleted = new Vertex(++number, "s" + number, kind, COMPLETED, blurb, properties, labels, null, null,
                completedDistanceFromRoot, 0, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);
        Vertex choiceTerminated = new Vertex(++number, "s" + number, kind, TERMINATED, blurb, null, null, null, null,
                terminatedDistanceFromRoot, 1, 0, origNumber, origParents, origChildren,
                origDistanceFromRoot, origSiblingNum, isRoot, isOriginal);

        // add posted & started to the template array list
        templateArrList.add(choicePosted);
        templateArrList.add(choiceStarted);

        // hook up template nodes to each other
        choicePosted.addChild(choiceStarted);
        choiceStarted.addParent(choicePosted);
        choicePosted.addRelation(new Relation(choicePosted, choiceStarted));

        // init arr lists for substeps, substepsNotStarted & substepsHasStarted
        ArrayList<Vertex> substeps = new ArrayList<>();
        ArrayList<Vertex> substepsNotStarted = new ArrayList<>();
        ArrayList<Vertex> substepsHasStarted = new ArrayList<>();

        // init array lists for adding & removing relations
        ArrayList<Relation> relationsToAdd = new ArrayList<>();
        ArrayList<Relation> relationsToRemove = new ArrayList<>();

        // add relations for hooking up children
        if (children != null) {

            int notStartedDistanceFromRoot = choiceStarted.getDistanceFromRoot() + 1;
            int substepDistanceFromRoot = notStartedDistanceFromRoot + 1;

            for (int i=0; i<children.size(); i++) {
                int lastIndex = children.size() - 1;
                Vertex thisChild = children.get(i);
                int ithChild = i + 1;
                int substepNum = ithChild;
                // int thisDistanceFromRoot = choiceStarted.getDistanceFromRoot() + ithChild;
                String thisBlurb = thisChild.getBlurb();
                ArrayList<String> thisProperties = thisChild.getProperties();
                ArrayList<Label> thisLabels = thisChild.getLabels();

                int hasStartedDistanceFromRoot = choiceStarted.getDistanceFromRoot() + ithChild;

                // set substep properties
                Vertex thisSubstep = thisChild;
                thisSubstep.setSubstepNum(substepNum);
                thisSubstep.setDistanceFromRoot(substepDistanceFromRoot);
                thisSubstep.setStatus(SUBSTEP);

                // create hasStarted and hasNotStarted substeps
                Vertex thisSubstepNotStarted = new Vertex(++number, "s" + number, kind, SUBSTEP_HAS_NOT_STARTED, thisBlurb, thisProperties, thisLabels, null, null,
                        notStartedDistanceFromRoot, ithChild, 0, origNumber, origParents, origChildren,
                        origDistanceFromRoot, origSiblingNum, false, false, substepNum);
                Vertex thisSubstepHasStarted = new Vertex(++number, "s" + number, kind, SUBSTEP_HAS_STARTED, thisBlurb, thisProperties, thisLabels, null, null,
                        hasStartedDistanceFromRoot, ithChild, 0, origNumber, origParents, origChildren,
                        origDistanceFromRoot, origSiblingNum, false, false, substepNum);

                // add new vertices to their respective arrays
                substepsNotStarted.add(thisSubstepNotStarted);
                substeps.add(thisSubstep);
                substepsHasStarted.add(thisSubstepHasStarted);

                // hook choice started up to each substepNotStarted
                // hook each substepNotStarted up to its substep
                relationsToAdd.add(new Relation(choiceStarted, thisSubstepNotStarted));
                relationsToAdd.add(new Relation(thisSubstepNotStarted, thisSubstep));

                // hook each substep to choiceCompleted
                relationsToAdd.add(new Relation(thisSubstep, choiceCompleted));

                // remove relation from original node to this child
                relationsToRemove.add(new Relation(vertexToReplace, thisChild));

                // hook up choiceStarted to the first substepHasStarted
                if (i==0) {
                    relationsToAdd.add(new Relation(choiceStarted, thisSubstepHasStarted));
                } else {
                    // hook up all substepHasStarteds to the next stubstepHasStarted
                    Vertex prevSubstepHasStarted = substepsHasStarted.get(i - 1);
                    relationsToAdd.add(new Relation(prevSubstepHasStarted, thisSubstepHasStarted));
                }
                // hook up the last substepHasStarted to choiceTerminated
                if (i == lastIndex) {
                    relationsToAdd.add(new Relation(thisSubstepHasStarted, choiceTerminated));
                }
            }

            // add substeps, substepsNotStarted & substepsHasStarted to the template arr list
            // for (Vertex substep : substeps) { templateArrList.add(substep); } // commenting this out, the plain substeps are the original children renamed - don't want them in there twice
            for (Vertex substepNotStarted : substepsNotStarted) { templateArrList.add(substepNotStarted); }
            for (Vertex substepHasStarted : substepsHasStarted) { templateArrList.add(substepHasStarted); }

            // hook every substepNotStarted to all other substepsNotStarted
            for (int i=0; i<substepsNotStarted.size(); i++) {
                Vertex substepNotStartedToHookUp = substepsNotStarted.get(i);
                for (int j=0; j<substepsNotStarted.size(); j++) {
                    Vertex thisTargetVertex = substepsNotStarted.get(j);
                    if (i != j) {
                        relationsToAdd.add(new Relation(substepNotStartedToHookUp, thisTargetVertex));
                    }
                }
            }

            // hook every substep to:
            //   - all other substeps
            //   - all substepHasNotStarted
            //   - first substepHasStarted
            Vertex firstSubstepHasStarted = substepsHasStarted.get(0);
            for (int i=0; i<substeps.size(); i++) {
                Vertex substepToHookUp = substeps.get(i);
                for (int j=0; j<substeps.size(); j++) {
                    Vertex thisTargetSubstep = substeps.get(j);
                    Vertex thisTargetSubstepNotStarted = substepsNotStarted.get(j);
                    if (i != j) {
                        relationsToAdd.add(new Relation(substepToHookUp, thisTargetSubstep));
                    }
                    relationsToAdd.add(new Relation(substepToHookUp, thisTargetSubstepNotStarted));
                }
                relationsToAdd.add(new Relation(substepToHookUp, firstSubstepHasStarted));
            }

        }

        // get relations for hooking up parents
        if (parents != null) {
            for (Vertex parent : parents) {
                relationsToAdd.add(new Relation(parent, choicePosted));
                relationsToRemove.add(new Relation(parent, vertexToReplace));
            }
        }

        // add terminated & completed to the template array list
        templateArrList.add(choiceTerminated);
        templateArrList.add(choiceCompleted);

        // create template vertex list
        VertexList template = new VertexList(templateArrList);

        this.templateSwapDetails = new TemplateSwapDetails(template, vertexToReplace, relationsToAdd, relationsToRemove);

    }

    public TemplateSwapDetails getTemplateSwapDetails() {
        return templateSwapDetails;
    }

}
