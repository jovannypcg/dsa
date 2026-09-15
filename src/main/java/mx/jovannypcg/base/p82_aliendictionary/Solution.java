package mx.jovannypcg.base.p82_aliendictionary;

import java.util.*;

/**
 * There is a new alien language that uses the English alphabet, but possibly in a different
 * order. You are given a list of strings {@code words} from the alien language's dictionary,
 * where the strings in {@code words} are sorted lexicographically by the rules of this new
 * language.
 *
 * <p>Derive the order of letters in this language. Return a string containing the unique
 * letters that appear in {@code words}, arranged in a valid alphabetical order for this alien
 * language. If there is no valid ordering that satisfies the constraints implied by
 * {@code words}, return an empty string {@code ""}.
 *
 * <p>If multiple valid orderings exist, any one of them is an acceptable answer.
 *
 * @see <a href="https://leetcode.com/problems/alien-dictionary/">Alien Dictionary - LeetCode</a>
 */
public class Solution {

    /**
     * Kahn's algorithm (BFS topological sort).
     *
     * <p>Step 1: seed every unique letter with in-degree 0.
     * <p>Step 2: compare adjacent words to derive "comes-before" edges between letters.
     * <p>Step 3: run BFS from all in-degree-0 letters to build the order.
     * <p>Step 4: if not every letter got placed, a cycle exists — return "".
     */
    public String alienOrder(String[] words) {
        if (words == null) return "";

        Map<Character, Set<Character>> graph = new HashMap<>();
        Map<Character, Integer> inDegrees = new HashMap<>();

        // Step 1: look up all nodes and initialize the graph and inDegrees structures
        for (String word : words) {
            for (int i = 0; i < word.length(); i++) {
                graph.putIfAbsent(word.charAt(i), new HashSet<>());
                inDegrees.putIfAbsent(word.charAt(i), 0);
            }
        }

        // Step 2: build graph by comparing adjacent words
        for (int i = 0; i < words.length - 1; i++) {
            String first = words[i];
            String second = words[i + 1];

            int c = 0;
            boolean differenceExists = false;
            while (c < first.length() && c < second.length()) {
                if (first.charAt(c) != second.charAt(c)) {
                    if (graph.get(first.charAt(c)).add(second.charAt(c))) {
                        inDegrees.put(second.charAt(c), inDegrees.get(second.charAt(c)) + 1);
                    }

                    differenceExists = true;
                    break;
                }

                c++;
            }

            if (!differenceExists && first.length() > second.length()) return "";
        }

        StringBuilder alphabet = new StringBuilder();

        // Step 3: Run Kahn's algo

        Deque<Character> queue = new ArrayDeque<>();
        for (Map.Entry<Character, Integer> inDegree : inDegrees.entrySet()) {
            if (inDegree.getValue() == 0) queue.offer(inDegree.getKey());
        }

        while (!queue.isEmpty()) {
            char vertex = queue.poll();

            alphabet.append(vertex);

            for (char neighbor : graph.get(vertex)) {
                inDegrees.put(neighbor, inDegrees.get(neighbor) - 1);

                if (inDegrees.get(neighbor) == 0) queue.offer(neighbor);
            }
        }

        // `alphabet`'s size must be equal to the number of nodes in the graph
        // otherwise, there was a cycle somewhere
        return alphabet.length() == graph.size() ? alphabet.toString() : "";
    }
}
