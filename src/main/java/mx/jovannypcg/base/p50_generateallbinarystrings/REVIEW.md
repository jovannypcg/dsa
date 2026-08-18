| | |
|---|---|
| **Solved on** | 2026-08-18 |
| **DSA Category** | Backtracking |

## 1. Your Solution Assessment

### Correctness

The implementation is correct. It builds each string incrementally in a `StringBuilder`, tries `'A'` before `'B'` at every position, and appends a completed string to the output once `path.length() == n`. Because `LETTERS = {'A', 'B'}` is iterated in that fixed order and each recursive call fully explores the `'A'` branch before starting the `'B'` branch, the results come out in lexicographic order automatically — no separate sort needed. All 9 tests pass, including the `n = 16` upper-boundary check (65,536 strings, correct first/last elements, no duplicates).

### Code Quality

Clean and idiomatic backtracking: a static `LETTERS` array avoids repeating character literals, the mutable `StringBuilder path` is shared across calls and properly backtracked (`append` then `deleteCharAt` after the recursive call returns), and the base case is a simple length check. One minor note: `backtracking` is package-private rather than `private` — harmless here since nothing else in the package calls it, but `private` would communicate intent slightly better.

### Time Complexity

**O(n · 2ⁿ)**. There are exactly 2ⁿ leaves in the recursion tree (one per length-`n` string), and each leaf does O(n) work to materialize the string via `path.toString()`. The internal (non-leaf) nodes contribute a lower-order O(2ⁿ) term for the recursive calls themselves, so the string-copying work dominates.

### Space Complexity

**O(n)** auxiliary, beyond the output. The recursion depth is at most `n` (one stack frame per character position), and the shared `StringBuilder` also holds at most `n` characters at a time. The output list itself needs O(n · 2ⁿ) to store all the strings, but that's required by the problem — it's not extra overhead from the algorithm.

### Algorithm Trace

Input: `n = 2`

| Depth | Call | Returns |
|---|---|---|
| 0 | `backtracking(path="")` | tries `'A'` then `'B'` |
| 1 | `backtracking(path="A")` | tries `'A'` then `'B'` |
| 2 | `backtracking(path="AA")` | length == 2 → add `"AA"` to output |
| 2 | `backtracking(path="AB")` | length == 2 → add `"AB"` to output |
| 1 | `backtracking(path="B")` | tries `'A'` then `'B'` |
| 2 | `backtracking(path="BA")` | length == 2 → add `"BA"` to output |
| 2 | `backtracking(path="BB")` | length == 2 → add `"BB"` to output |

→ `out = ["AA", "AB", "BA", "BB"]`

## 2. Optimal Approach

This backtracking approach **is** the optimal approach. The output itself has size Θ(n · 2ⁿ) (2ⁿ strings, each of length n), so no algorithm can beat Θ(n · 2ⁿ) time — you must at minimum write out every character of every result. Backtracking achieves this bound with O(n) auxiliary space, which is optimal as well. A cleaner canonical version below swaps `StringBuilder` for a `char[]` buffer, which avoids the small overhead of `StringBuilder`'s internal resizing/char-array management, though this doesn't change the asymptotic complexity.

```java
public List<String> generateStrings(int n) {
    List<String> result = new ArrayList<>();
    backtrack(new char[n], 0, result);
    return result;
}

private void backtrack(char[] buffer, int index, List<String> result) {
    if (index == buffer.length) {
        result.add(new String(buffer));
        return;
    }

    buffer[index] = 'A';
    backtrack(buffer, index + 1, result);

    buffer[index] = 'B';
    backtrack(buffer, index + 1, result);
}
```

**Time complexity: O(n · 2ⁿ)** — same reasoning as above: 2ⁿ leaves, each producing an O(n) string via `new String(buffer)`.

**Space complexity: O(n)** auxiliary — the `char[]` buffer and recursion depth are both bounded by `n`; the output storage is required by the problem, not extra overhead.

### Algorithm Trace

Input: `n = 3`

