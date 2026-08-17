package mx.jovannypcg.base.p49_binarytreerightsideview;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Given the root of a binary tree, imagine yourself standing on the right side of it. Return
 * the values of the nodes you can see ordered from top to bottom.
 *
 * @see <a href="https://leetcode.com/problems/binary-tree-right-side-view/">Binary Tree Right Side View - LeetCode</a>
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

    public List<Integer> rightSideView(TreeNode root) {
        if (root == null) return List.of();

        List<Integer> view = new ArrayList<>();
        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.offer(root);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            while (levelSize != 0) {
                TreeNode current = queue.poll();
                levelSize--;

                // Last node in the level (right most)
                if (levelSize == 0) {
                    view.add(current.val);
                }

                if (current.left != null) queue.offer(current.left);
                if (current.right != null) queue.offer(current.right);
            }
        }

        return view;
    }

    public List<Integer> rightSideViewExtraMemory(TreeNode root) {
        if (root == null) return List.of();

        List<Integer> view = new ArrayList<>();
        Deque<TreeNode> deque = new ArrayDeque<>();
        deque.addLast(root);

        while (!deque.isEmpty()) {
            int levelSize = deque.size();
            Deque<Integer> level = new ArrayDeque<>();

            while (levelSize != 0) {
                TreeNode current = deque.removeFirst();

                level.addLast(current.val);

                if (current.left != null) deque.addLast(current.left);
                if (current.right != null) deque.addLast(current.right);

                levelSize--;
            }

            int rightMost = level.removeLast();
            view.add(rightMost);
        }

        return view;
    }
}
