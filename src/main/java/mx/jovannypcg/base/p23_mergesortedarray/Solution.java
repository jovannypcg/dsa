package mx.jovannypcg.base.p23_mergesortedarray;

/**
 * You are given two integer arrays {@code nums1} and {@code nums2}, sorted in non-decreasing
 * order, and two integers {@code m} and {@code n}, representing the number of elements in
 * {@code nums1} and {@code nums2} respectively.
 *
 * <p>Merge {@code nums1} and {@code nums2} into a single array sorted in non-decreasing order.
 *
 * <p>The final sorted array should not be returned by the function, but instead be stored
 * inside the array {@code nums1}. To accommodate this, {@code nums1} has a length of
 * {@code m + n}, where the first {@code m} elements denote the elements that should be merged,
 * and the last {@code n} elements are set to 0 and should be ignored. {@code nums2} has a
 * length of {@code n}.
 *
 * @see <a href="https://leetcode.com/problems/merge-sorted-array/">Merge Sorted Array - LeetCode</a>
 */
public class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int a = m - 1; // `nums1` index
        int b = n - 1; // `nums2` index
        int r = nums1.length - 1; // Result index - placed at the end of `nums1`

        // Reverse order - while there's something valid to iterate on
        while (a >= 0 || b >= 0) {
            // Iteration is complete in `nums1` - continue with `nums2` only
            if (a < 0) {
                nums1[r--] = nums2[b--];
                continue;
            }

            // Iteration is complete in `nums2` - continue with `nums1` only
            if (b < 0) {
                nums1[r--] = nums1[a--];
                continue;
            }

            // At this point `a` and `b` haven't exceeded their boundaries, so their
            // values can be compared and sorted

            if (nums1[a] > nums2[b]) {
                nums1[r] = nums1[a--];
            } else {
                nums1[r] = nums2[b--];
            }

            r--;
        }
    }
}
