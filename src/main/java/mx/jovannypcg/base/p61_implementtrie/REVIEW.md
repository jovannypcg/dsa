| | |
|---|---|
| **Solved on** | 2026-08-25 |
| **DSA Category** | Tries |

## 1. Your Solution Assessment

**Correctness:** Correct. All 9 tests pass, including the LeetCode example, shared-prefix words, non-overlapping words, repeated inserts, and the 2000-character boundary. The end-of-word marker (`'*'` stored as a key in the same `Map<Character, Node>` used for children) works safely here because the constraints guarantee `word`/`prefix` contain only lowercase English letters, so `'*'` can never collide with a real traversal character.

**Code quality:** Clear variable names (`current`, `alphabet`) and consistent structure across the three methods — `search` and `startsWith` share nearly identical traversal logic, which makes the code easy to follow. Two things worth reconsidering:
- Overloading the children map with a sentinel `'*'` key is a clever trick, but it's implicit — a reader has to notice that `'*'` is being used as an "end of word" flag rather than a real character. A dedicated `boolean isEndOfWord` field on `Node` documents the intent directly and removes the (currently harmless) assumption that `'*'` will never appear in real input.
- `current.alphabet.put('*', new Node())` allocates a full `Node` (with its own `HashMap`) just to act as a boolean flag. A `boolean` field would avoid that wasted allocation per word.

**Time complexity:** O(m) for `insert`, `search`, and `startsWith`, where m is the length of the input word/prefix — each does a single pass over the string with O(1) average-case `HashMap` operations per character.

**Space complexity:** O(T) where T is the total number of characters across all inserted words (worst case, no shared prefixes), plus one extra `Node` per inserted word for the `'*'` marker. Each `Node` also carries `HashMap` overhead (buckets, entries) rather than a fixed 26-slot array, which increases the constant factor per node.

**Algorithm trace** (Step table) — input: `insert("apple")`, `search("apple")`, `search("app")`, `startsWith("app")`, `insert("app")`, `search("app")`:

`insert("apple")`

| i | char | node before | in map? | node after |
|---|------|-------------|---------|------------|
| 0 | a | root | No | new node A |
| 1 | p | A | No | new node P1 |
| 2 | p | P1 | No | new node P2 |
| 3 | l | P2 | No | new node L |
| 4 | e | L | No | new node E |

After loop: `E.alphabet.put('*', new Node())` → E is now marked as end-of-word.

`search("apple")`

| i | char | current | in map? | move to |
|---|------|---------|---------|---------|
| 0 | a | root | Yes | A |
| 1 | p | A | Yes | P1 |
| 2 | p | P1 | Yes | P2 |
| 3 | l | P2 | Yes | L |
| 4 | e | L | Yes | E |

Final check: `E.alphabet.containsKey('*')` → **Yes** → return `true`

`search("app")`

| i | char | current | in map? | move to |
|---|------|---------|---------|---------|
| 0 | a | root | Yes | A |
| 1 | p | A | Yes | P1 |
| 2 | p | P1 | Yes | P2 |

