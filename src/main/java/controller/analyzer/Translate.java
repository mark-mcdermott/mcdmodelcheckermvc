package controller.analyzer;

import _options.Options;
import controller.analyzer.translationTemplates.*;
import controller.types.analyzer.TemplateSwapDetails;
import controller.types.ctl.Relation;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;
import controller.utils.ExceptionMessage;
import model.Model;
import view.View;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import static controller.types.graph.VertexStatus.COMPLETED;

public class Translate {

    VertexList originalVertexList;
    VertexList vertexListToTranslate;
    VertexList translatedVertexList;
    ArrayList<Vertex> permutedVertices;
    Queue<Vertex> queueToTranslate;
    int numNodesExpanded = 0;
    int targetNumNodesExpanded;
    Integer numSteps;
    Integer numTargetSteps;
    Integer targetStep;
    Boolean debug;
    Model model;
    View view;
    Options options;

    public Translate() {
        this.options = new Options();
        this.debug = options.getDebug();
        this.targetNumNodesExpanded = options.getTargetNumNodesExpanded();
    }

    public Translate(Model model) {
        this.model = model;
        this.options = new Options();
        this.debug = options.getDebug();
        this.targetNumNodesExpanded = options.getTargetNumNodesExpanded();
    }

    public Translate(Model model, View view) {
        this.model = model;
        this.view = view;
        this.options = new Options();
        this.debug = options.getDebug();
        this.targetNumNodesExpanded = options.getTargetNumNodesExpanded();
    }

    // get translated vertexList without interleavings
    public VertexList getTransVertListNoInter(VertexList origVertexList, int numLoops, Boolean isStepSelected, Boolean prevStep, Integer selectedStep) throws ExceptionMessage {
        return getTranslatedVertexList(origVertexList, false, numLoops, isStepSelected, prevStep, selectedStep);
    }

    // get translated vertexList with interleavings
    public VertexList getTransVertListWithInters(VertexList origVertexList, int loopsNum, Boolean isStepSelected, Boolean prevStep, Integer selectedStep) throws ExceptionMessage {
        return getTranslatedVertexList(origVertexList, true, loopsNum, isStepSelected, prevStep, selectedStep);
    }

    private VertexList getTranslatedVertexList(VertexList origVertexList, Boolean getInterleavings, int loopsNum, Boolean isStepSelected, Boolean prevStep, Integer selectedStep) throws ExceptionMessage {
        if (prevStep != null && prevStep == true && selectedStep != null) {
            targetStep = selectedStep - 1;
        } else if (selectedStep != null) {
            targetStep = selectedStep;
        } else {
            targetStep = null;
        }
        numSteps = 0;
        numNodesExpanded = 0;
        permutedVertices = new ArrayList<>();
        this.originalVertexList = origVertexList;
        vertexListToTranslate = origVertexList.copyVertexList(); // preserve original xmlVertexList
        // vertexListToTranslate.setNumSteps(0);
        Vertex root = vertexListToTranslate.getRoot();
        translateRootVertex(root, getInterleavings);
        numSteps++;
        Vertex translatedRoot = vertexListToTranslate.getRoot();

        if (!isStepSelected || targetStep == null || targetStep > 1 && numSteps < targetStep) {
            // not sure this is necessary
            // if (!debug || numNodesExpanded < targetNumNodesExpanded) {
            //     translateVertexsChildren(translatedRoot, getInterleavings);
            // }
            if (!debug || numNodesExpanded < targetNumNodesExpanded) {
                if (root.getChildren() != null) {
                    for (Vertex child : translatedRoot.getChildren()) {
                        translateChildrenRecursively(child, getInterleavings, loopsNum);
                    }
                }
            }
        }

        if (!isStepSelected) {
            vertexListToTranslate.setNumTotalSteps(numSteps);
        } else {
            if (selectedStep != null) {
                vertexListToTranslate.setNumTotalSteps(selectedStep);
            } else {
                vertexListToTranslate.setNumTotalSteps(numSteps);
            }
            // vertexListToTranslate.setNumTargetSteps(numSteps);
            // if (!getInterleavings) { vertexListToTranslate.setNumTotalSteps(model.getNumTranslationSteps()); }
            // else { vertexListToTranslate.setNumTotalSteps(model.getNumInterleavingsSteps()); }
        }

        fixPermutedVertexReferences(vertexListToTranslate, permutedVertices);

        renumberVertices();

        VertexList translatedVertexList = vertexListToTranslate;

        // Kripke translationKripke = translatedVertexList.getKripke();
        // System.out.println("controller.Translate: 74");
        // System.out.print(translationKripke.toDescriptiveKripke());

        return translatedVertexList;

    }

