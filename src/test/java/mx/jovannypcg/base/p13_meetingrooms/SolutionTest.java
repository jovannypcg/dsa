package mx.jovannypcg.base.p13_meetingrooms;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    final Solution solution = new Solution();

    record TestCase(int[][] intervals, boolean expected) {}

    static Stream<TestCase> overlapCases() {
        return Stream.of(
            new TestCase(new int[][]{{0,30},{5,10},{15,20}}, false),
            new TestCase(new int[][]{{1,5},{5,10}},          false),
            new TestCase(new int[][]{{1,4},{2,3}},           false),
            new TestCase(new int[][]{{0,5},{4,6},{8,10}},    false)
        );
    }

    static Stream<TestCase> noOverlapCases() {
        return Stream.of(
            new TestCase(new int[][]{{7,10},{2,4}},          true),
            new TestCase(new int[][]{{1,2},{3,4},{5,6}},     true),
            new TestCase(new int[][]{{0,1},{2,3},{4,5}},     true)
        );
    }

    @ParameterizedTest(name = "[{index}] intervals={0} → overlap detected, expected=false")
    @MethodSource("overlapCases")
    @DisplayName("overlapping meetings return false")
    void overlappingMeetings(TestCase tc) {
        assertThat(solution.canAttendMeetings(tc.intervals())).isEqualTo(tc.expected());
    }

    @ParameterizedTest(name = "[{index}] intervals={0} → no overlap, expected=true")
    @MethodSource("noOverlapCases")
    @DisplayName("non-overlapping meetings return true")
    void nonOverlappingMeetings(TestCase tc) {
        assertThat(solution.canAttendMeetings(tc.intervals())).isEqualTo(tc.expected());
    }

    @Test
    @DisplayName("empty intervals array returns true")
    void emptyIntervals() {
        assertThat(solution.canAttendMeetings(new int[][]{})).isTrue();
    }

    @Test
    @DisplayName("single meeting returns true")
    void singleMeeting() {
        assertThat(solution.canAttendMeetings(new int[][]{{3,7}})).isTrue();
    }

    @Test
    @DisplayName("touching at a single point (end == next start) returns false")
    void touchingAtEndpoint() {
        assertThat(solution.canAttendMeetings(new int[][]{{1,5},{5,10}})).isFalse();
    }

    @Test
    @DisplayName("unsorted input with no overlap returns true")
    void unsortedNoOverlap() {
        assertThat(solution.canAttendMeetings(new int[][]{{10,15},{1,5},{6,9}})).isTrue();
    }

    @Test
    @DisplayName("unsorted input with overlap returns false")
    void unsortedWithOverlap() {
        assertThat(solution.canAttendMeetings(new int[][]{{10,20},{1,5},{4,8}})).isFalse();
    }

    @Test
    @DisplayName("boundary — maximum constraint values (end = 10^6) with no overlap")
    void maxConstraintValues() {
        assertThat(solution.canAttendMeetings(new int[][]{{0,500000},{500001,1000000}})).isTrue();
    }

    @Test
    @DisplayName("boundary — two meetings that start at same time return false")
    void sameSameStart() {
        assertThat(solution.canAttendMeetings(new int[][]{{5,10},{5,8}})).isFalse();
    }
}
