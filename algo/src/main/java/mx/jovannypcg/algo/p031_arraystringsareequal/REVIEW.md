| | |
|---|---|
| **Solved on** | 2026-08-04 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** The implementation is correct. It walks both arrays simultaneously using two independent 2D coordinates (an array index and a within-string character index for each side), comparing one character at a time. When a within-string index reaches the end of the current word, it resets to 0 and advances the array index. The loop stops as soon as either side runs out of words, and the final check (`word1Index >= word1.length && word2Index >= word2.length`) confirms both sides were fully and simultaneously consumed — this correctly rejects cases where one concatenated string is a strict prefix of the other (different total lengths) as well as cases with a genuine character mismatch. All 13 test cases pass, including the near-constraint-scale cases (1000 single-character elements, and a 1000-character string split at different boundaries).

**Code quality:** The variable names (`word1Index`, `char1Index`, `word2Index`, `char2Index`) are self-descriptive, and the inline comments clarify their roles, so the logic is easy to follow. The two `if` blocks that roll over to the next word are clean and symmetric between the `word1` and `word2` sides.

**Time complexity:** O(N), where N is the total number of characters across both arrays (bounded by 10^3 per the constraints). Each character from each side is visited exactly once via the `charAt` calls, and `charAt` itself is O(1).

**Space complexity:** O(1) additional space — only four integer pointers are used, no auxiliary strings or data structures are built.

**Algorithm trace** — Input: `word1 = ["ab", "c"]`, `word2 = ["a", "bc"]`

```
Step 1: comparing word1[0][0] vs word2[0][0]
word1: [»ab«, c]      (word1Index=0, char1Index=0)
word2: [»a«, bc]      (word2Index=0, char2Index=0)
compare 'a' vs 'a' → match, advance char1Index and char2Index
char1Index: 0→1 (1 < len("ab")=2 → stays in word 0)
char2Index: 0→1 (1 >= len("a")=1 → reset to 0, word2Index: 0→1)
matched so far: "a"

Step 2: comparing word1[0][1] vs word2[1][0]
word1: [»ab«, c]      (word1Index=0, char1Index=1)
word2: [a, »bc«]      (word2Index=1, char2Index=0)
compare 'b' vs 'b' → match, advance char1Index and char2Index
char1Index: 1→2 (2 >= len("ab")=2 → reset to 0, word1Index: 0→1)
char2Index: 0→1 (1 < len("bc")=2 → stays in word 1)
matched so far: "ab"

Step 3: comparing word1[1][0] vs word2[1][1]
word1: [ab, »c«]      (word1Index=1, char1Index=0)
word2: [a, »bc«]      (word2Index=1, char2Index=1)
compare 'c' vs 'c' → match, advance char1Index and char2Index
char1Index: 0→1 (1 >= len("c")=1 → reset to 0, word1Index: 1→2)
char2Index: 1→2 (2 >= len("bc")=2 → reset to 0, word2Index: 1→2)
matched so far: "abc"

Loop check: word1Index=2 < word1.length=2? No → exit loop
Result: word1Index=2 >= 2 && word2Index=2 >= 2 → true
```

## 2. Optimal Approach

This problem's optimal approach is exactly the two-pointer technique the user implemented: walk both arrays character-by-character using a pair of coordinates per side (current word index, current character index within that word), comparing as you go and rolling over to the next word whenever a word is exhausted. This avoids ever materializing the full concatenated strings, which is the key advantage over any approach that builds them first.

**Time complexity:** O(N), where N is the total character count across both arrays — each character is visited once.

**Space complexity:** O(1) — only a constant number of index variables are needed.

```java
public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
    int i1 = 0, c1 = 0;
    int i2 = 0, c2 = 0;

    while (i1 < word1.length && i2 < word2.length) {
        if (word1[i1].charAt(c1) != word2[i2].charAt(c2)) {
            return false;
        }

        c1++;
        c2++;

        if (c1 == word1[i1].length()) {
            i1++;
            c1 = 0;
        }

        if (c2 == word2[i2].length()) {
            i2++;
            c2 = 0;
        }
    }

    return i1 == word1.length && i2 == word2.length;
}
```

