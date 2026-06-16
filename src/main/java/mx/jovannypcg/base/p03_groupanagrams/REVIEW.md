# Group Anagrams — Review

**Date solved:** 2026-06-10

---

## 1. DSA Category

**Arrays & Hashing**

---

## 2. Your Solution Assessment

### Thought process comment
The trace-through comment at the top of the class is excellent. It shows you mapped out the key insight before coding: sorted string → canonical key → group. This is the right instinct — walking through a concrete example with pencil-and-paper logic before writing code. Keep doing this in interviews; it signals structured thinking.

### Correctness
Correct for all valid inputs per the constraints. The `null` guard is a nice defensive touch, though the problem guarantees `strs.length >= 1`, so it will never trigger. The empty-string case (`""`) is handled naturally — `sort("")` returns `""`, which becomes a valid map key.

### Code quality
- The `sort` helper is a clean extraction — it's testable in isolation and keeps `groupAnagrams` readable.
- `putIfAbsent` + `get` is a two-lookup pattern. `computeIfAbsent` expresses the same intent in one call and is idiomatic Java 8+:
  ```java
  groups.computeIfAbsent(sorted, k -> new ArrayList<>()).add(current);
  ```
- `LinkedList` works, but `ArrayList` is the better default for lists that only need sequential `add` and iteration — it has better cache locality. `LinkedList` adds overhead (node allocation per element) without benefit here.
- The index-based `for` loop is fine but an enhanced for-each (`for (String current : strs)`) is more idiomatic when the index isn't needed.

### Time complexity
**O(n · k log k)** — correct and well-annotated. `n` iterations, each sorting a string of length up to `k` in O(k log k).

### Space complexity
**O(n · k)** — the comment says O(n), but the map stores the original strings (not just keys), so the total character data stored is proportional to both `n` (number of strings) and `k` (their lengths). O(n · k) is the precise answer.

---

## 3. Optimal Approach

Your approach is already optimal for this problem with the sorting strategy. There is one alternative that achieves a better asymptotic time bound (see below), but sorting is the canonical and most interview-acceptable solution.

**Strategy:** Use the sorted string as a canonical key in a HashMap. All anagrams produce the same key when sorted, so iterating once and grouping by key solves the problem in a single pass.

**Time:** O(n · k log k) — one sort per string.  
**Space:** O(n · k) — the map holds all n strings totalling up to n·k characters.

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();

    for (String s : strs) {
        char[] chars = s.toCharArray();
        Arrays.sort(chars);
        String key = new String(chars);
        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }

    return new ArrayList<>(groups.values());
}
```

---

## 4. Alternative Approaches

### Character frequency array as key
Instead of sorting, build a 26-element frequency array for each string and use it as the map key (encoded as a string like `"1#0#0#..."`).

- **Time:** O(n · k) — counting characters is O(k), avoiding the O(k log k) sort.
- **Space:** O(n · k) — same as sorting approach.
- **When acceptable:** Preferable when strings are long (k is large) and the constant factor of sorting matters, or when interviewer asks for a sub-O(k log k) solution.

```java
public List<List<String>> groupAnagrams(String[] strs) {
    Map<String, List<String>> groups = new HashMap<>();

    for (String s : strs) {
        int[] count = new int[26];
        for (char c : s.toCharArray()) count[c - 'a']++;

        StringBuilder sb = new StringBuilder();
        for (int n : count) sb.append(n).append('#');
        String key = sb.toString();

        groups.computeIfAbsent(key, k -> new ArrayList<>()).add(s);
    }

    return new ArrayList<>(groups.values());
}
```

### Brute force — sort and compare all pairs
Compare every pair of strings: sort both and check equality. Group matches together.

- **Time:** O(n² · k log k) — comparing all pairs.
- **Space:** O(n) — no map needed beyond the output grouping.
- **When acceptable:** Only for very small inputs (n < 20) or under extreme memory pressure. Never in an interview.
