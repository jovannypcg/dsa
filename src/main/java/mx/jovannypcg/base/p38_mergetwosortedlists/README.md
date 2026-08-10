# Merge Two Sorted Lists

**Date added:** 2026-08-10

## Problem Description

You are given the heads of two sorted linked lists `list1` and `list2`. Merge the two lists into one sorted list. The list should be made by splicing together the nodes of the first two lists. Return the head of the merged linked list.

**Source:** https://leetcode.com/problems/merge-two-sorted-lists/

## Examples

**Example 1**
```
Input: list1 = [1,2,4], list2 = [1,3,4]
Output: [1,1,2,3,4,4]
Explanation: Merging the two sorted lists node by node, in non-decreasing order, produces [1,1,2,3,4,4].
```

**Example 2**
```
Input: list1 = [], list2 = []
Output: []
Explanation: Both lists are empty, so the merged result is also empty.
```

**Example 3**
```
Input: list1 = [], list2 = [0]
Output: [0]
Explanation: Since list1 is empty, the merged list is just list2.
```

## Constraints

- The number of nodes in both lists is in the range `[0, 50]`.
- `-100 <= Node.val <= 100`
- Both `list1` and `list2` are sorted in non-decreasing order.

## Hints

1. Think of this like merging two sorted arrays — at each step you only need to compare the current front of each list.
2. Use a dummy/sentinel node to simplify building the merged list, so you don't need special-case logic for the first node.
3. Keep a pointer to the tail of the merged list, and at each step attach the smaller of the two current nodes.
4. Advance only the pointer of the list whose node you just attached.
5. Once one list is exhausted, the rest of the other list is already sorted — attach it directly to the tail.
