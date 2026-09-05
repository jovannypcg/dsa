package mx.jovannypcg.base.p70_rottingoranges;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    @DisplayName("grid with mixed fresh and rotten oranges rots fully in 4 minutes")
    void mixedGridRotsInFourMinutes() {
        Solution solution = new Solution();
        int[][] grid = {
                {2, 1, 1},
                {1, 1, 0},
                {0, 1, 1}
        };

        assertThat(solution.orangesRotting(grid)).isEqualTo(4);
    }

    @Test
    @DisplayName("unreachable fresh orange due to 4-directional isolation returns -1")
    void unreachableFreshOrangeReturnsMinusOne() {
        Solution solution = new Solution();
        int[][] grid = {
                {2, 1, 1},
                {0, 1, 1},
                {1, 0, 1}
        };

        assertThat(solution.orangesRotting(grid)).isEqualTo(-1);
    }

    @Test
    @DisplayName("no fresh oranges at minute 0 returns 0")
    void noFreshOrangesReturnsZero() {
        Solution solution = new Solution();
        int[][] grid = {{0, 2}};

        assertThat(solution.orangesRotting(grid)).isEqualTo(0);
    }

    @Test
    @DisplayName("single fresh orange with no rotten orange returns -1")
    void singleFreshOrangeWithoutRotReturnsMinusOne() {
        Solution solution = new Solution();
        int[][] grid = {{1}};

        assertThat(solution.orangesRotting(grid)).isEqualTo(-1);
    }

    @Test
    @DisplayName("single empty cell grid returns 0")
    void singleEmptyCellReturnsZero() {
        Solution solution = new Solution();
        int[][] grid = {{0}};

        assertThat(solution.orangesRotting(grid)).isEqualTo(0);
    }

    @Test
    @DisplayName("single rotten orange cell returns 0")
    void singleRottenCellReturnsZero() {
        Solution solution = new Solution();
        int[][] grid = {{2}};

        assertThat(solution.orangesRotting(grid)).isEqualTo(0);
    }

    @Test
    @DisplayName("all cells empty returns 0")
    void allEmptyCellsReturnsZero() {
        Solution solution = new Solution();
        int[][] grid = {
                {0, 0, 0},
                {0, 0, 0}
        };

        assertThat(solution.orangesRotting(grid)).isEqualTo(0);
    }

    @Test
    @DisplayName("multiple rotten sources rot the grid in parallel from both ends")
    void multipleRottenSourcesRotInParallel() {
        Solution solution = new Solution();
        int[][] grid = {
                {2, 1, 1, 1, 2},
                {1, 1, 1, 1, 1},
                {1, 1, 1, 1, 1}
        };

        assertThat(solution.orangesRotting(grid)).isEqualTo(4);
    }

    @Test
    @DisplayName("fresh orange fully enclosed by empty cells never rots")
    void freshOrangeEnclosedByEmptyCellsReturnsMinusOne() {
        Solution solution = new Solution();
        int[][] grid = {
                {2, 0, 1},
                {0, 0, 0},
                {1, 0, 1}
        };

        assertThat(solution.orangesRotting(grid)).isEqualTo(-1);
    }

    @Test
    @DisplayName("maximum sized 10x10 grid with a single rotten corner rots in expected minutes")
    void maxSizedGridRotsFromCorner() {
        Solution solution = new Solution();
        int[][] grid = new int[10][10];

        for (int[] row : grid) {
            java.util.Arrays.fill(row, 1);
        }
        grid[0][0] = 2;

        assertThat(solution.orangesRotting(grid)).isEqualTo(18);
    }

    @Test
    @DisplayName("all cells already rotten returns 0")
    void allCellsAlreadyRottenReturnsZero() {
        Solution solution = new Solution();
        int[][] grid = {
                {2, 2},
                {2, 2}
        };

        assertThat(solution.orangesRotting(grid)).isEqualTo(0);
    }

    @Test
    @DisplayName("single row grid rots sequentially from one end")
    void singleRowGridRotsSequentially() {
        Solution solution = new Solution();
        int[][] grid = {{2, 1, 1, 1, 1}};

        assertThat(solution.orangesRotting(grid)).isEqualTo(4);
    }
}
