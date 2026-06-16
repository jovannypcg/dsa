package mx.jovannypcg.base.p05_validpalindrome;

/**
 * A phrase is a palindrome if, after converting all uppercase letters into lowercase letters
 * and removing all non-alphanumeric characters, it reads the same forward and backward.
 * Alphanumeric characters include letters and numbers.
 *
 * <p>Given a string {@code s}, return {@code true} if it is a palindrome, or {@code false} otherwise.
 *
 * @see <a href="https://leetcode.com/problems/valid-palindrome/">Valid Palindrome - LeetCode</a>
 */
public class Solution {
    /*
    Input: A man, a plan, a canal: Panama
             l                         r

    l = 2
    r = 28
    left = 'm' => lowercase: 'm'
    right = m => lowercase: 'm'

    same? true
    */

    public boolean isPalindrome(String s) {
        if (s == null || s.isBlank()) {
            return true;
        }

        int n = s.length();
        int left = 0;
        int right = n - 1;

        // ...
        //    l
        // r
        while (left < right) {
            while (left < right && !Character.isLetterOrDigit(s.charAt(left))) {
                left++;
            }

            while (left < right && !Character.isLetterOrDigit(s.charAt(right))) {
                right--;
            }

            // Edge case: string contains non-alphanumeric characters
            if (left > right) {
                return true;
            }

            char cLeft = Character.toLowerCase(s.charAt(left++));
            char cRight = Character.toLowerCase(s.charAt(right--));

            if (cLeft != cRight) {
                return false;
            }
        }

        return true;
    }
}
