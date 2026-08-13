package mx.jovannypcg.base.p42_inverttree;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.jupiter.api.Test;

class SolutionTest {

    private final Solution solution = new Solution();

    private Solution.TreeNode node(int val) {
        return new Solution.TreeNode(val);
    }

    // Flattens a tree to a level-order list, using null placeholders, trimmed of
    // trailing nulls so the assertion output matches LeetCode-style array notation.
    private List<Integer> toLevelOrder(Solution.TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null) {
            return result;
        }

        Queue<Solution.TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        result.add(root.val);

        while (!queue.isEmpty()) {
            Solution.TreeNode current = queue.poll();

            if (current.left != null) {
                result.add(current.left.val);
                queue.add(current.left);
            } else {
                result.add(null);
            }

            if (current.right != null) {
                result.add(current.right.val);
                queue.add(current.right);
            } else {
                result.add(null);
            }
        }

        while (!result.isEmpty() && result.get(result.size() - 1) == null) {
            result.remove(result.size() - 1);
        }

        return result;
    }

    // -------------------------------------------------------------------------
    // Happy-path cases
    // -------------------------------------------------------------------------

    @Test
    void invertsExampleOne() {
        // [4,2,7,1,3,6,9] -> [4,7,2,9,6,3,1]
        Solution.TreeNode root = new Solution.TreeNode(
            4,
            new Solution.TreeNode(2, node(1), node(3)),
            new Solution.TreeNode(7, node(6), node(9))
        );

        Solution.TreeNode inverted = solution.invertTree(root);

        assertThat(toLevelOrder(inverted)).containsExactly(4, 7, 2, 9, 6, 3, 1);
    }

    @Test
    void invertsExampleTwo() {
        // [2,1,3] -> [2,3,1]
        Solution.TreeNode root = new Solution.TreeNode(2, node(1), node(3));

        Solution.TreeNode inverted = solution.invertTree(root);

        assertThat(toLevelOrder(inverted)).containsExactly(2, 3, 1);
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    void emptyTree_returnsNull() {
        Solution.TreeNode inverted = solution.invertTree(null);

        assertThat(inverted).isNull();
    }

    @Test
    void singleNode_returnsSameValue() {
        Solution.TreeNode inverted = solution.invertTree(node(1));

        assertThat(toLevelOrder(inverted)).containsExactly(1);
    }

    @Test
    void leftSkewedTree_becomesRightSkewed() {
        // 3 -> 2 -> 1 (all left children)
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(2, node(1), null),
            null
        );

        Solution.TreeNode inverted = solution.invertTree(root);

        assertThat(inverted.left).isNull();
        assertThat(inverted.right.val).isEqualTo(2);
        assertThat(inverted.right.right.val).isEqualTo(1);
        assertThat(inverted.right.left).isNull();
    }

    @Test
    void rightSkewedTree_becomesLeftSkewed() {
        // 1 -> 2 -> 3 (all right children)
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            null,
            new Solution.TreeNode(2, null, node(3))
        );

        Solution.TreeNode inverted = solution.invertTree(root);

        assertThat(inverted.right).isNull();
        assertThat(inverted.left.val).isEqualTo(2);
        assertThat(inverted.left.left.val).isEqualTo(3);
        assertThat(inverted.left.right).isNull();
    }

    @Test
    void negativeValues_areInvertedCorrectly() {
        // [-4,-2,-7] -> [-4,-7,-2]
        Solution.TreeNode root = new Solution.TreeNode(-4, node(-2), node(-7));

        Solution.TreeNode inverted = solution.invertTree(root);

        assertThat(toLevelOrder(inverted)).containsExactly(-4, -7, -2);
    }

    @Test
    void duplicateValues_preservedAfterInversion() {
        // [5,5,5] -> [5,5,5]
        Solution.TreeNode root = new Solution.TreeNode(5, node(5), node(5));

        Solution.TreeNode inverted = solution.invertTree(root);

        assertThat(toLevelOrder(inverted)).containsExactly(5, 5, 5);
    }

    @Test
    void asymmetricTree_isMirroredCorrectly() {
        // 1 has only a left subtree with two levels; right is a single leaf
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), null),
            node(3)
        );

        Solution.TreeNode inverted = solution.invertTree(root);

        assertThat(inverted.val).isEqualTo(1);
        assertThat(inverted.left.val).isEqualTo(3);
        assertThat(inverted.right.val).isEqualTo(2);
        assertThat(inverted.right.left).isNull();
        assertThat(inverted.right.right.val).isEqualTo(4);
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    void maxNodeValue_singleNode() {
        // Node.val upper bound: 100
        Solution.TreeNode inverted = solution.invertTree(node(100));

        assertThat(toLevelOrder(inverted)).containsExactly(100);
    }

    @Test
    void minNodeValue_singleNode() {
        // Node.val lower bound: -100
        Solution.TreeNode inverted = solution.invertTree(node(-100));

        assertThat(toLevelOrder(inverted)).containsExactly(-100);
    }

    @Test
    void hundredNodeSkewedTree_invertsWithoutStackOverflow() {
        // Number of nodes upper bound: 100, chained as a right-skewed tree
        Solution.TreeNode root = node(1);
        Solution.TreeNode current = root;
        for (int i = 2; i <= 100; i++) {
            current.right = node(i);
            current = current.right;
        }

        Solution.TreeNode inverted = solution.invertTree(root);

        assertThat(inverted.val).isEqualTo(1);
        assertThat(inverted.right).isNull();

        Solution.TreeNode walker = inverted.left;
        for (int i = 2; i <= 100; i++) {
            assertThat(walker.val).isEqualTo(i);
            assertThat(walker.right).isNull();
            walker = walker.left;
        }
        assertThat(walker).isNull();
    }
}
