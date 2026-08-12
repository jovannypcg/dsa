# Evaluate Reverse Polish Notation

**Date added:** 2026-08-11

## Problem Description

You are given an array of strings `tokens` that represents an arithmetic expression in Reverse Polish Notation. Evaluate the expression and return an integer that represents the value of the expression.

The valid operators are `+`, `-`, `*`, and `/`. Each operand may be an integer or another expression. The division between two integers always truncates toward zero. There will not be any division by zero. The input represents a valid arithmetic expression in reverse polish notation. The answer and all the intermediate calculations can be represented in a 32-bit integer.

Polish Notation, also called prefix notation, writes an operator before its operands, as in `+ 2 3`. Reverse Polish Notation (RPN), also called postfix notation, flips this and writes the operator after its operands, as in `2 3 +`. Both forms remove the need for parentheses or operator precedence rules, since the position of each operator relative to its operands fully determines how the expression should be evaluated.

**Source:** https://leetcode.com/problems/evaluate-reverse-polish-notation

## Examples

**Example 1**
```
Input: tokens = ["2","1","+","3","*"]
Output: 9
Explanation: ((2 + 1) * 3) = 9
```

**Example 2**
```
Input: tokens = ["4","13","5","/","+"]
Output: 6
Explanation: (4 + (13 / 5)) = 6
```

**Example 3**
```
Input: tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]
Output: 22
Explanation: ((10 * (6 / ((9 + 3) * -11))) + 17) + 5
= ((10 * (6 / (12 * -11))) + 17) + 5
= ((10 * (6 / -132)) + 17) + 5
= ((10 * 0) + 17) + 5
= (0 + 17) + 5
= 17 + 5
= 22
```

## Constraints

- `1 <= tokens.length <= 10^4`
- `tokens[i]` is either an operator: `"+"`, `"-"`, `"*"`, or `"/"`, or an integer in the range `[-200, 200]`.

## Hints

1. What data structure naturally fits an expression where operands must be combined in the order they were most recently seen?
2. Walk through the tokens left to right. When you see a number, what should happen to it? When you see an operator, what two values does it need?
3. When an operator is found, the two most recently seen operands are its operands — pop them in the right order, since subtraction and division are not commutative.
4. Push the result of each operation back so it can be used by a later operator.
5. Remember Java's `/` already truncates toward zero for integer division, so no special rounding logic is needed.
