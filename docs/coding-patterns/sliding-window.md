# Sliding Window

## Pattern
Maintain a contiguous subarray (window) over the input, expanding and shrinking it to satisfy a constraint — avoiding redundant recomputation from scratch each step.

## How to Recognize
- "Longest / shortest subarray or substring that satisfies condition X"
- "Maximum sum of exactly k elements"
- "All substrings containing a given set of characters"
- Input is a linear sequence (array or string); contiguous elements matter

## Approach
**Fixed-size window** (window size = k):
- Add incoming element, remove outgoing element each step.

**Variable-size window** (shrink when constraint violated):
- Expand `right` unconditionally; shrink `left` until the window is valid again.

## Template

```java
// Fixed window — max sum of k elements
public int maxSumFixed(int[] nums, int k) {
    int sum = 0;
    for (int i = 0; i < k; i++) sum += nums[i];

    int max = sum;
    for (int i = k; i < nums.length; i++) {
        sum += nums[i] - nums[i - k]; // slide: add right, remove left
        max = Math.max(max, sum);
    }

    return max;
}
```

```java
// Variable window — longest subarray with at most k distinct values
public int longestSubarray(int[] nums, int k) {
    Map<Integer, Integer> freq = new HashMap<>();
    int left = 0, max = 0;

    for (int right = 0; right < nums.length; right++) {
        freq.merge(nums[right], 1, Integer::sum); // expand

        while (freq.size() > k) {                 // shrink
            freq.merge(nums[left], -1, Integer::sum);
            if (freq.get(nums[left]) == 0) freq.remove(nums[left]);
            left++;
        }

        max = Math.max(max, right - left + 1);
    }

    return max;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Best Time to Buy and Sell Stock | [LeetCode #121](https://leetcode.com/problems/best-time-to-buy-and-sell-stock/) |
| 2 | Longest Substring Without Repeating Characters | [LeetCode #3](https://leetcode.com/problems/longest-substring-without-repeating-characters/) |
| 3 | Longest Repeating Character Replacement | [LeetCode #424](https://leetcode.com/problems/longest-repeating-character-replacement/) |
| 4 | Permutation in String | [LeetCode #567](https://leetcode.com/problems/permutation-in-string/) |
| 5 | Minimum Window Substring | [LeetCode #76](https://leetcode.com/problems/minimum-window-substring/) |
| 6 | Sliding Window Maximum | [LeetCode #239](https://leetcode.com/problems/sliding-window-maximum/) |
| 7 | Find All Anagrams in a String | [LeetCode #438](https://leetcode.com/problems/find-all-anagrams-in-a-string/) |
| 8 | Minimum Size Subarray Sum | [LeetCode #209](https://leetcode.com/problems/minimum-size-subarray-sum/) |
| 9 | Fruit Into Baskets | [LeetCode #904](https://leetcode.com/problems/fruit-into-baskets/) |
| 10 | Max Consecutive Ones III | [LeetCode #1004](https://leetcode.com/problems/max-consecutive-ones-iii/) |
| 11 | Subarray Product Less Than K | [LeetCode #713](https://leetcode.com/problems/subarray-product-less-than-k/) |
| 12 | Longest Subarray of 1s After Deleting One Element | [LeetCode #1493](https://leetcode.com/problems/longest-subarray-of-1s-after-deleting-one-element/) |
| 13 | Contains Duplicate II | [LeetCode #219](https://leetcode.com/problems/contains-duplicate-ii/) |
| 14 | Number of Subarrays of Size K and Avg ≥ Threshold | [LeetCode #1343](https://leetcode.com/problems/number-of-sub-arrays-of-size-k-and-average-greater-than-or-equal-to-threshold/) |
| 15 | Longest Nice Subarray | [LeetCode #2401](https://leetcode.com/problems/longest-nice-subarray/) |
| 16 | K Radius Subarray Averages | [LeetCode #2090](https://leetcode.com/problems/k-radius-subarray-averages/) |
| 17 | Maximum Points You Can Obtain from Cards | [LeetCode #1423](https://leetcode.com/problems/maximum-points-you-can-obtain-from-cards/) |
| 18 | Grumpy Bookstore Owner | [LeetCode #1052](https://leetcode.com/problems/grumpy-bookstore-owner/) |
| 19 | Maximum Average Subarray I | [LeetCode #643](https://leetcode.com/problems/maximum-average-subarray-i/) |
| 20 | Longest Continuous Subarray With Absolute Diff ≤ Limit | [LeetCode #1438](https://leetcode.com/problems/longest-continuous-subarray-with-absolute-diff-less-than-or-equal-to-limit/) |
