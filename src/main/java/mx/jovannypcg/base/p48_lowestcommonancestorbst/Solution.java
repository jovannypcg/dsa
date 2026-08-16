package mx.jovannypcg.base.p48_lowestcommonancestorbst;

/**
 * Given a binary search tree (BST), find the lowest common ancestor (LCA) node of two given
 * nodes {@code p} and {@code q} in the BST.
 *
 * <p>According to the definition of LCA on Wikipedia: "The lowest common ancestor is defined
 * between two nodes {@code p} and {@code q} as the lowest node in the tree that has both
 * {@code p} and {@code q} as descendants (where we allow a node to be a descendant of itself)."
 *
 * @see <a href="https://leetcode.com/problems/lowest-common-ancestor-of-a-binary-search-tree/">Lowest Common Ancestor of a Binary Search Tree - LeetCode</a>
 */
public class Solution {

    public static class TreeNode {

        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }

    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if (root.val == p.val || root.val == q.val) return root;

        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        } else if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        } else {
            return root;
        }
    }
}
