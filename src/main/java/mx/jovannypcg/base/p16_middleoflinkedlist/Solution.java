package mx.jovannypcg.base.p16_middleoflinkedlist;

/**
 * Given the head of a singly linked list, return the middle node of the linked list.
 *
 * <p>If there are two middle nodes, return the second middle node.
 *
 * @see <a href="https://leetcode.com/problems/middle-of-the-linked-list/">Middle of the Linked List - LeetCode</a>
 */
public class Solution {

    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    public ListNode middleNode(ListNode head) {
        if (head == null) {
            return head;
        }

        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }

        return slow;
    }
}
