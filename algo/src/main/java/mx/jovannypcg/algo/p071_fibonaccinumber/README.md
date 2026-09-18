# Fibonacci Number

**Date added:** 2026-09-05

## Problem Description

The Fibonacci numbers, commonly denoted `F(n)`, form a sequence such that each number is the sum of the two preceding ones, starting from 0 and 1. That is:

```
F(0) = 0, F(1) = 1
F(n) = F(n - 1) + F(n - 2), for n > 1
```

Given `n`, calculate `F(n)`.

**Source:** https://leetcode.com/problems/fibonacci-number/

## Examples

**Example 1**
```
Input: n = 2
Output: 1
Explanation: F(2) = F(1) + F(0) = 1 + 0 = 1.
```

**Example 2**
```
Input: n = 3
Output: 2
Explanation: F(3) = F(2) + F(1) = 1 + 1 = 2.
```

**Example 3**
```
Input: n = 4
Output: 3
Explanation: F(4) = F(3) + F(2) = 2 + 1 = 3.
```

**Example 4**
```
Input: n = 0
Output: 0
Explanation: F(0) is defined as 0 by the base case.
```

**Example 5**
```
Input: n = 1
Output: 1
Explanation: F(1) is defined as 1 by the base case.
```

## Constraints

- `0 <= n <= 30`

## Hints

1. What are the two base cases you need to handle before any computation?
2. A direct recursive translation of the formula works, but think about how many times it recomputes the same subproblem.
3. What if you stored the result of each `F(i)` the first time you compute it, so you never compute it twice?
4. You don't actually need to remember every past value — only the two most recent ones to compute the next.
5. Try building the sequence from the bottom up (`F(0)`, `F(1)`, `F(2)`, ...) using two running variables instead of an array or recursion.
