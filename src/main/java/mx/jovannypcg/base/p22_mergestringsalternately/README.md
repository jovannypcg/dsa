# Merge Strings Alternately

**Date added:** 2026-07-06

## Problem Description

You are given two strings, `word1` and `word2`. Construct a new string by merging them in
alternating order, starting with `word1` — take one character from `word1`, then one from
`word2`, and repeat this process.

If one string is longer than the other, append the remaining characters from the longer
string to the end of the merged result.

Return the final merged string.

**Source:** https://neetcode.io/problems/merge-strings-alternately

## Examples

**Example 1**
```
Input: word1 = "abc", word2 = "xyz"
Output: "axbycz"
Explanation: Characters are taken alternately: a, x, b, y, c, z.
```

**Example 2**
```
Input: word1 = "ab", word2 = "abbxxc"
Output: "aabbbxxc"
Explanation: After merging "a"-"a" and "b"-"b", word1 is exhausted, so the remaining
characters of word2 ("bxxc") are appended.
```

**Example 3**
```
Input: word1 = "abcd", word2 = "xy"
Output: "axbycd"
Explanation: After merging "a"-"x" and "b"-"y", word2 is exhausted, so the remaining
characters of word1 ("cd") are appended.
```

**Example 4**
```
Input: word1 = "a", word2 = "b"
Output: "ab"
Explanation: Both strings have a single character, so the result is simply "a" followed by "b".
```

**Example 5**
```
Input: word1 = "aaaa", word2 = "b"
Output: "abaaa"
Explanation: "a"-"b" are merged first, then word2 is exhausted, so the remaining "aaa" from
word1 is appended.
```

## Constraints

- `1 <= word1.length, word2.length <= 100`
- `word1` and `word2` consist of lowercase English letters.

## Hints

1. Think about walking through both strings at the same time using an index.
2. What happens when one index runs past the end of its string while the other hasn't?
3. Consider using a mutable string builder rather than concatenating strings directly.
4. The loop can stop once you've covered indices up to the length of the *longer* string.
5. At each index, only append a character from a string if that index is still within its bounds.
