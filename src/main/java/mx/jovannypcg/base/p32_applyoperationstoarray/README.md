# Apply Operations to an Array

**Date added:** 2026-08-04

## Problem Description

You are given a 0-indexed array `nums` of size `n` consisting of non-negative integers. You need to apply `n - 1` operations to this array where, in the `i`th operation (0-indexed), you will apply the following on the `i`th element of `nums`: if `nums[i] == nums[i + 1]`, then multiply `nums[i]` by 2 and set `nums[i + 1]` to 0. Otherwise, you skip this operation. After performing all the operations, shift all the 0's to the end of the array. Note that the operations are applied sequentially, not all at once. Return the resulting array.

**Source:** https://leetcode.com/problems/apply-operations-to-an-array/

## Examples

**Example 1**
```
Input: nums = [1,2,2,1,1,0]
Output: [1,4,2,0,0,0]
Explanation: We do the following operations:
- i = 0: nums[0] and nums[1] are not equal, so we skip this operation.
- i = 1: nums[1] and nums[2] are equal, we multiply nums[1] by 2 and change nums[2] to 0. The array becomes [1,4,0,1,1,0].
- i = 2: nums[2] and nums[3] are not equal, so we skip this operation.
- i = 3: nums[3] and nums[4] are equal, we multiply nums[3] by 2 and change nums[4] to 0. The array becomes [1,4,0,2,0,0].
- i = 4: nums[4] and nums[5] are equal, we multiply nums[4] by 2 and change nums[5] to 0. The array becomes [1,4,0,2,0,0].
After that, we shift the 0's to the end, which gives the array [1,4,2,0,0,0].
```

**Example 2**
```
Input: nums = [0,1]
Output: [1,0]
Explanation: No operation can be applied, we just shift the 0 to the end.
```

## Constraints

- `2 <= nums.length <= 2000`
- `0 <= nums[i] <= 1000`

## Hints

1. Think about what each operation actually does to a pair of adjacent equal elements — it merges them into one, leaving a 0 behind.
2. You can process the array with a single left-to-right pass, comparing each element to its neighbor.
3. After the merge pass, you'll have some zeros scattered through the array — how would you move all of them to the end while preserving the relative order of the non-zero elements?
4. A second pointer that tracks "the next position to place a non-zero value" can help you compact the array in a single additional pass.
5. Both passes can be done in-place without extra array allocation, giving an O(n) time, O(1) extra space solution (excluding the output array).
