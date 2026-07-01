package mx.jovannypcg.base.p18_removenthnode;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private Solution solution;

    @BeforeEach
    void setUp() {
        solution = new Solution();
    }

    private Solution.ListNode buildList(int... vals) {
        Solution.ListNode dummy = new Solution.ListNode(0);
        Solution.ListNode curr = dummy;
        for (int v : vals) {
            curr.next = new Solution.ListNode(v);
            curr = curr.next;
        }
        return dummy.next;
    }

    private int[] toArray(Solution.ListNode node) {
        List<Integer> list = new ArrayList<>();
        while (node != null) {
            list.add(node.val);
            node = node.next;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    static Stream<Arguments> cases() {
        return Stream.of(
                // remove from middle
                Arguments.of(new int[]{1, 2, 3, 4, 5}, 2, new int[]{1, 2, 3, 5}),

                // remove last node (n=1)
                Arguments.of(new int[]{1, 2}, 1, new int[]{1}),

                // remove first node (n=sz, head changes)
                Arguments.of(new int[]{1, 2}, 2, new int[]{2}),

                // three-node list, remove middle
                Arguments.of(new int[]{1, 2, 3}, 2, new int[]{1, 3}),

                // three-node list, remove head
                Arguments.of(new int[]{1, 2, 3}, 3, new int[]{2, 3}),

                // three-node list, remove tail
                Arguments.of(new int[]{1, 2, 3}, 1, new int[]{1, 2}),

                // boundary Node.val: 0 and 100
                Arguments.of(new int[]{0, 50, 100}, 2, new int[]{0, 100}),

                // maximum list size (sz=30), remove from end
                Arguments.of(buildArray(30), 1, buildExpected(30, 1)),

                // maximum list size (sz=30), remove head
                Arguments.of(buildArray(30), 30, buildExpected(30, 30))
        );
    }

    private static int[] buildArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = i + 1;
        return arr;
    }

    private static int[] buildExpected(int size, int nFromEnd) {
        int removeFromFront = size - nFromEnd;
        List<Integer> result = new ArrayList<>();
        for (int i = 1; i <= size; i++) {
            if (i - 1 != removeFromFront) result.add(i);
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    @ParameterizedTest(name = "[{index}] head={0}, n={1} → {2}")
    @MethodSource("cases")
    @DisplayName("removeNthFromEnd returns correct list after removal")
    void removeNthFromEnd(int[] input, int n, int[] expected) {
        Solution.ListNode head = buildList(input);
        Solution.ListNode result = solution.removeNthFromEnd(head, n);
        assertThat(toArray(result)).isEqualTo(expected);
    }

    @Test
    @DisplayName("single-node list with n=1 returns null (empty list)")
    void singleNodeReturnsNull() {
        Solution.ListNode head = new Solution.ListNode(7);
        Solution.ListNode result = solution.removeNthFromEnd(head, 1);
        assertThat(result).isNull();
    }
}
