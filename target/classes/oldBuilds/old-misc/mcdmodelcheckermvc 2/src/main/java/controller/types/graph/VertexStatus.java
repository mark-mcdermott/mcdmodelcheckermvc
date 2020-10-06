package controller.types.graph;

import java.util.HashMap;
import java.util.Map;

// enum toString and reverse lookup code from https://howtodoinjava.com/java/enum/java-enum-string-example/ accesed on 4/2/20
public enum VertexStatus {

    POSTED("posted"),
    STARTED("started"),
    TERMINATED("terminated"),
    COMPLETED("completed"),
    SUBSTEP("choice substep"),                          // for choice only
    SUBSTEP_HAS_NOT_STARTED("substep has not started"), // for choice only
    SUBSTEP_HAS_STARTED("substep has started");         // for choice only

    private String string;

    VertexStatus(String string) {
        this.string = string;
    }

    public String toString() {
        return string;
    }

    public String toFirstChar() {
        return Character.toString(string.charAt(0));
    }

    // reverse lookup table
    private static final Map<String, VertexStatus> lookup = new HashMap<>();

    // populate reverse lookup table
    static {
        for (VertexStatus vertexStatus : VertexStatus.values()) {
            lookup.put(vertexStatus.toString(), vertexStatus);
        }
    }

    // reverse lookup call
    public static VertexStatus toEnum(String string) {
        return lookup.get(string);
    }

}
