package mx.jovannypcg.base.p55_subsets2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("example 1: nums=[1,2,2] → six distinct subsets")
    void example1_sixDistinctSubsets() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{1, 2, 2});

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(1),
                List.of(2),
                List.of(1, 2),
                List.of(2, 2),
                List.of(1, 2, 2)
        );
    }

    @Test
    @DisplayName("example 2: nums=[0] → empty set and single-element set")
    void example2_singleElement() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{0});

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(0)
        );
    }

    @Test
    @DisplayName("example 3: nums=[4,4,4] → subsets distinguished only by count")
    void example3_allIdenticalValues() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{4, 4, 4});

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(4),
                List.of(4, 4),
                List.of(4, 4, 4)
        );
    }

    @Test
    @DisplayName("example 4: nums=[1,2,3] with no duplicates → full power set of eight subsets")
    void example4_noDuplicatesInInput() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{1, 2, 3});

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(1),
                List.of(2),
                List.of(3),
                List.of(1, 2),
                List.of(1, 3),
                List.of(2, 3),
                List.of(1, 2, 3)
        );
    }

    @Test
    @DisplayName("example 5: nums=[-1,0,-1] with negatives → duplicates from repeated -1 collapsed")
    void example5_negativeValues() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{-1, 0, -1});

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(-1),
                List.of(0),
                List.of(-1, -1),
                List.of(-1, 0),
                List.of(-1, -1, 0)
        );
    }

    @Test
    @DisplayName("no duplicate subsets appear in the result")
    void resultContainsNoDuplicateSubsets() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{1, 2, 2});

        assertThat(normalize(result)).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("empty subset is always present")
    void emptySubsetAlwaysPresent() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{5, 5, -3});

        assertThat(normalize(result)).contains(List.of());
    }

    @Test
    @DisplayName("full array itself is always present as a subset")
    void fullArrayIsAlwaysPresent() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{2, 1, 2});

        assertThat(normalize(result)).contains(List.of(1, 2, 2));
    }

    @Test
    @DisplayName("boundary: minimum array length (1) with minimum value (-10)")
    void boundaryMinimumLengthMinimumValue() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{-10});

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(-10)
        );
    }

    @Test
    @DisplayName("boundary: minimum array length (1) with maximum value (10)")
    void boundaryMinimumLengthMaximumValue() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{10});

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(10)
        );
    }

    @Test
    @DisplayName("boundary: maximum array length (10), all identical values → eleven subsets by count")
    void boundaryMaximumLengthAllIdentical() {
        int[] nums = new int[]{7, 7, 7, 7, 7, 7, 7, 7, 7, 7};
        List<List<Integer>> result = solution.subsetsWithDup(nums);

        assertThat(normalize(result)).hasSize(11);
        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(7),
                List.of(7, 7),
                List.of(7, 7, 7),
                List.of(7, 7, 7, 7),
                List.of(7, 7, 7, 7, 7),
                List.of(7, 7, 7, 7, 7, 7),
                List.of(7, 7, 7, 7, 7, 7, 7),
                List.of(7, 7, 7, 7, 7, 7, 7, 7),
                List.of(7, 7, 7, 7, 7, 7, 7, 7, 7),
                List.of(7, 7, 7, 7, 7, 7, 7, 7, 7, 7)
        );
    }

    @Test
    @DisplayName("boundary: maximum array length (10) with mixed duplicate groups")
    void boundaryMaximumLengthMixedDuplicates() {
        int[] nums = new int[]{-10, -10, 0, 0, 0, 3, 3, 10, 10, 10};
        List<List<Integer>> result = solution.subsetsWithDup(nums);

        assertThat(normalize(result)).doesNotHaveDuplicates();
        assertThat(normalize(result)).hasSize(3 * 4 * 3 * 4);
    }

    @Test
    @DisplayName("two equal elements produce exactly three subsets, not four")
    void twoEqualElementsCollapseToThreeSubsets() {
        List<List<Integer>> result = solution.subsetsWithDup(new int[]{2, 2});

        assertThat(normalize(result)).containsExactlyInAnyOrder(
                List.of(),
                List.of(2),
                List.of(2, 2)
        );
    }

    /**
     * Subsets are unordered collections; sort each one so assertions don't depend on the
     * specific element order a backtracking implementation happens to produce.
     */
    private static List<List<Integer>> normalize(List<List<Integer>> subsets) {
        return subsets.stream()
                .map(subset -> subset.stream().sorted().toList())
                .toList();
    }
}
