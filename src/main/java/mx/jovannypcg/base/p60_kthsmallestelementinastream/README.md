# Kth Smallest Element in a Stream

**Date added:** 2026-08-25

## Problem Description

This is the mirror image of [Kth Largest Element in a Stream](../p59_kthlargestelementinastream): instead of tracking the top of the pool, the admissions office now wants to keep an eye on the lower end of the distribution — the kth *lowest* test score seen so far, updated in real time as new scores arrive.

You are tasked to implement a class which, for a given integer `k`, maintains a stream of test scores and continuously returns the kth lowest test score after a new score has been submitted. More specifically, we are looking for the kth smallest score in the sorted list of all scores.

Implement the `KthSmallest` class:

- `KthSmallest(int k, int[] nums)` Initializes the object with the integer `k` and the stream of test scores `nums`.
- `int add(int val)` Adds a new test score `val` to the stream and returns the element representing the kth smallest element in the pool of test scores so far.

## Examples

**Example 1**
```
Input:
["KthSmallest", "add", "add", "add", "add", "add"]
[[3, [4, 5, 8, 7]], [6], [3], [1], [2], [9]]

Output: [null, 6, 5, 4, 3, 3]
```
Explanation: `KthSmallest kthSmallest = new KthSmallest(3, [4, 5, 8, 7]);` (3rd smallest of `[4,5,7,8]` is 7, but that's before any `add` call) then `kthSmallest.add(6)` returns 6 (sorted: `4,5,6,7,8` → 3rd smallest is 6), `kthSmallest.add(3)` returns 5, `kthSmallest.add(1)` returns 4, `kthSmallest.add(2)` returns 3, `kthSmallest.add(9)` returns 3.

**Example 2**
```
Input:
["KthSmallest", "add", "add", "add", "add"]
[[4, [-7, -7, -7, -7, -8, -3]], [-2], [-10], [-9], [-9]]

Output: [null, -7, -7, -7, -8]
```
Explanation: `KthSmallest kthSmallest = new KthSmallest(4, [-7, -7, -7, -7, -8, -3]);` then `kthSmallest.add(-2)` returns -7, `kthSmallest.add(-10)` returns -7, `kthSmallest.add(-9)` returns -7, `kthSmallest.add(-9)` returns -8.

**Example 3**
```
Input:
["KthSmallest", "add", "add", "add"]
[[1, []], [-3], [5], [-10]]

Output: [null, -3, -3, -10]
```
Explanation: With `k = 1`, the kth smallest element is simply the minimum value seen so far. Starting from an empty pool, adding -3 makes it the min. Adding 5 doesn't change the min (still -3). Adding -10 makes -10 the new min.

## Constraints

- `0 <= nums.length <= 10^4`
- `1 <= k <= nums.length + 1`
- `-10^4 <= nums[i] <= 10^4`
- `-10^4 <= val <= 10^4`
- At most `10^4` calls will be made to `add`.
- It is guaranteed that there will be at least `k` elements in the pool when you search for the kth element.

## Hints

1. This is the mirror of tracking the kth *largest* element — think about what changes if you flip "largest" to "smallest" everywhere.
2. For the kth largest problem, a bounded min-heap of size `k` works because its root is the smallest of the "top k" values. What's the analogous structure here?
3. Try keeping a bounded **max-heap** of size `k` containing the `k` smallest values seen so far — its root (the maximum of that set) is exactly the kth smallest overall.
4. On each `add`: push the new value, and if the heap grows past size `k`, pop the *largest* element instead of the smallest.
5. In Java, `PriorityQueue` is a min-heap by default — you'll need to pass a comparator (e.g. `Comparator.reverseOrder()`) to make it behave as a max-heap.
