package mx.jovannypcg.base.p81_topologicalsort;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * You are given an integer {@code n} representing the number of nodes in a directed graph,
 * labeled from {@code 0} to {@code n - 1}, and a list {@code edges} where {@code edges[i] =
 * [u, v]} indicates a directed edge from node {@code u} to node {@code v}, meaning {@code u}
 * must appear before {@code v} in the ordering.
 *
 * <p>Return any valid topological ordering of all {@code n} nodes as an array. If no valid
 * ordering exists (the graph contains a cycle), return an empty array.
 *
 * <p>Implement this using Kahn's algorithm (BFS-based topological sort using in-degrees).
 */
public class Solution {

    public int[] topoSortKahn(int n, int[][] edges) {
        List<Integer> order = new ArrayList<>();

        // Step 1: build graph using adjacency lists to figure out what the neighbors of each vertex are
        Map<Integer, List<Integer>> graph = buildGraph(edges);
        // Step 2: compute the in-degrees for all vertices
        int[] inDegrees = getInDegrees(edges, n);
        Deque<Integer> queue = new ArrayDeque<>();

        // Step 3: add all 0-in-degree vertices to queue
        for (int vertex = 0; vertex < n; vertex++) {
            if (inDegrees[vertex] == 0) queue.offer(vertex);
        }

        // Step 4: run loop
        while (!queue.isEmpty()) {
            int current = queue.poll();

            // Step 5: add `current` to `order`
            order.add(current);

            // Step 6: decrease the in-degree of current's neighbors by 1
            //         if such an in-degree results in 0, add that neighbor to the queue
            for (int neighbor : graph.getOrDefault(current, List.of())) {
                inDegrees[neighbor]--;

                if (inDegrees[neighbor] == 0) queue.offer(neighbor);
            }
        }

        return order.size() == n ? toArray(order) : new int[0];
    }

    private int[] toArray(List<Integer> items) {
        int[] arr = new int[items.size()];

        int idx = 0;
        for (int item : items) {
            arr[idx++] = item;
        }

        return arr;
    }

    private int[] getInDegrees(int[][] edges, int n) {
        int[] inDegrees = new int[n];

        for (int[] edge : edges) {
            int v = edge[1];

            inDegrees[v]++;
        }

        return inDegrees;
    }

    private Map<Integer, List<Integer>> buildGraph(int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            graph.putIfAbsent(u, new ArrayList<>());
            graph.get(u).add(v);
        }

        return graph;
    }
}
