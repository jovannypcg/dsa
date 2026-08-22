package mx.jovannypcg.base.p56_generateparentheses;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {

    @Test
    @DisplayName("n = 1 (minimum constraint) → single combination \"()\"")
    void minimumN() {
        Solution solution = new Solution();

        List<String> result = solution.generateParenthesis(1);

        assertThat(result).containsExactlyInAnyOrder("()");
    }

    @Test
    @DisplayName("n = 2 → two combinations")
    void twoPairs() {
        Solution solution = new Solution();

        List<String> result = solution.generateParenthesis(2);

        assertThat(result).containsExactlyInAnyOrder("(())", "()()");
    }

    @Test
    @DisplayName("n = 3 → five combinations matching the problem statement example")
    void threePairs() {
        Solution solution = new Solution();

        List<String> result = solution.generateParenthesis(3);

        assertThat(result).containsExactlyInAnyOrder(
                "((()))", "(()())", "(())()", "()(())", "()()()"
        );
    }

    @Test
    @DisplayName("n = 4 → fourteen unique, well-formed combinations")
    void fourPairs() {
        Solution solution = new Solution();

        List<String> result = solution.generateParenthesis(4);

        assertThat(result).hasSize(14);
        assertThat(result).doesNotHaveDuplicates();
        assertThat(result).allSatisfy(SolutionTest::assertWellFormed);
    }

    @Test
    @DisplayName("n = 5 → every combination has length 2n and balances to zero")
    void fivePairsAreAllWellFormed() {
        Solution solution = new Solution();

        List<String> result = solution.generateParenthesis(5);

        assertThat(result).hasSize(42);
        assertThat(result).doesNotHaveDuplicates();
        assertThat(result).allSatisfy(combination -> {
            assertThat(combination).hasSize(10);
            assertWellFormed(combination);
        });
    }

    @Test
    @DisplayName("boundary: n = 8 (maximum constraint) → 1430 unique, well-formed combinations")
    void maximumN() {
        Solution solution = new Solution();

        List<String> result = solution.generateParenthesis(8);

        assertThat(result).hasSize(1430);
        assertThat(result).doesNotHaveDuplicates();
        assertThat(result).allSatisfy(combination -> {
            assertThat(combination).hasSize(16);
            assertWellFormed(combination);
        });
    }

    private static void assertWellFormed(String combination) {
        int balance = 0;

        for (char c : combination.toCharArray()) {
            balance += c == '(' ? 1 : -1;
            assertThat(balance).isGreaterThanOrEqualTo(0);
        }

        assertThat(balance).isZero();
    }
}
