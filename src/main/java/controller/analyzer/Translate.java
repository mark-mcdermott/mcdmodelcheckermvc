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
    Integer tempCurNum;
    Integer _leafCount;
    Integer _seqCount;
    Integer _parCount;

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
    public VertexList getTransVertListNoInter(VertexList origVertexList, int numLoops, Boolean isStepSelected, Boolean prevStep, Integer selectedStep, String[] xmlFilenames) throws ExceptionMessage {
        return getTranslatedVertexList(origVertexList, false, numLoops, isStepSelected, prevStep, selectedStep, xmlFilenames);
    }

    // get translated vertexList with interleavings
    public VertexList getTransVertListWithInters(VertexList origVertexList, int loopsNum, Boolean isStepSelected, Boolean prevStep, Integer selectedStep, String[] xmlFilenames) throws ExceptionMessage {
        return getTranslatedVertexList(origVertexList, true, loopsNum, isStepSelected, prevStep, selectedStep, xmlFilenames);
    }

    private VertexList getTranslatedVertexList(VertexList origVertexList, Boolean getInterleavings, int loopsNum, Boolean isStepSelected, Boolean prevStep, Integer selectedStep, String[] xmlFilenames) throws ExceptionMessage {
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
        translatedVertexList = new VertexList();
        VertexList copiedVertexList = origVertexList.copyVertexList();
        Vertex root = copiedVertexList.getRoot();

        if (xmlFilenames[0] == "TwoSteps.ljx") {
            Integer debugMarker = 0;
        }

        translateRootVertex(root, getInterleavings, xmlFilenames);
        numSteps++;
        Vertex translatedRoot = translatedVertexList.getRoot();

        // this is the fix for the below code that "looks" wrong
        translateChildrenRecursively(translatedRoot, getInterleavings, loopsNum, xmlFilenames);

        // this worked, but "looks" wrong. since's root's children are never original nodes, they don't need to be translated
        /*if (!isStepSelected || targetStep == null || targetStep > 1 && numSteps < targetStep) {
            if (!debug || numNodesExpanded < targetNumNodesExpanded) {
                if (root.getChildren() != null) {
                    for (Vertex child : translatedRoot.getChildren()) {
                        translateChildrenRecursively(child, getInterleavings, loopsNum, xmlFilenames);
                    }
                }
            }
        }*/

        if (!isStepSelected) {
            translatedVertexList.setNumTotalSteps(numSteps);
        } else {
            if (selectedStep != null) {
                translatedVertexList.setNumTotalSteps(selectedStep);
            } else {
                translatedVertexList.setNumTotalSteps(numSteps);
            }
        }
        fixPermutedVertexReferences(translatedVertexList, permutedVertices);
        renumberVertices(xmlFilenames);
        return translatedVertexList;
    }

    private void translateChildrenRecursively(Vertex vertex, Boolean getInterleavings, int loopsNum, String[] xmlFilenames) {
        numSteps++;

        // TODO: remove this, for debugging only
        if (getInterleavings && numSteps==7) {
            int breakpointMarker=5;
        }

        if (!debug || numNodesExpanded < targetNumNodesExpanded) {
            translateVertexsChildren(vertex, getInterleavings, xmlFilenames);
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
                                translateChildrenRecursively(child, getInterleavings, loopsNum, xmlFilenames);
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

    private void translateVertexsChildren(Vertex vertex, Boolean getInterleavings, String[] xmlFilenames) {
        // create queue of children (to avoid concurrent modification errors)
        if (vertex.getChildren() != null) {
            if (queueToTranslate == null) { queueToTranslate = new LinkedList<>(); }
            ArrayList<Vertex> children = vertex.getChildren();
            for (Vertex child : children) {
                if (child.getIsOriginal()) {
                    queueToTranslate.add(child);
                }
            }
        }
        // translate the children in the queue
        if (queueToTranslate != null && queueToTranslate.peek() != null) {
            while (queueToTranslate.peek() != null) {
                translateVertex(queueToTranslate.remove(), false, getInterleavings, xmlFilenames);

            }
        }
    }

    public void renumberVertices(String[] xmlFilenames) {

        // if (!isAlreadyNumberedCorrectly()) {
        if (!isAlreadyNumberedCorrectlyNew(xmlFilenames)) {
            resetVerticesIsVisited();
            resetVerticesNumbers();
            Integer vertexNumber = 0;
            Vertex root = translatedVertexList.getRoot();
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
            int newNum = getHighestVertexNum(translatedVertexList) + 1;
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
        for (Vertex vertex : translatedVertexList.getList()) {
            vertex.setVisited(false);
        }
    }

    private void resetVerticesNumbers() {
        for (Vertex vertex : translatedVertexList.getList()) {
            vertex.setNumber(-1);
        }
    }

    private Boolean isAlreadyNumberedCorrectlyNew(String[] xmlFilenames) {
        if (xmlFilenames[0] == "TwoSteps.ljx") {
            Integer debugVar = 0;
        }
        tempCurNum = 0;
        Vertex root = translatedVertexList.getRoot();
        if (root.getNumber() != tempCurNum) return false;
        tempCurNum++;
        if (!isAlreadyNumberedCorrectlyRecursiveNew(root, xmlFilenames)) {
            return false;
        } else {
            return true;
        }
    }

    private Vertex getVertexOfNumber(ArrayList<Vertex> vertices, Integer num) {
        for (Vertex vertex : vertices) {
            if (vertex.getNumber() == num) {
                return vertex;
            }
        }
        return new Vertex();
    }

    private Boolean isAlreadyNumberedCorrectlyRecursiveNew(Vertex node, String[] xmlFilenames) {
        ArrayList<Vertex> children = node.getChildren();
        if (children != null) {
            ArrayList<Integer> childNums = new ArrayList<>();
            for (Vertex child : children) {
                childNums.add(child.getNumber());
            }
            if (childNums.contains(tempCurNum)) {
                Vertex child = getVertexOfNumber(children, tempCurNum);
                tempCurNum++;
                if (isAlreadyNumberedCorrectlyRecursiveNew(child, xmlFilenames)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return true; // at leaf (we're either done or we'll start again higher up the tree)
        }
    }

    private Boolean isAlreadyNumberedCorrectlyRecursive(Vertex node) {
        ArrayList<Vertex> children = node.getChildren();
        if (children != null) {
            Boolean found = false;
            while (children.size() != 0) {
                Vertex child = children.get(0);
                children.remove(child);
                if (child.getNumber() == tempCurNum) {
                    found = true;
                    tempCurNum++;
                    if (!isAlreadyNumberedCorrectlyRecursive(child)) return false;
                }
            }
            if (!found) return false;
        } else {
            return true;
        }
        return true;
    }

    // this doesn't work right
    private Boolean isAlreadyNumberedCorrectly() {
        ArrayList<Integer> vertexNumbers = new ArrayList<>();
        for (Vertex vertex : translatedVertexList.getList()) {
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

    private void translateVertex(Vertex vertex, Boolean isRoot, Boolean getInterleavings, String[] xmlFilenames) {
        TemplateSwapDetails templateSwapDetails = new TemplateSwapDetails();

        if (xmlFilenames[0] == "TwoSteps.ljx") {
            Integer debugMarker = 0;
        }

        switch (vertex.getKind()) {
            case LEAF:
                templateSwapDetails = new LeafTemplate(vertex, translatedVertexList, originalVertexList).getTemplateSwapDetails();
                break;
            case SEQUENTIAL:
                templateSwapDetails = new SequentialTemplate(vertex, translatedVertexList).getTemplateSwapDetails();
                break;
            case PARALLEL:
                templateSwapDetails = new ParallelTemplate(vertex, translatedVertexList, getInterleavings).getTemplateSwapDetails();
                break;
            case TRY:
                templateSwapDetails = new TryTemplate(vertex, translatedVertexList, originalVertexList).getTemplateSwapDetails();
                break;
            case CHOICE:
                templateSwapDetails = new ChoiceTemplate(vertex, translatedVertexList).getTemplateSwapDetails();
                break;
            default:
                //
        }

        swapInTemplate(templateSwapDetails, isRoot);

        ArrayList<Vertex> thesePermutedVertices = templateSwapDetails.getOrigVerticesPermuted();
        if (thesePermutedVertices != null) {
            for (Vertex permutedVertex : thesePermutedVertices) {
                permutedVertices.add(permutedVertex);
            }
        }

        vertex.setHasBeenExpanded(true);
        numNodesExpanded++;
    }


    private void translateRootVertex(Vertex vertex, Boolean getInterleavings, String[] xmlFilenames) {
        translateVertex(vertex, true, getInterleavings, xmlFilenames);
    }


    private void swapInRoot(TemplateSwapDetails templateSwapDetails) {
        if (templateSwapDetails != null & templateSwapDetails.getTemplate() != null && templateSwapDetails.getTemplate().getRoot() != null) {
            translatedVertexList.setRoot(templateSwapDetails.getTemplate().getRoot());
        }
    }

    // this does not hook up the template to the children in the translated vertex list - that happens in the templates
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
                translatedVertexList.addVertex(templateVertex);
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
            // TODO: write a "safe remove" of vertex with some extra checks
            translatedVertexList.safeRemoveVertexIfExists(vertexToReplace);
            translatedVertexList.removeVertexFromListAndAllParentsAndChildren(vertexToReplace);
        }

        // remove any originally permuted vertices (if any exist)
        if (origVerticesPermuted != null) {
            for (Vertex origVertexPermuted : origVerticesPermuted) {
                translatedVertexList.removeVertexFromListAndAllParentsAndChildren(origVertexPermuted);
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
                translatedVertexList.removeVertexFromList(origVertexPermuted);
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
