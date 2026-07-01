# Remove Nth Node From End of List

**Date added:** 2026-06-30

## Problem Description

Given the head of a linked list, remove the nth node from the end of the list and return its head.

**Source:** https://leetcode.com/problems/remove-nth-node-from-end-of-list/

## Examples

**Example 1**
```
Input: head = [1,2,3,4,5], n = 2
Output: [1,2,3,5]
Explanation: The 2nd node from the end is 4 (at index 3). Removing it yields [1,2,3,5].
```

**Example 2**
```
Input: head = [1], n = 1
Output: []
Explanation: The only node is also the 1st from the end. Removing it yields an empty list.
```

**Example 3**
```
Input: head = [1,2], n = 1
Output: [1]
Explanation: The 1st node from the end is 2. Removing it yields [1].
```

**Example 4**
```
Input: head = [1,2], n = 2
Output: [2]
Explanation: The 2nd node from the end is 1 (the head). Removing it yields [2].
```

**Example 5**
```
Input: head = [1,2,3], n = 2
Output: [1,3]
Explanation: The 2nd node from the end is 2. Removing it yields [1,3].
```

## Constraints

- The number of nodes in the list is `sz`.
- `1 <= sz <= 30`
- `0 <= Node.val <= 100`
- `1 <= n <= sz`

## Hints

1. If you knew the total length of the list, could you figure out which node (from the front) to remove?
2. Can you find the node to remove in a single pass without computing the length first?
3. Think about using two pointers that maintain a fixed gap between them.
4. If the fast pointer starts `n` steps ahead of the slow pointer, where are they relative to each other when fast reaches the end?
5. You need a reference to the node *before* the one you want to remove — how does that change where you start your slow pointer?
