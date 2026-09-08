package mx.jovannypcg.base.p76_leafconcatenatedtreeequality;

/**
 * You are given the roots of two binary trees, {@code root1} and {@code root2}. Each leaf node
 * contains either a lowercase string value or no value (empty).
 *
 * <p>Consider the sequence of leaf values obtained by an in-order traversal of each tree,
 * restricted to leaf nodes only (values from non-leaf/internal nodes are ignored regardless of
 * whether they hold a value). Concatenate these leaf values, in traversal order, into a single
 * string for each tree.
 *
 * <p>Two trees are considered leaf-concatenation equal if the resulting concatenated strings are
 * identical — even if the trees differ in shape, size, or how the characters are distributed
 * among individual leaves.
 *
 * <p>Return {@code true} if {@code root1} and {@code root2} are leaf-concatenation equal, or
 * {@code false} otherwise.
 *
 * <p><b>Follow-up:</b> can you solve it using O(h1 + h2) extra space, where h1 and h2 are the
 * heights of the two trees — i.e., without materializing either tree's full concatenated string?
 */
public class Solution {

    public static class TreeNode {
        String val;
        TreeNode left;
        TreeNode right;

        TreeNode(String val) { this.val = val; }
        TreeNode(String val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public boolean leafConcatenationEqual(TreeNode q, TreeNode p) {
        StringBuilder collQ = new StringBuilder();
        StringBuilder collP = new StringBuilder();

        traverse(q, collQ);
        traverse(p, collP);

        return collQ.toString().equals(collP.toString());
    }

    private void traverse(TreeNode node, StringBuilder coll) {
        if (node == null) return;

        if (isLeaf(node)) {
            if (node.val != null) coll.append(node.val);
            return;
        }

        traverse(node.left, coll);
        traverse(node.right, coll);
    }

    private boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }
}
