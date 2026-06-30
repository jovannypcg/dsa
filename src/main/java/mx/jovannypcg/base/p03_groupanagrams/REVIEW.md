# Group Anagrams — Review

| | |
|---|---|
| **Solved on** | 2026-06-10 |
| **DSA Category** | Arrays & Hashing |

---

## 1. Your Solution Assessment

### Thought process comment
The trace-through comment at the top of the class is excellent. It shows you mapped out the key insight before coding: sorted string → canonical key → group. This is the right instinct — walking through a concrete example before writing code signals structured thinking in interviews.

### Correctness
Correct for all valid inputs. The `null` guard is a nice defensive touch, though the problem guarantees `strs.length >= 1`, so it will never trigger. The empty-string case (`""`) is handled naturally — sorting `""` returns `""`, which becomes a valid map key.

### Code quality
- The `sort` helper is a clean extraction — testable in isolation and keeps `groupAnagrams` readable.
- `putIfAbsent` + `get` is a two-lookup pattern. `computeIfAbsent` expresses the same intent in one call:
  ```java
  groups.computeIfAbsent(sorted, k -> new ArrayList<>()).add(current);
  ```
- `LinkedList` works, but `ArrayList` is the better default for lists that only need sequential `add` and iteration — better cache locality.

### Time complexity
**O(n · k log k)** — n iterations, each sorting a string of length up to k in O(k log k).

### Space complexity
**O(n · k)** — the map stores all original strings, so total character data is proportional to both n and k. (Not simply O(n) as noted in the original comment.)

**Algorithm trace** — Input: `strs = ["eat", "tea", "tan", "ate", "nat", "bat"]`

| s | sorted key | groups |
|---|------------|--------|
| "eat" | "aet" | {"aet": ["eat"]} |
| "tea" | "aet" | {"aet": ["eat","tea"]} |
| "tan" | "ant" | {"aet": ["eat","tea"], "ant": ["tan"]} |
| "ate" | "aet" | {"aet": ["eat","tea","ate"], "ant": ["tan"]} |
| "nat" | "ant" | {"aet": ["eat","tea","ate"], "ant": ["tan","nat"]} |
| "bat" | "abt" | {"aet": ["eat","tea","ate"], "ant": ["tan","nat"], "abt": ["bat"]} |

→ return `[["eat","tea","ate"], ["tan","nat"], ["bat"]]`

---

## 2. Optimal Approach

Your approach is already optimal for this problem. The sort-based key is the canonical and most interview-acceptable solution.

**Strategy:** Use the sorted string as a canonical key in a `HashMap`. All anagrams produce the same key when sorted, so iterating once and grouping by key solves the problem in a single pass.

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

**Algorithm trace** — Input: `strs = ["eat", "tea", "tan", "ate", "nat", "bat"]`

| s | sorted key | groups |
|---|------------|--------|
| "eat" | "aet" | {"aet": ["eat"]} |
| "tea" | "aet" | {"aet": ["eat","tea"]} |
| "tan" | "ant" | {"aet": ["eat","tea"], "ant": ["tan"]} |
| "ate" | "aet" | {"aet": ["eat","tea","ate"], "ant": ["tan"]} |
| "nat" | "ant" | {"aet": ["eat","tea","ate"], "ant": ["tan","nat"]} |
| "bat" | "abt" | {"aet": ["eat","tea","ate"], "ant": ["tan","nat"], "abt": ["bat"]} |

→ return `[["eat","tea","ate"], ["tan","nat"], ["bat"]]`

---

## 3. Alternative Approaches

### Character frequency array as key

Build a 26-element frequency array for each string and use it as the map key (encoded as a string like `"1#0#0#..."`).

- **Time:** O(n · k) — character counting is O(k), avoiding the O(k log k) sort.
- **Space:** O(n · k) — same as sorting approach.
- **When acceptable:** Preferable when strings are long (k is large) and the sort constant matters, or when the interviewer asks for a sub-O(k log k) solution.

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

**Algorithm trace** — Input: `strs = ["eat", "tea"]`

| s | count (non-zero) | key | groups |
|---|-----------------|-----|--------|
| "eat" | {a:1, e:1, t:1} | "1#0#0#1#...#1#..." | {"1#...": ["eat"]} |
| "tea" | {a:1, e:1, t:1} | same key | {"1#...": ["eat","tea"]} |

→ return `[["eat","tea"]]`

---

### Brute force — sort and compare all pairs

Compare every pair of strings: sort both and check equality. Group matches together.

- **Time:** O(n² · k log k) — comparing all pairs.
- **Space:** O(n) — output grouping only.
- **When acceptable:** Only for very small inputs (n < 20) or under extreme memory pressure. Never in an interview.

**Algorithm trace** — Input: `strs = ["eat", "tea", "bat"]`

| i | j | sort(strs[i]) | sort(strs[j]) | Same? |
|---|---|---------------|---------------|-------|
| 0 | 1 | "aet" | "aet" | Yes → group together |
| 0 | 2 | "aet" | "abt" | No |
| 1 | 2 | "aet" | "abt" | No |

→ groups: `[["eat","tea"], ["bat"]]`
