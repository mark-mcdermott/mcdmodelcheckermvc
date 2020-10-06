package controller.translationTemplates;

import controller.TranslateOld;
import controller.dataTypes.graphHelpers.TemplateSwapDetails;
import controller.dataTypes.graphHelpers.VertexKind;
import controller.dataTypes.graphHelpers.VertexList;
import controller.dataTypes.graphItems.Label;
import controller.dataTypes.graphItems.Relation;
import controller.dataTypes.graphItems.Vertex;

import java.util.ArrayList;
import java.util.Collections;

import static controller.dataTypes.graphHelpers.VertexStatus.*;
import static controller.dataTypes.graphHelpers.VertexStatus.TERMINATED;

public class ParallelTemplate {

    TemplateSwapDetails templateSwapDetails;
    ArrayList<ArrayList<Vertex>> tempVertexArrayPermutations;
    VertexList template;
    ArrayList<Vertex> origVerticesPermuted;

    ParallelTemplate() { }

    public ParallelTemplate(Vertex vertexToReplace, VertexList vertexList, Boolean getInterleavings) {

        // init vars
        Integer number = new TranslateOld().getHighestVertexNum(vertexList) + 1;
        VertexKind kind = (vertexToReplace.getKind() == null) ? null : vertexToReplace.getKind();
        String blurb = (vertexToReplace.getBlurb() == null) ? null : vertexToReplace.getBlurb();
        ArrayList<String> properties = (vertexToReplace.getProperties() == null) ? null : vertexToReplace.getProperties();
        ArrayList<Label> labels = (vertexToReplace.getLabels() == null) ? null : vertexToReplace.getLabels();
        ArrayList<Vertex> parents = (vertexToReplace.getParents() == null) ? null : vertexToReplace.getParents();
        ArrayList<Vertex> children = (vertexToReplace.getChildren() == null) ? null : vertexToReplace.getChildren();
        Integer distanceFromRoot = (vertexToReplace.getDistanceFromRoot() == null) ? null : vertexToReplace.getDistanceFromRoot();
        Integer siblingNum = (vertexToReplace.getSiblingNum() == null) ? null : vertexToReplace.getSiblingNum();
        Integer parentSiblingNum = (vertexToReplace.getParentSiblingNum() == null) ? null : vertexToReplace.getParentSiblingNum();
        Integer origNumber = vertexToReplace.getNumber();
        // Integer origNumber = vertexToReplace.getOrigNumber();
        ArrayList<Vertex> origParents = (vertexToReplace.getOrigParents() == null) ? null : vertexToReplace.getOrigParents();
        ArrayList<Vertex> origChildren = (vertexToReplace.getOrigChildren() == null) ? null : vertexToReplace.getOrigChildren();
        Integer origDistanceFromRoot = distanceFromRoot;
        Integer origSiblingNum = siblingNum;
        Boolean isRoot = false;
        Boolean isOriginal = false;

        // create template vertices
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


        // hook up first two template nodes to each other
        parPosted.addChild(parStarted);
        parStarted.addParent(parPosted);
        parPosted.addRelation(new Relation(parPosted, parStarted));

        // get relations for hooking up parents/children to template vertices
        ArrayList<Relation> relationsToAdd = new ArrayList<>();
        ArrayList<Relation> relationsToRemove = new ArrayList<>();

        // get relations for hooking up parents to template vertices
        if (parents != null) {
            for (Vertex parent : parents) {
                relationsToAdd.add(new Relation(parent, parPosted));
                relationsToRemove.add(new Relation(parent, vertexToReplace));
            }
        }

        // get relations for hooking up children to template vertices
        Integer numChildren = (children == null) ? null : children.size();
        ArrayList<ArrayList<Vertex>> permutations = null;
        if (!getInterleavings) {
            if (children != null && numChildren != null) {
                for (Integer i = 0; i < numChildren; i++) {

                    Vertex thisChild = children.get(i);
                    relationsToRemove.add(new Relation(vertexToReplace, thisChild));
                    relationsToAdd.add(new Relation(parStarted, thisChild));

                    for (Integer j = 0; j < numChildren; j++) {
                        if (i != j) {
                            Vertex otherChild = children.get(j);
                            relationsToAdd.add(new Relation(otherChild, thisChild));
                            relationsToAdd.add(new Relation(thisChild, otherChild));
                        }
                    }
                    thisChild.setParentSiblingNum(0);
                    thisChild.setSiblingNum(i);
                    relationsToAdd.add(new Relation(thisChild, parCompleted));
                    relationsToAdd.add(new Relation(thisChild, parTerminated));
                }

            } else {
                relationsToAdd.add(new Relation(parStarted, parCompleted));
                relationsToAdd.add(new Relation(parStarted, parTerminated));
            }

            // create template vertex list
            template = new VertexList(parPosted, parStarted, parCompleted, parTerminated);

            // if interleavings, instead of hooking up children normally,
            // get all permutations and hook those up instead
        } else if (getInterleavings) {
            // permutations = getChildrenInterleavings(origChildren, relationsToAdd, relationsToRemove, vertexToReplace);
            // TODO: ask Dr Podorozhny if this should be origChildren or children!
            permutations = getChildrenInterleavings(children, relationsToAdd, relationsToRemove, vertexToReplace);
            origVerticesPermuted = new ArrayList<>();
            if (children != null) {
                for (Vertex child : children) {
                    origVerticesPermuted.add(child);
                }
            }

            ArrayList<Vertex> childrensChildren = new ArrayList<>();
            if (children != null) {
                for (Vertex child : children) {
                    ArrayList<Vertex> childsChildren = child.getChildren();
                    if (childsChildren != null) {
                        for (Vertex childsChild : childsChildren) {
                            childrensChildren.add(childsChild);
                        }
                    }
                }
            }

            // remove original children
            // for (Vertex child : origChildren) {
            // TODO: is this right? Trying to fix negative node numbers
            for (Vertex child : children) {
                relationsToRemove.add(new Relation(vertexToReplace, child));
                // vertexList.removeVertexFromList(child);
            }


            // attach interleavings back to the parallel template nodes
            for (ArrayList<Vertex> thisPermutation : permutations) {

                // attach the first of each permutation as child of parStarted
                Vertex firstInThisPermutation = thisPermutation.get(0);
                relationsToAdd.add(new Relation(parStarted, firstInThisPermutation));

                // add all permutation vertices to vertexList
                int numInPermutation = thisPermutation.size();
                for (int i = 0; i < numInPermutation; i++) {
                    Vertex thisVertex = thisPermutation.get(i);
                    relationsToAdd.add(new Relation(thisVertex, parTerminated));

                    if (i == numInPermutation - 1) {
                        Vertex lastVertex = thisVertex;
                        relationsToAdd.add(new Relation(lastVertex, parCompleted));
                    }

                    // vertexList.addVertex(thisVertex);
                }

            }

            // attach children's children to par completed and par terminated
            // i think this was an error
            // for (Vertex childsChild : childrensChildren) {
            //    relationsToAdd.add(new Relation(parTerminated, childsChild));
            //    relationsToAdd.add(new Relation(parCompleted, childsChild));
            // }

            // create template vertex list
            template = new VertexList(parPosted, parStarted, parCompleted, parTerminated, permutations);

        }

        this.templateSwapDetails = new TemplateSwapDetails(template, vertexToReplace, relationsToAdd, relationsToRemove, origVerticesPermuted);

    }

