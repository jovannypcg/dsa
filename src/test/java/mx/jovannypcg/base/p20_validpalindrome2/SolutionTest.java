package mx.jovannypcg.base.p20_validpalindrome2;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    @DisplayName("single character → trivially a palindrome, no deletion needed")
    void singleCharacter() {
        assertThat(solution.validPalindrome("a")).isTrue();
    }

    @Test
    @DisplayName("two identical characters → already a palindrome")
    void twoIdenticalCharacters() {
        assertThat(solution.validPalindrome("aa")).isTrue();
    }

    @Test
    @DisplayName("two different characters → deleting either one leaves a valid palindrome")
    void twoDifferentCharacters() {
        assertThat(solution.validPalindrome("ab")).isTrue();
    }

    @Test
    @DisplayName("already a palindrome → no deletion needed")
    void alreadyPalindrome() {
        assertThat(solution.validPalindrome("aba")).isTrue();
    }

    @Test
    @DisplayName("one deletion from the right side makes it a palindrome")
    void oneDeletionFromRight() {
        assertThat(solution.validPalindrome("abca")).isTrue();
    }

    @Test
    @DisplayName("one deletion from the left side makes it a palindrome")
    void oneDeletionFromLeft() {
        assertThat(solution.validPalindrome("deeee")).isTrue();
    }

    @Test
    @DisplayName("no single deletion can make it a palindrome → false")
    void noSingleDeletionWorks() {
        assertThat(solution.validPalindrome("abc")).isFalse();
    }

    @Test
    @DisplayName("mismatch requires deleting more than one character → false")
    void requiresMoreThanOneDeletion() {
        assertThat(solution.validPalindrome("abxyzba")).isFalse();
    }

    @Test
    @DisplayName("all identical characters → already a palindrome")
    void allIdenticalCharacters() {
        assertThat(solution.validPalindrome("aaaaaa")).isTrue();
    }

    @Test
    @DisplayName("committing to the first matching skip must not block a later mismatch from trying the other skip")
    void mustNotCommitToFirstSkipWithoutVerifyingItFully() {
        // Deleting index 4 ('a') gives "baab", which is a palindrome.
        // A solution that only peeks one pair ahead after a skip — instead of fully
        // verifying that branch — commits to skipping left (s[1]=='a' matches s[4]=='a'),
        // then fails two steps later at s[2]='a' vs s[3]='b' and never tries skipping
        // right instead, incorrectly returning false.
        assertThat(solution.validPalindrome("baaba")).isTrue();
    }

    @Test
    @DisplayName("large input at upper constraint boundary (10^5 characters) is handled efficiently")
    void largeInputAtUpperBoundary() {
        int n = 100_000;
        StringBuilder sb = new StringBuilder(n);

        for (int i = 0; i < n / 2; i++) {
            sb.append('a');
        }
        sb.append('b');
        for (int i = 0; i < n / 2; i++) {
            sb.append('a');
        }

        assertThat(solution.validPalindrome(sb.toString())).isTrue();
    }

    @ParameterizedTest(name = "[{index}] s=\"{0}\" -> {1}")
    @CsvSource({
        // Happy path
        "aba,    true",
        "abca,   true",
        "abc,    false",
        // Single character (lower boundary of constraints)
        "a,      true",
        // Two characters
        "aa,     true",
        "ab,     true",
        // Palindrome with even length
        "abba,   true",
        // Deletion needed in the middle of a longer string
        "eeccccbebaeeabebccceea, false",
        // Not a palindrome even after one deletion
        "abcdef, false",
        // Deleting the only mismatched character at the edge
        "baa,    true",
        "aab,    true",
    })
    void validPalindrome(String input, boolean expected) {
        assertThat(solution.validPalindrome(input)).isEqualTo(expected);
    }
}
