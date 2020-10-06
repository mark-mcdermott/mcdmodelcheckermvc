package mcdModelChecker.controller.types.graph;

import mcdModelChecker.controller.exceptions.McdException;
import mcdModelChecker.controller.types.kripke.Label;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class LabelHash {

    private TreeMap<Label,String> labelHash;
    private Label currentLabel;

    public LabelHash() {
        labelHash = new TreeMap<Label,String>(new LabelComparator());
        currentLabel = new Label('p');
    }

    public Label getNextLabel() {
        char label = currentLabel.getLabelChar();
        currentLabel.setLabel(label++);
        return currentLabel;
    }

    public Label getCurrentLabel() {
        return currentLabel;
    }

    public Label getCopyOfCurrentLabel() {
        char currentChar = currentLabel.getLabelChar();
        Label newLabel = new Label(currentChar);
        return newLabel;
    }

    public Label copyLabel(Label label) {
        char thisChar = label.getLabelChar();
        Label newLabel = new Label(thisChar);
        return newLabel;
    }

    public char getCurrentLabelChar() {
        return currentLabel.getLabelChar();
    }

    public String getCurrentLabelStr() {
        return String.valueOf(currentLabel.getLabelChar());
    }

    public void advanceCurrentLabel() {
        char incrementedCurrentLabel = currentLabel.getLabelChar();
        incrementedCurrentLabel++;
        currentLabel.setLabel(incrementedCurrentLabel);
    }

    public Boolean doesHashContainProperty(String property) {
        Label label = getLabel(property);
        if (label == null) {
            return false;
        } else {
            return true;
        }
    }

    public void clearLabelHash() {
        labelHash.clear();
    }

    public void addProperty(String property) {
        synchronized (labelHash) {
            Label labelCopy = getCopyOfCurrentLabel();
            if (!doesHashContainProperty(property)) {
                labelHash.put(labelCopy, property);
                advanceCurrentLabel();
            } else {
                // throw exception
            }
        }
    }

    public Label addPropertyReturnLabel (String property) {
        Label labelCopy = getCopyOfCurrentLabel();
        if (!doesHashContainProperty(property)) {
            labelHash.put(labelCopy, property);
            advanceCurrentLabel();
        } else {
            labelCopy = copyLabel(getLabel(property));
        }
        return labelCopy;
    }

    public String[] getLabelDisplayListArr() {
        Object[] labels = labelHash.keySet().toArray();
        Object[] properties = labelHash.values().toArray();
        String[] labelsAndPropsArr = new String[labels.length];
        for (int i=0; i<labels.length; i++) {
            labelsAndPropsArr[i] = labels[i].toString() + " (" + properties[i].toString() + ")";
        }
        return labelsAndPropsArr;
    }

    public ArrayList<String> getLabelDisplayOutput() {
        ArrayList<String> labelsAndPropsArrList = new ArrayList<>();
        Object[] labels = labelHash.keySet().toArray();
        Object[] properties = labelHash.values().toArray();

        for (int i=0; i<labels.length; i++) {
            labelsAndPropsArrList.add(labels[i].toString() + " (" + properties[i].toString() + ")");
        }
        return labelsAndPropsArrList;
    }

    public ArrayList<String> getProperties() {
        ArrayList<String> propsArrList = new ArrayList<>();
        for (Map.Entry<Label, String> entry : labelHash.entrySet()) {
            propsArrList.add(entry.getValue());
        }
        return propsArrList;
    }

    public int getNumProperties() {
        return labelHash.size();
    }

    public ArrayList<Label> getLabels() throws McdException {
        ArrayList<Label> labelsArrList = new ArrayList<>();
        for (Map.Entry<Label, String> entry : labelHash.entrySet()) {
            labelsArrList.add(entry.getKey());
        }
        return labelsArrList;
    }

    public String getPropertiesStr() {
        String propertiesStr = "";
        ArrayList<String> propertiesArr = getProperties();
        int numProperties = propertiesArr.size();
        for (int i=0; i<numProperties; i++) {
            propertiesStr = propertiesStr + propertiesArr.get(i);
            if (i != numProperties - 1) {
                propertiesStr = propertiesStr + ", ";
            }
        }
        return propertiesStr;
    }

    public String getLabelsStr() throws McdException {
        String labelsStr = "";
        ArrayList<Label> labelsArr = getLabels();
        int numLabels = labelsArr.size();
        for (int i=0; i<numLabels; i++) {
            labelsStr = labelsStr + labelsArr.get(i);
            if (i != numLabels - 1) {
                labelsStr = labelsStr + ", ";
            }
        }
        return labelsStr;
    }

    public String getProperty(char label) {
        return labelHash.get(label);
    }

    public Label getLabel(String property) {
        Label label = null;
        for (Map.Entry<Label, String> thisProperty : labelHash.entrySet()) {
            // System.out.println("thisProperty: " + thisProperty.getValue());
            // System.out.println("property: " + property);
            // System.out.println();
            if (property.equals(thisProperty.getValue())) {
                label = thisProperty.getKey();
            }
        }
        return label;
    }

    public TreeMap<Label,String> getLabelHash() {
        return labelHash;
    }


    // helper

    // comparator code from https://www.geeksforgeeks.org/treemap-in-java
    class LabelComparator implements Comparator<Label> {
        public int compare(Label a, Label b) {
            return a.getLabelChar() - b.getLabelChar();
        }
    }

}
