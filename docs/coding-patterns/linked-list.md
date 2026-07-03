# Linked List

## Pattern
Manipulate node pointers directly — no index-based access. Key techniques: dummy head node, fast/slow pointers, reversing in-place, and merging.

## How to Recognize
- Input is a `ListNode` (singly or doubly linked)
- "Reverse", "detect cycle", "find middle", "merge", "remove nth from end"
- Need to insert/delete without shifting elements
- Fast/slow pointer: cycle detection, kth from end, middle node

## Approach
- **Dummy head**: simplifies edge cases when the head itself may change.
- **Fast/slow**: slow moves 1 step, fast moves 2 — when fast reaches end, slow is at middle.
- **Reverse**: keep `prev`, `curr`, advance both each iteration.
- **Two-pass or offset**: for "nth from end", use two pointers n apart.

## Template

```java
// Reverse a linked list
public ListNode reverse(ListNode head) {
    ListNode prev = null, curr = head;

    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }

    return prev; // new head
}
```

```java
// Fast / slow — detect cycle
public boolean hasCycle(ListNode head) {
    ListNode slow = head, fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
        if (slow == fast) return true;
    }

    return false;
}
```

```java
// Dummy head — remove nth node from end
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(0, head);
    ListNode left = dummy, right = head;

    while (n-- > 0) right = right.next;       // advance right by n

    while (right != null) {                    // move both until right hits end
        left = left.next;
        right = right.next;
    }

    left.next = left.next.next;                // remove target
    return dummy.next;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Reverse Linked List | [LeetCode #206](https://leetcode.com/problems/reverse-linked-list/) |
| 2 | Merge Two Sorted Lists | [LeetCode #21](https://leetcode.com/problems/merge-two-sorted-lists/) |
| 3 | Linked List Cycle | [LeetCode #141](https://leetcode.com/problems/linked-list-cycle/) |
| 4 | Reorder List | [LeetCode #143](https://leetcode.com/problems/reorder-list/) |
| 5 | Remove Nth Node From End of List | [LeetCode #19](https://leetcode.com/problems/remove-nth-node-from-end-of-list/) |
| 6 | Copy List with Random Pointer | [LeetCode #138](https://leetcode.com/problems/copy-list-with-random-pointer/) |
| 7 | Add Two Numbers | [LeetCode #2](https://leetcode.com/problems/add-two-numbers/) |
| 8 | Find the Duplicate Number | [LeetCode #287](https://leetcode.com/problems/find-the-duplicate-number/) |
| 9 | LRU Cache | [LeetCode #146](https://leetcode.com/problems/lru-cache/) |
| 10 | Merge K Sorted Lists | [LeetCode #23](https://leetcode.com/problems/merge-k-sorted-lists/) |
| 11 | Reverse Nodes in k-Group | [LeetCode #25](https://leetcode.com/problems/reverse-nodes-in-k-group/) |
| 12 | Linked List Cycle II | [LeetCode #142](https://leetcode.com/problems/linked-list-cycle-ii/) |
| 13 | Middle of the Linked List | [LeetCode #876](https://leetcode.com/problems/middle-of-the-linked-list/) |
| 14 | Palindrome Linked List | [LeetCode #234](https://leetcode.com/problems/palindrome-linked-list/) |
| 15 | Intersection of Two Linked Lists | [LeetCode #160](https://leetcode.com/problems/intersection-of-two-linked-lists/) |
| 16 | Odd Even Linked List | [LeetCode #328](https://leetcode.com/problems/odd-even-linked-list/) |
| 17 | Rotate List | [LeetCode #61](https://leetcode.com/problems/rotate-list/) |
| 18 | Partition List | [LeetCode #86](https://leetcode.com/problems/partition-list/) |
| 19 | Sort List | [LeetCode #148](https://leetcode.com/problems/sort-list/) |
| 20 | Swapping Nodes in a Linked List | [LeetCode #1721](https://leetcode.com/problems/swapping-nodes-in-a-linked-list/) |
