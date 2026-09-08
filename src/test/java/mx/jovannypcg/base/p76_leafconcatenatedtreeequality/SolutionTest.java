package mx.jovannypcg.base.p76_leafconcatenatedtreeequality;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SolutionTest {

    private final Solution solution = new Solution();

    private Solution.TreeNode leaf(String val) {
        return new Solution.TreeNode(val);
    }

    private Solution.TreeNode node(String val, Solution.TreeNode left, Solution.TreeNode right) {
        return new Solution.TreeNode(val, left, right);
    }

    // -------------------------------------------------------------------------
    // README examples
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("example one: different partitioning of the same string -> true")
    void exampleOne_differentPartitioningSameString_returnsTrue() {
        Solution.TreeNode root1 = node("1", leaf("ab"), leaf("c"));
        Solution.TreeNode root2 = node("1", leaf("a"), leaf("bc"));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("example two: mismatched characters -> false")
    void exampleTwo_mismatchedCharacters_returnsFalse() {
        Solution.TreeNode root1 = node("1", leaf("abc"), leaf(""));
        Solution.TreeNode root2 = node("1", leaf("ab"), leaf("d"));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isFalse();
    }

    @Test
    @DisplayName("example three: single-leaf tree vs multi-leaf tree with same concatenation -> true")
    void exampleThree_singleLeafVsMultiLeafSameString_returnsTrue() {
        Solution.TreeNode root1 = leaf("x");
        Solution.TreeNode root2 = node("1", leaf("x"), leaf(""));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("example four: imperfect, differently shaped trees with same concatenation -> true")
    void exampleFour_imperfectShapesSameConcatenation_returnsTrue() {
        Solution.TreeNode root1 = node("1", leaf("ca"), node("2", leaf("t"), leaf("")));
        Solution.TreeNode root2 = node("1", node("2", leaf("c"), leaf("a")), leaf("t"));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("example five: same leaf strings in different order -> false")
    void exampleFive_sameLeavesDifferentOrder_returnsFalse() {
        Solution.TreeNode root1 = node("1", leaf("ab"), leaf("cd"));
        Solution.TreeNode root2 = node("1", leaf("cd"), leaf("ab"));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isFalse();
    }

    @Test
    @DisplayName("example six: same prefix but different total length -> false")
    void exampleSix_samePrefixDifferentLength_returnsFalse() {
        Solution.TreeNode root1 = node("1", leaf("hel"), leaf("lo"));
        Solution.TreeNode root2 = node("1", leaf("hel"), leaf("l"));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isFalse();
    }

    @Test
    @DisplayName("example seven: perfect tree vs incomplete tree with same concatenation -> true")
    void exampleSeven_perfectVsIncompleteTreeSameConcatenation_returnsTrue() {
        Solution.TreeNode root1 = node(
            "1",
            node("2", node("4", leaf("a"), leaf("b")), node("5", leaf("c"), leaf("d"))),
            node("3", node("6", leaf("e"), leaf("f")), node("7", leaf("g"), leaf("h")))
        );
        Solution.TreeNode root2 = node("1", leaf("abcd"), leaf("efgh"));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("both single-node trees with the same leaf value -> true")
    void bothSingleLeafTreesSameValue_returnsTrue() {
        assertThat(solution.leafConcatenationEqual(leaf("hello"), leaf("hello"))).isTrue();
    }

    @Test
    @DisplayName("both single-node trees with different leaf values -> false")
    void bothSingleLeafTreesDifferentValue_returnsFalse() {
        assertThat(solution.leafConcatenationEqual(leaf("cat"), leaf("dog"))).isFalse();
    }

    @Test
    @DisplayName("all leaves empty in both trees -> true, since both concatenate to an empty string")
    void allEmptyLeavesBothTrees_returnsTrue() {
        Solution.TreeNode root1 = node("1", leaf(""), leaf(""));
        Solution.TreeNode root2 = node("1", node("2", leaf(""), leaf("")), leaf(""));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("empty leaves interspersed with value leaves contribute nothing -> true")
    void emptyLeavesInterspersedWithValues_returnsTrue() {
        Solution.TreeNode root1 = node("1", node("2", leaf(""), leaf("ab")), leaf(""));
        Solution.TreeNode root2 = node("1", leaf("a"), node("2", leaf(""), leaf("b")));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("non-leaf node values are ignored even when present -> true")
    void nonLeafValuesIgnored_returnsTrue() {
        Solution.TreeNode root1 = node("ignored", leaf("a"), leaf("b"));
        Solution.TreeNode root2 = node(null, leaf("a"), leaf("b"));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isTrue();
    }

    @Test
    @DisplayName("mismatched length due to an extra leaf on one side -> false")
    void differentLengthDueToExtraLeaf_returnsFalse() {
        Solution.TreeNode root1 = leaf("abc");
        Solution.TreeNode root2 = node("1", leaf("abc"), leaf("d"));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isFalse();
    }

    @Test
    @DisplayName("deep left-skewed tree vs balanced tree with the same concatenation -> true")
    void deepSkewedTreeVsBalancedTree_sameConcatenation_returnsTrue() {
        Solution.TreeNode root1 = node(
            "1",
            node("2", node("3", leaf("a"), leaf("b")), leaf("c")),
            leaf("d")
        );
        Solution.TreeNode root2 = node("1", leaf("ab"), node("2", leaf("c"), leaf("d")));

        assertThat(solution.leafConcatenationEqual(root1, root2)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("boundary leaf length: single-character leaves on both sides (min length 1) -> true")
    void minLeafValueLengthBoundary_returnsTrue() {
        assertThat(solution.leafConcatenationEqual(leaf("a"), leaf("a"))).isTrue();
    }

    @Test
    @DisplayName("boundary leaf length: matching 100-character leaf values -> true")
    void maxLeafValueLengthBoundary_returnsTrue() {
        String hundredChars = "a".repeat(100);

        assertThat(solution.leafConcatenationEqual(leaf(hundredChars), leaf(hundredChars))).isTrue();
    }
}
