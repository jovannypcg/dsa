# Meeting Rooms II

**Date added:** 2026-09-15

## Problem Description

Given an array of meeting time intervals `intervals` where `intervals[i] = [starti, endi]`, return the minimum number of conference rooms required so that all meetings can be held. A meeting room can be reused immediately once a meeting ends — if one meeting ends exactly when another starts, they do not need separate rooms.

**Source:** https://leetcode.com/problems/meeting-rooms-ii/

## Examples

**Example 1**
```
Input: intervals = [[0,30],[5,10],[15,20]]
Output: 2
Explanation: [0,30] overlaps both [5,10] and [15,20], but [5,10] and [15,20] don't overlap each other, so one room can be shared between them while [0,30] occupies the other.
```

**Example 2**
```
Input: intervals = [[7,10],[2,4]]
Output: 1
Explanation: [2,4] ends before [7,10] starts, so a single room can host both, one after the other.
```

**Example 3**
```
Input: intervals = [[9,10],[4,9],[4,17]]
Output: 2
Explanation: At time 4, both [4,9] and [4,17] are already running, requiring 2 rooms. [9,10] starts exactly when [4,9] ends, so it can reuse that freed-up room.
```

**Example 4**
```
Input: intervals = [[5,8]]
Output: 1
Explanation: A single meeting only ever needs a single room.
```

**Example 5**
```
Input: intervals = [[1,10],[2,6],[3,5]]
Output: 3
Explanation: All three meetings are simultaneously in progress between times 3 and 5, so 3 rooms are needed at once.
```

**Example 6**
```
Input: intervals = [[1,4],[2,5],[3,6],[7,8]]
Output: 3
Explanation: Meetings stagger in one at a time — by time 3, [1,4], [2,5], and [3,6] are all running at once, requiring 3 rooms. [7,8] starts after everything else has ended, so it reuses a freed room.
```

**Example 7**
```
Input: intervals = [[0,100],[1,2],[3,4],[5,6]]
Output: 2
Explanation: [0,100] runs the whole time and overlaps each short meeting, but the short meetings never overlap each other, so only one extra room is ever needed alongside [0,100]'s room.
```

**Example 8**
```
Input: intervals = [[1,5],[2,3],[4,7],[6,8],[9,10],[9,12],[11,13]]
Output: 2
Explanation: With 7 meetings, the busiest overlap is never more than a pair at any instant: [1,5]/[2,3] overlap near time 2, [1,5]/[4,7] near time 4, [4,7]/[6,8] near time 6, [9,10]/[9,12] at time 9, and [9,12]/[11,13] near time 11 — but no three of them are ever running at once, so 2 rooms suffice.
```

## Constraints

- `1 <= intervals.length <= 10^4`
- `0 <= starti < endi <= 10^6`

## Hints

1. Think about start times and end times separately — what happens if you sort each list independently?
2. At any given moment, the number of rooms in use equals the number of meetings that have started but not yet ended.
3. Try a two-pointer approach: walk through the sorted starts and sorted ends together, in order of time.
4. Alternatively, a min-heap of end times lets you check whether the room freed by the earliest-ending meeting is available before deciding you need a new one.
5. The answer is the maximum number of meetings that are simultaneously active at any single point in the whole schedule.
