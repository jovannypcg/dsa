package mx.jovannypcg.base.p47_subtreeofanothertree;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Given the roots of two binary trees {@code root} and {@code subRoot}, return {@code true} if
 * there is a subtree of {@code root} with the same structure and node values as {@code subRoot},
 * and {@code false} otherwise.
 *
 * <p>A subtree of a binary tree {@code tree} is a tree that consists of a node in {@code tree}
 * and all of this node's descendants. The tree {@code tree} could also be considered as a
 * subtree of itself.
 *
 * @see <a href="https://leetcode.com/problems/subtree-of-another-tree/">Subtree of Another Tree - LeetCode</a>
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

    public boolean isSubtree(TreeNode root, TreeNode subRoot) {
        if (root == null && subRoot == null) return true;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int size = queue.size();

            while (size != 0) {
                TreeNode current = queue.poll();

                if (isSameTree(current, subRoot)) return true;

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);

                size--;
            }
        }

        return false;
    }

    boolean isSameTree(TreeNode q, TreeNode p) {
        if (q == null && p == null) return true;
        if (q == null || p == null) return false;

        if (q.val != p.val) return false;

        return isSameTree(q.left, p.left) && isSameTree(q.right, p.right);
    }
}
