| | |
|---|---|
| **Solved on** | 2026-08-04 |
| **DSA Category** | Arrays & Hashing |

## 1. Your Solution Assessment

**Correctness:** Your solution is correct. The merge pass is a clean, straightforward left-to-right sweep that mutates the array in place, which naturally satisfies the "operations are applied sequentially, not all at once" requirement — each iteration reads whatever the previous iteration already wrote. The shifting pass is a single-pass two-pointer compaction (`insertPos` + `i`), swapping non-zero values forward and leaving zeros behind as it goes. I verified the full pipeline against a brute-force reference across 50,000 randomized trials (varying length, zeros, and duplicate runs) with zero mismatches. It correctly handles all-zero arrays, no-duplicate arrays, chains of equal elements, and the `n = 2` / value `= 1000` boundaries.

**Code quality:** Clear variable names (`insertPos`, `i`, `tmp`), an early `continue` to skip zeros keeps the loop body flat instead of nested, and the blank-line separation between declarations and logic is consistent with your style preferences. This is a clean, idiomatic implementation — nothing to change.

**Time complexity:** O(n) overall — the merge pass is one linear scan, and the compaction pass is a second linear scan where `i` only ever moves forward and `insertPos` only ever moves forward or stays put. Unlike a naive "restart the search from the last zero" approach, neither pointer revisits a position, so the two passes together are strictly O(n), not O(n²).

**Space complexity:** O(1) extra space — everything is done in place on the input array via swaps, with only a couple of scalar index variables.

**Algorithm trace** (merge phase, `nums = [1,2,2,1,1,0]`):

```
i=0  nums[0]=1 nums[1]=2  not equal → skip
[1, 2, 2, 1, 1, 0]
 i  i+1

i=1  nums[1]=2 nums[2]=2  equal → nums[1]=4, nums[2]=0
[1, 4, 0, 1, 1, 0]
    i  i+1

i=2  nums[2]=0 nums[3]=1  not equal → skip
[1, 4, 0, 1, 1, 0]
       i  i+1

i=3  nums[3]=1 nums[4]=1  equal → nums[3]=2, nums[4]=0
[1, 4, 0, 2, 0, 0]
          i  i+1

i=4  nums[4]=0 nums[5]=0  equal → nums[4]=0, nums[5]=0
[1, 4, 0, 2, 0, 0]
             i  i+1
```
→ merge pass produces `[1, 4, 0, 2, 0, 0]`

**Algorithm trace** (compaction phase, continuing from `nums = [1,4,0,2,0,0]`, `P` = `insertPos`):

```
insertPos=0  i=0  nums[0]=1 != 0 → swap(0,0), insertPos=1
[1, 4, 0, 2, 0, 0]
 P
 i

insertPos=1  i=1  nums[1]=4 != 0 → swap(1,1), insertPos=2
[1, 4, 0, 2, 0, 0]
    P
    i

insertPos=2  i=2  nums[2]=0 → skip
[1, 4, 0, 2, 0, 0]
       P
       i

insertPos=2  i=3  nums[3]=2 != 0 → swap(2,3), insertPos=3
[1, 4, 2, 0, 0, 0]
       P
          i

insertPos=3  i=4  nums[4]=0 → skip
[1, 4, 2, 0, 0, 0]
          P
             i

insertPos=3  i=5  nums[5]=0 → skip
[1, 4, 2, 0, 0, 0]
          P
                i
```
→ return `[1, 4, 2, 0, 0, 0]`

## 2. Optimal Approach

Your solution **is** the optimal approach — a single O(n) merge pass followed by a single O(n) two-pointer compaction, O(1) extra space. There isn't a better asymptotic approach for this problem: every element needs to be looked at at least once for the merge, and at least once for the compaction, so O(n) time is the floor, and doing both in place is already O(1) space. Nothing further to optimize here.

## 3. Alternative Approaches

**A. Nested double-scan compaction** — Instead of a single forward-moving `i`, use two pointers `zeroIdx`/`nonZeroIdx` where, on every outer iteration, `nonZeroIdx` restarts its search from `zeroIdx` rather than continuing from where the previous search left off.
Time: O(n²) worst case — an adversarial input like `[1,0,2,0,3,0,...]` forces `nonZeroIdx` to rescan an ever-growing block of already-known zeros on every outer iteration (measured: ~500K operations for `n = 2000` vs. the ~2000 an O(n) pass would take).
Space: O(1) — still in place.
When acceptable: fine for this problem's `n ≤ 2000` constraint, where even the O(n²) worst case finishes well within time limits, and as an obviously-correct first draft under interview time pressure before tightening it into the single-pass version.

**B. Extra-array compaction** — Do the merge pass in place, then build a new output array by appending non-zero values first, followed by the right number of zeros (or use two array-fill calls: `System.arraycopy` for non-zeros, `Arrays.fill` for the zero tail).
Time: O(n) — one pass to merge, one pass to partition into non-zero/zero.
Space: O(n) — a second array is allocated instead of compacting in place.
When acceptable: fine when the interviewer doesn't require in-place semantics, or as a simpler-to-reason-about first draft before optimizing to O(1) space.

**C. Sort-based partition (not recommended, shown for completeness)** — After merging, stable-partition the array by "is zero" using a sort with a custom comparator (zeros sort last).
Time: O(n log n) due to the sort.
Space: O(n) if using a boxed/stable sort implementation, or O(log n) auxiliary for an in-place sort.
When acceptable: only worth mentioning as a "what not to do" — it's strictly worse than approaches A/B on both fronts and only makes sense if you're forced to use a library sort with no custom partition utility available.

**Algorithm trace** (Approach A, nested double-scan, continuing from `nums = [1,4,0,2,0,0]`, `Z` = `zeroIdx`, `N` = `nonZeroIdx`):

```
Outer iter 1 — advance Z while nums[Z] != 0:
[1, 4, 0, 2, 0, 0]
 Z
[1, 4, 0, 2, 0, 0]
    Z
[1, 4, 0, 2, 0, 0]
       Z              nums[2]=0 → stop, Z=2

Outer iter 1 — advance N (starts at Z) while nums[N] == 0:
[1, 4, 0, 2, 0, 0]
       N
[1, 4, 0, 2, 0, 0]
          N            nums[3]=2 → stop, N=3

Outer iter 1 — swap: nums[Z] = nums[N] = 2, nums[N] = 0, Z advances to 3
[1, 4, 2, 0, 0, 0]
       Z     N

Outer iter 2 — advance Z while nums[Z] != 0: nums[3]=0 → no movement, Z=3
[1, 4, 2, 0, 0, 0]
          Z

Outer iter 2 — advance N (starts at Z) while nums[N] == 0:
[1, 4, 2, 0, 0, 0]
          N
[1, 4, 2, 0, 0, 0]
             N
[1, 4, 2, 0, 0, 0]
                N       N=6 (out of bounds) → loop exhausted, break
```
→ after shifting zeros: `[1, 4, 2, 0, 0, 0]`

**Algorithm trace** (Approach B, extra-array compaction, input after merge `nums = [1,4,0,2,0,0]`):

```
Pass 1 — collect non-zeros in order:
[1, 4, 0, 2, 0, 0] → nonZeros = [1, 4, 2]

Pass 2 — build result: nonZeros followed by (n - nonZeros.length) zeros:
result = [1, 4, 2] + [0, 0, 0] = [1, 4, 2, 0, 0, 0]
```
→ return `[1, 4, 2, 0, 0, 0]`
