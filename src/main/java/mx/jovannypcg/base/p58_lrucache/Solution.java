package mx.jovannypcg.base.p58_lrucache;

import java.util.HashMap;
import java.util.Map;

/**
 * Design a data structure that follows the constraints of a Least Recently Used (LRU) cache.
 *
 * <p>Implement the {@code LRUCache} class:
 * <ul>
 *   <li>{@code LRUCache(int capacity)} Initialize the LRU cache with positive size capacity.</li>
 *   <li>{@code int get(int key)} Return the value of the key if the key exists, otherwise return -1.</li>
 *   <li>{@code void put(int key, int value)} Update the value of the key if the key exists. Otherwise,
 *       add the key-value pair to the cache. If the number of keys exceeds the capacity from this
 *       operation, evict the least recently used key.</li>
 * </ul>
 *
 * <p>The functions {@code get} and {@code put} must each run in {@code O(1)} average time complexity.
 *
 * @see <a href="https://leetcode.com/problems/lru-cache/">LRU Cache - LeetCode</a>
 */
public class Solution {

    private Node head;
    private Node tail;
    private int capacity;
    private Map<Integer, Node> map;

    public Solution() {
        map = new HashMap<>();

        head = new Node(-1, -1);
        tail = new Node(-1, -1);

        head.next = tail;
        tail.prev = head;
    }

    public Solution(int capacity) {
        this();
        this.capacity = capacity;
    }

    public int get(int key) {
        if (key < 0) throw new IllegalArgumentException(
            "key must be greater or equal to 0"
        );

        if (!map.containsKey(key)) return -1;

        Node node = map.get(key);
        moveToHead(node);

        return node.val;
    }

    public void put(int key, int value) {
        if (key < 0 || value < 0) throw new IllegalArgumentException(
            "key and value must be greater or equal to 0"
        );

        if (map.containsKey(key)) {
            // Value update required
            // No eviction needed, capacity remains the same

            Node node = map.get(key);
            node.val = value;
            moveToHead(node);

            return;
        }

        // Eviction needed if capacity is exceeded

        Node node = new Node(key, value);
        map.put(key, node);
        putInHead(node);

        if (map.size() > capacity) {
            Node _tail = remove(tail.prev);
            map.remove(_tail.key);
        }
    }

    private void moveToHead(Node node) {
        Node _node = remove(node);
        putInHead(_node);
    }

    private void putInHead(Node node) {
        node.next = head.next;
        head.next.prev = node;
        node.prev = head;
        head.next = node;
    }

    private Node remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;

        node.next = null;
        node.prev = null;

        return node;
    }

    /**
     * Doubled linked list node.
     */
    private static class Node {

        int key;
        int val;
        Node next;
        Node prev;

        public Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}
