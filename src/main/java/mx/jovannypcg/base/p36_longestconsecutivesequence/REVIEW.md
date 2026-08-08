| | |
|---|---|
| **Solved on** | 2026-08-08 |
| **DSA Category** | Arrays & Hashing |

## 1. Your Solution Assessment

*(Updated: the outer loop now iterates over `seen` instead of `nums`, fixing the duplicate-start-value performance issue flagged in the previous revision of this section.)*

### Correctness

The implementation is correct. It builds a `HashSet<Integer>` of every value, then iterates over `seen` (the deduplicated set); for each `num`, if `num - 1` is not in the set, `num` is the start of a run, so it expands forward (`end + 1`, `end + 2`, ...) while the next value is present, tracking the longest run seen. I traced it against all 12 test cases (unsorted input, duplicates, negatives spanning zero, all-identical elements, no-consecutive-elements, and int-range boundary values) and it produces the expected result in every case, matching the `mvn test` run (12/12 passing).

### Code quality

Clean and idiomatic — `seen.contains(num - 1)` as the "am I a sequence start" guard is exactly the key insight of this problem, and the inner `while (seen.contains(end + 1)) end++;` is minimal. The comment on the `continue` line is a nice touch since the *why* (skip non-starts) isn't obvious from the code alone. Iterating over `seen` rather than `nums` also resolves the earlier duplicate-re-expansion issue (`nums = [5, 5, 5, 6, 7]` no longer walks `5 → 6 → 7` three times — each distinct value is now considered as a candidate start exactly once). This is now functionally equivalent to the Optimal Approach below.

### Time complexity

**O(n).** Building `seen` is O(n). The outer loop runs once per distinct value; the total work across all `while` expansions is bounded by O(n) because each element is only ever consumed by the expansion of its own run's start (a duplicate can no longer trigger a repeat walk, since the outer loop only ever sees it once).

### Space complexity

**O(n)** for the `seen` hash set holding up to n distinct values.

### Algorithm trace

Input: `nums = [100, 4, 200, 1, 3, 2]` → `seen = {100, 4, 200, 1, 3, 2}` (shown here in ascending order for readability; actual `HashSet` iteration order is unspecified)

| num (from `seen`) | seen.contains(num-1)? | action | expansion | run length | longestSequenceSize after |
|---|---|---|---|---|---|
| 1 | no (0 absent) | expand | 1→2→3→4 (5 absent, stop) | 4 | 4 |
| 2 | yes (1 present) | skip (`continue`) | — | — | 4 |
| 3 | yes (2 present) | skip (`continue`) | — | — | 4 |
| 4 | yes (3 present) | skip (`continue`) | — | — | 4 |
| 100 | no (99 absent) | expand | 100 (101 absent, stop) | 1 | 4 |
| 200 | no (199 absent) | expand | 200 (201 absent, stop) | 1 | 4 |

→ return 4

## 2. Optimal Approach

Put every number in a `HashSet` for O(1) membership checks. Then, for each **distinct** number, only start counting a sequence if `num - 1` is **not** in the set — that means `num` is the smallest element of its run. From there, walk forward (`num + 1`, `num + 2`, ...) while each next value exists in the set, tracking the run's length. Because expansion only ever happens from a true sequence start, every distinct number is visited at most twice across the whole algorithm (once as a candidate, once during at most one expansion), which is what makes this O(n) despite the nested-looking loop.

Your solution now matches this approach.

**Time complexity: O(n).** Building the set is O(n); the total work across all `while` expansions is bounded by O(n) because each element can only be consumed by the expansion of its own sequence's start.

**Space complexity: O(n)** for the hash set holding up to n distinct values.

```java
public int longestConsecutive(int[] nums) {
    Set<Integer> numSet = new HashSet<>();
    for (int num : nums) {
        numSet.add(num);
    }

    int longestStreak = 0;

    for (int num : numSet) {
        if (!numSet.contains(num - 1)) {
            int length = 1;
            int current = num;

            while (numSet.contains(current + 1)) {
                current++;
                length++;
            }

            longestStreak = Math.max(longestStreak, length);
        }
    }

    return longestStreak;
}
```

### Algorithm trace

Input: `nums = [100, 4, 200, 1, 3, 2]` → `numSet = {100, 4, 200, 1, 3, 2}` (shown here in ascending order for readability; actual `HashSet` iteration order is unspecified)

| num | num-1 in set? | starts a sequence? | expansion | length | longestStreak |
|---|---|---|---|---|---|
| 1 | no (0 absent) | yes | 1→2→3→4 (5 absent, stop) | 4 | 4 |
| 2 | yes (1 present) | no, skipped | — | — | 4 |
| 3 | yes (2 present) | no, skipped | — | — | 4 |
| 4 | yes (3 present) | no, skipped | — | — | 4 |
| 100 | no (99 absent) | yes | 100 (101 absent, stop) | 1 | 4 |
| 200 | no (199 absent) | yes | 200 (201 absent, stop) | 1 | 4 |

→ return 4

## 3. Alternative Approaches

