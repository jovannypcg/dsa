# Merge Intervals

**Date added:** 2026-06-16

## Problem Description

Given an array of intervals where `intervals[i] = [starti, endi]`, merge all overlapping intervals
and return an array of the non-overlapping intervals that cover all the intervals in the input.
Two intervals are considered overlapping if the start of one is less than or equal to the end of
the other.

**Source:** https://leetcode.com/problems/merge-intervals/

## Examples

**Example 1**
```
Input: intervals = [[1,3],[2,6],[8,10],[15,18]]
Output: [[1,6],[8,10],[15,18]]
Explanation: Since intervals [1,3] and [2,6] overlap, merge them into [1,6].
```

**Example 2**
```
Input: intervals = [[1,4],[4,5]]
Output: [[1,5]]
Explanation: Intervals [1,4] and [4,5] are considered overlapping (touching at 4).
```

**Example 3**
```
Input: intervals = [[4,7],[1,4]]
Output: [[1,7]]
Explanation: After sorting, [1,4] and [4,7] overlap, merging into [1,7].
```

**Example 4**
```
Input: intervals = [[1,4],[2,3]]
Output: [[1,4]]
Explanation: [2,3] is fully contained within [1,4], so they merge into [1,4].
```

**Example 5**
```
Input: intervals = [[1,2]]
Output: [[1,2]]
Explanation: A single interval has nothing to merge with; return it as-is.
```

## Constraints

- `1 <= intervals.length <= 10^4`
- `intervals[i].length == 2`
- `0 <= starti <= endi <= 10^4`

## Hints

1. What happens if you process intervals in sorted order by their start value?
2. After sorting, when can you be sure two adjacent intervals do NOT overlap?
3. When two intervals do overlap, which end value should the merged interval use?
4. A list you build up result-by-result lets you always compare against the last merged interval.
5. An interval `[a, b]` overlaps `[c, d]` (with `a <= c`) when `c <= b`; the merged end is `max(b, d)`.
