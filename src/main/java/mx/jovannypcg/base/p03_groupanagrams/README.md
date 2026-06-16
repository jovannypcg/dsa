# Group Anagrams

**Date added:** 2026-06-10

## Problem Description

Given an array of strings `strs`, group all anagrams together into sublists. You may return
the output in any order.

An anagram is a string that contains the exact same characters as another string, but the
order of the characters can be different.

**Source:** https://neetcode.io/problems/anagram-groups

## Examples

**Example 1**
```
Input: strs = ["act","pots","tops","cat","stop","hat"]
Output: [["hat"],["act","cat"],["stop","pots","tops"]]
Explanation: "act"/"cat", "pots"/"tops"/"stop", and "hat" each share the same character frequencies.
```

**Example 2**
```
Input: strs = ["x"]
Output: [["x"]]
Explanation: A single-element array forms one group on its own.
```

**Example 3**
```
Input: strs = [""]
Output: [[""]]
Explanation: Empty string is its own anagram group.
```

## Constraints

- `1 <= strs.length <= 1000`
- `0 <= strs[i].length <= 100`
- `strs[i]` is made up of lowercase English letters

## Hints

1. Two strings are anagrams if and only if they share the same characters — how could you transform a string into a canonical form that all its anagrams share?
2. What happens if you sort the characters of a string? What do all anagrams have in common after sorting?
3. A sorted string makes a natural key — what data structure lets you group strings by a shared key?
4. Iterate over each string, compute its key, and append the original string to the corresponding group in a map.
5. Once the map is built, the answer is simply its collection of values.