    public TemplateSwapDetails getTemplateSwapDetails() { return templateSwapDetails; }

    // interleavings
    public ArrayList<ArrayList<Vertex>> getChildrenInterleavings(ArrayList<Vertex> children, ArrayList<Relation> relationsToAdd, ArrayList<Relation> relationsToRemove, Vertex vertexToRemove) {
        ArrayList<ArrayList<Vertex>> childrenPermutations = permuteVertexArray(children, relationsToAdd, relationsToRemove, vertexToRemove);
        return childrenPermutations;
    }

    // permutation strategy from https://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
    public ArrayList<ArrayList<Vertex>> permuteVertexArray(ArrayList<Vertex> origArray, ArrayList<Relation> relationsToAdd, ArrayList<Relation> relationsToRemove, Vertex vertexToRemove) {
        ArrayList<ArrayList<Vertex>> permutationsParentAndChildrenFixed = new ArrayList<ArrayList<Vertex>>();
        if (origArray != null) {
            tempVertexArrayPermutations = new ArrayList<ArrayList<Vertex>>();
            int arrayLength = origArray.size() - 1; // for zero based array algo

            // gets permutations and put them in tempVertexArrayPermutations
            permuteStateArrayRecursive(origArray, 0, arrayLength);

            for (ArrayList<Vertex> permutation : tempVertexArrayPermutations) {
                ArrayList permutedVerticesLinked = linkPermutedVertices(permutation, relationsToAdd, relationsToRemove);
                ArrayList permutedVerticesParentsFixed = fixPermutedVerticesParents(permutedVerticesLinked, vertexToRemove);
                permutationsParentAndChildrenFixed.add(permutedVerticesParentsFixed);
            }
        }
        return permutationsParentAndChildrenFixed;
    }

    // goes through each vertex in a permutation and removes the vertexToRemove from its list of parents
    ArrayList<Vertex> fixPermutedVerticesParents(ArrayList<Vertex> permutation, Vertex vertexToRemove) {
        for (Vertex vertex : permutation) {
            ArrayList<Vertex> parents = vertex.getParents();
            if (parents != null) {
                if (parents.contains(vertexToRemove)) {
                    parents.remove(vertexToRemove);
                }
            }
        }
        return permutation;
    }

    // permutation strategy from https://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
    public void permuteStateArrayRecursive(ArrayList<Vertex> arr, int l, int r) {
        if (l == r) {
            ArrayList<Vertex> tempPermutation = new ArrayList<Vertex>();
            for (int i=0; i<arr.size(); i++) {
                Vertex sourceVertex = arr.get(i);
                Vertex vertex = sourceVertex.copyVertexForPermutation();
                // vertex.setOrigNumber(vertex.getNumber());
                // vertex.setOrigNumber(vertex.getOrigNumber());
                tempPermutation.add(vertex);
            }
            tempVertexArrayPermutations.add(tempPermutation);
        } else {
            for (int i=l; i<=r; i++) {
                Collections.swap(arr, l, i);
                permuteStateArrayRecursive(arr,l+1, r);
                Collections.swap(arr, l, i);
            }
        }
        // System.out.println("controller.translationTemplates.ParallelTemplate.java:237");
    }

    // take the unlinked vertices in the arrayList and link them together
    public ArrayList<Vertex> linkPermutedVertices(ArrayList<Vertex> permutation, ArrayList<Relation> relationsToAdd, ArrayList<Relation> relationsToRemove) {
        int permutationLength = permutation.size();
        int lastInPermutationNum = permutationLength - 1;
        for (int i=0; i< lastInPermutationNum; i++) {
            Vertex thisState = permutation.get(i);
            Vertex nextState = permutation.get(i + 1);
            relationsToAdd.add(new Relation(thisState, nextState));
        }
        return permutation;
    }

}
