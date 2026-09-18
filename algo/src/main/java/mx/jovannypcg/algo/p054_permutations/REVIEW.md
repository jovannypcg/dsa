| | |
|---|---|
| **Solved on** | 2026-08-20 |
| **DSA Category** | Backtracking |

## 1. Your Solution Assessment

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> permutations = new ArrayList<>();
    Set<Integer> permutation = new LinkedHashSet<>();

    backtracking(nums, permutations, permutation);

    return permutations;
}

void backtracking(
    int[] nums,
    List<List<Integer>> permutations,
    Set<Integer> permutation
) {
    if (permutation.size() == nums.length) {
        permutations.add(new ArrayList<>(permutation));
        return;
    }

    for (int num : nums) {
        if (!permutation.contains(num)) {
            permutation.add(num);
            backtracking(nums, permutations, permutation);
            permutation.remove(num);
        }
    }
}
```

**Correctness:** Handles all cases correctly. The first attempt used a plain `HashSet<Integer>`, which produced the same list six times for `[1,2,3]` because `HashSet` iteration order depends on the elements' hash codes, not insertion order — so `new ArrayList<>(permutation)` collapsed every path down to the same ordering. Switching to `LinkedHashSet` fixes this: it maintains an internal doubly-linked list of insertion order, and since `add`/`remove` here always follow LIFO discipline (the last-added element is always the one removed on backtrack), the iteration order at any point in the recursion always matches the actual path taken. All 6 tests pass, including the `n = 6` boundary (720 unique permutations) and negative-value inputs.

**Code quality:** Clear and short. Two minor nitpicks: `backtracking` could be `private` since it's only used internally, and the parameter name `num` inside the loop shadows the outer concept of "num being tried" reasonably well, so naming is fine overall.

**Time complexity:** O(n! · n). There are n! complete permutations; each one costs O(n) to copy into the result via `new ArrayList<>(permutation)`. The `LinkedHashSet`'s `contains`/`add`/`remove` are all O(1), so the branching work at each of the O(n · n!) call-tree nodes is O(1) per candidate check — the O(n) factor comes from copying each finished permutation, not from the set operations.

**Space complexity:** O(n) auxiliary — the `LinkedHashSet` holds at most n elements and the recursion depth is at most n — plus O(n! · n) for the output itself, which is unavoidable given the problem asks for all permutations.

**Algorithm trace** (`nums = [1, 2, 3]`)

Legend: `→ ADD num` = `permutation.add(num)`, then recurse · `← REMOVE num` = `permutation.remove(num)` on the way back up (the undo/backtrack step) · `✗ SKIP` = `contains` check fails the loop, no recursion · `✓ RECORD` = base case hit, copy saved

```
backtracking(perm=[])
  try 1 → ADD → perm=[1]
    backtracking(perm=[1])
      try 1 ✗ SKIP (already used)
      try 2 → ADD → perm=[1, 2]
        backtracking(perm=[1, 2])
          try 1 ✗ SKIP (already used)
          try 2 ✗ SKIP (already used)
          try 3 → ADD → perm=[1, 2, 3]
            backtracking(perm=[1, 2, 3]) ✓ RECORD [1, 2, 3]
          ← REMOVE 3 → perm=[1, 2]
      ← REMOVE 2 → perm=[1]
      try 3 → ADD → perm=[1, 3]
        backtracking(perm=[1, 3])
          try 1 ✗ SKIP (already used)
          try 2 → ADD → perm=[1, 3, 2]
            backtracking(perm=[1, 3, 2]) ✓ RECORD [1, 3, 2]
          ← REMOVE 2 → perm=[1, 3]
          try 3 ✗ SKIP (already used)
      ← REMOVE 3 → perm=[1]
  ← REMOVE 1 → perm=[]
  try 2 → ADD → perm=[2]
    backtracking(perm=[2])
      try 1 → ADD → perm=[2, 1]
        backtracking(perm=[2, 1])
          try 1 ✗ SKIP (already used)
          try 2 ✗ SKIP (already used)
          try 3 → ADD → perm=[2, 1, 3]
            backtracking(perm=[2, 1, 3]) ✓ RECORD [2, 1, 3]
          ← REMOVE 3 → perm=[2, 1]
      ← REMOVE 1 → perm=[2]
      try 2 ✗ SKIP (already used)
      try 3 → ADD → perm=[2, 3]
        backtracking(perm=[2, 3])
          try 1 → ADD → perm=[2, 3, 1]
            backtracking(perm=[2, 3, 1]) ✓ RECORD [2, 3, 1]
          ← REMOVE 1 → perm=[2, 3]
          try 2 ✗ SKIP (already used)
          try 3 ✗ SKIP (already used)
      ← REMOVE 3 → perm=[2]
  ← REMOVE 2 → perm=[]
  try 3 → ADD → perm=[3]
    backtracking(perm=[3])
      try 1 → ADD → perm=[3, 1]
        backtracking(perm=[3, 1])
          try 1 ✗ SKIP (already used)
          try 2 → ADD → perm=[3, 1, 2]
            backtracking(perm=[3, 1, 2]) ✓ RECORD [3, 1, 2]
          ← REMOVE 2 → perm=[3, 1]
          try 3 ✗ SKIP (already used)
      ← REMOVE 1 → perm=[3]
      try 2 → ADD → perm=[3, 2]
        backtracking(perm=[3, 2])
          try 1 → ADD → perm=[3, 2, 1]
            backtracking(perm=[3, 2, 1]) ✓ RECORD [3, 2, 1]
          ← REMOVE 1 → perm=[3, 2]
          try 2 ✗ SKIP (already used)
          try 3 ✗ SKIP (already used)
      ← REMOVE 2 → perm=[3]
      try 3 ✗ SKIP (already used)
  ← REMOVE 3 → perm=[]

