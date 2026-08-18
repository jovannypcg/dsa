# Generate All Binary Strings (A/B)

**Date added:** 2026-08-18

## Problem Description

Given a number `n`, generate all strings of length `n` using only the letters `'A'` and `'B'`. Return the strings in lexicographic order, where `'A'` sorts before `'B'`.

**Source:** https://www.geeksforgeeks.org/dsa/generate-all-the-binary-strings-of-n-bits/

## Examples

**Example 1**
```
Input: n = 1
Output: ["A","B"]
Explanation: There are 2 strings of length 1 over {A, B}: "A" and "B".
```

**Example 2**
```
Input: n = 2
Output: ["AA","AB","BA","BB"]
Explanation: There are 2^2 = 4 strings of length 2, listed in lexicographic order.
```

**Example 3**
```
Input: n = 3
Output: ["AAA","AAB","ABA","ABB","BAA","BAB","BBA","BBB"]
Explanation: There are 2^3 = 8 strings of length 3. Each position independently can be 'A' or 'B'.
```

**Example 4**
```
Input: n = 4
Output: ["AAAA","AAAB","AABA","AABB","ABAA","ABAB","ABBA","ABBB","BAAA","BAAB","BABA","BABB","BBAA","BBAB","BBBA","BBBB"]
Explanation: There are 2^4 = 16 strings of length 4, each built by choosing 'A' or 'B' at every position.
```

**Example 5**
```
Input: n = 16
Output: (65536 strings, from "AAAAAAAAAAAAAAAA" to "BBBBBBBBBBBBBBBB")
Explanation: This is the upper bound of n, producing 2^16 = 65536 strings — useful for sanity-checking that the solution runs efficiently at the largest allowed size.
```

## Constraints

- `1 <= n <= 16`
- The output must contain exactly `2^n` strings, each of length `n`.
- Every string consists only of the characters `'A'` and `'B'`.
- No duplicate strings.
- Strings must be returned in lexicographic order (`'A'` before `'B'`).

## Hints

1. Think about building the string one character at a time, from left to right — what are the choices at each position?
2. This is a classic backtracking problem: at each position, try placing `'A'`, recurse into the next position, then backtrack and try `'B'`.
3. You'll need a mutable buffer (e.g., a `char[]` or `StringBuilder`) to hold the string being built across recursive calls.
4. The base case is reached when the buffer's length equals `n` — that's when you add a completed string to the result list.
5. Because you always try `'A'` before `'B'` at each position, the results come out in lexicographic order for free — no separate sorting step needed.
