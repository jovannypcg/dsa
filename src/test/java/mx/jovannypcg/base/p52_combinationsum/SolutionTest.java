package mx.jovannypcg.base.p52_combinationsum;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("example 1: candidates=[2,3,6,7], target=7 → [[2,2,3],[7]]")
    void example1_twoCombinations() {
        List<List<Integer>> result = solution.combinationSum(new int[]{2, 3, 6, 7}, 7);

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(2, 2, 3),
                List.of(7)
        );
    }

    @Test
    @DisplayName("example 2: candidates=[2,3,5], target=8 → three combinations")
    void example2_threeCombinations() {
        List<List<Integer>> result = solution.combinationSum(new int[]{2, 3, 5}, 8);

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(2, 2, 2, 2),
                List.of(2, 3, 3),
                List.of(3, 5)
        );
    }

    @Test
    @DisplayName("example 3: single candidate larger than target → no combinations")
    void example3_noCombinations() {
        List<List<Integer>> result = solution.combinationSum(new int[]{2}, 1);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("single candidate equal to target → one combination using it once")
    void singleCandidateEqualsTarget() {
        List<List<Integer>> result = solution.combinationSum(new int[]{5}, 5);

        assertThat(normalize(result)).containsExactly(List.of(5));
    }

    @Test
    @DisplayName("single candidate reached only through repetition")
    void singleCandidateRepeatedToReachTarget() {
        List<List<Integer>> result = solution.combinationSum(new int[]{3}, 9);

        assertThat(normalize(result)).containsExactly(List.of(3, 3, 3));
    }

    @Test
    @DisplayName("no candidate combination sums to target → empty result")
    void noCombinationSumsToTarget() {
        List<List<Integer>> result = solution.combinationSum(new int[]{10, 20}, 15);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("boundary: minimum target (1) with minimum candidate value (2) → empty result")
    void boundaryMinimumTargetAndCandidate() {
        List<List<Integer>> result = solution.combinationSum(new int[]{2}, 1);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("boundary: maximum candidate value (40) equal to maximum target (40)")
    void boundaryMaximumCandidateAndTarget() {
        List<List<Integer>> result = solution.combinationSum(new int[]{40}, 40);

        assertThat(normalize(result)).containsExactly(List.of(40));
    }

    @Test
    @DisplayName("multiple candidates with several valid combinations and no duplicates")
    void multipleCandidates_noDuplicateCombinations() {
        List<List<Integer>> result = solution.combinationSum(new int[]{2, 4, 6, 8}, 8);

        assertThat(result).doesNotHaveDuplicates();
        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(2, 2, 2, 2),
                List.of(2, 2, 4),
                List.of(2, 6),
                List.of(4, 4),
                List.of(8)
        );
    }

    /**
     * Combinations are multisets; sort each one so assertions don't depend on the
     * specific element order a backtracking implementation happens to produce.
     */
    private static List<List<Integer>> normalize(List<List<Integer>> combinations) {
        return combinations.stream()
                .map(combo -> combo.stream().sorted().toList())
                .toList();
    }
}
