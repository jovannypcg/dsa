package mx.jovannypcg.base.p71_fibonaccinumber;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SolutionTest {

    @Test
    @DisplayName("n = 0 → base case returns 0")
    void nIsZero() {
        Solution solution = new Solution();

        assertThat(solution.fib(0)).isEqualTo(0);
    }

    @Test
    @DisplayName("n = 1 → base case returns 1")
    void nIsOne() {
        Solution solution = new Solution();

        assertThat(solution.fib(1)).isEqualTo(1);
    }

    @Test
    @DisplayName("n = 2 → sum of the two base cases")
    void nIsTwo() {
        Solution solution = new Solution();

        assertThat(solution.fib(2)).isEqualTo(1);
    }

    @Test
    @DisplayName("n = 3 → F(3) equals 2")
    void nIsThree() {
        Solution solution = new Solution();

        assertThat(solution.fib(3)).isEqualTo(2);
    }

    @Test
    @DisplayName("n = 4 → F(4) equals 3")
    void nIsFour() {
        Solution solution = new Solution();

        assertThat(solution.fib(4)).isEqualTo(3);
    }

    @Test
    @DisplayName("n = 10 → F(10) equals 55")
    void nIsTen() {
        Solution solution = new Solution();

        assertThat(solution.fib(10)).isEqualTo(55);
    }

    @Test
    @DisplayName("n = 30 → upper bound of constraints, F(30) equals 832040")
    void nIsUpperBound() {
        Solution solution = new Solution();

        assertThat(solution.fib(30)).isEqualTo(832_040);
    }

    @Test
    @DisplayName("n = 40 → upper bound of constraints, F(30) equals 832040")
    void nIsExceededBound() {
        Solution solution = new Solution();

        assertThat(solution.fib(40)).isEqualTo(102_334_155);
    }
}
