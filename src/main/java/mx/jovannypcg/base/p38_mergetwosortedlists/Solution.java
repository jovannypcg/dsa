package mx.jovannypcg.base.p38_mergetwosortedlists;

/**
 * You are given the heads of two sorted linked lists {@code list1} and {@code list2}.
 *
 * <p>Merge the two lists into one sorted list. The list should be made by splicing together
 * the nodes of the first two lists. Return the head of the merged linked list.
 *
 * @see <a href="https://leetcode.com/problems/merge-two-sorted-lists/">Merge Two Sorted Lists - LeetCode</a>
 */
public class Solution {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode mergeTwoLists(ListNode a, ListNode b) {
        ListNode out = new ListNode(-1);
        ListNode currOut = out;
        ListNode currA = a;
        ListNode currB = b;

        while (currA != null && currB != null) {
            if (currA.val < currB.val) {
                currOut.next = currA;
                currA = currA.next;
            } else {
                currOut.next = currB;
                currB = currB.next;
            }

            currOut = currOut.next;
        }

        if (currA != null) currOut.next = currA;
        if (currB != null) currOut.next = currB;

        return out.next;
    }
}
