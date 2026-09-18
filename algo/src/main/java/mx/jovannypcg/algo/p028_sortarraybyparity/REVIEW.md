| | |
|---|---|
| **Solved on** | 2026-07-13 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** Correct. `sortArrayByParity` delegates to `sortArrayByParityInPlace`, which uses two pointers converging from opposite ends: `left` starting at index `0`, `right` starting at index `n - 1`. While `left < right`, an already-even value at `left` is skipped over (`left++`), an already-odd value at `right` is skipped over (`right--`), and otherwise (`left` is odd, `right` is even) the two are swapped and both pointers move inward. Each iteration strictly narrows the `[left, right]` window, so the loop always terminates, and every element gets classified exactly once: everything left of the meeting point ends up even, everything from it onward ends up odd.

**Code quality:** Clean and matches the textbook quicksort-partition shape (see section 2.1 below) closely — `left`/`right`/`tmp` are conventional, well-understood names for this pattern. Mutating `nums` directly and returning it is appropriate here since the problem doesn't require preserving the input.

**Time complexity:** O(n) — `left` and `right` only ever move toward each other, so together they visit at most `n` positions total across the whole run.

**Space complexity:** O(1) — the swap happens in place on the input array; no auxiliary array is allocated.

**Algorithm trace** (`nums = [2, 1, 3, 4, 5, 6]` — chosen to exercise all three branches of the `if / else if / else`):

```
Initial:
nums: [2, 1, 3, 4, 5, 6]
       L              R
(left=0, right=5)

nums[L]=2 is even -> left++
nums: [2, 1, 3, 4, 5, 6]
       L              R
(after: left=1, right=5)

nums[L]=1 is odd, nums[R]=6 is even -> swap
nums: [2, 1, 3, 4, 5, 6]
          L           R
nums: [2, 6, 3, 4, 5, 1]
(after swap: left=2, right=4)

nums[R]=5 is odd -> right--
nums: [2, 6, 3, 4, 5, 1]
             L     R
(after: left=2, right=3)

nums[L]=3 is odd, nums[R]=4 is even -> swap
nums: [2, 6, 3, 4, 5, 1]
             L  R
nums: [2, 6, 4, 3, 5, 1]
(after swap: left=3, right=2 -> L >= R, loop ends)
```
→ return `[2, 6, 4, 3, 5, 1]` — a valid arrangement (evens `2, 6, 4` before odds `3, 5, 1`), achieved with zero extra array allocation.

## 2. Optimal Approach

`nums` can be partitioned in place with two pointers that converge from opposite ends, similar to the partition step in quicksort. `left` advances past values that are already even; `right` retreats past values that are already odd. When `left` lands on an odd value and `right` lands on an even value, swap them — that places one correct value on each side in O(1) work, and both pointers move inward. The loop ends when the pointers meet, at which point everything before `left` is even and everything from `right` onward is odd. This is the same approach implemented in the current `Solution.java`.

**Time complexity:** O(n) — each index is visited at most once total across both pointers combined (each pointer moves strictly inward every iteration).

**Space complexity:** O(1) — only the input array is modified in place; no auxiliary array is allocated.

```java
public int[] sortArrayByParity(int[] nums) {
    int left = 0;
    int right = nums.length - 1;

    while (left < right) {
        if (nums[left] % 2 == 0) {
            left++;
        } else if (nums[right] % 2 != 0) {
            right--;
        } else {
            int temp = nums[left];
            nums[left] = nums[right];
            nums[right] = temp;
            left++;
            right--;
        }
    }

    return nums;
}
```

**Algorithm trace** (`nums = [2, 1, 3, 4, 5, 6]` — chosen to exercise all three branches of the `if / else if / else`):

```
Initial:
nums: [2, 1, 3, 4, 5, 6]
       L              R
(left=0, right=5)

nums[L]=2 is even -> left++
nums: [2, 1, 3, 4, 5, 6]
       L              R
(after: left=1, right=5)

nums[L]=1 is odd, nums[R]=6 is even -> swap
nums: [2, 1, 3, 4, 5, 6]
          L           R
nums: [2, 6, 3, 4, 5, 1]
(after swap: left=2, right=4)

nums[R]=5 is odd -> right--
nums: [2, 6, 3, 4, 5, 1]
             L     R
(after: left=2, right=3)

nums[L]=3 is odd, nums[R]=4 is even -> swap
nums: [2, 6, 3, 4, 5, 1]
             L  R
nums: [2, 6, 4, 3, 5, 1]
(after swap: left=3, right=2 -> L >= R, loop ends)
```
→ return `[2, 6, 4, 3, 5, 1]` — a valid arrangement (evens `2, 6, 4` before odds `3, 5, 1`), achieved with zero extra array allocation.

### 2.1 Refresher: quicksort's partition step

Quicksort sorts by repeatedly **partitioning**: pick a `pivot` value, then rearrange the array so everything `< pivot` ends up on its left and everything `>= pivot` ends up on its right. It doesn't matter *how* the two sides are internally ordered yet — only that the split is correct. Once partitioned, quicksort recurses into each side and partitions again, until every side is a single element.

