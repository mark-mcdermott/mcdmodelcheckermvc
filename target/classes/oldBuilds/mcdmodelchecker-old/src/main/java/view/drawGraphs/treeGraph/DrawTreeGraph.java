package view.drawGraphs.treeGraph;

import controller.dataTypes.graphHelpers.VertexList;
import controller.dataTypes.graphItems.Vertex;

import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.TreeLayout;
import edu.uci.ics.jung.graph.DelegateTree;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractModalGraphMouse;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.control.ViewScalingControl;
import edu.uci.ics.jung.visualization.decorators.EdgeShape;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.geom.Ellipse2D;

public class DrawTreeGraph {

    private int edgeXLength;
    private int edgeYLength;
    private int nodeDiameter;
    private int layoutWidth;
    private int layoutHeight;
    private float scaleFactor;

    public DrawTreeGraph(TreeGraphOptions treeGraphOptions) {
        this.edgeXLength = treeGraphOptions.getEdgeXLength();
        this.edgeYLength = treeGraphOptions.getEdgeYLength();
        this.nodeDiameter = treeGraphOptions.getNodeDiameter();
        this.layoutWidth = treeGraphOptions.getLayoutWidth();
        this.layoutHeight = treeGraphOptions.getLayoutHeight();
        this.scaleFactor = treeGraphOptions.getScaleFactor();
    }

    // DelegateTree Tree Layout strategy adapted from code by Stasya Gera, as found on https://github.com/StasyaGera/translation-methods/blob/99b1114de703e180d286c999f67c05c7e102f2b2/lab4/ex2/Tree.java on 3/12/20
    public void drawGraph(JPanel graphPanel, VertexList vertexList) {

        // init vars
        DelegateTree<Vertex, Integer> delegateTree;
        VertexListToTree vertexListToDelegateTree;

        // create delegate tree
        vertexListToDelegateTree = new VertexListToTree();
        delegateTree = vertexListToDelegateTree.VertexListToTree(vertexList);

        // create jung layout and visualization server
        Layout<Vertex, Integer> layout = new TreeLayout<Vertex, Integer>(delegateTree, edgeXLength, edgeYLength);
        VisualizationViewer<Vertex, Integer> visualizationViewer = new VisualizationViewer<Vertex, Integer>(layout, new Dimension(layoutWidth, layoutHeight));
        // BasicVisualizationServer<Vertex, Integer> visServer = new BasicVisualizationServer<>(layout, new Dimension(layoutWidth, layoutHeight));

        // shrink the vertices a bit
        visualizationViewer.getRenderContext().setVertexShapeTransformer(n -> {
            int xyOffset = nodeDiameter / -2; // xyOffset recenters the vertices after shrinking them
            return new Ellipse2D.Double(xyOffset, xyOffset, nodeDiameter, nodeDiameter);
        });

        // use straight arrows, grey vertices, vertex toString() for labels, set labels to right
        visualizationViewer.getRenderContext().setEdgeShapeTransformer(new EdgeShape.Line<>());
        visualizationViewer.getRenderContext().setVertexFillPaintTransformer(tree -> Color.LIGHT_GRAY);
        visualizationViewer.getRenderContext().setVertexLabelTransformer(new ToStringLabeller<>());
        visualizationViewer.getRenderer().getVertexLabelRenderer().setPosition(Renderer.VertexLabel.Position.E);
        visualizationViewer.setBackground(Color.WHITE);

        // setup mouse zoom
        final AbstractModalGraphMouse graphMouse = new DefaultModalGraphMouse<Vertex, Number>();
        GraphZoomScrollPane graphZoomScrollPane = new GraphZoomScrollPane(visualizationViewer);
        KeyListener graphMouseKeyListener = graphMouse.getModeKeyListener();
        visualizationViewer.addKeyListener(graphMouseKeyListener);
        visualizationViewer.setGraphMouse(graphMouse);
        visualizationViewer.addKeyListener((graphMouse.getModeKeyListener()));

        // shrink the whole tree a bit
        ScalingControl scaler = new ViewScalingControl();
        scaler.scale(visualizationViewer, scaleFactor, visualizationViewer.getCenter());

        // add graphs to graphPanel
        // graphPanel.add(visServer);
        graphZoomScrollPane.setPreferredSize(new Dimension(200,600));
        graphPanel.add(graphZoomScrollPane);

    }

}
