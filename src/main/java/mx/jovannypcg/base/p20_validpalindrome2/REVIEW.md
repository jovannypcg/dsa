| | |
|---|---|
| **Solved on** | 2026-07-03 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

### Correctness

Correct. The first (buggy) version only peeked at a single pair of characters after a skip and
committed to that branch permanently. This version fixes that by extracting an `isPalindrome`
helper and fully verifying each candidate branch — `str[left+1..right]` or `str[left..right-1]` —
to completion before deciding. It correctly handles the earlier counterexample
(`"baaba"` → `true`), the README examples, and all boundary cases in the test suite (single
character, two characters, all-identical strings, and the 10^5-character upper bound).

### Code Quality

- The `for (; left < right; left++, right--)` loop with a multi-clause update is valid and used
  consistently in both `validPalindrome` and `isPalindrome`, which keeps the two methods visually
  symmetric — reasonable, if slightly less common than a `while` loop for this shape of scan.
- `isPalindrome` has default (package-private) visibility but is only ever called from within
  `Solution` — it could be `private` to make that intent explicit.
- `if (str == null || str.isBlank())` is dead code given the constraints
  (`1 <= s.length <= 10^5`, lowercase letters only) — `str` can never be `null` or blank, so this
  branch can never execute. Harmless, but worth dropping.

### Time Complexity

**O(n)** — the main loop runs until the first mismatch (at most `n/2` iterations). At that point,
exactly one side of the `||` in the `return` statement executes first; due to short-circuit
evaluation, the second `isPalindrome` call only runs if the first returns `false`. Either way, the
total work across both calls is bounded by a single additional O(n) scan, so the whole algorithm
is linear.

### Space Complexity

**O(1)** — only the `left`/`right` index variables are used; no extra data structures are
allocated.

### Algorithm Trace

Input: `s = "baaba"`

```
b a a b a
L       R
s[L]='b' vs s[R]='a' → mismatch → try isPalindrome(s, 1, 4) first
```

`isPalindrome(s, 1, 4)` — checked fully, to completion:

```
b a a b a
  L     R
s[L]='a' vs s[R]='a' → match, advance both

b a a b a
    L R
s[L]='a' vs s[R]='b' → mismatch → isPalindrome(s, 1, 4) = false
```

Falls back to `isPalindrome(s, 0, 3)` — checked fully:

```
b a a b a
L     R
s[L]='b' vs s[R]='b' → match, advance both

b a a b a
  L R
s[L]='a' vs s[R]='a' → match, L and R cross → loop ends → isPalindrome(s, 0, 3) = true
```

→ `validPalindrome` returns `true` (delete index 4 → `"baab"`, a palindrome).

### Idiomatic Approach

The implementation is already close to idiomatic — the `isPalindrome` extraction is exactly the
right move, since it replaces manual pointer juggling with a fully-verifying, reusable helper. Two
small polish items:

```java
public boolean validPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
        }

        left++;
        right--;
    }

    return true;
}

private boolean isPalindrome(String s, int left, int right) {
    while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) {
            return false;
        }
    }

    return true;
}
```

- Dropped the dead `null`/`isBlank` guard (unreachable given the constraints).
- Marked `isPalindrome` `private`, since it's an implementation detail of this class.
- Swapped the `for (; cond; update)` loops for plain `while` loops — with no initializer clause in
  the `for`, a `while` communicates the same logic with less ceremony.

## 2. Optimal Approach

On the first mismatch between `left` and `right`, at most one character can be removed, so exactly
one of two substrings must already be a palindrome: `s[left+1..right]` (delete the left character)
or `s[left..right-1]` (delete the right character). Check both fully with a helper; if the string
never mismatches, it's already a palindrome and no deletion is needed. This is precisely the
approach implemented above — the user's solution *is* the optimal solution.

**Time Complexity: O(n)** — see explanation above; the outer scan plus at most one full inner
`isPalindrome` scan.

