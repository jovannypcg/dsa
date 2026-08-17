package mx.jovannypcg.base.p49_binarytreerightsideview;

import mx.jovannypcg.base.p49_binarytreerightsideview.Solution.TreeNode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    @DisplayName("empty tree → empty list")
    void emptyTree() {
        Solution solution = new Solution();

        List<Integer> result = solution.rightSideView(null);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("single node → that node")
    void singleNode() {
        Solution solution = new Solution();
        TreeNode root = new TreeNode(1);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(1);
    }

    @Test
    @DisplayName("classic example: right child hangs off left subtree → [1,3,4]")
    void classicExample() {
        Solution solution = new Solution();
        TreeNode five = new TreeNode(5);
        TreeNode four = new TreeNode(4);
        TreeNode two = new TreeNode(2, null, five);
        TreeNode three = new TreeNode(3, null, four);
        TreeNode root = new TreeNode(1, two, three);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(1, 3, 4);
    }

    @Test
    @DisplayName("left-only skewed tree → every node visible")
    void leftSkewedTree() {
        Solution solution = new Solution();
        TreeNode three = new TreeNode(3);
        TreeNode two = new TreeNode(2, three, null);
        TreeNode root = new TreeNode(1, two, null);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    @DisplayName("right-only skewed tree → every node visible")
    void rightSkewedTree() {
        Solution solution = new Solution();
        TreeNode three = new TreeNode(3);
        TreeNode two = new TreeNode(2, null, three);
        TreeNode root = new TreeNode(1, null, two);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(1, 2, 3);
    }

    @Test
    @DisplayName("deep left subtree with empty right subtree → left descendants become visible")
    void deepLeftSubtreeBecomesVisible() {
        Solution solution = new Solution();
        TreeNode five = new TreeNode(5);
        TreeNode four = new TreeNode(4, five, null);
        TreeNode two = new TreeNode(2, four, null);
        TreeNode three = new TreeNode(3);
        TreeNode root = new TreeNode(1, two, three);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(1, 3, 4, 5);
    }

    @Test
    @DisplayName("perfect binary tree with three levels → rightmost path")
    void perfectBinaryTree() {
        Solution solution = new Solution();
        TreeNode four = new TreeNode(4);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);
        TreeNode two = new TreeNode(2, four, five);
        TreeNode three = new TreeNode(3, six, seven);
        TreeNode root = new TreeNode(1, two, three);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(1, 3, 7);
    }

    @Test
    @DisplayName("negative and boundary values within [-100, 100] are preserved")
    void boundaryNodeValues() {
        Solution solution = new Solution();
        TreeNode left = new TreeNode(-100);
        TreeNode right = new TreeNode(100);
        TreeNode root = new TreeNode(0, left, right);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(0, 100);
    }

    @Test
    @DisplayName("tree where a deep left-only chain becomes visible past a shallower right leaf")
    void lastLevelOnlyLeftChild() {
        Solution solution = new Solution();
        TreeNode nine = new TreeNode(9);
        TreeNode eight = new TreeNode(8, nine, null);
        TreeNode six = new TreeNode(6, eight, null);
        TreeNode two = new TreeNode(2, six, null);
        TreeNode three = new TreeNode(3);
        TreeNode root = new TreeNode(1, two, three);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(1, 3, 6, 8, 9);
    }

    @Test
    @DisplayName("perfect binary tree with an extra deep left leaf → deep leaf stays visible")
    void perfectBinaryTreeWithDeepLeftLeaf() {
        Solution solution = new Solution();
        TreeNode eight = new TreeNode(8);
        TreeNode four = new TreeNode(4, eight, null);
        TreeNode five = new TreeNode(5);
        TreeNode six = new TreeNode(6);
        TreeNode seven = new TreeNode(7);
        TreeNode two = new TreeNode(2, four, five);
        TreeNode three = new TreeNode(3, six, seven);
        TreeNode root = new TreeNode(1, two, three);

        List<Integer> result = solution.rightSideView(root);

        assertThat(result).containsExactly(1, 3, 7, 8);
    }
}
