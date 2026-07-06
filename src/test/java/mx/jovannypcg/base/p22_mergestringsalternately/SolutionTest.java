package mx.jovannypcg.base.p22_mergestringsalternately;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("equal length words → fully interleaved")
    void equalLengthWords() {
        assertThat(solution.mergeAlternately("abc", "xyz")).isEqualTo("axbycz");
    }

    @Test
    @DisplayName("word2 longer than word1 → remaining word2 characters appended")
    void word2Longer() {
        assertThat(solution.mergeAlternately("ab", "abbxxc")).isEqualTo("aabbbxxc");
    }

    @Test
    @DisplayName("word1 longer than word2 → remaining word1 characters appended")
    void word1Longer() {
        assertThat(solution.mergeAlternately("abcd", "xy")).isEqualTo("axbycd");
    }

    @Test
    @DisplayName("single character words → simple concatenation")
    void singleCharacterWords() {
        assertThat(solution.mergeAlternately("a", "b")).isEqualTo("ab");
    }

    @Test
    @DisplayName("word2 is a single character much shorter than word1")
    void word2MuchShorter() {
        assertThat(solution.mergeAlternately("aaaa", "b")).isEqualTo("abaaa");
    }

    @Test
    @DisplayName("word1 is a single character much shorter than word2")
    void word1MuchShorter() {
        assertThat(solution.mergeAlternately("a", "bbbb")).isEqualTo("abbbb");
    }

    @Test
    @DisplayName("both words contain repeated characters")
    void repeatedCharacters() {
        assertThat(solution.mergeAlternately("aaa", "bbb")).isEqualTo("ababab");
    }

    @Test
    @DisplayName("minimum length words at both bounds → single character each")
    void minimumLengthBothWords() {
        assertThat(solution.mergeAlternately("x", "y")).isEqualTo("xy");
    }

    @Test
    @DisplayName("maximum length words at constraint bound (100 chars each)")
    void maximumLengthWords() {
        String word1 = "a".repeat(100);
        String word2 = "b".repeat(100);
        String expected = "ab".repeat(100);

        assertThat(solution.mergeAlternately(word1, word2)).isEqualTo(expected);
    }

    @Test
    @DisplayName("word1 at maximum length, word2 at minimum length")
    void word1MaxWord2Min() {
        String word1 = "a".repeat(100);
        String word2 = "b";
        String expected = "ab" + "a".repeat(99);

        assertThat(solution.mergeAlternately(word1, word2)).isEqualTo(expected);
    }
}
