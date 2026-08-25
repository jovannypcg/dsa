package mx.jovannypcg.base.p60_kthsmallestelementinastream;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * Design a class to find the {@code k}th smallest element in a stream of test scores. Note
 * that it is the {@code k}th smallest element in the sorted order, not the {@code k}th
 * distinct element.
 *
 * <p>Implement {@code KthSmallest} class:
 * <ul>
 *   <li>{@code KthSmallest(int k, int[] nums)} Initializes the object with the integer
 *       {@code k} and the stream of test scores {@code nums}.</li>
 *   <li>{@code int add(int val)} Adds a new test score {@code val} to the stream and
 *       returns the element representing the {@code k}th smallest element in the pool of
 *       test scores so far.</li>
 * </ul>
 */
public class Solution {

    private int k;
    private PriorityQueue<Integer> kthSmallests;

    public Solution(int k, int[] nums) {
        this.k = k;

        Comparator<Integer> maxHeapComparator = (Integer a, Integer b) -> {
            return Integer.compare(b, a);
        };

        kthSmallests = new PriorityQueue<>(maxHeapComparator);

        for (int num : nums) add(num);
    }

    public int add(int val) {
        kthSmallests.offer(val);

        if (kthSmallests.size() > k) kthSmallests.poll();

        return kthSmallests.peek();
    }
}
