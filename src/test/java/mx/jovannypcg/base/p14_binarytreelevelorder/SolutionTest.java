package mx.jovannypcg.base.p14_binarytreelevelorder;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    private Solution.TreeNode node(int val) {
        return new Solution.TreeNode(val);
    }

    private Solution.TreeNode node(int val, Solution.TreeNode left, Solution.TreeNode right) {
        return new Solution.TreeNode(val, left, right);
    }

    @Test
    @DisplayName("null root → empty list")
    void nullRoot() {
        assertThat(solution.levelOrder(null)).isEmpty();
    }

    @Test
    @DisplayName("single node → one level with that node's value")
    void singleNode() {
        assertThat(solution.levelOrder(node(1)))
                .isEqualTo(List.of(List.of(1)));
    }

    @Test
    @DisplayName("three-level tree [3,9,20,null,null,15,7] → [[3],[9,20],[15,7]]")
    void threeLevelTree() {
        Solution.TreeNode root = node(3,
                node(9),
                node(20, node(15), node(7)));

        assertThat(solution.levelOrder(root))
                .isEqualTo(List.of(
                        List.of(3),
                        List.of(9, 20),
                        List.of(15, 7)));
    }

    @Test
    @DisplayName("left-skewed tree → each level contains one node")
    void leftSkewedTree() {
        Solution.TreeNode root = node(1, node(2, node(3), null), null);

        assertThat(solution.levelOrder(root))
                .isEqualTo(List.of(
                        List.of(1),
                        List.of(2),
                        List.of(3)));
    }

    @Test
    @DisplayName("right-skewed tree → each level contains one node")
    void rightSkewedTree() {
        Solution.TreeNode root = node(1, null, node(2, null, node(3)));

        assertThat(solution.levelOrder(root))
                .isEqualTo(List.of(
                        List.of(1),
                        List.of(2),
                        List.of(3)));
    }

    @Test
    @DisplayName("complete binary tree of depth 2 → three levels")
    void completeBinaryTree() {
        Solution.TreeNode root = node(1,
                node(2, node(4), node(5)),
                node(3, node(6), node(7)));

        assertThat(solution.levelOrder(root))
                .isEqualTo(List.of(
                        List.of(1),
                        List.of(2, 3),
                        List.of(4, 5, 6, 7)));
    }

    @Test
    @DisplayName("tree with negative values → values preserved in level order")
    void negativeValues() {
        Solution.TreeNode root = node(-1000,
                node(-500),
                node(1000));

        assertThat(solution.levelOrder(root))
                .isEqualTo(List.of(
                        List.of(-1000),
                        List.of(-500, 1000)));
    }

    @Test
    @DisplayName("root with only left child → two levels")
    void onlyLeftChild() {
        Solution.TreeNode root = node(5, node(3), null);

        assertThat(solution.levelOrder(root))
                .isEqualTo(List.of(
                        List.of(5),
                        List.of(3)));
    }

    @Test
    @DisplayName("root with only right child → two levels")
    void onlyRightChild() {
        Solution.TreeNode root = node(5, null, node(8));

        assertThat(solution.levelOrder(root))
                .isEqualTo(List.of(
                        List.of(5),
                        List.of(8)));
    }

    @Test
    @DisplayName("tree with duplicate values → duplicates appear in correct order")
    void duplicateValues() {
        Solution.TreeNode root = node(1,
                node(1, node(1), node(1)),
                node(1));

        assertThat(solution.levelOrder(root))
                .isEqualTo(List.of(
                        List.of(1),
                        List.of(1, 1),
                        List.of(1, 1)));
    }
}
