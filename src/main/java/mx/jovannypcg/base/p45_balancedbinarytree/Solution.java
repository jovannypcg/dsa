package mx.jovannypcg.base.p45_balancedbinarytree;

/**
 * Given a binary tree, determine if it is height-balanced.
 *
 * <p>A binary tree is height-balanced if, for every node in the tree, the height difference
 * between its left and right subtrees is never more than 1.
 *
 * @see <a href="https://leetcode.com/problems/balanced-binary-tree/">Balanced Binary Tree - LeetCode</a>
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

    private boolean balanced = true;

    public boolean isBalanced(TreeNode root) {
        balanced = true;
        height(root);
        return balanced;
    }

    int height(TreeNode root) {
        if (root == null) return 0;

        int left = height(root.left);
        int right = height(root.right);
        int diff = Math.abs(left - right);

        // -1 from either child means "already unbalanced below" — poison this node too.
        balanced = (left >= 0 && right >= 0 && diff <= 1);

        // -1 sentinel propagates up instead of a real height, so one violation anywhere
        // in the tree survives all the way to the root instead of being overwritten.
        return balanced ? 1 + Math.max(left, right) : -1;
    }
}
