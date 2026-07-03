# Trees

## Pattern
Decompose the problem recursively: solve for left subtree, solve for right subtree, combine. Alternatively, traverse level-by-level with BFS.

## How to Recognize
- Input is a `TreeNode`
- "Height / depth / diameter", "path sum", "lowest common ancestor"
- "Validate BST", "serialize/deserialize", "symmetric"
- Need to process nodes in pre/in/post order

## Approach
- **DFS (recursive)**: most tree problems reduce to "what do I return from each node?"
  - Preorder (root → left → right): build/serialize
  - Inorder (left → root → right): BST sorted order
  - Postorder (left → right → root): height, diameter, bottom-up aggregation
- **BFS**: level-order problems — use a queue.
- **Key insight**: trust the recursion. Define what your function returns, handle the base case (null), and combine children's results.

## Template

```java
// DFS postorder — height of tree
public int height(TreeNode root) {
    if (root == null) return 0;                          // base case

    int left  = height(root.left);
    int right = height(root.right);

    return 1 + Math.max(left, right);                   // combine
}
```

```java
// DFS with result tracking — diameter
private int diameter = 0;

public int diameterOfBinaryTree(TreeNode root) {
    depth(root);
    return diameter;
}

private int depth(TreeNode node) {
    if (node == null) return 0;
    int left  = depth(node.left);
    int right = depth(node.right);
    diameter = Math.max(diameter, left + right);         // update global answer
    return 1 + Math.max(left, right);
}
```

```java
// BFS — level order traversal
public List<List<Integer>> levelOrder(TreeNode root) {
    List<List<Integer>> result = new ArrayList<>();
    if (root == null) return result;

    Queue<TreeNode> q = new ArrayDeque<>();
    q.offer(root);

    while (!q.isEmpty()) {
        int size = q.size();
        List<Integer> level = new ArrayList<>();

        for (int i = 0; i < size; i++) {
            TreeNode node = q.poll();
            level.add(node.val);
            if (node.left  != null) q.offer(node.left);
            if (node.right != null) q.offer(node.right);
        }

        result.add(level);
    }

    return result;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Invert Binary Tree | [LeetCode #226](https://leetcode.com/problems/invert-binary-tree/) |
| 2 | Maximum Depth of Binary Tree | [LeetCode #104](https://leetcode.com/problems/maximum-depth-of-binary-tree/) |
| 3 | Diameter of Binary Tree | [LeetCode #543](https://leetcode.com/problems/diameter-of-binary-tree/) |
| 4 | Balanced Binary Tree | [LeetCode #110](https://leetcode.com/problems/balanced-binary-tree/) |
| 5 | Same Tree | [LeetCode #100](https://leetcode.com/problems/same-tree/) |
| 6 | Subtree of Another Tree | [LeetCode #572](https://leetcode.com/problems/subtree-of-another-tree/) |
| 7 | Lowest Common Ancestor of BST | [LeetCode #235](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/) |
| 8 | Binary Tree Level Order Traversal | [LeetCode #102](https://leetcode.com/problems/binary-tree-level-order-traversal/) |
| 9 | Binary Tree Right Side View | [LeetCode #199](https://leetcode.com/problems/binary-tree-right-side-view/) |
| 10 | Count Good Nodes in Binary Tree | [LeetCode #1448](https://leetcode.com/problems/count-good-nodes-in-binary-tree/) |
| 11 | Validate Binary Search Tree | [LeetCode #98](https://leetcode.com/problems/validate-binary-search-tree/) |
| 12 | Kth Smallest Element in a BST | [LeetCode #230](https://leetcode.com/problems/kth-smallest-element-in-a-bst/) |
| 13 | Construct Binary Tree from Preorder and Inorder | [LeetCode #105](https://leetcode.com/problems/construct-binary-tree-from-preorder-and-inorder-traversal/) |
| 14 | Binary Tree Maximum Path Sum | [LeetCode #124](https://leetcode.com/problems/binary-tree-maximum-path-sum/) |
| 15 | Serialize and Deserialize Binary Tree | [LeetCode #297](https://leetcode.com/problems/serialize-and-deserialize-binary-tree/) |
| 16 | Path Sum II | [LeetCode #113](https://leetcode.com/problems/path-sum-ii/) |
| 17 | Symmetric Tree | [LeetCode #101](https://leetcode.com/problems/symmetric-tree/) |
| 18 | Populating Next Right Pointers | [LeetCode #116](https://leetcode.com/problems/populating-next-right-pointers-in-each-node/) |
| 19 | Flatten Binary Tree to Linked List | [LeetCode #114](https://leetcode.com/problems/flatten-binary-tree-to-linked-list/) |
| 20 | Lowest Common Ancestor of Binary Tree | [LeetCode #236](https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-tree/) |
