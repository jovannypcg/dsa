package mx.jovannypcg.base.p73_mincostclimbingstairs;

/**
 * You are given an integer array {@code cost} where {@code cost[i]} is the cost of the
 * {@code i}th step on a staircase. Once you pay the cost, you can either climb one or two steps.
 *
 * <p>You can either start from the step with index 0, or the step with index 1.
 *
 * <p>Return the minimum cost to reach the top of the staircase, which is the position just past
 * the last step (index {@code cost.length}).
 *
 * @see <a href="https://leetcode.com/problems/min-cost-climbing-stairs/">Min Cost Climbing Stairs - LeetCode</a>
 */
public class Solution {
    public int minCostClimbingStairs(int[] cost) {
        if (cost == null || cost.length == 0) return 0;

        int n = cost.length;
        // dp[i] = min cost to REACH position i (not to leave it)
        int[] dp = new int[n + 1];
        dp[0] = 0; // free starting point
        dp[1] = 0; // free starting point

        // to land on i, come from i-1 (pay cost[i-1]) or i-2 (pay cost[i-2]); take the cheaper
        for (int i = 2; i <= n; i++) {
            dp[i] = Math.min(dp[i - 1] + cost[i - 1], dp[i - 2] + cost[i - 2]);
        }

        return dp[n]; // n is the top, one past the last step
    }
}
