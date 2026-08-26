package mx.jovannypcg.base.p63_countgoodnodesinbinarytree;

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
    @DisplayName("example one: [3,1,4,3,null,1,5] -> 4 good nodes")
    void exampleOne_returnsFour() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(1, null, node(3)),
            new Solution.TreeNode(4, node(1), node(5))
        );

        assertThat(solution.goodNodes(root)).isEqualTo(4);
    }

    @Test
    @DisplayName("example two: [3,3,null,4,2] -> 3 good nodes")
    void exampleTwo_returnsThree() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(3, node(4), node(2)),
            null
        );

        assertThat(solution.goodNodes(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("strictly increasing path: [1,2,3,4] -> every node is good")
    void strictlyIncreasingPath_returnsAllNodes() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), null),
            node(3)
        );

        assertThat(solution.goodNodes(root)).isEqualTo(4);
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("single node -> root is always good")
    void singleNode_returnsOne() {
        assertThat(solution.goodNodes(node(1))).isEqualTo(1);
    }

    @Test
    @DisplayName("all equal values -> every node is good since ties are not greater")
    void allEqualValues_returnsAllNodes() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            new Solution.TreeNode(5, node(5), node(5)),
            node(5)
        );

        assertThat(solution.goodNodes(root)).isEqualTo(5);
    }

    @Test
    @DisplayName("strictly decreasing left-skewed path -> only the root is good")
    void strictlyDecreasingLeftSkewed_returnsOne() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            new Solution.TreeNode(4, new Solution.TreeNode(3, new Solution.TreeNode(2, node(1), null), null), null),
            null
        );

        assertThat(solution.goodNodes(root)).isEqualTo(1);
    }

    @Test
    @DisplayName("strictly decreasing right-skewed path -> only the root is good")
    void strictlyDecreasingRightSkewed_returnsOne() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            null,
            new Solution.TreeNode(4, null, new Solution.TreeNode(3, null, new Solution.TreeNode(2, null, node(1))))
        );

        assertThat(solution.goodNodes(root)).isEqualTo(1);
    }

    @Test
    @DisplayName("negative values: only nodes exceeding the running maximum are good")
    void negativeValues_returnsExpectedCount() {
        // root -10 (good) -> left -5 (good, -5 > -10) -> left.left -3 (good, -3 > -5)
        // root -10 -> right -20 (not good, -20 < -10)
        Solution.TreeNode root = new Solution.TreeNode(
            -10,
            new Solution.TreeNode(-5, node(-3), null),
            node(-20)
        );

        assertThat(solution.goodNodes(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("mixed negative and positive values across branches")
    void mixedNegativeAndPositiveValues_returnsExpectedCount() {
        // root -3 -> left -5 (not good, -5 < -3) -> right 2 (good, 2 > -3)
        Solution.TreeNode root = new Solution.TreeNode(-3, node(-5), node(2));

        assertThat(solution.goodNodes(root)).isEqualTo(2);
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("max node value boundary: single node with val 10000")
    void maxNodeValue_singleNode() {
        assertThat(solution.goodNodes(node(10_000))).isEqualTo(1);
    }

    @Test
    @DisplayName("min node value boundary: single node with val -10000")
    void minNodeValue_singleNode() {
        assertThat(solution.goodNodes(node(-10_000))).isEqualTo(1);
    }

    @Test
    @DisplayName("boundary values across path: root at min, child at max -> both good")
    void boundaryValuesAcrossPath_bothGood() {
        Solution.TreeNode root = new Solution.TreeNode(-10_000, node(10_000), null);

        assertThat(solution.goodNodes(root)).isEqualTo(2);
    }

    @Test
    @DisplayName("hundred-node right-skewed strictly increasing tree -> all nodes good without stack overflow")
    void hundredNodeSkewedIncreasingTree_returnsNodeCount() {
        Solution.TreeNode root = node(1);
        Solution.TreeNode current = root;
        for (int i = 2; i <= 100; i++) {
            current.right = node(i);
            current = current.right;
        }

        assertThat(solution.goodNodes(root)).isEqualTo(100);
    }
}
