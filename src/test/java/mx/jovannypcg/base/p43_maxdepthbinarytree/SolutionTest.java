package mx.jovannypcg.base.p43_maxdepthbinarytree;

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
    @DisplayName("example one: [3,9,20,null,null,15,7] -> depth 3")
    void exampleOne_returnsThree() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            node(9),
            new Solution.TreeNode(20, node(15), node(7))
        );

        assertThat(solution.maxDepth(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("example two: [1,null,2] -> depth 2")
    void exampleTwo_returnsTwo() {
        Solution.TreeNode root = new Solution.TreeNode(1, null, node(2));

        assertThat(solution.maxDepth(root)).isEqualTo(2);
    }

    @Test
    @DisplayName("unbalanced tree: longer path through left subtree is chosen")
    void unbalancedTree_returnsLongestPath() {
        // [1,2,3,4,null,null,null,5] -> 1 -> 2 -> 4 -> 5, depth 4
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, new Solution.TreeNode(4, node(5), null), null),
            node(3)
        );

        assertThat(solution.maxDepth(root)).isEqualTo(4);
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("empty tree -> depth 0")
    void emptyTree_returnsZero() {
        assertThat(solution.maxDepth(null)).isEqualTo(0);
    }

    @Test
    @DisplayName("single node -> depth 1")
    void singleNode_returnsOne() {
        assertThat(solution.maxDepth(node(1))).isEqualTo(1);
    }

    @Test
    @DisplayName("left-skewed tree -> depth equals number of nodes")
    void leftSkewedTree_returnsNodeCount() {
        // 3 -> 2 -> 1, all left children
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(2, node(1), null),
            null
        );

        assertThat(solution.maxDepth(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("right-skewed tree -> depth equals number of nodes")
    void rightSkewedTree_returnsNodeCount() {
        // 1 -> 2 -> 3, all right children
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            null,
            new Solution.TreeNode(2, null, node(3))
        );

        assertThat(solution.maxDepth(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("negative values do not affect depth calculation")
    void negativeValues_calculatedCorrectly() {
        Solution.TreeNode root = new Solution.TreeNode(-4, node(-2), node(-7));

        assertThat(solution.maxDepth(root)).isEqualTo(2);
    }

    @Test
    @DisplayName("duplicate values across nodes do not affect depth calculation")
    void duplicateValues_calculatedCorrectly() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            new Solution.TreeNode(5, node(5), null),
            node(5)
        );

        assertThat(solution.maxDepth(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("asymmetric tree: shallow right subtree does not shorten the result")
    void asymmetricTree_returnsLongestSide() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, new Solution.TreeNode(4, node(6), null), null),
            node(3)
        );

        assertThat(solution.maxDepth(root)).isEqualTo(4);
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("max node value boundary: single node with val 100")
    void maxNodeValue_singleNode() {
        assertThat(solution.maxDepth(node(100))).isEqualTo(1);
    }

    @Test
    @DisplayName("min node value boundary: single node with val -100")
    void minNodeValue_singleNode() {
        assertThat(solution.maxDepth(node(-100))).isEqualTo(1);
    }

    @Test
    @DisplayName("hundred-node right-skewed tree -> depth equals node count without stack overflow")
    void hundredNodeSkewedTree_returnsNodeCount() {
        Solution.TreeNode root = node(1);
        Solution.TreeNode current = root;
        for (int i = 2; i <= 100; i++) {
            current.right = node(i);
            current = current.right;
        }

        assertThat(solution.maxDepth(root)).isEqualTo(100);
    }
}
