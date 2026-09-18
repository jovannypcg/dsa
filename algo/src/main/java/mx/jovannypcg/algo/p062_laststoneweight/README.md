# Last Stone Weight

**Date added:** 2026-08-25

## Problem Description

You are given an array of integers `stones` where `stones[i]` is the weight of the ith stone.

We are playing a game with the stones. On each turn, we choose the heaviest two stones and smash them together. Suppose the heaviest two stones have weights x and y with x <= y. The result of this smash is: if x == y, both stones are destroyed, and if x != y, the stone of weight x is destroyed, and the stone of weight y has new weight y - x.

At the end of the game, there is at most one stone left. Return the weight of the last remaining stone. If there are no stones left, return 0.

**Source:** https://leetcode.com/problems/last-stone-weight

## Examples

**Example 1**
```
Input: stones = [2,7,4,1,8,1]
Output: 1
Explanation: We combine 7 and 8 to get 1 so the array converts to [2,4,1,1,1] then, we combine 2 and 4 to get 2 so the array converts to [2,1,1,1] then, we combine 2 and 1 to get 1 so the array converts to [1,1,1] then, we combine 1 and 1 to get 0 so the array converts to [1] then that's the value of the last stone.
```

**Example 2**
```
Input: stones = [1]
Output: 1
Explanation: A single stone remains with no smashing needed, so its own weight is the answer.
```

**Example 3**
```
Input: stones = [1,1]
Output: 0
Explanation: The two equal stones smash together and both are destroyed, leaving no stones.
```

**Example 4**
```
Input: stones = [3,7,2]
Output: 2
Explanation: We combine 3 and 7 to get 4 so the array converts to [4,2] then, we combine 4 and 2 to get 2 so the array converts to [2] then that's the value of the last stone.
```

**Example 5**
```
Input: stones = [1000,1000]
Output: 0
Explanation: The two maximum-weight equal stones smash together and both are destroyed, leaving no stones.
```

## Constraints

- `1 <= stones.length <= 30`
- `1 <= stones[i] <= 1000`

## Hints

1. Every turn you need to quickly find the two heaviest stones — what data structure keeps the largest element accessible in less than linear time?
2. A max-heap (priority queue) lets you repeatedly extract the largest elements efficiently.
3. Pop the two largest stones, compute the smash result, and if the result is nonzero, push it back onto the heap.
4. Repeat this process until the heap has zero or one stone left.
5. Java's `PriorityQueue` is a min-heap by default — you'll need a custom comparator (or negate the values) to simulate a max-heap.
