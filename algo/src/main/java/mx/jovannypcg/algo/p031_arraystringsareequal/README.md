# Check if Two String Arrays are Equivalent

**Date added:** 2026-08-04

## Problem Description

Given two string arrays `word1` and `word2`, return `true` if the two arrays represent the same string, and `false` otherwise. A string is represented by an array if the array elements concatenated in order forms the string.

**Source:** https://leetcode.com/problems/check-if-two-string-arrays-are-equivalent/

## Examples

**Example 1**
```
Input: word1 = ["ab", "c"], word2 = ["a", "bc"]
Output: true
Explanation: word1 represents string "ab" + "c" -> "abc". word2 represents string "a" + "bc" -> "abc". The strings are the same, so return true.
```

**Example 2**
```
Input: word1 = ["a", "cb"], word2 = ["ab", "c"]
Output: false
Explanation: word1 represents "a" + "cb" -> "acb". word2 represents "ab" + "c" -> "abc". "acb" != "abc", so return false.
```

**Example 3**
```
Input: word1 = ["abc", "d", "defg"], word2 = ["abcddefg"]
Output: true
Explanation: word1 represents "abc" + "d" + "defg" -> "abcddefg". word2 represents "abcddefg". The strings are the same, so return true.
```

## Constraints

- `1 <= word1.length, word2.length <= 10^3`
- `1 <= word1[i].length, word2[i].length <= 10^3`
- `1 <= sum(word1[i].length), sum(word2[i].length) <= 10^3`
- `word1[i]` and `word2[i]` consist of lowercase letters.

## Hints

1. What is the most direct way to compare two arrays that represent strings?
2. You could build both full strings first, but is there a way to avoid the extra space that takes?
3. Think about walking through both arrays at the same time, one character at a time.
4. Track a pointer into the current word of each array; when you reach the end of a word, move to the next one.
5. If at any point the characters being compared differ, or one array runs out of characters before the other, the arrays are not equivalent.
