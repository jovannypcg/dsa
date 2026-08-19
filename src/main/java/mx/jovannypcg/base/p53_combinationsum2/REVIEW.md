| | |
|---|---|
| **Solved on** | 2026-08-19 |
| **DSA Category** | Backtracking |

## 1. Your Solution Assessment

**Correctness:** The implementation is correct. All 14 tests pass, including the boundary cases from the constraints (minimum/maximum candidate values, minimum/maximum target, and inputs made entirely of duplicate values). Sorting `candidates` up front is what makes the duplicate-skipping possible: after pushing a candidate at `idx` and exploring every combination that includes it, the `while (idx < candidates.length && candidates[idx] == candidate) idx++;` loop jumps past every remaining occurrence of that same value before trying the "don't include it" branch — so the same value is never re-chosen as the *first* new element added at a given recursion depth, which is exactly what prevents duplicate combinations.

**Code quality:** Clean and readable, and the push/recurse/pop/skip-duplicates/recurse structure is the right shape for this problem. Two small nits: `combinations.add(...)` for the `sum == target` case appears twice — once in the `idx >= candidates.length` guard, once in the main `if/else if/else` chain — which is exactly the redundancy flagged below. Separately, `Arrays.sort(candidates)` mutates the caller's array in place; harmless for this exercise, but worth being aware of as a side effect on the input.

**Time complexity:** O(2^N), where N is `candidates.length`. Each recursion level branches in exactly two directions — "include `candidates[idx]`" or "exclude it and every duplicate of it" — so the call tree has at most 2^N leaves; the `sum > target` prune cuts this in practice but not in the worst-case bound.

**Space complexity:** O(N) for the recursion stack and the `combination` deque, bounded by the array length — this excludes the space needed for the output list itself.

**Algorithm trace** (call stack table, `candidates = [2, 5, 2, 1, 2]` → sorted `[1, 2, 2, 2, 5]`, `target = 5`):

| Step | idx | Push | Combination | Sum | Outcome |
|---|---|---|---|---|---|
| 1 | 0 | 1 | [1] | 1 | sum < target → recurse (idx 1) |
| 2 | 1 | 2 | [1, 2] | 3 | sum < target → recurse (idx 2) |
| 3 | 2 | 2 | [1, 2, 2] | 5 | **sum == target → record [1, 2, 2]** |
| 4 | 4 | 5 | [1, 2, 5] | 8 | idx reaches end of array, sum ≠ target → no match |
| 5 | 4 | 5 | [1, 5] | 6 | duplicates at idx 1–3 skipped (all equal 2); idx reaches end, sum ≠ target → no match |
| 6 | 1 | 2 | [2] | 2 | duplicate at idx 0 skipped (the 1 already tried); sum < target → recurse (idx 2) |
| 7 | 2 | 2 | [2, 2] | 4 | sum < target → recurse (idx 3) |
| 8 | 3 | 2 | [2, 2, 2] | 6 | sum > target → prune |
| 9 | 4 | 5 | [2, 2, 5] | 9 | idx reaches end, sum ≠ target → no match |
| 10 | 4 | 5 | [2, 5] | 7 | duplicates at idx 2–3 skipped; idx reaches end, sum ≠ target → no match |
| 11 | 4 | 5 | [5] | 5 | duplicates at idx 1–3 skipped (all equal 2); **sum == target → record [5]** |

→ `combinations = [[1, 2, 2], [5]]`

### Improved version of your solution

The two guards at the top of `backtracking` — the `idx >= candidates.length` block and the `if (sum == target)` branch right below it — both end up deciding the same question ("did we hit the target?"), just for two different reasons to be in that state (ran off the end of the array vs. still have candidates left). Checking `sum == target` once, unconditionally, before anything else removes the duplication and lets `idx >= candidates.length` go back to doing exactly one job: guarding the `candidates[idx]` access.

```java
void backtracking(
    int[] candidates,
    int target,
    List<List<Integer>> combinations,
    Deque<Integer> combination,
    int sum,
    int idx
) {
    if (sum == target) {
        combinations.add(new ArrayList<>(combination));
        return;
    }

    if (sum > target || idx >= candidates.length) {
        return;
    }

    int candidate = candidates[idx];
    combination.addLast(candidate);
    sum += candidate;

    backtracking(candidates, target, combinations, combination, sum, idx + 1);

    sum -= candidate;
    combination.removeLast();

    while (idx < candidates.length && candidates[idx] == candidate) idx++;

    backtracking(candidates, target, combinations, combination, sum, idx);
}
```