**Algorithm trace** — Input: `word1 = ["ab", "c"]`, `word2 = ["a", "bc"]`

```
Step 1: comparing word1[0][0] vs word2[0][0]
word1: [»ab«, c]      (i1=0, c1=0)
word2: [»a«, bc]      (i2=0, c2=0)
compare 'a' vs 'a' → match, advance c1 and c2
c1: 0→1 (1 != len("ab")=2 → stays in word 0)
c2: 0→1 (1 == len("a")=1 → i2: 0→1, reset c2=0)
matched so far: "a"

Step 2: comparing word1[0][1] vs word2[1][0]
word1: [»ab«, c]      (i1=0, c1=1)
word2: [a, »bc«]      (i2=1, c2=0)
compare 'b' vs 'b' → match, advance c1 and c2
c1: 1→2 (2 == len("ab")=2 → i1: 0→1, reset c1=0)
c2: 0→1 (1 != len("bc")=2 → stays in word 1)
matched so far: "ab"

Step 3: comparing word1[1][0] vs word2[1][1]
word1: [ab, »c«]      (i1=1, c1=0)
word2: [a, »bc«]      (i2=1, c2=1)
compare 'c' vs 'c' → match, advance c1 and c2
c1: 0→1 (1 == len("c")=1 → i1: 1→2, reset c1=0)
c2: 1→2 (2 == len("bc")=2 → i2: 1→2, reset c2=0)
matched so far: "abc"

Loop check: i1=2 < word1.length=2? No → exit loop
Result: i1=2 == 2 && i2=2 == 2 → true
```

## 3. Alternative Approaches

### Concatenate with StringBuilder, then compare

Build the full string for each array using a `StringBuilder`, then compare the two resulting strings with `.equals()`.

**Time complexity:** O(N) — each character is appended once per side, and `String.equals` is O(N) in the worst case.

**Space complexity:** O(N) — both full concatenated strings are materialized in memory.

**When acceptable:** Fine given the small constraint bound (total length ≤ 10^3); the extra allocation is negligible. A reasonable first pass under interview time pressure since it's easy to reason about and get right quickly.

```java
public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
    StringBuilder sb1 = new StringBuilder();
    for (String w : word1) {
        sb1.append(w);
    }

    StringBuilder sb2 = new StringBuilder();
    for (String w : word2) {
        sb2.append(w);
    }

    return sb1.toString().equals(sb2.toString());
}
```

**Algorithm trace** — Input: `word1 = ["ab", "c"]`, `word2 = ["a", "bc"]`

Building `sb1`:

| i | word1[i] | sb1 after append |
|---|---|---|
| 0 | "ab" | "ab" |
| 1 | "c" | "abc" |

Building `sb2`:

| i | word2[i] | sb2 after append |
|---|---|---|
| 0 | "a" | "a" |
| 1 | "bc" | "abc" |

→ `"abc".equals("abc")` → `true`

### `String.join` one-liner

Use `String.join("", word1)` and `String.join("", word2)` to concatenate each array, then compare with `.equals()`. Functionally identical to the `StringBuilder` approach, just more concise.

**Time complexity:** O(N) — `String.join` internally does the same linear concatenation work.

**Space complexity:** O(N) — both joined strings are fully materialized.

**When acceptable:** Good for interview time pressure when brevity matters more than demonstrating the two-pointer trick — it's a single readable line per side.

```java
public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
    return String.join("", word1).equals(String.join("", word2));
}
```

**Algorithm trace** — Input: `word1 = ["ab", "c"]`, `word2 = ["a", "bc"]`

| Expression | Result |
|---|---|
| `String.join("", word1)` | `"ab" + "c"` → `"abc"` |
| `String.join("", word2)` | `"a" + "bc"` → `"abc"` |
| `"abc".equals("abc")` | `true` |

→ return `true`
