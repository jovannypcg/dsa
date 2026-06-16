package mx.jovannypcg.base.p10_courseschedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                // happy path: simple valid ordering
                Arguments.of(2, new int[][]{{1, 0}}, true),

                // cycle between two courses
                Arguments.of(2, new int[][]{{1, 0}, {0, 1}}, false),

                // no prerequisites at all — always possible
                Arguments.of(1, new int[][]{}, true),
                Arguments.of(2000, new int[][]{}, true),

                // single course, no prerequisites
                Arguments.of(1, new int[][]{}, true),

                // linear chain with no cycle
                Arguments.of(4, new int[][]{{1, 0}, {2, 1}, {3, 2}}, true),

                // three-node cycle
                Arguments.of(3, new int[][]{{0, 1}, {1, 2}, {2, 0}}, false),

                // multiple prerequisites converging — no cycle
                Arguments.of(3, new int[][]{{0, 1}, {0, 2}, {1, 2}}, true),

                // disconnected graph, all acyclic
                Arguments.of(4, new int[][]{{1, 0}, {3, 2}}, true),

                // disconnected graph, one component has a cycle
                Arguments.of(4, new int[][]{{1, 0}, {0, 1}, {3, 2}}, false),

                // self-loop (ai == bi) — immediate cycle
                Arguments.of(2, new int[][]{{0, 0}}, false),

                // large linear chain (boundary: 2000 courses)
                buildLinearChain(2000)
        );
    }

    /** Builds a case with a linear chain: 0->1->2->...->n-1 (no cycle). */
    private static Arguments buildLinearChain(int n) {
        int[][] prereqs = new int[n - 1][2];
        for (int i = 0; i < n - 1; i++) {
            prereqs[i] = new int[]{i + 1, i};
        }
        return Arguments.of(n, prereqs, true);
    }

    @ParameterizedTest
    @MethodSource("cases")
    void canFinish(int numCourses, int[][] prerequisites, boolean expected) {
        assertThat(solution.canFinish(numCourses, prerequisites)).isEqualTo(expected);
    }
}
