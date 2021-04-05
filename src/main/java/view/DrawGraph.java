package view;

import _options.DirectedGraphOptions;
import controller.types.analyzer.analyzerData.DisplayType;
import controller.types.graph.*;
import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.*;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationImageServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ViewScalingControl;
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
import java.util.Random;

import static controller.types.analyzer.analyzerData.DisplayType.TRANS_ONLY;
import static controller.types.analyzer.analyzerData.DisplayType.XML_ONLY;
import static controller.types.graph.VertexKind.PARALLEL;
import static controller.types.graph.VertexKind.SEQUENTIAL;
import static controller.types.graph.VertexStatus.*;
import static java.lang.StrictMath.abs;

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
    private Integer collisionXOffset;
    private Integer collisionYOffest;
    private float scaleFactor;
    private double vertexAttractionMultiplier;



    // process checks xml graph
    static HardCodedNode[] hardcodedChecksXml = {
            new HardCodedNode("s0", 125,5 ),
            new HardCodedNode("s1", 98,19 ),
            new HardCodedNode("s2", 120,18 ),
            new HardCodedNode("s3", 115,23 ),
            new HardCodedNode("s4", 94,31 ),
            new HardCodedNode("s5", 95,38 ),
            new HardCodedNode("s6", 105,44 ),
            new HardCodedNode("s7", 100,50 ),
            new HardCodedNode("s8", 103,53 ),
            new HardCodedNode("s9", 109,56 ),
            new HardCodedNode("s10", 121,27 ),
            new HardCodedNode("s11", 136,32 ),
            new HardCodedNode("s12", 130,39 ),
            new HardCodedNode("s13", 146,36 ),
            new HardCodedNode("s14", 135,45 ),
            new HardCodedNode("s15", 140,48 ),
            new HardCodedNode("s16", 145,51 ),
            new HardCodedNode("s17", 150,54 ),
            new HardCodedNode("s18", 150,13 ),
            new HardCodedNode("s19", 160,18 ),
            new HardCodedNode("s20", 147,24 ),
            new HardCodedNode("s21", 160,21 ),
    };

        static HardCodedNode[] hardcodedTransfersXml = {
            new HardCodedNode("s0", 125,5 ),
            new HardCodedNode("s1", 98,19 ),
            new HardCodedNode("s2", 102,24 ),
            new HardCodedNode("s3", 94,37 ),
            new HardCodedNode("s4", 89,41 ),
            new HardCodedNode("s5", 90,46 ),
            new HardCodedNode("s6", 93,51 ),
            new HardCodedNode("s7", 100,56 ),
            new HardCodedNode("s8", 95,60 ),
            new HardCodedNode("s9", 100,63 ),
            new HardCodedNode("s10", 109,66 ),
            new HardCodedNode("s11", 108,54 ),
            new HardCodedNode("s12", 102,31 ),
            new HardCodedNode("s13", 98,34 ),
            new HardCodedNode("s14", 135,45 ),
            new HardCodedNode("s15", 130,48 ),
            new HardCodedNode("s16", 135,51 ),
            new HardCodedNode("s17", 140,54 ),
            new HardCodedNode("s18", 145,57 ),
            new HardCodedNode("s19", 135,14 ),
            new HardCodedNode("s20", 135,17 ),
            new HardCodedNode("s21", 128,28 ),
            new HardCodedNode("s22", 136,31 ),
            new HardCodedNode("s23", 127,34 ),
            new HardCodedNode("s24", 132,40 ),
            new HardCodedNode("s25", 140,21 ),
            new HardCodedNode("s26", 140,24 ),
            new HardCodedNode("s27", 150,26 ),
    };

    static HardCodedNode[] hardcodedBankingParallelXml = {
            new HardCodedNode("s0", 125,5 ),
            new HardCodedNode("s1", 105,15 ),
            new HardCodedNode("s2", 88,18 ),
            new HardCodedNode("s3", 92,21 ),
            new HardCodedNode("s4", 85,30 ),
            new HardCodedNode("s5", 74,35 ),
            new HardCodedNode("s6", 75,38 ),
            new HardCodedNode("s7", 81,44 ),
            new HardCodedNode("s8", 80,50 ),
            new HardCodedNode("s9", 85,53 ),
            new HardCodedNode("s10", 90,47 ),
            new HardCodedNode("s11", 90,33 ),
            new HardCodedNode("s12", 110,32 ),
            new HardCodedNode("s13", 105,39 ),
            new HardCodedNode("s14", 110,41 ),
            new HardCodedNode("s15", 105,45 ),
            new HardCodedNode("s16", 110,48 ),
            new HardCodedNode("s17", 115,51 ),
            new HardCodedNode("s18", 120,54 ),
            new HardCodedNode("s19", 120,18 ),
            new HardCodedNode("s20", 120,23 ),
            new HardCodedNode("s21", 115,26 ),
            new HardCodedNode("s22", 117,30 ),
            new HardCodedNode("s23", 155,15 ),
            new HardCodedNode("s24", 145,21 ),
            new HardCodedNode("s25", 155,41 ),
            new HardCodedNode("s26", 150,43 ),
            new HardCodedNode("s27", 135,50 ),
            new HardCodedNode("s28", 140,55 ),
            new HardCodedNode("s29", 145,59 ),
            new HardCodedNode("s30", 150,62 ),
            new HardCodedNode("s31", 145,67 ),
            new HardCodedNode("s32", 150,72 ),
            new HardCodedNode("s33", 155,75 ),
            new HardCodedNode("s34", 160,82 ),
            new HardCodedNode("s35", 165,45 ),
            new HardCodedNode("s36", 175,50 ),
            new HardCodedNode("s37", 175,55 ),
            new HardCodedNode("s38", 185,60 ),
            new HardCodedNode("s39", 180,65 ),
            new HardCodedNode("s40", 162,70 ),
            new HardCodedNode("s41", 175,75 ),
            new HardCodedNode("s42", 165,18 ),
            new HardCodedNode("s43", 162,24 ),
            new HardCodedNode("s44", 160,32 ),
            new HardCodedNode("s45", 172,34 ),
            new HardCodedNode("s46", 160,36 ),
            new HardCodedNode("s47", 172,38 ),
            new HardCodedNode("s48", 175,26 ),
            new HardCodedNode("s49", 170,28 ),
            new HardCodedNode("s50", 180,30 ),
    };

    static HardCodedNode[] hardcodedCovidXml = {
            new HardCodedNode("s0", 125,5 ),
            new HardCodedNode("s1", 125,20 ),
            new HardCodedNode("s2", 102,24 ),
            new HardCodedNode("s3", 86,37 ),
            new HardCodedNode("s4", 89,41 ),
            new HardCodedNode("s5", 92,46 ),
            new HardCodedNode("s6", 95,51 ),
            new HardCodedNode("s7", 100,56 ),
            new HardCodedNode("s8", 95,60 ),
            new HardCodedNode("s9", 105,63 ),
            new HardCodedNode("s10", 120,66 ),
            new HardCodedNode("s11", 135,69 ),
            new HardCodedNode("s12", 150,20 ),
            new HardCodedNode("s13", 130,30 ),
            new HardCodedNode("s14", 120,33 ),
            new HardCodedNode("s15", 125,36 ),
            new HardCodedNode("s16", 130,39 ),
            new HardCodedNode("s17", 141,43 ),
            new HardCodedNode("s18", 151,46 ),
            new HardCodedNode("s19", 161,49 ),
            new HardCodedNode("s20", 171,52 ),
            new HardCodedNode("s21", 128,28 ),
            new HardCodedNode("s22", 136,31 ),
            new HardCodedNode("s23", 127,34 ),
            new HardCodedNode("s24", 132,40 ),
            new HardCodedNode("s25", 140,21 ),
            new HardCodedNode("s26", 140,24 ),
            new HardCodedNode("s27", 150,26 ),
    };

        static HardCodedNode[] hardcodedCovidTrans = {
            new HardCodedNode("s1", 125,14 ),
            new HardCodedNode("s2", 100,18 ),
            new HardCodedNode("s3", 100,22 ),
            new HardCodedNode("s4", 100,26 ),
            new HardCodedNode("s5", 100,30 ),
            new HardCodedNode("s6", 78,34 ),
            new HardCodedNode("s7", 78,38 ),
            new HardCodedNode("s8", 73,42 ),
            new HardCodedNode("s9", 100,48 ),
            new HardCodedNode("s10", 100,52 ),
            new HardCodedNode("s11", 100,56 ),
            new HardCodedNode("s12", 77,60 ),
            new HardCodedNode("s13", 77,64 ),
            new HardCodedNode("s14", 72,68 ),
            new HardCodedNode("s15", 100,74 ),
            new HardCodedNode("s16", 100,78 ),
            new HardCodedNode("s17", 100,82 ),
            new HardCodedNode("s18", 150,18 ),
            new HardCodedNode("s19", 150,22 ),
            new HardCodedNode("s20", 150,26 ),
            new HardCodedNode("s21", 150,30 ),
            new HardCodedNode("s22", 150,34 ),
            new HardCodedNode("s23", 150,38 ),
            new HardCodedNode("s24", 150,42 ),
            new HardCodedNode("s25", 150,46 ),
            new HardCodedNode("s26", 150,50 ),
            new HardCodedNode("s27", 150,54 ),
            new HardCodedNode("s28", 150,58 ),
            new HardCodedNode("s29", 150,62 ),
            new HardCodedNode("s30", 150,66 ),
            new HardCodedNode("s31", 150,70 ),
            new HardCodedNode("s32", 150,74 ),
            new HardCodedNode("s33", 150,78 ),
            new HardCodedNode("s34", 150,82 ),
            new HardCodedNode("s35", 150,86 ),
            new HardCodedNode("s36", 150,90 ),
            new HardCodedNode("s37", 150,94 ),
            new HardCodedNode("s38", 150,98 ),
            new HardCodedNode("s39", 150,102 ),
            new HardCodedNode("s40", 150,106 ),
            new HardCodedNode("s41", 150,110 ),
            new HardCodedNode("s42", 150,114 ),
            new HardCodedNode("s43", 150,118 ),
            new HardCodedNode("s44", 150,122 ),
            new HardCodedNode("s45", 165,118 ),
            new HardCodedNode("s46", 175,122 ),
            new HardCodedNode("s47", 115,82 ),
            new HardCodedNode("s48", 165,106 ),
            new HardCodedNode("s49", 165,94 ),
            new HardCodedNode("s50", 165,82 ),
            new HardCodedNode("s51", 165,66 ),
            new HardCodedNode("s52", 175,70 ),
            new HardCodedNode("s53", 165,54 ),
            new HardCodedNode("s54", 165,42 ),
            new HardCodedNode("s55", 92,60 ),
            new HardCodedNode("s56", 92,64 ),
            new HardCodedNode("s57", 87,68 ),
            new HardCodedNode("s58", 107,60 ),
            new HardCodedNode("s59", 107,64 ),
            new HardCodedNode("s60", 102,68 ),
            new HardCodedNode("s61", 123,60 ),
            new HardCodedNode("s62", 123,64 ),
            new HardCodedNode("s63", 117,68 ),
            new HardCodedNode("s64", 128,70 ),
            new HardCodedNode("s65", 115,74 ),
            new HardCodedNode("s66", 115,78 ),
            new HardCodedNode("s67", 112,70 ),
            new HardCodedNode("s68", 97,70 ),
            new HardCodedNode("s69", 82,70 ),
            new HardCodedNode("s70", 93,34 ),
            new HardCodedNode("s71", 93,38 ),
            new HardCodedNode("s72", 88,42 ),
            new HardCodedNode("s73", 108,34 ),
            new HardCodedNode("s74", 108,38 ),
            new HardCodedNode("s75", 101,42 ),
            new HardCodedNode("s76", 123,34 ),
            new HardCodedNode("s77", 123,38 ),
            new HardCodedNode("s78", 120,42 ),
            new HardCodedNode("s79", 130,44 ),
            new HardCodedNode("s80", 115,48 ),
            new HardCodedNode("s81", 113,44 ),
            new HardCodedNode("s82", 98,44 ),
            new HardCodedNode("s83", 83,44 ),
    };

    static HardCodedNode[] hardcodedBankingTrans = {
        new HardCodedNode("s1", 125, 14),
        new HardCodedNode("s2", 100, 18),
        new HardCodedNode("s3", 100, 22),
        new HardCodedNode("s4", 100, 26),
        new HardCodedNode("s5", 100, 30),
        new HardCodedNode("s6", 100, 34),
        new HardCodedNode("s7", 100, 38),
        new HardCodedNode("s8", 100, 42),
        new HardCodedNode("s9", 100, 46),
        new HardCodedNode("s10", 100, 50),
        new HardCodedNode("s11", 100, 54),
        new HardCodedNode("s12", 100, 58),
        new HardCodedNode("s13", 100, 62),
        new HardCodedNode("s14", 100, 66),
        new HardCodedNode("s15", 100, 70),
        new HardCodedNode("s16", 100, 74),
        new HardCodedNode("s17", 100, 78),
        new HardCodedNode("s18", 100, 82),
        new HardCodedNode("s19", 100, 86),
        new HardCodedNode("s20", 100, 90),
        new HardCodedNode("s21", 100, 94),
        new HardCodedNode("s22", 100, 98),
        new HardCodedNode("s23", 100, 102),
        new HardCodedNode("s24", 100, 106),
        new HardCodedNode("s25", 100, 110),
        new HardCodedNode("s26", 100, 114),
        new HardCodedNode("s27", 100, 118),
        new HardCodedNode("s28", 100, 122),
        new HardCodedNode("s29", 100, 126),
        new HardCodedNode("s30", 100, 130),
        new HardCodedNode("s31", 100, 134),
        new HardCodedNode("s32", 100, 138),
        new HardCodedNode("s33", 100, 142),
        new HardCodedNode("s34", 100, 146),
        new HardCodedNode("s35", 100, 150),
        new HardCodedNode("s36", 100, 154),
        new HardCodedNode("s37", 100, 158),
        new HardCodedNode("s38", 100, 162),
        new HardCodedNode("s39", 100, 166),
        new HardCodedNode("s40", 100, 170),
        new HardCodedNode("s41", 100, 174),
        new HardCodedNode("s42", 100, 178),
        new HardCodedNode("s43", 100, 182),
        new HardCodedNode("s44", 100, 186),
        new HardCodedNode("s45", 100, 190),
        new HardCodedNode("s46", 100, 194),
        new HardCodedNode("s47", 100, 198),
        new HardCodedNode("s48", 100, 202),
        new HardCodedNode("s49", 100, 206),
        new HardCodedNode("s50", 100, 210),
        new HardCodedNode("s51", 100, 214),
        new HardCodedNode("s52", 100, 218),
        new HardCodedNode("s53", 100, 222),
        new HardCodedNode("s54", 100, 226),
        new HardCodedNode("s55", 100, 230),
        new HardCodedNode("s56", 100, 234),
        new HardCodedNode("s57", 100, 238),
        new HardCodedNode("s58", 100, 242),
        new HardCodedNode("s59", 100, 246),
        new HardCodedNode("s60", 100, 250),
        new HardCodedNode("s61", 100, 254),
        new HardCodedNode("s62", 100, 258),
        new HardCodedNode("s63", 100, 262),
        new HardCodedNode("s64", 100, 266),
        new HardCodedNode("s65", 100, 270),
        new HardCodedNode("s66", 100, 274),
        new HardCodedNode("s67", 100, 278),
        new HardCodedNode("s68", 100, 282),
        new HardCodedNode("s69", 150, 18),
        new HardCodedNode("s70", 150, 22),
        new HardCodedNode("s71", 150, 26),
        new HardCodedNode("s72", 150, 30),
        new HardCodedNode("s73", 150, 34),
        new HardCodedNode("s74", 150, 38),
        new HardCodedNode("s75", 150, 42),
        new HardCodedNode("s76", 150, 46),
        new HardCodedNode("s77", 150, 50),
        new HardCodedNode("s78", 150, 54),
        new HardCodedNode("s79", 150, 58),
        new HardCodedNode("s80", 150, 62),
        new HardCodedNode("s81", 150, 66),
        new HardCodedNode("s82", 150, 70),
        new HardCodedNode("s83", 150, 74),

        new HardCodedNode("s194", 125, 62),
        new HardCodedNode("s195", 125, 30),
};


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
        this.collisionXOffset = directedGraphOptions.getCollisionXOffset();
        this.collisionYOffest = directedGraphOptions.getCollisionYOffest();
        this.scaleFactor = directedGraphOptions.getScaleFactor();
        this.vertexAttractionMultiplier = directedGraphOptions.getVertexAttractionMultiplier();
    }

        public void drawSingleNodeGraph(JPanel thisGraphPanel, VertexList vertexList, DisplayType graphType) {

        // clear temp lists between each graph
        tempPlacedVertices = new ArrayList<>();
        tempXyCoords = new ArrayList<>();

        // init vars
        VisualizationViewer<Vertex,Integer> visualizationViewer;
        KeyListener graphMouseKeyListener;

        // create directed graph
        UndirectedSparseMultigraph<Vertex, Integer> graph = new UndirectedSparseMultigraph<Vertex, Integer>();
        Layout<Vertex, Integer> layout = new CircleLayout<Vertex, Integer>(graph);

        // create jung layout and visualization viewer
        // StaticLayout<Vertex, Integer> layout = new StaticLayout<Vertex, Integer>(graph);
        visualizationViewer = new VisualizationViewer<Vertex,Integer>(layout);

        // set dimensions
        layout.setSize(new Dimension(layoutWidth, layoutHeight));
        // layout.setAttractionMultiplier(vertexAttractionMultiplier);
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
        Point2D.Double rootXyCoords = calcXYCoords(layoutWidth, level, vertexVertMultiplier, null, 0, 1, vertexSiblingOffset, 0, null, false, null, null);
        Vertex rootVertex = vertexList.getRoot();
        VertexKind rootKind = rootVertex.getKind();
        placeVertex(rootVertex, rootXyCoords, layout);

        // place vertices and add edges
        if (vertexList.getRoot().getChildren() != null) {
            placeStaticChildrenRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind, rootXyCoords, rootKind, graphType, collisionXOffset, collisionYOffest);
            /*if (rootKind.equals(SEQUENTIAL) && graphType != XML_ONLY) {
                placeSequentialRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
            } else if (rootKind.equals(PARALLEL) && graphType != XML_ONLY) {
                placeParallelRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
            } else {
                placeChildrenRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
            }*/
        }
        /*
        for (Vertex vertex : vertexList.getList()) {
            if (vertex.getChildren()!= null) {
                for (Vertex child : vertex.getChildren()) {
                    graph.addEdge(numEdges, vertex, child);
                    numEdges++;
                }
            }
        }
        */


    }

    public void drawGraph(JPanel thisGraphPanel, VertexList vertexList, DisplayType graphType, String file) {

        // clear temp lists between each graph
        tempPlacedVertices = new ArrayList<>();
        tempXyCoords = new ArrayList<>();

        // init vars
        VisualizationViewer<Vertex,Integer> visualizationViewer;
        KeyListener graphMouseKeyListener;

        // create directed graph
        DirectedGraph<Vertex, Integer> graph = new DirectedSparseMultigraph<Vertex, Integer>();
        // DirectedGraph<Vertex, Integer> graph = new DirectedGraph<>();

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
        Point2D.Double rootXyCoords = calcXYCoords(layoutWidth, level, vertexVertMultiplier, null, 0, 1, vertexSiblingOffset, 0, null, false, null, null);
        Vertex rootVertex = vertexList.getRoot();
        VertexKind rootKind = rootVertex.getKind();
        placeVertex(rootVertex, rootXyCoords, layout);
        ArrayList<Double> rootCoordsArrList = new ArrayList<>();
        rootCoordsArrList.add(rootXyCoords.x);
        rootCoordsArrList.add(rootXyCoords.y);
        tempXyCoords.add(rootCoordsArrList);

        // place vertices and add edges
        if (vertexList.getRoot().getChildren() != null) {
            placeChildrenRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind, rootXyCoords, rootKind, graphType, collisionXOffset, collisionYOffest, file);
            /*if (rootKind.equals(SEQUENTIAL) && graphType != XML_ONLY) {
                placeSequentialRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
            } else if (rootKind.equals(PARALLEL) && graphType != XML_ONLY) {
                placeParallelRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
            } else {
                placeChildrenRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
            }*/
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

    public void drawGraphAndMakePdf(JPanel thisGraphPanel, VertexList vertexList, VertexKind rootKind, DisplayType graphType, String file) throws IOException {

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
        Point2D.Double rootXyCoords =  calcXYCoords(layoutWidth, level, vertexVertMultiplier, null, 0, 1, vertexSiblingOffset, 0, null, false, null, null);
        placeVertex(vertexList.getRoot(), rootXyCoords, layout);

        // place vertices and add edges
        if (vertexList.getRoot().getChildren() != null) {
            placeChildrenRecursively(vertexList.getRoot(), level, layoutWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind, rootXyCoords, rootKind, graphType, collisionXOffset, collisionYOffest, file);
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


    public static Point2D.Double calcXYSequentialCoords(Integer canvasWidth, Integer level, Integer vertexVertMultiplier, Integer parentHorizPos, Integer numChild, Integer numChildren, Integer vertexSiblingOffset, Integer parentSiblingNum, VertexStatus nodeStatus, String nodeName, Vertex vertex, Point2D parentPos) {
        Float horizCenter = canvasWidth / 2f - 12;
        horizCenter = (float) parentPos.getX();
        double x;
        double mainSlot = horizCenter.intValue();
        double colWidth = vertexSiblingOffset;
        // double middleSlot = leftSlot + vertexSiblingOffset * 1.00001;
        // double rightSlot = leftSlot + vertexSiblingOffset * 1.4;
        String name = nodeName;

        if (parentHorizPos == null || nodeStatus != TERMINATED) { // if not a terminated, goes in left slot
            x = mainSlot;

        } else { // if a terminated, will go to the right of the main slot
                 // how far right depends on if it has a parent that's a terminated. if so, it will go one half column to the right of the farthest right terminal parent
                 // this is to cover situations like these https://imgur.com/a/tEkcUdh

            ArrayList<Vertex> parents = vertex.getParents();
            Double farthestRightTerminalPos = null;
            Integer numTerminalParents = 0;
            for (Vertex parent : parents) {
                if (parent.getStatus() == TERMINATED) {
                    numTerminalParents++;
                }
            }

            if (numTerminalParents == 0) {
                x = mainSlot + colWidth;
            }
            else if (numTerminalParents == 1) {
                x = mainSlot;
            } else {
                for (Vertex parent : parents) {
                    if (parent.getStatus() == TERMINATED) {
                        if (parent.getTranslationGraphPos() != null) {
                            Double thisParentPos = parent.getTranslationGraphPos().getX();
                            if (farthestRightTerminalPos == null) {
                                farthestRightTerminalPos = thisParentPos;
                            } else {
                                if (thisParentPos > farthestRightTerminalPos) {
                                    farthestRightTerminalPos = thisParentPos;
                                }
                            }
                        }
                    }
                }
                if (farthestRightTerminalPos == null) {
                    x = mainSlot + colWidth;
                } else {
                    x = farthestRightTerminalPos + colWidth / 2;
                }
            }

            /*
            ArrayList<Vertex> children = vertex.getChildren();
            Boolean found = false;
            if (children != null) {
                for (Integer i = 0; i < children.size(); i++) {
                    Vertex child = children.get(i);
                    if (tempPlacedVertices.contains(child)) {
                        found = true;
                    }
                }
            }
            if (found) {
                x = rightSlot;
            } else {
                x = middleSlot;
            }
            */

        }

        Integer y = level * vertexVertMultiplier;
        return new Point2D.Double(x, y);
    }

    // https://stackoverflow.com/posts/5271613/revisions
    public static int randomNum(int low, int high) {
        Random r = new Random();
        Integer result = r.nextInt(high - low) + low;
        return result;
    }

        public static Point2D.Double calcXYParallelCoords(Integer canvasWidth, Integer level, Integer vertexVertMultiplier, Integer parentHorizPos, Integer numChild, Integer numChildren, Integer vertexSiblingOffset, Integer parentSiblingNum, VertexStatus nodeStatus, String nodeName, Vertex vertex, Point2D parentPos) {
        Float horizCenter = canvasWidth / 2f - 12;
        horizCenter = (float) parentPos.getX();
        double x;
        double middleSlot = horizCenter.intValue();
        double colWidth = vertexSiblingOffset;

        if (parentHorizPos == null || nodeStatus == POSTED || nodeStatus == STARTED) {
            x = middleSlot;
        } else if (nodeStatus == COMPLETED) {
            // x = middleSlot - colWidth;
            x = middleSlot;
        } else if (nodeStatus == TERMINATED) {
            // x = middleSlot + colWidth;
            x = middleSlot;
        } else { // this is SUBSTEPS

            if (numChildren % 2 == 0) { // if num substeps is even
                Integer halfChildren = numChildren / 2;
                if (numChild <= halfChildren) {
                    x = horizCenter - (halfChildren - numChild - 1) * colWidth + colWidth / 2;
                } else {
                    x = horizCenter + (numChild - halfChildren - 1) * colWidth - colWidth / 2;
                }
            } else { // if num substeps is odd
                Integer middleChild = (int) Math.ceil(numChildren / 2);     // finds the middle number (if 11 children, middle is 6)
                Integer numLeft = (int) Math.floor(numChildren / 2);        // finds the number on either side of the middle number (if 11 children, 5 are to the left of 6 and 5 are to the right of 6)
                Integer colsAwayFromCenter;
                if (numChild == middleChild) {                              // if elem is middle child, place in at horizontal middle
                    x = horizCenter;
                } else if (numChild < middleChild) {
                    colsAwayFromCenter = numLeft - numChild - 1;            // if elem is left of middle, find the number of places it is left of middle (if 11 children, number 3 is 2 away from 5)
                    x = horizCenter - (colsAwayFromCenter * colWidth);      // multiplies the number of places left of middle by the column width and subtracts that from horizontal center
                } else {
                    colsAwayFromCenter = numChild - numLeft - 1;
                    x = horizCenter + (colsAwayFromCenter * colWidth);
                }
            }
        }
        Integer y = level * vertexVertMultiplier;
        return new Point2D.Double(x, y);
    }


    public static Point2D.Double calcXYCoords(Integer canvasWidth, Integer level, Integer vertexVertMultiplier, Integer parentHorizPos, Integer numChild, Integer numChildren, Integer vertexSiblingOffset, Integer parentSiblingNum, Point2D parentPos, Boolean isChildOfParallel, ArrayList<Vertex> siblings, VertexKind prevChildKind) {
        float horizCenter;
        if (parentPos == null) {
            horizCenter = canvasWidth / 2f - 12;
        } else {
            // horizCenter = (float) parentPos.getX();
            horizCenter = canvasWidth / 2f - 12;
        }

        // Integer x = horizCenter.intValue();
        float x = horizCenter;
        float thisNodePos = x;
        float leftChildPos = x;
        if (parentHorizPos == null) {
            // x = horizCenter.intValue();
            x = horizCenter;
        } else {

            if (isChildOfParallel) {
                Integer thisDebugPoint = 0;
            }

            Integer initPos = parentHorizPos;

            // System.out.println("leftChildPos = initPos - ((numChildren - 1) * vertexSiblingOffset / 2) - (vertexSiblingOffset * (level - 1) / 2) + parentSiblingNum * vertexSiblingOffset;");
            if (numChildren == 1) {
                leftChildPos = initPos - ((numChildren) * vertexSiblingOffset) + parentSiblingNum * vertexSiblingOffset;
                // leftChildPos = initPos - ((numChildren) * vertexSiblingOffset);
            } else {
                // leftChildPos = initPos - ((numChildren - 1) * vertexSiblingOffset / 2) - (vertexSiblingOffset) + parentSiblingNum * vertexSiblingOffset;
                leftChildPos = initPos - ((numChildren - 1) * vertexSiblingOffset / 2) - (vertexSiblingOffset);
                // leftChildPos = initPos - ((numChildren - 1) * vertexSiblingOffset / 2);
            }
            // System.out.println(leftChildPos+" = "+initPos+" - (("+numChildren+" - 1) * "+vertexSiblingOffset+" / 2) - ("+vertexSiblingOffset+" * ("+level+" - 1) / 2) + "+parentSiblingNum+" * "+vertexSiblingOffset+"\n");
            // leftChildPos = initPos - ((numChildren - 1) * vertexSiblingOffset / 2) - (vertexSiblingOffset * (level - 1)) + parentSiblingNum * vertexSiblingOffset;
            // if (leftChildPos != null && vertexSiblingOffset != null && numChild != null) {
            if (vertexSiblingOffset != null && numChild != null) {
                thisNodePos = leftChildPos + vertexSiblingOffset * (numChild + 1);
                // thisNodePos = leftChildPos + vertexSiblingOffset * numChild;
            }
            x = thisNodePos;
        }

        Integer randomVertOffset = randomNum(0,75);

        Integer y = (isChildOfParallel) ? (int) parentPos.getY() + vertexVertMultiplier : level * vertexVertMultiplier;
        // Integer y = (isChildOfParallel) ? (int) parentPos.getY() + vertexVertMultiplier : level * vertexVertMultiplier + randomVertOffset;
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

    public static void placeSequentialRecursively(Vertex node, Integer level, Integer canvasWidth, Integer vertexVertMultiplier, FRLayout layout, Integer vertexSiblingOffset, VertexKind rootKind) {
        level++;
        if (node.getChildren() != null) {
            Integer numChildren = node.getChildren().size();
            Point2D parentPos = node.getTranslationGraphPos();
            for (Integer i=0; i<numChildren; i++) {
                Vertex child = node.getChildren().get(i);
                child.setSiblingNum(i);
                Point2D.Double xyCoords = calcXYSequentialCoords(canvasWidth, level, vertexVertMultiplier, (int) node.getTranslationGraphPos().getX(), i, numChildren, vertexSiblingOffset, node.getSiblingNum(), child.getStatus(), child.toString(), child, parentPos);

                // if not placed, then placed vertex
                if (!tempPlacedVertices.contains(child)) {

                    Double childX = xyCoords.x;
                    Double childY = xyCoords.y;

                    // place vertex
                    ArrayList<Double> childXyCoords = new ArrayList<Double>();
                    childXyCoords.add(childX);
                    childXyCoords.add(childY);
                    tempXyCoords.add(childXyCoords);
                    placeVertex(child, xyCoords, layout);

                    // run this on on children recursively
                    if (child.getChildren() != null) {
                        placeSequentialRecursively(child, level, canvasWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
                    }

                }

            }
        }
    }


        public static void placeParallelRecursively(Vertex node, Integer level, Integer canvasWidth, Integer vertexVertMultiplier, FRLayout layout, Integer vertexSiblingOffset, VertexKind rootKind) {
        level++;
        if (node.getChildren() != null) {
            Integer numChildren = node.getChildren().size();
            Point2D parentPos = node.getTranslationGraphPos();
            for (Integer i=0; i<numChildren; i++) {
                Vertex child = node.getChildren().get(i);
                child.setSiblingNum(i);
                Point2D.Double xyCoords = calcXYParallelCoords(canvasWidth, level, vertexVertMultiplier, (int) node.getTranslationGraphPos().getX(), i, numChildren, vertexSiblingOffset, node.getSiblingNum(), child.getStatus(), child.toString(), child, parentPos);

                // if not placed, then placed vertex
                if (!tempPlacedVertices.contains(child)) {

                    Double childX = xyCoords.x;
                    Double childY = xyCoords.y;

                    // place vertex
                    ArrayList<Double> childXyCoords = new ArrayList<Double>();
                    childXyCoords.add(childX);
                    childXyCoords.add(childY);
                    tempXyCoords.add(childXyCoords);
                    placeVertex(child, xyCoords, layout);

                    // run this on on children recursively
                    if (child.getChildren() != null) {
                        placeParallelRecursively(child, level, canvasWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
                    }

                }

            }
        }
    }

    public static Boolean checkNodeForCollision(ArrayList<Double> oldNode, Point2D.Double thisNode) {
        Boolean collision = false;
        if (abs(oldNode.get(0) - thisNode.x) <= 50 && abs(oldNode.get(1) - thisNode.y) < 10) {
            collision = true;
        } else {
            collision = false;
        }
        return collision;
    }

    public static Double getYCollision(ArrayList<ArrayList<Double>> oldNodes, Point2D.Double thisNode) {
        Double collisionY = -1.0;
        for (ArrayList<Double> oldNode : oldNodes) {
            if (checkNodeForCollision(oldNode, thisNode)) {
                collisionY = thisNode.y;
                return collisionY;
            }
        }
        return collisionY;
    }

    public static Point2D.Double collisionCorrect(ArrayList<ArrayList<Double>> oldNodes, Point2D.Double thisNode, Integer yOffset, Integer level, Integer numChild, String name) {
        if (level == 2 && numChild == 2) {
            thisNode.x = thisNode.x + 30;
        }
        Double yCollisionPoint = getYCollision(oldNodes, thisNode);
        if (yCollisionPoint != -1.0) {
            thisNode.y = thisNode.y - yOffset;
        }
        while (yCollisionPoint != -1.0) {
            yCollisionPoint = getYCollision(oldNodes, thisNode);
            if (yCollisionPoint != -1.0) {
                thisNode.y = thisNode.y - yOffset;
            }
        }
        return thisNode;
    }

    private static Point2D.Double getHardcodedCoordsHelper (HardCodedNode[] hardcodedNodesArr, String nodeName) {
        for (HardCodedNode hardCodedNode : hardcodedNodesArr) {
            String hardCodedNodeName = hardCodedNode.getName();
            if (hardCodedNodeName.equals(nodeName)) {
                return hardCodedNode.getXy();
            }
        }
        return null;
    }

    public static Point2D.Double getHardcodedCoords(String file, DisplayType graphType, String nodeName) {
        if (graphType == XML_ONLY) {
            if (file.equals("ProcessChecks.ljx")) {
                return getHardcodedCoordsHelper(hardcodedChecksXml, nodeName);
            } else if (file.equals("ProcessTransfers.ljx")) {
                return getHardcodedCoordsHelper(hardcodedTransfersXml, nodeName);
            } else if (file.equals("Bank-Parallel.ljx")) {
                return getHardcodedCoordsHelper(hardcodedBankingParallelXml, nodeName);
            } else if (file.equals("Covid.ljx")) {
                return getHardcodedCoordsHelper(hardcodedCovidXml, nodeName);
            }
        }
        if (graphType == TRANS_ONLY) {
            if (file.equals("Covid.ljx")) {
                return getHardcodedCoordsHelper(hardcodedCovidTrans, nodeName);
            }
            if (file.equals("Bank-Parallel.ljx")) {
                return getHardcodedCoordsHelper(hardcodedBankingTrans, nodeName);
            }
        }
        return null;
    }



    public static void placeChildrenRecursively(Vertex node, Integer level, Integer canvasWidth, Integer vertexVertMultiplier, FRLayout layout, Integer vertexSiblingOffset, VertexKind vertexKind, Point2D.Double nodeXyCoords, VertexKind rootKind, DisplayType graphType, Integer collisionXOffset, Integer collisionYOffest, String file) {
        level++;
        if (node.getChildren() != null) {
            Point2D parentPos = node.getTranslationGraphPos();
            Integer numChildren = node.getChildren().size();
            Vertex prevChild = null;
            VertexKind prevChildKind = null;

            for (Integer i=0; i<numChildren; i++) {
                Integer numChild = i;
                Vertex child = node.getChildren().get(i);
                child.setSiblingNum(i);
                VertexKind childKind = child.getKind();
                String childName = child.getName();
                if (i>0) {
                    prevChild = node.getChildren().get(i-1);
                    prevChildKind = prevChild.getKind();
                }

                // check if child of parallel (special case)
                Boolean isChildOfParallel = false;
                for (Vertex parent : child.getParents()) {
                    if (parent.getKind() == PARALLEL && parent.getStatus() == STARTED) {
                        Vertex parStarted = parent;
                        isChildOfParallel = true;
                        parentPos = parStarted.getTranslationGraphPos();  // use the parentPos of the parent parallel started node, not just the node we traversed in on (this child is positioned directly underneath the parallel started node)

                        // get correct sibling num (check how many children of parStarted are already placed)
                        ArrayList<Vertex> childrenOfParallel = parStarted.getChildren();
                        Integer numPlaced = 0;
                        for (Vertex thisChildOfParallel : childrenOfParallel) {
                            if (tempPlacedVertices.contains(thisChildOfParallel)) {
                                numPlaced++;
                            }
                        }
                        child.setSiblingNum(numPlaced);

                        // get correct level
                        double parStartedYPos = parStarted.getTranslationGraphPos().getY();
                        level = (int) parStartedYPos / vertexVertMultiplier + 1;

                    }
                }

                // calculate xy coordinates
                Point2D.Double xyCoords;

                Point2D.Double hardcodedCoords = getHardcodedCoords(file, graphType, child.getName());
                if (hardcodedCoords != null) {
                    xyCoords = hardcodedCoords;
                } else if (!isChildOfParallel && (childKind == SEQUENTIAL || rootKind == SEQUENTIAL) && graphType != XML_ONLY) {
                    xyCoords = calcXYSequentialCoords(canvasWidth, level, vertexVertMultiplier, (int) node.getTranslationGraphPos().getX(), i, numChildren, vertexSiblingOffset, node.getSiblingNum(), child.getStatus(), child.toString(), child, parentPos);
                } else if (!isChildOfParallel && childKind == PARALLEL) {
                    xyCoords = calcXYParallelCoords(canvasWidth, level, vertexVertMultiplier, (int) node.getTranslationGraphPos().getX(), i, numChildren, vertexSiblingOffset, node.getSiblingNum(), child.getStatus(), child.toString(), child, parentPos);
                } else {
                    xyCoords = calcXYCoords(canvasWidth, level, vertexVertMultiplier, (int) parentPos.getX(), child.getSiblingNum(), numChildren, vertexSiblingOffset, node.getSiblingNum(), parentPos, isChildOfParallel, node.getChildren(), prevChildKind);
                }

                // if not placed, then placed vertex
                if (!tempPlacedVertices.contains(child)) {

                    Double childX = xyCoords.x;
                    Double childY = xyCoords.y;
                    ArrayList<Double> doubleArrCoords = new ArrayList<>();
                    doubleArrCoords.add(childX);
                    doubleArrCoords.add(childY);
                    Point2D.Double childXY = new Point2D.Double(childX,childY);

                    Double collisionY = -1.0;
                    Double oldY;
                    do {
                        oldY = childY;
                        // childY = collisionCorrect(tempXyCoords,childXY,collisionYOffest);
                    } while (oldY != childY);

                    // collisionCorrect(tempXyCoords, xyCoords);
                    childY = xyCoords.y;

                    // this fixes the rare node collision where two nodes print directly on top of each other
                    /*
                    Boolean collision = false;
                    for (ArrayList<Double> thisTempXyCoords : tempXyCoords) {
                        if (thisTempXyCoords.get(0) != null && thisTempXyCoords.get(1) != null) {
                            Double thisX = thisTempXyCoords.get(0);
                            Double thisY = thisTempXyCoords.get(1);
                            // if (childX.equals(thisX) && childY.equals(thisY)) {
                            if (abs(childX - thisX) <= 50 && abs(childY - thisY) < 10) {
                                collision = true;
                            // if (abs(childY - thisY) <= 10) {

                                // xyCoords.x = childX + collisionXOffset;
                                // Integer randomYOffset = randomNum(25,100);
                                Double newY = childY - collisionYOffest;
                                xyCoords.y = newY;
                                childY = newY;
                                // xyCoords.y = childY + randomYOffset;
                            }
                        }
                    }
                    */

                    // place vertex
                    ArrayList<Double> childXyCoords = new ArrayList<Double>();
                    Point2D.Double childXyCoordsPoint2D = new Point2D.Double(childX,childY);
                    childXyCoords.add(childX);
                    childXyCoords.add(childY);
                    tempXyCoords.add(childXyCoords);
                    // placeVertex(child, xyCoords, layout, xyCoords, childXyCoords);
                    placeVertex(child, xyCoords, layout);

                    // run this on on children recursively
                    if (child.getChildren() != null) {
                        // placeChildrenRecursively(child, level, canvasWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
                        placeChildrenRecursively(child, level, canvasWidth, vertexVertMultiplier, layout, vertexSiblingOffset, childKind, childXyCoordsPoint2D, rootKind, graphType, collisionXOffset, collisionYOffest, file);
                    }
                }
            }
        }
    }

    public static void placeStaticChildrenRecursively(Vertex node, Integer level, Integer canvasWidth, Integer vertexVertMultiplier, Layout layout, Integer vertexSiblingOffset, VertexKind vertexKind, Point2D.Double nodeXyCoords, VertexKind rootKind, DisplayType graphType, Integer collisionXOffset, Integer collisionYOffest) {
        level++;
        if (node.getChildren() != null) {
            Point2D parentPos = node.getTranslationGraphPos();
            Integer numChildren = node.getChildren().size();
            Vertex prevChild = null;
            VertexKind prevChildKind = null;

            for (Integer i=0; i<numChildren; i++) {
                Vertex child = node.getChildren().get(i);
                child.setSiblingNum(i);
                VertexKind childKind = child.getKind();
                if (i>0) {
                    prevChild = node.getChildren().get(i-1);
                    prevChildKind = prevChild.getKind();
                }

                // check if child of parallel (special case)
                Boolean isChildOfParallel = false;
                for (Vertex parent : child.getParents()) {
                    if (parent.getKind() == PARALLEL && parent.getStatus() == STARTED) {
                        Vertex parStarted = parent;
                        isChildOfParallel = true;
                        parentPos = parStarted.getTranslationGraphPos();  // use the parentPos of the parent parallel started node, not just the node we traversed in on (this child is positioned directly underneath the parallel started node)

                        // get correct sibling num (check how many children of parStarted are already placed)
                        ArrayList<Vertex> childrenOfParallel = parStarted.getChildren();
                        Integer numPlaced = 0;
                        for (Vertex thisChildOfParallel : childrenOfParallel) {
                            if (tempPlacedVertices.contains(thisChildOfParallel)) {
                                numPlaced++;
                            }
                        }
                        child.setSiblingNum(numPlaced);

                        // get correct level
                        double parStartedYPos = parStarted.getTranslationGraphPos().getY();
                        level = (int) parStartedYPos / vertexVertMultiplier + 1;

                    }
                }

                // calculate xy coordinates
                Point2D.Double xyCoords;
                if (!isChildOfParallel && (childKind == SEQUENTIAL || rootKind == SEQUENTIAL) && graphType != XML_ONLY) {
                    xyCoords = calcXYSequentialCoords(canvasWidth, level, vertexVertMultiplier, (int) node.getTranslationGraphPos().getX(), i, numChildren, vertexSiblingOffset, node.getSiblingNum(), child.getStatus(), child.toString(), child, parentPos);
                } else if (!isChildOfParallel && childKind == PARALLEL) {
                    xyCoords = calcXYParallelCoords(canvasWidth, level, vertexVertMultiplier, (int) node.getTranslationGraphPos().getX(), i, numChildren, vertexSiblingOffset, node.getSiblingNum(), child.getStatus(), child.toString(), child, parentPos);
                } else {
                    xyCoords = calcXYCoords(canvasWidth, level, vertexVertMultiplier, (int) parentPos.getX(), child.getSiblingNum(), numChildren, vertexSiblingOffset, node.getSiblingNum(), parentPos, isChildOfParallel, node.getChildren(), prevChildKind);
                }

                // if not placed, then placed vertex
                if (!tempPlacedVertices.contains(child)) {

                    Double childX = xyCoords.x;
                    Double childY = xyCoords.y;

                    // this fixes the rare node collision where two nodes print directly on top of each other
                    for (ArrayList<Double> thisTempXyCoords : tempXyCoords) {
                        if (thisTempXyCoords.get(0) != null && thisTempXyCoords.get(1) != null) {
                            Double thisX = thisTempXyCoords.get(0);
                            Double thisY = thisTempXyCoords.get(1);
                            // if (childX.equals(thisX) && childY.equals(thisY)) {
                            // if (abs(childX - thisX) < 10 && abs(childY - thisY) < 10) {
                            if (abs(childY - thisY) < 10) {

                                // xyCoords.x = childX + collisionXOffset;
                                // Integer randomYOffset = randomNum(25,100);
                                xyCoords.y = childY + collisionYOffest;
                                // xyCoords.y = childY + randomYOffset;
                            }
                        }
                    }

                    // place vertex
                    ArrayList<Double> childXyCoords = new ArrayList<Double>();
                    Point2D.Double childXyCoordsPoint2D = new Point2D.Double(childX,childY);
                    childXyCoords.add(childX);
                    childXyCoords.add(childY);
                    tempXyCoords.add(childXyCoords);
                    // placeVertex(child, xyCoords, layout, xyCoords, childXyCoords);
                    placeVertex(child, xyCoords, layout);

                    // run this on on children recursively
                    if (child.getChildren() != null) {
                        // placeChildrenRecursively(child, level, canvasWidth, vertexVertMultiplier, layout, vertexSiblingOffset, rootKind);
                        placeStaticChildrenRecursively(child, level, canvasWidth, vertexVertMultiplier, layout, vertexSiblingOffset, childKind, childXyCoordsPoint2D, rootKind, graphType, collisionXOffset, collisionYOffest);
                    }
                }
            }
        }
    }


}
