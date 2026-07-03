# Breadth-First Search (BFS)

## Pattern
Explore all neighbors at the current depth before moving deeper. Uses a queue (FIFO). Guarantees the shortest path in unweighted graphs.

## How to Recognize
- "Shortest path / minimum steps in an unweighted graph or grid"
- "Level-order traversal"
- "Minimum number of X to reach Y"
- "Spread / infection / ripple from multiple sources simultaneously" (multi-source BFS)
- When DFS would explore too deep before finding the answer

## Approach
1. Enqueue the start node(s) and mark as visited immediately (before dequeuing, not after).
2. While the queue is not empty: dequeue a node, process it, enqueue all unvisited neighbors.
3. Track `level` (distance) by processing one full level at a time — use `size = queue.size()` at the start of each level loop.

**Multi-source BFS**: enqueue all sources at once before the main loop. The algorithm finds the shortest distance from any source.

## Template

```java
// BFS on a graph — shortest path
public int bfs(int start, int target, List<List<Integer>> adj) {
    boolean[] visited = new boolean[adj.size()];
    Queue<Integer> queue = new ArrayDeque<>();

    queue.offer(start);
    visited[start] = true;
    int steps = 0;

    while (!queue.isEmpty()) {
        int size = queue.size(); // freeze current level size

        for (int i = 0; i < size; i++) {
            int node = queue.poll();
            if (node == target) return steps;

            for (int neighbor : adj.get(node)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true; // mark on enqueue, not on dequeue
                    queue.offer(neighbor);
                }
            }
        }

        steps++;
    }

    return -1; // unreachable
}
```

```java
// BFS on a grid
private final int[][] DIRS = {{0,1},{0,-1},{1,0},{-1,0}};

public int bfsGrid(int[][] grid, int[] start, int[] end) {
    int rows = grid.length, cols = grid[0].length;
    boolean[][] visited = new boolean[rows][cols];
    Queue<int[]> queue = new ArrayDeque<>();

    queue.offer(start);
    visited[start[0]][start[1]] = true;
    int steps = 0;

    while (!queue.isEmpty()) {
        int size = queue.size();

        for (int i = 0; i < size; i++) {
            int[] cell = queue.poll();
            if (cell[0] == end[0] && cell[1] == end[1]) return steps;

            for (int[] d : DIRS) {
                int nr = cell[0] + d[0], nc = cell[1] + d[1];
                if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                        && !visited[nr][nc] && grid[nr][nc] != 0) {
                    visited[nr][nc] = true;
                    queue.offer(new int[]{nr, nc});
                }
            }
        }

        steps++;
    }

    return -1;
}
```

```java
// Multi-source BFS — distance from nearest source
public int[][] multiSourceBFS(int[][] grid, List<int[]> sources) {
    int rows = grid.length, cols = grid[0].length;
    int[][] dist = new int[rows][cols];
    for (int[] row : dist) Arrays.fill(row, Integer.MAX_VALUE);

    Queue<int[]> queue = new ArrayDeque<>();
    for (int[] src : sources) {
        queue.offer(src);
        dist[src[0]][src[1]] = 0;
    }

    while (!queue.isEmpty()) {
        int[] cell = queue.poll();
        for (int[] d : DIRS) {
            int nr = cell[0] + d[0], nc = cell[1] + d[1];
            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols
                    && dist[nr][nc] == Integer.MAX_VALUE) {
                dist[nr][nc] = dist[cell[0]][cell[1]] + 1;
                queue.offer(new int[]{nr, nc});
            }
        }
    }

    return dist;
}
```

## BFS vs DFS — Quick Rule
| Goal | Prefer |
|------|--------|
| Shortest path (unweighted) | BFS |
| Full exploration / all paths | DFS |
| Topological sort | DFS |
| Cycle detection | DFS |
| Level-by-level processing | BFS |

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Binary Tree Level Order Traversal | [LeetCode #102](https://leetcode.com/problems/binary-tree-level-order-traversal/) |
| 2 | Rotting Oranges | [LeetCode #994](https://leetcode.com/problems/rotting-oranges/) |
| 3 | Walls and Gates | [NeetCode](https://neetcode.io/problems/islands-and-treasure) |
| 4 | Shortest Path in Binary Matrix | [LeetCode #1091](https://leetcode.com/problems/shortest-path-in-binary-matrix/) |
| 5 | Word Ladder | [LeetCode #127](https://leetcode.com/problems/word-ladder/) |
| 6 | Number of Islands (BFS variant) | [LeetCode #200](https://leetcode.com/problems/number-of-islands/) |
| 7 | Open the Lock | [LeetCode #752](https://leetcode.com/problems/open-the-lock/) |
| 8 | Binary Tree Right Side View | [LeetCode #199](https://leetcode.com/problems/binary-tree-right-side-view/) |
| 9 | Minimum Depth of Binary Tree | [LeetCode #111](https://leetcode.com/problems/minimum-depth-of-binary-tree/) |
| 10 | Jump Game III | [LeetCode #1306](https://leetcode.com/problems/jump-game-iii/) |
| 11 | Nearest Exit from Entrance in Maze | [LeetCode #1926](https://leetcode.com/problems/nearest-exit-from-entrance-in-maze/) |
| 12 | Snakes and Ladders | [LeetCode #909](https://leetcode.com/problems/snakes-and-ladders/) |
| 13 | Pacific Atlantic Water Flow (BFS) | [LeetCode #417](https://leetcode.com/problems/pacific-atlantic-water-flow/) |
| 14 | 01 Matrix | [LeetCode #542](https://leetcode.com/problems/01-matrix/) |
| 15 | As Far from Land as Possible | [LeetCode #1162](https://leetcode.com/problems/as-far-from-land-as-possible/) |
| 16 | Minimum Knight Moves | [LeetCode #1197](https://leetcode.com/problems/minimum-knight-moves/) |
| 17 | Bus Routes | [LeetCode #815](https://leetcode.com/problems/bus-routes/) |
| 18 | Binary Tree Zigzag Level Order Traversal | [LeetCode #103](https://leetcode.com/problems/binary-tree-zigzag-level-order-traversal/) |
| 19 | Find Largest Value in Each Tree Row | [LeetCode #515](https://leetcode.com/problems/find-largest-value-in-each-tree-row/) |
| 20 | Cut Off Trees for Golf Event | [LeetCode #675](https://leetcode.com/problems/cut-off-trees-for-golf-event/) |
