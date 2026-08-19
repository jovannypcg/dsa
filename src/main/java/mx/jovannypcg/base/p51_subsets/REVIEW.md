| | |
|---|---|
| **Solved on** | 2026-08-18 |
| **DSA Category** | Backtracking |

## 1. Your Solution Assessment

**Correctness:** The implementation is correct. For every index it explores both the "include `nums[idx]`" and "exclude `nums[idx]`" branches, and it snapshots `subset` into a new `ArrayList` only at the base case (`idx >= nums.length`), so no partially-built or mutated list ever leaks into `out`. Because `nums` is guaranteed to have unique elements, each root-to-leaf path through the include/exclude tree produces a distinct subset, so the "no duplicate subsets" requirement is satisfied for free. All 22 tests pass, including the 1- and 10-element boundary cases from the constraints.

**Code quality:** Clean and idiomatic. Using `Deque<Integer>` as a stack (`addLast`/`removeLast`) for the in-progress subset is a good choice over `List.add`/`remove(size - 1)` — it communicates the "push/pop" intent directly. Naming (`out`, `subset`, `idx`) is concise and clear. The helper method separates the public API from the recursive mechanics, which is idiomatic for backtracking solutions.

**Time complexity:** O(n · 2ⁿ). The recursion forms a full binary tree of depth n (each call branches into include/exclude), giving 2ⁿ leaves and O(2ⁿ) total calls. At each leaf, copying `subset` into a new `ArrayList` costs O(n). Total: O(n · 2ⁿ).

**Space complexity:** O(n) of extra space beyond the output — the recursion stack and the `subset` deque both hold at most n elements at any point. The output itself (`out`) necessarily holds 2ⁿ subsets averaging O(n) elements each, i.e. O(n · 2ⁿ), but that's required output size, not extra working space.

**Algorithm trace** (call stack table, `nums = [1, 2, 3]`):

| Depth (idx) | Call — subset on entry | Action | Subset after action | Output added |
|---|---|---|---|---|
| 0 | dfs(0, []) | include 1 | [1] | — |
| 1 | dfs(1, [1]) | include 2 | [1, 2] | — |
| 2 | dfs(2, [1, 2]) | include 3 | [1, 2, 3] | — |
| 3 | dfs(3, [1, 2, 3]) | idx ≥ len → leaf | [1, 2, 3] | **[1, 2, 3]** |
| 2 | dfs(2, [1, 2]) | exclude 3 | [1, 2] | — |
| 3 | dfs(3, [1, 2]) | idx ≥ len → leaf | [1, 2] | **[1, 2]** |
| 1 | dfs(1, [1]) | exclude 2 | [1] | — |
| 2 | dfs(2, [1]) | include 3 | [1, 3] | — |
| 3 | dfs(3, [1, 3]) | idx ≥ len → leaf | [1, 3] | **[1, 3]** |
| 2 | dfs(2, [1]) | exclude 3 | [1] | — |
| 3 | dfs(3, [1]) | idx ≥ len → leaf | [1] | **[1]** |
| 0 | dfs(0, []) | exclude 1 | [] | — |
| 1 | dfs(1, []) | include 2 | [2] | — |
| 2 | dfs(2, [2]) | include 3 | [2, 3] | — |
| 3 | dfs(3, [2, 3]) | idx ≥ len → leaf | [2, 3] | **[2, 3]** |
| 2 | dfs(2, [2]) | exclude 3 | [2] | — |
| 3 | dfs(3, [2]) | idx ≥ len → leaf | [2] | **[2]** |
| 1 | dfs(1, []) | exclude 2 | [] | — |
| 2 | dfs(2, []) | include 3 | [3] | — |
| 3 | dfs(3, [3]) | idx ≥ len → leaf | [3] | **[3]** |
| 2 | dfs(2, []) | exclude 3 | [] | — |
| 3 | dfs(3, []) | idx ≥ len → leaf | [] | **[]** |

→ `out = [[1,2,3], [1,2], [1,3], [1], [2,3], [2], [3], []]`

## 2. Optimal Approach

