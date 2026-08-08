package mx.jovannypcg.base.p36_longestconsecutivesequence;

import java.util.HashSet;
import java.util.Set;

/**
 * Given an unsorted array of integers {@code nums}, return the length of the longest
 * consecutive elements sequence.
 *
 * <p>The algorithm must run in O(n) time.
 *
 * @see <a href="https://leetcode.com/problems/longest-consecutive-sequence/">Longest Consecutive Sequence - LeetCode</a>
 */
public class Solution {

    public int longestConsecutive(int[] nums) {
        Set<Integer> seen = new HashSet<>();

        for (int num : nums) {
            seen.add(num);
        }

        int longestSequenceSize = 0;

        for (int num : seen) {
            // Not start of sequence; ignore
            if (seen.contains(num - 1)) continue;

            int end = num;
            while (seen.contains(end + 1)) end++;

            longestSequenceSize = Math.max(longestSequenceSize, end - num + 1);
        }

        return longestSequenceSize;
    }
}