permutations = [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,1,2], [3,2,1]]
```

Every `ADD` is bracketed by exactly one matching `REMOVE` at the same indentation — that symmetry is the backtracking invariant: whatever the recursive subtree does to `perm`, it always returns `perm` to the exact state it received it in.

## 2. Optimal Approach

The asymptotic complexity can't improve on O(n! · n) — the output itself has n! permutations of length n, so any correct algorithm must spend at least that much time writing the answer. What *can* improve is the constant factor: instead of a hash-based `used` structure, permute the array **in place** by swapping. At recursion depth `start`, try every candidate `i >= start` as the next element by swapping it into position `start`, recurse on `start + 1`, then swap back to undo. No extra `contains`/`add`/`remove` hashing at all — just O(1) array swaps.

**Time:** O(n! · n) — same leaf count and same O(n) copy cost per completed permutation, but each internal step is a plain array swap instead of a hash-set operation, so the constant factor is smaller.

**Space:** O(n) — only the recursion stack; the permutation itself is built by mutating the input array, not by maintaining a separate structure. (Output storage is still O(n! · n), same as any approach.)

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, 0, result);
    return result;
}

private void backtrack(int[] nums, int start, List<List<Integer>> result) {
    if (start == nums.length) {
        List<Integer> permutation = new ArrayList<>();
        for (int num : nums) {
            permutation.add(num);
        }
        result.add(permutation);
        return;
    }

    for (int i = start; i < nums.length; i++) {
        swap(nums, start, i);
        backtrack(nums, start + 1, result);
        swap(nums, start, i);
    }
}

private void swap(int[] nums, int i, int j) {
    int temp = nums[i];
    nums[i] = nums[j];
    nums[j] = temp;
}
```

**Algorithm trace** (`nums = [1, 2, 3]`)

Legend: `swap(i, j)` shown with the resulting array state · the matching undo `swap` right after the nested call returns

