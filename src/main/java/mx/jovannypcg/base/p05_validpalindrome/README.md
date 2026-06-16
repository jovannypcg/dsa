# Valid Palindrome

**Date added:** 2026-06-11

## Problem Description

A phrase is a palindrome if, after converting all uppercase letters into lowercase letters and
removing all non-alphanumeric characters, it reads the same forward and backward. Alphanumeric
characters include letters and numbers.

Given a string `s`, return `true` if it is a palindrome, or `false` otherwise.

**Source:** https://leetcode.com/problems/valid-palindrome/

## Examples

**Example 1**
```
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: After filtering, "amanaplanacanalpanama" reads the same forward and backward.
```

**Example 2**
```
Input: s = "race a car"
Output: false
Explanation: After filtering, "raceacar" is not a palindrome.
```

**Example 3**
```
Input: s = " "
Output: true
Explanation: After removing the non-alphanumeric space, the string is empty — an empty string is a palindrome.
```

## Constraints

- `1 <= s.length <= 2 * 10^5`
- `s` consists only of printable ASCII characters.

## Hints

1. Before checking for a palindrome, think about what characters you actually care about — how would you filter the string?
2. Once you have only alphanumeric characters in lowercase, how would you naively check if a string reads the same forward and backward?
3. Reversing the string and comparing is one way — but can you avoid building a new string altogether?
4. Think about using two indices: one starting at the left and one at the right, moving toward each other.
5. With two pointers, skip any character that isn't alphanumeric and compare only the valid ones — stop as soon as they meet in the middle.
