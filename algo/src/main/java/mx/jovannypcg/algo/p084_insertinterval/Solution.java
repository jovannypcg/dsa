package mx.jovannypcg.algo.p084_insertinterval;

import java.util.ArrayList;
import java.util.List;

/**
 * You are given an array of non-overlapping intervals {@code intervals} where
 * {@code intervals[i] = [starti, endi]} represent the start and the end of the
 * {@code i}th interval and {@code intervals} is sorted in ascending order by
 * {@code starti}. You are also given an interval {@code newInterval = [start, end]}
 * that represents the start and end of another interval.
 *
 * <p>Two intervals are considered overlapping if they share at least one point.
 *
 * <p>Insert {@code newInterval} into {@code intervals} such that {@code intervals}
 * is still sorted in ascending order by {@code starti} and {@code intervals} still
 * does not have any overlapping intervals (merge overlapping intervals if necessary).
 *
 * <p>Return {@code intervals} after the insertion.
 *
 * @see <a href="https://leetcode.com/problems/insert-interval">Problem Source</a>
 */
public class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;
        int start = newInterval[0];
        int end = newInterval[1];

        // Phase 1 — "before": copy every interval that ends strictly before newInterval
        // starts. They don't share a point with newInterval, so nothing to merge.
        while (i < n && intervals[i][1] < start) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2 — "overlapping": absorb every interval that shares a point with
        // newInterval into a single, growing merged interval instead of adding each
        // one separately.
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        // The overlap phase is over — push the fully-merged interval exactly once.
        result.add(new int[]{start, end});

        // Phase 3 — "after": everything left over starts strictly after newInterval
        // ends, so copy it as-is.
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }
}
