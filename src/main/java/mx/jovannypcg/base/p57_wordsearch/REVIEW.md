| | |
|---|---|
| **Solved on** | 2026-08-22 |
| **DSA Category** | Backtracking |

## 1. Your Solution Assessment

### Correctness

The first version had a real backtracking bug: `visited[row][col] = true` was set *before* checking whether the character matched, and there was no matching "un-visit" step after exploring a cell's neighbors. That meant a cell probed on one branch (even a dead-end or a character mismatch) stayed permanently marked as visited for the rest of that starting cell's search, even though it was never part of an actual path. The counterexample that exposed it: `board = [["a","b"],["c","d"]]`, `word = "acdb"` — a valid Hamiltonian path (`a→c→d→b`) existed, but the search returned `false` because `(0,1)='b'` got marked visited during an unrelated failed probe and was never released before the real path needed it.

The fix moves the character check before marking `visited[row][col] = true`, and adds `visited[row][col] = false` after the direction loop finishes, regardless of outcome — this is the actual "backtrack" step: try a choice, recurse, then undo the choice before returning control to the caller. With that in place, the solution now correctly explores and abandons paths without polluting future attempts.

### Code Quality

- Clear, descriptive names (`visited`, `wordIdx`, `isOutOfBounds`) and a small, focused directions table.
- Overloading `exist(...)` for both the public entry point and the private recursive helper works, but reads slightly ambiguous at the call site inside the double loop — a distinct name like `backtrack` or `dfs` for the helper would make the recursion easier to spot at a glance.
- A fresh `boolean[m][n]` is allocated for every starting cell in the outer double loop. This is correct (each starting attempt needs its own clean slate) but does more allocation than necessary — a single array reused and fully reset (or restored) between attempts, or in-place marking on the board itself, avoids the repeated allocation. See Optimal Approach.

### Time Complexity

**O(m · n · 3^L)**, where `m × n` is the board size and `L = word.length()`. The outer loop tries each of the `m·n` cells as a starting point. From the starting cell, the first step can branch up to 4 ways; every step after that has at most 3 productive directions, since the direction leading back to the immediately preceding cell is always blocked by `visited`. That gives roughly `4 · 3^(L-1)` work per starting cell, i.e. `O(3^L)` up to constant factors, times `m·n` starting cells.

### Space Complexity

**O(m · n + L)**. The `boolean[m][n]` visited matrix is the dominant term; the recursion depth is bounded by `L` (the search stops as soon as `wordIdx >= word.length()`).

### Algorithm Trace

Board:
```
C A A
A A A
B C D
```
Word: `"AAB"` — this example requires backtracking: the first two starting cells that match `'A'` both dead-end, and even the third (`(1,0)`) dead-ends on its first branch before a second branch from a later cell succeeds.

Legend: `ADD (r,c) ch` = mark visited & descend · `REMOVE (r,c)` = un-mark on the way back up · `✗ SKIP` = rejected without recursing (out of bounds, already visited, or character mismatch) · `✓ RECORD` = `wordIdx` reached `word.length()`, base case hit.

