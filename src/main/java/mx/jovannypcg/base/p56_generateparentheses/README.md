# Generate Parentheses

**Date added:** 2026-08-22

## Problem Description

Given `n` pairs of parentheses, write a function to generate all combinations of well-formed parentheses.

A combination of parentheses is well-formed if, reading left to right, every closing parenthesis `)` has a matching opening parenthesis `(` before it, and by the end of the string every opening parenthesis has been closed exactly once. Equivalently, at every prefix of the string the count of `(` seen so far must be greater than or equal to the count of `)` seen so far, and the final string must contain exactly `n` of each.

**Source:** https://leetcode.com/problems/generate-parentheses

## Examples

**Example 1**
```
Input: n = 1
Output: ["()"]
Explanation: With a single pair, the only well-formed arrangement is "()".
```

**Example 2**
```
Input: n = 2
Output: ["(())","()()"]
Explanation: Two pairs can be fully nested "(())" or placed side by side "()()". Any other arrangement, like ")((" or "))((", breaks the "never more closes than opens so far" rule.
```

**Example 3**
```
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]
Explanation: There are 5 well-formed combinations for 3 pairs, ranging from fully nested "((()))" to fully sequential "()()()" .
```

**Example 4**
```
Input: n = 4
Output: ["(((())))","((()()))","((())())","((()))()","(()(()))","(()()())","(()())()","(())(())","(())()()","()((()))","()(()())","()(())()","()()(())","()()()()"]
Explanation: 4 pairs yield 14 well-formed combinations. The count follows the Catalan number sequence: C(n) = (2n choose n) / (n + 1), so C(4) = 14.
```

**Example 5**
```
Input: n = 5
Output (first 4 of 42 shown for brevity): ["((((()))))","(((()())))","(((())()))","(((()))())", ...]
Explanation: The full output has C(5) = 42 well-formed combinations. Only the first few are shown here — notice the algorithm still lists them in the same lexicographic-like order produced by always trying "(" before ")" at each step.
```

**Example 6**
```
Input: n = 6
Output (first 4 of 132 shown for brevity): ["(((((())))))","((((()()))))","((((())())))","((((()))()))", ...]
Explanation: C(6) = 132. This example highlights how quickly the result set grows — doubling n from 3 to 6 grows the output from 5 to 132 combinations, not just 2x.
```

**Example 7**
```
Input: n = 8
Output (first 3 of 1430 shown for brevity): ["(((((((())))))))","((((((()()))))))","((((((())())))))", ...]
Explanation: n = 8 is the maximum value allowed by the constraints. C(8) = 1430, giving a sense of the upper bound on output size your solution must handle efficiently.
```

## Constraints

- `1 <= n <= 8`

## Hints

1. Think recursively: at each position in the string you're building, you have (at most) two choices — add an opening parenthesis or add a closing one.
2. Not every choice is legal at every step. What condition must hold before you're allowed to add a closing parenthesis?
3. Track two counters as you build each candidate string: how many `(` you've used so far and how many `)` you've used so far.
4. You can add `(` only while you still have unused opens (count < n). You can add `)` only while the number of closes used so far is less than the number of opens used so far.
5. A candidate is complete — and should be added to the result — once its length reaches `2 * n`. This is a classic backtracking template: choose, recurse, and the base case is the string length.
