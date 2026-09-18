package mx.jovannypcg.algo.p078_rotateimage;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    @DisplayName("single-cell matrix (n == 1) → unchanged")
    void singleCellMatrixUnchanged() {
        int[][] matrix = {{5}};

        new Solution().rotate(matrix);

        assertThat(matrix).isEqualTo(new int[][] {{5}});
    }

    @Test
    @DisplayName("2x2 matrix → rotates smallest non-trivial case")
    void twoByTwoMatrixRotates() {
        int[][] matrix = {{1, 2}, {3, 4}};

        new Solution().rotate(matrix);

        assertThat(matrix).isEqualTo(new int[][] {{3, 1}, {4, 2}});
    }

    @Test
    @DisplayName("3x3 matrix → classic clockwise rotation")
    void threeByThreeMatrixRotates() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        new Solution().rotate(matrix);

        assertThat(matrix).isEqualTo(new int[][] {{7, 4, 1}, {8, 5, 2}, {9, 6, 3}});
    }

    @Test
    @DisplayName("4x4 matrix → rotates even-sized square")
    void fourByFourMatrixRotates() {
        int[][] matrix = {
            {5, 1, 9, 11},
            {2, 4, 8, 10},
            {13, 3, 6, 7},
            {15, 14, 12, 16},
        };

        new Solution().rotate(matrix);

        assertThat(matrix).isEqualTo(new int[][] {
            {15, 13, 2, 5},
            {14, 3, 4, 1},
            {12, 6, 8, 9},
            {16, 7, 10, 11},
        });
    }

    @Test
    @DisplayName("matrix with negative values → preserves sign while rotating")
    void negativeValuesRotateCorrectly() {
        int[][] matrix = {{-1, -2, -3}, {-4, -5, -6}, {-7, -8, -9}};

        new Solution().rotate(matrix);

        assertThat(matrix).isEqualTo(new int[][] {{-7, -4, -1}, {-8, -5, -2}, {-9, -6, -3}});
    }

    @Test
    @DisplayName("matrix with duplicate values → rotation is purely positional")
    void duplicateValuesRotateByPosition() {
        int[][] matrix = {{1, 1, 1}, {2, 2, 2}, {3, 3, 3}};

        new Solution().rotate(matrix);

        assertThat(matrix).isEqualTo(new int[][] {{3, 2, 1}, {3, 2, 1}, {3, 2, 1}});
    }

    @Test
    @DisplayName("5x5 matrix → rotation pattern scales to a larger square")
    void fiveByFiveMatrixRotates() {
        int[][] matrix = {
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15},
            {16, 17, 18, 19, 20},
            {21, 22, 23, 24, 25},
        };

        new Solution().rotate(matrix);

        assertThat(matrix).isEqualTo(new int[][] {
            {21, 16, 11, 6, 1},
            {22, 17, 12, 7, 2},
            {23, 18, 13, 8, 3},
            {24, 19, 14, 9, 4},
            {25, 20, 15, 10, 5},
        });
    }

    @Test
    @DisplayName("matrix with boundary values (-1000 and 1000) → handles extremes")
    void boundaryValuesRotateCorrectly() {
        int[][] matrix = {{1000, -1000}, {-1000, 1000}};

        new Solution().rotate(matrix);

        assertThat(matrix).isEqualTo(new int[][] {{-1000, 1000}, {1000, -1000}});
    }
}
