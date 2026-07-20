# Backspace String Compare

**Date added:** 2026-07-19

## Problem Description

Given two strings `s` and `t`, return `true` if they are equal when both are typed into empty text editors. `'#'` means a backspace character.

Note that after backspacing an empty text, the text will continue empty.

**Source:** https://leetcode.com/problems/backspace-string-compare

## Examples

**Example 1**
```
Input: s = "ab#c", t = "ad#c"
Output: true
Explanation: Both s and t become "ac".
```

**Example 2**
```
Input: s = "ab##", t = "c#d#"
Output: true
Explanation: Both s and t become "".
```

**Example 3**
```
Input: s = "a#c", t = "b"
Output: false
Explanation: s becomes "c" while t becomes "b".
```

**Example 4**
```
Input: s = "xywrrmp", t = "xywrrmu#p"
Output: true
Explanation: s becomes "xywrrmp" and t becomes "xywrrmp".
```

**Example 5**
```
Input: s = "####", t = ""
Output: true
Explanation: s has no characters to backspace, so it becomes "". t is already "".
```

## Constraints

- `1 <= s.length, t.length <= 200`
- `s` and `t` only contain lowercase letters and `'#'` characters.

## Hints

1. A straightforward approach is to actually build the final string for each input, then compare them.
2. What data structure makes it easy to "undo" the last character when you see a `'#'`?
3. Think about processing each string from the end backward instead of the beginning — can you tell whether a character survives without building the whole result first?
4. When scanning backward, keep a counter of pending backspaces: a `'#'` increments it, and a counted-out character gets skipped rather than compared.
5. Use two independent backward pointers (one per string), advancing each to its next "surviving" character, then compare those characters one at a time. 
