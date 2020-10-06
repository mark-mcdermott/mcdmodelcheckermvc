package controller.dataTypes.graphItems;

import _options.Options;
import controller.dataTypes.ctlHelpers.ModelCheckResult;
import controller.dataTypes.graphHelpers.*;
import controller.dataTypes.pageHelpers.PageEnum;
import controller.dataTypes.testHelpers.FileTest;
import controller.dataTypes.testHelpers.FileTestSet;
import controller.helpers.ListHelper;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;

public class GraphModelObj {

    private VertexList xmlVertexList;                 // this stuff is the only stuff that should stay here
    private VertexList translationVertexList;
    private VertexList interleavingsVertexList;
    private Kripke xmlKripke;
    private Kripke translationKripke;
    private Kripke interleavingsKripke;
    private FileSelectResult fileSelectResult;
    private ModelCheckResult modelCheckResult;
    private Vertex selectedState;
    // private Integer numSteps;
    // private Integer numTargetSteps;

    public FileSelectResult fileSelectResult() {
        return fileSelectResult;
    }

    public void setSelectedStateToInitialState() {
        Vertex initialState = interleavingsVertexList.getRoot();
        setSelectedState(initialState);
    }

    // getters/setters

    public void setFileSelectResult(FileSelectResult fileSelectResult) {
        this.fileSelectResult = fileSelectResult;
    }

    public void setModelCheckResult(ModelCheckResult modelCheckResult) {
        this.modelCheckResult = modelCheckResult;
    }

    public void setXmlVertexList(VertexList xmlVertexList) {
        this.xmlVertexList = xmlVertexList;
    }

    public VertexList getXmlVertexList() {
        return xmlVertexList;
    }

    public VertexList getTranslationVertexList() {
        return translationVertexList;
    }

    public void setTranslationVertexList(VertexList translationVertexList) {
        this.translationVertexList = translationVertexList;
    }

    public VertexList getInterleavingsVertexList() {
        return interleavingsVertexList;
    }

    public void setInterleavingsVertexList(VertexList interleavingsVertexList) {
        this.interleavingsVertexList = interleavingsVertexList;
    }

    public Kripke getInterleavingsKripke() {
        return interleavingsKripke;
    }

    public void setInterleavingsKripke(Kripke interleavingsKripke) {
        this.interleavingsKripke = interleavingsKripke;
    }

    /*
    public String getSelectedStateStr() {
        return selectedStateStr;
    }
    */

    /*
    public void setSelectedStateStr(String selectedStateStr) {
        this.selectedStateStr = selectedStateStr;
    }
    */

    public ModelCheckResult getModelCheckResult() {
        return modelCheckResult;
    }

    // public Integer getSelectedStateIndex() {
    //     return selectedStateIndex;
    // }

    // public void setSelectedStateIndex(Integer selectedStateIndex) {
    //     this.selectedStateIndex = selectedStateIndex;
    // }

    public Vertex getSelectedState() {
        return selectedState;
    }

    public void setSelectedState(Vertex selectedState) {
        this.selectedState = selectedState;
    }

    /*
    public Integer getSelectedLabelIndex() {
        return selectedLabelIndex;
    }

    public void setSelectedLabelStr(String selectedLabelStr) {
        this.selectedLabelStr = selectedLabelStr;
    }

    public String getSelectedLabelStr() {
        return selectedLabelStr;
    }

    public void setSelectedLabelIndex(Integer selectedLabelIndex) {
        this.selectedLabelIndex = selectedLabelIndex;
    }
    */

    /*
    public Integer getNumTargetSteps() {
        return numTargetSteps;
    }

    public void setNumTargetSteps(Integer numTargetSteps) {
        this.numTargetSteps = numTargetSteps;
    }
    */

    public Kripke getXmlKripke() {
        return xmlKripke;
    }

    public Kripke getTranslationKripke() {
        return translationKripke;
    }

    public FileSelectResult getFileSelectResult() {
        return fileSelectResult;
    }

    /*
    public Integer getNumSteps() {
        return numSteps;
    }

    public void setNumSteps(Integer numSteps) {
        this.numSteps = numSteps;
    }
    */

}
