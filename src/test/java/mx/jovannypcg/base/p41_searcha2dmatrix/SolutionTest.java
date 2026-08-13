package mx.jovannypcg.base.p41_searcha2dmatrix;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    @DisplayName("target present in a middle row → true")
    void targetPresentInMiddleRow() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};

        assertThat(solution.searchMatrix(matrix, 3)).isTrue();
    }

    @Test
    @DisplayName("target absent, falls within a row's range → false")
    void targetAbsentWithinRowRange() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};

        assertThat(solution.searchMatrix(matrix, 13)).isFalse();
    }

    @Test
    @DisplayName("single-cell matrix, target equals the only element → true")
    void singleCellMatrixTargetPresent() {
        Solution solution = new Solution();
        int[][] matrix = {{1}};

        assertThat(solution.searchMatrix(matrix, 1)).isTrue();
    }

    @Test
    @DisplayName("single-cell matrix, target differs from the only element → false")
    void singleCellMatrixTargetAbsent() {
        Solution solution = new Solution();
        int[][] matrix = {{1}};

        assertThat(solution.searchMatrix(matrix, 2)).isFalse();
    }

    @Test
    @DisplayName("single-row matrix, target is the first element → true")
    void singleRowMatrixTargetAtStart() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 5, 7}};

        assertThat(solution.searchMatrix(matrix, 1)).isTrue();
    }

    @Test
    @DisplayName("single-row matrix, target is the last element → true")
    void singleRowMatrixTargetAtEnd() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 5, 7}};

        assertThat(solution.searchMatrix(matrix, 7)).isTrue();
    }

    @Test
    @DisplayName("single-column matrix, target present → true")
    void singleColumnMatrixTargetPresent() {
        Solution solution = new Solution();
        int[][] matrix = {{1}, {3}, {5}, {7}};

        assertThat(solution.searchMatrix(matrix, 5)).isTrue();
    }

    @Test
    @DisplayName("target smaller than every element in the matrix → false")
    void targetSmallerThanAllElements() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};

        assertThat(solution.searchMatrix(matrix, 0)).isFalse();
    }

    @Test
    @DisplayName("target larger than every element in the matrix → false")
    void targetLargerThanAllElements() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};

        assertThat(solution.searchMatrix(matrix, 100)).isFalse();
    }

    @Test
    @DisplayName("matrix with negative values, target present → true")
    void matrixWithNegativeValuesTargetPresent() {
        Solution solution = new Solution();
        int[][] matrix = {{-10, -8, -5}, {-3, -1, 0}, {2, 4, 6}};

        assertThat(solution.searchMatrix(matrix, -1)).isTrue();
    }

    @Test
    @DisplayName("target equals the first element of the matrix → true")
    void targetEqualsFirstElement() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};

        assertThat(solution.searchMatrix(matrix, 1)).isTrue();
    }

    @Test
    @DisplayName("target equals the last element of the matrix → true")
    void targetEqualsLastElement() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 5, 7}, {10, 11, 16, 20}, {23, 30, 34, 60}};

        assertThat(solution.searchMatrix(matrix, 60)).isTrue();
    }

    @Test
    @DisplayName("row contains duplicate values, target present among duplicates → true")
    void rowWithDuplicateValuesTargetPresent() {
        Solution solution = new Solution();
        int[][] matrix = {{1, 3, 3, 3}, {4, 5, 6, 6}};

        assertThat(solution.searchMatrix(matrix, 3)).isTrue();
    }

    @Test
    @DisplayName("boundary values at the lower constraint limit → true")
    void boundaryValueLowerLimit() {
        Solution solution = new Solution();
        int[][] matrix = {{-10000, -9999}, {-9998, -9997}};

        assertThat(solution.searchMatrix(matrix, -10000)).isTrue();
    }

    @Test
    @DisplayName("boundary values at the upper constraint limit → true")
    void boundaryValueUpperLimit() {
        Solution solution = new Solution();
        int[][] matrix = {{9997, 9998}, {9999, 10000}};

        assertThat(solution.searchMatrix(matrix, 10000)).isTrue();
    }
}
