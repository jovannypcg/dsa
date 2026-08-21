package mx.jovannypcg.base.p54_permutations;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    @DisplayName("single element → one permutation")
    void singleElement() {
        Solution solution = new Solution();

        List<List<Integer>> result = solution.permute(new int[]{1});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(1)
        );
    }

    @Test
    @DisplayName("two elements → two permutations")
    void twoElements() {
        Solution solution = new Solution();

        List<List<Integer>> result = solution.permute(new int[]{0, 1});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(0, 1),
                List.of(1, 0)
        );
    }

    @Test
    @DisplayName("three elements → six permutations")
    void threeElements() {
        Solution solution = new Solution();

        List<List<Integer>> result = solution.permute(new int[]{1, 2, 3});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(1, 2, 3),
                List.of(1, 3, 2),
                List.of(2, 1, 3),
                List.of(2, 3, 1),
                List.of(3, 1, 2),
                List.of(3, 2, 1)
        );
    }

    @Test
    @DisplayName("negative numbers are permuted correctly")
    void negativeNumbers() {
        Solution solution = new Solution();

        List<List<Integer>> result = solution.permute(new int[]{-1, -2});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(-1, -2),
                List.of(-2, -1)
        );
    }

    @Test
    @DisplayName("mix of positive and negative values")
    void mixedSignValues() {
        Solution solution = new Solution();

        List<List<Integer>> result = solution.permute(new int[]{-10, 0, 10});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(-10, 0, 10),
                List.of(-10, 10, 0),
                List.of(0, -10, 10),
                List.of(0, 10, -10),
                List.of(10, -10, 0),
                List.of(10, 0, -10)
        );
    }

    @Test
    @DisplayName("boundary: six elements → 720 permutations, all unique")
    void maxLengthProducesFactorialCountOfUniquePermutations() {
        Solution solution = new Solution();

        List<List<Integer>> result = solution.permute(new int[]{1, 2, 3, 4, 5, 6});

        assertThat(result).hasSize(720);
        assertThat(result).doesNotHaveDuplicates();
        assertThat(result).allSatisfy(permutation ->
                assertThat(permutation).containsExactlyInAnyOrder(1, 2, 3, 4, 5, 6)
        );
    }
}