This problem's output has 2ⁿ subsets, so no algorithm can beat O(n · 2ⁿ) time or O(2ⁿ) subset count — the work is asymptotically dominated by simply writing the output. The include/exclude DFS backtracking the user wrote **is** an optimal approach: at every index it makes a binary choice (take it or don't), and the base case fires exactly once per root-to-leaf path.

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, 0, new ArrayDeque<>(), result);
    return result;
}

private void backtrack(int[] nums, int idx, Deque<Integer> current, List<List<Integer>> result) {
    if (idx == nums.length) {
        result.add(new ArrayList<>(current));
        return;
    }

    current.addLast(nums[idx]);
    backtrack(nums, idx + 1, current, result);
    current.removeLast();

    backtrack(nums, idx + 1, current, result);
}
```

**Time complexity:** O(n · 2ⁿ) — 2ⁿ leaves in the recursion tree, O(n) to materialize each subset.

**Space complexity:** O(n) extra space (recursion depth + `current`), beyond the required O(n · 2ⁿ) output.

**Algorithm trace:** identical recursion tree to the one above — see the call stack table in section 1.

## 3. Alternative Approaches

### a. Iterative bitmask

For every integer `mask` from `0` to `2ⁿ - 1`, treat each bit of `mask` as "include `nums[i]`" (bit set) or "exclude `nums[i]`" (bit clear). This maps each of the 2ⁿ masks to exactly one subset, with no recursion needed.

```java
public List<List<Integer>> subsets(int[] nums) {
    int n = nums.length;
    List<List<Integer>> result = new ArrayList<>();

    for (int mask = 0; mask < (1 << n); mask++) {
        List<Integer> subset = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            if ((mask & (1 << i)) != 0) {
                subset.add(nums[i]);
            }
        }

        result.add(subset);
    }

    return result;
}
```

**Time complexity:** O(n · 2ⁿ) — 2ⁿ masks, O(n) bit-scan per mask.
**Space complexity:** O(n) extra space (the per-mask `subset` buffer), beyond the O(n · 2ⁿ) output.
**When acceptable:** Always a solid choice — same complexity as backtracking but avoids recursion entirely, which some interviewers prefer for its explicitness. Particularly handy if you also need each subset's bitmask (e.g. for further combinatorial processing).

**Algorithm trace** (step table, `nums = [1, 2, 3]`, n = 3):

| mask | binary | subset built |
|---|---|---|
| 0 | 000 | [] |
| 1 | 001 | [1] |
| 2 | 010 | [2] |
| 3 | 011 | [1, 2] |
| 4 | 100 | [3] |
| 5 | 101 | [1, 3] |
| 6 | 110 | [2, 3] |
| 7 | 111 | [1, 2, 3] |

### b. Iterative cascading (build up subset list)

Start with `result = [[]]`. For each number in `nums`, take every subset currently in `result`, copy it, append the new number, and add that copy back to `result`. After processing all numbers, `result` holds the full power set.

```java
public List<List<Integer>> subsets(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>());

    for (int num : nums) {
        int size = result.size();

        for (int i = 0; i < size; i++) {
            List<Integer> extended = new ArrayList<>(result.get(i));
            extended.add(num);
            result.add(extended);
        }
    }

    return result;
}
```

**Time complexity:** O(n · 2ⁿ) — the subset list doubles at each of the n steps, and each new subset costs O(n) to copy in the worst case.
**Space complexity:** O(n · 2ⁿ) total (this approach doesn't separate "extra" space from output — every intermediate list is retained in `result`).
**When acceptable:** Nice to know as a non-recursive, non-bitwise alternative; reads intuitively as "the power set after adding element k is the power set before, plus that same set with element k appended to each member." Good fallback if backtracking or bitmasking don't come to mind under interview pressure.

**Algorithm trace** (step table, `nums = [1, 2, 3]`):

| num processed | result before | result after |
|---|---|---|
| — | — | [[]] |
| 1 | [[]] | [[], [1]] |
| 2 | [[], [1]] | [[], [1], [2], [1,2]] |
| 3 | [[], [1], [2], [1,2]] | [[], [1], [2], [1,2], [3], [1,3], [2,3], [1,2,3]] |
