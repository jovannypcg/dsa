# Meeting Rooms — Review

| | |
|---|---|
| **Solved on** | 2026-06-16 |
| **DSA Category** | Intervals |

---

## 1. Your Solution Assessment

**Correctness:** Correct. Sorting by start time and then scanning adjacent pairs is the right approach. The overlap condition `current[0] <= previous[1]` correctly catches both:

- True overlaps: `[1,4]` and `[2,5]` → `2 <= 4` → false.
- Touching intervals: `[1,5]` and `[5,10]` → `5 <= 5` → false. This matches the problem's definition — a meeting that starts exactly when another ends is still a conflict.

The early return for `null` and `length < 2` is correct: zero or one meeting trivially has no conflicts.

**Code quality:** Clean and readable. `current` and `previous` are clear names that make the loop body self-explanatory. The comparator is explicit. No complaints.

**Time complexity: O(n log n)**
The sort dominates. The subsequent loop is O(n).

**Space complexity: O(log n)**
No extra data structures — `Arrays.sort` sorts the input in-place, using O(log n) stack space for TimSort's merge passes. This is better than the Merge Intervals solution (p12), which needed O(n) for the output list.

**Algorithm trace** — Input: `intervals = [[0,30],[5,10],[15,20]]`

(already sorted by start)

| i | previous | current | current[0] <= previous[1]? | result |
|---|----------|---------|----------------------------|--------|
| 1 | [0,30] | [5,10] | 5 <= 30 ✓ | overlap → return false |

→ return `false`

---

## 2. Optimal Approach

Sort by start time, then do one linear pass checking adjacent pairs. This is exactly what you implemented — your solution is already optimal.

**Time: O(n log n)** — sort dominates.
**Space: O(log n)** — in-place sort, no output list needed.

```java
public boolean canAttendMeetings(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[0]));

    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] <= intervals[i - 1][1]) {
            return false;
        }
    }

    return true;
}
```

**Algorithm trace** — same input: `intervals = [[0,30],[5,10],[15,20]]`

| i | intervals[i] | intervals[i-1] | overlap? | result |
|---|-------------|-----------------|----------|--------|
| 1 | [5,10] | [0,30] | 5 <= 30 ✓ | return false |

→ return `false`

---

## 3. Alternative Approaches

### Brute Force — O(n²) time, O(1) space

Compare every pair of intervals. Two intervals `[a,b]` and `[c,d]` (with `a <= c`) overlap when `c <= b`.

- **Time: O(n²)** — nested loop over all pairs.
- **Space: O(1)** — no extra memory.
- **When acceptable:** Tiny inputs or when you need to sketch a solution quickly under pressure. Not suitable for `n = 10^4`.

```java
public boolean canAttendMeetings(int[][] intervals) {
    for (int i = 0; i < intervals.length; i++) {
        for (int j = i + 1; j < intervals.length; j++) {
            int[] a = intervals[i], b = intervals[j];
            if (a[0] <= b[1] && b[0] <= a[1]) {
                return false;
            }
        }
    }
    return true;
}
```

**Algorithm trace** — Input: `intervals = [[0,30],[5,10],[15,20]]`

| i | j | a | b | a[0]<=b[1] && b[0]<=a[1]? |
|---|---|---|---|---------------------------|
| 0 | 1 | [0,30] | [5,10] | 0<=10 && 5<=30 → **true** → return false |

→ return `false`
