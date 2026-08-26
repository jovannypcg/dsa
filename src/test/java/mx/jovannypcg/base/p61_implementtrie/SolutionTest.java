package mx.jovannypcg.base.p61_implementtrie;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    @Test
    @DisplayName("example from problem statement: insert/search/startsWith mixed calls")
    void exampleFromProblemStatement() {
        Solution trie = new Solution();

        trie.insert("apple");

        assertThat(trie.search("apple")).isTrue();
        assertThat(trie.search("app")).isFalse();
        assertThat(trie.startsWith("app")).isTrue();

        trie.insert("app");

        assertThat(trie.search("app")).isTrue();
    }

    @Test
    @DisplayName("search on empty trie returns false")
    void searchOnEmptyTrieReturnsFalse() {
        Solution trie = new Solution();

        assertThat(trie.search("apple")).isFalse();
    }

    @Test
    @DisplayName("startsWith on empty trie returns false")
    void startsWithOnEmptyTrieReturnsFalse() {
        Solution trie = new Solution();

        assertThat(trie.startsWith("a")).isFalse();
    }

    @Test
    @DisplayName("single character word, minimum length boundary")
    void singleCharacterWord() {
        Solution trie = new Solution();

        trie.insert("a");

        assertThat(trie.search("a")).isTrue();
        assertThat(trie.search("aa")).isFalse();
        assertThat(trie.startsWith("aa")).isFalse();
    }

    @Test
    @DisplayName("word that is a prefix of another inserted word is not found by search")
    void wordAsPrefixOfAnotherIsNotFoundBySearch() {
        Solution trie = new Solution();

        trie.insert("apple");

        assertThat(trie.search("appl")).isFalse();
        assertThat(trie.startsWith("appl")).isTrue();
    }

    @Test
    @DisplayName("word longer than any inserted word is not found")
    void wordLongerThanInsertedIsNotFound() {
        Solution trie = new Solution();

        trie.insert("app");

        assertThat(trie.search("apple")).isFalse();
        assertThat(trie.startsWith("apple")).isFalse();
    }

    @Test
    @DisplayName("inserting the same word twice does not break search")
    void insertingSameWordTwice() {
        Solution trie = new Solution();

        trie.insert("app");
        trie.insert("app");

        assertThat(trie.search("app")).isTrue();
    }

    @Test
    @DisplayName("multiple words sharing a common prefix are all searchable independently")
    void multipleWordsSharingCommonPrefix() {
        Solution trie = new Solution();

        trie.insert("bat");
        trie.insert("batman");
        trie.insert("batmobile");

        assertThat(trie.search("bat")).isTrue();
        assertThat(trie.search("batman")).isTrue();
        assertThat(trie.search("batmobile")).isTrue();
        assertThat(trie.search("bam")).isFalse();
        assertThat(trie.startsWith("bat")).isTrue();
        assertThat(trie.startsWith("batm")).isTrue();
        assertThat(trie.startsWith("batx")).isFalse();
    }

    @Test
    @DisplayName("words with no shared prefix are stored independently")
    void wordsWithNoSharedPrefix() {
        Solution trie = new Solution();

        trie.insert("cat");
        trie.insert("dog");

        assertThat(trie.search("cat")).isTrue();
        assertThat(trie.search("dog")).isTrue();
        assertThat(trie.startsWith("c")).isTrue();
        assertThat(trie.startsWith("d")).isTrue();
        assertThat(trie.startsWith("b")).isFalse();
    }

    @Test
    @DisplayName("word at the maximum length constraint boundary of 2000 characters")
    void wordAtMaximumLengthBoundary() {
        Solution trie = new Solution();
        String longWord = "a".repeat(2000);

        trie.insert(longWord);

        assertThat(trie.search(longWord)).isTrue();
        assertThat(trie.startsWith(longWord)).isTrue();
        assertThat(trie.search("a".repeat(1999))).isFalse();
        assertThat(trie.startsWith("a".repeat(1999))).isTrue();
    }
}
