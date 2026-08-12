package mx.jovannypcg.base.p39_minstack;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {
    @Test
    @DisplayName("example from problem statement: push, getMin, pop, top, getMin")
    void exampleFromProblemStatement() {
        Solution minStack = new Solution();

        minStack.push(-2);
        minStack.push(0);
        minStack.push(-3);

        assertThat(minStack.getMin()).isEqualTo(-3);

        minStack.pop();

        assertThat(minStack.top()).isEqualTo(0);
        assertThat(minStack.getMin()).isEqualTo(-2);
    }

    @Test
    @DisplayName("single element → top and getMin both return it")
    void singleElement() {
        Solution minStack = new Solution();

        minStack.push(42);

        assertThat(minStack.top()).isEqualTo(42);
        assertThat(minStack.getMin()).isEqualTo(42);
    }

    @Test
    @DisplayName("duplicate minimums → popping one leaves the min unchanged")
    void duplicateMinimums() {
        Solution minStack = new Solution();

        minStack.push(0);
        minStack.push(0);

        assertThat(minStack.getMin()).isEqualTo(0);

        minStack.pop();

        assertThat(minStack.getMin()).isEqualTo(0);
        assertThat(minStack.top()).isEqualTo(0);
    }

    @Test
    @DisplayName("all negative values → getMin tracks the most negative")
    void allNegativeValues() {
        Solution minStack = new Solution();

        minStack.push(-5);
        minStack.push(-10);
        minStack.push(-1);

        assertThat(minStack.getMin()).isEqualTo(-10);

        minStack.pop();

        assertThat(minStack.getMin()).isEqualTo(-10);
        assertThat(minStack.top()).isEqualTo(-10);
    }

    @Test
    @DisplayName("boundary values Integer.MIN_VALUE and Integer.MAX_VALUE")
    void boundaryIntegerValues() {
        Solution minStack = new Solution();

        minStack.push(Integer.MAX_VALUE);
        minStack.push(Integer.MIN_VALUE);

        assertThat(minStack.getMin()).isEqualTo(Integer.MIN_VALUE);
        assertThat(minStack.top()).isEqualTo(Integer.MIN_VALUE);

        minStack.pop();

        assertThat(minStack.getMin()).isEqualTo(Integer.MAX_VALUE);
        assertThat(minStack.top()).isEqualTo(Integer.MAX_VALUE);
    }

    @Test
    @DisplayName("min decreases then increases as new minimums are pushed and popped")
    void minFluctuatesAsElementsAreAddedAndRemoved() {
        Solution minStack = new Solution();

        minStack.push(3);
        assertThat(minStack.getMin()).isEqualTo(3);

        minStack.push(1);
        assertThat(minStack.getMin()).isEqualTo(1);

        minStack.push(5);
        assertThat(minStack.getMin()).isEqualTo(1);

        minStack.pop();
        assertThat(minStack.getMin()).isEqualTo(1);

        minStack.pop();
        assertThat(minStack.getMin()).isEqualTo(3);
    }

    @Test
    @DisplayName("large sequence of pushes and pops maintains correct min")
    void largeSequenceOfPushesAndPops() {
        Solution minStack = new Solution();

        for (int i = 10; i >= 1; i--) {
            minStack.push(i);
        }

        assertThat(minStack.getMin()).isEqualTo(1);

        for (int i = 1; i <= 5; i++) {
            minStack.pop();
        }

        assertThat(minStack.getMin()).isEqualTo(6);
        assertThat(minStack.top()).isEqualTo(6);
    }
}
