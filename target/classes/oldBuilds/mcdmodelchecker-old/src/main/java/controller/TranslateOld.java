package controller;

import controller.dataTypes.graphHelpers.VertexKind;
import controller.dataTypes.graphHelpers.VertexList;
import controller.dataTypes.graphItems.Relation;
import controller.dataTypes.graphItems.Vertex;
import controller.helpers.McdException;
import controller.translationTemplates.*;

import java.util.ArrayList;
import java.util.Collections;

import static controller.helpers.LineNumber.getLineNumber;

public class TranslateOld {

    // ArrayList<Vertex> stackToCrawl;
    // VertexList xmlVertexList;
    Integer numVerticesInXmlList;
    Integer numVerticesSwapped;
    Vertex vertexToRevisit;

    public TranslateOld() { }

    // this gets translated vertexList without interleavings
    public VertexList getTranslatedVertexListNoInterleavings(VertexList origVertexList) throws McdException {
        return swapInSequencingTemplates(origVertexList, false);
    }

    // this gets translated vertexList with interleavings
    public VertexList getTranslatedVertexListWithInterleavings(VertexList origVertexList) throws McdException {
        return swapInSequencingTemplates(origVertexList, true);
    }

    private VertexList swapInSequencingTemplates(VertexList origVertexList, Boolean getInterleavings) throws McdException {

        // swap out root for translated template (children are hooked up to template & vice versa)
        numVerticesSwapped = 0;
        numVerticesInXmlList = origVertexList.getList().size();
        VertexList vertexList = origVertexList.copyVertexList(); // preserve original xmlVertexList
        Vertex oldRoot = vertexList.getRoot();
        ArrayList<Vertex> template = getTemplate(oldRoot, vertexList, getInterleavings);
        numVerticesSwapped++;
        Vertex newRoot = template.get(0);

        // remove orig root from its children's parents property array & from the main vertex list
        // is this necessary? i think it's being taken care of already in the templates
        // if (oldRoot.getChildren() != null) {
        //    for (Vertex child : oldRoot.getChildren()) { child.removeParent(oldRoot); }
        //    vertexList.removeVertex(oldRoot);
        // }

        // kick off swapInSequencingTemplatesRecursive()
        swapInSequencingTemplatesRecursive(newRoot, vertexList, getInterleavings);

        // renumber the vertices starting from zero (first few vertices probably were deleted, the ones that got swapped out for templates)
        renumberVertices(vertexList);

        return vertexList;
    }

