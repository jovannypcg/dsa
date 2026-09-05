| | |
|---|---|
| **Solved on** | 2026-09-05 |
| **DSA Category** | 1-D Dynamic Programming |

## 1. Your Solution Assessment

**Correctness:** The solution implements the recurrence `F(n) = F(n-1) + F(n-2)` with top-down memoization. All 8 tests pass, including the `n = 0`, `n = 1` base cases and the `n = 30` upper bound from the constraints (a `n = 40` test beyond the stated constraints also passes). The `memo[n] != 0` sentinel is safe here: `F(0) = 0` is the only value that could collide with the sentinel, but it's handled by the `n < 2` base case and is never written into `memo`, so it never gets mistaken for "not yet computed."

**Code quality:** Clean split between the public API and the private recursive helper, matching the pattern used in [[p72_climbingstairs]]. Naming is clear aside from the leading underscore in `_fib` (the local variable), which is unconventional Java style but harmless.

**Time complexity:** O(n). Each subproblem `F(k)` for `k` in `[2, n]` is computed exactly once thanks to memoization; base cases are O(1).

**Space complexity:** O(n) for the `memo` array, plus O(n) for the recursion call stack depth.

**Algorithm trace** (recursion → call stack table), input `n = 4`:

| Depth | Call | Returns |
|---|---|---|
| 0 | `fib(4)` | `fib(3) + fib(2)` |
| 1 | `fib(3)` | `fib(2) + fib(1)` |
| 2 | `fib(2)` | `fib(1) + fib(0)` |
| 3 | `fib(1)` | `1` (base case) |
| 3 | `fib(0)` | `0` (base case) |
| 2 | `fib(2)` returns | `1 + 0 = 1` → `memo[2] = 1` |
| 1 | `fib(1)` | `1` (base case) |
| 1 | `fib(3)` returns | `1 + 1 = 2` → `memo[3] = 2` |
| 0 | `fib(2)` (memo hit) | `1` (returned from `memo[2]`, not recomputed) |
| 0 | `fib(4)` returns | `2 + 1 = 3` → `memo[4] = 3` |

→ `fib(4) = 3`

## 2. Optimal Approach

Only the previous two Fibonacci values are ever needed, so a bottom-up loop with two rolling variables matches the O(n) time of the memoized version while dropping to O(1) space and avoiding recursion overhead entirely.

**Time complexity:** O(n) — one pass from `2` to `n`.
**Space complexity:** O(1) — only two variables are kept regardless of `n`.

```java
public int fib(int n) {
    if (n < 2) return n;

    int prev2 = 0, prev1 = 1;

    for (int i = 2; i <= n; i++) {
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
| init | — | — | — | 0 | 1 |
| 2 | 0 | 1 | 1 | 1 | 1 |
| 3 | 1 | 1 | 2 | 1 | 2 |
| 4 | 1 | 2 | 3 | 2 | 3 |
| 5 | 2 | 3 | 5 | 3 | 5 |

→ return `prev1 = 5`

## 3. Alternative Approaches

### a. Brute-force recursion (no memoization)

Directly recurse on `F(n) = F(n-1) + F(n-2)` without caching. Matches the problem statement almost word-for-word, but recomputes the same subproblems exponentially many times.

**Time complexity:** O(2^n) — each call branches into two more calls down to the base cases, and lower subproblems get fully recomputed every time they recur.
**Space complexity:** O(n) — recursion call stack depth only.
**When acceptable:** Only for very small `n`, or to state the recurrence in an interview before optimizing — with `n` up to 30, the runtime is still technically feasible but wasteful, and would become impractical if the bound were even a bit higher.

**Algorithm trace** (recursion → call stack table), input `n = 5`:

| Depth | Call | Returns |
|---|---|---|
| 0 | `fib(5)` | `fib(4) + fib(3)` |
| 1 | `fib(4)` | `fib(3) + fib(2)` |
| 2 | `fib(3)` | `fib(2) + fib(1)` |
| 3 | `fib(2)` | `fib(1) + fib(0)` |
| 4 | `fib(1)` | `1` (base case) |
| 4 | `fib(0)` | `0` (base case) |
| 3 | `fib(2)` returns | `1 + 0 = 1` |
| 2 | `fib(1)` | `1` (base case) |
| 2 | `fib(3)` returns | `1 + 1 = 2` |
| 1 | `fib(2)` (recomputed from scratch) | `fib(1) + fib(0)` |
| 2 | `fib(1)` | `1` (base case) |
| 2 | `fib(0)` | `0` (base case) |
| 1 | `fib(2)` returns | `1 + 0 = 1` |
| 1 | `fib(4)` returns | `2 + 1 = 3` |
| 1 | `fib(3)` (recomputed from scratch) | `fib(2) + fib(1)` |
| 2 | `fib(2)` | `fib(1) + fib(0)` |
| 3 | `fib(1)` | `1` (base case) |
| 3 | `fib(0)` | `0` (base case) |
| 2 | `fib(2)` returns | `1 + 0 = 1` |
| 2 | `fib(1)` | `1` (base case) |
| 1 | `fib(3)` returns | `1 + 1 = 2` |
| 0 | `fib(5)` returns | `3 + 2 = 5` |

→ `fib(5) = 5` — note `fib(3)` and `fib(2)` each get recomputed from scratch multiple times; this duplication compounds as `n` grows.

### b. Bottom-up tabulation with a full DP array

Same recurrence as the optimal approach, but stored in a `dp[]` array of size `n + 1` instead of two rolling variables. A natural stepping stone toward the space-optimized version — useful if the sequence itself (not just the final value) needs to be inspected afterward.

**Time complexity:** O(n) — one pass filling the table.
**Space complexity:** O(n) — the full `dp` array is retained.
**When acceptable:** Fine under normal interview time pressure; only worth trading down to O(1) space if the interviewer specifically asks for it.

**Algorithm trace** (DP table filling → step table), input `n = 5`:

| i | dp[i-2] | dp[i-1] | dp[i] |
|---|---|---|---|
| 0 | — | — | 0 |
| 1 | — | — | 1 |
| 2 | 0 | 1 | 1 |
| 3 | 1 | 1 | 2 |
| 4 | 1 | 2 | 3 |
| 5 | 2 | 3 | 5 |

→ `dp[5] = 5`

### c. Closed-form formula (Binet's formula)

`F(n) = (φ^n − ψ^n) / √5`, where `φ = (1 + √5) / 2` and `ψ = (1 - √5) / 2`. Gives O(1) time in theory, but relies on floating-point arithmetic, so it's risky — precision errors can silently produce an off-by-one result as `n` grows, and it sidesteps the DP insight the problem is testing.

**Time complexity:** O(1) — a constant number of arithmetic operations (or O(log n) if computed safely via matrix exponentiation instead of floating point).
**Space complexity:** O(1).
**When acceptable:** Rarely appropriate to lead with in an interview — mostly a curiosity, or useful if asked to compute a single large `n` many times with precomputed constants.

**Algorithm trace** (formula computation), input `n = 5`:

| Term | Value |
|---|---|
| `√5` | `2.2360679...` |
| `φ = (1 + √5) / 2` | `1.6180339...` |
| `ψ = (1 - √5) / 2` | `-0.6180339...` |
| `φ^5` | `11.0901699...` |
| `ψ^5` | `-0.0901699...` |
| `(φ^5 − ψ^5) / √5` | `5.0000000...` → rounds to `5` |

→ `fib(5) = 5`
