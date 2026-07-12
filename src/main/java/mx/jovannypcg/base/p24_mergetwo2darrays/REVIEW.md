| | |
|---|---|
| **Solved on** | 2026-07-11 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** Fully correct. This is the textbook two-pointer merge (the same core idea as the merge step of merge sort), adapted to compare by id: while both pointers reference a valid element, compare `nums1[idx1][0]` and `nums2[idx2][0]` — sum and advance both on a match, or advance whichever pointer points at the smaller id. Once the main loop exits (one array exhausted), the two trailing `while` drains append whatever remains of the other array, already in order. Handles every case correctly: no overlapping ids, full overlap, and one array's id range sitting entirely before or after the other's. The earlier revision relied on `id == 0` as an "array exhausted" sentinel, which only worked because of the `1 <= idi` constraint — this version removes that fragile assumption entirely by bounding the loop on array length instead.

**Code quality:** Clean and idiomatic. No unnecessary indirection — the earlier `getValue` helper is gone, replaced by direct, self-evidently-safe array access (`nums1[idx1][1]`) at the point where both indices are already known to be in bounds. The two drain loops read clearly as "whatever's left just gets appended in order," and the single-expression `nums1[idx1++]` / `nums2[idx2++]` calls avoid restating the pair construction when no summing is needed.

**Time complexity:** O(n + m), where n and m are the lengths of `nums1` and `nums2`. The main loop advances at least one pointer per iteration, and the two drain loops together advance the remaining pointer to its array's length — every element is visited exactly once.

**Space complexity:** O(n + m) for the output list (unavoidable — the result can contain up to n + m entries). Excluding the output, the algorithm uses only O(1) extra space (two index variables).

**Algorithm trace** (Example 1: `nums1 = [[1,2],[2,3],[4,5]]`, `nums2 = [[1,4],[3,2],[4,1]]`, with pointers `A` = idx1, `B` = idx2):

```
Step 1: idx1=0, idx2=0 → id1=1, id2=1 (equal) → sum = 2+4 = 6 → output += [1,6]
         → both idx1 and idx2 advance
nums1: [[1, 2], [2, 3], [4, 5]]
        A
nums2: [[1, 4], [3, 2], [4, 1]]
        B
out:   [[1, 6]]

Step 2: idx1=1, idx2=1 → id1=2, id2=3 (id1 < id2) → take nums1 → output += [2,3]
         → only idx1 advances
nums1: [[1, 2], [2, 3], [4, 5]]
                A
nums2: [[1, 4], [3, 2], [4, 1]]
                B
out:   [[1, 6], [2, 3]]

Step 3: idx1=2, idx2=1 → id1=4, id2=3 (id2 < id1) → take nums2 → output += [3,2]
         → only idx2 advances
nums1: [[1, 2], [2, 3], [4, 5]]
                        A
nums2: [[1, 4], [3, 2], [4, 1]]
                B
out:   [[1, 6], [2, 3], [3, 2]]

Step 4: idx1=2, idx2=2 → id1=4, id2=4 (equal) → sum = 5+1 = 6 → output += [4,6]
         → both idx1 and idx2 advance
nums1: [[1, 2], [2, 3], [4, 5]]
                        A
nums2: [[1, 4], [3, 2], [4, 1]]
                        B
out:   [[1, 6], [2, 3], [3, 2], [4, 6]]

idx1=3 = length(nums1), idx2=3 = length(nums2) → main loop condition fails, loop ends
both drain loops (`while (idx1 < nums1.length) ...` / `while (idx2 < nums2.length) ...`) are no-ops
```
→ return `[[1,6],[2,3],[3,2],[4,6]]`

## 2. Optimal Approach

Your solution above **is** the optimal approach — this section restates it for completeness, using conventional `i`/`j` pointer names.

This problem is a direct application of the merge step from merge sort, adapted to compare by id instead of blindly interleaving. Since both arrays are already sorted by id, a two-pointer scan lets you produce the merged, summed result in a single linear pass: advance whichever pointer has the smaller id (appending that entry as-is), or advance both and sum the values when the ids match. Once one array is exhausted, drain the remainder of the other directly.

**Time complexity:** O(n + m) — one pass over both arrays combined, each element visited once.

