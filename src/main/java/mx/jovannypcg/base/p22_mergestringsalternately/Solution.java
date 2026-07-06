package mx.jovannypcg.base.p22_mergestringsalternately;

/**
 * Given two strings {@code word1} and {@code word2}, merge them by alternating characters,
 * starting with {@code word1} — take one character from {@code word1}, then one from
 * {@code word2}, and repeat.
 *
 * <p>If one string is longer than the other, append the remaining characters from the
 * longer string to the end of the merged result.
 *
 * @see <a href="https://neetcode.io/problems/merge-strings-alternately">Merge Strings Alternately - NeetCode</a>
 */
public class Solution {

    public String mergeAlternately(String word1, String word2) {
        int idx = 0;
        StringBuilder sb = new StringBuilder();

        while (idx < word1.length() || idx < word2.length()) {
            if (idx < word1.length()) sb.append(word1.charAt(idx));
            if (idx < word2.length()) sb.append(word2.charAt(idx));

            idx++;
        }

        return sb.toString();
    }
}
