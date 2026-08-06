| | |
|---|---|
| **Solved on** | 2026-08-06 |
| **DSA Category** | Arrays & Hashing |

## 1. Your Solution Assessment

The graded method, `productExceptSelf`, builds prefix products directly into the output array during a left-to-right pass, then folds in suffix products during a right-to-left pass using a single running accumulator instead of a second array. This is not just a working solution — it is already the optimal O(1)-extra-space approach the problem's follow-up asks for.

**Correctness:** All 9 test cases pass, including the all-zero, single-zero, all-negative, and boundary-value (`±30`) cases. The two-pass structure naturally handles zeros without any special-casing, since a zero simply propagates through the running product the same way any other value would — this is exactly why the division-based approach (see Alternative Approaches) needs extra logic that this one avoids.

**Code quality:** Clear and minimal. Variable names (`acc`, `answer`) are short but unambiguous given the tight scope. The `productExceptSelfExtraSpace`/`getPrefix`/`getSuffix` trio is a nice touch — implementing the more intuitive two-array version alongside the space-optimized one shows the derivation path from one to the other.

**Time complexity:** O(n) — two linear passes over `nums`, each doing constant work per index.

**Space complexity:** O(1) extra space — only the `answer` output array (not counted per the problem's convention) and a single `acc` variable are used.

**Algorithm trace** (Step table — iterative loop)

Input: `nums = [1, 2, 3, 4]`

Phase 1 — build prefix products into `answer` (loop `i` from 1 to n-1):

| i | nums[i-1] | answer[i-1] | answer[i] = answer[i-1] * nums[i-1] |
|---|---|---|---|
| 1 | 1 | 1 | 1 |
| 2 | 2 | 1 | 2 |
| 3 | 3 | 2 | 6 |

After phase 1: `answer = [1, 1, 2, 6]`

Phase 2 — fold in suffix products via running `acc` (loop `i` from n-1 downto 0):

| i | acc before | answer[i] before | answer[i] *= acc | nums[i] | acc after = acc * nums[i] |
|---|---|---|---|---|---|
| 3 | 1 | 6 | 6 | 4 | 4 |
| 2 | 4 | 2 | 8 | 3 | 12 |
| 1 | 12 | 1 | 12 | 2 | 24 |
| 0 | 24 | 1 | 24 | 1 | 24 |

Final `answer = [24, 12, 8, 6]` — matches the expected output.

## 2. Optimal Approach

This is the same approach the user already implemented in `productExceptSelf`: compute prefix products left-to-right and suffix products right-to-left, but instead of storing suffix products in a second array, fold them into the answer array using a single running variable during the second pass. The key insight is that `answer[i]` only needs the *product* of the prefix and suffix contributions, so the prefix pass can write directly into the output array, and the suffix pass only needs to remember the running product seen so far, not the whole suffix array.

**Time complexity:** O(n) — two linear passes over the input.

**Space complexity:** O(1) extra space — the output array is not counted, and only one accumulator variable is needed beyond it.

```java
public int[] productExceptSelf(int[] nums) {
    int n = nums.length;
    int[] answer = new int[n];

    answer[0] = 1;
    for (int i = 1; i < n; i++) {
        answer[i] = answer[i - 1] * nums[i - 1];
    }

    int acc = 1;
    for (int i = n - 1; i >= 0; i--) {
        answer[i] *= acc;
        acc *= nums[i];
    }

    return answer;
}
```

**Algorithm trace:** identical to the trace in section 1, since the user's solution already implements this approach.

## 3. Alternative Approaches

### Brute force (nested loop)

For each index `i`, loop over every other index `j != i` and multiply `nums[j]` into a running product for that position.

**Time complexity:** O(n²) — for each of the n positions, an O(n) inner loop scans the rest of the array.

**Space complexity:** O(1) extra space — only a running product variable per outer iteration, no auxiliary arrays.

**When acceptable:** Only for very small inputs or as a warm-up answer under interview time pressure before optimizing — it does not meet the problem's required O(n) time constraint.

**Algorithm trace** (Step table)

Input: `nums = [1, 2, 3, 4]`

| i | j values scanned | running product | answer[i] |
|---|---|---|---|
| 0 | 1, 2, 3 | 1→2→6→24 | 24 |
| 1 | 0, 2, 3 | 1→1→3→12 | 12 |
| 2 | 0, 1, 3 | 1→1→2→8 | 8 |
| 3 | 0, 1, 2 | 1→1→2→6 | 6 |

Final `answer = [24, 12, 8, 6]`.

### Prefix and suffix arrays (two auxiliary arrays)

Build a `prefix` array where `prefix[i]` holds the product of all elements before index `i`, and a `suffix` array where `suffix[i]` holds the product of all elements after index `i`. Then `answer[i] = prefix[i] * suffix[i]`. This is exactly what the user's `productExceptSelfExtraSpace`/`getPrefix`/`getSuffix` methods implement.

**Time complexity:** O(n) — three linear passes (build prefix, build suffix, combine).

**Space complexity:** O(n) extra space — two full-length auxiliary arrays beyond the output.

**When acceptable:** A good stepping stone toward the O(1)-space version, and perfectly fine if the interviewer only asks for O(n) time without the space follow-up, or if code clarity is prioritized over space.

**Algorithm trace** (Step table)

Input: `nums = [1, 2, 3, 4]`

| i | prefix[i] | suffix[i] | answer[i] = prefix[i] * suffix[i] |
|---|---|---|---|
| 0 | 1 | 24 | 24 |
| 1 | 1 | 12 | 12 |
| 2 | 2 | 4 | 8 |
| 3 | 6 | 1 | 6 |

Final `answer = [24, 12, 8, 6]`.

### Division-based approach (disallowed by constraints, worth knowing)

Compute the total product of all elements, then `answer[i] = total / nums[i]`. This is disqualified by the problem's explicit "no division" rule, and it also needs special-case handling for zeros: if exactly one element is zero, every `answer[i]` is 0 except at the zero's own index, which gets the product of all non-zero elements; if two or more elements are zero, every `answer[i]` is 0.

**Time complexity:** O(n) — one pass to compute the total product, one pass to divide.

**Space complexity:** O(1) extra space.

**When acceptable:** Never as a submitted solution here, since it directly violates the stated constraint — but worth mentioning in an interview to show awareness of the simpler approach and why it's excluded.

**Algorithm trace** (Step table)

Input: `nums = [1, 2, 3, 4]` (no zeros, so no special-casing needed)

| i | total | nums[i] | answer[i] = total / nums[i] |
|---|---|---|---|
| 0 | 24 | 1 | 24 |
| 1 | 24 | 2 | 12 |
| 2 | 24 | 3 | 8 |
| 3 | 24 | 4 | 6 |

Final `answer = [24, 12, 8, 6]`.
