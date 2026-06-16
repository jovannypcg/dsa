# Binary Search

## Pattern
Eliminate half the search space each iteration by comparing the midpoint to the target or a condition — reducing O(n) to O(log n).

## How to Recognize
- Input is sorted (or the search space is monotonic)
- "Find target / index / value in sorted array"
- "Minimum / maximum value that satisfies condition X" (binary search on answer)
- "Find first / last occurrence"
- Decision function is binary: for a given `mid`, the answer is either "too small" or "too large"

## Approach
1. Define the search space `[lo, hi]`.
2. Compute `mid = lo + (hi - lo) / 2` (avoids overflow).
3. Decide which half to discard based on `nums[mid]` vs. target (or a predicate).
4. Repeat until `lo > hi` (or `lo == hi` for "find boundary" variants).

## Template

```java
// Standard — find exact target
public int search(int[] nums, int target) {
    int lo = 0, hi = nums.length - 1;

    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;

        if      (nums[mid] == target) return mid;
        else if (nums[mid] <  target) lo = mid + 1;
        else                          hi = mid - 1;
    }

    return -1;
}
```

```java
// Find leftmost position where condition is true (binary search on answer)
public int firstTrue(int[] nums) {
    int lo = 0, hi = nums.length - 1, result = -1;

    while (lo <= hi) {
        int mid = lo + (hi - lo) / 2;

        if (condition(nums, mid)) {
            result = mid;  // record candidate, keep searching left
            hi = mid - 1;
        } else {
            lo = mid + 1;
        }
    }

    return result;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Binary Search | [LeetCode #704](https://leetcode.com/problems/binary-search/) |
| 2 | Search a 2D Matrix | [LeetCode #74](https://leetcode.com/problems/search-a-2d-matrix/) |
| 3 | Koko Eating Bananas | [LeetCode #875](https://leetcode.com/problems/koko-eating-bananas/) |
| 4 | Find Minimum in Rotated Sorted Array | [LeetCode #153](https://leetcode.com/problems/find-minimum-in-rotated-sorted-array/) |
| 5 | Search in Rotated Sorted Array | [LeetCode #33](https://leetcode.com/problems/search-in-rotated-sorted-array/) |
| 6 | Time Based Key-Value Store | [LeetCode #981](https://leetcode.com/problems/time-based-key-value-store/) |
| 7 | Median of Two Sorted Arrays | [LeetCode #4](https://leetcode.com/problems/median-of-two-sorted-arrays/) |
| 8 | Find First and Last Position of Element | [LeetCode #34](https://leetcode.com/problems/find-first-and-last-position-of-element-in-sorted-array/) |
| 9 | Sqrt(x) | [LeetCode #69](https://leetcode.com/problems/sqrtx/) |
| 10 | Guess Number Higher or Lower | [LeetCode #374](https://leetcode.com/problems/guess-number-higher-or-lower/) |
| 11 | Peak Index in a Mountain Array | [LeetCode #852](https://leetcode.com/problems/peak-index-in-a-mountain-array/) |
| 12 | Capacity To Ship Packages Within D Days | [LeetCode #1011](https://leetcode.com/problems/capacity-to-ship-packages-within-d-days/) |
| 13 | Split Array Largest Sum | [LeetCode #410](https://leetcode.com/problems/split-array-largest-sum/) |
| 14 | Minimum Number of Days to Make m Bouquets | [LeetCode #1482](https://leetcode.com/problems/minimum-number-of-days-to-make-m-bouquets/) |
| 15 | Find the Duplicate Number | [LeetCode #287](https://leetcode.com/problems/find-the-duplicate-number/) |
| 16 | H-Index II | [LeetCode #275](https://leetcode.com/problems/h-index-ii/) |
| 17 | Search in Rotated Sorted Array II | [LeetCode #81](https://leetcode.com/problems/search-in-rotated-sorted-array-ii/) |
| 18 | Find K Closest Elements | [LeetCode #658](https://leetcode.com/problems/find-k-closest-elements/) |
| 19 | Successful Pairs of Spells and Potions | [LeetCode #2300](https://leetcode.com/problems/successful-pairs-of-spells-and-potions/) |
| 20 | Minimized Maximum of Products Distributed to Any Store | [LeetCode #2064](https://leetcode.com/problems/minimized-maximum-of-products-distributed-to-any-store/) |
