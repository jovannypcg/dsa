package mx.jovannypcg.base.p68_clonegraph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given a reference of a node in a connected undirected graph, return a deep copy (clone)
 * of the graph.
 *
 * <p>Each node in the graph contains a value ({@code int}) and a list ({@code List<Node>})
 * of its neighbors.
 *
 * @see <a href="https://leetcode.com/problems/clone-graph/">Clone Graph - LeetCode</a>
 */
public class Solution {

    public static class Node {

        public int val;
        public List<Node> neighbors;

        public Node() {
            val = 0;
            neighbors = new ArrayList<>();
        }

        public Node(int val) {
            this.val = val;
            neighbors = new ArrayList<>();
        }

        public Node(int val, List<Node> neighbors) {
            this.val = val;
            this.neighbors = neighbors;
        }
    }

    public Node cloneGraph(Node node) {
        if (node == null) return null;

        Map<Node, Node> cloneMap = new HashMap<>();
        Deque<Node[]> queue = new ArrayDeque<>();
        Node _clone = new Node(node.val);

        cloneMap.put(node, _clone);
        queue.addLast(new Node[] { node, _clone });

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (; levelSize > 0; levelSize--) {
                Node[] clonePair = queue.removeFirst();
                Node currOriginal = clonePair[0];
                Node currClone = clonePair[1];

                for (Node neighbor : currOriginal.neighbors) {
                    if (cloneMap.containsKey(neighbor)) {
                        currClone.neighbors.add(cloneMap.get(neighbor));
                    } else {
                        Node _neighbor = new Node(neighbor.val);
                        currClone.neighbors.add(_neighbor);

                        queue.addLast(new Node[] { neighbor, _neighbor });

                        cloneMap.put(neighbor, _neighbor);
                    }
                }
            }
        }

        return _clone;
    }
}
