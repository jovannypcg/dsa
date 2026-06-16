package mx.jovannypcg.base.p08_numberofislands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    void oneIsland_fullGrid() {
        char[][] grid = {
            {'1', '1', '1', '1', '0'},
            {'1', '1', '0', '1', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '0', '0', '0'}
        };
        assertThat(solution.numIslands(grid)).isEqualTo(1);
    }

    @Test
    void threeIslands() {
        char[][] grid = {
            {'1', '1', '0', '0', '0'},
            {'1', '1', '0', '0', '0'},
            {'0', '0', '1', '0', '0'},
            {'0', '0', '0', '1', '1'}
        };
        assertThat(solution.numIslands(grid)).isEqualTo(3);
    }

    @Test
    void singleLandCell() {
        char[][] grid = {{'1'}};
        assertThat(solution.numIslands(grid)).isEqualTo(1);
    }

    @Test
    void singleWaterCell() {
        char[][] grid = {{'0'}};
        assertThat(solution.numIslands(grid)).isEqualTo(0);
    }

    @Test
    void allWater() {
        char[][] grid = {
            {'0', '0', '0'},
            {'0', '0', '0'},
            {'0', '0', '0'}
        };
        assertThat(solution.numIslands(grid)).isEqualTo(0);
    }

    @Test
    void allLand() {
        char[][] grid = {
            {'1', '1', '1'},
            {'1', '1', '1'},
            {'1', '1', '1'}
        };
        assertThat(solution.numIslands(grid)).isEqualTo(1);
    }

    @Test
    void checkerboardPattern_fiveIslands() {
        char[][] grid = {
            {'1', '0', '1'},
            {'0', '1', '0'},
            {'1', '0', '1'}
        };
        assertThat(solution.numIslands(grid)).isEqualTo(5);
    }

    @Test
    void singleRow_multipleIslands() {
        char[][] grid = {{'1', '0', '1', '0', '1'}};
        assertThat(solution.numIslands(grid)).isEqualTo(3);
    }

    @Test
    void singleColumn_multipleIslands() {
        char[][] grid = {{'1'}, {'0'}, {'1'}, {'0'}, {'1'}};
        assertThat(solution.numIslands(grid)).isEqualTo(3);
    }

    @Test
    void islandAlongBorders() {
        char[][] grid = {
            {'1', '1', '1'},
            {'1', '0', '1'},
            {'1', '1', '1'}
        };
        assertThat(solution.numIslands(grid)).isEqualTo(1);
    }

    @Test
    void largeGrid_oneIsland() {
        int m = 300, n = 300;
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = '1';
            }
        }
        assertThat(solution.numIslands(grid)).isEqualTo(1);
    }

    @Test
    void largeGrid_allWater() {
        int m = 300, n = 300;
        char[][] grid = new char[m][n];
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = '0';
            }
        }
        assertThat(solution.numIslands(grid)).isEqualTo(0);
    }
}
