package mx.jovannypcg.base.p19_containerwithmostwater;

/**
 * You are given an integer array {@code height} of length {@code n}. There are {@code n}
 * vertical lines drawn such that the two endpoints of the {@code i}th line are {@code (i, 0)}
 * and {@code (i, height[i])}.
 *
 * <p>Find two lines that together with the x-axis form a container, such that the container
 * contains the most water. Return the maximum amount of water a container can store.
 *
 * <p>Notice that you may not slant the container.
 *
 * @see <a href="https://leetcode.com/problems/container-with-most-water/">Container With Most Water - LeetCode</a>
 */
public class Solution {

    public int maxArea(int[] heights) {
        if (heights == null || heights.length < 2) {
            return 0;
        }

        int n = heights.length,
            left = 0,
            right = n - 1,
            maxArea = 0;

        while (left < right) {
            int distance = right - left,
                minHeight = Math.min(heights[left], heights[right]),
                area = distance * minHeight;

            maxArea = Math.max(maxArea, area);

            // The minimum height decides the next move
            if (heights[left] <= heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
