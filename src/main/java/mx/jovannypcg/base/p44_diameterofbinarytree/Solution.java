package mx.jovannypcg.base.p44_diameterofbinarytree;

/**
 * Given the root of a binary tree, return the length of the diameter of the tree.
 *
 * <p>The diameter of a binary tree is the length of the longest path between any two nodes
 * in the tree. This path may or may not pass through the root. The length of a path between
 * two nodes is represented by the number of edges between them.
 *
 * @see <a href="https://leetcode.com/problems/diameter-of-binary-tree/">Diameter of Binary Tree - LeetCode</a>
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

    private int maxDiameter = 0;

    public int diameterOfBinaryTree(TreeNode root) {
        maxDiameter = 0;
        dfs(root);
        return maxDiameter;
    }

    int dfs(TreeNode root) {
        if (root == null) return 0;

        int left = dfs(root.left);
        int right = dfs(root.right);
        int diameter = left + right;

        maxDiameter = Math.max(maxDiameter, diameter);

        return 1 + Math.max(left, right);
    }
}
