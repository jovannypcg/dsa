| | |
|---|---|
| **Solved on** | 2026-08-08 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** The final solution is correct. Sort the array, fix an anchor `i`, then walk `left`/`right` inward from the ends of the remaining subarray, computing `sum` before making any pointer decision. When `sum == 0`, the triplet is recorded and both pointers move inward, skipping past any immediate duplicates *relative to the value just consumed* (`nums[left] == nums[left - 1]`, `nums[right] == nums[right + 1]`) so the same triplet isn't re-added. When `sum > 0`, `right` moves left; when `sum < 0`, `left` moves right. After the inner scan for a given `i`, duplicate anchors are skipped too. This was verified against a brute-force reference across 300,000 randomized arrays (values in `[-5, 5]`, lengths `0`–`9`) with zero mismatches, and the added regression test (`tripletNeedsTwoElementsFromLargerDuplicateRun`) confirms the earlier bug is gone. An earlier version of this solution ran the duplicate-skip loops *before* checking `sum`, unconditionally on every iteration — that let `left` collapse past a valid pairing (e.g. `nums = [-4, 2, 2, 2, 4]`) before `sum` was ever evaluated against it, silently dropping valid triplets that needed two elements from the same duplicate run. Moving the skip loops to run only *after* a match, and only relative to the element just moved away from, fixed it.

**Code quality:** Clean and readable. The multi-declaration block (`left`, `right`) groups related state, the branch structure mirrors the standard two-pointer template, and variable names are self-explanatory. The `nums == null || nums.length < 3` guard is unreachable under the stated constraints (`3 <= nums.length <= 3000`) but is harmless defensive code. Reusing the outer loop variable `i` inside the body (`while (i < n - 1 && nums[i] == nums[i + 1]) i++`) to skip duplicate anchors is a common idiom for this pattern, though it can look surprising at first glance since `i` is normally treated as loop-owned.

**Time complexity:** O(n²) — sorting is O(n log n), and for each of the n anchors the inner two-pointer scan does O(n) work, so the nested scan dominates at O(n²).

**Space complexity:** O(log n) auxiliary — `Arrays.sort` on primitives uses dual-pivot quicksort, which needs O(log n) stack space; the output triplets aren't counted as auxiliary space.

**Algorithm trace** — `nums = [-1, 0, 1, 2, -1, -4]`, sorted → `[-4, -1, -1, 0, 1, 2]`

```
I=0(-4) L=1(-1) R=5(2)  sum=-3 < 0 → move L right
[-4, -1, -1, 0, 1, 2]
 I   L             R
result: []

I=0(-4) L=2(-1) R=5(2)  sum=-3 < 0 → move L right
[-4, -1, -1, 0, 1, 2]
 I       L         R
result: []

I=0(-4) L=3(0) R=5(2)  sum=-2 < 0 → move L right
[-4, -1, -1, 0, 1, 2]
 I           L     R
result: []

I=0(-4) L=4(1) R=5(2)  sum=-1 < 0 → move L right
[-4, -1, -1, 0, 1, 2]
 I              L  R
result: []

L=5 R=5 → inner loop ends (L < R is false); nums[0] != nums[1] → I advances to 1

I=1(-1) L=2(-1) R=5(2)  sum=0 → match! add [-1,-1,2], move L right & R left
[-4, -1, -1, 0, 1, 2]
     I   L         R
result: [[-1,-1,2]]

I=1(-1) L=3(0) R=4(1)  sum=0 → match! add [-1,0,1], move L right & R left
[-4, -1, -1, 0, 1, 2]
     I       L  R
result: [[-1,-1,2], [-1,0,1]]

L=4 R=3 → inner loop ends (L < R is false); nums[1] == nums[2] → I skips to 2, then advances to 3

I=3(0) L=4(1) R=5(2)  sum=3 > 0 → move R left
[-4, -1, -1, 0, 1, 2]
             I  L  R
result: [[-1,-1,2], [-1,0,1]]

L=4 R=4 → inner loop ends; I=4 gives L=5,R=5 (no comparison), I=5 gives L=6,R=5 (no comparison) → outer loop ends
```
→ return `[[-1,-1,2], [-1,0,1]]`

## 2. Optimal Approach