Behavior is unchanged (it passes the same 14 tests): whenever the original code reached `idx >= candidates.length` with `sum == target`, this version already returned via the first `if` before `idx` mattered at all; whenever it reached that guard with `sum < target`, this version falls through to the merged `sum > target || idx >= candidates.length` check and returns the same way. The win is structural — one place decides success, one place decides "nothing left to try" — rather than a behavior change.

## 2. Optimal Approach

The asymptotic cost is inherent to the problem — any algorithm that enumerates every valid combination has to explore a search space shaped like this include/exclude tree — so the improved version above is already optimal. The idiomatic way this problem is usually written, though, is a `for` loop over "which candidate starts the next pick" instead of a binary include/exclude recursion, combined with sorting so the loop can `break` the instant a candidate would overshoot the target (rather than pushing it just to prune it one level down):

```java
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    List<List<Integer>> combinations = new ArrayList<>();
    backtrack(candidates, target, 0, new ArrayDeque<>(), combinations);
    return combinations;
}

private void backtrack(
    int[] candidates,
    int remaining,
    int start,
    Deque<Integer> combination,
    List<List<Integer>> combinations
) {
    if (remaining == 0) {
        combinations.add(new ArrayList<>(combination));
        return;
    }

    for (int i = start; i < candidates.length; i++) {
        if (i > start && candidates[i] == candidates[i - 1]) continue;
        if (candidates[i] > remaining) break;

        combination.addLast(candidates[i]);
        backtrack(candidates, remaining - candidates[i], i + 1, combination, combinations);
        combination.removeLast();
    }
}
```

**Time complexity:** O(2^N) — same bound as above; this is the inherent cost of the search space, not something a smarter algorithm can avoid. The `i > start && candidates[i] == candidates[i - 1]` skip and the `break` on overshoot reduce the constant factor but not the worst case.

**Space complexity:** O(N) for the recursion stack and `combination`, excluding the output.

**Algorithm trace** (call stack table, `candidates = [1, 2, 2, 2, 5]`, `target = 5`):

| Step | start | i | Push | Combination | Remaining | Outcome |
|---|---|---|---|---|---|---|
| 1 | 0 | 0 | 1 | [1] | 4 | recurse deeper (start = 1) |
| 2 | 1 | 1 | 2 | [1, 2] | 2 | recurse deeper (start = 2) |
| 3 | 2 | 2 | 2 | [1, 2, 2] | 0 | **remaining == 0 → record [1, 2, 2]** |
| — | 2 | 3 | — | [1, 2] | 2 | `candidates[3] == candidates[2]` → skip |
| — | 2 | 4 | — | [1, 2] | 2 | `candidates[4]=5 > remaining(2)` → break loop |
| — | 1 | 2 | — | [1] | 4 | `candidates[2] == candidates[1]` → skip |
| — | 1 | 3 | — | [1] | 4 | `candidates[3] == candidates[1]` → skip |
| — | 1 | 4 | — | [1] | 4 | `candidates[4]=5 > remaining(4)` → break loop |
| 4 | 0 | 1 | 2 | [2] | 3 | recurse deeper (start = 2) |
| 5 | 2 | 2 | 2 | [2, 2] | 1 | recurse deeper (start = 3) |
| — | 3 | 3 | — | [2, 2] | 1 | `candidates[3]=2 > remaining(1)` → break loop, no match |
| — | 0 | 2 | — | [] | 5 | `candidates[2] == candidates[1]` → skip |
| — | 0 | 3 | — | [] | 5 | `candidates[3] == candidates[1]` → skip |
| 6 | 0 | 4 | 5 | [5] | 0 | **remaining == 0 → record [5]** |

→ `combinations = [[1, 2, 2], [5]]`

## 3. Alternative Approaches

### a. Group by value, choose a count per distinct candidate

Instead of walking index by index and skipping runs of equal values, pre-group `candidates` into `(value, count)` pairs (e.g. a `TreeMap<Integer, Integer>`), then backtrack over the *distinct* values: at each one, decide how many copies to use (`0` up to `min(count, remaining / value)`), and move to the next distinct value. This sidesteps index-skipping entirely because duplicates are collapsed before the search starts.

