# Meeting Rooms

**Date added:** 2026-06-16

## Problem Description

Given an array of meeting time intervals where `intervals[i] = [starti, endi]`, determine if a
person could attend all meetings. A person can attend all meetings only if no two meetings overlap.
Two meetings overlap if one starts before the other ends.

**Source:** https://leetcode.com/problems/meeting-rooms/

## Examples

**Example 1**
```
Input: intervals = [[0,30],[5,10],[15,20]]
Output: false
Explanation: [0,30] overlaps with both [5,10] and [15,20].
```

**Example 2**
```
Input: intervals = [[7,10],[2,4]]
Output: true
Explanation: After sorting, [2,4] ends before [7,10] starts — no overlap.
```

**Example 3**
```
Input: intervals = [[1,5],[5,10]]
Output: false
Explanation: [1,5] and [5,10] overlap at t=5 (the end of one equals the start of the next).
```

**Example 4**
```
Input: intervals = []
Output: true
Explanation: No meetings means nothing to conflict — trivially true.
```

**Example 5**
```
Input: intervals = [[3,7]]
Output: true
Explanation: A single meeting can always be attended.
```

## Constraints

- `0 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= starti < endi <= 10^6`

## Hints

1. In what order should you process the intervals to make overlap detection simple?
2. After sorting by start time, which pair of intervals is the only one that can possibly overlap?
3. Two sorted adjacent intervals `[a, b]` and `[c, d]` overlap when `c < b`. What about `c == b`?
4. You only need a single pass once the intervals are sorted.
5. The moment you find one overlapping pair you can return immediately — no need to keep going.