    private void translateChildrenRecursively(Vertex vertex, Boolean getInterleavings, int loopsNum) {
        numSteps++;

        // TODO: remove this, for debugging only
        if (getInterleavings && numSteps==7) {
            int breakpointMarker=5;
        }

        if (!debug || numNodesExpanded < targetNumNodesExpanded) {
            translateVertexsChildren(vertex, getInterleavings);
        }
        vertex.setVisited(true);
        vertex.increaseNumVisitsByOne();
        if (!debug || numNodesExpanded < targetNumNodesExpanded) {
            ArrayList<Vertex> children = vertex.getChildren();

            if (children != null) {
                // TODO: There is a concurrent modification bug here in NestingTest and NestingTestSimplified
                // it's at numNodesExpanded=105, vertex: 406: completed, child:15
                // in child : children, it breaks because children has been modified, I assume
                // I've added the i<children.size() check to fix it, not sure if this is correct
                int numChildren = children.size();
                // System.out.println();
                // System.out.println(numChildren);
                for (int i=0; i<numChildren; i++) {
                    // System.out.println("i:"+i+ ", numChildren:" + numChildren);
                    if (i<children.size() && children.get(i) != null) {
                        Vertex child = children.get(i);
                        int curNumVisits = child.getNumVisits();
                        if (curNumVisits <= loopsNum) {
                        // if (curNumVisits < loopsNum) {
                            if (targetStep == null || numSteps < targetStep) {
                                translateChildrenRecursively(child, getInterleavings, loopsNum);
                            }
                        }
                        // TODO: remove this, for debugging only
                        if (numNodesExpanded==234 && vertex.getNumber()==406 && vertex.getStatus()==COMPLETED) {
                            int breakpointMarker=5;
                        }
                    }
                }
            }
        }
    }

    private void translateVertexsChildren(Vertex vertex, Boolean getInterleavings) {
        // create queue of children (to avoid concurrent modification errors)
        if (vertex.getChildren() != null) {
            if (queueToTranslate == null) { queueToTranslate = new LinkedList<>(); }
            ArrayList<Vertex> children = vertex.getChildren();
            for (Vertex child : children) {
                queueToTranslate.add(child);
            }
        }
        // translate the children in the queue
        if (queueToTranslate != null && queueToTranslate.peek() != null) {
            while (queueToTranslate.peek() != null) {
                translateVertex(queueToTranslate.remove(), false, getInterleavings);

            }
        }
    }

    public void renumberVertices() {

        if (!isAlreadyNumberedCorrectly()) {
            resetVerticesIsVisited();
            resetVerticesNumbers();
            Integer vertexNumber = 0;
            Vertex root = vertexListToTranslate.getRoot();
            root.setName("s" + vertexNumber);
            root.setNumber(vertexNumber++);
            root.setVisited(true);

            if (root.getChildren() != null) {
                for (Vertex child : root.getChildren()) {
                    if (!child.getVisited()) {
                        renumberVerticesRecursive(child);
                        child.setVisited(true);
                    }
                }
            }
        }
    }
    private void renumberVerticesRecursive(Vertex vertex) {
        if (!vertex.getVisited()) {
            int newNum = getHighestVertexNum(vertexListToTranslate) + 1;
            vertex.setNumber(newNum);
            vertex.setName("s" + newNum);

            if (vertex.getRelations() != null) {
                ArrayList<Relation> relations = vertex.getRelations();
                for (Relation relation : relations) {
                    relation.setFromVertex(vertex);
                }
            }

            vertex.setVisited(true);
            if (vertex.getChildren() != null) {
                ArrayList<Vertex> children = vertex.getChildren();
                for (Vertex child : children) {
                    if (child.getVisited() != null && !child.getVisited()) {
                        renumberVerticesRecursive(child);
                    }
                }
            }
        }
    }

