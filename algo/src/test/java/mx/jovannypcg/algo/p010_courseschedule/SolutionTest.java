package mx.jovannypcg.algo.p010_courseschedule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    @DisplayName("simple valid ordering, one prerequisite -> true")
    void simpleValidOrdering() {
        boolean result = solution.canFinish(2, new int[][]{{1, 0}});

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("cycle between two courses -> false")
    void cycleBetweenTwoCourses() {
        boolean result = solution.canFinish(2, new int[][]{{1, 0}, {0, 1}});

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("single course, no prerequisites -> true")
    void singleCourseNoPrerequisites() {
        boolean result = solution.canFinish(1, new int[][]{});

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("many courses, no prerequisites at all -> true")
    void manyCoursesNoPrerequisites() {
        boolean result = solution.canFinish(2000, new int[][]{});

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("linear chain with no cycle -> true")
    void linearChainNoCycle() {
        boolean result = solution.canFinish(4, new int[][]{{1, 0}, {2, 1}, {3, 2}});

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("three-node cycle -> false")
    void threeNodeCycle() {
        boolean result = solution.canFinish(3, new int[][]{{0, 1}, {1, 2}, {2, 0}});

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("multiple prerequisites converging, no cycle -> true")
    void multiplePrerequisitesConverging() {
        boolean result = solution.canFinish(3, new int[][]{{0, 1}, {0, 2}, {1, 2}});

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("disconnected graph, all components acyclic -> true")
    void disconnectedGraphAllAcyclic() {
        boolean result = solution.canFinish(4, new int[][]{{1, 0}, {3, 2}});

        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("disconnected graph, one component has a cycle -> false")
    void disconnectedGraphOneComponentHasCycle() {
        boolean result = solution.canFinish(4, new int[][]{{1, 0}, {0, 1}, {3, 2}});

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("self-loop (ai == bi) -> false")
    void selfLoop() {
        boolean result = solution.canFinish(2, new int[][]{{0, 0}});

        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("boundary: large linear chain of 2000 courses -> true")
    void largeLinearChainBoundary() {
        int numCourses = 2000;
        int[][] prerequisites = new int[numCourses - 1][2];

        for (int i = 0; i < numCourses - 1; i++) {
            prerequisites[i] = new int[]{i + 1, i};
        }

        boolean result = solution.canFinish(numCourses, prerequisites);

        assertThat(result).isTrue();
    }
}
