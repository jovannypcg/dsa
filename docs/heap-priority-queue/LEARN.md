# Heap / Priority Queue

## Pattern
Efficiently retrieve the minimum or maximum element at any time — O(log n) insert/remove, O(1) peek. Use when you repeatedly need the k-th largest/smallest, or need to process elements by priority.

## How to Recognize
- "K largest / K smallest / K most frequent"
- "Median of a data stream"
- "Merge K sorted lists/arrays"
- "Always process the minimum/maximum next"
- Greedy + priority ordering

## Approach
- **Min-heap**: `PriorityQueue<>()` (default in Java) — top is smallest.
- **Max-heap**: `PriorityQueue<>(Comparator.reverseOrder())` — top is largest.
- **K largest**: maintain a min-heap of size k. If new element > top, pop and push. Result is the heap itself.
- **K smallest**: maintain a max-heap of size k. If new element < top, pop and push.

## Template

```java
// K largest elements — min-heap of size k
public int[] kLargest(int[] nums, int k) {
    PriorityQueue<Integer> minHeap = new PriorityQueue<>(); // min-heap

    for (int n : nums) {
        minHeap.offer(n);
        if (minHeap.size() > k) minHeap.poll(); // evict smallest
    }

    return minHeap.stream().mapToInt(Integer::intValue).toArray();
}
```

```java
// Merge K sorted lists — min-heap on (value, listIndex, nodeRef)
public ListNode mergeKLists(ListNode[] lists) {
    PriorityQueue<ListNode> heap =
        new PriorityQueue<>(Comparator.comparingInt(n -> n.val));

    for (ListNode node : lists) {
        if (node != null) heap.offer(node);
    }

    ListNode dummy = new ListNode(0), curr = dummy;

    while (!heap.isEmpty()) {
        ListNode node = heap.poll();
        curr.next = node;
        curr = curr.next;
        if (node.next != null) heap.offer(node.next);
    }

    return dummy.next;
}
```

```java
// Find median from data stream — two heaps
PriorityQueue<Integer> lo = new PriorityQueue<>(Comparator.reverseOrder()); // max-heap (lower half)
PriorityQueue<Integer> hi = new PriorityQueue<>();                           // min-heap (upper half)

public void addNum(int num) {
    lo.offer(num);
    hi.offer(lo.poll());                         // balance: push max of lo into hi
    if (lo.size() < hi.size()) lo.offer(hi.poll()); // keep lo.size() >= hi.size()
}

public double findMedian() {
    return lo.size() > hi.size() ? lo.peek() : (lo.peek() + hi.peek()) / 2.0;
}
```

## Practice Problems

| # | Problem | Link |
|---|---------|------|
| 1 | Kth Largest Element in a Stream | [LeetCode #703](https://leetcode.com/problems/kth-largest-element-in-a-stream/) |
| 2 | Last Stone Weight | [LeetCode #1046](https://leetcode.com/problems/last-stone-weight/) |
| 3 | K Closest Points to Origin | [LeetCode #973](https://leetcode.com/problems/k-closest-points-to-origin/) |
| 4 | Kth Largest Element in an Array | [LeetCode #215](https://leetcode.com/problems/kth-largest-element-in-an-array/) |
| 5 | Task Scheduler | [LeetCode #621](https://leetcode.com/problems/task-scheduler/) |
| 6 | Design Twitter | [LeetCode #355](https://leetcode.com/problems/design-twitter/) |
| 7 | Find Median from Data Stream | [LeetCode #295](https://leetcode.com/problems/find-median-from-data-stream/) |
| 8 | Merge K Sorted Lists | [LeetCode #23](https://leetcode.com/problems/merge-k-sorted-lists/) |
| 9 | Top K Frequent Elements | [LeetCode #347](https://leetcode.com/problems/top-k-frequent-elements/) |
| 10 | Top K Frequent Words | [LeetCode #692](https://leetcode.com/problems/top-k-frequent-words/) |
| 11 | Ugly Number II | [LeetCode #264](https://leetcode.com/problems/ugly-number-ii/) |
| 12 | Reorganize String | [LeetCode #767](https://leetcode.com/problems/reorganize-string/) |
| 13 | Smallest Range Covering Elements from K Lists | [LeetCode #632](https://leetcode.com/problems/smallest-range-covering-elements-from-k-lists/) |
| 14 | IPO | [LeetCode #502](https://leetcode.com/problems/ipo/) |
| 15 | Meeting Rooms II | [LeetCode #253](https://leetcode.com/problems/meeting-rooms-ii/) |
| 16 | Sliding Window Maximum (heap variant) | [LeetCode #239](https://leetcode.com/problems/sliding-window-maximum/) |
| 17 | Minimize Deviation in Array | [LeetCode #1675](https://leetcode.com/problems/minimize-deviation-in-array/) |
| 18 | Single-Threaded CPU | [LeetCode #1834](https://leetcode.com/problems/single-threaded-cpu/) |
| 19 | Process Tasks Using Servers | [LeetCode #1882](https://leetcode.com/problems/process-tasks-using-servers/) |
| 20 | Find K Pairs with Smallest Sums | [LeetCode #373](https://leetcode.com/problems/find-k-pairs-with-smallest-sums/) |
