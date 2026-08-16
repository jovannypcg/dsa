package mx.jovannypcg.base.p46_sametree;

/**
 * Given the roots of two binary trees {@code p} and {@code q}, return {@code true} if they are
 * the same tree, and {@code false} otherwise.
 *
 * <p>Two binary trees are considered the same if they are structurally identical and the nodes
 * have the same value at each corresponding position.
 *
 * @see <a href="https://leetcode.com/problems/same-tree/">Same Tree - LeetCode</a>
 */
public class Solution {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public boolean isSameTree(TreeNode p, TreeNode q) {
        if (p == null && q == null) return true;
        if (p == null || q == null) return false;

        // At this point it's safe to call both `p.val` and `q.val`

        if (p.val != q.val) return false;

        return isSameTree(p.left, q.left) && isSameTree(p.right, q.right);
    }
}
