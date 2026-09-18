| | |
|---|---|
| **Solved on** | 2026-09-05 |
| **DSA Category** | 1-D Dynamic Programming |

## 1. Your Solution Assessment

**Correctness**: The solution is correct. It handles the empty/null array (returns 0), the single-house case (returns `nums[0]`), and builds up the classic recurrence `dp[i] = max(dp[i-1], dp[i-2] + nums[i])` for the rest. This recurrence correctly captures the "rob or skip" decision at every house, and it was verified against all 13 test cases, including the max-length, max-value boundary (`100` houses of `400` each → `20000`).

**Code quality**: Clear and readable. Early returns for the trivial cases avoid awkward indexing on a size-1 `dp` array, and variable names (`dp`, `n`) are conventional for this pattern.

**Time complexity**: O(n) — the array is scanned once, and each iteration does O(1) work.

**Space complexity**: O(n) — a full `dp` array of size `n` is allocated, even though each entry only ever depends on the two previous entries.

**Algorithm trace** (DP table filling → step table)

Input: `nums = [2, 7, 9, 3, 1]`

| i | nums[i] | dp[i-2] | dp[i-1] | dp[i] |
|---|---------|---------|---------|-------|
| 0 | 2 | — | — | 2 |
| 1 | 7 | — | 2 | 7 |
| 2 | 9 | 2 | 7 | max(7, 2+9) = 11 |
| 3 | 3 | 7 | 11 | max(11, 7+3) = 11 |
| 4 | 1 | 11 | 11 | max(11, 11+1) = 12 |
→ return `dp[4] = 12`

## 2. Optimal Approach

The recurrence only ever looks back two positions, so the whole `dp` array is unnecessary — track just the last two best totals as you scan left to right. Call them `prev1` (best up to two houses ago) and `prev2` (best up to the previous house). At each house, the best total including this house is `prev1 + nums[i]`; the best excluding it is `prev2`. Take the max, then slide the window forward.

**Time complexity**: O(n) — still one pass over the array.

**Space complexity**: O(1) — only two variables are kept regardless of input size.

```java
public int rob(int[] nums) {
    int prev1 = 0;
    int prev2 = 0;

    for (int n : nums) {
        int current = Math.max(prev1 + n, prev2);
        prev1 = prev2;
        prev2 = current;
    }

    return prev2;
}
```

**Algorithm trace** (DP table filling → step table)

Input: `nums = [2, 7, 9, 3, 1]`

| n | prev1 (before) | prev2 (before) | current = max(prev1+n, prev2) | prev1 (after) | prev2 (after) |
|---|---|---|---|---|---|
| 2 | 0 | 0 | max(0+2, 0) = 2 | 0 | 2 |
| 7 | 0 | 2 | max(0+7, 2) = 7 | 2 | 7 |
| 9 | 2 | 7 | max(2+9, 7) = 11 | 7 | 11 |
| 3 | 7 | 11 | max(7+3, 11) = 11 | 11 | 11 |
| 1 | 11 | 11 | max(11+1, 11) = 12 | 11 | 12 |
→ return `prev2 = 12`

## 3. Alternative Approaches

### a) Your approach — full DP array
Build a `dp[i]` array where `dp[i] = max(dp[i-1], dp[i-2] + nums[i])`.

- **Time**: O(n) — one pass to fill the array.
- **Space**: O(n) — the array stores every intermediate result, even though only the last two are ever read.
- **When acceptable**: Perfectly fine in an interview — it demonstrates the recurrence clearly. Worth mentioning the O(1) space optimization as a follow-up once it works.

**Algorithm trace**: see section 1 above.

### b) Top-down recursion with memoization
Define `best(i)` = max money obtainable from house `i` to the end (or from the start to house `i`, depending on direction), with results cached in a map or array to avoid recomputation.

```java
public int rob(int[] nums) {
    Integer[] memo = new Integer[nums.length];
    return helper(nums, nums.length - 1, memo);
}

private int helper(int[] nums, int i, Integer[] memo) {
    if (i < 0) return 0;
    if (memo[i] != null) return memo[i];

    memo[i] = Math.max(helper(nums, i - 1, memo), helper(nums, i - 2, memo) + nums[i]);
    return memo[i];
}
```

- **Time**: O(n) — each subproblem `helper(i)` is computed once thanks to memoization.
- **Space**: O(n) for the memo array plus O(n) recursion stack depth.
- **When acceptable**: Reasonable if recursion feels more natural to reason about under interview pressure, though the iterative version is preferred for its lower overhead and no stack-depth risk.

**Algorithm trace** (recursion → call stack table)

Input: `nums = [2, 7, 9]`

| Depth | Call | Returns |
|---|---|---|
| 0 | helper(2) | max(helper(1), helper(0) + 9) |
| 1 | helper(1) | max(helper(0), helper(-1) + 7) |
| 2 | helper(0) | max(helper(-1), helper(-2) + 2) = 2 |
| 2 | helper(-1) | 0 |
| 1 | helper(1) resolves | max(2, 0 + 7) = 7 |
| 1 | helper(0) (memoized) | 2 |
| 0 | helper(2) resolves | max(7, 2 + 9) = 11 |
→ `rob([2,7,9]) = 11`

### c) Brute-force recursion without memoization
Same recurrence as (b), but without caching — every call branches into two more calls.

- **Time**: O(2^n) — the recursion tree is unpruned, recomputing overlapping subproblems repeatedly.
- **Space**: O(n) recursion stack depth (no memo storage).
- **When acceptable**: Only for very small `n` or as a starting point to show the recurrence before optimizing to memoization or iteration; with `n` up to 100 per the constraints, this would time out.

**Algorithm trace** (recursion → call stack table)

Input: `nums = [2, 7, 9]`

| Depth | Call | Returns |
|---|---|---|
| 0 | helper(2) | max(helper(1), helper(0) + 9) |
| 1 | helper(1) | max(helper(0), helper(-1) + 7) |
| 2 | helper(0) | max(helper(-1), helper(-2) + 2) = 2 |
| 2 | helper(-1) | 0 |
| 1 | helper(1) resolves | max(2, 0 + 7) = 7 |
| 1 | helper(0) (recomputed) | max(helper(-1), helper(-2) + 2) = 2 |
| 0 | helper(2) resolves | max(7, 2 + 9) = 11 |
→ `rob([2,7,9]) = 11` (note `helper(0)` was recomputed instead of reused)
