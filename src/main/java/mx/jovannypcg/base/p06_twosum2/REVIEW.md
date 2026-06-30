# Two Sum II — Review

| | |
|---|---|
| **Solved on** | 2026-06-11 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** The solution is correct. The two-pointer approach is exactly right for a sorted array, and the 1-based index conversion (`left + 1`, `right + 1`) matches the problem's requirement. All edge cases — negatives, minimum-length arrays, duplicate values — are handled correctly by the loop invariant. The `return null` at the bottom is unreachable given the problem's guarantee that exactly one solution exists, so it causes no practical harm.

**Code quality:** Clean and easy to follow. Variable names (`left`, `right`, `sum`) are descriptive. One small thing: the second and third `if` blocks are mutually exclusive (the first already returned), so using `else if` would make that mutual exclusivity explicit to a reader. The `n` variable is only used to initialize `right`, so it could be inlined, but keeping it is fine.

**Time complexity:** O(n) — in the worst case both pointers traverse the entire array, but their combined movement never exceeds n steps.

**Space complexity:** O(1) — only a fixed number of scalar variables, regardless of input size.

**Algorithm trace** — Input: `numbers = [2, 7, 11, 15]`, `target = 9`

```
Initial:  [2,  7,  11,  15]   L=0, R=3
           L                R

Step 1: sum = 2+15 = 17 > 9 → right--
          [2,  7,  11,  15]
           L        R

Step 2: sum = 2+11 = 13 > 9 → right--
          [2,  7,  11,  15]
           L    R

Step 3: sum = 2+7 = 9 == 9 ✓
→ return [L+1, R+1] = [1, 2]
```

---

## 2. Optimal Approach

**Two Pointers** — the approach you used is already optimal.

Start one pointer at the leftmost element and one at the rightmost. At each step, compute their sum:
- Equal to target → return their 1-based indices.
- Greater than target → move the right pointer left (reduces the sum).
- Less than target → move the left pointer right (increases the sum).

Because the array is sorted, every move eliminates at least one candidate and the pointers are guaranteed to converge on the unique solution.

**Time:** O(n) — each pointer moves at most n steps in total.
**Space:** O(1) — two index variables, nothing else.

```java
public int[] twoSum(int[] numbers, int target) {
    int left = 0;
    int right = numbers.length - 1;

    while (left < right) {
        int sum = numbers[left] + numbers[right];

        if (sum == target) {
            return new int[]{left + 1, right + 1};
        } else if (sum > target) {
            right--;
        } else {
            left++;
        }
    }

    return null; // unreachable: problem guarantees one solution
}
```

**Algorithm trace** — Input: `numbers = [2, 7, 11, 15]`, `target = 9`

```
Initial:  [2,  7,  11,  15]
           L                R

Step 1: sum=2+15=17 > 9 → right--
          [2,  7,  11,  15]
           L        R

Step 2: sum=2+11=13 > 9 → right--
          [2,  7,  11,  15]
           L    R

Step 3: sum=2+7=9 == 9 ✓ → return [1, 2]
```

---

## 3. Alternative Approaches

### Binary Search

For each element `numbers[i]`, binary-search the remaining subarray for `target - numbers[i]`.

**Time:** O(n log n) — n elements, each triggering an O(log n) binary search.
**Space:** O(1) — no extra data structures.
**When acceptable:** If you forget the two-pointer trick under pressure, this is a clean fallback that still satisfies the constant-space constraint and is easy to reason about correctly.

**Algorithm trace** — Input: `numbers = [2, 7, 11, 15]`, `target = 9`

i=0, `numbers[0]=2`, need=7, search `[1..3]`:

| lo | hi | mid | numbers[mid] | action |
|----|-----|-----|-------------|--------|
| 1 | 3 | 2 | 11 > 7 → hi=1 | |
| 1 | 1 | 1 | 7 == 7 ✓ | return [1, 2] |

→ return `[1, 2]`

---

### Hash Map (violates space constraint)

Build a map of value → index as you scan. For each element, check whether `target - numbers[i]` is already in the map.

**Time:** O(n) — single pass.
**Space:** O(n) — stores up to n entries.
**When acceptable:** This is the standard solution for the unsorted Two Sum, but it violates this problem's O(1) space requirement. Mention it only to contrast with the two-pointer approach.

**Algorithm trace** — Input: `numbers = [2, 7, 11, 15]`, `target = 9`

| i | numbers[i] | need | map | found? |
|---|-----------|------|-----|--------|
| 0 | 2 | 7 | {} | No → map={2:0} |
| 1 | 7 | 2 | {2:0} | **Yes** → return [1, 2] |

→ return `[1, 2]`

---

### Brute Force

Check every pair (i, j) with i < j.

**Time:** O(n²) — all pairs.
**Space:** O(1).
**When acceptable:** Only worth mentioning as a starting point to motivate better approaches. Never acceptable as a final answer.

**Algorithm trace** — Input: `numbers = [2, 7, 11, 15]`, `target = 9`

| i | j | numbers[i]+numbers[j] | == target? |
|---|---|----------------------|------------|
| 0 | 1 | 2+7=9 | **Yes** → return [1, 2] |

→ return `[1, 2]`
