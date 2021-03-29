package _options;

public class Options {

    // debug options
    private Boolean debug;
    private Integer targetNumNodesExpanded;

    // paths
    private String pathToXmlDir;
    private String pathToKrpDir;
    private String pathToTests;
    private String pathToModels;

    // model checking settings
    private int analyzerResultsLineLength;

    // graph settings (these are a mess. a refactor would be nice, but is low priority)
    private int graphPanels3AcrossWidth;
    private int graphPanelsHeight;
    private int graphLayouts3AcrossWidth;
    private int graphLayouts3AcrossXMLWidth;
    private int graphLayouts3AcrossWidthMacBookMonitor;
    private int graphLayouts3AcrossWidthAOCMonitor;
    private int graphLayout2AcrossWidth;
    private int graphayout1AcrossWidth;
    private int graphLayoutsHeight;

    private int xmlGraphEdgeXLength;
    private int xmlGraphEdgeYLength;
    private int xmlGraphNodeDiameter;
    private int xmlGraphPanelWidth;
    private int xmlGraphPanelHeight;
    private int xmlGraphLayoutWidth;
    private int xmlGraphLayoutHeight;
    private int xmlGraphVertexSiblingOffset;
    private double xmlGraphVertexAttractionMultiplier;

    private int translationGraphEdgeXLength;
    private int translationGraphEdgeYLength;
    private int translationGraphCollisionXOffset;
    private int translationGraphCollisiionYOffset;
    private int translationGraphNodeDiameter;
    private int translationGraphPanelWidth;
    private int translationGraphPanelHeight;
    private int translationGraphLayoutWidth;
    private int translationGraphLayoutHeight;

    private Integer translationGraphLevel;
    private Integer translationGraphNumEdges;
    private Integer translationGraphCanvasWidth;
    private Integer translationGraphCanvasHeight;
    private Integer translationGraphVertexSiblingOffset;
    private Integer translationGraphVertexVertMultiplier;
    private double translationGraphVertexAttractionMultiplier;

    private int interleavingsGraphEdgeXLength;
    private int interleavingsGraphEdgeYLength;
    private int interleavingsGraphNodeDiameter;
    private int interleavingsGraphPanelWidth;
    private int interleavingsGraphPanelHeight;
    private int interleavingsGraphLayoutWidth;
    private int interleavingsGraphLayoutHeight;
    private double interleavingsGraphVertexAttractionMultiplier;

    private float xmlGraphScaleFactor;
    private float translationGraphScaleFactor;
    private float interleavingsGraphScaleFactor;

    // tester page vars
    private int testerExpectedActualPanelWidth;
    private int testerExpectedActualPanelHeight;
    private int testerExpectedActualTextAreaWidth;
    private int testerExpectedActualTextAreaHeight;
    private int testerKripkeLinesToSkip;
    private int testerModelCheckingLinesToSkip;
    private int testerLineLength;
    private int testerLineLengthGraphs;
    private int testerLineLengthModelCheck;


