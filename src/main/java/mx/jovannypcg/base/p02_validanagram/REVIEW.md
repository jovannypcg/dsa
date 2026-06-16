# Valid Anagram — Review

**Date solved:** 2026-06-09

---

## 1. DSA Category

**Arrays & Hashing**

---

## 2. Your Solution Assessment

You implemented two approaches, which is great for comparison. Both are correct.

### Approach A — Fixed-size array

**Correctness:** Correct for all cases. Even without an explicit length check, differing-length strings naturally produce unequal arrays (e.g., `"abc"` vs ``"ab"` will leave one slot non-zero). The `null`/`isBlank` guard in `getCharCount` is unnecessary given the constraints (`1 <= s.length, t.length`), but it doesn't cause harm.

**Code quality:** Clean and readable. `ALPHABET_SIZE` as a named constant is a good touch. The `idx` variable name is a bit non-standard — `i` is conventional for loop indices in Java; `idx` adds no extra clarity here.

**Time complexity:** O(n) — one pass through each string, where n = total character count across both strings.

**Space complexity:** O(1) — two arrays of fixed size 26, regardless of input length.

### Approach B — HashMap

**Correctness:** Correct. `HashMap.equals()` compares both keys and values, so it correctly identifies when character frequencies match.

**Code quality:** Clean. `getOrDefault` is idiomatic Java. However, `HashMap<Character, Integer>` involves boxing `char` → `Character` and `int` → `Integer` on every insertion, which adds overhead compared to the array approach.

**Time complexity:** O(n) — one pass through each string.

**Space complexity:** O(k) where k is the number of distinct characters. For this problem's constraints (lowercase English letters only), this is bounded at 26 — effectively O(1). For Unicode input, it would be truly O(k).

### Which is better here?

Approach A is the stronger solution for this problem. The constraints guarantee lowercase English letters, so the fixed alphabet size makes the array approach both faster in practice (no hashing, no boxing) and equally space-efficient.

Approach B is the better general-purpose solution if the input could include Unicode characters beyond 26 slots.

---

## 3. Optimal Approach

**Single-pass combined array** — same category as your approach A, but uses one array instead of two by incrementing for `s` and decrementing for `t`. If any count is non-zero at the end, the strings are not anagrams.

**Time complexity:** O(n) — single pass through both strings simultaneously.

**Space complexity:** O(1) — one array of size 26.

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;

    int[] count = new int[26];

    for (int i = 0; i < s.length(); i++) {
        count[s.charAt(i) - 'a']++;
        count[t.charAt(i) - 'a']--;
    }

    for (int c : count) {
        if (c != 0) return false;
    }

    return true;
}
```

The early length check is a meaningful optimization: it short-circuits before any array work for the common case of differing-length inputs.

---

## 4. Alternative Approaches

### Sort and compare

Sort both strings and check for equality. Two anagrams, when sorted, produce identical strings.

```java
public boolean isAnagram(String s, String t) {
    if (s.length() != t.length()) return false;

    char[] sArr = s.toCharArray();
    char[] tArr = t.toCharArray();
    Arrays.sort(sArr);
    Arrays.sort(tArr);

    return Arrays.equals(sArr, tArr);
}
```

**Time complexity:** O(n log n) — dominated by sorting.  
**Space complexity:** O(n) — `toCharArray()` allocates new arrays of length n.  
**When acceptable:** Under interview time pressure when you can't recall the counting approach, or when the input size is small enough that O(n log n) doesn't matter.
