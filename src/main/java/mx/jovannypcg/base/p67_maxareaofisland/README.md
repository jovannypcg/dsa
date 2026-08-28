# Max Area of Island

**Date added:** 2026-08-28

## Problem Description

You are given an `m x n` binary matrix `grid`. An island is a group of 1's (representing land) connected 4-directionally (horizontal or vertical). You may assume all four edges of the grid are surrounded by water.

The area of an island is the number of cells with a value 1 in the island.

Return the maximum area of an island in `grid`. If there is no island, return 0.

**Source:** https://leetcode.com/problems/max-area-of-island/description/

## Examples

Legend for the diagrams: light blue squares are water (`0`), orange squares are land (`1`) belonging to a non-maximal island, and red squares mark the land cells that make up the island with the largest area.

**Example 1**
```
Input: grid = [[0,0,1,0,0,0,0,1,0,0,0,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,1,1,0,1,0,0,0,0,0,0,0,0],[0,1,0,0,1,1,0,0,1,0,1,0,0],[0,1,0,0,1,1,0,0,1,1,1,0,0],[0,0,0,0,0,0,0,0,0,0,1,0,0],[0,0,0,0,0,0,0,1,1,1,0,0,0],[0,0,0,0,0,0,0,1,1,0,0,0,0]]
Output: 6
Explanation: The largest island is made up of the cells (3,8), (3,10), (4,8), (4,9), (4,10), (5,10) — connected as (3,8)-(4,8)-(4,9)-(4,10)-(3,10) and (4,10)-(5,10). Note that (3,8) and (3,10) are not directly adjacent to each other; they are only connected through the island's other cells. All other islands in the grid are smaller.
```

```mermaid
block-beta
columns 13
    r0c0["0"] r0c1["0"] r0c2["1"] r0c3["0"] r0c4["0"] r0c5["0"] r0c6["0"] r0c7["1"] r0c8["0"] r0c9["0"] r0c10["0"] r0c11["0"] r0c12["0"]
    r1c0["0"] r1c1["0"] r1c2["0"] r1c3["0"] r1c4["0"] r1c5["0"] r1c6["0"] r1c7["1"] r1c8["1"] r1c9["1"] r1c10["0"] r1c11["0"] r1c12["0"]
    r2c0["0"] r2c1["1"] r2c2["1"] r2c3["0"] r2c4["1"] r2c5["0"] r2c6["0"] r2c7["0"] r2c8["0"] r2c9["0"] r2c10["0"] r2c11["0"] r2c12["0"]
    r3c0["0"] r3c1["1"] r3c2["0"] r3c3["0"] r3c4["1"] r3c5["1"] r3c6["0"] r3c7["0"] r3c8["1"] r3c9["0"] r3c10["1"] r3c11["0"] r3c12["0"]
    r4c0["0"] r4c1["1"] r4c2["0"] r4c3["0"] r4c4["1"] r4c5["1"] r4c6["0"] r4c7["0"] r4c8["1"] r4c9["1"] r4c10["1"] r4c11["0"] r4c12["0"]
    r5c0["0"] r5c1["0"] r5c2["0"] r5c3["0"] r5c4["0"] r5c5["0"] r5c6["0"] r5c7["0"] r5c8["0"] r5c9["0"] r5c10["1"] r5c11["0"] r5c12["0"]
    r6c0["0"] r6c1["0"] r6c2["0"] r6c3["0"] r6c4["0"] r6c5["0"] r6c6["0"] r6c7["1"] r6c8["1"] r6c9["1"] r6c10["0"] r6c11["0"] r6c12["0"]
    r7c0["0"] r7c1["0"] r7c2["0"] r7c3["0"] r7c4["0"] r7c5["0"] r7c6["0"] r7c7["1"] r7c8["1"] r7c9["0"] r7c10["0"] r7c11["0"] r7c12["0"]
    classDef zero fill:#eef6ff,stroke:#8fb8e0,color:#123
    classDef island fill:#ffd580,stroke:#cc8400,color:#402d00
    classDef max fill:#ff6b4a,stroke:#b3261e,stroke-width:3px,color:#3a0000
    class r0c0,r0c1,r0c3,r0c4,r0c5,r0c6,r0c8,r0c9,r0c10,r0c11,r0c12,r1c0,r1c1,r1c2,r1c3,r1c4,r1c5,r1c6,r1c10,r1c11,r1c12,r2c0,r2c3,r2c5,r2c6,r2c7,r2c8,r2c9,r2c10,r2c11,r2c12,r3c0,r3c2,r3c3,r3c6,r3c7,r3c9,r3c11,r3c12,r4c0,r4c2,r4c3,r4c6,r4c7,r4c11,r4c12,r5c0,r5c1,r5c2,r5c3,r5c4,r5c5,r5c6,r5c7,r5c8,r5c9,r5c11,r5c12,r6c0,r6c1,r6c2,r6c3,r6c4,r6c5,r6c6,r6c10,r6c11,r6c12,r7c0,r7c1,r7c2,r7c3,r7c4,r7c5,r7c6,r7c9,r7c10,r7c11,r7c12 zero
    class r0c2,r0c7,r1c7,r1c8,r1c9,r2c1,r2c2,r2c4,r3c1,r3c4,r3c5,r4c1,r4c4,r4c5,r6c7,r6c8,r6c9,r7c7,r7c8 island
    class r3c8,r3c10,r4c8,r4c9,r4c10,r5c10 max
```

