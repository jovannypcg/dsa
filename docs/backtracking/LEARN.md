# Backtracking

## Pattern
Build a solution incrementally, one choice at a time. Abandon ("backtrack") a partial solution as soon as it violates a constraint, then try the next option.

## How to Recognize
- "All permutations / combinations / subsets"
- "Find all valid solutions" (not just one, not a count)
- N-Queens, Sudoku, word search on a grid
- Constraint satisfaction where you must explore possibilities

## Approach
The structure is always: **choose → explore → unchoose**.
1. If the current path is a complete valid solution → record it, return.
2. For each available choice:
   - Make the choice (add to path, mark visited).
   - Recurse.
   - Undo the choice (remove from path, unmark).

Pruning: skip choices that already violate constraints before recursing.

## Template

```java
// Generic backtracking skeleton
public List<List<Integer>> solve(int[] nums) {
    List<List<Integer>> result = new ArrayList<>();
    backtrack(nums, new ArrayList<>(), result);
    return result;
}

private void backtrack(int[] nums, List<Integer> path, List<List<Integer>> result) {
    if (isSolution(path)) {          // base case
        result.add(new ArrayList<>(path));
        return;
    }

    for (int choice : choices(nums, path)) {
        if (!isValid(choice, path)) continue; // prune

        path.add(choice);            // choose
        backtrack(nums, path, result); // explore
        path.remove(path.size() - 1); // unchoose
    }
}
```

```java
// Subsets (no duplicates)
private void subsets(int[] nums, int start, List<Integer> path, List<List<Integer>> result) {
    result.add(new ArrayList<>(path)); // every path is a valid subset

    for (int i = start; i < nums.length; i++) {
        path.add(nums[i]);
        subsets(nums, i + 1, path, result); // i+1 prevents reuse
        path.remove(path.size() - 1);
    }
}
```

```java
// Permutations
private void permute(int[] nums, boolean[] used, List<Integer> path, List<List<Integer>> result) {
    if (path.size() == nums.length) {
        result.add(new ArrayList<>(path));
        return;
    }

    for (int i = 0; i < nums.length; i++) {
        if (used[i]) continue;
        used[i] = true;
        path.add(nums[i]);
        permute(nums, used, path, result);
        path.remove(path.size() - 1);
        used[i] = false;
    }
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Subsets | [LeetCode #78](https://leetcode.com/problems/subsets/) |
| 2 | Subsets II (with duplicates) | [LeetCode #90](https://leetcode.com/problems/subsets-ii/) |
| 3 | Combination Sum | [LeetCode #39](https://leetcode.com/problems/combination-sum/) |
| 4 | Combination Sum II | [LeetCode #40](https://leetcode.com/problems/combination-sum-ii/) |
| 5 | Permutations | [LeetCode #46](https://leetcode.com/problems/permutations/) |
| 6 | Permutations II (with duplicates) | [LeetCode #47](https://leetcode.com/problems/permutations-ii/) |
| 7 | Word Search | [LeetCode #79](https://leetcode.com/problems/word-search/) |
| 8 | Palindrome Partitioning | [LeetCode #131](https://leetcode.com/problems/palindrome-partitioning/) |
| 9 | Letter Combinations of a Phone Number | [LeetCode #17](https://leetcode.com/problems/letter-combinations-of-a-phone-number/) |
| 10 | N-Queens | [LeetCode #51](https://leetcode.com/problems/n-queens/) |
| 11 | N-Queens II | [LeetCode #52](https://leetcode.com/problems/n-queens-ii/) |
| 12 | Sudoku Solver | [LeetCode #37](https://leetcode.com/problems/sudoku-solver/) |
| 13 | Generate Parentheses | [LeetCode #22](https://leetcode.com/problems/generate-parentheses/) |
| 14 | Restore IP Addresses | [LeetCode #93](https://leetcode.com/problems/restore-ip-addresses/) |
| 15 | Combinations | [LeetCode #77](https://leetcode.com/problems/combinations/) |
| 16 | Beautiful Arrangement | [LeetCode #526](https://leetcode.com/problems/beautiful-arrangement/) |
| 17 | Find Unique Binary String | [LeetCode #1980](https://leetcode.com/problems/find-unique-binary-string/) |
| 18 | Remove Invalid Parentheses | [LeetCode #301](https://leetcode.com/problems/remove-invalid-parentheses/) |
| 19 | Expression Add Operators | [LeetCode #282](https://leetcode.com/problems/expression-add-operators/) |
| 20 | Word Search II (Trie + backtracking) | [LeetCode #212](https://leetcode.com/problems/word-search-ii/) |
