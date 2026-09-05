package mx.jovannypcg.base.p73_mincostclimbingstairs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("example from problem statement: start at index 1, output 15")
    void example1() {
        int[] cost = {10, 15, 20};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(15);
    }

    @Test
    @DisplayName("example from problem statement: alternating cheap steps, output 6")
    void example2() {
        int[] cost = {1, 100, 1, 1, 1, 100, 1, 1, 100, 1};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(6);
    }

    @Test
    @DisplayName("minimum length input of two steps, both equal cost")
    void minimumLengthEqualCosts() {
        int[] cost = {999, 999};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(999);
    }

    @Test
    @DisplayName("minimum length input of two steps, first cost zero")
    void minimumLengthFirstZero() {
        int[] cost = {0, 2};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(0);
    }

    @Test
    @DisplayName("all zero costs yields zero total cost")
    void allZeroCosts() {
        int[] cost = {0, 0, 0, 0};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(0);
    }

    @Test
    @DisplayName("all steps at maximum cost boundary of 999")
    void allMaxCosts() {
        int[] cost = {999, 999, 999, 999, 999};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(1998);
    }

    @Test
    @DisplayName("strictly increasing costs favors jumping over expensive later steps")
    void strictlyIncreasingCosts() {
        int[] cost = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(25);
    }

    @Test
    @DisplayName("cheap steps surrounding a costly middle step avoids the expensive path")
    void avoidsExpensiveEdges() {
        int[] cost = {1, 0, 0, 0, 1};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(0);
    }

    @Test
    @DisplayName("strictly decreasing costs")
    void strictlyDecreasingCosts() {
        int[] cost = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(25);
    }

    @Test
    @DisplayName("large input at upper bound of length constraint runs efficiently")
    void largeInputAtMaxLength() {
        int[] cost = new int[1000];
        for (int i = 0; i < cost.length; i++) {
            cost[i] = 1;
        }

        assertThat(solution.minCostClimbingStairs(cost)).isEqualTo(500);
    }
}
