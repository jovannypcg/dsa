# Valid Anagram

**Date added:** 2026-06-09

## Problem Description

Given two strings `s` and `t`, return `true` if the two strings are anagrams of each other,
otherwise return `false`.

An anagram is a string that contains the exact same characters as another string, but the
order of the characters can be different.

**Source:** https://neetcode.io/problems/is-anagram

## Examples

**Example 1**
```
Input: s = "anagram", t = "nagaram"
Output: true
Explanation: Both strings contain the same characters with the same frequencies.
```

**Example 2**
```
Input: s = "rat", t = "car"
Output: false
Explanation: "rat" has no 'c', so it cannot be an anagram of "car".
```

**Example 3**
```
Input: s = "a", t = "a"
Output: true
Explanation: A single identical character is trivially an anagram.
```

## Constraints

- `1 <= s.length, t.length <= 5 * 10^4`
- `s` and `t` consist of lowercase English letters.

## Hints

1. If the two strings have different lengths, can they possibly be anagrams?
2. What information about each string do you need to compare — and what data structure captures that naturally?
3. Think about tracking how many times each character appears in each string.
4. You can count character frequencies using an array of size 26 (one slot per lowercase letter).
5. Increment counts for one string and decrement for the other; if all counts are zero at the end, the strings are anagrams.
