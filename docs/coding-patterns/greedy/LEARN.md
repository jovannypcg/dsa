# Greedy

## Pattern
Make the locally optimal choice at each step without reconsidering past decisions — works when a greedy choice leads to a globally optimal solution (provable by exchange argument or induction).

## How to Recognize
- "Minimum number of X to cover / achieve Y"
- Scheduling / interval problems
- "Maximize / minimize" with a clear local rule
- Sorting by a property unlocks a greedy choice
- No dependency between future decisions and past ones (no overlapping subproblems needing DP)

## Approach
1. **Sort** the input by the property that drives the greedy choice.
2. **Iterate** and make the greedy pick at each step.
3. **Verify** (mentally): does a local optimum guarantee a global one? If not, consider DP.

## Template

```java
// Jump Game — greedy reachability
public boolean canJump(int[] nums) {
    int maxReach = 0;

    for (int i = 0; i < nums.length; i++) {
        if (i > maxReach) return false;        // can't reach here
        maxReach = Math.max(maxReach, i + nums[i]);
    }

    return true;
}
```

```java
// Interval scheduling — max non-overlapping intervals (sort by end time)
public int eraseOverlapIntervals(int[][] intervals) {
    Arrays.sort(intervals, Comparator.comparingInt(a -> a[1])); // sort by end

    int count = 0, end = Integer.MIN_VALUE;

    for (int[] interval : intervals) {
        if (interval[0] >= end) {
            end = interval[1];  // greedily keep this interval
        } else {
            count++;            // remove the overlapping one
        }
    }

    return count;
}
```

```java
// Gas Station — greedy with running total
public int canCompleteCircuit(int[] gas, int[] cost) {
    int total = 0, tank = 0, start = 0;

    for (int i = 0; i < gas.length; i++) {
        int gain = gas[i] - cost[i];
        total += gain;
        tank  += gain;

        if (tank < 0) {     // can't reach i+1 from current start
            start = i + 1;
            tank  = 0;
        }
    }

    return total >= 0 ? start : -1;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Maximum Subarray | [LeetCode #53](https://leetcode.com/problems/maximum-subarray/) |
| 2 | Jump Game | [LeetCode #55](https://leetcode.com/problems/jump-game/) |
| 3 | Jump Game II | [LeetCode #45](https://leetcode.com/problems/jump-game-ii/) |
| 4 | Gas Station | [LeetCode #134](https://leetcode.com/problems/gas-station/) |
| 5 | Hand of Straights | [LeetCode #846](https://leetcode.com/problems/hand-of-straights/) |
| 6 | Merge Triplets to Form Target | [LeetCode #1899](https://leetcode.com/problems/merge-triplets-to-form-target-triplet/) |
| 7 | Partition Labels | [LeetCode #763](https://leetcode.com/problems/partition-labels/) |
| 8 | Valid Parenthesis String | [LeetCode #678](https://leetcode.com/problems/valid-parenthesis-string/) |
| 9 | Non-overlapping Intervals | [LeetCode #435](https://leetcode.com/problems/non-overlapping-intervals/) |
| 10 | Minimum Number of Arrows to Burst Balloons | [LeetCode #452](https://leetcode.com/problems/minimum-number-of-arrows-to-burst-balloons/) |
| 11 | Task Scheduler | [LeetCode #621](https://leetcode.com/problems/task-scheduler/) |
| 12 | Candy | [LeetCode #135](https://leetcode.com/problems/candy/) |
| 13 | Assign Cookies | [LeetCode #455](https://leetcode.com/problems/assign-cookies/) |
| 14 | Lemonade Change | [LeetCode #860](https://leetcode.com/problems/lemonade-change/) |
| 15 | Queue Reconstruction by Height | [LeetCode #406](https://leetcode.com/problems/queue-reconstruction-by-height/) |
| 16 | Reorganize String | [LeetCode #767](https://leetcode.com/problems/reorganize-string/) |
| 17 | Two City Scheduling | [LeetCode #1029](https://leetcode.com/problems/two-city-scheduling/) |
| 18 | Boats to Save People | [LeetCode #881](https://leetcode.com/problems/boats-to-save-people/) |
| 19 | Minimum Cost to Connect Sticks | [LeetCode #1167](https://leetcode.com/problems/minimum-cost-to-connect-sticks/) |
| 20 | Largest Number | [LeetCode #179](https://leetcode.com/problems/largest-number/) |
