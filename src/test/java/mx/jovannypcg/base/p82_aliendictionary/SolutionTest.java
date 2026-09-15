package mx.jovannypcg.base.p82_aliendictionary;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    private final Solution solution = new Solution();

    /**
     * Verifies that {@code result} is a valid alien order for {@code words}: it must contain
     * every unique letter across {@code words} exactly once, and for every pair of adjacent
     * words, the letter at their first differing position must respect the order in
     * {@code result}.
     */
    private void assertValidAlienOrder(String result, String[] words) {
        Set<Character> uniqueChars = new HashSet<>();
        for (String word : words) {
            for (char c : word.toCharArray()) {
                uniqueChars.add(c);
            }
        }

        assertThat(result.length()).isEqualTo(uniqueChars.size());
        assertThat(result.chars().distinct().count()).isEqualTo(result.length());

        Set<Character> resultChars = new HashSet<>();
        for (char c : result.toCharArray()) {
            resultChars.add(c);
        }
        assertThat(resultChars).isEqualTo(uniqueChars);

        int[] position = new int[128];
        for (int i = 0; i < result.length(); i++) {
            position[result.charAt(i)] = i;
        }

        for (int i = 0; i < words.length - 1; i++) {
            String first = words[i];
            String second = words[i + 1];
            int minLength = Math.min(first.length(), second.length());

            for (int j = 0; j < minLength; j++) {
                char before = first.charAt(j);
                char after = second.charAt(j);

                if (before != after) {
                    assertThat(position[before])
                            .as("expected letter '%c' before letter '%c' due to words \"%s\" and \"%s\"",
                                    before, after, first, second)
                            .isLessThan(position[after]);
                    break;
                }
            }
        }
    }

    @Test
    @DisplayName("classic multi-letter chain produces the exact single valid order")
    void classicMultiLetterChainProducesExactOrder() {
        String[] words = {"wrt", "wrf", "er", "ett", "rftt"};

        String result = solution.alienOrder(words);

        assertThat(result).isEqualTo("wertf");
    }

    @Test
    @DisplayName("two single-letter words produce the exact single valid order")
    void twoSingleLetterWordsProduceExactOrder() {
        String[] words = {"z", "x"};

        String result = solution.alienOrder(words);

        assertThat(result).isEqualTo("zx");
    }

    @Test
    @DisplayName("direct two-letter cycle → empty string")
    void directTwoLetterCycleReturnsEmptyString() {
        String[] words = {"z", "x", "z"};

        String result = solution.alienOrder(words);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("word after its own prefix → empty string")
    void invalidPrefixOrderReturnsEmptyString() {
        String[] words = {"abc", "ab"};

        String result = solution.alienOrder(words);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("single word with no adjacent pairs → any permutation of its unique letters is valid")
    void singleWordNoConstraintsProducesValidPermutation() {
        String[] words = {"hello"};

        String result = solution.alienOrder(words);

        assertValidAlienOrder(result, words);
    }

    @Test
    @DisplayName("independent constraint groups produce a valid interleaving")
    void independentConstraintGroupsProduceValidOrder() {
        String[] words = {"ac", "ab", "zc", "zb"};

        String result = solution.alienOrder(words);

        assertValidAlienOrder(result, words);
    }

    @Test
    @DisplayName("hidden cycle spanning three letters → empty string")
    void hiddenCycleAcrossThreeLettersReturnsEmptyString() {
        String[] words = {"a", "b", "c", "a"};

        String result = solution.alienOrder(words);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("repeated identical words produce a single-letter order")
    void identicalWordsRepeatedProduceSingleLetterOrder() {
        String[] words = {"a", "a", "a"};

        String result = solution.alienOrder(words);

        assertThat(result).isEqualTo("a");
    }

    @Test
    @DisplayName("full alphabet chain of single-letter words produces the exact a-to-z order")
    void fullAlphabetChainProducesExactOrder() {
        String[] words = new String[26];
        for (int i = 0; i < 26; i++) {
            words[i] = String.valueOf((char) ('a' + i));
        }

        String result = solution.alienOrder(words);

        assertThat(result).isEqualTo("abcdefghijklmnopqrstuvwxyz");
    }

    @Test
    @DisplayName("branching constraints with an undetermined pair still produce a valid order")
    void branchingConstraintsProduceValidOrder() {
        String[] words = {"a", "ba", "bc", "bd"};

        String result = solution.alienOrder(words);

        assertValidAlienOrder(result, words);
    }
}
