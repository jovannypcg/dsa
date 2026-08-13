| | |
|---|---|
| **Solved on** | 2026-08-13 |
| **DSA Category** | Binary Search |

## Note to self (quick reference)

Treat the 2D matrix as if it were a single flattened, sorted array, then run ordinary binary search over it. The only extra step is converting the linear `mid` index back into `(row, col)`:

```
row = mid / n   (n = number of columns)
col = mid % n
```

Why `/` for row? Every row holds exactly `n` elements, so the rows occupy linear index ranges `[0, n)`, `[n, 2n)`, `[2n, 3n)`, etc. Dividing by `n` tells you how many full rows of size `n` fit before `mid`, which is exactly the row `mid` falls into.

Why `%` for col? Once you know which row you're in, the remainder of that division is simply how far past the start of that row `mid` landed, i.e. the column offset.

Example: a 3 x 4 matrix (`n = 4`) has linear indices `0..11`. Index `11` (the last element): `row = 11 / 4 = 2`, `col = 11 % 4 = 3`. Indices `0-3` map to row 0, `4-7` to row 1, `8-11` to row 2 — dividing by `n` picks the row "block", and the remainder picks the offset within it.

## 1. Your Solution Assessment

### Correctness

The solution correctly maps a linear index to `(row, col)` and uses standard binary-search bound updates (`left = mid + 1` on a low miss, `right = mid - 1` on a high miss), so it converges to the right answer while discarding half the remaining search space on every iteration. It passes all 15 cases in `SolutionTest.java`, including single-cell, single-row, single-column, negative-value, duplicate-value, and constraint-boundary matrices.

### Code quality

Clear variable names (`m`, `n`, `left`, `right`, `mid`, `row`, `col`) and a tight `while (left <= right)` loop. The null/empty guard at the top is reasonable defensive coding for a public API, even though it's outside what the stated constraints require (`1 <= m, n`).

### Time complexity

`O(log(m * n))` — each iteration inspects the midpoint of the current `[left, right]` range and discards the half that can't contain `target`, exactly like binary search over a 1-D array of `m * n` elements.

### Space complexity

`O(1)` — only a fixed number of scalar variables are used regardless of input size.

### Algorithm trace

Input: `matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]]`, `target = 3` (flattened: `[1,3,5,7,10,11,16,20,23,30,34,60]`, indices `0..11`)

| # | left | right | mid | row | col | matrix[row][col] | comparison | action |
|---|------|-------|-----|-----|-----|-------------------|------------|--------|
| 1 | 0 | 11 | 5 | 1 | 1 | 11 | 11 > 3 | right = mid - 1 → 4 |
| 2 | 0 | 4 | 2 | 0 | 2 | 5 | 5 > 3 | right = mid - 1 → 1 |
| 3 | 0 | 1 | 0 | 0 | 0 | 1 | 1 < 3 | left = mid + 1 → 1 |
| 4 | 1 | 1 | 1 | 0 | 1 | 3 | 3 == 3 | **found** |

→ return `true` after **4 iterations**, matching the `⌈log2(12)⌉ = 4` bound.

## 2. Optimal Approach

Your solution already is the optimal approach: treat the matrix as a flattened, sorted 1-D array and binary search over it, converting the midpoint index to `(row, col)` via `/` and `%`. There isn't a meaningfully better complexity class than `O(log(m * n))` for this problem, since any correct solution must at least be able to rule out half the remaining candidates per comparison to beat linear time.

### Complexity

- **Time:** `O(log(m * n))` — classic binary search over `m * n` elements; each iteration halves the remaining range.
- **Space:** `O(1)` — a fixed number of scalar variables, no extra data structures.

### Code

```java
public class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int left = 0;
        int right = m * n - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;
            int row = mid / n;
            int col = mid % n;
            int value = matrix[row][col];

            if (value == target) {
                return true;
            } else if (value < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return false;
    }
}
```

### Algorithm trace

Same trace as above — see **Your Solution Assessment**, since the two implementations are equivalent.

## 3. Alternative Approaches

### Brute force (linear scan)

Scan every cell of the matrix and compare it to `target`.

- **Time:** `O(m * n)` — every cell may need to be visited in the worst case (target absent or in the last cell checked).
- **Space:** `O(1)` — no extra storage beyond loop indices.
- **When acceptable:** Fine for very small matrices, as a quick correctness check while developing, or under interview time pressure if you state up front that you know it doesn't meet the stated `O(log(m*n))` requirement and plan to optimize afterward.

```java
public boolean searchMatrixBruteForce(int[][] matrix, int target) {
    for (int[] row : matrix) {
        for (int value : row) {
            if (value == target) {
                return true;
            }
        }
    }
    return false;
}
```

Trace (partial, target = 3):

| # | row | col | matrix[row][col] | comparison |
|---|-----|-----|-------------------|------------|
| 1 | 0 | 0 | 1 | 1 != 3 |
| 2 | 0 | 1 | 3 | 3 == 3 → **found** |

→ return `true` after 2 iterations here, but worst case (target absent) visits all 12 cells.

### Two-step binary search (search rows, then columns)

Binary search the first column to find the one row whose range could contain `target` (the last row whose first element is `<= target`), then binary search within that row.

- **Time:** `O(log m + log n)` — one binary search over `m` rows, then one over `n` columns; asymptotically the same as treating the matrix as flattened (`log m + log n == log(m * n)`).
- **Space:** `O(1)` — only scalar indices for both searches.
- **When acceptable:** Always acceptable complexity-wise; some find it more intuitive than the flattened-index trick since it mirrors "find the row, then find the cell" reasoning, at the cost of slightly more code (two loops instead of one).

```java
public boolean searchMatrixTwoStep(int[][] matrix, int target) {
    int top = 0;
    int bottom = matrix.length - 1;

    while (top < bottom) {
        int midRow = top + (bottom - top + 1) / 2;
        if (matrix[midRow][0] <= target) {
            top = midRow;
        } else {
            bottom = midRow - 1;
        }
    }

    int left = 0;
    int right = matrix[top].length - 1;

    while (left <= right) {
        int mid = left + (right - left) / 2;
        if (matrix[top][mid] == target) {
            return true;
        } else if (matrix[top][mid] < target) {
            left = mid + 1;
        } else {
            right = mid - 1;
        }
    }

    return false;
}
```

Trace (target = 3):

Row search — candidate rows by first element: `row 0 → 1`, `row 1 → 10`, `row 2 → 23`.

| # | top | bottom | midRow | matrix[midRow][0] | comparison | action |
|---|-----|--------|--------|--------------------|------------|--------|
| 1 | 0 | 2 | 1 | 10 | 10 <= 3? No | bottom = midRow - 1 → 0 |

→ `top == bottom == 0`, so search row 0: `[1, 3, 5, 7]`.

| # | left | right | mid | matrix[0][mid] | comparison | action |
|---|------|-------|-----|-----------------|------------|--------|
| 1 | 0 | 3 | 1 | 3 | 3 == 3 | **found** |

→ return `true` after 1 row-search iteration + 1 column-search iteration.