**Space Complexity: O(1)** — only pointer variables are used.

```java
public boolean validPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        if (s.charAt(left) != s.charAt(right)) {
            return isPalindrome(s, left + 1, right) || isPalindrome(s, left, right - 1);
        }

        left++;
        right--;
    }

    return true;
}

private boolean isPalindrome(String s, int left, int right) {
    while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) {
            return false;
        }
    }

    return true;
}
```

### Algorithm Trace

See the trace under **Your Solution Assessment** above — the submitted solution already
implements this exact approach, so the trace is identical.

## 3. Alternative Approaches

### Brute Force: Try Every Deletion

For each index `i`, build the string with `s.charAt(i)` removed and check whether the result is a
palindrome.

**Time Complexity: O(n²)** — n candidate strings, each requiring an O(n) build-and-check.

**Space Complexity: O(n)** — each candidate string is a new O(n)-sized allocation.

Acceptable only under tight time pressure in an interview, or for very small inputs — at
`n = 10^5` this is far too slow.

```java
public boolean validPalindrome(String s) {
    if (isPalindrome(s)) {
        return true;
    }

    for (int i = 0; i < s.length(); i++) {
        String candidate = s.substring(0, i) + s.substring(i + 1);

        if (isPalindrome(candidate)) {
            return true;
        }
    }

    return false;
}

private boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        if (s.charAt(left++) != s.charAt(right--)) {
            return false;
        }
    }

    return true;
}
```

#### Algorithm Trace

Input: `s = "baaba"`

| i (index removed) | candidate string | palindrome? |
|---|---|---|
| 0 | `aaba` | No |
| 1 | `baba` | No |
| 2 | `baba` | No |
| 3 | `baaa` | No |
| 4 | `baab` | **Yes** → return `true` |

### Dynamic Programming: Longest Common Subsequence with the Reverse

"Can delete at most one character to make `s` a palindrome" is equivalent to "the longest
palindromic subsequence of `s` has length ≥ `n - 1`", which is equivalent to "the longest common
subsequence of `s` and `reverse(s)` has length ≥ `n - 1`".

**Time Complexity: O(n²)** — filling an `n x n` DP table, one O(1) transition per cell.

**Space Complexity: O(n²)** — the full DP table (can be reduced to O(n) with a rolling row, but
still far more expensive than the two-pointer approach for no benefit).

A reasonable approach to mention if the two-pointer insight isn't found, but strictly worse in both
time and space — not viable at `n = 10^5` and unnecessary complexity for this problem.

```java
public boolean validPalindrome(String s) {
    String reversed = new StringBuilder(s).reverse().toString();
    int n = s.length();
    int[][] dp = new int[n + 1][n + 1];

    for (int i = 1; i <= n; i++) {
        for (int j = 1; j <= n; j++) {
            if (s.charAt(i - 1) == reversed.charAt(j - 1)) {
                dp[i][j] = dp[i - 1][j - 1] + 1;
            } else {
                dp[i][j] = Math.max(dp[i - 1][j], dp[i][j - 1]);
            }
        }
    }

    return dp[n][n] >= n - 1;
}
```

#### Algorithm Trace

Input: `s = "baaba"`, `reversed = "abaab"` — `dp[i][j]` = LCS length of `s[0..i)` and `reversed[0..j)`

|     | "" | a | b | a | a | b |
|---|---|---|---|---|---|---|
| "" | 0 | 0 | 0 | 0 | 0 | 0 |
| b | 0 | 0 | 1 | 1 | 1 | 1 |
| a | 0 | 1 | 1 | 2 | 2 | 2 |
| a | 0 | 1 | 1 | 2 | 3 | 3 |
| b | 0 | 1 | 2 | 2 | 3 | 4 |
| a | 0 | 1 | 2 | 3 | 4 | 4 |

→ `dp[5][5] = 4`, and `n - 1 = 4`, so `4 >= 4` → return `true`.
