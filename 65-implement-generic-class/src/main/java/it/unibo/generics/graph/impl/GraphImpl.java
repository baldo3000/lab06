package it.unibo.generics.graph.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import it.unibo.generics.graph.api.Graph;

public class GraphImpl<N> implements Graph<N> {

    public static String DEPTH = "DFS";
    public static String BREATH = "BFS";

    private final Map<N, Set<N>> nodes;
    private final String searchMode;

    public GraphImpl(final String searchMode) {
        this.nodes = new LinkedHashMap<>();
        if (searchMode.equals(DEPTH) || searchMode.equals(BREATH)) {
            this.searchMode = searchMode;
        } else {
            this.searchMode = BREATH;
        }
    }

    public GraphImpl() {
        this(BREATH);
    }

    public void addNode(final N node) {
        if (node != null) {
            this.nodes.putIfAbsent(node, new HashSet<>());
        }
    }

    public void addEdge(final N source, final N target) {
        if (hasNode(source) && hasNode(target)) {
            this.nodes.get(source).add(target);
        }
    }

    public Set<N> nodeSet() {
        return new HashSet<N>(this.nodes.keySet());
    }

    public Set<N> linkedNodes(final N node) {
        return new HashSet<N>(this.nodes.get(node));
    }

    public List<N> getPath(final N source, final N target) {
        if (hasNode(source) && hasNode(target)) {
            if (this.searchMode.equals(BREATH)) {
                return createPath(target, BFS(source));
            } else {
                return createPath(target, DFS(source));
            }

        }
        return Collections.emptyList();
    }

    private boolean hasNode(final N node) {
        return this.nodes.containsKey(node);
        // throw new IllegalArgumentException("Node \"" + node + "\" is not inside
        // graph!");
    }

    /**
     * Returns the path from target node to the source
     * provided when searching using either DFS or BFS
     * 
     * @param target
     *                     ending node
     * @param predecessors
     *                     Map of pairs node-predecessor
     * @return
     *         list of nodes
     */
    private List<N> createPath(final N target, final Map<N, N> predecessors) {
        final List<N> path = new LinkedList<>();
        N currentNode = target;
        while (currentNode != null) {
            path.add(0, currentNode);
            currentNode = predecessors.get(currentNode);
        }
        return path;
    }

    /**
     * Returns a Map containing the pairs node-predecessor
     * using BFS search
     * 
     * @param source
     *               starting node
     * @return
     *         map containing pairs node-predecessor
     */
    private Map<N, N> BFS(final N source) {
        final Queue<N> queue = new LinkedList<>();
        final Set<N> visitedNodes = new LinkedHashSet<>();
        final Map<N, N> predecessors = new LinkedHashMap<>();

        visitedNodes.add(source);
        queue.add(source);
        predecessors.put(source, null);
        while (!queue.isEmpty()) {
            N pop = queue.poll();
            for (final var neighbor : this.nodes.get(pop)) {
                if (!visitedNodes.contains(neighbor)) {
                    visitedNodes.add(neighbor);
                    queue.add(neighbor);
                    predecessors.put(neighbor, pop);
                }
            }
        }
        return predecessors;
    }

    /**
     * Returns a Map containing the pairs node-predecessor
     * using DFS search
     * 
     * @param source
     *               starting node
     * @return
     *         map containing pairs node-predecessor
     */
    private Map<N, N> DFS(final N source) {
        final Set<N> visitedNodes = new LinkedHashSet<>();
        final Map<N, N> predecessors = new LinkedHashMap<>();

        predecessors.put(source, null);
        searchDFS(source, visitedNodes, predecessors);
        return predecessors;
    }

    /**
     * Recursive DFS exploration
     */
    private void searchDFS(final N currentNode, final Set<N> visitedNodes, final Map<N, N> predecessors) {
        visitedNodes.add(currentNode);
        for (final var neighbor : this.nodes.get(currentNode)) {
            if (!visitedNodes.contains(neighbor)) {
                predecessors.put(neighbor, currentNode);
                searchDFS(neighbor, visitedNodes, predecessors);
            }
        }
    }
}
