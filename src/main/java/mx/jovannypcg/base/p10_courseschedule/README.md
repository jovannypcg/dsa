# Course Schedule

**Date added:** 2026-06-14

## Problem Description

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`.
You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you must
take course `bi` first if you want to take course `ai`.

Return `true` if you can finish all courses. Otherwise, return `false`.

**Source:** https://leetcode.com/problems/course-schedule/

## Examples

**Example 1**
```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: To take course 1 you must first take course 0. That ordering is valid.
```

**Example 2**
```
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: Course 1 requires course 0, and course 0 requires course 1 — a cycle makes it impossible.
```

**Example 3**
```
Input: numCourses = 4, prerequisites = [[1,0],[2,1],[3,2]]
Output: true
Explanation: The chain 0 → 1 → 2 → 3 has no cycle, so all courses can be completed.
```

**Example 4**
```
Input: numCourses = 3, prerequisites = [[0,1],[0,2],[1,2]]
Output: true
Explanation: Take 2 first, then 1, then 0. No cycle exists.
```

**Example 5**
```
Input: numCourses = 3, prerequisites = [[0,1],[1,2],[2,0]]
Output: false
Explanation: Courses 0, 1, and 2 form a cycle — none can be started.
```

## Constraints

- `1 <= numCourses <= 2000`
- `0 <= prerequisites.length <= 5000`
- `prerequisites[i].length == 2`
- `0 <= ai, bi < numCourses`
- All the pairs `prerequisites[i]` are unique.

## Hints

1. Model this problem as a directed graph: each course is a node, each prerequisite `[a, b]` is a directed edge from `b` to `a`.
2. The question becomes: does this directed graph contain a cycle? If it does, some courses can never be completed.
3. Depth-first search (DFS) can detect cycles — track which nodes are currently on the active DFS path.
4. You need three states for each node: unvisited, visiting (on the current DFS stack), and visited (fully processed). Why?
5. If you reach a node that is already in the "visiting" state, you've found a cycle. If you fully explore a node without finding a cycle, mark it "visited" so you never re-process it.
