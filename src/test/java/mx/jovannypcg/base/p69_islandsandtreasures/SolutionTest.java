package mx.jovannypcg.base.p69_islandsandtreasures;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    private static final int INF = Integer.MAX_VALUE;

    @Test
    @DisplayName("4x4 grid with two treasures fills every land cell with shortest distance")
    void fourByFourGridWithTwoTreasures() {
        Solution solution = new Solution();
        int[][] grid = {
                {INF, -1, 0, INF},
                {INF, INF, INF, -1},
                {INF, -1, INF, -1},
                {0, -1, INF, INF}
        };
        int[][] expected = {
                {3, -1, 0, 1},
                {2, 2, 1, -1},
                {1, -1, 2, -1},
                {0, -1, 3, 4}
        };

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("2x2 grid with one treasure requires a detour around water")
    void twoByTwoGridWithDetourAroundWater() {
        Solution solution = new Solution();
        int[][] grid = {
                {0, -1},
                {INF, INF}
        };
        int[][] expected = {
                {0, -1},
                {1, 2}
        };

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("1x1 grid that is only a treasure chest stays unchanged")
    void singleCellTreasureStaysUnchanged() {
        Solution solution = new Solution();
        int[][] grid = {{0}};
        int[][] expected = {{0}};

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("1x1 grid that is only land with no treasure remains INF")
    void singleCellLandWithNoTreasureRemainsInf() {
        Solution solution = new Solution();
        int[][] grid = {{INF}};
        int[][] expected = {{INF}};

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("land cells fully walled off by water remain INF")
    void landCellsWalledOffByWaterRemainInf() {
        Solution solution = new Solution();
        int[][] grid = {
                {0, -1, INF},
                {-1, -1, -1},
                {INF, -1, INF}
        };
        int[][] expected = {
                {0, -1, INF},
                {-1, -1, -1},
                {INF, -1, INF}
        };

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("single row grid (m == 1) fills distances symmetrically from the middle treasure")
    void singleRowGridFillsSymmetrically() {
        Solution solution = new Solution();
        int[][] grid = {{INF, INF, 0, INF, INF}};
        int[][] expected = {{2, 1, 0, 1, 2}};

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("single column grid (n == 1) fills distances downward from the top treasure")
    void singleColumnGridFillsDownward() {
        Solution solution = new Solution();
        int[][] grid = {{0}, {INF}, {INF}, {INF}};
        int[][] expected = {{0}, {1}, {2}, {3}};

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("grid with no treasure at all leaves every land cell as INF")
    void gridWithNoTreasureLeavesEverythingInf() {
        Solution solution = new Solution();
        int[][] grid = {
                {INF, INF},
                {INF, INF}
        };
        int[][] expected = {
                {INF, INF},
                {INF, INF}
        };

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("grid with only water and no land or treasure stays unchanged")
    void gridWithOnlyWaterStaysUnchanged() {
        Solution solution = new Solution();
        int[][] grid = {
                {-1, -1},
                {-1, -1}
        };
        int[][] expected = {
                {-1, -1},
                {-1, -1}
        };

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("all cells are treasure chests so nothing needs filling")
    void allCellsAreTreasureChests() {
        Solution solution = new Solution();
        int[][] grid = {
                {0, 0},
                {0, 0}
        };
        int[][] expected = {
                {0, 0},
                {0, 0}
        };

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }

    @Test
    @DisplayName("land cell equidistant from two treasures picks the shared minimum distance")
    void landCellEquidistantFromTwoTreasures() {
        Solution solution = new Solution();
        int[][] grid = {
                {0, INF, INF, 0}
        };
        int[][] expected = {
                {0, 1, 1, 0}
        };

        solution.islandsAndTreasure(grid);

        assertThat(grid).isEqualTo(expected);
    }
}
