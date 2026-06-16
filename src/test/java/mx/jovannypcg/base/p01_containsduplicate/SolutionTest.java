package mx.jovannypcg.base.p01_containsduplicate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                // happy path
                Arguments.of(new int[]{1, 2, 3, 3}, true),
                Arguments.of(new int[]{1, 2, 3, 4}, false),

                // single element — can't duplicate
                Arguments.of(new int[]{7}, false),

                // all same
                Arguments.of(new int[]{5, 5, 5}, true),

                // empty array
                Arguments.of(new int[]{}, false),

                // duplicate at the start
                Arguments.of(new int[]{1, 1, 2, 3}, true),

                // negative values with duplicate
                Arguments.of(new int[]{-1, -2, -1}, true),

                // negative values without duplicate
                Arguments.of(new int[]{-3, -2, -1}, false),

                // large boundary values, no duplicate
                Arguments.of(new int[]{-1_000_000_000, 1_000_000_000}, false),

                // large boundary values, duplicate
                Arguments.of(new int[]{1_000_000_000, 1_000_000_000}, true)
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void containsDuplicate(int[] nums, boolean expected) {
        assertThat(solution.containsDuplicate(nums)).isEqualTo(expected);
    }
}