```
Start (0,1) 'A' (wordIdx 0) — matches
ADD (0,1) A → visited={(0,1)}
  → right (0,2) 'A' (wordIdx 1) — matches
  ADD (0,2) A → visited={(0,1),(0,2)}
    → right (0,3) out of bounds            ✗ SKIP
    → left  (0,1) already visited          ✗ SKIP
    → down  (1,2) 'A' != 'B' (wordIdx 2)   ✗ SKIP
    → up    (-1,2) out of bounds           ✗ SKIP
  REMOVE (0,2) → visited={(0,1)}
  → left (0,0) 'C' != 'A' (wordIdx 1)      ✗ SKIP
  → down (1,1) 'A' (wordIdx 1) — matches
  ADD (1,1) A → visited={(0,1),(1,1)}
    → right (1,2) 'A' != 'B' (wordIdx 2)   ✗ SKIP
    → left  (1,0) 'A' != 'B' (wordIdx 2)   ✗ SKIP
    → down  (2,1) 'C' != 'B' (wordIdx 2)   ✗ SKIP
    → up    (0,1) already visited          ✗ SKIP
  REMOVE (1,1) → visited={(0,1)}
  → up (-1,1) out of bounds                ✗ SKIP
REMOVE (0,1) → visited={}                                         → no path from (0,1)

Start (0,2) 'A' (wordIdx 0) — matches
ADD (0,2) A → visited={(0,2)}
  → left (0,1) 'A' (wordIdx 1) — matches
  ADD (0,1) A → visited={(0,2),(0,1)}
    → right (0,2) already visited          ✗ SKIP
    → left  (0,0) 'C' != 'B' (wordIdx 2)   ✗ SKIP
    → down  (1,1) 'A' != 'B' (wordIdx 2)   ✗ SKIP
    → up    out of bounds                  ✗ SKIP
  REMOVE (0,1) → visited={(0,2)}
  → down (1,2) 'A' (wordIdx 1) — matches
  ADD (1,2) A → visited={(0,2),(1,2)}
    → right out of bounds                  ✗ SKIP
    → left  (1,1) 'A' != 'B' (wordIdx 2)   ✗ SKIP
    → down  (2,2) 'D' != 'B' (wordIdx 2)   ✗ SKIP
    → up    (0,2) already visited          ✗ SKIP
  REMOVE (1,2) → visited={(0,2)}
REMOVE (0,2) → visited={}                                         → no path from (0,2)

Start (1,0) 'A' (wordIdx 0) — matches
ADD (1,0) A → visited={(1,0)}
  → right (1,1) 'A' (wordIdx 1) — matches
  ADD (1,1) A → visited={(1,0),(1,1)}
    → right (1,2) 'A' != 'B' (wordIdx 2)   ✗ SKIP
    → left  (1,0) already visited          ✗ SKIP
    → down  (2,1) 'C' != 'B' (wordIdx 2)   ✗ SKIP
    → up    (0,1) 'A' != 'B' (wordIdx 2)   ✗ SKIP
  REMOVE (1,1) → visited={(1,0)}
  → down (2,0) 'B' != 'A' (wordIdx 1)      ✗ SKIP
  → up (0,0) 'C' != 'A' (wordIdx 1)        ✗ SKIP
REMOVE (1,0) → visited={}                                         → no path from (1,0)

Start (1,1) 'A' (wordIdx 0) — matches
ADD (1,1) A → visited={(1,1)}
  → right (1,2) 'A' (wordIdx 1) — matches
  ADD (1,2) A → visited={(1,1),(1,2)}
    → right out of bounds                  ✗ SKIP
    → left  (1,1) already visited          ✗ SKIP
    → down  (2,2) 'D' != 'B' (wordIdx 2)   ✗ SKIP
    → up    (0,2) 'A' != 'B' (wordIdx 2)   ✗ SKIP
  REMOVE (1,2) → visited={(1,1)}
  → left (1,0) 'A' (wordIdx 1) — matches
  ADD (1,0) A → visited={(1,1),(1,0)}
    → right (1,1) already visited          ✗ SKIP
    → left  out of bounds                  ✗ SKIP
    → down (2,0) 'B' (wordIdx 2) — matches
    ADD (2,0) B → visited={(1,1),(1,0),(2,0)}
      wordIdx = 3 = word.length()          ✓ RECORD
    → return true
    REMOVE (2,0) → visited={(1,1),(1,0)}          (unmark on the way back up; true already captured)
  REMOVE (1,0) → visited={(1,1)}                  (true propagates)
REMOVE (1,1) → visited={}                          (true propagates)
```
→ `exist(board, "AAB")` returns `true`.

## 2. Optimal Approach

Same backtracking idea, but skip the auxiliary `boolean[m][n]` entirely: mark a cell as "in use" by temporarily overwriting it on the board itself (e.g. with a sentinel like `'#'` that can never match a letter), then restore the original character once the recursive exploration from that cell is done. This removes the `O(m·n)` visited matrix and the repeated per-starting-cell allocation, leaving only the recursion stack as extra space.

