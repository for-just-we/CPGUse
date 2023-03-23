package org.example.schemas;

import de.fraunhofer.aisec.cpg.graph.Node;
import de.fraunhofer.aisec.cpg.sarif.PhysicalLocation;

import java.util.Objects;

public class CustomizedNode {
    private final String type;
    private final String code;

    private final PhysicalLocation location;

    public CustomizedNode(Node n) {
        this.type = n.getClass().getSimpleName();
        this.code = n.getCode();
        this.location = n.getLocation();
    }

    public String toString() {
        return String.format("%s: %s - line: %d", type, code, location.getRegion().getStartLine());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomizedNode that = (CustomizedNode) o;
        return type.equals(that.type) && code.equals(that.code) && location.equals(that.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, code, location);
    }

    public String getType() {
        return type;
    }

    public String getCode() {
        return code;
    }

    public PhysicalLocation getLocation() {
        return location;
    }
}
