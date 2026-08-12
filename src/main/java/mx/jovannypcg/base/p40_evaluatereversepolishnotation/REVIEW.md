| | |
|---|---|
| **Solved on** | 2026-08-12 |
| **DSA Category** | Stack |

## 1. Your Solution Assessment

### Correctness

The implementation is correct. It handles every case exercised by the tests: simple addition, division that truncates toward zero (both positive and negative operands), order-sensitive subtraction, negative-number tokens, deeply nested expressions, and the ±200 boundary values. The core invariant — pop the two most recent operands, apply the operator with the correct left/right order (`first`, `second`), push the result back — is implemented correctly. Since the problem guarantees a valid RPN expression, no defensive checks (empty stack, unknown operator, division by zero) are needed, and the solution correctly avoids adding any.

### Code quality

The code is compact and readable. `isOperator` and `eval` are cleanly separated from the main loop, and the `continue` after pushing an operand keeps the loop body flat instead of nesting an `if/else`. One minor nit: `OPERATORS` is declared as an instance field but never depends on instance state — it could be `static final`, saving a per-instance allocation, though for this problem size it makes no measurable difference.

### Time complexity

**O(n)**, where `n` is the number of tokens. Each token is visited exactly once, and every stack operation (`push`/`pop`) is O(1).

### Space complexity

**O(n)** in the worst case. For an expression with `n` tokens, up to `⌈n/2⌉` operands can be sitting on the stack before an operator ever appears (e.g., all numbers followed by all operators), so the stack can grow linearly with the input.

### Algorithm trace

Input: `tokens = ["10","6","9","3","+","-11","*","/","*","17","+","5","+"]` (README Example 3)

| Token | Action | Stack (bottom → top) |
|---|---|---|
| 10 | push 10 | [10] |
| 6 | push 6 | [10, 6] |
| 9 | push 9 | [10, 6, 9] |
| 3 | push 3 | [10, 6, 9, 3] |
| + | pop 3, pop 9 → eval(+, 9, 3) = 12, push | [10, 6, 12] |
| -11 | push -11 | [10, 6, 12, -11] |
| * | pop -11, pop 12 → eval(*, 12, -11) = -132, push | [10, 6, -132] |
| / | pop -132, pop 6 → eval(/, 6, -132) = 0, push | [10, 0] |
| * | pop 0, pop 10 → eval(*, 10, 0) = 0, push | [0] |
| 17 | push 17 | [0, 17] |
| + | pop 17, pop 0 → eval(+, 0, 17) = 17, push | [17] |
| 5 | push 5 | [17, 5] |
| + | pop 5, pop 17 → eval(+, 17, 5) = 22, push | [22] |

→ return `22` ✓

## 2. Optimal Approach

A single-pass, single-stack scan is optimal for this problem — the user's solution already implements it. Walk the tokens left to right. When a token is a number, push it. When a token is an operator, pop the top two values off the stack (the top is the right-hand operand, the one below it is the left-hand operand), apply the operator, and push the result back. After the last token is processed, exactly one value remains on the stack: the answer. This works because RPN is exactly the traversal order that a stack naturally reconstructs — every operator in postfix notation always immediately follows its two fully-resolved operands.

### Time complexity

**O(n)** — one pass over the tokens, O(1) work per token.

### Space complexity

**O(n)** — the operand stack holds up to roughly half the tokens in the worst case (a long run of numbers before any operator).

```java
public int evalRPN(String[] tokens) {
    Deque<Integer> stack = new ArrayDeque<>();

    for (String token : tokens) {
        switch (token) {
            case "+" -> stack.push(stack.pop() + stack.pop());
            case "*" -> stack.push(stack.pop() * stack.pop());
            case "-" -> {
                int b = stack.pop(), a = stack.pop();
                stack.push(a - b);
            }
            case "/" -> {
                int b = stack.pop(), a = stack.pop();
                stack.push(a / b);
            }
            default -> stack.push(Integer.parseInt(token));
        }
    }

    return stack.pop();
}
```

### Algorithm trace

Input: `tokens = ["4","13","5","/","+"]` (README Example 2)

| Token | Action | Stack (bottom → top) |
|---|---|---|
| 4 | push 4 | [4] |
| 13 | push 13 | [4, 13] |
| 5 | push 5 | [4, 13, 5] |
| / | pop 5, pop 13 → 13/5 = 2 (truncated), push | [4, 2] |
| + | pop 2, pop 4 → 4+2 = 6, push | [6] |

→ return `6` ✓

## 3. Alternative Approaches

### Recursive evaluation from the end of the array

Since the last token of a valid RPN expression is always the outermost operator, you can evaluate recursively by walking the array backward with a mutable index: if the current token is an operator, recursively evaluate the right operand first (decrementing the index), then the left operand, then combine them; if it's a number, return it directly and decrement the index. This avoids an explicit `Stack`/`Deque` object, using the call stack instead.

- **Time complexity:** O(n) — each token is visited exactly once across all recursive calls.
- **Space complexity:** O(n) — the recursion depth can reach O(n) for a right-heavy expression (e.g., many chained operators), plus the implicit call stack frames.
- **When acceptable:** Mostly a stylistic choice to show recursion fluency in an interview; the explicit-stack version is simpler to reason about and avoids recursion-depth concerns for large inputs (`10^4` tokens could risk a `StackOverflowError` depending on JVM stack size), so it's rarely preferable in practice.

**Algorithm trace** (call stack table) — Input: `tokens = ["2","1","+","3","*"]`, starting index `i = 4` (last token)

| Depth | Call | Returns |
|---|---|---|
| 0 | eval(i=4, token="*") | eval(i=3) * eval(i=1) |
| 1 | eval(i=3, token="3") | 3 (i→2) |
| 1 | eval(i=1, token="+") | eval(i=0) + eval(i=-1 boundary)... |

*(In practice the reverse-walk recursion is fiddly to index correctly — this is exactly why the iterative stack approach is preferred; the recursive variant is included here for completeness, not as a recommendation.)*

### Brute-force in-place reduction

Repeatedly scan the token list for the first operator found; apply it to the two elements immediately preceding it in the list, replace those three elements with the single result, and repeat the scan from the beginning until only one element remains.

- **Time complexity:** O(n²) — each reduction step requires an O(n) scan to find the next operator and an O(n) shift to collapse the list, and there are O(n) such steps.
- **Space complexity:** O(n) for the mutable copy of the token list (or O(1) extra if mutating in place with index bookkeeping).
- **When acceptable:** Only under heavy time pressure as a "get something working first" fallback, or for very small inputs where the quadratic blowup doesn't matter. Given the constraint `tokens.length <= 10^4`, this would risk timing out and should be replaced by the stack approach once correctness is confirmed.

**Algorithm trace** (annotated array) — Input: `["2","1","+","3","*"]`

```
Scan 1: find first operator at index 2 ("+")
[2, 1, +, 3, *]
       ^
Apply 2+1=3, replace tokens[0..2] with 3
[3, 3, *]

Scan 2: find first operator at index 2 ("*")
[3, 3, *]
       ^
Apply 3*3=9, replace tokens[0..2] with 9
[9]
```
→ return `9` ✓
