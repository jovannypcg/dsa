package mx.jovannypcg.base.p47_subtreeofanothertree;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SolutionTest {

    private final Solution solution = new Solution();

    private Solution.TreeNode node(int val) {
        return new Solution.TreeNode(val);
    }

    // -------------------------------------------------------------------------
    // Happy-path cases (from README examples)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("example one: root=[3,4,5,1,2], subRoot=[4,1,2] -> match at non-root node")
    void exampleOne_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(4, node(1), node(2)),
            node(5)
        );
        Solution.TreeNode subRoot = new Solution.TreeNode(4, node(1), node(2));

        assertThat(solution.isSubtree(root, subRoot)).isTrue();
    }

    @Test
    @DisplayName("example two: extra descendant on candidate node breaks the match -> false")
    void exampleTwo_returnsFalse() {
        Solution.TreeNode two = node(2);
        two.left = node(0);
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(4, node(1), two),
            node(5)
        );
        Solution.TreeNode subRoot = new Solution.TreeNode(4, node(1), node(2));

        assertThat(solution.isSubtree(root, subRoot)).isFalse();
    }

    @Test
    @DisplayName("example three: root=[1,1], subRoot=[1] -> match at leaf, not at root")
    void exampleThree_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(1, node(1), null);
        Solution.TreeNode subRoot = node(1);

        assertThat(solution.isSubtree(root, subRoot)).isTrue();
    }

    @Test
    @DisplayName("example four: subRoot value never appears in root -> false")
    void exampleFour_returnsFalse() {
        Solution.TreeNode root = new Solution.TreeNode(1, node(2), node(3));
        Solution.TreeNode subRoot = node(4);

        assertThat(solution.isSubtree(root, subRoot)).isFalse();
    }

    @Test
    @DisplayName("example five: subRoot is identical to root -> a tree is a subtree of itself")
    void exampleFive_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(
            3,
            new Solution.TreeNode(4, node(1), node(2)),
            node(5)
        );
        Solution.TreeNode subRoot = new Solution.TreeNode(
            3,
            new Solution.TreeNode(4, node(1), node(2)),
            node(5)
        );

        assertThat(solution.isSubtree(root, subRoot)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("single-node root and subRoot with the same value -> true")
    void singleNodeSameValue_returnsTrue() {
        assertThat(solution.isSubtree(node(1), node(1))).isTrue();
    }

    @Test
    @DisplayName("single-node root and subRoot with different values -> false")
    void singleNodeDifferentValue_returnsFalse() {
        assertThat(solution.isSubtree(node(1), node(2))).isFalse();
    }

    @Test
    @DisplayName("subRoot has more nodes than root -> false")
    void subRootLargerThanRoot_returnsFalse() {
        Solution.TreeNode root = node(1);
        Solution.TreeNode subRoot = new Solution.TreeNode(1, node(2), null);

        assertThat(solution.isSubtree(root, subRoot)).isFalse();
    }

    @Test
    @DisplayName("duplicate root values in tree, only one position structurally matches -> true")
    void duplicateValuesOnlyOneStructuralMatch_returnsTrue() {
        Solution.TreeNode decoyFour = new Solution.TreeNode(4, node(1), null);
        Solution.TreeNode realFour = new Solution.TreeNode(4, node(1), node(2));
        Solution.TreeNode root = new Solution.TreeNode(3, decoyFour, realFour);
        Solution.TreeNode subRoot = new Solution.TreeNode(4, node(1), node(2));

        assertThat(solution.isSubtree(root, subRoot)).isTrue();
    }

    @Test
    @DisplayName("negative values throughout matching subtree -> true")
    void negativeValues_returnsTrue() {
        Solution.TreeNode root = new Solution.TreeNode(
            -3,
            new Solution.TreeNode(-4, node(-1), node(-2)),
            node(-5)
        );
        Solution.TreeNode subRoot = new Solution.TreeNode(-4, node(-1), node(-2));

        assertThat(solution.isSubtree(root, subRoot)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("max node value boundary: single node with val 10000 in both trees -> true")
    void maxNodeValue_returnsTrue() {
        assertThat(solution.isSubtree(node(10000), node(10000))).isTrue();
    }

    @Test
    @DisplayName("min node value boundary: single node with val -10000 in both trees -> true")
    void minNodeValue_returnsTrue() {
        assertThat(solution.isSubtree(node(-10000), node(-10000))).isTrue();
    }

    @Test
    @DisplayName("value boundary mismatch: max value root vs min value subRoot -> false")
    void maxVsMinValue_returnsFalse() {
        assertThat(solution.isSubtree(node(10000), node(-10000))).isFalse();
    }
}
