# Min Cost Climbing Stairs

**Date added:** 2026-09-05

## Problem Description

You are given an integer array `cost` where `cost[i]` is the cost of the `i`th step on a staircase. Once you pay the cost, you can either climb one or two steps. You can either start from the step with index 0, or the step with index 1. Return the minimum cost to reach the top of the staircase, which is the position just past the last step (index `cost.length`).

**Source:** https://leetcode.com/problems/min-cost-climbing-stairs/

## Examples

**Example 1**
```
Input: cost = [10,15,20]
Output: 15
Explanation: You will start at index 1. Pay 15 and climb two steps to reach the top. The total cost is 15.
```

**Example 2**
```
Input: cost = [1,100,1,1,1,100,1,1,100,1]
Output: 6
Explanation: You will start at index 0. Pay 1 and climb two steps to reach index 2. Pay 1 and climb two steps to reach index 4. Pay 1 and climb two steps to reach index 6. Pay 1 and climb one step to reach index 7. Pay 1 and climb two steps to reach index 9. Pay 1 and climb one step to reach the top. The total cost is 6.
```

**Example 3**
```
Input: cost = [0,0,0,0]
Output: 0
Explanation: Every step is free, so no matter which steps you climb, the total cost is 0.
```

**Example 4**
```
Input: cost = [0,2]
Output: 0
Explanation: Starting at index 0 costs 0 and you can climb two steps directly to the top, so the minimum cost is 0.
```

**Example 5**
```
Input: cost = [999,999]
Output: 999
Explanation: You must pay for either index 0 or index 1 before jumping to the top; both cost 999, so the minimum is 999.
```

**Example 6**
```
Input: cost = [1,2,3,4,5,6,7,8,9,10]
Output: 25
Explanation: Starting at index 0 and always taking two-step jumps (paying cost[0]+cost[2]+cost[4]+cost[6]+cost[8] = 1+3+5+7+9 = 25) is the optimal path when costs strictly increase.
```

**Example 7**
```
Input: cost = [1,0,0,0,1]
Output: 0
Explanation: Start at index 1 (free), jump two steps to index 3 by paying cost[1] = 0, then jump two steps to the top by paying cost[3] = 0. The total cost is 0, avoiding the expensive steps at indices 0 and 4.
```

## Constraints

- `2 <= cost.length <= 1000`
- `0 <= cost[i] <= 999`

## Hints

1. Think of this as a dynamic programming problem: define `dp[i]` as the minimum cost to reach step `i`.
2. To reach step `i`, you must have come from step `i-1` or step `i-2`, paying that step's cost.
3. The recurrence is `dp[i] = min(dp[i-1] + cost[i-1], dp[i-2] + cost[i-2])`.
4. The base cases are `dp[0] = 0` and `dp[1] = 0`, since you can start at either index 0 or 1 for free.
5. The answer is `dp[cost.length]`. You can reduce space by only tracking the last two values instead of a full array.
