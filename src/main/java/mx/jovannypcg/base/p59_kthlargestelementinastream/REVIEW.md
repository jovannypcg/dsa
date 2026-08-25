| | |
|---|---|
| **Solved on** | 2026-08-25 |
| **DSA Category** | Heap / Priority Queue |

## 1. Your Solution Assessment

**Correctness:** Correct. A min-heap bounded to size `k` always has, as its root, the `k`th largest element seen so far — every element smaller than the root has already been evicted, and everything remaining in the heap is `>=` the root, with exactly `k` such elements. This handles duplicates naturally (the heap is a multiset), negative values, and the `k == nums.length + 1` boundary (the heap just never exceeds `nums.length` elements until enough `add` calls arrive).

**Code quality:** Clean and idiomatic. Reusing `add` inside the constructor to seed the heap from `nums` avoids duplicating the push/evict logic. `PriorityQueue<Integer>` defaults to natural (ascending) ordering, which is exactly the min-heap behavior needed here — worth knowing this is relying on that default rather than an explicit comparator.

**Time complexity:** `O(log k)` per `add` call — each call does at most one `offer` and one `poll` on a heap of size <= `k`, both `O(log k)`. Construction from `nums` is `O(n log k)` for `n = nums.length`, since it's `n` calls to `add`.

**Space complexity:** `O(k)` — the heap never holds more than `k` elements.

**Algorithm trace** (constructor + first `add`, using `k = 3`, `nums = [4, 5, 8, 2]`, then `add(3)`):

| Call | val | heap before | push | size | evict smallest? | heap after | returns |
|---|---|---|---|---|---|---|---|
| constructor | 4 | {} | 4 | 1 | no (1 <= 3) | {4} | — |
| constructor | 5 | {4} | 5 | 2 | no (2 <= 3) | {4, 5} | — |
| constructor | 8 | {4, 5} | 8 | 3 | no (3 <= 3) | {4, 5, 8} | — |
| constructor | 2 | {4, 5, 8} | 2 | 4 | **yes**, pop 2 | {4, 5, 8} | — |
| `add(3)` | 3 | {4, 5, 8} | 3 | 4 | **yes**, pop 3 | {4, 5, 8} | peek = **4** |

→ `add(3)` returns `4`, matching the expected output.

## 2. Optimal Approach

This is the optimal approach, and it's what you implemented: keep a **min-heap of size `k`** over the values seen so far. When a new value arrives, push it; if the heap now holds more than `k` elements, pop the smallest. The root of the heap is always the `k`th largest element among everything inserted, because the heap only ever discards values that are smaller than at least `k` other values already tracked.

The key insight is that you never need to know the full order of all elements — you only need to track the smallest member of the "top `k`" set, which a bounded min-heap gives you for free at the root.

**Time complexity:** `O(log k)` per `add` — one `offer` plus at most one `poll`, each `O(log k)` on a heap capped at size `k`.
**Space complexity:** `O(k)` — the heap holds at most `k` elements regardless of how many scores have streamed in.

```java
import java.util.PriorityQueue;

public class KthLargest {
    private final int k;
    private final PriorityQueue<Integer> minHeap;

    public KthLargest(int k, int[] nums) {
        this.k = k;
        this.minHeap = new PriorityQueue<>();

        for (int num : nums) {
            add(num);
        }
    }

    public int add(int val) {
        minHeap.offer(val);

        if (minHeap.size() > k) {
            minHeap.poll();
        }

        return minHeap.peek();
    }
}
```

**Algorithm trace** (`k = 4`, `nums = [7, 7, 7, 7, 8, 3]`, then `add(2)`):

| Call | val | heap before | push | size | evict smallest? | heap after | returns |
|---|---|---|---|---|---|---|---|
| constructor | 7 | {} | 7 | 1 | no | {7} | — |
| constructor | 7 | {7} | 7 | 2 | no | {7, 7} | — |
| constructor | 7 | {7, 7} | 7 | 3 | no | {7, 7, 7} | — |
| constructor | 7 | {7, 7, 7} | 7 | 4 | no (4 <= 4) | {7, 7, 7, 7} | — |
| constructor | 8 | {7, 7, 7, 7} | 8 | 5 | **yes**, pop 7 | {7, 7, 7, 8} | — |
| constructor | 3 | {7, 7, 7, 8} | 3 | 5 | **yes**, pop 3 | {7, 7, 7, 8} | — |
| `add(2)` | 2 | {7, 7, 7, 8} | 2 | 5 | **yes**, pop 2 | {7, 7, 7, 8} | peek = **7** |

→ `add(2)` returns `7`, matching the expected output.

## 3. Alternative Approaches

### Brute force: re-sort on every call
Keep all seen values in a list. On every `add`, append the new value, sort the whole list descending, and index into position `k - 1`.

- **Time complexity:** `O(n log n)` per `add`, where `n` is the number of elements seen so far — dominated by the sort.
- **Space complexity:** `O(n)` to store every element.
- **When acceptable:** Only for a tiny number of `add` calls or as a first-pass answer under interview time pressure to prove correctness before optimizing. With up to `10^4` calls on a growing stream, this degrades badly (`O(n^2 log n)` overall).

**Algorithm trace** (`k = 3`, list starts `[4, 5, 8, 2]`, then `add(3)`):

| Call | list before | list after append | sorted desc | index k-1 (=2) | returns |
|---|---|---|---|---|---|
| `add(3)` | [4, 5, 8, 2] | [4, 5, 8, 2, 3] | [8, 5, 4, 3, 2] | index 2 → 4 | **4** |

### Maintain a sorted structure (e.g. `TreeMap<Integer, Integer>` as a multiset)
Store value → count in a `TreeMap`, which keeps keys sorted. On `add`, insert/increment in `O(log n)`. To answer the query, walk the map from the largest key downward, subtracting counts until you've passed `k` elements.

- **Time complexity:** `O(log n)` for the insert, but `O(k)` in the worst case to walk down to the `k`th element on each query (or `O(n)` if you don't cap the walk), since the map isn't bounded to size `k`.
- **Space complexity:** `O(n)` — every distinct value seen is tracked (with counts), unlike the bounded min-heap.
- **When acceptable:** If you also need other order-statistics (e.g., "how many scores are above X") beyond just the `k`th largest, since a `TreeMap` supports richer range queries than a size-bounded heap. For this problem alone, the min-heap is strictly better — smaller memory footprint and no per-query walk.

**Algorithm trace** (`k = 3`, map starts `{2:1, 4:1, 5:1, 8:1}`, then `add(3)`):

| Call | map before | map after insert | walk from top | count consumed | returns |
|---|---|---|---|---|---|
| `add(3)` | {2:1, 4:1, 5:1, 8:1} | {2:1, 3:1, 4:1, 5:1, 8:1} | 8(1) → 5(2) → 4(3) | 3 | **4** |
