package org.example.schemas;

import de.fraunhofer.aisec.cpg.graph.Node;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CustomizedGraph {
    private Map<Node, CustomizedNode> cpgNode2cusNode;

    private Map<String, Set<CustomizedEdge>> edges;

    public CustomizedGraph() {
        cpgNode2cusNode = new HashMap<>();
        edges = Map.of("eog", new HashSet<>(), "dfg", new HashSet<>());
    }

    public void addEdge(Node src, Node dst, String type) {
        CustomizedNode srcNode = cpgNode2cusNode.getOrDefault(src, new CustomizedNode(src));
        CustomizedNode dstNode = cpgNode2cusNode.getOrDefault(dst, new CustomizedNode(dst));
        edges.get(type).add(new CustomizedEdge(srcNode, dstNode));
    }

    public Map<Node, CustomizedNode> getCpgNode2cusNode() {
        return cpgNode2cusNode;
    }

    public Map<String, Set<CustomizedEdge>> getEdges() {
        return edges;
    }
}
