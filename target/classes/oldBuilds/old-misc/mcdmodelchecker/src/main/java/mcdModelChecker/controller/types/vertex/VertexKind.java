package mcdModelChecker.controller.types.vertex;

import java.util.HashMap;
import java.util.Map;

/** Enum denoting the different types of vertices, according to Little-JIL (leaf, sequential, parallel, try, choice).
 * Also provides different output types for printing results and noting graphs - toString, toSlug (a 3 letter
 * shortened version) and toFirstChar.
 */
public enum VertexKind {

    LEAF("leaf", "leaf"),
    SEQUENTIAL("sequential", "seq"),
    PARALLEL("parallel", "par"),
    TRY("try", "try"),
    CHOICE("choice", "choice");

    private String string;
    private String slug;

    VertexKind(String string, String slug) {
        this.string = string;
        this.slug = slug;
    }

    public String toString() {
        return string;
    }

    public String toSlug() {
        return slug;
    }

    public String toFirstChar() {
        return Character.toString(string.charAt(0));
    }

    private static final Map<String, VertexKind> lookup = new HashMap<>();

    // populate reverse lookup table
    static {
        for (VertexKind vertexKind : VertexKind.values()) {
            lookup.put(vertexKind.toString(), vertexKind);
        }
    }

    // reverse lookup call
    public static VertexKind toEnum(String string) {
        return lookup.get(string);
    }

}