```java
public class Solution {
    private static final int[][] DIRECTIONS = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};

    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                if (backtrack(board, word, row, col, 0)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean backtrack(char[][] board, String word, int row, int col, int wordIdx) {
        if (wordIdx == word.length()) return true;
        if (row < 0 || row >= board.length || col < 0 || col >= board[0].length) return false;
        if (board[row][col] != word.charAt(wordIdx)) return false;

        char original = board[row][col];
        board[row][col] = '#';

        boolean found = false;

        for (int[] direction : DIRECTIONS) {
            if (backtrack(board, word, row + direction[0], col + direction[1], wordIdx + 1)) {
                found = true;
                break;
            }
        }

        board[row][col] = original;

        return found;
    }
}
```

### Time Complexity

**O(m · n · 3^L)** — identical reasoning to Your Solution: `m·n` starting cells, and at most 3 productive branches per step after the first (the 4th direction always leads back to the sentinel-marked predecessor).

### Space Complexity

**O(L)** — only the recursion stack, since the "visited" marker is stored in-place on the board instead of in a separate matrix (the board itself is not counted as extra space).

### Algorithm Trace

Same board and word as above (`"AAB"`); the shape of the search is identical to Your Solution Assessment's trace, so only the mechanism differs: instead of `ADD/REMOVE` on a `visited` set, each step temporarily overwrites `board[row][col]` with `'#'` and restores the original character afterward.

```
Start (1,1) 'A' (wordIdx 0) — matches
board[1][1]: 'A' → '#'
  → right (1,2) 'A' (wordIdx 1) — matches
  board[1][2]: 'A' → '#'
    → right out of bounds                        ✗ SKIP
    → left  (1,1) is '#', != 'A'                  ✗ SKIP
    → down  (2,2) 'D' != 'B' (wordIdx 2)          ✗ SKIP
    → up    (0,2) 'A' != 'B' (wordIdx 2)          ✗ SKIP
  board[1][2]: '#' → 'A'  (restored)
  → left (1,0) 'A' (wordIdx 1) — matches
  board[1][0]: 'A' → '#'
    → right (1,1) is '#', != 'A'                  ✗ SKIP
    → left  out of bounds                         ✗ SKIP
    → down (2,0) 'B' (wordIdx 2) — matches
    board[2][0]: 'B' → '#'
      wordIdx = 3 = word.length()                 ✓ RECORD
    → return true
    board[2][0]: '#' → 'B'  (restored, true propagates)
  board[1][0]: '#' → 'A'  (restored, true propagates)
board[1][1]: '#' → 'A'  (restored, true propagates)
```
→ `exist(board, "AAB")` returns `true`, and the board is left byte-for-byte identical to how it started.

## 3. Alternative Approaches

### a) Backtracking with a single reused visited matrix

Instead of allocating a fresh `boolean[m][n]` for every starting cell (as Your Solution does) or mutating the board (as the Optimal Approach does), allocate `visited` once outside the outer loop and rely on the mark/unmark discipline to always leave it fully `false` between starting-cell attempts. This keeps the board untouched (useful if the board must remain read-only, e.g. shared across threads) while avoiding `m·n` allocations.

- **Time:** `O(m · n · 3^L)` — same search shape as Your Solution and Optimal Approach.
- **Space:** `O(m · n + L)` — one visited matrix reused across all starting cells, plus recursion depth.
- **When acceptable:** any time mutating the input board is undesirable, but the allocation churn of a per-attempt matrix isn't. A reasonable middle ground under interview time pressure.

**Algorithm trace:** structurally identical to Your Solution Assessment's trace above (same `ADD`/`REMOVE` pattern) — the only difference is that `visited` is created once before the outer loop instead of once per starting cell, so it's already guaranteed empty at the start of `(1,1)`'s attempt because every prior attempt fully unmarked itself on the way back up.

### b) Backtracking with letter-frequency pruning (the follow-up: search pruning for larger boards)

Before searching, count the frequency of every character on the board and compare it against the frequency of characters required by `word`. If the board doesn't contain enough of some letter, return `false` immediately without any DFS. A further refinement: if the last character of `word` is rarer on the board than the first, reverse `word` before searching — failing paths are then pruned sooner on average. This doesn't change the worst-case asymptotic complexity, but it meaningfully cuts down real search time on larger, sparser boards.

