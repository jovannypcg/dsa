# Reverse Linked List

**Date added:** 2026-08-10

## Problem Description

Given the head of a singly linked list, reverse the list, and return the reversed list.

**Source:** https://leetcode.com/problems/reverse-linked-list/

## Examples

**Example 1**
```
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]
Explanation: The list is reversed end to end, so the original tail becomes the new head.
```

**Example 2**
```
Input: head = [1,2]
Output: [2,1]
Explanation: With two nodes, the order simply flips.
```

**Example 3**
```
Input: head = []
Output: []
Explanation: An empty list reversed is still empty.
```

## Constraints

- The number of nodes in the list is in the range `[0, 5000]`.
- `-5000 <= Node.val <= 5000`

**Follow up:** A linked list can be reversed either iteratively or recursively. Could you implement both?

## Hints

1. Think about what changes for each node: only the direction of its `next` pointer.
2. You'll need to keep track of the node that comes before the one you're currently processing, since you're about to point the current node backward at it.
3. Iterate through the list once, and for each node, redirect its `next` pointer to the previous node before moving forward.
4. Be careful not to lose the rest of the list when you reverse a pointer — save a reference to the next node before overwriting `current.next`.
5. For the recursive version, think about what `reverseList` should return when called on the rest of the list (everything after the head), and how to reattach the current node at the end of that reversed sublist.
