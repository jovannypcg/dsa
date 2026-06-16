# 2-D Dynamic Programming

## Pattern
Subproblems depend on two dimensions — typically two sequences, a grid, or a capacity + item index. `dp[i][j]` represents the answer for a subproblem defined by both `i` and `j`.

## How to Recognize
- Two sequences being compared or aligned (strings, arrays)
- Grid path problems ("minimum cost path", "unique paths")
- Knapsack-style: items + remaining capacity
- "Edit distance", "longest common subsequence", "regex matching"

## Approach
1. **Define** `dp[i][j]` precisely.
2. **Base cases**: first row and/or first column (empty prefix, zero capacity, etc.).
3. **Recurrence**: fill row by row (or column by column), expressing `dp[i][j]` from neighbors.
4. **Space optimization**: if `dp[i][j]` only depends on the previous row, reduce to a 1-D array.

## Template

```java
// Longest Common Subsequence
public int longestCommonSubsequence(String text1, String text2) {
    int m = text1.length(), n = text2.length();
    int[][] dp = new int[m + 1][n + 1]; // dp[i][j] = LCS of text1[0..i-1] and text2[0..j-1]

    for (int i = 1; i <= m; i++) {
        for (int j = 1; j <= n; j++) {
            if (text1.charAt(i - 1) == text2.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    return dp[m][n];
}
```

```java
// Grid — unique paths
public int uniquePaths(int m, int n) {
    int[][] dp = new int[m][n];

    for (int i = 0; i < m; i++) dp[i][0] = 1; // base: left column
    for (int j = 0; j < n; j++) dp[0][j] = 1; // base: top row

    for (int i = 1; i < m; i++) {
        for (int j = 1; j < n; j++) {
            dp[i][j] = dp[i - 1][j] + dp[i][j - 1];
        }
    }

    return dp[m - 1][n - 1];
}
```

```java
// 0/1 Knapsack
public int knapsack(int[] weights, int[] values, int capacity) {
    int n = weights.length;
    int[][] dp = new int[n + 1][capacity + 1];
    // dp[i][w] = max value using first i items with capacity w

    for (int i = 1; i <= n; i++) {
        for (int w = 0; w <= capacity; w++) {
            dp[i][w] = dp[i - 1][w]; // skip item i
            if (weights[i - 1] <= w) {
                dp[i][w] = Math.max(dp[i][w], dp[i - 1][w - weights[i - 1]] + values[i - 1]);
            }
        }
    }

    return dp[n][capacity];
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Unique Paths | [LeetCode #62](https://leetcode.com/problems/unique-paths/) |
| 2 | Unique Paths II | [LeetCode #63](https://leetcode.com/problems/unique-paths-ii/) |
| 3 | Longest Common Subsequence | [LeetCode #1143](https://leetcode.com/problems/longest-common-subsequence/) |
| 4 | Best Time to Buy and Sell Stock with Cooldown | [LeetCode #309](https://leetcode.com/problems/best-time-to-buy-and-sell-stock-with-cooldown/) |
| 5 | Coin Change II | [LeetCode #518](https://leetcode.com/problems/coin-change-ii/) |
| 6 | Target Sum | [LeetCode #494](https://leetcode.com/problems/target-sum/) |
| 7 | Interleaving String | [LeetCode #97](https://leetcode.com/problems/interleaving-string/) |
| 8 | Longest Increasing Path in a Matrix | [LeetCode #329](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/) |
| 9 | Distinct Subsequences | [LeetCode #115](https://leetcode.com/problems/distinct-subsequences/) |
| 10 | Edit Distance | [LeetCode #72](https://leetcode.com/problems/edit-distance/) |
| 11 | Burst Balloons | [LeetCode #312](https://leetcode.com/problems/burst-balloons/) |
| 12 | Regular Expression Matching | [LeetCode #10](https://leetcode.com/problems/regular-expression-matching/) |
| 13 | Wildcard Matching | [LeetCode #44](https://leetcode.com/problems/wildcard-matching/) |
| 14 | Minimum Path Sum | [LeetCode #64](https://leetcode.com/problems/minimum-path-sum/) |
| 15 | Triangle | [LeetCode #120](https://leetcode.com/problems/triangle/) |
| 16 | Maximal Square | [LeetCode #221](https://leetcode.com/problems/maximal-square/) |
| 17 | Count Square Submatrices with All Ones | [LeetCode #1277](https://leetcode.com/problems/count-square-submatrices-with-all-ones/) |
| 18 | Palindrome Partitioning II | [LeetCode #132](https://leetcode.com/problems/palindrome-partitioning-ii/) |
| 19 | Dungeon Game | [LeetCode #174](https://leetcode.com/problems/dungeon-game/) |
| 20 | Stone Game | [LeetCode #877](https://leetcode.com/problems/stone-game/) |
