package mx.jovannypcg.base.p11_courseschedule2;

import java.util.*;

/**
 * Given {@code numCourses} courses labeled from 0 to {@code numCourses - 1} and an array
 * {@code prerequisites} where {@code prerequisites[i] = [ai, bi]} means course {@code bi} must
 * be taken before course {@code ai}, return an ordering of courses to finish all of them.
 *
 * <p>If it is impossible to finish all courses (i.e., there is a cycle), return an empty array.
 * If multiple valid orderings exist, return any one of them.
 *
 * @see <a href="https://leetcode.com/problems/course-schedule-ii/">Course Schedule II - LeetCode</a>
 */
public class Solution {

    /*
    input = [[1,0],[2,0],[3,1],[3,2]]

    0 -> 1 -> 3
      -> 2 ->

    graph = {
        0: [1, 2],
        1: [3],
        2: [3],
        3: []
    }

    inDegrees: [0, 1, 1, 2]
    order = [0]

    queue = []
    node = 0


    */
    public int[] findOrder(int numCourses, int[][] prerequisites) {
        Map<Integer, List<Integer>> graph = new HashMap<>();
        int[] inDegree = new int[numCourses];
        int[] order = new int[numCourses];

        for (int i = 0; i < numCourses; i++)
            graph.put(i, new ArrayList<>());

        for (int[] prerequisite : prerequisites) {
            graph.get(prerequisite[1]).add(prerequisite[0]);
            inDegree[prerequisite[0]]++;
        }

        Deque<Integer> queue = new ArrayDeque<>();

        for (int i = 0; i < inDegree.length; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        int idx = 0;
        while (!queue.isEmpty()) {
            int node = queue.poll();

            order[idx++] = node;

            for (int neighbor : graph.get(node)) {
                inDegree[neighbor]--;

                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        return idx == numCourses ? order : new int[0];
    }
}
