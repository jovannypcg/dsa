package mx.jovannypcg.base.p27_firstpalindrome;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    @Test
    @DisplayName("palindrome in the middle of the array is returned")
    void palindromeInMiddle() {
        Solution solution = new Solution();
        String[] words = {"abc", "car", "ada", "racecar", "cool"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("ada");
    }

    @Test
    @DisplayName("only the last word is palindromic")
    void onlyLastWordIsPalindrome() {
        Solution solution = new Solution();
        String[] words = {"notapalindrome", "racecar"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("racecar");
    }

    @Test
    @DisplayName("no palindromic words returns empty string")
    void noPalindromeReturnsEmptyString() {
        Solution solution = new Solution();
        String[] words = {"def", "ghi"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("");
    }

    @Test
    @DisplayName("multiple palindromes returns the first one, not the longest or last")
    void multiplePalindromesReturnsFirst() {
        Solution solution = new Solution();
        String[] words = {"xyz", "aba", "racecar", "level"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("aba");
    }

    @Test
    @DisplayName("single-element array that is palindromic")
    void singleElementPalindromic() {
        Solution solution = new Solution();
        String[] words = {"noon"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("noon");
    }

    @Test
    @DisplayName("single-element array that is not palindromic")
    void singleElementNotPalindromic() {
        Solution solution = new Solution();
        String[] words = {"hello"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("");
    }

    @Test
    @DisplayName("single-character word is always a palindrome (minimum word length boundary)")
    void singleCharacterWordIsPalindrome() {
        Solution solution = new Solution();
        String[] words = {"z", "abc"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("z");
    }

    @Test
    @DisplayName("even-length palindrome is detected correctly")
    void evenLengthPalindrome() {
        Solution solution = new Solution();
        String[] words = {"xyz", "abba"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("abba");
    }

    @Test
    @DisplayName("odd-length palindrome is detected correctly")
    void oddLengthPalindrome() {
        Solution solution = new Solution();
        String[] words = {"xyz", "level"};

        assertThat(solution.firstPalindrome(words)).isEqualTo("level");
    }

    @Test
    @DisplayName("word at maximum length boundary (100 chars) that is palindromic")
    void maxLengthWordPalindromic() {
        Solution solution = new Solution();
        String palindrome = "a".repeat(50) + "a".repeat(50);
        String[] words = {"xyz", palindrome};

        assertThat(solution.firstPalindrome(words)).isEqualTo(palindrome);
    }

    @Test
    @DisplayName("large array at maximum length boundary (100 words) with palindrome only at the end")
    void maxLengthArrayPalindromeAtEnd() {
        Solution solution = new Solution();
        String[] words = new String[100];
        for (int i = 0; i < 99; i++) {
            words[i] = "notapalindrome" + i;
        }
        words[99] = "racecar";

        assertThat(solution.firstPalindrome(words)).isEqualTo("racecar");
    }

    @Test
    @DisplayName("all words are non-palindromic across a large array returns empty string")
    void largeArrayNoPalindromeReturnsEmptyString() {
        Solution solution = new Solution();
        String[] words = new String[100];
        for (int i = 0; i < 100; i++) {
            words[i] = "notapalindrome" + i;
        }

        assertThat(solution.firstPalindrome(words)).isEqualTo("");
    }
}
