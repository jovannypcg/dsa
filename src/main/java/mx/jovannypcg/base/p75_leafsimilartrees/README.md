# Leaf-Similar Trees

**Date added:** 2026-09-08

## Problem Description

Consider all the leaves of a binary tree, from left to right order, the values of those leaves form a leaf value sequence.

For example, in a tree with root 3, left child 5 (with left leaf 6 and right child 2, which has leaves 7 and 4), and right child 1 (with leaves 9 and 8), the leaf value sequence is (6, 7, 4, 9, 8).

Two binary trees are considered leaf-similar if their leaf value sequence is the same.

Return `true` if and only if the two given trees with head nodes `root1` and `root2` are leaf-similar.

**Source:** https://leetcode.com/problems/leaf-similar-trees/

## Examples

**Example 1**
```
Input: root1 = [3,5,1,6,2,9,8,null,null,7,4], root2 = [3,5,1,6,7,4,2,null,null,null,null,null,null,9,8]
Output: true
Explanation: Both trees have the same leaf value sequence: (6, 7, 4, 9, 8).
```
```mermaid
graph TD
    A["3"] --> B["5"]
    A --> C["1"]
    B --> D["6"]
    B --> E["2"]
    C --> F["9"]
    C --> G["8"]
    E --> H["7"]
    E --> I["4"]

    style D fill:#bbdefb,stroke:#1565c0
    style H fill:#bbdefb,stroke:#1565c0
    style I fill:#bbdefb,stroke:#1565c0
    style F fill:#bbdefb,stroke:#1565c0
    style G fill:#bbdefb,stroke:#1565c0
    style A fill:#ffffff,stroke:#9e9e9e
    style B fill:#ffffff,stroke:#9e9e9e
    style C fill:#ffffff,stroke:#9e9e9e
    style E fill:#ffffff,stroke:#9e9e9e
```
```mermaid
graph TD
    A2["3"] --> B2["5"]
    A2 --> C2["1"]
    B2 --> D2["6"]
    B2 --> E2["7"]
    C2 --> F2["4"]
    C2 --> G2["2"]
    G2 --> H2["9"]
    G2 --> I2["8"]

    style D2 fill:#bbdefb,stroke:#1565c0
    style E2 fill:#bbdefb,stroke:#1565c0
    style F2 fill:#bbdefb,stroke:#1565c0
    style H2 fill:#bbdefb,stroke:#1565c0
    style I2 fill:#bbdefb,stroke:#1565c0
    style A2 fill:#ffffff,stroke:#9e9e9e
    style B2 fill:#ffffff,stroke:#9e9e9e
    style C2 fill:#ffffff,stroke:#9e9e9e
    style G2 fill:#ffffff,stroke:#9e9e9e
```

**Example 2**
```
Input: root1 = [1,2,3], root2 = [1,3,2]
Output: false
Explanation: root1's leaf sequence is (2, 3); root2's leaf sequence is (3, 2). Same values, different order.
```
```mermaid
graph TD
    A["1"] --> B["2"]
    A --> C["3"]

    style B fill:#bbdefb,stroke:#1565c0
    style C fill:#bbdefb,stroke:#1565c0
    style A fill:#ffffff,stroke:#9e9e9e
```
```mermaid
graph TD
    A2["1"] --> B2["3"]
    A2 --> C2["2"]

    style B2 fill:#bbdefb,stroke:#1565c0
    style C2 fill:#bbdefb,stroke:#1565c0
    style A2 fill:#ffffff,stroke:#9e9e9e
```

**Example 3**
```
Input: root1 = [5], root2 = [5]
Output: true
Explanation: Both trees are a single node. A single node is itself a leaf, so both leaf sequences are (5).
```
```mermaid
graph TD
    A["5"]

    style A fill:#bbdefb,stroke:#1565c0
```
```mermaid
graph TD
    A2["5"]

    style A2 fill:#bbdefb,stroke:#1565c0
```

**Example 4**
```
Input: root1 = [3], root2 = [7]
Output: false
Explanation: root1's leaf sequence is (3); root2's leaf sequence is (7). Different single values.
```
```mermaid
graph TD
    A["3"]

    style A fill:#bbdefb,stroke:#1565c0
```
```mermaid
graph TD
    A2["7"]

    style A2 fill:#bbdefb,stroke:#1565c0
```

