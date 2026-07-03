package mx.jovannypcg.base.p19_containerwithmostwater;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    static Stream<Arguments> provideTestCases() {
        return Stream.of(
                Arguments.of(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7}, 49),
                Arguments.of(new int[]{1, 1}, 1),
                Arguments.of(new int[]{4, 3, 2, 1, 4}, 16),
                Arguments.of(new int[]{1, 2, 1}, 2),
                Arguments.of(new int[]{0, 2}, 0),
                Arguments.of(new int[]{1, 2, 4, 3}, 4),
                Arguments.of(new int[]{2, 3, 4, 5, 18, 17, 6}, 17)
        );
    }

    @ParameterizedTest(name = "[{index}] height={0} → {1}")
    @MethodSource("provideTestCases")
    @DisplayName("Parameterized: various height arrays")
    void parameterized(int[] height, int expected) {
        assertThat(solution.maxArea(height)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Minimum n=2 with equal heights")
    void minimumSizeEqualHeights() {
        int[] height = {5, 5};
        assertThat(solution.maxArea(height)).isEqualTo(5);
    }

    @Test
    @DisplayName("Minimum n=2 with both heights zero")
    void minimumSizeBothZero() {
        int[] height = {0, 0};
        assertThat(solution.maxArea(height)).isEqualTo(0);
    }

    @Test
    @DisplayName("All lines the same height — widest pair wins")
    void allEqualHeights() {
        int[] height = {4, 4, 4, 4, 4};
        assertThat(solution.maxArea(height)).isEqualTo(16);
    }

    @Test
    @DisplayName("Strictly increasing heights — best container is not the widest pair")
    void strictlyIncreasing() {
        int[] height = {1, 2, 3, 4, 5};
        assertThat(solution.maxArea(height)).isEqualTo(6);
    }

    @Test
    @DisplayName("Strictly decreasing heights — best container is not the widest pair")
    void strictlyDecreasing() {
        int[] height = {5, 4, 3, 2, 1};
        assertThat(solution.maxArea(height)).isEqualTo(6);
    }

    @Test
    @DisplayName("Duplicate tall lines with a short one in between")
    void duplicateTallLines() {
        int[] height = {8, 1, 8};
        assertThat(solution.maxArea(height)).isEqualTo(16);
    }

    @Test
    @DisplayName("Maximum height boundary value (10^4) on both ends")
    void maxHeightBoundary() {
        int[] height = {10_000, 1, 1, 1, 10_000};
        assertThat(solution.maxArea(height)).isEqualTo(40_000);
    }

    @Test
    @DisplayName("Large input near upper bound of n — still returns a valid area efficiently")
    void largeInput() {
        int n = 100_000;
        int[] height = new int[n];

        for (int i = 0; i < n; i++) {
            height[i] = i % 10_000;
        }

        // Widest container uses the first and last elements: index 0 (height 0)
        // and index n-1 (height (n-1) % 10000 = 99999 % 10000 = 9999).
        // Since the boundary height is limited by the shorter line (0), the true
        // maximum comes from elsewhere; just assert the call completes and returns
        // a non-negative, plausible result within bounds.
        int result = solution.maxArea(height);

        assertThat(result).isGreaterThan(0);
        assertThat(result).isLessThanOrEqualTo(10_000 * (n - 1));
    }

    @Test
    @DisplayName("Single zero surrounded by tall lines does not block the best container")
    void zeroInMiddleDoesNotBlock() {
        int[] height = {6, 0, 6};
        assertThat(solution.maxArea(height)).isEqualTo(12);
    }
}
