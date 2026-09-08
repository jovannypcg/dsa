package mx.jovannypcg.base.p75_leafsimilartrees;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

/**
 * Consider all the leaves of a binary tree, from left to right order, the values of those
 * leaves form a leaf value sequence.
 *
 * <p>Two binary trees are considered leaf-similar if their leaf value sequence is the same.
 *
 * <p>Return {@code true} if and only if the two given trees with head nodes {@code root1} and
 * {@code root2} are leaf-similar.
 *
 * @see <a href="https://leetcode.com/problems/leaf-similar-trees/">Leaf-Similar Trees - LeetCode</a>
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

    public boolean leafSimilar(TreeNode q, TreeNode p) {
        List<Integer> collQ = new ArrayList<>();
        List<Integer> collP = new ArrayList<>();

        traverseDFS(q, collQ);
        traverseDFS(p, collP);

        return collQ.equals(collP);
    }

    // Broken: BFS groups leaves by depth, not left-to-right position, so leaves at different
    // depths (e.g. a shallow leaf in one subtree vs. a deeper leaf in a sibling subtree) end up
    // out of order. Kept only as a reference; see REVIEW.md Section 4 for a full trace.
    void traverseBFS(TreeNode node, List<Integer> coll) {
        if (node == null) return;

        Deque<TreeNode> queue = new ArrayDeque<>();
        queue.addLast(node);

        while (!queue.isEmpty()) {
            int levelSize = queue.size();

            for (; levelSize > 0; levelSize--) {
                TreeNode current = queue.removeFirst();

                if (isLeaf(current)) {
                    coll.add(current.val);
                    continue;
                }

                if (current.left != null) queue.addLast(current.left);
                if (current.right != null) queue.addLast(current.right);
            }
        }
    }

    void traverseDFS(TreeNode node, List<Integer> coll) {
        if (node == null) return;
        if (isLeaf(node)) {
            coll.add(node.val);
            return;
        }

        traverseDFS(node.left, coll);
        traverseDFS(node.right, coll);
    }

    boolean isLeaf(TreeNode node) {
        return node.left == null && node.right == null;
    }
}
