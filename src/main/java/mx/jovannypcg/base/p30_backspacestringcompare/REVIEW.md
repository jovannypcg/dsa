| | |
|---|---|
| **Solved on** | 2026-07-20 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

### Correctness

Correct. It handles every case in the constraint space: no backspaces, chained/consecutive backspaces (`"a##b"`), backspaces that outnumber the available characters (`"##a#b"`), backspaces that fully empty both strings (`"ab##"` vs `"c#d#"`), and unequal-length surviving results. The core idea — scan each string from the right, and whenever a `'#'` is met, accumulate a skip counter that cancels out the next real characters — is applied independently and correctly to both `q` and `p`.

The one subtle piece of the logic is the "subtract-then-check" pattern: the inner run-of-`'#'` loop moves `qPos` left while incrementing `qBackspaceCount`, then control returns to the top of the outer `while (qPos >= 0)`, which does `qPos -= qBackspaceCount` *again* before resetting the counter to 0. This looks like a double subtraction at first glance, but it isn't: the run-of-`'#'` loop stops exactly at the index of the *candidate victim character*, and the follow-up subtraction is what actually consumes that victim (and any earlier characters canceled by the same run). It works, but it takes a careful trace to convince yourself of that — a reader skimming the method is likely to suspect an off-by-one bug where none exists.

### Code Quality

- Parameter names `q` and `p` don't match the problem's vocabulary (`s`, `t` in the prompt and `README.md`), which costs a small amount of readability when cross-referencing the problem statement.
- The `q`-side and `p`-side blocks are structurally identical (same nested-while shape, same subtract/reset/break logic) applied to two different strings — a textbook case for extracting a helper method to remove duplication.
- The sentinel value `'\0'` for "no character left" works because `'\0'` can never appear in the input, but it's an implicit contract rather than an obvious one — comparing indices (`qPos < 0`) directly is more idiomatic than comparing a placeholder character.
- Three levels of nested `while` loops with multiple `break` statements make the control flow harder to follow than the underlying algorithm requires.

### Time Complexity

**O(n + m)**, where `n = q.length()` and `m = p.length()`. Although the method has nested loops, `qPos` and `pPos` only ever move left and never reset forward — every position in each string is visited a constant number of times across the entire run (once by the run-of-`'#'` loop, at most once more by the subtract step). So the total work is linear in the combined input size, not quadratic.

### Space Complexity

**O(1)** additional space. Only a fixed number of integer variables are used; no auxiliary strings, stacks, or arrays are built.

### Algorithm Trace

Input: `s = "ab#c"`, `t = "ad#c"` (using `q = s`, `p = t`)

```
Step 1 — compare rightmost surviving characters
s: [a, b, #, c]        t: [a, d, #, c]
              ^qPos=3               ^pPos=3
s[3]='c'  t[3]='c'  → 'c' == 'c' → match
advance both pointers left: qPos=2, pPos=2

Step 2 — '#' encountered on both sides, skip cancels the preceding char
s: [a, b, #, c]        t: [a, d, #, c]
        ^qPos=2 is '#'          ^pPos=2 is '#'
run-of-'#' loop: qBackspaceCount=1, qPos→1 ('b')     pBackspaceCount=1, pPos→1 ('d')
subtract step:   qPos = 1 - 1 = 0 ('a')              pPos = 1 - 1 = 0 ('a')
s[0]='a'  t[0]='a'  → 'a' == 'a' → match
advance both pointers left: qPos=-1, pPos=-1

Step 3 — both pointers exhausted
qPos=-1, pPos=-1 → loop condition (qPos>=0 || pPos>=0) is false → exit
return qPos<0 && pPos<0 → true
```
Result: `true` — both strings reduce to `"ac"`, matching the expected output.

### Improved Implementation

The algorithm itself doesn't need to change — it's already the optimal O(n + m) / O(1) approach. What can improve is the shape of the code: extracting the duplicated "find the next surviving character" logic into a single helper removes the two near-identical blocks, replaces the subtract/reset trick with a simpler skip-counter that decrements as it consumes canceled characters (avoiding the double-subtraction read), uses parameter names that match the problem statement, and replaces the `'\0'` sentinel with direct index checks.

