# Top K Frequent Elements — Review

| | |
|---|---|
| **Solved on** | 2026-06-10 |
| **DSA Category** | Arrays & Hashing |

---

## 1. Your Solution Assessment

### Thought process comment
The trace is clear and shows the right mental model: build frequencies first, then use a min-heap to maintain the top k. The final state `minHeap = [2 -> 2, 3 -> 3]` is accurate — you correctly reasoned that when the third entry (1→1) was polled, the heap settled into the right two winners. One small typo in `getFrequencies`: the comment says "n = number of items in n" — should be "in nums".

### Correctness
Correct. The min-heap of size k invariant works: always poll the lowest-frequency entry when the heap exceeds k, so only the k highest remain. All constraints are handled, including negatives and k equal to the distinct count.

### Code quality
- `getFrequencies` is a well-named, cleanly extracted helper.
- The lambda comparator works but is more verbosely expressed than necessary. The idiomatic one-liner: `Comparator.comparingInt(Map.Entry::getValue)`
- `getOrDefault` is clear; `frequencies.merge(num, 1, Integer::sum)` is a common alternative.

### Time complexity
**O(n log k)** — building the frequency map is O(n). The heap loop iterates over at most d distinct elements (d ≤ n), each offer/poll is O(log k), giving O(d log k) ⊆ O(n log k). Draining the heap is O(k log k).

### Space complexity
**O(n)** — frequency map stores up to n distinct keys; the heap holds at most k+1 entries at any point.

**Algorithm trace** — Input: `nums = [1, 2, 2, 3, 3, 3]`, `k = 2`

Frequencies: `{1→1, 2→2, 3→3}`

| entry | heap before offer | heap after offer | size > k? | heap after poll |
|-------|------------------|-----------------|-----------|-----------------|
| 1→1 | [] | [1→1] | No (1 ≤ 2) | [1→1] |
| 2→2 | [1→1] | [1→1, 2→2] | No (2 ≤ 2) | [1→1, 2→2] |
| 3→3 | [1→1, 2→2] | [1→1, 2→2, 3→3] | Yes (3 > 2) → poll min 1→1 | [2→2, 3→3] |

Drain heap: `result = [2, 3]`

---

## 2. Optimal Approach

**Bucket sort** achieves O(n) time by exploiting the bounded frequency range. Every frequency value is between 1 and `nums.length`, so you can use the frequency as a direct array index. Fill buckets, then sweep from the highest index down to collect k elements.

**Time:** O(n) — one pass to count, one pass to bucket, one sweep to collect.
**Space:** O(n) — frequency map + n+1 buckets.

```java
public int[] topKFrequent(int[] nums, int k) {
    Map<Integer, Integer> frequencies = new HashMap<>();
    for (int num : nums) frequencies.merge(num, 1, Integer::sum);

    List<Integer>[] buckets = new List[nums.length + 1];
    for (Map.Entry<Integer, Integer> e : frequencies.entrySet()) {
        int freq = e.getValue();
        if (buckets[freq] == null) buckets[freq] = new ArrayList<>();
        buckets[freq].add(e.getKey());
    }

    int[] result = new int[k];
    int idx = 0;
    for (int freq = buckets.length - 1; freq >= 1 && idx < k; freq--) {
        if (buckets[freq] != null) {
            for (int num : buckets[freq]) {
                result[idx++] = num;
                if (idx == k) break;
            }
        }
    }
    return result;
}
```

**Algorithm trace** — Input: `nums = [1, 2, 2, 3, 3, 3]`, `k = 2`

Frequencies: `{1→1, 2→2, 3→3}`

| freq | bucket[freq] |
|------|-------------|
| 1 | [1] |
| 2 | [2] |
| 3 | [3] |

Sweep from freq=6 down:
| freq | bucket | idx | result |
|------|--------|-----|--------|
| 6..4 | null | 0 | [] |
| 3 | [3] | 0→1 | [3] |
| 2 | [2] | 1→2 | [3, 2] |
| idx==k → stop | | | |

→ return `[3, 2]`

---

## 3. Alternative Approaches

### Sort all entries by frequency descending

Build the frequency map, collect entries into a list, sort by value descending, take the first k keys.

- **Time:** O(n log n) — sorting all distinct entries.
- **Space:** O(n) — list of all entries.
- **When acceptable:** Simple to write under time pressure; fine when n is small or k is close to n.

**Algorithm trace** — Input: `nums = [1, 2, 2, 3, 3, 3]`, `k = 2`

| step | entries | after sort (desc by freq) | take k=2 |
|------|---------|--------------------------|----------|
| build freq | {1→1, 2→2, 3→3} | | |
| sort desc | | [(3,3),(2,2),(1,1)] | |
| take first 2 | | | [3, 2] |

→ return `[3, 2]`

---

### Max-heap — poll k times

Add all frequency entries to a max-heap (reverse comparator), then poll exactly k times.

- **Time:** O(n + k log n) — O(n) to heapify all entries, O(k log n) to poll k times.
- **Space:** O(n) — heap holds all distinct entries.
- **When acceptable:** Intuitive approach; slightly worse than min-heap of size k when k << n, but easier to reason about at a glance.

**Algorithm trace** — Input: `nums = [1, 2, 2, 3, 3, 3]`, `k = 2`

Max-heap (by freq): `[3→3, 2→2, 1→1]`

| poll | result so far |
|------|--------------|
| 3→3 (freq=3, highest) | [3] |
| 2→2 (freq=2) | [3, 2] |
| k reached → stop | |

→ return `[3, 2]`
