package mx.jovannypcg.base.p15_removeduplicates;

/**
 * Given an integer array {@code nums} sorted in non-decreasing order, removes duplicates
 * in-place so that each element appears only once. Returns {@code k}, the number of unique
 * elements, such that the first {@code k} elements of {@code nums} hold the unique values
 * in their original order.
 *
 * @see <a href="https://neetcode.io/problems/remove-duplicates-from-sorted-array">Remove Duplicates from Sorted Array - NeetCode</a>
 */
public class Solution {

    /*
    nums = [1, 1, 1, 1]
            s
                       f

    slow = 0
    fast = 4
    */
    public int removeDuplicates(int[] nums) {
        if (nums.length < 2) {
            return nums.length;
        }

        int n = nums.length;
        int slow = 0,
            fast = 0;

        while (fast < n) {
            while (fast < n && nums[fast] == nums[slow]) {
                fast++;
            }

            if (fast >= n) {
                break;
            }

            nums[++slow] = nums[fast++];
        }

        return slow + 1;
    }
}
