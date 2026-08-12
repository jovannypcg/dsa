| | |
|---|---|
| **Solved on** | 2026-08-11 |
| **DSA Category** | Stack |

## 1. Your Solution Assessment

**Correctness:** Correct. You maintain a second stack (`minStack`) that tracks the running minimum by pushing a new value onto it whenever the incoming value is `<=` the current minimum, and popping from it whenever the popped value from the main stack equals the current minimum. The `<=` (rather than `<`) is the key detail that makes duplicate minimums work — I verified this against duplicate-minimum, all-negative, `Integer.MIN_VALUE`/`Integer.MAX_VALUE`, and fluctuating-min scenarios; all pass. The `Integer.MAX_VALUE` sentinel returned by `getMin()` when `minStack` is empty is only ever used to seed the very first push's comparison, so it never leaks into an observable result — `getMin()` is guaranteed by the problem to only be called on a non-empty stack.

Using a hand-rolled singly linked-list stack with a dummy head node (instead of `java.util.Deque<Integer>`) is a nice touch here: it stores primitive `int`s in `Node`, avoiding the autoboxing that `Stack<Integer>` or `Deque<Integer>` would incur.

**Time complexity:** O(1) for every operation (`push`, `pop`, `top`, `getMin`). Each does a fixed, small amount of work: a comparison and at most one push/pop on each of the two linked-list stacks — no loops, no scans.

**Space complexity:** O(n), where n is the number of elements pushed. In the worst case (strictly non-increasing pushes), `minStack` grows in lockstep with the main stack, doubling total storage — still O(n), just with a larger constant.

**Algorithm trace** (Example 1: `push(-2)`, `push(0)`, `push(-3)`, `getMin()`, `pop()`, `top()`, `getMin()`):

| Operation | stack (bottom→top) | minStack (bottom→top) | Result |
|---|---|---|---|
| push(-2) | [-2] | [-2] | — |
| push(0) | [-2, 0] | [-2] | — |
| push(-3) | [-2, 0, -3] | [-2, -3] | — |
| getMin() | [-2, 0, -3] | [-2, -3] | -3 |
| pop() | [-2, 0] | [-2] | — |
| top() | [-2, 0] | [-2] | 0 |
| getMin() | [-2, 0] | [-2] | -2 |

## 2. Optimal Approach

This is already the optimal approach: an **auxiliary stack that mirrors the running minimum**. Every push compares the incoming value to the current minimum (or `+∞` if empty) and pushes onto the auxiliary stack only when the new value is `≤` the current minimum. Every pop checks whether the value leaving the main stack equals the current minimum, and if so, pops the auxiliary stack too — keeping both stacks perfectly in sync so `getMin()` is always just a peek.

**Time complexity:** O(1) per operation — no scanning, ever.
**Space complexity:** O(n) — the auxiliary stack holds at most one entry per push.

```java
import java.util.Deque;
import java.util.ArrayDeque;

class MinStack {
    private final Deque<Integer> stack = new ArrayDeque<>();
    private final Deque<Integer> minStack = new ArrayDeque<>();

    public void push(int value) {
        stack.push(value);

        if (minStack.isEmpty() || value <= minStack.peek()) {
            minStack.push(value);
        }
    }

    public void pop() {
        int value = stack.pop();

        if (value == minStack.peek()) {
            minStack.pop();
        }
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        return minStack.peek();
    }
}
```

**Algorithm trace:** identical to the table above — this is the same algorithm you implemented, just backed by `java.util.Deque` instead of a hand-rolled linked-list stack.

## 3. Alternative Approaches

### Brute force: single stack, scan for min
Keep only one stack. `push`/`pop`/`top` are O(1), but `getMin()` walks the entire stack to find the smallest value.
- **Time:** O(1) for push/pop/top, **O(n)** for getMin — a full scan of up to n elements.
- **Space:** O(n) for the stack, O(1) extra.
- **When acceptable:** Only if the problem didn't require O(1) `getMin()`, or `getMin()` is called rarely relative to push/pop. Fails this problem's explicit constraint, but is the natural first instinct and a fine warm-up before spotting the auxiliary-stack trick.

**Algorithm trace** (`getMin()` on stack `[-2, 0, -3]` after the three pushes):
| Operation | stack (bottom→top) | scan | Result |
|---|---|---|---|
| getMin() | [-2, 0, -3] | compare -2, 0, -3 → min so far -3 | -3 |

### Single stack of (value, minSoFar) pairs
Instead of two parallel stacks, push a pair on every operation: `(value, min(value, currentMin))`. `getMin()` just reads the second field of the top pair.
- **Time:** O(1) for all operations — same as the two-stack approach.
- **Space:** O(n), but with a larger constant per element (two ints per node instead of one) since every push stores a pair, whereas the two-stack approach only grows `minStack` when a new minimum appears.
- **When acceptable:** Always correct and arguably simpler to reason about (no need to compare on pop to decide whether to pop the second stack). Reasonable to reach for under interview time pressure since it avoids the "should I pop the aux stack?" conditional entirely.

**Algorithm trace** (`push(-2)`, `push(0)`, `push(-3)`):
| Operation | stack of (value, minSoFar) | Result |
|---|---|---|
| push(-2) | [(-2, -2)] | — |
| push(0) | [(-2, -2), (0, -2)] | — |
| push(-3) | [(-2, -2), (0, -2), (-3, -3)] | — |
| getMin() | [(-2, -2), (0, -2), (-3, -3)] | -3 (top pair's second field) |

### Encoded single-stack trick (O(1) extra space)
Store only one stack, but track `min` as a single variable. When pushing a value smaller than the current min, push an *encoded* value (e.g., `2 * value - min`) instead of the raw value, then update `min` to the new value. On pop, if the popped encoded value is less than `min`, it signals "this was a min-update marker" — recover the previous min via `min = 2 * min - encodedValue` before discarding it.
- **Time:** O(1) for all operations.
- **Space:** O(n) for the stack itself, but **O(1) extra** beyond it (just the `min` variable) — better than the two-stack approach's extra O(n).
- **When acceptable:** Useful when interviewers explicitly ask to optimize auxiliary space, or in memory-constrained settings. Riskier in practice: the encoding math can silently overflow `int` near `Integer.MIN_VALUE`/`MAX_VALUE` unless done carefully with `long`, and the code is noticeably harder to read and explain under time pressure — not worth it unless the O(n) extra space of the two-stack approach is explicitly called out as unacceptable.

**Algorithm trace** (`push(3)`, `push(1)`, `push(2)`, starting `min = +∞`):
| Operation | condition | encoded value pushed | stack | min after |
|---|---|---|---|---|
| push(3) | 3 < ∞ → new min | 3 (first push, stored raw) | [3] | 3 |
| push(1) | 1 < 3 → new min | 2·1 − 3 = **−1** | [3, −1] | 1 |
| push(2) | 2 ≥ 1 → not a new min | 2 (raw) | [3, −1, 2] | 1 |
| getMin() | — | — | [3, −1, 2] | 1 |
