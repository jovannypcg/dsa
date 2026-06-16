# 1-D Dynamic Programming

## Pattern
Break the problem into overlapping subproblems along a single dimension. Store solutions to subproblems (memoization or tabulation) to avoid recomputation.

## How to Recognize
- "Number of ways to reach X"
- "Minimum / maximum cost to achieve X"
- "Longest subsequence / substring satisfying condition"
- Decision at each step affects future steps
- Recursive solution with repeated subproblems (overlapping subproblems + optimal substructure)

## Approach
1. **Define** `dp[i]` clearly (e.g., "minimum cost to reach step i").
2. **Base case**: smallest valid input(s).
3. **Recurrence**: express `dp[i]` in terms of previous values.
4. **Answer**: `dp[n]` or `max/min` over all `dp[i]`.

Bottom-up (tabulation) is usually preferred — no recursion overhead, easier to optimize space.

## Template

```java
// Climbing stairs — number of ways to reach step n
public int climbStairs(int n) {
    if (n <= 2) return n;

    int prev2 = 1, prev1 = 2;

    for (int i = 3; i <= n; i++) {
        int curr = prev1 + prev2;
        prev2 = prev1;
        prev1 = curr;
    }

    return prev1;
}
```

```java
// General 1-D DP table
public int solve(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];

    dp[0] = /* base case */;

    for (int i = 1; i < n; i++) {
        dp[i] = /* recurrence using dp[i-1], dp[i-2], nums[i], ... */;
    }

    return dp[n - 1]; // or max over dp
}
```

```java
// Longest Increasing Subsequence
public int lengthOfLIS(int[] nums) {
    int n = nums.length;
    int[] dp = new int[n];
    Arrays.fill(dp, 1);         // each element is a subsequence of length 1
    int max = 1;

    for (int i = 1; i < n; i++) {
        for (int j = 0; j < i; j++) {
            if (nums[j] < nums[i]) {
                dp[i] = Math.max(dp[i], dp[j] + 1);
            }
        }
        max = Math.max(max, dp[i]);
    }

    return max;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Climbing Stairs | [LeetCode #70](https://leetcode.com/problems/climbing-stairs/) |
| 2 | Min Cost Climbing Stairs | [LeetCode #746](https://leetcode.com/problems/min-cost-climbing-stairs/) |
| 3 | House Robber | [LeetCode #198](https://leetcode.com/problems/house-robber/) |
| 4 | House Robber II | [LeetCode #213](https://leetcode.com/problems/house-robber-ii/) |
| 5 | Longest Palindromic Substring | [LeetCode #5](https://leetcode.com/problems/longest-palindromic-substring/) |
| 6 | Palindromic Substrings | [LeetCode #647](https://leetcode.com/problems/palindromic-substrings/) |
| 7 | Decode Ways | [LeetCode #91](https://leetcode.com/problems/decode-ways/) |
| 8 | Coin Change | [LeetCode #322](https://leetcode.com/problems/coin-change/) |
| 9 | Maximum Product Subarray | [LeetCode #152](https://leetcode.com/problems/maximum-product-subarray/) |
| 10 | Word Break | [LeetCode #139](https://leetcode.com/problems/word-break/) |
| 11 | Longest Increasing Subsequence | [LeetCode #300](https://leetcode.com/problems/longest-increasing-subsequence/) |
| 12 | Partition Equal Subset Sum | [LeetCode #416](https://leetcode.com/problems/partition-equal-subset-sum/) |
| 13 | Jump Game | [LeetCode #55](https://leetcode.com/problems/jump-game/) |
| 14 | Jump Game II | [LeetCode #45](https://leetcode.com/problems/jump-game-ii/) |
| 15 | Perfect Squares | [LeetCode #279](https://leetcode.com/problems/perfect-squares/) |
| 16 | Counting Bits | [LeetCode #338](https://leetcode.com/problems/counting-bits/) |
| 17 | Coin Change II | [LeetCode #518](https://leetcode.com/problems/coin-change-ii/) |
| 18 | Maximum Subarray (Kadane's) | [LeetCode #53](https://leetcode.com/problems/maximum-subarray/) |
| 19 | Delete and Earn | [LeetCode #740](https://leetcode.com/problems/delete-and-earn/) |
| 20 | Minimum Path Sum (1-D optimization) | [LeetCode #64](https://leetcode.com/problems/minimum-path-sum/) |
