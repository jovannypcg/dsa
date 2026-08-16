package mx.jovannypcg.base.p45_balancedbinarytree;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SolutionTest {

    private final Solution solution = new Solution();

    private Solution.TreeNode node(int val) {
        return new Solution.TreeNode(val);
    }

    // -------------------------------------------------------------------------
    // Happy-path cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("example one: [3,9,20,null,null,15,7] -> balanced")
    void exampleOne_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            node(9),
            new Solution.TreeNode(20, node(15), node(7))
        );

        assertThat(solution.isBalanced(root)).isTrue();
    }

    @Test
    @DisplayName("example two: [1,2,2,3,3,null,null,4,4] -> unbalanced")
    void exampleTwo_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(
                2,
                new Solution.TreeNode(3, node(4), node(4)),
                node(3)
            ),
            node(2)
        );

        assertThat(solution.isBalanced(root)).isFalse();
    }

    @Test
    @DisplayName("root with two children only: [1,2,3] -> balanced")
    void rootWithTwoChildren_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(1, node(2), node(3));

        assertThat(solution.isBalanced(root)).isTrue();
    }

    @Test
    @DisplayName("imbalance deep in a subtree, away from the root -> unbalanced")
    void imbalanceAwayFromRoot_returnsFalse() {
        // 1 -> 2 (only child), 2 -> 3,4, 3 -> 5 (only child), 5 -> 6 (only child)
        // node 2's left subtree has height 3, right subtree has height 0
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(
                2,
                new Solution.TreeNode(
                    3,
                    new Solution.TreeNode(5, node(6), null),
                    null
                ),
                node(4)
            ),
            null
        );

        assertThat(solution.isBalanced(root)).isFalse();
    }

    @Test
    @DisplayName("[1,2,2,3,null,null,3,4,null,null,4]: symmetric imbalance hidden by a balanced root -> unbalanced")
    void symmetricImbalanceHiddenByBalancedRoot_returnsFalse() {
        // Both node-2 children are individually unbalanced (left subtree height 2, right height 0),
        // but the root's own two subtrees both have height 3, so a balance check that only looks
        // at the root (or overwrites earlier findings) is fooled into reporting "balanced".
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, new Solution.TreeNode(3, node(4), null), null),
            new Solution.TreeNode(2, null, new Solution.TreeNode(3, null, node(4)))
        );

        assertThat(solution.isBalanced(root)).isFalse();
    }

    @Test
    @DisplayName("full balanced tree of height 3 -> balanced")
    void fullBalancedTree_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), node(5)),
            new Solution.TreeNode(3, node(6), node(7))
        );

        assertThat(solution.isBalanced(root)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("empty tree -> balanced")
    void emptyTree_returnsTrue() {
        assertThat(solution.isBalanced(null)).isTrue();
    }

    @Test
    @DisplayName("single node -> balanced")
    void singleNode_returnsTrue() {
        assertThat(solution.isBalanced(node(1))).isTrue();
    }

    @Test
    @DisplayName("left-skewed straight line of 4 nodes -> unbalanced")
    void leftSkewedLine_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, new Solution.TreeNode(3, node(4), null), null),
            null
        );

        assertThat(solution.isBalanced(root)).isFalse();
    }

    @Test
    @DisplayName("right-skewed straight line of 3 nodes -> unbalanced")
    void rightSkewedLine_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            null,
            new Solution.TreeNode(2, null, node(3))
        );

        assertThat(solution.isBalanced(root)).isFalse();
    }

    @Test
    @DisplayName("two-node tree with a single child -> balanced")
    void twoNodeTree_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(1, node(2), null);

        assertThat(solution.isBalanced(root)).isTrue();
    }

    @Test
    @DisplayName("negative values do not affect balance calculation")
    void negativeValues_calculatedCorrectly() {
        Solution.TreeNode root = new Solution.TreeNode(
            -1,
            new Solution.TreeNode(-2, node(-4), null),
            node(-3)
        );

        assertThat(solution.isBalanced(root)).isTrue();
    }

    @Test
    @DisplayName("duplicate values across nodes do not affect balance calculation")
    void duplicateValues_calculatedCorrectly() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            new Solution.TreeNode(5, node(5), null),
            node(5)
        );

        assertThat(solution.isBalanced(root)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("max node value boundary: single node with val 10000")
    void maxNodeValue_singleNode() {
        assertThat(solution.isBalanced(node(10000))).isTrue();
    }

    @Test
    @DisplayName("min node value boundary: single node with val -10000")
    void minNodeValue_singleNode() {
        assertThat(solution.isBalanced(node(-10000))).isTrue();
    }

    @Test
    @DisplayName("thousand-node right-skewed tree -> unbalanced, without stack overflow on early short-circuit")
    void thousandNodeSkewedTree_returnsFalse() {
        Solution.TreeNode root = node(1);
        Solution.TreeNode current = root;
        for (int i = 2; i <= 1000; i++) {
            current.right = node(i);
            current = current.right;
        }

        assertThat(solution.isBalanced(root)).isFalse();
    }
}