```java
static void quicksort(int[] arr, int lo, int hi) {
    if (lo >= hi) return;

    int pivot = arr[hi];   // pick the last element as pivot
    int boundary = lo;     // everything before `boundary` is < pivot

    for (int i = lo; i < hi; i++) {
        if (arr[i] < pivot) {
            swap(arr, i, boundary);
            boundary++;
        }
    }
    swap(arr, boundary, hi); // put pivot in its final position

    quicksort(arr, lo, boundary - 1);  // recurse left of pivot
    quicksort(arr, boundary + 1, hi);  // recurse right of pivot
}
```

That inner loop — scan, test a condition, swap into place, advance a boundary — is exactly the same *shape* as the Optimal Approach above, just done with two pointers closing in from both ends instead of one pointer sweeping left to right:

| Quicksort partition | This problem's partition |
|---|---|
| condition: `arr[i] < pivot` | condition: `nums[i] % 2 == 0` |
| one pointer (`i`) sweeps left→right, one boundary (`boundary`) trails behind it | two pointers (`left`, `right`) sweep toward each other |
| swap moves a "belongs-on-the-left" value into place | swap moves an even value to the left side, odd to the right |
| result: `< pivot` before `boundary`, `>= pivot` after | result: evens before `left`/`right` meeting point, odds after |

We don't need the recursive step here — there's no "pivot" to divide the problem into smaller sub-problems, since parity is a simple binary condition, not an ordering relative to a chosen value. So this problem only borrows quicksort's **partition** mechanism, not the full sort.

## 3. Alternative Approaches

**A. Sort with a custom comparator**

Box `nums` into `Integer[]`, then `Arrays.sort` with a comparator that treats even values as "less than" odd values (e.g. compare `n % 2` for each element). This produces a valid parity-partitioned array as a side effect of sorting, without writing partition logic by hand.

**Time complexity:** O(n log n) — comparison-based sort dominates.
**Space complexity:** O(n) — boxing primitives into `Integer[]` plus the sort's internal buffer (Java's `Arrays.sort` for objects is a TimSort variant, not in-place).
**When acceptable:** Fine under interview time pressure when the parity-comparator trick is faster to reach for than deriving the two-pointer partition, or when `n` is small enough that O(n log n) vs O(n) doesn't matter in practice.

```java
public int[] sortArrayByParity(int[] nums) {
    Integer[] boxed = Arrays.stream(nums).boxed().toArray(Integer[]::new);
    Arrays.sort(boxed, (a, b) -> (a % 2) - (b % 2));
    return Arrays.stream(boxed).mapToInt(Integer::intValue).toArray();
}
```

**Algorithm trace:** Skipped — a general-purpose sort's internal comparisons aren't a meaningful step-by-step trace for this problem; the comparator itself (`(a % 2) - (b % 2)`) is the whole story.

**B. Two-pass counting copy (stable partition)**

First pass counts how many elements are even to know where the "odd" region should start in the output array. Second pass walks `nums` again, placing each even value at the next free front slot and each odd value at the next free slot in the odd region — preserving the original relative order within each parity group (unlike the swap-based approaches above, which don't guarantee stability).

**Time complexity:** O(n) — two linear passes over `nums`.
**Space complexity:** O(n) — a new output array of size `n`.
**When acceptable:** Whenever preserving the original relative order of evens and odds matters (the LeetCode problem doesn't require it, but a variant or follow-up interview question might).

```java
public int[] sortArrayByParity(int[] nums) {
    int evenCount = 0;
    for (int num : nums) {
        if (num % 2 == 0) evenCount++;
    }

    int[] out = new int[nums.length];
    int evenIdx = 0;
    int oddIdx = evenCount;

    for (int num : nums) {
        if (num % 2 == 0) {
            out[evenIdx++] = num;
        } else {
            out[oddIdx++] = num;
        }
    }

    return out;
}
```

**Algorithm trace** (`nums = [3, 1, 2, 4]`, `evenCount = 2` so odds start at index `2`):

```
Step i=0: nums[0]=3 (odd)
nums: [3, 1, 2, 4]
       i
out:  [_, _, 3, _]
(evenIdx=0, oddIdx=3)

Step i=1: nums[1]=1 (odd)
nums: [3, 1, 2, 4]
          i
out:  [_, _, 3, 1]
(evenIdx=0, oddIdx=4)

Step i=2: nums[2]=2 (even)
nums: [3, 1, 2, 4]
             i
out:  [2, _, 3, 1]
(evenIdx=1, oddIdx=4)

Step i=3: nums[3]=4 (even)
nums: [3, 1, 2, 4]
                i
out:  [2, 4, 3, 1]
(evenIdx=2, oddIdx=4)
```
→ return `[2, 4, 3, 1]` — notice the evens (`2, 4`) and odds (`3, 1`) each keep their original relative order, unlike the swap-based optimal approach used in the current `Solution.java`.
