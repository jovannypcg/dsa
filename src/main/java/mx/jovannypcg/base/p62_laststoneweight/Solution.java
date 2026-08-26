package mx.jovannypcg.base.p62_laststoneweight;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * You are given an array of integers {@code stones} where {@code stones[i]} is the weight of
 * the ith stone.
 *
 * <p>We are playing a game with the stones. On each turn, we choose the heaviest two stones and
 * smash them together. Suppose the heaviest two stones have weights x and y with x &lt;= y. The
 * result of this smash is: if x == y, both stones are destroyed; if x != y, the stone of weight
 * x is destroyed, and the stone of weight y has new weight y - x.
 *
 * <p>At the end of the game, there is at most one stone left. Return the weight of the last
 * remaining stone. If there are no stones left, return 0.
 *
 * @see <a href="https://leetcode.com/problems/last-stone-weight">Problem Source</a>
 */
public class Solution {

    private Comparator<Integer> stoneWeightComparator = (a, b) ->
        Integer.compare(b, a);

    public int lastStoneWeight(int[] stones) {
        var heaviestStones = getHeaviestStones(stones);

        while (heaviestStones.size() > 1) {
            int heaviest = heaviestStones.poll();
            int secondHeaviest = heaviestStones.poll();
            int smash = heaviest - secondHeaviest;

            heaviestStones.offer(smash);
        }

        return heaviestStones.peek();
    }

    private PriorityQueue<Integer> getHeaviestStones(int[] stones) {
        PriorityQueue<Integer> heaviestStones = new PriorityQueue<>(
            stoneWeightComparator
        );

        for (int stone : stones) heaviestStones.offer(stone);

        return heaviestStones;
    }
}
