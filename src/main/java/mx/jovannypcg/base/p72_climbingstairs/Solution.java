package mx.jovannypcg.base.p72_climbingstairs;

/**
 * You are climbing a staircase. It takes {@code n} steps to reach the top.
 *
 * <p>Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb
 * to the top?
 *
 * @see <a href="https://leetcode.com/problems/climbing-stairs/">Climbing Stairs - LeetCode</a>
 */
public class Solution {
    public int climbStairs(int n) {
        if (n < 3) return n;

        int[] dp = new int[n + 1];
        dp[1] = 1;
        dp[2] = 2;

        for (int i = 3; i <= n; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }

        return dp[n];
    }

    private int climbMemoization(int n, int[] memo) {
        if (n < 3) return n;
        if (memo[n] != 0) return memo[n];

        int ways = climbMemoization(n - 1, memo) + climbMemoization(n - 2, memo);

        memo[n] = ways;

        return ways;
    }
}
