package mcdModelChecker.controller.types.vertex;

import mcdModelChecker.controller.exceptions.McdException;
import mcdModelChecker.controller.types.kripke.Label;
import mcdModelChecker.controller.types.kripke.Relation;

import java.awt.geom.Point2D;
import java.util.ArrayList;

import static mcdModelChecker.controller.Controller.getLineNumber;

/**
 * A vertex is a node of a graph.
 * It has a name, a number, a kind, a status and a blurb
 * Example: s0, 0, LEAF, STATED, "Doctor performs heart transplant"
 * Also has CTL related lists: properties, labels, parents, children and relations
 * Also has a <em>ton</em> of bookkeeping properties - TODO: cull these, these can't all be necessary, right?
 */
public class Vertex {

    // inherent properties of a step
    private Integer number;                 /** ex: 0 */
    private String name;                    /** ex: will be "s0" if number is 0 */
    private VertexKind kind;                /** ex: LEAF */
    private VertexStatus status;            /** ex: STARTED */
    private String blurb;                   /** ex: "Doctor performs heart transplant" */
    private ArrayList<String> properties;   /** ex: "heart is ready" */
    private ArrayList<Label> labels;        /** ex: "p" (label for the property) */
    private ArrayList<Vertex> parents;
    private ArrayList<Vertex> children;
    private ArrayList<Relation> relations;

    // utility properties (non-inherent stuff that helps us with logistics)
    private Integer distanceFromRoot;       /** steps away from root */
    private Integer siblingNum;             /** 0 based list of elems in a row (item 0 could either be the first in a row with siblings or an only child) */
    private Integer parentSiblingNum;       /** 0 based list of elems in a row (item 0 could either be the first in a row with siblings or an only child) */
    private Integer origNumber;             /** number before translation & interleavings */
    private Vertex prePermutationVertex;    /** reference to the source vertex (which gets deleted) if this vertex is created for interleavings/permutation */
    private Integer xmlId;                  /** xml property number (ie, id="_1"), zero based and not necessarily sequential. the number in the xml tag is preceeded by an underscore. used with loopTo property. */
    private Integer transitionTo;           /** refers to an xmlId, used to denote where a node should loop back to (loops aren't possible in normal xml) */
    private Integer numVisits;              /** count number of loops made in translation/interleavings */
    private ArrayList<Vertex> origParents;  /** parents before translation & interleavings */
    private ArrayList<Vertex> origChildren; /** children before translation & interleavings */
    private Integer origDistanceFromRoot;   /** distanceFromRoot before translation & interleavings */
    private Integer origSiblingNum;         /** sibling num before translation & interleavings */
    private Point2D xmlGraphPos;
    private Point2D translationGraphPos;
    private Boolean isRoot;                 /** is this vertex the root */
    private Boolean isOriginal;             /** was this vertex in the original vertex list or was it added in translation/interleavings */
    private Boolean visited;                /** mark a vertex as visited in translation/interleavings */
    private Integer numTimesVisited;        /** track number of times a vertex is visited in translation/interleavings */
    private Boolean isTemplateRoot;
    private Boolean isTemplateTerminal;
    private ArrayList<Vertex> templateToSwapIn;
    private ArrayList<Vertex> childrenToSwapIn;
    private ArrayList<Vertex> childrenToRemove;
    private Boolean hasBeenExpanded;        /** has been translated */
    private int substepNum;                 /** for choice substeps only */


    // constructor with no params
    public Vertex() {
        this.number = null;
        this.name = null;
        this.kind = null;
        this.status = null;
        this.blurb = null;
        this.properties = null;
        this.labels = null;
        this.parents = null;
        this.children = null;
        this.distanceFromRoot = null;
        this.siblingNum = null;
        this.parentSiblingNum = null;
        this.origNumber = null;
        this.xmlId = null;
        this.transitionTo = null;
        this.origParents = null;
        this.origChildren = null;
        this.origDistanceFromRoot = null;
        this.origSiblingNum = null;
        this.xmlGraphPos = null;
        this.translationGraphPos = null;
        this.isRoot = null;
        this.isOriginal = null;
        this.visited = null;
        this.numTimesVisited = null;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        addRelationsToParents(this.parents);
        this.hasBeenExpanded = false;
    }

