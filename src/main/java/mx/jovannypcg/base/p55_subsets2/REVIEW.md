| | |
|---|---|
| **Solved on** | 2026-08-21 |
| **DSA Category** | Backtracking |

## 1. Your Solution Assessment

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    List<List<Integer>> subsets = new ArrayList<>();
    Deque<Integer> subset = new ArrayDeque<>();

    Arrays.sort(nums);

    backtracking(nums, subsets, subset, 0);

    return subsets;
}

void backtracking(
    int[] nums,
    List<List<Integer>> subsets,
    Deque<Integer> subset,
    int idx
) {
    if (idx >= nums.length) {
        subsets.add(new ArrayList<>(subset));
        return;
    }

    int num = nums[idx];

    // Decision: add current num to subset
    subset.addLast(num);
    backtracking(nums, subsets, subset, idx + 1);

    // Decision: remove current num
    // Skip duplicates
    subset.removeLast();

    while (idx < nums.length && nums[idx] == num) idx++;

    backtracking(nums, subsets, subset, idx);
}
```

**Correctness:** Handles all cases correctly, including every boundary from the constraints (13 tests pass: min/max element length, min/max value, all-identical inputs, and mixed duplicate groups). The key insight is baked into the "don't include `num`" branch: rather than simply recursing on `idx + 1`, it advances `idx` past every remaining occurrence of the same value before recursing. That guarantees a given value is only ever skipped *once* as a group at each recursion depth, instead of once per occurrence — which is exactly what prevents the same subset from being produced twice. Sorting up front is what makes equal values adjacent so this skip works.

**Code quality:** Clear and short, with a shape consistent with the combination-sum solutions elsewhere in this repo. The `// Decision: add current num to subset` and `// Decision: remove current num` / `// Skip duplicates` comments restate what the code already says (`subset.addLast(num)` and `subset.removeLast()` are self-explanatory); they could be dropped without losing anything. `backtracking` could also be `private` since it's only called internally.

**Time complexity:** O(n · 2^n), where n = `nums.length`. Sorting is O(n log n), dominated by the rest. Each call either includes `nums[idx]` and recurses on `idx + 1`, or skips a whole run of duplicates and recurses on the index past that run — both shrink the remaining problem size by at least 1, so the call tree has at most O(2^n) nodes. A subset is only recorded when `idx` reaches `nums.length`, and copying it via `new ArrayList<>(subset)` costs O(n), which is what pushes the bound from O(2^n) to O(n · 2^n).

**Space complexity:** O(n) auxiliary — the `subset` deque holds at most n elements and the recursion depth is at most n — plus O(n · 2^n) for the output itself, which any correct algorithm must produce.

**Algorithm trace** (`nums = [1, 2, 2]`)

Legend: `→ ADD x` = `subset.addLast(x)`, then recurse on `idx + 1` · `← REMOVE x` = `subset.removeLast()` on the way back up · `⤳ SKIP DUPES` = `idx` advances past every remaining occurrence of `x` before the second recursive call · `✓ RECORD` = base case hit (`idx >= nums.length`), copy saved

```
backtracking(idx=0, subset=[])
  num=nums[0]=1
  → ADD 1 → subset=[1]
    backtracking(idx=1, subset=[1])
      num=nums[1]=2
      → ADD 2 → subset=[1, 2]
        backtracking(idx=2, subset=[1, 2])
          num=nums[2]=2
          → ADD 2 → subset=[1, 2, 2]
            backtracking(idx=3, subset=[1, 2, 2]) ✓ RECORD [1, 2, 2]
          ← REMOVE 2 → subset=[1, 2]
          ⤳ SKIP DUPES (idx 2 → 3)
          backtracking(idx=3, subset=[1, 2]) ✓ RECORD [1, 2]
      ← REMOVE 2 → subset=[1]
      ⤳ SKIP DUPES (idx 1 → 3, both remaining 2s)
      backtracking(idx=3, subset=[1]) ✓ RECORD [1]
  ← REMOVE 1 → subset=[]
  ⤳ SKIP DUPES (idx 0 → 1, only one 1)
  backtracking(idx=1, subset=[])
    num=nums[1]=2
    → ADD 2 → subset=[2]
      backtracking(idx=2, subset=[2])
        num=nums[2]=2
        → ADD 2 → subset=[2, 2]
          backtracking(idx=3, subset=[2, 2]) ✓ RECORD [2, 2]
        ← REMOVE 2 → subset=[2]
        ⤳ SKIP DUPES (idx 2 → 3)
        backtracking(idx=3, subset=[2]) ✓ RECORD [2]
    ← REMOVE 2 → subset=[]
    ⤳ SKIP DUPES (idx 1 → 3, both remaining 2s)
    backtracking(idx=3, subset=[]) ✓ RECORD []

subsets = [[1,2,2], [1,2], [1], [2,2], [2], []]
```

