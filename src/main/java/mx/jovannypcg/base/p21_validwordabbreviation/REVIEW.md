| | |
|---|---|
| **Solved on** | 2026-07-06 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

### Correctness

The implementation is correct and handles every edge case implied by the constraints:

- **Leading zero** (`current == '0'` check fires only when a digit *group* starts, so `"01"`, `"w0rd"`, `"a0"`, etc. are all correctly rejected).
- **Empty-substring replacement** (a lone `"0"` is caught by the same leading-zero check, since a jump of length 0 is never allowed to be explicit).
- **Adjacent replacements collapsing into one number** (`"i57n"`-style abbreviations parse `57` as a single multi-digit jump rather than two separate jumps — this is the standard, expected parsing rule, and it still correctly rejects invalid abbreviations because the combined number then fails the length check).
- **Overshoot** (`w + jump > word.length()` returns `false` before the jump is applied).
- **Undershoot / trailing characters** (the final `return w == word.length() && a == abbr.length()` requires both pointers to finish exactly together).
- **Boundary case where `word` and `abbr` are identical strings** (falls through the letter-matching branch every time).

### Code Quality

Clean and readable. Variable names `w`, `a`, and `jump` are short but immediately explained by the inline comments; the two early-return branches (`letter mismatch`, `leading zero`) keep the main body flat instead of nesting. One very minor nit: the digit-parsing `while` loop could be extracted into a small private helper (e.g. `parseNumber`) to make the outer loop read as a flat sequence of cases, but at this size it's a matter of taste, not a defect.

### Time Complexity

**O(n + m)**, where `n = word.length()` and `m = abbr.length()`. Both pointers `w` and `a` only ever move forward — `w` advances either by 1 (letter match) or by `jump` (digit group), and `a` advances by 1 or by the number of digit characters consumed. Neither pointer is ever reset backward, so the total work across the whole run is bounded by a single pass over each string.

### Space Complexity

**O(1)** — only a constant number of integer variables (`w`, `a`, `jump`) are used; no auxiliary data structures.

### Algorithm Trace

Representative example: `word = "implementation"`, `abbr = "imp4n5n"` → expected `true`.

```
Step: w=0, a=0 → compare 'i' (abbr) vs 'i' (word) → match, advance both

word: i m p l e m e n t a t i o n
      ^
abbr: i m p 4 n 5 n
      ^
```

```
Step: w=1, a=1 → compare 'm' vs 'm' → match, advance both

word: i m p l e m e n t a t i o n
        ^
abbr: i m p 4 n 5 n
        ^
```

```
Step: w=2, a=2 → compare 'p' vs 'p' → match, advance both

word: i m p l e m e n t a t i o n
          ^
abbr: i m p 4 n 5 n
          ^
```

```
Step: w=3, a=3 → abbr[a] = '4' is a digit → parse number "4" → jump=4
      check 3 + 4 = 7 <= 14 ✓ → w jumps from 3 to 7, a advances past the digit to 4

word: i m p l e m e n t a t i o n
            ^                 (before jump)
abbr: i m p 4 n 5 n
            ^
```

```
Step: w=7, a=4 → compare 'n' (word[7]) vs 'n' (abbr[4]) → match, advance both

word: i m p l e m e n t a t i o n
                    ^
abbr: i m p 4 n 5 n
              ^
```

```
Step: w=8, a=5 → abbr[a] = '5' is a digit → parse number "5" → jump=5
      check 8 + 5 = 13 <= 14 ✓ → w jumps from 8 to 13, a advances past the digit to 6

word: i m p l e m e n t a t i o n
                      ^                 (before jump)
abbr: i m p 4 n 5 n
                ^
```

```
Step: w=13, a=6 → compare 'n' (word[13]) vs 'n' (abbr[6]) → match, advance both

word: i m p l e m e n t a t i o n
                                ^
abbr: i m p 4 n 5 n
                  ^
```

```
Final: w=14, a=7 → loop ends (a == abbr.length())
      w == word.length() (14 == 14) ✓ and a == abbr.length() (7 == 7) ✓ → return true
```

---

## 2. Optimal Approach

This *is* the optimal approach. Because the abbreviation grammar strictly alternates letters and digit-groups, a single left-to-right scan with two pointers is enough — there is no benefit to precomputation, sorting, or auxiliary data structures. The two-pointer walk is:

1. If the current `abbr` character is a letter, it must equal `word[w]`; advance both pointers by 1.
2. If it's a digit, it must not be `'0'` (no leading zero / no empty replacement); consume the full run of consecutive digits to form the number, then jump `w` forward by that amount, making sure it doesn't run past `word.length()`.
3. At the end, both pointers must have reached the end of their strings.

```java
public boolean validWordAbbreviation(String word, String abbr) {
    int w = 0, a = 0;

    while (w < word.length() && a < abbr.length()) {
        char c = abbr.charAt(a);

        if (Character.isLetter(c)) {
            if (c != word.charAt(w)) return false;
            w++;
            a++;
        } else {
            if (c == '0') return false;

            int jump = 0;
            while (a < abbr.length() && Character.isDigit(abbr.charAt(a))) {
                jump = jump * 10 + (abbr.charAt(a) - '0');
                a++;
            }

            if (w + jump > word.length()) return false;
            w += jump;
        }
    }

    return w == word.length() && a == abbr.length();
}
```

### Time Complexity

**O(n + m)** — same reasoning as above: each pointer only moves forward, bounded by the length of its own string.

### Space Complexity