**Example 2**
```
Input: grid = [[0,0,0,0,0,0,0,0]]
Output: 0
Explanation: There are no 1's anywhere in the grid, so no island exists and the answer is 0.
```

```mermaid
block-beta
columns 8
    r0c0["0"] r0c1["0"] r0c2["0"] r0c3["0"] r0c4["0"] r0c5["0"] r0c6["0"] r0c7["0"]
    classDef zero fill:#eef6ff,stroke:#8fb8e0,color:#123
    classDef island fill:#ffd580,stroke:#cc8400,color:#402d00
    classDef max fill:#ff6b4a,stroke:#b3261e,stroke-width:3px,color:#3a0000
    class r0c0,r0c1,r0c2,r0c3,r0c4,r0c5,r0c6,r0c7 zero
```

**Example 3**
```
Input: grid = [[1,1,1],[1,1,1],[1,1,1]]
Output: 9
Explanation: Every cell is land and all of them are 4-directionally connected, so the whole grid is a single island whose area equals the total number of cells.
```

```mermaid
block-beta
columns 3
    r0c0["1"] r0c1["1"] r0c2["1"]
    r1c0["1"] r1c1["1"] r1c2["1"]
    r2c0["1"] r2c1["1"] r2c2["1"]
    classDef zero fill:#eef6ff,stroke:#8fb8e0,color:#123
    classDef island fill:#ffd580,stroke:#cc8400,color:#402d00
    classDef max fill:#ff6b4a,stroke:#b3261e,stroke-width:3px,color:#3a0000
    class r0c0,r0c1,r0c2,r1c0,r1c1,r1c2,r2c0,r2c1,r2c2 max
```

**Example 4**
```
Input: grid = [[1]]
Output: 1
Explanation: The grid is a single cell of land with no neighbors, so it is its own island with area 1. This exercises the minimum allowed grid size (m == n == 1).
```

```mermaid
block-beta
columns 1
    r0c0["1"]
    classDef zero fill:#eef6ff,stroke:#8fb8e0,color:#123
    classDef island fill:#ffd580,stroke:#cc8400,color:#402d00
    classDef max fill:#ff6b4a,stroke:#b3261e,stroke-width:3px,color:#3a0000
    class r0c0 max
```

**Example 5**
```
Input: grid = [[1,1,0,0],[1,0,0,1],[0,0,1,1],[0,0,0,1]]
Output: 4
Explanation: The top-left island is (0,0)-(0,1)-(1,0), area 3. The bottom-right island is (1,3)-(2,3)-(2,2)-(3,3), area 4, which wins. This shows a grid with several separate islands of different sizes.
```

