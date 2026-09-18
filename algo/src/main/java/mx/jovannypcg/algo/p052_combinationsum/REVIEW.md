| | |
|---|---|
| **Solved on** | 2026-08-19 |
| **DSA Category** | Backtracking |

## 1. Your Solution Assessment

**Correctness:** The implementation is correct. All 9 tests pass, including the boundary cases from the constraints (minimum target/candidate value, maximum candidate value equal to the target). The trickiest part of this solution is the order of the two guard checks at the top of `backtracking`: `idx >= candidates.length` is checked *before* `sum == target`. At first glance that looks like it could hide a valid match — but it can't. A match is only ever detected on the "reuse the same candidate" recursive call (`backtracking(candidates, target, combinations, combination, sum, idx)` with `idx` unchanged), and `idx` there is always the same value that was already known to be in range at the start of the current frame. The "move to the next candidate" call (`idx + 1`) always carries the sum from *before* the current candidate was added — which, by construction, is strictly less than `target` (otherwise the current frame would never have entered the `else` branch). So whenever `idx` reaches `candidates.length`, `sum` is guaranteed to be less than `target`, and no valid combination is ever lost.

**Code quality:** Clean and easy to follow. `Deque<Integer>` as a stack for the in-progress combination is a good choice, and the push/recurse/pop/recurse pattern is the standard idiom for this kind of backtracking. Two small nits: `backtracking` could be `private` since it's an implementation detail, not part of the public API; and putting the `sum == target` check first (before the `idx` bound check) would make the "why doesn't this drop matches" reasoning above unnecessary for a future reader — the current order works but relies on the invariant rather than being self-evident.

**Time complexity:** O(N^(T/M + 1)), where N is the number of candidates, T is the target, and M is the smallest candidate value. At each recursion level the code branches into "reuse this candidate" or "move to the next one", and the maximum depth of the "reuse" chain is bounded by `T / M` (how many times the smallest candidate can be added before exceeding the target).

**Space complexity:** O(T / M) for the recursion stack and the `combination` deque, which is bounded by the same maximum depth — this excludes the space needed for the output list itself.

**Algorithm trace** (call stack table, `candidates = [2, 3, 6, 7]`, `target = 7`):

| Step | idx | Push | Combination | Sum | Outcome |
|---|---|---|---|---|---|
| 1 | 0 | 2 | [2] | 2 | sum < target → recurse (same idx) |
| 2 | 0 | 2 | [2, 2] | 4 | sum < target → recurse (same idx) |
| 3 | 0 | 2 | [2, 2, 2] | 6 | sum < target → recurse (same idx) |
| 4 | 0 | 2 | [2, 2, 2, 2] | 8 | sum > target → prune |
| 5 | 1 | 3 | [2, 2, 2, 3] | 9 | sum > target → prune |
| 6 | 2 | 6 | [2, 2, 2, 6] | 12 | sum > target → prune |
| 7 | 3 | 7 | [2, 2, 2, 7] | 13 | sum > target → prune, no candidates left |
| 8 | 1 | 3 | [2, 2, 3] | 7 | **sum == target → record [2, 2, 3]** |
| 9 | 2 | 6 | [2, 2, 6] | 10 | sum > target → prune |
| 10 | 3 | 7 | [2, 2, 7] | 11 | sum > target → prune, no candidates left |
| 11 | 1 | 3 | [2, 3] | 5 | sum < target → recurse (same idx) |
| 12 | 1 | 3 | [2, 3, 3] | 8 | sum > target → prune |
| 13 | 2 | 6 | [2, 3, 6] | 11 | sum > target → prune |
| 14 | 3 | 7 | [2, 3, 7] | 12 | sum > target → prune, no candidates left |
| 15 | 2 | 6 | [2, 6] | 8 | sum > target → prune |
| 16 | 3 | 7 | [2, 7] | 9 | sum > target → prune, no candidates left |
| 17 | 1 | 3 | [3] | 3 | sum < target → recurse (same idx) |
| 18 | 1 | 3 | [3, 3] | 6 | sum < target → recurse (same idx) |
| 19 | 1 | 3 | [3, 3, 3] | 9 | sum > target → prune |
| 20 | 2 | 6 | [3, 3, 6] | 12 | sum > target → prune |
| 21 | 3 | 7 | [3, 3, 7] | 13 | sum > target → prune, no candidates left |
| 22 | 2 | 6 | [3, 6] | 9 | sum > target → prune |
| 23 | 3 | 7 | [3, 7] | 10 | sum > target → prune, no candidates left |
| 24 | 2 | 6 | [6] | 6 | sum < target → recurse (same idx) |
| 25 | 2 | 6 | [6, 6] | 12 | sum > target → prune |
| 26 | 3 | 7 | [6, 7] | 13 | sum > target → prune, no candidates left |
| 27 | 3 | 7 | [7] | 7 | **sum == target → record [7]** |

