package mx.jovannypcg.base.p57_wordsearch;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    @DisplayName("classic example ABCCED with duplicate letters and a bend in the path → true")
    void classicExampleAbccedReturnsTrue() {
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        assertThat(new Solution().exist(board, "ABCCED")).isTrue();
    }

    @Test
    @DisplayName("classic example SEE found via a short vertical-then-horizontal path → true")
    void classicExampleSeeReturnsTrue() {
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        assertThat(new Solution().exist(board, "SEE")).isTrue();
    }

    @Test
    @DisplayName("classic example ABCB where no unused neighbor holds the final letter → false")
    void classicExampleAbcbReturnsFalse() {
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        assertThat(new Solution().exist(board, "ABCB")).isFalse();
    }

    @Test
    @DisplayName("1x1 board whose only cell matches a single-character word → true")
    void singleCellMatchingWordReturnsTrue() {
        char[][] board = {{'A'}};

        assertThat(new Solution().exist(board, "A")).isTrue();
    }

    @Test
    @DisplayName("1x1 board whose only cell does not match the word → false")
    void singleCellNonMatchingWordReturnsFalse() {
        char[][] board = {{'A'}};

        assertThat(new Solution().exist(board, "B")).isFalse();
    }

    @Test
    @DisplayName("word needs 3 cells but the board only has 2, so no cell reuse can satisfy it → false")
    void cannotReuseSameCellReturnsFalse() {
        char[][] board = {{'A', 'A'}};

        assertThat(new Solution().exist(board, "AAA")).isFalse();
    }

    @Test
    @DisplayName("matching is case-sensitive, so lowercase and uppercase of the same letter are distinct → true")
    void caseSensitiveMatchingReturnsTrue() {
        char[][] board = {
                {'a', 'A'},
                {'A', 'a'}
        };

        assertThat(new Solution().exist(board, "aA")).isTrue();
    }

    @Test
    @DisplayName("matching is case-sensitive, so a lowercase cell does not satisfy an uppercase letter in the word → false")
    void caseMismatchReturnsFalse() {
        char[][] board = {
                {'a', 'b'},
                {'c', 'd'}
        };

        assertThat(new Solution().exist(board, "AB")).isFalse();
    }

    @Test
    @DisplayName("word is longer than the total number of cells, so no path can possibly exist → false")
    void wordLongerThanCellCountReturnsFalse() {
        char[][] board = {
                {'A', 'B'},
                {'C', 'D'}
        };

        assertThat(new Solution().exist(board, "ABCDE")).isFalse();
    }

    @Test
    @DisplayName("first-attempt paths dead-end and must backtrack before a valid path is found → true")
    void backtrackingRequiredReturnsTrue() {
        char[][] board = {
                {'C', 'A', 'A'},
                {'A', 'A', 'A'},
                {'B', 'C', 'D'}
        };

        assertThat(new Solution().exist(board, "AAB")).isTrue();
    }

    @Test
    @DisplayName("a Hamiltonian path visits every cell, so a probed-then-abandoned cell must be un-marked before the real path can reuse it → true")
    void unmarkOnBacktrackReturnsTrue() {
        char[][] board = {
                {'a', 'b'},
                {'c', 'd'}
        };

        assertThat(new Solution().exist(board, "acdb")).isTrue();
    }

    @Test
    @DisplayName("boundary: 6x6 board (max m,n) and 15-char word (max length) along a snake path → true")
    void maxBoardAndWordLengthBoundaryReturnsTrue() {
        char[][] board = {
                {'A', 'B', 'C', 'D', 'E', 'F'},
                {'L', 'K', 'J', 'I', 'H', 'G'},
                {'M', 'N', 'O', 'Z', 'Z', 'Z'},
                {'Z', 'Z', 'Z', 'Z', 'Z', 'Z'},
                {'Z', 'Z', 'Z', 'Z', 'Z', 'Z'},
                {'Z', 'Z', 'Z', 'Z', 'Z', 'Z'}
        };

        assertThat(new Solution().exist(board, "ABCDEFGHIJKLMNO")).isTrue();
    }

    @Test
    @DisplayName("boundary: same 6x6 max board but the last letter breaks the snake path → false")
    void maxBoardBoundaryWrongTailReturnsFalse() {
        char[][] board = {
                {'A', 'B', 'C', 'D', 'E', 'F'},
                {'L', 'K', 'J', 'I', 'H', 'G'},
                {'M', 'N', 'O', 'Z', 'Z', 'Z'},
                {'Z', 'Z', 'Z', 'Z', 'Z', 'Z'},
                {'Z', 'Z', 'Z', 'Z', 'Z', 'Z'},
                {'Z', 'Z', 'Z', 'Z', 'Z', 'Z'}
        };

        assertThat(new Solution().exist(board, "ABCDEFGHIJKLMNP")).isFalse();
    }

    @Test
    @DisplayName("word's letters do not appear anywhere in the board → false")
    void wordNotPresentAnywhereReturnsFalse() {
        char[][] board = {
                {'A', 'B', 'C', 'E'},
                {'S', 'F', 'C', 'S'},
                {'A', 'D', 'E', 'E'}
        };

        assertThat(new Solution().exist(board, "XYZ")).isFalse();
    }
}
