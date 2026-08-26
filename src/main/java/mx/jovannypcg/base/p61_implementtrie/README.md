# Implement Trie (Prefix Tree)

**Date added:** 2026-08-25

## Problem Description

A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently store and retrieve keys in a dataset of strings. There are various applications of this data structure, such as autocomplete and spellchecker.

Implement the `Trie` class:

- `Trie()` Initializes the trie object.
- `void insert(String word)` Inserts the string `word` into the trie.
- `boolean search(String word)` Returns true if the string `word` is in the trie (i.e., was inserted before), and false otherwise.
- `boolean startsWith(String prefix)` Returns true if there is a previously inserted string `word` that has the prefix `prefix`, and false otherwise.

**Source:** https://leetcode.com/problems/implement-trie-prefix-tree

## Examples

**Example 1**
```
Input
["Trie", "insert", "search", "search", "startsWith", "insert", "search"]
[[], ["apple"], ["apple"], ["app"], ["app"], ["app"], ["app"]]
Output
[null, null, true, false, true, null, true]
```
Explanation: `trie.insert("apple")` stores "apple". `trie.search("apple")` returns true because "apple" was inserted. `trie.search("app")` returns false because "app" itself was never inserted, only "apple". `trie.startsWith("app")` returns true because "apple" starts with "app". `trie.insert("app")` now stores "app" too. `trie.search("app")` returns true because "app" was just inserted.

**Example 2**
```
Input: insert("a"); search("a")
Output: true
```
Explanation: A single-character word is inserted and then found by an exact search.

**Example 3**
```
Input: insert("a"); search("aa")
Output: false
```
Explanation: "aa" was never inserted, even though it starts with "a", so search returns false.

**Example 4**
```
Input: insert("a"); startsWith("aa")
Output: false
```
Explanation: No inserted word starts with the prefix "aa", so startsWith returns false.

**Example 5**
```
Input: startsWith("")
Output: true
```
Explanation: Every inserted word (or the trie itself, even if empty) has the empty string as a prefix, so startsWith("") returns true.

## Constraints

- `1 <= word.length, prefix.length <= 2000`
- `word` and `prefix` consist only of lowercase English letters.
- At most `3 * 10^4` calls in total will be made to `insert`, `search`, and `startsWith`.

## Hints

1. Think about what each node in the tree needs to represent — a single character, and links to the characters that can follow it.
2. A node needs a way to reach its children for each of the 26 possible lowercase letters, and a way to mark "a word ends here."
3. For `insert`, walk the trie character by character, creating new nodes only when a path doesn't already exist.
4. `search` and `startsWith` share almost identical traversal logic — the only difference is what they check once the traversal finishes.
5. `search` requires the final node to be marked as an end-of-word; `startsWith` only requires that the traversal completed without hitting a missing character.
