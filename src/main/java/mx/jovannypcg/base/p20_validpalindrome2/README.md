# Valid Palindrome II

**Date added:** 2026-07-03

## Problem Description

Given a string `s`, return `true` if `s` can be a palindrome after deleting at most one
character from it.

**Source:** https://leetcode.com/problems/valid-palindrome-ii/

## Examples

**Example 1**
```
Input: s = "aba"
Output: true
Explanation: "aba" is already a palindrome, no deletion needed.
```

**Example 2**
```
Input: s = "abca"
Output: true
Explanation: You could delete the character 'c' to get "aba", which is a palindrome.
```

**Example 3**
```
Input: s = "abc"
Output: false
Explanation: No single deletion of a character results in a palindrome.
```

**Example 4**
```
Input: s = "a"
Output: true
Explanation: A single character is trivially a palindrome, no deletion needed.
```

**Example 5**
```
Input: s = "deeee"
Output: true
Explanation: Deleting the 'd' results in "eeee", which is a palindrome.
```

## Constraints

- `1 <= s.length <= 10^5`
- `s` consists of lowercase English letters.

## Hints

1. A brute-force approach would try deleting each character one at a time and check if the
   result is a palindrome — what would that cost in time?
2. The two-pointer technique used for the basic "Valid Palindrome" problem can be adapted here.
3. Walk pointers from both ends toward the middle; as long as characters match, keep moving.
4. The moment you hit a mismatch, you have (at most) one deletion to spend — try skipping
   either the left character or the right character and see if the remainder is a palindrome.
5. You only ever need to use your one allowed deletion once, at the first mismatch — if
   skipping either side doesn't produce a palindrome, the answer is `false`.
