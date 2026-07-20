package mx.jovannypcg.base.p29_reversewordsinstring3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("sentence with apostrophe and multiple words")
    void sentenceWithApostropheAndMultipleWords() {
        assertThat(solution.reverseWords("Let's take LeetCode contest"))
                .isEqualTo("s'teL ekat edoCteeL tsetnoc");
    }

    @Test
    @DisplayName("two words of different lengths")
    void twoWordsOfDifferentLengths() {
        assertThat(solution.reverseWords("Mr Ding")).isEqualTo("rM gniD");
    }

    @Test
    @DisplayName("single character string (minimum length constraint)")
    void singleCharacterString() {
        assertThat(solution.reverseWords("a")).isEqualTo("a");
    }

    @Test
    @DisplayName("single word with no spaces")
    void singleWordNoSpaces() {
        assertThat(solution.reverseWords("hello")).isEqualTo("olleh");
    }

    @Test
    @DisplayName("all single-character words remain unchanged")
    void allSingleCharacterWords() {
        assertThat(solution.reverseWords("a b c")).isEqualTo("a b c");
    }

    @Test
    @DisplayName("palindromic word stays the same after reversal")
    void palindromicWord() {
        assertThat(solution.reverseWords("racecar level")).isEqualTo("racecar level");
    }

    @Test
    @DisplayName("words with digits and punctuation are reversed as characters")
    void wordsWithDigitsAndPunctuation() {
        assertThat(solution.reverseWords("abc123 hi!")).isEqualTo("321cba !ih");
    }

    @Test
    @DisplayName("many short words separated by single spaces")
    void manyShortWords() {
        assertThat(solution.reverseWords("do or do not there is no try"))
                .isEqualTo("od ro od ton ereht si on yrt");
    }

    @Test
    @DisplayName("long single word near constraint scale")
    void longSingleWord() {
        String word = "a".repeat(1000) + "b";
        String expected = "b" + "a".repeat(1000);

        assertThat(solution.reverseWords(word)).isEqualTo(expected);
    }

    @Test
    @DisplayName("mixed case letters preserve case per character while reversing order")
    void mixedCaseLetters() {
        assertThat(solution.reverseWords("AbC dEf")).isEqualTo("CbA fEd");
    }
}
