# Binary Tree Level Order Traversal

**Date added:** 2026-08-16

## Problem Description

Given the root of a binary tree, return the level order traversal of its nodes' values
(i.e., from left to right, level by level). Each level's values are grouped into their own
sublist.

**Source:** https://leetcode.com/problems/binary-tree-level-order-traversal/

## Examples

**Example 1**

```mermaid
graph TD
    A((3)) --> B((9))
    A --> C((20))
    C --> D((15))
    C --> E((7))

    style A fill:#fff,stroke:#333
    style B fill:#eee,stroke:#333
    style C fill:#eee,stroke:#333
    style D fill:#cce5ff,stroke:#333
    style E fill:#cce5ff,stroke:#333
```

```
Input: root = [3,9,20,null,null,15,7]
Output: [[3],[9,20],[15,7]]
Explanation: Level 0 has [3], level 1 has [9,20], level 2 has [15,7].
```

**Example 2**

```mermaid
graph TD
    A((1))

    style A fill:#fff,stroke:#333
```

```
Input: root = [1]
Output: [[1]]
Explanation: The tree has a single node; the output is one level containing just that node.
```

**Example 3**

```mermaid
graph TD
    A["(empty tree)"]

    style A fill:#f5f5f5,stroke:#999,stroke-dasharray: 5 5
```

```
Input: root = []
Output: []
Explanation: An empty tree has no nodes, so there are no levels to report.
```

**Example 4**

```mermaid
graph TD
    A((1)) --> B((2))
    B --> C((3))

    style A fill:#fff,stroke:#333
    style B fill:#eee,stroke:#333
    style C fill:#cce5ff,stroke:#333
```

```
Input: root = [1,2,null,3]
Output: [[1],[2],[3]]
Explanation: The tree is left-skewed; every level has exactly one node.
```

**Example 5**

```mermaid
graph TD
    A((1)) --> B((2))
    B --> C((3))

    style A fill:#fff,stroke:#333
    style B fill:#eee,stroke:#333
    style C fill:#cce5ff,stroke:#333
```

```
Input: root = [1,null,2,null,3]
Output: [[1],[2],[3]]
Explanation: The tree is right-skewed; every level has exactly one node, reached via right children.
```

**Example 6**

```mermaid
graph TD
    A((-1000)) --> B((-500))
    A --> C((1000))

    style A fill:#fff,stroke:#333
    style B fill:#eee,stroke:#333
    style C fill:#eee,stroke:#333
```

```
Input: root = [-1000,-500,1000]
Output: [[-1000],[-500,1000]]
Explanation: Node values can span the full constraint range; they are grouped by level regardless of sign or magnitude.
```

**Example 7**

```mermaid
graph TD
    A((1)) --> B((2))
    A --> C((3))
    B --> D((4))
    B --> E((5))
    C --> F((6))
    C --> G((7))

    style A fill:#fff,stroke:#333
    style B fill:#eee,stroke:#333
    style C fill:#eee,stroke:#333
    style D fill:#cce5ff,stroke:#333
    style E fill:#cce5ff,stroke:#333
    style F fill:#cce5ff,stroke:#333
    style G fill:#cce5ff,stroke:#333
```

```
Input: root = [1,2,3,4,5,6,7]
Output: [[1],[2,3],[4,5,6,7]]
Explanation: A complete binary tree of depth 2 produces three levels, each fully populated left to right.
```

## Constraints

- The number of nodes in the tree is in the range `[0, 2000]`.
- `-1000 <= Node.val <= 1000`

## Hints

1. How would you visit every node level by level? What data structure naturally processes elements in the order they were added?
2. A queue gives you FIFO ordering — nodes enqueued first come out first, which matches left-to-right, top-to-bottom traversal.
3. At the start of each iteration, the queue holds exactly all nodes at the current level. How can you use the queue's current size to know when one level ends and the next begins?
4. For each node you dequeue, add its children (left then right) to the back of the queue — they'll be processed in the next level's iteration.
5. Collect each level's values into a sublist, then append that sublist to the result before moving on to the next level.
