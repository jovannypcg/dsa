# Valid Word Abbreviation

**Date added:** 2026-07-06

## Problem Description

A string can be shortened by replacing any number of non-adjacent, non-empty substrings with
their lengths (without leading zeros).

For example, the string `"implementation"` can be abbreviated in several ways, such as:

- `"i12n"` -> (`i` `mplementatio` `n`)
- `"imp4n5n"` -> (`imp` `leme` `n` `tatio` `n`)
- `"14"` -> (`implementation`)
- `"implemetation"` -> (no substrings replaced)

Invalid abbreviations include:

- `"i57n"` -> (`i` `mplem` `entatio` `n`, adjacent substrings are replaced.)
- `"i012n"` -> (has leading zeros)
- `"i0mplementation"` (replaces an empty substring)

You are given a string named `word` and an abbreviation named `abbr`, return `true` if `abbr`
correctly abbreviates `word`, otherwise return `false`.

A substring is a contiguous non-empty sequence of characters within a string.

**Source:** https://neetcode.io/problems/valid-word-abbreviation

## Examples

**Example 1**
```
Input: word = "apple", abbr = "a3e"
Output: true
Explanation: "ppl" (3 characters) is replaced by "3", giving "a3e".
```

**Example 2**
```
Input: word = "international", abbr = "i9l"
Output: false
Explanation: There are only 8 characters between the first "i" and the last "l" ("nternationa"),
not 9, so the abbreviation does not match.
```

**Example 3**
```
Input: word = "abbreviation", abbr = "abbreviation"
Output: true
Explanation: No substrings were replaced; the strings match exactly.
```

**Example 4**
```
Input: word = "internationalization", abbr = "i18n"
Output: true
Explanation: "nternationalizatio" (18 characters) is replaced by "18", giving "i18n".
```

**Example 5**
```
Input: word = "a", abbr = "01"
Output: false
Explanation: The number "01" has a leading zero, which is invalid.
```

## Constraints

- `1 <= word.length <= 100`
- `word` is made up of only lowercase English letters.
- `1 <= abbr.length <= 100`
- `abbr` is made up of lowercase English letters and digits.
- All digit-only substrings of `abbr` fit in a 32-bit integer.

## Hints

1. Try walking through `word` and `abbr` at the same time using two pointers.
2. When you hit a digit in `abbr`, you need to consume all consecutive digits to form the full number, not just one digit.
3. A number with a leading zero (e.g., `"01"`) is always invalid — check for this explicitly.
4. Once you've parsed a number, advance the pointer in `word` by that many characters instead of comparing them.
5. The abbreviation is only valid if both pointers reach the end of their respective strings at the same time.
