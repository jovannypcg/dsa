package mx.jovannypcg.base.p12_mergeintervals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    final Solution solution = new Solution();

    record TestCase(int[][] intervals, int[][] expected) {}

    static Stream<TestCase> happyPathCases() {
        return Stream.of(
            new TestCase(
                new int[][]{{1,3},{2,6},{8,10},{15,18}},
                new int[][]{{1,6},{8,10},{15,18}}
            ),
            new TestCase(
                new int[][]{{1,4},{4,5}},
                new int[][]{{1,5}}
            ),
            new TestCase(
                new int[][]{{4,7},{1,4}},
                new int[][]{{1,7}}
            ),
            new TestCase(
                new int[][]{{1,4},{2,3}},
                new int[][]{{1,4}}
            ),
            new TestCase(
                new int[][]{{1,4},{0,2},{3,5}},
                new int[][]{{0,5}}
            )
        );
    }

    @ParameterizedTest(name = "[{index}] intervals={0} → {1}")
    @MethodSource("happyPathCases")
    @DisplayName("overlapping intervals are merged correctly")
    void mergesOverlappingIntervals(TestCase tc) {
        assertThat(solution.merge(tc.intervals())).isDeepEqualTo(tc.expected());
    }

    @Test
    @DisplayName("single interval returns itself unchanged")
    void singleInterval() {
        int[][] intervals = {{3, 7}};
        assertThat(solution.merge(intervals)).isDeepEqualTo(new int[][]{{3, 7}});
    }

    @Test
    @DisplayName("no overlaps — all intervals remain separate")
    void noOverlaps() {
        int[][] intervals = {{1,2},{3,4},{5,6}};
        assertThat(solution.merge(intervals)).isDeepEqualTo(new int[][]{{1,2},{3,4},{5,6}});
    }

    @Test
    @DisplayName("all intervals overlap into one")
    void allMergeIntoOne() {
        int[][] intervals = {{1,10},{2,5},{3,8}};
        assertThat(solution.merge(intervals)).isDeepEqualTo(new int[][]{{1,10}});
    }

    @Test
    @DisplayName("touching at a single point are merged (starti == endi of previous)")
    void touchingIntervalsAreMerged() {
        int[][] intervals = {{1,3},{3,5}};
        assertThat(solution.merge(intervals)).isDeepEqualTo(new int[][]{{1,5}});
    }

    @Test
    @DisplayName("intervals already sorted with no overlap")
    void alreadySortedNoOverlap() {
        int[][] intervals = {{0,1},{2,3},{4,5},{6,7}};
        assertThat(solution.merge(intervals)).isDeepEqualTo(new int[][]{{0,1},{2,3},{4,5},{6,7}});
    }

    @Test
    @DisplayName("intervals given in reverse order are sorted and merged")
    void reverseOrderInput() {
        int[][] intervals = {{5,6},{3,4},{1,2}};
        assertThat(solution.merge(intervals)).isDeepEqualTo(new int[][]{{1,2},{3,4},{5,6}});
    }

    @Test
    @DisplayName("boundary — zero-length interval (start == end) merges with touching neighbor")
    void zeroLengthInterval() {
        int[][] intervals = {{5,5},{5,5}};
        assertThat(solution.merge(intervals)).isDeepEqualTo(new int[][]{{5,5}});
    }

    @Test
    @DisplayName("boundary — maximum constraint values (start and end both 10^4)")
    void maxConstraintValues() {
        int[][] intervals = {{0,10000},{5000,10000}};
        assertThat(solution.merge(intervals)).isDeepEqualTo(new int[][]{{0,10000}});
    }
}
