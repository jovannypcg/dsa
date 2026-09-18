# Insert Interval — Solution

| | |
|---|---|
| **Created on** | 2026-09-15 |
| **DSA Category** | Intervals |

## Approach 1: Single Linear Pass (Optimal)

Walk through `intervals` once, in order, splitting the work into three phases:

1. **Before**: while the current interval ends strictly before `newInterval` starts (no shared point), copy it directly to the result.
2. **Overlapping**: while the current interval overlaps `newInterval` (shares at least one point), expand `newInterval` to cover both — `start = min(start, cur[0])`, `end = max(end, cur[1])` — without adding it to the result yet. Once this phase ends, push the fully-expanded `newInterval` to the result.
3. **After**: copy every remaining interval directly to the result.

Because `intervals` is already sorted by `starti`, a single forward pass is enough — no sorting needed.

**Time complexity:** O(n), where n is the number of intervals — each interval is visited exactly once.
**Space complexity:** O(n) for the result array (required by the problem; no extra auxiliary structures beyond that).

```java
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        List<int[]> result = new ArrayList<>();
        int i = 0;
        int n = intervals.length;
        int start = newInterval[0];
        int end = newInterval[1];

        // Phase 1 — "before": copy every interval that ends strictly before newInterval
        // starts. They don't share a point with newInterval, so nothing to merge.
        while (i < n && intervals[i][1] < start) {
            result.add(intervals[i]);
            i++;
        }

        // Phase 2 — "overlapping": absorb every interval that shares a point with
        // newInterval into a single, growing merged interval instead of adding each
        // one separately.
        while (i < n && intervals[i][0] <= end) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
            i++;
        }
        // The overlap phase is over — push the fully-merged interval exactly once.
        result.add(new int[]{start, end});

        // Phase 3 — "after": everything left over starts strictly after newInterval
        // ends, so copy it as-is.
        while (i < n) {
            result.add(intervals[i]);
            i++;
        }

        return result.toArray(new int[result.size()][]);
    }
}
```

### Algorithm trace (Annotated array)

Input: `intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]]`, `newInterval = [4,8]`

```
i=0  intervals[0]=[1,2]  end(2) < start(4) → before-phase, copy [1,2]
[[1,2], [3,5], [6,7], [8,10], [12,16]]
   i
output so far: [[1,2]]

i=1  intervals[1]=[3,5]  start(3) <= end(8) → overlap-phase
[[1,2], [3,5], [6,7], [8,10], [12,16]]
         i
merged newInterval: start=min(4,3)=3, end=max(8,5)=8 → [3,8]

i=2  intervals[2]=[6,7]  start(6) <= end(8) → overlap-phase
[[1,2], [3,5], [6,7], [8,10], [12,16]]
                i
merged newInterval: start=min(3,6)=3, end=max(8,7)=8 → [3,8] (unchanged)

i=3  intervals[3]=[8,10]  start(8) <= end(8) → overlap-phase (shares point 8)
[[1,2], [3,5], [6,7], [8,10], [12,16]]
                        i
merged newInterval: start=min(3,8)=3, end=max(8,10)=10 → [3,10]

i=4  intervals[4]=[12,16]  start(12) > end(10) → overlap-phase ends
push merged interval [3,10] to output
output so far: [[1,2], [3,10]]

i=4  intervals[4]=[12,16] → after-phase, copy [12,16]
[[1,2], [3,5], [6,7], [8,10], [12,16]]
                                  i
output so far: [[1,2], [3,10], [12,16]]
```
→ return `[[1,2], [3,10], [12,16]]`

---

## Approach 2: Append, Sort, Merge

Append `newInterval` to `intervals`, sort the combined array by start value, then run the standard "merge overlapping intervals" sweep: keep a running last-added interval and merge the next one into it whenever they overlap.

This works without exploiting the fact that `intervals` was already sorted, so it's more general but strictly worse here since the input guarantees sorted order.

**Time complexity:** O(n log n) — dominated by the sort; the merge sweep itself is O(n).
**Space complexity:** O(n) for the combined/result array.

```java
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        // Step 1: treat newInterval as just another interval and combine everything
        // into one array so it can be sorted alongside the rest.
        int n = intervals.length;
        int[][] combined = new int[n + 1][];
        System.arraycopy(intervals, 0, combined, 0, n);
        combined[n] = newInterval;

        // Step 2: sort by start time so any intervals that overlap end up adjacent.
        Arrays.sort(combined, (a, b) -> Integer.compare(a[0], b[0]));

        // Step 3: standard "merge overlapping intervals" sweep — extend the last
        // interval already in the result if the next one overlaps it, otherwise
        // start a brand new interval in the result.
        List<int[]> result = new ArrayList<>();
        for (int[] cur : combined) {
            if (result.isEmpty() || result.get(result.size() - 1)[1] < cur[0]) {
                result.add(cur);
            } else {
                int[] last = result.get(result.size() - 1);
                last[1] = Math.max(last[1], cur[1]);
            }
        }

        return result.toArray(new int[result.size()][]);
    }
}
```

### Algorithm trace (Step table)

