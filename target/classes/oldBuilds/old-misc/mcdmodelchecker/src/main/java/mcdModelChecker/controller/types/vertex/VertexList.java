package mcdModelChecker.controller.types.vertex;

import mcdModelChecker.controller.types.kripke.Kripke;
import mcdModelChecker.controller.types.kripke.Relation;

import java.util.ArrayList;

/**
 * A vertexList is an ArrayList of {@link Vertex} that explicitly sets one as the root.
 * Also has some Integer bookkeeping counters for the "step graphs", which end translation after a certain step (for
 * debugging, verifying tests and comparison purposes)
 */
public class VertexList {

    private ArrayList<Vertex> list;
    private Vertex root;
    private Integer numTotalSteps;          /** counter getting the total number of steps in translating the graph */
    private Integer numTargetSteps;         /** counter counting the number of steps taken towards a certain target number, where it will stop (for comparison/testing) */

    /**
     * Constructor with no parameters, creates blank list and sets root to null
     */
    public VertexList() {
        list = new ArrayList<Vertex>();
        root = null;
    }

    /**
     * Constructor takes in just list, sets it equal to class' list and uses the first vertex as the root
     *
     * @param list a preset list of vertices to use in this new vertexList
     */
    public VertexList(ArrayList<Vertex> list) {
        this.list = list;
        this.root = list.get(0);
    }

    /**
     * Constructor takes in list and root and sets' them equal to class' list and root
     *
     * @param list  a preset list of vertices to use in this new vertexList
     * @param root  specified vertex of the list in first param that represents the root / initial vertex of the graph
     */
    public VertexList(ArrayList<Vertex> list, Vertex root) {
        this.list = list;
        this.root = root;
    }

    /**
     * Constructor takes in root, then three more vertices. Adds them all to a blank list and sets root explicitly
     *
     * @param rootVertex1   initial vertex in this set of vertices
     * @param vertex2       second vertex in this set of vertices
     * @param vertex3       third vertex in this set of vertices
     * @param vertex4       fourth vertex in this set of vertices
     */
    public VertexList(Vertex rootVertex1, Vertex vertex2, Vertex vertex3, Vertex vertex4) {
        list = new ArrayList<Vertex>();
        list.add(rootVertex1);
        list.add(vertex2);
        list.add(vertex3);
        list.add(vertex4);
        root = rootVertex1;
    }

    /**
     * Only constructor that takes in a list of permutations (as well as root and three vertices).
     * Flattens the permutations and adds all permutation vertices to list (as well as root and the three vertices first).
     * Sets root explicitly.
     *
     * @param rootVertex1   initial vertex in this set of vertices
     * @param vertex2       second vertex in this set of vertices
     * @param vertex3       third vertex in this set of vertices
     * @param vertex4       fourth vertex in this set of vertices
     * @param permutations  a set of permutations (so an arrayList of an arrayList of vertices) that gets added to this vertexList after the fourth vertex specified above. Every node in the permutations gets added one after another, so essentially the ArrayList of ArrayList of Vertex is flattened as it is added to the list
     */
    public VertexList(Vertex rootVertex1, Vertex vertex2, Vertex vertex3, Vertex vertex4, ArrayList<ArrayList<Vertex>> permutations) {
        list = new ArrayList<Vertex>();
        list.add(rootVertex1);
        list.add(vertex2);
        list.add(vertex3);
        list.add(vertex4);
        for (ArrayList<Vertex> permutation : permutations) {
            for (Vertex permutationVertex : permutation) {
                list.add(permutationVertex);
            }
        }
        root = rootVertex1;
    }


    public void incrementNumSteps() {
        this.numTotalSteps++;
    }

    public void incrementNumTargetSteps() {
        this.numTargetSteps++;
    }

    public void addVertex(Vertex vertex) {
        list.add(vertex);
    }

    public void removeVertexFromList(Vertex vertex) {
        list.remove(vertex);
    }

    public void removeVertexFromListAndAllParentsAndChildren(Vertex vertex) {
        removeVertexFromList(vertex);
        removeVertexFromAllParentsAndChildren(vertex);
    }

    // this is very expensive when the list is long (1300+ nodes, etc)
    // is there a smarter way to do this?
    public void removeVertexFromAllParentsAndChildren(Vertex vertexToRemove) {
        for (Vertex thisVertex : list) {
            removeVertexFromVertexsParentsAndChildren(vertexToRemove, thisVertex);
        }
    }

    public void removeVertexFromVertexsParentsAndChildren(Vertex vertexToRemove, Vertex thisVertex) {
        ArrayList<Vertex> parents = thisVertex.getParents();
        ArrayList<Vertex> children = thisVertex.getChildren();
        removeVertexIfExists(vertexToRemove, children);
        removeVertexIfExists(vertexToRemove, parents);
    }

    public void removeVertexIfExists(Vertex vertexToRemove, ArrayList<Vertex> vertexArrList) {
        if (vertexToRemove != null && vertexArrList != null) {
            if (vertexArrList.contains(vertexToRemove)) {
                vertexArrList.remove(vertexToRemove);
            }
        }
    }

