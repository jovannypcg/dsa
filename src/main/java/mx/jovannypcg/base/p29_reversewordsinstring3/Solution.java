package mx.jovannypcg.base.p29_reversewordsinstring3;

/**
 * Given a string {@code s}, reverse the order of characters in each word within a sentence
 * while still preserving whitespace and initial word order.
 *
 * <p>Words in {@code s} are separated by a single space, and {@code s} does not contain
 * leading or trailing spaces.
 *
 * @see <a href="https://leetcode.com/problems/reverse-words-in-a-string-iii/">Reverse Words in a String III - LeetCode</a>
 */
public class Solution {
    public String reverseWords(String str) {
        if (str == null || str.isBlank())
            return str;

        int left = 0, right = 0;
        StringBuilder sb = new StringBuilder(str);

        while (right < sb.length()) {
            if (sb.charAt(right) != ' ') {
                right++;
                continue;
            }

            // End of word detected; reverse word

            reverse(sb, left, right - 1);
            right++;
            left = right;
        }

        reverse(sb, left, right - 1);

        return sb.toString();
    }

    void reverse(StringBuilder sb, int from, int to) {
        if (from < 0 || to > sb.length()) return;

        while (from < to) {
            char tmp = sb.charAt(from);
            sb.setCharAt(from, sb.charAt(to));
            sb.setCharAt(to, tmp);

            from++;
            to--;
        }
    }
}
