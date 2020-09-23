package _options;

public class Options {

    // paths
    private String pathToXmlDir;
    private String pathToKrpDir;
    private String pathToTests;
    private String pathToModels;

    // graph settings (these are a mess. a refactor would be nice, but is low priority)
    private int graphPanelsWidth;
    private int graphPanelsHeight;
    private int graphLayoutsWidth;
    private int graphHalfLayoutWidth;
    private int graphFullLayoutWidth;
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


    public Options() {

        // set paths
        pathToXmlDir = "src/main/resources/xml/";
        pathToKrpDir = "src/main/resources/krp/";
        pathToTests = "src/main/resources/tests/";
        pathToModels = "src/main/java/controller/content/";

        // graph settings (these are a mess. a refactor would be nice, but is low priority)
        // graph width/heights
        graphPanelsWidth = 400;
        graphPanelsHeight = 600;
        graphLayoutsWidth = 350;
        graphHalfLayoutWidth = 675;
        graphFullLayoutWidth = 1400;
        graphLayoutsHeight = 550;

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
        translationGraphNodeDiameter = 18;
        translationGraphPanelWidth = 450;
        translationGraphPanelHeight = 800;
        translationGraphLayoutWidth = 550;
        translationGraphLayoutHeight = 875;
        translationGraphLevel = 1;
        translationGraphNumEdges = 0;
        translationGraphCanvasWidth = 475;
        translationGraphCanvasHeight = 350;
        translationGraphVertexSiblingOffset = 150;
        translationGraphVertexAttractionMultiplier = .99;
        translationGraphVertexVertMultiplier = 60;

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
}
