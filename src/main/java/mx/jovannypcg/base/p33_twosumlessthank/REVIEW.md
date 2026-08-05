| | |
|---|---|
| **Solved on** | 2026-08-05 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** The solution is correct. It sorts the array, then walks two pointers inward from both ends. When the sum is too large (`>= k`), it shrinks from the right, since every remaining left index paired with that right value would also be too large (the array is sorted ascending). When the sum is valid (`< k`), it records the sum and advances the left pointer, since a larger left value paired with the same right value could still be valid and larger. This greedy narrowing never discards a pair that could have been the true maximum, so it correctly returns `-1` when no pair qualifies (single-element arrays, all pairs `>= k`) and the maximum valid sum otherwise.

**Code quality:** Clean and idiomatic. Variable names (`left`, `right`, `maxSum`) are clear, the multi-declaration block at the top groups related state, and the loop body reads as two clearly separated branches.

**Time complexity:** O(n log n) — dominated by `Arrays.sort`; the two-pointer scan itself is O(n).

**Space complexity:** O(1) auxiliary — sorting is done in place (ignoring the O(log n) internal stack space `Arrays.sort` may use for primitives).

**Algorithm trace** — `nums = [34, 23, 1, 24, 75, 33, 54, 8]`, `k = 60`, sorted → `[1, 8, 23, 24, 33, 34, 54, 75]`

```
L=0(1) R=7(75)  sum=76 ≥ 60 → move R left   maxSum=-1
[1, 8, 23, 24, 33, 34, 54, 75]
 L                          R

L=0(1) R=6(54)  sum=55 < 60 → maxSum=55, move L right
[1, 8, 23, 24, 33, 34, 54, 75]
 L                      R

L=1(8) R=6(54)  sum=62 ≥ 60 → move R left   maxSum=55
[1, 8, 23, 24, 33, 34, 54, 75]
    L                   R

L=1(8) R=5(34)  sum=42 < 60 → maxSum=55 (unchanged), move L right
[1, 8, 23, 24, 33, 34, 54, 75]
    L               R

L=2(23) R=5(34)  sum=57 < 60 → maxSum=57, move L right
[1, 8, 23, 24, 33, 34, 54, 75]
        L           R

L=3(24) R=5(34)  sum=58 < 60 → maxSum=58, move L right
[1, 8, 23, 24, 33, 34, 54, 75]
            L       R

L=4(33) R=5(34)  sum=67 ≥ 60 → move R left   maxSum=58
[1, 8, 23, 24, 33, 34, 54, 75]
                L   R

L=4 R=4 → loop ends (L < R is false)
```
→ return `58`

## 2. Optimal Approach

Sort the array, then use two pointers starting at both ends. If the current pair's sum is `< k`, it's a valid candidate — record it and move the left pointer right to look for a larger valid sum. If the sum is `>= k`, it's too big — move the right pointer left, since every larger-or-equal left index would only make the sum bigger with this same right value. This is exactly the approach implemented above; the user's solution already is the optimal one.

**Time complexity:** O(n log n), from sorting; the pointer sweep afterward is O(n).

**Space complexity:** O(1) extra space, beyond what in-place sorting requires.

```java
public int twoSumLessThanK(int[] nums, int k) {
    Arrays.sort(nums);

    int left = 0,
        right = nums.length - 1,
        maxSum = -1;

    while (left < right) {
        int sum = nums[left] + nums[right];

        if (sum < k) {
            maxSum = Math.max(maxSum, sum);
            left++;
        } else {
            right--;
        }
    }

    return maxSum;
}
```

**Algorithm trace:** identical to the trace above — this is the same algorithm the user implemented.

## 3. Alternative Approaches

### Brute force — check every pair

Compare every pair `(i, j)` with `i < j` and track the largest sum that stays below `k`. No sorting needed.

**Time complexity:** O(n²) — nested loop over all pairs.

**Space complexity:** O(1) — no extra storage beyond the running max.

**When it's acceptable:** fine for very small inputs, or as a warm-up answer under interview time pressure before optimizing to two pointers.

```java
public int twoSumLessThanK(int[] nums, int k) {
    int maxSum = -1;

    for (int i = 0; i < nums.length; i++) {
        for (int j = i + 1; j < nums.length; j++) {
            int sum = nums[i] + nums[j];

            if (sum < k) {
                maxSum = Math.max(maxSum, sum);
            }
        }
    }

    return maxSum;
}
```

**Algorithm trace** — `nums = [1, 2, 3, 4]`, `k = 6`

| i | j | nums[i] | nums[j] | sum | < k? | maxSum |
|---|---|---------|---------|-----|------|--------|
| 0 | 1 | 1 | 2 | 3 | Yes | 3 |
| 0 | 2 | 1 | 3 | 4 | Yes | 4 |
| 0 | 3 | 1 | 4 | 5 | Yes | 5 |
| 1 | 2 | 2 | 3 | 5 | Yes | 5 |
| 1 | 3 | 2 | 4 | 6 | No | 5 |
| 2 | 3 | 3 | 4 | 7 | No | 5 |
→ return `5`

### Sort + binary search per element

Sort the array, then for each index `i`, binary search among indices `> i` for the rightmost `j` such that `nums[i] + nums[j] < k`. Track the best sum found across all `i`.

**Time complexity:** O(n log n) — sorting, plus n binary searches each O(log n).

**Space complexity:** O(1) extra (iterative binary search), or O(log n) if implemented recursively due to call stack.

**When it's acceptable:** a reasonable lateral answer if two pointers doesn't come to mind first — same asymptotic complexity, though slightly more code and constant-factor overhead than the two-pointer sweep.

```java
public int twoSumLessThanK(int[] nums, int k) {
    Arrays.sort(nums);
    int maxSum = -1;

    for (int i = 0; i < nums.length - 1; i++) {
        int lo = i + 1,
            hi = nums.length - 1,
            bestJ = -1;

        while (lo <= hi) {
            int mid = lo + (hi - lo) / 2;

            if (nums[i] + nums[mid] < k) {
                bestJ = mid;
                lo = mid + 1;
            } else {
                hi = mid - 1;
            }
        }

        if (bestJ != -1) {
            maxSum = Math.max(maxSum, nums[i] + nums[bestJ]);
        }
    }

    return maxSum;
}
```

**Algorithm trace** — `nums = [1, 2, 3, 4]`, `k = 6`

| i | lo | hi | mid | nums[mid] | sum | < k? | action |
|---|----|----|-----|-----------|-----|------|--------|
| 0 | 1 | 3 | 2 | 3 | 4 | Yes | bestJ=2, lo=3 |
| 0 | 3 | 3 | 3 | 4 | 5 | Yes | bestJ=3, lo=4 |
| 0 | 4 | 3 | — | — | — | — | loop ends (lo > hi), maxSum=1+4=5 |
| 1 | 2 | 3 | 2 | 3 | 5 | Yes | bestJ=2, lo=3 |
| 1 | 3 | 3 | 3 | 4 | 6 | No | hi=2 |
| 1 | 3 | 2 | — | — | — | — | loop ends, maxSum stays 5 (2+3=5, not greater) |
| 2 | 3 | 3 | 3 | 4 | 7 | No | hi=2, loop ends, no feasible j |
→ return `5`
