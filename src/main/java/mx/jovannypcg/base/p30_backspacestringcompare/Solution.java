package mx.jovannypcg.base.p30_backspacestringcompare;

/**
 * Given two strings {@code s} and {@code t}, return {@code true} if they are equal when both
 * are typed into empty text editors. {@code '#'} means a backspace character.
 *
 * <p>Note that after backspacing an empty text, the text will continue empty.
 *
 * @see <a href="https://leetcode.com/problems/backspace-string-compare/">Backspace String Compare - LeetCode</a>
 */
public class Solution {

    public boolean backspaceCompare(String q, String p) {
        int qPos = q.length() - 1,
            qBackspaceCount = 0;
        int pPos = p.length() - 1,
            pBackspaceCount = 0;

        while (qPos >= 0 || pPos >= 0) {
            while (qPos >= 0) {
                qPos -= qBackspaceCount;
                qBackspaceCount = 0;

                if (qPos < 0) break;
                if (q.charAt(qPos) != '#') break;

                while (qPos >= 0 && q.charAt(qPos) == '#') {
                    qBackspaceCount++;
                    qPos--;
                }
            }

            while (pPos >= 0) {
                pPos -= pBackspaceCount;
                pBackspaceCount = 0;

                if (pPos < 0) break;
                if (p.charAt(pPos) != '#') break;

                while (pPos >= 0 && p.charAt(pPos) == '#') {
                    pBackspaceCount++;
                    pPos--;
                }
            }

            char qChar = qPos >= 0 ? q.charAt(qPos) : '\0';
            char pChar = pPos >= 0 ? p.charAt(pPos) : '\0';

            if (qChar != pChar) return false;

            qPos--;
            pPos--;
        }

        return qPos < 0 && pPos < 0;
    }
}
