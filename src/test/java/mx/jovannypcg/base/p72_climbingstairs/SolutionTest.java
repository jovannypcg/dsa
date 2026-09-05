package mx.jovannypcg.base.p72_climbingstairs;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("n = 1 (lower boundary) → 1 way")
    void lowerBoundaryOneStep() {
        assertThat(solution.climbStairs(1)).isEqualTo(1);
    }

    @Test
    @DisplayName("n = 2 → 2 ways")
    void twoSteps() {
        assertThat(solution.climbStairs(2)).isEqualTo(2);
    }

    @Test
    @DisplayName("n = 3 → 3 ways")
    void threeSteps() {
        assertThat(solution.climbStairs(3)).isEqualTo(3);
    }

    @Test
    @DisplayName("n = 4 → 5 ways")
    void fourSteps() {
        assertThat(solution.climbStairs(4)).isEqualTo(5);
    }

    @Test
    @DisplayName("n = 5 → 8 ways")
    void fiveSteps() {
        assertThat(solution.climbStairs(5)).isEqualTo(8);
    }

    @Test
    @DisplayName("n = 6 → 13 ways")
    void sixSteps() {
        assertThat(solution.climbStairs(6)).isEqualTo(13);
    }

    @Test
    @DisplayName("n = 45 (upper boundary) → 1836311903 ways, fits in int")
    void upperBoundaryFortyFiveSteps() {
        assertThat(solution.climbStairs(45)).isEqualTo(1836311903);
    }
}
