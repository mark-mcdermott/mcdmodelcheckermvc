package controller.analyzer;

import controller.types.ctl.Relation;
import controller.types.graph.Vertex;

import java.util.ArrayList;
import java.util.Collections;

public class Interleavings {

    public static ArrayList<ArrayList<Vertex>> tempVertexArrayPermutations;

    public Interleavings() { }

    public ArrayList<ArrayList<Vertex>> getOrigChildrenInterleavings(ArrayList<Vertex> origChildren) {
        ArrayList<ArrayList<Vertex>> origChildrenPermutations = permuteVertexArray(origChildren);
        return origChildrenPermutations;
    }

    // permutation strategy from https://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
    public ArrayList<ArrayList<Vertex>> permuteVertexArray(ArrayList<Vertex> origArray) {
        ArrayList<ArrayList<Vertex>> permutationsParentAndChildrenFixed = new ArrayList<ArrayList<Vertex>>();
        if (origArray != null) {
            tempVertexArrayPermutations = new ArrayList<ArrayList<Vertex>>();
            int arrayLength = origArray.size() - 1; // for zero based array algo

            // gets permutations and put them in tempVertexArrayPermutations
            permuteStateArrayRecursive(origArray, 0, arrayLength);

            for (ArrayList<Vertex> permutation : tempVertexArrayPermutations) {
                permutationsParentAndChildrenFixed.add(linkPermutedVertices(permutation));
            }
        }
        return permutationsParentAndChildrenFixed;
    }

    // permutation strategy from https://www.geeksforgeeks.org/write-a-c-program-to-print-all-permutations-of-a-given-string/
    public void permuteStateArrayRecursive(ArrayList<Vertex> arr, int l, int r) {
        if (l == r) {
            ArrayList<Vertex> tempPermutation = new ArrayList<Vertex>();
            for (int i=0; i<arr.size(); i++) {
                Vertex vertex = arr.get(i).copyVertex();
                vertex.setOrigNumber(vertex.getNumber());
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
    }

    // take the unlinked vertices in the arrayList and link them together
    public ArrayList<Vertex> linkPermutedVertices(ArrayList<Vertex> permutation) {
        int permutationLength = permutation.size();
        int lastInPermutationNum = permutationLength - 1;
        for (int i=0; i< lastInPermutationNum; i++) {
            Vertex thisState = permutation.get(i);
            Vertex nextState = permutation.get(i + 1);
            thisState.addChild(nextState);
            nextState.setParent(thisState);
            // TODO: not sure about this
            thisState.addRelation(new Relation(thisState, nextState));
        }
        return permutation;
    }

}
