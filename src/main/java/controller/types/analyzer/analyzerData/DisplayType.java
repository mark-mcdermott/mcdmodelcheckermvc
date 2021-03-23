package controller.types.analyzer.analyzerData;

/**
 * Which graphs to display
 */
public enum DisplayType {

    ALL_GRAPHS, // all 3 graphs (xml, translation, interleavings)
    TWO_GRAPHS, // not sure exactly what this is for
    XML_ONLY,   // xml only
    TRANS_ONLY, // translation only
    TRANS_COMP, // translation comparison (shows translation graphs of specified step x and step x-1 side by side)
    INTER_ONLY, // interleavings only
    INTER_COMP, // interleavings comparison (shows interleavings graphs of specified step x and step x-1 side by side)

}
