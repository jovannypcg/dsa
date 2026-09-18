| | |
|---|---|
| **Solved on** | 2026-09-11 |
| **DSA Category** | Arrays & Hashing |

## 1. Your Solution Assessment

**Correctness:** After adding the two missing guards (`if (top < bottom)` before sweeping the bottom row, `if (left < right)` before sweeping the left column), the solution is correct. All 10 tests pass, including the single-row, single-column, and flat-inner-layer cases that originally exposed the bug — those are exactly the situations where a ring collapses to one row or one column, and the third/fourth loops would otherwise re-walk cells already added by the first/second loops.

**Code quality:** Clean and idiomatic already. Naming (`top`, `bottom`, `left`, `right`) matches how most people talk through this problem out loud, the early `null`/empty guard is a nice touch most people forget, and the four inner loops read as "sweep top, sweep right, sweep bottom (if needed), sweep left (if needed)" without extra ceremony.

**Time complexity:** O(m·n). Every cell is visited and added to the result exactly once — the boundaries only ever move inward, never re-cross a cell.

**Space complexity:** O(1) extra space, not counting the output list itself (which is O(m·n) because it must hold every element). Only a handful of `int` boundary variables are used.

**Algorithm trace** (Step table) — input `matrix = [[1,2,3],[4,5,6],[7,8,9]]`:

| Ring | top,bottom,left,right (before) | Top row added | Right col added | Bottom row added (guarded) | Left col added (guarded) | Traversal so far |
|---|---|---|---|---|---|---|
| 1 | 0,2,0,2 | 1,2,3 | 6,9 | 8,7 (top<bottom ✓) | 4 (left<right ✓) | [1,2,3,6,9,8,7,4] |
| 2 | 1,1,1,1 | 5 | — (top+1>bottom) | skipped (top<bottom ✗) | skipped (left<right ✗) | [1,2,3,6,9,8,7,4,5] |

Loop ends: `top=2 > bottom=0`. → return `[1,2,3,6,9,8,7,4,5]`.

## 2. Optimal Approach

This **is** the optimal approach — boundary-shrinking layer traversal. The mental model: track four boundaries (`top`, `bottom`, `left`, `right`) that frame the "unvisited" rectangle. Each pass around the `while` loop sweeps the four edges of that rectangle clockwise, then shrinks all four boundaries by one so the next pass sweeps the next layer in. The only subtlety — and the one bug in your original code — is guarding the third and fourth sweeps so a collapsed ring (one remaining row or column) isn't walked twice.

**Time:** O(m·n) — each of the m·n cells is appended to the result exactly once across all ring iterations.
**Space:** O(1) extra — four integer boundaries, no auxiliary grid.

```java
public List<Integer> spiralOrder(int[][] matrix) {
    if (matrix == null || matrix.length == 0 || matrix[0].length == 0)
        return List.of();

    List<Integer> traversal = new ArrayList<>();
    int top = 0, bottom = matrix.length - 1;
    int left = 0, right = matrix[0].length - 1;

    while (top <= bottom && left <= right) {
        for (int col = left; col <= right; col++)
            traversal.add(matrix[top][col]);

        for (int row = top + 1; row <= bottom; row++)
            traversal.add(matrix[row][right]);

        if (top < bottom)
            for (int col = right - 1; col >= left; col--)
                traversal.add(matrix[bottom][col]);

        if (left < right)
            for (int row = bottom - 1; row > top; row--)
                traversal.add(matrix[row][left]);

        top++; bottom--; left++; right--;
    }

    return traversal;
}
```

This is essentially your fixed implementation, renamed loop variables aside — there isn't a meaningfully "more idiomatic" way to write *this specific* algorithm; the alternatives below trade this approach's simplicity for a different mental model, not a cleaner one.

**Algorithm trace:** identical to the one above (same algorithm) — see Section 1.

## 3. Alternative Approaches

### A. Direction-vector simulation

Instead of tracking four numeric boundaries, walk the matrix with a single `(row, col)` cursor and a direction vector cycling through right → down → left → up. Move forward one cell at a time; when the next cell would be out of bounds or already visited, rotate the direction 90° clockwise and continue. This is the technique that generalizes most cleanly to non-rectangular "walk until blocked" problems (e.g. robot room cleaner), which is why some consider it the more idiomatic pattern to reach for by default — at the cost of needing a `visited` grid.

