package mx.jovannypcg.base.p65_kthsmallestelementinabst;

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
    @DisplayName("example one: [3,1,4,null,2], k=1 -> 1")
    void exampleOne_returnsSmallest() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(1, null, node(2)),
            node(4)
        );

        assertThat(solution.kthSmallest(root, 1)).isEqualTo(1);
    }

    @Test
    @DisplayName("example two: [5,3,6,2,4,null,null,1], k=3 -> 3")
    void exampleTwo_returnsThirdSmallest() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            new Solution.TreeNode(3, new Solution.TreeNode(2, node(1), null), node(4)),
            node(6)
        );

        assertThat(solution.kthSmallest(root, 3)).isEqualTo(3);
    }

    @Test
    @DisplayName("larger balanced tree: [8,3,10,1,6,9,14,null,null,4,7,null,null,13], k=6 -> 8")
    void largerBalancedTree_returnsSixthSmallest() {
        Solution.TreeNode left = new Solution.TreeNode(
            3,
            node(1),
            new Solution.TreeNode(6, node(4), node(7))
        );
        Solution.TreeNode right = new Solution.TreeNode(
            10,
            node(9),
            new Solution.TreeNode(14, node(13), null)
        );
        Solution.TreeNode root = new Solution.TreeNode(8, left, right);

        assertThat(solution.kthSmallest(root, 6)).isEqualTo(8);
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("single node: k=1 -> that node's value")
    void singleNode_returnsItsValue() {
        assertThat(solution.kthSmallest(node(1), 1)).isEqualTo(1);
    }

    @Test
    @DisplayName("k equals number of nodes -> returns the maximum value")
    void kEqualsNodeCount_returnsMaximum() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(1, null, node(2)),
            node(4)
        );

        assertThat(solution.kthSmallest(root, 4)).isEqualTo(4);
    }

    @Test
    @DisplayName("left-skewed tree: [5,4,null,3,null,2,null,1], k=4 -> 4")
    void leftSkewedTree_returnsFourthSmallest() {
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            new Solution.TreeNode(
                4,
                new Solution.TreeNode(
                    3,
                    new Solution.TreeNode(2, node(1), null),
                    null
                ),
                null
            ),
            null
        );

        assertThat(solution.kthSmallest(root, 4)).isEqualTo(4);
    }

    @Test
    @DisplayName("right-skewed tree: [1,null,2,null,3,null,4,null,5], k=5 -> 5")
    void rightSkewedTree_returnsFifthSmallest() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            null,
            new Solution.TreeNode(
                2,
                null,
                new Solution.TreeNode(
                    3,
                    null,
                    new Solution.TreeNode(4, null, node(5))
                )
            )
        );

        assertThat(solution.kthSmallest(root, 5)).isEqualTo(5);
    }

    @Test
    @DisplayName("k=1 on a right-skewed tree -> returns the root, which is the minimum")
    void kOneOnRightSkewedTree_returnsRoot() {
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            null,
            new Solution.TreeNode(2, null, node(3))
        );

        assertThat(solution.kthSmallest(root, 1)).isEqualTo(1);
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("node value at lower bound 0 -> found correctly")
    void nodeValueAtLowerBound_returnsZero() {
        Solution.TreeNode root = new Solution.TreeNode(1, node(0), null);

        assertThat(solution.kthSmallest(root, 1)).isEqualTo(0);
    }

    @Test
    @DisplayName("node value at upper bound 10^4 -> found correctly")
    void nodeValueAtUpperBound_returnsTenThousand() {
        Solution.TreeNode root = new Solution.TreeNode(5000, null, node(10_000));

        assertThat(solution.kthSmallest(root, 2)).isEqualTo(10_000);
    }

    @Test
    @DisplayName("ten-thousand-node right-skewed tree, k = n -> returns the largest value without stack overflow")
    void tenThousandNodeSkewedTree_kEqualsN_returnsLargest() {
        Solution.TreeNode root = node(1);
        Solution.TreeNode current = root;
        for (int i = 2; i <= 10_000; i++) {
            current.right = node(i);
            current = current.right;
        }

        assertThat(solution.kthSmallest(root, 10_000)).isEqualTo(10_000);
    }

    @Test
    @DisplayName("ten-thousand-node right-skewed tree, k = 1 -> returns the smallest value without stack overflow")
    void tenThousandNodeSkewedTree_kOne_returnsSmallest() {
        Solution.TreeNode root = node(1);
        Solution.TreeNode current = root;
        for (int i = 2; i <= 10_000; i++) {
            current.right = node(i);
            current = current.right;
        }

        assertThat(solution.kthSmallest(root, 1)).isEqualTo(1);
    }
}
