package mx.jovannypcg.base.p26_assigncookies;

import java.util.Arrays;

/**
 * Assume you are an awesome parent and want to give your children some cookies. But, you
 * should give each child at most one cookie.
 *
 * <p>Each child {@code i} has a greed factor {@code g[i]}, which is the minimum size of a
 * cookie that the child will be content with; and each cookie {@code j} has a size {@code s[j]}.
 * If {@code s[j] >= g[i]}, we can assign the cookie {@code j} to the child {@code i}, and the
 * child {@code i} will be content. Your goal is to maximize the number of your content children
 * and output the maximum number.
 *
 * @see <a href="https://leetcode.com/problems/assign-cookies/">Assign Cookies - LeetCode</a>
 */
public class Solution {
    public int findContentChildren(int[] g, int[] s) {
        Arrays.sort(g); // Sort by children's greed factor ascending order
        Arrays.sort(s); // Sort by cookie size in ascending order

        int child = 0;
        int cookie = 0;
        int count = 0;

        while (child < g.length && cookie < s.length) {
            // If child is satisfied with a cookie that meets or exceeds their greed,
            // `count` increases; next `child` is process and another `cookie` is grabbed
            // from the collection
            if (s[cookie] >= g[child]) {
                count++;
                cookie++;
                child++;
            } else {
                // When the cookie cannot satisfy the child's greed, try the next (bigger) cookie

                cookie++;
            }
        }

        return count;
    }
}
