| | |
|---|---|
| **Solved on** | 2026-08-06 |
| **DSA Category** | Arrays & Hashing |

## 1. Your Solution Assessment

**Correctness:** The implementation is correct. It performs a single pass over all 81 cells, skips `.` via `Character.isDigit`, computes a unique box key with `(row / 3) + "," + (col / 3)`, and checks/records membership in per-row, per-column, and per-box sets before moving on. All 11 tests pass, including duplicate-in-row, duplicate-in-column, duplicate-in-box, and the two example boards.

**Code quality:** Clean and readable. The guard clause (`if (!Character.isDigit(item)) continue;`) keeps the main logic flat, and variable names (`isRowSeen`, `isColSeen`, `isBoxSeen`) make the duplicate check self-explanatory. Using `"row/3,col/3"` as a string key for the box sidesteps the arithmetic-collision pitfall of folding two bands into one `int` (the source of the earlier `(row/3)*3+(col/3)` confusion) — the two bands stay visually separate, so there's nothing to derive. The tradeoff is a `String` concatenation + hash per filled cell, versus a single integer computation, for `seenBox`. Two remaining nitpicks: (1) `row`, `col`, and the row/col bands only ever range over a small, fixed, known domain — plain arrays (e.g. `Set<Character>[9]` for rows/cols, `Set<Character>[3][3]` for boxes) would express that domain directly and avoid the `HashMap`/`String`-key overhead entirely; (2) `rows`/`cols` still use `Integer` keys while `squares` uses a `String` key — mixing key styles for structurally identical lookups is a minor inconsistency. See the Optimal Approach below.

**Time complexity:** O(1) — the board is always 9×9 (81 cells), so the single pass is bounded by a constant. Generalized to an n×n board, it would be O(n²).

**Space complexity:** O(1) — at most 27 sets (9 rows + 9 columns + 9 boxes), each holding at most 9 characters.

**Algorithm trace** (Step table, iterative loop): Example 2 board, traced cell-by-cell in row-major order. The trace stops at the first duplicate, which the algorithm finds at `(2, 2)` — the second `8` in the top-left box — before ever reaching row 3.

Input: Example 2 board (`8` at `(0,0)` instead of `5`, all else unchanged).

| Cell (row, col) | item | boxKey | seenRow[row] before | seenCol[col] before | seenBox[boxKey] before | duplicate? | action |
|---|---|---|---|---|---|---|---|
| (0,0) | 8 | "0,0" | {} | {} | {} | No | add 8 to row0, col0, box"0,0" |
| (0,1) | 3 | "0,0" | {} | {} | {8} | No | add 3 to row0, col1, box"0,0" |
| (0,4) | 7 | "0,1" | {8,3} | {} | {} | No | add 7 to row0, col4, box"0,1" |
| (1,0) | 6 | "0,0" | {} | {8} | {8,3} | No | add 6 to row1, col0, box"0,0" |
| (1,3) | 1 | "0,1" | {6} | {} | {7} | No | add 1 to row1, col3, box"0,1" |
| (1,4) | 9 | "0,1" | {6,1} | {7} | {7,1} | No | add 9 to row1, col4, box"0,1" |
| (1,5) | 5 | "0,1" | {6,1,9} | {} | {7,1,9} | No | add 5 to row1, col5, box"0,1" |
| (2,1) | 9 | "0,0" | {} | {3} | {8,3,6} | No | add 9 to row2, col1, box"0,0" |
| (2,2) | 8 | "0,0" | {9} | {} | **{8,3,6,9}** | **Yes (box)** | return `false` |

→ `isValidSudoku` returns `false`, matching the expected output.

## 2. Optimal Approach

Same three-set idea, but replace the three `HashMap<Integer, Set<Character>>` with fixed-size arrays, since the row, column, and box indices are always `0-8`. Instead of a `HashSet<Character>` per group, use a 9-bit integer bitmask per group: bit `(digit - 1)` set means that digit has already been seen. Checking membership is a single `&`, and recording it is a single `|` — no hashing, no boxing, no dynamic set allocation.

**Time complexity:** O(1) — still one pass over the fixed 81 cells; generalizes to O(n²).

**Space complexity:** O(1) — 27 `int`s total (9 rows + 9 columns + 9 boxes) instead of up to 27 `HashSet` objects.

