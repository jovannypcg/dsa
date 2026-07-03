# Intervals

## Pattern
Sort intervals by start time, then sweep through to merge, insert, or count overlaps in a single pass.

## How to Recognize
- Input is a list of `[start, end]` pairs
- "Merge overlapping intervals"
- "Insert a new interval"
- "Minimum meeting rooms / resources needed"
- "Total covered length"
- Any problem asking about overlap between ranges

## Approach
1. **Sort by start time** (almost always the first step).
2. **Sweep**: compare current interval's start to the last merged interval's end.
   - `curr.start <= last.end` → overlap → merge by extending end: `last.end = max(last.end, curr.end)`.
   - Otherwise → no overlap → add current as a new interval.
3. For "minimum rooms" / resource counting: use a min-heap of end times (or sort both starts and ends separately and use two pointers).

## Template

```java
// Merge intervals
public int[][] merge(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

    List<int[]> merged = new ArrayList<>();
    merged.add(intervals[0]);

    for (int i = 1; i < intervals.length; i++) {
        int[] last = merged.get(merged.size() - 1);

        if (intervals[i][0] <= last[1]) {
            last[1] = Math.max(last[1], intervals[i][1]); // extend
        } else {
            merged.add(intervals[i]);
        }
    }

    return merged.toArray(new int[0][]);
}
```

```java
// Insert interval (list is already sorted and non-overlapping)
public int[][] insert(int[][] intervals, int[] newInterval) {
    List<int[]> result = new ArrayList<>();
    int i = 0, n = intervals.length;

    // Add all intervals that end before newInterval starts
    while (i < n && intervals[i][1] < newInterval[0]) result.add(intervals[i++]);

    // Merge all overlapping intervals
    while (i < n && intervals[i][0] <= newInterval[1]) {
        newInterval[0] = Math.min(newInterval[0], intervals[i][0]);
        newInterval[1] = Math.max(newInterval[1], intervals[i][1]);
        i++;
    }
    result.add(newInterval);

    // Add remaining
    while (i < n) result.add(intervals[i++]);

    return result.toArray(new int[0][]);
}
```

```java
// Minimum meeting rooms (min-heap of end times)
public int minMeetingRooms(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));
    PriorityQueue<Integer> endTimes = new PriorityQueue<>();

    for (int[] interval : intervals) {
        if (!endTimes.isEmpty() && endTimes.peek() <= interval[0]) {
            endTimes.poll(); // reuse this room
        }
        endTimes.offer(interval[1]);
    }

    return endTimes.size(); // rooms in use = heap size
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Meeting Rooms | [LeetCode #252](https://leetcode.com/problems/meeting-rooms/) |
| 2 | Merge Intervals | [LeetCode #56](https://leetcode.com/problems/merge-intervals/) |
| 3 | Insert Interval | [LeetCode #57](https://leetcode.com/problems/insert-interval/) |
| 4 | Non-overlapping Intervals | [LeetCode #435](https://leetcode.com/problems/non-overlapping-intervals/) |
| 5 | Meeting Rooms II | [LeetCode #253](https://leetcode.com/problems/meeting-rooms-ii/) |
| 6 | Minimum Number of Arrows to Burst Balloons | [LeetCode #452](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/) |
| 7 | Employee Free Time | [LeetCode #759](https://leetcode.com/problems/employee-free-time/) |
| 8 | Interval List Intersections | [LeetCode #986](https://leetcode.com/problems/interval-list-intersections/) |
| 9 | Remove Covered Intervals | [LeetCode #1288](https://leetcode.com/problems/remove-covered-intervals/) |
| 10 | Data Stream as Disjoint Intervals | [LeetCode #352](https://leetcode.com/problems/data-stream-as-disjoint-intervals/) |
| 11 | My Calendar I | [LeetCode #729](https://leetcode.com/problems/my-calendar-i/) |
| 12 | My Calendar II | [LeetCode #731](https://leetcode.com/problems/my-calendar-ii/) |
| 13 | Count of Range Sum | [LeetCode #327](https://leetcode.com/problems/count-of-range-sum/) |
| 14 | Minimum Interval to Include Each Query | [LeetCode #1851](https://leetcode.com/problems/minimum-interval-to-include-each-query/) |
| 15 | Maximum CPU Load | [NeetCode](https://neetcode.io/problems/maximum-cpu-load) |
| 16 | Task Scheduler (interval view) | [LeetCode #621](https://leetcode.com/problems/task-scheduler/) |
| 17 | Divide Intervals Into Minimum Number of Groups | [LeetCode #2406](https://leetcode.com/problems/divide-intervals-into-minimum-number-of-groups/) |
| 18 | Amount of New Area Painted Each Day | [LeetCode #2158](https://leetcode.com/problems/amount-of-new-area-painted-each-day/) |
| 19 | Falling Squares | [LeetCode #699](https://leetcode.com/problems/falling-squares/) |
| 20 | Maximize the Confusion of an Exam | [LeetCode #2024](https://leetcode.com/problems/maximize-the-confusion-of-an-exam/) |
