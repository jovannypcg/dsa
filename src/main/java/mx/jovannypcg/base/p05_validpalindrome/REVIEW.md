# Valid Palindrome — Review

| | |
|---|---|
| **Solved on** | 2026-06-11 |
| **DSA Category** | Two Pointers |

---

## 1. Your Solution Assessment

**Correctness:** The solution handles all cases correctly. The key fix you applied — swapping the bounds check before `charAt` in both inner loops — is exactly right and prevents the `StringIndexOutOfBoundsException` for strings that are entirely non-alphanumeric (e.g., `"..."`). The `left > right` guard after the skipping loops is a good defensive check that handles strings where all characters are non-alphanumeric and the pointers cross mid-skip. The early return for `null` and blank strings is also correct.

**Code quality:** Clean and readable. Variable names (`left`, `right`, `cLeft`, `cRight`) are clear. The working notes in the block comment are harmless during practice. The debug `System.out.println` has been removed, which is good.

**Time complexity: O(n)** — each character is visited at most once by either pointer.

**Space complexity: O(1)** — only a fixed number of integer variables and characters, regardless of input size.

**Algorithm trace** — Input: `s = "race a car"` (indices 0–9)

```
Initial:   r  a  c  e  ' '  a  ' '  c  a  r
           L                             R
           0  1  2  3   4   5   6   7  8  9

Step 1: charAt(L=0)='r' alnum, charAt(R=9)='r' alnum
        toLowerCase: 'r' == 'r' ✓ → L=1, R=8

Step 2: charAt(L=1)='a' alnum, charAt(R=8)='a' alnum
        'a' == 'a' ✓ → L=2, R=7

Step 3: charAt(L=2)='c' alnum, charAt(R=7)='c' alnum
        'c' == 'c' ✓ → L=3, R=6

Step 4: charAt(L=3)='e' alnum, charAt(R=6)=' ' NOT alnum → skip R: R=5

Step 5: charAt(L=3)='e' alnum, charAt(R=5)='a' alnum
        'e' != 'a' → return false
```

→ return `false`

---

## 2. Optimal Approach

The two-pointer approach you implemented is the optimal solution. The clean version uses `left < right` as the inner-loop guard, which makes the post-skip `left > right` check unnecessary.

**Strategy:** Place one pointer at each end. Skip non-alphanumeric characters on both sides, then compare case-insensitively. If any pair mismatches, return `false`; if the pointers meet, return `true`.

**Time: O(n)** — single linear pass.
**Space: O(1)** — no extra storage.

```java
public boolean isPalindrome(String s) {
    int left = 0;
    int right = s.length() - 1;

    while (left < right) {
        while (left < right && !Character.isLetterOrDigit(s.charAt(left))) left++;
        while (left < right && !Character.isLetterOrDigit(s.charAt(right))) right--;

        if (Character.toLowerCase(s.charAt(left)) != Character.toLowerCase(s.charAt(right))) {
            return false;
        }

        left++;
        right--;
    }

    return true;
}
```

**Algorithm trace** — Input: `s = "race a car"`

```
Initial:   r  a  c  e  ' '  a  ' '  c  a  r
           L                             R

Step 1: 'r' alnum, 'r' alnum. 'r'=='r' ✓ → L=1, R=8
Step 2: 'a' alnum, 'a' alnum. 'a'=='a' ✓ → L=2, R=7
Step 3: 'c' alnum, 'c' alnum. 'c'=='c' ✓ → L=3, R=6
Step 4: 'e' alnum, ' ' NOT alnum → inner while: R=5
        'e' alnum, 'a' alnum. 'e'!='a' → return false
```

→ return `false`

---

## 3. Alternative Approaches

### Clean-and-reverse

Filter the string to only lowercase alphanumeric characters into a new string, then compare it to its reverse.

- **Time: O(n)** — one pass to filter, one to reverse.
- **Space: O(n)** — stores the filtered string and its reverse.
- **When acceptable:** Short strings or under interview time pressure when pointer manipulation feels risky.

```java
public boolean isPalindrome(String s) {
    String cleaned = s.toLowerCase().replaceAll("[^a-z0-9]", "");
    String reversed = new StringBuilder(cleaned).reverse().toString();
    return cleaned.equals(reversed);
}
```

**Algorithm trace** — Input: `s = "race a car"`

| Step | Action | Value |
|------|--------|-------|
| toLowerCase | | "race a car" |
| replaceAll non-alnum | | "raceacar" |
| reverse | | "racaecar" |
| equals | "raceacar" == "racaecar"? | **false** |

→ return `false`

---

### Clean-and-two-pointer (on filtered string)

Same filtering step, but then use two pointers on the cleaned string instead of reversing it — saves the second string allocation.

- **Time: O(n)**
- **Space: O(n)** — still allocates the filtered string, but not a second one.
- **When acceptable:** Cleaner to reason about than raw-string two pointers; still O(n) space.

```java
public boolean isPalindrome(String s) {
    String cleaned = s.toLowerCase().replaceAll("[^a-z0-9]", "");
    int left = 0, right = cleaned.length() - 1;
    while (left < right) {
        if (cleaned.charAt(left++) != cleaned.charAt(right--)) return false;
    }
    return true;
}
```

**Algorithm trace** — Input: `s = "race a car"` → cleaned: `"raceacar"`

```
Initial:  r  a  c  e  a  c  a  r
          L                    R

Step 1: 'r' == 'r' ✓ → L=1, R=6
Step 2: 'a' == 'a' ✓ → L=2, R=5
Step 3: 'c' == 'c' ✓ → L=3, R=4
Step 4: 'e' != 'a' → return false
```

→ return `false`
