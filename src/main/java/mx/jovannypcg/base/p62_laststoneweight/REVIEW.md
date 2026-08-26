| | |
|---|---|
| **Solved on** | 2026-08-25 |
| **DSA Category** | Heap / Priority Queue |

## 1. Your Solution Assessment

**Correctness:** Correct. All 10 tests pass, including the boundary cases (all-equal stones, min/max weights, and the 30-stone limit).

One subtle detail worth understanding: when the two heaviest stones are equal, `smash` is `0`, and your code still `offer`s that `0` back onto the heap instead of skipping the push. This doesn't produce a wrong answer — a `0`-weight stone smashed against any positive stone `x` just yields `x - 0 = x` again, so it's a no-op that costs one extra loop iteration. It does mean the heap can briefly hold more elements than the "true" stone count, and the final `peek()` can return `0` explicitly (rather than the loop ending on an empty heap). Not a bug, but skipping the push when `smash == 0` would save an iteration in the equal-stones case.

**Code quality:** Clear and readable. `getHeaviestStones` cleanly separates heap construction from the smashing loop, and the field-level `stoneWeightComparator` documents the max-heap intent well. Minor nit: the comparator and helper method don't need to be extracted for a solution this short, but it doesn't hurt readability.

**Time complexity:** O(n log n). Building the heap is O(n log n) via repeated `offer` (or O(n) with `PriorityQueue`'s bulk constructor, which isn't used here), and each of the up to n smash operations does two O(log n) polls and one O(log n) offer.

**Space complexity:** O(n) for the heap holding up to n stones.

**Algorithm trace** (stones = `[2, 7, 4, 1, 8, 1]`, heap shown sorted descending for readability):

| Iteration | Heap before | heaviest | secondHeaviest | smash | Heap after |
|---|---|---|---|---|---|
| 1 | [8, 7, 4, 2, 1, 1] | 8 | 7 | 1 | [4, 2, 1, 1, 1] |
| 2 | [4, 2, 1, 1, 1] | 4 | 2 | 2 | [2, 1, 1, 1] |
| 3 | [2, 1, 1, 1] | 2 | 1 | 1 | [1, 1, 1] |
| 4 | [1, 1, 1] | 1 | 1 | 0 | [1, 0] |
| 5 | [1, 0] | 1 | 0 | 1 | [1] |

Heap size is now 1, loop ends → return `1`. Note iteration 4 is the "wasted" step described above: it pushes `0` instead of ending with `[1]` directly, which iteration 5 then cleans up.

## 2. Optimal Approach

A max-heap is already the optimal approach for this problem — your solution has the right idea. The only refinement is to skip re-inserting the smash result when it's `0`, and to guard `peek()` for the (here, unreachable given `stones.length >= 1`, but good practice) empty-heap case.

Each turn, pop the two largest stones, compute their difference, and push the difference back only if it's positive. Repeat until at most one stone remains.

**Time complexity:** O(n log n) — n stones initially pushed (O(log n) each), and up to n − 1 smash rounds, each doing two O(log n) pops and at most one O(log n) push.

**Space complexity:** O(n) for the heap.

```java
public int lastStoneWeight(int[] stones) {
    PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

    for (int stone : stones) {
        maxHeap.offer(stone);
    }

    while (maxHeap.size() > 1) {
        int y = maxHeap.poll();
        int x = maxHeap.poll();
        int diff = y - x;

        if (diff > 0) {
            maxHeap.offer(diff);
        }
    }

    return maxHeap.isEmpty() ? 0 : maxHeap.peek();
}
```

**Algorithm trace** (stones = `[2, 7, 4, 1, 8, 1]`):

| Iteration | Heap before | y | x | diff | Pushed? | Heap after |
|---|---|---|---|---|---|---|
| 1 | [8, 7, 4, 2, 1, 1] | 8 | 7 | 1 | Yes | [4, 2, 1, 1, 1] |
| 2 | [4, 2, 1, 1, 1] | 4 | 2 | 2 | Yes | [2, 1, 1, 1] |
| 3 | [2, 1, 1, 1] | 2 | 1 | 1 | Yes | [1, 1, 1] |
| 4 | [1, 1, 1] | 1 | 1 | 0 | No | [1] |

Heap size is now 1, loop ends → return `1`. Same result, one fewer iteration than the trace above.

## 3. Alternative Approaches

### Sort-and-resmash simulation

Sort the array descending, smash the first two elements, insert the result back at its sorted position (via a linear scan or binary search), and repeat.

**Time complexity:** O(n² ) — up to n smash rounds, each doing an O(n) re-sort or O(n) insertion scan.

**Space complexity:** O(1) extra if done in-place on the array (ignoring the initial O(n log n) sort, or O(n) if using a new list/array each round).

**When acceptable:** Fine for the given constraints (`n <= 30`), and a reasonable fallback under interview time pressure if a heap isn't top of mind — but it doesn't scale as well as the heap approach.

**Algorithm trace** (stones = `[2, 7, 4, 1, 8, 1]`):

| Iteration | Array before (sorted desc) | Smash pair | Result | Array after |
|---|---|---|---|---|
| 1 | [8, 7, 4, 2, 1, 1] | (8, 7) | 1 | [4, 2, 1, 1, 1] |
| 2 | [4, 2, 1, 1, 1] | (4, 2) | 2 | [2, 1, 1, 1] |
| 3 | [2, 1, 1, 1] | (2, 1) | 1 | [1, 1, 1] |
| 4 | [1, 1, 1] | (1, 1) | 0 | [1] |

Array length is now 1 → return `1`.

### Brute-force linear scan (no sorting)

Each turn, do two linear passes over the array to find the indices of the largest and second-largest stones, compute the smash, write the result back into one slot, and shrink the effective array length by one (swap the removed stone to the end).

**Time complexity:** O(n²) — up to n rounds, each an O(n) scan.

**Space complexity:** O(1) extra — everything happens in-place on the input array.

**When acceptable:** Reasonable for small, bounded input like this problem's `n <= 30` constraint, or as a quick first pass to get a working solution before optimizing to the heap. Not recommended once `n` grows large, since it's asymptotically worse than the heap approach.

**Algorithm trace** (stones = `[2, 7, 4, 1, 8, 1]`, `len` = effective array length):

| Iteration | Active elements | Largest | 2nd largest | Result | len after |
|---|---|---|---|---|---|
| 1 | [2, 7, 4, 1, 8, 1] | 8 | 7 | 1 | 5 |
| 2 | [2, 1, 4, 1, 1] | 4 | 2 | 2 | 4 |
| 3 | [2, 1, 1, 1] | 2 | 1 | 1 | 3 |
| 4 | [1, 1, 1] | 1 | 1 | 0 | 2 |
| 5 | [1, 0] | 1 | 0 | 1 | 1 |

`len` is now 1 → return the remaining element, `1`.