    private VertexList swapInSequencingTemplatesRecursive(Vertex vertex, VertexList vertexList, Boolean getInterleavings) throws McdException {

        // init  vars
        ArrayList<Vertex> children = vertex.getChildren();
        vertex.setVisited(true);
        vertexToRevisit = null;
        ArrayList<ArrayList<Vertex>> templatesToSwapIn = new ArrayList<ArrayList<Vertex>>();

        // get vertex's children
        if (children != null) {
            ArrayList<Vertex> childrenToRemove = new ArrayList<>();

            // get templates for all children, but don't hook them up yet)
            for (Vertex child : children) {
                if (child.getIsOriginal()) {
                    Vertex childToRemove = child;
                    ArrayList<Vertex> templateToSwapIn = getTemplate(child, vertexList, getInterleavings);
                    templatesToSwapIn.add(templateToSwapIn);
                    childToRemove.setTemplateToSwapIn(templateToSwapIn);
                    childrenToRemove.add(childToRemove);
                    numVerticesSwapped++;
                }
            }

            // remove original child vertices (that were replaced by templates)
            for (Vertex childToRemove : childrenToRemove) {
                // remove the orig child vertex
                vertex.removeChild(childToRemove);
                vertex.removeRelationByVertex(childToRemove);
                vertexList.removeVertexFromList(childToRemove);
                // remove vertex from vertex's parents' children array
                Vertex thisVertex = childToRemove;
                ArrayList<Vertex> theseParents = thisVertex.getParents();
                for (Vertex parent : theseParents) {
                    ArrayList<Vertex> parentsChildren = parent.getChildren();
                    parentsChildren.remove(vertex);
                    parent.removeRelationByVertex(vertex);
                }
                // remove the vertex from vertex's childrens' parent array
                ArrayList<Vertex> theseChildren = thisVertex.getChildren();
                if (theseChildren != null) {
                    for (Vertex child : theseChildren) {
                        ArrayList<Vertex> childrensParents = child.getParents();
                        childrensParents.remove(vertex);
                    }
                }
            }

            // hook up template's root as vertex's child
            for (Vertex childToRemove : childrenToRemove) {
                ArrayList<Vertex> templateToSwapIn = childToRemove.getTemplateToSwapIn();
                for (Vertex templateVertex : templateToSwapIn) {
                    Vertex templateRoot = null;
                    // if template root, hook up to vertex
                    if (templateVertex.getIsTemplateRoot() != null && templateVertex.getIsTemplateRoot()) {
                        templateRoot = templateVertex;
                        vertex.addChild(templateRoot);
                        vertex.addRelation(new Relation(vertex, templateRoot));
                        templateRoot.addParent(vertex);
                    }
                    // vertexList.addVertex(templateVertex);
                }
            }


            // crawl all new template nodes, looking for any original nodes that need templates. original nodes are marked.
            for (ArrayList<Vertex> templateSwappedIn : templatesToSwapIn) {
                for (Vertex vertexSwappedIn : templateSwappedIn) {
                    if (vertexSwappedIn.getChildren() != null) {
                        ArrayList<Vertex> vertexSwappedInsChildren = vertexSwappedIn.getChildren();
                        for (Vertex vertexSwappedInsChild : vertexSwappedInsChildren) {
                            if (vertexSwappedInsChild.getIsOriginal()) { // not sure if isOriginal is the right logic - looking for pre-template swap vertices here that should have been replaced when a template swapped in
                                // if (vertexSwappedIn.getTemplateToSwapIn() != null && vertexSwappedIn.getTemplateToSwapIn().get(0) != null) {
                                if (vertexSwappedInsChild.getTemplateToSwapIn() != null && vertexSwappedInsChild.getTemplateToSwapIn().get(0) != null) {
                                    Vertex correctTemplateRootToSwapIn = vertexSwappedInsChild.getTemplateToSwapIn().get(0);
                                    vertexSwappedIn.addTemplatRootToSwapIn(correctTemplateRootToSwapIn);
                                    vertexSwappedIn.addChildToRemove(vertexSwappedInsChild);
                                }
                            }
                        }
                    }
                }
            }

            // all original marked nodes from step above, get templates swapped in and then are removed
            // (this is a separate step from marking children to swap in templates for to avoid concurrent modification errors)
            for (ArrayList<Vertex> templateSwappedIn : templatesToSwapIn) {
                for (Vertex vertexSwappedIn : templateSwappedIn) {
                    if (vertexSwappedIn.getTemplateRootsToSwapIn() != null) {
                        ArrayList<Vertex> templateRootsToSwapIn = vertexSwappedIn.getTemplateRootsToSwapIn();
                        for (Vertex templateRootToSwapIn : templateRootsToSwapIn) {
                            vertexSwappedIn.addChild(templateRootToSwapIn);
                        }
                    }
                    // remove the child that is swapped out for the template
                    // (again, separate step than swapping in the template to avoid concurrent modification errors)
                    if (vertexSwappedIn.getChildrenToRemove() != null) {
                        ArrayList<Vertex> oldChildrenToRemove = vertexSwappedIn.getChildrenToRemove();
                        for (Vertex childToRemove : oldChildrenToRemove) {
                            vertexSwappedIn.removeChild(childToRemove);
                        }
                    }
                }
            }


        }

        // run recursively on all children including the new template roots added
        if (getInterleavings || (!getInterleavings && numVerticesSwapped < numVerticesInXmlList)) {
            ArrayList<Vertex> newChildren = (vertex.getChildren() == null) ? null : vertex.getChildren();
            if (newChildren != null) {
                for (Vertex newChild : newChildren) {
                    swapInSequencingTemplatesRecursive(newChild, vertexList, getInterleavings);
                }
            }
        }

        return vertexList;

    }

