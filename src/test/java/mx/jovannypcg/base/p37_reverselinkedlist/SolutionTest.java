package mx.jovannypcg.base.p37_reverselinkedlist;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    @DisplayName("empty list → empty list (minimum length per constraints)")
    void emptyList() {
        Solution.ListNode result = solution.reverseList(null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("single node → same single node")
    void singleNode() {
        Solution.ListNode head = new Solution.ListNode(42);
        Solution.ListNode result = solution.reverseList(head);
        assertThat(toArray(result)).containsExactly(42);
    }

    @Test
    @DisplayName("two nodes → order flips")
    void twoNodes() {
        Solution.ListNode head = buildList(1, 2);
        Solution.ListNode result = solution.reverseList(head);
        assertThat(toArray(result)).containsExactly(2, 1);
    }

    @Test
    @DisplayName("five nodes → fully reversed, matches example 1")
    void fiveNodes() {
        Solution.ListNode head = buildList(1, 2, 3, 4, 5);
        Solution.ListNode result = solution.reverseList(head);
        assertThat(toArray(result)).containsExactly(5, 4, 3, 2, 1);
    }

    @Test
    @DisplayName("list with duplicate values → order reversed, duplicates preserved")
    void duplicateValues() {
        Solution.ListNode head = buildList(7, 7, 3, 7);
        Solution.ListNode result = solution.reverseList(head);
        assertThat(toArray(result)).containsExactly(7, 3, 7, 7);
    }

    @Test
    @DisplayName("list with negative and positive values → order reversed")
    void mixedSignValues() {
        Solution.ListNode head = buildList(-5, 0, 5, -10);
        Solution.ListNode result = solution.reverseList(head);
        assertThat(toArray(result)).containsExactly(-10, 5, 0, -5);
    }

    @Test
    @DisplayName("node values at min and max boundaries (-5000 and 5000)")
    void boundaryValues() {
        Solution.ListNode head = buildList(-5000, 0, 5000);
        Solution.ListNode result = solution.reverseList(head);
        assertThat(toArray(result)).containsExactly(5000, 0, -5000);
    }

    @Test
    @DisplayName("list of 5000 nodes → reversed correctly (maximum length per constraints)")
    void maxLengthList() {
        int[] vals = new int[5000];
        for (int i = 0; i < 5000; i++) vals[i] = i + 1;
        Solution.ListNode head = buildList(vals);

        Solution.ListNode result = solution.reverseList(head);

        int[] expected = new int[5000];
        for (int i = 0; i < 5000; i++) expected[i] = 5000 - i;
        assertThat(toArray(result)).containsExactly(expected);
    }

    @Test
    @DisplayName("reversing twice returns the original order")
    void doubleReverseRestoresOriginal() {
        Solution.ListNode head = buildList(1, 2, 3, 4);
        Solution.ListNode onceReversed = solution.reverseList(head);
        Solution.ListNode twiceReversed = solution.reverseList(onceReversed);
        assertThat(toArray(twiceReversed)).containsExactly(1, 2, 3, 4);
    }
}
