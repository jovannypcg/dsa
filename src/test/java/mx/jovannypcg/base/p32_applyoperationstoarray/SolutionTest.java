package mx.jovannypcg.base.p32_applyoperationstoarray;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("example 1: mixed merges then shift zeros to the end")
    void example1() {
        int[] nums = {1, 2, 2, 1, 1, 0};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(1, 4, 2, 0, 0, 0);
    }

    @Test
    @DisplayName("example 2: no operation applies, only a shift is needed")
    void example2() {
        int[] nums = {0, 1};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(1, 0);
    }

    @Test
    @DisplayName("minimum size array, equal nonzero elements merge")
    void minimumSizeEqualElements() {
        int[] nums = {5, 5};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(10, 0);
    }

    @Test
    @DisplayName("minimum size array, both elements zero")
    void minimumSizeBothZero() {
        int[] nums = {0, 0};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(0, 0);
    }

    @Test
    @DisplayName("no adjacent duplicates, array is unchanged")
    void noDuplicates() {
        int[] nums = {1, 2, 3, 4};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(1, 2, 3, 4);
    }

    @Test
    @DisplayName("chain of equal elements merges pairwise left to right")
    void chainOfEqualElements() {
        int[] nums = {1, 1, 1, 1};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(2, 2, 0, 0);
    }

    @Test
    @DisplayName("all zeros stay zero after merging and shifting")
    void allZeros() {
        int[] nums = {0, 0, 0};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(0, 0, 0);
    }

    @Test
    @DisplayName("boundary value: max element value 1000 doubles correctly")
    void maxElementValueBoundary() {
        int[] nums = {1000, 1000};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(2000, 0);
    }

    @Test
    @DisplayName("zero already present before a merge is not treated as equal to a nonzero neighbor")
    void zeroDoesNotMergeWithNonzeroNeighbor() {
        int[] nums = {0, 3, 3, 0};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(6, 0, 0, 0);
    }

    @Test
    @DisplayName("multiple separate merges preserve relative order after shifting")
    void multipleSeparateMerges() {
        int[] nums = {2, 2, 3, 3, 4, 5};

        int[] result = solution.applyOperations(nums);

        assertThat(result).containsExactly(4, 6, 4, 5, 0, 0);
    }
}