    // constructor with name
    // public Vertex(Integer integer, String s, VertexKind kind, VertexStatus hasNotStarted, String thisBlurb, ArrayList<String> thisProperties, ArrayList<Label> thisLabels, Object o, String name, int notStartedDistanceFromRoot, int ithChild, int i, Integer origNumber, ArrayList<Vertex> origParents, ArrayList<Vertex> origChildren, Integer origDistanceFromRoot, Integer origSiblingNum, boolean b1, boolean b, int substepNum) {
    public Vertex(String name) {
        this.number = null;
        this.name = name;
        this.kind = null;
        this.status = null;
        this.blurb = null;
        this.properties = null;
        this.labels = null;
        this.parents = null;
        this.children = null;
        this.distanceFromRoot = null;
        this.siblingNum = null;
        this.parentSiblingNum = null;
        this.origNumber = null;
        this.xmlId = null;
        this.transitionTo = null;
        this.origParents = null;
        this.origChildren = null;
        this.origDistanceFromRoot = null;
        this.origSiblingNum = null;
        this.xmlGraphPos = null;
        this.translationGraphPos = null;
        this.isRoot = null;
        this.isOriginal = null;
        this.visited = null;
        this.numTimesVisited = null;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        addRelationsToParents(this.parents);
        this.hasBeenExpanded = false;
    }

    // constructor with name and status
    public Vertex(String name, VertexStatus status) {
        this.number = null;
        this.name = name;
        this.kind = null;
        this.status = status;
        this.blurb = null;
        this.properties = null;
        this.labels = null;
        this.parents = null;
        this.children = null;
        this.distanceFromRoot = null;
        this.siblingNum = null;
        this.parentSiblingNum = null;
        this.origNumber = null;
        this.xmlId = null;
        this.transitionTo = null;
        this.origParents = null;
        this.origChildren = null;
        this.origDistanceFromRoot = null;
        this.origSiblingNum = null;
        this.xmlGraphPos = null;
        this.translationGraphPos = null;
        this.isRoot = null;
        this.isOriginal = null;
        this.visited = null;
        this.numTimesVisited = null;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        addRelationsToParents(this.parents);
        this.hasBeenExpanded = false;
    }

    // constructor with name and number
    public Vertex(String name, Integer number) {
        this.number = number;
        this.name = name;
        this.kind = null;
        this.status = null;
        this.blurb = null;
        this.properties = null;
        this.labels = null;
        this.parents = null;
        this.children = null;
        this.distanceFromRoot = null;
        this.siblingNum = null;
        this.parentSiblingNum = null;
        this.origNumber = null;
        this.xmlId = null;
        this.transitionTo = null;
        this.origParents = null;
        this.origChildren = null;
        this.origDistanceFromRoot = null;
        this.origSiblingNum = null;
        this.xmlGraphPos = null;
        this.translationGraphPos = null;
        this.isRoot = null;
        this.isOriginal = null;
        this.visited = null;
        this.numTimesVisited = null;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        addRelationsToParents(this.parents);
        this.hasBeenExpanded = false;
    }

    // constructor with name, number and status
    public Vertex(String name, Integer number, VertexStatus status) {
        this.number = number;
        this.name = name;
        this.kind = null;
        this.status = status;
        this.blurb = null;
        this.properties = null;
        this.labels = null;
        this.parents = null;
        this.children = null;
        this.distanceFromRoot = null;
        this.siblingNum = null;
        this.parentSiblingNum = null;
        this.origNumber = null;
        this.xmlId = null;
        this.transitionTo = null;
        this.origParents = null;
        this.origChildren = null;
        this.origDistanceFromRoot = null;
        this.origSiblingNum = null;
        this.xmlGraphPos = null;
        this.translationGraphPos = null;
        this.isRoot = null;
        this.isOriginal = null;
        this.visited = null;
        this.numTimesVisited = null;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        addRelationsToParents(this.parents);
        this.hasBeenExpanded = false;
    }

    // constructor with name, number, kind and status
    public Vertex(String name, Integer number, VertexKind kind, VertexStatus status) {
        this.number = number;
        this.name = name;
        this.kind = kind;
        this.status = status;
        this.blurb = null;
        this.properties = null;
        this.labels = null;
        this.parents = null;
        this.children = null;
        this.distanceFromRoot = null;
        this.siblingNum = null;
        this.parentSiblingNum = null;
        this.origNumber = null;
        this.xmlId = null;
        this.transitionTo = null;
        this.origParents = null;
        this.origChildren = null;
        this.origDistanceFromRoot = null;
        this.origSiblingNum = null;
        this.xmlGraphPos = null;
        this.translationGraphPos = null;
        this.isRoot = null;
        this.isOriginal = null;
        this.visited = null;
        this.numTimesVisited = null;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        addRelationsToParents(this.parents);
        this.hasBeenExpanded = false;
    }

