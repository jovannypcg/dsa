package mx.jovannypcg.base.p28_sortarraybyparity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private void assertValidParityOrder(int[] original, int[] result) {
        int[] sortedOriginal = original.clone();
        int[] sortedResult = result.clone();
        Arrays.sort(sortedOriginal);
        Arrays.sort(sortedResult);
        assertThat(sortedResult).isEqualTo(sortedOriginal);

        boolean seenOdd = false;
        for (int num : result) {
            if (num % 2 != 0) {
                seenOdd = true;
            } else if (seenOdd) {
                throw new AssertionError("Found even number " + num + " after an odd number in " + Arrays.toString(result));
            }
        }
    }

    @Test
    @DisplayName("example from prompt: mixed evens and odds")
    void mixedEvensAndOdds() {
        Solution solution = new Solution();
        int[] nums = {3, 1, 2, 4};

        int[] result = solution.sortArrayByParity(nums);

        assertValidParityOrder(new int[]{3, 1, 2, 4}, result);
    }

    @Test
    @DisplayName("single element array (minimum length constraint)")
    void singleElement() {
        Solution solution = new Solution();
        int[] nums = {0};

        int[] result = solution.sortArrayByParity(nums);

        assertThat(result).containsExactly(0);
    }

    @Test
    @DisplayName("all even numbers already satisfy the condition")
    void allEven() {
        Solution solution = new Solution();
        int[] nums = {2, 4, 6};

        int[] result = solution.sortArrayByParity(nums);

        assertValidParityOrder(new int[]{2, 4, 6}, result);
    }

    @Test
    @DisplayName("all odd numbers already satisfy the condition")
    void allOdd() {
        Solution solution = new Solution();
        int[] nums = {1, 3, 5};

        int[] result = solution.sortArrayByParity(nums);

        assertValidParityOrder(new int[]{1, 3, 5}, result);
    }

    @Test
    @DisplayName("two elements: even must precede odd")
    void twoElementsEvenAndOdd() {
        Solution solution = new Solution();
        int[] nums = {1, 2};

        int[] result = solution.sortArrayByParity(nums);

        assertValidParityOrder(new int[]{1, 2}, result);
    }

    @Test
    @DisplayName("array with duplicate values")
    void duplicateValues() {
        Solution solution = new Solution();
        int[] nums = {2, 2, 3, 3};

        int[] result = solution.sortArrayByParity(nums);

        assertValidParityOrder(new int[]{2, 2, 3, 3}, result);
    }

    @Test
    @DisplayName("boundary values: minimum and maximum allowed nums[i]")
    void boundaryValues() {
        Solution solution = new Solution();
        int[] nums = {5000, 0, 4999, 1};

        int[] result = solution.sortArrayByParity(nums);

        assertValidParityOrder(new int[]{5000, 0, 4999, 1}, result);
    }

    @Test
    @DisplayName("larger array with alternating parity")
    void largerAlternatingArray() {
        Solution solution = new Solution();
        int[] nums = {9, 8, 7, 6, 5, 4, 3, 2, 1, 0};

        int[] result = solution.sortArrayByParity(nums);

        assertValidParityOrder(new int[]{9, 8, 7, 6, 5, 4, 3, 2, 1, 0}, result);
    }
}
