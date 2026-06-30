package mx.jovannypcg.base.p16_middleoflinkedlist;

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
                // odd-length: middle is the single center node
                Arguments.of(new int[]{1, 2, 3, 4, 5}, new int[]{3, 4, 5}),

                // even-length: must return the second middle node
                Arguments.of(new int[]{1, 2, 3, 4, 5, 6}, new int[]{4, 5, 6}),

                // two nodes: second node is the second (and only valid) middle
                Arguments.of(new int[]{1, 2}, new int[]{2}),

                // three nodes: center is unambiguous
                Arguments.of(new int[]{10, 20, 30}, new int[]{20, 30}),

                // four nodes: second middle
                Arguments.of(new int[]{1, 2, 3, 4}, new int[]{3, 4}),

                // all same values
                Arguments.of(new int[]{5, 5, 5, 5, 5}, new int[]{5, 5, 5}),

                // boundary values: min and max Node.val
                Arguments.of(new int[]{1, 100}, new int[]{100}),
                Arguments.of(new int[]{1, 50, 100}, new int[]{50, 100})
        );
    }

    @ParameterizedTest(name = "[{index}] head={0} → middle={1}")
    @MethodSource("cases")
    @DisplayName("middleNode returns the correct middle node and remainder of list")
    void middleNode(int[] input, int[] expectedVals) {
        Solution.ListNode head = buildList(input);
        Solution.ListNode result = solution.middleNode(head);
        assertThat(toArray(result)).isEqualTo(expectedVals);
    }

    @Test
    @DisplayName("single node list returns that node (minimum list length per constraints)")
    void singleNode() {
        Solution.ListNode head = new Solution.ListNode(42);
        Solution.ListNode result = solution.middleNode(head);
        assertThat(result.val).isEqualTo(42);
        assertThat(result.next).isNull();
    }

    @Test
    @DisplayName("list of 100 nodes returns the 51st node (maximum length per constraints)")
    void hundredNodes() {
        int[] vals = new int[100];
        for (int i = 0; i < 100; i++) vals[i] = i + 1;
        Solution.ListNode head = buildList(vals);
        Solution.ListNode result = solution.middleNode(head);
        assertThat(result.val).isEqualTo(51);
    }
}
