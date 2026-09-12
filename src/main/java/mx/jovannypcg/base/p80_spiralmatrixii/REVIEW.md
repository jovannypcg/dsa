| | |
|---|---|
| **Solved on** | 2026-09-11 |
| **DSA Category** | Arrays & Hashing |

## 1. Your Solution Assessment

**Correctness:** Correct, and correct without the `if (top < bottom)` / `if (left < right)` guards that the sibling read-direction problem (Spiral Matrix, p79) needed. That's not an oversight — for a square `n x n` matrix, `bottom - top` and `right - left` shrink at exactly the same rate every ring (both start at `n - 1` and both boundaries move one step per ring), so the region left to fill is always either a full rectangle or, at the very last ring, a single cell. It can never collapse into a lone row with several columns left (or vice versa), which is the only situation those guards exist to protect against. Because of that, the third and fourth loops' own bounds (`right - 1 >= left`, `bottom - 1 > top`) already evaluate to "don't run" exactly when a guard would have said so — the guards become redundant, not wrong to omit. All 7 tests pass, including the `n = 1` and `n = 20` boundaries.

**Code quality:** Clean. Grouped variable declarations with a blank line before the loop match the style used elsewhere in this repo, and the early `n <= 0` guard is a thoughtful touch beyond what the constraints strictly require (`1 <= n <= 20` already rules it out, but it documents the precondition explicitly). The four sweeps read in the same "top, right, bottom, left" order as the boundary shrink at the end, which keeps the two in sync visually.

**Time complexity:** O(n²). Every one of the n² cells is written exactly once; the boundaries only ever move inward.

**Space complexity:** O(1) extra space, not counting the output matrix itself (which is O(n²) and unavoidable — it *is* the answer). Only four boundary integers and the counter are used.

**Algorithm trace** (Step table) — input `n = 3`:

| Ring | top,bottom,left,right (before) | Top row (left→right) | Right col (top+1→bottom) | Bottom row (right-1→left) | Left col (bottom-1→top+1) | item after ring |
|---|---|---|---|---|---|---|
| 1 | 0,2,0,2 | matrix[0][0..2] = 1,2,3 | matrix[1..2][2] = 4,5 | matrix[2][1..0] = 6,7 | matrix[1][0] = 8 | 9 |
| 2 | 1,1,1,1 | matrix[1][1] = 9 | skipped (top+1=2 > bottom=1) | skipped (right-1=0 < left=1) | skipped (bottom-1=0, not > top=1) | 10 |

Loop ends: `left=2 > right=0`. → `matrix = [[1,2,3],[8,9,4],[7,6,5]]`.

## 2. Optimal Approach

This **is** the optimal approach — boundary-shrinking ring fill. Track four boundaries (`top`, `bottom`, `left`, `right`) framing the unfilled region; each pass around the `while` loop writes the four edges of that region clockwise (starting from 1 and counting up), then shrinks all four boundaries inward by one for the next ring. As shown above, the square shape means the two guard checks needed for the general rectangular version are unnecessary here — a genuine simplification, not just a stylistic choice.

**Time:** O(n²) — each of the n² cells is written exactly once across all rings.
**Space:** O(1) extra — four integer boundaries and a counter, no auxiliary structures.

```java
public int[][] generateMatrix(int n) {
    int[][] matrix = new int[n][n];

    int item = 1,
        top = 0, bottom = n - 1,
        left = 0, right = n - 1;

    while (top <= bottom && left <= right) {
        for (int col = left; col <= right; col++)
            matrix[top][col] = item++;

        for (int row = top + 1; row <= bottom; row++)
            matrix[row][right] = item++;

        for (int col = right - 1; col >= left; col--)
            matrix[bottom][col] = item++;

        for (int row = bottom - 1; row > top; row--)
            matrix[row][left] = item++;

        top++; bottom--; left++; right--;
    }

    return matrix;
}
```

This is essentially your implementation. There isn't a meaningfully "more idiomatic" way to write *this specific* algorithm — the alternatives below trade its simplicity for a different mental model, not a cleaner one.

**Algorithm trace:** identical to the one above (same algorithm) — see Section 1.

## 3. Alternative Approaches

### A. Direction-vector simulation

