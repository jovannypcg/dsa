package mx.jovannypcg.base.p66_shortestpathinbinarymatrix;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    @DisplayName("example 1: 2x2 grid with only a diagonal route → path of length 2")
    void exampleOneReturnsTwo() {
        int[][] grid = {{0, 1}, {1, 0}};

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(2);
    }

    @Test
    @DisplayName("example 2: 3x3 grid requiring a mix of straight and diagonal moves → path of length 4")
    void exampleTwoReturnsFour() {
        int[][] grid = {{0, 0, 0}, {1, 1, 0}, {1, 1, 0}};

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(4);
    }

    @Test
    @DisplayName("example 3: start cell is blocked → no path exists")
    void exampleThreeReturnsNegativeOne() {
        int[][] grid = {{1, 0, 0}, {1, 1, 0}, {1, 1, 0}};

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("boundary n=1: single open cell → path of length 1")
    void singleCellZeroReturnsOne() {
        int[][] grid = {{0}};

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(1);
    }

    @Test
    @DisplayName("boundary n=1: single blocked cell → no path exists")
    void singleCellOneReturnsNegativeOne() {
        int[][] grid = {{1}};

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("end cell is blocked → no path exists even if the rest of the grid is open")
    void blockedEndCellReturnsNegativeOne() {
        int[][] grid = {{0, 0, 0}, {0, 0, 0}, {0, 0, 1}};

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(-1);
    }

    @Test
    @DisplayName("all-zero 3x3 grid → shortest path is the straight diagonal, length 3")
    void allZerosThreeByThreeReturnsThreeViaDiagonal() {
        int[][] grid = {{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("center obstacle blocks the direct diagonal → forces a 1-cell detour, length 4")
    void centerObstacleForcesDetourReturnsFour() {
        int[][] grid = {{0, 0, 0}, {0, 1, 0}, {0, 0, 0}};

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(4);
    }

    @Test
    @DisplayName("4x4 grid where the direct diagonal is blocked at (1,1) → shortest detour has length 5")
    void fourByFourMixedReturnsFive() {
        int[][] grid = {
                {0, 0, 0, 0},
                {1, 1, 0, 1},
                {0, 0, 0, 0},
                {0, 1, 1, 0}
        };

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(5);
    }

    @Test
    @DisplayName("boundary n=100: all-zero grid → shortest path is the straight diagonal, length 100")
    void hundredByHundredAllZerosReturnsHundred() {
        int n = 100;
        int[][] grid = new int[n][n];

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(100);
    }

    @Test
    @DisplayName("fully walled-off grid with no route to the end → no path exists")
    void noPossibleRouteReturnsNegativeOne() {
        int[][] grid = {
                {0, 1, 0},
                {1, 1, 0},
                {0, 1, 0}
        };

        int result = solution.shortestPathBinaryMatrix(grid);

        assertThat(result).isEqualTo(-1);
    }
}