```
backtrack(nums=[1,2,3], start=0)
  i=0: swap(0,0) → nums=[1,2,3]
    backtrack(start=1)
      i=1: swap(1,1) → nums=[1,2,3]
        backtrack(start=2)
          i=2: swap(2,2) → nums=[1,2,3]
            backtrack(start=3) ✓ RECORD [1,2,3]
          ← swap(2,2) undo → nums=[1,2,3]
      ← swap(1,1) undo → nums=[1,2,3]
      i=2: swap(1,2) → nums=[1,3,2]
        backtrack(start=2)
          i=2: swap(2,2) → nums=[1,3,2]
            backtrack(start=3) ✓ RECORD [1,3,2]
          ← swap(2,2) undo → nums=[1,3,2]
      ← swap(1,2) undo → nums=[1,2,3]
  ← swap(0,0) undo → nums=[1,2,3]
  i=1: swap(0,1) → nums=[2,1,3]
    backtrack(start=1)
      i=1: swap(1,1) → nums=[2,1,3]
        backtrack(start=2)
          i=2: swap(2,2) → nums=[2,1,3]
            backtrack(start=3) ✓ RECORD [2,1,3]
          ← swap(2,2) undo → nums=[2,1,3]
      ← swap(1,1) undo → nums=[2,1,3]
      i=2: swap(1,2) → nums=[2,3,1]
        backtrack(start=2)
          i=2: swap(2,2) → nums=[2,3,1]
            backtrack(start=3) ✓ RECORD [2,3,1]
          ← swap(2,2) undo → nums=[2,3,1]
      ← swap(1,2) undo → nums=[2,1,3]
  ← swap(0,1) undo → nums=[1,2,3]
  i=2: swap(0,2) → nums=[3,2,1]
    backtrack(start=1)
      i=1: swap(1,1) → nums=[3,2,1]
        backtrack(start=2)
          i=2: swap(2,2) → nums=[3,2,1]
            backtrack(start=3) ✓ RECORD [3,2,1]
          ← swap(2,2) undo → nums=[3,2,1]
      ← swap(1,1) undo → nums=[3,2,1]
      i=2: swap(1,2) → nums=[3,1,2]
        backtrack(start=2)
          i=2: swap(2,2) → nums=[3,1,2]
            backtrack(start=3) ✓ RECORD [3,1,2]
          ← swap(2,2) undo → nums=[3,1,2]
      ← swap(1,2) undo → nums=[3,2,1]
  ← swap(0,2) undo → nums=[1,2,3]

result = [[1,2,3], [1,3,2], [2,1,3], [2,3,1], [3,2,1], [3,1,2]]
```

Note the swap-based order differs from the `LinkedHashSet` version's order (`[3,2,1]` before `[3,1,2]` here, reversed there) — the problem allows any order, so this is not a discrepancy.

## 3. Alternative Approaches

### 3a. Backtracking with `boolean[] used` + `List<Integer>`

The textbook version of your original idea: keep the ordered permutation in a plain `List<Integer>` and track "used" status in a separate `boolean[]`, indexed by position in `nums` rather than by value (works even if values weren't distinct integers in range, and avoids hashing). This is what the `LinkedHashSet` fix effectively simulates with one structure instead of two.

**Time:** O(n! · n) — identical reasoning to the optimal approach: n! leaves, O(n) copy per leaf, O(1) per candidate check via array indexing.

**Space:** O(n) auxiliary — the `List` (≤ n elements) and the `boolean[]` (n elements) plus recursion depth, functionally equivalent to the optimal approach's footprint.

**When acceptable:** Always a reasonable choice — this is arguably the most common way this problem is solved in interviews, since it doesn't require realizing the swap trick and is very readable.

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, new ArrayList<>(), new boolean[nums.length], result);
    return result;
}

