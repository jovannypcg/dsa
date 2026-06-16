# Review: Contains Duplicate

## 1. DSA Category

**Arrays & Hashing**

---

## 2. Your Solution Assessment

### Correctness
Handles all cases correctly: empty array, single element, negatives, large values, and duplicates anywhere in the array. The `null` guard is a safe touch even though the problem constraints don't mention null input.

### Code Quality
Clean and readable. Naming is clear (`seen`, `current`). Logical sections are well separated with blank lines. The early-return on an empty array is a nice optimization that avoids allocating the `HashSet` unnecessarily.

One minor note: the `null` check (`nums == null`) is a defensive guard that goes beyond the stated constraints (which only bound `nums.length >= 0`). It's harmless and arguably good practice, but it's worth knowing when to include it vs. when it adds noise.

### Time Complexity
**O(n)** — a single pass through the array; each `HashSet.contains` and `HashSet.add` is O(1) amortized.

### Space Complexity
**O(n)** — in the worst case (no duplicates), every element is inserted into the `HashSet`.

---

## 3. Optimal Approach

Your solution **is** the optimal approach. A `HashSet` gives O(1) average-case lookup and insertion, achieving the best possible time complexity for this problem.

**Approach:** Iterate through the array, adding each element to a set. If an element is already present in the set before insertion, a duplicate exists — return `true`. If the loop completes without a hit, return `false`.

- **Time:** O(n)
- **Space:** O(n)

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

> `Set.add` returns `false` when the element is already present, letting you collapse the `contains` + `add` into a single call.

---

## 4. Alternative Approaches

### Brute Force (Nested Loops)

Compare every pair of elements.

```java
for (int i = 0; i < nums.length; i++) {
    for (int j = i + 1; j < nums.length; j++) {
        if (nums[i] == nums[j]) return true;
    }
}
return false;
```

- **Time:** O(n²)
- **Space:** O(1)
- **When acceptable:** Extremely small inputs or interview situations where you need to demonstrate understanding before optimizing.

### Sorting

Sort the array first; duplicates become adjacent and can be found in a single pass.

```java
Arrays.sort(nums);
for (int i = 1; i < nums.length; i++) {
    if (nums[i] == nums[i - 1]) return true;
}
return false;
```

- **Time:** O(n log n) — dominated by the sort.
- **Space:** O(1) extra (in-place sort), though `Arrays.sort` on primitives uses Dual-Pivot Quicksort which is O(log n) stack space.
- **When acceptable:** When memory is tightly constrained and O(n log n) time is acceptable. Note that this mutates the input array, which may be undesirable.
