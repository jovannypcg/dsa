package mx.jovannypcg.base.p59_kthlargestelementinastream;

import java.util.PriorityQueue;

/**
 * Design a class to find the {@code k}th largest element in a stream of test scores. Note
 * that it is the {@code k}th largest element in the sorted order, not the {@code k}th
 * distinct element.
 *
 * <p>Implement {@code KthLargest} class:
 * <ul>
 *   <li>{@code KthLargest(int k, int[] nums)} Initializes the object with the integer
 *       {@code k} and the stream of test scores {@code nums}.</li>
 *   <li>{@code int add(int val)} Adds a new test score {@code val} to the stream and
 *       returns the element representing the {@code k}th largest element in the pool of
 *       test scores so far.</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/kth-largest-element-in-a-stream">Problem Source</a>
 */
public class Solution {
    private int k;
    private PriorityQueue<Integer> kthLargests;

    public Solution(int k, int[] nums) {
        this.k = k;
        kthLargests = new PriorityQueue<>();

        for (int num : nums) {
            add(num);
        }
    }

    public int add(int val) {
        kthLargests.offer(val);

        if (kthLargests.size() > k) {
            kthLargests.poll();
        }

        return kthLargests.peek();
    }
}