    // constructor with params for all main properties
    public Vertex(
            Integer number,
            String name,
            VertexKind kind,
            VertexStatus status,
            String blurb,
            ArrayList<String> properties,
            ArrayList<Label> labels,
            ArrayList<Vertex> parents,
            ArrayList<Vertex> children,
            Integer distanceFromRoot,
            Integer siblingNum,
            Integer parentSiblingNum,
            Integer origNumber,
            Integer xmlId,
            Integer transitionTo,
            ArrayList<Vertex> origParents,
            ArrayList<Vertex> origChildren,
            Integer origDistanceFromRoot,
            Integer origSiblingNum,
            Boolean isRoot,
            Boolean isOriginal
    ) {
        this.number = number;
        this.name = name;
        this.kind = kind;
        this.status = status;
        this.blurb = blurb;
        this.properties = properties;
        this.labels = labels;
        this.parents = parents;
        this.children = children;
        this.distanceFromRoot = distanceFromRoot;
        this.siblingNum = siblingNum;
        this.parentSiblingNum = parentSiblingNum;
        this.origNumber = origNumber;
        this.xmlId = xmlId;
        this.transitionTo = transitionTo;
        this.origParents = origParents;
        this.origChildren = origChildren;
        this.origDistanceFromRoot = origDistanceFromRoot;
        this.origSiblingNum = origSiblingNum;
        this.isRoot = isRoot;
        this.isOriginal = isOriginal;
        this.visited = false;
        this.numTimesVisited = 0;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        addRelationsToParents(this.parents);  // TODO: probably move this to where children are added so they don't get out of sync
    }


    // constructor with main params for all properties except xmlId and loopTo
    public Vertex(
            Integer number,
            String name,
            VertexKind kind,
            VertexStatus status,
            String blurb,
            ArrayList<String> properties,
            ArrayList<Label> labels,
            ArrayList<Vertex> parents,
            ArrayList<Vertex> children,
            Integer distanceFromRoot,
            Integer siblingNum,
            Integer parentSiblingNum,
            Integer origNumber,
            ArrayList<Vertex> origParents,
            ArrayList<Vertex> origChildren,
            Integer origDistanceFromRoot,
            Integer origSiblingNum,
            Boolean isRoot,
            Boolean isOriginal
    ) {
        this.number = number;
        this.name = name;
        this.kind = kind;
        this.status = status;
        this.blurb = blurb;
        this.properties = properties;
        this.labels = labels;
        this.parents = parents;
        this.children = children;
        this.distanceFromRoot = distanceFromRoot;
        this.siblingNum = siblingNum;
        this.parentSiblingNum = parentSiblingNum;
        this.origNumber = origNumber;
        this.origParents = origParents;
        this.origChildren = origChildren;
        this.origDistanceFromRoot = origDistanceFromRoot;
        this.origSiblingNum = origSiblingNum;
        this.isRoot = isRoot;
        this.isOriginal = isOriginal;
        this.visited = false;
        this.numTimesVisited = 0;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        addRelationsToParents(this.parents);
    }

    // constructor with params including substepNum for all properties except xmlId and loopTo
    public Vertex(
            Integer number,
            String name,
            VertexKind kind,
            VertexStatus status,
            String blurb,
            ArrayList<String> properties,
            ArrayList<Label> labels,
            ArrayList<Vertex> parents,
            ArrayList<Vertex> children,
            Integer distanceFromRoot,
            Integer siblingNum,
            Integer parentSiblingNum,
            Integer origNumber,
            ArrayList<Vertex> origParents,
            ArrayList<Vertex> origChildren,
            Integer origDistanceFromRoot,
            Integer origSiblingNum,
            Boolean isRoot,
            Boolean isOriginal,
            Integer substepNum
    ) {
        this.number = number;
        this.name = name;
        this.kind = kind;
        this.status = status;
        this.blurb = blurb;
        this.properties = properties;
        this.labels = labels;
        this.parents = parents;
        this.children = children;
        this.distanceFromRoot = distanceFromRoot;
        this.siblingNum = siblingNum;
        this.parentSiblingNum = parentSiblingNum;
        this.origNumber = origNumber;
        this.origParents = origParents;
        this.origChildren = origChildren;
        this.origDistanceFromRoot = origDistanceFromRoot;
        this.origSiblingNum = origSiblingNum;
        this.isRoot = isRoot;
        this.isOriginal = isOriginal;
        this.visited = false;
        this.numTimesVisited = 0;
        this.relations = new ArrayList<Relation>();
        this.numVisits = 0;
        this.substepNum = substepNum;
        addRelationsToParents(this.parents);
    }

