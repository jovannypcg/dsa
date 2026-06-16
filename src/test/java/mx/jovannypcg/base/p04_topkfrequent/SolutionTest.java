package mx.jovannypcg.base.p04_topkfrequent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    void topKFrequent_typicalInput_returnsTopTwo() {
        int[] result = solution.topKFrequent(new int[]{1, 2, 2, 3, 3, 3}, 2);
        assertThat(result).containsExactlyInAnyOrder(2, 3);
    }

    @Test
    void topKFrequent_allSameElement_returnsIt() {
        int[] result = solution.topKFrequent(new int[]{7, 7}, 1);
        assertThat(result).containsExactlyInAnyOrder(7);
    }

    @Test
    void topKFrequent_kEqualsDistinctCount_returnsAll() {
        int[] result = solution.topKFrequent(new int[]{1, 2, 3}, 3);
        assertThat(result).containsExactlyInAnyOrder(1, 2, 3);
    }

    @Test
    void topKFrequent_singleElement_returnsIt() {
        int[] result = solution.topKFrequent(new int[]{42}, 1);
        assertThat(result).containsExactlyInAnyOrder(42);
    }

    @Test
    void topKFrequent_negativeNumbers_handledCorrectly() {
        int[] result = solution.topKFrequent(new int[]{-1, -1, -2, -2, -2, -3}, 2);
        assertThat(result).containsExactlyInAnyOrder(-1, -2);
    }

    @Test
    void topKFrequent_mixOfPositiveAndNegative_returnsTopK() {
        int[] result = solution.topKFrequent(new int[]{1, 1, -1, -1, -1, 2}, 1);
        assertThat(result).containsExactlyInAnyOrder(-1);
    }

    @Test
    void topKFrequent_largeFrequencyGap_returnsCorrectK() {
        // 5 appears 5 times, 3 appears 3 times, 1 appears once
        int[] result = solution.topKFrequent(new int[]{5, 5, 5, 5, 5, 3, 3, 3, 1}, 2);
        assertThat(result).containsExactlyInAnyOrder(5, 3);
    }

    @Test
    void topKFrequent_kEqualsOne_returnsMostFrequent() {
        int[] result = solution.topKFrequent(new int[]{1, 2, 2, 3, 3, 3, 4, 4, 4, 4}, 1);
        assertThat(result).containsExactlyInAnyOrder(4);
    }

    @Test
    void topKFrequent_boundaryValues_handledCorrectly() {
        // -1000 and 1000 are at the constraint boundaries
        int[] result = solution.topKFrequent(new int[]{-1000, -1000, 1000}, 1);
        assertThat(result).containsExactlyInAnyOrder(-1000);
    }
}