```java
public boolean isValidSudoku(char[][] board) {
    int[] rowMask = new int[9];
    int[] colMask = new int[9];
    int[] boxMask = new int[9];

    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            char item = board[row][col];

            if (item == '.') continue;

            int bit = 1 << (item - '1');
            int box = (row / 3) * 3 + col / 3;

            if ((rowMask[row] & bit) != 0
                    || (colMask[col] & bit) != 0
                    || (boxMask[box] & bit) != 0) {
                return false;
            }

            rowMask[row] |= bit;
            colMask[col] |= bit;
            boxMask[box] |= bit;
        }
    }

    return true;
}
```

**Algorithm trace** (Step table): same Example 2 board, showing the bitmasks in binary (bit 0 = digit 1 ... bit 8 = digit 9). `boxMask[0]` is the one to watch.

| Cell (row, col) | item | bit | box | boxMask[0] before | (boxMask[0] & bit)? | action |
|---|---|---|---|---|---|---|
| (0,0) | 8 | `010000000` | 0 | `000000000` | 0 → no dup | boxMask[0] = `010000000` |
| (0,1) | 3 | `000000100` | 0 | `010000000` | 0 → no dup | boxMask[0] = `010000100` |
| (1,0) | 6 | `000010000` | 0 | `010000100` | 0 → no dup | boxMask[0] = `010010100` |
| (2,1) | 9 | `100000000` | 0 | `010010100` | 0 → no dup | boxMask[0] = `110010100` |
| (2,2) | 8 | `010000000` | 0 | `110010100` | **`010000000` → dup** | return `false` |

→ `isValidSudoku` returns `false`, same result as the set-based version, with less overhead per cell.

## 3. Alternative Approaches

### a) Brute-force scan (no auxiliary storage)

For each filled cell, scan the rest of its row, its column, and its 3×3 box directly for a matching digit, instead of remembering what's been seen.

**Time complexity:** O(n³) for an n×n board (n=9) — for each of the n² cells, scanning its row + column + box costs O(n), giving O(n² · n) = O(n³). Concretely, up to 81 × 27 ≈ 2,187 comparisons — still trivially fast for a fixed 9×9 board, but asymptotically worse than the set/bitmask approaches.

**Space complexity:** O(1) — no extra storage beyond loop variables.

**When acceptable:** Fine under interview time pressure as a first correct pass, or when memory is so constrained that even 27 small sets/ints are undesirable (not a realistic constraint for a 9×9 board, but a reasonable answer to "can you do this with zero extra space?").

```java
public boolean isValidSudoku(char[][] board) {
    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            char item = board[row][col];
            if (item == '.') continue;
            if (!isUniqueInRow(board, row, col, item)
                    || !isUniqueInCol(board, row, col, item)
                    || !isUniqueInBox(board, row, col, item)) {
                return false;
            }
        }
    }
    return true;
}

private boolean isUniqueInRow(char[][] board, int row, int col, char item) {
    for (int c = 0; c < 9; c++) {
        if (c != col && board[row][c] == item) return false;
    }
    return true;
}

private boolean isUniqueInCol(char[][] board, int row, int col, char item) {
    for (int r = 0; r < 9; r++) {
        if (r != row && board[r][col] == item) return false;
    }
    return true;
}

private boolean isUniqueInBox(char[][] board, int row, int col, char item) {
    int boxRow = (row / 3) * 3;
    int boxCol = (col / 3) * 3;
    for (int r = boxRow; r < boxRow + 3; r++) {
        for (int c = boxCol; c < boxCol + 3; c++) {
            if ((r != row || c != col) && board[r][c] == item) return false;
        }
    }
    return true;
}
```

**Algorithm trace** (Step table): Example 2 board, checking cell `(0, 0) = '8'` against its box directly (no memory of prior cells needed).

| Scan | Cell compared | Value | Match with '8'? |
|---|---|---|---|
| Box scan for (0,0) | (0,1) | 3 | No |
| | (0,2) | . | No |
| | (1,0) | 6 | No |
| | (1,1) | . | No |
| | (1,2) | . | No |
| | (2,0) | . | No |
| | (2,1) | 9 | No |
| | (2,2) | 8 | **Yes → duplicate** |
→ `isUniqueInBox` returns `false` for `(0,0)`, so `isValidSudoku` returns `false` immediately, without needing to reach row 1 or row 2 in the outer loop.

### b) Single 2D boolean array — `boolean[9][27]` indexed by `[digit][row|col|box]`

