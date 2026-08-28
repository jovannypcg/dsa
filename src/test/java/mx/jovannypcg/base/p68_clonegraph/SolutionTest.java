package mx.jovannypcg.base.p68_clonegraph;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SolutionTest {

    private final Solution solution = new Solution();

    private Solution.Node[] buildGraph(int[][] adjList) {
        Solution.Node[] nodes = new Solution.Node[adjList.length];
        for (int i = 0; i < adjList.length; i++) {
            nodes[i] = new Solution.Node(i + 1);
        }
        for (int i = 0; i < adjList.length; i++) {
            for (int neighborVal : adjList[i]) {
                nodes[i].neighbors.add(nodes[neighborVal - 1]);
            }
        }
        return nodes;
    }

    // Walks both graphs in lockstep, verifying the clone is a structurally
    // identical but reference-distinct copy, including consistent reuse of
    // clones for nodes reachable through multiple paths (cycles).
    private void assertIsDeepCopy(Solution.Node original, Solution.Node clone) {
        if (original == null) {
            assertThat(clone).isNull();
            return;
        }

        assertThat(clone).isNotSameAs(original);

        Map<Solution.Node, Solution.Node> originalToClone = new HashMap<>();
        originalToClone.put(original, clone);

        Deque<Solution.Node> queue = new ArrayDeque<>();
        queue.add(original);

        while (!queue.isEmpty()) {
            Solution.Node currentOriginal = queue.poll();
            Solution.Node currentClone = originalToClone.get(currentOriginal);

            assertThat(currentClone.val).isEqualTo(currentOriginal.val);
            assertThat(currentClone.neighbors).hasSameSizeAs(currentOriginal.neighbors);

            for (int i = 0; i < currentOriginal.neighbors.size(); i++) {
                Solution.Node originalNeighbor = currentOriginal.neighbors.get(i);
                Solution.Node cloneNeighbor = currentClone.neighbors.get(i);

                assertThat(cloneNeighbor).isNotSameAs(originalNeighbor);
                assertThat(cloneNeighbor.val).isEqualTo(originalNeighbor.val);

                if (originalToClone.containsKey(originalNeighbor)) {
                    assertThat(cloneNeighbor).isSameAs(originalToClone.get(originalNeighbor));
                } else {
                    originalToClone.put(originalNeighbor, cloneNeighbor);
                    queue.add(originalNeighbor);
                }
            }
        }
    }

    private int countReachableNodes(Solution.Node start) {
        if (start == null) return 0;

        Set<Solution.Node> visited = new HashSet<>();
        Deque<Solution.Node> queue = new ArrayDeque<>();
        visited.add(start);
        queue.add(start);

        while (!queue.isEmpty()) {
            Solution.Node current = queue.poll();
            for (Solution.Node neighbor : current.neighbors) {
                if (visited.add(neighbor)) {
                    queue.add(neighbor);
                }
            }
        }

        return visited.size();
    }

    // -------------------------------------------------------------------------
    // Happy-path cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("example one: 4-node square (cycle) graph")
    void fourNodeSquareGraph_returnsDeepCopy() {
        Solution.Node[] nodes = buildGraph(new int[][] {{2, 4}, {1, 3}, {2, 4}, {1, 3}});

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertIsDeepCopy(nodes[0], clone);
        assertThat(countReachableNodes(clone)).isEqualTo(4);
    }

    @Test
    @DisplayName("triangle graph: three nodes each connected to the other two")
    void triangleGraph_returnsDeepCopy() {
        Solution.Node[] nodes = buildGraph(new int[][] {{2, 3}, {1, 3}, {1, 2}});

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertIsDeepCopy(nodes[0], clone);
        assertThat(countReachableNodes(clone)).isEqualTo(3);
    }

    @Test
    @DisplayName("path graph: straight line 1-2-3-4")
    void pathGraph_returnsDeepCopy() {
        Solution.Node[] nodes = buildGraph(new int[][] {{2}, {1, 3}, {2, 4}, {3}});

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertIsDeepCopy(nodes[0], clone);
        assertThat(countReachableNodes(clone)).isEqualTo(4);
    }

    @Test
    @DisplayName("star graph: one hub connected to four leaves")
    void starGraph_returnsDeepCopy() {
        Solution.Node[] nodes = buildGraph(new int[][] {{2, 3, 4, 5}, {1}, {1}, {1}, {1}});

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertIsDeepCopy(nodes[0], clone);
        assertThat(countReachableNodes(clone)).isEqualTo(5);
    }

    @Test
    @DisplayName("pentagon graph: five nodes forming a 5-cycle")
    void pentagonGraph_returnsDeepCopy() {
        Solution.Node[] nodes = buildGraph(new int[][] {{2, 5}, {1, 3}, {2, 4}, {3, 5}, {1, 4}});

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertIsDeepCopy(nodes[0], clone);
        assertThat(countReachableNodes(clone)).isEqualTo(5);
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("empty graph: null input returns null")
    void emptyGraph_returnsNull() {
        assertThat(solution.cloneGraph(null)).isNull();
    }

    @Test
    @DisplayName("single node with no neighbors")
    void singleNodeNoNeighbors_returnsDeepCopy() {
        Solution.Node[] nodes = buildGraph(new int[][] {{}});

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertIsDeepCopy(nodes[0], clone);
        assertThat(clone.neighbors).isEmpty();
    }

    @Test
    @DisplayName("mutating the original graph after cloning does not affect the clone")
    void mutatingOriginalAfterClone_doesNotAffectClone() {
        Solution.Node[] nodes = buildGraph(new int[][] {{2}, {1}});

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        Solution.Node newNode = new Solution.Node(3);
        nodes[0].neighbors.add(newNode);
        nodes[1].neighbors.add(newNode);
        newNode.neighbors.add(nodes[0]);
        newNode.neighbors.add(nodes[1]);

        assertThat(clone.neighbors).hasSize(1);
        assertThat(countReachableNodes(clone)).isEqualTo(2);
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("min node value boundary: single node with val 1")
    void minNodeValueBoundary_singleNode() {
        Solution.Node[] nodes = buildGraph(new int[][] {{}});

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertThat(clone.val).isEqualTo(1);
    }

    @Test
    @DisplayName("max node value boundary: two-node graph where the second node has val 100")
    void maxNodeValueBoundary_twoNodeGraph() {
        int[][] adjList = new int[100][];
        adjList[0] = new int[] {100};
        for (int i = 1; i < 99; i++) {
            adjList[i] = new int[] {};
        }
        adjList[99] = new int[] {1};
        Solution.Node[] nodes = buildGraph(adjList);

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertThat(clone.neighbors).hasSize(1);
        assertThat(clone.neighbors.get(0).val).isEqualTo(100);
    }

    @Test
    @DisplayName("max node count boundary: 100-node cycle clones without stack overflow")
    void maxNodeCountBoundary_hundredNodeCycle() {
        int n = 100;
        int[][] adjList = new int[n][];
        for (int i = 0; i < n; i++) {
            int prev = (i - 1 + n) % n + 1;
            int next = (i + 1) % n + 1;
            adjList[i] = new int[] {prev, next};
        }
        Solution.Node[] nodes = buildGraph(adjList);

        Solution.Node clone = solution.cloneGraph(nodes[0]);

        assertIsDeepCopy(nodes[0], clone);
        assertThat(countReachableNodes(clone)).isEqualTo(n);
    }
}
