package mx.jovannypcg.base.p62_laststoneweight;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    @Test
    @DisplayName("example 1: mixed stones reduce to a single stone of weight 1")
    void example1() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{2, 7, 4, 1, 8, 1})).isEqualTo(1);
    }

    @Test
    @DisplayName("single stone returns its own weight without smashing")
    void singleStone() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{1})).isEqualTo(1);
    }

    @Test
    @DisplayName("two equal stones destroy each other and return 0")
    void twoEqualStonesDestroyEachOther() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{1, 1})).isEqualTo(0);
    }

    @Test
    @DisplayName("two different stones leave the difference as the last weight")
    void twoDifferentStonesLeaveDifference() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{3, 7})).isEqualTo(4);
    }

    @Test
    @DisplayName("three stones reduce down to a single remaining stone")
    void threeStonesReduceToSingleStone() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{3, 7, 2})).isEqualTo(2);
    }

    @Test
    @DisplayName("all stones with equal weight fully cancel out to 0")
    void allStonesEqualCancelOut() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{5, 5, 5, 5})).isEqualTo(0);
    }

    @Test
    @DisplayName("boundary: minimum weight value 1 for every stone")
    void minimumWeightValues() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{1, 1, 1})).isEqualTo(1);
    }

    @Test
    @DisplayName("boundary: maximum weight value 1000 on two stones cancels out")
    void maximumWeightValues() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{1000, 1000})).isEqualTo(0);
    }

    @Test
    @DisplayName("boundary: maximum array length of 30 stones all weighing 1")
    void maximumArrayLength() {
        Solution solution = new Solution();
        int[] stones = new int[30];

        for (int i = 0; i < stones.length; i++) {
            stones[i] = 1;
        }

        assertThat(solution.lastStoneWeight(stones)).isEqualTo(0);
    }

    @Test
    @DisplayName("descending order input still resolves correctly")
    void descendingOrderInput() {
        Solution solution = new Solution();

        assertThat(solution.lastStoneWeight(new int[]{9, 4, 3, 2})).isEqualTo(0);
    }
}
