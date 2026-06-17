package mx.jovannypcg.base.p14_binarytreelevelorder;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given the root of a binary tree, return the level order traversal of its nodes' values
 * (i.e., from left to right, level by level).
 *
 * @see <a href="https://leetcode.com/problems/binary-tree-level-order-traversal/">Binary Tree Level Order Traversal - LeetCode</a>
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

    public List<List<Integer>> levelOrder(TreeNode root) {
        if (root == null) {
            return List.of();
        }

        List<List<Integer>> traversal = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            List<Integer> level = new ArrayList<>();

            while (levelSize > 0) {
                TreeNode current = queue.poll();

                level.add(current.val);

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);

                levelSize--;
            }

            traversal.add(level);
        }

        return traversal;
    }
}
