package mx.jovannypcg.base.p57_wordsearch;

/**
 * Given an {@code m x n} grid of characters {@code board} and a string {@code word}, return
 * {@code true} if {@code word} exists in the grid.
 *
 * <p>The word can be constructed from letters of sequentially adjacent cells, where adjacent
 * cells are horizontally or vertically neighboring. The same letter cell may not be used more
 * than once.
 *
 * @see <a href="https://leetcode.com/problems/word-search/">Word Search - LeetCode</a>
 */
public class Solution {

    private static final int[][] DIRECTIONS = new int[][] {
        { 0, 1 },
        { 0, -1 },
        { 1, 0 },
        { -1, 0 },
    };

    public boolean exist(char[][] board, String word) {
        int m = board.length;
        int n = board[0].length;

        for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[0].length; col++) {
                if (exist(board, word, row, col, 0, new boolean[m][n])) {
                    return true;
                }
            }
        }

        return false;
    }

    boolean exist(
        char[][] board,
        String word,
        int row,
        int col,
        int wordIdx,
        boolean[][] visited
    ) {
        if (wordIdx >= word.length()) return true;
        if (isOutOfBounds(board, row, col)) return false;
        if (visited[row][col]) return false;
        if (word.charAt(wordIdx) != board[row][col]) return false;

        visited[row][col] = true;

        boolean found = false;

        for (int[] direction : DIRECTIONS) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];

            if (exist(board, word, newRow, newCol, wordIdx + 1, visited)) {
                found = true;
                break;
            }
        }

        visited[row][col] = false;

        return found;
    }

    private boolean isOutOfBounds(char[][] board, int row, int col) {
        return (
            row < 0 || row >= board.length || col < 0 || col >= board[0].length
        );
    }
}
