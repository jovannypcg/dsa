package mx.jovannypcg.base.p13_meetingrooms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given an array of meeting time intervals where {@code intervals[i] = [starti, endi]},
 * determine if a person could attend all meetings (i.e., no two intervals overlap).
 *
 * @see <a href="https://leetcode.com/problems/meeting-rooms/">Meeting Rooms - LeetCode</a>
 */
public class Solution {

    public boolean canAttendMeetings(int[][] intervals) {
        if (intervals == null || intervals.length < 2) {
            return true;
        }

        Comparator<int[]> intervalStartComparator = (a, b) -> {
            return Integer.compare(a[0], b[0]);
        };

        Arrays.sort(intervals, intervalStartComparator);

        for (int i = 1; i < intervals.length; i++) {
            int[] current = intervals[i];
            int[] previous = intervals[i - 1];

            if (current[0] <= previous[1]) { // Overlap detected
                return false;
            }
        }

        return true;
    }
}
