package mx.jovannypcg.base.p81_topologicalsort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    /**
     * Verifies that {@code order} is a valid topological order: it must contain every node
     * from 0 to n - 1 exactly once, and for every edge [u, v], u must appear before v.
     */
    private void assertValidTopoOrder(int[] order, int n, int[][] edges) {
        assertThat(order).hasSize(n);

        Set<Integer> seen = new HashSet<>();
        int[] position = new int[n];

        for (int i = 0; i < order.length; i++) {
            int node = order[i];
            assertThat(node).isBetween(0, n - 1);
            assertThat(seen.add(node)).as("node %d appears more than once in order", node).isTrue();
            position[node] = i;
        }

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            assertThat(position[u])
                    .as("expected node %d before node %d due to edge [%d, %d]", u, v, u, v)
                    .isLessThan(position[v]);
        }
    }

    @Test
    @DisplayName("diamond-shaped DAG produces a valid order")
    void diamondShapedDagProducesValidOrder() {
        int n = 4;
        int[][] edges = {{0, 1}, {0, 2}, {1, 3}, {2, 3}};

        int[] result = solution.topoSortKahn(n, edges);

        assertValidTopoOrder(result, n, edges);
    }

    @Test
    @DisplayName("two-node cycle → empty array")
    void twoNodeCycleReturnsEmptyArray() {
        int n = 2;
        int[][] edges = {{0, 1}, {1, 0}};

        int[] result = solution.topoSortKahn(n, edges);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("single node with no edges → order containing just that node")
    void singleNodeNoEdges() {
        int n = 1;
        int[][] edges = {};

        int[] result = solution.topoSortKahn(n, edges);

        assertThat(result).containsExactly(0);
    }

    @Test
    @DisplayName("six-node graph with multiple independent roots produces a valid order")
    void sixNodeGraphWithMultipleRoots() {
        int n = 6;
        int[][] edges = {{5, 2}, {5, 0}, {4, 0}, {4, 1}, {2, 3}, {3, 1}};

        int[] result = solution.topoSortKahn(n, edges);

        assertValidTopoOrder(result, n, edges);
    }

    @Test
    @DisplayName("no edges at all → any permutation of nodes is valid")
    void noEdgesAtAll() {
        int n = 3;
        int[][] edges = {};

        int[] result = solution.topoSortKahn(n, edges);

        assertValidTopoOrder(result, n, edges);
    }

    @Test
    @DisplayName("self-contained cycle among a subset of nodes with an otherwise free node → empty array")
    void partialCycleAmongSubsetOfNodesStillFails() {
        int n = 4;
        int[][] edges = {{0, 1}, {1, 2}, {2, 0}, {3, 0}};

        int[] result = solution.topoSortKahn(n, edges);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("three-way disconnected components produce a valid order")
    void disconnectedComponentsProduceValidOrder() {
        int n = 6;
        int[][] edges = {{0, 1}, {2, 3}, {4, 5}};

        int[] result = solution.topoSortKahn(n, edges);

        assertValidTopoOrder(result, n, edges);
    }

    @Test
    @DisplayName("long chain of dependencies produces the exact single valid order")
    void longChainProducesExactOrder() {
        int n = 5;
        int[][] edges = {{0, 1}, {1, 2}, {2, 3}, {3, 4}};

        int[] result = solution.topoSortKahn(n, edges);

        assertThat(result).containsExactly(0, 1, 2, 3, 4);
    }

    @Test
    @DisplayName("large chain at upper boundary style input still resolves to a valid order")
    void largeChainResolvesToValidOrder() {
        int n = 1000;
        int[][] edges = new int[n - 1][2];
        for (int i = 0; i < n - 1; i++) {
            edges[i] = new int[] {i, i + 1};
        }

        int[] result = solution.topoSortKahn(n, edges);

        assertValidTopoOrder(result, n, edges);
    }

    @Test
    @DisplayName("redundant duplicate edge between the same pair of nodes doesn't break the ordering")
    void duplicateEdgeDoesNotBreakOrdering() {
        int n = 3;
        int[][] edges = {{0, 1}, {0, 1}, {1, 2}};

        int[] result = solution.topoSortKahn(n, edges);

        assertValidTopoOrder(result, n, edges);
    }
}
