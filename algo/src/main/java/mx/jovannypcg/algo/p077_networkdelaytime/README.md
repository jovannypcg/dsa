# Network Delay Time

**Date added:** 2026-09-09

## Problem Description

You are given a network of `n` nodes, labeled from `1` to `n`. You are also given `times`, a list of travel times as directed edges `times[i] = (ui, vi, wi)`, where `ui` is the source node, `vi` is the target node, and `wi` is the time it takes for a signal to travel from source to target.

We will send a signal from a given node `k`. Return the minimum time it takes for all `n` nodes to receive the signal. If it is impossible for all `n` nodes to receive the signal, return `-1`.

**Source:** https://leetcode.com/problems/network-delay-time/

## Examples

**Example 1**
```
Input: times = [[2,1,1],[2,3,1],[3,4,1]], n = 4, k = 2
Output: 2
Explanation: The signal reaches node 1 and node 3 after 1 unit of time, then node 4 after 2 units of time. The last node to receive the signal is node 4, so the answer is 2.
```

**Example 2**
```
Input: times = [[1,2,1]], n = 2, k = 1
Output: 1
Explanation: The signal travels from node 1 to node 2 in 1 unit of time, and both nodes have now received it.
```

**Example 3**
```
Input: times = [[1,2,1]], n = 2, k = 2
Output: -1
Explanation: There is no edge leaving node 2, so node 1 never receives the signal.
```

## Constraints

- `1 <= k <= n <= 100`
- `1 <= times.length <= 6000`
- `times[i].length == 3`
- `1 <= ui, vi <= n`
- `ui != vi`
- `0 <= wi <= 100`
- All the pairs `(ui, vi)` are unique (i.e., no multiple edges between the same two nodes in the same direction).

## Hints

1. This is a directed, weighted graph problem — think about what it means to find the "time to reach every node" from one starting node.
2. You need the shortest path from a single source (`k`) to every other node in the graph.
3. Since all weights are non-negative, there's a well-known greedy algorithm that always expands the currently-closest unvisited node next.
4. A priority queue (min-heap) keyed on accumulated distance lets you efficiently pick that closest unvisited node at each step.
5. Once you've computed the shortest distance from `k` to every reachable node, the answer is the maximum of those distances — but if any node was never reached, the answer is `-1`.
