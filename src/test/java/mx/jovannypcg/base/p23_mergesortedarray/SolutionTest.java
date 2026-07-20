package mx.jovannypcg.base.p23_mergesortedarray;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class SolutionTest {

    @Test
    @DisplayName("interleaved merge of two non-empty sorted arrays")
    void interleavedMerge() {
        Solution solution = new Solution();
        int[] nums1 = { 1, 2, 3, 0, 0, 0 };

        solution.merge(nums1, 3, new int[] { 2, 5, 6 }, 3);

        assertThat(nums1).containsExactly(1, 2, 2, 3, 5, 6);
    }

    @Test
    @DisplayName("nums2 is empty → nums1 stays unchanged")
    void nums2Empty() {
        Solution solution = new Solution();
        int[] nums1 = { 1 };

        solution.merge(nums1, 1, new int[] {}, 0);

        assertThat(nums1).containsExactly(1);
    }

    @Test
    @DisplayName("nums1 has no real elements (m = 0) → result is just nums2")
    void nums1Empty() {
        Solution solution = new Solution();
        int[] nums1 = { 0 };

        solution.merge(nums1, 0, new int[] { 1 }, 1);

        assertThat(nums1).containsExactly(1);
    }

    @Test
    @DisplayName("all of nums2 is smaller than all of nums1")
    void nums2AllSmaller() {
        Solution solution = new Solution();
        int[] nums1 = { 4, 5, 6, 0, 0, 0 };

        solution.merge(nums1, 3, new int[] { 1, 2, 3 }, 3);

        assertThat(nums1).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("all of nums2 is larger than all of nums1")
    void nums2AllLarger() {
        Solution solution = new Solution();
        int[] nums1 = { 1, 2, 3, 0, 0, 0 };

        solution.merge(nums1, 3, new int[] { 4, 5, 6 }, 3);

        assertThat(nums1).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("duplicate values across both arrays are preserved")
    void duplicateValues() {
        Solution solution = new Solution();
        int[] nums1 = { 2, 2, 3, 0, 0, 0 };

        solution.merge(nums1, 3, new int[] { 2, 2, 4 }, 3);

        assertThat(nums1).containsExactly(2, 2, 2, 2, 3, 4);
    }

    @Test
    @DisplayName("negative values merge correctly")
    void negativeValues() {
        Solution solution = new Solution();
        int[] nums1 = { -5, -3, -1, 0, 0, 0 };

        solution.merge(nums1, 3, new int[] { -4, -2, 0 }, 3);

        assertThat(nums1).containsExactly(-5, -4, -3, -2, -1, 0);
    }

    @Test
    @DisplayName(
        "boundary values at the constraint limits (+-10^9) merge correctly"
    )
    void extremeValues() {
        Solution solution = new Solution();
        int[] nums1 = { -1000000000, 1000000000, 0, 0 };

        solution.merge(nums1, 2, new int[] { -500000000, 500000000 }, 2);

        assertThat(nums1).containsExactly(
            -1000000000,
            -500000000,
            500000000,
            1000000000
        );
    }

    @Test
    @DisplayName("single element in each array (m = 1, n = 1)")
    void singleElementEach() {
        Solution solution = new Solution();
        int[] nums1 = { 3, 0 };

        solution.merge(nums1, 1, new int[] { 1 }, 1);

        assertThat(nums1).containsExactly(1, 3);
    }

    @Test
    @DisplayName(
        "larger merge with m + n at a moderate size preserves sort order"
    )
    void largerMerge() {
        Solution solution = new Solution();
        int[] nums1 = { 1, 3, 5, 7, 9, 0, 0, 0, 0, 0 };

        solution.merge(nums1, 5, new int[] { 0, 2, 4, 6, 8 }, 5);

        assertThat(nums1).containsExactly(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
    }
}
