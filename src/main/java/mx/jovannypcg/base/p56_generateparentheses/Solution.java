package mx.jovannypcg.base.p56_generateparentheses;

import java.util.ArrayList;
import java.util.List;

/**
 * Given {@code n} pairs of parentheses, generate all combinations of well-formed
 * (balanced) parentheses.
 *
 * <p>A combination is well-formed if every opening parenthesis has a matching closing
 * parenthesis and, at every prefix of the string, the number of opening parentheses seen so
 * far is greater than or equal to the number of closing parentheses seen so far.
 *
 * @see <a href="https://leetcode.com/problems/generate-parentheses/">Generate Parentheses - LeetCode</a>
 */
public class Solution {

    public List<String> generateParenthesis(int n) {
        List<String> combinations = new ArrayList<>();
        StringBuilder combination = new StringBuilder();

        backtracking(n, combinations, combination, 0, 0);

        return combinations;
    }

    void backtracking(
        int n,
        List<String> combinations,
        StringBuilder combination,
        int openingCount,
        int closingCount
    ) {
        if (openingCount == n && closingCount == openingCount) {
            combinations.add(combination.toString());
            return;
        }

        if (openingCount < n) {
            combination.append('(');
            backtracking(n, combinations, combination, openingCount + 1, closingCount);

            combination.deleteCharAt(combination.length() - 1);
        }

        if (closingCount < openingCount) {
            combination.append(')');

            backtracking(n, combinations, combination, openingCount, closingCount + 1);
            combination.deleteCharAt(combination.length() - 1);
        }

    }
}
