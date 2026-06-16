package mx.jovannypcg.base.p12_mergeintervals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * Given an array of intervals where {@code intervals[i] = [starti, endi]}, merge all overlapping
 * intervals, and return an array of the non-overlapping intervals that cover all the intervals
 * in the input.
 *
 * @see <a href="https://leetcode.com/problems/merge-intervals/">Merge Intervals - LeetCode</a>
 */
public class Solution {

    public int[][] merge(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return intervals;
        }

        Comparator<int[]> intervalStartComparator = (a, b) -> {
            return Integer.compare(a[0], b[0]);
        };

        Arrays.sort(intervals, intervalStartComparator);

        List<int[]> merged = new ArrayList<>();
        merged.add(intervals[0]);

        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] last = merged.get(merged.size() - 1); // Is this a pointer or a copy? -- let's assume it's a pointer

            if (current[0] <= last[1]) {
                // current started before the end of the last merged item -- merge required
                last[1] = Math.max(current[1], last[1]); // Assuming `last` is a pointer, it should update the value in the list
            } else {
                merged.add(current);
            }
        }

        return merged.toArray(new int[0][]);
    }
}