    // add relations to parents
    void addRelationsToParents(ArrayList<Vertex> parents) {
        if (parents != null && parents.size() > 0) {
            for (Vertex parent : parents) {
                addRelationToParent(parent);
            }
        }
    }

    void addRelationToParent(Vertex parent) {
        Relation relation = new Relation(parent, this);
        ArrayList<Relation> parentRelations = parent.getRelations();
        if (!doRelationsHaveThisRelation(parentRelations, relation)) {
            parent.getRelations().add(relation);
        }
    }

    Boolean doRelationsHaveThisRelation(ArrayList<Relation> relations, Relation relation) {

        Vertex fromVertex = relation.getFromVertex();
        Vertex toVertex = relation.getToVertex();

        for (Relation thisRelation: relations) {
            Vertex thisFromVertex = thisRelation.getFromVertex();
            Vertex thisToVertex = thisRelation.getToVertex();
            if (thisFromVertex == fromVertex && thisToVertex == toVertex) {
                return true;
            }
        }
        return false;

        // this didn't work for interleavings where the same node number appears multiple times
//        for (Relation thisRelation : relations) {
//            int thisRelationFrom = thisRelation.getFromVertex().getNumber();
//            int thisRelationTo = thisRelation.getToVertex().getNumber();
//            int relationFrom = relation.getFromVertex().getNumber();
//            int relationTo = relation.getToVertex().getNumber();
//            if (thisRelationFrom == relationFrom && thisRelationTo == relationTo) {
//                return true;
//            }
//        }
//         return false;
    }

    public Boolean hasRelation(Relation relation) {
        ArrayList<Relation> relations = this.relations;
        Boolean found = doRelationsHaveThisRelation(relations, relation);
        return found;
    }

    // creates new vertex in new memory space with the same properties as the original
    public Vertex copyVertex() {
        Vertex target = new Vertex();
        this.copyShallowProperties(target);
        this.copySimpleArrayProperties(target);
        this.copyParentsAndChildren(target);
        this.copyRelations(target);
        this.copyOrigParentsAndOrigChildren(target);
        return target;
    }

    public Vertex copyVertexForPermutation() {
        Vertex target = copyVertex();
        target.setPrePermutationVertex(this);
        return target;
    }

    public Vertex copyVertexNoChildrenOrParents() {
        Vertex target = new Vertex();
        ArrayList<Vertex> emptyChildrenArr = new ArrayList<Vertex>();
        ArrayList<Vertex> emptyParentArr = new ArrayList<Vertex>();
        this.copyShallowProperties(target);
        this.copySimpleArrayProperties(target);
        this.copyOrigParentsAndOrigChildren(target);
        this.parents = emptyParentArr;
        this.children = emptyChildrenArr;
        return target;
    }

    public Vertex copySimpleArrayProperties(Vertex target) {
        Vertex source = this;

        // properties
        if (source.properties != null) {
            ArrayList<String> targetProperties = new ArrayList<String>();
            for (String sourceProperty : source.properties) {
                targetProperties.add(sourceProperty);
            }
            target.properties = targetProperties;
        } else {
            target.properties = null;
        }

        // labels
        if (source.labels != null) {
            ArrayList<Label> targetLabels = new ArrayList<Label>();
            for (Label sourceLabel : source.labels) {
                targetLabels.add(sourceLabel);
            }
            target.labels = targetLabels;
        } else {
            target.labels = null;
        }

        return target;
    }

    public Vertex copyShallowProperties(Vertex target) {
        Vertex source = this;
        target.number = source.number;
        target.name = source.name;
        target.kind = source.kind;
        target.status = source.status;
        target.blurb = source.blurb;
        target.distanceFromRoot = source.distanceFromRoot;
        target.siblingNum = source.siblingNum;
        target.parentSiblingNum = source.parentSiblingNum;
        target.origNumber = source.origNumber;
        target.origDistanceFromRoot = source.origDistanceFromRoot;
        target.origSiblingNum = source.origSiblingNum;
        target.isRoot = source.isRoot;
        target.isOriginal = source.isOriginal;
        return target;
    }

    public Vertex copyParentsAndChildren(Vertex target) {
        Vertex source = this;
        target.setParents(null);
        target.setChildren(null);
        if (source.getParents() != null) {
            for (Vertex parent : source.getParents()) {
                target.addParent(parent);
            }
        }
        if (source.getChildren() != null) {
            for (Vertex child : source.getChildren()) {
                target.addChild(child);
            }
        }
        return target;
    }

