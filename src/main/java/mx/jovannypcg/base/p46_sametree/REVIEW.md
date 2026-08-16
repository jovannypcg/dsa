| | |
|---|---|
| **Solved on** | 2026-08-16 |
| **DSA Category** | Trees |

## 1. Your Solution Assessment

**Correctness:** Correct. The base cases are handled in the right order — `p == null && q == null` returns `true` before either node is dereferenced, and `p == null || q == null` catches the "one side is missing" case before comparing `.val`, so there is no risk of a `NullPointerException`. Values are compared with `!=` (correct for primitive `int`), and the final `return` recurses into both subtrees. All 16 tests, including the empty-tree, single-node, and 100-node skewed-tree boundary cases, pass without modification.

**Code quality:** Clear and minimal. The null-handling comment at line 31 documents a non-obvious invariant (why it's now safe to call `.val`) rather than restating the code. Returning `isSameTree(p.left, q.left) && isSameTree(p.right, q.right)` directly (rather than assigning each side to a local variable first) lets Java's `&&` short-circuit: as soon as the left subtree comparison returns `false`, the right subtree is never even visited. This is the same algorithm as the standard optimal solution.

**Time complexity:** O(n), where n is the number of nodes in the smaller of the two trees. Every node pair is visited at most once, and short-circuiting skips whole subtrees once a mismatch is found anywhere above them.

**Space complexity:** O(h), where h is the height of the tree, for the recursion call stack. Worst case O(n) for a completely skewed tree (as exercised by the 100-node right-skewed test), best case O(log n) for a balanced tree.

**Algorithm trace** — call stack table on `p = [1,2,1]`, `q = [1,1,2]` (Example 3: same structure, swapped values), showing the short-circuit in action:

| Depth | Call | Returns |
|---|---|---|
| 0 | isSameTree(1, 1) | val match → left(depth 1a) && right(never called) |
| 1a | isSameTree(2, 1) | val 2 ≠ 1 → **false** |
| 0 | (short-circuits) | left already **false** → right skipped entirely → **false** |
→ return `false`

## 2. Optimal Approach

This submitted solution *is* the optimal approach. A pre-order (or any consistent-order) recursive comparison is necessary and sufficient here: every node pair must potentially be examined (two trees identical except for one deeply buried leaf force a full traversal in the worst case), so no algorithm can beat O(n) time in the worst case, and short-circuiting `&&` guarantees the best case (an early mismatch) is exploited instead of wasted. The recursive shape: two `null` roots are trivially the same; one `null` and one non-`null` root can never be the same; otherwise the current pair must hold equal values *and* both the left subtrees and the right subtrees must recursively be the same.

**Time complexity:** O(n), where n is the number of nodes in the smaller tree — each node pair is visited at most once, fewer when a mismatch short-circuits the remaining comparisons.

**Space complexity:** O(h) for the recursion stack, where h is the tree height — O(log n) for a balanced tree, O(n) worst case for a skewed one.

```java
public boolean isSameTree(TreeNode p, TreeNode q) {
    if (p == null && q == null) return true;
    if (p == null || q == null) return false;

    return p.val == q.val
        && isSameTree(p.left, q.left)
        && isSameTree(p.right, q.right);
}
```

**Algorithm trace** — call stack table on the "mismatch away from root" case (`p`: 1→2→3→4 all left children; `q`: same shape but the deepest leaf is `9` instead of `4`), showing a mismatch found several levels deep:

| Depth | Call | Returns |
|---|---|---|
| 0 | isSameTree(1, 1) | val match → left(depth 1) && right(null,null)=true |
| 1 | isSameTree(2, 2) | val match → left(depth 2) && right(null,null)=true |
| 2 | isSameTree(3, 3) | val match → left(depth 3) && right(null,null)=true |
| 3 | isSameTree(4, 9) | val 4 ≠ 9 → **false** |
| 2 | isSameTree(3, 3) resumes | left **false** → right(null,null) short-circuited, never evaluated → **false** |
| 1 | isSameTree(2, 2) resumes | left **false** → right short-circuited → **false** |
| 0 | isSameTree(1, 1) resumes | left **false** → right short-circuited → **false** |
→ return `false`

## 3. Alternative Approaches

**Iterative BFS with two queues.** Push both roots into a queue (or two parallel queues). At each step, dequeue one node from each side, check for the null/value mismatches described above, and if both are non-null and equal, enqueue their `left` and `right` children pairwise. Return `false` as soon as any pair fails the check; return `true` if the queues empty out together.

- Time: O(n) — every node pair is dequeued and checked once.
- Space: O(w), where w is the maximum width of either tree (the most nodes held in the queue at once) — O(n) worst case for a wide/complete tree.
- When acceptable: useful when recursion depth is a concern (e.g., avoiding stack overflow on pathologically deep, skewed trees with tens of thousands of nodes) or when an interviewer specifically asks for an iterative solution.

**Serialize both trees and compare strings.** Traverse each tree in pre-order, emitting a sentinel token (e.g., `"#"`) for every `null` child so the serialization captures structure as well as values (without the null markers, `[1,2]` and `[1,null,2]` would serialize identically and wrongly compare equal). Then compare the two resulting strings or lists directly.

- Time: O(n) to serialize each tree plus O(n) to compare the strings — O(n) overall.
- Space: O(n) to store both serialized representations, in addition to the O(h) recursion used during serialization.
- When acceptable: rarely preferred here since it uses strictly more space than the direct comparison for no benefit, but the technique (canonical serialization) generalizes well to other problems like "subtree of another tree," where comparing serialized forms is genuinely useful.
