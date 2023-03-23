package org.example.schemas;

import java.util.Objects;

public class CustomizedEdge {
    private final CustomizedNode srcNode;
    private final CustomizedNode dstNode;

    public CustomizedEdge(CustomizedNode srcNode, CustomizedNode dstNode) {
        this.srcNode = srcNode;
        this.dstNode = dstNode;
    }

    public String toString() {
        return "(%s) ----> (%s)".formatted(srcNode, dstNode);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomizedEdge that = (CustomizedEdge) o;
        return srcNode.equals(that.srcNode) && dstNode.equals(that.dstNode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(srcNode, dstNode);
    }

    public CustomizedNode getSrcNode() {
        return srcNode;
    }

    public CustomizedNode getDstNode() {
        return dstNode;
    }
}
