package mx.jovannypcg.base.p05_validpalindrome;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @ParameterizedTest(name = "\"{0}\" -> {1}")
    @CsvSource({
        // Happy path
        "'A man, a plan, a canal: Panama', true",
        "'race a car',                     false",
        // Single space — becomes empty string
        "' ',                              true",
        // Single alphanumeric character
        "'a',                              true",
        // Single non-alphanumeric character
        "',',                              true",
        // Already clean palindrome
        "'racecar',                        true",
        // Already clean non-palindrome
        "'hello',                          false",
        // Mixed case palindrome
        "'Madam',                          true",
        // Numbers
        "'12321',                          true",
        "'12345',                          false",
        // Alphanumeric mix
        "'0P',                             false",
        // Only punctuation — empty after cleanup
        "'...',                            true",
        // Palindrome with punctuation in the middle
        "'A.,',                            true",
    })
    void isPalindrome(String input, boolean expected) {
        assertThat(solution.isPalindrome(input)).isEqualTo(expected);
    }
}
