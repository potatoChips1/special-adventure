package com.assignment.calculator.parser;

import com.assignment.calculator.parser.exceptions.CalculatorExpressionParserException;
import org.apache.commons.lang3.StringUtils;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Class to parse a user input expression into tokens defined by the calculator specifications. The order of operations are retained
 * because the expressions are in a fully parenthesized format.
 *
 *
 * Example of valid expressions:
 * add(1, 2)
 * sub(111, -30000)
 * div(5, 2)
 * add(1, mul(2, 3))
 * mul(add(2, 2), div(9, 3))
 * let(a, 5, add(a, a))
 * let(a, 5, let(b, mul(a, 10), add(b, a)))
 * let(a, let(b, 10, add(b, b)), let(b, 20, add(a, b)))
 *
 * ie. add(1, 2) would result in:
 * ["add", "(", "1", ",", "2", ")"]
 */
public class Lexer {
    private static final Integer OPERATOR_LENGTH = 3;
    private static final char NEGATIVE_SIGN = '-';
    private static final char COMMA = ',';
    private static final char LPARENTH = '(';
    private static final char RPARENTH = ')';
    private static final String ERROR_MESSAGE = "The input did not match specifications: ";

    private static final Pattern NUMBER_VALIDATOR_REGEX = Pattern.compile("^((-?[1-9])(\\d+)?)|[0]$");
    private static final Pattern VARIABLE_VALIDATOR_REGEX = Pattern.compile("^([a-zA-Z_])([a-zA-Z0-9_]+)?$");

    private Lexer() {}
    /**
     * Goes through each character in an input string and determines if it belongs to a digit, variable, number, etc.
     *
     *
     * @param inputExpression Direct user input string
     * @return List of parsed tokens based off the input expression.
     * @throws CalculatorExpressionParserException If the number, variable or operator format is invalid.
     */
    public static List<Token> lex(String inputExpression) throws CalculatorExpressionParserException {
        LinkedList<Token> parsedTokens = new LinkedList<>();

        if (StringUtils.isNotBlank(inputExpression)) {
            String trimmedInputExpression = inputExpression.trim();
            for (int i = 0; i < trimmedInputExpression.length();) {
                char currentCharValue = trimmedInputExpression.charAt(i);
                if (isWhiteSpace(currentCharValue)) {
                    i++;
                } else if(isDigit(currentCharValue)) {
                    Integer digit = parseDigit(trimmedInputExpression, i);
                    parsedTokens.add(new Token(Type.digit, digit));
                    i += String.valueOf(digit).length();
                } else if(isComma(currentCharValue)) {
                    parsedTokens.add(new Token(Type.comma));
                    i++;
                } else if(isLeftParenthesis(currentCharValue)) {
                    parsedTokens.add(new Token(Type.lparenth));
                    i++;
                } else if(isRightParenthesis(currentCharValue)) {
                    parsedTokens.add(new Token(Type.rparenth));
                    i++;
                } else if(isLetter(currentCharValue)) {
                    Type operator = parseOperator(trimmedInputExpression, i);
                    if (Type.invalid != operator) {
                        parsedTokens.add(new Token(operator));
                        i += OPERATOR_LENGTH;
                    } else {
                        String variable = parseVariable(trimmedInputExpression, i);
                        parsedTokens.add(new Token(Type.var, variable));
                        i += variable.length();
                    }
                } else {
                    throw new CalculatorExpressionParserException(ERROR_MESSAGE + currentCharValue);
                }
            }
        }
        return Collections.unmodifiableList(parsedTokens);
    }

    private static Type parseOperator(String inputExpression, int offset) {
        String potentialOperator = inputExpression.substring(offset, offset + OPERATOR_LENGTH);

        return parseOperator(potentialOperator);
    }

    private static Type parseOperator(String potentialOperator) {
        switch(potentialOperator) {
            case "add":
                return Type.add;
            case "sub":
                return Type.sub;
            case "div":
                return Type.div;
            case "mul":
                return Type.mul;
            case "let":
                return Type.let;
            default:
                return Type.invalid;
        }
    }

    //Parses a potential variable name and matches the format with a regex expression
    private static String parseVariable(String inputExpression, int offset) throws CalculatorExpressionParserException {
        StringBuilder stringBuilder = new StringBuilder();
        while (offset < inputExpression.length() && inputExpression.charAt(offset) != COMMA && inputExpression.charAt(offset) != RPARENTH) {
            stringBuilder.append(inputExpression.charAt(offset++));
        }
        String potentialVariable = stringBuilder.toString().trim();
        if (!VARIABLE_VALIDATOR_REGEX.matcher(potentialVariable).matches()) {
            throw new CalculatorExpressionParserException(ERROR_MESSAGE + potentialVariable);
        }
        return potentialVariable;
    }

    //Parses a potential digit and matches the format with a regex expression
    private static Integer parseDigit(String inputExpression, int offset) throws CalculatorExpressionParserException {
        StringBuilder stringBuilder = new StringBuilder();
        while (offset < inputExpression.length() && (!isComma(inputExpression.charAt(offset)) && !isRightParenthesis(inputExpression.charAt(offset)))) {
            stringBuilder.append(inputExpression.charAt(offset++));
        }

        String potentialDigit = stringBuilder.toString().trim();
        if (!NUMBER_VALIDATOR_REGEX.matcher(potentialDigit).matches()) {
            throw new CalculatorExpressionParserException(ERROR_MESSAGE + potentialDigit);
        }
        Integer digit;
        try {
            digit = Integer.valueOf(potentialDigit);
        } catch (NumberFormatException nfe) {
            throw new CalculatorExpressionParserException("Invalid number format");
        }
        return digit;
    }

    private static boolean isWhiteSpace(char value) {
        return Character.isWhitespace(value);
    }

    private static boolean isComma(char value) {
        return COMMA == value;
    }

    private static boolean isLeftParenthesis(char value) {
        return LPARENTH == value;
    }

    private static boolean isRightParenthesis(char value) {
        return RPARENTH == value;
    }

    private static boolean isLetter(char value) {
        return Character.isLetter(value);
    }

    private static boolean isDigit(char value) {
        return (Character.isDigit(value) || value == NEGATIVE_SIGN);
    }
}