Every `ADD` is bracketed by exactly one matching `REMOVE`, and every "exclude" branch is bracketed by a `SKIP DUPES` jump instead of a plain `idx + 1` — that's what turns the ordinary include/exclude subset tree into one that never revisits the same value as a fresh choice at a given depth.

## 2. Optimal Approach

Asymptotically this can't beat O(n · 2^n): the output can itself contain up to 2^n subsets, each up to length n to copy out, so any correct algorithm is bound by that. Your solution is already there. The more idiomatic way this problem is usually written, though — and the shape used for the plain Subsets problem elsewhere in this repo — is a `for` loop over "which index starts the next pick," recording the current subset on *every* call (not just at `idx >= nums.length`) and skipping adjacent duplicates within the loop instead of via a `while` jump between two recursive calls:

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> subsets = new ArrayList<>();
    backtrack(nums, 0, new ArrayDeque<>(), subsets);
    return subsets;
}

private void backtrack(int[] nums, int start, Deque<Integer> subset, List<List<Integer>> subsets) {
    subsets.add(new ArrayList<>(subset));

    for (int i = start; i < nums.length; i++) {
        if (i > start && nums[i] == nums[i - 1]) continue;

        subset.addLast(nums[i]);
        backtrack(nums, i + 1, subset, subsets);
        subset.removeLast();
    }
}
```

**Time complexity:** O(n · 2^n) — same bound as above, for the same reason (up to 2^n subsets, each O(n) to copy). The `i > start && nums[i] == nums[i - 1]` guard skips duplicate siblings in the loop rather than jumping over a run in a `while`, but doesn't change the worst-case bound.

**Space complexity:** O(n) auxiliary — the `subset` deque and recursion depth, both bounded by n — excluding the output.

**Algorithm trace** (`nums = [1, 2, 2]`)

Legend: same as section 1, plus `✗ SKIP` = loop guard rejects `nums[i]` as a duplicate sibling, no recursion

```
backtrack(start=0, subset=[]) ✓ RECORD []
  i=0 (nums[0]=1): → ADD 1 → subset=[1]
    backtrack(start=1, subset=[1]) ✓ RECORD [1]
      i=1 (nums[1]=2): → ADD 2 → subset=[1, 2]
        backtrack(start=2, subset=[1, 2]) ✓ RECORD [1, 2]
          i=2 (nums[2]=2): → ADD 2 → subset=[1, 2, 2]
            backtrack(start=3, subset=[1, 2, 2]) ✓ RECORD [1, 2, 2]
              (start=3 >= length, loop does not run)
          ← REMOVE 2 → subset=[1, 2]
      ← REMOVE 2 → subset=[1]
      i=2 (nums[2]=2): i>start(1) && nums[2]==nums[1] → ✗ SKIP
  ← REMOVE 1 → subset=[]
  i=1 (nums[1]=2): → ADD 2 → subset=[2]
    backtrack(start=2, subset=[2]) ✓ RECORD [2]
      i=2 (nums[2]=2): → ADD 2 → subset=[2, 2]
        backtrack(start=3, subset=[2, 2]) ✓ RECORD [2, 2]
      ← REMOVE 2 → subset=[2]
  ← REMOVE 2 → subset=[]
  i=2 (nums[2]=2): i>start(0) && nums[2]==nums[1] → ✗ SKIP

subsets = [[], [1], [1, 2], [1, 2, 2], [2], [2, 2]]
```

Same 6 subsets as your solution, produced in a different order (every subset is recorded on the way down here, instead of only at the deepest point of each path) — the problem allows any order, so this is not a discrepancy.

## 3. Alternative Approaches

### 3a. Group by value, choose a count per distinct value

Pre-group `nums` into `(value, count)` pairs (e.g. a `TreeMap<Integer, Integer>`), then backtrack over the *distinct* values: at each one, decide how many copies to include (`0` up to `count`), and move to the next distinct value. This mirrors the technique used for Combination Sum II in this repo and sidesteps index-skipping entirely, since duplicates are collapsed before the search starts. Recording only when `idx` reaches the end of `entries` — after every distinct value has been assigned a count, including 0 — guarantees exactly one record per combination of counts, with no double-counting.

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    TreeMap<Integer, Integer> counts = new TreeMap<>();
    for (int num : nums) counts.merge(num, 1, Integer::sum);

    List<Map.Entry<Integer, Integer>> entries = new ArrayList<>(counts.entrySet());
    List<List<Integer>> subsets = new ArrayList<>();
    backtrack(entries, 0, new ArrayDeque<>(), subsets);
    return subsets;
}

private void backtrack(
    List<Map.Entry<Integer, Integer>> entries,
    int idx,
    Deque<Integer> subset,
    List<List<Integer>> subsets
) {
    if (idx >= entries.size()) {
        subsets.add(new ArrayList<>(subset));
        return;
    }

    int value = entries.get(idx).getKey();
    int count = entries.get(idx).getValue();

    for (int use = 0; use <= count; use++) {
        backtrack(entries, idx + 1, subset, subsets);
        subset.addLast(value);
    }

    for (int use = 0; use <= count; use++) subset.removeLast();
}
```

