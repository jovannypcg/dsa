package mx.jovannypcg.base.p09_pathsum;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class SolutionTest {

    private final Solution solution = new Solution();

    // Convenience builder mirrors LeetCode's level-order input
    private Solution.TreeNode node(int val) {
        return new Solution.TreeNode(val);
    }

    // -------------------------------------------------------------------------
    // Happy-path cases
    // -------------------------------------------------------------------------

    @Test
    void pathExists_exampleOne() {
        // [5,4,8,11,null,13,4,7,2,null,null,null,1], targetSum = 22
        // Path: 5 → 4 → 11 → 2
        Solution.TreeNode root = new Solution.TreeNode(
            5,
            new Solution.TreeNode(
                4,
                new Solution.TreeNode(11, node(7), node(2)),
                null
            ),
            new Solution.TreeNode(
                8,
                node(13),
                new Solution.TreeNode(4, null, node(1))
            )
        );

        assertThat(solution.hasPathSum(root, 22)).isTrue();
    }

    @Test
    void noPath_exampleTwo() {
        // [1,2,3], targetSum = 5
        Solution.TreeNode root = new Solution.TreeNode(1, node(2), node(3));

        assertThat(solution.hasPathSum(root, 5)).isFalse();
    }

    // -------------------------------------------------------------------------
    // Edge cases
    // -------------------------------------------------------------------------

    @Test
    void emptyTree_returnsFalse() {
        assertThat(solution.hasPathSum(null, 0)).isFalse();
    }

    @Test
    void singleNodeMatchesTarget() {
        assertThat(solution.hasPathSum(node(1), 1)).isTrue();
    }

    @Test
    void singleNodeDoesNotMatchTarget() {
        assertThat(solution.hasPathSum(node(1), 2)).isFalse();
    }

    @Test
    void negativeValues_pathExists() {
        // -2 → -3 = -5
        Solution.TreeNode root = new Solution.TreeNode(-2, null, node(-3));
        assertThat(solution.hasPathSum(root, -5)).isTrue();
    }

    @Test
    void negativeValues_pathDoesNotExist() {
        Solution.TreeNode root = new Solution.TreeNode(-2, null, node(-3));
        assertThat(solution.hasPathSum(root, -4)).isFalse();
    }

    @Test
    void internalNodeSumMatchesTarget_butNotLeaf() {
        // Tree: 1 → 2 → 3; targetSum = 3 (1+2 = 3, but 2 is not a leaf)
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(2, node(3), null),
            null
        );
        assertThat(solution.hasPathSum(root, 3)).isFalse();
    }

    @Test
    void onlyRightPathReachesTarget() {
        // Left: 1 → 2 = 3; Right: 1 → 10 = 11; targetSum = 11
        Solution.TreeNode root = new Solution.TreeNode(1, node(2), node(10));
        assertThat(solution.hasPathSum(root, 11)).isTrue();
    }

    // -------------------------------------------------------------------------
    // Boundary values from constraints
    // -------------------------------------------------------------------------

    @Test
    void maxNodeValue_singleNode() {
        // Node.val upper bound: 1000; targetSum upper bound: 1000
        assertThat(solution.hasPathSum(node(1000), 1000)).isTrue();
    }

    @Test
    void minNodeValue_singleNode() {
        // Node.val lower bound: -1000; targetSum lower bound: -1000
        assertThat(solution.hasPathSum(node(-1000), -1000)).isTrue();
    }

    @Test
    void zeroTargetSum_pathExists() {
        // Path: -1 → 1 = 0
        Solution.TreeNode root = new Solution.TreeNode(-1, node(1), null);
        assertThat(solution.hasPathSum(root, 0)).isTrue();
    }

    @Test
    void zeroTargetSum_emptyTree_returnsFalse() {
        assertThat(solution.hasPathSum(null, 0)).isFalse();
    }

    @Test
    void targetSumNotReachableInDeepTree() {
        // Deep left-skewed tree: 1 → 2 → 3 → 4, targetSum = 100
        Solution.TreeNode root = new Solution.TreeNode(
            1,
            new Solution.TreeNode(
                2,
                new Solution.TreeNode(3, node(4), null),
                null
            ),
            null
        );
        assertThat(solution.hasPathSum(root, 100)).isFalse();
    }
}
