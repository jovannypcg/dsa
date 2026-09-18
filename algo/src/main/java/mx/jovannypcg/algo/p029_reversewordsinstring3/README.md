# Reverse Words in a String III

**Date added:** 2026-07-19

## Problem Description

Given a string `s`, reverse the order of characters in each word within a sentence while still preserving whitespace and initial word order.

**Source:** https://leetcode.com/problems/reverse-words-in-a-string-iii

## Examples

**Example 1**
```
Input: s = "Let's take LeetCode contest"
Output: "s'teL ekat edoCteeL tsetnoc"
Explanation: Each word's characters are reversed in place, but the order of the words and the spaces between them stay the same.
```

**Example 2**
```
Input: s = "Mr Ding"
Output: "rM gniD"
Explanation: "Mr" becomes "rM" and "Ding" becomes "gniD", while the single space between them is preserved.
```

**Example 3**
```
Input: s = "God Ding"
Output: "doG gniD"
Explanation: Each word is reversed independently; "God" and "Ding" do not merge or interact with each other.
```

**Example 4**
```
Input: s = "a"
Output: "a"
Explanation: A single-character word reversed is itself.
```

**Example 5**
```
Input: s = "a b c"
Output: "a b c"
Explanation: Every word here is a single character, so reversing each word leaves the string unchanged.
```

## Constraints

- `1 <= s.length <= 5 * 10^4`
- `s` contains printable ASCII characters.
- `s` does not contain any leading or trailing spaces.
- There is at least one word in `s`.
- All the words in `s` are separated by a single space.

## Hints

1. Think about how you'd split the string into its individual words.
2. Each word can be reversed independently of the others.
3. Once a word is reversed, how do you put it back in the right place in the final result?
4. Consider whether you can reverse characters in place using two pointers within each word's boundaries, rather than allocating a new string per word.
5. A `StringBuilder` (or `char[]`) let you scan once, tracking the start index of the current word and reversing that slice when you hit a space or the end of the string.
