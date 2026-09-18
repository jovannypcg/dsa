# LRU Cache

**Date added:** 2026-08-23

## Problem Description

Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.

Implement the `LRUCache` class:

- `LRUCache(int capacity)` Initialize the LRU cache with positive size capacity.
- `int get(int key)` Return the value of the key if the key exists, otherwise return -1.
- `void put(int key, int value)` Update the value of the key if the key exists. Otherwise, add the key-value pair to the cache. If the number of keys exceeds the capacity from this operation, evict the least recently used key.

The functions `get` and `put` must each run in O(1) average time complexity.

**Source:** https://leetcode.com/problems/lru-cache

## Examples

**Example 1**
```
Input
["LRUCache", "put", "put", "get", "put", "get", "put", "get", "get", "get"]
[[2], [1, 1], [2, 2], [1], [3, 3], [2], [4, 4], [1], [3], [4]]
Output
[null, null, null, 1, null, -1, null, -1, 3, 4]
```
Explanation: `LRUCache lRUCache = new LRUCache(2);` `lRUCache.put(1, 1);` cache is `{1=1}`. `lRUCache.put(2, 2);` cache is `{1=1, 2=2}`. `lRUCache.get(1);` returns 1. `lRUCache.put(3, 3);` LRU key was 2, evicts key 2, cache is `{1=1, 3=3}`. `lRUCache.get(2);` returns -1 (not found). `lRUCache.put(4, 4);` LRU key was 1, evicts key 1, cache is `{4=4, 3=3}`. `lRUCache.get(1);` returns -1 (not found). `lRUCache.get(3);` returns 3. `lRUCache.get(4);` returns 4.

**Example 2**
```
Input
["LRUCache", "put", "get", "put", "get", "get"]
[[1], [2, 1], [2], [3, 2], [2], [3]]
Output
[null, null, 1, null, -1, 2]
```
Explanation: Capacity is 1, so every `put` on a new key evicts the current entry. `put(2, 1)` stores `{2=1}`. `get(2)` returns 1. `put(3, 2)` evicts key 2, cache is `{3=2}`. `get(2)` returns -1 (evicted). `get(3)` returns 2.

**Example 3**
```
Input
["LRUCache", "put", "put", "get", "get"]
[[2], [1, 1], [1, 2], [1], [1]]
Output
[null, null, null, 2, 2]
```
Explanation: `put(1, 1)` stores `{1=1}`. `put(1, 2)` updates the existing key, cache is `{1=2}` (no eviction since the key already existed). `get(1)` returns 2 both times.

## Constraints

- `1 <= capacity <= 3000`
- `0 <= key <= 10^4`
- `0 <= value <= 10^5`
- At most `2 * 10^5` calls will be made to `get` and `put`.

## Hints

1. What two operations need to be fast: "find a value by key" and "know which entry was used least recently"?
2. A hash map alone gives you O(1) lookup, but it can't tell you usage order. What data structure naturally tracks order and allows O(1) removal from either end?
3. Consider combining a hash map (key → node) with a doubly linked list (usage order), so a node can be unlinked and relinked in O(1) without scanning.
4. On every `get` or `put` that touches an existing key, that key becomes the most recently used — move its node to one end of the list.
5. When `put` adds a new key and capacity is exceeded, the node at the opposite end of the list is the least recently used — remove it from both the list and the map.
