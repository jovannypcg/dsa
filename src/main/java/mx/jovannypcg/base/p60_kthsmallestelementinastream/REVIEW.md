| | |
|---|---|
| **Solved on** | 2026-08-25 |
| **DSA Category** | Heap / Priority Queue |

## 1. Your Solution Assessment

**Correctness:** Correct. A max-heap bounded to size `k` always has, as its root, the `k`th smallest element seen so far — every element larger than the root has already been evicted, and everything remaining in the heap is `<=` the root, with exactly `k` such elements. This is the exact mirror of the min-heap trick used for [Kth Largest Element in a Stream](../p59_kthlargestelementinastream), and it handles duplicates, negative values, and the `k == nums.length + 1` boundary the same way.

**Code quality:** Clean and correctly mirrors the largest-element solution. Reusing `add` inside the constructor avoids duplicating push/evict logic. The comparator `(a, b) -> Integer.compare(b, a)` correctly flips `PriorityQueue`'s default min-heap into a max-heap — equivalent to `Comparator.reverseOrder()`, and explicit about the flip, which is good for readability given this problem's whole point is "reverse the heap direction." One style note: the single-statement `if` blocks on lines 34 and 40 omit braces — harmless here, but brace-less `if`s are an easy place to introduce bugs later if a second statement gets added without adding braces too.

**Time complexity:** `O(log k)` per `add` call — one `offer` and at most one `poll` on a heap of size `<= k`. Construction from `nums` is `O(n log k)`.

**Space complexity:** `O(k)` — the heap never holds more than `k` elements.

**Algorithm trace** (constructor + first two `add` calls, using `k = 3`, `nums = [4, 5, 8, 7]`, then `add(6)`, `add(3)`):

| Call | val | heap before | push | size | evict largest? | heap after | returns |
|---|---|---|---|---|---|---|---|
| constructor | 4 | {} | 4 | 1 | no (1 <= 3) | {4} | — |
| constructor | 5 | {4} | 5 | 2 | no (2 <= 3) | {4, 5} | — |
| constructor | 8 | {4, 5} | 8 | 3 | no (3 <= 3) | {4, 5, 8} | — |
| constructor | 7 | {4, 5, 8} | 7 | 4 | **yes**, pop 8 | {4, 5, 7} | — |
| `add(6)` | 6 | {4, 5, 7} | 6 | 4 | **yes**, pop 7 | {4, 5, 6} | peek = **6** |
| `add(3)` | 3 | {4, 5, 6} | 3 | 4 | **yes**, pop 6 | {3, 4, 5} | peek = **5** |

→ `add(6)` returns `6` and `add(3)` returns `5`, matching the expected output.

## 2. Optimal Approach

This is the optimal approach, and it's what you implemented: keep a **max-heap of size `k`** over the values seen so far. When a new value arrives, push it; if the heap now holds more than `k` elements, pop the largest. The root of the heap is always the `k`th smallest element among everything inserted, because the heap only ever discards values larger than at least `k` other values already tracked.

The key insight is the mirror image of the largest-element case: you never need the full sorted order, only the largest member of the "bottom `k`" set — a bounded max-heap gives you that at the root.

**Time complexity:** `O(log k)` per `add` — one `offer` plus at most one `poll`, each `O(log k)` on a heap capped at size `k`.
**Space complexity:** `O(k)` — the heap holds at most `k` elements regardless of how many scores have streamed in.

```java
import java.util.Comparator;
import java.util.PriorityQueue;

public class KthSmallest {
    private final int k;
    private final PriorityQueue<Integer> maxHeap;

    public KthSmallest(int k, int[] nums) {
        this.k = k;
        this.maxHeap = new PriorityQueue<>(Comparator.reverseOrder());

        for (int num : nums) {
            add(num);
        }
    }

    public int add(int val) {
        maxHeap.offer(val);

        if (maxHeap.size() > k) {
            maxHeap.poll();
        }

        return maxHeap.peek();
    }
}
```

**Algorithm trace** (`k = 4`, `nums = [-7, -7, -7, -7, -8, -3]`, then `add(-2)`, `add(-10)`):

| Call | val | heap before | push | size | evict largest? | heap after | returns |
|---|---|---|---|---|---|---|---|
| constructor | -7 | {} | -7 | 1 | no | {-7} | — |
| constructor | -7 | {-7} | -7 | 2 | no | {-7, -7} | — |
| constructor | -7 | {-7, -7} | -7 | 3 | no | {-7, -7, -7} | — |
| constructor | -7 | {-7, -7, -7} | -7 | 4 | no (4 <= 4) | {-7, -7, -7, -7} | — |
| constructor | -8 | {-7, -7, -7, -7} | -8 | 5 | **yes**, pop -7 | {-8, -7, -7, -7} | — |
| constructor | -3 | {-8, -7, -7, -7} | -3 | 5 | **yes**, pop -3 | {-8, -7, -7, -7} | — |
| `add(-2)` | -2 | {-8, -7, -7, -7} | -2 | 5 | **yes**, pop -2 | {-8, -7, -7, -7} | peek = **-7** |
| `add(-10)` | -10 | {-8, -7, -7, -7} | -10 | 5 | **yes**, pop -7 | {-10, -8, -7, -7} | peek = **-7** |

→ `add(-2)` and `add(-10)` both return `-7`, matching the expected output.

## 3. Alternative Approaches

### Brute force: re-sort on every call
Keep all seen values in a list. On every `add`, append the new value, sort the whole list ascending, and index into position `k - 1`.

- **Time complexity:** `O(n log n)` per `add`, where `n` is the number of elements seen so far — dominated by the sort.
- **Space complexity:** `O(n)` to store every element.
- **When acceptable:** Only for a tiny number of `add` calls, or as a correctness-first draft under interview time pressure before optimizing to the bounded heap. With up to `10^4` calls, this is `O(n^2 log n)` overall and degrades badly.

**Algorithm trace** (`k = 3`, list starts `[4, 5, 8, 7]`, then `add(6)`):

| Call | list before | list after append | sorted asc | index k-1 (=2) | returns |
|---|---|---|---|---|---|
| `add(6)` | [4, 5, 8, 7] | [4, 5, 8, 7, 6] | [4, 5, 6, 7, 8] | index 2 → 6 | **6** |

### Maintain a sorted structure (e.g. `TreeMap<Integer, Integer>` as a multiset)
Store value → count in a `TreeMap`, which keeps keys sorted ascending. On `add`, insert/increment in `O(log n)`. To answer the query, walk the map from the smallest key upward, subtracting counts until you've passed `k` elements.

- **Time complexity:** `O(log n)` for the insert, but `O(k)` in the worst case to walk up to the `k`th element on each query, since the map isn't bounded to size `k`.
- **Space complexity:** `O(n)` — every distinct value seen is tracked (with counts), unlike the bounded max-heap.
- **When acceptable:** If you also need other order-statistics beyond just the `k`th smallest (e.g., "how many scores are below X"), since a `TreeMap` supports richer range queries than a size-bounded heap. For this problem alone, the max-heap is strictly better — smaller memory footprint and no per-query walk.

**Algorithm trace** (`k = 3`, map starts `{4:1, 5:1, 7:1, 8:1}`, then `add(6)`):

| Call | map before | map after insert | walk from bottom | count consumed | returns |
|---|---|---|---|---|---|
| `add(6)` | {4:1, 5:1, 7:1, 8:1} | {4:1, 5:1, 6:1, 7:1, 8:1} | 4(1) → 5(2) → 6(3) | 3 | **6** |
