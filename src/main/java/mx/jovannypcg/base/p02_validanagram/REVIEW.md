# Valid Anagram — Review

| | |
|---|---|
| **Solved on** | 2026-06-09 |
| **DSA Category** | Arrays & Hashing |

---

## 1. Your Solution Assessment

You implemented three approaches. The active `isAnagram` method delegates to `arrayCombined`, which is the optimal solution. Both `a` and `b` are also correct.

### Approach A — Two fixed-size arrays (`a`)

**Correctness:** Correct. Differing-length strings naturally produce unequal arrays. The `null`/`isBlank` guard in `getCharCount` is unnecessary given the constraints (`1 <= s.length, t.length`), but harmless.

**Code quality:** Clean. `ALPHABET_SIZE` as a named constant is a good touch. `idx` is slightly non-standard — plain `i` is the Java convention for loop indices.

**Time:** O(n) — one pass through each string.
**Space:** O(1) — two fixed arrays of size 26.

**Algorithm trace** — Input: `s = "rat"`, `t = "car"`

| i | s[i] | t[i] | countS | countT |
|---|------|------|--------|--------|
| 0 | r | c | [r:1] | [c:1] |
| 1 | a | a | [r:1, a:1] | [c:1, a:1] |
| 2 | t | r | [r:1, a:1, t:1] | [c:1, a:1, r:1] |

→ `Arrays.equals([a:1,r:1,t:1], [a:1,c:1,r:1])` = **false**

---

### Approach B — HashMap (`b`)

**Correctness:** Correct. `HashMap.equals()` compares both keys and values, so it correctly identifies when character frequencies match.

**Code quality:** Clean. `getOrDefault` is idiomatic Java. However, boxing `char` → `Character` and `int` → `Integer` on every insertion adds overhead compared to the array approach.

**Time:** O(n) — one pass through each string.
**Space:** O(k) — k distinct characters, bounded at 26 for lowercase English → effectively O(1).

**Algorithm trace** — Input: `s = "rat"`, `t = "car"`

| i | s[i] | t[i] | countS | countT |
|---|------|------|--------|--------|
| 0 | r | c | {r:1} | {c:1} |
| 1 | a | a | {r:1, a:1} | {c:1, a:1} |
| 2 | t | r | {r:1, a:1, t:1} | {c:1, a:1, r:1} |

→ `{r:1,a:1,t:1}.equals({c:1,a:1,r:1})` = **false**

---

## 2. Optimal Approach

**Single-pass combined array (`arrayCombined`)** — your active implementation. Uses one array instead of two by incrementing for `s` and decrementing for `t`. If any count is non-zero at the end, the strings are not anagrams.

**Time:** O(n) — single pass through both strings simultaneously.
**Space:** O(1) — one array of size 26.

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

**Algorithm trace** — Input: `s = "rat"`, `t = "car"`

Length check: 3 == 3 ✓

| i | s[i] | count[s[i]-'a']++ | t[i] | count[t[i]-'a']-- | count (non-zero only) |
|---|------|-------------------|------|-------------------|----------------------|
| 0 | r | count[r]=1 | c | count[c]=-1 | {r:1, c:-1} |
| 1 | a | count[a]=1 | a | count[a]=0 | {r:1, c:-1} |
| 2 | t | count[t]=1 | r | count[r]=0 | {c:-1, t:1} |

Final check: count[c]=-1 ≠ 0 → return **false**

---

## 3. Alternative Approaches

### Sort and compare

Sort both strings as char arrays and check equality. Two anagrams, when sorted, produce identical strings.

- **Time:** O(n log n) — dominated by sorting.
- **Space:** O(n) — `toCharArray()` allocates new arrays of length n.
- **When acceptable:** Under interview time pressure when you can't recall the counting approach, or when input size is small enough that O(n log n) doesn't matter.

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

**Algorithm trace** — Input: `s = "rat"`, `t = "car"`

| Step | Action | Result |
|------|--------|--------|
| Length check | 3 == 3 | ✓ |
| Sort "rat" | toCharArray → [r,a,t] → sort | [a,r,t] |
| Sort "car" | toCharArray → [c,a,r] → sort | [a,c,r] |
| Compare | [a,r,t] == [a,c,r]? | **false** |

→ return `false`
