package mx.jovannypcg.base.p01_containsduplicate;

import java.util.Set;
import java.util.HashSet;

/**
 * Given an integer array {@code nums}, return {@code true} if any value appears more than once
 * in the array, otherwise return {@code false}.
 *
 * <p>Constraints:
 * <ul>
 *   <li>0 &lt;= nums.length &lt;= 10^5</li>
 *   <li>-10^9 &lt;= nums[i] &lt;= 10^9</li>
 * </ul>
 *
 * @see <a href="https://neetcode.io/problems/duplicate-integer">Contains Duplicate - NeetCode</a>
 */
public class Solution {
    public boolean containsDuplicate(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        Set<Integer> seen = new HashSet<>();

        for (int idx = 0; idx < nums.length; idx++) {
            int current = nums[idx];

            if (seen.contains(current)) {
                return true;
            }

            seen.add(current);
        }

        return false;
    }
}