Final check: `P2.alphabet.containsKey('*')` → **No** (P2's only child is `l`) → return `false`

`startsWith("app")` → same traversal as above, reaches P2 without a missing character → return `true`

`insert("app")` → traverses a → P1 → P2 reusing existing nodes (no new nodes created), then `P2.alphabet.put('*', new Node())`.

`search("app")` → traverses to P2, `P2.alphabet.containsKey('*')` → **Yes** → return `true`

## 2. Optimal Approach

The user's solution is already asymptotically optimal — O(m) per operation is the best possible, since you must inspect every character of the word/prefix at least once. The "canonical" version differs mainly in node representation, not algorithmic idea:

- Use a fixed-size `Node[26]` array (or `Node[]` indexed by `c - 'a'`) instead of a `HashMap<Character, Node>`. Since the alphabet is fixed and small (lowercase English letters), array indexing is O(1) with a much smaller constant factor than hashing, and avoids `HashMap` bucket/entry overhead.
- Use an explicit `boolean isEndOfWord` flag on `Node` instead of a sentinel map entry. This avoids allocating an extra `Node` per word and makes the end-of-word check self-documenting.

```java
class Trie {
    private static class Node {
        Node[] children = new Node[26];
        boolean isEndOfWord = false;
    }

    private final Node root = new Node();

    public void insert(String word) {
        Node current = root;
        for (char c : word.toCharArray()) {
            int idx = c - 'a';
            if (current.children[idx] == null) {
                current.children[idx] = new Node();
            }
            current = current.children[idx];
        }
        current.isEndOfWord = true;
    }

    public boolean search(String word) {
        Node node = traverse(word);
        return node != null && node.isEndOfWord;
    }

    public boolean startsWith(String prefix) {
        return traverse(prefix) != null;
    }

    private Node traverse(String s) {
        Node current = root;
        for (char c : s.toCharArray()) {
            int idx = c - 'a';
            if (current.children[idx] == null) {
                return null;
            }
            current = current.children[idx];
        }
        return current;
    }
}
```

**Time complexity:** O(m) per operation — identical to the user's solution, one pass over the string.

**Space complexity:** O(T) where T is total characters inserted, same as the user's solution, but each `Node` now costs a fixed 27-word array/flag instead of a `HashMap`, which is cheaper per node when the alphabet is small and mostly populated.

**Algorithm trace** (Step table) — same input as above, `insert("apple")` then `search("app")`:

| i | char | current | children[idx] exists? | move to |
|---|------|---------|------------------------|---------|
| 0 | a | root | No → create | A |
| 1 | p | A | No → create | P1 |
| 2 | p | P1 | No → create | P2 |
| 3 | l | P2 | No → create | L |
| 4 | e | L | No → create | E |

After loop: `E.isEndOfWord = true`

`search("app")`: traverse a → P1 → P2 (all exist) → check `P2.isEndOfWord` → **false** → return `false`

## 3. Alternative Approaches

**Approach A — HashSet of words + on-the-fly prefix scan (brute force)**
Store all inserted words in a `HashSet<String>`. `search` is an O(1) set lookup. `startsWith(prefix)` requires scanning every stored word and checking `word.startsWith(prefix)`.
- **Time:** `insert`/`search` O(m) (hashing) or O(1) amortized; `startsWith` O(N·m) where N is the number of stored words and m is the average word length, since every word must be checked.
- **Space:** O(T), just the raw strings.
- **When acceptable:** Only if `startsWith` is called rarely relative to `insert`/`search`, or N is small. With the problem's stated limit of 3×10⁴ total calls, this can degrade badly if `startsWith` dominates the call mix — not recommended as a general solution, but fine as a first pass under interview time pressure.
- **Algorithm trace** (Step table) — `startsWith("app")` against `{"apple", "banana"}`:

| word checked | starts with "app"? |
|---|---|
| apple | Yes → return true (short-circuits) |
| banana | not reached |

**Approach B — Precompute all prefixes into a HashSet at insert time**
Alongside the word set, also insert every prefix of each word into a second `HashSet<String>`. Then `startsWith` becomes an O(1) lookup too.
- **Time:** `insert` becomes O(m²) in the worst case (generating and hashing every prefix substring costs O(m) per prefix, m prefixes); `search`/`startsWith` O(1) amortized.
- **Space:** O(N·m²) worst case (all words share no structure, e.g. `T`, `TE`, `TES`... duplicated per word), far worse than the trie's O(T).
- **When acceptable:** If `insert` calls are rare and `search`/`startsWith` dominate and must be as fast as possible — but the trie already gives O(1)-per-character lookups without the quadratic blowup, so this is rarely worth it in practice.
- **Algorithm trace** (Step table) — `insert("app")` generating prefixes:

| i | prefix generated | added to prefix set |
|---|---|---|
| 0 | "a" | Yes |
| 1 | "ap" | Yes |
| 2 | "app" | Yes |

**Approach C — Sorted structure (TreeSet) with ceiling lookup**
Store words in a `TreeSet<String>`. `search` is `contains`. `startsWith(prefix)` uses `ceiling(prefix)` to find the smallest word ≥ `prefix` lexicographically, then checks whether it actually starts with `prefix`.
- **Time:** `insert`/`search`/`startsWith` O(log N · m) — each is a balanced-tree operation with O(m) string comparison cost per node visited.
- **Space:** O(T).
- **When acceptable:** If the application also needs ordered operations (e.g., "words between X and Y", autocomplete sorted output) that a trie doesn't directly give for free — otherwise the trie's O(m) per operation beats this O(log N · m).
- **Algorithm trace** (Step table) — `startsWith("app")` on `TreeSet{"apple", "banana"}`:

| step | operation | result |
|---|---|---|
| 1 | `ceiling("app")` | "apple" |
| 2 | `"apple".startsWith("app")` | true → return true |
