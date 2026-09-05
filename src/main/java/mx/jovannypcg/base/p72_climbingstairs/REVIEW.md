| | |
|---|---|
| **Solved on** | 2026-09-05 |
| **DSA Category** | 1-D Dynamic Programming |

## 1. Your Solution Assessment

**Correctness:** The current `climbStairs` uses bottom-up tabulation: base cases `dp[1] = 1`, `dp[2] = 2` are seeded directly, then `dp[i] = dp[i-1] + dp[i-2]` fills the table up to `n`. All 7 tests pass, including the `n = 1` and `n = 45` boundaries from the constraints.

**Code quality:** The public method itself is clear and correctly ordered (each `dp[i]` is only read after being written). However, the private `climbMemoization` method — the top-down recursive approach from the previous revision — is now unreachable dead code; nothing in the class calls it anymore. Worth removing, since an unused private method with no caller is a code smell (and most linters/IDEs would flag it as such).

**Time complexity:** O(n) — one pass filling `dp[3..n]`, each cell O(1) work.

**Space complexity:** O(n) — the full `dp` array of size `n + 1` is retained, even though only the last two entries are ever read at any point.

**Algorithm trace** (DP table filling → step table), input `n = 5`:

| i | dp[i-2] | dp[i-1] | dp[i] |
|---|---|---|---|
| 1 | — | — | 1 |
| 2 | — | — | 2 |
| 3 | 1 | 2 | 3 |
| 4 | 2 | 3 | 5 |
| 5 | 3 | 5 | 8 |

→ `dp[5] = 8`

## 2. Optimal Approach

Since `dp[i]` only ever depends on the previous two entries, the full array can be collapsed into two rolling variables — same O(n) time, but O(1) space instead of O(n), and no array allocation at all.

**Time complexity:** O(n) — one pass from `3` to `n`.
**Space complexity:** O(1) — only two variables are kept regardless of `n`.

```java
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

**Algorithm trace** (iterative loop → step table), input `n = 5`:

| i | prev2 (before) | prev1 (before) | curr | prev2 (after) | prev1 (after) |
|---|---|---|---|---|---|
| init | — | — | — | 1 | 2 |
| 3 | 1 | 2 | 3 | 2 | 3 |
| 4 | 2 | 3 | 5 | 3 | 5 |
| 5 | 3 | 5 | 8 | 5 | 8 |

→ return `prev1 = 8`

## 3. Alternative Approaches

### a. Top-down memoized recursion

This is the approach from your previous revision (now the dead `climbMemoization` method): recurse on `ways(n) = ways(n-1) + ways(n-2)`, caching each result in a `memo` array the first time it's computed so no subproblem is solved twice.

**Time complexity:** O(n) — each subproblem `climb(k)` for `k` in `[3, n]` is computed exactly once.
**Space complexity:** O(n) for the `memo` array, plus O(n) recursion call stack depth — strictly worse than tabulation's O(n) array with no stack usage.
**When acceptable:** When the recursive framing is more natural to reason about, or when you don't expect every subproblem in `[1, n]` to actually be needed (not the case here, but a general reason to prefer top-down elsewhere).

**Algorithm trace** (recursion → call stack table), input `n = 4`:

| Depth | Call | Returns |
|---|---|---|
| 0 | `climb(4)` | `climb(3) + climb(2)` |
| 1 | `climb(3)` | `climb(2) + climb(1)` |
| 2 | `climb(2)` | `2` (base case, `n < 3`) |
| 2 | `climb(1)` | `1` (base case, `n < 3`) |
| 1 | `climb(3)` returns | `2 + 1 = 3` → `memo[3] = 3` |
| 1 | `climb(2)` | `2` (base case, recomputed — not memoized since `n < 3`) |
| 0 | `climb(4)` returns | `3 + 2 = 5` → `memo[4] = 5` |

→ `climbStairs(4) = 5`

### b. Brute-force recursion (no memoization)

Directly recurse on `ways(n) = ways(n-1) + ways(n-2)` without caching results. Simple to write and clearly expresses the recurrence, but recomputes the same subproblems exponentially many times.

**Time complexity:** O(2^n) — each call branches into two more calls down to the base cases, and lower subproblems (like `climb(3)`) get fully recomputed from scratch every time they're needed again.
**Space complexity:** O(n) — recursion call stack depth only (no memo table).
**When acceptable:** Only for very small `n`, or as a first pass in an interview to state the recurrence before optimizing — with `n` up to 45, this is far too slow in practice.

**Algorithm trace** (recursion → call stack table), input `n = 5`:

| Depth | Call | Returns |
|---|---|---|
| 0 | `climb(5)` | `climb(4) + climb(3)` |
| 1 | `climb(4)` | `climb(3) + climb(2)` |
| 2 | `climb(3)` | `climb(2) + climb(1)` |
| 3 | `climb(2)` | `2` (base case) |
| 3 | `climb(1)` | `1` (base case) |
| 2 | `climb(3)` returns | `2 + 1 = 3` |
| 1 | `climb(2)` | `2` (base case) |
| 0 | `climb(4)` returns | `3 + 2 = 5` |
| 1 | `climb(3)` (recomputed from scratch) | `climb(2) + climb(1)` |
| 2 | `climb(2)` | `2` (base case) |
| 2 | `climb(1)` | `1` (base case) |
| 1 | `climb(3)` returns | `2 + 1 = 3` |
| 0 | `climb(5)` returns | `5 + 3 = 8` |

→ `climbStairs(5) = 8` — note the entire `climb(3)` subtree is recomputed a second time; this duplication compounds as `n` grows.

### c. Closed-form formula (Binet's formula)

The recurrence is exactly the Fibonacci sequence shifted by one (`climbStairs(n) = fib(n + 1)`), which has a closed form using the golden ratio `φ = (1 + √5) / 2` and `ψ = (1 - √5) / 2`: `fib(k) = (φ^k − ψ^k) / √5`. This gives O(1) time in theory, but relies on floating-point arithmetic, so it's risky for interviews — precision errors can silently produce an off-by-one result, and it obscures the actual algorithmic insight the problem is testing.

**Time complexity:** O(1) — a constant number of arithmetic operations (or O(log n) if computed safely via matrix exponentiation instead of floating point).
**Space complexity:** O(1).
**When acceptable:** Rarely appropriate to lead with in an interview — mostly a curiosity, or useful if asked to compute a single very large `n` many times with precomputed constants.

**Algorithm trace** (formula computation), input `n = 5` (computing `fib(6)`):

| Term | Value |
|---|---|
| `√5` | `2.2360679...` |
| `φ = (1 + √5) / 2` | `1.6180339...` |
| `ψ = (1 - √5) / 2` | `-0.6180339...` |
| `φ^6` | `17.9442719...` |
| `ψ^6` | `0.0557280...` |
| `(φ^6 − ψ^6) / √5` | `8.0000000...` → rounds to `8` |

→ `climbStairs(5) = fib(6) = 8`
