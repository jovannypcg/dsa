package mx.jovannypcg.base.p21_validwordabbreviation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("example: apple / a3e -> true")
    void examplePartialAbbreviation() {
        assertThat(solution.validWordAbbreviation("apple", "a3e")).isTrue();
    }

    @Test
    @DisplayName("example: international / i9l -> false (wrong count)")
    void exampleWrongCount() {
        assertThat(solution.validWordAbbreviation("international", "i9l")).isFalse();
    }

    @Test
    @DisplayName("example: abbreviation / abbreviation -> true (no substrings replaced)")
    void exampleNoAbbreviation() {
        assertThat(solution.validWordAbbreviation("abbreviation", "abbreviation")).isTrue();
    }

    @Test
    @DisplayName("example: internationalization / i18n -> true")
    void exampleFullAbbreviation() {
        assertThat(solution.validWordAbbreviation("internationalization", "i18n")).isTrue();
    }

    @Test
    @DisplayName("leading zero in number -> false")
    void leadingZeroIsInvalid() {
        assertThat(solution.validWordAbbreviation("a", "01")).isFalse();
    }

    @Test
    @DisplayName("adjacent numeric substrings are invalid (i57n style)")
    void adjacentSubstringsAreInvalid() {
        assertThat(solution.validWordAbbreviation("implementation", "i57n")).isFalse();
    }

    @Test
    @DisplayName("replacing an empty substring (leading zero digit before letters) -> false")
    void emptySubstringReplacementIsInvalid() {
        assertThat(solution.validWordAbbreviation("implementation", "i0mplementation")).isFalse();
    }

    @Test
    @DisplayName("whole word replaced by its length -> true")
    void wholeWordAsNumber() {
        assertThat(solution.validWordAbbreviation("implementation", "14")).isTrue();
    }

    @Test
    @DisplayName("multiple separate numeric groups -> true")
    void multipleNumericGroups() {
        assertThat(solution.validWordAbbreviation("implementation", "imp4n5n")).isTrue();
    }

    @Test
    @DisplayName("single-character word matches identical single-character abbr")
    void singleCharacterWordMatchesLiteral() {
        assertThat(solution.validWordAbbreviation("a", "a")).isTrue();
    }

    @Test
    @DisplayName("single-character word matches number 1")
    void singleCharacterWordMatchesNumber() {
        assertThat(solution.validWordAbbreviation("a", "1")).isTrue();
    }

    @Test
    @DisplayName("number longer than remaining word -> false")
    void numberExceedsRemainingWordLength() {
        assertThat(solution.validWordAbbreviation("apple", "a10e")).isFalse();
    }

    @Test
    @DisplayName("number causes overshoot past end of word -> false")
    void numberOvershootsWordLength() {
        assertThat(solution.validWordAbbreviation("apple", "6")).isFalse();
    }

    @Test
    @DisplayName("abbr shorter than word after full literal match -> false")
    void abbrEndsBeforeWord() {
        assertThat(solution.validWordAbbreviation("apple", "appl")).isFalse();
    }

    @Test
    @DisplayName("abbr longer than word with trailing extra character -> false")
    void abbrLongerThanWord() {
        assertThat(solution.validWordAbbreviation("apple", "apples")).isFalse();
    }

    @Test
    @DisplayName("mismatched literal character -> false")
    void mismatchedLiteralCharacter() {
        assertThat(solution.validWordAbbreviation("apple", "a3f")).isFalse();
    }

    @Test
    @DisplayName("zero as a standalone number (word cannot be empty) -> false")
    void zeroNumberIsInvalidForNonEmptyWord() {
        assertThat(solution.validWordAbbreviation("a", "0")).isFalse();
    }

    @Test
    @DisplayName("substitution / s10n -> true")
    void substitutionWithNumericMiddle() {
        assertThat(solution.validWordAbbreviation("substitution", "s10n")).isTrue();
    }

    @Test
    @DisplayName("substitution / sub4ution -> true")
    void substitutionWithNumericInterior() {
        assertThat(solution.validWordAbbreviation("substitution", "sub4ution")).isTrue();
    }

    @Test
    @DisplayName("substitution / s55 -> false (adjacent numeric groups)")
    void substitutionWithAdjacentNumbersIsInvalid() {
        assertThat(solution.validWordAbbreviation("substitution", "s55")).isFalse();
    }

    @Test
    @DisplayName("word / w0rd -> false (zero-length replacement)")
    void wordWithZeroReplacementIsInvalid() {
        assertThat(solution.validWordAbbreviation("word", "w0rd")).isFalse();
    }

    @Test
    @DisplayName("word / 4 -> true (whole word replaced by its length)")
    void wordFullyReplacedByLength() {
        assertThat(solution.validWordAbbreviation("word", "4")).isTrue();
    }

    @Test
    @DisplayName("cat / c1t -> true")
    void catWithSingleCharacterReplacement() {
        assertThat(solution.validWordAbbreviation("cat", "c1t")).isTrue();
    }

    @Test
    @DisplayName("cat / ca2 -> false (number overshoots remaining word)")
    void catNumberOvershootsRemainingWord() {
        assertThat(solution.validWordAbbreviation("cat", "ca2")).isFalse();
    }
}
