# Climbing Stairs

**Date added:** 2026-09-05

## Problem Description

You are climbing a staircase. It takes `n` steps to reach the top. Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?

**Source:** https://leetcode.com/problems/climbing-stairs/

## Examples

**Example 1**
```
Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
```

**Example 2**
```
Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
```

**Example 3**
```
Input: n = 1
Output: 1
Explanation: There is only one way to climb to the top: a single 1 step.
```

**Example 4**
```
Input: n = 4
Output: 5
Explanation: The five ways are: 1+1+1+1, 1+1+2, 1+2+1, 2+1+1, 2+2.
```

**Example 5**
```
Input: n = 5
Output: 8
Explanation: The number of ways follows the Fibonacci sequence: 1, 2, 3, 5, 8 for n = 1..5.
```

**Example 6**
```
Input: n = 6
Output: 13
Explanation: The number of ways continues the Fibonacci-like pattern: 1, 2, 3, 5, 8, 13 for n = 1..6.
```

**Example 7**
```
Input: n = 45
Output: 1836311903
Explanation: This is the upper bound of n per the constraints, and shows the count grows quickly, still fitting in a 32-bit int.
```

## Constraints

- `1 <= n <= 45`

## Hints

1. Think about the last move you make to reach step `n` — it's either a 1-step from step `n-1` or a 2-step from step `n-2`.
2. That means the number of ways to reach step `n` can be expressed in terms of the number of ways to reach smaller steps.
3. This recurrence should look familiar — it's the same relationship as a well-known sequence.
4. A naive recursive solution recomputes the same subproblems many times — how can you avoid that?
5. You only ever need the results for the previous two steps, so you don't need to store the whole history.
