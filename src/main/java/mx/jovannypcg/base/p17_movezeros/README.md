# Move Zeros

**Date added:** 2026-06-30

## Problem Description

Given an integer array `nums`, move all `0`'s to the end of it while maintaining the relative
order of the non-zero elements.

The operation must be done in-place without making a copy of the array.

**Source:** https://neetcode.io/problems/move-zeroes

## Examples

**Example 1**
```
Input: nums = [0,0,1,2,0,5]
Output: [1,2,5,0,0,0]
Explanation: The three zeroes are moved to the end while 1, 2, and 5 keep their relative order.
```

**Example 2**
```
Input: nums = [0,1,0]
Output: [1,0,0]
Explanation: The zeroes are moved to the end; 1 stays at the front.
```

**Example 3**
```
Input: nums = [1]
Output: [1]
Explanation: A single non-zero element — nothing to move.
```

**Example 4**
```
Input: nums = [0]
Output: [0]
Explanation: A single zero — nothing to move.
```

**Example 5**
```
Input: nums = [1,2,3]
Output: [1,2,3]
Explanation: No zeroes present — array is unchanged.
```

## Constraints

- `1 <= nums.length <= 10,000`
- `-(2^31) <= nums[i] <= (2^31) - 1`

## Hints

1. Think about what it means to "place" a non-zero element — where exactly should it go?
2. Try maintaining a pointer that tracks where the next non-zero element should be written.
3. A single pass can place all non-zero elements at the front in order — what's left to do after that?
4. After all non-zero elements are placed, the remaining positions should all become zero.
5. Two pointers — one scanning the array, one marking the "write position" — can solve this in O(n) time and O(1) space.
