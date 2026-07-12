| | |
|---|---|
| **Solved on** | 2026-07-07 |
| **DSA Category** | Two Pointers |

## 1. Your Solution Assessment

**Correctness:** Handles all cases correctly, including `m = 0`, `n = 0`, duplicate values, negative values, and the `±10^9` boundary values from the constraints. The comparison `nums1[a] > nums2[b]` (strict) correctly breaks ties in favor of `nums2[b]`, which is safe since either choice preserves sort order when the values are equal.

**Code quality:** Clean and readable. Variable names `a`, `b`, `r` are explained with inline comments, and the two early-exit branches (`a < 0`, `b < 0`) are commented clearly. The `nums1[r--] = ...` post-decrement idiom is used consistently.

**Time complexity:** O(m + n) — each element of `nums1`'s real portion and each element of `nums2` is visited and written exactly once.

**Space complexity:** O(1) — merging happens in place inside `nums1`; no auxiliary array is allocated.

**Algorithm trace** (merge-from-the-back, three pointers):
Input: `nums1 = [1,2,3,0,0,0]`, `m = 3`, `nums2 = [2,5,6]`, `n = 3`

Each block below shows the state **after** that step's write and pointer updates — `a`, `b`, and `r` are always moved together so the markers match exactly what the array already contains.

```
Start: a points to last real nums1 element, b to last nums2 element, r to last slot of nums1
nums1: [1, 2, 3, 0, 0, 0]
              a        r
nums2: [2, 5, 6]
              b

nums1[a]=3 > nums2[b]=6? No → write nums2[b] at r, b--, r--
nums1: [1, 2, 3, 0, 0, 6]
              a     r
nums2: [2, 5, 6]
           b

nums1[a]=3 > nums2[b]=5? No → write nums2[b] at r, b--, r--
nums1: [1, 2, 3, 0, 5, 6]
              a  r
nums2: [2, 5, 6]
        b

nums1[a]=3 > nums2[b]=2? Yes → write nums1[a] at r, a--, r--
nums1: [1, 2, 3, 3, 5, 6]
           a  r
nums2: [2, 5, 6]
        b

nums1[a]=2 > nums2[b]=2? No → write nums2[b] at r, b--, r--
nums1: [1, 2, 2, 3, 5, 6]
           a
           r
nums2: [2, 5, 6]
        (b = -1, exhausted)

b < 0 → write nums1[a] at r, a--, r--
nums1: [1, 2, 2, 3, 5, 6]
        a
        r

b < 0 → write nums1[a] at r, a--, r--
nums1: [1, 2, 2, 3, 5, 6]
(a = -1, r = -1, loop ends)
```
→ final `nums1 = [1, 2, 2, 3, 5, 6]`

Note: the final two writes above are no-ops (`nums1[r] = nums1[a]` where `r == a` already) — this is expected once the "gap" between the merged and unmerged portions closes, and doesn't affect correctness.

## 2. Optimal Approach

This **is** the optimal approach: merge from the back using three pointers so that values are always read before they're overwritten, achieving O(1) extra space. Filling from the front would require a temporary buffer, since writing the smallest merged value into `nums1[0]` could destroy a `nums1` value that hasn't been compared yet.

- **Time complexity:** O(m + n) — one pass, each element placed exactly once.
- **Space complexity:** O(1) — in-place, no auxiliary storage.

```java
public void merge(int[] nums1, int m, int[] nums2, int n) {
    int a = m - 1;
    int b = n - 1;
    int write = m + n - 1;

    while (b >= 0) {
        if (a >= 0 && nums1[a] > nums2[b]) {
            nums1[write--] = nums1[a--];
        } else {
            nums1[write--] = nums2[b--];
        }
    }
}
```

This variant loops `while (b >= 0)` instead of `while (a >= 0 || b >= 0)` — once `nums2` is exhausted, the remaining `nums1` elements are already in their correct final positions (they're being compared against nothing and would just be copied onto themselves), so the loop can stop early. It's the same algorithm as your solution, just with a slightly tighter loop condition.

**Algorithm trace:** identical to the trace above — same three-pointer merge-from-the-back strategy, same intermediate states.

## 3. Alternative Approaches

### A. Copy + merge into a temporary buffer
Copy `nums1`'s first `m` elements into a temp array, then merge `temp` and `nums2` front-to-back into `nums1` using the standard two-pointer merge (like the merge step of merge sort).

- **Time complexity:** O(m + n) — same single pass, just front-to-back instead of back-to-front.
- **Space complexity:** O(m) — the temporary copy of `nums1`'s real elements.
- **When acceptable:** Fine under interview time pressure if the in-place back-to-front trick doesn't come to mind immediately — it's a natural first correct solution before optimizing to O(1) space.

**Algorithm trace:**
Input: `nums1 = [1,2,3,0,0,0]`, `m = 3`, `nums2 = [2,5,6]`, `n = 3`

Each block shows the state **after** that step's write and pointer updates, so `i`/`j` always match what `nums1 so far` already contains.

```
temp = [1, 2, 3]  (copy of nums1[0..m-1])
i=0 (temp), j=0 (nums2), k=0 (write index into nums1)

temp[i]=1 <= nums2[j]=2 → take from temp → write 1, i++, k++
temp: [1, 2, 3]
          i
nums2: [2, 5, 6]
        j
nums1 so far: [1, _, _, _, _, _]

temp[i]=2 <= nums2[j]=2 → take from temp → write 2, i++, k++
temp: [1, 2, 3]
             i
nums2: [2, 5, 6]
        j
nums1 so far: [1, 2, _, _, _, _]

temp[i]=3 > nums2[j]=2 → take from nums2 → write 2, j++, k++
temp: [1, 2, 3]
             i
nums2: [2, 5, 6]
           j
nums1 so far: [1, 2, 2, _, _, _]

temp[i]=3 <= nums2[j]=5 → take from temp → write 3, i++, k++
temp: [1, 2, 3]
(i = 3, exhausted)
nums2: [2, 5, 6]
           j
nums1 so far: [1, 2, 2, 3, _, _]

temp exhausted → copy remaining nums2[j..] = [5, 6]
nums1 so far: [1, 2, 2, 3, 5, 6]
```
→ final `nums1 = [1, 2, 2, 3, 5, 6]`

### B. Concatenate then sort
Overwrite the trailing zeros in `nums1` with `nums2`'s elements, then sort all of `nums1`.

- **Time complexity:** O((m + n) log(m + n)) — dominated by the sort.
- **Space complexity:** O(log(n)) to O(n) depending on the sort algorithm's internal stack/buffer (e.g. `Arrays.sort` on primitives uses a dual-pivot quicksort variant with O(log n) space).
- **When acceptable:** Only for a quick first pass to get something working, or if `m` and `n` are tiny and clarity matters more than performance — it ignores the fact that both inputs are already sorted, which is the whole point of the problem.

**Algorithm trace:**
Input: `nums1 = [1,2,3,0,0,0]`, `m = 3`, `nums2 = [2,5,6]`, `n = 3`

```
Step 1 — copy nums2 into the trailing zeros of nums1:
nums1: [1, 2, 3, 2, 5, 6]

Step 2 — sort nums1 in place:
nums1: [1, 2, 2, 3, 5, 6]
```
→ final `nums1 = [1, 2, 2, 3, 5, 6]`
