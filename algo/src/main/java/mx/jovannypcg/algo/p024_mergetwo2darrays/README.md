# Merge Two 2D Arrays by Summing Values

**Date added:** 2026-07-11

## Problem Description

You are given two 2D integer arrays `nums1` and `nums2`.

`nums1[i] = [idi, vali]` indicates that the number with the id `idi` has a value equal to
`vali`. `nums2[i] = [idi, vali]` indicates that the number with the id `idi` has a value equal
to `vali`. Each array contains unique ids and is sorted in ascending order by id.

Merge the two arrays into one array that is sorted in ascending order by id, respecting the
following conditions:

- Only ids that appear in at least one of the two arrays should be included in the resulting
  array.
- Each id should be included only once and its value should be the sum of the values of this
  id in the two arrays. If the id does not exist in one of the two arrays, then assume its
  value in that array to be 0.

Return the resulting array. The returned array must be sorted in ascending order by id.

**Source:** https://leetcode.com/problems/merge-two-2d-arrays-by-summing-values/

## Examples

**Example 1**
```
Input: nums1 = [[1,2],[2,3],[4,5]], nums2 = [[1,4],[3,2],[4,1]]
Output: [[1,6],[2,3],[3,2],[4,6]]
Explanation: The resulting array contains the following:
- id = 1, the value of this id is 2 + 4 = 6.
- id = 2, the value of this id is 3.
- id = 3, the value of this id is 2.
- id = 4, the value of this id is 5 + 1 = 6.
```

**Example 2**
```
Input: nums1 = [[2,4],[3,6],[5,5]], nums2 = [[1,3],[4,3]]
Output: [[1,3],[2,4],[3,6],[4,3],[5,5]]
Explanation: There are no common ids, so we just include each id with its value in the resulting list.
```

**Example 3**
```
Input: nums1 = [[1,3]], nums2 = [[1,7]]
Output: [[1,10]]
Explanation: Both arrays share the same single id, so its value is 3 + 7 = 10.
```

**Example 4**
```
Input: nums1 = [[1,1000]], nums2 = [[1,1000]]
Output: [[1,2000]]
Explanation: Values sit at the constraint's upper bound; their sum (2000) must not overflow or be truncated.
```

**Example 5**
```
Input: nums1 = [[1,5],[2,6]], nums2 = [[3,7]]
Output: [[1,5],[2,6],[3,7]]
Explanation: nums2's id (3) is greater than every id in nums1, so it is simply appended at the end.
```

## Constraints

- `1 <= nums1.length, nums2.length <= 200`
- `nums1[i].length == nums2[j].length == 2`
- `1 <= idi, vali <= 1000`
- Both arrays contain unique ids.
- Both arrays are in strictly ascending order by id.

## Hints

1. Both arrays are already sorted by id — how could that help you merge them efficiently?
2. Think about the classic "merge step" from merge sort, but adapted to compare ids instead of merging blindly.
3. At each step, compare the current id from each array: if they match, sum the values; if not, take the smaller one and advance only that pointer.
4. Use two pointers, one per array, and advance the pointer(s) whose id was just consumed.
5. Don't forget to drain whichever array still has remaining elements once the other is exhausted.
