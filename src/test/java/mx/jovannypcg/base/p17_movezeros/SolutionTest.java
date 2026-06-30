package mx.jovannypcg.base.p17_movezeros;

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
                Arguments.of(new int[]{0, 0, 1, 2, 0, 5}, new int[]{1, 2, 5, 0, 0, 0}),
                Arguments.of(new int[]{0, 1, 0}, new int[]{1, 0, 0}),
                Arguments.of(new int[]{1, 2, 3}, new int[]{1, 2, 3}),
                Arguments.of(new int[]{0, 0, 0}, new int[]{0, 0, 0}),
                Arguments.of(new int[]{1, 0, 2, 0, 3}, new int[]{1, 2, 3, 0, 0}),
                Arguments.of(new int[]{0, 0, 1}, new int[]{1, 0, 0}),
                Arguments.of(new int[]{1, 0, 0}, new int[]{1, 0, 0})
        );
    }

    @ParameterizedTest(name = "[{index}] nums={0} → {1}")
    @MethodSource("provideTestCases")
    @DisplayName("Parameterized: various inputs with zeroes and non-zeroes")
    void parameterized(int[] nums, int[] expected) {
        solution.moveZeroes(nums);
        assertThat(nums).isEqualTo(expected);
    }

    @Test
    @DisplayName("Single non-zero element — array unchanged")
    void singleNonZero() {
        int[] nums = {42};
        solution.moveZeroes(nums);
        assertThat(nums).isEqualTo(new int[]{42});
    }

    @Test
    @DisplayName("Single zero element — array unchanged")
    void singleZero() {
        int[] nums = {0};
        solution.moveZeroes(nums);
        assertThat(nums).isEqualTo(new int[]{0});
    }

    @Test
    @DisplayName("Zeroes already at the end — array unchanged")
    void zeroesAlreadyAtEnd() {
        int[] nums = {3, 7, 0, 0};
        solution.moveZeroes(nums);
        assertThat(nums).isEqualTo(new int[]{3, 7, 0, 0});
    }

    @Test
    @DisplayName("All zeroes — array unchanged")
    void allZeroes() {
        int[] nums = {0, 0, 0, 0};
        solution.moveZeroes(nums);
        assertThat(nums).isEqualTo(new int[]{0, 0, 0, 0});
    }

    @Test
    @DisplayName("No zeroes — relative order preserved")
    void noZeroes() {
        int[] nums = {5, 3, 1, 4};
        solution.moveZeroes(nums);
        assertThat(nums).isEqualTo(new int[]{5, 3, 1, 4});
    }

    @Test
    @DisplayName("Large negative values — moved correctly alongside zeroes")
    void largeNegativeValues() {
        int[] nums = {0, Integer.MIN_VALUE, 0, Integer.MAX_VALUE};
        solution.moveZeroes(nums);
        assertThat(nums).isEqualTo(new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE, 0, 0});
    }

    @Test
    @DisplayName("Single zero at start — moved to end")
    void singleZeroAtStart() {
        int[] nums = {0, 1, 2, 3};
        solution.moveZeroes(nums);
        assertThat(nums).isEqualTo(new int[]{1, 2, 3, 0});
    }
}