    public Vertex copyRelations(Vertex target) {
        Vertex source = this;
        ArrayList<Relation> relationsToAdd = new ArrayList<>();
        if (source.getRelations() != null) {
            for (Relation relation : source.getRelations()) {
                Vertex toVertex = relation.getToVertex();
                relationsToAdd.add(new Relation(this, toVertex));
            }
        }
        target.addRelations(relationsToAdd);
        return target;
    }

    public Vertex copyOrigParentsAndOrigChildren(Vertex target) {
        Vertex source = this;
        ArrayList<Vertex> targetOrigParents = new ArrayList<Vertex>();
        ArrayList<Vertex> targetOrigChildren = new ArrayList<Vertex>();
        if (source.getOrigParents() != null) {
            for (Vertex origParent : source.getOrigParents()) {
                targetOrigParents.add(origParent);
            }
        }
        if (source.getOrigChildren() != null) {
            for (Vertex origChild : source.getOrigChildren()) {
                targetOrigChildren.add(origChild);
            }
        }
        target.setOrigParents(targetOrigParents);
        target.setOrigChildren(targetOrigChildren);
        return target;
    }

    public void addChild(Vertex child) {
        if (this.children == null) {
            this.children = new ArrayList<Vertex>();
        }
        this.children.add(child);
    }

    public void removeChild(Vertex child) {
        this.getChildren().remove(child);
    }

    public void linkChild(Vertex child) {
        this.addChild(child);
        child.addParent(this);
    }

    public void linkChildAndRelation(Vertex child) {
        this.addChild(child);
        child.addParent(this);
        this.addRelation(new Relation(this, child));
    }

    public void addOrigParent(Vertex origParent) {
        if (this.origParents == null) {
            this.origParents = new ArrayList<Vertex>();
        }
        this.origParents.add(origParent);
    }

    public void addOrigChild(Vertex child) {
        if (this.origChildren == null) {
            this.origChildren = new ArrayList<Vertex>();
        }
        this.origChildren.add(child);
    }

    public void addParent(Vertex parent) {
        if (this.parents == null) {
            this.parents = new ArrayList<Vertex>();
        }
        this.parents.add(parent);
    }

    public void removeParent(Vertex parent) {
        if (this.parents != null) {
            this.parents.remove(parent);
        }
    }

    public Vertex createRoot(VertexList vertexList) {
        this.setNumber(0);
        this.setSiblingNum(0);
        vertexList.addVertex(this);
        vertexList.setRoot(this);
        return this;
    }

    public void addTemplatRootToSwapIn(Vertex child) {
        if (childrenToSwapIn == null) {
            childrenToSwapIn = new ArrayList<Vertex>();
        }
        childrenToSwapIn.add(child);
    }

    public void addChildToRemove(Vertex child) {
        if (childrenToRemove == null) {
            childrenToRemove = new ArrayList<Vertex>();
        }
        childrenToRemove.add(child);
    }

    public String getShortenedKind() throws McdException {
        VertexKind kind = this.kind;
        String kindStrShort;
        switch(kind) {
            case SEQUENTIAL:
                kindStrShort = "seq";
                break;
            case PARALLEL:
                kindStrShort = "par";
                break;
            case LEAF:
                kindStrShort = "leaf";
                break;
            case CHOICE:
                kindStrShort = "choice";
                break;
            case TRY:
                kindStrShort = "try";
                break;
            default:
                throw new McdException("controller.dataTypes.vertex.Vertex.java " + getLineNumber() + ": kind not recognized as VertexKind const.");
        }
        return kindStrShort;
    }

    public String toStringDetailed() {
        String status = new String();
        String output = "";
        Integer number = this.number;
        Integer origNumber = this.origNumber;
        String kind = this.kind.toFirstChar();
        if (this.status != null) {
            status = this.status.toFirstChar();
        }
        String blurb = this.blurb;

        if (this.status != null) {
            output = "s" + number + ":" + kind + origNumber + status + " " + blurb;
        } else {
            output = "s" + number + ":" + kind + origNumber + " " + blurb;
        }
        return output;
    }

