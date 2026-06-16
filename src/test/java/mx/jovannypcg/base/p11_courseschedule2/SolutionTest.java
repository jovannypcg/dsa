package mx.jovannypcg.base.p11_courseschedule2;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    // Validates that `order` is a valid topological ordering for the given prerequisites.
    private boolean isValidOrder(int numCourses, int[][] prerequisites, int[] order) {
        if (order.length != numCourses) return false;

        // each course must appear exactly once
        Set<Integer> seen = new HashSet<>();
        for (int c : order) seen.add(c);
        if (seen.size() != numCourses) return false;

        // for every prerequisite [a, b], b must appear before a in the order
        int[] position = new int[numCourses];
        for (int i = 0; i < order.length; i++) position[order[i]] = i;
        for (int[] pre : prerequisites) {
            if (position[pre[1]] >= position[pre[0]]) return false;
        }
        return true;
    }

    // --- happy path ---

    @Test
    void singleCourse_noPrerequisites() {
        int[] result = solution.findOrder(1, new int[][]{});
        assertThat(result).containsExactly(0);
    }

    @Test
    void twoCourses_onePrerequisite() {
        int[] result = solution.findOrder(2, new int[][]{{1, 0}});
        assertThat(isValidOrder(2, new int[][]{{1, 0}}, result)).isTrue();
    }

    @Test
    void fourCourses_diamondPrerequisites() {
        int[][] prereqs = {{1, 0}, {2, 0}, {3, 1}, {3, 2}};
        int[] result = solution.findOrder(4, prereqs);
        assertThat(isValidOrder(4, prereqs, result)).isTrue();
    }

    @Test
    void linearChain() {
        int[][] prereqs = {{1, 0}, {2, 1}, {3, 2}};
        int[] result = solution.findOrder(4, prereqs);
        assertThat(isValidOrder(4, prereqs, result)).isTrue();
    }

    @Test
    void noPrerequisites_manyCourses() {
        int[] result = solution.findOrder(5, new int[][]{});
        assertThat(result).hasSize(5);
        assertThat(isValidOrder(5, new int[][]{}, result)).isTrue();
    }

    // --- cycle detection ---

    @Test
    void twoCourses_directCycle_returnsEmpty() {
        int[] result = solution.findOrder(2, new int[][]{{1, 0}, {0, 1}});
        assertThat(result).isEmpty();
    }

    @Test
    void threeCourses_indirectCycle_returnsEmpty() {
        int[] result = solution.findOrder(3, new int[][]{{1, 0}, {2, 1}, {0, 2}});
        assertThat(result).isEmpty();
    }

    @Test
    void selfLoop_returnsEmpty() {
        // ai != bi is guaranteed by constraints, but a two-node mutual dependency is the minimal cycle
        int[] result = solution.findOrder(3, new int[][]{{0, 1}, {1, 2}, {2, 0}});
        assertThat(result).isEmpty();
    }

    // --- boundary values ---

    @Test
    void maxCourses_noPrerequisites() {
        int n = 2000;
        int[] result = solution.findOrder(n, new int[][]{});
        assertThat(result).hasSize(n);
        assertThat(isValidOrder(n, new int[][]{}, result)).isTrue();
    }

    @Test
    void maxCourses_linearChain() {
        int n = 2000;
        int[][] prereqs = new int[n - 1][2];
        for (int i = 1; i < n; i++) prereqs[i - 1] = new int[]{i, i - 1};
        int[] result = solution.findOrder(n, prereqs);
        assertThat(isValidOrder(n, prereqs, result)).isTrue();
    }

    @Test
    void disconnectedComponents() {
        // courses 0-1 and courses 2-3 are independent groups
        int[][] prereqs = {{1, 0}, {3, 2}};
        int[] result = solution.findOrder(4, prereqs);
        assertThat(isValidOrder(4, prereqs, result)).isTrue();
    }

    @Test
    void singlePrerequisite_lastCourseFirst() {
        int[][] prereqs = {{0, 1}};
        int[] result = solution.findOrder(2, prereqs);
        assertThat(isValidOrder(2, prereqs, result)).isTrue();
    }
}
