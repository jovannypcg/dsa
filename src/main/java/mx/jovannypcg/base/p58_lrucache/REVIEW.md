| | |
|---|---|
| **Solved on** | 2026-08-23 |
| **DSA Category** | Linked List |

## 1. Your Solution Assessment

**Correctness:** Handles the full LeetCode example correctly, key updates via `put`, missing-key lookups returning -1, capacity-1 thrashing, and recency refresh on both `get` and `put`. The sentinel `head`/`tail` nodes eliminate null-check edge cases when the list is empty or has a single element. All 10 tests pass.

**Code quality:** The split into `moveToHead`, `putInHead`, and `remove` helpers keeps `get`/`put` readable. A few nits:
- The public no-arg `Solution()` constructor leaves `capacity` at 0, which violates the documented "positive size capacity" precondition and isn't part of the LeetCode API — it's dead surface area that only invites misuse.
- `Node(int key, int val, Node next, Node prev)` is never called.
- Local variables `_node` and `_tail` use a leading underscore, which isn't idiomatic Java naming.
- The `key < 0` / `value < 0` guards are defensive but unreachable under the stated constraints (`0 <= key`, `0 <= value`), so they add branches without protecting against anything the grader can trigger.

None of these affect correctness.

**Time complexity:** O(1) average per `get` and `put`. `HashMap` lookup/insert/remove is O(1) average, and unlinking/relinking a node in a doubly linked list given a direct reference is O(1) — no traversal is required in either structure.

**Space complexity:** O(capacity). At most `capacity` nodes are live at once, each with one `HashMap` entry, so memory scales linearly with the configured capacity rather than the number of calls made.

**Algorithm trace** (capacity = 2; ops from the example: `put(1,1) put(2,2) get(1) put(3,3) get(2) put(4,4) get(1) get(3) get(4)`; list shown MRU → LRU):

| Op | List before | Return | List after | Evicted |
|---|---|---|---|---|
| put(1,1) | [] | — | [1] | — |
| put(2,2) | [1] | — | [2,1] | — |
| get(1) | [2,1] | 1 | [1,2] | — |
| put(3,3) | [1,2] | — | [3,1] | 2 |
| get(2) | [3,1] | -1 | [3,1] | — |
| put(4,4) | [3,1] | — | [4,3] | 1 |
| get(1) | [4,3] | -1 | [4,3] | — |
| get(3) | [4,3] | 3 | [3,4] | — |
| get(4) | [3,4] | 4 | [4,3] | — |

## 2. Optimal Approach

The implementation above already is the optimal approach: a `HashMap<key, Node>` for O(1) lookup combined with a doubly linked list that tracks recency order. The list's MRU end is the head, the LRU end is the tail; every touched key gets unlinked and relinked at the head in O(1), and eviction simply drops the node before the tail sentinel. Sentinel head/tail nodes remove the need for null checks on an empty or single-element list.

**Time complexity:** O(1) average for both `get` and `put` — one hash lookup plus a constant number of pointer updates, no traversal.

**Space complexity:** O(capacity) — one node and one map entry per cached key, bounded by capacity.

```java
public class LRUCache {
    private final int capacity;
    private final Map<Integer, Node> map = new HashMap<>();
    private final Node head = new Node(-1, -1);
    private final Node tail = new Node(-1, -1);

    public LRUCache(int capacity) {
        this.capacity = capacity;
        head.next = tail;
        tail.prev = head;
    }

    public int get(int key) {
        Node node = map.get(key);
        if (node == null) return -1;

        moveToHead(node);
        return node.val;
    }

    public void put(int key, int value) {
        Node node = map.get(key);

        if (node != null) {
            node.val = value;
            moveToHead(node);
            return;
        }

        node = new Node(key, value);
        map.put(key, node);
        insertAtHead(node);

        if (map.size() > capacity) {
            Node lru = tail.prev;
            unlink(lru);
            map.remove(lru.key);
        }
    }

    private void moveToHead(Node node) {
        unlink(node);
        insertAtHead(node);
    }

    private void insertAtHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }

    private void unlink(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    private static class Node {
        int key, val;
        Node prev, next;

        Node(int key, int val) {
            this.key = key;
            this.val = val;
        }
    }
}
```

**Algorithm trace:** identical to the trace in section 1 — this is the same approach the user implemented.

## 3. Alternative Approaches

### Brute force: array/ArrayList with linear scan

Store `(key, value)` pairs in a list ordered by recency (most recent at the end). `get` scans for the key, moves it to the end if found. `put` scans for the key; if present, updates and moves it to the end; if absent and the list is full, removes index 0 (the oldest) before appending.

- **Time complexity:** O(n) per `get`/`put`, where n = capacity — the scan to find a key, and the shift to remove/reinsert, are both linear.
- **Space complexity:** O(capacity) — one entry per cached key.
- **When acceptable:** Never for the stated constraints (up to 2×10^5 calls) since it fails the required O(1) bound, but reasonable as a first pass under interview time pressure to establish correctness before optimizing.

**Algorithm trace** (capacity = 2, same op sequence, list ordered oldest → newest):

| Op | List before | Return | List after | Evicted |
|---|---|---|---|---|
| put(1,1) | [] | — | [(1,1)] | — |
| put(2,2) | [(1,1)] | — | [(1,1),(2,2)] | — |
| get(1) | [(1,1),(2,2)] | 1 | [(2,2),(1,1)] | — |
| put(3,3) | [(2,2),(1,1)] | — | [(1,1),(3,3)] | 2 |
| get(2) | [(1,1),(3,3)] | -1 | [(1,1),(3,3)] | — |
| put(4,4) | [(1,1),(3,3)] | — | [(3,3),(4,4)] | 1 |
| get(1) | [(3,3),(4,4)] | -1 | [(3,3),(4,4)] | — |
| get(3) | [(3,3),(4,4)] | 3 | [(4,4),(3,3)] | — |
| get(4) | [(4,4),(3,3)] | 4 | [(3,3),(4,4)] | — |

### Java's built-in `LinkedHashMap` with access order

`LinkedHashMap` can be constructed with `accessOrder = true`, which reorders entries to the end on both `get` and `put`. Overriding `removeEldestEntry` to return `true` once `size() > capacity` makes eviction automatic.

```java
class LRUCache extends LinkedHashMap<Integer, Integer> {
    private final int capacity;

    public LRUCache(int capacity) {
        super(capacity, 0.75f, true);
        this.capacity = capacity;
    }

    public int get(int key) {
        return super.getOrDefault(key, -1);
    }

    public void put(int key, int value) {
        super.put(key, value);
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<Integer, Integer> eldest) {
        return size() > capacity;
    }
}
```

- **Time complexity:** O(1) average for `get`/`put` — `LinkedHashMap` already maintains an internal doubly linked list for iteration/access order, so this is the same underlying technique as the optimal approach, just implemented by the JDK.
- **Space complexity:** O(capacity) — same bound as the hand-rolled version.
- **When acceptable:** Fine in production code where reinventing the data structure adds no value, but often disallowed in interviews since the goal is usually to demonstrate you can build the hash map + doubly linked list combination yourself.

**Algorithm trace:** identical externally to the trace in section 1 — `LinkedHashMap`'s internal access-order list plays the same role as the hand-rolled doubly linked list, just hidden behind the JDK API.