| Depth | Call | Returns |
|---|---|---|
| 0 | `backtrack(buffer=[_,_,_], idx=0)` | sets `buffer[0]='A'`, then `buffer[0]='B'` |
| 1 | `backtrack(buffer=[A,_,_], idx=1)` | sets `buffer[1]='A'`, then `buffer[1]='B'` |
| 2 | `backtrack(buffer=[A,A,_], idx=2)` | sets `buffer[2]='A'`, then `buffer[2]='B'` |
| 3 | `backtrack(buffer=[A,A,A], idx=3)` | idx == 3 → add `"AAA"` |
| 3 | `backtrack(buffer=[A,A,B], idx=3)` | idx == 3 → add `"AAB"` |
| 2 | `backtrack(buffer=[A,B,_], idx=2)` | sets `buffer[2]='A'`, then `buffer[2]='B'` |
| 3 | `backtrack(buffer=[A,B,A], idx=3)` | idx == 3 → add `"ABA"` |
| 3 | `backtrack(buffer=[A,B,B], idx=3)` | idx == 3 → add `"ABB"` |
| 1 | `backtrack(buffer=[B,_,_], idx=1)` | sets `buffer[1]='A'`, then `buffer[1]='B'` |
| 2 | `backtrack(buffer=[B,A,_], idx=2)` | sets `buffer[2]='A'`, then `buffer[2]='B'` |
| 3 | `backtrack(buffer=[B,A,A], idx=3)` | idx == 3 → add `"BAA"` |
| 3 | `backtrack(buffer=[B,A,B], idx=3)` | idx == 3 → add `"BAB"` |
| 2 | `backtrack(buffer=[B,B,_], idx=2)` | sets `buffer[2]='A'`, then `buffer[2]='B'` |
| 3 | `backtrack(buffer=[B,B,A], idx=3)` | idx == 3 → add `"BBA"` |
| 3 | `backtrack(buffer=[B,B,B], idx=3)` | idx == 3 → add `"BBB"` |

→ `out = ["AAA", "AAB", "ABA", "ABB", "BAA", "BAB", "BBA", "BBB"]`

## 3. Alternative Approaches

### A. Iterative Bit-Manipulation

Loop `i` from `0` to `2ⁿ - 1`. Each `i` is an n-bit number; for bit position `j` (from the most significant to least significant, so the result comes out in lexicographic order), if the bit is `0` place `'A'`, otherwise place `'B'`. This treats every integer in `[0, 2ⁿ)` as a blueprint for one output string.

**Time complexity: O(n · 2ⁿ)** — 2ⁿ values of `i`, each requiring O(n) work to inspect its `n` bits and build the string.

**Space complexity: O(n)** auxiliary (the per-string character buffer), plus the required O(n · 2ⁿ) output.

This is a reasonable alternative under interview time pressure since it avoids recursion entirely — some interviewers prefer seeing you reach for an iterative bit-trick, though it's less immediately readable than backtracking to someone unfamiliar with the pattern.

```java
public List<String> generateStrings(int n) {
    List<String> result = new ArrayList<>();
    int total = 1 << n;

    for (int i = 0; i < total; i++) {
        char[] buffer = new char[n];
        for (int j = 0; j < n; j++) {
            int bit = (i >> (n - 1 - j)) & 1;
            buffer[j] = (bit == 0) ? 'A' : 'B';
        }
        result.add(new String(buffer));
    }

    return result;
}
```

#### Algorithm Trace

Input: `n = 2` (total = 4)

| i | binary (n bits) | buffer built | string added |
|---|---|---|---|
| 0 | 00 | ['A','A'] | "AA" |
| 1 | 01 | ['A','B'] | "AB" |
| 2 | 10 | ['B','A'] | "BA" |
| 3 | 11 | ['B','B'] | "BB" |

→ `result = ["AA", "AB", "BA", "BB"]`

### B. Iterative Level-by-Level (BFS-style) Expansion

Start with a list containing just `[""]`. At each of the `n` rounds, build a new list by taking every string currently in the list and appending both `'A'` and `'B'` to it, doubling the list's size each round. After `n` rounds, the list holds all 2ⁿ strings.

**Time complexity: O(n · 2ⁿ)** — the list doubles n times, and at round `k` there are 2ᵏ strings each of length up to `k`, so the total character-copying work across all rounds is O(n · 2ⁿ).

**Space complexity: O(n · 2ⁿ)** — unlike backtracking, this approach keeps a full intermediate list of up to 2ⁿ partial strings alive at every round (not just O(n) on a call stack), so its peak auxiliary space is much larger.

This approach is acceptable for small `n` or when an interviewer wants to see an explicitly iterative, non-recursive construction, but it's strictly worse on space than backtracking or bit-manipulation, so it's a weaker choice once `n` grows.

```java
public List<String> generateStrings(int n) {
    List<String> result = new ArrayList<>();
    result.add("");

    for (int round = 0; round < n; round++) {
        List<String> next = new ArrayList<>();
        for (String s : result) {
            next.add(s + "A");
            next.add(s + "B");
        }
        result = next;
    }

    return result;
}
```

#### Algorithm Trace

Input: `n = 2`

| round | list before | list after |
|---|---|---|
| 0 | `[""]` | `["A", "B"]` |
| 1 | `["A", "B"]` | `["AA", "AB", "BA", "BB"]` |

→ `result = ["AA", "AB", "BA", "BB"]`
