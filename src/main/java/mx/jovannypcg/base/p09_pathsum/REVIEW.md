# Path Sum — Review

| | |
|---|---|
| **Solved on** | 2026-06-13 |
| **DSA Category** | Trees |

---

## 1. Your Solution Assessment

**Correctness:** Solid. Null root is handled, the leaf check is correct (both children null), and the accumulated sum is compared at leaf nodes only. Skipping null children explicitly avoids recursing into non-existent paths, which is correct.

**Code quality:** The three-method split (`dfs` → `dfsHelper` → `isLeaf`) adds more ceremony than this problem warrants. `dfs` does nothing beyond a null check that `dfsHelper` could own. The accumulation-forward style (building `sum` up from 0) works, but the subtraction style (decrementing `targetSum` as you descend) is idiomatic for this problem and collapses everything into one method.

**Time complexity: O(n)** — every node is visited exactly once.

**Space complexity: O(h)** — the call stack reaches at most the height of the tree. O(log n) for a balanced tree; O(n) worst case for a fully skewed tree.

---

## 2. Optimal Approach

Subtract the current node's value from `targetSum` at each step. At a leaf, you need `targetSum` to equal the node's value — equivalently, `targetSum - node.val == 0`. This removes the need for any helper or accumulator variable and keeps the entire logic in one method.

**Time complexity: O(n)** — each node visited once.  
**Space complexity: O(h)** — recursion depth equals tree height.

```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;
    if (root.left == null && root.right == null) return root.val == targetSum;
    return hasPathSum(root.left, targetSum - root.val)
        || hasPathSum(root.right, targetSum - root.val);
}
```

---

## 3. Alternative Approaches

### BFS with paired queues

Use two queues in lockstep: one for nodes to visit, one for the remaining sum at each node. When you dequeue a leaf whose remaining sum is 0, a valid path exists.

**Time complexity: O(n)** — every node is enqueued and dequeued once.  
**Space complexity: O(w)** — the queues hold at most one full level of the tree at a time, where w is the maximum width. O(n) worst case for a complete tree (bottom level ≈ n/2 nodes); O(log n) for a balanced tree.

```java
public boolean hasPathSum(TreeNode root, int targetSum) {
    if (root == null) return false;

    Queue<TreeNode> nodes = new LinkedList<>();
    Queue<Integer> remaining = new LinkedList<>();

    nodes.offer(root);
    remaining.offer(targetSum - root.val);

    while (!nodes.isEmpty()) {
        TreeNode node = nodes.poll();
        int rem = remaining.poll();

        if (node.left == null && node.right == null && rem == 0) return true;

        if (node.left != null) {
            nodes.offer(node.left);
            remaining.offer(rem - node.left.val);
        }

        if (node.right != null) {
            nodes.offer(node.right);
            remaining.offer(rem - node.right.val);
        }
    }

    return false;
}
```

Prefer BFS when you suspect the target path is near the top of the tree — it finds shallow solutions without descending the entire tree. In practice both DFS and BFS are O(n), but BFS exits earlier on shallow hits.

### Brute force — enumerate all root-to-leaf paths

Collect every root-to-leaf path as a list, sum each one, and check against `targetSum`.

**Time complexity: O(n²)** — O(n) paths, each up to O(n) long to copy.  
**Space complexity: O(n·h)** — storing all paths simultaneously.

Acceptable only under extreme time pressure in an interview where you cannot recall the recursive approach. Not recommended otherwise.
