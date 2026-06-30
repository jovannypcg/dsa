# Review — Remove Duplicates from Sorted Array

| | |
|---|---|
| **Solved on** | 2026-06-30 |
| **DSA Category** | Two Pointers |

---

## 1. Your Solution Assessment

**Correctness:** Handles all cases correctly. The inner `while` safely skips runs of duplicates by comparing `nums[fast] == nums[slow]` — valid because `slow` never moves inside that loop. The `if (fast >= n) break` cleanly handles the all-duplicates case (e.g., `[1,1,1,1]`) where the inner loop exhausts the array. The early return for `nums.length < 2` is a correct guard, though unnecessary — the main loop handles length-1 correctly on its own.

**Code quality:** Clear and readable. Naming (`slow`, `fast`) immediately signals the two-pointer pattern. The inline comment traces through a concrete example, which helps communicate the termination logic.

**Time complexity:** O(n) — each element is visited at most twice: once by `fast` inside the inner loop, once implicitly passed over by `slow`.

**Space complexity:** O(1) — only two index variables, no auxiliary storage.

**Algorithm trace** — Input: `nums = [2, 10, 10, 30, 30, 30]`

```
Initial:
  [2, 10, 10, 30, 30, 30]
   s
   f

Step 1 — inner while: nums[fast]=2 == nums[slow]=2 → fast++
  [2, 10, 10, 30, 30, 30]
   s
       f
  nums[fast]=10 != nums[slow]=2 → exit inner while
  → nums[++slow] = nums[fast++]  (write 10 at index 1)
  [2, 10, 10, 30, 30, 30]
       s
           f

Step 2 — inner while: nums[fast]=10 == nums[slow]=10 → fast++
  [2, 10, 10, 30, 30, 30]
       s
               f
  nums[fast]=30 != nums[slow]=10 → exit inner while
  → nums[++slow] = nums[fast++]  (write 30 at index 2)
  [2, 10, 30, 30, 30, 30]
           s
                   f

Step 3 — inner while: nums[fast]=30 == nums[slow]=30 → fast++ (×2)
  fast=6 >= n=6 → if (fast >= n) break

→ return slow + 1 = 3
```

---

## 2. Optimal Approach

**Strategy:** Two pointers — `slow` marks the tail of the unique prefix; `fast` scans ahead for the next distinct value. When `fast` finds one, write it to `nums[slow + 1]` and advance `slow`. This is exactly what you implemented. The `for`-loop variant collapses the nested `while` into a single comparison, eliminating the bounds check and early break.

**Time complexity:** O(n) — single pass with two pointers.

**Space complexity:** O(1) — in-place mutation, no extra memory.

```java
public int removeDuplicates(int[] nums) {
    int slow = 0;

    for (int fast = 1; fast < nums.length; fast++) {
        if (nums[fast] != nums[slow]) {
            nums[++slow] = nums[fast];
        }
    }

    return slow + 1;
}
```

**Algorithm trace** — Input: `nums = [2, 10, 10, 30, 30, 30]`

```
Initial:
  [2, 10, 10, 30, 30, 30]
   s

fast=1: nums[1]=10 != nums[0]=2 → nums[++slow] = nums[1]  (write 10 at index 1)
  [2, 10, 10, 30, 30, 30]
       s

fast=2: nums[2]=10 == nums[1]=10 → skip

fast=3: nums[3]=30 != nums[1]=10 → nums[++slow] = nums[3]  (write 30 at index 2)
  [2, 10, 30, 30, 30, 30]
           s

fast=4: nums[4]=30 == nums[2]=30 → skip
fast=5: nums[5]=30 == nums[2]=30 → skip

→ return slow + 1 = 3
```

---

## 3. Alternative Approaches

### Brute force — shift on duplicate
When a duplicate is detected at index `i` (i.e., `nums[i] == nums[i-1]`), shift all elements from `i+1` onwards one position left and decrement the logical length. Re-check the same index `i` after the shift.

- **Time:** O(n²) — up to n shifts, each costing O(n).
- **Space:** O(1).
- **When acceptable:** Never preferable, but simple to reason about under interview pressure if two pointers don't come to mind immediately.

**Algorithm trace** — Input: `nums = [1, 1, 2, 3, 4]`, `n = 5`

| i | nums[i-1] | nums[i] | Action | Array | n |
|---|-----------|---------|--------|-------|---|
| 1 | 1 | 1 | duplicate → shift left from i=1 | [1, 2, 3, 4, 4] | 4 |
| 1 | 1 | 2 | unique → advance | [1, 2, 3, 4, 4] | 4 |
| 2 | 2 | 3 | unique → advance | [1, 2, 3, 4, 4] | 4 |
| 3 | 3 | 4 | unique → advance | [1, 2, 3, 4, 4] | 4 |

→ return 4

---

### HashSet (not in-place)
Iterate the array and add each value to a `LinkedHashSet` (preserves insertion order). Write the set's contents back into `nums` from index 0.

- **Time:** O(n).
- **Space:** O(k), where k is the number of unique elements.
- **When acceptable:** If the in-place constraint were removed; also useful when the input isn't guaranteed to be sorted.

**Algorithm trace** — Input: `nums = [2, 10, 10, 30, 30, 30]`

| i | nums[i] | set | written back? |
|---|---------|-----|---------------|
| 0 | 2 | {2} | Yes → nums[0]=2 |
| 1 | 10 | {2, 10} | Yes → nums[1]=10 |
| 2 | 10 | {2, 10} | No (already in set) |
| 3 | 30 | {2, 10, 30} | Yes → nums[2]=30 |
| 4 | 30 | {2, 10, 30} | No (already in set) |
| 5 | 30 | {2, 10, 30} | No (already in set) |

→ nums = [2, 10, 30, 30, 30, 30], return 3
