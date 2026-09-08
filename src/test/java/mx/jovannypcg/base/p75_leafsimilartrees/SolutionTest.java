package mx.jovannypcg.base.p75_leafsimilartrees;

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
    @DisplayName("example one: matching leaf sequence across differently shaped trees -> true")
    void exampleOne_matchingLeafSequence_returnsTrue() {
        Solution.TreeNode root1 = new Solution.TreeNode(
            3,
            new Solution.TreeNode(5, node(6), new Solution.TreeNode(2, node(7), node(4))),
            new Solution.TreeNode(1, node(9), node(8))
        );
        Solution.TreeNode root2 = new Solution.TreeNode(
            3,
            new Solution.TreeNode(5, node(6), node(7)),
            new Solution.TreeNode(1, node(4), new Solution.TreeNode(2, node(9), node(8)))
        );

        assertThat(solution.leafSimilar(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("example two: same values in different order -> false")
    void exampleTwo_sameValuesDifferentOrder_returnsFalse() {
        Solution.TreeNode root1 = new Solution.TreeNode(1, node(2), node(3));
        Solution.TreeNode root2 = new Solution.TreeNode(1, node(3), node(2));

        assertThat(solution.leafSimilar(root1, root2)).isFalse();
    }

    @Test
    @DisplayName("different shapes, same leaf sequence -> true")
    void differentShapesSameLeafSequence_returnsTrue() {
        Solution.TreeNode root1 = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), node(5)),
            node(3)
        );
        Solution.TreeNode root2 = new Solution.TreeNode(
            10,
            new Solution.TreeNode(20, node(4), node(5)),
            node(3)
        );

        assertThat(solution.leafSimilar(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("same leaf values but different sequence length -> false")
    void sameLeafValuesDifferentLength_returnsFalse() {
        Solution.TreeNode root1 = new Solution.TreeNode(1, node(2), node(2));
        Solution.TreeNode root2 = new Solution.TreeNode(
            1,
            node(2),
            new Solution.TreeNode(2, node(2), node(2))
        );

        assertThat(solution.leafSimilar(root1, root2)).isFalse();
    }

    @Test
    @DisplayName("duplicate leaf values across very different depths -> true")
    void duplicateLeafValuesDifferentDepths_returnsTrue() {
        Solution.TreeNode root1 = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(4), node(4)),
            new Solution.TreeNode(3, node(4), node(4))
        );
        Solution.TreeNode root2 = new Solution.TreeNode(
            5,
            node(4),
            new Solution.TreeNode(
                6,
                node(4),
                new Solution.TreeNode(7, node(4), node(4))
            )
        );

        assertThat(solution.leafSimilar(root1, root2)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("both single-node trees with the same value -> true")
    void singleNodeTreesSameValue_returnsTrue() {
        assertThat(solution.leafSimilar(node(5), node(5))).isTrue();
    }

    @Test
    @DisplayName("both single-node trees with different values -> false")
    void singleNodeTreesDifferentValue_returnsFalse() {
        assertThat(solution.leafSimilar(node(3), node(7))).isFalse();
    }

    @Test
    @DisplayName("left-skewed vs right-skewed tree sharing one leaf value -> true")
    void leftSkewedVsRightSkewed_sharesLeafValue_returnsTrue() {
        Solution.TreeNode root1 = new Solution.TreeNode(1, new Solution.TreeNode(2, node(9), null), null);
        Solution.TreeNode root2 = new Solution.TreeNode(1, null, new Solution.TreeNode(2, null, node(9)));

        assertThat(solution.leafSimilar(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("leaf sequences differ only in the last element -> false")
    void leafSequencesDifferInLastElement_returnsFalse() {
        Solution.TreeNode root1 = new Solution.TreeNode(1, node(2), node(3));
        Solution.TreeNode root2 = new Solution.TreeNode(1, node(2), node(4));

        assertThat(solution.leafSimilar(root1, root2)).isFalse();
    }

    @Test
    @DisplayName("one tree's leaf sequence is a prefix of the other's -> false")
    void leafSequenceIsPrefixOfOther_returnsFalse() {
        Solution.TreeNode root1 = new Solution.TreeNode(1, node(2), node(3));
        Solution.TreeNode root2 = new Solution.TreeNode(
            1,
            node(2),
            new Solution.TreeNode(3, node(4), null)
        );

        assertThat(solution.leafSimilar(root1, root2)).isFalse();
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("boundary value 0: both single-node trees with the minimum value -> true")
    void minNodeValueBoundary_singleNodeTrees_returnsTrue() {
        assertThat(solution.leafSimilar(node(0), node(0))).isTrue();
    }

    @Test
    @DisplayName("boundary value 200: both single-node trees with the maximum value -> true")
    void maxNodeValueBoundary_singleNodeTrees_returnsTrue() {
        assertThat(solution.leafSimilar(node(200), node(200))).isTrue();
    }

    @Test
    @DisplayName("boundary values across a matching leaf sequence: 0 and 200 in the same order -> true")
    void boundaryValuesAcrossLeafSequence_returnsTrue() {
        Solution.TreeNode root1 = new Solution.TreeNode(1, node(0), node(200));
        Solution.TreeNode root2 = new Solution.TreeNode(2, node(0), node(200));

        assertThat(solution.leafSimilar(root1, root2)).isTrue();
    }
}
