package com.assignment.calculator.parser;

/**
 * Represents a token and stores its corresponding value. ie. 1 would be type num and have a number value of 1.
 */
public class Token {
    private Type type;
    private String variableName;
    private Integer numberValue;

    public Token(Type type) {
        this.type = type;
        this.variableName = "";
        this.numberValue = 0;
    }

    public Token(Type type, String variableName) {
        this.type = type;
        this.variableName = variableName;
        this.numberValue = 0;
    }

    public Token(Type type, Integer numberValue) {
        this.type = type;
        this.numberValue = numberValue;
        this.variableName = "";
    }

    public Type getType() {
        return type;
    }

    public String getVariableName() {
        return variableName;
    }

    public Integer getNumberValue() {
        return numberValue;
    }

    public String toString() {
        String stringToReturn;
        if (Type.digit ==  type) {
            stringToReturn = numberValue.toString();
        } else if (Type.var == type) {
            stringToReturn = variableName;
        } else {
            stringToReturn = type.toString();
        }
        return stringToReturn;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof  Token)) {
            return false;
        }

        Token token = (Token)obj;

        return (this.type == token.type &&
                this.numberValue.equals(token.numberValue) &&
                this.variableName.equals(token.variableName));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + numberValue.hashCode();
        result = 31 * result + variableName.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
