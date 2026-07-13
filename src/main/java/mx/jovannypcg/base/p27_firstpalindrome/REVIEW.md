| | |
|---|---|
| **Solved on** | 2026-07-13 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** The implementation is correct. It iterates through `words` in order and returns the first one for which `isPalindrome` returns true, falling back to `""` if none match. `isPalindrome` uses the classic two-pointer technique — walking from both ends toward the center — which correctly handles even-length, odd-length, single-character (loop body never runs, returns `true`), and (defensively) `null`/blank strings.

**Code quality:** Clean and readable. Method names are descriptive, the early-return structure avoids nested conditionals, and the `null`/empty guards are reasonable defensive touches even though the constraints (`1 <= words[i].length <= 100`, lowercase letters only) guarantee they won't be exercised by valid input.

**Time complexity:** O(n · m), where `n` is the number of words and `m` is the max word length. In the worst case (no palindrome, or the palindrome is last), every character of every word is compared once.

**Space complexity:** O(1) additional space — only the two pointers are allocated per call; no auxiliary data structures are used.

**Algorithm trace** (Two Pointers — Annotated array)

Input: `words = ["abc", "car", "ada", "racecar", "cool"]`

Word `"abc"` — not a palindrome:
```
[a, b, c]
 L     R

L=0 R=2  'a' != 'c' → return false
```

Word `"car"` — not a palindrome:
```
[c, a, r]
 L     R

L=0 R=2  'c' != 'r' → return false
```

Word `"ada"` — palindrome, first match:
```
[a, d, a]
 L     R

L=0 R=2  'a' == 'a' → advance both
[a, d, a]
    L
    R

L=1 R=1  loop ends (L < R is false) → return true
```
→ `firstPalindrome` returns `"ada"` (loop stops here; `"racecar"` is never checked)

## 2. Optimal Approach

This *is* the optimal approach: a single left-to-right scan over `words`, short-circuiting on the first palindrome, with an O(m) two-pointer palindrome check per word. There's no algorithmic improvement available — every word potentially needs to be inspected, and every character of a word potentially needs to be compared once.

**Time complexity:** O(n · m) — same reasoning as above; this is the theoretical lower bound since a non-palindromic word can't be identified as such without inspecting the mismatching pair of characters.

**Space complexity:** O(1) — two pointers, no extra storage.

```java
public String firstPalindrome(String[] words) {
    for (String word : words) {
        if (isPalindrome(word)) {
            return word;
        }
    }

    return "";
}

private boolean isPalindrome(String word) {
    int left = 0;
    int right = word.length() - 1;

    while (left < right) {
        if (word.charAt(left) != word.charAt(right)) {
            return false;
        }

        left++;
        right--;
    }

    return true;
}
```

**Algorithm trace** (Two Pointers — Annotated array)

Input: `words = ["notapalindrome", "racecar"]`

Word `"notapalindrome"` (14 chars) — mismatch found early:
```
[n, o, t, a, p, a, l, i, n, d, r, o, m, e]
 L                                      R

L=0 R=13  'n' != 'e' → return false
```

Word `"racecar"` — palindrome:
```
[r, a, c, e, c, a, r]
 L                 R

L=0 R=6  'r' == 'r' → advance both
[r, a, c, e, c, a, r]
    L           R

L=1 R=5  'a' == 'a' → advance both
[r, a, c, e, c, a, r]
       L     R

L=2 R=4  'c' == 'c' → advance both
[r, a, c, e, c, a, r]
          L,R

L=3 R=3  loop ends (L < R is false) → return true
```
→ `firstPalindrome` returns `"racecar"`

## 3. Alternative Approaches

**Approach A — Reverse and compare (`StringBuilder.reverse()` / `new StringBuilder(word).reverse().toString().equals(word)`)**

Instead of a manual two-pointer scan, reverse each candidate word and compare it to the original.

- **Time complexity:** O(n · m) — building the reversed string and comparing it is still linear in word length, same asymptotic bound as the two-pointer version.
- **Space complexity:** O(m) — each reversal allocates a new string/StringBuilder, unlike the O(1) two-pointer check.
- **When acceptable:** Fine under interview time pressure since it's a one-liner and easy to get right quickly; the extra O(m) space per word is negligible given `words[i].length <= 100`.

**Algorithm trace** (Two Pointers — Annotated array, showing the comparison rather than an internal scan)

Input: `words = ["def", "ghi"]`

Word `"def"` reversed → `"fed"`:
```
original: [d, e, f]
reversed: [f, e, d]
"def" vs "fed" → not equal → return false
```

Word `"ghi"` reversed → `"ihg"`:
```
original: [g, h, i]
reversed: [i, h, g]
"ghi" vs "ihg" → not equal → return false
```
→ no palindrome found, loop ends → `firstPalindrome` returns `""`

**Approach B — Recursive palindrome check**

Replace the iterative two-pointer loop with a recursive helper `isPalindrome(word, left, right)` that recurses inward until `left >= right`.

- **Time complexity:** O(n · m) — same number of character comparisons as the iterative version.
- **Space complexity:** O(m) — each recursive call adds a stack frame, so the call stack depth is proportional to half the word length, versus O(1) for the iterative loop.
- **When acceptable:** Reasonable if asked to demonstrate recursion explicitly, but with `words[i].length` up to 100 the recursion depth (≤ 50) is trivial and won't risk a stack overflow — still, the iterative version is preferable in production code for its O(1) space.

**Algorithm trace** (Recursion — Call stack table)

Input: word = `"aba"`

| Depth | Call | Returns |
|---|---|---|
| 0 | isPalindrome("aba", 0, 2) | 'a'=='a' → isPalindrome("aba", 1, 1) |
| 1 | isPalindrome("aba", 1, 1) | left >= right → true |
→ `isPalindrome("aba", 0, 2) = true`
