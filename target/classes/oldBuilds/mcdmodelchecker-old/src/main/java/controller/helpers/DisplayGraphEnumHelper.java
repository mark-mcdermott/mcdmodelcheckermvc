package controller.helpers;

import controller.dataTypes.graphHelpers.DisplayGraphsEnum;

import static controller.dataTypes.graphHelpers.DisplayGraphsEnum.*;

public class DisplayGraphEnumHelper {

    public static DisplayGraphsEnum displayGraphStringToEnum(String selectedDisplayGraphStr, String displayGraphStringSet) {
        DisplayGraphsEnum displayGraphEnum;
        String[] displayGraphArr = displayGraphStringSet.split(",");
        // these assignments will be based on the the way the analyzerDisplayGraphListItems string is written in Options.java:192.
        // right now the string is "all 3 graphs,xml only,trans. only,inter. only,trans. comp.,inter. comp."
        switch (selectedDisplayGraphStr) {
            case "all 3 graphs":
                displayGraphEnum = ALL_GRAPHS;
                break;
            case "xml only":
                displayGraphEnum = XML_ONLY;
                break;
            case "trans. only":
                displayGraphEnum = TRANSLATION_ONLY;
                break;
            case "inter. only":
                displayGraphEnum = INTERLEAVINGS_ONLY;
                break;
            case "trans. comp.":
                displayGraphEnum = TRANSLATION_COMPARISON;
                break;
            case "inter. comp.":
                displayGraphEnum = INTERLEAVINGS_COMPARISON;
                break;
            default:
                displayGraphEnum = ALL_GRAPHS;
        }
        return displayGraphEnum;
    }

    public static String displayGraphEnumToString(DisplayGraphsEnum graphEnum, String displayGraphStringSet) {
        String displayGraphString = new String();
        String[] displayGraphArr = displayGraphStringSet.split(",");
        // these assignments will be based on the the way the analyzerDisplayGraphListItems string is written in Options.java:192.
        // right now the string is "all 3 graphs,xml only,trans. only,inter. only,trans. comp.,inter. comp."
        String allThreeGraphs = displayGraphArr[0];
        String xmlOnly = displayGraphArr[1];
        String transOnly = displayGraphArr[2];
        String interOnly = displayGraphArr[3];
        String transComp = displayGraphArr[4];
        String interComp = displayGraphArr[5];
        switch (graphEnum) {
            case ALL_GRAPHS:
                displayGraphString = allThreeGraphs;
                break;
            case XML_ONLY:
                displayGraphString = xmlOnly;
                break;
            case TRANSLATION_ONLY:
                displayGraphString = transOnly;
                break;
            case INTERLEAVINGS_ONLY:
                displayGraphString = interOnly;
                break;
            case TRANSLATION_COMPARISON:
                displayGraphString = transComp;
                break;
            case INTERLEAVINGS_COMPARISON:
                displayGraphString = interComp;
                break;
            default:
                displayGraphString = allThreeGraphs;
        }
        return displayGraphString;
    }

}
