package mx.jovannypcg.base.p43_maxdepthbinarytree;

/**
 * Given the root of a binary tree, return its maximum depth.
 *
 * <p>A binary tree's maximum depth is the number of nodes along the longest path from the
 * root node down to the farthest leaf node.
 *
 * @see <a href="https://leetcode.com/problems/maximum-depth-of-binary-tree/">Maximum Depth of Binary Tree - LeetCode</a>
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

    public int maxDepth(TreeNode root) {
        return maxDepth(root, 0);
    }

    int maxDepth(TreeNode root, int acc) {
        if (root == null) return acc;

        int left = maxDepth(root.left, acc + 1);
        int right = maxDepth(root.right, acc + 1);

        return Math.max(left, right);
    }
}
