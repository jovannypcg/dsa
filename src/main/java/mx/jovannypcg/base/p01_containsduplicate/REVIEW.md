# Review — Contains Duplicate

| | |
|---|---|
| **Solved on** | 2026-06-09 |
| **DSA Category** | Arrays & Hashing |

---

## 1. Your Solution Assessment

**Correctness:** Handles all cases correctly: empty array (early return), single element, negatives, large values, and duplicates anywhere in the array. The `null` guard is a safe defensive touch even though the constraints don't mention null input.

**Code quality:** Clean and readable. Naming is clear (`seen`, `current`). The early return on an empty array is a nice optimization that avoids allocating the `HashSet` unnecessarily. One minor note: the index-based loop (`for (int idx = 0; ...)`) can be replaced with an enhanced for-each since `idx` itself is never needed beyond accessing `nums[idx]`.

**Time complexity:** O(n) — a single pass through the array; each `HashSet.contains` and `HashSet.add` is O(1) amortized.

**Space complexity:** O(n) — in the worst case (no duplicates), every element is inserted into the `HashSet`.

**Algorithm trace** — Input: `nums = [1, 2, 3, 1]`

| i | nums[i] | seen before? | action | seen after |
|---|---------|-------------|--------|------------|
| 0 | 1 | {} → No | add | {1} |
| 1 | 2 | {1} → No | add | {1, 2} |
| 2 | 3 | {1, 2} → No | add | {1, 2, 3} |
| 3 | 1 | {1, 2, 3} → **Yes** | return true | — |

→ return `true`

---

## 2. Optimal Approach

Your solution is already optimal. A `HashSet` gives O(1) average-case lookup and insertion, achieving the best possible time complexity for this problem.

**Strategy:** Iterate through the array. For each element, check whether it is already in the set. If yes, return `true`. If the loop completes without a hit, return `false`. Using `Set.add`'s boolean return value collapses the `contains` + `add` into a single call.

**Time:** O(n) — single pass.
**Space:** O(n) — set holds up to n elements.

```java
public boolean containsDuplicate(int[] nums) {
    Set<Integer> seen = new HashSet<>();

    for (int n : nums) {
        if (!seen.add(n)) {
            return true;
        }
    }

    return false;
}
```

**Algorithm trace** — Input: `nums = [1, 2, 3, 1]`

| i | nums[i] | seen.add() returns | action |
|---|---------|-------------------|--------|
| 0 | 1 | true (new) | continue |
| 1 | 2 | true (new) | continue |
| 2 | 3 | true (new) | continue |
| 3 | 1 | **false** (already present) | return true |

→ return `true`

---

## 3. Alternative Approaches

### Brute Force — nested loops

Compare every pair of elements.

- **Time:** O(n²) — all pairs.
- **Space:** O(1) — no extra data structures.
- **When acceptable:** Extremely small inputs or as a starting sketch to motivate a better approach. Never acceptable as a final answer.

```java
for (int i = 0; i < nums.length; i++) {
    for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] == nums[j]) return true;
    }
}
return false;
```

**Algorithm trace** — Input: `nums = [1, 2, 3, 1]`

| i | j | nums[i] | nums[j] | Match? |
|---|---|---------|---------|--------|
| 0 | 1 | 1 | 2 | No |
| 0 | 2 | 1 | 3 | No |
| 0 | 3 | 1 | 1 | **Yes** → return true |

→ return `true`

---

### Sort then scan

Sort the array; duplicates become adjacent and are found in a single pass.

- **Time:** O(n log n) — dominated by the sort.
- **Space:** O(1) extra (in-place sort uses O(log n) call stack).
- **When acceptable:** When memory is tightly constrained and O(n log n) time is acceptable. Note: mutates the input array.

```java
Arrays.sort(nums);
for (int i = 1; i < nums.length; i++) {
    if (nums[i] == nums[i - 1]) return true;
}
return false;
```

**Algorithm trace** — Input: `nums = [1, 2, 3, 1]`

After sort: `[1, 1, 2, 3]`

| i | nums[i-1] | nums[i] | Equal? |
|---|-----------|---------|--------|
| 1 | 1 | 1 | **Yes** → return true |

→ return `true`
