package mx.jovannypcg.algo.p084_insertinterval;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    @DisplayName("newInterval overlaps a single existing interval and merges")
    void newIntervalOverlapsSingleInterval() {
        Solution solution = new Solution();
        int[][] intervals = {{1, 3}, {6, 9}};
        int[] newInterval = {2, 5};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{1, 5}, {6, 9}});
    }

    @Test
    @DisplayName("newInterval overlaps multiple existing intervals and merges all of them")
    void newIntervalOverlapsMultipleIntervals() {
        Solution solution = new Solution();
        int[][] intervals = {{1, 2}, {3, 5}, {6, 7}, {8, 10}, {12, 16}};
        int[] newInterval = {4, 8};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{1, 2}, {3, 10}, {12, 16}});
    }

    @Test
    @DisplayName("empty intervals array → newInterval becomes the only interval")
    void emptyIntervalsArray() {
        Solution solution = new Solution();
        int[][] intervals = {};
        int[] newInterval = {5, 7};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{5, 7}});
    }

    @Test
    @DisplayName("newInterval starts right after existing interval ends → no merge")
    void newIntervalAdjacentAfterNoMerge() {
        Solution solution = new Solution();
        int[][] intervals = {{1, 5}};
        int[] newInterval = {6, 8};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{1, 5}, {6, 8}});
    }

    @Test
    @DisplayName("newInterval fully contained within existing interval → result unchanged")
    void newIntervalFullyContained() {
        Solution solution = new Solution();
        int[][] intervals = {{1, 5}};
        int[] newInterval = {2, 3};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{1, 5}});
    }

    @Test
    @DisplayName("newInterval ends right before existing interval starts → inserted before, no merge")
    void newIntervalAdjacentBeforeNoMerge() {
        Solution solution = new Solution();
        int[][] intervals = {{1, 5}};
        int[] newInterval = {0, 0};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{0, 0}, {1, 5}});
    }

    @Test
    @DisplayName("newInterval is a single point placed between two non-touching intervals")
    void newIntervalSinglePointBetweenIntervals() {
        Solution solution = new Solution();
        int[][] intervals = {{3, 5}, {12, 15}};
        int[] newInterval = {6, 6};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{3, 5}, {6, 6}, {12, 15}});
    }

    @Test
    @DisplayName("newInterval completely engulfs every existing interval")
    void newIntervalEngulfsAllIntervals() {
        Solution solution = new Solution();
        int[][] intervals = {{2, 3}, {4, 5}, {6, 7}};
        int[] newInterval = {1, 10};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{1, 10}});
    }

    @Test
    @DisplayName("newInterval starts before the first interval and merges only with it")
    void newIntervalMergesOnlyWithFirstInterval() {
        Solution solution = new Solution();
        int[][] intervals = {{3, 5}, {9, 12}};
        int[] newInterval = {1, 4};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{1, 5}, {9, 12}});
    }

    @Test
    @DisplayName("newInterval overlaps the last interval and merges only with it")
    void newIntervalMergesOnlyWithLastInterval() {
        Solution solution = new Solution();
        int[][] intervals = {{1, 3}, {8, 10}};
        int[] newInterval = {9, 14};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{1, 3}, {8, 14}});
    }

    @Test
    @DisplayName("single interval in intervals array that exactly matches newInterval")
    void singleIntervalIdenticalToNewInterval() {
        Solution solution = new Solution();
        int[][] intervals = {{5, 5}};
        int[] newInterval = {5, 5};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{5, 5}});
    }

    @Test
    @DisplayName("boundary values at the maximum constraint range (10^5)")
    void boundaryValuesAtMaximumRange() {
        Solution solution = new Solution();
        int[][] intervals = {{0, 0}, {99998, 100000}};
        int[] newInterval = {50000, 99999};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{0, 0}, {50000, 100000}});
    }

    @Test
    @DisplayName("newInterval spans the minimum boundary value 0 to 0")
    void newIntervalAtMinimumBoundary() {
        Solution solution = new Solution();
        int[][] intervals = {{1, 5}, {10, 15}};
        int[] newInterval = {0, 0};

        int[][] result = solution.insert(intervals, newInterval);

        assertThat(result).isEqualTo(new int[][]{{0, 0}, {1, 5}, {10, 15}});
    }
}
