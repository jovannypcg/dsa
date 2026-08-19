package mx.jovannypcg.base.p53_combinationsum2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("example 1: candidates=[10,1,2,7,6,1,5], target=8 → four combinations")
    void example1_fourCombinations() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{10, 1, 2, 7, 6, 1, 5}, 8);

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(1, 1, 6),
                List.of(1, 2, 5),
                List.of(1, 7),
                List.of(2, 6)
        );
    }

    @Test
    @DisplayName("example 2: candidates=[2,5,2,1,2], target=5 → two combinations")
    void example2_twoCombinations() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{2, 5, 2, 1, 2}, 5);

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(1, 2, 2),
                List.of(5)
        );
    }

    @Test
    @DisplayName("example 3: candidates=[1,1], target=2 → both 1s used once")
    void example3_bothOnesUsed() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{1, 1}, 2);

        assertThat(normalize(result)).containsExactly(List.of(1, 1));
    }

    @Test
    @DisplayName("example 4: single candidate smaller than target → no combinations")
    void example4_noCombinations() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{5}, 3);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("example 5: candidates=[3,1,3,5,1,1], target=8 → three combinations")
    void example5_threeCombinations() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{3, 1, 3, 5, 1, 1}, 8);

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(1, 1, 1, 5),
                List.of(1, 1, 3, 3),
                List.of(3, 5)
        );
    }

    @Test
    @DisplayName("single candidate equal to target → one combination using it once")
    void singleCandidateEqualsTarget() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{5}, 5);

        assertThat(normalize(result)).containsExactly(List.of(5));
    }

    @Test
    @DisplayName("candidate can't be reused even if it alone would reach a larger target")
    void candidateNotReusedWithinCombination() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{4}, 8);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("duplicate combinations from repeated values are collapsed to one")
    void duplicateCombinationsCollapsed() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{1, 1, 2}, 3);

        assertThat(result).doesNotHaveDuplicates();
        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(1, 2)
        );
    }

    @Test
    @DisplayName("no combination of candidates sums to target → empty result")
    void noCombinationSumsToTarget() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{10, 20}, 15);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("boundary: minimum candidate length (1) with candidate equal to target")
    void boundaryMinimumLengthCandidateEqualsTarget() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{1}, 1);

        assertThat(normalize(result)).containsExactly(List.of(1));
    }

    @Test
    @DisplayName("boundary: minimum candidate value (1) repeated up to minimum target (1)")
    void boundaryMinimumCandidateValueAndTarget() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{1, 1, 1}, 1);

        assertThat(normalize(result)).containsExactly(List.of(1));
    }

    @Test
    @DisplayName("boundary: maximum candidate value (50) exceeds minimum target (1) → no combinations")
    void boundaryMaximumCandidateValueExceedsTarget() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{50}, 1);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("boundary: candidates summing exactly to maximum target (30)")
    void boundaryMaximumTarget() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{10, 10, 10}, 30);

        assertThat(normalize(result)).containsExactly(List.of(10, 10, 10));
    }

    @Test
    @DisplayName("all identical candidates with target reached by a subset of them")
    void allIdenticalCandidatesPartialSubset() {
        List<List<Integer>> result = solution.combinationSum2(new int[]{2, 2, 2, 2}, 6);

        assertThat(normalize(result)).containsExactly(List.of(2, 2, 2));
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
