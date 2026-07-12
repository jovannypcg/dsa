package mx.jovannypcg.base.p25_squaresofsortedarray;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    @DisplayName("example 1: mixed negatives and positives")
    void mixedNegativesAndPositives() {
        Solution solution = new Solution();
        int[] nums = {-4, -1, 0, 3, 10};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(0, 1, 9, 16, 100);
    }

    @Test
    @DisplayName("example 2: mixed negatives and positives with duplicate square")
    void mixedWithDuplicateSquare() {
        Solution solution = new Solution();
        int[] nums = {-7, -3, 2, 3, 11};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(4, 9, 9, 49, 121);
    }

    @Test
    @DisplayName("all negative values reverse order after squaring")
    void allNegativeValues() {
        Solution solution = new Solution();
        int[] nums = {-5, -3, -2, -1};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(1, 4, 9, 25);
    }

    @Test
    @DisplayName("all non-negative values preserve order after squaring")
    void allNonNegativeValues() {
        Solution solution = new Solution();
        int[] nums = {1, 2, 3, 4};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(1, 4, 9, 16);
    }

    @Test
    @DisplayName("single element array")
    void singleElement() {
        Solution solution = new Solution();
        int[] nums = {0};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(0);
    }

    @Test
    @DisplayName("single negative element")
    void singleNegativeElement() {
        Solution solution = new Solution();
        int[] nums = {-7};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(49);
    }

    @Test
    @DisplayName("all zeros")
    void allZeros() {
        Solution solution = new Solution();
        int[] nums = {0, 0, 0};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(0, 0, 0);
    }

    @Test
    @DisplayName("boundary values at minimum and maximum constraint bounds")
    void boundaryConstraintValues() {
        Solution solution = new Solution();
        int[] nums = {-10000, -1, 0, 1, 10000};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(0, 1, 1, 100000000, 100000000);
    }

    @Test
    @DisplayName("two elements, both negative")
    void twoNegativeElements() {
        Solution solution = new Solution();
        int[] nums = {-2, -1};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(1, 4);
    }

    @Test
    @DisplayName("larger array with repeated absolute values on both sides")
    void repeatedAbsoluteValuesBothSides() {
        Solution solution = new Solution();
        int[] nums = {-4, -4, -2, 0, 2, 4, 4};

        int[] result = solution.sortedSquares(nums);

        assertThat(result).containsExactly(0, 4, 4, 16, 16, 16, 16);
    }
}