```java
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    TreeMap<Integer, Integer> counts = new TreeMap<>();
    for (int c : candidates) counts.merge(c, 1, Integer::sum);

    List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(counts.entrySet());
    List<List<Integer>> combinations = new ArrayList<>();
    backtrack(entries, 0, target, new ArrayDeque<>(), combinations);
    return combinations;
}

private void backtrack(
    List<Map.Entry<Integer, Integer>> entries,
    int idx,
    int remaining,
    Deque<Integer> combination,
    List<List<Integer>> combinations
) {
    if (remaining == 0) {
        combinations.add(new ArrayList<>(combination));
        return;
    }
    if (idx >= entries.size()) return;

    int value = entries.get(idx).getKey();
    int count = entries.get(idx).getValue();

    for (int use = 0; use <= count && use * value <= remaining; use++) {
        for (int k = 0; k < use; k++) combination.addLast(value);
        backtrack(entries, idx + 1, remaining - use * value, combination, combinations);
        for (int k = 0; k < use; k++) combination.removeLast();
    }
}
```

**Time complexity:** Bounded by the same O(2^N) worst case (all distinct values collapses this back to the plain include/exclude tree), but when the input has many duplicates — likely given the constraints (`candidates[i] <= 50`, length up to `100`) — the branching factor per distinct value shrinks the practical search space well below the index-based versions.
**Space complexity:** O(D) for the grouping map (D = number of distinct values) plus O(N) recursion depth, excluding the output.
**When acceptable:** Whenever the "how many of this value do I use" framing is more natural than "skip past equal indices" — it also sets up cleanly for a follow-up like "count the combinations" (the `TreeMap` becomes the input to a DP over counts instead of a full enumeration).

**Algorithm trace** (call stack table, `candidates = [1, 2, 2, 2, 5]` → grouped `{1: ×1, 2: ×3, 5: ×1}`, `target = 5`; condensed to the branches that matter):

| Depth | Value (available) | use chosen | Combination | Remaining | Outcome |
|---|---|---|---|---|---|
| 0 | 1 (×1) | 0 | [] | 5 | skip the only 1, continue to value 2 |
| 1 | 2 (×3) | 0 | [] | 5 | skip all 2s, continue to value 5 |
| 2 | 5 (×1) | 1 | [5] | 0 | **remaining == 0 → record [5]** |
| 0 | 1 (×1) | 1 | [1] | 4 | use the 1, continue to value 2 |
| 1 | 2 (×3) | 2 | [1, 2, 2] | 0 | **remaining == 0 → record [1, 2, 2]** (value 5 never even considered) |

→ `combinations = [[5], [1, 2, 2]]` — same set as sections 1 and 2, found in a different order because duplicates are grouped by value instead of walked index by index.

### b. Brute-force subset enumeration with a `Set` for de-duplication

Generate every one of the 2^N subsets with a bitmask, sum each one, and rely on a `Set<List<Integer>>` (built from the sorted candidate order) to collapse duplicate combinations after the fact instead of skipping them during the search.

```java
public List<List<Integer>> combinationSum2(int[] candidates, int target) {
    Arrays.sort(candidates);
    int n = candidates.length;
    Set<List<Integer>> seen = new LinkedHashSet<>();

    for (int mask = 0; mask < (1 << n); mask++) {
        int sum = 0;
        List<Integer> combo = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {
                combo.add(candidates[i]);
                sum += candidates[i];
            }
        }

        if (sum == target) seen.add(combo);
    }

    return new ArrayList<>(seen);
}
```

**Time complexity:** O(2^N · N) — every subset is generated and summed in O(N), plus hashing the resulting list for the `Set`.
**Space complexity:** O(2^N · N) worst case — the `Set` can hold up to every distinct combination, each of length up to N, on top of the discarded duplicate lists built along the way. Clearly heavier than the backtracking approaches, which only ever hold one in-progress combination.
**When acceptable:** Only for toy inputs — `candidates.length` up to `100` per the constraints makes `2^100` completely infeasible. Still a reasonable first pass under interview time pressure, since it requires no insight into the duplicate-skipping trick at all: sort, brute-force every subset, let the `Set` handle de-duplication.

**Algorithm trace** (step table, smaller example for a readable mask range — `candidates = [1, 2, 2]`, `target = 3`):

| mask (binary) | included indices | subset | sum | kept? |
|---|---|---|---|---|
| 000 | — | [] | 0 | no |
| 001 | {0} | [1] | 1 | no |
| 010 | {1} | [2] | 2 | no |
| 011 | {0,1} | [1, 2] | 3 | **yes → [1, 2]** |
| 100 | {2} | [2] | 2 | no |
| 101 | {0,2} | [1, 2] | 3 | yes, but duplicate of [1, 2] → `Set` drops it |
| 110 | {1,2} | [2, 2] | 4 | no |
| 111 | {0,1,2} | [1, 2, 2] | 6 | no |

→ `combinations = [[1, 2]]`
