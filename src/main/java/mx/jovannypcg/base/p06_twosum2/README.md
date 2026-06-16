# Two Sum II

**Date added:** 2026-06-11

## Problem Description

Given a 1-indexed array of integers `numbers` that is already sorted in non-decreasing order,
find two numbers such that they add up to a specific `target` number. Return the 1-based indices
of the two numbers as an array `[index1, index2]`.

There is exactly one solution, and you may not use the same element twice. Your solution must
use only constant extra space.

**Source:** https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/

## Examples

**Example 1**
```
Input: numbers = [2,7,11,15], target = 9
Output: [1,2]
Explanation: numbers[1] + numbers[2] == 2 + 7 == 9, so we return [1, 2].
```

**Example 2**
```
Input: numbers = [2,3,4], target = 6
Output: [1,3]
Explanation: numbers[1] + numbers[3] == 2 + 4 == 6, so we return [1, 3].
```

**Example 3**
```
Input: numbers = [-1,0], target = -1
Output: [1,2]
Explanation: numbers[1] + numbers[2] == -1 + 0 == -1, so we return [1, 2].
```

## Constraints

- `2 <= numbers.length <= 3 * 10^4`
- `-1000 <= numbers[i] <= 1000`
- `numbers` is sorted in non-decreasing order.
- `-1000 <= target <= 1000`
- Exactly one solution exists.

## Hints

1. Because the array is sorted, what can you infer about the relationship between the sum and the positions of the two elements?
2. If you pick the smallest and largest elements and their sum is too big, which pointer should you move — and why?
3. If their sum is too small, which pointer should you move?
4. Two pointers starting at opposite ends of the array can cover all candidates without extra space.
5. Each step eliminates at least one element from consideration, so the whole search takes O(n) time.