**Space complexity:** O(n + m) for the output array; O(1) auxiliary space beyond that.

```java
public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
    List<int[]> result = new ArrayList<>();
    int i = 0, j = 0;

    while (i < nums1.length && j < nums2.length) {
        if (nums1[i][0] == nums2[j][0]) {
            result.add(new int[]{nums1[i][0], nums1[i][1] + nums2[j][1]});
            i++;
            j++;
        } else if (nums1[i][0] < nums2[j][0]) {
            result.add(nums1[i++]);
        } else {
            result.add(nums2[j++]);
        }
    }

    while (i < nums1.length) result.add(nums1[i++]);
    while (j < nums2.length) result.add(nums2[j++]);

    return result.toArray(new int[0][]);
}
```

**Algorithm trace** (same Example 1, pointers `A` = i, `B` = j):

```
Step 1: i=0, j=0 → id1=1, id2=1 (equal) → sum = 2+4 = 6 → result += [1,6]
         → both i and j advance
nums1: [[1, 2], [2, 3], [4, 5]]
        A
nums2: [[1, 4], [3, 2], [4, 1]]
        B
out:   [[1, 6]]

Step 2: i=1, j=1 → id1=2, id2=3 (id1 < id2) → take nums1 → result += [2,3]
         → only i advances
nums1: [[1, 2], [2, 3], [4, 5]]
                A
nums2: [[1, 4], [3, 2], [4, 1]]
                B
out:   [[1, 6], [2, 3]]

Step 3: i=2, j=1 → id1=4, id2=3 (id2 < id1) → take nums2 → result += [3,2]
         → only j advances
nums1: [[1, 2], [2, 3], [4, 5]]
                        A
nums2: [[1, 4], [3, 2], [4, 1]]
                B
out:   [[1, 6], [2, 3], [3, 2]]

Step 4: i=2, j=2 → id1=4, id2=4 (equal) → sum = 5+1 = 6 → result += [4,6]
         → both i and j advance
nums1: [[1, 2], [2, 3], [4, 5]]
                        A
nums2: [[1, 4], [3, 2], [4, 1]]
                        B
out:   [[1, 6], [2, 3], [3, 2], [4, 6]]

i=3 = length(nums1), j=3 = length(nums2) → both loops (main + drains) end
```
→ return `[[1,6],[2,3],[3,2],[4,6]]`

## 3. Alternative Approaches

### a) Bucket / counting array (exploits `1 <= idi <= 1000`)

Since ids are bounded by a small constant (1000), allocate a `int[1001]` accumulator, add every value from both arrays into `bucket[id]`, then scan ids from 1 to 1000 and collect every id whose bucket is non-zero. This sidesteps sorting entirely because the bucket index *is* the sort key.

**Time complexity:** O(n + m + K), where K = 1000 is the fixed id range — effectively O(n + m) since K is a constant.

**Space complexity:** O(K) for the bucket array, i.e., O(1) relative to input size since K is fixed by the constraints.

**When acceptable:** Great here because the constraint caps ids at 1000, making the bucket small and the approach both simple and fast. Would not generalize if the id range were unbounded or very large (e.g., ids up to 10^9), since the bucket array would become impractically large.

```java
public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
    int[] bucket = new int[1001];
    boolean[] seen = new boolean[1001];

    for (int[] pair : nums1) {
        bucket[pair[0]] += pair[1];
        seen[pair[0]] = true;
    }
    for (int[] pair : nums2) {
        bucket[pair[0]] += pair[1];
        seen[pair[0]] = true;
    }

    List<int[]> result = new ArrayList<>();
    for (int id = 1; id <= 1000; id++) {
        if (seen[id]) result.add(new int[]{id, bucket[id]});
    }

    return result.toArray(new int[0][]);
}
```

**Algorithm trace** (same Example 1):

| Step | Source | id | val | bucket[id] after |
|---|---|---|---|---|
| 1 | nums1[0] | 1 | 2 | 2 |
| 2 | nums1[1] | 2 | 3 | 3 |
| 3 | nums1[2] | 4 | 5 | 5 |
| 4 | nums2[0] | 1 | 4 | 6 |
| 5 | nums2[1] | 3 | 2 | 2 |
| 6 | nums2[2] | 4 | 1 | 6 |

