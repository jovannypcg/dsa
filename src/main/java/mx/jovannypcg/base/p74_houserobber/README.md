# House Robber

**Date added:** 2026-09-05

## Problem Description

You are a professional robber planning to rob houses along a street. Each house has a certain amount of money stashed, the only constraint stopping you from robbing each of them is that adjacent houses have security systems connected and it will automatically contact the police if two adjacent houses were broken into on the same night.

Given an integer array `nums` representing the amount of money of each house, return the maximum amount of money you can rob tonight without alerting the police.

**Source:** https://leetcode.com/problems/house-robber

## Examples

**Example 1**
```
Input: nums = [1,2,3,1]
Output: 4
Explanation: Rob house 1 (money = 1) and then rob house 3 (money = 3). Total amount you can rob = 1 + 3 = 4.
```

**Example 2**
```
Input: nums = [2,7,9,3,1]
Output: 12
Explanation: Rob house 1 (money = 2), rob house 3 (money = 9) and rob house 5 (money = 1). Total amount you can rob = 2 + 9 + 1 = 12.
```

**Example 3**
```
Input: nums = [0]
Output: 0
Explanation: There is only one house with no money, so the maximum you can rob is 0.
```

**Example 4**
```
Input: nums = [5,5]
Output: 5
Explanation: Two adjacent houses cannot both be robbed, so you can only take one of them, the maximum being 5.
```

**Example 5**
```
Input: nums = [2,1,1,2]
Output: 4
Explanation: Rob house 1 (money = 2) and house 4 (money = 2), since they are not adjacent. Total amount you can rob = 2 + 2 = 4.
```

**Example 6**
```
Input: nums = [400,400,400,400,400,400,400,400,400,400]
Output: 2000
Explanation: With alternating identical values, the best strategy is to take every other house, yielding 5 houses of 400 each.
```

**Example 7**
```
Input: nums = [4,1,2,7,5,3,1]
Output: 14
Explanation: Rob house 1 (money = 4), house 4 (money = 7), and house 6 (money = 3). Total amount you can rob = 4 + 7 + 3 = 14.
```

## Constraints

- `1 <= nums.length <= 100`
- `0 <= nums[i] <= 400`

## Hints

1. Think about what decision you face at each house: rob it, or skip it.
2. If you rob the current house, you cannot use the money from the house immediately before it.
3. Define a function that represents the best amount obtainable up to house `i`, expressed in terms of the best amounts up to `i-1` and `i-2`.
4. This recurrence can be computed iteratively, without recursion or memoization tables sized beyond a couple of variables.
5. Track only the two previous best totals as you scan left to right — no need to store the whole history.