**Time:** O(m·n) — each cell is visited once; the wasted probes into out-of-bounds/visited cells are O(1) each and bounded by the number of direction turns (at most 4 per ring).
**Space:** O(m·n) for the `visited` boolean matrix (this is the real trade-off versus the boundary approach's O(1)).

```java
public List<Integer> spiralOrder(int[][] matrix) {
    int m = matrix.length, n = matrix[0].length;
    List<Integer> traversal = new ArrayList<>();
    boolean[][] visited = new boolean[m][n];
    int[][] dirs = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};
    int row = 0, col = 0, dir = 0;

    for (int i = 0; i < m * n; i++) {
        traversal.add(matrix[row][col]);
        visited[row][col] = true;

        int nextRow = row + dirs[dir][0];
        int nextCol = col + dirs[dir][1];

        if (nextRow < 0 || nextRow >= m || nextCol < 0 || nextCol >= n || visited[nextRow][nextCol]) {
            dir = (dir + 1) % 4;
            nextRow = row + dirs[dir][0];
            nextCol = col + dirs[dir][1];
        }

        row = nextRow;
        col = nextCol;
    }

    return traversal;
}
```

**When acceptable:** Always correct and a fine choice under interview time pressure since the turning rule is easy to state out loud. Prefer the boundary approach when O(1) extra space matters or the interviewer explicitly asks for in-place traversal.

**Algorithm trace** (Step table) — input `matrix = [[1,2,3],[4,5,6],[7,8,9]]`, `dirs = [right, down, left, up]`:

| Step | (row,col) | Added | dir | Next candidate | Blocked? | New dir |
|---|---|---|---|---|---|---|
| 1 | (0,0) | 1 | right | (0,1) | No | right |
| 2 | (0,1) | 2 | right | (0,2) | No | right |
| 3 | (0,2) | 3 | right | (0,3) | Yes (out of bounds) | down |
| 4 | (1,2) | 6 | down | (2,2) | No | down |
| 5 | (2,2) | 9 | down | (3,2) | Yes (out of bounds) | left |
| 6 | (2,1) | 8 | left | (2,0) | No | left |
| 7 | (2,0) | 7 | left | (2,-1) | Yes (out of bounds) | up |
| 8 | (1,0) | 4 | up | (0,0) | Yes (visited) | right |
| 9 | (1,1) | 5 | right | (1,2) | Yes (visited) | — (loop ends, 9 cells visited) |

→ return `[1,2,3,6,9,8,7,4,5]`.

### B. Peel-and-rotate

Repeatedly pop the first row into the result, then rotate the remaining submatrix 90° so the "next row to peel" is always the top row. This is a well-known trick in languages with cheap list slicing/transposition (classically written in Python as `result += matrix.pop(0); matrix = list(zip(*matrix))[::-1]`), and it's included here mainly because it's a common "clever one-liner" people bring up — not because it's a good fit for Java.

**Time:** roughly O(m·n·min(m,n)) in the worst case — every "peel" rebuilds the entire remaining submatrix via a transpose, and that rebuild cost is paid on every iteration rather than once per cell.
**Space:** O(m·n) — each rotation allocates a brand-new submatrix instead of reusing the input array in place.

```java
public List<Integer> spiralOrder(int[][] input) {
    List<List<Integer>> matrix = new ArrayList<>();
    for (int[] row : input) {
        List<Integer> r = new ArrayList<>();
        for (int v : row) r.add(v);
        matrix.add(r);
    }

    List<Integer> traversal = new ArrayList<>();
    while (!matrix.isEmpty()) {
        traversal.addAll(matrix.remove(0));
        matrix = rotateCounterClockwise(matrix);
    }
    return traversal;
}

private List<List<Integer>> rotateCounterClockwise(List<List<Integer>> matrix) {
    List<List<Integer>> rotated = new ArrayList<>();
    if (matrix.isEmpty()) return rotated;

    int cols = matrix.get(0).size();
    for (int col = cols - 1; col >= 0; col--) {
        List<Integer> newRow = new ArrayList<>();
        for (List<Integer> row : matrix) newRow.add(row.get(col));
        rotated.add(newRow);
    }
    return rotated;
}
```

**When acceptable:** Interview whiteboard only, to show you know the trick — not something to actually ship in Java given the allocation overhead and the fact it obscures the O(m·n) cells-visited-once invariant that makes this problem easy to reason about.

**Algorithm trace** (Step table) — input `matrix = [[1,2,3],[4,5,6],[7,8,9]]`:

| Iteration | Popped row (added) | Remaining before rotate | Remaining after rotate | Traversal so far |
|---|---|---|---|---|
| 1 | [1,2,3] | [[4,5,6],[7,8,9]] | [[6,9],[5,8],[4,7]] | [1,2,3] |
| 2 | [6,9] | [[5,8],[4,7]] | [[8,7],[5,4]] | [1,2,3,6,9] |
| 3 | [8,7] | [[5,4]] | [[4],[5]] | [1,2,3,6,9,8,7] |
| 4 | [4] | [[5]] | [[5]] | [1,2,3,6,9,8,7,4] |
| 5 | [5] | [] | [] | [1,2,3,6,9,8,7,4,5] |

Loop ends: `matrix` is empty. → return `[1,2,3,6,9,8,7,4,5]`.
