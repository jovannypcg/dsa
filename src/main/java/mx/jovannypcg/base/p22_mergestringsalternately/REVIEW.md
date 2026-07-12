| | |
|---|---|
| **Solved on** | 2026-07-06 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** Fully correct. The `while (idx < word1.length() || idx < word2.length())` condition naturally handles both the interleaving phase and the "append the remainder of the longer string" phase, since each `if` guard independently checks bounds before appending. All 10 test cases pass, including the equal-length case, both directions of unequal length, and the boundary lengths from the constraints (1 and 100 characters).

**Code quality:** Clean and easy to follow — a single shared index driving both strings is a natural fit for this problem, and `StringBuilder` avoids the O(n²) cost of string concatenation.

**Time complexity:** O(n + m), where `n = word1.length()` and `m = word2.length()`. The loop runs `max(n, m)` times, and each iteration does O(1) work.

**Space complexity:** O(n + m) for the output string itself; O(1) auxiliary space beyond that.

**Algorithm trace** (Annotated array) — Input: `word1 = "ab"`, `word2 = "abbxxc"`

```
idx=0
word1: [a, b]
        ^
word2: [a, b, b, x, x, c]
        ^
append word1[0]='a', word2[0]='a' → sb = "aa"

idx=1
word1: [a, b]
           ^
word2: [a, b, b, x, x, c]
           ^
append word1[1]='b', word2[1]='b' → sb = "aabb"

idx=2
word1: [a, b]              (idx out of bounds, skip)
word2: [a, b, b, x, x, c]
              ^
append word2[2]='b' → sb = "aabbb"

idx=3
word2: [a, b, b, x, x, c]
                 ^
append word2[3]='x' → sb = "aabbbx"

idx=4
word2: [a, b, b, x, x, c]
                    ^
append word2[4]='x' → sb = "aabbbxx"

idx=5
word2: [a, b, b, x, x, c]
                       ^
append word2[5]='c' → sb = "aabbbxxc"

idx=6 → idx >= word1.length() and idx >= word2.length() → loop ends
→ return "aabbbxxc"
```

## 2. Optimal Approach

This problem has essentially one optimal shape: walk both strings with a shared index and append whichever characters are still in bounds, using a mutable buffer. The user's solution already implements this — there isn't a faster asymptotic approach, since every character of both strings must be read and written at least once.

**Time complexity:** O(n + m) — one pass, one append per character.

**Space complexity:** O(n + m) for the result; O(1) auxiliary.

```java
public String mergeAlternately(String word1, String word2) {
    StringBuilder result = new StringBuilder(word1.length() + word2.length());
    int i = 0;

    while (i < word1.length() || i < word2.length()) {
        if (i < word1.length()) result.append(word1.charAt(i));
        if (i < word2.length()) result.append(word2.charAt(i));
        i++;
    }

    return result.toString();
}
```

**Algorithm trace** (Annotated array) — Input: `word1 = "abcd"`, `word2 = "xy"`

```
i=0
word1: [a, b, c, d]
        ^
word2: [x, y]
        ^
append 'a', 'x' → result = "ax"

i=1
word1: [a, b, c, d]
           ^
word2: [x, y]
           ^
append 'b', 'y' → result = "axby"

i=2
word1: [a, b, c, d]
              ^
word2: [x, y]              (out of bounds, skip)
append 'c' → result = "axbyc"

i=3
word1: [a, b, c, d]
                 ^
append 'd' → result = "axbycd"

i=4 → both out of bounds → loop ends
→ return "axbycd"
```

## 3. Alternative Approaches

### Two independent pointers with separate phases
Use one pointer `i` for `word1` and one `j` for `word2`. First loop while both are in bounds, interleaving characters; then two follow-up loops each drain whatever remains of the longer string. Functionally identical to the optimal approach, just split into explicit phases instead of one combined condition.

**Time complexity:** O(n + m) — same total work, just organized into up to three loops.
**Space complexity:** O(n + m) for the result; O(1) auxiliary.

**When acceptable:** Interview settings where you want to make the "handle the leftover tail" step visually explicit rather than folding it into a single combined condition.

**Algorithm trace** (Annotated array) — Input: `word1 = "ab"`, `word2 = "abbxxc"`

```
Phase 1 — interleave while both in bounds
i=0,j=0: word1[0]='a', word2[0]='a' → result = "aa"
word1: [a, b]      word2: [a, b, b, x, x, c]
        ^                  ^
i=1,j=1: word1[1]='b', word2[1]='b' → result = "aabb"
word1: [a, b]      word2: [a, b, b, x, x, c]
           ^                  ^
i=2 → i == word1.length(), stop phase 1

Phase 2 — word1 has no remainder (i already at end), skip

Phase 3 — drain remainder of word2 from j=2
word2: [a, b, b, x, x, c]
              ^  ^  ^  ^
append 'b','x','x','c' → result = "aabbbxxc"
→ return "aabbbxxc"
```

### Recursive character-by-character merge
Define `merge(word1, word2, i)` that appends `word1.charAt(i)` (if in bounds) and `word2.charAt(i)` (if in bounds) to a shared buffer, then recurses on `i + 1` until both are out of bounds. Same logic as the iterative version, just expressed as recursion.

**Time complexity:** O(n + m) — one call per index, O(1) work per call.
**Space complexity:** O(n + m) — O(max(n, m)) call-stack frames in addition to the O(n + m) result buffer.

**When acceptable:** Rarely preferable here — recursion adds call-stack overhead and risks a stack overflow on pathological inputs with no benefit, since the problem has no natural divide-and-conquer structure. Mostly useful as a way to demonstrate recursion fluency in an interview if explicitly asked to avoid loops.

**Algorithm trace** (Call stack table) — Input: `word1 = "ab"`, `word2 = "abbxxc"`

| Depth | Call | Appends | Returns |
|---|---|---|---|
| 0 | merge(i=0) | 'a', 'a' | merge(i=1) |
| 1 | merge(i=1) | 'b', 'b' | merge(i=2) |
| 2 | merge(i=2) | 'b' (word1 out of bounds) | merge(i=3) |
| 3 | merge(i=3) | 'x' | merge(i=4) |
| 4 | merge(i=4) | 'x' | merge(i=5) |
| 5 | merge(i=5) | 'c' | merge(i=6) |
| 6 | merge(i=6) | — both out of bounds | base case: return buffer |

→ buffer = "aabbbxxc"
