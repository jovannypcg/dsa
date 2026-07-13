| | |
|---|---|
| **Solved on** | 2026-07-12 |
| **DSA Category** | Greedy |

## 1. Your Solution Assessment

**Correctness:** The implementation is correct. It handles all cases from the constraints:
- Empty `s` (`cookie < s.length` guards the loop, returns 0).
- No cookie big enough for any child (loop advances `cookie` without ever matching, returns 0).
- All children satisfiable (loop terminates on `child == g.length`).
- Duplicate greed factors / cookie sizes (sorting handles ties naturally; each cookie is only ever consumed once).
- `Integer.MAX_VALUE` values (no overflow risk since the comparison `s[cookie] >= g[child]` never adds or multiplies).

**Code quality:** Clear and idiomatic. Variable names (`child`, `cookie`, `count`) are self-explanatory. The comments explain the *why* (e.g. why skipping to the next cookie is safe) without restating the obvious. One nitpick: the inline comment on line 19-20 restates what `Arrays.sort` already says — could be trimmed, but it's harmless.

**Time complexity:** O(n log n + m log m), where `n = g.length` and `m = s.length`, dominated by the two sorts. The subsequent two-pointer scan is O(n + m).

**Space complexity:** O(log n + log m) for the sort's recursion stack (Java's `Arrays.sort` on primitives uses a dual-pivot quicksort in place), or O(1) auxiliary if you don't count sort internals. No extra data structures are allocated.

**Algorithm trace** (annotated array, two pointers)

Input (unsorted): `g = [3, 1, 2]`, `s = [9, 1, 2]`

```
After Arrays.sort(g) and Arrays.sort(s):
g: [1, 2, 3]      s: [1, 2, 9]
```

```
child=0 cookie=0  s[0]=1 >= g[0]=1 → match, count=1
g: [1, 2, 3]      s: [1, 2, 9]
    child             cookie

child=1 cookie=1  s[1]=2 >= g[1]=2 → match, count=2
g: [1, 2, 3]      s: [1, 2, 9]
       child             cookie

child=2 cookie=2  s[2]=9 >= g[2]=3 → match, count=3
g: [1, 2, 3]      s: [1, 2, 9]
          child             cookie

child=3 cookie=3  child == g.length → loop ends
```
→ return `count = 3`

## 2. Optimal Approach

This greedy two-pointer strategy *is* the optimal approach. The key insight: sort both arrays, then always try to satisfy the least-greedy remaining child with the smallest cookie that hasn't been tried yet. If the smallest untried cookie can't satisfy the least-greedy child, it can't satisfy any more-greedy child either, so it's safe to discard it. If it *can* satisfy that child, using it on a less-greedy child would only waste it (a more-greedy child might have needed it), so match it immediately and move both pointers forward. This exchange argument proves greedy optimality.

**Time complexity:** O(n log n + m log m) — dominated by sorting both arrays; the merge-style scan afterward is linear.

**Space complexity:** O(log n + log m) for sort recursion, O(1) otherwise.

```java
public int findContentChildren(int[] g, int[] s) {
    Arrays.sort(g);
    Arrays.sort(s);

    int child = 0;
    int cookie = 0;

    while (child < g.length && cookie < s.length) {
        if (s[cookie] >= g[child]) {
            child++;
        }
        cookie++;
    }

    return child;
}
```

**Algorithm trace** (annotated array, two pointers)

Input: `g = [1, 2]`, `s = [1, 2, 3]` (already sorted)

```
child=0 cookie=0  s[0]=1 >= g[0]=1 → match, child advances
g: [1, 2]      s: [1, 2, 3]
    child             cookie

child=1 cookie=1  s[1]=2 >= g[1]=2 → match, child advances
g: [1, 2]      s: [1, 2, 3]
       child          cookie

child=2 cookie=2  child == g.length → loop ends
```
→ return `child = 2`

## 3. Alternative Approaches

### Brute force (try every child–cookie pairing)

For each child (sorted by greed, most greedy first), scan all remaining cookies for the smallest one that satisfies them, remove it from consideration, and repeat.

**Time complexity:** O(n log n + m log m + n·m) — the nested scan for the best-fit cookie per child is quadratic in the worst case.

**Space complexity:** O(m) if using a removable structure (e.g., a `List<Integer>` copy of `s`) to track unused cookies, or O(log m) if just sorting in place and using a "used" boolean array of size `m`.

**When acceptable:** Small inputs (well under the `3 * 10^4` bound) or as a warm-up approach under interview time pressure before optimizing to two pointers.

**Algorithm trace** (annotated array — per-child linear scan for best fit)

Input: `g = [1, 2, 3]`, `s = [1, 1]`, children processed most-greedy first: `[3, 2, 1]`

```
child=3: scan s=[1,1] for smallest s[j]>=3 → none found, child 3 stays hungry
s: [1, 1]
    (no assignment)

child=2: scan s=[1,1] for smallest s[j]>=2 → none found, child 2 stays hungry
s: [1, 1]
    (no assignment)

child=1: scan s=[1,1] for smallest s[j]>=1 → s[0]=1 matches, remove it, count=1
s: [1, 1]
    used
```
→ return `count = 1`

### Priority queue (max-heap of cookies, greedy from most-greedy child)

Push all cookie sizes into a max-heap. Sort children by greed descending. For each child, peek the largest available cookie; if it satisfies the child, pop it and count the match; otherwise no smaller cookie can satisfy this child either (since we're going in greed-descending order the child only gets pickier), so nothing to do — but this direction actually requires popping and discarding under-sized cookies from a *min*-heap instead, which converges back to the sorted two-pointer idea with extra `O(log m)` overhead per operation.

**Time complexity:** O(n log n + m log m) for heap construction/sorting, plus O(m log m) for up to `m` heap pop operations — asymptotically the same as the optimal approach but with a larger constant factor.

**Space complexity:** O(m) for the heap.

**When acceptable:** Never strictly better than sorting + two pointers for this problem; only useful if cookies arrive as a live stream and you need incremental best-fit queries.

**Algorithm trace** (annotated array — min-heap of cookies, children processed least-greedy first)

Input: `g = [1, 2]`, `s = [1, 2, 3]`, heap (min-heap) initialized from `s`: `[1, 2, 3]`

```
child=1: heap top = 1, 1 >= 1 → pop, count=1
heap: [1, 2, 3] → pop 1 → [2, 3]

child=2: heap top = 2, 2 >= 2 → pop, count=2
heap: [2, 3] → pop 2 → [3]
```
→ return `count = 2`
