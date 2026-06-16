package mx.jovannypcg.base.p07_3sum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    void twoNegativeOnePositive() {
        var result = solution.threeSum(new int[]{-1, 0, 1, 2, -1, -4});

        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        List.of(-1, -1, 2),
                        List.of(-1, 0, 1)
                );
    }

    @Test
    void noValidTriplet() {
        var result = solution.threeSum(new int[]{0, 1, 1});

        assertThat(result).isEmpty();
    }

    @Test
    void allZeros() {
        var result = solution.threeSum(new int[]{0, 0, 0});

        assertThat(result)
                .hasSize(1)
                .containsExactly(List.of(0, 0, 0));
    }

    @Test
    void duplicateElements() {
        var result = solution.threeSum(new int[]{-2, 0, 0, 2, 2});

        assertThat(result)
                .hasSize(1)
                .containsExactly(List.of(-2, 0, 2));
    }

    @Test
    void allNegatives() {
        var result = solution.threeSum(new int[]{-3, -2, -1});

        assertThat(result).isEmpty();
    }

    @Test
    void allPositives() {
        var result = solution.threeSum(new int[]{1, 2, 3});

        assertThat(result).isEmpty();
    }

    @Test
    void minimumLength() {
        var result = solution.threeSum(new int[]{-1, 0, 1});

        assertThat(result)
                .hasSize(1)
                .containsExactly(List.of(-1, 0, 1));
    }

    @Test
    void minimumLengthNoSolution() {
        var result = solution.threeSum(new int[]{1, 2, 3});

        assertThat(result).isEmpty();
    }

    @Test
    void largeValues() {
        var result = solution.threeSum(new int[]{-100000, 0, 100000});

        assertThat(result)
                .hasSize(1)
                .containsExactly(List.of(-100000, 0, 100000));
    }

    @Test
    void multipleTriplets() {
        var result = solution.threeSum(new int[]{-4, -1, -1, 0, 1, 2});

        assertThat(result)
                .hasSize(2)
                .containsExactlyInAnyOrder(
                        List.of(-1, -1, 2),
                        List.of(-1, 0, 1)
                );
    }

    @Test
    void manyDuplicatesSingleTriplet() {
        var result = solution.threeSum(new int[]{0, 0, 0, 0, 0});

        assertThat(result)
                .hasSize(1)
                .containsExactly(List.of(0, 0, 0));
    }
}
