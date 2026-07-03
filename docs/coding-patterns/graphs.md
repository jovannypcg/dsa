# Graphs

## Pattern
Model relationships between nodes (vertices) and their connections (edges). Traverse with DFS or BFS, tracking visited nodes to avoid cycles.

## How to Recognize
- "Connected components", "islands", "regions"
- "Shortest path between two nodes"
- "Can you reach X from Y?"
- "Detect a cycle"
- "Topological order" (dependencies, course schedule)
- Grid problems where cells connect to neighbors

## Approach
- **DFS**: recursion or explicit stack — good for connectivity, cycle detection, topological sort.
- **BFS**: queue — good for shortest path in unweighted graphs.
- **Topological sort**: Kahn's algorithm (in-degree BFS) or DFS post-order.
- **Union-Find**: for dynamic connectivity / number of components.

Always track `visited` to avoid revisiting nodes in cyclic graphs.

## Template

```java
// DFS — count connected components (adjacency list)
public int countComponents(int n, int[][] edges) {
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] e : edges) {
        adj.get(e[0]).add(e[1]);
        adj.get(e[1]).add(e[0]);
    }

    boolean[] visited = new boolean[n];
    int components = 0;

    for (int i = 0; i < n; i++) {
        if (!visited[i]) {
            dfs(adj, visited, i);
            components++;
        }
    }

    return components;
}

private void dfs(List<List<Integer>> adj, boolean[] visited, int node) {
    visited[node] = true;
    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) dfs(adj, visited, neighbor);
    }
}
```

```java
// BFS on grid — number of islands
public int numIslands(char[][] grid) {
    int count = 0;
    int rows = grid.length, cols = grid[0].length;
    int[][] dirs = {{0,1},{0,-1},{1,0},{-1,0}};

    for (int r = 0; r < rows; r++) {
        for (int c = 0; c < cols; c++) {
            if (grid[r][c] == '1') {
                count++;
                Queue<int[]> q = new ArrayDeque<>();
                q.offer(new int[]{r, c});
                grid[r][c] = '0'; // mark visited

                while (!q.isEmpty()) {
                    int[] cell = q.poll();
                    for (int[] d : dirs) {
                        int nr = cell[0] + d[0], nc = cell[1] + d[1];
                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && grid[nr][nc] == '1') {
                            grid[nr][nc] = '0';
                            q.offer(new int[]{nr, nc});
                        }
                    }
                }
            }
        }
    }

    return count;
}
```

```java
// Topological sort — Kahn's algorithm (BFS, in-degree)
public int[] topoSort(int n, int[][] prerequisites) {
    int[] inDegree = new int[n];
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

    for (int[] p : prerequisites) {
        adj.get(p[1]).add(p[0]);
        inDegree[p[0]]++;
    }

    Queue<Integer> q = new ArrayDeque<>();
    for (int i = 0; i < n; i++) if (inDegree[i] == 0) q.offer(i);

    int[] order = new int[n];
    int idx = 0;
    while (!q.isEmpty()) {
        int node = q.poll();
        order[idx++] = node;
        for (int neighbor : adj.get(node)) {
            if (--inDegree[neighbor] == 0) q.offer(neighbor);
        }
    }

    return idx == n ? order : new int[]{}; // empty = cycle exists
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Number of Islands | [LeetCode #200](https://leetcode.com/problems/number-of-islands/) |
| 2 | Max Area of Island | [LeetCode #695](https://leetcode.com/problems/max-area-of-island/) |
| 3 | Clone Graph | [LeetCode #133](https://leetcode.com/problems/clone-graph/) |
| 4 | Walls and Gates | [NeetCode](https://neetcode.io/problems/islands-and-treasure) |
| 5 | Rotting Oranges | [LeetCode #994](https://leetcode.com/problems/rotting-oranges/) |
| 6 | Pacific Atlantic Water Flow | [LeetCode #417](https://leetcode.com/problems/pacific-atlantic-water-flow/) |
| 7 | Surrounded Regions | [LeetCode #130](https://leetcode.com/problems/surrounded-regions/) |
| 8 | Course Schedule | [LeetCode #207](https://leetcode.com/problems/course-schedule/) |
| 9 | Course Schedule II | [LeetCode #210](https://leetcode.com/problems/course-schedule-ii/) |
| 10 | Graph Valid Tree | [LeetCode #261](https://leetcode.com/problems/graph-valid-tree/) |
| 11 | Number of Connected Components | [LeetCode #323](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/) |
| 12 | Redundant Connection | [LeetCode #684](https://leetcode.com/problems/redundant-connection/) |
| 13 | Word Ladder | [LeetCode #127](https://leetcode.com/problems/word-ladder/) |
| 14 | Alien Dictionary | [LeetCode #269](https://leetcode.com/problems/alien-dictionary/) |
| 15 | Cheapest Flights Within K Stops | [LeetCode #787](https://leetcode.com/problems/cheapest-flights-within-k-stops/) |
| 16 | Network Delay Time | [LeetCode #743](https://leetcode.com/problems/network-delay-time/) |
| 17 | Find the City With the Smallest Number of Neighbors | [LeetCode #1334](https://leetcode.com/problems/find-the-city-with-the-smallest-number-of-neighbors-at-a-threshold-distance/) |
| 18 | Swim in Rising Water | [LeetCode #778](https://leetcode.com/problems/swim-in-rising-water/) |
| 19 | Reconstruct Itinerary | [LeetCode #332](https://leetcode.com/problems/reconstruct-itinerary/) |
| 20 | Is Graph Bipartite? | [LeetCode #785](https://leetcode.com/problems/is-graph-bipartite/) |
