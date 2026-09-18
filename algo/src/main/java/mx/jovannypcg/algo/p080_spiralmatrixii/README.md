# Spiral Matrix II

**Date added:** 2026-09-11

## Problem Description

Given a positive integer `n`, generate an `n x n` matrix filled with elements from 1 to n^2 in spiral order — starting at the top-left cell, placing 1 there, then moving right across the top row, then down the right column, then left across the bottom row, then up the left column, and repeating with the next inner layer until every cell has been filled.

**Source:** https://leetcode.com/problems/spiral-matrix-ii/description/

## Examples

Legend for the diagrams: node labels are the value written into that cell, and arrows follow the order values are placed (1 → 2 → 3 → ...). Cells belong to a "ring" — the outermost ring is red (`outer`), the next ring in is cyan (`mid`), the ring after that is green (`inner`), and — for matrices deep enough to have a lone center cell — that cell is gold (`center`). Smaller matrices simply run out of rings before using every color.

**Example 1**
```
Input: n = 1
Output: [[1]]
Explanation: A single-cell matrix, the minimum allowed size. There is nowhere to spiral to, so 1 is placed and the matrix is done.
```

```mermaid
block-beta
columns 1
    r0c0["1"]
    classDef outer fill:#ffadad,stroke:#d64550,color:#4a0000
    class r0c0 outer
```

**Example 2**
```
Input: n = 2
Output: [[1,2],[4,3]]
Explanation: The smallest square with an actual turn. After the top row (1, 2), the spiral drops down the right column (3) then finishes along the bottom row (4) — there is no room left for an inner ring.
```

```mermaid
block-beta
columns 2
    r0c0["1"] r0c1["2"]
    r1c0["4"] r1c1["3"]
    r0c0 --> r0c1
    r0c1 --> r1c1
    r1c1 --> r1c0
    classDef outer fill:#ffadad,stroke:#d64550,color:#4a0000
    class r0c0,r0c1,r1c0,r1c1 outer
```

**Example 3**
```
Input: n = 3
Output: [[1,2,3],[8,9,4],[7,6,5]]
Explanation: The classic case from the problem statement. The outer ring is filled clockwise (1,2,3,4,5,6,7,8), and the single leftover center cell is filled last with 9.
```

```mermaid
block-beta
columns 3
    r0c0["1"] r0c1["2"] r0c2["3"]
    r1c0["8"] r1c1["9"] r1c2["4"]
    r2c0["7"] r2c1["6"] r2c2["5"]
    r0c0 --> r0c1
    r0c1 --> r0c2
    r0c2 --> r1c2
    r1c2 --> r2c2
    r2c2 --> r2c1
    r2c1 --> r2c0
    r2c0 --> r1c0
    r1c0 --> r1c1
    classDef outer fill:#ffadad,stroke:#d64550,color:#4a0000
    classDef inner fill:#9bf6ff,stroke:#1fa2b8,color:#00343d
    class r0c0,r0c1,r0c2,r1c2,r2c2,r2c1,r2c0,r1c0 outer
    class r1c1 inner
```

**Example 4**
```
Input: n = 4
Output: [[1,2,3,4],[12,13,14,5],[11,16,15,6],[10,9,8,7]]
Explanation: An even-sized matrix (n == 4). The outer ring (1 through 12) wraps the whole border; the leftover inner 2x2 block (13, 14, 15, 16) forms its own complete ring with no single center cell, since an even-sized square never leaves exactly one cell behind.
```

```mermaid
block-beta
columns 4
    r0c0["1"] r0c1["2"] r0c2["3"] r0c3["4"]
    r1c0["12"] r1c1["13"] r1c2["14"] r1c3["5"]
    r2c0["11"] r2c1["16"] r2c2["15"] r2c3["6"]
    r3c0["10"] r3c1["9"] r3c2["8"] r3c3["7"]
    r0c0 --> r0c1
    r0c1 --> r0c2
    r0c2 --> r0c3
    r0c3 --> r1c3
    r1c3 --> r2c3
    r2c3 --> r3c3
    r3c3 --> r3c2
    r3c2 --> r3c1
    r3c1 --> r3c0
    r3c0 --> r2c0
    r2c0 --> r1c0
    r1c0 --> r1c1
    r1c1 --> r1c2
    r1c2 --> r2c2
    r2c2 --> r2c1
    classDef outer fill:#ffadad,stroke:#d64550,color:#4a0000
    classDef inner fill:#9bf6ff,stroke:#1fa2b8,color:#00343d
    class r0c0,r0c1,r0c2,r0c3,r1c3,r2c3,r3c3,r3c2,r3c1,r3c0,r2c0,r1c0 outer
    class r1c1,r1c2,r2c2,r2c1 inner
```

**Example 5**
```
Input: n = 5
Output: [[1,2,3,4,5],[16,17,18,19,6],[15,24,25,20,7],[14,23,22,21,8],[13,12,11,10,9]]
Explanation: An odd-sized matrix with three full rings: the outer ring (1-16), a middle ring (17-24), and a single leftover center cell (25) — the same nesting pattern as the n = 3 case, one layer deeper.
```

