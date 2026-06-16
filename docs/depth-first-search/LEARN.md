# Depth-First Search (DFS)

## Pattern
Explore as far as possible along a branch before backtracking. Uses the call stack (recursion) or an explicit stack. Visits every node reachable from the start exactly once.

## How to Recognize
- "Find a path between two nodes"
- "Detect a cycle"
- "Connected components / flood fill"
- "All possible paths" (combined with backtracking)
- Problems on trees (almost always DFS) or graphs where full exploration is needed before deciding
- "Topological sort" (DFS post-order)

## Approach
1. Mark the current node as visited.
2. Recurse (or push to stack) on each unvisited neighbor.
3. Post-order logic runs after all neighbors are fully explored (useful for topological sort, tree height, etc.).

**Recursive DFS** is the default. Switch to an **iterative stack** when recursion depth may exceed limits (very deep graphs/trees).

## Template

```java
// Recursive DFS on a graph (adjacency list)
public void dfs(int node, boolean[] visited, List<List<Integer>> adj) {
    visited[node] = true;
    // pre-order logic here (process node on the way in)

    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) {
            dfs(neighbor, visited, adj);
        }
    }

    // post-order logic here (process node on the way out)
}
```

```java
// Iterative DFS (explicit stack)
public void dfsIterative(int start, List<List<Integer>> adj) {
    boolean[] visited = new boolean[adj.size()];
    Deque<Integer> stack = new ArrayDeque<>();
    stack.push(start);

    while (!stack.isEmpty()) {
        int node = stack.pop();
        if (visited[node]) continue;
        visited[node] = true;

        // process node

        for (int neighbor : adj.get(node)) {
            if (!visited[neighbor]) stack.push(neighbor);
        }
    }
}
```

```java
// DFS on a grid (4-directional flood fill)
private final int[][] DIRS = {{0,1},{0,-1},{1,0},{-1,0}};

public void dfsGrid(int[][] grid, boolean[][] visited, int r, int c) {
    int rows = grid.length, cols = grid[0].length;
    if (r < 0 || r >= rows || c < 0 || c >= cols) return;
    if (visited[r][c] || grid[r][c] == 0) return;

    visited[r][c] = true;

    for (int[] d : DIRS) {
        dfsGrid(grid, visited, r + d[0], c + d[1]);
    }
}
```

```java
// DFS post-order — topological sort
public List<Integer> topoSort(int n, List<List<Integer>> adj) {
    boolean[] visited = new boolean[n];
    Deque<Integer> result = new ArrayDeque<>();

    for (int i = 0; i < n; i++) {
        if (!visited[i]) dfsPost(i, visited, adj, result);
    }

    return new ArrayList<>(result); // front = first in topo order
}

private void dfsPost(int node, boolean[] visited, List<List<Integer>> adj, Deque<Integer> result) {
    visited[node] = true;
    for (int neighbor : adj.get(node)) {
        if (!visited[neighbor]) dfsPost(neighbor, visited, adj, result);
    }
    result.push(node); // post-order: push after all descendants
}
```

## DFS vs BFS — Quick Rule
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
| 1 | Number of Islands | [LeetCode #200](https://leetcode.com/problems/number-of-islands/) |
| 2 | Max Area of Island | [LeetCode #695](https://leetcode.com/problems/max-area-of-island/) |
| 3 | Flood Fill | [LeetCode #733](https://leetcode.com/problems/flood-fill/) |
| 4 | Clone Graph | [LeetCode #133](https://leetcode.com/problems/clone-graph/) |
| 5 | Path Sum (tree) | [LeetCode #112](https://leetcode.com/problems/path-sum/) |
| 6 | All Paths from Source to Target | [LeetCode #797](https://leetcode.com/problems/all-paths-from-source-to-target/) |
| 7 | Course Schedule (cycle detection) | [LeetCode #207](https://leetcode.com/problems/course-schedule/) |
| 8 | Course Schedule II (topo sort) | [LeetCode #210](https://leetcode.com/problems/course-schedule-ii/) |
| 9 | Pacific Atlantic Water Flow | [LeetCode #417](https://leetcode.com/problems/pacific-atlantic-water-flow/) |
| 10 | Surrounded Regions | [LeetCode #130](https://leetcode.com/problems/surrounded-regions/) |
| 11 | Word Search | [LeetCode #79](https://leetcode.com/problems/word-search/) |
| 12 | Number of Provinces | [LeetCode #547](https://leetcode.com/problems/number-of-provinces/) |
| 13 | Detect Cycle in Directed Graph | [LeetCode #802](https://leetcode.com/problems/find-eventual-safe-states/) |
| 14 | Reconstruct Itinerary | [LeetCode #332](https://leetcode.com/problems/reconstruct-itinerary/) |
| 15 | Alien Dictionary | [LeetCode #269](https://leetcode.com/problems/alien-dictionary/) |
| 16 | Path Sum II (all paths) | [LeetCode #113](https://leetcode.com/problems/path-sum-ii/) |
| 17 | Binary Tree Paths | [LeetCode #257](https://leetcode.com/problems/binary-tree-paths/) |
| 18 | Longest Increasing Path in a Matrix | [LeetCode #329](https://leetcode.com/problems/longest-increasing-path-in-a-matrix/) |
| 19 | Number of Connected Components | [LeetCode #323](https://leetcode.com/problems/number-of-connected-components-in-an-undirected-graph/) |
| 20 | Is Graph Bipartite? | [LeetCode #785](https://leetcode.com/problems/is-graph-bipartite/) |
