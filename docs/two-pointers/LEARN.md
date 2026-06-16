# Two Pointers

## Pattern
Use two indices — moving toward each other or in the same direction — to reduce an O(n²) brute force to O(n).

## How to Recognize
- Input is sorted (or can be sorted without affecting the answer)
- "Find a pair / triplet that satisfies condition X"
- "Remove duplicates / elements in-place"
- "Is this string a palindrome?"
- Need to compare elements from both ends simultaneously

## Approach
**Opposite ends** (sorted array, palindrome):
- Start `left = 0`, `right = n-1`; move inward based on comparison.

**Same direction** (remove duplicates, fast/slow):
- `slow` marks the write position; `fast` scans ahead.

## Template

```java
// Opposite ends — pair sum in sorted array
public boolean hasPairSum(int[] nums, int target) {
    int left = 0, right = nums.length - 1;

    while (left < right) {
        int sum = nums[left] + nums[right];

        if (sum == target)  return true;
        else if (sum < target) left++;
        else                   right--;
    }

    return false;
}
```

```java
// Same direction — remove duplicates in-place
public int removeDuplicates(int[] nums) {
    int slow = 1;

    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[fast - 1]) {
            nums[slow++] = nums[fast];
        }
    }

    return slow;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Valid Palindrome | [LeetCode #125](https://leetcode.com/problems/valid-palindrome/) |
| 2 | Two Sum II | [LeetCode #167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) |
| 3 | 3Sum | [LeetCode #15](https://leetcode.com/problems/3sum/) |
| 4 | Container With Most Water | [LeetCode #11](https://leetcode.com/problems/container-with-most-water/) |
| 5 | Trapping Rain Water | [LeetCode #42](https://leetcode.com/problems/trapping-rain-water/) |
| 6 | Remove Duplicates from Sorted Array | [LeetCode #26](https://leetcode.com/problems/remove-duplicates-from-sorted-array/) |
| 7 | Move Zeroes | [LeetCode #283](https://leetcode.com/problems/move-zeroes/) |
| 8 | Squares of a Sorted Array | [LeetCode #977](https://leetcode.com/problems/squares-of-a-sorted-array/) |
| 9 | 4Sum | [LeetCode #18](https://leetcode.com/problems/4sum/) |
| 10 | 3Sum Closest | [LeetCode #16](https://leetcode.com/problems/3sum-closest/) |
| 11 | Sort Colors (Dutch flag) | [LeetCode #75](https://leetcode.com/problems/sort-colors/) |
| 12 | Reverse String | [LeetCode #344](https://leetcode.com/problems/reverse-string/) |
| 13 | Is Subsequence | [LeetCode #392](https://leetcode.com/problems/is-subsequence/) |
| 14 | Minimum Size Subarray Sum | [LeetCode #209](https://leetcode.com/problems/minimum-size-subarray-sum/) |
| 15 | Palindrome Linked List | [LeetCode #234](https://leetcode.com/problems/palindrome-linked-list/) |
| 16 | Boats to Save People | [LeetCode #881](https://leetcode.com/problems/boats-to-save-people/) |
| 17 | Bag of Tokens | [LeetCode #948](https://leetcode.com/problems/bag-of-tokens/) |
| 18 | Longest Mountain in Array | [LeetCode #845](https://leetcode.com/problems/longest-mountain-in-array/) |
| 19 | Remove Element | [LeetCode #27](https://leetcode.com/problems/remove-element/) |
| 20 | Merge Sorted Array | [LeetCode #88](https://leetcode.com/problems/merge-sorted-array/) |