### Union-Find (Disjoint Set Union)

Map every distinct value to its own DSU node. For each number, if `num - 1` or `num + 1` is already present, union the two nodes and merge their component sizes (union by size, with path compression on `find`). The answer is the size of the largest component at the end.

**Time complexity: O(n · α(n))**, effectively linear — `α` is the inverse Ackermann function, which is ≤ 4 for any input size that fits in memory.

**Space complexity: O(n)** for the `parent` and `size` maps.

Acceptable when the interviewer specifically wants to see Union-Find, or when the problem later evolves to require online updates (e.g., numbers inserted one at a time with running "longest streak" queries), where DSU adapts naturally but the hash-set expansion approach does not.

```java
public int longestConsecutive(int[] nums) {
    if (nums.length == 0) return 0;

    Map<Integer, Integer> parent = new HashMap<>();
    Map<Integer, Integer> size = new HashMap<>();

    for (int num : nums) {
        if (parent.containsKey(num)) continue;
        parent.put(num, num);
        size.put(num, 1);

        if (parent.containsKey(num - 1)) union(parent, size, num, num - 1);
        if (parent.containsKey(num + 1)) union(parent, size, num, num + 1);
    }

    int longestStreak = 0;
    for (int s : size.values()) {
        longestStreak = Math.max(longestStreak, s);
    }

    return longestStreak;
}

private int find(Map<Integer, Integer> parent, int x) {
    while (parent.get(x) != x) {
        parent.put(x, parent.get(parent.get(x)));
        x = parent.get(x);
    }
    return x;
}

private void union(Map<Integer, Integer> parent, Map<Integer, Integer> size, int a, int b) {
    int rootA = find(parent, a);
    int rootB = find(parent, b);
    if (rootA == rootB) return;

    if (size.get(rootA) < size.get(rootB)) {
        int temp = rootA;
        rootA = rootB;
        rootB = temp;
    }

    parent.put(rootB, rootA);
    size.put(rootA, size.get(rootA) + size.get(rootB));
}
```

#### Algorithm trace

Input: `nums = [100, 4, 200, 1, 3, 2]` (processed in array order)

| num | num-1 present? | num+1 present? | union(s) performed | resulting root size | longestStreak so far |
|---|---|---|---|---|---|
| 100 | no | no | none | size[100]=1 | 1 |
| 4 | no | no | none | size[4]=1 | 1 |
| 200 | no | no | none | size[200]=1 | 1 |
| 1 | no | no | none | size[1]=1 | 1 |
| 3 | no | yes (4) | union(3, 4) | size[root]=2 | 2 |
| 2 | yes (1) | yes (3) | union(2, 1), union(2, 3) | size[root]=4 | 4 |

→ largest component size = 4 → return 4

### Sort + Two Pointers (your original approach)

