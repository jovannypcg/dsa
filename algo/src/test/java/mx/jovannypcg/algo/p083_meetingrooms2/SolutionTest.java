package mx.jovannypcg.algo.p083_meetingrooms2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    final Solution solution = new Solution();

    @Test
    @DisplayName("classic example — one big meeting overlapping two smaller, non-overlapping ones needs 2 rooms")
    void classicExample() {
        assertThat(solution.minMeetingRooms(new int[][]{{0, 30}, {5, 10}, {15, 20}})).isEqualTo(2);
    }

    @Test
    @DisplayName("two non-overlapping meetings need only 1 room")
    void noOverlapTwoMeetings() {
        assertThat(solution.minMeetingRooms(new int[][]{{7, 10}, {2, 4}})).isEqualTo(1);
    }

    @Test
    @DisplayName("three meetings with one freed-up room reused need 2 rooms")
    void threeMeetingsTwoRoomsNeeded() {
        assertThat(solution.minMeetingRooms(new int[][]{{9, 10}, {4, 9}, {4, 17}})).isEqualTo(2);
    }

    @Test
    @DisplayName("single meeting needs 1 room")
    void singleMeeting() {
        assertThat(solution.minMeetingRooms(new int[][]{{5, 8}})).isEqualTo(1);
    }

    @Test
    @DisplayName("three simultaneously overlapping meetings need 3 rooms")
    void allMeetingsOverlapSimultaneously() {
        assertThat(solution.minMeetingRooms(new int[][]{{1, 10}, {2, 6}, {3, 5}})).isEqualTo(3);
    }

    @Test
    @DisplayName("touching endpoints (end == next start) do not count as overlap — 1 room")
    void touchingEndpointsNoOverlap() {
        assertThat(solution.minMeetingRooms(new int[][]{{5, 10}, {10, 15}})).isEqualTo(1);
    }

    @Test
    @DisplayName("duplicate identical intervals each need their own room")
    void duplicateIntervals() {
        assertThat(solution.minMeetingRooms(new int[][]{{1, 5}, {1, 5}, {1, 5}})).isEqualTo(3);
    }

    @Test
    @DisplayName("unsorted input with no real overlap still resolves to 1 room")
    void unsortedInputNoOverlap() {
        assertThat(solution.minMeetingRooms(new int[][]{{6, 7}, {2, 4}, {8, 12}})).isEqualTo(1);
    }

    @Test
    @DisplayName("boundary — maximum constraint values (end = 10^6) with full overlap need 2 rooms")
    void maxConstraintBoundaryValues() {
        assertThat(solution.minMeetingRooms(new int[][]{{0, 1000000}, {0, 1000000}})).isEqualTo(2);
    }

    @Test
    @DisplayName("partial overlap pattern needs 2 rooms")
    void partialOverlapNeedsTwoRooms() {
        assertThat(solution.minMeetingRooms(new int[][]{{1, 5}, {2, 3}, {4, 6}})).isEqualTo(2);
    }
}