```java
public boolean backspaceCompare(String s, String t) {
    int i = s.length() - 1;
    int j = t.length() - 1;

    while (i >= 0 || j >= 0) {
        i = nextSurvivingIndex(s, i);
        j = nextSurvivingIndex(t, j);

        if (i < 0 || j < 0) {
            return i == j;
        }

        if (s.charAt(i) != t.charAt(j)) {
            return false;
        }

        i--;
        j--;
    }

    return true;
}

private int nextSurvivingIndex(String str, int pos) {
    int skip = 0;

    while (pos >= 0) {
        if (str.charAt(pos) == '#') {
            skip++;
            pos--;
        } else if (skip > 0) {
            skip--;
            pos--;
        } else {
            break;
        }
    }

    return pos;
}
```

Complexity is unchanged: **O(n + m) time** (each index is visited a constant number of times by `nextSurvivingIndex` across the whole run) and **O(1) space**.

#### Algorithm Trace (Improved Implementation)

Input: `s = "ab#c"`, `t = "ad#c"`

```
Step 1 — nextSurvivingIndex(s, 3) and nextSurvivingIndex(t, 3)
s: [a, b, #, c]        t: [a, d, #, c]
              ^pos=3               ^pos=3
s[3]='c' not '#', skip=0 → return 3      t[3]='c' not '#', skip=0 → return 3
i=3, j=3 → s[3]='c' == t[3]='c' → match
i--, j-- → i=2, j=2

Step 2 — nextSurvivingIndex(s, 2) and nextSurvivingIndex(t, 2)
s: [a, b, #, c]        t: [a, d, #, c]
        ^pos=2 is '#'           ^pos=2 is '#'
skip=1, pos=1 ('b'): skip>0 → skip=0, pos=0 ('a')   skip=1, pos=1 ('d'): skip>0 → skip=0, pos=0 ('a')
pos=0, 'a' not '#', skip=0 → return 0                pos=0, 'a' not '#', skip=0 → return 0
i=0, j=0 → s[0]='a' == t[0]='a' → match
i--, j-- → i=-1, j=-1

Step 3 — both pointers exhausted
i=-1, j=-1 → loop condition false → exit
return true
```
Result: `true`, identical to the original implementation, with a more direct trace through each skip decision.

## 2. Optimal Approach

This problem is already best solved with the two-pointer, right-to-left scan used above — it processes each string once, needs no auxiliary storage, and short-circuits as soon as a mismatch is found, so there's nothing more optimal to reach for. The plain-language idea: process each string from the end backward. When a `'#'` is seen, it doesn't just delete the previous character immediately — it needs to be treated as "one pending deletion" so that a run of consecutive `'#'` characters, or a `'#'` immediately following an already-canceled character, is handled correctly. A `skip` counter tracks how many upcoming (i.e., leftward) real characters still need to be canceled. Once a real, uncanceled character is found in each string, the two are compared directly; a mismatch (or one string running out before the other) means the two typed results differ.

**Time Complexity: O(n + m)** — each string is scanned once from right to left; every index is visited a constant number of times.

**Space Complexity: O(1)** — only pointers and a skip counter are used; no result strings are built.

```java
public boolean backspaceCompare(String s, String t) {
    int i = s.length() - 1;
    int j = t.length() - 1;

    while (i >= 0 || j >= 0) {
        i = nextSurvivingIndex(s, i);
        j = nextSurvivingIndex(t, j);

        if (i < 0 || j < 0) {
            return i == j;
        }

        if (s.charAt(i) != t.charAt(j)) {
            return false;
        }

        i--;
        j--;
    }

    return true;
}

private int nextSurvivingIndex(String str, int pos) {
    int skip = 0;

    while (pos >= 0) {
        if (str.charAt(pos) == '#') {
            skip++;
            pos--;
        } else if (skip > 0) {
            skip--;
            pos--;
        } else {
            break;
        }
    }

    return pos;
}
```

### Algorithm Trace

Input: `s = "xywrrmp"`, `t = "xywrrmu#p"`

