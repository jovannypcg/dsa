# Review: Meeting Rooms II

| | |
|---|---|
| **Solved on** | 2026-09-15 |
| **DSA Category** | Heap / Priority Queue |

## 1. Your Solution Assessment

The solution is correct. It sorts meetings by start time, then keeps a min-heap of the end times of currently occupied rooms. For each meeting, it checks the room that frees up soonest (the heap's minimum): if that room's end time is at or before the current meeting's start, the meeting reuses it; otherwise no room is free and a new one is added to the heap. The heap's final size is the number of rooms in simultaneous use at the peak, which is exactly the answer. All 10 tests pass, including the touching-endpoint, duplicate-interval, and max-constraint boundary cases.

Code quality is good: `meetingComparator`, `rooms`, and `roomEndTime` are all self-explanatory, and the comments now correctly describe what each branch does — reuse the freed room, or occupy a new one on overlap. One small nit: `meetingComparator` is declared as an instance field even though it's only used once, inside `minMeetingRooms`; a local variable (or an inline lambda passed to `Arrays.sort`) would scope it more tightly without changing behavior.

**Time complexity:** O(n log n) — sorting is O(n log n), and each of the n meetings does one heap `peek`/`poll`/`offer`, each O(log n).

**Space complexity:** O(n) — the heap holds up to n end times in the worst case (no meetings ever overlap in end time and free up a room).

**Algorithm trace** (step table) — Input: `intervals = [[0,30],[5,10],[15,20]]` (already sorted by start)

| idx | meetings[idx] | roomEndTime (peek) | start >= roomEndTime? | action | heap after |
|---|---|---|---|---|---|
| — | (init) | — | — | offer(30) | {30} |
| 1 | [5,10] | 30 | 5 >= 30 → No | no poll, offer(10) | {10, 30} |
| 2 | [15,20] | 10 | 15 >= 10 → Yes | poll(10), offer(20) | {20, 30} |

`rooms.size()` = 2 → return `2` ✓

## 2. Optimal Approach

This is already the optimal approach. Sort meetings by start time, then use a min-heap keyed on end time to always know which occupied room frees up soonest. For every meeting, compare its start time to the heap's minimum end time: if the earliest-ending room has already finished, pop it and reuse it; otherwise push a new room. The heap's final size is the maximum number of rooms ever in use at once — precisely the answer, since a room is only ever added when every existing room is still busy.

**Time complexity:** O(n log n) — sorting dominates; each of the n heap operations is O(log n).
**Space complexity:** O(n) — the heap can grow to hold one end time per meeting in the worst case.

```java
public int minMeetingRooms(int[][] intervals) {
    if (intervals.length == 0) {
        return 0;
    }

    Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));

    PriorityQueue<Integer> roomEndTimes = new PriorityQueue<>();
    roomEndTimes.offer(intervals[0][1]);

    for (int i = 1; i < intervals.length; i++) {
        if (intervals[i][0] >= roomEndTimes.peek()) {
            roomEndTimes.poll();
        }
        roomEndTimes.offer(intervals[i][1]);
    }

    return roomEndTimes.size();
}
```

**Algorithm trace** (step table) — Input: `intervals = [[0,30],[5,10],[15,20]]`

| i | intervals[i] | roomEndTimes.peek() | intervals[i][0] >= peek()? | action | heap after |
|---|---|---|---|---|---|
| — | (init) | — | — | offer(30) | {30} |
| 1 | [5,10] | 30 | 5 >= 30 → No | offer(10) | {10, 30} |
| 2 | [15,20] | 10 | 15 >= 10 → Yes | poll(10), offer(20) | {20, 30} |

→ return `2`

## 3. Alternative Approaches

### Two-pointer over sorted starts and ends

Split the intervals into two separate arrays — one of start times, one of end times — and sort each independently. Walk through both with a pointer each (`s` for starts, `e` for ends): if the current start is before the current end, a new room is needed (advance `s`, track a running room count); otherwise a room has freed up (advance `e`, decrement the running count). The answer is the maximum running count seen. This achieves the same result as the heap without needing a priority queue — just two sorted arrays and two index pointers.

**Time complexity:** O(n log n) — sorting the two arrays dominates; the merge-style scan is O(n).
**Space complexity:** O(n) — for the two separate start/end arrays.
**When acceptable:** always a solid choice — same complexity as the heap approach, arguably simpler to reason about since it avoids heap operations entirely.

**Algorithm trace** (step table) — Input: `intervals = [[0,30],[5,10],[15,20]]` → `starts = [0,5,15]`, `ends = [10,20,30]`

| s | e | starts[s] | ends[e] | starts[s] < ends[e]? | action | rooms | max |
|---|---|---|---|---|---|---|---|
| 0 | 0 | 0 | 10 | Yes | rooms++, s++ | 1 | 1 |
| 1 | 0 | 5 | 10 | Yes | rooms++, s++ | 2 | 2 |
| 2 | 0 | 15 | 10 | No | rooms--, e++ | 1 | 2 |
| 2 | 1 | 15 | 20 | Yes | rooms++, s++ | 2 | 2 |

`s` exhausted → return `max` = `2`

### Brute force — sweep every start time

For each meeting's start time, count how many other meetings are active at that instant (i.e., `otherStart <= thisStart < otherEnd`, adjusting for the touching-is-not-overlap rule). The answer is the maximum count found across all start times.

**Time complexity:** O(n²) — for each of the n start times, scan all n meetings to count active ones.
**Space complexity:** O(1) — no extra structures beyond a running maximum.
**When acceptable:** fine for very small inputs, or as a starting point under interview time pressure before optimizing to sorting-based approaches.

**Algorithm trace** (step table) — Input: `intervals = [[0,30],[5,10],[15,20]]`

| checking start of | active meetings at that instant | count |
|---|---|---|
| [0,30] (t=0) | [0,30] | 1 |
| [5,10] (t=5) | [0,30], [5,10] | 2 |
| [15,20] (t=15) | [0,30], [15,20] | 2 |

Maximum count = 2 → return `2`
