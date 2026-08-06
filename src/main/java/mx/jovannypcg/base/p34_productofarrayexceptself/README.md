# Product of Array Except Self

**Date added:** 2026-08-06

## Problem Description

Given an integer array `nums`, return an array `answer` such that `answer[i]` is equal to the product of all the elements of `nums` except `nums[i]`. The product of any prefix or suffix of `nums` is guaranteed to fit in a 32-bit integer. You must write an algorithm that runs in O(n) time and without using the division operation.

**Source:** https://leetcode.com/problems/product-of-array-except-self

## Examples

**Example 1**
```
Input: nums = [1,2,3,4]
Output: [24,12,8,6]
Explanation: answer[0] = 2*3*4 = 24, answer[1] = 1*3*4 = 12, answer[2] = 1*2*4 = 8, answer[3] = 1*2*3 = 6.
```

**Example 2**
```
Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
Explanation: Since one element is 0, every product except the one that excludes index 2 (the zero itself) becomes 0. answer[2] = -1*1*-3*3 = 9.
```

**Example 3**
```
Input: nums = [2,3]
Output: [3,2]
Explanation: answer[0] excludes nums[0], leaving just nums[1] = 3. answer[1] excludes nums[1], leaving just nums[0] = 2.
```

**Example 4**
```
Input: nums = [1,1,1,1]
Output: [1,1,1,1]
Explanation: Every product of the other three 1s is 1.
```

**Example 5**
```
Input: nums = [-1,-1,-1]
Output: [1,1,1]
Explanation: Each answer[i] is the product of the two remaining -1 values, and (-1)*(-1) = 1.
```

## Constraints

- `2 <= nums.length <= 10^5`
- `-30 <= nums[i] <= 30`
- The input is generated such that `answer[i]` is guaranteed to fit in a 32-bit integer.
- Follow up: can you solve it in O(1) extra space complexity, not counting the output array?

## Hints

1. A brute-force approach recomputes the product of all other elements for every index — what is its time complexity?
2. Since you can't use division, think about splitting the problem into two passes: what's to the left of `i`, and what's to the right of `i`.
3. Try building a "prefix products" array where `prefix[i]` holds the product of all elements before index `i`.
4. Now do the same from the right to get a "suffix products" array, then multiply `prefix[i] * suffix[i]`.
5. For the O(1) extra space follow-up, can you fill `answer` with prefix products first, then do a second pass from the right multiplying in the suffix product using just one running variable?