```java
private boolean canPossiblyContain(char[][] board, String word) {
    Map<Character, Integer> boardCounts = new HashMap<>();
    for (char[] row : board) {
        for (char c : row) {
            boardCounts.merge(c, 1, Integer::sum);
        }
    }

    Map<Character, Integer> wordCounts = new HashMap<>();
    for (char c : word.toCharArray()) {
        wordCounts.merge(c, 1, Integer::sum);
    }

    for (Map.Entry<Character, Integer> entry : wordCounts.entrySet()) {
        if (boardCounts.getOrDefault(entry.getKey(), 0) < entry.getValue()) {
            return false;
        }
    }

    return true;
}
```

- **Time:** `O(m·n)` for the frequency pre-check, then `O(m · n · 3^L)` for the backtracking search itself if the pre-check doesn't rule it out — same worst case, better average case.
- **Space:** `O(1)` extra for the frequency maps (bounded by the alphabet size, 52 for upper/lowercase English letters), plus whatever the underlying backtracking approach uses.
- **When acceptable:** whenever the board is large and words are checked repeatedly (e.g. many `exist` calls against the same board) — the pre-check is cheap and can short-circuit a large fraction of impossible queries before any recursion starts.

**Algorithm trace (frequency pre-check only):**

| Step | Action | Result |
|---|---|---|
| 1 | Count board letters | `{C:2, A:5, B:1, D:1}` |
| 2 | Count word letters (`"AAB"`) | `{A:2, B:1}` |
| 3 | Compare `A`: need 2, board has 5 | OK |
| 4 | Compare `B`: need 1, board has 1 | OK |
| 5 | All checks pass → proceed to backtracking search | same trace as Optimal Approach above |

### c) Iterative backtracking with an explicit stack

Simulate the recursion with your own stack of `(row, col, wordIdx, pathCells)` frames instead of relying on the call stack. Each iteration pops a frame, checks the current cell, and pushes up to 4 child frames (one per direction) if it matches. This avoids recursion depth limits entirely (irrelevant here since `L <= 15`, but relevant for much longer words) at the cost of manually managing what the visited set looks like for each frame, since frames for sibling branches must not share mutable state the way a single `visited` array does across recursive calls.

- **Time:** `O(m · n · 3^L)` — same search space as the recursive versions.
- **Space:** `O(3^L · L)` in the worst case, since each stack frame may need to carry its own snapshot (or path list) of visited cells rather than sharing one mutable structure the way recursion's call stack does implicitly — noticeably worse than the recursive approaches' `O(m·n + L)` or `O(L)`.
- **When acceptable:** rarely preferable here given the space regression; mainly useful as a lateral exercise, or in languages/environments where deep recursion is genuinely constrained.

**Algorithm trace (step table, one row per popped frame — abbreviated to the successful branch only):**

| Stack (top → bottom) after push | Popped frame | Cell matches `word[wordIdx]`? | Action |
|---|---|---|---|
| `[(1,1),0,{}]` | — | — | initial frame pushed for starting cell (1,1) |
| `[(1,2),1,{(1,1)}] [(1,0),1,{(1,1)}] [(2,1),1,{(1,1)}] [(0,1),1,{(1,1)}]` | `(1,1),0,{}` | yes, `'A'` | push 4 child frames, one per direction |
| ... | `(1,0),1,{(1,1)}` | yes, `'A'` | push child frames for `(1,1)[visited,skip]`, `(2,0),2,{(1,1),(1,0)}`, out-of-bounds skipped |
| ... | `(2,0),2,{(1,1),(1,0)}` | yes, `'B'` | `wordIdx+1 == 3 == word.length()` → **return true** |

→ stack unwinds immediately on the first frame that reaches `wordIdx == word.length()`; the other branches explored earlier (e.g. `(1,2)`, `(0,1)`) are abandoned in the stack without needing an explicit "unmark" step, since each frame owns its own `pathCells` copy rather than sharing one mutable array.