**O(1)** — only a few scalar variables.

### Algorithm Trace

Representative example: `word = "international"`, `abbr = "i9l"` → expected `false` (demonstrates the letter-mismatch-after-jump failure mode, distinct from the overshoot check).

```
Step: w=0, a=0 → compare 'i' vs 'i' → match, advance both

word: i n t e r n a t i o n a l
      ^
abbr: i 9 l
      ^
```

```
Step: w=1, a=1 → abbr[a] = '9' is a digit → parse number "9" → jump=9
      check 1 + 9 = 10 <= 13 ✓ (no overshoot!) → w jumps from 1 to 10, a advances to 2

word: i n t e r n a t i o n a l
        ^                       (before jump)
abbr: i 9 l
        ^
```

```
Step: w=10, a=2 → compare 'l' (abbr[2]) vs 'n' (word[10]) → mismatch → return false

word: i n t e r n a t i o n a l
                          ^
abbr: i 9 l
          ^
```

The abbreviation `"i9l"` claims there are 9 characters between the first `i` and the last `l`, but there are actually 11 (`"nternationa"`) — the mismatch surfaces here as a letter comparison failure rather than a length overshoot, which is why both checks (`w + jump > word.length()` *and* the final letter comparison) are necessary.

---

## 3. Alternative Approaches

### 3a. Recursive / Backtracking Two Pointers

Same matching rule as above, but expressed as a recursion that advances both indices and calls itself, instead of an explicit `while` loop.

```java
public boolean validWordAbbreviation(String word, String abbr) {
    return helper(word, abbr, 0, 0);
}

private boolean helper(String word, String abbr, int w, int a) {
    if (w == word.length() && a == abbr.length()) return true;
    if (w > word.length() || a >= abbr.length()) return false;

    char c = abbr.charAt(a);

    if (Character.isLetter(c)) {
        if (w >= word.length() || c != word.charAt(w)) return false;
        return helper(word, abbr, w + 1, a + 1);
    }

    if (c == '0') return false;

    int jump = 0;
    int next = a;
    while (next < abbr.length() && Character.isDigit(abbr.charAt(next))) {
        jump = jump * 10 + (abbr.charAt(next) - '0');
        next++;
    }

    return helper(word, abbr, w + jump, next);
}
```

**Time complexity:** O(n + m), identical work to the iterative version — there's no branching, so exactly one recursive call is made per letter/digit-group.

**Space complexity:** O(n + m) in the worst case for the recursion call stack (e.g. `abbr` made entirely of single letters), versus O(1) for the iterative version.

**When acceptable:** Fine under interview time pressure if the iterative form doesn't come to mind immediately, or when the interviewer explicitly wants to see a recursive formulation — but call out the O(n) stack space cost as a known tradeoff versus the loop version.

**Algorithm trace** (call stack table), `word = "apple"`, `abbr = "a3e"` → expected `true`:

| Depth | Call | Returns |
|---|---|---|
| 0 | helper(w=0, a=0) — 'a'=='a' | helper(w=1, a=1) |
| 1 | helper(w=1, a=1) — '3' digit, jump=3 | helper(w=4, a=2) |
| 2 | helper(w=4, a=2) — 'e'=='e' | helper(w=5, a=3) |
| 3 | helper(w=5, a=3) — w==word.length() && a==abbr.length() | `true` |
→ unwinds back up to `true`

### 3b. Convert `abbr` to a Regex and Match

Build a regex pattern from `abbr` by turning every digit-group into `.{n}` (n wildcard characters) and leaving letters as literals, then test it against `word` with `String.matches`.

```java
public boolean validWordAbbreviation(String word, String abbr) {
    StringBuilder pattern = new StringBuilder();
    int a = 0;

    while (a < abbr.length()) {
        char c = abbr.charAt(a);

        if (Character.isLetter(c)) {
            pattern.append(c);
            a++;
        } else {
            if (c == '0') return false;

            int start = a;
            while (a < abbr.length() && Character.isDigit(abbr.charAt(a))) a++;

            pattern.append(".{").append(abbr, start, a).append("}");
        }
    }

    return word.matches(pattern.toString());
}
```

**Time complexity:** O(n + m) to build the pattern, but regex compilation and matching add real constant-factor overhead beyond that — matching itself is still linear for this simple pattern shape, but the engine setup cost is non-trivial compared to direct character comparison.

**Space complexity:** O(m) for the `StringBuilder` pattern plus whatever internal structures `Pattern.compile` allocates.

**When acceptable:** A neat one-liner-ish trick to show pattern-building creativity, but wasteful for a problem this simple — regex machinery (compilation, backtracking engine) is overkill for what's fundamentally a linear character scan. Not recommended as a primary interview answer.

**Algorithm trace** (step table — this is a straight-line iterative build, not a two-pointer scan into the same array), `abbr = "imp4n5n"` → pattern built:

| a (start) | abbr[a] | action | pattern so far |
|---|---|---|---|
| 0 | `i` | literal | `i` |
| 1 | `m` | literal | `im` |
| 2 | `p` | literal | `imp` |
| 3 | `4` | digit-group "4" | `imp.{4}` |
| 4 | `n` | literal | `imp.{4}n` |
| 5 | `5` | digit-group "5" | `imp.{4}n.{5}` |
| 6 | `n` | literal | `imp.{4}n.{5}n` |
→ final pattern: `imp.{4}n.{5}n`, then `"implementation".matches("imp.{4}n.{5}n")` → `true`
