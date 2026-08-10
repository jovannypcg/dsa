package mx.jovannypcg.base.p37_reverselinkedlist;

/**
 * Given the head of a singly linked list, reverse the list, and return the reversed list.
 *
 * @see <a href="https://leetcode.com/problems/reverse-linked-list/">Reverse Linked List - LeetCode</a>
 */
public class Solution {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode reverseList(ListNode head) {
        return reverseListRecursive(head, null);
    }

    ListNode reverseListRecursive(ListNode curr, ListNode prev) {
        if (curr == null) return prev;

        ListNode next = curr.next;
        curr.next = prev;

        return reverseListRecursive(next, curr);
    }

    public ListNode reverseListIterative(ListNode head) {
        if (head == null) return head;

        ListNode prev = null;
        ListNode curr = head;

        while (curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }
}
