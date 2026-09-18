# Review: Meeting Rooms

| | |
|---|---|
| **Solved on** | 2026-09-15 |
| **DSA Category** | Intervals |

## 1. Your Solution Assessment

The solution is correct. It sorts the meetings by start time, then walks forward with a single index `idx`, advancing past every pair that does not overlap and stopping the moment it finds a pair that does. It correctly treats touching endpoints (`end == nextStart`) as an overlap, matching this repository's variant of the problem, and it handles the empty-array and single-meeting cases up front with the `meetings == null || meetings.length == 0` guard. All 14 tests pass, including the touching-endpoint, same-start, unsorted-input, and max-constraint-value boundary cases.

Code quality is solid: `meetingComparator`, `idx`, and `n` are all self-explanatory, and expressing the scan as a `while` loop that stops at the first overlap rather than scanning the whole array is a nice touch — it reads a bit less obviously than a `for` loop with an early `return false`, but it is not incorrect.

**Time complexity:** O(n log n) — dominated by `Arrays.sort`. Since `int[][]` is an array of objects, the JDK uses TimSort rather than the primitive dual-pivot quicksort, but the asymptotic bound is the same. The scan afterward is O(n).

**Space complexity:** O(n) — TimSort allocates an auxiliary merge buffer for object arrays (unlike primitive arrays, which sort in place). The scan itself uses O(1) extra space.

**Algorithm trace** (step table) — Input: `intervals = [[0,30],[5,10],[15,20]]` (already sorted by start)

| idx | meetings[idx] | meetings[idx-1] | meetings[idx][0] > meetings[idx-1][1]? | action |
|---|---|---|---|---|
| 1 | [5,10] | [0,30] | 5 > 30 → No | overlap found, loop stops |

`idx == n` → `1 == 3` → `false`
→ return `false` ✓ (matches expected: `[0,30]` overlaps both `[5,10]` and `[15,20]`)

## 2. Optimal Approach

Sort the intervals by start time. Once sorted, a person can attend all meetings if and only if every meeting starts at or after the previous one ends — so a single forward pass comparing each interval only to its immediate predecessor is enough. This works because sorting by start time makes "no overlap" transitive: if meeting `i` doesn't overlap meeting `i-1`, then meeting `i-1`'s end is already ≤ meeting `i`'s start, and since every earlier meeting `j < i-1` has an even smaller or equal start, this repository's "touching counts as overlap" rule only changes the comparison operator, not the overall strategy. This is exactly the approach in the current solution.

**Time complexity:** O(n log n) — sorting dominates; the linear scan afterward is O(n).
**Space complexity:** O(n) for the sort's auxiliary buffer (or O(1) extra if you ignore the sort's internal allocation and count only the scan).

```java
public boolean canAttendMeetings(int[][] intervals) {
    if (intervals.length < 2) {
        return true;
    }

    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] <= intervals[i - 1][1]) {
            return false;
        }
    }

    return true;
}
```

**Algorithm trace** (step table) — Input: `intervals = [[0,30],[5,10],[15,20]]`

| i | intervals[i] | intervals[i-1] | intervals[i][0] <= intervals[i-1][1]? | result |
|---|---|---|---|---|
| 1 | [5,10] | [0,30] | 5 <= 30 → Yes | return `false` |

## 3. Alternative Approaches

### Brute force — check every pair

Compare every pair of meetings directly, without sorting: for each pair `(i, j)`, they overlap if `intervals[i][0] <= intervals[j][1] && intervals[j][0] <= intervals[i][1]`. If any pair overlaps, return `false`.

**Time complexity:** O(n²) — every pair of the n meetings is compared once.
**Space complexity:** O(1) — no sorting, no auxiliary structures.
**When acceptable:** fine for very small inputs, or as a first pass under interview time pressure to establish correctness before optimizing to the sort-based approach.

**Algorithm trace** (step table) — Input: `intervals = [[0,30],[5,10],[15,20]]`

| i | j | intervals[i] | intervals[j] | overlap? |
|---|---|---|---|---|
| 0 | 1 | [0,30] | [5,10] | 0<=10 && 5<=30 → Yes → return `false` |

### Sweep line / event counting

Split each meeting into a `+1` event at its start and a `-1` event at its end, sort all events by time (with end-events processed before start-events at the same timestamp, since this variant treats a touching endpoint as an overlap), and sweep through accumulating a running count. If the running count ever exceeds 1, some meeting overlaps another. This is the same technique used to compute the minimum number of rooms needed (Meeting Rooms II) — here we only care whether the peak ever exceeds 1.

**Time complexity:** O(n log n) — sorting the 2n events dominates; the sweep itself is O(n).
**Space complexity:** O(n) — for the events array.
**When acceptable:** it is strictly more machinery than this problem needs (the sort-and-scan approach is simpler and equally fast), but it is worth knowing because it generalizes directly to "how many rooms do we need," which the adjacent-pair-comparison approach does not.

**Algorithm trace** (step table) — Input: `intervals = [[0,30],[5,10],[15,20]]` → events (sorted, ends before starts on ties): `(0,+1), (5,+1), (10,-1), (15,+1), (20,-1), (30,-1)`

| event | count after | peak so far |
|---|---|---|
| (0,+1) | 1 | 1 |
| (5,+1) | 2 | 2 |

Peak reaches 2 → return `false` (a peak > 1 means some meeting overlaps another).
