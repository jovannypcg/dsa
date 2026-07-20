| | |
|---|---|
| **Solved on** | 2026-07-19 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

### Correctness

Handles all cases correctly: single-character strings, single words with no spaces, multiple words, palindromic words, and words with digits/punctuation. The two-phase design — advance `right` until a space is hit, reverse the word behind it, then reverse the final word once the loop exits — correctly covers the "no trailing space" guarantee from the constraints, since the last word never gets its own space delimiter and needs the post-loop `reverse` call.

The `str == null || str.isBlank()` guard is dead code under this problem's constraints (`s` always has length ≥ 1 and at least one non-space word), but it's harmless.

### Time complexity

**O(n)**, where `n = s.length()`. The outer scan visits each character once via `right`, and each character is swapped at most once across all the `reverse` calls (each character belongs to exactly one word).

### Space complexity

**O(n)** for the `StringBuilder` copy of the input (required, since Java strings are immutable and the result must be a new string). Excluding the output, the algorithm uses only O(1) auxiliary space (`left`, `right`, `from`, `to`).

### Algorithm trace

Input: `s = "Mr Ding"` → indices: `0:M 1:r 2:_ 3:D 4:i 5:n 6:g` (`_` = space)

```
M  r  _  D  i  n  g
L
R
right=0 → sb[R]='M' (not space) → advance R
```

```
M  r  _  D  i  n  g
L
   R
right=1 → sb[R]='r' (not space) → advance R
```

```
M  r  _  D  i  n  g
L
      R
right=2 → sb[R]=' ' (space!) → word boundary → reverse(sb, L=0, R-1=1)
```

Inner reverse of `sb[0..1]` ("Mr"):
```
M  r  _  D  i  n  g
F
   T
from=0, to=1 → swap sb[0], sb[1]
```
```
r  M  _  D  i  n  g
from=1, to=0 → from >= to → stop
```
sb after word 1: `r M _ D i n g` → right advances to 3, left set to 3

```
r  M  _  D  i  n  g
         L
         R
right=3 → sb[R]='D' (not space) → advance R
```

```
r  M  _  D  i  n  g
         L
            R
right=4 → sb[R]='i' (not space) → advance R
```

```
r  M  _  D  i  n  g
         L
               R
right=5 → sb[R]='n' (not space) → advance R
```

```
r  M  _  D  i  n  g
         L
                  R
right=6 → sb[R]='g' (not space) → advance R
```

`right=7` now equals `sb.length()` → loop exits → post-loop call `reverse(sb, L=3, R-1=6)`

Inner reverse of `sb[3..6]` ("Ding"):
```
r  M  _  D  i  n  g
         F
               T
from=3, to=6 → swap sb[3], sb[6]
```
```
r  M  _  g  i  n  D
            F
         T
from=4, to=5 → swap sb[4], sb[5]
```
```
r  M  _  g  n  i  D
from=5, to=4 → from >= to → stop
```

Final: `sb = "rM gniD"` → matches expected output ✓

## 2. Optimal Approach

The user's solution **is** the optimal approach: a single forward scan tracking the start of the current word (`left`) plus a running index (`right`) that finds word boundaries, with an in-place two-pointer reversal (`from`/`to`) applied to each word's slice. No auxiliary word list, split, or extra buffer beyond the one output buffer is needed.

- **Time complexity: O(n)** — every character is visited once by the scan pointer and swapped at most once by a `reverse` call.
- **Space complexity: O(n)** for the output buffer (unavoidable, since strings are immutable in Java); O(1) auxiliary space otherwise.

```java
public String reverseWords(String s) {
    char[] chars = s.toCharArray();
    int wordStart = 0;

    for (int i = 0; i <= chars.length; i++) {
        if (i == chars.length || chars[i] == ' ') {
            reverse(chars, wordStart, i - 1);
            wordStart = i + 1;
        }
    }

    return new String(chars);
}

private void reverse(char[] chars, int from, int to) {
    while (from < to) {
        char tmp = chars[from];
        chars[from] = chars[to];
        chars[to] = tmp;
        from++;
        to--;
    }
}
```

### Algorithm trace

Identical mechanics to the trace in Section 1 — the only difference from the user's submission is using a `char[]` instead of a `StringBuilder` and folding the "final word" case into the main loop via the `i == chars.length` sentinel check instead of a separate call after the loop. See the trace above for the full step-by-step walkthrough on `"Mr Ding"`.

## 3. Alternative Approaches

### A. Split, reverse each word with a library call, rejoin

```java
public String reverseWords(String s) {
    String[] words = s.split(" ");

    for (int i = 0; i < words.length; i++) {
        words[i] = new StringBuilder(words[i]).reverse().toString();
    }

    return String.join(" ", words);
}
```

- **Time complexity: O(n)** — splitting, reversing every character once total across all words, and rejoining are each linear in the total string length.
- **Space complexity: O(n)** — `split` allocates an array of word strings, each `StringBuilder.reverse()` allocates a new string, and `join` allocates the final result, so this uses several multiples of O(n) rather than the single buffer the in-place approach uses.
- **When acceptable:** Fine under interview time pressure or when code brevity matters more than minimizing allocations — for `n` up to `5 * 10^4` the extra allocations are not a real performance concern.

**Trace** (annotated array, word-level since this approach operates on whole words rather than character-level pointers):
```
words: ["Mr", "Ding"]
        i=0
reverse("Mr") → "rM"
words: ["rM", "Ding"]

words: ["rM", "Ding"]
              i=1
reverse("Ding") → "gniD"
words: ["rM", "gniD"]
```
→ `String.join(" ", ["rM", "gniD"])` = `"rM gniD"`

### B. Build the result word-by-word with a stack (or explicit index scan) into a new buffer

```java
public String reverseWords(String s) {
    StringBuilder result = new StringBuilder();
    Deque<Character> stack = new ArrayDeque<>();

    for (int i = 0; i <= s.length(); i++) {
        if (i == s.length() || s.charAt(i) == ' ') {
            while (!stack.isEmpty()) {
                result.append(stack.pop());
            }
            if (i != s.length()) {
                result.append(' ');
            }
        } else {
            stack.push(s.charAt(i));
        }
    }

    return result.toString();
}
```

- **Time complexity: O(n)** — every character is pushed once and popped once.
- **Space complexity: O(n)** — the stack holds up to one word's worth of characters at a time, plus the O(n) result buffer.
- **When acceptable:** A reasonable fallback if two-pointer in-place reversal doesn't come to mind immediately in an interview — it's a natural way to "reverse" a word using a well-known data structure, though it does more allocation work than necessary.

**Trace** (annotated array over `stack` and `result` per character):
```
s: M  r  _  D  i  n  g
   i
stack: []   result: ""
push 'M' → stack: [M]

s: M  r  _  D  i  n  g
      i
stack: [M]   result: ""
push 'r' → stack: [M,r]

s: M  r  _  D  i  n  g
         i
stack: [M,r]   result: ""
space → pop all → result: "rM" → append ' ' → result: "rM "
stack: []

s: M  r  _  D  i  n  g
            i
stack: []   result: "rM "
push 'D' → stack: [D]

s: M  r  _  D  i  n  g
               i
stack: [D]   result: "rM "
push 'i' → stack: [D,i]

s: M  r  _  D  i  n  g
                  i
stack: [D,i]   result: "rM "
push 'n' → stack: [D,i,n]

s: M  r  _  D  i  n  g
                     i
stack: [D,i,n]   result: "rM "
push 'g' → stack: [D,i,n,g]

i=7 == s.length() → pop all → result: "rM gniD"
```
→ return `"rM gniD"`
