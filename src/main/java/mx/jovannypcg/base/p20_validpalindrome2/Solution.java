package mx.jovannypcg.base.p20_validpalindrome2;

/**
 * Given a string {@code s}, return {@code true} if {@code s} can be a palindrome after
 * deleting at most one character from it.
 *
 * @see <a href="https://leetcode.com/problems/valid-palindrome-ii/">Valid Palindrome II - LeetCode</a>
 */
public class Solution {

    public boolean validPalindrome(String str) {
        if (str == null || str.isBlank()) {
            return true;
        }

        int left = 0;
        int right = str.length() - 1;

        for (; left < right; left++, right--) {
            if (str.charAt(left) != str.charAt(right)) {
                return (
                    isPalindrome(str, left + 1, right) ||
                    isPalindrome(str, left, right - 1)
                );
            }
        }

        return true;
    }

    boolean isPalindrome(String str, int left, int right) {
        for (; left < right; left++, right--) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
        }

        return true;
    }
}
