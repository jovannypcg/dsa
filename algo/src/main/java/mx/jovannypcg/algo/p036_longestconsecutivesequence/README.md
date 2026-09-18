# Longest Consecutive Sequence

**Date added:** 2026-08-07

## Problem Description

Given an unsorted array of integers `nums`, return the length of the longest consecutive elements sequence. You must write an algorithm that runs in O(n) time.

**Source:** https://leetcode.com/problems/longest-consecutive-sequence

## Examples

**Example 1**
```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

**Example 2**
```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
Explanation: The longest consecutive elements sequence is [0, 1, 2, 3, 4, 5, 6, 7, 8]. Therefore its length is 9.
```

**Example 3**
```
Input: nums = [1,0,1,2]
Output: 3
Explanation: The longest consecutive elements sequence is [0, 1, 2]. Therefore its length is 3.
```

**Example 4**
```
Input: nums = []
Output: 0
Explanation: An empty array has no elements, so the longest consecutive sequence has length 0.
```

**Example 5**
```
Input: nums = [5]
Output: 1
Explanation: A single element is itself a consecutive sequence of length 1.
```

## Constraints

- `0 <= nums.length <= 10^5`
- `-10^9 <= nums[i] <= 10^9`

## Hints

1. Sorting gives an easy O(n log n) solution — can you avoid the sort entirely?
2. A hash set lets you check whether a value exists in O(1).
3. A number only starts a new sequence if `num - 1` is not in the set.
4. Once you find a sequence start, count upward (`num + 1`, `num + 2`, ...) while the next value exists in the set.
5. Each number is only ever visited as the start of a sequence expansion once, which is what keeps the overall algorithm O(n).