    public Integer getHighestVertexNum(VertexList vertexList) {
        Integer highest = -1;
        for (Vertex thisVertex : vertexList.getList()) {
            Integer vertexNumber = thisVertex.getNumber();
            if (vertexNumber != null) {
                if (vertexNumber > highest) highest = vertexNumber;
            }
            if (thisVertex.getTemplateToSwapIn() != null) {
                ArrayList<Vertex> thisTemplate = thisVertex.getTemplateToSwapIn();
                for (Vertex templateVertex : thisTemplate) {
                    if (templateVertex.getNumber() != null) {
                        Integer templateVertexNumber = templateVertex.getNumber();
                        if (templateVertexNumber > highest) highest = templateVertexNumber;
                    }
                }
            }
        }
        // }
        return highest;
    }

    private void resetVerticesIsVisited() {
        for (Vertex vertex : vertexListToTranslate.getList()) {
            vertex.setVisited(false);
        }
    }

    private void resetVerticesNumbers() {
        for (Vertex vertex : vertexListToTranslate.getList()) {
            vertex.setNumber(-1);
        }
    }

    private Boolean isAlreadyNumberedCorrectly() {
        ArrayList<Integer> vertexNumbers = new ArrayList<>();
        for (Vertex vertex : vertexListToTranslate.getList()) {
            Integer vertexNumber = vertex.getNumber();
            vertexNumbers.add(vertexNumber);
        }
        Collections.sort(vertexNumbers);
        for (Integer i=0; i<vertexNumbers.size(); i++) {
            Integer thisVertexNumber = vertexNumbers.get(i);
            if (thisVertexNumber != i) {
                return false;
            }
        }
        return true;
    }

    private void translateVertex(Vertex vertex, Boolean isRoot, Boolean getInterleavings) {
        Boolean isOriginal = vertex.getIsOriginal();
        TemplateSwapDetails templateSwapDetails = new TemplateSwapDetails();
        if (numNodesExpanded == 105 && vertex.getNumber()==391) {
            int debugBreakpoint = 5;
        }
        if (numNodesExpanded == 132 ) {
            int debugBreakpoint = 5;
        }
        if (isOriginal) {
            // if (getInterleavings && numNodesExpanded == 3) {
            if (numNodesExpanded == 2) {
                // System.out.println("Translate.java: 205");
            }
            switch (vertex.getKind()) {
                case LEAF:
//                    if (getInterleavings && numNodesExpanded == 2) {
//                        System.out.println("test");
//                    }
                    templateSwapDetails = new LeafTemplate(vertex, vertexListToTranslate).getTemplateSwapDetails();
                    break;
                case SEQUENTIAL:
                    templateSwapDetails = new SequentialTemplate(vertex, vertexListToTranslate).getTemplateSwapDetails();
                    break;
                case PARALLEL:
                    if (getInterleavings && vertex.getName().equals("s3")) {
                        System.out.println("hi");
                        int breakpointMarker = -1;
                        breakpointMarker++;
                    }
                    templateSwapDetails = new ParallelTemplate(vertex, vertexListToTranslate, getInterleavings).getTemplateSwapDetails();
                    break;
                case TRY:
                    templateSwapDetails = new TryTemplate(vertex, vertexListToTranslate).getTemplateSwapDetails();
                    break;
                case CHOICE:
                    templateSwapDetails = new ChoiceTemplate(vertex, vertexListToTranslate).getTemplateSwapDetails();
                    break;
                default:
                    //
            }
            if (getInterleavings) {
                if (numNodesExpanded == 132) {
                    // System.out.println("hi");
                }
            }
            swapInTemplate(templateSwapDetails, isRoot);

            ArrayList<Vertex> thesePermutedVertices = templateSwapDetails.getOrigVerticesPermuted();
            if (thesePermutedVertices != null) {
                for (Vertex permutedVertex : thesePermutedVertices) {
                    permutedVertices.add(permutedVertex);
                }
            }

            // permutedVertices = templateSwapDetails.getOrigVerticesPermuted();
            vertex.setHasBeenExpanded(true);
            if (getInterleavings) {
                if (numNodesExpanded == 5) {

//                    ArrayList<Vertex> verticesWithoutParents = new ArrayList<>();
//                    for (Vertex thisVertex : vertexListToTranslate.getList()) {
//                        ArrayList<Vertex> thisParents = thisVertex.getParents();
//                        if (thisParents == null || thisParents.size() == 0) {
//                            verticesWithoutParents.add(thisVertex);
//                        }
//                    }
                    // System.out.println(verticesWithoutParents);
                }
            }
            numNodesExpanded++;
        }
    }

