package mx.jovannypcg.base.p35_validsudoku;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("example 1 board is valid")
    void example1BoardIsValid() {
        char[][] board = {
                {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        assertThat(solution.isValidSudoku(board)).isTrue();
    }

    @Test
    @DisplayName("example 2 board is invalid due to duplicate 8 in top-left box")
    void example2BoardIsInvalidDueToDuplicateInBox() {
        char[][] board = {
                {'8', '3', '.', '.', '7', '.', '.', '.', '.'},
                {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
                {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
                {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
                {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
                {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
                {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
                {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
                {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };

        assertThat(solution.isValidSudoku(board)).isFalse();
    }

    @Test
    @DisplayName("completely empty board is valid")
    void emptyBoardIsValid() {
        char[][] board = emptyBoard();

        assertThat(solution.isValidSudoku(board)).isTrue();
    }

    @Test
    @DisplayName("single filled cell is valid")
    void singleFilledCellIsValid() {
        char[][] board = emptyBoard();
        board[0][0] = '1';

        assertThat(solution.isValidSudoku(board)).isTrue();
    }

    @Test
    @DisplayName("duplicate digit within the same row is invalid")
    void duplicateInRowIsInvalid() {
        char[][] board = emptyBoard();
        board[0][0] = '5';
        board[0][8] = '5';

        assertThat(solution.isValidSudoku(board)).isFalse();
    }

    @Test
    @DisplayName("duplicate digit within the same column is invalid")
    void duplicateInColumnIsInvalid() {
        char[][] board = emptyBoard();
        board[0][0] = '9';
        board[8][0] = '9';

        assertThat(solution.isValidSudoku(board)).isFalse();
    }

    @Test
    @DisplayName("duplicate digit within the same 3x3 box but different row/column is invalid")
    void duplicateInBoxIsInvalid() {
        char[][] board = emptyBoard();
        board[0][0] = '4';
        board[2][2] = '4';

        assertThat(solution.isValidSudoku(board)).isFalse();
    }

    @Test
    @DisplayName("same digit repeated across different boxes, rows, and columns is valid")
    void sameDigitInDifferentRowColumnBoxIsValid() {
        char[][] board = emptyBoard();
        board[0][0] = '7';
        board[3][3] = '7';
        board[6][6] = '7';

        assertThat(solution.isValidSudoku(board)).isTrue();
    }

    @Test
    @DisplayName("full valid completed board is valid")
    void fullValidCompletedBoardIsValid() {
        char[][] board = {
                {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
                {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
                {'3', '4', '5', '2', '8', '6', '1', '7', '9'}
        };

        assertThat(solution.isValidSudoku(board)).isTrue();
    }

    @Test
    @DisplayName("full completed board with one duplicate is invalid")
    void fullCompletedBoardWithDuplicateIsInvalid() {
        char[][] board = {
                {'5', '3', '4', '6', '7', '8', '9', '1', '2'},
                {'6', '7', '2', '1', '9', '5', '3', '4', '8'},
                {'1', '9', '8', '3', '4', '2', '5', '6', '7'},
                {'8', '5', '9', '7', '6', '1', '4', '2', '3'},
                {'4', '2', '6', '8', '5', '3', '7', '9', '1'},
                {'7', '1', '3', '9', '2', '4', '8', '5', '6'},
                {'9', '6', '1', '5', '3', '7', '2', '8', '4'},
                {'2', '8', '7', '4', '1', '9', '6', '3', '5'},
                {'3', '4', '5', '2', '8', '6', '1', '7', '7'}
        };

        assertThat(solution.isValidSudoku(board)).isFalse();
    }

    @Test
    @DisplayName("boundary digits 1 and 9 coexisting without repetition are valid")
    void boundaryDigitsOneAndNineAreValid() {
        char[][] board = emptyBoard();
        board[0][0] = '1';
        board[0][1] = '9';
        board[8][8] = '1';
        board[8][7] = '9';

        assertThat(solution.isValidSudoku(board)).isTrue();
    }

    private char[][] emptyBoard() {
        char[][] board = new char[9][9];

        for (char[] row : board) {
            java.util.Arrays.fill(row, '.');
        }

        return board;
    }
}
