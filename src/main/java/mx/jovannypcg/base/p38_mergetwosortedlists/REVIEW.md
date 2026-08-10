| | |
|---|---|
| **Solved on** | 2026-08-10 |
| **DSA Category** | Linked List |

## 1. Your Solution Assessment

**Correctness:** Correct. All 10 tests pass, including both empty lists, one list empty, ties in value, negative values, and the maximum-length case (50 nodes each). The tie-breaking rule (equal values fall into the `else` branch, so `b`'s node is taken first) is applied consistently and produces a valid non-decreasing merge either way.

**Code quality:** Clear and easy to follow. The dummy-head pattern (`out`) avoids special-casing the first node, and `currOut`/`currA`/`currB` names make the roles obvious. The nodes are re-linked directly (`currOut.next = currA` / `currOut.next = currB`) rather than copied into new nodes — a true splice, matching the problem statement exactly ("made by splicing together the nodes of the first two lists").

**Time complexity:** O(n + m), where `n` and `m` are the lengths of `list1` and `list2`. Each node from both lists is visited exactly once across the main loop and the two tail-attachment checks.

**Space complexity:** O(1) extra — no new nodes are allocated anywhere; every node in the output is one of the original input nodes, re-pointed in place. Only the single dummy sentinel node is allocated, which doesn't scale with input size.

**Algorithm trace** (two-pointer merge, `list1 = [1, 2, 4]`, `list2 = [1, 3, 4]`):

```
A=1, B=1  →  1 < 1? No (tie → take B, advance B)
[1, 2, 4]
 A
[1, 3, 4]
 B
output = [1]

A=1, B=3  →  1 < 3? Yes (take A, advance A)
[1, 2, 4]
 A
[1, 3, 4]
    B
output = [1, 1]

A=2, B=3  →  2 < 3? Yes (take A, advance A)
[1, 2, 4]
    A
[1, 3, 4]
    B
output = [1, 1, 2]

A=4, B=3  →  4 < 3? No (take B, advance B)
[1, 2, 4]
       A
[1, 3, 4]
    B
output = [1, 1, 2, 3]

A=4, B=4  →  4 < 4? No (tie → take B, advance B)
[1, 2, 4]
       A
[1, 3, 4]
       B
output = [1, 1, 2, 3, 4]

B is now exhausted (null) → loop ends.
Remaining list1 tail (node 4) is spliced directly onto the output.
output = [1, 1, 2, 3, 4, 4]
```
→ return `[1, 1, 2, 3, 4, 4]` — same nodes as the input, just re-linked; no copies.

## 2. Optimal Approach

Walk both lists with a dummy head and a tail pointer; at each step, point the tail's `next` at whichever of the two current nodes has the smaller value, then advance that list's pointer. When one list runs out, attach the remainder of the other directly — this already happens for free since the rest is already sorted. This is the approach implemented above.

**Time complexity:** O(n + m) — one pass over both lists.

**Space complexity:** O(1) extra — no new nodes are ever allocated.

```java
public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
    ListNode dummy = new ListNode(-1);
    ListNode tail = dummy;

    while (list1 != null && list2 != null) {
        if (list1.val <= list2.val) {
            tail.next = list1;
            list1 = list1.next;
        } else {
            tail.next = list2;
            list2 = list2.next;
        }
        tail = tail.next;
    }

    tail.next = (list1 != null) ? list1 : list2;

    return dummy.next;
}
```

**Algorithm trace** (`list1 = [1, 2, 4]`, `list2 = [1, 3, 4]`):

```
A=1, B=1  →  1 <= 1? Yes (take A, advance A)
[1, 2, 4]
 A
[1, 3, 4]
 B
output = [1]

A=2, B=1  →  2 <= 1? No (take B, advance B)
[1, 2, 4]
    A
[1, 3, 4]
 B
output = [1, 1]

A=2, B=3  →  2 <= 3? Yes (take A, advance A)
[1, 2, 4]
    A
[1, 3, 4]
    B
output = [1, 1, 2]

A=4, B=3  →  4 <= 3? No (take B, advance B)
[1, 2, 4]
       A
[1, 3, 4]
    B
output = [1, 1, 2, 3]

A=4, B=4  →  4 <= 4? Yes (take A, advance A)
[1, 2, 4]
       A
[1, 3, 4]
       B
output = [1, 1, 2, 3, 4]

A is now exhausted (null) → loop ends.
Remaining list2 tail (node 4) is spliced directly onto the output.
output = [1, 1, 2, 3, 4, 4]
```
→ return `[1, 1, 2, 3, 4, 4]` — same nodes as the input, just re-linked; no copies.

## 3. Alternative Approaches

### Recursive merge

Mirror the iterative logic with recursion: the smaller head's `next` becomes the result of recursively merging the rest of its list with the other list.

**Time complexity:** O(n + m) — each call consumes exactly one node from one list.

**Space complexity:** O(n + m) — call stack depth grows with the combined length (no tail-call optimization in the JVM), unlike the O(1) iterative version.

**When acceptable:** Fine for interviews when you want to show you know both styles, or when list lengths are small enough (constraints here cap at 50 nodes each) that stack depth isn't a concern. Prefer the iterative version for large or unbounded inputs.

```java
public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
    if (l1 == null) return l2;
    if (l2 == null) return l1;

    if (l1.val <= l2.val) {
        l1.next = mergeTwoLists(l1.next, l2);
        return l1;
    } else {
        l2.next = mergeTwoLists(l1, l2.next);
        return l2;
    }
}
```

**Algorithm trace** (`list1 = [1, 2]`, `list2 = [1, 3]`):

| Depth | Call | Returns |
|---|---|---|
| 0 | mergeTwoLists([1,2], [1,3]) | l1(1).next = [1,2,3] → [1,1,2,3] |
| 1 | mergeTwoLists([2], [1,3]) | l2(1).next = [2,3] → [1,2,3] |
| 2 | mergeTwoLists([2], [3]) | l1(2).next = [3] → [2,3] |
| 3 | mergeTwoLists([], [3]) | l1 is null → returns l2 = [3] |
→ `mergeTwoLists([1,2], [1,3])` = `[1, 1, 2, 3]`

### Collect, sort, and rebuild

Ignore that both lists are already sorted: walk both lists collecting every value into one array, sort the array, then rebuild a new linked list from the sorted values.

**Time complexity:** O((n + m) log(n + m)) — dominated by the sort, which is unnecessary work since both inputs are already ordered.

**Space complexity:** O(n + m) — for the array plus the newly built list.

**When acceptable:** Only under time pressure as a "get something working" fallback, or if you forget the merge invariant mid-interview. It throws away the sorted-input guarantee, so it's strictly worse than the merge approach and shouldn't be the final answer.

**Algorithm trace** (`list1 = [1, 2, 4]`, `list2 = [1, 3, 4]`):

| Step | Action | Result |
|---|---|---|
| 1 | Collect list1 values | `[1, 2, 4]` |
| 2 | Collect list2 values | `[1, 3, 4]` |
| 3 | Concatenate | `[1, 2, 4, 1, 3, 4]` |
| 4 | Sort | `[1, 1, 2, 3, 4, 4]` |
| 5 | Rebuild linked list from sorted array | `1 → 1 → 2 → 3 → 4 → 4` |
→ return `[1, 1, 2, 3, 4, 4]`