```mermaid
block-beta
columns 5
    r0c0["1"] r0c1["2"] r0c2["3"] r0c3["4"] r0c4["5"]
    r1c0["16"] r1c1["17"] r1c2["18"] r1c3["19"] r1c4["6"]
    r2c0["15"] r2c1["24"] r2c2["25"] r2c3["20"] r2c4["7"]
    r3c0["14"] r3c1["23"] r3c2["22"] r3c3["21"] r3c4["8"]
    r4c0["13"] r4c1["12"] r4c2["11"] r4c3["10"] r4c4["9"]
    r0c0 --> r0c1
    r0c1 --> r0c2
    r0c2 --> r0c3
    r0c3 --> r0c4
    r0c4 --> r1c4
    r1c4 --> r2c4
    r2c4 --> r3c4
    r3c4 --> r4c4
    r4c4 --> r4c3
    r4c3 --> r4c2
    r4c2 --> r4c1
    r4c1 --> r4c0
    r4c0 --> r3c0
    r3c0 --> r2c0
    r2c0 --> r1c0
    r1c0 --> r1c1
    r1c1 --> r1c2
    r1c2 --> r1c3
    r1c3 --> r2c3
    r2c3 --> r3c3
    r3c3 --> r3c2
    r3c2 --> r3c1
    r3c1 --> r2c1
    r2c1 --> r2c2
    classDef outer fill:#ffadad,stroke:#d64550,color:#4a0000
    classDef mid fill:#9bf6ff,stroke:#1fa2b8,color:#00343d
    classDef inner fill:#caffbf,stroke:#2f9e44,color:#0b3d0b
    class r0c0,r0c1,r0c2,r0c3,r0c4,r1c4,r2c4,r3c4,r4c4,r4c3,r4c2,r4c1,r4c0,r3c0,r2c0,r1c0 outer
    class r1c1,r1c2,r1c3,r2c3,r3c3,r3c2,r3c1,r2c1 mid
    class r2c2 inner
```

**Example 6**
```
Input: n = 6
Output: [[1,2,3,4,5,6],[20,21,22,23,24,7],[19,32,33,34,25,8],[18,31,36,35,26,9],[17,30,29,28,27,10],[16,15,14,13,12,11]]
Explanation: An even-sized matrix one step past example 4, with three full rings and — since n is even — no leftover center cell. The outer ring (1-20), middle ring (21-32), and innermost 2x2 ring (33-36) each close on themselves.
```

```mermaid
block-beta
columns 6
    r0c0["1"] r0c1["2"] r0c2["3"] r0c3["4"] r0c4["5"] r0c5["6"]
    r1c0["20"] r1c1["21"] r1c2["22"] r1c3["23"] r1c4["24"] r1c5["7"]
    r2c0["19"] r2c1["32"] r2c2["33"] r2c3["34"] r2c4["25"] r2c5["8"]
    r3c0["18"] r3c1["31"] r3c2["36"] r3c3["35"] r3c4["26"] r3c5["9"]
    r4c0["17"] r4c1["30"] r4c2["29"] r4c3["28"] r4c4["27"] r4c5["10"]
    r5c0["16"] r5c1["15"] r5c2["14"] r5c3["13"] r5c4["12"] r5c5["11"]
    r0c0 --> r0c1
    r0c1 --> r0c2
    r0c2 --> r0c3
    r0c3 --> r0c4
    r0c4 --> r0c5
    r0c5 --> r1c5
    r1c5 --> r2c5
    r2c5 --> r3c5
    r3c5 --> r4c5
    r4c5 --> r5c5
    r5c5 --> r5c4
    r5c4 --> r5c3
    r5c3 --> r5c2
    r5c2 --> r5c1
    r5c1 --> r5c0
    r5c0 --> r4c0
    r4c0 --> r3c0
    r3c0 --> r2c0
    r2c0 --> r1c0
    r1c0 --> r1c1
    r1c1 --> r1c2
    r1c2 --> r1c3
    r1c3 --> r1c4
    r1c4 --> r2c4
    r2c4 --> r3c4
    r3c4 --> r4c4
    r4c4 --> r4c3
    r4c3 --> r4c2
    r4c2 --> r4c1
    r4c1 --> r3c1
    r3c1 --> r2c1
    r2c1 --> r2c2
    r2c2 --> r2c3
    r2c3 --> r3c3
    r3c3 --> r3c2
    classDef outer fill:#ffadad,stroke:#d64550,color:#4a0000
    classDef mid fill:#9bf6ff,stroke:#1fa2b8,color:#00343d
    classDef inner fill:#caffbf,stroke:#2f9e44,color:#0b3d0b
    class r0c0,r0c1,r0c2,r0c3,r0c4,r0c5,r1c5,r2c5,r3c5,r4c5,r5c5,r5c4,r5c3,r5c2,r5c1,r5c0,r4c0,r3c0,r2c0,r1c0 outer
    class r1c1,r1c2,r1c3,r1c4,r2c4,r3c4,r4c4,r4c3,r4c2,r4c1,r3c1,r2c1 mid
    class r2c2,r2c3,r3c3,r3c2 inner
```

