package mx.jovannypcg.base.p64_validatebinarysearchtree;

/**
 * Given the {@code root} of a binary tree, determine if it is a valid binary search tree (BST).
 *
 * <p>A valid BST is defined as follows:
 * <ul>
 *     <li>The left subtree of a node contains only nodes with keys strictly less than the node's key.</li>
 *     <li>The right subtree of a node contains only nodes with keys strictly greater than the node's key.</li>
 *     <li>Both the left and right subtrees must also be binary search trees.</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/validate-binary-search-tree/">Validate Binary Search Tree - LeetCode</a>
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

    public boolean isValidBST(TreeNode root) {
        return isValidBST(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBST(TreeNode root, long lowerLimit, long upperLimit) {
        if (root == null) return true;

        if (root.val <= lowerLimit || root.val >= upperLimit)
            return false;

        boolean left = isValidBST(root.left, lowerLimit, root.val);
        boolean right = isValidBST(root.right, root.val, upperLimit);

        return left && right;
    }
}