→ `combinations = [[2,2,3], [7]]`

## 2. Optimal Approach

For this problem, the user's backtracking approach **is** the optimal one — every algorithm that enumerates all valid combinations must, in the worst case, visit every node of this same "reuse or advance" recursion tree, so no asymptotic improvement is possible. The only meaningful tweak is sorting `candidates` first so a branch can stop trying larger candidates the moment the running sum overshoots, instead of pushing each one just to immediately prune it (see Alternative Approach A below for the concrete gain).

```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<Integer>> combinations = new ArrayList<>();
    backtrack(candidates, target, 0, 0, new ArrayDeque<>(), combinations);
    return combinations;
}

private void backtrack(
    int[] candidates,
    int target,
    int idx,
    int sum,
    Deque<Integer> combination,
    List<List<Integer>> combinations
) {
    if (sum == target) {
        combinations.add(new ArrayList<>(combination));
        return;
    }

    if (sum > target || idx >= candidates.length) {
        return;
    }

    combination.addLast(candidates[idx]);
    backtrack(candidates, target, idx, sum + candidates[idx], combination, combinations);
    combination.removeLast();

    backtrack(candidates, target, idx + 1, sum, combination, combinations);
}
```

**Time complexity:** O(N^(T/M + 1)) — same bound as above; this is the inherent cost of the search space, not something a smarter algorithm can avoid.

**Space complexity:** O(T / M) extra space for the recursion stack and `combination`, excluding the output.

**Algorithm trace:** identical recursion tree to the one above — see the call stack table in section 1.

## 3. Alternative Approaches

### a. Sorted candidates with early termination

Sort `candidates` ascending first. Inside the loop/recursion, as soon as `sum + candidates[idx] > target`, stop trying any further candidates at this level immediately — since the array is sorted, every remaining candidate is even larger and would also overshoot, so there's no need to advance `idx` and try each one individually.

```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    Arrays.sort(candidates);
    List<List<Integer>> combinations = new ArrayList<>();
    backtrack(candidates, target, 0, 0, new ArrayDeque<>(), combinations);
    return combinations;
}

private void backtrack(
    int[] candidates,
    int target,
    int idx,
    int sum,
    Deque<Integer> combination,
    List<List<Integer>> combinations
) {
    if (sum == target) {
        combinations.add(new ArrayList<>(combination));
        return;
    }

    for (int i = idx; i < candidates.length; i++) {
        if (sum + candidates[i] > target) break;

        combination.addLast(candidates[i]);
        backtrack(candidates, target, i, sum + candidates[i], combination, combinations);
        combination.removeLast();
    }
}
```

**Time complexity:** O(N^(T/M + 1)) in the worst case — same bound as the user's solution, since the shape of the valid search space is unchanged.
**Space complexity:** O(T / M) extra space, same as the user's solution.
**When acceptable:** This is a strict improvement in practice (fewer wasted calls that immediately prune) at no extra cost, so it's worth knowing as the "polished" version of the same idea — but the user's unsorted version is already correct and asymptotically equivalent, so this is a nice-to-have rather than a requirement.

