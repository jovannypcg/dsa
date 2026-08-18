package mx.jovannypcg.base.p50_generateallbinarystrings;

import java.util.ArrayList;
import java.util.List;

/**
 * Given a number {@code n}, generate all strings of length {@code n} using only the
 * letters {@code 'A'} and {@code 'B'}.
 *
 * <p>Return the strings in lexicographic order, where {@code 'A'} sorts before {@code 'B'}.
 *
 * @see <a href="https://www.geeksforgeeks.org/dsa/generate-all-the-binary-strings-of-n-bits/">Generate all the binary strings of n bits - GeeksforGeeks</a>
 */
public class Solution {

    private static final char[] LETTERS = { 'A', 'B' };

    public List<String> generateStrings(int n) {
        List<String> strs = new ArrayList<>();
        backtracking(n, strs, new StringBuilder());
        return strs;
    }

    void backtracking(int n, List<String> out, StringBuilder path) {
        if (path.length() == n) {
            out.add(path.toString());
            return;
        }

        for (char c : LETTERS) {
            path.append(c);
            backtracking(n, out, path);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
