# Queue

## Pattern
Use a FIFO structure to process elements in arrival order — the backbone of BFS and any problem requiring level-by-level or order-preserving processing.

## How to Recognize
- Level-order traversal of a tree or graph
- "Shortest path in an unweighted graph"
- "Process tasks / requests in order"
- Sliding window maximum (monotonic deque variant)
- "First in, first out" semantics explicitly stated

## Approach
**BFS template**: enqueue the start, then loop — dequeue, process, enqueue unvisited neighbors.
**Monotonic deque**: maintain a deque of useful candidates (e.g., max in window), removing elements that are out of range or dominated.

## Template

```java
// BFS — shortest path / level-order traversal
public int bfs(int start, int target, Map<Integer, List<Integer>> graph) {
    Queue<Integer> queue = new ArrayDeque<>();
    Set<Integer> visited = new HashSet<>();

    queue.offer(start);
    visited.add(start);
    int level = 0;

    while (!queue.isEmpty()) {
        int size = queue.size(); // process one level at a time

        for (int i = 0; i < size; i++) {
            int node = queue.poll();
            if (node == target) return level;

            for (int neighbor : graph.getOrDefault(node, List.of())) {
                if (visited.add(neighbor)) { // add returns false if already present
                    queue.offer(neighbor);
                }
            }
        }

        level++;
    }

    return -1; // not reachable
}
```

```java
// Monotonic deque — sliding window maximum
public int[] maxSlidingWindow(int[] nums, int k) {
    int n = nums.length;
    int[] result = new int[n - k + 1];
    Deque<Integer> deque = new ArrayDeque<>(); // stores indices, front = max

    for (int i = 0; i < n; i++) {
        // remove indices outside the window
        if (!deque.isEmpty() && deque.peekFirst() < i - k + 1) deque.pollFirst();

        // remove smaller elements from the back (they'll never be the max)
        while (!deque.isEmpty() && nums[deque.peekLast()] < nums[i]) deque.pollLast();

        deque.offerLast(i);
        if (i >= k - 1) result[i - k + 1] = nums[deque.peekFirst()];
    }

    return result;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Binary Tree Level Order Traversal | [LeetCode #102](https://leetcode.com/problems/binary-tree-level-order-traversal/) |
| 2 | Sliding Window Maximum | [LeetCode #239](https://leetcode.com/problems/sliding-window-maximum/) |
| 3 | Number of Islands (BFS) | [LeetCode #200](https://leetcode.com/problems/number-of-islands/) |
| 4 | Rotting Oranges | [LeetCode #994](https://leetcode.com/problems/rotting-oranges/) |
| 5 | Walls and Gates | [NeetCode](https://neetcode.io/problems/islands-and-treasure) |
| 6 | Word Ladder | [LeetCode #127](https://leetcode.com/problems/word-ladder/) |
| 7 | Shortest Path in Binary Matrix | [LeetCode #1091](https://leetcode.com/problems/shortest-path-in-binary-matrix/) |
| 8 | Open the Lock | [LeetCode #752](https://leetcode.com/problems/open-the-lock/) |
| 9 | Snakes and Ladders | [LeetCode #909](https://leetcode.com/problems/snakes-and-ladders/) |
| 10 | Design Circular Queue | [LeetCode #622](https://leetcode.com/problems/design-circular-queue/) |
| 11 | Recent Calls Counter | [LeetCode #933](https://leetcode.com/problems/number-of-recent-calls/) |
| 12 | Task Scheduler | [LeetCode #621](https://leetcode.com/problems/task-scheduler/) |
| 13 | Dota2 Senate | [LeetCode #649](https://leetcode.com/problems/dota2-senate/) |
| 14 | Jump Game III | [LeetCode #1306](https://leetcode.com/problems/jump-game-iii/) |
| 15 | Binary Tree Zigzag Level Order Traversal | [LeetCode #103](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) |
| 16 | Maximum Width of Binary Tree | [LeetCode #662](https://leetcode.com/problems/maximum-width-of-binary-tree/) |
| 17 | Find Largest Value in Each Tree Row | [LeetCode #515](https://leetcode.com/problems/find-largest-value-in-each-tree-row/) |
| 18 | Cousins in Binary Tree | [LeetCode #993](https://leetcode.com/problems/cousins-in-binary-tree/) |
| 19 | Nearest Exit from Entrance in Maze | [LeetCode #1926](https://leetcode.com/problems/nearest-exit-from-entrance-in-maze/) |
| 20 | Jump Game IV | [LeetCode #1345](https://leetcode.com/problems/jump-game-iv/) |
