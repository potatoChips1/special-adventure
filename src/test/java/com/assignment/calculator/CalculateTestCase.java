package com.assignment.calculator;

import com.assignment.calculator.parser.exceptions.CalculatorExpressionParserException;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class CalculateTestCase {
    private Calculate calculate;

    @Before
    public void setUp() {
        calculate = new Calculate();
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testShouldNotAllowInvalidBrackets() throws CalculatorExpressionParserException{
        String expressionToTest = "(add(3, 4)))";
        calculate.performCalculation(expressionToTest);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testShouldNotAllowOperatorForVariable() throws CalculatorExpressionParserException{
        String expressionToTest = "(let(add, add(3, 4)))";
        calculate.performCalculation(expressionToTest);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testShouldNotAllowVariableForOperator() throws CalculatorExpressionParserException{
        String expressionToTest = "(var(a, add(3, 4)))";
        calculate.performCalculation(expressionToTest);
    }

    @Test
    public void testCalculateAdd() throws CalculatorExpressionParserException {
        String expressionToTest = "add(1, 2)";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(3, result);
    }

    @Test
    public void testCalculateSub() throws CalculatorExpressionParserException {
        String expressionToTest = "sub(111, -30000)";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(30111, result);
    }

    @Test
    public void testCalculateDivRoundTowardsZero() throws CalculatorExpressionParserException {
        String expressionToTest = "div(5, 2)";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(2, result);
    }

    @Test
    public void testCalculateEmbeddedExpressionAsRightOperand() throws CalculatorExpressionParserException {
        String expressionToTest = "add(1, mul(2, 3))";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(7, result);
    }


    @Test
    public void testCalculateEmbeddedExpressionAsBothOperands() throws CalculatorExpressionParserException {
        String expressionToTest = "mul(add(2, 2), div(9, 3))";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(12, result);
    }

    @Test
    public void testCalculateSimpleLet() throws CalculatorExpressionParserException {
        String expressionToTest = "let(a, 5, add(a, a))";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(10, result);
    }

    @Test
    public void testCalculateMediumLet() throws CalculatorExpressionParserException {
        String expressionToTest = "let(a, 5, let(b, mul(a, 10), add(b, a)))";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(55, result);
    }

    @Test
    public void testCalculateComplexLet() throws CalculatorExpressionParserException {
        String expressionToTest = "let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(40, result);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testVariableNameIsCaseSensitive() throws CalculatorExpressionParserException {
        String expressionToTest = "let(a, 4, add(A, a))";
        calculate.performCalculation(expressionToTest);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testOperatorNameIsCaseSensitive() throws CalculatorExpressionParserException {
        String expressionToTest = "lEt(a, 4, add(a, a))";
        calculate.performCalculation(expressionToTest);
    }

    @Test(expected = ArithmeticException.class)
    public void testHandleDivideByZero() throws CalculatorExpressionParserException {
        String expressionToTest = "div(10, 0)";
        calculate.performCalculation(expressionToTest);
    }

    @Test
    public void testHandleVeryLargeNumber() throws CalculatorExpressionParserException {
        String expressionToTest = "mul(32767, 32767)";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(1073676289, result);
    }

    @Test
    public void testHandleVerySmallNumber() throws CalculatorExpressionParserException {
        String expressionToTest = "mul(-32767, 32767)";
        int result = calculate.performCalculation(expressionToTest);
        assertEquals(-1073676289, result);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testHandleExtremelyLargeNumber() throws CalculatorExpressionParserException {
        String expressionToTest = "mul(32723423423423467, 32723423423423467)";
        calculate.performCalculation(expressionToTest);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testHandleExtremelySmallNumber() throws CalculatorExpressionParserException {
        String expressionToTest = "mul(-32723423423423467, 2723423423423467)";
        calculate.performCalculation(expressionToTest);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testMoreThanTwoArgumentsInAnExpression() throws CalculatorExpressionParserException {
        String expressionToTest = "add(-2, 3, 2, 3, 4)";
        calculate.performCalculation(expressionToTest);
    }
}
