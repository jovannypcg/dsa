package mx.jovannypcg.base.p24_mergetwo2darrays;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("example 1: overlapping and non-overlapping ids get merged and summed")
    void overlappingAndNonOverlappingIds() {
        int[][] nums1 = {{1, 2}, {2, 3}, {4, 5}};
        int[][] nums2 = {{1, 4}, {3, 2}, {4, 1}};

        int[][] result = solution.mergeArrays(nums1, nums2);

        assertThat(result).isEqualTo(new int[][]{{1, 6}, {2, 3}, {3, 2}, {4, 6}});
    }

    @Test
    @DisplayName("example 2: no common ids, arrays are simply merged in order")
    void noCommonIds() {
        int[][] nums1 = {{2, 4}, {3, 6}, {5, 5}};
        int[][] nums2 = {{1, 3}, {4, 3}};

        int[][] result = solution.mergeArrays(nums1, nums2);

        assertThat(result).isEqualTo(new int[][]{{1, 3}, {2, 4}, {3, 6}, {4, 3}, {5, 5}});
    }

    @Test
    @DisplayName("single matching id in both arrays sums to one entry")
    void singleMatchingId() {
        int[][] nums1 = {{1, 3}};
        int[][] nums2 = {{1, 7}};

        int[][] result = solution.mergeArrays(nums1, nums2);

        assertThat(result).isEqualTo(new int[][]{{1, 10}});
    }

    @Test
    @DisplayName("values at upper bound (1000 each) sum correctly without overflow or truncation")
    void valuesAtUpperBound() {
        int[][] nums1 = {{1, 1000}};
        int[][] nums2 = {{1, 1000}};

        int[][] result = solution.mergeArrays(nums1, nums2);

        assertThat(result).isEqualTo(new int[][]{{1, 2000}});
    }

    @Test
    @DisplayName("second array's ids are all greater than first array's ids, gets appended at the end")
    void secondArrayAppendedAtEnd() {
        int[][] nums1 = {{1, 5}, {2, 6}};
        int[][] nums2 = {{3, 7}};

        int[][] result = solution.mergeArrays(nums1, nums2);

        assertThat(result).isEqualTo(new int[][]{{1, 5}, {2, 6}, {3, 7}});
    }

    @Test
    @DisplayName("first array's ids are all greater than second array's ids, gets appended at the end")
    void firstArrayAppendedAtEnd() {
        int[][] nums1 = {{3, 7}};
        int[][] nums2 = {{1, 5}, {2, 6}};

        int[][] result = solution.mergeArrays(nums1, nums2);

        assertThat(result).isEqualTo(new int[][]{{1, 5}, {2, 6}, {3, 7}});
    }

    @Test
    @DisplayName("single-element arrays with the minimum id and value (1, 1)")
    void minimumIdAndValueBoundary() {
        int[][] nums1 = {{1, 1}};
        int[][] nums2 = {{2, 1}};

        int[][] result = solution.mergeArrays(nums1, nums2);

        assertThat(result).isEqualTo(new int[][]{{1, 1}, {2, 1}});
    }

    @Test
    @DisplayName("interleaved ids alternate between both arrays")
    void interleavedIds() {
        int[][] nums1 = {{1, 10}, {3, 30}, {5, 50}};
        int[][] nums2 = {{2, 20}, {4, 40}, {6, 60}};

        int[][] result = solution.mergeArrays(nums1, nums2);

        assertThat(result).isEqualTo(new int[][]{{1, 10}, {2, 20}, {3, 30}, {4, 40}, {5, 50}, {6, 60}});
    }
}
