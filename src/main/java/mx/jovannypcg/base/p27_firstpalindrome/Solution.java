package mx.jovannypcg.base.p27_firstpalindrome;

/**
 * Given an array of strings {@code words}, return the first palindromic string in the array.
 * If there is no such string, return an empty string {@code ""}.
 *
 * <p>A string is palindromic if it reads the same forward and backward.
 *
 * @see <a href="https://leetcode.com/problems/find-first-palindromic-string-in-the-array/">Find First Palindromic String in the Array - LeetCode</a>
 */
public class Solution {

    public String firstPalindrome(String[] words) {
        if (words == null || words.length == 0) return "";

        for (String word : words) {
            if (isPalindrome(word)) {
                return word;
            }
        }

        return "";
    }

    boolean isPalindrome(String str) {
        if (str == null || str.isBlank()) return true;

        int left = 0;
        int right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }

            left++;
            right--;
        }

        return true;
    }
}
