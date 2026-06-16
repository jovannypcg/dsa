package mx.jovannypcg.base.p02_validanagram;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    @ParameterizedTest(name = "isAnagram(\"{0}\", \"{1}\") == true")
    @CsvSource({
        "racecar, carrace",
        "anagram, nagaram",
        "a, a",
        "aab, baa",
        "listen, silent"
    })
    void returnsTrue_whenStringsAreAnagrams(String s, String t) {
        assertThat(solution.isAnagram(s, t)).isTrue();
    }

    @ParameterizedTest(name = "isAnagram(\"{0}\", \"{1}\") == false")
    @CsvSource({
        "jar, jam",
        "rat, car",
        "abc, ab",
        "ab, abc",
        "aab, bba"
    })
    void returnsFalse_whenStringsAreNotAnagrams(String s, String t) {
        assertThat(solution.isAnagram(s, t)).isFalse();
    }
}
