package com.assignment.calculator;

import com.assignment.calculator.parser.Token;
import com.assignment.calculator.parser.Type;

import java.util.LinkedList;
import java.util.List;

public class TestHelper {

    private TestHelper(){}

    public static List<Token> createSimpleAddExpression() {
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(Type.add));
        tokens.add(new Token(Type.lparenth));
        tokens.add(new Token(Type.digit, 0));
        tokens.add(new Token(Type.comma));
        tokens.add(new Token(Type.digit, 3));
        tokens.add(new Token(Type.rparenth));
        return tokens;
    }
    public static List<Token> createSimpleMultExpression() {
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(Type.mul));
        tokens.add(new Token(Type.lparenth));
        tokens.add(new Token(Type.digit, 2));
        tokens.add(new Token(Type.comma));
        tokens.add(new Token(Type.digit, 4));
        tokens.add(new Token(Type.rparenth));
        return tokens;
    }

    public static List<Token> createSimpleSub() {
        LinkedList<Token> expectedTokens = new LinkedList<>();
        expectedTokens.add(new Token(Type.sub));
        expectedTokens.add(new Token(Type.lparenth));
        expectedTokens.add(new Token(Type.digit, -2));
        expectedTokens.add(new Token(Type.comma));
        expectedTokens.add(new Token(Type.digit, 4));
        expectedTokens.add(new Token(Type.rparenth));
        return expectedTokens;
    }

    public static List<Token> createSimpleDiv() {
        LinkedList<Token> expectedTokens = new LinkedList<>();
        expectedTokens.add(new Token(Type.div));
        expectedTokens.add(new Token(Type.lparenth));
        expectedTokens.add(new Token(Type.digit, 4));
        expectedTokens.add(new Token(Type.comma));
        expectedTokens.add(new Token(Type.digit, 4));
        expectedTokens.add(new Token(Type.rparenth));
        return expectedTokens;
    }

    public static List<Token> createSimpleLet() {
        LinkedList<Token> expectedTokens = new LinkedList<>();
        expectedTokens.add(new Token(Type.let));
        expectedTokens.add(new Token(Type.lparenth));
        expectedTokens.add(new Token(Type.var, "a"));
        expectedTokens.add(new Token(Type.comma));
        expectedTokens.add(new Token(Type.digit, 4));
        expectedTokens.add(new Token(Type.lparenth));
        expectedTokens.add(new Token(Type.add));
        expectedTokens.add(new Token(Type.lparenth));
        expectedTokens.add(new Token(Type.var, "a"));
        expectedTokens.add(new Token(Type.comma));
        expectedTokens.add(new Token(Type.var, "a"));
        expectedTokens.add(new Token(Type.rparenth));
        expectedTokens.add(new Token(Type.rparenth));
        return expectedTokens;
    }

    public static List<Token> createNestedLet() {
        LinkedList<Token> tokens = new LinkedList<>();
        tokens.add(new Token(Type.let));
        tokens.add(new Token(Type.lparenth));
        tokens.add(new Token(Type.var, "a"));
        tokens.add(new Token(Type.comma));
        tokens.add(new Token(Type.let));
        tokens.add(new Token(Type.lparenth));
        tokens.add(new Token(Type.var, "b"));
        tokens.add(new Token(Type.comma));
        tokens.add(new Token(Type.digit, 2));
        tokens.add(new Token(Type.comma));
        tokens.add(new Token(Type.mul));
        tokens.add(new Token(Type.lparenth));
        tokens.add(new Token(Type.var, "b"));
        tokens.add(new Token(Type.comma));
        tokens.add(new Token(Type.var, "b"));
        tokens.add(new Token(Type.rparenth));
        tokens.add(new Token(Type.rparenth));
        tokens.add(new Token(Type.comma));
        tokens.add(new Token(Type.add));
        tokens.add(new Token(Type.lparenth));
        tokens.add(new Token(Type.var, "a"));
        tokens.add(new Token(Type.comma));
        tokens.add(new Token(Type.var, "a"));
        tokens.add(new Token(Type.rparenth));
        tokens.add(new Token(Type.rparenth));
        return tokens;
    }

    public static List<Token> createInvalidInput() {
        LinkedList<Token> expectedTokens = new LinkedList<>();
        expectedTokens.add(new Token(Type.sub));
        expectedTokens.add(new Token(Type.lparenth));
        expectedTokens.add(new Token(Type.lparenth));
        expectedTokens.add(new Token(Type.digit, -2));
        expectedTokens.add(new Token(Type.comma));
        expectedTokens.add(new Token(Type.digit, 4));
        expectedTokens.add(new Token(Type.rparenth));
        return expectedTokens;
    }
}