    public VertexList copyVertexList() {
        VertexList sourceVertexList = this;
        VertexList targetVertexList = new VertexList();
        ArrayList<Vertex> sourceList = sourceVertexList.getList();
        ArrayList<Vertex> targetList = new ArrayList<Vertex>();

        // copy all properties except parents/children
        for (Vertex sourceVertex : sourceList) {
            Vertex targetVertex = new Vertex();
            targetVertex = sourceVertex.copyShallowProperties(targetVertex);
            targetVertex = sourceVertex.copySimpleArrayProperties(targetVertex);
            targetList.add(targetVertex);
            if (sourceVertex.getIsRoot() != null && sourceVertex.getIsRoot() == true) {
                targetVertexList.setRoot(targetVertex);
            }
            targetVertexList.setList(targetList);
        }

        // copy parents & children
        for (int i=0; i<sourceList.size(); i++) {
            Vertex sourceVertex = sourceVertexList.getList().get(i);
            Vertex targetVertex = targetVertexList.getList().get(i);

            if (sourceVertex.getParents() != null) {
                ArrayList<Vertex> sourceParents = sourceVertex.getParents();
                for (int j=0; j<sourceParents.size(); j++) {
                    Vertex sourceParent = sourceParents.get(j);
                    int parentsIndexInVertexList = sourceParent.getNumber();
                    targetVertex.addParent(targetList.get(parentsIndexInVertexList));
                }
            }

            if (sourceVertex.getChildren() != null && sourceVertex.getChildren().get(0) != null && sourceVertex.getChildren().get(0).getNumber() != null) {
                ArrayList<Vertex> sourceChildren = sourceVertex.getChildren();
                for (int j=0; j<sourceChildren.size(); j++) {
                    Vertex sourceChild = sourceChildren.get(j);
                    int childsIndexInVertexList = sourceChild.getNumber();
                    targetVertex.addChild(targetList.get(childsIndexInVertexList));
                }
            }

            if (sourceVertex.getOrigParents() != null) {
                ArrayList<Vertex> sourceOrigParents = sourceVertex.getOrigParents();
                for (int j=0; j<sourceOrigParents.size(); j++) {
                    Vertex sourceOrigParent = sourceOrigParents.get(j);
                    int origParentsIndexInVertexList = sourceOrigParent.getNumber();
                    targetVertex.addOrigParent(targetList.get(origParentsIndexInVertexList));
                }
            }

            if (sourceVertex.getOrigChildren() != null) {
                ArrayList<Vertex> sourceOrigChildren = sourceVertex.getOrigChildren();
                for (int j=0; j<sourceOrigChildren.size(); j++) {
                    Vertex sourceOrigChild = sourceOrigChildren.get(j);
                    int origChildsIndexInVertexList = sourceOrigChild.getNumber();
                    targetVertex.addOrigChild(targetList.get(origChildsIndexInVertexList));
                }
            }

            // copy relations
            if (sourceVertex.getRelations() != null && sourceVertex.getRelations().get(0) != null && sourceVertex.getRelations().get(0).getToVertex() != null && sourceVertex.getRelations().get(0).getToVertex().getNumber() != null) {
                ArrayList<Relation> sourceRelations = sourceVertex.getRelations();
                ArrayList<Relation> targetRelations = targetVertex.getRelations();
                if (targetRelations == null) {
                    targetRelations = new ArrayList<Relation>();
                }
                for (int j=0; j<sourceRelations.size(); j++) {
                    Relation sourceRelation = sourceRelations.get(j);
                    targetVertex.addRelation(sourceRelation);
                }
            }

        }
        return targetVertexList;
    }

    public String toString() {
        String output;
        output = "";
        for (int i=0; i<list.size(); i++) {
            Vertex vertex = list.get(i);
            output = output + vertex.toString();
            if ((i + 1) < list.size()) {
                output = output + ", ";
            }
        }
        return output;
    }

    // getters/setters

    public ArrayList<Vertex> getList() {
        return list;
    }

    public void setList(ArrayList<Vertex> list) {
        this.list = list;
    }

    public Vertex getRoot() {
        return root;
    }

    public void setRoot(Vertex root) {
        this.root = root;
    }

    public Vertex findVertexById(int id) {
        Vertex foundVertex = new Vertex();

        for (Vertex thisVertex : list) {
            int thisId = thisVertex.getXmlId();
            if (thisId == id) {
                foundVertex = thisVertex;
            }
        }
        return foundVertex;
    }

    public Kripke getKripke() {
        Kripke kripke = new Kripke();
        return kripke;
    }

    public Integer getNumTotalSteps() {
        return numTotalSteps;
    }

    public void setNumTotalSteps(Integer numTotalSteps) {
        this.numTotalSteps = numTotalSteps;
    }

    public Integer getNumTargetSteps() {
        return numTargetSteps;
    }

    public void setNumTargetSteps(Integer numTargetSteps) {
        this.numTargetSteps = numTargetSteps;
    }


}
