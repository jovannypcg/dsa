package mx.jovannypcg.base.p83_meetingrooms2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given an array of meeting time intervals where {@code intervals[i] = [starti, endi]},
 * return the minimum number of conference rooms required so that all meetings can be held
 * without any overlap.
 *
 * @see <a href="https://leetcode.com/problems/meeting-rooms-ii/">Meeting Rooms II - LeetCode</a>
 */
public class Solution {

    private Comparator<int[]> meetingComparator = (a, b) ->
        Integer.compare(a[0], b[0]);

    public int minMeetingRooms(int[][] meetings) {
        if (meetings == null) return 0;
        if (meetings.length < 2) return meetings.length;

        Arrays.sort(meetings, meetingComparator);

        int n = meetings.length;

        // Min heap of occupied rooms, ordered by end time
        PriorityQueue<Integer> rooms = new PriorityQueue<>();

        // The first meeting always occupies a new room
        rooms.offer(meetings[0][1]);

        // For each remaining meeting, check the room freeing up soonest
        for (int idx = 1; idx < n; idx++) {
            int roomEndTime = rooms.peek();

            // No overlap: that room is free, so the meeting takes it over
            if (meetings[idx][0] >= roomEndTime) {
                rooms.poll();
            }

            // Reuses the freed room, or occupies a new one if there was overlap
            rooms.offer(meetings[idx][1]);
        }

        return rooms.size();
    }
}
