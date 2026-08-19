package mx.jovannypcg.base.p51_subsets;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("three unique elements → all eight subsets, no duplicates")
    void threeElements_allSubsets() {
        List<List<Integer>> result = solution.subsets(new int[]{1, 2, 3});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(),
                List.of(1),
                List.of(2),
                List.of(1, 2),
                List.of(3),
                List.of(1, 3),
                List.of(2, 3),
                List.of(1, 2, 3)
        );
    }

    @Test
    @DisplayName("single element → empty set and the element itself")
    void singleElement_twoSubsets() {
        List<List<Integer>> result = solution.subsets(new int[]{0});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(),
                List.of(0)
        );
    }

    @Test
    @DisplayName("two elements → four subsets")
    void twoElements_fourSubsets() {
        List<List<Integer>> result = solution.subsets(new int[]{1, 2});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(),
                List.of(1),
                List.of(2),
                List.of(1, 2)
        );
    }

    @Test
    @DisplayName("negative and positive values are handled the same as any other unique values")
    void negativeAndPositiveValues() {
        List<List<Integer>> result = solution.subsets(new int[]{-2, 4});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(),
                List.of(-2),
                List.of(4),
                List.of(-2, 4)
        );
    }

    @Test
    @DisplayName("single negative element at the lower bound of the value range")
    void singleNegativeElementAtLowerBound() {
        List<List<Integer>> result = solution.subsets(new int[]{-10});

        assertThat(result).containsExactlyInAnyOrder(
                List.of(),
                List.of(-10)
        );
    }

    @Test
    @DisplayName("no subset is repeated in the result")
    void noDuplicateSubsets() {
        List<List<Integer>> result = solution.subsets(new int[]{5, -5, 10});

        assertThat(result).doesNotHaveDuplicates();
        assertThat(result).hasSize(8);
    }

    @Test
    @DisplayName("result always includes the empty subset and the full set")
    void includesEmptyAndFullSet() {
        List<List<Integer>> result = solution.subsets(new int[]{7, -3, 9});

        assertThat(result).contains(List.of());
        assertThat(result).contains(List.of(7, -3, 9));
    }

    @Test
    @DisplayName("boundary: ten elements at the edge of the length constraint → 1024 subsets")
    void tenElements_maxLength() {
        int[] nums = {-10, -8, -6, -4, -2, 0, 2, 4, 6, 10};

        List<List<Integer>> result = solution.subsets(nums);

        assertThat(result).hasSize(1024);
        assertThat(result).doesNotHaveDuplicates();
        assertThat(result).contains(List.of());
        assertThat(result).contains(Arrays.stream(nums).boxed().toList());
    }
}
