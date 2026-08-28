package mx.jovannypcg.base.p67_maxareaofisland;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    @DisplayName("LeetCode example grid with several islands → max area 6")
    void leetCodeExampleGrid() {
        Solution solution = new Solution();
        int[][] grid = {
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0}
        };

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(6);
    }

    @Test
    @DisplayName("grid with no land at all → returns 0")
    void allWater() {
        Solution solution = new Solution();
        int[][] grid = {{0, 0, 0, 0, 0, 0, 0, 0}};

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(0);
    }

    @Test
    @DisplayName("entire grid is one island → area equals total cell count")
    void wholeGridIsOneIsland() {
        Solution solution = new Solution();
        int[][] grid = {
                {1, 1, 1},
                {1, 1, 1},
                {1, 1, 1}
        };

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(9);
    }

    @Test
    @DisplayName("single cell grid containing land → returns 1")
    void singleCellLand() {
        Solution solution = new Solution();
        int[][] grid = {{1}};

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(1);
    }

    @Test
    @DisplayName("single cell grid containing water → returns 0")
    void singleCellWater() {
        Solution solution = new Solution();
        int[][] grid = {{0}};

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(0);
    }

    @Test
    @DisplayName("multiple islands of different sizes → returns the largest")
    void multipleIslandsDifferentSizes() {
        Solution solution = new Solution();
        int[][] grid = {
                {1, 1, 0, 0},
                {1, 0, 0, 1},
                {0, 0, 1, 1},
                {0, 0, 0, 1}
        };

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(4);
    }

    @Test
    @DisplayName("diagonally adjacent land cells do not count as connected → max area 1")
    void diagonalAdjacencyDoesNotConnect() {
        Solution solution = new Solution();
        int[][] grid = {
                {1, 0, 1},
                {0, 1, 0},
                {1, 0, 1}
        };

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(1);
    }

    @Test
    @DisplayName("border ring island with a disconnected interior cell → returns ring's area")
    void borderRingWithDisconnectedCenter() {
        Solution solution = new Solution();
        int[][] grid = {
                {1, 1, 1, 1, 1},
                {1, 0, 0, 0, 1},
                {1, 0, 1, 0, 1},
                {1, 0, 0, 0, 1},
                {1, 1, 1, 1, 1}
        };

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(16);
    }

    @Test
    @DisplayName("island touching all four edges of the grid → area counts every cell")
    void islandTouchingAllEdges() {
        Solution solution = new Solution();
        int[][] grid = {
                {1, 1, 1},
                {0, 1, 0},
                {1, 1, 1}
        };

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(7);
    }

    @Test
    @DisplayName("single row grid with one island in the middle → returns island length")
    void singleRowGrid() {
        Solution solution = new Solution();
        int[][] grid = {{0, 1, 1, 1, 0, 1, 0}};

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(3);
    }

    @Test
    @DisplayName("single column grid with one island → returns island length")
    void singleColumnGrid() {
        Solution solution = new Solution();
        int[][] grid = {{0}, {1}, {1}, {0}, {1}};

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(2);
    }

    @Test
    @DisplayName("maximum-size 50x50 grid fully covered in land → returns 2500")
    void maxSizeGridFullyLand() {
        Solution solution = new Solution();
        int[][] grid = new int[50][50];
        for (int[] row : grid) {
            java.util.Arrays.fill(row, 1);
        }

        assertThat(solution.maxAreaOfIsland(grid)).isEqualTo(2500);
    }
}