private void backtrack(int[] nums, List<Integer> permutation, boolean[] used, List<List<Integer>> result) {
    if (permutation.size() == nums.length) {
        result.add(new ArrayList<>(permutation));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) {
            continue;
        }

        used[i] = true;
        permutation.add(nums[i]);
        backtrack(nums, permutation, used, result);
        permutation.remove(permutation.size() - 1);
        used[i] = false;
    }
}
```

**Algorithm trace** (`nums = [1, 2, 3]`, first branch only — the remaining two branches follow the identical ADD/REMOVE pattern shown in section 1)

```
backtrack(perm=[], used=[F,F,F])
  i=0 (nums[0]=1): used[0]=T → ADD 1 → perm=[1]
    backtrack(perm=[1], used=[T,F,F])
      i=0: used[0]=T ✗ SKIP
      i=1 (nums[1]=2): used[1]=T → ADD 2 → perm=[1, 2]
        backtrack(perm=[1, 2], used=[T,T,F])
          i=0: ✗ SKIP · i=1: ✗ SKIP
          i=2 (nums[2]=3): used[2]=T → ADD 3 → perm=[1, 2, 3]
            backtrack(perm=[1, 2, 3]) ✓ RECORD [1, 2, 3]
          ← REMOVE 3 → perm=[1, 2], used[2]=F
      ← REMOVE 2 → perm=[1], used[1]=F
      i=2 (nums[2]=3): used[2]=T → ADD 3 → perm=[1, 3]
        backtrack(perm=[1, 3], used=[T,F,T])
          i=0: ✗ SKIP
          i=1 (nums[1]=2): used[1]=T → ADD 2 → perm=[1, 3, 2]
            backtrack(perm=[1, 3, 2]) ✓ RECORD [1, 3, 2]
          ← REMOVE 2 → perm=[1, 3], used[1]=F
          i=2: ✗ SKIP
      ← REMOVE 3 → perm=[1], used[2]=F
  ← REMOVE 1 → perm=[], used[0]=F
  ... (i=1 and i=2 root branches produce [2,1,3], [2,3,1], [3,1,2], [3,2,1] the same way)
```

### 3b. Insert-into-every-position (iterative expansion)

A completely different construction: start with the single empty permutation, then for each number in `nums`, expand every permutation built so far by inserting that number into every possible position. After processing all n numbers, the result is every permutation.

**Time:** O(n! · n). After processing k numbers there are k! partial permutations of length k; inserting the (k+1)-th number creates (k+1) copies of length (k+1) from each. Summed across all n numbers, this telescopes to O(n! · n).

**Space:** O(n! · n) held simultaneously — unlike the backtracking approaches, every intermediate generation of permutations is fully materialized in memory at once (not just one root-to-leaf path), so this is the most memory-hungry of the approaches discussed here.

**When acceptable:** Useful under interview time pressure since it needs no explicit "used" tracking and no recursion at all — just nested loops and list copies, which is easy to reason about and debug on a whiteboard.

```java
public List<List<Integer>> permute(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    result.add(new ArrayList<>());

    for (int num : nums) {
        List<List<Integer>> next = new ArrayList<>();
        for (List<Integer> permutation : result) {
            for (int pos = 0; pos <= permutation.size(); pos++) {
                List<Integer> copy = new ArrayList<>(permutation);
                copy.add(pos, num);
                next.add(copy);
            }
        }
        result = next;
    }

    return result;
}
```

**Algorithm trace** (`nums = [1, 2, 3]`)

| num inserted | expansion | result before | result after |
|---|---|---|---|
| 1 | insert 1 at pos 0 into `[]` | `[[]]` | `[[1]]` |
| 2 | insert 2 at pos 0, 1 into `[1]` | `[[1]]` | `[[2,1], [1,2]]` |
| 3 | insert 3 at pos 0, 1, 2 into `[2,1]` and `[1,2]` | `[[2,1], [1,2]]` | `[[3,2,1], [2,3,1], [2,1,3], [3,1,2], [1,3,2], [1,2,3]]` |

→ final result has all 6 permutations of `[1, 2, 3]`.
