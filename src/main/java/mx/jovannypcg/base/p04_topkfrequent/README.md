# Top K Frequent Elements

**Date added:** 2026-06-10

## Problem Description

Given an integer array `nums` and an integer `k`, return the `k` most frequent elements
within the array. The test cases are generated such that the answer is always unique, and
the output may be returned in any order.

**Source:** https://neetcode.io/problems/top-k-elements-in-list

## Examples

**Example 1**
```
Input: nums = [1,2,2,3,3,3], k = 2
Output: [2,3]
Explanation: 3 appears 3 times and 2 appears 2 times — the two most frequent elements.
```

**Example 2**
```
Input: nums = [7,7], k = 1
Output: [7]
Explanation: 7 is the only element, so it is trivially the most frequent.
```

## Constraints

- `1 <= nums.length <= 10^4`
- `-1000 <= nums[i] <= 1000`
- `1 <= k <= number of distinct elements in nums`

## Hints

1. Before you can rank elements by frequency, you need to know how often each one appears — what data structure gives you that in one pass?
2. Once you have a frequency map, the problem reduces to: "find the top k entries by value." What sorting or selection strategy applies?
3. A min-heap of size k can track the top k elements as you scan — what is its time complexity compared to a full sort?
4. Think about the range of possible frequencies: each frequency is between 1 and `nums.length`. Is there a way to use that bounded range to sort in O(n)?
5. Bucket sort: create `n+1` buckets indexed by frequency, place each number into its bucket, then read from the highest-frequency bucket downward until you have k elements.
