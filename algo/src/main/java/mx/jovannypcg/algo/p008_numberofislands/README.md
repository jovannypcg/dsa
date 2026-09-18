# Number of Islands

**Date added:** 2026-06-13

## Problem Description

Given an `m x n` 2D binary grid `grid` which represents a map of `'1'`s (land) and `'0'`s
(water), return the number of islands.

An island is surrounded by water and is formed by connecting adjacent lands horizontally or
vertically. You may assume all four edges of the grid are all surrounded by water.

**Source:** https://leetcode.com/problems/number-of-islands/

## Examples

**Example 1**
```
Input: grid = [
  ["1","1","1","1","0"],
  ["1","1","0","1","0"],
  ["1","1","0","0","0"],
  ["0","0","0","0","0"]
]
Output: 1
Explanation: All connected '1' cells form a single island.
```

**Example 2**
```
Input: grid = [
  ["1","1","0","0","0"],
  ["1","1","0","0","0"],
  ["0","0","1","0","0"],
  ["0","0","0","1","1"]
]
Output: 3
Explanation: There are three separate groups of connected land cells, each forming its own island.
```

**Example 3**
```
Input: grid = [["1"]]
Output: 1
Explanation: A single land cell is an island by itself.
```

**Example 4**
```
Input: grid = [["0"]]
Output: 0
Explanation: No land cells exist, so there are no islands.
```

**Example 5**
```
Input: grid = [
  ["1","0","1"],
  ["0","1","0"],
  ["1","0","1"]
]
Output: 5
Explanation: Each '1' is surrounded by water on all four sides, so each forms its own island.
```

## Constraints

- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 300`
- `grid[i][j]` is `'0'` or `'1'`.

## Hints

1. Think about what it means to "explore" an island — once you find a land cell, how do you find all land cells that belong to the same island?
2. Visiting every neighbor (up, down, left, right) of a land cell and repeating for each new land cell sounds like a well-known graph traversal.
3. How do you avoid visiting the same cell twice during an exploration?
4. After fully exploring one island, how do you count the next one? What triggers a new island count?
5. You can explore using either DFS (recursion or stack) or BFS (queue) — both work. Mark visited cells so you don't recount them.