**Time complexity:** Bounded by the same O(n · 2^n) worst case (all-distinct values collapses this back to the plain power-set tree), but when the input has many duplicates the branching factor per distinct value shrinks the practical search space well below the index-based versions above.
**Space complexity:** O(d) for the grouping map (d = number of distinct values) plus O(n) recursion depth, excluding the output.
**When acceptable:** Whenever the "how many of this value do I use" framing is more natural than "skip past equal indices" — also a natural setup if a follow-up asked to *count* subsets instead of enumerate them.

**Algorithm trace** (`nums = [1, 2, 2]` → grouped `{1: ×1, 2: ×2}`)

Legend: `use=k` = how many copies of the current distinct value this branch commits to · `→ ADD x` happens *after* the recursive call returns (count is chosen, then the value is appended for the next `use`) · `✓ RECORD` = base case hit (`idx >= entries.size()`), copy saved

```
backtrack(idx=0, subset=[])
  value=1 (×1)
  use=0: backtrack(idx=1, subset=[])
    value=2 (×2)
    use=0: backtrack(idx=2, subset=[]) ✓ RECORD []
      → ADD 2 → subset=[2]
    use=1: backtrack(idx=2, subset=[2]) ✓ RECORD [2]
      → ADD 2 → subset=[2, 2]
    use=2: backtrack(idx=2, subset=[2, 2]) ✓ RECORD [2, 2]
    ← REMOVE both 2s → subset=[]
  → ADD 1 → subset=[1]
  use=1: backtrack(idx=1, subset=[1])
    value=2 (×2)
    use=0: backtrack(idx=2, subset=[1]) ✓ RECORD [1]
      → ADD 2 → subset=[1, 2]
    use=1: backtrack(idx=2, subset=[1, 2]) ✓ RECORD [1, 2]
      → ADD 2 → subset=[1, 2, 2]
    use=2: backtrack(idx=2, subset=[1, 2, 2]) ✓ RECORD [1, 2, 2]
    ← REMOVE both 2s → subset=[1]
  ← REMOVE 1 → subset=[]

subsets = [[], [2], [2, 2], [1], [1, 2], [1, 2, 2]]
```

### 3b. Brute-force bitmask enumeration with a `Set` for de-duplication

Generate all 2^n subsets with a bitmask, and rely on a `Set<List<Integer>>` (built from the sorted element order) to collapse duplicate subsets after the fact instead of skipping them during the search.

```java
public List<List<Integer>> subsetsWithDup(int[] nums) {
    Arrays.sort(nums);
    int n = nums.length;
    Set<List<Integer>> seen = new LinkedHashSet<>();

    for (int mask = 0; mask < (1 << n); mask++) {
        List<Integer> subset = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) subset.add(nums[i]);
        }

        seen.add(subset);
    }

    return new ArrayList<>(seen);
}
```

**Time complexity:** O(n · 2^n) — every one of the 2^n masks is turned into a subset in O(n), then hashed into the `Set` in O(n).
**Space complexity:** O(n · 2^n) — the `Set` transiently holds up to 2^n subsets (even the duplicate ones get built before being rejected by `add`), each up to length n. Heavier than the backtracking approaches, which only ever hold one in-progress subset at a time.
**When acceptable:** Only for small n (comfortably true here, since `n <= 10` per the constraints means at most 1024 masks) — but it needs no insight into the duplicate-skipping trick at all: sort, brute-force every subset, let the `Set` handle de-duplication. Reasonable as a first pass under interview time pressure.

**Algorithm trace** (step table, `nums = [1, 2, 2]`)

| mask (binary) | included indices | subset | kept? |
|---|---|---|---|
| 000 | — | [] | **yes → []** |
| 001 | {0} | [1] | **yes → [1]** |
| 010 | {1} | [2] | **yes → [2]** |
| 011 | {0,1} | [1, 2] | **yes → [1, 2]** |
| 100 | {2} | [2] | duplicate of mask 010 → `Set` drops it |
| 101 | {0,2} | [1, 2] | duplicate of mask 011 → `Set` drops it |
| 110 | {1,2} | [2, 2] | **yes → [2, 2]** |
| 111 | {0,1,2} | [1, 2, 2] | **yes → [1, 2, 2]** |

→ `subsets = [[], [1], [2], [1, 2], [2, 2], [1, 2, 2]]`
