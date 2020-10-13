package view;

import _options.DirectedGraphOptions;
import controller.types.graph.Vertex;
import controller.types.graph.VertexList;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ViewScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import org.apache.commons.collections15.Transformer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static controller.types.analyzer.analyzerData.DisplayType.INTER_ONLY;

public class DrawGraph {

    private Integer nodeDiameter;
    private Integer level;
    private Integer numEdges;
    private Integer canvasWidth;
    private Integer canvasHeight;
    private Integer vertexSiblingOffset;
    private Integer vertexVertMultiplier;
    private Integer layoutWidth;
    private Integer layoutHeight;
    private float scaleFactor;
    private double vertexAttractionMultiplier;

    // TODO: delete these?
    public static ArrayList<Vertex> tempPlacedVertices = new ArrayList<Vertex>();
    public static ArrayList<ArrayList<Double>> tempXyCoords;

    public DrawGraph(DirectedGraphOptions directedGraphOptions) {
        this.nodeDiameter = directedGraphOptions.getNodeDiameter();
        this.level = directedGraphOptions.getLevel();
        this.numEdges = directedGraphOptions.getNumEdges();
        this.canvasWidth = directedGraphOptions.getCanvasWidth();
        this.canvasHeight = directedGraphOptions.getCanvasHeight();
        this.vertexSiblingOffset = directedGraphOptions.getVertexSiblingOffset();
        this.vertexVertMultiplier = directedGraphOptions.getVertexVertMultiplier();
        this.layoutWidth = directedGraphOptions.getLayoutWidth();
        this.layoutHeight = directedGraphOptions.getLayoutHeight();
        this.scaleFactor = directedGraphOptions.getScaleFactor();
        this.vertexAttractionMultiplier = directedGraphOptions.getVertexAttractionMultiplier();
    }

