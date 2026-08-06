package mx.jovannypcg.base.p34_productofarrayexceptself;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SolutionTest {

    @Test
    @DisplayName("example from problem statement, no zeros")
    void exampleNoZeros() {
        Solution solution = new Solution();
        int[] nums = {1, 2, 3, 4};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(24, 12, 8, 6);
    }

    @Test
    @DisplayName("single zero in the array zeroes out every index except the zero's own")
    void singleZero() {
        Solution solution = new Solution();
        int[] nums = {-1, 1, 0, -3, 3};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(0, 0, 9, 0, 0);
    }

    @Test
    @DisplayName("two zeros in the array makes every index zero")
    void twoZeros() {
        Solution solution = new Solution();
        int[] nums = {0, 4, 0, 2};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(0, 0, 0, 0);
    }

    @Test
    @DisplayName("minimum length array of two elements")
    void minimumLengthArray() {
        Solution solution = new Solution();
        int[] nums = {2, 3};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(3, 2);
    }

    @Test
    @DisplayName("all elements identical produce identical results")
    void allElementsIdentical() {
        Solution solution = new Solution();
        int[] nums = {1, 1, 1, 1};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(1, 1, 1, 1);
    }

    @Test
    @DisplayName("all negative values still produce correct signs")
    void allNegativeValues() {
        Solution solution = new Solution();
        int[] nums = {-1, -1, -1};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(1, 1, 1);
    }

    @Test
    @DisplayName("boundary values at the extremes of the constraint range")
    void boundaryValueExtremes() {
        Solution solution = new Solution();
        int[] nums = {30, -30, 30, -30};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(27000, -27000, 27000, -27000);
    }

    @Test
    @DisplayName("array containing a mix of positive, negative, and zero values")
    void mixedPositiveNegativeAndZero() {
        Solution solution = new Solution();
        int[] nums = {2, -3, 0, 5, -1};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(0, 0, 30, 0, 0);
    }

    @Test
    @DisplayName("larger array without zeros to verify overall product accumulation")
    void largerArrayWithoutZeros() {
        Solution solution = new Solution();
        int[] nums = {1, 2, 3, 4, 5, 6};

        int[] result = solution.productExceptSelf(nums);

        assertThat(result).containsExactly(720, 360, 240, 180, 144, 120);
    }
}
