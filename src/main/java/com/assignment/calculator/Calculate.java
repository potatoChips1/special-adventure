package com.assignment.calculator;

import com.assignment.calculator.parser.*;
import com.assignment.calculator.parser.exceptions.CalculatorExpressionParserException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Evaluates the result of an expression given an abstract syntax tree. A map is used to store the values of variables declared.
 *
 */
public class Calculate {
    private Map<String, Integer> variableMap = new HashMap<>();

    /**
     *
     * @param inputExpression Input calculation expression.
     * @return Integer value of the result.
     * @throws CalculatorExpressionParserException Any invalid input will result in an exception.
     */
    public int performCalculation(String inputExpression) throws CalculatorExpressionParserException {
        List<Token> tokenList = Lexer.lex(inputExpression);
        AbstractSyntaxTree tree = Parser.parseTokensToAST(tokenList);
        return evaluateTree(tree.getRoot());
    }

    /**
     * 1. If it's a let expression, recursively evaluate the middle child which should return the value of the defined variable. Put key/value pair into map.
     * 2. Recursively evaluate the left and right nodes. This will give us an integer value that we can apply an operand to.
     * 3. If a node is a leaf node, return the value. If the value is a variable check the map for the integer value.
     *
     * @param rootNode The root node of a fully parsed abstract syntax tree.
     * @return The result of evaluating the expression.
     * @throws CalculatorExpressionParserException If a variable used does not have a defined value.
     */
    private Integer evaluateTree(Node rootNode) throws CalculatorExpressionParserException {
        Node leftChild = rootNode.getLeftChild();
        Node rightChild = rootNode.getRightChild();
        Node middleChild = rootNode.getMiddleChild();

        if (leftChild != null && middleChild != null) {
            int val = evaluateTree(middleChild);
            variableMap.put(leftChild.getVariableName(), val);
            return evaluateTree(rightChild);
        } else if (leftChild != null && rightChild != null) {
            Type operator = rootNode.getType();
            return performCalculation(evaluateTree(leftChild), evaluateTree((rightChild)), operator);
        } else {
            Integer leafNodeNumber = rootNode.getNumberValue() == null ? variableMap.get(rootNode.getVariableName()) : rootNode.getNumberValue();
            if (leafNodeNumber == null) {
                throw new CalculatorExpressionParserException("The input did not match specifications");
            }
            return leafNodeNumber;
        }
    }

    private Integer performCalculation(Integer operand1, Integer operand2, Type operator) {
            switch (operator) {
                case add:
                    return operand1 + operand2;
                case sub:
                    return operand1 - operand2;
                case mul:
                    return operand1 * operand2;
                case div:
                    return operand1 / operand2;
                default:
                    throw new IllegalArgumentException("Illegal operators");
            }
    }
}