    private ArrayList<Vertex> getTemplate(Vertex vertex, VertexList vertexList, Boolean getInterleavings) throws McdException {
        VertexKind kind = vertex.getKind();
        // Integer startingVertexNum = getHighestVertexNum(vertexList) + 1;
        ArrayList<Vertex> templateArray = new ArrayList<>();

        switch(kind) {
            case LEAF:
                templateArray = new LeafTemplateOld().leafTemplate(vertex, vertexList);
                break;
            case SEQUENTIAL:
                templateArray = new SequentialTemplateOld().swapInSequentialTemplate(vertex, vertexList);
                break;
            case PARALLEL:
                // only get interleavings if vertex has more than one child
                Integer numChildren = (vertex.getChildren() == null) ? null : vertex.getChildren().size();
                if (numChildren == null || numChildren < 2) { getInterleavings = false; }
                templateArray = new ParallelTemplateOld().getParallelTemplate(vertex, vertexList, getInterleavings);
                break;
            case TRY:
                // templateArray = new TryTemplate().swapInTryTemplate(vertex, vertexList);
                break;
            // case CHOICE:
                // TODO: figure out choice code (i don't understand the diagram in the paper)
                // templateArray = new ChoiceTemplate().swapInChoiceTemplate(vertex, vertexList);
                // templateArray = new ChoiceTemplate(vertex, vertexList).getTemplateSwapDetails();
                // break;
            default:
                throw new McdException("core.Translate.java " + getLineNumber() + ": vertex.kind string not recognized in swapInSequencingTemplate(), Translation.java: " + getLineNumber());
        }
        return templateArray;
    }



    // helpers

    private Boolean isAlreadyNumberedCorrectly(VertexList vertexList) {
        ArrayList<Integer> vertexNumbers = new ArrayList<>();
        for (Vertex vertex : vertexList.getList()) {
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

    private void resetVerticesIsVisited(VertexList vertexList) {
        for (Vertex vertex : vertexList.getList()) {
            vertex.setVisited(false);
        }
    }

    private void resetVerticesNumbers(VertexList vertexList) {
        for (Vertex vertex : vertexList.getList()) {
            vertex.setNumber(-1);
        }
    }

    public void renumberVertices(VertexList vertexList) {
        if (!isAlreadyNumberedCorrectly(vertexList)) {
            resetVerticesIsVisited(vertexList);
            resetVerticesNumbers(vertexList);
            Integer vertexNumber = 0;
            Vertex root = vertexList.getRoot();
            root.setName("s" + vertexNumber);
            root.setNumber(vertexNumber++);
            root.setVisited(true);
            if (root.getChildren() != null) {
                for (Vertex child : root.getChildren()) {
                    if (!child.getVisited()) {
                        renumberVerticesRecursive(child, vertexList);
                        child.setVisited(true);
                    }
                }
            }
        }
    }

    private void renumberVerticesRecursive(Vertex vertex, VertexList vertexList) {
        if (!vertex.getVisited()) {
            int newNum = getHighestVertexNum(vertexList) + 1;
            vertex.setNumber(newNum);
            vertex.setName("s" + newNum);
            vertex.setVisited(true);
            if (vertex.getChildren() != null) {
                ArrayList<Vertex> children = vertex.getChildren();
                for (Vertex child : children) {
                    if (child.getVisited() != null && !child.getVisited()) {
                        renumberVerticesRecursive(child, vertexList);
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


}
