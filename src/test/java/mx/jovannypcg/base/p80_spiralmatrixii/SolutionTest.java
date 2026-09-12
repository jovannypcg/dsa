package mx.jovannypcg.base.p80_spiralmatrixii;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    @DisplayName("n = 1, minimum boundary → single-cell matrix [[1]]")
    void minimumBoundarySingleCell() {
        int[][] expected = {{1}};

        assertThat(solution.generateMatrix(1)).isEqualTo(expected);
    }

    @Test
    @DisplayName("n = 2, smallest square with a turn → [[1,2],[4,3]]")
    void smallestSquareWithTurn() {
        int[][] expected = {
                {1, 2},
                {4, 3}
        };

        assertThat(solution.generateMatrix(2)).isEqualTo(expected);
    }

    @Test
    @DisplayName("n = 3, classic example from the problem statement")
    void classicThreeByThreeExample() {
        int[][] expected = {
                {1, 2, 3},
                {8, 9, 4},
                {7, 6, 5}
        };

        assertThat(solution.generateMatrix(3)).isEqualTo(expected);
    }

    @Test
    @DisplayName("n = 4, even-sized matrix with two complete rings and no center cell")
    void evenSizedMatrixTwoRings() {
        int[][] expected = {
                {1, 2, 3, 4},
                {12, 13, 14, 5},
                {11, 16, 15, 6},
                {10, 9, 8, 7}
        };

        assertThat(solution.generateMatrix(4)).isEqualTo(expected);
    }

    @Test
    @DisplayName("n = 5, odd-sized matrix with three rings and a single center cell")
    void oddSizedMatrixWithCenterCell() {
        int[][] expected = {
                {1, 2, 3, 4, 5},
                {16, 17, 18, 19, 6},
                {15, 24, 25, 20, 7},
                {14, 23, 22, 21, 8},
                {13, 12, 11, 10, 9}
        };

        assertThat(solution.generateMatrix(5)).isEqualTo(expected);
    }

    @Test
    @DisplayName("n = 6, even-sized matrix with three complete rings and no center cell")
    void evenSizedMatrixThreeRings() {
        int[][] expected = {
                {1, 2, 3, 4, 5, 6},
                {20, 21, 22, 23, 24, 7},
                {19, 32, 33, 34, 25, 8},
                {18, 31, 36, 35, 26, 9},
                {17, 30, 29, 28, 27, 10},
                {16, 15, 14, 13, 12, 11}
        };

        assertThat(solution.generateMatrix(6)).isEqualTo(expected);
    }

    @Test
    @DisplayName("n = 20, maximum boundary → correct shape and every value 1..400 placed exactly once")
    void maximumBoundaryHasAllValuesExactlyOnce() {
        int[][] matrix = solution.generateMatrix(20);
        Set<Integer> seen = new HashSet<>();

        assertThat(matrix).hasDimensions(20, 20);
        assertThat(matrix[0][0]).isEqualTo(1);
        assertThat(matrix[0][19]).isEqualTo(20);

        for (int[] row : matrix) {
            for (int value : row) {
                assertThat(seen.add(value)).isTrue();
                assertThat(value).isBetween(1, 400);
            }
        }

        assertThat(seen).hasSize(400);
    }
}
