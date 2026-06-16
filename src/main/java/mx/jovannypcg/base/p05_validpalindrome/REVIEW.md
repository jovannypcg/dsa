# Valid Palindrome — Review

| | |
|---|---|
| **Solved on** | 2026-06-11 |
| **DSA Category** | Two Pointers |

---

## 1. Your Solution Assessment

**Correctness:** The solution handles all cases correctly. The key fix you applied — swapping the bounds check before `charAt` in both inner loops — is exactly right and prevents the `StringIndexOutOfBoundsException` for strings that are entirely non-alphanumeric (e.g., `"..."`). The `left > right` guard after the skipping loops is a good defensive check that handles strings where all characters are non-alphanumeric and the pointers cross mid-skip. The early return for `null` and blank strings is also correct.

**Code quality:** Clean and readable. Variable names (`left`, `right`, `cLeft`, `cRight`) are clear. The working notes in the block comment are harmless — in a real PR you'd strip those, but they show good thinking. The `System.out.println` debug line from an earlier version has been removed, which is good.

**Time complexity: O(n)** — Each character is visited at most once by either pointer; the two pointers together traverse the string linearly.

**Space complexity: O(1)** — Only a fixed number of integer variables and characters are allocated, regardless of input size. No auxiliary string is built.

---

## 2. Optimal Approach

The two-pointer approach you implemented *is* the optimal solution. Here is a clean reference version:

**Strategy:** Place one pointer at each end of the string. Skip non-alphanumeric characters on both sides, then compare the characters at the two pointers in a case-insensitive way. If any pair mismatches, return `false`; if the pointers meet, return `true`.

- **Time complexity: O(n)** — single linear pass.
- **Space complexity: O(1)** — no extra storage.

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

Note: using `left < right` as the guard in the inner loops (instead of `left < n` / `right >= 0`) makes the `left > right` post-skip check unnecessary — the outer loop condition already covers it.

---

## 3. Alternative Approaches

### Clean-and-reverse

Filter the string to only lowercase alphanumeric characters into a new string, then compare it to its reverse.

- **Time complexity: O(n)** — one pass to filter, one to reverse.
- **Space complexity: O(n)** — stores the filtered string and its reverse.
- **When acceptable:** Short strings, quick prototype, or under interview time pressure when pointer manipulation feels risky.

```java
public boolean isPalindrome(String s) {
    String cleaned = s.toLowerCase().replaceAll("[^a-z0-9]", "");
    String reversed = new StringBuilder(cleaned).reverse().toString();
    return cleaned.equals(reversed);
}
```

### Clean-and-two-pointer (single filtered string)

Same filtering step, but then use two pointers on the cleaned string instead of reversing it — saves the extra string allocation.

- **Time complexity: O(n)**
- **Space complexity: O(n)** — still allocates the filtered string, but not a second one.
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
