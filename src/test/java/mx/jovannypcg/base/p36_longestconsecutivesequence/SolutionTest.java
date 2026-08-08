package mx.jovannypcg.base.p36_longestconsecutivesequence;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    @DisplayName("unsorted array with a gap → longest run is [1,2,3,4]")
    void unsortedArrayWithGap() {
        Solution solution = new Solution();
        int[] nums = {100, 4, 200, 1, 3, 2};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(4);
    }

    @Test
    @DisplayName("full consecutive run with a duplicate → longest run is entire distinct set")
    void fullConsecutiveRunWithDuplicate() {
        Solution solution = new Solution();
        int[] nums = {0, 3, 7, 2, 5, 8, 4, 6, 0, 1};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(9);
    }

    @Test
    @DisplayName("duplicates collapse to a short run → longest run is [0,1,2]")
    void duplicatesCollapseToShortRun() {
        Solution solution = new Solution();
        int[] nums = {1, 0, 1, 2};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(3);
    }

    @Test
    @DisplayName("empty array → length is 0")
    void emptyArray() {
        Solution solution = new Solution();
        int[] nums = {};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(0);
    }

    @Test
    @DisplayName("single element → length is 1")
    void singleElement() {
        Solution solution = new Solution();
        int[] nums = {5};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(1);
    }

    @Test
    @DisplayName("all identical elements → length is 1")
    void allIdenticalElements() {
        Solution solution = new Solution();
        int[] nums = {7, 7, 7, 7};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(1);
    }

    @Test
    @DisplayName("no two elements consecutive → length is 1")
    void noConsecutiveElements() {
        Solution solution = new Solution();
        int[] nums = {10, 30, 50, 70};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(1);
    }

    @Test
    @DisplayName("negative numbers spanning zero → longest run crosses zero")
    void negativeNumbersSpanningZero() {
        Solution solution = new Solution();
        int[] nums = {-2, -1, 0, 1, 2, 8};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(5);
    }

    @Test
    @DisplayName("all negative numbers → longest run computed correctly")
    void allNegativeNumbers() {
        Solution solution = new Solution();
        int[] nums = {-5, -4, -3, -10};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(3);
    }

    @Test
    @DisplayName("boundary values at int range extremes → no overflow, length is 1")
    void boundaryValuesAtIntRangeExtremes() {
        Solution solution = new Solution();
        int[] nums = {-1000000000, 1000000000};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(1);
    }

    @Test
    @DisplayName("sequence adjacent to int boundary → expands without overflow")
    void sequenceAdjacentToIntBoundary() {
        Solution solution = new Solution();
        int[] nums = {1000000000, 999999999, 999999998};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(3);
    }

    @Test
    @DisplayName("two separate runs → returns the longer one")
    void twoSeparateRuns() {
        Solution solution = new Solution();
        int[] nums = {1, 2, 3, 20, 21, 22, 23, 24};

        assertThat(solution.longestConsecutive(nums)).isEqualTo(5);
    }
}
