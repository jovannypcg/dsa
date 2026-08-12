package mx.jovannypcg.base.p39_minstack;

/**
 * Design a stack that supports push, pop, top, and retrieving the minimum element,
 * all in constant time.
 *
 * <p>Implement the {@code MinStack} class:
 * <ul>
 *   <li>{@code MinStack()} initializes the stack object.</li>
 *   <li>{@code void push(int value)} pushes the element {@code value} onto the stack.</li>
 *   <li>{@code void pop()} removes the element on the top of the stack.</li>
 *   <li>{@code int top()} gets the top element of the stack.</li>
 *   <li>{@code int getMin()} retrieves the minimum element in the stack.</li>
 * </ul>
 *
 * <p>Every function must run in O(1) time complexity.
 *
 * @see <a href="https://leetcode.com/problems/min-stack">Min Stack - LeetCode</a>
 */
public class Solution {
    private Stack stack;
    private Stack minStack;

    public Solution() {
        stack = new Stack();
        minStack = new Stack();
    }

    public void push(int value) {
        stack.push(value);

        if (value <= getMin()) minStack.push(value);
    }

    public void pop() {
        int value = stack.pop();

        if (value == getMin()) minStack.pop();
    }

    public int top() {
        return stack.peek();
    }

    public int getMin() {
        if (!minStack.isEmpty()) return minStack.peek();

        return Integer.MAX_VALUE;
    }

    private static class Stack {
        private Node head = new Node(-1);

        public boolean isEmpty() {
            return head == null || head.next == null;
        }

        public int peek() {
            if (isEmpty()) throw new IllegalStateException("Stack is empty");

            return head.next.value;
        }

        public void push(int value) {
            Node node = new Node(value);
            node.next = head.next;
            head.next = node;
        }

        public int pop() {
            if (isEmpty()) throw new IllegalStateException("Stack is empty");

            Node node = head.next;
            head.next = node.next;
            node.next = null;

            return node.value;
        }

        private static class Node {
            int value;
            Node next;

            public Node(int value) {
                this.value = value;
            }
        }
    }
}
