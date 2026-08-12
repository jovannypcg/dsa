# Min Stack

**Date added:** 2026-08-11

## Problem Description

Design a stack that supports push, pop, top, and retrieving the minimum element, all in constant time.

Implement the `MinStack` class:

- `MinStack()` initializes the stack object.
- `void push(int value)` pushes the element `value` onto the stack.
- `void pop()` removes the element on the top of the stack.
- `int top()` gets the top element of the stack.
- `int getMin()` retrieves the minimum element in the stack.

You must implement a solution with O(1) time complexity for each function.

**Source:** https://leetcode.com/problems/min-stack

## Examples

**Example 1**
```
Input
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]

Output
[null,null,null,null,-3,null,0,-2]
```
Explanation: `push(-2)`, `push(0)`, `push(-3)`. `getMin()` returns `-3`. `pop()` removes `-3`. `top()` returns `0`. `getMin()` now returns `-2` since the stack is `[-2, 0]`.

**Example 2**
```
Input
["MinStack","push","getMin","push","getMin","pop","getMin"]
[[],[5],[],[2],[],[],[]]

Output
[null,null,5,null,2,null,5]
```
Explanation: `push(5)`, `getMin()` returns `5`. `push(2)`, `getMin()` returns `2`. `pop()` removes `2`, `getMin()` returns `5` again.

**Example 3**
```
Input
["MinStack","push","push","getMin","pop","getMin"]
[[],[0],[0],[],[],[]]

Output
[null,null,null,0,null,0]
```
Explanation: Duplicate minimums are pushed (`0` twice). Popping one `0` still leaves `0` as the minimum, since the other `0` remains on the stack.

## Constraints

- `-2^31 <= val <= 2^31 - 1`
- Methods `pop`, `top`, and `getMin` operations will always be called on non-empty stacks.
- At most `3 * 10^4` calls will be made to `push`, `pop`, `top`, and `getMin`.

## Hints

1. A naive `getMin()` could scan the whole stack every time — what would that cost, and can we avoid it?
2. What if you tracked the minimum separately as elements are pushed?
3. A single "current minimum" variable breaks when you pop it off — think about what history you need.
4. Consider keeping a second stack that mirrors the minimum value at each point in time.
5. Every time you push, also push the smaller of the new value and the current minimum onto the second stack; every pop removes from both stacks in sync.