    private void translateRootVertex(Vertex vertex, Boolean getInterleavings) {
        translateVertex(vertex, true, getInterleavings);
    }


    private void swapInRoot(TemplateSwapDetails templateSwapDetails) {
        if (templateSwapDetails != null & templateSwapDetails.getTemplate() != null && templateSwapDetails.getTemplate().getRoot() != null) {
            vertexListToTranslate.setRoot(templateSwapDetails.getTemplate().getRoot());
        }
    }

    private void swapInTemplate(TemplateSwapDetails templateSwapDetails, Boolean isRoot) {

        if (isRoot) {
            swapInRoot(templateSwapDetails);
        }

        // init vars
        Vertex vertexToReplace = templateSwapDetails.getVertexToReplace();
        VertexList template = templateSwapDetails.getTemplate();
        ArrayList<Relation> relationsToAdd = templateSwapDetails.getRelationsToAdd();
        ArrayList<Relation> relationsToRemove = templateSwapDetails.getRelationsToRemove();
        ArrayList<Vertex> origVerticesPermuted = templateSwapDetails.getOrigVerticesPermuted();

        // add template vertices
        if (template != null && template.getList() != null) {
            for (Vertex templateVertex : template.getList()) {
                vertexListToTranslate.addVertex(templateVertex);
            }
        }

        // remove original permutation vertices
//        if (origVerticesPermuted != null) {
//            for (Vertex origVertexPermuted : origVerticesPermuted) {
//                vertexListToTranslate.removeVertexFromList(origVertexPermuted);
//            }
//        }

        // update arrays of relations to add/remove to include fixed references to originally permuted vertices (which will get removed)
        if (origVerticesPermuted != null) {
            // getPermutedReferencesToAddAndRemove(vertexListToTranslate, origVerticesPermuted, relationsToAdd, relationsToRemove, vertexToReplace);
        }

        // add relations and update vertices' parent & children properties
        if (relationsToAdd != null) {
            int relationsAdded = 0;
            for (Relation relationToAdd : relationsToAdd) {
                Vertex parent = relationToAdd.getFromVertex();
                Vertex child = relationToAdd.getToVertex();

                if (parent.getChildren() == null || !parent.getChildren().contains(child)) {
                    parent.addChild(child);
                }
                if (child.getParents() == null || !child.getParents().contains(parent)) {
                    child.addParent(parent);
                }
                if (!parent.hasRelation(relationToAdd)) {
                    parent.addRelation(relationToAdd);
                }
                if (origVerticesPermuted != null) {
                    if (template.getList().size() > 50 && origVerticesPermuted.size() > 4) {
                        int test = 1;
                        // System.out.println("controller.Translate.java:312");
                    }
                }

            }
        }

        // remove relations and update vertices' parent & children properties
        if (relationsToRemove != null) {
            for (Relation relationToRemove : relationsToRemove) {
                Vertex parent = relationToRemove.getFromVertex();
                Vertex child = relationToRemove.getToVertex();
                parent.removeChild(child);
                child.removeParent(parent);
                parent.removeRelationByVertex(child);
            }
        }

        // remove the vertex getting replaced by the template
        if (vertexToReplace != null) {
            // vertexListToTranslate.removeVertexFromList(vertexToReplace); // this line was causing a bug in graphs with a parallel substep of a parallel node
            vertexListToTranslate.removeVertexFromListAndAllParentsAndChildren(vertexToReplace);
        }

        // remove any originally permuted vertices (if any exist)
        if (origVerticesPermuted != null) {
            for (Vertex origVertexPermuted : origVerticesPermuted) {
                vertexListToTranslate.removeVertexFromListAndAllParentsAndChildren(origVertexPermuted);
            }
        }
    }

