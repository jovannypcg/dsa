package mx.jovannypcg.base.p15_removeduplicates;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
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
                // happy path
                Arguments.of(new int[]{1, 1, 2, 3, 4}, 4, new int[]{1, 2, 3, 4}),
                Arguments.of(new int[]{2, 10, 10, 30, 30, 30}, 3, new int[]{2, 10, 30}),

                // negative values
                Arguments.of(new int[]{-3, -1, -1, 0, 0, 5}, 4, new int[]{-3, -1, 0, 5}),

                // all duplicates
                Arguments.of(new int[]{1, 1, 1, 1}, 1, new int[]{1}),

                // no duplicates
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 5, new int[]{1, 2, 3, 4, 5}),

                // single element (minimum length per constraints)
                Arguments.of(new int[]{7}, 1, new int[]{7}),

                // two elements, duplicates
                Arguments.of(new int[]{5, 5}, 1, new int[]{5}),

                // two elements, unique
                Arguments.of(new int[]{5, 6}, 2, new int[]{5, 6}),

                // boundary values: min and max per constraints
                Arguments.of(new int[]{-100, -100, 100, 100}, 2, new int[]{-100, 100}),

                // all same value at boundary min
                Arguments.of(new int[]{-100, -100, -100}, 1, new int[]{-100}),

                // all same value at boundary max
                Arguments.of(new int[]{100, 100, 100}, 1, new int[]{100}),

                // mixed: zeros, repeated small values, large gaps
                Arguments.of(new int[]{0, 0, 1, 1, 1, 2, 2, 200, 200, 300, 3000}, 6, new int[]{0, 1, 2, 200, 300, 3000})
        );
    }

    @ParameterizedTest(name = "[{index}] nums={0} → k={1}, unique={2}")
    @MethodSource("cases")
    @DisplayName("removeDuplicates returns correct k and mutates first k elements correctly")
    void removeDuplicates(int[] nums, int expectedK, int[] expectedPrefix) {
        int k = solution.removeDuplicates(nums);

        assertThat(k).isEqualTo(expectedK);
        assertThat(Arrays.copyOf(nums, k)).isEqualTo(expectedPrefix);
    }
}