    public Options() {

        // debug options
        debug = false;
        targetNumNodesExpanded = 1;

        // ** selected file may be hardcoded in at Controller.java:341 // TODO: remove this random hard coding?
        // ** file list items may also be hardcoded in controller/content/staticContent/XmlFileOrder:8 // TODO: remove this random hard coding?

        // set paths
        pathToXmlDir = "src/main/resources/xml/";
        pathToKrpDir = "src/main/resources/krp/";
        pathToTests = "src/main/resources/tests/";
        pathToModels = "src/main/java/controller/content/";

        // model checking settings
        analyzerResultsLineLength = 25;

        // graph settings (these are a mess. a refactor would be nice, but is low priority)
        // graph width/heights
        graphPanelsHeight = 970;
        graphPanels3AcrossWidth = 500;
        graphLayouts3AcrossXMLWidth = 465;
        graphLayouts3AcrossWidthMacBookMonitor = 465;
        graphLayouts3AcrossWidthAOCMonitor = 865;
        graphLayouts3AcrossWidth = graphLayouts3AcrossWidthAOCMonitor; // 465 // TODO: change this to graphLayouts3AcrossWidthMacBookMonitor if on macbook
        graphLayout2AcrossWidth = 675;
        graphayout1AcrossWidth = 2500; // 1400 for macbook
        graphLayoutsHeight = 920;

        // xml graphs settings
        xmlGraphEdgeXLength = 50; // 50 is default i think
        xmlGraphEdgeYLength = 35;
        xmlGraphScaleFactor = 0.6f; // shrinks graphs to 75% size to fix in containing box
        xmlGraphNodeDiameter = 18;
        xmlGraphPanelWidth = 300;
        xmlGraphPanelHeight = 875;
        xmlGraphLayoutWidth = 275;
        xmlGraphLayoutHeight = 825;
        xmlGraphVertexAttractionMultiplier = .3;
        xmlGraphVertexSiblingOffset = 150;

        // translation graphs settings
        translationGraphEdgeXLength = 50; // 50 is default i think
        translationGraphEdgeYLength = 35;
        translationGraphScaleFactor = 0.75f; // shrinks graphs to 75% size to fix in containing box
        translationGraphCollisionXOffset = 0; // pushes colliding vertices this many pixels horizontally (pos or neg ok)
        // translationGraphCollisiionYOffset = -25; // pushes colliding vertices this many pixels vertically (pos or neg ok)
        translationGraphCollisiionYOffset = -35; // pushes colliding vertices this many pixels vertically (pos or neg ok)
        translationGraphNodeDiameter = 18;
        translationGraphPanelWidth = 450;
        translationGraphPanelHeight = 800;
        translationGraphLayoutWidth = 550;
        translationGraphLayoutHeight = 875;
        translationGraphLevel = 1;
        translationGraphNumEdges = 0;
        translationGraphCanvasWidth = 475;
        translationGraphCanvasHeight = 350;
        // translationGraphVertexSiblingOffset = 150;
        translationGraphVertexSiblingOffset = 50;
        translationGraphVertexAttractionMultiplier = .99;
        // translationGraphVertexVertMultiplier = 60;
        translationGraphVertexVertMultiplier = 20;

        // interleavings graphs settings
        interleavingsGraphEdgeXLength = 50; // 50 is default i think
        interleavingsGraphEdgeYLength = 35;
        interleavingsGraphScaleFactor = 0.7f; // shrinks graphs to 75% size to fix in containing box
        interleavingsGraphEdgeXLength = 50; // 50 is default i think
        interleavingsGraphEdgeYLength = 35;
        interleavingsGraphEdgeXLength = 50; // 50 is default i think
        interleavingsGraphNodeDiameter = 18;
        interleavingsGraphPanelWidth = 550;
        interleavingsGraphPanelHeight = 900;
        interleavingsGraphLayoutWidth = 525;;
        interleavingsGraphLayoutHeight = 875;
        interleavingsGraphScaleFactor = 0.75f; // shrinks graphs to 75% size to fix in containing box
        interleavingsGraphVertexAttractionMultiplier = .99;

        // tester page misc
        testerKripkeLinesToSkip = 2; // based on the stucture of the tests. if the structure of the tests were changed, this would have to change.
        testerModelCheckingLinesToSkip = 2;
        // testerLineLength = 18; // line length for expected & actual results for xml, translations & interleavings
        // testerLineLength = 30;

        testerLineLengthGraphs = 18;
        testerLineLengthModelCheck = 30;

        // testerExpectedActualPanelWidth = 350;
        // testerExpectedActualPanelHeight = 700;
        // testerExpectedActualTextAreaWidth = 163;

    }

    public DirectedGraphOptions graphOptions() {
        DirectedGraphOptions graphOptions3AcrossSpot1 = new DirectedGraphOptions(
                xmlGraphNodeDiameter,
                translationGraphLevel,
                translationGraphNumEdges,
                translationGraphCanvasWidth,
                translationGraphCanvasHeight,
                translationGraphVertexSiblingOffset,
                translationGraphVertexVertMultiplier,
                xmlGraphLayoutWidth,
                xmlGraphLayoutHeight,
                xmlGraphEdgeXLength,
                xmlGraphEdgeYLength,
                translationGraphCollisionXOffset,
                translationGraphCollisiionYOffset,
                xmlGraphScaleFactor,
                xmlGraphVertexAttractionMultiplier
        );
        return graphOptions3AcrossSpot1;
    }

