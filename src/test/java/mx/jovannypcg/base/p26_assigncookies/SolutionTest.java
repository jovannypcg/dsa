package mx.jovannypcg.base.p26_assigncookies;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    @Test
    @DisplayName("example 1: more children than cookies, only one satisfied")
    void moreChildrenThanCookies() {
        Solution solution = new Solution();
        int[] g = {1, 2, 3};
        int[] s = {1, 1};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(1);
    }

    @Test
    @DisplayName("example 2: enough cookies to satisfy every child")
    void enoughCookiesForAllChildren() {
        Solution solution = new Solution();
        int[] g = {1, 2};
        int[] s = {1, 2, 3};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(2);
    }

    @Test
    @DisplayName("empty cookie array → no child can be content")
    void emptyCookieArray() {
        Solution solution = new Solution();
        int[] g = {1, 2, 3};
        int[] s = {};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(0);
    }

    @Test
    @DisplayName("largest cookie still too small for the only child")
    void noCookieBigEnough() {
        Solution solution = new Solution();
        int[] g = {10};
        int[] s = {1, 2, 3, 9};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(0);
    }

    @Test
    @DisplayName("all equal greed factors and cookie sizes → all satisfied")
    void allEqualGreedAndSizes() {
        Solution solution = new Solution();
        int[] g = {1, 1, 1};
        int[] s = {1, 1, 1};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(3);
    }

    @Test
    @DisplayName("single child, single cookie, exact match")
    void singleChildSingleCookieExactMatch() {
        Solution solution = new Solution();
        int[] g = {5};
        int[] s = {5};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(1);
    }

    @Test
    @DisplayName("single child, single cookie, cookie too small")
    void singleChildSingleCookieTooSmall() {
        Solution solution = new Solution();
        int[] g = {5};
        int[] s = {4};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(0);
    }

    @Test
    @DisplayName("unsorted input arrays are still handled correctly")
    void unsortedInputArrays() {
        Solution solution = new Solution();
        int[] g = {3, 1, 2};
        int[] s = {3, 1, 1};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(2);
    }

    @Test
    @DisplayName("more cookies than children, all children satisfied")
    void moreCookiesThanChildren() {
        Solution solution = new Solution();
        int[] g = {2, 3};
        int[] s = {1, 2, 3, 4, 5};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(2);
    }

    @Test
    @DisplayName("values at the upper constraint bound (Integer.MAX_VALUE)")
    void maxValueGreedAndSize() {
        Solution solution = new Solution();
        int[] g = {Integer.MAX_VALUE};
        int[] s = {Integer.MAX_VALUE};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(1);
    }

    @Test
    @DisplayName("duplicate greed factors with only one matching cookie")
    void duplicateGreedFactorsOneCookie() {
        Solution solution = new Solution();
        int[] g = {2, 2, 2};
        int[] s = {2};

        assertThat(solution.findContentChildren(g, s)).isEqualTo(1);
    }
}
