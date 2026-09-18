package mx.jovannypcg.algo.p077_networkdelaytime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

/**
 * You are given a network of {@code n} nodes, labeled from {@code 1} to {@code n}. You are also
 * given {@code times}, a list of directed edges {@code times[i] = (ui, vi, wi)}, where {@code ui}
 * is the source node, {@code vi} is the target node, and {@code wi} is the time it takes for a
 * signal to travel from source to target.
 *
 * <p>We will send a signal from a given node {@code k}. Return the minimum time it takes for all
 * {@code n} nodes to receive the signal. If it is impossible for all {@code n} nodes to receive
 * the signal, return {@code -1}.
 *
 * @see <a href="https://leetcode.com/problems/network-delay-time/">Network Delay Time - LeetCode</a>
 */
public class Solution {
    public int networkDelayTime(int[][] times, int n, int k) {
        // Step 1: build an adjacency list so we can quickly answer "where can I go from node X,
        // and how long does each hop take?"
        Map<Integer, List<int[]>> graph = new HashMap<>();
        for (int[] t : times) {
            graph.computeIfAbsent(t[0], x -> new ArrayList<>()).add(new int[]{t[1], t[2]});
        }

        // Step 2: dist[node] = shortest known time to reach that node from k.
        // Every node starts "infinitely far away" except the source itself, which is 0.
        int[] dist = new int[n + 1];
        Arrays.fill(dist, Integer.MAX_VALUE);
        dist[k] = 0;

        // Step 3: the min-heap always hands us the unvisited node that is currently closest
        // to k. Each entry is {node, distanceFromK}.
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> a[1] - b[1]);
        pq.offer(new int[]{k, 0});
        boolean[] visited = new boolean[n + 1];

        // Step 4: this is Dijkstra's greedy core — always finalize the closest unvisited
        // node next, since nothing shorter to it can still be found later.
        while (!pq.isEmpty()) {
            int[] cur = pq.poll();
            int node = cur[0];
            int d = cur[1];

            // The same node can be pushed onto the heap multiple times with different
            // distances (before we knew its true shortest one). Skip stale duplicates.
            if (visited[node]) {
                continue;
            }
            visited[node] = true;

            // Step 5: "relax" every outgoing edge from this node — if hopping through it
            // gives a shorter path to a neighbor than what we knew before, update and
            // re-queue that neighbor so it can be expanded with its new, better distance.
            for (int[] neighbor : graph.getOrDefault(node, Collections.emptyList())) {
                int next = neighbor[0];
                int weight = neighbor[1];
                if (d + weight < dist[next]) {
                    dist[next] = d + weight;
                    pq.offer(new int[]{next, dist[next]});
                }
            }
        }

        // Step 6: the signal reaches every node at a different time — the answer is how
        // long the SLOWEST node takes, i.e. the maximum shortest distance. If any node is
        // still at "infinity", it was never reached, so the signal can't reach everyone.
        int maxDist = 0;
        for (int i = 1; i <= n; i++) {
            if (dist[i] == Integer.MAX_VALUE) {
                return -1;
            }
            maxDist = Math.max(maxDist, dist[i]);
        }
        return maxDist;
    }
}
