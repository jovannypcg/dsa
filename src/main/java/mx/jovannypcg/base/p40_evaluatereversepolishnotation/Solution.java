package mx.jovannypcg.base.p40_evaluatereversepolishnotation;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Set;

/**
 * You are given an array of strings {@code tokens} that represents an arithmetic expression
 * in Reverse Polish Notation. Evaluate the expression and return an integer that represents
 * its value.
 *
 * <p>The valid operators are {@code +}, {@code -}, {@code *}, and {@code /}. Each operand may
 * be an integer or another expression. Division between two integers always truncates toward
 * zero. The input represents a valid arithmetic expression in reverse polish notation, and the
 * answer and all intermediate calculations can be represented in a 32-bit integer.
 *
 * @see <a href="https://leetcode.com/problems/evaluate-reverse-polish-notation/">Evaluate Reverse Polish Notation - LeetCode</a>
 */
public class Solution {
    private final Set<String> OPERATORS = Set.of("+", "-", "/", "*");

    public int evalRPN(String[] tokens) {
        Deque<Integer> operands = new ArrayDeque<>();

        for (String token : tokens) {
            if (!isOperator(token)) {
                operands.push(Integer.valueOf(token));
                continue;
            }

            int second = operands.pop();
            int first = operands.pop();
            int result = eval(token, first, second);

            operands.push(result);
        }

        return operands.pop();
    }

    int eval(String operator, int a, int b) {
        switch (operator) {
            case "/":
                return a / b;
            case "*":
                return a * b;
            case "-":
                return a - b;
            default:
                return a + b;
        }
    }

    boolean isOperator(String token) {
        return OPERATORS.contains(token);
    }
}