    public DirectedGraphOptions graphOptions3AcrossSpot2() {
        DirectedGraphOptions graphOptions3AcrossSpot2 = new DirectedGraphOptions(
                translationGraphNodeDiameter,
                translationGraphLevel,
                translationGraphNumEdges,
                translationGraphCanvasWidth,
                translationGraphCanvasHeight,
                translationGraphVertexSiblingOffset,
                translationGraphVertexVertMultiplier,
                translationGraphLayoutWidth,
                translationGraphLayoutHeight,
                translationGraphEdgeXLength,
                translationGraphEdgeYLength,
                translationGraphCollisionXOffset,
                translationGraphCollisiionYOffset,
                translationGraphScaleFactor,
                translationGraphVertexAttractionMultiplier
        );
        return graphOptions3AcrossSpot2;
    }

    public DirectedGraphOptions graphOptions3AcrossSpot3() {
        DirectedGraphOptions graphOptions3AcrossSpot3 = new DirectedGraphOptions(
                interleavingsGraphNodeDiameter,
                translationGraphLevel,
                translationGraphNumEdges,
                translationGraphCanvasWidth,
                translationGraphCanvasHeight,
                translationGraphVertexSiblingOffset,
                translationGraphVertexVertMultiplier,
                translationGraphLayoutWidth,
                translationGraphLayoutHeight,
                translationGraphEdgeXLength,
                translationGraphEdgeYLength,
                translationGraphCollisionXOffset,
                translationGraphCollisiionYOffset,
                translationGraphScaleFactor,
                translationGraphVertexAttractionMultiplier
        );
        return graphOptions3AcrossSpot3;
    }

    public DirectedGraphOptions getOptions2Across(Options options) {
        DirectedGraphOptions getOptions2Across = new DirectedGraphOptions(
                interleavingsGraphNodeDiameter,
                translationGraphLevel,
                translationGraphNumEdges,
                translationGraphCanvasWidth,
                translationGraphCanvasHeight,
                translationGraphVertexSiblingOffset,
                translationGraphVertexVertMultiplier,
                translationGraphLayoutWidth,
                translationGraphLayoutHeight,
                translationGraphEdgeXLength,
                translationGraphEdgeYLength,
                translationGraphCollisionXOffset,
                translationGraphCollisiionYOffset,
                translationGraphScaleFactor,
                translationGraphVertexAttractionMultiplier
        );
        return getOptions2Across;
    }

    public String getPathToTests() {
        return pathToTests;
    }

    public String getPathToKrpDir() {
        return pathToKrpDir;
    }

    public String getPathToXmlDir() {
        return pathToXmlDir;
    }

    public String getPathToModels() {
        return pathToModels;
    }

    public Boolean getDebug() {
        return debug;
    }

    public Integer getTargetNumNodesExpanded() {
        return targetNumNodesExpanded;
    }

    public int getAnalyzerResultsLineLength() {
        return analyzerResultsLineLength;
    }

    public int getGraphLayouts3AcrossWidth() {
        return graphLayouts3AcrossWidth;
    }

    public int getGraphLayoutsHeight() {
        return graphLayoutsHeight;
    }

    public int getGraphPanelsHeight() {
        return graphPanelsHeight;
    }

    public int getGraphLayout1AcrossWidth() {
        return graphayout1AcrossWidth;
    }

    public int getGraphLayout2AcrossWidth() {
        return graphLayout2AcrossWidth;
    }

    public int getGraphPanels3AcrossWidth() {
        return graphPanels3AcrossWidth;
    }

    public int getGraphLayouts3AcrossXMLWidth() {
        return graphLayouts3AcrossXMLWidth;
    }

    public int getTesterKripkeLinesToSkip() {
        return testerKripkeLinesToSkip;
    }

    public int getTesterModelCheckingLinesToSkip() {
        return testerModelCheckingLinesToSkip;
    }

    public int getTesterLineLength() {
        return testerLineLength;
    }
    public int getTesterGraphsLineLength() {
        return testerLineLengthGraphs;
    }
    public int getTesterModelCheckLineLength() {
        return testerLineLengthModelCheck;
    }

    public int getTesterExpectedActualTextAreaWidth() {
        return testerExpectedActualTextAreaWidth;
    }

    public int getTesterExpectedActualTextAreaHeight() {
        return testerExpectedActualTextAreaHeight;
    }

    public int getTesterExpectedActualPanelHeight() {
        return testerExpectedActualPanelHeight;
    }

    public int getTesterExpectedActualPanelWidth() {
        return testerExpectedActualPanelWidth;
    }

}
