package mx.jovannypcg.base.p06_twosum2;

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
            Arguments.of(new int[]{2, 7, 11, 15}, 9,  new int[]{1, 2}),
            Arguments.of(new int[]{2, 3, 4},       6,  new int[]{1, 3}),
            // negatives
            Arguments.of(new int[]{-1, 0},         -1, new int[]{1, 2}),
            // target at the end of the array
            Arguments.of(new int[]{1, 2, 3, 4, 5}, 9,  new int[]{4, 5}),
            // two-element array, only possible pair
            Arguments.of(new int[]{-1000, 1000},   0,  new int[]{1, 2}),
            // duplicate values
            Arguments.of(new int[]{1, 1, 3, 5},    2,  new int[]{1, 2}),
            // large array boundaries
            Arguments.of(new int[]{-1000, -500, 0, 500, 1000}, 0, new int[]{1, 5})
        );
    }

    @ParameterizedTest
    @MethodSource("cases")
    void twoSum(int[] numbers, int target, int[] expected) {
        assertThat(solution.twoSum(numbers, target)).isEqualTo(expected);
    }
}
