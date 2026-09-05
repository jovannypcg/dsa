| | |
|---|---|
| **Solved on** | 2026-09-05 |
| **DSA Category** | 1-D Dynamic Programming |

## 1. Your Solution Assessment

**Correctness:** The solution is correct. It handles all constraint boundaries (`cost.length == 2`, all-zero costs, all-max costs) and the general case correctly. The `cost == null || cost.length == 0` guard is defensive but unreachable given the constraint `2 <= cost.length <= 1000` — harmless, but not strictly necessary.

**Code quality:** Clean and readable. `dp[i]` is well-named for "min cost to reach position `i`", and the base cases are documented inline. The recurrence line reads directly like the problem's recurrence relation, which makes it easy to map back to the problem statement.

**Time complexity:** O(n), where n is `cost.length`. The solution does a single pass filling `dp[2..n]`, each step doing constant work.

**Space complexity:** O(n), for the `dp` array of size `n + 1`. This can be reduced to O(1) since each `dp[i]` only depends on the two previous entries (see Optimal Approach).

**Algorithm trace** (bottom-up DP table filling — step table)

Input: `cost = [10, 15, 20]`

| i | dp[i-2] | cost[i-2] | dp[i-2]+cost[i-2] | dp[i-1] | cost[i-1] | dp[i-1]+cost[i-1] | dp[i] = min |
|---|---|---|---|---|---|---|---|
| 2 | dp[0]=0 | cost[0]=10 | 10 | dp[1]=0 | cost[1]=15 | 15 | 10 |
| 3 | dp[1]=0 | cost[1]=15 | 15 | dp[2]=10 | cost[2]=20 | 30 | 15 |

→ return `dp[3] = 15`

## 2. Optimal Approach

Same recurrence as your solution, but since `dp[i]` only ever needs `dp[i-1]` and `dp[i-2]`, the full array can be replaced with two rolling variables. This keeps the O(n) time but drops space to O(1).

**Time complexity:** O(n) — still one pass over the array.
**Space complexity:** O(1) — only two variables are kept instead of an array of size n+1.

```java
public int minCostClimbingStairs(int[] cost) {
    int n = cost.length;
    int prev2 = 0; // dp[i-2]
    int prev1 = 0; // dp[i-1]

    for (int i = 2; i <= n; i++) {
        int current = Math.min(prev1 + cost[i - 1], prev2 + cost[i - 2]);
        prev2 = prev1;
        prev1 = current;
    }

    return prev1;
}
```

**Algorithm trace** (step table)

Input: `cost = [10, 15, 20]`

| i | prev2 | prev1 | cost[i-2] | cost[i-1] | current = min(prev1+cost[i-1], prev2+cost[i-2]) | updated (prev2, prev1) |
|---|---|---|---|---|---|---|
| 2 | 0 | 0 | 10 | 15 | 10 | (0, 10) |
| 3 | 0 | 10 | 15 | 20 | 15 | (10, 15) |

→ return `prev1 = 15`

## 3. Alternative Approaches

### Brute force recursion (no memoization)

Define `dp(i)` as the min cost to reach the top starting *from* step `i`: `dp(i) = cost[i] + min(dp(i+1), dp(i+2))`, with base case `dp(i) = 0` once `i >= n`. The answer is `min(dp(0), dp(1))`. Without caching, overlapping subproblems (e.g. `dp(2)`) get recomputed many times, giving exponential blowup.

**Time complexity:** O(2^n) — each call branches into two more calls down to depth n.
**Space complexity:** O(n) — bounded by the recursion call stack depth.
**When acceptable:** Only for very small `n`, or as a first-pass "make it work" solution before optimizing in an interview.

**Algorithm trace** (call stack table)

Input: `cost = [10, 15, 20]`

| Depth | Call | Returns |
|---|---|---|
| 0 | dp(0) | cost[0] + min(dp(1), dp(2)) |
| 1 | dp(1) | cost[1] + min(dp(2), dp(3)) |
| 2 | dp(2) | cost[2] + min(dp(3), dp(4)) |
| 3 | dp(3) | 0 (base case, i ≥ n) |
| 3 | dp(4) | 0 (base case, i ≥ n) |
| 2 | dp(2) = 20 + min(0, 0) | 20 |
| 1 | dp(3) | 0 (base case) |
| 1 | dp(1) = 15 + min(20, 0) | 15 |
| 1 | dp(2) *(recomputed)* | cost[2] + min(dp(3), dp(4)) |
| 2 | dp(3) *(recomputed)* | 0 |
| 2 | dp(4) *(recomputed)* | 0 |
| 1 | dp(2) *(recomputed)* = 20 + min(0, 0) | 20 |
| 0 | dp(0) = 10 + min(15, 20) | 25 |

→ answer = `min(dp(0), dp(1))` = `min(25, 15)` = **15**

Note how `dp(2)`, `dp(3)`, and `dp(4)` are each recomputed from scratch across branches — this repeated work is exactly what memoization eliminates.

### Top-down recursion with memoization

Same recurrence as brute force, but cache each `dp(i)` result after the first computation so overlapping subproblems are only solved once.

**Time complexity:** O(n) — each `dp(i)` is computed once and cached.
**Space complexity:** O(n) — memo array plus recursion call stack.
**When acceptable:** When recursion is more natural to reason about than an iterative loop, or as a stepping stone toward the bottom-up solution.

**Algorithm trace** (call stack table)

Input: `cost = [10, 15, 20]`

| Depth | Call | Cache | Returns |
|---|---|---|---|
| 0 | dp(0) | miss | cost[0] + min(dp(1), dp(2)) |
| 1 | dp(1) | miss | cost[1] + min(dp(2), dp(3)) |
| 2 | dp(2) | miss | cost[2] + min(dp(3), dp(4)) |
| 3 | dp(3) | miss | 0 (base case) |
| 3 | dp(4) | miss | 0 (base case) |
| 2 | dp(2) = 20 + min(0, 0) | store 20 | 20 |
| 1 | dp(3) | hit (0) | 0 |
| 1 | dp(1) = 15 + min(20, 0) | store 15 | 15 |
| 0 | dp(2) | hit (20) | 20 |
| 0 | dp(0) = 10 + min(15, 20) | store 25 | 25 |

→ answer = `min(dp(0), dp(1))` = `min(25, 15)` = **15**
