package mx.jovannypcg.algo.p013_meetingrooms;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given an array of meeting time intervals where {@code intervals[i] = [starti, endi]},
 * determine if a person could attend all meetings (i.e., no two intervals overlap).
 *
 * @see <a href="https://leetcode.com/problems/meeting-rooms/">Meeting Rooms - LeetCode</a>
 */
public class Solution {

    public boolean canAttendMeetings(int[][] meetings) {
        if (meetings == null || meetings.length == 0) return true;

        int n = meetings.length;

        Comparator<int[]> meetingComparator = (a, b) -> {
            return Integer.compare(a[0], b[0]);
        };

        Arrays.sort(meetings, meetingComparator);

        int idx = 1;
        while (idx < n && meetings[idx][0] > meetings[idx - 1][1]) idx++;

        return idx == n;
    }
}
