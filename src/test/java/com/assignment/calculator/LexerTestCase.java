package com.assignment.calculator;

import com.assignment.calculator.parser.exceptions.CalculatorExpressionParserException;
import com.assignment.calculator.parser.Lexer;
import com.assignment.calculator.parser.Token;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class LexerTestCase {
    @Test
    public void testInputStringIsNull() throws CalculatorExpressionParserException {
        assertEquals(new LinkedList<>(), Lexer.lex(null));
    }

    @Test
    public void testInputStringIsEmpty() throws CalculatorExpressionParserException {
        String testExpression = "";
        assertEquals(new LinkedList<>(), Lexer.lex(testExpression));
    }

    @Test
    public void testShouldTrimWhiteSpaceEverywhereElse() throws CalculatorExpressionParserException {
        String testExpression = "  let    (   a   ,  4    , add  (   a   , a    )   )   ";

        List<Token> actualTokens = Lexer.lex(testExpression);
        List<Token> expectedTokens = TestHelper.createSimpleLet();
        assertEquals(expectedTokens.size(), actualTokens.size());
        assertTrue(expectedTokens.containsAll(actualTokens));
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testShouldNotAllowWhiteSpaceInNumbers() throws CalculatorExpressionParserException {
        String testExpression = "add(35 5, 4)";
        Lexer.lex(testExpression);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testNumberShouldNotBeginWith0() throws CalculatorExpressionParserException {
        String testExpression = "add(355, 04)";
        Lexer.lex(testExpression);
    }

    @Test
    public void testMulIsAValidOperator() throws CalculatorExpressionParserException {
        String testExpression = "mul(2, 4)";
        List<Token> expectedTokens = TestHelper.createSimpleMultExpression();
        List<Token> actualTokens = Lexer.lex(testExpression);
        assertEquals(expectedTokens.size(), actualTokens.size());
        assertTrue(expectedTokens.containsAll(actualTokens));
    }

    @Test
    public void testDivIsAValidOperator() throws CalculatorExpressionParserException {
        String testExpression = "div(4, 4)";
        List<Token> expectedTokens = TestHelper.createSimpleDiv();
        List<Token> actualTokens = Lexer.lex(testExpression);
        assertEquals(expectedTokens.size(), actualTokens.size());
        assertTrue(expectedTokens.containsAll(actualTokens));
    }

    @Test
    public void testLetIsAValidOperator() throws CalculatorExpressionParserException {
        String testExpression = "let(a, let(b, 2, mul(b, b)), add(a, a))";
        List<Token> expectedTokens = TestHelper.createNestedLet();
        List<Token> actualTokens = Lexer.lex(testExpression);
        assertEquals(expectedTokens.size(), actualTokens.size());
        assertTrue(expectedTokens.containsAll(actualTokens));
    }

    @Test
    public void testShouldAllowOnlyZero() throws CalculatorExpressionParserException {
        String testExpression = "add(0, 3)";
        List<Token> expectedTokens = TestHelper.createSimpleAddExpression();
        List<Token> actualTokens = Lexer.lex(testExpression);
        assertEquals(expectedTokens.size(), actualTokens.size());
        assertTrue(expectedTokens.containsAll(actualTokens));
    }

    @Test
    public void testShouldAllowNegativeNumbers() throws CalculatorExpressionParserException {
        String testExpression = "sub(-2, 4)";
        List<Token> expectedTokens = TestHelper.createSimpleSub();
        List<Token> actualTokens = Lexer.lex(testExpression);
        assertEquals(expectedTokens.size(), actualTokens.size());
        assertTrue(expectedTokens.containsAll(actualTokens));
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testShouldNotAllowWhiteSpaceInKeyWords() throws CalculatorExpressionParserException {
        String testExpression = "ad d(3, 4)";
        Lexer.lex(testExpression);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testShouldNotAllowWhiteSpaceInVariableNames() throws CalculatorExpressionParserException {
        String testExpression = "add(ar d, 4)";
        Lexer.lex(testExpression);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testShouldNotAllowInvalidCharacters() throws CalculatorExpressionParserException {
        String testExpression = "add(a, 4&)";
        Lexer.lex(testExpression);
    }

    @Test(expected = CalculatorExpressionParserException.class)
    public void testVariableNameShouldNotStartWithDigit() throws CalculatorExpressionParserException {
        String testExpression = "let(4a, 4, add(a, a))";
        Lexer.lex(testExpression);
    }
}

