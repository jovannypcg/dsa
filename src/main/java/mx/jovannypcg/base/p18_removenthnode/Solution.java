package mx.jovannypcg.base.p18_removenthnode;

/**
 * Given the head of a linked list, remove the nth node from the end of the list
 * and return its head.
 *
 * @see <a href="https://leetcode.com/problems/remove-nth-node-from-end-of-list/">Remove Nth Node From End of List - LeetCode</a>
 */
public class Solution {

    public static class ListNode {

        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    /*
    head = [1, 2, 3, 4, 5], n = 2
            p
            s
            f

    [1 2 3], n = 3
     s
           f
   p

    Note to self: when introducing a `prev` node, remember to bring up a `dummy` node
                  to keep the reference to the original node.
    */
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode slow = head;
        ListNode fast = head;
        ListNode dummy = new ListNode(-1, head);
        ListNode prev = dummy;

        while (fast != null && n > 0) {
            fast = fast.next;
            n--;
        }

        while (fast != null) {
            fast = fast.next;

            prev = prev.next;
            slow = slow.next;
        }

        prev.next = slow.next;
        slow.next = null;

        return dummy.next;
    }
}
