package mx.jovannypcg.base.p60_kthsmallestelementinastream;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    @Test
    @DisplayName("example 1: k=3, initial nums, sequential adds")
    void example1() {
        Solution kthSmallest = new Solution(3, new int[]{4, 5, 8, 7});

        assertThat(kthSmallest.add(6)).isEqualTo(6);
        assertThat(kthSmallest.add(3)).isEqualTo(5);
        assertThat(kthSmallest.add(1)).isEqualTo(4);
        assertThat(kthSmallest.add(2)).isEqualTo(3);
        assertThat(kthSmallest.add(9)).isEqualTo(3);
    }

    @Test
    @DisplayName("example 2: k=4, initial nums with duplicates, sequential adds")
    void example2() {
        Solution kthSmallest = new Solution(4, new int[]{-7, -7, -7, -7, -8, -3});

        assertThat(kthSmallest.add(-2)).isEqualTo(-7);
        assertThat(kthSmallest.add(-10)).isEqualTo(-7);
        assertThat(kthSmallest.add(-9)).isEqualTo(-7);
        assertThat(kthSmallest.add(-9)).isEqualTo(-8);
    }

    @Test
    @DisplayName("k=1 with empty initial nums returns running minimum")
    void k1WithEmptyInitialArray() {
        Solution kthSmallest = new Solution(1, new int[]{});

        assertThat(kthSmallest.add(-3)).isEqualTo(-3);
        assertThat(kthSmallest.add(5)).isEqualTo(-3);
        assertThat(kthSmallest.add(-10)).isEqualTo(-10);
    }

    @Test
    @DisplayName("single element nums with k equal to nums.length + 1")
    void kEqualsNumsLengthPlusOne() {
        Solution kthSmallest = new Solution(2, new int[]{6});

        assertThat(kthSmallest.add(4)).isEqualTo(6);
        assertThat(kthSmallest.add(10)).isEqualTo(6);
    }

    @Test
    @DisplayName("all positive scores stay ordered correctly")
    void allPositiveScores() {
        Solution kthSmallest = new Solution(2, new int[]{10, 5, 8});

        assertThat(kthSmallest.add(1)).isEqualTo(5);
        assertThat(kthSmallest.add(20)).isEqualTo(5);
        assertThat(kthSmallest.add(2)).isEqualTo(2);
    }

    @Test
    @DisplayName("duplicate values across initial nums and adds")
    void duplicateValues() {
        Solution kthSmallest = new Solution(2, new int[]{5, 5, 5});

        assertThat(kthSmallest.add(5)).isEqualTo(5);
        assertThat(kthSmallest.add(5)).isEqualTo(5);
    }

    @Test
    @DisplayName("boundary values at -10^4 and 10^4")
    void boundaryValues() {
        Solution kthSmallest = new Solution(2, new int[]{10000, -10000, 0});

        assertThat(kthSmallest.add(-10000)).isEqualTo(-10000);
        assertThat(kthSmallest.add(10000)).isEqualTo(-10000);
    }
}
