package mx.jovannypcg.base.p03_groupanagrams;

import java.util.List;
import java.util.LinkedList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;

/**
 * Given an array of strings {@code strs}, group all anagrams together into sublists.
 * The output may be returned in any order.
 *
 * <p>An anagram is a string that contains the exact same characters as another string,
 * but the order of the characters can be different.
 *
 * @see <a href="https://neetcode.io/problems/anagram-groups">Group Anagrams - NeetCode</a>
 */
public class Solution {
    /*
    strs = ["act","pots","tops","cat","stop","hat"]
                                              ^

    current = "hat"
    sorted = "aht"
    exists? = no
    groups = {
        "act" -> ["act", "cat"],
        "opst" -> ["pots", "tops", "stop"],
        "aht" -> ["hat"]
    }

    output: [["act", "cat"], ["pots", "tops", "stop"], ["hat"]]
    */
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null || strs.length == 0) {
            return List.of();
        }

        // Space complexity: O(n)
        // n = number of strings in `strs`; worst case is that
        // all strings are unique
        Map<String, List<String>> groups = new HashMap<>();

        // Time complexity: O(n * k log k)
        // n = number of strings in `strs`
        // k = number of characters in every string in `strs`
        for (int i = 0; i < strs.length; i++) {
            String current = strs[i];
            String sorted = sort(current);

            groups.putIfAbsent(sorted, new LinkedList<>());
            groups.get(sorted).add(current);
        }

        return new LinkedList<>(groups.values());
    }

    // Time complexity: O(k log k)
    // k = number of characters in String
    String sort(String str) {
        char[] sorted = str.toCharArray();
        Arrays.sort(sorted);

        return new String(sorted);
    }
}
