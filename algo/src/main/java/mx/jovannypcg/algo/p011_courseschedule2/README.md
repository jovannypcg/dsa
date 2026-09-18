# Course Schedule II

**Date added:** 2026-06-14

## Problem Description

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`.
You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you
must take course `bi` first if you want to take course `ai`.

Return an ordering of courses you should take to finish all courses. If there are many valid
answers, return any of them. If it is impossible to finish all courses, return an empty array.

**Source:** https://leetcode.com/problems/course-schedule-ii/

## Examples

**Example 1**
```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: [0,1]
Explanation: To take course 1 you must first take course 0. So the correct order is [0, 1].
```

**Example 2**
```
Input: numCourses = 4, prerequisites = [[1,0],[2,0],[3,1],[3,2]]
Output: [0,2,1,3]
Explanation: Course 0 must come first. Courses 1 and 2 both depend on 0. Course 3 depends on
both 1 and 2. One valid ordering is [0,1,2,3]; another is [0,2,1,3].
```

**Example 3**
```
Input: numCourses = 1, prerequisites = []
Output: [0]
Explanation: There is only one course and no prerequisites, so [0] is the answer.
```

**Example 4**
```
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: []
Explanation: Course 0 requires 1 and course 1 requires 0 — a cycle, so no valid order exists.
```

**Example 5**
```
Input: numCourses = 3, prerequisites = [[1,0],[2,1]]
Output: [0,1,2]
Explanation: A linear chain: 0 → 1 → 2. The only valid ordering is [0, 1, 2].
```

## Constraints

- `1 <= numCourses <= 2000`
- `0 <= prerequisites.length <= numCourses * (numCourses - 1)`
- `prerequisites[i].length == 2`
- `0 <= ai, bi < numCourses`
- `ai != bi`
- All the pairs `[ai, bi]` are distinct.

## Hints

1. Model the problem as a directed graph where each edge `bi → ai` means "bi must come before ai". The problem then becomes: find a topological ordering of this graph, or report that none exists.
2. A valid topological order only exists if the graph has no cycles. What graph property indicates a cycle?
3. Kahn's algorithm (BFS-based): count in-degrees for all nodes. Start by enqueuing all nodes with in-degree 0. Each time you process a node, decrement the in-degree of its neighbors — enqueue any that reach 0.
4. Alternatively, use DFS: track three states per node — unvisited, visiting (currently on the recursion stack), and visited. If you reach a "visiting" node, you've found a cycle.
5. For the DFS approach, nodes finish in reverse topological order — push each node onto a stack when the DFS call for it returns, then pop the stack to get the final ordering.