    // looks through permuted vertices for references to any of the original pre-permuted vertices
    // if a reference to a pre-permuted vertex is found, a reference to the permuted version of the
    // pre-permutation vertex is added and the reference to the pre-permuted vertex is removed
    void getPermutedReferencesToAddAndRemove(VertexList vertexList, ArrayList<Vertex> origVerticesPermuted, ArrayList<Relation> relationsToAdd, ArrayList<Relation> relationsToRemove) {

        for (Vertex thisVertex : vertexList.getList()) {
            if (origVerticesPermuted != null) {
                for (Vertex thisOrigVertexPermuted : origVerticesPermuted) {
                    if (thisVertex.getChildren() != null) {
                        if (thisVertex.getChildren().contains(thisOrigVertexPermuted)) {

                            // if (thisVertex != vertexToRemove) {
                            // find the permuted vertex & add reference of permuted vertex
                            findPermutedVerticesAndAddToArr(thisVertex, thisOrigVertexPermuted, vertexList, relationsToAdd);
                            // }

                            // remove reference of prepermuted vertex
                            addPermutedVertexRelationToRemoveToArr(thisVertex, thisOrigVertexPermuted, relationsToRemove);
                        }

                    }

                }
            }
        }
    }

    void addPermutedVertexRelationToRemoveToArr(Vertex fromVertex, Vertex toVertex, ArrayList<Relation> relationsToRemove) {
        Relation relationToRemove = new Relation(fromVertex, toVertex);
        relationsToRemove.add(relationToRemove);
    }

    // find the permuted vertex & add reference of permuted vertex
    void findPermutedVerticesAndAddToArr(Vertex vertexToAddReferenceTo, Vertex prepermutedVertex, VertexList vertexList, ArrayList<Relation> relationsToAdd) {
        Boolean hasBeenExpanded = vertexToAddReferenceTo.getHasBeenExpanded();

        // if (hasBeenExpanded == null || hasBeenExpanded == false) {
        ArrayList<Vertex> permutedVertices = findPermutedVersionsOfPrepermutationVertex(prepermutedVertex, vertexList);
        for (Vertex permutedVertex : permutedVertices) {
            Vertex fromVertex = vertexToAddReferenceTo;
            Vertex toVertex = permutedVertex;
            // relationsToAdd.add(new Relation(fromVertex, toVertex));
        }
        // }
    }

    ArrayList<Vertex> findPermutedVersionsOfPrepermutationVertex(Vertex prepermutationVertex, VertexList vertexList) {
        ArrayList<Vertex> permutedVersionsOfPrepermutationVertices = new ArrayList<>();
        for (Vertex thisVertex : vertexList.getList()) {
            Vertex thisPrepermutationVertex = thisVertex.getPrePermutationVertex();
            if (thisPrepermutationVertex != null && thisPrepermutationVertex == prepermutationVertex) {
                permutedVersionsOfPrepermutationVertices.add(thisVertex);
            }
        }
        return permutedVersionsOfPrepermutationVertices;
    }

    public void fixPermutedVertexReferences(VertexList vertexList, ArrayList<Vertex> origPermutedVertices) {
        ArrayList<Relation> relationsToAdd = new ArrayList<>();
        ArrayList<Relation> relationsToRemove = new ArrayList<>();

        if (origPermutedVertices != null) {
            for (Vertex origVertexPermuted : origPermutedVertices) {
                vertexListToTranslate.removeVertexFromList(origVertexPermuted);
            }
        }

        getPermutedReferencesToAddAndRemove(vertexList,origPermutedVertices,relationsToAdd,relationsToRemove);

        // add relations and update vertices' parent & children properties
        if (relationsToAdd != null) {
            int relationsAdded = 0;
            for (Relation relationToAdd : relationsToAdd) {
                Vertex parent = relationToAdd.getFromVertex();
                Vertex child = relationToAdd.getToVertex();

                if (parent.getChildren() == null || !parent.getChildren().contains(child)) {
                    parent.addChild(child);
                }
                if (child.getParents() == null || !child.getParents().contains(parent)) {
                    child.addParent(parent);
                }
                if (!parent.hasRelation(relationToAdd)) {
                    parent.addRelation(relationToAdd);
                }

            }
        }

        // remove relations and update vertices' parent & children properties
        if (relationsToRemove != null) {
            for (Relation relationToRemove : relationsToRemove) {
                Vertex parent = relationToRemove.getFromVertex();
                Vertex child = relationToRemove.getToVertex();
                parent.removeChild(child);
                child.removeParent(parent);
                parent.removeRelationByVertex(child);
            }
        }
    }

        /*
    private void swapInTemplatesRecursively(Vertex vertex) {
        if (vertex.getChildren() != null) {
            ArrayList<Vertex> children = vertex.getChildren();
            for (Vertex child : children) {

            }
        }
    }
    */
    
}
