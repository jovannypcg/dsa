package mx.jovannypcg.base.p46_sametree;

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
    @DisplayName("example one: p=[1,2,3], q=[1,2,3] -> same tree")
    void exampleOne_returnsTrue() {
        Solution.TreeNode p = new Solution.TreeNode(1, node(2), node(3));
        Solution.TreeNode q = new Solution.TreeNode(1, node(2), node(3));

        assertThat(solution.isSameTree(p, q)).isTrue();
    }

    @Test
    @DisplayName("example two: p=[1,2], q=[1,null,2] -> different structure")
    void exampleTwo_returnsFalse() {
        Solution.TreeNode p = new Solution.TreeNode(1, node(2), null);
        Solution.TreeNode q = new Solution.TreeNode(1, null, node(2));

        assertThat(solution.isSameTree(p, q)).isFalse();
    }

    @Test
    @DisplayName("example three: p=[1,2,1], q=[1,1,2] -> same structure, mismatched values")
    void exampleThree_returnsFalse() {
        Solution.TreeNode p = new Solution.TreeNode(1, node(2), node(1));
        Solution.TreeNode q = new Solution.TreeNode(1, node(1), node(2));

        assertThat(solution.isSameTree(p, q)).isFalse();
    }

    @Test
    @DisplayName("deep identical trees of height 3 -> same tree")
    void deepIdenticalTrees_returnsTrue() {
        Solution.TreeNode p = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), node(5)),
            new Solution.TreeNode(3, node(6), node(7))
        );
        Solution.TreeNode q = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), node(5)),
            new Solution.TreeNode(3, node(6), node(7))
        );

        assertThat(solution.isSameTree(p, q)).isTrue();
    }

    @Test
    @DisplayName("mismatch hidden deep in a subtree, away from the root -> different tree")
    void mismatchAwayFromRoot_returnsFalse() {
        Solution.TreeNode p = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, new Solution.TreeNode(3, node(4), null), null),
            null
        );
        Solution.TreeNode q = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, new Solution.TreeNode(3, node(9), null), null),
            null
        );

        assertThat(solution.isSameTree(p, q)).isFalse();
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("both trees empty -> same tree")
    void bothEmpty_returnsTrue() {
        assertThat(solution.isSameTree(null, null)).isTrue();
    }

    @Test
    @DisplayName("p has nodes, q is empty -> different tree")
    void pNonEmptyQEmpty_returnsFalse() {
        Solution.TreeNode p = new Solution.TreeNode(1, node(2), node(3));

        assertThat(solution.isSameTree(p, null)).isFalse();
    }

    @Test
    @DisplayName("p is empty, q has nodes -> different tree")
    void pEmptyQNonEmpty_returnsFalse() {
        Solution.TreeNode q = new Solution.TreeNode(1, node(2), node(3));

        assertThat(solution.isSameTree(null, q)).isFalse();
    }

    @Test
    @DisplayName("single matching node in each tree -> same tree")
    void singleMatchingNode_returnsTrue() {
        assertThat(solution.isSameTree(node(1), node(1))).isTrue();
    }

    @Test
    @DisplayName("single node with different values -> different tree")
    void singleNodeDifferentValues_returnsFalse() {
        assertThat(solution.isSameTree(node(1), node(2))).isFalse();
    }

    @Test
    @DisplayName("negative values compared correctly")
    void negativeValues_calculatedCorrectly() {
        Solution.TreeNode p = new Solution.TreeNode(-1, node(-2), node(-3));
        Solution.TreeNode q = new Solution.TreeNode(-1, node(-2), node(-3));

        assertThat(solution.isSameTree(p, q)).isTrue();
    }

    @Test
    @DisplayName("duplicate values across nodes in matching structure -> same tree")
    void duplicateValues_calculatedCorrectly() {
        Solution.TreeNode p = new Solution.TreeNode(5, node(5), node(5));
        Solution.TreeNode q = new Solution.TreeNode(5, node(5), node(5));

        assertThat(solution.isSameTree(p, q)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("max node value boundary: single node with val 10000 in both trees")
    void maxNodeValue_singleNode() {
        assertThat(solution.isSameTree(node(10000), node(10000))).isTrue();
    }

    @Test
    @DisplayName("min node value boundary: single node with val -10000 in both trees")
    void minNodeValue_singleNode() {
        assertThat(solution.isSameTree(node(-10000), node(-10000))).isTrue();
    }

    @Test
    @DisplayName("value boundary mismatch: max value vs min value -> different tree")
    void maxVsMinValue_returnsFalse() {
        assertThat(solution.isSameTree(node(10000), node(-10000))).isFalse();
    }

    @Test
    @DisplayName("hundred-node identical right-skewed trees -> same tree, without stack overflow")
    void hundredNodeSkewedTrees_returnsTrue() {
        Solution.TreeNode p = node(1);
        Solution.TreeNode pCurrent = p;
        Solution.TreeNode q = node(1);
        Solution.TreeNode qCurrent = q;

        for (int i = 2; i <= 100; i++) {
            pCurrent.right = node(i);
            pCurrent = pCurrent.right;
            qCurrent.right = node(i);
            qCurrent = qCurrent.right;
        }

        assertThat(solution.isSameTree(p, q)).isTrue();
    }
}
