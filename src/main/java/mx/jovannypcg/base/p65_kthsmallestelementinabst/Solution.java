package mx.jovannypcg.base.p65_kthsmallestelementinabst;

/**
 * Given the {@code root} of a binary search tree, and an integer {@code k}, return the
 * {@code k}th smallest value (1-indexed) of all the values of the nodes in the tree.
 *
 * @see <a href="https://leetcode.com/problems/kth-smallest-element-in-a-bst/">Kth Smallest Element in a BST - LeetCode</a>
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

    public int kthSmallest(TreeNode root, int k) {
        return dfs(root, k, new Counter());
    }

    private int dfs(TreeNode root, int k, Counter counter) {
        if (root == null) return -1;

        int left = dfs(root.left, k, counter);

        counter.count = counter.count + 1;
        if (counter.count == k) return root.val;

        int right = dfs(root.right, k, counter);

        return left >= 0 ? left : right;
    }

    /*
     * Wrapper to instantiate a counter so that
     * every DFS call modifies the reference to the
     * instance.
     *
     * At the beginning, I tried `int` for the counter:
     *
     * dfs(TreeNode, int, int counter)
     *
     * But since `int` is a native value, that passed
     * as a copy, not reference and the count was messed up.
     */
    private static class Counter {
        int count;
    }
}
