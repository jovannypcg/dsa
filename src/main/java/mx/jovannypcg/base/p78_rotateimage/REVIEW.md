| | |
|---|---|
| **Solved on** | 2026-09-11 |
| **DSA Category** | Arrays & Hashing |

## 1. Your Solution Assessment

**Correctness:** The implementation is correct. It transposes the matrix in place, then reverses every row. This is the standard identity for a 90-degree clockwise rotation: `rotate90(M) = reverseRows(transpose(M))`. All 8 tests pass, including the `n == 1` edge case, negative values, duplicate values, and boundary values (`-1000`/`1000`).

**Code quality:** Clean and readable. `transpose`, `reverseRows`, and `swap` are each single-purpose and well-named. The `col > row` bound in `transpose` correctly avoids double-swapping the diagonal. The early return on `matrix == null || matrix.length == 0` is defensive but harmless (the constraints guarantee `n >= 1`, so it never actually fires).

**Time complexity:** `O(n²)`. `transpose` visits roughly half of the `n × n` cells (upper triangle), and `reverseRows` visits every cell once via `n` two-pointer sweeps — both are `O(n²)`, so the total is `O(n²)`.

**Space complexity:** `O(1)` extra space. All swaps happen directly on the input matrix; only a constant number of temporary variables are used regardless of `n`.

**Algorithm trace** (Step table, 3x3 example: `matrix = [[1,2,3],[4,5,6],[7,8,9]]`)

| Step | Operation | Matrix state after |
|---|---|---|
| 0 | initial | `[[1,2,3],[4,5,6],[7,8,9]]` |
| 1 | transpose: swap (0,1)↔(1,0) | `[[1,4,3],[2,5,6],[7,8,9]]` |
| 2 | transpose: swap (0,2)↔(2,0) | `[[1,4,7],[2,5,6],[3,8,9]]` |
| 3 | transpose: swap (1,2)↔(2,1) | `[[1,4,7],[2,5,8],[3,6,9]]` |
| 4 | reverseRows: reverse row 0 → `[7,4,1]` | `[[7,4,1],[2,5,8],[3,6,9]]` |
| 5 | reverseRows: reverse row 1 → `[8,5,2]` | `[[7,4,1],[8,5,2],[3,6,9]]` |
| 6 | reverseRows: reverse row 2 → `[9,6,3]` | `[[7,4,1],[8,5,2],[9,6,3]]` |

→ final `[[7,4,1],[8,5,2],[9,6,3]]`, matching the expected output.

## 2. Optimal Approach

Transpose + reverse each row **is** the optimal approach for this problem — `O(n²)` time is unavoidable since every one of the `n²` cells must move, and `O(1)` extra space is the best possible under the in-place constraint. The user's solution already achieves this, so the code below is essentially the same shape, shown here as the canonical reference:

```java
public void rotate(int[][] matrix) {
    int n = matrix.length;

    for (int row = 0; row < n; row++) {
        for (int col = row + 1; col < n; col++) {
            int tmp = matrix[row][col];
            matrix[row][col] = matrix[col][row];
            matrix[col][row] = tmp;
        }
    }

    for (int[] r : matrix) {
        for (int left = 0, right = n - 1; left < right; left++, right--) {
            int tmp = r[left];
            r[left] = r[right];
            r[right] = tmp;
        }
    }
}
```

- **Time complexity:** `O(n²)` — every cell is touched exactly once across the transpose and the row reversals.
- **Space complexity:** `O(1)` — only scalar temporaries are used; the rotation happens entirely within the input matrix.

**Algorithm trace:** identical to the trace in section 1, since this is the same algorithm as the user's submission.

## 3. Alternative Approaches

### A. Layer-by-layer four-way swap (in-place, single pass)

Instead of two separate phases, rotate directly by processing the matrix as concentric square "rings." For each ring, walk along its top edge and, for each position, cycle four cells (top → right, right → bottom, bottom → left, left → top) using one temporary variable.

- **Time complexity:** `O(n²)` — each of the `n²` cells is moved exactly once across all rings.
- **Space complexity:** `O(1)` — one temporary variable per 4-cycle, no auxiliary matrix.
- **When it's acceptable:** Always a valid substitute for the transpose+reverse approach; some prefer it because it rotates in one logical pass without an intermediate transposed state, though it's arguably harder to get right under interview pressure due to the index arithmetic.

**Algorithm trace** (Step table, 3x3 example: `matrix = [[1,2,3],[4,5,6],[7,8,9]]`, single ring since `n = 3`)

| Step | i (offset) | Cycle (top→right→bottom→left→top) | Matrix state after |
|---|---|---|---|
| 0 | initial | — | `[[1,2,3],[4,5,6],[7,8,9]]` |
| 1 | i=0 | top=1; (0,0)=7, (2,0)=9, (2,2)=3, (0,2)=1 | `[[7,2,1],[4,5,6],[9,8,3]]` |
| 2 | i=1 | top=2; (0,1)=4, (1,0)=8, (2,1)=6, (1,2)=2 | `[[7,4,1],[8,5,2],[9,6,3]]` |

→ final `[[7,4,1],[8,5,2],[9,6,3]]`, matching the expected output.

### B. Brute force with an auxiliary matrix

Allocate a new `n x n` matrix `res`, compute `res[col][n-1-row] = matrix[row][col]` for every cell, then copy `res` back into `matrix` (or return it directly if the in-place constraint is relaxed). This is the most intuitive approach: it directly encodes "row `i` becomes column `n-1-i`" without any in-place trickery.

- **Time complexity:** `O(n²)` — every cell is read once and written once.
- **Space complexity:** `O(n²)` — a full second matrix is allocated.
- **When it's acceptable:** This approach **violates the problem's explicit in-place requirement**, so it would not be accepted as a final answer here. It's worth knowing as a stepping stone: writing it first can help derive the transpose-based trick, and it's fine to mention out loud in an interview before optimizing to `O(1)` space.

**Algorithm trace** (Step table, 3x3 example: `matrix = [[1,2,3],[4,5,6],[7,8,9]]`, grouped by source row)

| i (source row) | matrix[i] | target column = n-1-i | res after this step |
|---|---|---|---|
| 0 | `[1,2,3]` | 2 | `res[0][2]=1, res[1][2]=2, res[2][2]=3` |
| 1 | `[4,5,6]` | 1 | `res[0][1]=4, res[1][1]=5, res[2][1]=6` |
| 2 | `[7,8,9]` | 0 | `res[0][0]=7, res[1][0]=8, res[2][0]=9` |

→ final `res = [[7,4,1],[8,5,2],[9,6,3]]`, then copied back into `matrix`.
