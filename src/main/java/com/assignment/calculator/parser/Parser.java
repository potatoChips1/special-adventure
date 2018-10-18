package com.assignment.calculator.parser;

import com.assignment.calculator.parser.exceptions.CalculatorExpressionParserException;

import java.util.EmptyStackException;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * Builds an abstract syntax tree from a list of valid tokens. Nodes are created from the root and starting from the left.
 * The left parenthesis and comma tokens are used to create nodes, the right parenthesis token is used to traverse back to the parent node,
 * and operator/variable/number tokens set a value in the node. A stack is used to keep track of parent nodes.
 *
 * ie. add(2, 3) would result in:
 *     add
 *   /     \
 * 2        3
 *
 * ie. let(a, 1, add(a, a)) would result in:
 *
 *     let
 *   /  |   \
 * a    1    add
 * 		    /    \
 * 	       a	  a
 */

public class Parser {
    private static final int MAXIMUM_OPERANDS_FOR_EXPRESSION = 2;

    private Parser() {}
    /**
     *
     * @param tokens An ordered list of parsed tokens (ie. (, add, ), etc.)
     * @return A tree with each token as a node
     * @throws CalculatorExpressionParserException If the list of tokens contains recognizable tokens in an unrecognizable format
     */
    public static AbstractSyntaxTree parseTokensToAST(List<Token> tokens) throws CalculatorExpressionParserException {
        Stack<Node> parentStack = new Stack();
        if (tokens == null || tokens.isEmpty()) {
            throw new NullPointerException("No expression to parse");
        }

        validateTokens(tokens);

        LinkedList<Token> tokensToEvaluate = new LinkedList<>(tokens);
        AbstractSyntaxTree tree = new AbstractSyntaxTree(tokensToEvaluate.removeFirst().getType());
        Node cursor = tree.getRoot();
        try {
            while (!tokensToEvaluate.isEmpty()) {
                Token token = tokensToEvaluate.removeFirst();
                if (Type.lparenth == token.getType()) {
                    parentStack.push(cursor);
                    cursor = tree.addChildNodeToCurrentNode(cursor, new Node());
                } else if (Type.digit == token.getType()) {
                    cursor.setType(token.getType());
                    cursor.setNumberValue(token.getNumberValue());
                } else if (Type.comma == token.getType()) {
                    cursor = parentStack.pop();
                    parentStack.push(cursor);
                    cursor = tree.addChildNodeToCurrentNode(cursor, new Node());
                } else if (Type.rparenth == token.getType()) {
                    cursor = parentStack.pop();
                } else if (Type.var == token.getType()) {
                    cursor.setType(token.getType());
                    cursor.setVariableName(token.getVariableName());
                } else if (isOperator(token.getType())) {
                    cursor.setType(token.getType());
                }
            }
        } catch (EmptyStackException ese) {
            throw new CalculatorExpressionParserException("The input did not match specifications");
        }
        return tree;
    }

    //Validates that at most two operands are passed into each expression
    private static void validateTokens(List<Token> tokens) throws CalculatorExpressionParserException {
        LinkedList<Token> tokensToEvaluate = new LinkedList<>(tokens);
        LinkedList<Token> operandsAndBrackets = new LinkedList<>();
        LinkedList<Token> operands = new LinkedList<>();


        while(!tokensToEvaluate.isEmpty()) {
            Token currentToken = tokensToEvaluate.removeFirst();
            if(Type.var == currentToken.getType() || Type.digit == currentToken.getType()) {
                operandsAndBrackets.add(currentToken);
            } else if(Type.lparenth.equals(currentToken.getType())) {
                operandsAndBrackets.add(currentToken);
            } else if (Type.rparenth.equals(currentToken.getType())) {
                while (!operandsAndBrackets.isEmpty() && Type.lparenth != operandsAndBrackets.peekLast().getType()) {
                    Token operatorOrlParenth = operandsAndBrackets.removeLast();
                    operands.add(operatorOrlParenth);
                }
                if (operands.size() > MAXIMUM_OPERANDS_FOR_EXPRESSION) {
                    throw new CalculatorExpressionParserException("Invalid number of arguments for an expression.");
                }
                operands.clear();
            }
        }
    }

    private static boolean isOperator(Type type) {
        switch (type) {
            case add:
                return true;
            case sub:
                return true;
            case mul:
                return true;
            case div:
                return true;
            case let:
                return true;
            default:
                return false;
        }
    }
}
