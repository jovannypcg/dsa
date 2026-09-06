package mx.jovannypcg.base.p74_houserobber;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    @DisplayName("example 1: [1,2,3,1] → 4")
    void exampleOne() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{1, 2, 3, 1})).isEqualTo(4);
    }

    @Test
    @DisplayName("example 2: [2,7,9,3,1] → 12")
    void exampleTwo() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{2, 7, 9, 3, 1})).isEqualTo(12);
    }

    @Test
    @DisplayName("single house → that house's value")
    void singleHouse() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{5})).isEqualTo(5);
    }

    @Test
    @DisplayName("single house with zero money → 0")
    void singleHouseZero() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{0})).isEqualTo(0);
    }

    @Test
    @DisplayName("two houses → max of the two")
    void twoHouses() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{5, 5})).isEqualTo(5);
    }

    @Test
    @DisplayName("two houses with different values → the larger one")
    void twoHousesDifferent() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{2, 9})).isEqualTo(9);
    }

    @Test
    @DisplayName("all zeros → 0")
    void allZeros() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{0, 0, 0, 0})).isEqualTo(0);
    }

    @Test
    @DisplayName("alternating pattern favors skipping every other house")
    void alternatingPattern() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{2, 1, 1, 2})).isEqualTo(4);
    }

    @Test
    @DisplayName("increasing values still respects the adjacency constraint")
    void increasingValues() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{1, 2, 3, 4, 5, 6})).isEqualTo(12);
    }

    @Test
    @DisplayName("all houses share the max constraint value 400")
    void maxValueRepeated() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{400, 400, 400, 400, 400, 400, 400, 400, 400, 400}))
                .isEqualTo(2000);
    }

    @Test
    @DisplayName("mixed values requiring non-trivial choice")
    void mixedValues() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{4, 1, 2, 7, 5, 3, 1})).isEqualTo(14);
    }

    @Test
    @DisplayName("large input at max constraint length (100 houses) of max value")
    void largeInputAtMaxLength() {
        Solution solution = new Solution();
        int[] nums = new int[100];

        for (int i = 0; i < 100; i++) {
            nums[i] = 400;
        }

        assertThat(solution.rob(nums)).isEqualTo(20000);
    }

    @Test
    @DisplayName("three houses, middle one is the largest")
    void threeHousesMiddleLargest() {
        Solution solution = new Solution();

        assertThat(solution.rob(new int[]{1, 100, 1})).isEqualTo(100);
    }
}