Scan id = 1..4 with `seen[id] == true`: id=1→6, id=2→3, id=3→2, id=4→6
→ return `[[1,6],[2,3],[3,2],[4,6]]`

### b) `TreeMap<Integer, Integer>`

Insert every `(id, val)` pair from both arrays into a `TreeMap`, summing values on collision. A `TreeMap` keeps keys sorted automatically, so iterating its entry set at the end yields the result directly, with no separate sort step.

**Time complexity:** O((n + m) log(n + m)) — each of the n + m insertions costs O(log(n + m)) to maintain the map's red-black tree ordering.

**Space complexity:** O(n + m) for the map.

**When acceptable:** Reasonable under interview time pressure if the two-pointer merge doesn't come to mind immediately, or if the "both arrays are pre-sorted" detail is overlooked — it doesn't require reasoning about that invariant at all.

```java
public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
    TreeMap<Integer, Integer> map = new TreeMap<>();

    for (int[] pair : nums1) map.merge(pair[0], pair[1], Integer::sum);
    for (int[] pair : nums2) map.merge(pair[0], pair[1], Integer::sum);

    int[][] result = new int[map.size()][2];
    int i = 0;
    for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
        result[i++] = new int[]{entry.getKey(), entry.getValue()};
    }

    return result;
}
```

**Algorithm trace** (same Example 1):

| Step | Source | id | val | map after |
|---|---|---|---|---|
| 1 | nums1[0] | 1 | 2 | {1=2} |
| 2 | nums1[1] | 2 | 3 | {1=2, 2=3} |
| 3 | nums1[2] | 4 | 5 | {1=2, 2=3, 4=5} |
| 4 | nums2[0] | 1 | 4 | {1=6, 2=3, 4=5} |
| 5 | nums2[1] | 3 | 2 | {1=6, 2=3, 3=2, 4=5} |
| 6 | nums2[2] | 4 | 1 | {1=6, 2=3, 3=2, 4=6} |

Iterate entries in sorted key order → return `[[1,6],[2,3],[3,2],[4,6]]`

### c) `HashMap` + explicit sort (brute force)

Insert every `(id, val)` pair into a plain `HashMap`, summing on collision, without relying on any ordering. Then extract the key set, sort it, and build the result by looking up each sorted key.

**Time complexity:** O((n + m) log(n + m)) — dominated by sorting the up-to-(n+m) distinct ids; the map insertions themselves are O(1) amortized each.

**Space complexity:** O(n + m) for the map plus the sorted key array.

**When acceptable:** The simplest approach to reason about under pressure — ignores the sorted input entirely and treats the problem as "group and sort." Acceptable for small inputs or as a fallback when correctness matters more than optimality, but it's the least efficient of the approaches listed since it throws away the pre-sorted structure of the input.

```java
public int[][] mergeArrays(int[][] nums1, int[][] nums2) {
    Map<Integer, Integer> map = new HashMap<>();

    for (int[] pair : nums1) map.merge(pair[0], pair[1], Integer::sum);
    for (int[] pair : nums2) map.merge(pair[0], pair[1], Integer::sum);

    List<Integer> ids = new ArrayList<>(map.keySet());
    Collections.sort(ids);

    int[][] result = new int[ids.size()][2];
    for (int i = 0; i < ids.size(); i++) {
        result[i] = new int[]{ids.get(i), map.get(ids.get(i))};
    }

    return result;
}
```

**Algorithm trace** (same Example 1):

| Step | Source | id | val | map after (unordered) |
|---|---|---|---|---|
| 1 | nums1[0] | 1 | 2 | {1=2} |
| 2 | nums1[1] | 2 | 3 | {1=2, 2=3} |
| 3 | nums1[2] | 4 | 5 | {1=2, 2=3, 4=5} |
| 4 | nums2[0] | 1 | 4 | {1=6, 2=3, 4=5} |
| 5 | nums2[1] | 3 | 2 | {1=6, 2=3, 3=2, 4=5} |
| 6 | nums2[2] | 4 | 1 | {1=6, 2=3, 3=2, 4=6} |

Extract keys `[1, 2, 4, 3]` (arbitrary order) → sort → `[1, 2, 3, 4]` → look up each
→ return `[[1,6],[2,3],[3,2],[4,6]]`
