package mx.jovannypcg.base.p79_spiralmatrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("single cell matrix (1x1, minimum size) → returns that one element")
    void singleCellMatrix() {
        int[][] matrix = {{42}};

        assertThat(solution.spiralOrder(matrix)).isEqualTo(List.of(42));
    }

    @Test
    @DisplayName("single row matrix → left-to-right order, no downward turn")
    void singleRowMatrix() {
        int[][] matrix = {{1, 2, 3, 4, 5}};

        assertThat(solution.spiralOrder(matrix)).isEqualTo(List.of(1, 2, 3, 4, 5));
    }

    @Test
    @DisplayName("single column matrix → top-to-bottom order, no sideways turn")
    void singleColumnMatrix() {
        int[][] matrix = {{1}, {2}, {3}, {4}, {5}};

        assertThat(solution.spiralOrder(matrix)).isEqualTo(List.of(1, 2, 3, 4, 5));
    }

    @Test
    @DisplayName("2x2 square → smallest non-trivial square, one ring only")
    void twoByTwoSquare() {
        int[][] matrix = {{1, 2}, {3, 4}};

        assertThat(solution.spiralOrder(matrix)).isEqualTo(List.of(1, 2, 4, 3));
    }

    @Test
    @DisplayName("3x3 square → classic case with an outer ring and a single-cell inner layer")
    void threeByThreeSquare() {
        int[][] matrix = {{1, 2, 3}, {4, 5, 6}, {7, 8, 9}};

        assertThat(solution.spiralOrder(matrix)).isEqualTo(List.of(1, 2, 3, 6, 9, 8, 7, 4, 5));
    }

    @Test
    @DisplayName("3x4 wide rectangle (m < n) → outer ring plus a flat two-cell inner layer")
    void wideRectangle() {
        int[][] matrix = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}};

        assertThat(solution.spiralOrder(matrix))
                .isEqualTo(List.of(1, 2, 3, 4, 8, 12, 11, 10, 9, 5, 6, 7));
    }

    @Test
    @DisplayName("4x3 tall rectangle (m > n) with negative values → sign is preserved")
    void tallRectangleWithNegatives() {
        int[][] matrix = {{-1, -2, -3}, {-4, -5, -6}, {-7, -8, -9}, {-10, -11, -12}};

        assertThat(solution.spiralOrder(matrix))
                .isEqualTo(List.of(-1, -2, -3, -6, -9, -12, -11, -10, -7, -4, -5, -8));
    }

    @Test
    @DisplayName("value boundaries (-100 and 100) → extremes from the value constraint are handled")
    void valueBoundaries() {
        int[][] matrix = {{-100, 100}, {100, -100}};

        assertThat(solution.spiralOrder(matrix)).isEqualTo(List.of(-100, 100, -100, 100));
    }

    @Test
    @DisplayName("1x10 row → maximum n from the size constraint on a single row")
    void maxWidthSingleRow() {
        int[][] matrix = {{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}};

        assertThat(solution.spiralOrder(matrix))
                .isEqualTo(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    @Test
    @DisplayName("10x1 column → maximum m from the size constraint on a single column")
    void maxHeightSingleColumn() {
        int[][] matrix = {{1}, {2}, {3}, {4}, {5}, {6}, {7}, {8}, {9}, {10}};

        assertThat(solution.spiralOrder(matrix))
                .isEqualTo(List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }
}
