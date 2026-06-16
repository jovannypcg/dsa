package mx.jovannypcg.base.p02_validanagram;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Given two strings {@code s} and {@code t}, return {@code true} if the two strings are
 * anagrams of each other, otherwise return {@code false}.
 *
 * <p>An anagram is a string that contains the exact same characters as another string,
 * but the order of the characters can be different.
 *
 * @see <a href="https://neetcode.io/problems/is-anagram">Valid Anagram - NeetCode</a>
 */
public class Solution {

    private static final int ALPHABET_SIZE = 26;

    public boolean isAnagram(String s, String t) {
        // return a(s, t);
        // return b(s, t);
        return arrayCombined(s, t);
    }

    public boolean a(String s, String t) {
        int[] charCountS = getCharCount(s);
        int[] charCountT = getCharCount(t);

        return Arrays.equals(charCountS, charCountT);
    }

    public boolean b(String s, String t) {
        Map<Character, Integer> countS = getCharCountMap(s);
        Map<Character, Integer> countT = getCharCountMap(t);

        return countS.equals(countT);
    }

    public boolean arrayCombined(String s, String t) {
        if (s.length() != t.length()) {
            return false;
        }

        int[] count = new int[ALPHABET_SIZE];

        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }

        for (int i = 0; i < count.length; i++) {
            if (count[i] != 0) {
                return false;
            }
        }

        return true;
    }

    int[] getCharCount(String str) {
        if (str == null || str.isBlank()) {
            return new int[ALPHABET_SIZE];
        }

        int[] charCount = new int[ALPHABET_SIZE];

        for (int idx = 0; idx < str.length(); idx++) {
            int current = str.charAt(idx);
            int alphabetIdx = current - 'a';

            charCount[alphabetIdx]++;
        }

        return charCount;
    }

    Map<Character, Integer> getCharCountMap(String str) {
        Map<Character, Integer> count = new HashMap<>();

        for (int idx = 0; idx < str.length(); idx++) {
            char current = str.charAt(idx);

            count.put(current, count.getOrDefault(current, 0) + 1);
        }

        return count;
    }
}
