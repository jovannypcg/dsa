# Middle of the Linked List — Review

| | |
|---|---|
| **Solved on** | 2026-06-30 |
| **DSA Category** | Linked List |

---

## 1. Your Solution Assessment

**Correctness:** Correct. The fast/slow pointer logic handles odd-length lists (fast lands on the last node), even-length lists (fast lands on null, giving the second middle), and single-node lists (loop never runs, head is returned). The `head == null` guard is safe, though the constraints guarantee at least one node so it never triggers in practice.

**Code quality:** Clean and idiomatic. Variable names `slow` and `fast` communicate the algorithm's intent without any comments. No unnecessary branches.

**Time complexity: O(n)** — `fast` advances two nodes per iteration, so the loop runs n/2 times.

**Space complexity: O(1)** — only two pointers regardless of list length.

**Algorithm trace** — Input: `head = [1, 2, 3, 4, 5]`

```
Initial:
1 → 2 → 3 → 4 → 5 → null
S
F

Iteration 1:  slow = slow.next, fast = fast.next.next
1 → 2 → 3 → 4 → 5 → null
    S
        F

Iteration 2:  slow = slow.next, fast = fast.next.next
1 → 2 → 3 → 4 → 5 → null
        S
                F

fast.next == null → exit loop
→ return slow (node 3)
```

---

## 2. Optimal Approach

Your solution **is** the optimal approach. The fast/slow pointer technique (also called the tortoise-and-hare pattern) finds the middle in a single pass with constant space.

**Time complexity: O(n)** — one traversal, each node visited at most twice (once by each pointer).

**Space complexity: O(1)** — two pointer variables only.

```java
public ListNode middleNode(ListNode head) {
    ListNode slow = head;
    ListNode fast = head;

    while (fast != null && fast.next != null) {
        slow = slow.next;
        fast = fast.next.next;
    }

    return slow;
}
```

**Algorithm trace** — Input: `head = [1, 2, 3, 4, 5, 6]`

```
Initial:
1 → 2 → 3 → 4 → 5 → 6 → null
S
F

Iteration 1:
1 → 2 → 3 → 4 → 5 → 6 → null
    S
        F

Iteration 2:
1 → 2 → 3 → 4 → 5 → 6 → null
        S
                F

Iteration 3:
1 → 2 → 3 → 4 → 5 → 6 → null
            S
                        F (= null)

fast == null → exit loop
→ return slow (node 4) ✓ second middle
```

---

## 3. Alternative Approaches

### Two-pass count-then-seek

Traverse the list once to count `n` nodes, then traverse again for `n/2` steps from the head.

- **Time: O(n)** — two full passes, but still linear.
- **Space: O(1)** — no extra storage.
- **When acceptable:** Perfectly fine in an interview if you blank on the fast/slow trick; it's just a bit more code and traverses the list twice.

```java
public ListNode middleNode(ListNode head) {
    int n = 0;
    ListNode curr = head;
    while (curr != null) { n++; curr = curr.next; }

    curr = head;
    for (int i = 0; i < n / 2; i++) curr = curr.next;
    return curr;
}
```

**Algorithm trace** — Input: `head = [1, 2, 3, 4, 5]`

| Pass | Step | curr.val | Notes |
|------|------|----------|-------|
| Count | 1→5 | — | n = 5 |
| Seek | 0 | 1 | i=0 |
| Seek | 1 | 2 | i=1 |
| Seek | 2 | 3 | i=2, loop ends (i < 5/2 = 2) |
→ return node 3

---

### Collect into array

Dump all nodes into an `ArrayList`, then return `arr.get(arr.size() / 2)`.

- **Time: O(n)** — one pass to collect, O(1) index access.
- **Space: O(n)** — stores a reference to every node.
- **When acceptable:** Useful for quick prototyping or when list mutation is a concern; not ideal in interviews where the interviewers expect the pointer technique.

```java
public ListNode middleNode(ListNode head) {
    List<ListNode> nodes = new ArrayList<>();
    while (head != null) { nodes.add(head); head = head.next; }
    return nodes.get(nodes.size() / 2);
}
```

**Algorithm trace** — Input: `head = [1, 2, 3, 4, 5]`

| i | nodes collected |
|---|----------------|
| 0 | [1] |
| 1 | [1, 2] |
| 2 | [1, 2, 3] |
| 3 | [1, 2, 3, 4] |
| 4 | [1, 2, 3, 4, 5] |
→ nodes.get(5 / 2) = nodes.get(2) = node 3
