# Move Zeros — Review

| | |
|---|---|
| **Solved on** | 2026-06-30 |
| **DSA Category** | Two Pointers |

---

## 1. Your Solution Assessment

**Correctness:** Solid. Handles the standard cases correctly — zeroes move to the end, non-zero relative order is preserved, and it operates in-place. The `null` guard is a safe defensive touch, though the constraint guarantees `1 <= nums.length`, so it can never actually be `null` in the given problem.

**Code quality:** Clean and readable. `slow` and `fast` are well-chosen names that communicate the two-pointer idiom clearly. The two-phase structure (compact non-zeroes → fill remaining with zeroes) is easy to follow.

**Time complexity:** O(n) — one pass to compact, one pass to fill, both linear in `n`.

**Space complexity:** O(1) — all work is done in-place with only two index variables.

**Algorithm trace** — Input: `nums = [0, 0, 1, 2, 0, 5]`

```
Start:    [0, 0, 1, 2, 0, 5]
           S
           F

fast=0, nums[0]=0 → skip
          [0, 0, 1, 2, 0, 5]
           S
              F

fast=1, nums[1]=0 → skip
          [0, 0, 1, 2, 0, 5]
           S
                 F

fast=2, nums[2]=1 → write nums[S]=1, S++
          [1, 0, 1, 2, 0, 5]
              S
                 F

fast=3, nums[3]=2 → write nums[S]=2, S++
          [1, 2, 1, 2, 0, 5]
                 S
                    F

fast=4, nums[4]=0 → skip
          [1, 2, 1, 2, 0, 5]
                 S
                       F

fast=5, nums[5]=5 → write nums[S]=5, S++
          [1, 2, 5, 2, 0, 5]
                    S
                          F

Fill from S=3 to end with zeroes:
          [1, 2, 5, 0, 0, 0]
```
→ Final: `[1, 2, 5, 0, 0, 0]` ✓

---

## 2. Optimal Approach

**Strategy:** Two pointers — one (`slow`) tracks the next write position for non-zero elements, the other (`fast`) scans the array. After compaction, fill remaining slots with zeroes. This is exactly what you implemented.

**Time complexity:** O(n) — two linear passes over the array.

**Space complexity:** O(1) — in-place, no auxiliary storage.

```java
public void moveZeroes(int[] nums) {
    int slow = 0;

    for (int fast = 0; fast < nums.length; fast++) {
        if (nums[fast] != 0) {
            nums[slow++] = nums[fast];
        }
    }

    while (slow < nums.length) nums[slow++] = 0;
}
```

**Algorithm trace** — Input: `nums = [0, 1, 0]`

| fast | nums[fast] | non-zero? | slow before | slow after |
|------|-----------|-----------|-------------|------------|
| 0 | 0 | No | 0 | 0 |
| 1 | 1 | Yes | 0 | 1 |
| 2 | 0 | No | 1 | 1 |

Fill: `nums[1]=0`

→ Final: `[1, 0, 0]` ✓

---

## 3. Alternative Approaches

### Swap variant (single pass)

Instead of compacting then filling, swap each non-zero element with the element at `slow`. This avoids the fill phase but performs a swap even when `fast == slow` (element is already in place) unless you add a guard.

**Time complexity:** O(n) — single pass.

**Space complexity:** O(1).

**Acceptable when:** You want a single-loop solution or the interviewer asks you to avoid a second pass.

```java
public void moveZeroes(int[] nums) {
    int slow = 0;

    for (int fast = 0; fast < nums.length; fast++) {
        if (nums[fast] != 0) {
            int tmp = nums[slow];
            nums[slow] = nums[fast];
            nums[fast] = tmp;
            slow++;
        }
    }
}
```

**Algorithm trace** — Input: `nums = [0, 0, 1, 2, 0, 5]`

```
Step 1: fast=0, nums[0]=0 → skip
        [0, 0, 1, 2, 0, 5]
         S  F

Step 2: fast=1, nums[1]=0 → skip
        [0, 0, 1, 2, 0, 5]
         S     F

Step 3: fast=2, nums[2]=1 → swap(0,2)
        [1, 0, 0, 2, 0, 5]
            S        F

Step 4: fast=3, nums[3]=2 → swap(1,3)
        [1, 2, 0, 0, 0, 5]
               S           F

Step 5: fast=4, nums[4]=0 → skip
        [1, 2, 0, 0, 0, 5]
               S              F

Step 6: fast=5, nums[5]=5 → swap(2,5)
        [1, 2, 5, 0, 0, 0]
                  S                F
```
→ Final: `[1, 2, 5, 0, 0, 0]` ✓

---

### Brute force (extra array)

Copy non-zero elements into a new array, then copy back and fill zeroes.

**Time complexity:** O(n).

**Space complexity:** O(n) — violates the in-place constraint.

**Acceptable when:** The in-place constraint is relaxed (e.g., in non-interview code where clarity beats constraints).