Sort the array, then fix each element in turn as the anchor `i` and use two pointers (`left` starting right after `i`, `right` at the array's end) to find pairs in the remaining subarray that make the triplet sum to zero. Because the array is sorted, if the current sum is too small, only moving `left` right can increase it; if it's too large, only moving `right` left can decrease it — this greedy narrowing never skips a valid pair. Skipping duplicate anchors and duplicate pointer values (after a match) keeps the result free of duplicate triplets without needing a `Set`. This is exactly the approach implemented above — the user's (corrected) solution already is the optimal one.

**Time complexity:** O(n²) — O(n log n) to sort, then O(n) anchors each doing an O(n) two-pointer scan.

**Space complexity:** O(log n) auxiliary, from the sort's internal stack; no extra data structures are needed to dedupe.

```java
public List<List<Integer>> threeSum(int[] nums) {
    Arrays.sort(nums);
    List<List<Integer>> triplets = new ArrayList<>();
    int n = nums.length;

    for (int i = 0; i < n - 2; i++) {
        if (i > 0 && nums[i] == nums[i - 1]) continue;

        int left = i + 1,
            right = n - 1;

        while (left < right) {
            int sum = nums[i] + nums[left] + nums[right];

            if (sum == 0) {
                triplets.add(List.of(nums[i], nums[left], nums[right]));
                left++;
                right--;

                while (left < right && nums[left] == nums[left - 1]) left++;
                while (left < right && nums[right] == nums[right + 1]) right--;
            } else if (sum > 0) {
                right--;
            } else {
                left++;
            }
        }
    }

    return triplets;
}
```

**Algorithm trace:** identical to the trace above — this is the same algorithm the user implemented.

## 3. Alternative Approaches

### Hash set per anchor

Fix each element as anchor `i`, then scan the remaining subarray left to right with a `HashSet<Integer>`. For each element `nums[j]`, compute the complement needed to reach zero (`-nums[i] - nums[j]`); if that complement is already in the set, a triplet is found. Add `nums[j]` to the set and continue. Since input duplicates can still produce the same triplet from different index combinations, results must be deduplicated with a `Set<List<Integer>>` of sorted triplets rather than a plain list.

**Time complexity:** O(n²) — n anchors, each doing an O(n) single pass with O(1) average hash set lookups.

**Space complexity:** O(n) — the per-anchor `HashSet`, plus the result-dedup `Set`, versus O(log n) for the two-pointer approach.

**When it's acceptable:** a reasonable answer if two pointers doesn't come to mind first, or if sorting the array is undesirable for some reason (e.g. original order must be preserved elsewhere) — same time complexity as the optimal approach, but more memory and messier dedup logic.

```java
public List<List<Integer>> threeSum(int[] nums) {
    Set<List<Integer>> uniqueTriplets = new HashSet<>();
    int n = nums.length;

    for (int i = 0; i < n; i++) {
        Set<Integer> seen = new HashSet<>();

        for (int j = i + 1; j < n; j++) {
            int complement = -nums[i] - nums[j];

            if (seen.contains(complement)) {
                List<Integer> triplet = Arrays.asList(nums[i], nums[j], complement);
                Collections.sort(triplet);
                uniqueTriplets.add(triplet);
            }

            seen.add(nums[j]);
        }
    }

    return new ArrayList<>(uniqueTriplets);
}
```

**Algorithm trace** — `nums = [-2, 0, 0, 2, 2]` (i = 0 shown; later anchors find no new triplets)

| i | j | nums[j] | complement | in seen? | action | seen after |
|---|---|---------|------------|----------|--------|-------------|
| 0 | 1 | 0 | 2 | No | add 0 to seen | {0} |
| 0 | 2 | 0 | 2 | No | add 0 to seen | {0} |
| 0 | 3 | 2 | 0 | **Yes** | add [-2,0,2] | {0,2} |
| 0 | 4 | 2 | 0 | **Yes** | duplicate of [-2,0,2] (Set dedups) | {0,2} |
→ `uniqueTriplets = {[-2,0,2]}` (remaining anchors i=1,2,3 find no new complements) → return `[[-2,0,2]]`

### Brute force — check every triple

Check every combination of three distinct indices `(i, j, k)` with `i < j < k` and record any triplet that sums to zero. No sorting is required, but a `Set<List<Integer>>` of sorted triplets is still needed to filter out duplicates.

**Time complexity:** O(n³) — three nested loops over all index combinations.

**Space complexity:** O(n) for the result-dedup set in the worst case; O(1) beyond that.

**When it's acceptable:** only for very small inputs, or as a warm-up answer under interview time pressure before optimizing to sorting + two pointers.

```java
public List<List<Integer>> threeSum(int[] nums) {
    Set<List<Integer>> uniqueTriplets = new HashSet<>();
    int n = nums.length;

    for (int i = 0; i < n; i++) {
        for (int j = i + 1; j < n; j++) {
            for (int k = j + 1; k < n; k++) {
                if (nums[i] + nums[j] + nums[k] == 0) {
                    List<Integer> triplet = Arrays.asList(nums[i], nums[j], nums[k]);
                    Collections.sort(triplet);
                    uniqueTriplets.add(triplet);
                }
            }
        }
    }

    return new ArrayList<>(uniqueTriplets);
}
```

**Algorithm trace** — `nums = [-2, 0, 0, 2, 2]`

| i | j | k | nums[i] | nums[j] | nums[k] | sum | == 0? | action |
|---|---|---|---------|---------|---------|-----|-------|--------|
| 0 | 1 | 2 | -2 | 0 | 0 | -2 | No | — |
| 0 | 1 | 3 | -2 | 0 | 2 | 0 | Yes | add [-2,0,2] |
| 0 | 1 | 4 | -2 | 0 | 2 | 0 | Yes | duplicate (Set dedups) |
| 0 | 2 | 3 | -2 | 0 | 2 | 0 | Yes | duplicate |
| 0 | 2 | 4 | -2 | 0 | 2 | 0 | Yes | duplicate |
| 0 | 3 | 4 | -2 | 2 | 2 | 2 | No | — |
| 1 | 2 | 3 | 0 | 0 | 2 | 2 | No | — |
| 1 | 2 | 4 | 0 | 0 | 2 | 2 | No | — |
| 1 | 3 | 4 | 0 | 2 | 2 | 4 | No | — |
| 2 | 3 | 4 | 0 | 2 | 2 | 4 | No | — |
→ `uniqueTriplets = {[-2,0,2]}` → return `[[-2,0,2]]`