Input: `intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]]`, `newInterval = [4,8]`
After append + sort: `combined = [[1,2],[3,5],[4,8],[6,7],[8,10],[12,16]]`

| cur | result before | overlaps last? | result after |
|---|---|---|---|
| [1,2] | [] | — (empty) | [[1,2]] |
| [3,5] | [[1,2]] | 2 < 3 → No | [[1,2],[3,5]] |
| [4,8] | [[1,2],[3,5]] | 5 >= 4 → Yes | [[1,2],[3,8]] |
| [6,7] | [[1,2],[3,8]] | 8 >= 6 → Yes | [[1,2],[3,8]] |
| [8,10] | [[1,2],[3,8]] | 8 >= 8 → Yes | [[1,2],[3,10]] |
| [12,16] | [[1,2],[3,10]] | 10 < 12 → No | [[1,2],[3,10],[12,16]] |

→ return `[[1,2], [3,10], [12,16]]`

**When acceptable:** fine under interview time pressure if you already know the general merge-intervals pattern and don't immediately spot the linear-scan optimization — it's a safe fallback that still gets a correct answer.

---

## Approach 3: Binary Search for Insertion Boundaries

Use binary search to find the first interval whose end is `>= newInterval.start` (left boundary of the overlap region) and the first interval whose start is `> newInterval.end` (right boundary, exclusive). Everything before the left boundary is copied as-is, everything from the right boundary onward is copied as-is, and everything in between is merged with `newInterval` in one pass.

This doesn't improve the asymptotic complexity — the merge step and the copy steps are still O(n) — but it demonstrates how to exploit the sorted input with binary search, which can be a useful technique to mention even though it adds complexity without benefit here.

**Time complexity:** O(n) — the two binary searches are O(log n), but the merge/copy of the overlap region and the final array construction are still O(n) overall.
**Space complexity:** O(n) for the result array.

```java
import java.util.ArrayList;
import java.util.List;

public class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        int n = intervals.length;
        if (n == 0) {
            return new int[][]{newInterval};
        }

        // Step 1: binary search for the boundaries of the overlap region.
        // left  = first interval whose end is >= newInterval's start (could overlap).
        // right = first interval whose start is > newInterval's end (definitely doesn't).
        int left = findFirstEndGreaterOrEqual(intervals, newInterval[0]);
        int right = findFirstStartGreater(intervals, newInterval[1]);

        List<int[]> result = new ArrayList<>();

        // Step 2: everything before "left" ends before newInterval starts — copy as-is.
        for (int i = 0; i < left; i++) {
            result.add(intervals[i]);
        }

        // Step 3: everything in [left, right) overlaps newInterval — merge them all
        // into a single interval in one sweep.
        int start = newInterval[0];
        int end = newInterval[1];
        for (int i = left; i < right; i++) {
            start = Math.min(start, intervals[i][0]);
            end = Math.max(end, intervals[i][1]);
        }
        result.add(new int[]{start, end});

        // Step 4: everything from "right" onward starts after newInterval ends — copy as-is.
        for (int i = right; i < n; i++) {
            result.add(intervals[i]);
        }

        return result.toArray(new int[result.size()][]);
    }

    // Binary search for the first index whose interval END is >= target.
    private int findFirstEndGreaterOrEqual(int[][] intervals, int target) {
        int lo = 0;
        int hi = intervals.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (intervals[mid][1] < target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }

    // Binary search for the first index whose interval START is > target.
    private int findFirstStartGreater(int[][] intervals, int target) {
        int lo = 0;
        int hi = intervals.length;
        while (lo < hi) {
            int mid = lo + (hi - lo) / 2;
            if (intervals[mid][0] <= target) {
                lo = mid + 1;
            } else {
                hi = mid;
            }
        }
        return lo;
    }
}
```

### Algorithm trace (Step table + annotated array)

Input: `intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]]`, `newInterval = [4,8]`

**Finding `left`** — first index where `intervals[i][1] >= 4`:

| lo | hi | mid | intervals[mid] | end >= 4? |
|---|---|---|---|---|
| 0 | 5 | 2 | [6,7] | Yes → hi=2 |
| 0 | 2 | 1 | [3,5] | Yes → hi=1 |
| 0 | 1 | 0 | [1,2] | No → lo=1 |
→ `left = 1`

**Finding `right`** — first index where `intervals[i][0] > 8`:

| lo | hi | mid | intervals[mid] | start > 8? |
|---|---|---|---|---|
| 0 | 5 | 2 | [6,7] | No → lo=3 |
| 3 | 5 | 4 | [12,16] | Yes → hi=4 |
| 3 | 4 | 3 | [8,10] | No → lo=4 |
→ `right = 4`

```
[[1,2], [3,5], [6,7], [8,10], [12,16]]
  copy    ←─ merge region [left,right) ─→   copy
        left=1                    right=4
```
Merge region `[1,4)` = `[3,5],[6,7],[8,10]` combined with `newInterval [4,8]` → `[3,10]`

→ return `[[1,2], [3,10], [12,16]]`

**When acceptable:** mostly a talking point to show awareness of binary search on sorted intervals — rarely worth the added complexity in an interview since Approach 1 is simpler, equally fast asymptotically, and easier to get right under time pressure.