    public void drawGraph(JPanel thisGraphPanel, VertexList vertexList) {

        // clear temp lists between each graph
        tempPlacedVertices = new ArrayList<>();
        tempXyCoords = new ArrayList<>();

        // init vars
        VisualizationViewer<Vertex,Integer> visualizationViewer;
        KeyListener graphMouseKeyListener;

        // create directed graph
        DirectedGraph<Vertex, Integer> graph = new DirectedSparseMultigraph<Vertex, Integer>();

        // create jung layout and visualization viewer
        FRLayout layout = new FRLayout<Vertex, Integer>(graph);
        visualizationViewer = new VisualizationViewer<Vertex,Integer>(layout);

        // set dimensions
        layout.setSize(new Dimension(layoutWidth, layoutHeight));
        layout.setAttractionMultiplier(vertexAttractionMultiplier);
        visualizationViewer.setPreferredSize(new Dimension(layoutWidth, layoutHeight));
        visualizationViewer.setBackground(Color.white);

        // grey vertices
        Transformer<Vertex, Paint> vertexPaint = new Transformer<Vertex, Paint>() {
            public Paint transform(Vertex i) { return Color.LIGHT_GRAY; }
        };

        // shrink the vertices a bit
        visualizationViewer.getRenderContext().setVertexShapeTransformer(n -> {
            int xyOffset = nodeDiameter / -2; // xyOffset recenters the vertices after shrinking them
            return new Ellipse2D.Double(xyOffset, xyOffset, nodeDiameter, nodeDiameter);
        });

        visualizationViewer.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        visualizationViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        visualizationViewer.getRenderer().getVertexLabelRenderer().setPosition(edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.E);

        // shrink the whole tree a bit
        ScalingControl scaler = new ViewScalingControl();
        scaler.scale(visualizationViewer, scaleFactor, visualizationViewer.getCenter());

        // setup mouse zoom
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse<Vertex, Number>();
        visualizationViewer.setGraphMouse(graphMouse);
        visualizationViewer.addKeyListener((graphMouse.getModeKeyListener()));
        GraphZoomScrollPane graphZoomScrollPane = new GraphZoomScrollPane(visualizationViewer);
        thisGraphPanel.add(graphZoomScrollPane);
        visualizationViewer.setGraphMouse(graphMouse);
        graphMouseKeyListener = graphMouse.getModeKeyListener();
        visualizationViewer.addKeyListener(graphMouseKeyListener);

        // place root vertex
        tempXyCoords = new ArrayList<ArrayList<Double>>();
        Point2D.Double rootXyCoords = calcXYCoords(layoutWidth, level, vertexVertMultiplier, null, 0, 1, vertexSiblingOffset, 0);
        placeVertex(vertexList.getRoot(), rootXyCoords, layout);

        // place vertices and add edges
        if (vertexList.getRoot().getChildren() != null) {
            placeChildrenRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset);
        }
        for (Vertex vertex : vertexList.getList()) {
            if (vertex.getChildren()!= null) {
                for (Vertex child : vertex.getChildren()) {
                    graph.addEdge(numEdges, vertex, child);
                    numEdges++;
                }
            }
        }



    }

    public void drawGraphAndMakePdf(JPanel thisGraphPanel, VertexList vertexList) throws IOException {

        // clear temp lists between each graph
        tempPlacedVertices = new ArrayList<>();
        tempXyCoords = new ArrayList<>();

        // init vars
        VisualizationViewer<Vertex,Integer> visualizationViewer;
        KeyListener graphMouseKeyListener;

        // create directed graph
        DirectedGraph<Vertex, Integer> graph = new DirectedSparseMultigraph<Vertex, Integer>();

        // create jung layout and visualization viewer
        FRLayout layout = new FRLayout<Vertex, Integer>(graph);
        visualizationViewer = new VisualizationViewer<Vertex,Integer>(layout);

        // set dimensions
        layout.setSize(new Dimension(layoutWidth, layoutHeight));
        layout.setAttractionMultiplier(vertexAttractionMultiplier);
        visualizationViewer.setPreferredSize(new Dimension(layoutWidth, layoutHeight));
        visualizationViewer.setBackground(Color.white);

        // grey vertices
        Transformer<Vertex, Paint> vertexPaint = new Transformer<Vertex, Paint>() {
            public Paint transform(Vertex i) { return Color.LIGHT_GRAY; }
        };

        // shrink the vertices a bit
        visualizationViewer.getRenderContext().setVertexShapeTransformer(n -> {
            int xyOffset = nodeDiameter / -2; // xyOffset recenters the vertices after shrinking them
            return new Ellipse2D.Double(xyOffset, xyOffset, nodeDiameter, nodeDiameter);
        });

        visualizationViewer.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        visualizationViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        visualizationViewer.getRenderer().getVertexLabelRenderer().setPosition(edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.E);

        // shrink the whole tree a bit
        ScalingControl scaler = new ViewScalingControl();
        scaler.scale(visualizationViewer, scaleFactor, visualizationViewer.getCenter());

        // setup mouse zoom
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse<Vertex, Number>();
        visualizationViewer.setGraphMouse(graphMouse);
        visualizationViewer.addKeyListener((graphMouse.getModeKeyListener()));
        GraphZoomScrollPane graphZoomScrollPane = new GraphZoomScrollPane(visualizationViewer);
        thisGraphPanel.add(graphZoomScrollPane);
        visualizationViewer.setGraphMouse(graphMouse);
        graphMouseKeyListener = graphMouse.getModeKeyListener();
        visualizationViewer.addKeyListener(graphMouseKeyListener);

        // place root vertex
        tempXyCoords = new ArrayList<ArrayList<Double>>();
        Point2D.Double rootXyCoords = calcXYCoords(layoutWidth, level, vertexVertMultiplier, null, 0, 1, vertexSiblingOffset, 0);
        placeVertex(vertexList.getRoot(), rootXyCoords, layout);

        // place vertices and add edges
        if (vertexList.getRoot().getChildren() != null) {
            placeChildrenRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset);
        }
        for (Vertex vertex : vertexList.getList()) {
            if (vertex.getChildren()!= null) {
                for (Vertex child : vertex.getChildren()) {
                    graph.addEdge(numEdges, vertex, child);
                    numEdges++;
                }
            }
        }

        // image export stuff
        // code/approach from https://stackoverflow.com/a/10426669, accessed 10/12/20
        // i'm converting these pngs to pdfs in photoshop and then compressing on https://www.ilovepdf.com

        // this is random but works. something is overriding all size input specs, it's annoying.
        Integer visLayoutWidth = 7000;
        Integer visLayoutHeight = 7000;

        System.out.println(visLayoutWidth.toString() + " " + layoutHeight.toString());
        VisualizationImageServer<Vertex,Integer> vis = new VisualizationImageServer<Vertex,Integer>(layout, new Dimension(visLayoutWidth, visLayoutHeight));

        // set dimensions
        layout.setSize(new Dimension(visLayoutWidth, visLayoutHeight));
        layout.setAttractionMultiplier(vertexAttractionMultiplier);
        vis.setPreferredSize(new Dimension(visLayoutWidth, visLayoutHeight));
        vis.setBackground(Color.white);
        // shrink the vertices a bit
        vis.getRenderContext().setVertexShapeTransformer(n -> {
            int xyOffset = nodeDiameter / -2; // xyOffset recenters the vertices after shrinking them
            return new Ellipse2D.Double(xyOffset, xyOffset, nodeDiameter, nodeDiameter);
        });

        vis.getRenderContext().setVertexFillPaintTransformer(vertexPaint);
        vis.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
        vis.getRenderer().getVertexLabelRenderer().setPosition(edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position.E);
        // shrink the whole tree a bit
        scaler.scale(vis, scaleFactor, vis.getCenter());

        BufferedImage image = (BufferedImage) vis.getImage(
                new Point2D.Double(visLayoutWidth / 2, layoutHeight / 2), new Dimension(visLayoutWidth, visLayoutHeight));
        File outputfile = new File("src/main/resources/images/test.png");

        try {
            ImageIO.write(image, "png", outputfile);
        } catch (IOException e) {
            // Exception handling
        }

    }

    // graph helpers

    public static Point2D.Double calcXYCoords(Integer canvasWidth, Integer level, Integer vertexVertMultiplier, Integer parentHorizPos, Integer numChild, Integer numChildren, Integer vertexSiblingOffset, Integer parentSiblingNum) {
        Float horizCenter = canvasWidth / 2f - 12;
        Integer x = horizCenter.intValue();
        Integer thisNodePos = x;
        Integer leftChildPos = x;
        if (parentHorizPos == null) {
            x = horizCenter.intValue();
        } else {
            Integer initPos = parentHorizPos;

            // System.out.println("leftChildPos = initPos - ((numChildren - 1) * vertexSiblingOffset / 2) - (vertexSiblingOffset * (level - 1) / 2) + parentSiblingNum * vertexSiblingOffset;");
            if (numChildren == 1) {
                leftChildPos = initPos - ((numChildren) * vertexSiblingOffset) + parentSiblingNum * vertexSiblingOffset;
            } else {
                leftChildPos = initPos - ((numChildren - 1) * vertexSiblingOffset / 2) - (vertexSiblingOffset) + parentSiblingNum * vertexSiblingOffset;
            }
            // System.out.println(leftChildPos+" = "+initPos+" - (("+numChildren+" - 1) * "+vertexSiblingOffset+" / 2) - ("+vertexSiblingOffset+" * ("+level+" - 1) / 2) + "+parentSiblingNum+" * "+vertexSiblingOffset+"\n");
            // leftChildPos = initPos - ((numChildren - 1) * vertexSiblingOffset / 2) - (vertexSiblingOffset * (level - 1)) + parentSiblingNum * vertexSiblingOffset;
            if (leftChildPos != null && vertexSiblingOffset != null && numChild != null) {
                thisNodePos = leftChildPos + vertexSiblingOffset * (numChild + 1);
            }
            x = thisNodePos;
        }
        Integer y = level * vertexVertMultiplier;
        return new Point2D.Double(x, y);
    }

    public static void placeVertex(Vertex vertex, Point2D.Double xyCoords, Layout layout) {
        layout.setLocation(vertex,xyCoords);
        layout.lock(vertex, true);
        Integer xPosInt = ((Integer) (int) Math.round(xyCoords.x));
        Integer yPosInt = ((Integer) (int) Math.round(xyCoords.y));
        vertex.setTranslationGraphPos(new Point2D.Double(xPosInt, yPosInt));
        tempPlacedVertices.add(vertex);
    }

    public static void placeChildrenRecursively(Vertex node, Integer level, Integer canvasWidth, Integer vertexVertMultiplier, FRLayout layout, Integer vertexSiblingOffset) {
        level++;
        if (node.getChildren() != null) {
            Integer numChildren = node.getChildren().size();
            for (Integer i=0; i<numChildren; i++) {
                Vertex child = node.getChildren().get(i);
                child.setSiblingNum(i);

                // calculate xy coordinates
                Point2D.Double xyCoords = calcXYCoords(canvasWidth, level, vertexVertMultiplier, (int) node.getTranslationGraphPos().getX(), i, numChildren, vertexSiblingOffset, node.getSiblingNum());

                // if not placed, then placed vertex
                if (!tempPlacedVertices.contains(child)) {

                    // this fixes the rare node collision where two nodes print directly on top of each other
                    Double childX = xyCoords.x;
                    Double childY = xyCoords.y;
                    for (ArrayList<Double> thisTempXyCoords : tempXyCoords) {
                        if (thisTempXyCoords.get(0) != null && thisTempXyCoords.get(1) != null) {
                            Double thisX = thisTempXyCoords.get(0);
                            Double thisY = thisTempXyCoords.get(1);
                            if (childX.equals(thisX) && childY.equals(thisY)) {
                                xyCoords.x = childX + 100;
                            }
                        }
                    }

                    // place vertex
                    ArrayList<Double> childXyCoords = new ArrayList<Double>();
                    childXyCoords.add(childX);
                    childXyCoords.add(childY);
                    tempXyCoords.add(childXyCoords);
                    placeVertex(child, xyCoords, layout);

                    // run this on on children recursively
                    if (child.getChildren() != null) {
                        placeChildrenRecursively(child, level, canvasWidth, vertexVertMultiplier, layout, vertexSiblingOffset);
                    }
                }
            }
        }
    }


}
