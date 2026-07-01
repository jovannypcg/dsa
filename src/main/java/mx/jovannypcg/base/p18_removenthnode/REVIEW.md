# Remove Nth Node From End of List — Review

| | |
|---|---|
| **Solved on** | 2026-06-30 |
| **DSA Category** | Linked List |

---

## 1. Your Solution Assessment

### Correctness

Handles all cases correctly, including the trickiest edge case: removing the head node (when `n == sz`). The dummy sentinel makes this case fall through the same code path as any other removal — no special branch needed.

### Code Quality

Clean and readable. Variable names (`slow`, `fast`, `prev`, `dummy`) clearly communicate their roles. The two-phase structure (advance fast, then walk together) is easy to follow.

One minor note: after the second loop, `slow == prev.next` is always true, so `prev.next = slow.next` could be written as `prev.next = prev.next.next`. Your version is actually clearer — keeping `slow` explicit makes the intent obvious.

### Key Insight from Your Notes

> When introducing a `prev` pointer, remember to pair it with a `dummy` sentinel node.

This is a crucial pattern. A `prev` pointer needs a valid starting position *before the first real node* to handle head removal uniformly. Without `dummy`, there's no node for `prev` to sit on when the target is the head — you'd need a special case. The dummy gives `prev` a stable anchor before the list begins, so removal always reads the same: `prev.next = slow.next`.

### Complexity

- **Time:** O(sz) — single pass through the list; fast pointer traverses at most `sz` nodes, slow pointer traverses at most `sz - n` nodes.
- **Space:** O(1) — only four pointer variables regardless of list size.

### Algorithm Trace

Input: `head = [1, 2, 3, 4, 5]`, `n = 2`

**Setup**
```
dummy → 1 → 2 → 3 → 4 → 5
  P     S
        F
```

**Phase 1 — advance F by n=2 steps**
```
dummy → 1 → 2 → 3 → 4 → 5
  P     S
                F
```

**Phase 2 — walk until F is null**
```
Step 1:
dummy → 1 → 2 → 3 → 4 → 5
        P   S
                    F

Step 2:
dummy → 1 → 2 → 3 → 4 → 5
            P   S
                        F

Step 3 (F goes null — stop):
dummy → 1 → 2 → 3 → 4 → 5 → null
                P   S
                              F
```

**Removal:** `prev.next = slow.next` → node 3 now points to node 5; node 4 is unlinked.

```
1 → 2 → 3 → 5
```

→ return `dummy.next` = node 1

---

## 2. Optimal Approach

Your solution **is** the optimal approach. For completeness:

Advance a `fast` pointer `n` steps ahead of `slow`. Walk both together until `fast` is null — at that point `slow` is exactly the node to remove. A `prev` pointer (anchored at a dummy sentinel) tracks the node before `slow` to perform the unlinking.

- **Time:** O(sz) — single pass; each node visited at most once.
- **Space:** O(1) — fixed number of pointers.

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(-1, head);
    ListNode prev = dummy;
    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && n > 0) {
        fast = fast.next;
        n--;
    }

    while (fast != null) {
        fast = fast.next;
        prev = prev.next;
        slow = slow.next;
    }

    prev.next = slow.next;
    slow.next = null;

    return dummy.next;
}
```

**Algorithm Trace** — same as above (your solution is optimal).

---

## 3. Alternative Approaches

### Two-Pass: Count Length, Then Remove

Traverse the list once to count its length `sz`. The node to remove is at position `sz - n` from the front (0-indexed). Traverse again to that position and unlink it.

- **Time:** O(sz) — two full passes.
- **Space:** O(1) — only a counter and a couple of pointers.

When acceptable: straightforward to reason about and implement under time pressure; same asymptotic complexity as the one-pass approach.

```java
public ListNode removeNthFromEnd(ListNode head, int n) {
    ListNode dummy = new ListNode(-1, head);
    int size = 0;
    ListNode curr = head;

    while (curr != null) {
        size++;
        curr = curr.next;
    }

    int stepsFromFront = size - n;
    ListNode prev = dummy;

    for (int i = 0; i < stepsFromFront; i++) {
        prev = prev.next;
    }

    ListNode target = prev.next;
    prev.next = target.next;
    target.next = null;

    return dummy.next;
}
```

**Algorithm Trace**

Input: `head = [1, 2, 3, 4, 5]`, `n = 2`

Pass 1 — count length:

| Step | Node | size |
|------|------|------|
| 1 | 1 | 1 |
| 2 | 2 | 2 |
| 3 | 3 | 3 |
| 4 | 4 | 4 |
| 5 | 5 | 5 |

`stepsFromFront = 5 - 2 = 3`

Pass 2 — walk 3 steps from dummy:

```
dummy → 1 → 2 → 3 → 4 → 5
  P
Step 1:  P=1
Step 2:  P=2
Step 3:  P=3   target=4
```

`prev.next = target.next` → node 3 points to node 5.

→ return `[1, 2, 3, 5]`
