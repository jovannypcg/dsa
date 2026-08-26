package mx.jovannypcg.base.p64_validatebinarysearchtree;

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
    @DisplayName("example one: [2,1,3] -> valid BST")
    void exampleOne_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(2, node(1), node(3));

        assertThat(solution.isValidBST(root)).isTrue();
    }

    @Test
    @DisplayName("example two: [5,1,4,null,null,3,6] -> invalid, right subtree has value less than root")
    void exampleTwo_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            node(1),
            new Solution.TreeNode(4, node(3), node(6))
        );

        assertThat(solution.isValidBST(root)).isFalse();
    }

    @Test
    @DisplayName("larger valid BST: [3,1,5,0,2,4,6] -> true")
    void largerValidBst_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(1, node(0), node(2)),
            new Solution.TreeNode(5, node(4), node(6))
        );

        assertThat(solution.isValidBST(root)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("single node -> trivially a valid BST")
    void singleNode_returnsTrue() {
        assertThat(solution.isValidBST(node(1))).isTrue();
    }

    @Test
    @DisplayName("duplicate values: [1,1] -> false, equal values are not allowed")
    void duplicateValues_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(1, node(1), null);

        assertThat(solution.isValidBST(root)).isFalse();
    }

    @Test
    @DisplayName("violation against ancestor, not immediate parent: [5,4,6,null,null,3,7] -> false")
    void violationAgainstAncestor_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            node(4),
            new Solution.TreeNode(6, node(3), node(7))
        );

        assertThat(solution.isValidBST(root)).isFalse();
    }

    @Test
    @DisplayName("strictly increasing right-skewed path -> valid BST")
    void strictlyIncreasingRightSkewed_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            null,
            new Solution.TreeNode(2, null, new Solution.TreeNode(3, null, node(4)))
        );

        assertThat(solution.isValidBST(root)).isTrue();
    }

    @Test
    @DisplayName("strictly decreasing left-skewed path -> valid BST")
    void strictlyDecreasingLeftSkewed_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(
            4,
            new Solution.TreeNode(3, new Solution.TreeNode(2, node(1), null), null),
            null
        );

        assertThat(solution.isValidBST(root)).isTrue();
    }

    @Test
    @DisplayName("negative values: [-5,-10,-1] -> valid BST")
    void negativeValues_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(-5, node(-10), node(-1));

        assertThat(solution.isValidBST(root)).isTrue();
    }

    @Test
    @DisplayName("left child greater than root -> false")
    void leftChildGreaterThanRoot_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(2, node(3), node(5));

        assertThat(solution.isValidBST(root)).isFalse();
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("single node at Integer.MAX_VALUE -> true, guards against naive MAX_VALUE sentinel bugs")
    void singleNodeAtMaxValue_returnsTrue() {
        assertThat(solution.isValidBST(node(Integer.MAX_VALUE))).isTrue();
    }

    @Test
    @DisplayName("root at MAX_VALUE with any right child -> false, guards against overflow when narrowing bounds with root.val + 1")
    void rootAtMaxValueWithRightChild_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(Integer.MAX_VALUE, null, node(Integer.MIN_VALUE));

        assertThat(solution.isValidBST(root)).isFalse();
    }

    @Test
    @DisplayName("root at MIN_VALUE with any left child -> false, guards against overflow when narrowing bounds with root.val - 1")
    void rootAtMinValueWithLeftChild_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(Integer.MIN_VALUE, node(Integer.MAX_VALUE), null);

        assertThat(solution.isValidBST(root)).isFalse();
    }

    @Test
    @DisplayName("single node at Integer.MIN_VALUE -> true, guards against naive MIN_VALUE sentinel bugs")
    void singleNodeAtMinValue_returnsTrue() {
        assertThat(solution.isValidBST(node(Integer.MIN_VALUE))).isTrue();
    }

    @Test
    @DisplayName("root at MIN_VALUE with right child at MAX_VALUE -> true")
    void minAndMaxValueBoundary_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(Integer.MIN_VALUE, null, node(Integer.MAX_VALUE));

        assertThat(solution.isValidBST(root)).isTrue();
    }

    @Test
    @DisplayName("ten-thousand-node right-skewed strictly increasing tree -> valid without stack overflow")
    void tenThousandNodeSkewedIncreasingTree_returnsTrue() {
        Solution.TreeNode root = node(1);
        Solution.TreeNode current = root;
        for (int i = 2; i <= 10_000; i++) {
            current.right = node(i);
            current = current.right;
        }

        assertThat(solution.isValidBST(root)).isTrue();
    }
}
