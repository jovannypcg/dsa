package mx.jovannypcg.algo.p010_courseschedule;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Given {@code numCourses} courses labeled from {@code 0} to {@code numCourses - 1} and an array
 * of {@code prerequisites} where {@code prerequisites[i] = [ai, bi]} means you must take course
 * {@code bi} before course {@code ai}, return {@code true} if you can finish all courses,
 * or {@code false} if a cycle makes it impossible.
 *
 * @see <a href="https://leetcode.com/problems/course-schedule/">Course Schedule - LeetCode</a>
 */
public class Solution {

    public boolean canFinish(int numCourses, int[][] prerequisites) {
        var graph = buildGraph(prerequisites);
        var inDegrees = getInDegrees(numCourses, prerequisites);
        var queue = new ArrayDeque<Integer>();
        int netCourses = 0;

        for (int vertex = 0; vertex < numCourses; vertex++) {
            if (inDegrees[vertex] == 0) queue.offer(vertex);
        }

        while (!queue.isEmpty()) {
            int vertex = queue.poll();
            netCourses++;

            for (int neighbor : graph.getOrDefault(vertex, List.of())) {
                inDegrees[neighbor]--;

                if (inDegrees[neighbor] == 0) queue.offer(neighbor);
            }
        }

        return netCourses == numCourses;
    }

    private int[] getInDegrees(int numCourses, int[][] prerequisites) {
        int[] inDegrees = new int[numCourses];

        for (int[] prerequisite : prerequisites) {
            int v = prerequisite[0];

            inDegrees[v]++;
        }

        return inDegrees;
    }

    private Map<Integer, List<Integer>> buildGraph(int[][] edges) {
        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int[] edge : edges) {
            int u = edge[1];
            int v = edge[0];

            graph.putIfAbsent(u, new ArrayList<>());
            graph.get(u).add(v);
        }

        return graph;
    }
}