**Example 7**
```
Input: n = 7
Output: [[1,2,3,4,5,6,7],[24,25,26,27,28,29,8],[23,40,41,42,43,30,9],[22,39,48,49,44,31,10],[21,38,47,46,45,32,11],[20,37,36,35,34,33,12],[19,18,17,16,15,14,13]]
Explanation: The deepest example here, with four full rings: outer (1-24), a second ring (25-40), a third ring (41-48), and a single leftover center cell (49) — the same odd-sized nesting as n = 3 and n = 5, now three layers deep.
```

```mermaid
block-beta
columns 7
    r0c0["1"] r0c1["2"] r0c2["3"] r0c3["4"] r0c4["5"] r0c5["6"] r0c6["7"]
    r1c0["24"] r1c1["25"] r1c2["26"] r1c3["27"] r1c4["28"] r1c5["29"] r1c6["8"]
    r2c0["23"] r2c1["40"] r2c2["41"] r2c3["42"] r2c4["43"] r2c5["30"] r2c6["9"]
    r3c0["22"] r3c1["39"] r3c2["48"] r3c3["49"] r3c4["44"] r3c5["31"] r3c6["10"]
    r4c0["21"] r4c1["38"] r4c2["47"] r4c3["46"] r4c4["45"] r4c5["32"] r4c6["11"]
    r5c0["20"] r5c1["37"] r5c2["36"] r5c3["35"] r5c4["34"] r5c5["33"] r5c6["12"]
    r6c0["19"] r6c1["18"] r6c2["17"] r6c3["16"] r6c4["15"] r6c5["14"] r6c6["13"]
    r0c0 --> r0c1
    r0c1 --> r0c2
    r0c2 --> r0c3
    r0c3 --> r0c4
    r0c4 --> r0c5
    r0c5 --> r0c6
    r0c6 --> r1c6
    r1c6 --> r2c6
    r2c6 --> r3c6
    r3c6 --> r4c6
    r4c6 --> r5c6
    r5c6 --> r6c6
    r6c6 --> r6c5
    r6c5 --> r6c4
    r6c4 --> r6c3
    r6c3 --> r6c2
    r6c2 --> r6c1
    r6c1 --> r6c0
    r6c0 --> r5c0
    r5c0 --> r4c0
    r4c0 --> r3c0
    r3c0 --> r2c0
    r2c0 --> r1c0
    r1c0 --> r1c1
    r1c1 --> r1c2
    r1c2 --> r1c3
    r1c3 --> r1c4
    r1c4 --> r1c5
    r1c5 --> r2c5
    r2c5 --> r3c5
    r3c5 --> r4c5
    r4c5 --> r5c5
    r5c5 --> r5c4
    r5c4 --> r5c3
    r5c3 --> r5c2
    r5c2 --> r5c1
    r5c1 --> r4c1
    r4c1 --> r3c1
    r3c1 --> r2c1
    r2c1 --> r2c2
    r2c2 --> r2c3
    r2c3 --> r2c4
    r2c4 --> r3c4
    r3c4 --> r4c4
    r4c4 --> r4c3
    r4c3 --> r4c2
    r4c2 --> r3c2
    r3c2 --> r3c3
    classDef outer fill:#ffadad,stroke:#d64550,color:#4a0000
    classDef mid fill:#9bf6ff,stroke:#1fa2b8,color:#00343d
    classDef inner fill:#caffbf,stroke:#2f9e44,color:#0b3d0b
    classDef center fill:#ffd6a5,stroke:#e8890c,color:#4a2600
    class r0c0,r0c1,r0c2,r0c3,r0c4,r0c5,r0c6,r1c6,r2c6,r3c6,r4c6,r5c6,r6c6,r6c5,r6c4,r6c3,r6c2,r6c1,r6c0,r5c0,r4c0,r3c0,r2c0,r1c0 outer
    class r1c1,r1c2,r1c3,r1c4,r1c5,r2c5,r3c5,r4c5,r5c5,r5c4,r5c3,r5c2,r5c1,r4c1,r3c1,r2c1 mid
    class r2c2,r2c3,r2c4,r3c4,r4c4,r4c3,r4c2,r3c2 inner
    class r3c3 center
```

## Constraints

- `1 <= n <= 20`

## Hints

1. Think about generating the matrix the same way you'd traverse an already-filled matrix in spiral order — except instead of reading a value, you're writing the next one into that cell.
2. Track four shrinking boundaries — `top`, `bottom`, `left`, `right` — the same technique used to walk an existing matrix in spiral order.
3. Sweep each boundary in turn (left→right along the top row, top→bottom along the right column, right→left along the bottom row, bottom→top along the left column), incrementing a running counter each time you place a value.
4. After finishing a sweep along one edge, move that boundary inward so its cells are never revisited.
5. Since `n` is always a positive integer and the matrix is always square, there's no ragged-edge case to guard against — just repeat the four sweeps until the boundaries cross, e.g. `while (top <= bottom && left <= right)`.
