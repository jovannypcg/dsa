package mx.jovannypcg.base.p50_generateallbinarystrings;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    @DisplayName("n = 1 → the two single-character strings")
    void singleCharacter() {
        List<String> result = solution.generateStrings(1);

        assertThat(result).containsExactly("A", "B");
    }

    @Test
    @DisplayName("n = 2 → all four two-character strings in lexicographic order")
    void twoCharacters() {
        List<String> result = solution.generateStrings(2);

        assertThat(result).containsExactly("AA", "AB", "BA", "BB");
    }

    @Test
    @DisplayName("n = 3 → all eight three-character strings in lexicographic order")
    void threeCharacters() {
        List<String> result = solution.generateStrings(3);

        assertThat(result).containsExactly(
                "AAA", "AAB", "ABA", "ABB", "BAA", "BAB", "BBA", "BBB"
        );
    }

    @Test
    @DisplayName("n = 4 → correct count, first, and last elements")
    void fourCharactersBoundaryCheck() {
        List<String> result = solution.generateStrings(4);

        assertThat(result).hasSize(16);
        assertThat(result.get(0)).isEqualTo("AAAA");
        assertThat(result.get(result.size() - 1)).isEqualTo("BBBB");
    }

    @Test
    @DisplayName("every generated string has length n")
    void everyStringHasCorrectLength() {
        List<String> result = solution.generateStrings(5);

        assertThat(result).allSatisfy(s -> assertThat(s).hasSize(5));
    }

    @Test
    @DisplayName("every generated string contains only 'A' and 'B'")
    void everyStringUsesOnlyAllowedCharacters() {
        List<String> result = solution.generateStrings(5);

        assertThat(result).allSatisfy(s -> assertThat(s).matches("[AB]+"));
    }

    @Test
    @DisplayName("no duplicate strings are produced")
    void noDuplicates() {
        List<String> result = solution.generateStrings(6);

        assertThat(result).doesNotHaveDuplicates();
    }

    @Test
    @DisplayName("results are sorted in lexicographic order")
    void resultsAreLexicographicallySorted() {
        List<String> result = solution.generateStrings(6);
        List<String> sorted = result.stream().sorted().collect(Collectors.toList());

        assertThat(result).isEqualTo(sorted);
    }

    @Test
    @DisplayName("n = 16 (upper bound) → produces exactly 2^16 strings with correct first and last")
    void upperBoundaryProducesAllStrings() {
        List<String> result = solution.generateStrings(16);

        assertThat(result).hasSize(65536);
        assertThat(result.get(0)).isEqualTo("A".repeat(16));
        assertThat(result.get(result.size() - 1)).isEqualTo("B".repeat(16));
        assertThat(result).doesNotHaveDuplicates();
    }
}
