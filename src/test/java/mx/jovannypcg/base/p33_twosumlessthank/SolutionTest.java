package mx.jovannypcg.base.p33_twosumlessthank;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    @DisplayName("typical case with mixed values → returns maximum valid sum")
    void typicalCase() {
        Solution solution = new Solution();
        int[] nums = {34, 23, 1, 24, 75, 33, 54, 8};

        assertThat(solution.twoSumLessThanK(nums, 60)).isEqualTo(58);
    }

    @Test
    @DisplayName("no pair sums below k → returns -1")
    void noValidPair() {
        Solution solution = new Solution();
        int[] nums = {10, 20, 30};

        assertThat(solution.twoSumLessThanK(nums, 15)).isEqualTo(-1);
    }

    @Test
    @DisplayName("small ascending array → returns best pair below k")
    void smallAscendingArray() {
        Solution solution = new Solution();
        int[] nums = {1, 2, 3, 4};

        assertThat(solution.twoSumLessThanK(nums, 6)).isEqualTo(5);
    }

    @Test
    @DisplayName("duplicate values still form a valid pair")
    void duplicateValues() {
        Solution solution = new Solution();
        int[] nums = {5, 5};

        assertThat(solution.twoSumLessThanK(nums, 11)).isEqualTo(10);
    }

    @Test
    @DisplayName("every pair equals k → returns -1 since sum must be strictly less than k")
    void allPairsEqualK() {
        Solution solution = new Solution();
        int[] nums = {1, 1, 1};

        assertThat(solution.twoSumLessThanK(nums, 2)).isEqualTo(-1);
    }

    @Test
    @DisplayName("single element array (below minimum pair size) → returns -1")
    void singleElementArray() {
        Solution solution = new Solution();
        int[] nums = {5};

        assertThat(solution.twoSumLessThanK(nums, 100)).isEqualTo(-1);
    }

    @Test
    @DisplayName("minimum value elements at minimum k boundary → returns -1")
    void minimumValuesMinimumK() {
        Solution solution = new Solution();
        int[] nums = {1, 1};

        assertThat(solution.twoSumLessThanK(nums, 1)).isEqualTo(-1);
    }

    @Test
    @DisplayName("maximum value elements at maximum k boundary → returns maximum sum")
    void maximumValuesMaximumK() {
        Solution solution = new Solution();
        int[] nums = {1000, 1000, 999};

        assertThat(solution.twoSumLessThanK(nums, 2000)).isEqualTo(1999);
    }

    @Test
    @DisplayName("large array of 100 elements → returns maximum valid sum")
    void largeArray() {
        Solution solution = new Solution();
        int[] nums = new int[100];
        for (int i = 0; i < 100; i++) {
            nums[i] = i + 1;
        }

        assertThat(solution.twoSumLessThanK(nums, 150)).isEqualTo(149);
    }

    @Test
    @DisplayName("unsorted array with negative-adjacent boundary values → returns correct max sum")
    void unsortedArrayBoundary() {
        Solution solution = new Solution();
        int[] nums = {99, 1, 50, 49, 2};

        assertThat(solution.twoSumLessThanK(nums, 100)).isEqualTo(99);
    }
}