Walk the matrix with a single `(row, col)` cursor and a direction vector cycling right → down → left → up, writing the next value and advancing one cell at a time. When the next cell would be out of bounds *or already filled*, rotate 90° clockwise and continue. The nice trick specific to the "generate" direction of this problem (unlike p79's "read" direction): since every value written is `>= 1` and a freshly-allocated `int[][]` defaults every cell to `0`, the matrix itself can serve as its own "visited" marker — checking `matrix[nextRow][nextCol] != 0` — so no separate `boolean[][] visited` grid is needed. That's a genuine space win over the read-direction version of this trick.

**Time:** O(n²) — each cell is visited once; wasted boundary/turn checks are O(1) each and bounded by 4 turns per ring.
**Space:** O(1) extra — no visited grid needed, since the output matrix's zero-default doubles as the sentinel.

```java
public int[][] generateMatrix(int n) {
    int[][] matrix = new int[n][n];
    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int row = 0, col = 0, dir = 0;

    for (int item = 1; item <= n * n; item++) {
        matrix[row][col] = item;

        int nextRow = row + dirs[dir][0];
        int nextCol = col + dirs[dir][1];

        if (nextRow < 0 || nextRow >= n || nextCol < 0 || nextCol >= n || matrix[nextRow][nextCol] != 0) {
            dir = (dir + 1) % 4;
            nextRow = row + dirs[dir][0];
            nextCol = col + dirs[dir][1];
        }

        row = nextRow;
        col = nextCol;
    }

    return matrix;
}
```

**When acceptable:** Always correct, and arguably the more idiomatic default for "walk until blocked" problems in general since it generalizes to non-rectangular boards. Prefer the boundary approach when you want the ring structure to be explicit (e.g. if a follow-up asks "what's the value at ring k?").

**Algorithm trace** (Step table) — input `n = 3`, `dirs = [right, down, left, up]`:

| item | (row,col) | Written | dir | Next candidate | Blocked? | New dir |
|---|---|---|---|---|---|---|
| 1 | (0,0) | 1 | right | (0,1) | No | right |
| 2 | (0,1) | 2 | right | (0,2) | No | right |
| 3 | (0,2) | 3 | right | (0,3) | Yes (out of bounds) | down |
| 4 | (1,2) | 4 | down | (2,2) | No | down |
| 5 | (2,2) | 5 | down | (3,2) | Yes (out of bounds) | left |
| 6 | (2,1) | 6 | left | (2,0) | No | left |
| 7 | (2,0) | 7 | left | (2,-1) | Yes (out of bounds) | up |
| 8 | (1,0) | 8 | up | (0,0) | Yes (filled) | right |
| 9 | (1,1) | 9 | right | (1,2) | Yes (filled) | — (loop ends, 9 cells written) |

→ `matrix = [[1,2,3],[8,9,4],[7,6,5]]`.

### B. Recursive ring fill

Fill the outermost ring of the current `[top,bottom] x [left,right]` subproblem, then recurse on the sub-square one step in on every side (`top+1, bottom-1, left+1, right-1`), carrying the running counter forward. Base case: `top > bottom || left > right`. Mechanically the same rings as the iterative version, just expressed as recursive calls instead of loop iterations — mostly useful as a way to demonstrate the ring decomposition explicitly if asked to.

**Time:** O(n²) — same total cells written, just distributed across n/2 recursive calls instead of loop iterations.
**Space:** O(n) — recursion depth is one call per ring (n/2 rings), versus O(1) for the iterative version's fixed boundary variables.

```java
public int[][] generateMatrix(int n) {
    int[][] matrix = new int[n][n];
    fillRing(matrix, 0, n - 1, 0, n - 1, 1);
    return matrix;
}

private void fillRing(int[][] matrix, int top, int bottom, int left, int right, int item) {
    if (top > bottom || left > right) return;

    for (int col = left; col <= right; col++)
        matrix[top][col] = item++;

    for (int row = top + 1; row <= bottom; row++)
        matrix[row][right] = item++;

    for (int col = right - 1; col >= left; col--)
        matrix[bottom][col] = item++;

    for (int row = bottom - 1; row > top; row--)
        matrix[row][left] = item++;

    fillRing(matrix, top + 1, bottom - 1, left + 1, right - 1, item);
}
```

**When acceptable:** Interview whiteboard, mainly to show the ring decomposition is naturally recursive — not a practical improvement over the iterative version in Java, since it trades O(1) space for O(n) call-stack depth with no offsetting benefit.

**Algorithm trace** (Call stack table) — input `n = 3`:

| Depth | Call | Rings filled this call | Returns |
|---|---|---|---|
| 0 | fillRing(top=0,bottom=2,left=0,right=2,item=1) | 1,2,3,4,5,6,7,8 | calls depth 1 with item=9 |
| 1 | fillRing(top=1,bottom=1,left=1,right=1,item=9) | 9 | calls depth 2 with item=10 |
| 2 | fillRing(top=2,bottom=1,left=2,right=1,item=10) | none (top > bottom) | returns immediately |

→ `matrix = [[1,2,3],[8,9,4],[7,6,5]]`.
