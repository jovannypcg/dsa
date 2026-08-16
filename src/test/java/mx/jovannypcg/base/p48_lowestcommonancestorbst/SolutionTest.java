package mx.jovannypcg.base.p48_lowestcommonancestorbst;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SolutionTest {

    private final Solution solution = new Solution();

    private Solution.TreeNode node(int val) {
        return new Solution.TreeNode(val);
    }

    // Builds root = [6,2,8,0,4,7,9,null,null,3,5] and returns a lookup by value,
    // since all values are unique and tests need the exact node references for p/q.
    private Map<Integer, Solution.TreeNode> mainTree() {
        Solution.TreeNode n0 = node(0);
        Solution.TreeNode n3 = node(3);
        Solution.TreeNode n5 = node(5);
        Solution.TreeNode n4 = new Solution.TreeNode(4, n3, n5);
        Solution.TreeNode n2 = new Solution.TreeNode(2, n0, n4);
        Solution.TreeNode n7 = node(7);
        Solution.TreeNode n9 = node(9);
        Solution.TreeNode n8 = new Solution.TreeNode(8, n7, n9);
        Solution.TreeNode n6 = new Solution.TreeNode(6, n2, n8);

        Map<Integer, Solution.TreeNode> nodesByValue = new HashMap<>();
        nodesByValue.put(0, n0);
        nodesByValue.put(2, n2);
        nodesByValue.put(3, n3);
        nodesByValue.put(4, n4);
        nodesByValue.put(5, n5);
        nodesByValue.put(6, n6);
        nodesByValue.put(7, n7);
        nodesByValue.put(8, n8);
        nodesByValue.put(9, n9);
        return nodesByValue;
    }

    // -------------------------------------------------------------------------
    // Happy-path cases (from README examples)
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("example one: p=2, q=8 on opposite sides of root -> LCA is root 6")
    void exampleOne_lcaIsRoot() {
        Map<Integer, Solution.TreeNode> tree = mainTree();
        Solution.TreeNode root = tree.get(6);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, tree.get(2), tree.get(8));

        assertThat(result.val).isEqualTo(6);
    }

    @Test
    @DisplayName("example two: p=2, q=4 where q descends from p -> LCA is p itself")
    void exampleTwo_lcaIsAncestorNode() {
        Map<Integer, Solution.TreeNode> tree = mainTree();
        Solution.TreeNode root = tree.get(6);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, tree.get(2), tree.get(4));

        assertThat(result.val).isEqualTo(2);
    }

    @Test
    @DisplayName("example three: minimal two-node tree [2,1], p=2, q=1 -> LCA is root 2")
    void exampleThree_minimalTree() {
        Solution.TreeNode child = node(1);
        Solution.TreeNode root = new Solution.TreeNode(2, child, null);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, root, child);

        assertThat(result.val).isEqualTo(2);
    }

    @Test
    @DisplayName("example four: p=3, q=5 are siblings under 4 -> LCA is 4")
    void exampleFour_lcaIsSharedParent() {
        Map<Integer, Solution.TreeNode> tree = mainTree();
        Solution.TreeNode root = tree.get(6);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, tree.get(3), tree.get(5));

        assertThat(result.val).isEqualTo(4);
    }

    @Test
    @DisplayName("example five: p=7, q=9 are siblings under 8 -> LCA is 8")
    void exampleFive_lcaIsShallowerSharedParent() {
        Map<Integer, Solution.TreeNode> tree = mainTree();
        Solution.TreeNode root = tree.get(6);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, tree.get(7), tree.get(9));

        assertThat(result.val).isEqualTo(8);
    }

    @Test
    @DisplayName("example six: p=0, q=5 at different depths in the same subtree -> LCA is 2")
    void exampleSix_lcaAtDifferentDepths() {
        Map<Integer, Solution.TreeNode> tree = mainTree();
        Solution.TreeNode root = tree.get(6);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, tree.get(0), tree.get(5));

        assertThat(result.val).isEqualTo(2);
    }

    @Test
    @DisplayName("example seven: p is the root itself -> LCA is the root")
    void exampleSeven_pIsRoot() {
        Map<Integer, Solution.TreeNode> tree = mainTree();
        Solution.TreeNode root = tree.get(6);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, tree.get(6), tree.get(4));

        assertThat(result.val).isEqualTo(6);
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("order of p and q swapped still yields the same LCA")
    void swappedArgumentOrder_stillFindsLca() {
        Map<Integer, Solution.TreeNode> tree = mainTree();
        Solution.TreeNode root = tree.get(6);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, tree.get(4), tree.get(2));

        assertThat(result.val).isEqualTo(2);
    }

    @Test
    @DisplayName("q is the root itself -> LCA is the root")
    void qIsRoot_lcaIsRoot() {
        Map<Integer, Solution.TreeNode> tree = mainTree();
        Solution.TreeNode root = tree.get(6);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, tree.get(9), tree.get(6));

        assertThat(result.val).isEqualTo(6);
    }

    @Test
    @DisplayName("negative values throughout the tree still resolve the correct LCA")
    void negativeValues_lcaResolved() {
        Solution.TreeNode nMinus9 = node(-9);
        Solution.TreeNode nMinus5 = node(-5);
        Solution.TreeNode nMinus7 = new Solution.TreeNode(-7, nMinus9, nMinus5);
        Solution.TreeNode nMinus1 = node(-1);
        Solution.TreeNode nMinus3 = new Solution.TreeNode(-3, nMinus7, nMinus1);

        Solution.TreeNode result = solution.lowestCommonAncestor(nMinus3, nMinus9, nMinus1);

        assertThat(result.val).isEqualTo(-3);
    }

    @Test
    @DisplayName("both nodes in the deepest left-leaning chain -> LCA is their shared parent")
    void deepLeftLeaningChain_lcaIsImmediateParent() {
        Solution.TreeNode leaf = node(1);
        Solution.TreeNode sibling = node(3);
        Solution.TreeNode parent = new Solution.TreeNode(2, leaf, sibling);
        Solution.TreeNode grandparent = new Solution.TreeNode(4, parent, null);
        Solution.TreeNode root = new Solution.TreeNode(6, grandparent, null);

        Solution.TreeNode result = solution.lowestCommonAncestor(root, leaf, sibling);

        assertThat(result.val).isEqualTo(2);
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    @DisplayName("boundary node values: -10^9 and 10^9 in a minimal two-node tree -> LCA is root")
    void minAndMaxNodeValues_lcaIsRoot() {
        Solution.TreeNode maxChild = node(1_000_000_000);
        Solution.TreeNode minRoot = new Solution.TreeNode(-1_000_000_000, null, maxChild);

        Solution.TreeNode result = solution.lowestCommonAncestor(minRoot, minRoot, maxChild);

        assertThat(result.val).isEqualTo(-1_000_000_000);
    }
}
