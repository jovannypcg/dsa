package mx.jovannypcg.base.p31_arraystringsareequal;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("different splits of the same string are equivalent")
    void differentSplitsOfSameStringAreEquivalent() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"ab", "c"}, new String[]{"a", "bc"})).isTrue();
    }

    @Test
    @DisplayName("same split lengths but different characters are not equivalent")
    void sameSplitLengthsButDifferentCharactersAreNotEquivalent() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"a", "cb"}, new String[]{"ab", "c"})).isFalse();
    }

    @Test
    @DisplayName("many small elements versus a single concatenated element")
    void manySmallElementsVersusASingleConcatenatedElement() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"abc", "d", "defg"}, new String[]{"abcddefg"})).isTrue();
    }

    @Test
    @DisplayName("single-element arrays with identical strings")
    void singleElementArraysWithIdenticalStrings() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"abc"}, new String[]{"abc"})).isTrue();
    }

    @Test
    @DisplayName("single-element arrays with different strings")
    void singleElementArraysWithDifferentStrings() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"abc"}, new String[]{"abd"})).isFalse();
    }

    @Test
    @DisplayName("minimum length single character elements, equal")
    void minimumLengthSingleCharacterElementsEqual() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"a"}, new String[]{"a"})).isTrue();
    }

    @Test
    @DisplayName("minimum length single character elements, different")
    void minimumLengthSingleCharacterElementsDifferent() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"a"}, new String[]{"b"})).isFalse();
    }

    @Test
    @DisplayName("concatenated strings have different lengths")
    void concatenatedStringsHaveDifferentLengths() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"abc"}, new String[]{"ab"})).isFalse();
    }

    @Test
    @DisplayName("one array is a prefix of the other overall")
    void oneArrayIsAPrefixOfTheOtherOverall() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"abc", "d"}, new String[]{"abcd", "e"})).isFalse();
    }

    @Test
    @DisplayName("many single-character elements reconstruct the same string")
    void manySingleCharacterElementsReconstructTheSameString() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"a", "b", "c", "d"}, new String[]{"ab", "cd"})).isTrue();
    }

    @Test
    @DisplayName("mismatch occurs at the very last character")
    void mismatchOccursAtTheVeryLastCharacter() {
        assertThat(solution.arrayStringsAreEqual(new String[]{"abcx"}, new String[]{"abc", "y"})).isFalse();
    }

    @Test
    @DisplayName("large number of elements near constraint scale, equivalent")
    void largeNumberOfElementsNearConstraintScaleEquivalent() {
        String[] word1 = new String[1000];
        for (int i = 0; i < 1000; i++) {
            word1[i] = "a";
        }
        String[] word2 = new String[]{"a".repeat(1000)};

        assertThat(solution.arrayStringsAreEqual(word1, word2)).isTrue();
    }

    @Test
    @DisplayName("single long element near constraint scale compared to itself split apart")
    void singleLongElementNearConstraintScaleComparedToItselfSplitApart() {
        String longWord = "xy".repeat(500);

        assertThat(solution.arrayStringsAreEqual(new String[]{longWord}, new String[]{longWord.substring(0, 500), longWord.substring(500)})).isTrue();
    }
}
