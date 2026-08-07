package mx.jovannypcg.base.p35_validsudoku;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Determine if a 9 x 9 Sudoku board is valid. Only the filled cells need to be validated
 * according to the following rules:
 *
 * <p>Each row must contain the digits 1-9 without repetition.
 * Each column must contain the digits 1-9 without repetition.
 * Each of the nine 3 x 3 sub-boxes of the grid must contain the digits 1-9 without repetition.
 *
 * <p>Note: A Sudoku board (partially filled) could be valid but is not necessarily solvable.
 * Only the filled cells need to be validated according to the mentioned rules.
 *
 * @see <a href="https://leetcode.com/problems/valid-sudoku">Problem Source</a>
 */
public class Solution {

    public boolean isValidSudoku(char[][] board) {
        int n = board.length;

        Map<Integer, Set<Character>> seenRow = new HashMap<>();
        Map<Integer, Set<Character>> seenCol = new HashMap<>();
        Map<String, Set<Character>> seenBox = new HashMap<>();

        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                char item = board[row][col];

                if (!Character.isDigit(item)) continue;

                String boxKey = (row / 3) + "," + (col / 3);

                seenRow.putIfAbsent(row, new HashSet<>());
                seenCol.putIfAbsent(col, new HashSet<>());
                seenBox.putIfAbsent(boxKey, new HashSet<>());

                boolean isRowSeen = seenRow.get(row).contains(item);
                boolean isColSeen = seenCol.get(col).contains(item);
                boolean isBoxSeen = seenBox.get(boxKey).contains(item);

                if (isRowSeen || isColSeen || isBoxSeen) return false;

                seenRow.get(row).add(item);
                seenCol.get(col).add(item);
                seenBox.get(boxKey).add(item);
            }
        }

        return true;
    }
}