    public String toString() {
        String output = "";
        String name = this.name;
        Integer number = this.number;
        Integer origNumber = this.origNumber;
        String kind = "";
        String status = "";
        String propertiesStr = getPropertiesStr();
        String labelsStr = null;
        Integer substepNum = this.substepNum;

        try {
            labelsStr = getLabelsStr();
        } catch (McdException e) {
            e.printStackTrace();
        }

        VertexKind kindEnum = this.kind;
        VertexStatus statusEnum = this.status;

        if (this.number != null) {
            number = this.number;
        }

        if (this.name != null) {
            name = this.name;
        }

        if (kindEnum != null) {
            if (kindEnum.toSlug() != null) {
                kind = kindEnum.toSlug();
            }
        }

        if (statusEnum != null) {
            if (statusEnum.toString() != null) {
                status = statusEnum.toString();
            }
        }

        if (isOriginal == null || !isOriginal) {
            if (number != null && name != null && labelsStr != null && labelsStr.length() > 0 && origNumber == null ) {
                output = name + ": " + labelsStr;
            } else if (number != null && name != null && origNumber == null) {
                output = name;
            } else if (number != null && kind != null && origNumber != null && status != null && labelsStr != null && labelsStr != "" && substepNum == null || substepNum == 0) {
                output = number + ": " + kind + " " + origNumber + " " + status + " (" + labelsStr + ")";
            } else if (number != null && kind != null && origNumber != null && status != null && labelsStr != null && labelsStr != "" && substepNum != null) {
                output = number + ": " + kind + " " + origNumber + " " + status + " " + substepNum + " (" + labelsStr + ")";
            } else if (number != null && kind != null && origNumber != null && status != null && substepNum != null) {
                output = number + ": " + kind + " " + origNumber + " " + status + " " + substepNum;
            } else if (number != null && kind != null && origNumber != null && status != null) {
                output = number + ": " + kind + " " + origNumber + " " + status;
            } else if (number != null && kind != null && origNumber != null) {
                output = number + ": " + kind + " " + origNumber;
            } else if (number != null && kind != null) {
                output = number + ": " + kind;
            }
        } else {
            if (number != null && kind != null && status != null && labelsStr != null && labelsStr != "" && substepNum == null) {
                output = number + ": " + kind + " " + status + " (" + labelsStr + ")";
            } else if (number != null && kind != null && status != null && labelsStr != null && labelsStr != "" && substepNum != null) {
                output = number + ": " + kind + " " + status + " " + substepNum + " (" + labelsStr + ")";
            } else if (number != null && kind != null && status != null && status != "" && substepNum != null) {
                output = number + ": " + kind + " " + status + " " + substepNum;
            } else if (number != null && kind != null && status != null && status != "") {
                output = number + ": " + kind + " " + status;
            } else if (number != null && kind != null && origNumber != null) {
                output = number + ": " + kind + " " + origNumber;
            } else if (number != null && kind != null) {
                output = number + ": " + kind;
            }
        }

        return output;

    }

    // longStringArr[0]: the state number (ie, s0)
    // longStringArr[1]: the state type (ie, seq)
    // longStringArr[1]: the state type number (ie, 26 in seq 26)
    // longStringArr[2]: the state status (ie, posted)
    // this function returns "s26p" for "seq 26 posted")
    public String toShortString() {
        String longString = toString();
        String[] longStringArr = longString.split(" ");
        String shortString = "";
        shortString = shortString + longStringArr[1].charAt(0);
        shortString = shortString + longStringArr[2];
        if (longStringArr.length > 3) {
            shortString = shortString + longStringArr[3].charAt(0);
        }
        return shortString;
    }

    public String toShortStringWithNodeNumber() {
        String longString = toString();
        String[] longStringArr = longString.split(" ");
        String shortString = "";
        shortString = shortString + longStringArr[0];
        shortString = shortString + longStringArr[1].charAt(0);
        shortString = shortString + longStringArr[2];
        if (longStringArr.length > 3) {
            shortString = shortString + longStringArr[3].charAt(0);
        }
        return shortString;
    }

    public Boolean hasSamePropsAs(Vertex vertexToCompare) {
        Vertex thisVertex = this;
        if (!thisVertex.getName().equals(vertexToCompare.getName())) { return false; }
        else if (thisVertex.getNumber() != vertexToCompare.getNumber()) { return false; }
        /* else if (thisVertex.getRelations().size() != vertexToCompare.getRelations().size()) { return false; }
        else {
            for (Relation thisRelation : thisVertex.getRelations()) {
                if (!vertexToCompare.hasRelation(thisRelation)) { return false; }
            }
        } */
        return true;
    }






