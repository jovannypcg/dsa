package mx.jovannypcg.algo.p077_networkdelaytime;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    @DisplayName("multi-hop network, signal reaches all nodes → returns max shortest distance")
    void multiHopNetworkReturnsMaxShortestDistance() {
        int[][] times = {{2, 1, 1}, {2, 3, 1}, {3, 4, 1}};

        int result = new Solution().networkDelayTime(times, 4, 2);

        assertThat(result).isEqualTo(2);
    }

    @Test
    @DisplayName("single direct edge, k is the source → returns edge weight")
    void singleEdgeSourceIsK() {
        int[][] times = {{1, 2, 1}};

        int result = new Solution().networkDelayTime(times, 2, 1);

        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("single direct edge, k has no outgoing edge → returns -1")
    void singleEdgeKHasNoOutgoingEdge() {
        int[][] times = {{1, 2, 1}};

        int result = new Solution().networkDelayTime(times, 2, 2);

        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("isolated node unreachable from k → returns -1")
    void isolatedNodeIsUnreachable() {
        int[][] times = {{1, 2, 1}};

        int result = new Solution().networkDelayTime(times, 3, 1);

        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("multiple paths to same node → shortest path wins")
    void multiplePathsShortestWins() {
        int[][] times = {{1, 2, 5}, {1, 3, 1}, {3, 2, 1}, {2, 4, 1}};

        int result = new Solution().networkDelayTime(times, 4, 1);

        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("zero-weight edge boundary (wi = 0) → returns 0")
    void zeroWeightEdgeBoundary() {
        int[][] times = {{1, 2, 0}};

        int result = new Solution().networkDelayTime(times, 2, 1);

        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("max-weight edge boundary (wi = 100) → returns 100")
    void maxWeightEdgeBoundary() {
        int[][] times = {{1, 2, 100}};

        int result = new Solution().networkDelayTime(times, 2, 1);

        assertThat(result).isEqualTo(100);
    }

    @Test
    @DisplayName("straight chain at n = 100 boundary → returns 99")
    void straightChainAtMaxNodeBoundary() {
        int n = 100;
        int[][] times = new int[n - 1][3];
        for (int i = 0; i < n - 1; i++) {
            times[i] = new int[]{i + 1, i + 2, 1};
        }

        int result = new Solution().networkDelayTime(times, n, 1);

        assertThat(result).isEqualTo(99);
    }

    @Test
    @DisplayName("k equals n boundary, source is highest-labeled node → returns max shortest distance")
    void kEqualsNBoundary() {
        int[][] times = {{3, 1, 2}, {3, 2, 1}};

        int result = new Solution().networkDelayTime(times, 3, 3);

        assertThat(result).isEqualTo(2);
    }
}
