# Stack

## Pattern
Use a LIFO structure to defer decisions until a matching or bounding element is found — especially useful when each element's answer depends on the next/previous greater or smaller element, or when validating nested/paired structures.

## How to Recognize
- Matching pairs: parentheses, brackets, tags
- "Next greater / smaller element"
- Nested structures that must be unwound in reverse order
- "Valid" or "evaluate" problems on expressions
- Monotonic stack: maintaining increasing or decreasing order to answer range queries

## Approach
1. Iterate through elements.
2. Push elements that are "waiting" for a match or a boundary.
3. Pop when the current element resolves the top of the stack.
4. What remains on the stack after iteration is unmatched/unresolved.

## Template

```java
// Matching pairs — valid parentheses
public boolean isValid(String s) {
    Deque<Character> stack = new ArrayDeque<>();

    for (char c : s.toCharArray()) {
        if (c == '(' || c == '{' || c == '[') {
            stack.push(c);
        } else {
            if (stack.isEmpty()) return false;
            char top = stack.pop();
            if (c == ')' && top != '(') return false;
            if (c == '}' && top != '{') return false;
            if (c == ']' && top != '[') return false;
        }
    }

    return stack.isEmpty();
}
```

```java
// Monotonic stack — next greater element
public int[] nextGreater(int[] nums) {
    int n = nums.length;
    int[] result = new int[n];
    Arrays.fill(result, -1);
    Deque<Integer> stack = new ArrayDeque<>(); // stores indices

    for (int i = 0; i < n; i++) {
        while (!stack.isEmpty() && nums[i] > nums[stack.peek()]) {
            result[stack.pop()] = nums[i];
        }
        stack.push(i);
    }

    return result;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Valid Parentheses | [LeetCode #20](https://leetcode.com/problems/valid-parentheses/) |
| 2 | Min Stack | [LeetCode #155](https://leetcode.com/problems/min-stack/) |
| 3 | Evaluate Reverse Polish Notation | [LeetCode #150](https://leetcode.com/problems/evaluate-reverse-polish-notation/) |
| 4 | Generate Parentheses | [LeetCode #22](https://leetcode.com/problems/generate-parentheses/) |
| 5 | Daily Temperatures | [LeetCode #739](https://leetcode.com/problems/daily-temperatures/) |
| 6 | Car Fleet | [LeetCode #853](https://leetcode.com/problems/car-fleet/) |
| 7 | Largest Rectangle in Histogram | [LeetCode #84](https://leetcode.com/problems/largest-rectangle-in-histogram/) |
| 8 | Next Greater Element I | [LeetCode #496](https://leetcode.com/problems/next-greater-element-i/) |
| 9 | Next Greater Element II | [LeetCode #503](https://leetcode.com/problems/next-greater-element-ii/) |
| 10 | Asteroid Collision | [LeetCode #735](https://leetcode.com/problems/asteroid-collision/) |
| 11 | Decode String | [LeetCode #394](https://leetcode.com/problems/decode-string/) |
| 12 | Remove K Digits | [LeetCode #402](https://leetcode.com/problems/remove-k-digits/) |
| 13 | 132 Pattern | [LeetCode #456](https://leetcode.com/problems/132-pattern/) |
| 14 | Simplify Path | [LeetCode #71](https://leetcode.com/problems/simplify-path/) |
| 15 | Basic Calculator II | [LeetCode #227](https://leetcode.com/problems/basic-calculator-ii/) |
| 16 | Trapping Rain Water | [LeetCode #42](https://leetcode.com/problems/trapping-rain-water/) |
| 17 | Maximum Width Ramp | [LeetCode #962](https://leetcode.com/problems/maximum-width-ramp/) |
| 18 | Online Stock Span | [LeetCode #901](https://leetcode.com/problems/online-stock-span/) |
| 19 | Sum of Subarray Minimums | [LeetCode #907](https://leetcode.com/problems/sum-of-subarray-minimums/) |
| 20 | Maximal Rectangle | [LeetCode #85](https://leetcode.com/problems/maximal-rectangle/) |
