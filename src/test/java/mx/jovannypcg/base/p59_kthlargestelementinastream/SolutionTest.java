package mx.jovannypcg.base.p59_kthlargestelementinastream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    @Test
    @DisplayName("example 1: k=3, initial nums, sequential adds")
    void example1() {
        Solution kthLargest = new Solution(3, new int[]{4, 5, 8, 2});

        assertThat(kthLargest.add(3)).isEqualTo(4);
        assertThat(kthLargest.add(5)).isEqualTo(5);
        assertThat(kthLargest.add(10)).isEqualTo(5);
        assertThat(kthLargest.add(9)).isEqualTo(8);
        assertThat(kthLargest.add(4)).isEqualTo(8);
    }

    @Test
    @DisplayName("example 2: k=4, initial nums with duplicates, sequential adds")
    void example2() {
        Solution kthLargest = new Solution(4, new int[]{7, 7, 7, 7, 8, 3});

        assertThat(kthLargest.add(2)).isEqualTo(7);
        assertThat(kthLargest.add(10)).isEqualTo(7);
        assertThat(kthLargest.add(9)).isEqualTo(7);
        assertThat(kthLargest.add(9)).isEqualTo(8);
    }

    @Test
    @DisplayName("k=1 with empty initial nums returns running maximum")
    void k1WithEmptyInitialArray() {
        Solution kthLargest = new Solution(1, new int[]{});

        assertThat(kthLargest.add(-3)).isEqualTo(-3);
        assertThat(kthLargest.add(5)).isEqualTo(5);
        assertThat(kthLargest.add(2)).isEqualTo(5);
    }

    @Test
    @DisplayName("single element nums with k equal to nums.length + 1")
    void kEqualsNumsLengthPlusOne() {
        Solution kthLargest = new Solution(2, new int[]{6});

        assertThat(kthLargest.add(4)).isEqualTo(4);
        assertThat(kthLargest.add(10)).isEqualTo(6);
    }

    @Test
    @DisplayName("all negative scores stay ordered correctly")
    void allNegativeScores() {
        Solution kthLargest = new Solution(2, new int[]{-10, -5, -8});

        assertThat(kthLargest.add(-1)).isEqualTo(-5);
        assertThat(kthLargest.add(-20)).isEqualTo(-5);
        assertThat(kthLargest.add(-2)).isEqualTo(-2);
    }

    @Test
    @DisplayName("duplicate values across initial nums and adds")
    void duplicateValues() {
        Solution kthLargest = new Solution(2, new int[]{5, 5, 5});

        assertThat(kthLargest.add(5)).isEqualTo(5);
        assertThat(kthLargest.add(5)).isEqualTo(5);
    }

    @Test
    @DisplayName("boundary values at -10^4 and 10^4")
    void boundaryValues() {
        Solution kthLargest = new Solution(2, new int[]{-10000, 10000, 0});

        assertThat(kthLargest.add(10000)).isEqualTo(10000);
        assertThat(kthLargest.add(-10000)).isEqualTo(10000);
    }
}
