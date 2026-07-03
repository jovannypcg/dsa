# Tries

## Pattern
A prefix tree where each node represents one character. Enables O(m) insert and search (m = word length) and efficient prefix-based operations that a hash map cannot do easily.

## How to Recognize
- "Search for words with a given prefix"
- "Autocomplete / suggest completions"
- "Word dictionary with wildcard matching"
- "Longest word / common prefix"
- Multiple string lookups where shared prefixes can be reused

## Approach
1. Each `TrieNode` holds an array (or map) of 26 children and an `isEnd` flag.
2. **Insert**: walk character by character, creating nodes as needed, mark `isEnd` at last character.
3. **Search**: walk character by character; return false if any node is missing, check `isEnd` at the end.
4. **StartsWith**: same as search but don't check `isEnd`.

## Template

```java
class TrieNode {
    TrieNode[] children = new TrieNode[26];
    boolean isEnd = false;
}

class Trie {
    private final TrieNode root = new TrieNode();

    public void insert(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) node.children[i] = new TrieNode();
            node = node.children[i];
        }
        node.isEnd = true;
    }

    public boolean search(String word) {
        TrieNode node = root;
        for (char c : word.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) return false;
            node = node.children[i];
        }
        return node.isEnd;
    }

    public boolean startsWith(String prefix) {
        TrieNode node = root;
        for (char c : prefix.toCharArray()) {
            int i = c - 'a';
            if (node.children[i] == null) return false;
            node = node.children[i];
        }
        return true;
    }
}
```

**Wildcard search (`.` matches any character):**
```java
public boolean searchWithWildcard(String word) {
    return dfs(word, 0, root);
}

private boolean dfs(String word, int idx, TrieNode node) {
    if (idx == word.length()) return node.isEnd;

    char c = word.charAt(idx);

    if (c == '.') {
        for (TrieNode child : node.children) {
            if (child != null && dfs(word, idx + 1, child)) return true;
        }
        return false;
    }

    TrieNode next = node.children[c - 'a'];
    return next != null && dfs(word, idx + 1, next);
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Implement Trie (Prefix Tree) | [LeetCode #208](https://leetcode.com/problems/implement-trie-prefix-tree/) |
| 2 | Design Add and Search Words Data Structure | [LeetCode #211](https://leetcode.com/problems/design-add-and-search-words-data-structure/) |
| 3 | Word Search II | [LeetCode #212](https://leetcode.com/problems/word-search-ii/) |
| 4 | Replace Words | [LeetCode #648](https://leetcode.com/problems/replace-words/) |
| 5 | Map Sum Pairs | [LeetCode #677](https://leetcode.com/problems/map-sum-pairs/) |
| 6 | Longest Word in Dictionary | [LeetCode #720](https://leetcode.com/problems/longest-word-in-dictionary/) |
| 7 | Index Pairs of a String | [LeetCode #1065](https://leetcode.com/problems/index-pairs-of-a-string/) |
| 8 | Search Suggestions System | [LeetCode #1268](https://leetcode.com/problems/search-suggestions-system/) |
| 9 | Maximum XOR of Two Numbers in an Array | [LeetCode #421](https://leetcode.com/problems/maximum-xor-of-two-numbers-in-an-array/) |
| 10 | Palindrome Pairs | [LeetCode #336](https://leetcode.com/problems/palindrome-pairs/) |
| 11 | Word Squares | [LeetCode #425](https://leetcode.com/problems/word-squares/) |
| 12 | Stream of Characters | [LeetCode #1032](https://leetcode.com/problems/stream-of-characters/) |
| 13 | Concatenated Words | [LeetCode #472](https://leetcode.com/problems/concatenated-words/) |
| 14 | Implement Magic Dictionary | [LeetCode #676](https://leetcode.com/problems/implement-magic-dictionary/) |
| 15 | Prefix and Suffix Search | [LeetCode #745](https://leetcode.com/problems/prefix-and-suffix-search/) |
