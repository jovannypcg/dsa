package mx.jovannypcg.base.p30_backspacestringcompare;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SolutionTest {
    private final Solution solution = new Solution();

    @Test
    @DisplayName("backspaces produce equal strings via different edits")
    void backspacesProduceEqualStringsViaDifferentEdits() {
        assertThat(solution.backspaceCompare("ab#c", "ad#c")).isTrue();
    }

    @Test
    @DisplayName("both strings fully erased down to empty")
    void bothStringsFullyErasedDownToEmpty() {
        assertThat(solution.backspaceCompare("ab##", "c#d#")).isTrue();
    }

    @Test
    @DisplayName("different resulting characters compare unequal")
    void differentResultingCharactersCompareUnequal() {
        assertThat(solution.backspaceCompare("a#c", "b")).isFalse();
    }

    @Test
    @DisplayName("trailing backspace-cancelled character still matches")
    void trailingBackspaceCancelledCharacterStillMatches() {
        assertThat(solution.backspaceCompare("xywrrmp", "xywrrmu#p")).isTrue();
    }

    @Test
    @DisplayName("all backspaces on one side versus already-empty other side")
    void allBackspacesOnOneSideVersusAlreadyEmptyOtherSide() {
        assertThat(solution.backspaceCompare("####", "#")).isTrue();
    }

    @Test
    @DisplayName("single lowercase letter strings, minimum length, equal")
    void singleLowercaseLetterStringsMinimumLengthEqual() {
        assertThat(solution.backspaceCompare("a", "a")).isTrue();
    }

    @Test
    @DisplayName("single lowercase letter strings, minimum length, different")
    void singleLowercaseLetterStringsMinimumLengthDifferent() {
        assertThat(solution.backspaceCompare("a", "b")).isFalse();
    }

    @Test
    @DisplayName("single backspace character alone is equivalent to empty result")
    void singleBackspaceCharacterAloneIsEquivalentToEmptyResult() {
        assertThat(solution.backspaceCompare("#", "#")).isTrue();
    }

    @Test
    @DisplayName("excess backspaces beyond available characters do not underflow")
    void excessBackspacesBeyondAvailableCharactersDoNotUnderflow() {
        assertThat(solution.backspaceCompare("##a#b", "b")).isTrue();
    }

    @Test
    @DisplayName("consecutive backspaces mid-string chain together correctly")
    void consecutiveBackspacesMidStringChainTogetherCorrectly() {
        assertThat(solution.backspaceCompare("a##b", "b")).isTrue();
    }

    @Test
    @DisplayName("no backspaces present, strings equal")
    void noBackspacesPresentStringsEqual() {
        assertThat(solution.backspaceCompare("hello", "hello")).isTrue();
    }

    @Test
    @DisplayName("no backspaces present, strings of different length are unequal")
    void noBackspacesPresentStringsOfDifferentLengthAreUnequal() {
        assertThat(solution.backspaceCompare("hello", "helloo")).isFalse();
    }

    @Test
    @DisplayName("same edits leave a common prefix followed by divergent tails")
    void sameEditsLeaveACommonPrefixFollowedByDivergentTails() {
        assertThat(solution.backspaceCompare("ab#cd#e", "ab#cd#f")).isFalse();
    }

    @Test
    @DisplayName("long strings near constraint scale with heavy backspacing remain equal")
    void longStringsNearConstraintScaleWithHeavyBackspacingRemainEqual() {
        String s = "a".repeat(100) + "#".repeat(100) + "z";
        String t = "z";

        assertThat(solution.backspaceCompare(s, t)).isTrue();
    }
}
