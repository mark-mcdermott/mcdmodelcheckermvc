package controller.types.data;

/**
 * Which graphs to display
 */
public enum DisplayType {

    ALL_GRAPHS, // all 3 graphs (xml, translation, interleavings)
    XML_ONLY,   // xml only
    TRANS_ONLY, // translation only
    TRANS_COMP, // translation comparison (shows translation graphs of specified step x and step x-1 side by side)
    INTER_ONLY, // interleavings only
    INTER_COMP, // interleavings comparison (shows interleavings graphs of specified step x and step x-1 side by side)

}