Instead of 27 separate `HashSet`s (submitted) or 27 separate `int` bitmasks (Optimal Approach), use one fixed-size `boolean[9][27]` array: the first dimension is the digit (`0-8`), the second is a combined group index — `0-8` for rows, `9-17` for columns (`9 + col`), `18-26` for boxes (`18 + box`). `seen[digit][groupIndex]` being `true` means that digit has already appeared in that group. This keeps the "known, small, fixed domain → array, not map" idea from the Optimal Approach, but skips the bit-shifting, so it reads closer to the submitted solution while still avoiding all hashing/boxing overhead.

**Time complexity:** O(1) — one pass over the fixed 81 cells; generalizes to O(n²).

**Space complexity:** O(1) — a single `9 × 27` (243-element) boolean array, allocated once.

**When acceptable:** A good middle ground for an interview: it has the same performance characteristics as the bitmask version without requiring the interviewer (or your future self) to mentally decode bit arithmetic, at the cost of a slightly less compact combined index scheme.

```java
public boolean isValidSudoku(char[][] board) {
    boolean[][] seen = new boolean[9][27];

    for (int row = 0; row < 9; row++) {
        for (int col = 0; col < 9; col++) {
            char item = board[row][col];

            if (item == '.') continue;

            int digit = item - '1';
            int box = (row / 3) * 3 + col / 3;
            int rowIdx = row;
            int colIdx = 9 + col;
            int boxIdx = 18 + box;

            if (seen[digit][rowIdx] || seen[digit][colIdx] || seen[digit][boxIdx]) {
                return false;
            }

            seen[digit][rowIdx] = true;
            seen[digit][colIdx] = true;
            seen[digit][boxIdx] = true;
        }
    }

    return true;
}
```

**Algorithm trace** (Step table): same Example 2 board. `seen[7][18]` (digit `8`, box `0`) is the flag to watch — it gets set at `(0,0)` and triggers the duplicate at `(2,2)`.

| Cell (row,col) | item | digit idx | rowIdx | colIdx | boxIdx | seen[digit][rowIdx] | seen[digit][colIdx] | seen[digit][boxIdx] | duplicate? | action |
|---|---|---|---|---|---|---|---|---|---|---|
| (0,0) | 8 | 7 | 0 | 9 | 18 | false | false | false | No | set seen[7][0], seen[7][9], seen[7][18] = true |
| (0,1) | 3 | 2 | 0 | 10 | 18 | false | false | false | No | set seen[2][0], seen[2][10], seen[2][18] = true |
| (0,4) | 7 | 6 | 0 | 13 | 19 | false | false | false | No | set seen[6][0], seen[6][13], seen[6][19] = true |
| (1,0) | 6 | 5 | 1 | 9 | 18 | false | false | false | No | set seen[5][1], seen[5][9], seen[5][18] = true |
| (1,3) | 1 | 0 | 1 | 12 | 19 | false | false | false | No | set seen[0][1], seen[0][12], seen[0][19] = true |
| (1,4) | 9 | 8 | 1 | 13 | 19 | false | false | false | No | set seen[8][1], seen[8][13], seen[8][19] = true |
| (1,5) | 5 | 4 | 1 | 14 | 19 | false | false | false | No | set seen[4][1], seen[4][14], seen[4][19] = true |
| (2,1) | 9 | 8 | 2 | 10 | 18 | false | false | false | No | set seen[8][2], seen[8][10], seen[8][18] = true |
| (2,2) | 8 | 7 | 2 | 11 | 18 | false | false | **true** | **Yes (box)** | return `false` |

→ `isValidSudoku` returns `false`, same result as the set-based and bitmask versions.

### c) Your submitted approach — `HashMap<Integer, Set<Character>>` (rows/cols) + `HashMap<String, Set<Character>>` (boxes)

This is functionally identical to the Optimal Approach (same O(1) time, same O(1) space), but pays a real, if small, constant-factor cost per cell: three `HashMap` lookups plus `putIfAbsent`, `Integer` autoboxing for the row/col keys, a `String` concatenation + hash for the box key, and `HashSet` allocation on first use per group — versus a plain array index and a bitwise `&`/`|` for the bitmask version. Using a composite `"row/3,col/3"` string for the box key (rather than folding both bands into one `int`) trades a little performance for a lot of clarity — it avoids having to reason about why `(row/3)*3+(col/3)` doesn't collide, at the cost of a `String` allocation per filled cell.

**When acceptable:** Always reasonable in an interview — it's easier to write and reason about correctly under pressure than bit tricks or index-collision arithmetic, and the board's fixed 9×9 size means the overhead is irrelevant in practice. Prefer the array/bitmask version only when asked to optimize further or when explaining the tradeoff.
