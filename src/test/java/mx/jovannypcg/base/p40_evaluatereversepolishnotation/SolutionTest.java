package mx.jovannypcg.base.p40_evaluatereversepolishnotation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SolutionTest {

    private final Solution solution = new Solution();

    @Test
    @DisplayName("addition then multiplication → ((2 + 1) * 3) = 9")
    void additionThenMultiplication() {
        String[] tokens = {"2", "1", "+", "3", "*"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(9);
    }

    @Test
    @DisplayName("division truncates toward zero before addition → (4 + (13 / 5)) = 6")
    void divisionTruncatesTowardZeroBeforeAddition() {
        String[] tokens = {"4", "13", "5", "/", "+"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(6);
    }

    @Test
    @DisplayName("deeply nested expression with negative operand → 22")
    void deeplyNestedExpressionWithNegativeOperand() {
        String[] tokens = {"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(22);
    }

    @Test
    @DisplayName("single token, no operators → returns the number itself")
    void singleTokenNoOperators() {
        String[] tokens = {"42"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(42);
    }

    @Test
    @DisplayName("subtraction is order-sensitive → 5 - 3 = 2, not 3 - 5")
    void subtractionIsOrderSensitive() {
        String[] tokens = {"5", "3", "-"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(2);
    }

    @Test
    @DisplayName("negative operand as a token → -4 + 2 = -2")
    void negativeOperandAsToken() {
        String[] tokens = {"-4", "2", "+"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(-2);
    }

    @Test
    @DisplayName("negative division truncates toward zero, not floor → 7 / -2 = -3")
    void negativeDivisionTruncatesTowardZero() {
        String[] tokens = {"7", "-2", "/"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(-3);
    }

    @Test
    @DisplayName("boundary operand values -200 and 200 → -200 * 200 = -40000")
    void boundaryOperandValues() {
        String[] tokens = {"-200", "200", "*"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(-40000);
    }

    @Test
    @DisplayName("repeated multiplication of same operand chain → ((3 * 4) * 5) = 60")
    void repeatedMultiplicationChain() {
        String[] tokens = {"3", "4", "*", "5", "*"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(60);
    }

    @Test
    @DisplayName("all four operators combined → ((10 - 2) * 6) / 4 = 12")
    void allFourOperatorsCombined() {
        String[] tokens = {"10", "2", "-", "6", "*", "4", "/"};

        assertThat(solution.evalRPN(tokens)).isEqualTo(12);
    }
}
