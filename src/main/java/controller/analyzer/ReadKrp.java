package controller.analyzer;

import _options.Options;
import controller.types.ctl.Label;
import controller.types.ctl.Relation;
import controller.types.graph.LabelHash;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;
import controller.utils.ExceptionMessage;
import controller.utils.ListHelper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ReadKrp {

    VertexList vertexList;
    Options options;

    public ReadKrp() {

    }

    public String[] getKripkeStringsFromFile(BufferedReader reader, Options options, Boolean customKrpFile) throws IOException {
        int numLinesToSkip = options.getTesterKripkeLinesToSkip(); // based on the stucture of the tests. if the structure of the tests were changed, this would have to change.
        String[] kripkeLines = new String[3];
        if (!customKrpFile) {
            for (int i=0; i<numLinesToSkip; i++) { reader.readLine(); }
        }
        kripkeLines[0] = reader.readLine();
        kripkeLines[1] = reader.readLine();
        kripkeLines[2] = reader.readLine();
        return kripkeLines;
    }

    public ArrayList<Vertex> getStates(String statesString) {
        ListHelper listHelper = new ListHelper();
        statesString = statesString.replace("W={","");
        statesString = statesString.replace("};","");
        String[] statesArr = statesString.split(",");
        ArrayList<String> statesStringArrList = listHelper.stringArrToArrList(statesArr);
        ArrayList<Vertex> statesVertexArrList = new ArrayList<>();
        for (String stateName : statesStringArrList) {
            int stateNumber = Integer.parseInt(stateName.substring(1));
            Vertex newVertex = new Vertex(stateName, stateNumber);
            statesVertexArrList.add(newVertex);
        }
        Collections.sort(statesVertexArrList, new Comparator<Vertex>() {
            @Override
            public int compare(Vertex o1, Vertex o2) {
                return o1.getNumber().compareTo(o2.getNumber());
            }
        });
        return statesVertexArrList;
    }

    public ArrayList<Vertex> addRelations(ArrayList<Vertex> states, String relationsString) {
        ListHelper listHelper = new ListHelper();
        relationsString = relationsString.replace("R={","");
        relationsString = relationsString.replace("};","");
        String[] relationsArr = relationsString.split("\\),\\(");
        ArrayList<String> relationsStringArrList = listHelper.stringArrToArrList(relationsArr);
        for (String relationString : relationsStringArrList) {
            relationString = relationString.replace("(","");
            relationString = relationString.replace(")","");
            String[] relationsPairArr = relationString.split(",");
            String fromRel = relationsPairArr[0];
            String toRel = relationsPairArr[1];
            Vertex fromVertex = new Vertex();
            Vertex toVertex = new Vertex();
            for (Vertex state : states) {
                String stateName = state.getName();
                if (stateName.equals(fromRel)) { fromVertex = state; }
                if (stateName.equals(toRel)) { toVertex = state; }
            }
            if (fromVertex != null && toVertex != null) {
                fromVertex.addRelation(new Relation(fromVertex, toVertex));
                fromVertex.addChild(toVertex);
                toVertex.addParent(fromVertex);
            }
        }
        return states;
    }

    public void setParentSiblingNums(VertexList states) {
        Vertex root = states.getRoot();
        root.setSiblingNum(0);
        root.setVisited(true);
        setParentSiblingNumsRecursive(root);
    }

    public void setParentSiblingNumsRecursive(Vertex state) {
        ArrayList<Vertex> children = state.getChildren();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                Vertex child = children.get(i);
                Boolean visited = child.getVisited();
                if (visited == null || visited == false) {
                    int siblingNum = i;
                    int parentSiblingNum = state.getSiblingNum();
                    child.setSiblingNum(siblingNum);
                    child.setParentSiblingNum(parentSiblingNum);
                    child.setVisited(true);
                    setParentSiblingNumsRecursive(child);
                }
            }
        }
    }

    public ArrayList<Vertex> addLabels(ArrayList<Vertex> states, String kripeLabelsString) throws ExceptionMessage, ExceptionMessage {
        String[] labelArr = kripeLabelsString.split(",");
        ListHelper listHelper = new ListHelper();
        ArrayList<String> kripkeLabelArrList = listHelper.stringArrToArrList(labelArr);
        for (String labelEquation : kripkeLabelArrList) {
            String[] labelEquationParts = kripeLabelsString.split("=");

            // get vertex to add label too
            String vertexStringName = labelEquationParts[0];
            vertexStringName = vertexStringName.replace("L(","");
            vertexStringName = vertexStringName.replace(")","");
            Vertex labelVertex = new Vertex();
            for (Vertex state : states) {
                if (state.getName().equals(vertexStringName)) {
                    labelVertex = state;
                }
            }

            // get label(s) to add to vertex
            String labelsSetString = labelEquationParts[1];
            labelsSetString = labelsSetString.replace("{","");
            labelsSetString = labelsSetString.replace("};","");
            String[] labelNames = labelsSetString.split(",");
            ArrayList<String> labelStringArrList = listHelper.stringArrToArrList(labelNames);
            ArrayList<Label> labelArrList = new ArrayList<>();
            for (String labelString : labelStringArrList) {
                Label label = new Label(labelString);
                labelArrList.add(label);
            }

            labelVertex.setLabels(labelArrList);
        }
        return states;
    }

    public void setupLabelHash(LabelHash labelHash, VertexList states) {
        ArrayList<String> allLabelsStrings = new ArrayList<>();
        for (Vertex state : states.getList()) {
            ArrayList<Label> labels = state.getLabels();
            if (labels != null) {
                for (Label label : labels) {
                    String labelString = label.getLabelStr();
                    if (!allLabelsStrings.contains(labelString)) {
                        allLabelsStrings.add(labelString);
                    }
                }
            }
        }
        Collections.sort(allLabelsStrings, String.CASE_INSENSITIVE_ORDER);
        for (String label : allLabelsStrings) {
            labelHash.addProperty(label);
        }
    }

    public VertexList krpStringsToVertexList(String[] krpStringsArr, LabelHash labelHash) throws ExceptionMessage, ExceptionMessage {
        String statesString = krpStringsArr[0];
        String relationsString = krpStringsArr[1];
        String labelsString = krpStringsArr[2];
        ArrayList<Vertex> states = getStates(statesString);
        states = addRelations(states, relationsString);
        states = addLabels(states, labelsString);
        Vertex root = states.get(0);
        VertexList vertexList = new VertexList(states, root);
        setParentSiblingNums(vertexList);
        setupLabelHash(labelHash, vertexList);
        return vertexList;
    }

    public VertexList convertKrpToVertexList(String krpFileName, LabelHash labelHash) throws IOException, ExceptionMessage, ExceptionMessage {
        BufferedReader reader;
        Options options = new Options();
        String pathToKrp = options.getPathToKrpDir();
        String krpFilePath = pathToKrp + krpFileName;
        reader = new BufferedReader(new FileReader(krpFilePath));
        String[] krpStrings = getKripkeStringsFromFile(reader, options, true);
        VertexList vertexList = krpStringsToVertexList(krpStrings, labelHash);
        return vertexList;
    }

}