**Algorithm trace** (call stack table, `candidates = [2, 3, 6, 7]` sorted, `target = 7`, first divergence from section 1's trace):

| Step | idx | Combination | Sum | Outcome |
|---|---|---|---|---|
| 1 | 0 | [2] | 2 | recurse (i = 0) |
| 2 | 0 | [2, 2] | 4 | recurse (i = 0) |
| 3 | 0 | [2, 2, 2] | 6 | recurse (i = 0) |
| — | 0 | (would be [2,2,2,2], sum 8) | 8 | `8 > 7` → **break loop immediately**, no calls for 3, 6, or 7 |
| 4 | 1 | [2, 2, 3] | 7 | **sum == target → record [2, 2, 3]** |

→ candidates 6 and 7 are never even pushed at this level, unlike steps 5–7 in section 1's trace.

### b. Bottom-up dynamic programming (build combinations per achievable sum)

Maintain `dp[s]` = list of all combinations (as non-decreasing lists) that sum to `s`, for `s` from `0` to `target`. For each candidate `c` and each sum `s >= c`, extend every combination in `dp[s - c]` whose last element is `<= c` with `c`, and add the result to `dp[s]`. This mirrors the classic "coin change — count/list combinations" DP.

```java
public List<List<Integer>> combinationSum(int[] candidates, int target) {
    List<List<List<Integer>>> dp = new ArrayList<>();
    for (int s = 0; s <= target; s++) dp.add(new ArrayList<>());
    dp.get(0).add(new ArrayList<>());

    for (int c : candidates) {
        for (int s = c; s <= target; s++) {
            for (List<Integer> combo : dp.get(s - c)) {
                if (combo.isEmpty() || combo.get(combo.size() - 1) <= c) {
                    List<Integer> extended = new ArrayList<>(combo);
                    extended.add(c);
                    dp.get(s).add(extended);
                }
            }
        }
    }

    return dp.get(target);
}
```

**Time complexity:** O(N · T · K), where N is the number of candidates, T is the target, and K is the number of combinations stored per sum — each combination copy costs O(T / M) in the worst case, so this can be noticeably slower in practice than backtracking despite covering the same search space.
**Space complexity:** O(T · K) to store every intermediate combination list for every sum from 0 to `target`, which is strictly more than the backtracking approaches (they only ever hold one combination at a time, plus the output).
**When acceptable:** Handy if the interviewer specifically wants to see a DP formulation, or if a follow-up asks for the *count* of combinations per sum (in which case `dp[s]` collapses to a single integer and the space blows up disappears) — but for producing the actual list of combinations, backtracking is the more natural and memory-efficient fit.

**Algorithm trace** (step table, `candidates = [2, 3, 6, 7]`, `target = 7`, showing `dp[s]` after processing each candidate):

| Candidate processed | dp[2] | dp[3] | dp[4] | dp[5] | dp[6] | dp[7] |
|---|---|---|---|---|---|---|
| — (init) | [] | [] | [] | [] | [] | [] |
| 2 | [[2]] | [] | [[2,2]] | [] | [[2,2,2]] | [] |
| 3 | [[2]] | [[3]] | [[2,2]] | [[2,3]] | [[2,2,2],[3,3]] | [[2,2,3]] |
| 6 | [[2]] | [[3]] | [[2,2]] | [[2,3]] | [[2,2,2],[3,3],[6]] | [[2,2,3]] |
| 7 | [[2]] | [[3]] | [[2,2]] | [[2,3]] | [[2,2,2],[3,3],[6]] | [[2,2,3],[7]] |

`dp[4] = [[2,2]]` (built while processing candidate 2) already feeds `dp[7]` during the very next candidate: processing candidate 3 extends it into `[2,2,3]`, landing in `dp[7]` right away — one candidate before `dp[7]` gets its second entry, `[7]`, from processing candidate 7 itself. The final `dp[7] = [[2,2,3], [7]]` matches the expected output.
