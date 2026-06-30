# Course Schedule II — Review

| | |
|---|---|
| **Solved on** | 2026-06-14 |
| **DSA Category** | Graphs |

---

## 1. Your Solution Assessment

**Correctness**

The implementation is correct. You chose Kahn's algorithm (BFS-based topological sort), which handles all cases:
- No prerequisites → all nodes start with in-degree 0, all are enqueued immediately.
- Valid DAG → BFS drains completely, `idx == numCourses`, valid order returned.
- Cycle → some nodes never reach in-degree 0, `idx < numCourses`, empty array returned.

The bug you caught mid-session (`inDegree[prerequisite[1]]++` → `inDegree[prerequisite[0]]++`) was the only issue, and you found it yourself.

**Code quality**

Clean and readable. Variable names (`inDegree`, `order`, `queue`, `node`, `neighbor`) are precise. The scratch-work comment block at the top is a trace of your thinking — fine during practice, but would be removed before a real submission.

**Time complexity: O(V + E)**
- Building the graph: O(E).
- Building in-degree array: O(E).
- BFS: each node enqueued/dequeued once (O(V)), each edge traversed once (O(E)).

**Space complexity: O(V + E)**
- Adjacency list: O(V + E). `inDegree` and `order` arrays: O(V). Queue: O(V) worst case.

**Algorithm trace** — Input: `numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]`

Graph: 0→1, 0→2, 1→3, 2→3. inDegree: `[0, 1, 1, 2]`. Initial queue: `[0]`

| step | dequeue | order | neighbors processed | inDegree after | enqueued |
|------|---------|-------|---------------------|----------------|----------|
| 1 | 0 | [0] | 1→0, 2→0 | [0,0,0,2] | 1, 2 |
| 2 | 1 | [0,1] | 3→1 | [0,0,0,1] | — |
| 3 | 2 | [0,1,2] | 3→0 | [0,0,0,0] | 3 |
| 4 | 3 | [0,1,2,3] | — | | |

idx=4 == numCourses=4 → return `[0,1,2,3]`

---

## 2. Optimal Approach

Kahn's algorithm (what you implemented) is already optimal for this problem.

**Strategy:** Model courses as nodes and prerequisites as directed edges. Count in-degrees. Start from nodes with no prerequisites (in-degree 0) and repeatedly "peel" them off the graph, decrementing neighbors' in-degrees. If all nodes are processed, a valid topological order exists; otherwise a cycle was detected.

**Time:** O(V + E) — each node and edge is visited once.
**Space:** O(V + E) — adjacency list dominates.

```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
    Map<Integer, List<Integer>> graph = new HashMap<>();
    int[] inDegree = new int[numCourses];
    int[] order = new int[numCourses];

    for (int i = 0; i < numCourses; i++)
        graph.put(i, new ArrayList<>());

    for (int[] pre : prerequisites) {
        graph.get(pre[1]).add(pre[0]);
        inDegree[pre[0]]++;
    }

    Deque<Integer> queue = new ArrayDeque<>();
    for (int i = 0; i < numCourses; i++)
        if (inDegree[i] == 0) queue.offer(i);

    int idx = 0;
    while (!queue.isEmpty()) {
        int node = queue.poll();
        order[idx++] = node;
        for (int neighbor : graph.get(node))
            if (--inDegree[neighbor] == 0) queue.offer(neighbor);
    }

    return idx == numCourses ? order : new int[0];
}
```

**Algorithm trace** — same as above: `numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]`

| step | dequeue | order | inDegree after |
|------|---------|-------|----------------|
| 1 | 0 | [0] | [0,0,0,2] |
| 2 | 1 | [0,1] | [0,0,0,1] |
| 3 | 2 | [0,1,2] | [0,0,0,0] |
| 4 | 3 | [0,1,2,3] | |

→ return `[0,1,2,3]`

---

## 3. Alternative Approaches

### DFS-based topological sort

Perform DFS on each unvisited node. Track three states per node: unvisited (0), visiting / on the current stack (1), and fully processed (2). If DFS reaches a node in state 1, a cycle exists — return empty. When a node's DFS call finishes, push it onto a stack. Pop the stack at the end to get topological order.

- **Time:** O(V + E) — same as Kahn's.
- **Space:** O(V + E) — adjacency list + recursion stack (O(V) depth worst case).
- **When acceptable:** Either approach is fine in an interview. DFS is more natural if you're already thinking recursively about the graph; Kahn's is more natural if you want an explicit queue and no recursion overhead.

```java
public int[] findOrder(int numCourses, int[][] prerequisites) {
    List<List<Integer>> graph = new ArrayList<>();
    for (int i = 0; i < numCourses; i++) graph.add(new ArrayList<>());
    for (int[] pre : prerequisites) graph.get(pre[1]).add(pre[0]);

    int[] state = new int[numCourses]; // 0=unvisited, 1=visiting, 2=done
    Deque<Integer> stack = new ArrayDeque<>();

    for (int i = 0; i < numCourses; i++)
        if (state[i] == 0 && hasCycle(i, graph, state, stack)) return new int[0];

    int[] order = new int[numCourses];
    for (int i = 0; i < numCourses; i++) order[i] = stack.pop();
    return order;
}

private boolean hasCycle(int node, List<List<Integer>> graph, int[] state, Deque<Integer> stack) {
    state[node] = 1;
    for (int neighbor : graph.get(node)) {
        if (state[neighbor] == 1) return true;
        if (state[neighbor] == 0 && hasCycle(neighbor, graph, state, stack)) return true;
    }
    state[node] = 2;
    stack.push(node);
    return false;
}
```

**Algorithm trace** — Input: `numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]`

| Depth | Call | Action | Returns |
|-------|------|--------|---------|
| 0 | hasCycle(0) | mark visiting | recurse into 1, 2 |
| 1 | hasCycle(1) | mark visiting | recurse into 3 |
| 2 | hasCycle(3) | mark visiting, no neighbors | push 3, mark visited, false |
| 1 | (1) | | push 1, mark visited, false |
| 0 | hasCycle(2) | mark visiting, neighbor 3 already visited (state=2) — skip | push 2, mark visited, false |
| 0 | (0) | | push 0, mark visited, false |

Stack (bottom→top): `[3, 1, 2, 0]` → pop order: `[0, 2, 1, 3]`

→ return `[0, 2, 1, 3]` (a valid topological order)

---

### Brute-force repeated scanning

Repeatedly scan all prerequisites, adding a course to the order once all its prerequisites are already in the order. Repeat until no progress is made (cycle detected) or all courses are scheduled.

- **Time:** O(V * E) — up to V passes each scanning all E edges.
- **Space:** O(V) — just the visited set and output array.
- **When acceptable:** Only for very small inputs or if you forget Kahn's under pressure.

**Algorithm trace** — Input: `numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]`

| Pass | Scan result | order |
|------|------------|-------|
| 1 | 0 has no prereqs → add | [0] |
| 2 | 1's prereq (0) in order → add; 2's prereq (0) in order → add | [0,1,2] |
| 3 | 3's prereqs (1,2) in order → add | [0,1,2,3] |

All 4 courses scheduled → return `[0,1,2,3]`