```
Step 1 — nextSurvivingIndex(s, 6) and nextSurvivingIndex(t, 8)
s: [x, y, w, r, r, m, p]              t: [x, y, w, r, r, m, u, #, p]
                     ^pos=6                                     ^pos=8
s[6]='p' not '#', skip=0 → return 6    t[8]='p' not '#', skip=0 → return 8
i=6, j=8 → s[6]='p' == t[8]='p' → match
i--, j-- → i=5, j=7

Step 2 — nextSurvivingIndex(s, 5) and nextSurvivingIndex(t, 7)
s: [x, y, w, r, r, m, p]              t: [x, y, w, r, r, m, u, #, p]
                  ^pos=5                                  ^pos=7 is '#'
s[5]='m' not '#', skip=0 → return 5    skip=1, pos=6 ('u'): skip>0 → skip=0, pos=5 ('m')
                                        pos=5, 'm' not '#', skip=0 → return 5
i=5, j=5 → s[5]='m' == t[5]='m' → match
i--, j-- → i=4, j=4

Step 3 — remaining characters "xywrr" vs "xywrr" match one by one
i=4→0 and j=4→0, each pair ('r','r'), ('r','r'), ('w','w'), ('y','y'), ('x','x') matches
i--, j-- down to i=-1, j=-1

Step 4 — both pointers exhausted
i=-1, j=-1 → loop condition false → exit
return true
```
Result: `true` — `s` is already `"xywrrmp"`, and `t`'s trailing `"u#"` cancels out, leaving `"xywrrmp"` too.

## 3. Alternative Approaches

### Build the Result Strings (Stack / StringBuilder Simulation)

Simulate typing into each editor using a `StringBuilder` (or an explicit stack) as a mutable buffer: append a character for a letter, and delete the last character (if any) for a `'#'`. Compare the two finished buffers with `.equals()`.

**Time Complexity: O(n + m)** — each character is processed once, and `StringBuilder.deleteCharAt` on the last index and `.equals()` are both linear overall.

**Space Complexity: O(n + m)** — both final (or intermediate) strings must be materialized.

This is the most natural first approach and is perfectly acceptable under interview time pressure or when code clarity matters more than shaving off the auxiliary space — it directly mirrors the problem statement ("typed into a text editor") and is easy to explain and verify.

```java
public boolean backspaceCompare(String s, String t) {
    return build(s).equals(build(t));
}

private String build(String str) {
    StringBuilder sb = new StringBuilder();

    for (char c : str.toCharArray()) {
        if (c == '#') {
            if (sb.length() > 0) {
                sb.deleteCharAt(sb.length() - 1);
            }
        } else {
            sb.append(c);
        }
    }

    return sb.toString();
}
```

#### Algorithm Trace

Input: `s = "ab#c"`

| char | action | buffer before | buffer after |
|---|---|---|---|
| `a` | append | `""` | `"a"` |
| `b` | append | `"a"` | `"ab"` |
| `#` | delete last | `"ab"` | `"a"` |
| `c` | append | `"a"` | `"ac"` |
→ `build("ab#c")` = `"ac"`. The same process on `t = "ad#c"` also yields `"ac"`, so `"ac".equals("ac")` → `true`.

### Deque-Based Simulation

A variant of the buffer approach using an explicit `Deque<Character>` as a stack instead of `StringBuilder`: push a letter, pop on `'#'` (if the stack isn't empty), then compare the two stacks (or convert them to strings/arrays) for equality.

**Time Complexity: O(n + m)** — same reasoning as the `StringBuilder` version, one push/pop per character.

**Space Complexity: O(n + m)** — the stacks hold up to the full length of each string.

Functionally equivalent to the `StringBuilder` approach; only worth reaching for if a `Deque` is already in play elsewhere in the same solution, since `StringBuilder` is simpler for pure string building.

#### Algorithm Trace

Input: `t = "c#d#"`

| char | action | stack before (top → right) | stack after |
|---|---|---|---|
| `c` | push | `[]` | `[c]` |
| `#` | pop | `[c]` | `[]` |
| `d` | push | `[]` | `[d]` |
| `#` | pop | `[d]` | `[]` |
→ final stack for `t` is `[]` (empty string). Compared against `s = "ab##"`, whose stack also empties out to `[]`, the two are equal → `true`.
