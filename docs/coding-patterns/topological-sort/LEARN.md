# Topological Sort

## Pattern
Order the nodes of a directed acyclic graph (DAG) such that every edge `u → v` has `u` appearing before `v`. Used whenever tasks have dependencies that must be resolved in order.

## How to Recognize
- "Given prerequisites / dependencies, find a valid order"
- "Can all tasks be completed?" (cycle detection in a directed graph)
- "Schedule jobs where X must come before Y"
- Input is a directed graph with no cycles (or you must detect if one exists)
- Keywords: *prerequisites*, *dependencies*, *ordering*, *before/after*

## Approach

Two standard algorithms — pick the one that fits:

**Kahn's Algorithm (BFS, in-degree)**
1. Build adjacency list + compute in-degree for every node.
2. Enqueue all nodes with in-degree 0.
3. Dequeue a node, add to result, decrement in-degree of its neighbors; enqueue any that reach 0.
4. If result size < n → cycle exists (some nodes were never reachable).

**DFS Post-order**
1. Run DFS from every unvisited node.
2. Push each node onto a stack *after* all its descendants are visited.
3. Pop the stack for the topological order.
4. Track `VISITING` state to detect back edges (cycles).

## Template

```java
// Kahn's Algorithm (BFS / in-degree)
public int[] topoSortKahn(int n, int[][] edges) {
    int[] inDegree = new int[n];
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());

    for (int[] e : edges) {
        adj.get(e[0]).add(e[1]);
        inDegree[e[1]]++;
    }

    Queue<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < n; i++) {
        if (inDegree[i] == 0) queue.offer(i);
    }

    int[] order = new int[n];
    int idx = 0;

    while (!queue.isEmpty()) {
        int node = queue.poll();
        order[idx++] = node;

        for (int neighbor : adj.get(node)) {
            if (--inDegree[neighbor] == 0) queue.offer(neighbor);
        }
    }

    return idx == n ? order : new int[0]; // empty → cycle detected
}
```

```java
// DFS Post-order
public int[] topoSortDFS(int n, int[][] edges) {
    List<List<Integer>> adj = new ArrayList<>();
    for (int i = 0; i < n; i++) adj.add(new ArrayList<>());
    for (int[] e : edges) adj.get(e[0]).add(e[1]);

    // 0 = unvisited, 1 = visiting, 2 = visited
    int[] state = new int[n];
    Deque<Integer> stack = new ArrayDeque<>();
    boolean[] hasCycle = {false};

    for (int i = 0; i < n; i++) {
        if (state[i] == 0) dfs(i, adj, state, stack, hasCycle);
    }

    if (hasCycle[0]) return new int[0];

    int[] order = new int[n];
    for (int i = 0; i < n; i++) order[i] = stack.pop();
    return order;
}

private void dfs(int node, List<List<Integer>> adj, int[] state,
                 Deque<Integer> stack, boolean[] hasCycle) {
    state[node] = 1; // visiting

    for (int neighbor : adj.get(node)) {
        if (state[neighbor] == 1) { hasCycle[0] = true; return; } // back edge
        if (state[neighbor] == 0) dfs(neighbor, adj, state, stack, hasCycle);
    }

    state[node] = 2; // visited
    stack.push(node); // post-order
}
```

## When to Use Kahn's vs DFS

| | Kahn's (BFS) | DFS Post-order |
|---|---|---|
| Cycle detection | Yes — check `idx == n` | Yes — check for back edge |
| Finds one valid order | Yes | Yes |
| Finds all valid orders | No (harder) | No |
| Lexicographically smallest order | Yes — use `PriorityQueue` instead of queue | No |
| Intuition | Process what's "ready" now | Finish dependencies before scheduling |

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Course Schedule | [LeetCode #207](https://leetcode.com/problems/course-schedule/) |
| 2 | Course Schedule II | [LeetCode #210](https://leetcode.com/problems/course-schedule-ii/) |
| 3 | Alien Dictionary | [LeetCode #269](https://leetcode.com/problems/alien-dictionary/) |
| 4 | Minimum Height Trees | [LeetCode #310](https://leetcode.com/problems/minimum-height-trees/) |
| 5 | Sequence Reconstruction | [LeetCode #444](https://leetcode.com/problems/sequence-reconstruction/) |
| 6 | Find All Possible Recipes from Given Supplies | [LeetCode #2115](https://leetcode.com/problems/find-all-possible-recipes-from-given-supplies/) |
| 7 | Parallel Courses | [LeetCode #1136](https://leetcode.com/problems/parallel-courses/) |
| 8 | Parallel Courses III | [LeetCode #2050](https://leetcode.com/problems/parallel-courses-iii/) |
| 9 | Sort Items by Groups Respecting Dependencies | [LeetCode #1203](https://leetcode.com/problems/sort-items-by-groups-respecting-dependencies/) |
| 10 | Build a Matrix with Conditions | [LeetCode #2392](https://leetcode.com/problems/build-a-matrix-with-conditions/) |
| 11 | Largest Color Value in a Directed Graph | [LeetCode #1857](https://leetcode.com/problems/largest-color-value-in-a-directed-graph/) |
| 12 | Find Eventual Safe States | [LeetCode #802](https://leetcode.com/problems/find-eventual-safe-states/) |
| 13 | Graph Valid Tree | [LeetCode #261](https://leetcode.com/problems/graph-valid-tree/) |
| 14 | Number of Operations to Make Network Connected | [LeetCode #1319](https://leetcode.com/problems/number-of-operations-to-make-network-connected/) |
| 15 | Maximum Employees to Be Invited to a Meeting | [LeetCode #2127](https://leetcode.com/problems/maximum-employees-to-be-invited-to-a-meeting/) |
