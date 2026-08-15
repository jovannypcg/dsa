package mx.jovannypcg.base.p44_diameterofbinarytree;

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
    @DisplayName("example one: [1,2,3,4,5] -> diameter 3")
    void exampleOne_returnsThree() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), node(5)),
            node(3)
        );

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("example two: [1,2] -> diameter 1")
    void exampleTwo_returnsOne() {
        Solution.TreeNode root = new Solution.TreeNode(1, node(2), null);

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(1);
    }

    @Test
    @DisplayName("root with two children only: [1,2,3] -> diameter 2")
    void rootWithTwoChildren_returnsTwo() {
        Solution.TreeNode root = new Solution.TreeNode(1, node(2), node(3));

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(2);
    }

    @Test
    @DisplayName("diameter does not pass through the root")
    void diameterAvoidsRoot_returnsLongestSubtreePath() {
        // 1 -> 2 (only child), 2 -> 3,4, 3 -> 5,6, 4 -> 7,8
        // longest path: 5 -> 3 -> 2 -> 4 -> 7, 4 edges, never touching node 1
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(
                2,
                new Solution.TreeNode(3, node(5), node(6)),
                new Solution.TreeNode(4, node(7), node(8))
            ),
            null
        );

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(4);
    }

    @Test
    @DisplayName("irregular tree combining skewed trunk and bushy branch -> diameter 5")
    void irregularTree_returnsFive() {
        // 1 -> 2, 2 -> 3,4, 3 -> 9, 4 -> 5,6, 5 -> 7,8
        // longest path: 9 -> 3 -> 2 -> 4 -> 5 -> 7, 5 edges
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            null,
            new Solution.TreeNode(
                2,
                new Solution.TreeNode(3, node(9), null),
                new Solution.TreeNode(
                    4,
                    new Solution.TreeNode(5, node(7), node(8)),
                    node(6)
                )
            )
        );

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(5);
    }

    @Test
    @DisplayName("reusing the same solution instance does not leak state between calls")
    void reusedInstance_doesNotLeakStateBetweenCalls() {
        Solution.TreeNode largeDiameterTree = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), node(5)),
            node(3)
        );
        Solution.TreeNode smallDiameterTree = new Solution.TreeNode(1, node(2), null);

        solution.diameterOfBinaryTree(largeDiameterTree);

        assertThat(solution.diameterOfBinaryTree(smallDiameterTree)).isEqualTo(1);
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("single node -> diameter 0")
    void singleNode_returnsZero() {
        assertThat(solution.diameterOfBinaryTree(node(1))).isEqualTo(0);
    }

    @Test
    @DisplayName("left-skewed straight line -> diameter equals edge count")
    void leftSkewedLine_returnsEdgeCount() {
        // 1 -> 2 -> 3 -> 4, all left children
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, new Solution.TreeNode(3, node(4), null), null),
            null
        );

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("right-skewed straight line -> diameter equals edge count")
    void rightSkewedLine_returnsEdgeCount() {
        // 1 -> 2 -> 3 -> 4, all right children
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            null,
            new Solution.TreeNode(2, null, new Solution.TreeNode(3, null, node(4)))
        );

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("negative values do not affect diameter calculation")
    void negativeValues_calculatedCorrectly() {
        Solution.TreeNode root = new Solution.TreeNode(
            -1,
            new Solution.TreeNode(-2, node(-4), null),
            node(-3)
        );

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(3);
    }

    @Test
    @DisplayName("duplicate values across nodes do not affect diameter calculation")
    void duplicateValues_calculatedCorrectly() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            new Solution.TreeNode(5, node(5), null),
            node(5)
        );

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(3);
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("max node value boundary: single node with val 100")
    void maxNodeValue_singleNode() {
        assertThat(solution.diameterOfBinaryTree(node(100))).isEqualTo(0);
    }

    @Test
    @DisplayName("min node value boundary: single node with val -100")
    void minNodeValue_singleNode() {
        assertThat(solution.diameterOfBinaryTree(node(-100))).isEqualTo(0);
    }

    @Test
    @DisplayName("hundred-node right-skewed tree -> diameter equals node count minus one, without stack overflow")
    void hundredNodeSkewedTree_returnsEdgeCount() {
        Solution.TreeNode root = node(1);
        Solution.TreeNode current = root;
        for (int i = 2; i <= 100; i++) {
            current.right = node(i);
            current = current.right;
        }

        assertThat(solution.diameterOfBinaryTree(root)).isEqualTo(99);
    }
}
