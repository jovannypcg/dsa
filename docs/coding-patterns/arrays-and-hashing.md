# Arrays & Hashing

## Pattern
Use a hash map or hash set to trade space for time — avoid O(n²) nested loops by storing and looking up values in O(1).

## How to Recognize
- "Does X exist in the array?"
- "Find two elements that satisfy condition Y"
- "Count frequencies / group elements by property"
- "Find duplicates / missing values"
- No ordering requirement (or ordering is irrelevant to the solution)

## Approach
1. Identify what needs to be tracked (seen values, counts, index mappings).
2. Choose the right structure: `HashMap` for key→value, `HashSet` for membership.
3. Single pass: for each element, check the map, then insert.

## Template

```java
public int[] example(int[] nums, int target) {
    Map<Integer, Integer> map = new HashMap<>(); // value → index

    for (int i = 0; i < nums.length; i++) {
        int complement = target - nums[i];

        if (map.containsKey(complement)) {
            return new int[]{map.get(complement), i};
        }

        map.put(nums[i], i);
    }

    return new int[]{};
}
```

**Frequency count variant:**
```java
Map<Integer, Integer> freq = new HashMap<>();
for (int n : nums) {
    freq.merge(n, 1, Integer::sum); // freq.put(n, freq.getOrDefault(n, 0) + 1)
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Two Sum | [LeetCode #1](https://leetcode.com/problems/two-sum/) |
| 2 | Contains Duplicate | [LeetCode #217](https://leetcode.com/problems/contains-duplicate/) |
| 3 | Valid Anagram | [LeetCode #242](https://leetcode.com/problems/valid-anagram/) |
| 4 | Group Anagrams | [LeetCode #49](https://leetcode.com/problems/group-anagrams/) |
| 5 | Top K Frequent Elements | [LeetCode #347](https://leetcode.com/problems/top-k-frequent-elements/) |
| 6 | Product of Array Except Self | [LeetCode #238](https://leetcode.com/problems/product-of-array-except-self/) |
| 7 | Encode and Decode Strings | [NeetCode](https://neetcode.io/problems/string-encode-and-decode) |
| 8 | Longest Consecutive Sequence | [LeetCode #128](https://leetcode.com/problems/longest-consecutive-sequence/) |
| 9 | Valid Sudoku | [LeetCode #36](https://leetcode.com/problems/valid-sudoku/) |
| 10 | Ransom Note | [LeetCode #383](https://leetcode.com/problems/ransom-note/) |
| 11 | Word Pattern | [LeetCode #290](https://leetcode.com/problems/word-pattern/) |
| 12 | Isomorphic Strings | [LeetCode #205](https://leetcode.com/problems/isomorphic-strings/) |
| 13 | Find the Difference | [LeetCode #389](https://leetcode.com/problems/find-the-difference/) |
| 14 | Intersection of Two Arrays II | [LeetCode #350](https://leetcode.com/problems/intersection-of-two-arrays-ii/) |
| 15 | Subarray Sum Equals K | [LeetCode #560](https://leetcode.com/problems/subarray-sum-equals-k/) |
| 16 | 4Sum II | [LeetCode #454](https://leetcode.com/problems/4sum-ii/) |
| 17 | First Missing Positive | [LeetCode #41](https://leetcode.com/problems/first-missing-positive/) |
| 18 | Majority Element | [LeetCode #169](https://leetcode.com/problems/majority-element/) |
| 19 | Find All Duplicates in an Array | [LeetCode #442](https://leetcode.com/problems/find-all-duplicates-in-an-array/) |
| 20 | Two Sum II (sorted) | [LeetCode #167](https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/) |
