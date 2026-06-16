package mx.jovannypcg.base.p04_topkfrequent;

import java.util.Map;
import java.util.HashMap;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Given an integer array {@code nums} and an integer {@code k}, return the {@code k} most
 * frequent elements within the array.
 *
 * <p>The test cases are generated such that the answer is always unique.
 * The output may be returned in any order.
 *
 * @see <a href="https://neetcode.io/problems/top-k-elements-in-list">Top K Frequent Elements - NeetCode</a>
 */
public class Solution {
    /*
    nums = [1,2,2,3,3,3], k = 2
    Output: [2,3]

    [1,2,2,3,3,3]
     ^

    Capture frequencies of the items:
    freq = {
        1 -> 1,
        2 -> 2,
        3 -> 3
    }

    Entries are processed in a min priority queue; comparison is based on the entry's value:

    minHeap = [2 -> 2, 3 -> 3]

    Overall time complexity: O(n log k)
    */
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> frequencies = getFrequencies(nums);

        // Custom comparator based on the entry's value
        Comparator<Map.Entry<Integer, Integer>> freqComparator = (a, b) -> {
            return Integer.compare(a.getValue(), b.getValue());
        };

        PriorityQueue<Map.Entry<Integer, Integer>> freqMinHeap = new PriorityQueue<>(freqComparator);

        // Time complexity: O(n log k)
        for (Map.Entry<Integer, Integer> entry : frequencies.entrySet()) {
            freqMinHeap.offer(entry);

            if (freqMinHeap.size() > k) {
                freqMinHeap.poll();
            }
        }

        int[] result = new int[k];
        int i = 0;

        // O(k)
        while (!freqMinHeap.isEmpty()) {
            result[i++] = freqMinHeap.poll().getKey();
        }

        return result;
    }

    // Time complexity: O(n)
    // Space complexity: O(n); worst case is that all nums are unique
    //
    // n = number of items in n
    Map<Integer, Integer> getFrequencies(int[] nums) {
        Map<Integer, Integer> frequencies = new HashMap<>();

        for (int num : nums) {
            frequencies.put(num, frequencies.getOrDefault(num, 0) + 1);
        }

        return frequencies;
    }
}
