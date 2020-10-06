package mcdModelChecker.controller.graphs.utils;

import mcdModelChecker.controller.types.vertex.Vertex;
import mcdModelChecker.controller.types.vertex.VertexList;

import java.util.ArrayList;

public class GraphUtils {

    public GraphUtils() { }

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
