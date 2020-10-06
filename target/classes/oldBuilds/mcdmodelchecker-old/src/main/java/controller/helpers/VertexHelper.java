package controller.helpers;

import controller.dataTypes.graphItems.GraphModelObj;
import controller.dataTypes.graphItems.Vertex;
import model.Model;

import java.util.ArrayList;

public class VertexHelper {

    public Vertex vertexStringToVertex(String vertexStr, ArrayList<Vertex> vertices) {
        for (Vertex thisVertex : vertices) {
            String thisVertexName = "s" + thisVertex.getNumber();
            if (thisVertexName.equals(vertexStr)) {
                return thisVertex;
            }
        }
        return null;
    }

    public Vertex selectedValueToVertex(Object selectedStateValue, Model model, GraphModelObj graphModel) {
        String selectedStateStr = selectedStateValue.toString();
        ArrayList<Vertex> states = graphModel.getInterleavingsVertexList().getList();
        Vertex selectedState = vertexStringToVertex(selectedStateStr, states);
        return selectedState;
    }



}
