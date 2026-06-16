package mx.jovannypcg.base.p03_groupanagrams;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @Test
    void groupAnagrams_typicalInput_groupsCorrectly() {
        String[] strs = {"act", "pots", "tops", "cat", "stop", "hat"};
        List<List<String>> result = solution.groupAnagrams(strs);

        assertThat(result).hasSize(3);
        assertThat(result).anySatisfy(group -> assertThat(group).containsExactlyInAnyOrder("act", "cat"));
        assertThat(result).anySatisfy(group -> assertThat(group).containsExactlyInAnyOrder("pots", "tops", "stop"));
        assertThat(result).anySatisfy(group -> assertThat(group).containsExactlyInAnyOrder("hat"));
    }

    @Test
    void groupAnagrams_singleElement_returnsSingleGroup() {
        String[] strs = {"x"};
        List<List<String>> result = solution.groupAnagrams(strs);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).containsExactly("x");
    }

    @Test
    void groupAnagrams_emptyString_returnsSingleGroup() {
        String[] strs = {""};
        List<List<String>> result = solution.groupAnagrams(strs);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).containsExactly("");
    }

    @Test
    void groupAnagrams_multipleEmptyStrings_groupedTogether() {
        String[] strs = {"", "", ""};
        List<List<String>> result = solution.groupAnagrams(strs);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).hasSize(3);
    }

    @Test
    void groupAnagrams_noAnagramsAmongStrings_eachInOwnGroup() {
        String[] strs = {"abc", "def", "ghi"};
        List<List<String>> result = solution.groupAnagrams(strs);

        assertThat(result).hasSize(3);
        result.forEach(group -> assertThat(group).hasSize(1));
    }

    @Test
    void groupAnagrams_allSameAnagram_allInOneGroup() {
        String[] strs = {"abc", "bca", "cab", "acb", "bac", "cba"};
        List<List<String>> result = solution.groupAnagrams(strs);

        assertThat(result).hasSize(1);
        assertThat(result.get(0)).hasSize(6);
    }

    @Test
    void groupAnagrams_singleCharacterStrings_groupsByCharacter() {
        String[] strs = {"a", "b", "a", "c", "b"};
        List<List<String>> result = solution.groupAnagrams(strs);

        assertThat(result).hasSize(3);
        assertThat(result).anySatisfy(group -> assertThat(group).hasSize(2).containsOnly("a"));
        assertThat(result).anySatisfy(group -> assertThat(group).hasSize(2).containsOnly("b"));
        assertThat(result).anySatisfy(group -> assertThat(group).hasSize(1).containsOnly("c"));
    }

    @Test
    void groupAnagrams_longStrings_handledCorrectly() {
        String[] strs = {"abcdefghij", "jihgfedcba", "aaaaaaaaaa"};
        List<List<String>> result = solution.groupAnagrams(strs);

        assertThat(result).hasSize(2);
        assertThat(result).anySatisfy(group ->
                assertThat(group).containsExactlyInAnyOrder("abcdefghij", "jihgfedcba"));
        assertThat(result).anySatisfy(group ->
                assertThat(group).containsExactly("aaaaaaaaaa"));
    }
}
