package mx.jovannypcg.base.p42_inverttree;

/**
 * Given the root of a binary tree, invert the tree, and return its root.
 *
 * <p>Inverting a binary tree means swapping the left and right children of every node in the
 * tree.
 *
 * @see <a href="https://leetcode.com/problems/invert-binary-tree/">Invert Binary Tree - LeetCode</a>
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

    public TreeNode invertTree(TreeNode root) {
        if (root == null) return root;

        TreeNode left = invertTree(root.left);
        TreeNode right = invertTree(root.right);

        root.left = right;
        root.right = left;

        return root;
    }
}