Sort the array first, so every consecutive run becomes contiguous. Then scan once with a `[left, right]` window: advance `right` while the next value is a duplicate (tracked separately so it doesn't inflate the count) or exactly one greater than the current value; as soon as neither holds, close the segment (`right - left + 1 - nDuplicates`), record it if it's the longest so far, and restart the window at `right`. This was your first working solution for this problem, before switching to the hash-set version.

**Time complexity: O(n log n)**, dominated by `Arrays.sort(nums)`. The single left-right scan after sorting is O(n).

**Space complexity: O(log n)** auxiliary, for Java's in-place dual-pivot quicksort on primitives (no extra hash set or map needed).

Acceptable when the interviewer is fine with O(n log n) — many are, since it trades the strict O(n) requirement for an approach that's arguably easier to reason about (no hash set, and duplicate/consecutive handling falls out naturally once the array is sorted). Also a reasonable fallback if memory is tight enough that an O(n)-space hash set is undesirable, since sorting can be done in place.

```java
public int longestConsecutive(int[] nums) {
    if (nums == null || nums.length == 0) return 0;

    int n = nums.length;
    int left = 0,
        right = 0,
        nDuplicates = 0,
        longestSequenceSize = 0;

    Arrays.sort(nums);

    while (right < n) {
        if (right + 1 >= n) break; // Last item reached

        if (isDuplicate(nums[right], nums[right + 1])) {
            nDuplicates++;
            right++;
        } else if (isConsecutive(nums[right], nums[right + 1])) {
            right++;
        } else {
            int sequenceSize = right - left + 1 - nDuplicates;
            longestSequenceSize = Math.max(longestSequenceSize, sequenceSize);

            right++;
            left = right;
            nDuplicates = 0;
        }
    }

    int sequenceSize = right - left + 1 - nDuplicates;
    longestSequenceSize = Math.max(longestSequenceSize, sequenceSize);

    return longestSequenceSize;
}

boolean isDuplicate(int a, int b) {
    return a == b;
}

boolean isConsecutive(int a, int b) {
    return a + 1 == b;
}
```

#### Algorithm trace

Input: `nums = [100, 4, 200, 1, 3, 2]` → after `Arrays.sort`: `[1, 2, 3, 4, 100, 200]`

```
val:     1    2    3    4  100  200
         L
         R
right=0: nums[R]=1, nums[R+1]=2 → consecutive → R advances
longest: 0

val:     1    2    3    4  100  200
         L    R
right=1: nums[R]=2, nums[R+1]=3 → consecutive → R advances
longest: 0

val:     1    2    3    4  100  200
         L         R
right=2: nums[R]=3, nums[R+1]=4 → consecutive → R advances
longest: 0

val:     1    2    3    4  100  200
         L              R
right=3: nums[R]=4, nums[R+1]=100 → not consecutive, not duplicate → close segment [L..R]
segment size = R-L+1-nDup = 3-0+1-0 = 4 → longest = max(0, 4) = 4
R advances, L jumps to R, nDup resets to 0
longest: 4

val:     1    2    3    4  100  200
                             L
                             R
right=4: nums[R]=100, nums[R+1]=200 → not consecutive, not duplicate → close segment [L..R]
segment size = R-L+1-nDup = 4-4+1-0 = 1 → longest = max(4, 1) = 4
R advances, L jumps to R, nDup resets to 0
longest: 4

val:     1    2    3    4  100  200
                                  L
                                  R
right=5: right+1=6 ≥ n → loop ends (no comparison made)

final segment size = R-L+1-nDup = 5-5+1-0 = 1 → longest = max(4, 1) = 4
→ return 4
```

### Hash Set Without the "Sequence Start" Check

Same as the optimal approach's set-building step, but skip the `!numSet.contains(num - 1)` guard and expand forward from *every* element, not just true sequence starts.

**Time complexity: O(n²) worst case.** If all n numbers form one consecutive run, every one of the n elements triggers its own O(n) forward walk, since nothing prevents re-walking the same run from each of its members.

**Space complexity: O(n)** for the hash set.

Only acceptable as a quick, "good enough for now" pass under interview time pressure when the constraints are small — it is asymptotically worse than the optimal approach for the exact same amount of code, so it should be upgraded to the start-check version once there's time.

```java
public int longestConsecutive(int[] nums) {
    Set<Integer> numSet = new HashSet<>();
    for (int num : nums) {
        numSet.add(num);
    }

    int longestStreak = 0;

    for (int num : numSet) {
        int current = num;
        int length = 1;

        while (numSet.contains(current + 1)) {
            current++;
            length++;
        }

        longestStreak = Math.max(longestStreak, length);
    }

    return longestStreak;
}
```

#### Algorithm trace

Input: `nums = [100, 4, 200, 1, 3, 2]` → `numSet = {1, 2, 3, 4, 100, 200}` (ascending order for readability)

| num | expansion (no start check) | length | longestStreak |
|---|---|---|---|
| 1 | 1→2→3→4 (5 absent, stop) | 4 | 4 |
| 2 | 2→3→4 (5 absent, stop) | 3 | 4 |
| 3 | 3→4 (5 absent, stop) | 2 | 4 |
| 4 | 4 (5 absent, stop) | 1 | 4 |
| 100 | 100 (101 absent, stop) | 1 | 4 |
| 200 | 200 (201 absent, stop) | 1 | 4 |

→ return 4 (correct result, but note elements 1, 2, 3 each redundantly re-walk part of the same run)

### Brute Force (No Hash Set)

For each element, repeatedly search the *raw array* (linear scan, no auxiliary structure) for the next consecutive value, extending the streak until the next value is missing.

**Time complexity: O(n³).** The outer loop is O(n); for a run of length k the inner `while` executes k times, and each iteration does an O(n) linear `contains` scan — worst case this compounds to O(n³) when one long run dominates.

**Space complexity: O(1)** extra space — no hash set or sorting needed.

Only acceptable as a first correctness pass on tiny inputs (e.g., n ≤ 20) or as a starting point to reason about the problem out loud before optimizing in an interview.

```java
public int longestConsecutive(int[] nums) {
    int longestStreak = 0;

    for (int num : nums) {
        int current = num;
        int length = 1;

        while (contains(nums, current + 1)) {
            current++;
            length++;
        }

        longestStreak = Math.max(longestStreak, length);
    }

    return longestStreak;
}

private boolean contains(int[] nums, int target) {
    for (int n : nums) {
        if (n == target) return true;
    }
    return false;
}
```

#### Algorithm trace

Input: `nums = [100, 4, 200, 1, 3, 2]`

| num (outer loop) | current | contains(current+1)? | length |
|---|---|---|---|
| 100 | 100 | contains(101)? no | 1 |
| 4 | 4→5? no | contains(5)? no | 1 |
| 200 | 200 | contains(201)? no | 1 |
| 1 | 1→2→3→4→5? | contains(2)✓, contains(3)✓, contains(4)✓, contains(5)✗ | 4 |
| 3 | 3→4→5? | contains(4)✓, contains(5)✗ | 2 |
| 2 | 2→3→4→5? | contains(3)✓, contains(4)✓, contains(5)✗ | 3 |

→ longestStreak = max(1, 1, 1, 4, 2, 3) = 4 → return 4
