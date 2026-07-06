package mx.jovannypcg.base.p21_validwordabbreviation;

/**
 * A string can be shortened by replacing any number of non-adjacent, non-empty substrings
 * with their lengths (without leading zeros).
 *
 * <p>Given a string {@code word} and an abbreviation {@code abbr}, return {@code true} if
 * {@code abbr} correctly abbreviates {@code word}, otherwise return {@code false}.
 *
 * @see <a href="https://neetcode.io/problems/valid-word-abbreviation">Valid Word Abbreviation - NeetCode</a>
 */
public class Solution {

    public boolean validWordAbbreviation(String word, String abbr) {
        int w = 0, // word index
            a = 0, // abbr index
            jump = 0;

        /*

        abbr = s u b 4 s t i t u t i o n
                       ^
        word = s u b s t i t u t i o n
                             ^

        jump = 4
        */
        while (a < abbr.length() && w < word.length()) {
            char current = abbr.charAt(a);

            // Happy path
            if (Character.isLetter(current) && current == word.charAt(w)) {
                a++;
                w++;

                continue;
            }

            // Current pointers don't match
            if (Character.isLetter(current) && current != word.charAt(w)) {
                return false;
            }

            // `current` is a digit at this point

            // Trailing 0 not allowed
            if (current == '0') return false;

            while (a < abbr.length() && Character.isDigit(abbr.charAt(a))) {
                jump = jump * 10 + (abbr.charAt(a) - '0');
                a++;
            }

            // Jump exceeded word's length
            if (w + jump > word.length()) return false;

            w += jump; // Jump applied
            jump = 0; // Reset jump
        }

        return w == word.length() && a == abbr.length();
    }
}
