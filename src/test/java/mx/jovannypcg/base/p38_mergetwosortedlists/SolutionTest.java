package mx.jovannypcg.base.p38_mergetwosortedlists;

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
    @DisplayName("both lists empty → empty result (minimum length per constraints)")
    void bothListsEmpty() {
        Solution.ListNode result = solution.mergeTwoLists(null, null);
        assertThat(result).isNull();
    }

    @Test
    @DisplayName("list1 empty, list2 has one node → returns list2, matches example 3")
    void list1EmptyList2SingleNode() {
        Solution.ListNode list2 = buildList(0);
        Solution.ListNode result = solution.mergeTwoLists(null, list2);
        assertThat(toArray(result)).containsExactly(0);
    }

    @Test
    @DisplayName("list2 empty, list1 has one node → returns list1")
    void list2EmptyList1SingleNode() {
        Solution.ListNode list1 = buildList(5);
        Solution.ListNode result = solution.mergeTwoLists(list1, null);
        assertThat(toArray(result)).containsExactly(5);
    }

    @Test
    @DisplayName("classic interleaving case → merged in sorted order, matches example 1")
    void classicInterleaving() {
        Solution.ListNode list1 = buildList(1, 2, 4);
        Solution.ListNode list2 = buildList(1, 3, 4);
        Solution.ListNode result = solution.mergeTwoLists(list1, list2);
        assertThat(toArray(result)).containsExactly(1, 1, 2, 3, 4, 4);
    }

    @Test
    @DisplayName("list1 entirely smaller than list2 → list1 fully placed before list2")
    void list1EntirelySmaller() {
        Solution.ListNode list1 = buildList(1, 2, 3);
        Solution.ListNode list2 = buildList(4, 5, 6);
        Solution.ListNode result = solution.mergeTwoLists(list1, list2);
        assertThat(toArray(result)).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("list2 entirely smaller than list1 → list2 fully placed before list1")
    void list2EntirelySmaller() {
        Solution.ListNode list1 = buildList(4, 5, 6);
        Solution.ListNode list2 = buildList(1, 2, 3);
        Solution.ListNode result = solution.mergeTwoLists(list1, list2);
        assertThat(toArray(result)).containsExactly(1, 2, 3, 4, 5, 6);
    }

    @Test
    @DisplayName("all values equal across both lists → duplicates preserved in merged order")
    void allValuesEqual() {
        Solution.ListNode list1 = buildList(2, 2, 2);
        Solution.ListNode list2 = buildList(2, 2);
        Solution.ListNode result = solution.mergeTwoLists(list1, list2);
        assertThat(toArray(result)).containsExactly(2, 2, 2, 2, 2);
    }

    @Test
    @DisplayName("negative and positive values mixed → merged in non-decreasing order")
    void negativeAndPositiveValues() {
        Solution.ListNode list1 = buildList(-10, -3, 5);
        Solution.ListNode list2 = buildList(-8, 0, 2);
        Solution.ListNode result = solution.mergeTwoLists(list1, list2);
        assertThat(toArray(result)).containsExactly(-10, -8, -3, 0, 2, 5);
    }

    @Test
    @DisplayName("node values at min and max boundaries (-100 and 100)")
    void boundaryValues() {
        Solution.ListNode list1 = buildList(-100, 0);
        Solution.ListNode list2 = buildList(-50, 100);
        Solution.ListNode result = solution.mergeTwoLists(list1, list2);
        assertThat(toArray(result)).containsExactly(-100, -50, 0, 100);
    }

    @Test
    @DisplayName("both lists at maximum length (50 nodes each) → merged correctly")
    void maxLengthLists() {
        int[] evens = new int[50];
        int[] odds = new int[50];
        for (int i = 0; i < 50; i++) {
            evens[i] = 2 * i;
            odds[i] = 2 * i + 1;
        }
        Solution.ListNode list1 = buildList(evens);
        Solution.ListNode list2 = buildList(odds);

        Solution.ListNode result = solution.mergeTwoLists(list1, list2);

        int[] expected = new int[100];
        for (int i = 0; i < 100; i++) expected[i] = i;
        assertThat(toArray(result)).containsExactly(expected);
    }
}
