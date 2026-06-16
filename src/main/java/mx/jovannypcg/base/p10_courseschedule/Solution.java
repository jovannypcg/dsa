package mx.jovannypcg.base.p10_courseschedule;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

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
        Map<Integer, List<Integer>> graph = buildGraph(numCourses, prerequisites);
        char[] visited = new char[numCourses];

        for (int[] prerequisite : prerequisites) {
            if (!canFinish(graph, visited, prerequisite[0])) {
                return false;
            }
        }

        return true;
    }

    boolean canFinish(Map<Integer, List<Integer>> graph, char[] visited, int visiting) {
        visited[visiting] = '*';

        for (int prerequisite : graph.get(visiting)) {
            if (visited[prerequisite] == '*') return false; // cycle detected
            if (visited[prerequisite] == 'v') continue; // prerequisite already visited

            if (!canFinish(graph, visited, prerequisite)) {
                return false;
            }
        }

        visited[visiting] = 'v';

        return true;
    }

    Map<Integer, List<Integer>> buildGraph(int numCourses, int[][] prerequisites) {
        if (prerequisites == null || prerequisites.length == 0)
            return Map.of();

        Map<Integer, List<Integer>> graph = new HashMap<>();

        for (int i = 0; i < numCourses; i++) {
            graph.put(i, new ArrayList<>());
        }

        for (int[] prerequisite : prerequisites) {
            graph.get(prerequisite[0]).add(prerequisite[1]);
        }

        return graph;
    }
}