**Example 5**
```
Input: root1 = [1,2,3,4,5], root2 = [10,20,3,4,5]
Output: true
Explanation: root1's leaf sequence is (4, 5, 3) and root2's leaf sequence is (4, 5, 3) as well. The trees have very different shapes and internal values, but the leaves read the same left to right.
```
```mermaid
graph TD
    A["1"] --> B["2"]
    A --> C["3"]
    B --> D["4"]
    B --> E["5"]

    style D fill:#bbdefb,stroke:#1565c0
    style E fill:#bbdefb,stroke:#1565c0
    style C fill:#bbdefb,stroke:#1565c0
    style A fill:#ffffff,stroke:#9e9e9e
    style B fill:#ffffff,stroke:#9e9e9e
```
```mermaid
graph TD
    A2["10"] --> B2["20"]
    A2 --> C2["3"]
    B2 --> D2["4"]
    B2 --> E2["5"]

    style D2 fill:#bbdefb,stroke:#1565c0
    style E2 fill:#bbdefb,stroke:#1565c0
    style C2 fill:#bbdefb,stroke:#1565c0
    style A2 fill:#ffffff,stroke:#9e9e9e
    style B2 fill:#ffffff,stroke:#9e9e9e
```

**Example 6**
```
Input: root1 = [1,2,2], root2 = [1,2,2,null,null,2,2]
Output: false
Explanation: root1's leaf sequence is (2, 2). root2's right child (2) is not a leaf — it has two children of its own — so root2's leaf sequence is (2, 2, 2), a different length.
```
```mermaid
graph TD
    A["1"] --> B["2"]
    A --> C["2"]

    style B fill:#bbdefb,stroke:#1565c0
    style C fill:#bbdefb,stroke:#1565c0
    style A fill:#ffffff,stroke:#9e9e9e
```
```mermaid
graph TD
    A2["1"] --> B2["2"]
    A2 --> C2["2"]
    C2 --> D2["2"]
    C2 --> E2["2"]

    style B2 fill:#bbdefb,stroke:#1565c0
    style D2 fill:#bbdefb,stroke:#1565c0
    style E2 fill:#bbdefb,stroke:#1565c0
    style A2 fill:#ffffff,stroke:#9e9e9e
    style C2 fill:#ffffff,stroke:#9e9e9e
```

**Example 7**
```
Input: root1 = [1,2,3,4,4,4,4], root2 = [5,4,6,null,null,4,7,4,4]
Output: true
Explanation: root1's leaf sequence is (4, 4, 4, 4). root2 is a much deeper, unbalanced tree, but its leaf sequence, read left to right, is also (4, 4, 4, 4).
```
```mermaid
graph TD
    A["1"] --> B["2"]
    A --> C["3"]
    B --> D["4"]
    B --> E["4"]
    C --> F["4"]
    C --> G["4"]

    style D fill:#bbdefb,stroke:#1565c0
    style E fill:#bbdefb,stroke:#1565c0
    style F fill:#bbdefb,stroke:#1565c0
    style G fill:#bbdefb,stroke:#1565c0
    style A fill:#ffffff,stroke:#9e9e9e
    style B fill:#ffffff,stroke:#9e9e9e
    style C fill:#ffffff,stroke:#9e9e9e
```
```mermaid
graph TD
    A2["5"] --> B2["4"]
    A2 --> C2["6"]
    C2 --> D2["4"]
    C2 --> E2["7"]
    E2 --> F2["4"]
    E2 --> G2["4"]

    style B2 fill:#bbdefb,stroke:#1565c0
    style D2 fill:#bbdefb,stroke:#1565c0
    style F2 fill:#bbdefb,stroke:#1565c0
    style G2 fill:#bbdefb,stroke:#1565c0
    style A2 fill:#ffffff,stroke:#9e9e9e
    style C2 fill:#ffffff,stroke:#9e9e9e
    style E2 fill:#ffffff,stroke:#9e9e9e
```

## Constraints

- The number of nodes in each tree will be in the range `[1, 200]`.
- Both of the given trees will have values in the range `[0, 200]`.

## Hints

1. What does it mean for a node to be a leaf? How would you check that condition on a `TreeNode`?
2. Think about a traversal order that naturally visits leaves from left to right — which classic tree traversal does that?
3. As you traverse, collect leaf values into a list instead of processing them immediately.
4. Do this for both trees independently, then compare the two resulting lists.
5. Two lists are equal only if they have the same length and the same values in the same positions — no need for anything fancier than that comparison.
