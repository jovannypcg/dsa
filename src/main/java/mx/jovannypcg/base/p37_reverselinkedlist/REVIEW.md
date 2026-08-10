| | |
|---|---|
| **Solved on** | 2026-08-10 |
| **DSA Category** | Linked List |

## 1. Your Solution Assessment

You implemented both versions the follow-up asked for: `reverseList` (which delegates to a recursive helper `reverseListRecursive`) and a separate `reverseListIterative`.

**Correctness:** Both handle the full range of cases correctly — empty list, single node, and longer lists. The recursive version's base case (`curr == null → return prev`) correctly returns the new head once it walks off the end of the list. The iterative version correctly starts `prev` at `null` so the original head's `next` ends up pointing to `null`, terminating the new list properly. All 9 tests pass, including the 5000-node boundary case and the double-reverse round-trip check.

**Code quality:** Both versions are clean and idiomatic — good variable names (`prev`/`curr`/`next`), no wasted lines, and the recursive helper cleanly threads `prev` as an accumulator parameter instead of relying on instance state.

**Time complexity:** O(n) for both — each node is visited and relinked exactly once.

**Space complexity:** The iterative version is O(1) — only a constant number of pointers are kept regardless of list length. The recursive version is O(n) — each call to `reverseListRecursive` adds a frame to the call stack, and the recursion depth equals the list length (worth noting for the 5000-node constraint: this is safely within Java's default stack size, but it's a real tradeoff worth calling out in an interview).

**Algorithm trace** (recursive version, `head = [1,2,3,4]`):

| Depth | Call | curr | prev | Returns |
|---|---|---|---|---|
| 0 | reverseListRecursive(1→2→3→4, null) | 1 | null | (waits on depth 1) |
| 1 | reverseListRecursive(2→3→4, 1→null) | 2 | 1 | (waits on depth 2) |
| 2 | reverseListRecursive(3→4, 2→1→null) | 3 | 2 | (waits on depth 3) |
| 3 | reverseListRecursive(4, 3→2→1→null) | 4 | 3 | (waits on depth 4) |
| 4 | reverseListRecursive(null, 4→3→2→1→null) | null | 4→3→2→1→null | returns `4→3→2→1→null` |
→ each frame passes its returned value straight back up unchanged → final result `[4, 3, 2, 1]`

## 2. Optimal Approach

The iterative pointer-reversal approach is optimal: walk the list once, and for each node, flip its `next` pointer to point at the previous node instead of the next one, advancing three pointers (`prev`, `curr`, `next`) in lockstep.

**Time complexity:** O(n) — one pass, one relink per node.
**Space complexity:** O(1) — three pointer variables regardless of input size, no call stack growth.

```java
public ListNode reverseList(ListNode head) {
    ListNode prev = null;
    ListNode curr = head;

    while (curr != null) {
        ListNode next = curr.next;
        curr.next = prev;
        prev = curr;
        curr = next;
    }

    return prev;
}
```

**Algorithm trace** (`nums` list `head = [1,2,3,4,5]`):

```
prev=null curr=1  save next=2, 1.next→null
[1]→null   2 → 3 → 4 → 5

prev=1 curr=2  save next=3, 2.next→1
[2]→[1]→null   3 → 4 → 5

prev=2 curr=3  save next=4, 3.next→2
[3]→[2]→[1]→null   4 → 5

prev=3 curr=4  save next=5, 4.next→3
[4]→[3]→[2]→[1]→null   5

prev=4 curr=5  save next=null, 5.next→4
[5]→[4]→[3]→[2]→[1]→null   (curr=null, loop ends)
```
→ return `prev` = `[5, 4, 3, 2, 1]`

## 3. Alternative Approaches

### Recursive reversal
Recurse to the end of the list first, then reverse the `next` pointer of each node on the way back up the call stack (as implemented in your `reverseListRecursive`).

- **Time complexity:** O(n) — every node is visited exactly once across all recursive calls.
- **Space complexity:** O(n) — recursion depth equals list length, so the call stack grows linearly. For this problem's constraint (up to 5000 nodes) this is safe, but for much longer lists it risks a `StackOverflowError`.
- **When acceptable:** Interviews where you're explicitly asked for a recursive solution (as the follow-up here requests), or when list length is known to be bounded. Less suitable as a "default" answer when O(1) space is achievable and no constraint demands recursion.
- **Algorithm trace:** see the trace under Section 1 above — same call sequence applies here since it's the same algorithm.

### Build a new list by prepending
Traverse the original list, and for each node create a **new** node whose `next` points to the previously-built new list (equivalent to prepending to a new list as you go), leaving the original list untouched.

- **Time complexity:** O(n) — one pass over the original list, one allocation per node.
- **Space complexity:** O(n) — allocates an entirely new list instead of relinking in place.
- **When acceptable:** Only if the original list must be preserved unmodified (not required by this problem, which explicitly returns "the reversed list"). Otherwise strictly worse than in-place reversal since it needlessly doubles memory usage.
- **Algorithm trace** (`head = [1,2,3]`):

| step | current (original) | new list built so far |
|---|---|---|
| 1 | 1 | `[1]` |
| 2 | 2 | `[2, 1]` |
| 3 | 3 | `[3, 2, 1]` |
→ return new list `[3, 2, 1]`, original list `[1, 2, 3]` left unchanged
