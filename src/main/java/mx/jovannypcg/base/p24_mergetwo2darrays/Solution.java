package mx.jovannypcg.base.p24_mergetwo2darrays;

import java.util.List;
import java.util.ArrayList;

/**
 * You are given two 2D integer arrays {@code nums1} and {@code nums2}.
 *
 * <p>{@code nums1[i] = [idi, vali]} indicates that the number with the id {@code idi} has a
 * value equal to {@code vali}. {@code nums2[i] = [idi, vali]} indicates that the number with
 * the id {@code idi} has a value equal to {@code vali}. Each array contains unique ids and is
 * sorted in ascending order by id.
 *
 * <p>Merge the two arrays into one array that is sorted in ascending order by id, respecting
 * the following conditions:
 * <ul>
 *   <li>Only ids that appear in at least one of the two arrays should be included in the
 *   resulting array.</li>
 *   <li>Each id should be included only once and its value should be the sum of the values of
 *   this id in the two arrays. If the id does not exist in one of the two arrays, then assume
 *   its value in that array to be 0.</li>
 * </ul>
 *
 * <p>Return the resulting array. The returned array must be sorted in ascending order by id.
 *
 * @see <a href="https://leetcode.com/problems/merge-two-2d-arrays-by-summing-values/">Merge Two 2D Arrays by Summing Values - LeetCode</a>
 */
public class Solution {
    public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
        int idx1 = 0;
        int idx2 = 0;
        List<int[]> out = new ArrayList<>();

        while (idx1 < nums1.length && idx2 < nums2.length) {
            if (nums1[idx1][0] == nums2[idx2][0]) {
                out.add(new int[]{nums1[idx1][0], nums1[idx1][1] + nums2[idx2][1]});
                idx1++;
                idx2++;
            } else if (nums1[idx1][0] < nums2[idx2][0]) {
                out.add(nums1[idx1++]);
            } else {
                out.add(nums2[idx2++]);
            }
        }

        while (idx1 < nums1.length) out.add(nums1[idx1++]);
        while (idx2 < nums2.length) out.add(nums2[idx2++]);

        return out.toArray(new int[0][]);
    }
}
