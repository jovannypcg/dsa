package mx.jovannypcg.base.p63_countgoodnodesinbinarytree;

/**
 * Given a binary tree {@code root}, a node X in the tree is named good if in the path from
 * root to X there are no nodes with a value greater than X.
 *
 * <p>Return the number of good nodes in the binary tree.
 *
 * @see <a href="https://leetcode.com/problems/count-good-nodes-in-binary-tree/">Count Good Nodes in Binary Tree - LeetCode</a>
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

    public int goodNodes(TreeNode root) {
        return goodNodes(root, Integer.MIN_VALUE);
    }

    private int goodNodes(TreeNode root, int max) {
        if (root == null) return 0;

        max = Math.max(max, root.val);

        int res = isGoodNode(root, max) ? 1 : 0;
        int left = goodNodes(root.left, max);
        int right = goodNodes(root.right, max);

        return res + left + right;
    }

    private boolean isGoodNode(TreeNode root, int max) {
        return root.val >= max;
    }
}
