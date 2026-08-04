package mx.jovannypcg.base.p31_arraystringsareequal;

/**
 * Given two string arrays {@code word1} and {@code word2}, return {@code true} if the two
 * arrays represent the same string, and {@code false} otherwise.
 *
 * <p>A string is represented by an array if the array elements concatenated in order form
 * the string.
 *
 * @see <a href="https://leetcode.com/problems/check-if-two-string-arrays-are-equivalent/">Check if Two String Arrays are Equivalent - LeetCode</a>
 */
public class Solution {

    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        // 2D coordinates to locate the current character under comparison
        int word1Index = 0, // Traverses each `String` in `word1`
            char1Index = 0, // Traverses each character in the current `String` of `word1`
            word2Index = 0,
            char2Index = 0;

        while (word1Index < word1.length && word2Index < word2.length) {
            if (word1[word1Index].charAt(char1Index) != word2[word2Index].charAt(char2Index)) {
                return false;
            }

            char1Index++;
            char2Index++;

            if (char1Index >= word1[word1Index].length()) {
                char1Index = 0;
                word1Index++;
            }

            if (char2Index >= word2[word2Index].length()) {
                char2Index = 0;
                word2Index++;
            }
        }

        return word1Index >= word1.length && word2Index >= word2.length;
    }
}
