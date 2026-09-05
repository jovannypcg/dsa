package mx.jovannypcg.base.p71_fibonaccinumber;

/**
 * The Fibonacci numbers, commonly denoted {@code F(n)}, form a sequence such that each
 * number is the sum of the two preceding ones, starting from 0 and 1. That is,
 *
 * <pre>
 * F(0) = 0, F(1) = 1
 * F(n) = F(n - 1) + F(n - 2), for n &gt; 1
 * </pre>
 *
 * <p>Given {@code n}, calculate {@code F(n)}.
 *
 * @see <a href="https://leetcode.com/problems/fibonacci-number/">Fibonacci Number - LeetCode</a>
 */
public class Solution {

    public int fib(int n) {
        if (n < 0) throw new IllegalArgumentException(
            "n must be a positive number"
        );

        int[] memo = new int[n + 1];
        return fib(n, memo);
    }

    private int fib(int n, int[] memo) {
        if (n < 2) return n;
        if (memo[n] != 0) return memo[n];

        int _fib = fib(n - 1, memo) + fib(n - 2, memo);

        memo[n] = _fib;

        return _fib;
    }
}
