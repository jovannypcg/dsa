package mx.jovannypcg.base.p61_implementtrie;

import java.util.HashMap;
import java.util.Map;

/**
 * A trie (pronounced as "try") or prefix tree is a tree data structure used to efficiently
 * store and retrieve keys in a dataset of strings. There are various applications of this
 * data structure, such as autocomplete and spellchecker.
 *
 * <p>Implement the Trie class:
 * <ul>
 *   <li>{@code Trie()} Initializes the trie object.</li>
 *   <li>{@code void insert(String word)} Inserts the string {@code word} into the trie.</li>
 *   <li>{@code boolean search(String word)} Returns true if the string {@code word} is in the
 *   trie (i.e., was inserted before), and false otherwise.</li>
 *   <li>{@code boolean startsWith(String prefix)} Returns true if there is a previously
 *   inserted string {@code word} that has the prefix {@code prefix}, and false otherwise.</li>
 * </ul>
 *
 * @see <a href="https://leetcode.com/problems/implement-trie-prefix-tree">Problem Source</a>
 */
public class Solution {
    private Node root;

    public Solution() {
        root = new Node();
    }

    public void insert(String word) {
        Node current = root;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            // Current character doesn't exist in trie,
            // then add the character
            if (!current.alphabet.containsKey(c)) {
                current.alphabet.put(c, new Node());
            }

            // Trie traversal
            current = current.alphabet.get(c);
        }

        // End of word signal
        current.alphabet.put('*', new Node());
    }

    public boolean search(String word) {
        Node current = root;

        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);

            // Current char doesn't exist in trie
            // Word hasn't been recorded
            if (!current.alphabet.containsKey(c)) {
                return false;
            }

            current = current.alphabet.get(c);
        }

        // Is an actual word? Check for the end of word signal
        return current.alphabet.containsKey('*');
    }

    public boolean startsWith(String prefix) {
        Node current = root;

        for (int i = 0; i < prefix.length(); i++) {
            char c = prefix.charAt(i);

            // Current character doesn't exist in trie
            // Word hasn't been recorded
            if (!current.alphabet.containsKey(c)) {
                return false;
            }

            current = current.alphabet.get(c);
        }

        return true;
    }

    private static class Node {
        Map<Character, Node> alphabet;

        Node() {
            alphabet = new HashMap<>();
        }
    }
}