```mermaid
block-beta
columns 4
    r0c0["1"] r0c1["1"] r0c2["0"] r0c3["0"]
    r1c0["1"] r1c1["0"] r1c2["0"] r1c3["1"]
    r2c0["0"] r2c1["0"] r2c2["1"] r2c3["1"]
    r3c0["0"] r3c1["0"] r3c2["0"] r3c3["1"]
    classDef zero fill:#eef6ff,stroke:#8fb8e0,color:#123
    classDef island fill:#ffd580,stroke:#cc8400,color:#402d00
    classDef max fill:#ff6b4a,stroke:#b3261e,stroke-width:3px,color:#3a0000
    class r0c2,r0c3,r1c1,r1c2,r2c0,r2c1,r3c0,r3c1,r3c2 zero
    class r0c0,r0c1,r1c0 island
    class r1c3,r2c2,r2c3,r3c3 max
```

**Example 6**
```
Input: grid = [[1,0,1],[0,1,0],[1,0,1]]
Output: 1
Explanation: Every 1 only touches other 1's diagonally, and diagonal adjacency does not count. Each land cell is therefore its own island of area 1, so the maximum area is 1.
```

```mermaid
block-beta
columns 3
    r0c0["1"] r0c1["0"] r0c2["1"]
    r1c0["0"] r1c1["1"] r1c2["0"]
    r2c0["1"] r2c1["0"] r2c2["1"]
    classDef zero fill:#eef6ff,stroke:#8fb8e0,color:#123
    classDef island fill:#ffd580,stroke:#cc8400,color:#402d00
    classDef max fill:#ff6b4a,stroke:#b3261e,stroke-width:3px,color:#3a0000
    class r0c1,r1c0,r1c2,r2c1 zero
    class r0c0,r0c2,r1c1,r2c0,r2c2 max
```

**Example 7**
```
Input: grid = [[1,1,1,1,1],[1,0,0,0,1],[1,0,1,0,1],[1,0,0,0,1],[1,1,1,1,1]]
Output: 16
Explanation: The border cells form one connected ring of 16 land cells that runs along all four edges of the grid. The single land cell in the center, (2,2), is surrounded by water on all four sides, so it forms its own separate island of area 1 and does not connect to the ring.
```

```mermaid
block-beta
columns 5
    r0c0["1"] r0c1["1"] r0c2["1"] r0c3["1"] r0c4["1"]
    r1c0["1"] r1c1["0"] r1c2["0"] r1c3["0"] r1c4["1"]
    r2c0["1"] r2c1["0"] r2c2["1"] r2c3["0"] r2c4["1"]
    r3c0["1"] r3c1["0"] r3c2["0"] r3c3["0"] r3c4["1"]
    r4c0["1"] r4c1["1"] r4c2["1"] r4c3["1"] r4c4["1"]
    classDef zero fill:#eef6ff,stroke:#8fb8e0,color:#123
    classDef island fill:#ffd580,stroke:#cc8400,color:#402d00
    classDef max fill:#ff6b4a,stroke:#b3261e,stroke-width:3px,color:#3a0000
    class r1c1,r1c2,r1c3,r2c1,r2c3,r3c1,r3c2,r3c3 zero
    class r2c2 island
    class r0c0,r0c1,r0c2,r0c3,r0c4,r1c0,r1c4,r2c0,r2c4,r3c0,r3c4,r4c0,r4c1,r4c2,r4c3,r4c4 max
```

## Constraints

- `m == grid.length`
- `n == grid[i].length`
- `1 <= m, n <= 50`
- `grid[i][j]` is either `0` or `1`.

## Hints

1. This is a connected-components problem — you need to find each group of adjacent 1's and measure its size.
2. A depth-first (or breadth-first) search starting from any unvisited land cell can explore its entire island.
3. Mark cells as visited as you explore them (e.g., by flipping a `1` to `0`, or using a separate visited set) so you never count the same cell twice or loop forever.
4. Each time you start a fresh search from an unvisited `1`, count how many cells that search visits — that count is the area of that island.
5. Scan every cell in the grid as a potential search-starting point, and keep a running maximum of the areas you find; remember to return `0` if the grid has no land at all.