    // getters/setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDistanceFromRoot() {
        return distanceFromRoot;
    }

    public VertexKind getKind() {
        return kind;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getNumber() {
        return this.number;
    }

    public ArrayList<String> getProperties() {
        return properties;
    }

    public void setProperties(ArrayList<String> properties) {
        this.properties = properties;
    }

    public String getPropertiesStr() {
        String propertiesStr = "";
        ArrayList<String> propertiesArr = getProperties();
        if (propertiesArr != null) {
            int numProperties = propertiesArr.size();
            for (int i = 0; i < numProperties; i++) {
                propertiesStr = propertiesStr + propertiesArr.get(i);
                if (i != numProperties - 1) {
                    propertiesStr = propertiesStr + ", ";
                }
            }
        }
        return propertiesStr;
    }

    public String getLabelsStr() throws McdException {
        String labelsStr = "";
        ArrayList<Label> labelsArr = getLabels();
        if (labelsArr != null) {
            int numLabels = labelsArr.size();
            for (int i = 0; i < numLabels; i++) {
                labelsStr = labelsStr + labelsArr.get(i);
                if (i != numLabels - 1) {
                    labelsStr = labelsStr + ", ";
                }
            }
        }
        return labelsStr;
    }

    public void addRelation(Relation relation) {
        if (this.relations == null) {
            this.relations = new ArrayList<Relation>();
        }
        if (!hasRelation(relation)) {
            this.relations.add(relation);
        }
    }

    public void addRelations(ArrayList<Relation> relations) {
        for (Relation relation : relations) {
            this.addRelation(relation);
        }
    }

    public void removeRelation(Relation relationToRemove) {
        ArrayList<Relation> relations = this.getRelations();
        if (relations != null) {
            for (Relation relation : relations) {
                if (this.hasRelation(relation)) {
                    relations.remove(relation);
                }
            }
        }
    }

    public void removeRelationByVertex(Vertex vertex) {
        Relation relationToRemove = new Relation(this, vertex);
        Boolean found = false;
        for (Relation relation : this.relations) {
            if (relation.getToVertex().getNumber() == vertex.getNumber()) {
                relationToRemove = relation;
                found = true;
            }
        }
        if (found) {
            this.relations.remove(relationToRemove);
        }
    }

    // "reference" here is an abstraction including both child and relation
    // using this abstraction so i can refactor easier when I switch from using children to relations
    public void addReference(Vertex vertexToAddReferenceOf) {
        this.addChild(vertexToAddReferenceOf);
        this.addRelation(new Relation(this, vertexToAddReferenceOf));
    }

    // "reference" here is an abstraction including both child and relation
    // using this abstraction so i can refactor easier when I switch from using children to relations
    public void removeReference(Vertex vertexToRemoveReferenceOf) {
        this.getChildren().remove(vertexToRemoveReferenceOf);
        this.removeRelation(new Relation(this, vertexToRemoveReferenceOf));
    }

    public void setParents(ArrayList<Vertex> parents) {
        this.parents = parents;
    }

    public void setParent(Vertex parent) {
        ArrayList<Vertex> parents = new ArrayList<Vertex>();
        parents.add(parent);
        this.parents = parents;
    }

    public void linkParent(Vertex newParent) {
        this.parents.add(newParent);
        newParent.addChild(this);
    }

    public Boolean hasLabel(Label labelToFind) {
        if (labels != null) {
            for (Label thisLabel : labels) {
                if (thisLabel.getLabelStr().equals(labelToFind.getLabelStr())) {
                    return true;
                }
            }
        }
        return false;
    }

    public ArrayList<Vertex> getParents() {
        return this.parents;
    }

    public String getBlurb() {
        return blurb;
    }

    public void setBlurb(String blurb) {
        this.blurb = blurb;
    }

    public Integer getOrigNumber() {
        return origNumber;
    }

    public void setOrigNumber(Integer origNumber) {
        this.origNumber = origNumber;
    }

    public ArrayList<Vertex> getOrigParents() {
        return origParents;
    }

    public void setOrigParents(ArrayList<Vertex> origParents) {
        this.origParents = origParents;
    }

    public ArrayList<Vertex> getOrigChildren() {
        return origChildren;
    }

    public void setOrigChildren(ArrayList<Vertex> origChildren) {
        this.origChildren = origChildren;
    }

    public Integer getOrigDistanceFromRoot() {
        return origDistanceFromRoot;
    }

    public void setOrigDistanceFromRoot(Integer origDistanceFromRoot) {
        this.origDistanceFromRoot = origDistanceFromRoot;
    }

    public ArrayList<Label> getLabels() {
        return labels;
    }

    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public Integer getParentSiblingNum() {
        return parentSiblingNum;
    }

    public void setLabels(ArrayList<Label> labels) {
        this.labels = labels;
    }

    public ArrayList<Vertex> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<Vertex> children) {
        this.children = children;
    }

    public Integer getSiblingNum() {
        return siblingNum;
    }

    public void setSiblingNum(Integer siblingNum) {
        this.siblingNum = siblingNum;
    }

    public Integer getOrigSiblingNum() {
        return origSiblingNum;
    }

    public void setOrigSiblingNum(Integer origSiblingNum) {
        this.origSiblingNum = origSiblingNum;
    }

    public VertexStatus getStatus() {
        return status;
    }

    public void setStatus(VertexStatus status) {
        this.status = status;
    }

    public Boolean getIsRoot() {
        return isRoot;
    }

    public void setIsRoot(Boolean initial) {
        isRoot = initial;
    }

    public Boolean getVisited() {
        return visited;
    }

    public void setVisited(Boolean visited) {
        this.visited = visited;
    }

    public Integer getNumTimesVisited() {
        return numTimesVisited;
    }

    public void setNumTimesVisited(Integer numTimesVisited) {
        this.numTimesVisited = numTimesVisited;
    }

    public Boolean getIsOriginal() {
        return isOriginal;
    }

    public void setIsOriginal(Boolean original) {
        isOriginal = original;
    }

    public void setParentSiblingNum(Integer parentSiblingNum) {
        this.parentSiblingNum = parentSiblingNum;
    }

    public Point2D getXmlGraphPos() {
        return xmlGraphPos;
    }

    public void setXmlGraphPos(Point2D xmlGraphPos) {
        this.xmlGraphPos = xmlGraphPos;
    }

    public Point2D getTranslationGraphPos() {
        return translationGraphPos;
    }

    public void setTranslationGraphPos(Point2D translationGraphPos) {
        this.translationGraphPos = translationGraphPos;
    }

    public Boolean getIsTemplateRoot() {
        return isTemplateRoot;
    }

    public void setIsTemplateRoot(Boolean isTemplateRoot) {
        this.isTemplateRoot = isTemplateRoot;
    }

    public Boolean getIsTemplateTerminal() {
        return isTemplateTerminal;
    }

    public void setIsTemplateTerminal(Boolean isTemplateTerminal) {
        this.isTemplateTerminal = isTemplateTerminal;
    }

    public ArrayList<Vertex> getTemplateToSwapIn() {
        return templateToSwapIn;
    }

    public void setTemplateToSwapIn(ArrayList<Vertex> templateToSwapIn) {
        this.templateToSwapIn = templateToSwapIn;
    }

    public void setChildrenToSwapIn(ArrayList<Vertex> childrenToSwapIn) {
        this.childrenToSwapIn = childrenToSwapIn;
    }

    public ArrayList<Vertex> getTemplateRootsToSwapIn() {
        return childrenToSwapIn;
    }

    public ArrayList<Vertex> getChildrenToRemove() {
        return childrenToRemove;
    }

    public void setChildrenToRemove(ArrayList<Vertex> childrenToRemove) {
        this.childrenToRemove = childrenToRemove;
    }

    public ArrayList<Relation> getRelations() {
        return relations;
    }

    public void setRelations(ArrayList<Relation> relations) {
        this.relations = relations;
    }

    public Integer getTransitionTo() {
        return transitionTo;
    }

    public Integer getXmlId() {
        return xmlId;
    }

    public void setTransitionTo(Integer transitionTo) {
        this.transitionTo = transitionTo;
    }

    public void setXmlId(Integer xmlId) {
        this.xmlId = xmlId;
    }

    public Integer getNumVisits() {
        return numVisits;
    }

    public void setNumVisits(Integer numVisits) {
        this.numVisits = numVisits;
    }

    public void increaseNumVisitsByOne() {
        this.numVisits = this.numVisits + 1;
    }

    public void setPrePermutationVertex(Vertex prePermutationVertex) {
        this.prePermutationVertex = prePermutationVertex;
    }

    public Vertex getPrePermutationVertex() {
        return prePermutationVertex;
    }

    public Boolean getHasBeenExpanded() {
        return hasBeenExpanded;
    }

    public void setHasBeenExpanded(Boolean hasBeenExpanded) {
        this.hasBeenExpanded = hasBeenExpanded;
    }

    public void setDistanceFromRoot(Integer distanceFromRoot) {
        this.distanceFromRoot = distanceFromRoot;
    }

    public int getSubstepNum() {
        return substepNum;
    }

    public void setSubstepNum(int substepNum) {
        this.substepNum = substepNum;
    }


}
