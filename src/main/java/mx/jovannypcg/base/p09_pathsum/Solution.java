package mx.jovannypcg.base.p09_pathsum;

/**
 * Given the root of a binary tree and an integer {@code targetSum}, return {@code true} if the
 * tree has a root-to-leaf path such that adding up all the values along the path equals
 * {@code targetSum}.
 *
 * <p>A leaf is a node with no children.
 *
 * @see <a href="https://leetcode.com/problems/path-sum/description/">Path Sum - LeetCode</a>
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

    public boolean hasPathSum(TreeNode root, int targetSum) {
        return dfs(root, targetSum);
    }

    boolean dfs(TreeNode root, int target) {
        if (root == null) {
            return false;
        }

        return dfsHelper(root, target, 0);
    }

    boolean dfsHelper(TreeNode root, int target, int sum) {
        sum += root.val;

        if (isLeaf(root)) {
            return target == sum;
        }

        boolean left = false;
        if (root.left != null) {
            left = dfsHelper(root.left, target, sum);
        }

        boolean right = false;
        if (root.right != null) {
            right = dfsHelper(root.right, target, sum);
        }

        return left || right;
    }

    boolean isLeaf(TreeNode node) {
        return node != null && node.left == null && node.right == null;
    }
}
